package com.zidio.controller;

import com.zidio.payload.CourseDto;
import com.zidio.payload.StudentCourseSubscriptionDto;
import com.zidio.service.CourseService;
import com.zidio.service.StudentCourseSubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired 
    private CourseService courseService;

    @Autowired
    private StudentCourseSubscriptionService subscriptionService;

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/subscribe/{courseId}")
    public ResponseEntity<String> subscribe(@PathVariable Long courseId, Authentication auth) {
        return ResponseEntity.ok(subscriptionService.subscribeToCourse(courseId, auth.getName()));
    }

    @GetMapping("/my-subscriptions")
    public ResponseEntity<List<StudentCourseSubscriptionDto>> getMySubs(Authentication auth) {
        return ResponseEntity.ok(subscriptionService.getMySubscriptions(auth.getName()));
    }
}
