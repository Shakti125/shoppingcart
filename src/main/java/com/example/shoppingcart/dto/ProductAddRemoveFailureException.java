/**
 * 
 */
package com.example.shoppingcart.dto;

import com.example.shoppingcart.exception.ShoppingCartRuntimeException;

/**
 * @author Shakti
 *
 */
public class ProductAddRemoveFailureException extends ShoppingCartRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ProductAddRemoveFailureException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ProductAddRemoveFailureException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ProductAddRemoveFailureException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ProductAddRemoveFailureException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ProductAddRemoveFailureException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
