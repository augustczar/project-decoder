package com.ead.authuser.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserCourseRepository userCourseRepository;
	
	@Autowired
	CourseClient courseClient;
	
	@Override
	public List<UserModel> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserModel> findById(UUID userId) {
		return userRepository.findById(userId);
	}

	@Transactional
	@Override
	public void delete(UserModel userModel) {
		boolean deleteUserCourseInCourse = false;
		List<UserCourseModel> userCourseModels = userCourseRepository.findAllUseCourseIntoUser(userModel.getUserId());
		if(!userCourseModels.isEmpty()) {
			userCourseRepository.deleteAll(userCourseModels);
			deleteUserCourseInCourse = true;
	}
		userRepository.delete(userModel);
		if (deleteUserCourseInCourse) {
			courseClient.deleteUserCourseInCourse(userModel.getUserId());
		}
	}

	@Override
	public void save(UserModel userModel) {
		userRepository.save(userModel);
	}

	@Override
	public boolean existsByUserName(String userName) {
		return userRepository.existsByUserName(userName);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
		return userRepository.findAll(spec, pageable);
	}
}
