package com.example.demo.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginEntityRepository extends JpaRepository<UserLoginEntity, Integer> {
}
