package com.example.employee_task_api.controller;

import com.example.employee_task_api.common.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnJwtToken() {
        // Arrange
        String username = "john.doe";
        String expectedToken = "mocked-jwt-token";

        when(jwtTokenUtil.generateToken(username)).thenReturn(expectedToken);

        // Act
        ResponseEntity<String> response = authController.getToken(username);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedToken, response.getBody());
        verify(jwtTokenUtil, times(1)).generateToken(username);
    }
}