package com.ennio.main.chapter6.service;

import java.util.List;

import com.ennio.main.chapter6.domain.User;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {

	UserService userService;
	PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void add(User user) {
		this.userService.add(user);
	}

	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager
				.getTransaction(new DefaultTransactionDefinition());
		try {

			userService.upgradeLevels();

			this.transactionManager.commit(status);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}
	public void deleteAll() { 	userService.deleteAll(); }
	public User get(String id) { return userService.get(id); }
	public List<User> getAll() { return userService.getAll(); }
	public void update(User user) { userService.update(user); }
}
