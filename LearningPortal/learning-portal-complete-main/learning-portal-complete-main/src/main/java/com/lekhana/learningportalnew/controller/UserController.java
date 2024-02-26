package com.lekhana.learningportalnew.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lekhana.learningportalnew.dto.FavouriteCourseDTO;
import com.lekhana.learningportalnew.dto.RegisteredCourseDTO;
import com.lekhana.learningportalnew.dto.UserDTO;
import com.lekhana.learningportalnew.entity.CourseEntity;
import com.lekhana.learningportalnew.entity.CourseEntity.Category;
import com.lekhana.learningportalnew.entity.FavouriteCourseEntity;
import com.lekhana.learningportalnew.entity.UserEntity;
import com.lekhana.learningportalnew.mapper.UserMapper;
import com.lekhana.learningportalnew.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService) {
		this.userService = userService;
	}

	//GET ALL USERS
	@GetMapping("/getall")
	public List<UserEntity> getAllUser() {

		log.info("showing all users");
		return userService.getAllUsers();
	}

	//DELETE USERS
	@DeleteMapping("/delete/{deleteId}/{adminId}")
	public void deleteUser(@PathVariable Long deleteId, @PathVariable Long adminId) {
		// finding if the user making the request (admin) exists
		Optional<UserEntity> isAdmin = userService.getUser(adminId);

		// checking if the user making the request (admin) exists and is an admin
		if (isAdmin.isPresent() && (isAdmin.get().getRole() == UserEntity.Role.ADMIN)) {
			userService.deleteUser(deleteId);
			log.info("user deleted");
		} else {
			log.error("Invalid userId or adminId");
		}
	}

	//GET ALL COURSES BY CATEGORY
	@GetMapping("/categories/{category}")
	public List<CourseEntity> getByCategory(@PathVariable Category category) {
		//listing courses by category
		log.info("Listing all the courses by category:{} ", category);
		return userService.getCoursesByCategory(category);
	}

	//LOGIN USER
	@GetMapping("/login/{userId}")
	public Optional<UserEntity> loginUser(@PathVariable Long userId) {
		//finding the user
		Optional<UserEntity> isUser = userService.getUser(userId);

		//if user exists
		if (isUser.isPresent()) {
			log.info("user loggedIn");
			return userService.loginUser(userId);
		}
		return Optional.empty();
	}

	//REGISTER USER
	@PostMapping("/register")
	public UserDTO registerUser(@RequestBody UserEntity user) {

		log.info("User Registered: {}", user);
		UserDTO resUserDTO = UserMapper.userEntitytoDTO(user);

		// Registering the user
		return userService.registerUser(resUserDTO);
	}

	//PURCHASE COURSE
	@PostMapping("/purchase/{userId}/{courseId}")
	public RegisteredCourseDTO purchaseCourse(@PathVariable Long userId, @PathVariable Long courseId) {

		log.info("course purchased:{}", courseId);
		return userService.purchaseCourse(courseId, userId);

	}

	//ADDING A FAVOURITE COURSE
	@PostMapping("/favourite/{registrationId}")
	public FavouriteCourseDTO addFavouriteCourse(@PathVariable Long registrationId) {

		log.info("course added to favourites");
		return userService.favouriteCourse(registrationId);
	}

	//SEE FAVOURITE COURSE
	@GetMapping("/favourite/seeAll/{userId}")
	public List<FavouriteCourseEntity> seeAllFavourite(@PathVariable Long userId) {

		log.info("listing all the favourite courses");
		return userService.seeFavouriteCourses(userId);
	}

}
