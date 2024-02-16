package com.learningportal.sam.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learningportal.sam.entity.course;
import com.learningportal.sam.entity.user;
import com.learningportal.sam.repository.courseRepository;
import com.learningportal.sam.repository.userRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class courseService {
	
	private courseRepository courseRepo;
	private userRepository userRepository;
	
	public List<String> getAllCourses() {
		List<course> courseList = courseRepo.findAll();
		
		if (courseList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<String> courseResponseList = new ArrayList<>();
		
		courseList.forEach(course -> {
			String courseResponse = "Course Title: " + course.getTitle();
			courseResponseList.add(courseResponse);
		});
		
		return courseResponseList;
	}
	
	public String createCourse(String title, String description, String category, int durationHours) throws Exception {
		Optional<user> userOptional = userRepository.findById(13L);
		if (userOptional.isEmpty()) {
			throw new Exception("Invalid request. User not present.");
		}
		user user = userOptional.get();
		
		course course = new course();
		course.setTitle(title);
		course.setDescription(description);
		course.setCategory(category);
		course.setDurationHours(durationHours);
		course.setAuthor(user);
		
		course = courseRepo.save(course);
		
		return "Course Created: " + course.getTitle();
	}

	public String updateCourse(long id, String title, String description, String category, int durationHours) {
		Optional<course> courseOptional = courseRepo.findById(id);
		if (courseOptional.isPresent()) {
			course existingCourse = courseOptional.get();
			
			if (title != null) {
				existingCourse.setTitle(title);
			}
			
			if (description != null) {
				existingCourse.setDescription(description);
			}
			
			if (category != null) {
				existingCourse.setCategory(category);
			}
			
			if (durationHours > 0) {
				existingCourse.setDurationHours(durationHours);
			}
			
			courseRepo.save(existingCourse);
			
			return "Course Updated: " + existingCourse.getTitle();
		}
		return "Course not found";
	}
}
