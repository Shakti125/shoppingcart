/**
 * 
 */
package com.example.shoppingcart.service;

import com.example.shoppingcart.dto.CartBean;
import com.example.shoppingcart.dto.ProductCategoryBean;
import com.example.shoppingcart.dto.ProductQuantityBean;
import com.example.shoppingcart.exception.ShoppingCartServiceException;

/**
 * @author Shakti
 *
 */
public interface CartService {

	public CartBean getCartDetails(int userId) throws ShoppingCartServiceException;

	public CartBean createOrUpdateCart(int userId, ProductQuantityBean productBean) throws ShoppingCartServiceException;

	public CartBean addOrRemoveProductFromCart(int cartId, ProductCategoryBean productCategoryBean)
			throws ShoppingCartServiceException;

	public CartBean removeAllProducts(int cartId) throws ShoppingCartServiceException;

}
