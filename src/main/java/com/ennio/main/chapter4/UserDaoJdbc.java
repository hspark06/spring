package com.ennio.main.chapter4;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.ennio.main.chapter4.domain.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

public class UserDaoJdbc implements UserDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
		
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
				return user;
			}
		};

	
	public void add(final User user) {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
						user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] {id}, this.userMapper);
	} 

	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}

	//public int getCount() {
	//	return this.jdbcTemplate.queryForInt("select count(*) from users");
	//}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",this.userMapper);
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
