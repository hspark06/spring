package com.ennio.main.chapter1.dao;

import java.sql.*;

import com.ennio.main.chapter1.domain.User;

public class UserDao {
   
	private ConnectionMaker connectionMaker;

	public void setConnectionMaker(ConnectionMaker simpleConnectionMaker) {
		this.connectionMaker = simpleConnectionMaker;
	}

    public void add(User user) throws ClassNotFoundException, SQLException {
		
		Connection c = this.connectionMaker.makeConnection();
        //Connection c = getConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		
		Connection c = this.connectionMaker.makeConnection();
		//Connection c = getConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();

		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}
	/*
    private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "user", "dsic");
	}
	*/
}
