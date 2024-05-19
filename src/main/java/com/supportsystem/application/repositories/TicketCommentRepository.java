package com.supportsystem.application.repositories;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.domains.TicketComment;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {

	List<TicketComment> findAllByTicketId(Long id);
}
