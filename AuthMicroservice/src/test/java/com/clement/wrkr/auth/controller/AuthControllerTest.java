package com.clement.wrkr.auth.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.clement.wrkr.auth.model.User;
import com.clement.wrkr.auth.service.UserService;
import com.clement.wrkr.auth.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Test
    void testRegisterUser_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        when(userService.registerUser("testUser", "password123", "USER")).thenReturn(true);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", \"password\":\"password123\", \"role\":\"USER\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testRegisterUser_Failure() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        when(userService.registerUser("testUser", "password123", "USER")).thenReturn(false);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", \"password\":\"password123\", \"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists"));
    }

    @Test
    void testLogin_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        User mockUser = new User("testUser", "password123", "USER");
        when(userService.authenticate("testUser", "password123")).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken("testUser", "USER")).thenReturn("mockJwtToken");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockJwtToken"));
    }

    @Test
    void testLogin_Failure() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        when(userService.authenticate("testUser", "wrongPassword")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }
}
