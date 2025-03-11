package com.clement.wrkr.pbook.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneBookRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PhoneBookRepository phoneBookRepository;

    @Test
    void testAddContact() {
        phoneBookRepository.addContact("personal_book", "John Doe", "1234567890");
        verify(jdbcTemplate, times(1)).update("INSERT INTO personal_book (name, phone_number) VALUES (?, ?)", "John Doe", "1234567890");
    }

    @Test
    void testDeleteContact() {
        phoneBookRepository.deleteContact("personal_book", 1);
        verify(jdbcTemplate, times(1)).update("DELETE FROM personal_book WHERE id = ?", 1);
    }
}
