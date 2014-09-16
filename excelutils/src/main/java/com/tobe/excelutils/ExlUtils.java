package com.tobe.excelutils;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

public class ExlUtils {

	
	/**
	 *  where子句表示值等于value才放入结果集
	 * @param titles
	 * @param cell
	 * @param titleIndex
	 * @return
	 */
	public static boolean rowFilter(List<String> titles, Cell cell, int titleIndex){
//		if(wheres.size() == 0)return true;//没有where字段,全部显示
		
		
//		//这里一行会调用多次,只有当匹配的字段值相等才把该行记录加入结果集中
//		String t = titles.get(titleIndex).toLowerCase();
//		Object object = wheres.get(t);//name = kkkkk
//		if(object != null){
//			 if(cell.toString().equals(object.toString())){//B5 
//				 return false;
//			 }
//		}
		
		//没有在where中也添加到结果集中
		return true;
		
	}
}
