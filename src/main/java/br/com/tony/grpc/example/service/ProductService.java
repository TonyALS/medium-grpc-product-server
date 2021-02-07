package br.com.tony.grpc.example.service;

import br.com.tony.grpc.example.dto.ProductDTO;
import br.com.tony.grpc.example.dto.ProductInputDTO;

public interface ProductService {
    ProductDTO createProduct(ProductInputDTO productInputDTO);
    void deleteProduct(Integer productId);
    ProductDTO findById(Integer productId);
    void updateProduct(ProductDTO productDTO);
}
