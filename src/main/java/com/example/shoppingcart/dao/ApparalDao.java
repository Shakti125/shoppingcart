/**
 * 
 */
package com.example.shoppingcart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.Apparal;

/**
 * @author Shakti
 *
 */
@Repository
public interface ApparalDao extends JpaRepository<Apparal, Integer> {

}
