package com.ead.authuser.service;

import java.util.UUID;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;

public interface UserCourseService {

	boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

	UserCourseModel save(UserCourseModel convertToUserCourseModel);

}
