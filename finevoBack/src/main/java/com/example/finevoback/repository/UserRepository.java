package com.example.finevoback.repository;

import com.example.finevoback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name); //AdminView 검색

    boolean existsByUserId(String userId); //Id 중복확인

    User findByUserId(String userId); // 사용자 로그인
}