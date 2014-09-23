package com.tobe.excelutils.code;

import java.util.ArrayList;

/** 一个sheet描述 */
public class SheetBean {

	// excel文件名
	private String filename;
	// sheet名
	private String name;
	// 主键名称
	private Field key;
	// 主键组合列
	private ArrayList<Field> keys = new ArrayList<Field>();
	// 字段信息表
	private ArrayList<Field> fields = new ArrayList<Field>();
	// sheet中的数据,这里全部存为String,用freemark生成数据库插入语句
	private String[][] datas;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}

	public ArrayList<Field> getKeys() {
		return keys;
	}

	public void setKeys(ArrayList<Field> keys) {
		this.keys = keys;
	}

	public Field getKey() {
		return key;
	}

	public void setKey(Field key) {
		this.key = key;
	}

}
