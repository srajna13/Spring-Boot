package com.example.WalletApplication.service;

import com.example.WalletApplication.Entity.Users;
import com.example.WalletApplication.Exception.UserAlreadyExistsException;
import com.example.WalletApplication.Repository.UserRepository;
import com.example.WalletApplication.Service.AuthService;
import com.example.WalletApplication.Util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


public class AuthServiceTest {
    
    @InjectMocks
    AuthService authService;
    
    @Mock
    UserRepository userRepository;


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        String username = "testuser";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        authService.register(username, rawPassword);

        // Verify save is called with correct encoded password
        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals(username) && user.getPassword().equals(encodedPassword)
        ));
    }

    @Test
    void testRegister_UsernameAlreadyExists() {
        String username = "testuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new Users()));

        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.register(username, "password");
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    void testAuthenticate_Success() {
        String username = "testuser";
        String password = "password";
        String token = "generated-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
    .thenReturn(mock(org.springframework.security.core.Authentication.class));

        when(jwtUtil.generateToken(username)).thenReturn(token);

        String returnedToken = authService.authenticate(username, password);

        assertEquals(token, returnedToken);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(username);
    }
}
