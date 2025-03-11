package com.clement.wrkr.pbook.service;

import com.clement.wrkr.pbook.repository.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PhoneBookService {

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    private String getTableName(String type) {
        switch (type.toLowerCase()) {
            case "personal":
                return "personal_book";
            case "official":
                return "official_book";
            default:
                throw new IllegalArgumentException("Invalid phone book type. Use 'personal' or 'official'.");
        }
    }

    public List<Map<String, Object>> getAllContacts(String type) {
        return phoneBookRepository.getAllContacts(getTableName(type));
    }

    public void addContact(String type, String name, String phoneNumber) {
        validateName(name);
        validatePhoneNumber(phoneNumber);
        phoneBookRepository.addContact(getTableName(type), name, phoneNumber);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be blank");
        }
        if (!name.contains(" ")) {
            throw new IllegalArgumentException("Contact must have both first and last names");
        }
        if (name.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Numbers are not allowed in a contact's name");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact phone number cannot be blank");
        }
        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Alphabets are not allowed in a contact's phone number");
        }
    }

    public void deleteContact(String type, int id) {
        phoneBookRepository.deleteContact(getTableName(type), id);
    }

    public List<Map<String, Object>> getSortedContacts(String type) {
        return phoneBookRepository.getSortedContacts(getTableName(type));
    }

    public List<Map<String, Object>> comparePhoneBooks() {
        return phoneBookRepository.comparePhoneBooks();
    }
}
