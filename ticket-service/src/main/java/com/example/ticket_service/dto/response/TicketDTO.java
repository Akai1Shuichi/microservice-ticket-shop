package com.example.ticket_service.dto.response;

import com.example.ticket_service.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDTO {
    private Long id;
    private String name;
    private TicketStatus status;
    private Long userId;
}
