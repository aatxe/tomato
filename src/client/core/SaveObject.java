package client.core;

import java.util.ArrayList;
import tools.Pair;

public class SaveObject {
	private ArrayList<Pair<String, Object>> saveDict;
	
	public SaveObject() {
		saveDict = new ArrayList<Pair<String, Object>>();
	}
	
	public SaveObject(Pair<String, Object>[] entries) {
		saveDict = new ArrayList<Pair<String, Object>>(entries.length);
		for (Pair<String, Object> entry : entries) {
			saveDict.add(entry);
		}
	}
	
	public SaveObject(ArrayList<Pair<String, Object>> entries) {
		saveDict = entries;
	}
	
	public Object get(String key) {
		for (Pair<String, Object> entry : saveDict) {
			if (entry.getLeft() == key) {
				return entry.getRight();
			}
		}
		return null;
	}
	
	public ArrayList<Object> getAll(String key) {
		ArrayList<Object> ret = new ArrayList<Object>();
		for (Pair<String, Object> entry : saveDict) {
			if (entry.getLeft() == key) {
				ret.add(entry.getRight());
			}
		}
		return ((ret.isEmpty()) ? null : ret);
	}
	
	public void add(Pair<String, Object> entry) {
		saveDict.add(entry);
	}
	
	public void add(String key, Object value) {
		this.add(new Pair<String, Object>(key, value));
	}
}
