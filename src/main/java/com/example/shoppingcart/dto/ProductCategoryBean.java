/**
 * 
 */
package com.example.shoppingcart.dto;

/**
 * @author Shakti
 *
 */
public class ProductCategoryBean {

	private int productId;
	private String productCategoty;
	private boolean addFlag;

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the productCategoty
	 */
	public String getProductCategoty() {
		return productCategoty;
	}

	/**
	 * @param productCategoty the productCategoty to set
	 */
	public void setProductCategoty(String productCategoty) {
		this.productCategoty = productCategoty;
	}

	/**
	 * @return the addFlag
	 */
	public boolean isAddFlag() {
		return addFlag;
	}

	/**
	 * @param addFlag the addFlag to set
	 */
	public void setAddFlag(boolean addFlag) {
		this.addFlag = addFlag;
	}

}
