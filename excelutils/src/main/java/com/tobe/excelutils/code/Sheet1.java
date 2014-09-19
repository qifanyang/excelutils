package com.tobe.excelutils.code;

import java.util.Map;
import java.util.List;

public class Sheet1 {

	/** 名字 */
	private String name;

	private int age;

	private List<String> list;

	private Map<String, String> map;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<String> getList() {
		return list;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}

}
