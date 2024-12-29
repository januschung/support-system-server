package com.supportsystem.application.repositories;

import com.supportsystem.application.domains.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {

	List<TicketComment> findAllByTicketId(Long id);

    Optional<TicketComment> findByTicketIdAndId(Long ticketId, Long commentId);

    boolean existsByTicketId(Long ticketId);
}
