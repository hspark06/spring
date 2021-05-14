package com.ennio.main.chapter7.sqlservice;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
