package com.ennio.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ennio.main.chapter4.UserDao;
import com.ennio.main.chapter4.domain.Level;
import com.ennio.main.chapter4.domain.User;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class) 
@ContextConfiguration(locations="/com/ennio/main/chapter1/applicationContext.xml")
@SpringBootTest
public class UserDaoTest {
	
	@Autowired
	private UserDao dao; 
	@Autowired 
	DataSource dataSource;
	
	private static User user1;
	private static User user2;
	private static User user3;

	@BeforeAll
	static public void setUp() {
		
		user1 = new User("gyumee", "111", "springno1", Level.BASIC, 1, 0);
		user2 = new User("leegw700", "222", "springno2", Level.SILVER, 55, 10);
		user3 = new User("bumjin", "333", "springno3", Level.GOLD, 100, 40);
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
	
	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();
		
		List<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));
		
		dao.add(user1); // Id: gyumee
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2); // Id: leegw700
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));  
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3); // Id: bumjin
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user3, users3.get(0));  
		checkSameUser(user1, users3.get(1));  
		checkSameUser(user2, users3.get(2));  
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}

	@Test
	public void duplciateKey() {
		dao.deleteAll();
		
		Assertions.assertThrows(DuplicateKeyException.class, () -> {
			dao.add(user1);
			dao.add(user1);
		});
	}
	@Test
	public void sqlExceptionTranslate() {
		dao.deleteAll();
		
		try {
			dao.add(user1);
			dao.add(user1);
		}
		catch(DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException)ex.getCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);			
			DataAccessException transEx = set.translate(null, null, sqlEx);
			assertThat(transEx, is(DuplicateKeyException.class));
		}
	}



}
