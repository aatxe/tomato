package org.tomato.client.core;

import java.util.ArrayList;
import org.tomato.tools.Pair;

/**
 * An object that can quickly, and easily be saved to a database.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class SaveObject {
	private String tableName; 
	private ArrayList<Pair<String, Object>> saveDict;
	
	/**
	 * Creates a new <code>SaveObject</code>.
	 * @param tableName the name of the database table
	 */
	public SaveObject(String tableName) {
		this.tableName = tableName;
		saveDict = new ArrayList<Pair<String, Object>>();
	}
	
	/**
	 * Creates a new <code>SaveObject</code> with the supplied entries.
	 * @param tableName the name of the database table
	 * @param entries the entries to add to the save object.
	 */
	public SaveObject(String tableName, Pair<String, Object>[] entries) {
		this.tableName = tableName;
		saveDict = new ArrayList<Pair<String, Object>>(entries.length);
		for (Pair<String, Object> entry : entries) {
			saveDict.add(entry);
		}
	}
	

	/**
	 * Creates a new <code>SaveObject</code> with the supplied entries.
	 * @param tableName the name of the database table
	 * @param entries the entries to add to the save object.
	 */
	public SaveObject(String tableName, ArrayList<Pair<String, Object>> entries) {
		this.tableName = tableName;
		saveDict = entries;
	}
	
	/**
	 * Gets the first value corresponding to the passed key from this object.
	 * @param key the key to search for
	 * @return the first value corresponding to the passed key
	 */
	public Object get(String key) {
		for (Pair<String, Object> entry : saveDict) {
			if (entry.getLeft() == key) {
				return entry.getRight();
			}
		}
		return null;
	}
	
	/**
	 * Gets all values corresponding to the passed key from this object.
	 * @param key the key to search for
	 * @return all values corresponding to the passed key
	 */
	public ArrayList<Object> getAll(String key) {
		ArrayList<Object> ret = new ArrayList<Object>();
		for (Pair<String, Object> entry : saveDict) {
			if (entry.getLeft() == key) {
				ret.add(entry.getRight());
			}
		}
		return ((ret.isEmpty()) ? null : ret);
	}
	
	/**
	 * Adds an entry to this object.
	 * @param entry a key value <code>Pair</code> to be added
	 */
	public void add(Pair<String, Object> entry) {
		saveDict.add(entry);
	}
	
	/**
	 * Adds an entry to this object.
	 * @param key the key of the entry
	 * @param value the value of the entry
	 */
	public void add(String key, Object value) {
		this.add(new Pair<String, Object>(key, value));
	}
	
	/**
	 * Gets the name of the database table for this object.
	 * @return the name of the database table
	 */
	public String getTableName() {
		return this.tableName;
	}
	
	/**
	 * Gets the number of entries to save for this object.
	 * @return the number of entries to save
	 */
	public int size() {
		return saveDict.size();
	}
}
