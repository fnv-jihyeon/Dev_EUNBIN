package com.example.finevoback.controller;
import com.example.finevoback.entity.User;
import com.example.finevoback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/membership")
    public String getMembershipPage() {
        return "membership";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            userRepository.delete(user.get());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userRepository.findByName(query);
        return ResponseEntity.ok(users);
    }


}