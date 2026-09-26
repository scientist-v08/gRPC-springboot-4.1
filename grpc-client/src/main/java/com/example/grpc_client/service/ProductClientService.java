package com.example.grpc_client.service;

import grpc_server.Empty;
import grpc_server.ProductList;
import grpc_server.ProductServiceGrpc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductClientService {

    private final ProductServiceGrpc.ProductServiceBlockingStub productStub;

    public ProductList getAllProducts() {
        return productStub.getProducts(Empty.getDefaultInstance());
    }

}
