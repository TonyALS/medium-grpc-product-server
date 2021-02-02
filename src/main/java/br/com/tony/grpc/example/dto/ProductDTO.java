package br.com.tony.grpc.example.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDTO {
    private final Long id;
    private final String name;
    private final double price;
}
