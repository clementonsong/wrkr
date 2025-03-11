package com.clement.wrkr.auth.filter;

import com.clement.wrkr.auth.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private UserDetails userDetails;

    @Test
    void testFilter_SkipsAuthForLogin() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/auth/login");

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void testFilter_ProcessesValidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/protected");  
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");

        lenient().when(jwtUtil.extractUsername("validToken")).thenReturn("testUser"); 
        lenient().when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }


    @Test
    void testFilter_HandlesInvalidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        

        doThrow(new RuntimeException("Invalid token")).when(jwtUtil).extractUsername(anyString());

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response); 
    }
    
}
