package com.ennio.main.chapter6.service;

import com.ennio.main.chapter6.domain.User;

public interface UserService {
    void add(User user);
	void upgradeLevels();
}
