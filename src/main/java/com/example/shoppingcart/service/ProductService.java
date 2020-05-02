/**
 * 
 */
package com.example.shoppingcart.service;

import java.util.List;

import com.example.shoppingcart.dto.ProductUIBean;
import com.example.shoppingcart.exception.ShoppingCartServiceException;

/**
 * @author Shakti
 *
 */
public interface ProductService {

	public List<ProductUIBean> getAllProducts() throws ShoppingCartServiceException;

	public List<ProductUIBean> serachProduct(String serachCriteria, String param) throws ShoppingCartServiceException;
}
