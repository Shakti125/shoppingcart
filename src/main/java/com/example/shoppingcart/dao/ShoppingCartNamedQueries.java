/**
 * 
 */
package com.example.shoppingcart.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Shakti
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_PRODUCT_BY_ID, query = "from Product p where p.productId=?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_APPARAL_BY_ID, query = "from Apparal a where a.productId=?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_BOOK_BY_ID, query = "from Book b where b.productId=?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_PRODUCT_BY_NAME, query = "from Product p where p.productName like ?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_APPARAL_BY_NAME, query = "from Apparal a where a.productName like ?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_BOOK_BY_NAME, query = "from Book b where b.productName like ?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_PRODUCT_BY_CATEGORY, query = "from Product p where TYPE(p)=?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_APPARAL_BY_CATEGORY, query = "from Apparal a where TYPE(a)=?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.SEARCH_BOOK_BY_CATEGORY, query = "from Book b where TYPE(b)=?1"),
		@NamedQuery(name = ShoppingCartNamedQueries.GET_CART_BY_USER_ID, query = "from Cart c where c.user.userId=?1") })
public class ShoppingCartNamedQueries {

	@Id
	private int id;

	public static final String SEARCH_PRODUCT_BY_ID = "Product.searchById";
	public static final String SEARCH_APPARAL_BY_ID = "Apparal.searchById";
	public static final String SEARCH_BOOK_BY_ID = "Book.searchById";
	public static final String SEARCH_PRODUCT_BY_NAME = "Product.searchByName";
	public static final String SEARCH_APPARAL_BY_NAME = "Apparal.searchByName";
	public static final String SEARCH_BOOK_BY_NAME = "Book.searchByName";
	public static final String SEARCH_PRODUCT_BY_CATEGORY = "Product.searchByCategory";
	public static final String SEARCH_APPARAL_BY_CATEGORY = "Apparal.searchByCategory";
	public static final String SEARCH_BOOK_BY_CATEGORY = "Book.searchByCategory";
	public static final String GET_CART_BY_USER_ID = "Cart.getCartByUserId";

}
