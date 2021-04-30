package com.ennio.main;

import org.h2.jdbc.JdbcSQLNonTransientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ennio.main.chapter1.dao.UserDao;
import com.ennio.main.chapter1.domain.User;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) 
@ContextConfiguration(locations="/com/ennio/main/chapter1/applicationContext.xml")
@SpringBootTest
public class UserDaoTest {
	
	@Autowired
	private UserDao dao; 
	
	private static User user1;
	private static User user2;
	private static User user3;

	@BeforeAll
	static public void setUp() {
		
		user1 = new User("gyumee", "111", "springno1");
		user2 = new User("leegw700", "222", "springno2");
		user3 = new User("bumjin", "333", "springno3");
	}

	@Test
	public void andAndGet() throws SQLException {	
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));

	}

	@Test
	public void count() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
				
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test
	public void getUserFailure() throws SQLException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			dao.get("unknown_id");
		  });
	}

}
