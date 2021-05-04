package com.ennio.main.chapter4;

import java.util.List;

import com.ennio.main.chapter1.domain.User;

public interface UserDao {
    
    void add(User user);
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
}
