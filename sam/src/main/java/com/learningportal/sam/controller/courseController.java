package com.learningportal.sam.controller;
import com.learningportal.sam.service.courseService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class courseController {

    private final courseService courseService = new courseService();

    @GetMapping
    public List<String> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping
    public String createCourse(@RequestParam String title,
                               @RequestParam String description,
                               @RequestParam String category,
                               @RequestParam int durationHours) throws Exception {
        return courseService.createCourse(title, description, category, durationHours);
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable long id,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false) int durationHours) {
        return courseService.updateCourse(id, title, description, category, durationHours);
    }
}