package br.com.tony.grpc.example.exception;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
