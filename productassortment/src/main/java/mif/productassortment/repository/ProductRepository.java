package mif.productassortment.repository;

import java.util.List;

import mif.productassortment.domain.Product;
import mif.productassortment.domain.enumerator.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT u.id, u.date_created, u.date_updated,  u.name, u.description, u.status FROM product u WHERE u.status LIKE CONCAT('%',:status,'%')")
	List<Product> findByStatus(ProductStatus status);
	@Query("SELECT u.id, u.date_created, u.date_updated,  u.name, u.description, u.status FROM product u WHERE u.name LIKE CONCAT('%',:name,'%')")
	List<Product> findByName(String name);
	@Query("SELECT u.id, u.date_created, u.date_updated,  u.name, u.description, u.status FROM product u WHERE u.description LIKE CONCAT('%',:description,'%')")
	List<Product> findByDescription(String description);
}