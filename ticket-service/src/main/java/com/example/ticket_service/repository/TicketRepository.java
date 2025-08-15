package com.example.ticket_service.repository;

import com.example.ticket_service.entity.Ticket;
import com.example.ticket_service.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    Optional<Ticket> findByName(String name);
    Optional<Ticket> findByStatus(TicketStatus status);
}
