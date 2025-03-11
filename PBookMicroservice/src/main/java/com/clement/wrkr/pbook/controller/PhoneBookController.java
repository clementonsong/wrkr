package com.clement.wrkr.pbook.controller;

import com.clement.wrkr.pbook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/phone-book")
public class PhoneBookController {

    @Autowired
    private PhoneBookService phoneBookService;

    @GetMapping("/{type}")
    public ResponseEntity<List<Map<String, Object>>> getAllContacts(@PathVariable String type) {
        List<Map<String, Object>> contacts = phoneBookService.getAllContacts(type);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/{type}")
    public ResponseEntity<String> addContact(@PathVariable String type, @RequestBody Map<String, String> request) {
        String name = request.get("name");
        String phoneNumber = request.get("phoneNumber");
        phoneBookService.addContact(type, name, phoneNumber);
        return ResponseEntity.ok("Contact added successfully to " + type + " phone book.");
    }

    @DeleteMapping("/{type}/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable String type, @PathVariable int id) {
        phoneBookService.deleteContact(type, id);
        return ResponseEntity.ok("Contact deleted successfully from " + type + " phone book.");
    }

    @GetMapping("/{type}/sort")
    public ResponseEntity<List<Map<String, Object>>> getSortedContacts(@PathVariable String type) {
        List<Map<String, Object>> contacts = phoneBookService.getSortedContacts(type);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/compare")
    public ResponseEntity<List<Map<String, Object>>> comparePhoneBooks() {
        List<Map<String, Object>> uniqueContacts = phoneBookService.comparePhoneBooks();
        return ResponseEntity.ok(uniqueContacts);
    }
}
