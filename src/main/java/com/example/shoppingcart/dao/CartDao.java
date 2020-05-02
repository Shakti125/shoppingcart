/**
 * 
 */
package com.example.shoppingcart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.Cart;

/**
 * @author Shakti
 *
 */
@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

}
