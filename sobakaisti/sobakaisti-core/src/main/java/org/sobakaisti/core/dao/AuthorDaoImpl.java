package org.sobakaisti.core.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sobakaisti.core.dao.template.EntityDispatcherImpl;
import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jelles
 *
 */
@Repository
public class AuthorDaoImpl extends EntityDispatcherImpl<Author> implements AuthorDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean authorHasArticles(Author author) {
		final String HQL = "select count(*) from Article where author = :author";
		Session session = sessionFactory.getCurrentSession();
		boolean hasArticles = false;
		try {
			hasArticles = (long) session.createQuery(HQL).setParameter("author", author).uniqueResult() > 0;
		} catch (Exception e) {
			hasArticles = false;
		}	
		return hasArticles;
	}	

	@Override
	public Author findDefaultAuthor() {
		final String HQL = "from Author where lastName in (:lastName)";
		try{
			Session session = sessionFactory.getCurrentSession();
			Author author = (Author) session.createQuery(HQL).setParameter("lastName", AuthorService.SOBAKOV).uniqueResult();
			return author;
		}catch (Exception e) {
			return null;
		}
	}
		
}