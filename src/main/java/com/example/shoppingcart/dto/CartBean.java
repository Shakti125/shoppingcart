/**
 * 
 */
package com.example.shoppingcart.dto;

import java.util.Map;

/**
 * @author Shakti
 *
 */
public class CartBean {

	private int userId;
	private String userName;
	private int cartId;
	private long totalQuantity;
	private float totalPrice;
	private Map<Integer, ProductUIBean> productDetails;

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the cartId
	 */
	public int getCartId() {
		return cartId;
	}

	/**
	 * @param cartId the cartId to set
	 */
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	/**
	 * @return the totalQuantity
	 */
	public long getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * @param totalQuantity the totalQuantity to set
	 */
	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * @return the totalPrice
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the productDetails
	 */
	public Map<Integer, ProductUIBean> getProductDetails() {
		return productDetails;
	}

	/**
	 * @param productDetails the productDetails to set
	 */
	public void setProductDetails(Map<Integer, ProductUIBean> productDetails) {
		this.productDetails = productDetails;
	}

}
