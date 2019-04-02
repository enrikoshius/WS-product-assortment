package mif.productassortment.web.rest.errors;

public class ErrConstants {
	public static String PRODUCT_NOT_FOUND_BY_ID = "Requested product cannot be found.";
	public static String INVALID_STATUS_NAME = "Incorrect status name. Valid options: IN_STOCK, OUT_OF_STOCK, SUSPENDED.";
	public static String MISSING_FIELDS = "The following fields are required and cannot be empty: ";
	public static String INVALID_PUT_REQUEST = "Put request must contain at least one of the following fields: name, description, status";
	public static String ID_CANNOT_BE_SET_MANUALLY = "Id for a new product cannot be set manually. It will be generated automatically.";
	public static String CREATION_DATE_CANNOT_BE_SET_MANUALLY = "Creation date for a new product cannot be set manually. It will be generated automatically.";
	public static String MODIFICATION_DATE_CANNOT_BE_SET_MANUALLY  = "Modification date for a new product cannot be set manually.";
}
