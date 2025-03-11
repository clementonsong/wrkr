package com.clement.wrkr.pbook.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PhoneBookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllContacts(String tableName) {
        String query = "SELECT * FROM " + tableName;
        return jdbcTemplate.queryForList(query);
    }

    public void addContact(String tableName, String name, String phoneNumber) {
        String query = "INSERT INTO " + tableName + " (name, phone_number) VALUES (?, ?)";
        jdbcTemplate.update(query, name, phoneNumber);
    }

    public void deleteContact(String tableName, int id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public List<Map<String, Object>> getSortedContacts(String tableName) {
        String query = "SELECT * FROM " + tableName + " ORDER BY name ASC";
        return jdbcTemplate.queryForList(query);
    }

    public List<Map<String, Object>> comparePhoneBooks() {
        String query = "SELECT name FROM personal_book WHERE name NOT IN (SELECT name FROM official_book) " +
                       "UNION " +
                       "SELECT name FROM official_book WHERE name NOT IN (SELECT name FROM personal_book)";
        return jdbcTemplate.queryForList(query);
    }
}
