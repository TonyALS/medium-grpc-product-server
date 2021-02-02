package br.com.tony.grpc.example.repository;

import br.com.tony.grpc.example.entity.Product;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameIgnoreCase(String productName);
}
