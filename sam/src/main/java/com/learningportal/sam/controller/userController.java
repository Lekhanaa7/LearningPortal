package com.learningportal.sam.controller;

import com.learningportal.sam.service.userService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class userController {

    private final userService userService = new userService();

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestParam String email) {
        // Assuming you want to return a response indicating authentication status
        return userService.authenticateUser(email) != null ? "Authenticated" : "Authentication Failed";
    }

    @GetMapping
    public List<String> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public String findUserById(@PathVariable long id) {
        // Assuming you want to return user details as a string
        return userService.findUserById(id).toString();
    }

    @PostMapping
    public String createUser(@RequestParam String name, @RequestParam String email, @RequestParam String role) {
        // Assuming you want to return a response indicating user creation status
        return userService.createUser(name, email, role);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String role) {
        // Assuming you want to return a response indicating user update status
        return userService.updateUser(id, name, email, role);
    }
}

