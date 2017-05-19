/**
 * 
 */
package org.sobakaisti.core.dao.template;

import java.util.List;

/**
 * @author jelles
 * Univerzalna klasa za bazicnu komunikaciju sa bazom
 */
public interface EntityDispatcher<T> {
	
	/**
	 * Osnovna metoda za dohvatanje objekta
	 * @param T genericki objekat
	 * */
	public T find(T t);
	
	/**
	 * Metoda za dohvatanje entiteta po njegovom ID
	 * @param id
	 * */
	public T findById(int id);
	
	/**
	 * Pronalazi sve objekte odgovarajuceg entiteta
	 * */
	public List<T> findAll();
	
	/**
	 * Pronalazi sve aktivne (active=1) objekte odgovarajuceg entiteta 
	 * */
	public List<T> findAllActive();

	/**
	 * Brise prosledjeni objekat i vraca stanje o uspesnosti operacije
	 * @param t objekat za brisanje
	 * */
	public boolean delete(T t);
	
	/**
	 * Dohvata entitet preko ID a onda brise isti objekat
	 * @param id
	 * */
	public boolean deleteById(int id);
	
	/**
	 * Metoda cuva objekat i vraca isti u slucaju uspesne akcije
	 * @param t
	 * */
	public T save(T t);
}
