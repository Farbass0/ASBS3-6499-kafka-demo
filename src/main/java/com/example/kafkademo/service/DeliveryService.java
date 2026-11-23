package com.example.kafkademo.service;

import com.example.kafkademo.dto.DeliveryOrderDto;
import com.example.kafkademo.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class DeliveryService {
    @KafkaListener(topics = "orders-topic", groupId = "delivery-service-group")
    public void consumeOrder(OrderDto order) {
        log.info("DeliveryService: Получен заказ из Kafka -> {}", order);

        // Обогащаем данные по ТЗ: добавляем статус и ориентировочное время доставки
        DeliveryOrderDto enrichedOrder = DeliveryOrderDto.builder()
                .orderId(order.getOrderId())
                .itemName(order.getItemName())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .deliveryStatus("ОЖИДАЕТ КУРЬЕРА")
                .estimatedDeliveryTime(LocalDateTime.now().plusHours(2))
                .build();

        // Записываем обогащенный заказ в лог по критериям приемки
        log.info("DeliveryService: Заказ обогащен и готов к доставке -> {}", enrichedOrder);
    }
}
