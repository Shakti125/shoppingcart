/**
 * 
 */
package com.example.shoppingcart.dto;

import java.util.Map;

/**
 * @author Shakti
 *
 */
public class ProductQuantityBean {

	private Map<Integer, Long> appralQuantity;

	private Map<Integer, Long> bookQuantity;

	private boolean createFlag;

	/**
	 * @return the appralQuantity
	 */
	public Map<Integer, Long> getAppralQuantity() {
		return appralQuantity;
	}

	/**
	 * @param appralQuantity the appralQuantity to set
	 */
	public void setAppralQuantity(Map<Integer, Long> appralQuantity) {
		this.appralQuantity = appralQuantity;
	}

	/**
	 * @return the bookQuantity
	 */
	public Map<Integer, Long> getBookQuantity() {
		return bookQuantity;
	}

	/**
	 * @param bookQuantity the bookQuantity to set
	 */
	public void setBookQuantity(Map<Integer, Long> bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	/**
	 * @return the createFlag
	 */
	public boolean isCreateFlag() {
		return createFlag;
	}

	/**
	 * @param createFlag the createFlag to set
	 */
	public void setCreateFlag(boolean createFlag) {
		this.createFlag = createFlag;
	}

}
