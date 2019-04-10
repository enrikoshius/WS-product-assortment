package mif.productassortment.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import mif.productassortment.domain.Product;

public interface ProductService {

	Product createProduct(Product product);

	Product updateProduct(Product product, Long id);

	Product patchProduct(Product product, Long id);

	List<Product> getProducts(final Map<String, String> allParams);

	Optional<Product> getProductById(Long id);

	void deleteProductById(Long id);
}

