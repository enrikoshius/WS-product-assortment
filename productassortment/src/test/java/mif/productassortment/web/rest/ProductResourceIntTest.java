package mif.productassortment.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import javax.persistence.EntityManager;

import mif.productassortment.ProductAssortmentApplication;
import mif.productassortment.domain.Product;
import mif.productassortment.domain.enumerator.ProductStatus;
import mif.productassortment.repository.ProductRepository;
import mif.productassortment.service.ProductService;
import mif.productassortment.util.TestUtil;
import mif.productassortment.web.rest.errors.CustExceptionAdvice;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductAssortmentApplication.class)
public class ProductResourceIntTest {

	private static final String PRODUCT_NAME = "TestName";
	private static final String UPDATED_PRODUCT_NAME = "UpdatedTestName";
	private static final String PRODUCT_DESCRIPTION = "TestDescription";
	private static final String UPDATED_PRODUCT_DESCRIPTION = "UpdatedTestDescription";

	private MockMvc restProductMockMvc;

	private Product product;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustExceptionAdvice customExceptionAdvice;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private EntityManager em;

	@Before
	public void setup() {
		final ProductResource productResource = new ProductResource(productService);
		this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
				.setControllerAdvice(customExceptionAdvice)
				.build();
	}

	@Before
	public void init() {
		product = new Product();
		product.setStatus(ProductStatus.IN_STOCK);
		product.setName(PRODUCT_NAME);
		product.setDescription(PRODUCT_DESCRIPTION);
	}

	@Test
	@Transactional
	public void createProduct() throws Exception {
		final int databaseSizeBeforeCreate = productRepository.findAll().size();

		restProductMockMvc.perform(post("/products")
				.contentType(APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(product)))
				.andExpect(status().isCreated());

		final List<Product> productList = productRepository.findAll();

		assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
		final Product testProduct = productList.get(productList.size() - 1);
		assertThat(testProduct.getName()).isEqualTo(PRODUCT_NAME);
		assertThat(testProduct.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
		Assertions.assertThat(testProduct.getStatus()).isEqualTo(ProductStatus.IN_STOCK);
	}

	@Test
	@Transactional
	public void createProductWithExistingId() throws Exception {
		final int databaseSizeBeforeCreate = productRepository.findAll().size();

		product.setId(1L);

		restProductMockMvc.perform(post("/products")
				.contentType(APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(product)))
				.andExpect(status().isBadRequest());

		final List<Product> cartOrderList = productRepository.findAll();
		assertThat(cartOrderList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllProducts() throws Exception {
		productRepository.saveAndFlush(product);

		restProductMockMvc.perform(get("/products?sort=id").contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[-1:].id").value(hasItem(product.getId().intValue())))
				.andExpect(jsonPath("$.[-1:].name").value(hasItem(product.getName())))
				.andExpect(jsonPath("$.[-1:].description").value(hasItem(product.getDescription())))
				.andExpect(jsonPath("$.[-1:].status").value(product.getStatus().toString()));
	}

	@Test
	@Transactional
	public void getProduct() throws Exception {
		productRepository.saveAndFlush(product);

		restProductMockMvc.perform(get("/products/{id}", product.getId()).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(product.getId().intValue()))
				.andExpect(jsonPath("$.name").value(product.getName()))
				.andExpect(jsonPath("$.description").value(product.getDescription()))
				.andExpect(jsonPath("$.status").value(product.getStatus().toString()));
	}

	@Test
	@Transactional
	public void updateProduct() throws Exception {
		productRepository.saveAndFlush(product);

		final int databaseSizeBeforeUpdate = productRepository.findAll().size();
		final Long productId = product.getId();
		final Product updatedProduct = productRepository.findById(productId).get();

		em.detach(updatedProduct);
		updatedProduct.setName(UPDATED_PRODUCT_NAME);
		updatedProduct.setDescription(UPDATED_PRODUCT_DESCRIPTION);
		product.setId(null);
		product.setDateCreated(null);

		restProductMockMvc.perform(put("/products/{id}/", productId)
				.contentType(APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(product)))
				.andExpect(status().isOk());

		final List<Product> productList = productRepository.findAll();
		assertThat(productList).hasSize(databaseSizeBeforeUpdate);
		final Product testProduct = productList.get(productList.size() - 1);
		assertThat(testProduct.getName()).isEqualTo(UPDATED_PRODUCT_NAME);
		assertThat(testProduct.getDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
	}

	@Test
	@Transactional
	public void deleteProduct() throws Exception {
		productRepository.saveAndFlush(product);

		final int databaseSizeBeforeDelete = productRepository.findAll().size();

		restProductMockMvc.perform(delete("/products/{id}", product.getId()).contentType(APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());

		final List<Product> cartOrderList = productRepository.findAll();
		assertThat(cartOrderList).hasSize(databaseSizeBeforeDelete - 1);
	}

}
