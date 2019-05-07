package mif.productassortment.repository;

import java.util.List;

import mif.productassortment.domain.Product;
import mif.productassortment.domain.enumerator.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByStatus(ProductStatus status);

	List<Product> findByNameContaining(String name);

	List<Product> findByDescriptionContaining(String description);
}
