package com.example.user_service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TicketEventConsumer {

    @KafkaListener(topics = "ticket.sold", groupId = "user-service-group")
    public void consumeTicketSold(String message) {
        log.info("Nhận sự kiện ticket.sold: {}", message);
        // TODO: parse JSON -> update lịch sử mua vé user, gửi email...
    }
}

