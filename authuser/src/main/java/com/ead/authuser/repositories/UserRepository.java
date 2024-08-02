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
	
	/*Para esta consulta especificamente ela traz as lista de roles, caso contrario não seria possivel,
	 *  se não estiver usando o EntityGraph devido a @ManyToMany(fetch = FetchType.LAZY) no UserModel, e por
	 *  mais este motivo não utilizaremos o methodo default do spring jpa e sim usar ele com anotação a cima 
	 *  de sua implementação conforme os dois methodos abaixo.
	*/
	@EntityGraph(attributePaths = "roles", type = EntityGraphType.FETCH)
	Optional<UserModel> findByUserName(String username);
	
	@EntityGraph(attributePaths = "roles", type = EntityGraphType.FETCH)
	Optional<UserModel> findById(UUID userId);
}
