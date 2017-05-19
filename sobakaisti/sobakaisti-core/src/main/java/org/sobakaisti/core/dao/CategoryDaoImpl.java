package org.sobakaisti.core.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sobakaisti.core.dao.template.EntityDispatcherImpl;
import org.sobakaisti.core.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author jelles
 *
 */
@Repository
@Transactional
public class CategoryDaoImpl extends EntityDispatcherImpl<Category> implements CategoryDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Category> getAllCategoriesByIds(List<Integer> ids) {
		Session session = sessionFactory.getCurrentSession();
		String HQL = "from Category c where c.id in (:ids)";
		try{
			@SuppressWarnings("unchecked")
			List<Category> categories = (List<Category>) session.createQuery(HQL).setParameterList("ids", ids).list();
			System.out.println("Dohvatio sam categorija size: "+categories.size());
			return categories;
		}catch (HibernateException he) {
			System.err.println("Greska pri dohvatanju kategorija: "+he.getMessage());
			return null;
		}		
	}

}
