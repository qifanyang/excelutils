package com.tobe.excelutils.code;

/**
 * 字段描述
 * 
 */
public class Field {
	/**字段名,Bean字段和数据库字段都是这个*/
	private String name;
	
	private String className;
	/**字段数据类型*/
	private String javaClassName;
	/**mysql数据库字段类型*/
	private String dbClassName;
	/**在excel中的位置*/
	private int cell;
	/**表注释*/
	private String explain;
	
}
