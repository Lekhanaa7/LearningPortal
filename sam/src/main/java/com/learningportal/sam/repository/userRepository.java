package com.learningportal.sam.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learningportal.sam.entity.user;

public interface userRepository extends JpaRepository<user, Long> {
	List<user> findByEmail(String email);
	Optional<user> findById(Long id);


    // Additional query methods can be defined here if needed
}


