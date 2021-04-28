package com.ennio.main.chapter1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker  implements ConnectionMaker {
    
	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {

        Class.forName("org.h2.Driver");
		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "user", "dsic");
		return c;
	}
    
}
