/**
 * 
 */
package com.example.shoppingcart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.Book;

/**
 * @author Shakti
 *
 */
@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

}
