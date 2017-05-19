package org.sobakaisti.core.service;

import java.util.List;

import org.sobakaisti.core.model.Article;
import org.sobakaisti.core.model.ResponseInfo;
import org.sobakaisti.core.model.Tag;

public interface ArticleService {

	public static final int LINE_HEIGHT = 16; 			// (px) visina jednog reda definisano CSS-om 
	public static final double FONT_WIDTH = 7.0;		// (px) sirina jednog slova
	public static final int ACTIVE = 1;
	public static final int INACTIVE = 0;
	
	public static enum Manifesto {
		manifest, manifesto
	}
	
	public List<String> getRowsFromArticleWithDimension(int width, int height, double charWidth, String lang);
	
	public Article getArticleBySlug(String slug, String lang);
	
	/**
	 * Metoda cuva novi 
	 * */
	public ResponseInfo saveArticle(Article article);
	
	public List<Article> getArticlesOrderByDate();
	
	/**
	 * Brise clanak na osnovu ID-a clanka
	 * @param id
	 * */
	public ResponseInfo deleteArticleById(int id);
	
	/**
	 * menja status clanka 1/0; publikovan/draft
	 * @return ResponseInfo
	 * */
	public ResponseInfo switchArticleStatus(int articleId);
	
	public List<Tag> getTagSerachResult(String phrase);
}
