/**
 * 
 */
package com.example.shoppingcart.serviceimpl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingcart.constants.ShoppingCartExceptionMessages;
import com.example.shoppingcart.dao.UserDao;
import com.example.shoppingcart.dto.UserBean;
import com.example.shoppingcart.exception.ShoppingCartServiceException;
import com.example.shoppingcart.model.User;
import com.example.shoppingcart.service.UserService;

/**
 * @author Shakti
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public UserBean getUserInformation() throws ShoppingCartServiceException {
		logger.info("Fetching User Information..");
		List<User> users = null;
		try {
			users = userDao.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShoppingCartServiceException(ShoppingCartExceptionMessages.FAILED_TO_FETCH_USER_DETAILS);
		}
		return CollectionUtils.isNotEmpty(users) ? createUserBean(users.get(0)) : null;
	}

	private UserBean createUserBean(User user) {
		UserBean userBean = new UserBean();
		userBean.setUserId(user.getUserId());
		userBean.setUserName(user.getUserName());
		return userBean;
	}

}
