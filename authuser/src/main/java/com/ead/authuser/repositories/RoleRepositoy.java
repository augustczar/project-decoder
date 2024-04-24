package com.ead.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authuser.models.RoleModel;

public interface RoleRepositoy extends JpaRepository<RoleModel, UUID> {

}
