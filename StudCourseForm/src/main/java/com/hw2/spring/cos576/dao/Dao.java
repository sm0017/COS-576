package com.hw2.spring.cos576.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends Object> {
	
	void create(T t);
	T get(Serializable studID);
	
T load(Serializable studID);
	
	List<T> getAll();
	
	void update(T t);
	
	void delete(T t);
	
	void deleteById(Serializable studID);
	
	void deleteAll();
	
	long count();
	
	boolean exists(Serializable studID);
	

}
