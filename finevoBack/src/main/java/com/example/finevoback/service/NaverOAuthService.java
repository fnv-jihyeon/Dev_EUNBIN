package com.example.finevoback.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverOAuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getAccessToken(String code, String state) {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", "50PITAaw8WSkVynGQgS6");
        params.put("client_secret", "kIqdzsVf8y");
        params.put("code", code);
        params.put("state", state);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, params, String.class);

        try {
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
            return (String) responseBody.get("access_token");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse access token", e);
        }
    }

    public Map<String, Object> getUserAttributes(String accessToken) {
        String userInfoEndpoint = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, String.class);

        try {
            return objectMapper.readValue(response.getBody(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse user information", e);
        }
    }

    public void logout(String accessToken) {
        String logoutEndpoint = "https://nid.naver.com/oauth2.0/token";
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "delete");
        params.put("client_id", "50PITAaw8WSkVynGQgS6");
        params.put("client_secret", "kIqdzsVf8y");
        params.put("access_token", accessToken);
        params.put("service_provider", "NAVER");

        restTemplate.postForEntity(logoutEndpoint, params, String.class);
    }
}
