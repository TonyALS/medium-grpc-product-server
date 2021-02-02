package br.com.tony.grpc.example.resource;

import br.com.tony.grpc.example.GrpcProductServer;
import br.com.tony.grpc.example.GrpcServerServiceGrpc;
import br.com.tony.grpc.example.dto.ProductDTO;
import br.com.tony.grpc.example.exception.ProductAlreadyExistsException;
import br.com.tony.grpc.example.exception.ProductNotFoundException;
import br.com.tony.grpc.example.service.ProductService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductResource extends GrpcServerServiceGrpc.GrpcServerServiceImplBase {

    private final ProductService productService;

    @Inject
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void createProduct(GrpcProductServer.CreateProductInput request, StreamObserver<GrpcProductServer.ProductOutput> responseObserver) {
        try {
            ProductDTO productDTO = this.productService.createProduct(ProductDTO.builder()
                    .name(request.getName())
                    .price(request.getPrice())
                    .build());

            GrpcProductServer.ProductOutput response = GrpcProductServer.ProductOutput.newBuilder()
                    .setId(productDTO.getId().intValue())
                    .setName(productDTO.getName())
                    .setPrice(productDTO.getPrice())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (ProductAlreadyExistsException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException()
            );
        }
    }

    @Override
    public void deleteProduct(GrpcProductServer.ProductId request, StreamObserver<GrpcProductServer.EmptyResponse> responseObserver) {
        try {
            GrpcProductServer.EmptyResponse response = GrpcProductServer.EmptyResponse.newBuilder().build();
            this.productService.deleteProduct(request.getId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ProductNotFoundException e) {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException()
            );
        }
    }

    @Override
    public void findProductById(GrpcProductServer.ProductId request, StreamObserver<GrpcProductServer.ProductOutput> responseObserver) {
        try {
            ProductDTO productDTO = this.productService.findById(request.getId());

            GrpcProductServer.ProductOutput response = GrpcProductServer.ProductOutput.newBuilder()
                    .setId(productDTO.getId().intValue())
                    .setName(productDTO.getName())
                    .setPrice(productDTO.getPrice())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (ProductNotFoundException e) {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException()
            );
        }
    }

    @Override
    public void updateProduct(GrpcProductServer.UpdateProductInput request, StreamObserver<GrpcProductServer.EmptyResponse> responseObserver) {
        try {
            this.productService.updateProduct(ProductDTO.builder()
                    .id((long) request.getId())
                    .name(request.getName())
                    .price(request.getPrice())
                    .build());
            GrpcProductServer.EmptyResponse response = GrpcProductServer.EmptyResponse.newBuilder().build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (ProductAlreadyExistsException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (ProductNotFoundException e) {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException()
            );
        }
    }
}
