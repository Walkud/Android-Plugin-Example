package com.example.pluginstudy.database;

import com.j256.ormlite.field.DatabaseField;

/**
 * 
 * @author hi
 *
 */
public class DataMap {
	/**
	 * id 编号
	 */
	@DatabaseField(generatedId = true)
	int id;

	@DatabaseField
	String key;

	@DatabaseField
	String value;

	@DatabaseField
	long time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
