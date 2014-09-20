package com.tobe.excelutils;

import java.util.List;


/**
 * 返回多Sheet查询结果的Handler,返回的结果为list，list里面每个元素为BeanList
 *@author TOBE
 *
 */
public interface MultiSheetListSetHandler{

	public List<?> handle(MultiSheetResultSet rs) throws Exception;

}
