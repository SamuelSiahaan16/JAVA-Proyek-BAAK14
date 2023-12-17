package com.baak14.javabaak14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.baak14.javabaak14.model.User;
import com.baak14.javabaak14.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> listAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            // Check if the username already exists in the database
            if (userService.isUsernameExists(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"message\": \"Username already exists. Please choose a different username.\"}");
            }

            // Check if there is an existing user with the same nim, email, or noKtp
            if (userService.isDuplicateUser(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"message\": \"User with the same nim, email, or noKtp already exists.\"}");
            }

            // If the username is unique and there is no duplicate user, proceed to add User
            userService.addUser(user);
            return ResponseEntity.ok("{\"message\": \"User added successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to add user. Error: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            User existingUser = userService.getUserById(id);
            if (existingUser != null) {
                // Check if there is an existing user with the same nim, email, or noKtp
                if (userService.isDuplicateUser(user)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("{\"message\": \"User with the same nim, email, or noKtp already exists.\"}");
                }

                existingUser.setNim(user.getNim());
                existingUser.setNama(user.getNama());
                existingUser.setEmail(user.getEmail());
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());
                existingUser.setNoKtp(user.getNoKtp());

                // Update other fields as needed
                userService.updateUser(existingUser);
                return ResponseEntity.ok("{\"message\": \"User updated successfully.\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"User not found with ID: " + id + "\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to update user. Error: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("{\"message\": \"User deleted successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to delete user. Error: " + e.getMessage() + "\"}");
        }
    }
}
