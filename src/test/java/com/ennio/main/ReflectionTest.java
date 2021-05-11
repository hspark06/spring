package com.ennio.main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ennio.main.chapter6.dao.UserDao;
import com.ennio.main.chapter6.domain.Level;
import com.ennio.main.chapter6.domain.User;
import com.ennio.main.chapter6.service.UserService;
import com.ennio.main.chapter6.service.UserServiceImpl;
import com.ennio.main.chapter6.service.UserServiceTx;

import static com.ennio.main.chapter6.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.ennio.main.chapter6.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;


@ExtendWith(SpringExtension.class) 
@ContextConfiguration(locations="/com/ennio/main/chapter6/applicationContext.xml")
@SpringBootTest
public class ReflectionTest {

    @Test
	public void invokeMethod() throws Exception {
		String name = "Spring";

		// length
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));
		
		// toUpperCase
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
	}
	
	@Test 
	public void createObject() throws Exception {
		Date now = (Date) Class.forName("java.util.Date").newInstance();
	}

}
