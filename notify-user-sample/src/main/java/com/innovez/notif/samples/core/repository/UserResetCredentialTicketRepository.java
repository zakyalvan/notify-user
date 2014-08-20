package com.innovez.notif.samples.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovez.notif.samples.core.entity.UserResetCredentialTicket;

public interface UserResetCredentialTicketRepository extends JpaRepository<UserResetCredentialTicket, String> {

}
