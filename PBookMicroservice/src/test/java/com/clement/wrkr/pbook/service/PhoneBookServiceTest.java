package com.clement.wrkr.pbook.service;

import com.clement.wrkr.pbook.repository.PhoneBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneBookServiceTest {

    @Mock
    private PhoneBookRepository phoneBookRepository;

    @InjectMocks
    private PhoneBookService phoneBookService;

    @Test
    void testAddContact_ValidData() {
        phoneBookService.addContact("personal", "John Doe", "1234567890");
        verify(phoneBookRepository, times(1)).addContact("personal_book", "John Doe", "1234567890");
    }

    @Test
    void testAddContact_InvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneBookService.addContact("personal", "John", "1234567890"));

        assertEquals("Contact must have both first and last names", exception.getMessage());
    }

    @Test
    void testAddContact_InvalidPhoneNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneBookService.addContact("personal", "John Doe", "123ABC"));

        assertEquals("Alphabets are not allowed in a contact's phone number", exception.getMessage());
    }

    @Test
    void testDeleteContact() {
        phoneBookService.deleteContact("personal", 1);
        verify(phoneBookRepository, times(1)).deleteContact("personal_book", 1);
    }
}
