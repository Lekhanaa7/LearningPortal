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
import com.learningportal.sam.repository.favoriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class favoriteService {

    private favoriteRepository favoriteRepository;
    private userRepository userRepository;
    private courseRepository courseRepository;

    public List<String> getFavoriteCoursesByUserId(long userId) {
        Optional<user> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            user user = userOptional.get();
            List<course> favoriteCourses = favoriteRepository.findFavoriteCoursesByUser(user);

            if (favoriteCourses.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> favoriteCoursesResponseList = new ArrayList<>();
            favoriteCourses.forEach(course -> {
                String courseResponse = "Favorite Course Title: " + course.getTitle();
                favoriteCoursesResponseList.add(courseResponse);
            });

            return favoriteCoursesResponseList;
        }

        return Collections.emptyList();
    }

    public String addCourseToFavorites(long userId, long courseId) {
        Optional<user> userOptional = userRepository.findById(userId);
        Optional<course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            user user = userOptional.get();
            course course = courseOptional.get();

            user.getFavoriteCourses().add(course);
            userRepository.save(user);

            return "Course added to favorites: " + course.getTitle();
        }

        return "Adding to favorites failed. User or Course not found.";
    }

    public boolean removeCourseFromFavorites(long userId, long courseId) {
        Optional<user> userOptional = userRepository.findById(userId);
        Optional<course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            user user = userOptional.get();
            course course = courseOptional.get();

            user.getFavoriteCourses().remove(course);
            userRepository.save(user);

            return true;
        }

        return false;
    }
}
