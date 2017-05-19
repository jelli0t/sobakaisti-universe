package org.sobakaisti.core.dao.template;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

/**
 * @author jelles
 *
 */
@Repository
@Transactional
public class EntityDispatcherImpl<T> implements EntityDispatcher<T> {

	protected final Class<T> entity;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/* default constructor */
	@SuppressWarnings("unchecked")
	public EntityDispatcherImpl(){
		this.entity = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), EntityDispatcherImpl.class);
	}
	
	@Override
	public T find(T t) {
		return null;
	}

	@Override
	public T findById(int id) {
		try{			
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			T t = (T) session.get(entity, id);			
			return t;
		}catch (Exception e) {
			return null;
		}		
	}

	@Override
	public boolean delete(T t) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(t);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		final String HQL = "from :entity";
		try {
			Session session = sessionFactory.getCurrentSession();
			List<T> ts = session.createQuery(HQL).setParameter("entity", entity).list();
			return ts;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllActive() {
		final String HQL = "from :entity where active = 1";
		try {
			Session session = sessionFactory.getCurrentSession();
			List<T> ts = session.createQuery(HQL).setParameter("entity", entity).list();
			return ts;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public T save(T t) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Serializable id = session.save(t);
		} catch (HibernateException he) {
			return null;
		}	
		return t;
	}

	@Override
	public boolean deleteById(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			T t = (T) session.load(entity, id);
			if(t != null){
				session.delete(t);
				return true;
			}else {
				return false;
			}
		} catch (HibernateException he) {
			return false;
		}
	}
}