package br.com.tony.grpc.example.exception;

public class ProductAlreadyExistsException extends ProductException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
