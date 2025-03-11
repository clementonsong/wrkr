package com.clement.wrkr.pbook.controller;

import com.clement.wrkr.pbook.service.PhoneBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PhoneBookControllerTest {

    @Mock
    private PhoneBookService phoneBookService;

    @InjectMocks
    private PhoneBookController phoneBookController;

    private MockMvc mockMvc;

    @Test
    void testGetAllContacts() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneBookController).build();
        when(phoneBookService.getAllContacts("personal")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/phone-book/personal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testAddContact() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneBookController).build();
        String jsonPayload = "{\"name\":\"John Doe\", \"phoneNumber\":\"1234567890\"}";

        mockMvc.perform(post("/api/phone-book/personal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("Contact added successfully to personal phone book."));

        verify(phoneBookService, times(1)).addContact("personal", "John Doe", "1234567890");
    }

    @Test
    void testDeleteContact() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneBookController).build();

        mockMvc.perform(delete("/api/phone-book/personal/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Contact deleted successfully from personal phone book."));

        verify(phoneBookService, times(1)).deleteContact("personal", 1);
    }

    @Test
    void testGetSortedContacts() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneBookController).build();
        when(phoneBookService.getSortedContacts("personal")).thenReturn(List.of(Map.of("name", "Alice")));

        mockMvc.perform(get("/api/phone-book/personal/sort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void testComparePhoneBooks() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneBookController).build();
        when(phoneBookService.comparePhoneBooks()).thenReturn(List.of(Map.of("name", "Bob")));

        mockMvc.perform(get("/api/phone-book/compare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob"));
    }
}
