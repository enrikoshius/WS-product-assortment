package mif.productassortment.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import mif.productassortment.domain.Product;
import mif.productassortment.repository.ProductRepository;
import mif.productassortment.service.ProductService;
import mif.productassortment.validation.ProductValidator;
import mif.productassortment.web.rest.errors.ResourceNotFoundException;
import mif.productassortment.web.rest.errors.ErrConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final ProductValidator productValidator;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository, final ProductValidator productValidator) {
		this.productRepository = productRepository;
		this.productValidator = productValidator;
	}

	@Override
	public Product createProduct(final Product product) {
		productValidator.validateRequest(product);

		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(final Product product, final Long id) {
		productValidator.validateRequest(product);

		final Date existingProductCreationDate = productRepository.findById(id)
				.map(Product::getDateCreated)
				.orElseThrow(() -> new ResourceNotFoundException(ErrConstants.PRODUCT_NOT_FOUND_BY_ID));

		product.setId(id);
		final Product updatedProduct = productRepository.save(product);
		updatedProduct.setDateCreated(existingProductCreationDate);

		return updatedProduct;
	}

	@Override
	public Product patchProduct(final Product product, final Long id) {
		final Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrConstants.PRODUCT_NOT_FOUND_BY_ID));

		productValidator.validatePatchRequest(product);
		product.setId(id);

		if (!StringUtils.isEmpty(product.getName())) {
			existingProduct.setName(product.getName());
		}
		if (!StringUtils.isEmpty(product.getDescription())) {
			existingProduct.setDescription(product.getDescription());
		}
		if(!StringUtils.isEmpty(product.getStatus())) {
			existingProduct.setStatus(product.getStatus());
		}


		final Product updatedProduct = productRepository.save(existingProduct);
		updatedProduct.setDateCreated(existingProduct.getDateCreated());

		return updatedProduct;
	}

	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(final Long id) {
		return productRepository.findById(id);
	}

	@Override
	public void deleteProductById(final Long id) {
		if (productRepository.findById(id).isPresent()) {
			productRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(ErrConstants.PRODUCT_NOT_FOUND_BY_ID);
		}
	}
}
