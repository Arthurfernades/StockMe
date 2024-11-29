package com.example.stockapi.service;

import com.example.stockapi.helper.UserHelper;
import com.example.stockapi.model.User;
import com.example.stockapi.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserHelper("Email already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setBirthDate(userDetails.getBirthDate());
            user.setGender(userDetails.getGender());
            user.setProfit(userDetails.getProfit());
            user.setBudget(userDetails.getBudget());
            return userRepository.save(user);
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserHelper("Email not registered");
        }

        if (!user.get().getPassword().equals(password)) {
            throw new UserHelper("Invalid password");
        }

        return true;
    }
}

