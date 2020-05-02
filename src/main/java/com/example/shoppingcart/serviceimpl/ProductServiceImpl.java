/**
 * 
 */
package com.example.shoppingcart.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingcart.constants.ProductCategory;
import com.example.shoppingcart.constants.SearchCriteria;
import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.dao.ApparalDao;
import com.example.shoppingcart.dao.BookDao;
import com.example.shoppingcart.dao.ShoppingCartDao;
import com.example.shoppingcart.dto.ProductUIBean;
import com.example.shoppingcart.exception.ShoppingCartDaoException;
import com.example.shoppingcart.exception.ShoppingCartServiceException;
import com.example.shoppingcart.model.Apparal;
import com.example.shoppingcart.model.Book;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.service.ProductService;
import com.example.shoppingcart.utility.ProductBeanUtil;

/**
 * @author Shakti
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ShoppingCartDao shoppingcartDao;

	@Autowired
	private ApparalDao appralDao;

	@Autowired
	private BookDao bookDao;

	@Override
	public List<ProductUIBean> getAllProducts() throws ShoppingCartServiceException {
		try {
			List<Apparal> apprals = appralDao.findAll();
			List<Book> books = bookDao.findAll();
			return createProductList(apprals, books);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.GET_ALL_PRODUCTS_SERVICEEXCEPTION);
		}
	}

	@Override
	public List<ProductUIBean> serachProduct(String searchCriteria, String param) throws ShoppingCartServiceException {
		List<ProductUIBean> products = null;
		if (searchCriteria.equalsIgnoreCase(SearchCriteria.ID.getSerachCriteria())) {
			products = serachproductByid(param);
		} else if (searchCriteria.equalsIgnoreCase(SearchCriteria.NAME.getSerachCriteria())) {
			products = serachproductByName(param);
		} else if (searchCriteria.equalsIgnoreCase(SearchCriteria.CATEGORY.getSerachCriteria())) {
			products = serachproductByCategory(param);
		} else {
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.INVALID_SEARCH_CRITERIA_EXCEPTION);
		}
		if (CollectionUtils.isEmpty(products)) {
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.PRODUCT_NOT_FOUND);
		}
		return products;
	}

	private List<ProductUIBean> serachproductByid(String param) throws ShoppingCartServiceException {
		List<ProductUIBean> products = new ArrayList<>();
		List<Apparal> apparals = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		try {
			apparals = shoppingcartDao.serachApparalByid(Integer.valueOf(param));
			books = shoppingcartDao.serachBookByid(Integer.valueOf(param));
			products = createProductList(apparals, books);
		} catch (NumberFormatException e) {
			logger.error(ShoppingCartExceptionMessages.INVALID_PARAMETER_EXCEPTION);
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.INVALID_PARAMETER_EXCEPTION);
		} catch (ShoppingCartDaoException e) {
			logger.error(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_ID_SERVICEEXCEPTION);
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_ID_SERVICEEXCEPTION);
		}
		return products;
	}

	private List<ProductUIBean> serachproductByName(String param) throws ShoppingCartServiceException {
		List<ProductUIBean> products = new ArrayList<>();
		List<Apparal> apparals = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		try {
			apparals = shoppingcartDao.searchApparalByName(param);
			books = shoppingcartDao.searchBookByName(param);
			products = createProductList(apparals, books);
		} catch (ShoppingCartDaoException e) {
			logger.error(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_NAME_SERVICEEXCEPTION);
			throw new ShoppingCartServiceException(
					ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_NAME_SERVICEEXCEPTION);
		}
		return products;
	}

	private List<ProductUIBean> serachproductByCategory(String param) throws ShoppingCartServiceException {
		List<ProductUIBean> productBeans = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		try {
			if (param.equalsIgnoreCase(ProductCategory.APPARAL.getProductCategoryId())) {
				products = shoppingcartDao.searchProductByCategory(Apparal.class);
			} else if (param.equalsIgnoreCase(ProductCategory.BOOK.getProductCategoryId())) {
				products = shoppingcartDao.searchProductByCategory(Book.class);
			}
		} catch (ShoppingCartDaoException e) {
			logger.error(ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_CATEGORY_SERVICEEXCEPTION);
			throw new ShoppingCartServiceException(
					ShoppingCartExceptionMessages.SEARCH_BY_PRODUCT_CATEGORY_SERVICEEXCEPTION);
		}
		if (param.equalsIgnoreCase(ProductCategory.APPARAL.getProductCategoryId())) {
			List<Apparal> apparals = new ArrayList<>();
			for (Product product : products) {
				Optional<Apparal> appral = appralDao.findById(product.getProductId());
				if (appral.isPresent()) {
					apparals.add(appral.get());
				}
				productBeans = createProductList(apparals, null);
			}
		}
		if (param.equalsIgnoreCase(ProductCategory.BOOK.getProductCategoryId())) {
			List<Book> books = new ArrayList<>();
			for (Product product : products) {
				Optional<Book> book = bookDao.findById(product.getProductId());
				if (book.isPresent()) {
					books.add(book.get());
				}
				productBeans = createProductList(null, books);
			}
		}
		return productBeans;
	}

	private List<ProductUIBean> createProductList(List<Apparal> apprals, List<Book> books) {
		List<ProductUIBean> products = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(apprals)) {
			for (Apparal apparal : apprals) {
				products.add(ProductBeanUtil.createApparalProductUIBean(apparal));
			}
		}
		if (CollectionUtils.isNotEmpty(books)) {
			for (Book book : books) {
				products.add(ProductBeanUtil.createBookProductUIBean(book));
			}
		}
		return products;
	}

}
