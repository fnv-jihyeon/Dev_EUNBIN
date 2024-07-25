package com.example.finevoback.service;

import com.example.finevoback.entity.User;
import com.example.finevoback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverOAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public NaverOAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> getUserAttributes(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        } catch (HttpClientErrorException e) {
            System.err.println("오류가 발생하였습니다: " + e.getResponseBodyAsString());
            throw new RuntimeException("오류가 발생하였습니다", e);
        }

        Map<String, Object> response = responseEntity.getBody();
        if (response == null || response.get("response") == null) {
            throw new RuntimeException("오류가 발생하였습니다");
        }

        // response 키의 값을 사용자 정보로 가져옴
        Map<String, Object> userInfo = (Map<String, Object>) response.get("response");
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        // 연동 관련 : userId나 email이 이미 존재하는지 확인 -> 재구현 필요
        boolean userIdExists = userRepository.existsByUserId(email);

        if (userIdExists) {
            //회원 이력이 있으면
            throw new RuntimeException("기존에 회원가입한 이력이 있습니다");
        } else {
            // 새로운 사용자라면
            User user = new User();
            user.setUserId(email); // 이메일-> userId로 설정 (소셜로그인이므로)
            user.setEmail("");     // 기존 이메일 -> 빈 값으로 설정
            user.setName(name);
            user.setPassword(passwordEncoder.encode("NaverOAuthPassword")); // 임의의 기본 PW 설정
            user.setSocialType("NAVER");

            userRepository.save(user);

            Map<String, Object> result = new HashMap<>();
            result.put("userId", user.getUserId()); // 이메일
            result.put("email", user.getEmail());   // 빈 값
            result.put("name", user.getName());

            return result;
        }
    }

    public void logout(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id={client_id}&client_secret={client_secret}&access_token={access_token}&service_provider=NAVER";
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "50PITAaw8WSkVynGQgS6");
        params.put("client_secret", "50PITAaw8WSkVynGQgS6");
        params.put("access_token", accessToken);

        restTemplate.getForObject(url, Void.class, params);
    }
}

