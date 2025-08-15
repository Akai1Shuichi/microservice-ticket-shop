package com.example.ticket_service.mapper;

import com.example.ticket_service.dto.response.TicketDTO;
import com.example.ticket_service.entity.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TicketDTO toDto(Ticket ticket) {
        return modelMapper.map(ticket, TicketDTO.class);
    }

    public Ticket toEntity(TicketDTO ticketDTO) {
        return modelMapper.map(ticketDTO, Ticket.class);
    }
}
