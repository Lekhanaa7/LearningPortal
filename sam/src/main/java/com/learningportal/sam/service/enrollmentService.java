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
import com.learningportal.sam.repository.enrollmentRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class enrollmentService {

    private enrollmentRepository enrollmentRepository;
    private userRepository userRepository;
    private courseRepository courseRepository;

    public List<String> getEnrolledCoursesByUserId(long userId) {
        Optional<user> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            user user = userOptional.get();
            List<course> enrolledCourses = enrollmentRepository.findEnrolledCoursesByUser(user);

            if (enrolledCourses.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> enrolledCoursesResponseList = new ArrayList<>();
            enrolledCourses.forEach(course -> {
                String courseResponse = "Enrolled Course Title: " + course.getTitle();
                enrolledCoursesResponseList.add(courseResponse);
            });

            return enrolledCoursesResponseList;
        }

        return Collections.emptyList();
    }

    public String enrollUserToCourse(long userId, long courseId) {
        Optional<user> userOptional = userRepository.findById(userId);
        Optional<course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            user user = userOptional.get();
            course course = courseOptional.get();

            user.getEnrolledCourses().add(course);
            userRepository.save(user);

            return "User enrolled in the course: " + course.getTitle();
        }

        return "Enrollment failed. User or Course not found.";
    }

    public boolean withdrawUserFromCourse(long userId, long courseId) {
        Optional<user> userOptional = userRepository.findById(userId);
        Optional<course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            user user = userOptional.get();
            course course = courseOptional.get();

            user.getEnrolledCourses().remove(course);
            userRepository.save(user);

            return true;
        }

        return false;
    }
}
