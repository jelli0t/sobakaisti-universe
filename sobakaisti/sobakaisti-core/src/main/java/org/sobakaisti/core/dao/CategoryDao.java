package org.sobakaisti.core.dao;

import java.util.List;

import org.sobakaisti.core.dao.template.EntityDispatcher;
import org.sobakaisti.core.model.Category;

/**
 * @author jelles
 * Kalsa za pristup podacima o kategorijama clanaka
 */
public interface CategoryDao extends EntityDispatcher<Category>{
	
	/**
	 * Metoda koja dohvate kategorije clanaka za navedene id-ijeve
	 * @param niz id-eva koje trazimo
	 * @return lista kategorija
	 * */
	public List<Category> getAllCategoriesByIds(List<Integer> ids);
}
