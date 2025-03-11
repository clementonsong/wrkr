package com.clement.wrkr.auth.controller;

import com.clement.wrkr.auth.model.User;
import com.clement.wrkr.auth.service.UserService;
import com.clement.wrkr.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        boolean success = userService.registerUser(request.get("username"), request.get("password"), request.get("role"));
        if (success) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.badRequest().body("User already exists");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<User> userOpt = userService.authenticate(request.get("username"), request.get("password"));
        if (userOpt.isPresent()) {
            String token = jwtUtil.generateToken(userOpt.get().getUsername(), userOpt.get().getRole());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
