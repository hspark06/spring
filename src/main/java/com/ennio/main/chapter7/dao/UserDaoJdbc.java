package com.ennio.main.chapter7.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import com.ennio.main.chapter7.domain.*;
import com.ennio.main.chapter7.sqlservice.SqlService;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

public class UserDaoJdbc implements UserDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private SqlService sqlService;

	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		this.jdbcTemplate.setDataSource(dataSource);

		this.dataSource = dataSource;
	}
	
	private RowMapper<User> userMapper = 
		new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setLevel(Level.valueOf(rs.getInt("level")));
				user.setLogin(rs.getInt("login"));
				user.setRecommend(rs.getInt("recommend"));
				return user;
			}
		};
	
	public void add(User user) {
		this.jdbcTemplate.update(
				this.sqlService.getSql("userAdd"), 
					user.getId(), user.getName(), user.getPassword(), user.getEmail(), 
					user.getLevel().intValue(), user.getLogin(), user.getRecommend());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
				new Object[] {id}, this.userMapper);
	} 

	public void deleteAll() {
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
	}

	//public int getCount() {
	//	return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
	//}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), this.userMapper);
	}

	public void update(User user) {
		this.jdbcTemplate.update(
				this.sqlService.getSql("userUpdate"),
		user.getName(), user.getPassword(), user.getEmail(), 
		user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
		user.getId());
		
	}

    public int getCount() {
		Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count =0;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           
                try {
                    if(rs !=null) rs.close();
                    if(ps !=null) ps.close();
                    if(c !=null) c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        
		return count;
	}
    
}
