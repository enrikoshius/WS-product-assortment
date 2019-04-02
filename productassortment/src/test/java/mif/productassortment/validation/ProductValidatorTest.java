package mif.productassortment.validation;

import mif.productassortment.domain.Product;
import mif.productassortment.domain.enumerator.ProductStatus;
import mif.productassortment.web.rest.errors.InvalidRequestException;
import mif.productassortment.web.rest.errors.ErrConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ProductValidatorTest {

	private static final String PRODUCT_TEST_NAME = "TestName";
	private static final String PRODUCT_TEST_DESCRIPTION = "TestDescription";
	private static final Long PRODUCT_ID = 1L;
	private static final String PRODUCT_STATUS_FIELD = "status";

	private final ProductValidator productValidator = new ProductValidator();

	private Product product = new Product();

	@Before
	public void init() {
		product.setName(PRODUCT_TEST_NAME);
		product.setDescription(PRODUCT_TEST_DESCRIPTION);
		product.setStatus(ProductStatus.IN_STOCK);
	}

	@Test
	public void testValidateUpdateRequestStatusNotPresent() {
		product.setStatus(null);
		try {
			productValidator.validateRequest(product);
		} catch (InvalidRequestException e) {
			Assert.assertEquals(ErrConstants.MISSING_FIELDS + PRODUCT_STATUS_FIELD, e.getMessage());
		}
	}

	@Test
	public void testValidatePostRequestSuccess() {
		productValidator.validateRequest(product);
	}

	@Test
	public void testValidatePostRequestIdIsPresent() {
		product.setId(PRODUCT_ID);
		try {
			productValidator.validateRequest(product);
		} catch (InvalidRequestException e) {
			Assert.assertEquals(ErrConstants.ID_CANNOT_BE_SET_MANUALLY, e.getMessage());
		}
	}

}
