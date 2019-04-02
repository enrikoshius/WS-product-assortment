package mif.productassortment.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mif.productassortment.domain.Product;
import mif.productassortment.domain.enumerator.ProductStatus;
import mif.productassortment.web.rest.errors.InvalidRequestException;
import mif.productassortment.web.rest.errors.ErrConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProductValidator {

	private static final String NAME_FIELD = "name";
	private static final String DESCRIPTION_FIELD = "description";
	private static final String STATUS_FIELD = "status";
	private static final String COMMA_DELIMITER = ", ";

	private List<ProductStatus> productStatusList = Arrays.asList(ProductStatus.IN_STOCK, ProductStatus.OUT_OF_STOCK, ProductStatus.SUSPENDED);

	public void validatePatchRequest(final Product product) {
		if (product.getId() != null) {
			throw new InvalidRequestException(ErrConstants.ID_CANNOT_BE_SET_MANUALLY);
		}
		if (!StringUtils.isEmpty(product.getDateCreated())) {
			throw new InvalidRequestException(ErrConstants.CREATION_DATE_CANNOT_BE_SET_MANUALLY);
		}
		if (!StringUtils.isEmpty(product.getDateUpdated())) {
			throw new InvalidRequestException(ErrConstants.MODIFICATION_DATE_CANNOT_BE_SET_MANUALLY);
		}
		if (StringUtils.isEmpty(product.getName()) && StringUtils.isEmpty(product.getStatus()) && StringUtils.isEmpty(product.getDescription())) {
			throw new InvalidRequestException(ErrConstants.INVALID_PUT_REQUEST);
		}
	}

	private void validateFaultyFields(final Product product) {
		productStatusList.stream()
				.filter(status -> status.equals(product.getStatus()))
				.findAny()
				.orElseThrow(() -> new InvalidRequestException(ErrConstants.INVALID_STATUS_NAME));
	}

	private String collectFaultyFieldNames(final Product product) {
		final List<String> fields = new ArrayList<>();

		if (StringUtils.isEmpty(product.getName())) {
			fields.add(NAME_FIELD);
		}
		if (StringUtils.isEmpty(product.getDescription())) {
			fields.add(DESCRIPTION_FIELD);
		}
		if (StringUtils.isEmpty(product.getStatus())) {
			fields.add(STATUS_FIELD);
		}

		return String.join(COMMA_DELIMITER, fields);
	}

	private void validateEmptyFields(final Product product) {
		if (!StringUtils.isEmpty(product.getDateCreated())) {
			throw new InvalidRequestException(ErrConstants.CREATION_DATE_CANNOT_BE_SET_MANUALLY);
		}
		if (!StringUtils.isEmpty(product.getDateUpdated())) {
			throw new InvalidRequestException(ErrConstants.MODIFICATION_DATE_CANNOT_BE_SET_MANUALLY);
		}

		final String invalidFieldNames = collectFaultyFieldNames(product);

		if (!StringUtils.isEmpty(invalidFieldNames)) {
			throw new InvalidRequestException(ErrConstants.MISSING_FIELDS + invalidFieldNames);
		}
	}

	private void validateProductBody(final Product product) {
		validateEmptyFields(product);
		validateFaultyFields(product);
	}

	public void validateRequest(final Product product) {
		if (product.getId() != null) {
			throw new InvalidRequestException(ErrConstants.ID_CANNOT_BE_SET_MANUALLY);
		}

		validateProductBody(product);
	}

}
