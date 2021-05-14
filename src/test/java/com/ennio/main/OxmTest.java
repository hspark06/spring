package com.ennio.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.List;

import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ennio.main.chapter6.factorybean.Message;
import com.ennio.main.chapter6.factorybean.MessageFactoryBean;
import com.ennio.main.chapter7.sqlservice.jaxb.Sqlmap;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

@ExtendWith(SpringExtension.class) 
@ContextConfiguration(locations="/com/ennio/main/chapter6/FactoryBeanTest-context.xml")
@SpringBootTest
public class OxmTest {

    @Autowired
	Unmarshaller unmarshaller;
	
	@Test 
	public void unmarshallSqlMap() throws XmlMappingException, IOException  {
		Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
		Sqlmap sqlmap = (Sqlmap)this.unmarshaller.unmarshal(xmlSource);
		
		List<SqlType> sqlList = sqlmap.getSql();		
		assertThat(sqlList.size(), is(3));
		assertThat(sqlList.get(0).getKey(), is("add"));
		assertThat(sqlList.get(0).getValue(), is("insert"));
		assertThat(sqlList.get(1).getKey(), is("get"));
		assertThat(sqlList.get(1).getValue(), is("select"));
		assertThat(sqlList.get(2).getKey(), is("delete"));
		assertThat(sqlList.get(2).getValue(), is("delete"));
	}
}
