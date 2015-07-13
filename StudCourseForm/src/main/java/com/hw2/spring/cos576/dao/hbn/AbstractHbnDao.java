package com.hw2.spring.cos576.dao.hbn;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.ReflectionUtils;

import com.hw2.spring.cos576.dao.Dao;

public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {
	@Inject private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
	    if (domainClass == null) {
	    	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	        this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
	    }
	    return domainClass;
	}
	
	private String getDomainClassName() { return getDomainClass().getName(); }
	
	
	@Override
	public void create(T t) {
		
		// If there's a setDateCreated() method, then set the date.
		Method method = ReflectionUtils.findMethod(
				getDomainClass(), "setDateCreated", new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, new Date());
			} catch (Exception e) {
				// Ignore any exception here; simply abort the setDate() attempt
			}
		}
		
		getSession().save(t);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable studID) {
		return (T) getSession().get(getDomainClass(), studID);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public T load(Serializable studID) {
		return (T) getSession().load(getDomainClass(), studID);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession()
			.createQuery("from " + getDomainClassName())
			.list();
	}
	
	
	@Override
	public void update(T t) { getSession().update(t); }
	
	
	@Override
	public void delete(T t) { getSession().delete(t); }
	
	
	@Override
	public void deleteById(Serializable studID) { delete(load(studID)); }
	
	
	@Override
	public void deleteAll() {
		getSession()
			.createQuery("delete " + getDomainClassName())
			.executeUpdate();
	}
	
	
	@Override
	public long count() {
		return (Long) getSession()
			.createQuery("select count(*) from " + getDomainClassName())
			.uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see com.springinpractice.dao.Dao#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(Serializable studID) { return (get(studID) != null); }
}