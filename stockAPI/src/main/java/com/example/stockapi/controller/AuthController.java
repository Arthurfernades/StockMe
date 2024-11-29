package com.example.stockapi.controller;

import com.example.stockapi.model.dto.LoginDTO;
import com.example.stockapi.model.dto.ResponseDTO;
import com.example.stockapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            if (!authService.emailExists(loginDTO.getEmail())) {
                return ResponseEntity.status(404).body(new ResponseDTO("Email not registered", 404));
            }

            if (!authService.validateCredentials(loginDTO.getEmail(), loginDTO.getPassword())) {
                return ResponseEntity.status(401).body(new ResponseDTO("Invalid password", 401));
            }

            return ResponseEntity.ok(new ResponseDTO("Login successful", 200));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO("Internal server error: " + e, 500));
        }
    }

}
