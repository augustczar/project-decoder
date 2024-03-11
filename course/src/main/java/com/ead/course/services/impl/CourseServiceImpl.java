package com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepositoy;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.CourseService;

import jakarta.transaction.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepositoy lessonRepositoy;
	
	@Autowired
	UserRepository courseUserRepository;
	
	@Transactional
	@Override
	public void delete(CourseModel courseModel) {
		List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
		if (!moduleModelList.isEmpty()) {
			for (ModuleModel moduleModel : moduleModelList) {
				List<LessonModel> lessonModelList = lessonRepositoy.findAllLessonsIntoModule(moduleModel.getModuleId());
				if (!lessonModelList.isEmpty()) {
					lessonRepositoy.deleteAll(lessonModelList);
				}
			}
			moduleRepository.deleteAll(moduleModelList);
		}
		courseRepository.deleteCouseUserByCourse(courseModel.getCourseId());
		courseRepository.delete(courseModel);

	}

	@Override
	public CourseModel save(CourseModel courseModel) {
		return courseRepository.save(courseModel);
		
	}

	@Override
	public Optional<CourseModel> findById(UUID courseId) {
		return courseRepository.findById(courseId);
	}

	@Override
	public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
		return courseRepository.findAll(spec, pageable);
	}

	@Override
	public boolean existesByCouseAndUser(UUID courseId, UUID userId) {
		return courseRepository.existsByCourseAndUser(courseId, userId);
	}

	@Transactional
	@Override
	public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
		courseRepository.saveCourseUser(courseId, userId);
	}
}
