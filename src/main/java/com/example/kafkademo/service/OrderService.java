package com.example.kafkademo.service;

import com.example.kafkademo.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private static final String TOPIC_NAME = "orders-topic";

    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public OrderDto createAndSendOrder(OrderDto order) {
        if (order.getOrderId() == null) {
            order.setOrderId(UUID.randomUUID());
        }

        log.info("OrderService: Создан новый заказ -> {}", order);

        kafkaTemplate.send(TOPIC_NAME, order.getOrderId().toString(), order)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("OrderService: Заказ успешно отправлен в топик '{}' [partition={}, offset={}]",
                                TOPIC_NAME,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("OrderService: Ошибка при отправке заказа в Kafka", ex);
                    }
                });

        return order;
    }
}
