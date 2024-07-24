package com.example.finevoback.controller;

import com.example.finevoback.service.NaverOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class NaverOAuthController {

    @Autowired
    private NaverOAuthService naverOAuthService;

    public static class TokenPayload {
        public String accessToken;

        public TokenPayload() {}

        @Override
        public String toString() {
            return "TokenPayload{" +
                    "accessToken='" + accessToken + '\'' +
                    '}';
        }
    }

    @PostMapping("/naverlogin")
    public ResponseEntity<Map<String, Object>> callbackPost(@RequestBody TokenPayload payload) {
        System.out.println("Received TokenPayload: " + payload); // 디버깅용 콘솔 로그 추가
        String accessToken = payload.accessToken;
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing access token"));
        }
        Map<String, Object> userInfo = naverOAuthService.getUserAttributes(accessToken);
        System.out.println("User Info: " + userInfo); // 디버깅용 콘솔 로그 추가
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/naverlogout")
    public ResponseEntity<String> logout(@RequestBody TokenPayload payload) {
        System.out.println("Received TokenPayload for logout: " + payload);
        String accessToken = payload.accessToken;
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body("토큰 오류 발생");
        }

        // NaverOAuthService를 사용하여 Naver 로그아웃 처리
        naverOAuthService.logout(accessToken);

        System.out.println("로그아웃되었습니다.");
        return ResponseEntity.ok("로그아웃되었습니다");
    }
}
