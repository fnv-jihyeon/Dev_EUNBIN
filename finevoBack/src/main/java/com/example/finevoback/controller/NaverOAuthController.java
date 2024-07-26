package com.example.finevoback.controller;

import com.example.finevoback.entity.User;
import com.example.finevoback.service.NaverOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NaverOAuthController {

    private static final Logger logger = LoggerFactory.getLogger(NaverOAuthController.class);

    private final NaverOAuthService naverOAuthService;

    @Autowired
    public NaverOAuthController(NaverOAuthService naverOAuthService) {
        this.naverOAuthService = naverOAuthService;
    }

    public static class TokenPayload {
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        @Override
        public String toString() {
            return "TokenPayload{" +
                    "accessToken='" + accessToken + '\'' +
                    '}';
        }
    }

    @PostMapping("/naverlogin")
    public ResponseEntity<Map<String, Object>> callbackPost(@RequestBody TokenPayload payload) {
        String accessToken = payload.getAccessToken();
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Access token이 누락되었습니다."));
        }
        Map<String, Object> userInfo = naverOAuthService.getUserAttributes(accessToken);
        logger.info("사용자 정보: {}", userInfo);

        try {
            naverOAuthService.handleUserAttributes(userInfo);
        } catch (Exception e) {
            logger.info("사용자 속성 처리 중 오류 발생");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "사용자 속성 처리 중 오류 발생: " + e.getMessage()));
        }

        return ResponseEntity.ok(userInfo);
    }
    @PostMapping("/naverlogout")
    public ResponseEntity<String> logout(@RequestBody TokenPayload payload) {
        String accessToken = payload.getAccessToken();
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.badRequest().body("토큰 오류 발생");
        }
        naverOAuthService.logout(accessToken);
        logger.info("로그아웃 완료.");
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}
