package com.clement.wrkr.auth.service;

import com.clement.wrkr.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password, String role) {
        String existingUser = "SELECT COUNT(*) FROM users WHERE username = ?";
        int count = jdbcTemplate.queryForObject(existingUser, Integer.class, username);

        if (count > 0) {
            return false;
        }

        String encryptedPassword = passwordEncoder.encode(password);
        String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertQuery, username, encryptedPassword, role);
        return true;
    }

    public Optional<User> authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(query, new Object[]{username}, (rs, rowNum) ->
                    new User(rs.getString("username"), rs.getString("password"), rs.getString("role"))
            );

            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(query, new Object[]{username}, (rs, rowNum) ->
                    new User(rs.getString("username"), rs.getString("password"), rs.getString("role"))
            );
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
