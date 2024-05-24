package com.ead.authuser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ead.authuser.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
	
	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);
	
	@EntityGraph(attributePaths = "roles", type = EntityGraphType.FETCH)
	Optional<UserModel> findByUserName(String username);
}
