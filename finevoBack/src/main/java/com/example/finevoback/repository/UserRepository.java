package com.example.finevoback.repository;

import com.example.finevoback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name); //AdminView 검색

    boolean existsByUserId(String userId); //Id 중복확인

    Optional<User> findByUserIdAndEmail(String userId, String email); //Pw 찾기

    User findByUserId(String userId); // 사용자 로그인
}