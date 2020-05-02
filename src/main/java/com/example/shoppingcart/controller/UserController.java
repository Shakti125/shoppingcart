/**
 * 
 */
package com.example.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.dto.UserBean;
import com.example.shoppingcart.exception.ShoppingCartServiceException;
import com.example.shoppingcart.exception.UserNotFoundException;
import com.example.shoppingcart.service.UserService;

/**
 * @author Shakti
 *
 */
@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserBean> getUserInfo() {
		try {
			UserBean userBean = userService.getUserInformation();
			return new ResponseEntity<>(userBean, HttpStatus.OK);
		} catch (ShoppingCartServiceException e) {
			throw new UserNotFoundException(ShoppingCartExceptionMessages.USER_NOT_FOUND);
		}
	}
}
