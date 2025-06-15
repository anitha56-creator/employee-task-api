package com.example.employee_task_api.filter;

import com.example.employee_task_api.common.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthFilterTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TestableJwtAuthFilter jwtAuthFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_withValidToken_setsAuthentication() throws ServletException, IOException {
        String token = "valid-token";
        String username = "john.doe";

        request.addHeader("Authorization", "Bearer " + token);

        when(jwtTokenUtil.validateToken(token)).thenReturn(true);
        when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);

        jwtAuthFilter.invokeDoFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(username, authentication.getPrincipal());
        assertTrue(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_withInvalidToken_doesNotSetAuthentication() throws ServletException, IOException {
        String token = "invalid-token";

        request.addHeader("Authorization", "Bearer " + token);

        when(jwtTokenUtil.validateToken(token)).thenReturn(false);

        jwtAuthFilter.invokeDoFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_withoutAuthorizationHeader() throws ServletException, IOException {

        jwtAuthFilter.invokeDoFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain, times(1)).doFilter(request, response);
    }
}