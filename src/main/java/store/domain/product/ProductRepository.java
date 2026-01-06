package store.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findByProductName(String productName);

    List<Product> findAllProducts();
}
