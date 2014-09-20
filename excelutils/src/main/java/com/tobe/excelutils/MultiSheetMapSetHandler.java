package com.tobe.excelutils;

import java.util.Map;

/**
 * 返回多Sheet查询结果的Handler,返回的结果为map，map的key为sheet名字，value为BeanList
 * 
 *@author TOBE
 *
 */
public interface MultiSheetMapSetHandler {
	
	public Map<String, Object> handle(MultiSheetResultSet rs) throws Exception;
}
