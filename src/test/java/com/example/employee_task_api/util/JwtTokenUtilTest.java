package com.example.employee_task_api.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.employee_task_api.common.util.JwtTokenUtil;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        jwtTokenUtil.init(); // Manually call @PostConstruct
    }

    @Test
    void testGenerateAndValidateToken() {
        // Arrange
        String username = "john.doe";

        // Act
        String token = jwtTokenUtil.generateToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(jwtTokenUtil.validateToken(token));
        assertEquals(username, jwtTokenUtil.getUsernameFromToken(token));
    }

    @Test
    void testValidateTokenFailsWithInvalidToken() {
        // Invalid or tampered token
        String invalidToken = "this.is.an.invalid.token";

        // Act
        boolean isValid = jwtTokenUtil.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "test.user";
        String token = jwtTokenUtil.generateToken(username);

        String extractedUsername = jwtTokenUtil.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }
}
