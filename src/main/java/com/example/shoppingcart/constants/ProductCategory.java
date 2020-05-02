/**
 * 
 */
package com.example.shoppingcart.constants;

/**
 * @author Shakti
 *
 */
public enum ProductCategory {
	APPARAL("A", "appral"), BOOK("B", "book");

	private String productCatType;
	private String productCategoryName;

	private ProductCategory(String string1, String string2) {
		this.productCatType = string1;
		this.productCategoryName = string2;
	}

	public String getProductCategoryId() {
		return this.productCatType;
	}

	public String getProductCategory() {
		return this.productCategoryName;
	}
}
