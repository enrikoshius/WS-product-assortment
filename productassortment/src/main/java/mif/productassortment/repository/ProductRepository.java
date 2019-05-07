package mif.productassortment.repository;

import java.util.List;

import mif.productassortment.domain.Product;
import mif.productassortment.domain.enumerator.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT u.status FROM Product u WHERE u.status LIKE CONCAT('%',:status,'%')")
	List<Product> findByStatus(@Param("status")ProductStatus status);
	@Query("SELECT  u.name FROM Product u WHERE u.name LIKE CONCAT('%',:name,'%')")
	List<Product> findByName(@Param("name")String name);
	@Query("SELECT u.description FROM Product u WHERE u.description LIKE CONCAT('%',:description,'%')")
	List<Product> findByDescription(@Param("description")String description);
}
