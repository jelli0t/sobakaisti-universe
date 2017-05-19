package org.sobakaisti.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sobakaisti.core.dao.template.EntityDispatcherImpl;
import org.sobakaisti.core.model.Article;
import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ArticleDaoImpl extends EntityDispatcherImpl<Article> implements ArticleDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	
	//TODO izbaci IntroArticle
	@Override
	@Transactional
	public String getintroArticleByLanguage(String langCode) {
//		String HQL = "FROM IntroArticle ia WHERE ia.lang=:langCode";
//		Session session = sessionFactory.getCurrentSession();
//		IntroArticle article = (IntroArticle) session.createQuery(HQL).setString("langCode", langCode).uniqueResult();
//		String content = article.getContent();
//		if(content != null){
//			return content;
//		}else{
//			return "";
//		}
		return null;
	}

	@Override
	@Transactional
	public Article getArticleBySlugTitle(String slug, String lang) {
		String HQL = "FROM Article WHERE slug=:slug and lang=:langCode";
		try{
			Session session = sessionFactory.getCurrentSession();
			Article article = (Article) session.createQuery(HQL)
					.setString("slug",slug)
					.setString("langCode", lang).uniqueResult();
			return article;
		}catch (HibernateException he) {
			// TODO: handle exception
			return null;
		}		
	}

	@Override
	@Transactional
	public boolean saveArticle(Article article) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.save(article);
			return true;
		}catch (HibernateError he) {
			return false;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Article> getArticlesSortedByDate() {
		String HQL = "FROM Article a WHERE a.postDate is not null order by date(a.postDate) desc";
		Session session = sessionFactory.getCurrentSession();
		List<Article> articles = session.createQuery(HQL).list();
		return articles;
	}

	@Override
	@Transactional
	public int switchArticleStatus(int articleId) {
		Session session = sessionFactory.getCurrentSession();
		Article article = (Article) session.load(Article.class, articleId);
		if(article != null){
			int active = article.getActive();
			switch (active) {
			case 0:
				article.setActive(1);				
				break;
			case 1:
				article.setActive(0);
				break;
			default:
				return -1;
			}
			session.update("article", article);
			return article.getActive();
		}
		return -1;
	}

	@Override
	@Transactional
	public List<Tag> searchTagsByPhrase(String phrase) {
		Session session = sessionFactory.getCurrentSession();
		final String HQL = "from Tag t where t.tag like :phrase";
		List<Tag> tags = new ArrayList<Tag>();
		try {
			tags = session.createQuery(HQL).setString("phrase", phrase+"%").list();
			return tags;
		} catch (Exception e) {
			return tags;
		}	
	}
	
	@Override
	@Transactional
	public boolean switchArticlesAuthor(Author previousAuthor, Author newAuthor) {
		final String HQL = "update Article ar set ar.author = :newAuthor where ar.author = :previousAuthor";
		try {
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unused")
			int updated = session.createQuery(HQL).setParameter("newAuthor", newAuthor).setParameter("previousAuthor", previousAuthor).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
}
