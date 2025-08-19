package com.example.ticket_service.service.impl;

import com.example.ticket_service.dto.request.TicketSearchRequestDTO;
import com.example.ticket_service.dto.response.TicketDTO;
import com.example.ticket_service.dto.request.TicketRequestDTO;
import com.example.ticket_service.entity.Ticket;
import com.example.ticket_service.enums.TicketStatus;
import com.example.ticket_service.mapper.TicketMapper;
import com.example.ticket_service.repository.TicketRepository;
import com.example.ticket_service.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Cacheable(value = "ticketLists")
    public List<TicketDTO> getAllTickets() {
        System.out.println("📦 Query getAllTickets DB...");
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "searchTickets")
    public List<TicketDTO> searchTickets(TicketSearchRequestDTO req) {
        System.out.println("📦 Query searchTickets DB...");
        Specification<Ticket> spec = (root, query, cb) -> cb.conjunction();

        if (req.getId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), req.getId()));
        }
        if (req.getName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("name")), req.getName().toLowerCase()));
        }
        if (req.getDescription() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("description")), req.getDescription().toLowerCase()));
        }
        if (req.getPrice() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("price"), req.getPrice()));
        }
        if (req.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), req.getStatus()));
        }
        if (req.getCreatedAt() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("createdAt"), req.getCreatedAt()));
        }
        if (req.getUpdatedAt() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("updatedAt"), req.getUpdatedAt()));
        }
        if (req.getUserId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), req.getUserId()));
        }

        return ticketRepository.findAll(spec)
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public TicketDTO saveTicket(TicketRequestDTO ticketRequestDTO) {
        Optional<Ticket> ticketByName = ticketRepository.findByName(ticketRequestDTO.getName());

        Ticket ticketRequest = modelMapper.map(ticketRequestDTO, Ticket.class);

        if (ticketByName.isPresent()) {
            Ticket ticket = ticketByName.get();
            ticket.setId(ticketRequest.getId());
            ticket.setName(ticketRequest.getName());
            ticket.setDescription(ticketRequest.getDescription());
            ticket.setPrice(ticketRequest.getPrice());
            ticket.setStatus(ticketRequest.getStatus());
            Ticket saved = ticketRepository.save(ticket);
            return ticketMapper.toDto(saved);
        }

        Ticket saved = ticketRepository.save(ticketRequest);
        return ticketMapper.toDto(saved);
    }


    @Transactional
    public TicketDTO buyTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found "));

        if (ticket.getStatus() == TicketStatus.SOLD && ticket.getUserId() != null) {
            throw new RuntimeException("Ticket already sold");
        }

        ticket.setStatus(TicketStatus.SOLD);
        ticket.setUserId(userId);
        ticketRepository.save(ticket);

        // Gửi event Kafka
        String event = String.format("{\"ticketId\":%d,\"userId\":%d}", ticketId, userId);
        kafkaTemplate.send("ticket.sold", event);

        return ticketMapper.toDto(ticket);
    }

    @CacheEvict(value = { "ticketLists", "searchTickets" }, allEntries = true)
    public String deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket với id " + id + " không tồn tại");
        }

        ticketRepository.deleteById(id);
        return "Xoá ticket thành công";
    }


}
