package com.learningportal.sam.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learningportal.sam.entity.course;

public interface courseRepository extends JpaRepository<course, Long> {
    
}


