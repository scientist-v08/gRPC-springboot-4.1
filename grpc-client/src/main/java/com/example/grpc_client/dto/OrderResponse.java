package com.example.grpc_client.dto;

import java.util.List;

public record OrderResponse(
        List<Orders> orders,
        List<ProductResponse> allProducts
) {
}
