package com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.models.LessonModel;
import com.ead.course.repositories.LessonRepositoy;
import com.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {

	@Autowired
	LessonRepositoy lessonRepositoy;

	@Override
	public LessonModel save(LessonModel lessonModel) {
		return lessonRepositoy.save(lessonModel);
	}

	@Override
	public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
		return lessonRepositoy.findLessonsIntoModule(moduleId, lessonId);
	}

	@Override
	public void delete(LessonModel lessonModel) {
		lessonRepositoy.delete(lessonModel);
	}

	@Override
	public List<LessonModel> findAllByCourse(UUID moduleId) {
		return lessonRepositoy.findAllLessonsIntoModule(moduleId);
	}
}
