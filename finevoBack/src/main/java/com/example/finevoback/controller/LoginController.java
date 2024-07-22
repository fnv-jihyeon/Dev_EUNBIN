package com.example.finevoback.controller;

import com.example.finevoback.entity.User;
import com.example.finevoback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login_proc")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        User user = userRepository.findByUserId(loginUser.getUserId());
        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("로그인 성공");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디나 비밀번호가 일치하지 않습니다.");
        }
    }
}
