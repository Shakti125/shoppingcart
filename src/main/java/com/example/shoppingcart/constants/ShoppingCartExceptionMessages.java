/**
 * 
 */
package com.example.shoppingcart.constants;

/**
 * @author Shakti
 *
 */
public class ShoppingCartExceptionMessages {

	private ShoppingCartExceptionMessages() {

	}

	public static final String SEARCH_BY_PRODUCT_ID_DAOEXCEPTION = "Unable to find product by Id";
	public static final String SEARCH_BY_PRODUCT_ID_SERVICEEXCEPTION = "Failed to find product by id";

	public static final String SEARCH_BY_PRODUCT_NAME_DAOEXCEPTION = "Unable to find product by product name";
	public static final String SEARCH_BY_PRODUCT_NAME_SERVICEEXCEPTION = "Failed to find product by id";

	public static final String SEARCH_BY_PRODUCT_CATEGORY_DAOEXCEPTION = "Unable to find product by product category";
	public static final String SEARCH_BY_PRODUCT_CATEGORY_SERVICEEXCEPTION = "Failed to find product by id";

	public static final String INVALID_PARAMETER_EXCEPTION = "Invaild parameter to serach Product by Id";
	public static final String INVALID_SEARCH_CRITERIA_EXCEPTION = "Invalid criteria to search product. Please search it by id, name or category";

	public static final String GET_CART_DETAILS_BY_USER_ID_DAOEXCEPTION = "Unable to find cart details by user id";
	public static final String GET_CART_DETAILS_BY_USER_ID_SERVICEEXCEPTION = "Failed to cart details by user id";

	public static final String CART_CREATION_FAILED = "Failed to create cart";
	public static final String CART_UPDATION_FAILED = "Failed to update cart";

	public static final String GET_ALL_PRODUCTS_SERVICEEXCEPTION = "Failed to get all products";
	public static final String FAILED_TO_FETCH_USER_DETAILS = "Failed to fetch user details";
	public static final String USER_NOT_FOUND = "User Not Found";
	public static final String PRODUCT_NOT_FOUND = "Products Not Found";
	public static final String CART_NOT_FOUND = "Cart Details Not Found";
	public static final String CART_ALL_PRODUCTS_REMOVAL_SERVICEEXCEPTION = "Failed to remove all products from cart";
	public static final String PRODUCT_ADDITION_FAILED = "Failed to add product";
	public static final String PRODUCT_REMOVAL_FAILED = "Failed to add product";
	public static final String ALL_PRODUCTS_REMOVAL_FAILED = "All products removal failed";

}
