package org.sobakaisti.core.dao;

import org.sobakaisti.core.dao.template.EntityDispatcher;
import org.sobakaisti.core.model.Author;

/**
 * @author jelles
 *
 */
public interface AuthorDao extends EntityDispatcher<Author>{	
	/**
	 * dohvata podrazumevanog autora (bota)
	 * bot medju sobakistima je Sobakov
	 * @return author
	 * */
	public Author findDefaultAuthor();
	
	/**
	 * Metoda proverava da li autor poseduje clanke
	 * @param author
	 * */	
	public boolean authorHasArticles(Author author);
		
}