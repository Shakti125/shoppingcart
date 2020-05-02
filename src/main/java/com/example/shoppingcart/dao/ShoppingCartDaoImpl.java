/**
 * 
 */
package com.example.shoppingcart.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.exception.ShoppingCartDaoException;
import com.example.shoppingcart.model.Apparal;
import com.example.shoppingcart.model.Book;
import com.example.shoppingcart.model.Cart;
import com.example.shoppingcart.model.Product;

/**
 * @author Shakti
 *
 */
@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {

	private final Logger logger = LoggerFactory.getLogger(ShoppingCartDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> serachProductByid(int productId) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_PRODUCT_BY_ID);
			query.setParameter(1, productId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_ID_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Apparal> serachApparalByid(int productId) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_APPARAL_BY_ID);
			query.setParameter(1, productId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_ID_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> serachBookByid(int productId) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_BOOK_BY_ID);
			query.setParameter(1, productId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_ID_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchProductByName(String name) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_PRODUCT_BY_NAME);
			query.setParameter(1, "%" + name + "%");
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_NAME_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Apparal> searchApparalByName(String name) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_APPARAL_BY_NAME);
			query.setParameter(1, "%" + name + "%");
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_NAME_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> searchBookByName(String name) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_BOOK_BY_NAME);
			query.setParameter(1, "%" + name + "%");
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_NAME_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Product> searchProductByCategory(Class<T> class1) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.SEARCH_PRODUCT_BY_CATEGORY);
			query.setParameter(1, class1);
			return query.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_CATEGORY_DAOEXCEPTION);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Cart getCartDetailsByUserId(int userId) throws ShoppingCartDaoException {
		try {
			Query query = entityManager.createNamedQuery(ShoppingCartNamedQueries.GET_CART_BY_USER_ID);
			query.setParameter(1, userId);
			List objects = query.getResultList();
			return CollectionUtils.isNotEmpty(objects) ? (Cart) objects.get(0) : null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartDaoException(ShoppingCartExceptionMessages.GET_CART_DETAILS_BY_USER_ID_DAOEXCEPTION);
		}
	}

}
