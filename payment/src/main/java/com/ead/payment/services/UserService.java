package com.ead.payment.services;

import java.util.Optional;
import java.util.UUID;

import com.ead.payment.models.UserModel;

public interface UserService {

	UserModel save(UserModel userModel);
	
	void delete(UUID userUId);
	
	Optional<UserModel> findById(UUID userUId);
}
