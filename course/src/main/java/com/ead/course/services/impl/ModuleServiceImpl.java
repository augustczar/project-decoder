package com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepositoy;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;

import jakarta.transaction.Transactional;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	LessonRepositoy lessonRepositoy;
	
	@Transactional
	@Override
	public void delete(ModuleModel moduleModel) {
		List<LessonModel> lessonModelList = lessonRepositoy.findAllLessonsIntoModule(moduleModel.getModuleId());
		if (!lessonModelList.isEmpty()) {
			lessonRepositoy.deleteAll(lessonModelList);
		}
		moduleRepository.delete(moduleModel);
	}

	@Override
	public ModuleModel save(ModuleModel moduleModel) {
		return moduleRepository.save(moduleModel);
	}

	@Override
	public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
		return moduleRepository.moduleModelOptional(courseId, moduleId);
	}

	@Override
	public List<ModuleModel> findAllByCourse(UUID courseId) {
		return moduleRepository.findAllModulesIntoCourse(courseId);
	}

	@Override
	public Optional<ModuleModel> findById(UUID moduleId) {
		return moduleRepository.findById(moduleId);
	}

	@Override
	public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
		return moduleRepository.findAll(spec, pageable);
	}
}
