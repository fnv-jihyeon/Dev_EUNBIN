package com.example.finevoback.service;

import com.example.finevoback.entity.User;
import com.example.finevoback.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

@Service
public class NaverOAuthService {

    private static final Logger logger = LoggerFactory.getLogger(NaverOAuthService.class);

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
            throw new RuntimeException("오류가 발생하였습니다", e);
        }

        Map<String, Object> response = responseEntity.getBody();
        if (response == null || response.get("response") == null) {
            throw new RuntimeException("오류가 발생하였습니다");
        }

        Map<String, Object> userInfo = (Map<String, Object>) response.get("response");
        return userInfo;
    }

    @Transactional
    public Optional<User> handleUserAttributes(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Optional<User> optionalUser = userRepository.findByEmailAndName(email, name);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            logger.info("User found: {}", user);

            user.setSocialType("NAVER");
            userRepository.save(user);
            
            logger.info("소셜 회원(NAVER)으로 연동되었습니다: {}", user);
        } else {
            logger.info("기존에 회원가입한 이력이 없습니다. 홈페이지에서 가입을 진행해주세요.");
        }
        return optionalUser;
    }

    public void logout(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id={client_id}&client_secret={client_secret}&access_token={access_token}&service_provider=NAVER";
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "50PITAaw8WSkVynGQgS6");
        params.put("client_secret", "kIqdzsVf8y");
        params.put("access_token", accessToken);

        restTemplate.getForObject(url, Void.class, params);
    }
}
