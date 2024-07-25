package com.example.finevoback.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final NaverOAuthService naverOAuthService;

    // @Lazy => for 빈의 지연 초기화
    public CustomOAuth2UserService(@Lazy NaverOAuthService naverOAuthService) {
        this.naverOAuthService = naverOAuthService;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        String accessToken = userRequest.getAccessToken().getTokenValue();
        Map<String, Object> attributes = naverOAuthService.getUserAttributes(accessToken);
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email"
        );
    }
}