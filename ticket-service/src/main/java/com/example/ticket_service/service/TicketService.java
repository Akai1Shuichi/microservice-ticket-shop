package com.example.ticket_service.service;

import com.example.ticket_service.dto.request.TicketRequestDTO;
import com.example.ticket_service.dto.request.TicketSearchRequestDTO;
import com.example.ticket_service.dto.response.TicketDTO;

import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllTickets();
    List<TicketDTO> searchTickets(TicketSearchRequestDTO req);
    TicketDTO saveTicket(TicketRequestDTO ticketRequestDTO);
    TicketDTO buyTicket(Long ticketId, Long userId);
    String deleteTicket(Long id);
}
