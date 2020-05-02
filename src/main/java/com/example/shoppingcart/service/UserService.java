/**
 * 
 */
package com.example.shoppingcart.service;

import com.example.shoppingcart.dto.UserBean;
import com.example.shoppingcart.exception.ShoppingCartServiceException;

/**
 * @author Shakti
 *
 */
public interface UserService {

	public UserBean getUserInformation() throws ShoppingCartServiceException;
}
