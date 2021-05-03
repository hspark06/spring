package com.ennio.main;

import java.io.IOException;

import com.ennio.main.template.Calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class CalcSumTest {
    private static Calculator calculator;
	String numFilepath;
	
	@BeforeAll 
    static public void setUp() {
		calculator = new Calculator();
		//numFilepath = getClass().getResource("numbers.txt").getPath();
	}
	
	@Test public void sumOfNumbers() throws IOException {
        numFilepath = getClass().getResource("/com/ennio/main/template/numbers.txt").getPath();
		assertThat(calculator.calcSum(numFilepath), is(10));
	}
	
	@Test public void multiplyOfNumbers() throws IOException {
        numFilepath = getClass().getResource("/com/ennio/main/template/numbers.txt").getPath();
		assertThat(calculator.calcMultiply(numFilepath), is(24));
	}
	
	@Test public void concatenateStrings() throws IOException {
        numFilepath = getClass().getResource("/com/ennio/main/template/numbers.txt").getPath();
		assertThat(calculator.concatenate(numFilepath), is("1234"));
	}

}
