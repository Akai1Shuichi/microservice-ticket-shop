package com.example.ticket_service.dto.request;

import com.example.ticket_service.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSearchRequestDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private TicketStatus status;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
}
