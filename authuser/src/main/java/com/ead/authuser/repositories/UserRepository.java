package com.ead.authuser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ead.authuser.models.UserModel;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import java.util.List;


public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
	
	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);
	
	/*Para esta consulta especificamente ela traz as lista de roles, caso contrario não seria possivel,
	 *  se não estiver usando o EntityGraph devido a @ManyToMany(fetch = FetchType.LAZY) no UserModel, e por
	 *  mais este motivo não utilizaremos o methodo deault do spring jpa e sim usar ele com anotação acima 
	 *  de suaimplementação comforme os dois methodos abaixo.
	*/
	@EntityGraph(attributePaths = "roles", type = EntityGraphType.FETCH)
	Optional<UserModel> findByUserName(String username);
	
	@EntityGraph(attributePaths = "roles", type = EntityGraphType.FETCH)
	Optional<UserModel> findById(UUID userId);
}
