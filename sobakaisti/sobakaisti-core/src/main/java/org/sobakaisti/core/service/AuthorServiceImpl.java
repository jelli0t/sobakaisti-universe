/**
 * 
 */
package org.sobakaisti.core.service;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.sobakaisti.core.dao.ArticleDao;
import org.sobakaisti.core.dao.AuthorDao;
import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.model.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jelles
 * klasa za rad sa autorima 
 */
@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorDao authorDao;
	@Autowired
	private ArticleDao articleDao;
	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
	
	@Override
	public List<Author> findAllActiveAuthors() {
		return authorDao.findAllActive();
	}

	@Override
	public Author saveAndReturnAuthor(Author author) {		
		return authorDao.save(author);
	}

	@Override
	public ResponseInfo deleteAuthorById(int id) {
		Author author = authorDao.findById(id);
		if(author != null){
			/* ako autor ima clanke prebaci ih na bota */
			boolean transfered = transferArticlesToDefaultAuthor(author);
			if(!transfered){
				return new ResponseInfo(false, resourceBundle.getString("failure.author.switch.notFound"));
			}
			
			boolean deleted = authorDao.delete(author);
			if(deleted){
				return new ResponseInfo(true, resourceBundle.getString("success.author.delete"));
			} else {
				return new ResponseInfo(false, resourceBundle.getString("failure.author.delete"));
			}
		}else {
			return new ResponseInfo(false, resourceBundle.getString("failure.author.delete"));
		}	
	}

	/**
	 * prebacuje autora clanka sa aktuelnog na podrazumevanog (bota)
	 * */
	private boolean transferArticlesToDefaultAuthor(Author author){
		if(authorDao.authorHasArticles(author)){
			/* dohvatam Sobakova koa defaultnog autora*/
			Author newAuthor = authorDao.findDefaultAuthor();
			if(newAuthor == null) return false;
			
			/* radim prebacivanje autora clanaka na defaultnog */
			return articleDao.switchArticlesAuthor(author, newAuthor);
		}
		return false;
	}
}
