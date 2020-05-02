/**
 * 
 */
package com.example.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.dto.ProductSearchBean;
import com.example.shoppingcart.dto.ProductUIBean;
import com.example.shoppingcart.exception.ProductNotFoundException;
import com.example.shoppingcart.exception.ShoppingCartServiceException;
import com.example.shoppingcart.service.ProductService;

/**
 * @author Shakti
 *
 */
@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(path = "getAllProducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductUIBean>> getAllProducts() {
		try {
			List<ProductUIBean> products = productService.getAllProducts();
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new ProductNotFoundException(ShoppingCartExceptionMessages.PRODUCT_NOT_FOUND);
		}
	}

	@PostMapping(path = "searchProducts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductUIBean>> searchProducts(@RequestBody ProductSearchBean productSearchBean) {
		try {
			List<ProductUIBean> products = productService.serachProduct(productSearchBean.getSearchCriteria(),
					productSearchBean.getSearchKey());
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new ProductNotFoundException(ShoppingCartExceptionMessages.PRODUCT_NOT_FOUND);
		}
	}
}
