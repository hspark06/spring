package com.ennio.main.template;

public interface  LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
