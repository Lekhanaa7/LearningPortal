package com.lekhana.learningportalnew.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lekhana.learningportalnew.dto.FavouriteCourseDTO;
import com.lekhana.learningportalnew.dto.RegisteredCourseDTO;
import com.lekhana.learningportalnew.dto.UserDTO;
import com.lekhana.learningportalnew.entity.CourseEntity;
import com.lekhana.learningportalnew.entity.CourseEntity.Category;
import com.lekhana.learningportalnew.entity.FavouriteCourseEntity;
import com.lekhana.learningportalnew.entity.RegisteredCourseEntity;
import com.lekhana.learningportalnew.entity.UserEntity;
import com.lekhana.learningportalnew.mapper.FavouriteMapper;
import com.lekhana.learningportalnew.mapper.RegisteredMapper;
import com.lekhana.learningportalnew.mapper.UserMapper;
import com.lekhana.learningportalnew.repository.CourseRepository;
import com.lekhana.learningportalnew.repository.FavouriteCourseRepository;
import com.lekhana.learningportalnew.repository.RegisteredCourseRepository;
import com.lekhana.learningportalnew.repository.UserRepository;
import com.lekhana.learningportalnew.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final RegisteredCourseRepository registeredCourseRepository;
	private final FavouriteCourseRepository favouriteCourseRepository;

	public UserServiceImpl(UserRepository userRepository, RegisteredCourseRepository registeredCourseRepository,
			CourseRepository courseRepository, FavouriteCourseRepository favouriteCourseRepository) {
		this.userRepository = userRepository;
		this.registeredCourseRepository = registeredCourseRepository;
		this.courseRepository = courseRepository;
		this.favouriteCourseRepository = favouriteCourseRepository;
	}

	//get all users
	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();

	}

	@Override
	public Optional<UserEntity> getUser(Long id) {
		return userRepository.findById(id);
	}

	//deleting an user
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);

	}

	//adding and user by a admin
	@Override
	public UserDTO addUser(UserDTO user) {
		UserEntity userEntity = UserMapper.populateUser(user);
		UserEntity resUserEntity = userRepository.save(userEntity);
		UserDTO resUserDTO = UserMapper.userEntitytoDTO(resUserEntity);
		return resUserDTO;
	}

	//get courses by category
	@Override
	public List<CourseEntity> getCoursesByCategory(Category category) {
		return courseRepository.findByCategory(category);
	}

	//logging in an user
	@Override
	public Optional<UserEntity> loginUser(Long userId) {
		return userRepository.findById(userId);
	}

	//registering an user
	@Override
	public UserDTO registerUser(UserDTO user) {

		UserEntity userEntity = UserMapper.populateUser(user);
		UserEntity resUserEntity = userRepository.save(userEntity);
		UserDTO resUserDTO = UserMapper.userEntitytoDTO(resUserEntity);
		return resUserDTO;
	}

	//purchasing a course 
	@Override
	@Transactional
	public RegisteredCourseDTO purchaseCourse(Long courseId, Long userId) {
		//finding if the course and user exist
		Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
		Optional<UserEntity> optionalUser = userRepository.findById(userId);

		//if course and user exist
		if (!optionalCourse.isEmpty() && !optionalUser.isEmpty()) {
			CourseEntity course = optionalCourse.get();
			UserEntity user = optionalUser.get();

			// Check if the user is a learner before allowing the purchase
			if (user.getRole() == UserEntity.Role.LEARNER) {
				// set course and user reference in registered course
				RegisteredCourseEntity registeredCourse = new RegisteredCourseEntity();
				registeredCourse.setCourse(course);
				registeredCourse.setUser(user);

				// saving the registered course
				RegisteredCourseEntity regCourse = registeredCourseRepository.save(registeredCourse);
				RegisteredCourseDTO registeredCourseDTO = RegisteredMapper.regCourseEntitytoDTO(regCourse);

				return registeredCourseDTO;
			} else {
				// If the user is not a learner, return an error or handle accordingly
				// For example, you can throw an exception or return a specific DTO indicating the error
				// Here, I'm returning a RegisteredCourseDTO with an error message
				return new RegisteredCourseDTO("Only learners can purchase courses");
			}
		}
		return new RegisteredCourseDTO();
	}

	//adding a course to favourite
	@Override
	public FavouriteCourseDTO favouriteCourse(Long registrationId) {
		// finding if the registered course exists
		Optional<RegisteredCourseEntity> regCourse = registeredCourseRepository.findById(registrationId);

		// if it exists, check if the user is a learner
		if (regCourse.isPresent()) {
			RegisteredCourseEntity registeredCourse = regCourse.get();

			// check if the user associated with the registered course is a learner
			if (registeredCourse.getUser().getRole() == UserEntity.Role.LEARNER) {
				FavouriteCourseEntity favouriteCourse = new FavouriteCourseEntity();
				favouriteCourse.setRegisteredCourse(registeredCourse);

				// save the favourite course
				FavouriteCourseEntity favCourse = favouriteCourseRepository.save(favouriteCourse);
				FavouriteCourseDTO favouriteCourseDTO = FavouriteMapper.favCourseEntitytoDTO(favCourse);

				return favouriteCourseDTO;
			} else {
				// If the user is not a learner, return an error or handle accordingly
				// Here, I'm returning an empty FavouriteCourseDTO to indicate an error
				return new FavouriteCourseDTO("Only learners can add courses to favorites");
			}
		}

		// If the registered course doesn't exist, return an empty FavouriteCourseDTO
		return new FavouriteCourseDTO();
	}

	//listing all your favourite courses
	@Override
	public List<FavouriteCourseEntity> seeFavouriteCourses(Long userId) {
		//finding all registered courses
		List<RegisteredCourseEntity> registeredCourses = registeredCourseRepository.findByUserId(userId);
		//List to store favourite courses for a specific user
		List<FavouriteCourseEntity> favouriteCourses = new ArrayList<>();

		// Extract IDs of registered courses
		List<Long> registeredCourseIds = registeredCourses.stream().map(RegisteredCourseEntity::getRegistrationId)
				.collect(Collectors.toList());

		// Find favorite courses for the registered courses
		for (Long id : registeredCourseIds) {
			List<FavouriteCourseEntity> favouriteCoursesForRegistrationId = favouriteCourseRepository
					.findByRegistrationId(id);
			favouriteCourses.addAll(favouriteCoursesForRegistrationId);
		}

		return favouriteCourses;

	}

}
