package com.supportsystem.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supportsystem.application.domains.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
