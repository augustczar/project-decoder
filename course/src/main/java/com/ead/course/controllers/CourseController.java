package com.ead.course.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	CourseService courseService;
	
	@PostMapping
	public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseDto courseDto){
		log.debug("POST saveCourse courseDto received {} ", courseDto.toString());
		var courseModel = new CourseModel();
		BeanUtils.copyProperties(courseDto, courseModel);
		courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));	
		courseService.save(courseModel);
        log.debug("POST saveCourse courseId saved {} ", courseModel.getCourseId());
        log.info("Course saved successfully courseId {} ", courseModel.getCourseId());
		return ResponseEntity.status(HttpStatus.CREATED).body(courseModel);
	}
	
	@DeleteMapping("/{courseId}")
	public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId){
		log.debug("DELETE deleteCourse courseId received {} ", courseId);
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
 		if (!courseModelOptional.isPresent()) {
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
		}
 		courseService.delete(courseModelOptional.get());
 		log.debug("DELETE deleteCourse courseId deleted {} ", courseId);
        log.info("Course deleted successfully courseId {} ", courseId);
 		return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully!");	
	}
	
	@PutMapping("/{courseId}")
	public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
			@RequestBody @Valid CourseDto courseDto){
		log.debug("PUT updateCourse courseDto received {} ", courseDto.toString());
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
 		if (!courseModelOptional.isPresent()) {
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
		}
 		var courseModel = courseModelOptional.get();
 		courseModel.setName(courseDto.getName());
 		courseModel.setDescription(courseDto.getDescription());
 		courseModel.setImageUrl(courseDto.getImageUrl());
 		courseModel.setCourseStatus(courseDto.getCourseStatus());
 		courseModel.setCourseLevel(courseDto.getCourseLevel());
 		courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
 		courseService.save(courseModel);
 		log.debug("PUT updateCourse courseId saved {} ", courseModel.getCourseId());
        log.info("Course updated successfully courseId {} ", courseModel.getCourseId());
		return ResponseEntity.status(HttpStatus.OK).body(courseModel);
	}
	
	@GetMapping
	public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec,
			@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC)
			Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
	}
	
	@GetMapping("/{courseId}")
	public ResponseEntity<Object> getOneCourses(@PathVariable(value = "courseId") UUID courseId){
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
 		if (!courseModelOptional.isPresent()) {
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
	}
}
