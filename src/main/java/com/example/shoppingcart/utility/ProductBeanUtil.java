/**
 * 
 */
package com.example.shoppingcart.utility;

import com.example.shoppingcart.constants.ProductCategory;
import com.example.shoppingcart.dto.ProductUIBean;
import com.example.shoppingcart.model.Apparal;
import com.example.shoppingcart.model.Book;

/**
 * @author Shakti
 *
 */
public class ProductBeanUtil {

	private ProductBeanUtil() {

	}

	public static ProductUIBean createApparalProductUIBean(Apparal apparal) {
		ProductUIBean productUIBean = new ProductUIBean();
		productUIBean.setProductId(apparal.getProductId());
		productUIBean.setProductName(apparal.getProductName());
		productUIBean.setPrice(apparal.getPrice());
		productUIBean.setProductCategory(ProductCategory.APPARAL.getProductCategoryId());
		productUIBean.setAppralBrand(apparal.getBrand());
		productUIBean.setAppralDesign(apparal.getDesign());
		productUIBean.setAppralType(apparal.getType());
		productUIBean.setProductQuantity(apparal.getProductQuantity());
		return productUIBean;
	}

	public static ProductUIBean createBookProductUIBean(Book book) {
		ProductUIBean productUIBean = new ProductUIBean();
		productUIBean.setProductId(book.getProductId());
		productUIBean.setProductName(book.getProductName());
		productUIBean.setPrice(book.getPrice());
		productUIBean.setProductCategory(ProductCategory.BOOK.getProductCategoryId());
		productUIBean.setBookDescription(book.getDescription());
		productUIBean.setBookGenre(book.getGenre());
		productUIBean.setBookPublications(book.getPublications());
		productUIBean.setProductQuantity(book.getProductQuantity());
		return productUIBean;
	}
}
