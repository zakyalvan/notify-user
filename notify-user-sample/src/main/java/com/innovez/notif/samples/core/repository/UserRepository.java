package com.innovez.notif.samples.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.notif.samples.core.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
}
