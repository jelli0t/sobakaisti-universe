/**
 * 
 */
package org.sobakaisti.core.service;

import java.util.List;

import org.sobakaisti.core.model.Category;

/**
 * @author jelles
 *
 */
public interface CategoryService {

	/**
	 * Dohvata sve kategorije
	 * */
	public List<Category> findAllCategories();
}
