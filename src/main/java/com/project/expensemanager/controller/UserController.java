package com.project.expensemanager.controller;

import com.project.expensemanager.domain.User;
import com.project.expensemanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), OK);
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUsers(@RequestBody User user) {
        boolean newUser = userService.registerNewUser(user);
        if (newUser) {
            return ResponseEntity.ok("{\"message\":\"User registered successfully\"}");
        } else {
            return ResponseEntity.status(BAD_REQUEST).body("{\"message\":\"Failed to register user\"}");
        }
    }

    @DeleteMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("{\"message\":\"User deleted successfully\"}");
    }
}
