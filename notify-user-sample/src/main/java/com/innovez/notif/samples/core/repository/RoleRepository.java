package com.innovez.notif.samples.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.notif.samples.core.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
