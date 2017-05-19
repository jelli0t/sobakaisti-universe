package org.sobakaisti.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.transaction.Transactional;

import org.sobakaisti.core.dao.ArticleDao;
import org.sobakaisti.core.dao.AuthorDao;
import org.sobakaisti.core.dao.CategoryDao;
import org.sobakaisti.core.model.Article;
import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.model.Category;
import org.sobakaisti.core.model.ResponseInfo;
import org.sobakaisti.core.model.Tag;
import org.sobakaisti.core.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jelles
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private AuthorDao authorDao;
	@Autowired
	private CategoryDao categoryDao;
	
	private int charsPerRow;
	private int rowsPerPage;
	private int charsToFill;
	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
	
	@Override
	public List<String> getRowsFromArticleWithDimension(int width, int height, double charWidth, String lang) {
		charsPerRow = (int) Math.floor(width / charWidth);
		rowsPerPage = (int) Math.floor(height / LINE_HEIGHT);
		String content = articleDao.getintroArticleByLanguage(lang);
		int length = content.length();
		charsToFill = (int) (charsPerRow - Math.ceil(length % charsPerRow));
		
		System.out.println("Params: Dimensions: "+width+"x"+height+"; Char width: "+charWidth+"; karaktera za popunjavanje: "+charsToFill
				+ " Chars x Rows: "+charsPerRow+"x"+rowsPerPage);
		
		List<String> row = new ArrayList<>(rowsPerPage);
		
		if(!content.equals("")){
			for(int i=0, j=0; i<rowsPerPage; i++){				
				if(j<length-charsPerRow){					
					row.add(content.substring(j, j+charsPerRow));
					j+=charsPerRow;
				}else if(j < length){
					String ending = content.substring(j)+" "+content.substring(0, charsToFill-1);
					row.add(ending);
					j = 0;
				}
			}
		}
		return row;
	}

	@Override
	public Article getArticleBySlug(String slug, String lang) {
		Article article = (Article) articleDao.getArticleBySlugTitle(slug, lang);
		return article;
	}

	@Override
	public ResponseInfo saveArticle(Article article) {			
		Author author = authorDao.findById(article.getAuthor().getId());
		article.setCategories(prepareCategoriesForArticle(article));		
		article.setAuthor(author);
		article.setPostDate(Calendar.getInstance());		
		article = articleDao.save(article);
		
		if(article != null){
			return new ResponseInfo(true, resourceBundle.getString("success.author.save"));
		}else{
			return new ResponseInfo(false, resourceBundle.getString("failure.author.save"));
		}		
	}

	@Override
	public List<Article> getArticlesOrderByDate() {
		System.out.println("metoda: getArticlesOrderByDate() ArticleServiceImpl");
		return articleDao.getArticlesSortedByDate();
	}
	
	/**
	 * metoda priprema (dohvata) kategorije za prosledjeni clanak
	 * */
	private List<Category> prepareCategoriesForArticle(Article article){
		List<Category> categories = null;
		if(article.getCategories() != null){
			final int size = article.getCategories().size();
			categories = new ArrayList<Category>(size);
			List<Integer> ids = new ArrayList<Integer>(size);			
			
			for(Category c : article.getCategories()){
				ids.add(c.getId());				
			}
			System.out.println("dodao ids u listu, size: "+ids.size());	
			categories = categoryDao.getAllCategoriesByIds(ids);	
		}
		return categories;
	}

	@Override
	public ResponseInfo deleteArticleById(int id) {		
		boolean deleted = articleDao.deleteById(id);
		if(deleted){
			return new ResponseInfo(true, resourceBundle.getString("success.article.delete"));
		}else {
			return new ResponseInfo(false, resourceBundle.getString("failure.article.delete"));
		}
	}

	@Override
	public ResponseInfo switchArticleStatus(int articleId) {
		int articleStatus = articleDao.switchArticleStatus(articleId);		
		if(articleStatus == ACTIVE){
			return new ResponseInfo(true, resourceBundle.getString("success.article.status.activated"));
		}else if(articleStatus == INACTIVE) {
			return new ResponseInfo(true, resourceBundle.getString("success.article.status.deactivated"));
		}
		return new ResponseInfo(false, resourceBundle.getString("failure.article.status.changed"));
	}

	@Override
	public List<Tag> getTagSerachResult(String phrase) {
		return articleDao.searchTagsByPhrase(phrase);
	}
}
