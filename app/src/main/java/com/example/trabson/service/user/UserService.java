package com.example.trabson.service.user;

import com.example.trabson.model.dto.InputLoginDTO;
import com.example.trabson.model.dto.UserDTO;
import com.example.trabson.service.task.LoginTask;

public class UserService {

    public UserDTO Logar(String email, String password){

        LoginTask serv = new LoginTask();

        InputLoginDTO log = new InputLoginDTO(email, password);
        try {
            return serv.execute(log).get();

        } catch (Exception e) {
            return null;

        }
    }
}
