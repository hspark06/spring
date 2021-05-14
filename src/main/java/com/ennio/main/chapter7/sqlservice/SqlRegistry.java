package com.ennio.main.chapter7.sqlservice;

public interface SqlRegistry {
	void registerSql(String key, String sql);

	String findSql(String key) throws SqlNotFoundException;
    
}
