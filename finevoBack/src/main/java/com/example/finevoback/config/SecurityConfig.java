package com.example.finevoback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //WebSecurityConfigurerAdapter은 이제 지원x. 필터 체인 활용하기
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll() // 모든 요청에 대해 접근 허용
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // 로그인 페이지 설정
                                .loginProcessingUrl("/login_proc") // 로그인 처리 URL 설정
                                .permitAll() // 로그인 페이지 모든 사용자에게 허용
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // 로그아웃 URL 설정
                                .logoutSuccessUrl("/login") // 로그아웃 후 이동할 URL
                                .permitAll() // 로그아웃은 모든 사용자에게 허용
                )
                .csrf().disable(); // CSRF 보안 비활성화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

