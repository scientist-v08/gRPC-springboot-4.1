package com.example.grpc_client.dto;

import java.math.BigDecimal;

public record Orders(
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
