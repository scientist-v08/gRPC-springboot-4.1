package com.example.grpc_server.service.impl;

import com.example.grpc_server.repository.ProductRepository;
import grpc_server.Empty;
import grpc_server.Product;
import grpc_server.ProductList;
import grpc_server.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class ProductServiceGrpcImpl extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository productRepository;

    public ProductServiceGrpcImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void getProducts(
            Empty request,
            StreamObserver<ProductList> responseObserver
    ) {
        productRepository.findAll()
                .map(p -> grpc_server.Product.newBuilder()
                        .setId(p.getId().toHexString())
                        .setName(p.getName())
                        .setDescription(p.getDescription())
                        .setPrice(p.getPrice().doubleValue())
                        .build())
                .collectList()
                .map(list -> ProductList.newBuilder().addAllProducts(list).build())
                .subscribe(
                        responseObserver::onNext,
                        responseObserver::onError,
                        responseObserver::onCompleted
                );
    }
}
