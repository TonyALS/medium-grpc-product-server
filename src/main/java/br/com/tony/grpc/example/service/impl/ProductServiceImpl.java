package br.com.tony.grpc.example.service.impl;

import br.com.tony.grpc.example.dto.ProductDTO;
import br.com.tony.grpc.example.dto.ProductInputDTO;
import br.com.tony.grpc.example.entity.Product;
import br.com.tony.grpc.example.exception.ProductAlreadyExistsException;
import br.com.tony.grpc.example.exception.ProductNotFoundException;
import br.com.tony.grpc.example.repository.ProductRepository;
import br.com.tony.grpc.example.service.ProductService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final String PRODUCT_ALREADY_EXISTS = "Product %s already exists";
    private static final String PRODUCT_NOT_FOUND = "Product not found";

    @Inject
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(ProductInputDTO productInputDTO) {
        if (this.productRepository.findByNameIgnoreCase(productInputDTO.getName()).isPresent()) {
            throw new ProductAlreadyExistsException(String.format(
                    PRODUCT_ALREADY_EXISTS, productInputDTO.getName()
            ));
        }
        Product product = Product.builder()
                .name(productInputDTO.getName())
                .price(productInputDTO.getPrice())
                .build();

        return this.productRepository.save(product).fromProductToProductDTO();
    }

    @Override
    public void deleteProduct(Integer productId) {
        if (this.productRepository.findById(productId.longValue()).isEmpty()) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        this.productRepository.deleteById(productId.longValue());
    }

    @Override
    public ProductDTO findById(Integer productId) {
        Product product = this.productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        return product.fromProductToProductDTO();
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = this.productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        if (product.getName().compareToIgnoreCase(productDTO.getName()) == 0) {
            throw new ProductAlreadyExistsException(String.format(
                    PRODUCT_ALREADY_EXISTS, productDTO.getName()
            ));
        }
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        this.productRepository.update(product);
    }
}
