package com.example.kafkademo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DeliveryOrderDto {
    private UUID orderId;
    private String itemName;
    private Integer quantity;
    private BigDecimal price;

    // Поля, которыми обогащаем заказ по ТЗ:
    private String deliveryStatus;
    private LocalDateTime estimatedDeliveryTime;
}
