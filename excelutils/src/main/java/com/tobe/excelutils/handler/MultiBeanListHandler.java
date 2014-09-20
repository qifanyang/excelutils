package com.tobe.excelutils.handler;

import java.util.ArrayList;
import java.util.List;

import com.tobe.excelutils.MultiSheetResultSet;
import com.tobe.excelutils.MultiSheetListSetHandler;
import com.tobe.excelutils.RowProcessor;

public class MultiBeanListHandler implements MultiSheetListSetHandler{

	/**名字要和excel的sheet对应, 必须一样, 可以忽略大小写*/
	private final List<Class<?>> typeList;
	
	private final RowProcessor convert;
	
	public MultiBeanListHandler(List<Class<?>> typeList){
		this.typeList = typeList;
		this.convert = ArrayHandler.ROW_PROCESSOR;
	}
	
	@Override
	public List<?> handle(MultiSheetResultSet rs) throws Exception {
		List<List<?>> list = new ArrayList<List<?>>();
		for(Class<?> cls : typeList){
			List<?> beanList = this.convert.toBeanList(rs.getMultiResultSet().get(cls.getSimpleName().toLowerCase()), cls);
			list.add(beanList);
		}
		return list;
	}

}
