/**
 * 
 */
package org.sobakaisti.core.dao;

import java.util.List;

import org.sobakaisti.core.model.Tag;

/**
 * @author jelles
 * klasa za pristup Tag-ovima u bazi i manipulaciju istima
 */
public interface TagDao {

	/**
	 * pronalazi listu Tagova na osnovu dela reci kojom pocinju
	 * @param String - nagovestaj taga
	 * */
	public List<Tag> findTagsByHint(String hint);
}
