package com.learningportal.sam.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learningportal.sam.entity.course;
import com.learningportal.sam.entity.enrollment;
import com.learningportal.sam.entity.user;

public interface enrollmentRepository extends JpaRepository<enrollment, Long> {
    // Additional query methods can be defined here if needed
	List<enrollment> findByUser(user user);

	List<course> findEnrolledCoursesByUser(user user);

}

