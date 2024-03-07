package com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.models.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

	@Query(value = "SELECT CASE WHEN COUNT(tcu) > 0 THEN TRUE ELSE FALSE END FROM tb_courses_users tcu WHERE"
			+ " tcu.course_Id = :courseId and tcu.user_id = :userId", nativeQuery = true)
	boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);
	
	@Modifying
	@Query(value = "INSERT INTO TB_COURSES_USERS VALUES(:courseId, :userId);", nativeQuery = true)
	void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);
	
	@Modifying
	@Query(value = "DELETE FROM TB_COURSES_USERS WHERE course_id = :courseId", nativeQuery = true)
	void deleteCouseUserByCourse(@Param("courseId") UUID courseId);
	
	@Modifying
	@Query(value = "DELETE FROM TB_COURSES_USERS WHERE user_id = :userId", nativeQuery = true)
	void deleteCourseUserByUser(@Param("userId") UUID userId);
}
