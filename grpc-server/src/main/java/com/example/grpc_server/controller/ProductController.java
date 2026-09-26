package com.example.grpc_server.controller;

import com.example.grpc_server.dto.ProductRequest;
import com.example.grpc_server.model.Product;
import com.example.grpc_server.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a single product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.description(),
                request.price()
        );
        return productRepository.save(product);
    }

    // Create multiple products at once
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Product> createProducts(@RequestBody Flux<ProductRequest> requests) {
        return requests
                .map(req -> new Product(req.name(), req.description(), req.price()))
                .flatMap(productRepository::save);
    }

    // Optional: list all products (useful for testing)
    @GetMapping
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
