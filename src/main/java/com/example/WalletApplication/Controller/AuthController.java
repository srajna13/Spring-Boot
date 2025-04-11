package com.example.WalletApplication.Controller;

import com.example.WalletApplication.Entity.Users;
import com.example.WalletApplication.dto.AuthRequest;
import com.example.WalletApplication.dto.AuthResponse;
import com.example.WalletApplication.Service.AuthService;
import com.example.WalletApplication.Util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) {
        authService.register(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        JwtUtil jwtUtil = new JwtUtil();
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        String username = authentication.getName();
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
