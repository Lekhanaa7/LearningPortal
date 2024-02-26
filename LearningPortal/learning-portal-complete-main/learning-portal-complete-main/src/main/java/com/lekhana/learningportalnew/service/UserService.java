package com.lekhana.learningportalnew.service;

import java.util.List;
import java.util.Optional;

import com.lekhana.learningportalnew.dto.FavouriteCourseDTO;
import com.lekhana.learningportalnew.dto.RegisteredCourseDTO;
import com.lekhana.learningportalnew.dto.UserDTO;
import com.lekhana.learningportalnew.entity.CourseEntity;
import com.lekhana.learningportalnew.entity.FavouriteCourseEntity;
import com.lekhana.learningportalnew.entity.UserEntity;

public interface UserService {
	//ADMIN
	//get all users
	List<UserEntity> getAllUsers();

	//get unique user
	Optional<UserEntity> getUser(Long id);

	//delete users
	void deleteUser(Long id);

	//add new users
	UserDTO addUser(UserDTO user);

	//LEARNER
	//get all courses by category
	List<CourseEntity> getCoursesByCategory(CourseEntity.Category category);

	//login user
	Optional<UserEntity> loginUser(Long userId);

	//register user
	UserDTO registerUser(UserDTO user);

	//purchase course
	RegisteredCourseDTO purchaseCourse(Long courseId, Long userId);

	//Favorite course
	FavouriteCourseDTO favouriteCourse(Long registrationId);

	//see favorite courses
	List<FavouriteCourseEntity> seeFavouriteCourses(Long userId);

}
