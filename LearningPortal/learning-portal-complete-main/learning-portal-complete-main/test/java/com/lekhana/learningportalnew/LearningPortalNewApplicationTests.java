package com.lekhana.learningportalnew;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.lekhana.learningportalnew.dto.CourseDTO;
import com.lekhana.learningportalnew.dto.UserDTO;
import com.lekhana.learningportalnew.entity.CourseEntity;
import com.lekhana.learningportalnew.entity.CourseEntity.Category;
import com.lekhana.learningportalnew.entity.FavouriteCourseEntity;
import com.lekhana.learningportalnew.entity.UserEntity;
import com.lekhana.learningportalnew.entity.UserEntity.Role;
import com.lekhana.learningportalnew.mapper.CourseMapper;
import com.lekhana.learningportalnew.mapper.UserMapper;
import com.lekhana.learningportalnew.repository.CourseRepository;
import com.lekhana.learningportalnew.repository.FavouriteCourseRepository;
import com.lekhana.learningportalnew.repository.RegisteredCourseRepository;
import com.lekhana.learningportalnew.repository.UserRepository;
import com.lekhana.learningportalnew.service.CourseService;
import com.lekhana.learningportalnew.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class LearningPortalNewApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CourseMapper courseMapper;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private CourseRepository courseRepository;
	@MockBean
	private FavouriteCourseRepository favouriteCourseRepository;
	@MockBean
	private RegisteredCourseRepository registeredCourseRepository;

	@Test
	void testGetAllUsers() {
		when(userRepository.findAll()).thenReturn(Stream.of(new UserDTO("123", "Sam", Role.ADMIN))
				.map(userMapper::toEntity).collect(Collectors.toList()));

		assertEquals(1, userService.getAllUsers().size());
	}

	@Test
	void testRegister() {
		UserDTO userDTO = new UserDTO("!@#", "manya", Role.ADMIN);
		when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
			UserEntity savedEntity = invocation.getArgument(0);

			return savedEntity;
		});

		assertEquals(userDTO, userService.registerUser(userDTO));
	}

	@Test
	void testDeleteUser() {
		Long deleteId = 1L;

		userService.deleteUser(deleteId);

		verify(userRepository).deleteById(deleteId);
	}

	@Test
	void testSeeFavouriteCourses() {

		List<FavouriteCourseEntity> expectedFavouriteCourses = new ArrayList<>();
		when(favouriteCourseRepository.findByRegistrationId(1L)).thenReturn(expectedFavouriteCourses);

		List<FavouriteCourseEntity> result = userService.seeFavouriteCourses(1L);

		assertEquals(expectedFavouriteCourses, result);
	}

	@Test
	void testGetAllCourses() {

		List<CourseEntity> expectedCourses = new ArrayList<>();
		when(courseRepository.findAll()).thenReturn(expectedCourses);

		List<CourseEntity> result = courseService.getAllCourses();

		assertEquals(expectedCourses, result);
	}

	@Test
	void testAddCourse() {
		CourseDTO courseDTO = new CourseDTO(1L, "learn java", "coding in java", 299L, Category.JAVA);
		when(courseRepository.save(any(CourseEntity.class))).thenAnswer(invocation -> {
			CourseEntity savedEntity = invocation.getArgument(0);

			return savedEntity;
		});

		assertEquals(courseDTO, courseService.addCourse(courseDTO));

	}

	@Test
	void testDeleteCourse() {
		Long deleteId = 1L;

		courseService.deleteCourse(deleteId);

		verify(courseRepository).deleteById(deleteId);
	}
}
