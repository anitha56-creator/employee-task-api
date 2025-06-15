package com.example.employee_task_api.filter;

import com.example.employee_task_api.common.filter.JwtAuthFilter;
import com.example.employee_task_api.common.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestableJwtAuthFilter extends JwtAuthFilter {

    public TestableJwtAuthFilter(JwtTokenUtil jwtTokenUtil) {
        super(jwtTokenUtil);
    }

    // Expose doFilterInternal for testing
    public void invokeDoFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }
}