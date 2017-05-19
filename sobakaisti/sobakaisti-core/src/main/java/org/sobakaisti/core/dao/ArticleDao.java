package org.sobakaisti.core.dao;

import java.util.List;

import org.sobakaisti.core.dao.template.EntityDispatcher;
import org.sobakaisti.core.model.Article;
import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.model.Tag;

/**
 * @author jelles
 * Klasa za pristup clancima u bazi
 */
public interface ArticleDao extends EntityDispatcher<Article>{
	
	/**
	 * Metoda pronalazi i vraca clanak po Naslovu na zadatom jeziku
	 * @param title		Naslov clanka
	 * @param lang		Kod jezika na kom je clanak
	 * */
	public Article getArticleBySlugTitle(String slug, String lang);
	
	/**
	 * Metoda vraca clanak na onovu prosledjenog lang koda.
	 * @param langCode	Kod jezika (rs, en, it, fr...)
	 * */
	public String getintroArticleByLanguage(String langCode);
	
	/**
	 * Metoda cuva clanak
	 * @param clanka
	 * @return boolean
	 * */
	public boolean saveArticle(Article article);
	
	
	public List<Article> getArticlesSortedByDate();
		
	/**
	 *  metoda menja status clanka active 1/0
	 *  public / draft
	 * */
	public int switchArticleStatus(int articleId);
	
	/**
	 * metoda koja pronalazi sve tagove na osnovu prosledjene fraze
	 * */
	public List<Tag> searchTagsByPhrase(String phrase);
	
	/**
	 * metoda menja autora clanaka na novog
	 * @param previousAuthor
	 * @param newAuthor
	 * */
	public boolean switchArticlesAuthor(Author previousAuthor, Author newAuthor);
}