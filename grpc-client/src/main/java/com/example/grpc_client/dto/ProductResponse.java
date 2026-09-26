package com.example.grpc_client.dto;

public record ProductResponse(
        String id,
        String name,
        String description,
        double price
) {
}
