package com.example.grpc_client.controller;

import com.example.grpc_client.dto.OrderResponse;
import com.example.grpc_client.dto.Orders;
import com.example.grpc_client.dto.ProductResponse;
import com.example.grpc_client.entity.Order;
import com.example.grpc_client.service.OrderService;
import com.example.grpc_client.service.ProductClientService;
import grpc_server.ProductList;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductClientService productClientService;

    @GetMapping
    public OrderResponse getOrders() {

        CompletableFuture<List<Order>> ordersFuture =
                CompletableFuture.supplyAsync(orderService::getAllOrders);

        CompletableFuture<ProductList> productsFuture =
                CompletableFuture.supplyAsync(productClientService::getAllProducts);

        CompletableFuture.allOf(ordersFuture, productsFuture).join();

        List<Orders> orders = ordersFuture.join()
                .stream()
                .map(order -> new Orders(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getSkuCode(),
                        order.getPrice(),
                        order.getQuantity()
                ))
                .toList();

        List<ProductResponse> products = productsFuture.join()
                .getProductsList()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .toList();

        return new OrderResponse(
                orders,
                products
        );
    }

}
