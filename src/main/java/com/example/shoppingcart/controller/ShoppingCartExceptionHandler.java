/**
 * 
 */
package com.example.shoppingcart.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.shoppingcart.dto.CartCreationFailureException;
import com.example.shoppingcart.dto.CustomErrorResponse;
import com.example.shoppingcart.dto.ProductAddRemoveFailureException;
import com.example.shoppingcart.exception.CartNotFoundException;
import com.example.shoppingcart.exception.ProductNotFoundException;
import com.example.shoppingcart.exception.UserNotFoundException;

/**
 * @author Shakti
 *
 */
@RestControllerAdvice
public class ShoppingCartExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler({ UserNotFoundException.class, ProductNotFoundException.class, CartNotFoundException.class })
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setErrorMessage(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		logger.error(ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ CartCreationFailureException.class, ProductAddRemoveFailureException.class })
	public ResponseEntity<CustomErrorResponse> customHandleFailed(Exception ex) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setErrorMessage(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		logger.error(ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
