package com.lekhana.learningportalnew.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lekhana.learningportalnew.dto.CourseDTO;
import com.lekhana.learningportalnew.entity.CourseEntity;
import com.lekhana.learningportalnew.entity.UserEntity;
import com.lekhana.learningportalnew.entity.UserEntity.Role;
import com.lekhana.learningportalnew.service.CourseService;
import com.lekhana.learningportalnew.service.UserService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	private final UserService userService;
	private static final Logger log = LoggerFactory.getLogger(CourseController.class);

	//GET COURSES
	@GetMapping("/getall")
	public List<CourseEntity> getAllCourses() {
		log.info("listing all courses");
		return courseService.getAllCourses();
	}

	//ADD COURSES
	@PostMapping("/add/{id}")
	public CourseDTO addCourse(@RequestBody CourseDTO course, @PathVariable Long id) {
		//finding the author from the passed user_id
		Optional<UserEntity> author = userService.getUser(id);

		//if user is present and the role is author
		if (author.isPresent() && (author.get().getRole() == Role.AUTHOR)) {
			log.info("course added");
			//add course
			return courseService.addCourse(course);
		}
		//return empty course
		return new CourseDTO();
	}

	//DELETE COURSES
	@DeleteMapping("/delete/{user_Id}")
	public void deleteCourse(@PathVariable Long user_Id) {
		//finding if the user is present 
		Optional<UserEntity> isAuthor = userService.getUser(user_Id);

		//if the user is present and the role is author
		if (isAuthor.isPresent() && (isAuthor.get().getRole() == Role.AUTHOR)) {
			//delete the course
			courseService.deleteCourse(user_Id);
			log.info("course deleted");
		}
	}

	//UPDATE COURSES
	@PutMapping("/update/{user_Id}")
	public CourseDTO updateCourse(@RequestBody CourseDTO course, @PathVariable Long user_Id) {
		//finding if the user is present
		Optional<UserEntity> isAuthor = userService.getUser(user_Id);

		//if user is present and the role is author
		if (isAuthor.isPresent() && (isAuthor.get().getRole() == Role.AUTHOR)) {
			log.info("course updated");
			return courseService.updateCourse(course);
		}
		return new CourseDTO();
	}
}