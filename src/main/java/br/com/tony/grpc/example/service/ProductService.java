package br.com.tony.grpc.example.service;

import br.com.tony.grpc.example.dto.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    void deleteProduct(Integer productId);
    ProductDTO findById(Integer productId);
    void updateProduct(ProductDTO productDTO);
}
