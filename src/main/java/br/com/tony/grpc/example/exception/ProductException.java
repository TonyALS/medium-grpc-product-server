package br.com.tony.grpc.example.exception;

public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }
}
