package com.ennio.main.chapter1.dao;

import java.sql.*;
import javax.sql.DataSource;
import java.util.List;
import com.ennio.main.chapter1.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {
   
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
		
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		this.jdbcTemplate.setDataSource(dataSource);

		this.dataSource = dataSource;
	}
	
	public void add(final User user) throws SQLException {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
						user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] {id}, 
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	} 

	public void deleteAll() throws SQLException {
		this.jdbcTemplate.update("delete from users");
	}

	public int getCount() throws SQLException  {
		Connection c = dataSource.getConnection();
	
		PreparedStatement ps = c.prepareStatement("select count(*) from users");

		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		ps.close();
		c.close();
	
		return count;
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	}
}
