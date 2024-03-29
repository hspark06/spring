package com.ennio.main.chapter7.service;

import com.ennio.main.chapter7.domain.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    void add(User user);
	void deleteAll();
	void update(User user);	

	@Transactional(readOnly=true)
	User get(String id);
	
	@Transactional(readOnly=true)
	List<User> getAll();
	
	void upgradeLevels();
}
