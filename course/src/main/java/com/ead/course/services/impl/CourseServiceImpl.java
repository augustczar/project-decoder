package com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepositoy;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepositoy lessonRepositoy;
	
	@Autowired
	CourseUserRepository courseUserRepository;

	@Autowired
	AuthUserClient authUserClient;
	
	@Transactional
	@Override
	public void delete(CourseModel courseModel) {
		boolean deleteCourseUserInAuthUser = false;
		
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
		List<CourseUserModel> courseUserModels = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
			if(!courseUserModels.isEmpty()) {
				courseUserRepository.deleteAll(courseUserModels);
				deleteCourseUserInAuthUser = true;
		}
		courseRepository.delete(courseModel);
		if (deleteCourseUserInAuthUser) {
			authUserClient.deleteCourseUserInAuthUser(courseModel.getCourseId());
		}
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
}
