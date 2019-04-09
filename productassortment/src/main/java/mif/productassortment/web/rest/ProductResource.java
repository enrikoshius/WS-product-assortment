package mif.productassortment.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import java.util.Optional;

import com.sun.javafx.collections.MappingChange;
import mif.productassortment.domain.Product;
import mif.productassortment.service.ProductService;
import mif.productassortment.web.rest.errors.ResourceNotFoundException;
import mif.productassortment.web.rest.errors.ErrConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResource {

	private final ProductService productService;

	@Autowired
	public ProductResource(final ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getProducts(@RequestParam Map<String,String> allParams) {
		final List<Product> result = productService.getProducts(allParams);
		return ResponseEntity.ok(result);
	}



	@PostMapping(value = "/products", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addProduct(@RequestBody Product product) throws URISyntaxException {
		final Product result = productService.createProduct(product);

		return ResponseEntity.created(new URI("/products/" + result.getId()))
				.body(result);
	}

	@PutMapping(value = "/products/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		final Product result = productService.updateProduct(product, id);

		return ResponseEntity.ok()
				.body(result);
	}

	@DeleteMapping(value = "/products/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProductById(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/products/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		final Optional<Product> result = productService.getProductById(id);

		return result
				.map((response) -> ResponseEntity.ok().body(response))
				.orElseThrow(() -> new ResourceNotFoundException(ErrConstants.PRODUCT_NOT_FOUND_BY_ID));
	}

	@PatchMapping(value = "/products/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> patchProduct(@RequestBody Product product, @PathVariable Long id) {
		final Product result = productService.patchProduct(product, id);

		return ResponseEntity.ok()
				.body(result);
	}

}
