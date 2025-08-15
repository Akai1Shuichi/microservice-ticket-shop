package com.example.ticket_service.controller;

import com.example.ticket_service.dto.request.TicketSearchRequestDTO;
import com.example.ticket_service.dto.request.UserPrincipal;
import com.example.ticket_service.dto.response.TicketDTO;
import com.example.ticket_service.dto.request.TicketRequestDTO;
import org.springframework.security.core.Authentication;
import com.example.ticket_service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAll() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping("/search")
    public ResponseEntity<List<TicketDTO>> searchTickets(@RequestBody TicketSearchRequestDTO request) {
        return ResponseEntity.ok(ticketService.searchTickets(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<TicketDTO> saveTicket(@RequestBody TicketRequestDTO ticket) {
        return ResponseEntity.ok(ticketService.saveTicket(ticket));
    }

    @PostMapping("/{ticketId}/buy")
    public ResponseEntity<TicketDTO> buyTicket(@PathVariable Long ticketId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Long userId = principal.getId();
        TicketDTO ticketDTO = ticketService.buyTicket(ticketId, userId);
        return ResponseEntity.ok(ticketDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTicket(@PathVariable Long id) {
        String message = ticketService.deleteTicket(id);
        return ResponseEntity.ok(Map.of("message", message));
    }

}
