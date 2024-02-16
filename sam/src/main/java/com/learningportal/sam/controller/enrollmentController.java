package com.learningportal.sam.controller;
import com.learningportal.sam.service.enrollmentService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/enrollments")
public class enrollmentController {
	
	private final enrollmentService enrollmentService = new enrollmentService();

    @GetMapping("/{userId}")
    public List<String> getEnrolledCoursesByUserId(@PathVariable long userId) {
        return enrollmentService.getEnrolledCoursesByUserId(userId);
    }

    @PostMapping
    public String enrollUserToCourse(@RequestParam long userId, @RequestParam long courseId) {
        return enrollmentService.enrollUserToCourse(userId, courseId);
    }

    @DeleteMapping
    public boolean withdrawUserFromCourse(@RequestParam long userId, @RequestParam long courseId) {
        return enrollmentService.withdrawUserFromCourse(userId, courseId);
    }
}
