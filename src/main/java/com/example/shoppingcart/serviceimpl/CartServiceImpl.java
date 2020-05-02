/**
 * 
 */
package com.example.shoppingcart.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingcart.constants.ProductCategory;
import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.dao.ApparalDao;
import com.example.shoppingcart.dao.BookDao;
import com.example.shoppingcart.dao.CartDao;
import com.example.shoppingcart.dao.ShoppingCartDao;
import com.example.shoppingcart.dao.UserDao;
import com.example.shoppingcart.dto.CartBean;
import com.example.shoppingcart.dto.ProductCategoryBean;
import com.example.shoppingcart.dto.ProductQuantityBean;
import com.example.shoppingcart.dto.ProductUIBean;
import com.example.shoppingcart.exception.ShoppingCartDaoException;
import com.example.shoppingcart.exception.ShoppingCartServiceException;
import com.example.shoppingcart.model.Apparal;
import com.example.shoppingcart.model.Book;
import com.example.shoppingcart.model.Cart;
import com.example.shoppingcart.model.User;
import com.example.shoppingcart.service.CartService;
import com.example.shoppingcart.utility.ProductBeanUtil;

/**
 * @author Shakti
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CartServiceImpl implements CartService {

	private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CartDao cartDao;

	@Autowired
	private ApparalDao appralDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private ShoppingCartDao shoppingCartDao;

	@Override
	public CartBean getCartDetails(int userId) throws ShoppingCartServiceException {
		try {
			return createCartBean(shoppingCartDao.getCartDetailsByUserId(userId));
		} catch (ShoppingCartDaoException e) {
			logger.error(e.getMessage());
			throw new ShoppingCartServiceException(
					ShoppingCartExceptionMessages.GET_CART_DETAILS_BY_USER_ID_SERVICEEXCEPTION);
		}
	}

	@Override
	public CartBean createOrUpdateCart(int userId, ProductQuantityBean productBean)
			throws ShoppingCartServiceException {
		logger.info("Creating Cart for user with id {}", userId);
		Cart cart = null;
		try {
			cart = shoppingCartDao.getCartDetailsByUserId(userId);
		} catch (ShoppingCartDaoException e) {
			logger.error(e.getMessage());
			throw new ShoppingCartServiceException(
					ShoppingCartExceptionMessages.GET_CART_DETAILS_BY_USER_ID_SERVICEEXCEPTION);
		}
		if (cart == null) {
			cart = new Cart();
			User user = findUser(userId);
			cart.setUser(user);
		}

		List<Apparal> apparals = cart.getApparals();
		apparals = convertAppralQuantity(productBean, apparals, cart);
		cart.setApparals(apparals);
		List<Book> books = cart.getBooks();
		books = convertBookQuantity(productBean, books, cart);
		cart.setBooks(books);
		cart.setQuantity(calculateCartTotalQuantity(apparals, books));
		cart.setTotalPrice(calculateCartTotalPrice(apparals, books));
		try {
			cart = cartDao.saveAndFlush(cart);
			logger.info("Cart creation successfull for user {} with cart id {}", userId, cart.getCartId());
			return createCartBean(cart);
		} catch (Exception e) {
			logger.error("Cart creation failed for user {}", userId);
			logger.error(e.getMessage());
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.CART_CREATION_FAILED);
		}
	}

	@Override
	public CartBean addOrRemoveProductFromCart(int cartId, ProductCategoryBean productCategoryBean)
			throws ShoppingCartServiceException {
		Optional<Cart> cartOptional = cartDao.findById(cartId);
		Cart cart = cartOptional.isPresent() ? cartOptional.get() : cartOptional.orElse(null);
		Cart modifiedCart = new Cart();
		if (cart != null) {
			if (productCategoryBean.getProductCategoty()
					.equalsIgnoreCase(ProductCategory.APPARAL.getProductCategoryId())) {
				Optional<Apparal> apparalOptianal = appralDao.findById(productCategoryBean.getProductId());
				Apparal apparal = apparalOptianal.isPresent() ? apparalOptianal.get() : apparalOptianal.orElse(null);
				if (productCategoryBean.isAddFlag()) {
					modifiedCart = addOrRemoveApparalToCart(cart, apparal, true);
				} else {
					modifiedCart = addOrRemoveApparalToCart(cart, apparal, false);
				}
			} else if (productCategoryBean.getProductCategoty()
					.equalsIgnoreCase(ProductCategory.BOOK.getProductCategoryId())) {
				Optional<Book> bookOptional = bookDao.findById(productCategoryBean.getProductId());
				Book book = bookOptional.isPresent() ? bookOptional.get() : bookOptional.orElse(null);
				if (productCategoryBean.isAddFlag()) {
					modifiedCart = addOrRemoveBookToCart(cart, book, true);
				} else {
					modifiedCart = addOrRemoveBookToCart(cart, book, false);
				}
			}
			try {
				modifiedCart = cartDao.saveAndFlush(modifiedCart);
				logger.info("Products added or removed from cart successfully for user {} with cart id {}",
						modifiedCart.getUser().getUserId(), modifiedCart.getCartId());
				return createCartBean(modifiedCart);
			} catch (Exception e) {
				logger.error("Failed to add or remove product in cart for user {}", modifiedCart.getUser().getUserId());
				logger.error(e.getMessage());
				throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.CART_CREATION_FAILED);
			}
		} else {
			return null;
		}
	}

	@Override
	public CartBean removeAllProducts(int cartId) throws ShoppingCartServiceException {
		Optional<Cart> cartOptional = cartDao.findById(cartId);
		Cart cart = cartOptional.isPresent() ? cartOptional.get() : cartOptional.orElse(null);
		if (cart != null) {
			cart.getApparals().stream().filter(Objects::nonNull).forEach(a -> {
				a.setProductQuantity(null);
				a.setCart(null);
			});
			cart.getBooks().stream().filter(Objects::nonNull).forEach(b -> {
				b.setProductQuantity(null);
				b.setCart(null);
			});
			cart.getApparals().clear();
			cart.getBooks().clear();
			cart.setQuantity(0);
			cart.setTotalPrice(0f);

			try {
				cart = cartDao.saveAndFlush(cart);
				logger.info("All the Products removed from cart successfully for user {} with cart id {}",
						cart.getUser().getUserId(), cart.getCartId());
				return createCartBean(cart);
			} catch (Exception e) {
				logger.error("Failed to remove all products from cart of user {}", cart.getUser().getUserId());
				logger.error(e.getMessage());
				throw new ShoppingCartServiceException(
						ShoppingCartExceptionMessages.CART_ALL_PRODUCTS_REMOVAL_SERVICEEXCEPTION);
			}
		} else {
			throw new ShoppingCartServiceException(
					ShoppingCartExceptionMessages.GET_CART_DETAILS_BY_USER_ID_SERVICEEXCEPTION);
		}

	}

	private User findUser(int userId) {
		logger.info("Fetching user with id {}", userId);
		Optional<User> user = userDao.findById(userId);
		return user.isPresent() ? user.get() : user.orElse(null);
	}

	private List<Apparal> convertAppralQuantity(ProductQuantityBean productBean, List<Apparal> exitingApparals,
			Cart cart) {
		List<Apparal> apparals = new ArrayList<>();
		Map<Integer, Long> apprralQuantity = productBean.getAppralQuantity();
		boolean createdFlag = productBean.isCreateFlag();
		if (createdFlag && MapUtils.isNotEmpty(apprralQuantity)) {
			for (Map.Entry<Integer, Long> entry : apprralQuantity.entrySet()) {
				int productId = entry.getKey();
				long quantity = entry.getValue();
				Apparal apparal = findApparalById(productId);
				if (apparal != null) {
					apparal.setProductQuantity(quantity);
					apparal.setCart(cart);
				}
				apparals.add(apparal);
			}
		} else if (!createdFlag) {
			apparals = exitingApparals;
			if (MapUtils.isNotEmpty(apprralQuantity)) {
				Set<Integer> differntProductIds = getDiffernetProductIds(apprralQuantity.keySet(),
						apparals.stream().map(Apparal::getProductId).collect(Collectors.toSet()));
				if (differntProductIds != null && !differntProductIds.isEmpty()) {
					for (int id : differntProductIds) {
						long quantity = apprralQuantity.get(id);
						Apparal apparal = findApparalById(id);
						if (apparal != null) {
							apparal.setProductQuantity(quantity);
							apparal.setCart(cart);
						}
						apparals.add(apparal);
					}
				}
				Set<Integer> similarProductIds = getSimilarProductIds(apprralQuantity.keySet(),
						apparals.stream().map(Apparal::getProductId).collect(Collectors.toSet()));
				if (similarProductIds != null && !similarProductIds.isEmpty()) {
					for (int id : similarProductIds) {
						Apparal apparal = findApparalById(id);
						long quantityNew = apprralQuantity.get(id);
						if (apparal != null) {
							apparals.stream().filter(a -> a.getProductId() == id)
									.forEach(a -> a.setProductQuantity(quantityNew));
						}
						if (quantityNew == 0) {
							apparals.stream().filter(a -> a.getProductId() == id).forEach(a -> a.setCart(null));
							apparals.remove(apparal);
						}
					}
				}
			}
		}
		return apparals;
	}

	private List<Book> convertBookQuantity(ProductQuantityBean productBean, List<Book> existingBooks, Cart cart) {
		List<Book> books = new ArrayList<>();
		Map<Integer, Long> bookQuantity = productBean.getBookQuantity();
		boolean createdFlag = productBean.isCreateFlag();
		if (createdFlag && MapUtils.isNotEmpty(bookQuantity)) {
			for (Map.Entry<Integer, Long> entry : bookQuantity.entrySet()) {
				int productId = entry.getKey();
				long quantity = entry.getValue();
				Book book = findBookById(productId);
				if (book != null) {
					book.setProductQuantity(quantity);
					book.setCart(cart);
				}
				books.add(book);
			}
		} else if (!createdFlag) {
			books = existingBooks;
			if (MapUtils.isNotEmpty(bookQuantity)) {
				Set<Integer> differntProductIds = getDiffernetProductIds(bookQuantity.keySet(),
						books.stream().map(Book::getProductId).collect(Collectors.toSet()));
				if (differntProductIds != null && !differntProductIds.isEmpty()) {
					for (int id : differntProductIds) {
						long quantity = bookQuantity.get(id);
						Book book = findBookById(id);
						if (book != null) {
							book.setProductQuantity(quantity);
							book.setCart(cart);
						}
						books.add(book);
					}
				}
				Set<Integer> similarProductIds = getSimilarProductIds(bookQuantity.keySet(),
						books.stream().map(Book::getProductId).collect(Collectors.toSet()));
				if (similarProductIds != null && !similarProductIds.isEmpty()) {
					for (int id : similarProductIds) {
						Book book = findBookById(id);
						long quantityNew = bookQuantity.get(id);
						if (book != null) {
							books.stream().filter(b -> b.getProductId() == id)
									.forEach(b -> b.setProductQuantity(quantityNew));
						}
						if (quantityNew == 0) {
							books.stream().filter(b -> b.getProductId() == id).forEach(b -> b.setCart(null));
							books.remove(book);
						}
					}
				}
			}
		}
		return books;
	}

	private Set<Integer> getSimilarProductIds(Set<Integer> productIdsNew, Set<Integer> productIdsExisting) {
		return productIdsNew.stream().filter(productIdsExisting::contains).collect(Collectors.toSet());
	}

	private Set<Integer> getDiffernetProductIds(Set<Integer> productIdsNew, Set<Integer> productIdsExisting) {
		return productIdsNew.stream().filter(i -> !productIdsExisting.contains(i)).collect(Collectors.toSet());
	}

	private Apparal findApparalById(int productId) {
		logger.info("Fetching apparal details with id {}", productId);
		Optional<Apparal> appral = appralDao.findById(productId);
		return appral.isPresent() ? appral.get() : appral.orElse(null);
	}

	private Book findBookById(int productId) {
		logger.info("Fetching book details with id {}", productId);
		Optional<Book> book = bookDao.findById(productId);
		return book.isPresent() ? book.get() : book.orElse(null);
	}

	private long calculateCartTotalQuantity(List<Apparal> apparals, List<Book> books) {
		long appralQuantity = 0l;
		long bookQuantity = 0l;
		if (CollectionUtils.isNotEmpty(apparals)) {
			appralQuantity = apparals.stream().map(Apparal::getProductQuantity).reduce(0l, Long::sum);
		}
		if (CollectionUtils.isNotEmpty(books)) {
			bookQuantity = books.stream().map(Book::getProductQuantity).reduce(0l, Long::sum);
		}
		return appralQuantity + bookQuantity;
	}

	private float calculateCartTotalPrice(List<Apparal> apparals, List<Book> books) {
		float appralTotalPrice = 0f;
		float bookTotalPrice = 0f;
		if (CollectionUtils.isNotEmpty(apparals)) {
			appralTotalPrice = apparals.stream().map(a -> a.getProductQuantity() * a.getPrice()).reduce(0f, Float::sum);
		}
		if (CollectionUtils.isNotEmpty(books)) {
			bookTotalPrice = books.stream().map(b -> b.getProductQuantity() * b.getPrice()).reduce(0f, Float::sum);
		}
		return appralTotalPrice + bookTotalPrice;
	}

	private CartBean createCartBean(Cart cart) {
		if (cart != null) {
			CartBean cartBean = new CartBean();
			cartBean.setUserId(cart.getUser().getUserId());
			cartBean.setUserName(cart.getUser().getUserName());
			cartBean.setCartId(cart.getCartId());
			cartBean.setTotalQuantity(cart.getQuantity());
			cartBean.setTotalPrice(cart.getTotalPrice());
			cartBean.setProductDetails(prepareProductDetailsMapForCartBean(cart.getApparals(), cart.getBooks()));
			return cartBean;
		} else {
			return null;
		}
	}

	private Map<Integer, ProductUIBean> prepareProductDetailsMapForCartBean(List<Apparal> apparals, List<Book> books) {
		Map<Integer, ProductUIBean> productDetails = new HashMap<>();
		if (CollectionUtils.isNotEmpty(apparals)) {
			apparals.stream().filter(Objects::nonNull).forEachOrdered(a -> {
				productDetails.put(a.getProductId(), createProductUIBeanApparal(a));
			});
		}
		if (CollectionUtils.isNotEmpty(books)) {
			books.stream().filter(Objects::nonNull).forEachOrdered(b -> {
				productDetails.put(b.getProductId(), createProductUIBeanBook(b));
			});
		}
		return productDetails;
	}

	private ProductUIBean createProductUIBeanApparal(Apparal apparal) {
		if (apparal != null) {
			return ProductBeanUtil.createApparalProductUIBean(apparal);
		} else {
			return null;
		}
	}

	private ProductUIBean createProductUIBeanBook(Book book) {
		if (book != null) {
			return ProductBeanUtil.createBookProductUIBean(book);
		} else {
			return null;
		}
	}

	private Cart addOrRemoveApparalToCart(Cart cart, Apparal apparal, boolean addFlag) {
		long quantity = cart.getQuantity();
		float totalPrice = cart.getTotalPrice();
		if (apparal != null) {
			List<Apparal> apparals = cart.getApparals();
			if (CollectionUtils.isEmpty(apparals)) {
				apparals = new ArrayList<>();
			}
			if (addFlag) {
				apparals.stream().filter(a -> a.getProductId() == apparal.getProductId())
						.forEach(a -> a.setProductQuantity(a.getProductQuantity() + 1));
				cart.setQuantity(quantity + 1);
				cart.setTotalPrice(totalPrice + apparal.getPrice());
			} else {
				if (apparal.getProductQuantity() - 1 == 0) {
					apparals.stream().filter(a -> a.getProductId() == apparal.getProductId()).forEach(a -> {
						a.setProductQuantity(null);
						a.setCart(null);
					});
				} else {
					apparals.stream().filter(a -> a.getProductId() == apparal.getProductId())
							.forEach(a -> a.setProductQuantity(a.getProductQuantity() - 1));
				}
				cart.setQuantity(quantity != 0 ? quantity - 1 : 0);
				cart.setTotalPrice(totalPrice != 0 ? totalPrice - apparal.getPrice() : 0);
			}
			cart.setApparals(apparals);
		}
		return cart;
	}

	private Cart addOrRemoveBookToCart(Cart cart, Book book, boolean addFlag) {
		long quantity = cart.getQuantity();
		float totalPrice = cart.getTotalPrice();
		if (book != null) {
			List<Book> books = cart.getBooks();
			if (CollectionUtils.isEmpty(books)) {
				books = new ArrayList<>();
			}
			if (addFlag) {
				books.stream().filter(b -> b.getProductId() == book.getProductId())
						.forEach(b -> b.setProductQuantity(b.getProductQuantity() + 1));
				cart.setQuantity(quantity + 1);
				cart.setTotalPrice(totalPrice + book.getPrice());
			} else {
				if (book.getProductQuantity() - 1 == 0) {
					books.stream().filter(b -> b.getProductId() == book.getProductId()).forEach(b -> {
						b.setProductQuantity(null);
						b.setCart(null);
					});
				} else {
					books.stream().filter(b -> b.getProductId() == book.getProductId())
							.forEach(b -> b.setProductQuantity(b.getProductQuantity() - 1));
				}
				cart.setQuantity(quantity != 0 ? quantity - 1 : 0);
				cart.setTotalPrice(totalPrice != 0 ? totalPrice - book.getPrice() : 0);
			}
			cart.setBooks(books);
		}
		return cart;
	}

}
