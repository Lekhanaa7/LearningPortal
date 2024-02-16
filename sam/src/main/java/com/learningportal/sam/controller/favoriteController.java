package com.learningportal.sam.controller;
import com.learningportal.sam.service.favoriteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/favorites")
public class favoriteController {

    private final favoriteService favoriteService = new favoriteService();

    @GetMapping("/{userId}")
    public List<String> getFavoriteCoursesByUserId(@PathVariable long userId) {
        return favoriteService.getFavoriteCoursesByUserId(userId);
    }

    @PostMapping
    public String addCourseToFavorites(@RequestParam long userId, @RequestParam long courseId) {
        return favoriteService.addCourseToFavorites(userId, courseId);
    }

    @DeleteMapping
    public boolean removeCourseFromFavorites(@RequestParam long userId, @RequestParam long courseId) {
        return favoriteService.removeCourseFromFavorites(userId, courseId);
    }
}