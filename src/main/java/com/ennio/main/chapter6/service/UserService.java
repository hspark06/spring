package com.ennio.main.chapter6.service;

import com.ennio.main.chapter6.domain.User;
import java.util.List;

public interface UserService {
    void add(User user);
	void deleteAll();
	void update(User user);	
	User get(String id);
	List<User> getAll();
	
	void upgradeLevels();
}
