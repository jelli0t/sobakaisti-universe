package org.sobakaisti.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sobakaisti.core.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findTagsByHint(String hint) {
		Session session = sessionFactory.getCurrentSession();
		final String HQL = "from Tag t where t.tag like :hint";
		List<Tag> tags = new ArrayList<Tag>();
		try {
			tags = session.createQuery(HQL).setString("hint", hint+"%").list();
			return tags;
		} catch (Exception e) {
			return tags;
		}	
	}

}
