package com.example.finevoback.controller;

import com.example.finevoback.service.NaverOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class NaverOAuthController {

    @Autowired
    private NaverOAuthService naverOAuthService;

    @PostMapping("/naverlogin")
    public RedirectView callbackPost(@RequestBody Map<String, String> payload) {
        String accessToken = payload.get("accessToken");
        if (accessToken == null || accessToken.isEmpty()) {
            return new RedirectView("http://localhost:3000/error?message=Missing access token");
        }
        Map<String, Object> userInfo = naverOAuthService.getUserAttributes(accessToken);
        System.out.println("User Info: " + userInfo);
        return new RedirectView("http://localhost:3000");
    }

    @PostMapping("/naverlogout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> payload) {
        String accessToken = payload.get("accessToken");
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body("토큰 오류 발생");
        }
        naverOAuthService.logout(accessToken);
        return ResponseEntity.ok("로그아웃되었습니다");
    }
}
