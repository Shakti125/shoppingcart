/**
 * 
 */
package com.example.shoppingcart.dao;

import java.util.List;

import com.example.shoppingcart.exception.ShoppingCartDaoException;
import com.example.shoppingcart.model.Apparal;
import com.example.shoppingcart.model.Book;
import com.example.shoppingcart.model.Cart;
import com.example.shoppingcart.model.Product;

/**
 * @author Shakti
 *
 */
public interface ShoppingCartDao {

	public List<Product> serachProductByid(int productId) throws ShoppingCartDaoException;

	public List<Apparal> serachApparalByid(int productId) throws ShoppingCartDaoException;

	public List<Book> serachBookByid(int productId) throws ShoppingCartDaoException;

	public List<Product> searchProductByName(String name) throws ShoppingCartDaoException;

	public List<Apparal> searchApparalByName(String name) throws ShoppingCartDaoException;

	public List<Book> searchBookByName(String name) throws ShoppingCartDaoException;

	public <T> List<Product> searchProductByCategory(Class<T> class1) throws ShoppingCartDaoException;

	public Cart getCartDetailsByUserId(int userId) throws ShoppingCartDaoException;
}
