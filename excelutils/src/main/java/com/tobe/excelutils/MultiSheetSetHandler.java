package com.tobe.excelutils;


/**
 * 返回多Sheet查询结果的Handler
 *@author TOBE
 *
 */
public interface MultiSheetSetHandler<T>{

	public T handle(MultiSheetResultSet rs) throws Exception;

}
