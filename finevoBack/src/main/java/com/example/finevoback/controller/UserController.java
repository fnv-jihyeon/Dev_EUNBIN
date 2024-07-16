package com.example.finevoback.controller;

import com.example.finevoback.entity.User;
import com.example.finevoback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword()); // 저장부터 비밀번호 암호화
            user.setPassword(encodedPassword);

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

    @GetMapping("/users/{userId}/exists")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String userId) {
        boolean exists = userRepository.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        String userId = loginUser.getUserId();
        String password = loginUser.getPassword();

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자를 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok("로그인 성공");
    }


}