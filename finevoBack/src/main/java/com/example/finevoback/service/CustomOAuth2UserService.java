package com.example.finevoback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final NaverOAuthService naverOAuthService;

    @Autowired
    public CustomOAuth2UserService(@Lazy NaverOAuthService naverOAuthService) {
        this.naverOAuthService = naverOAuthService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        String accessToken = userRequest.getAccessToken().getTokenValue();
        Map<String, Object> attributes = naverOAuthService.getUserAttributes(accessToken);

        logger.info("Naver에서 가져온 사용자 속성: {}", attributes);

        try {
            naverOAuthService.handleUserAttributes(attributes);
        } catch (Exception e) {
            logger.error("사용자 속성 처리 중 오류 발생");
        }

        return new DefaultOAuth2User(

                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email"
        );
    }
}