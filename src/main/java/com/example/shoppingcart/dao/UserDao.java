/**
 * 
 */
package com.example.shoppingcart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.User;

/**
 * @author Shakti
 *
 */

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}
