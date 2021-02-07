package br.com.tony.grpc.example.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductInputDTO {
    private final String name;
    private final double price;
}
