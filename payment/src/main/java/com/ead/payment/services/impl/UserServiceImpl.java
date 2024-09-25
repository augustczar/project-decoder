package com.ead.payment.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.payment.models.UserModel;
import com.ead.payment.repositories.UserRepository;
import com.ead.payment.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}

	@Transactional
	@Override
	public void delete(UUID userUId) {
		userRepository.deleteById(userUId);	
	}

	@Override
	public Optional<UserModel> findById(UUID userUId) {
		return userRepository.findById(userUId);
	}

}
