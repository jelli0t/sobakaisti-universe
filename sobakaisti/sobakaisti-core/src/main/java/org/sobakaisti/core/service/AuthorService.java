/**
 * 
 */
package org.sobakaisti.core.service;

import java.util.List;

import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.model.ResponseInfo;

/**
 * @author jelles
 * interfejs za prisup metodama koje rade sa autorima
 */
public interface AuthorService {
	
	public static final String[] SOBAKOV = {"Sobakov", "Собаков"};
	
	/**
	 * dohvata sve aktivne autore
	 * @return lista aktivnih autora
	 * */
	public List<Author> findAllActiveAuthors();

	/**
	 * cuva i vraca sacuvanog autora
	 * @param Author
	 * */
	public Author saveAndReturnAuthor(Author author);

	/**
	 * brise autora na osnovu njegovog ID-a
	 * @param int id
	 * */
	public ResponseInfo deleteAuthorById(int id);
}
