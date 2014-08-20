package com.innovez.notif.samples.core.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innovez.notif.samples.core.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	@Query("SELECT COUNT (user.username)>0 FROM User user WHERE UPPER(TRIM(BOTH FROM user.emailAddress)) = UPPER(TRIM(BOTH FROM :emailAddress))")
	boolean existsByEmailAddressIgnoreCase(@Param("emailAddress") String emailAddress);
	
	@Query("SELECT COUNT (user.username)>0 FROM User user WHERE UPPER(TRIM(BOTH FROM user.emailAddress)) = UPPER(TRIM(BOTH FROM :emailAddress)) AND user.username NOT IN :excludeUsernames")
	boolean existsByEmailAddressIgnoreCaseExcludeUsernames(@Param("emailAddress") String emailAddress, @Param("excludeUsernames") Collection<String> excludeUsernames);
}
