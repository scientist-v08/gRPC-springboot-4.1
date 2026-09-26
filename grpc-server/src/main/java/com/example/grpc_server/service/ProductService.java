package com.example.grpc_server.service;

import com.example.grpc_server.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
}
