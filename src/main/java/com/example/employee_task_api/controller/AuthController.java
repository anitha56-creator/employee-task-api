package com.example.employee_task_api.controller;

import com.example.employee_task_api.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// This is a sample Authentication Controller to generate JWT Token.
// In Real world, there will be a OIDC Provider who will validate username and password
// and generate the JWT token.
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam String username) {
        String token = jwtTokenUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }
}

