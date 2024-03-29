package com.ead.authuser.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

	@Autowired
	private CourseClient courseClient;
	
	@Autowired
	UserService userService;
	@GetMapping("/users/{userId}/courses")
	public ResponseEntity<Object> getAllCoursesByUser(
			@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Direction.ASC) Pageable pageable,
			@PathVariable UUID userId){
		
		if(courseClient.getAllCoursesByUser(userId, pageable).getContent().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
		}	
		return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));		
	}
}
