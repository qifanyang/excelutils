package com.tobe.excelutils.code;

/**
 * 字段描述
 * 
 */
public class Field {
	/** 字段名,Bean字段和数据库字段都是这个 */
	private String name;
	/** 字段数据类型 */
	private String javaClassName;
	/** mysql数据库字段类型 */
	private String dbClassName;
	/** 在excel中的位置 */
	private int cell;
	/** 表注释 */
	private String explain;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	public String getDbClassName() {
		return dbClassName;
	}

	public void setDbClassName(String dbClassName) {
		this.dbClassName = dbClassName;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
