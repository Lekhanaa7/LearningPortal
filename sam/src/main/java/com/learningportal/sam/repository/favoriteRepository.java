package com.learningportal.sam.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learningportal.sam.entity.course;
import com.learningportal.sam.entity.favorite;
import com.learningportal.sam.entity.user;

public interface favoriteRepository extends JpaRepository<favorite, Long> {
    // Additional query methods can be defined here if needed
	List<favorite> findByUser(user user);

	List<course> findFavoriteCoursesByUser(user user);

}

