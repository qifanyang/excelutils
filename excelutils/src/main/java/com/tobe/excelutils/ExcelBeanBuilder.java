package com.tobe.excelutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.ImmutableBiMap.Builder;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

/**
 * 根据excel文件,生成title的Bean
 * 
 * 在将excel数据插入数据库,可以用ASM生成class,但对于需要使用excel配置就需要生成对应的bean
 * 
 * @author TOBE
 * 
 */
public class ExcelBeanBuilder {

	private int titleIndex;// 标题栏在excel中的行

	private int sheetIndex;

	private String packageName;

	public ExcelBeanBuilder() {
		this(1, 0, null);
	}

	public ExcelBeanBuilder(int titleIndex, int sheetIndex, String packageName) {
		this.titleIndex = titleIndex;
		this.sheetIndex = sheetIndex;
		this.packageName = packageName;
	}

	public int getTitleIndex() {
		return titleIndex;
	}

	/**
	 * excel标题栏,默认为第一行,对应bean字段
	 * @param titleIndex
	 */
	public void setTitleIndex(int titleIndex) {
		this.titleIndex = titleIndex;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	/**
	 * 要解析的excel的sheet,默认第0个
	 * @param sheetIndex
	 */
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getPackageName() {
		return packageName;
	}

	/**
	 * 生成类的包名,默认为空,没有包
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> parse(String path) {
		List<String> titles = new ArrayList<String>();
		InputStream dataSource = ExlUtils.getDataSource(path);
		try {

			Workbook wb = new XSSFWorkbook(dataSource);
			String sheetName = wb.getSheetName(sheetIndex);
			Sheet sheet = wb.getSheetAt(sheetIndex);

			titles.add(sheetName);// 0为sheetName
			Iterator<Row> rowIterator = sheet.rowIterator();

			int rowCount = 1;
			while (rowIterator.hasNext()) {
				if (rowCount++ == titleIndex) {// 该行为标题栏
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String title = cell.toString().trim();
						titles.add(title);
					}
					break;// title已将找到,跳出
				} else {
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dataSource) {
				try {
					dataSource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return titles;
	}

	/**
	 * list的第一字段作为类名,其它为字段
	 * 
	 * @param fields
	 * @return
	 */
	public String buildCode(List<String> fields) {
		StringBuilder sb = new StringBuilder();

		if(null != packageName && packageName.length() > 0){
			sb.append("package ").append(packageName).append("\n");
		}
		
		sb.append("public class ").append(toUpper(fields.get(0), 0)).append("{ \n");
		
		//字段
		for(int i = 1, size = fields.size(); i < size; i++){
			sb.append("private String ").append(fields.get(i)).append(";\n\n");
		}
		
		//get set 方法
		for(int i = 1, size = fields.size(); i < size; i++){
			String f = fields.get(i);
			
			sb.append("private void ")
			.append("set").append(toUpper(f, 0)).append("(String ").append(f).append("){\n")//方法名
			.append("\tthis.").append(f).append(" = ").append(f).append(";")//方法体
			.append("\n}\n\n");
			
			sb.append("private String ").append("get").append(toUpper(f, 0)).append("(").append("){\n")//方法名
			.append("\treturn ").append(f).append(";")//方法体
			.append("\n}\n\n");
		}
		
		sb.append("}");
		
		return sb.toString();

	}

	public String buildCode(String path) {
		return buildCode(parse(path));
	}

	public static String toUpper(String s, int index){
		if(index == 0){
			//首字母大写
			String upperCase = s.substring(0, 1).toUpperCase();
			String suffix = s.substring(1);
			return upperCase + suffix;
		}else{
			String pre = s.substring(0, index);
			String upperCase = s.substring(index, index + 1).toUpperCase();
			String suffix = s.substring(index + 1);
			return pre + upperCase + suffix;
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(toUpper("leifds", 0));
		System.out.println(toUpper("leifds", 3));
		
		ExcelBeanBuilder beanBuilder = new ExcelBeanBuilder();
		beanBuilder.setTitleIndex(1);
		System.out.println(beanBuilder.buildCode("/商业活动.xlsx"));
	}
}
