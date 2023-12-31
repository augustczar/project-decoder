package com.ead.authuser.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID>  {

	boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

	@Query(value = "SELECT * FROM tb_users_courses WHERE user_user_id = :userId", nativeQuery = true)
	List<UserCourseModel> findAllUseCourseIntoUser(@Param("userId") UUID userId);

}
