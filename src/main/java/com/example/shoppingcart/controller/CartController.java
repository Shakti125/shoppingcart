/**
 * 
 */
package com.example.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.dto.CartBean;
import com.example.shoppingcart.dto.CartCreationFailureException;
import com.example.shoppingcart.dto.ProductAddRemoveFailureException;
import com.example.shoppingcart.dto.ProductCategoryBean;
import com.example.shoppingcart.dto.ProductQuantityBean;
import com.example.shoppingcart.exception.CartNotFoundException;
import com.example.shoppingcart.exception.ShoppingCartServiceException;
import com.example.shoppingcart.service.CartService;

/**
 * @author Shakti
 *
 */
@RestController
@RequestMapping("/api/v1/cart/")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping(path = "getCartDetails/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartBean> getCartDetails(@PathVariable("userId") int userId) {
		try {
			CartBean cartBean = cartService.getCartDetails(userId);
			return new ResponseEntity<>(cartBean, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new CartNotFoundException(ShoppingCartExceptionMessages.CART_NOT_FOUND);
		}
	}

	@PostMapping(path = "createCart/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartBean> createCart(@PathVariable("userId") int userId,
			@RequestBody ProductQuantityBean productBean) {
		try {
			CartBean cartBean = cartService.createOrUpdateCart(userId, productBean);
			return new ResponseEntity<>(cartBean, HttpStatus.CREATED);
		} catch (ShoppingCartServiceException e) {
			throw new CartCreationFailureException(ShoppingCartExceptionMessages.CART_CREATION_FAILED);
		}
	}

	@PutMapping(path = "updateCart/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartBean> updateCart(@PathVariable("userId") int userId,
			@RequestBody ProductQuantityBean productBean) {
		try {
			CartBean cartBean = cartService.createOrUpdateCart(userId, productBean);
			return new ResponseEntity<>(cartBean, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new CartCreationFailureException(ShoppingCartExceptionMessages.CART_UPDATION_FAILED);
		}
	}

	@PostMapping(path = "/addProduct/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartBean> addProductToCart(@PathVariable("cartId") int cartId,
			@RequestBody ProductCategoryBean productCategoryBean) {
		try {
			CartBean cartBean = cartService.addOrRemoveProductFromCart(cartId, productCategoryBean);
			return new ResponseEntity<>(cartBean, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new ProductAddRemoveFailureException(ShoppingCartExceptionMessages.PRODUCT_ADDITION_FAILED);
		}
	}

	@PostMapping(path = "removeProduct/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartBean> removeProductToCart(@PathVariable("cartId") int cartId,
			@RequestBody ProductCategoryBean productCategoryBean) {
		try {
			CartBean cartBean = cartService.addOrRemoveProductFromCart(cartId, productCategoryBean);
			return new ResponseEntity<>(cartBean, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new ProductAddRemoveFailureException(ShoppingCartExceptionMessages.PRODUCT_REMOVAL_FAILED);
		}
	}

	@PostMapping(path = "removeAllProducts/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartBean> removeAllProductsFromCart(@PathVariable("cartId") int cartId) {
		try {
			CartBean cartBean = cartService.removeAllProducts(cartId);
			return new ResponseEntity<>(cartBean, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new ProductAddRemoveFailureException(ShoppingCartExceptionMessages.ALL_PRODUCTS_REMOVAL_FAILED);
		}
	}
}
