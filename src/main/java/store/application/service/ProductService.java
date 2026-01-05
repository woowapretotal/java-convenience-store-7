package store.application.service;

import store.application.service.response.ProductResponse;
import store.domain.Product;
import store.domain.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAllProducts();
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
