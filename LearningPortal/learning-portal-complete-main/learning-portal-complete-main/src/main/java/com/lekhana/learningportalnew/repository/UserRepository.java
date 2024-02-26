package com.lekhana.learningportalnew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lekhana.learningportalnew.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
