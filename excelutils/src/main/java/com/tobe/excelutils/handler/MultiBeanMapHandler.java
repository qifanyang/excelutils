package com.tobe.excelutils.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tobe.excelutils.MultiSheetMapSetHandler;
import com.tobe.excelutils.MultiSheetResultSet;
import com.tobe.excelutils.RowProcessor;

public class MultiBeanMapHandler implements MultiSheetMapSetHandler {

	/**名字要和excel的sheet对应, 必须一样, 可以忽略大小写*/
	private final List<Class<?>> typeList;
	
	private final RowProcessor convert;
	
	public MultiBeanMapHandler(List<Class<?>> typeList){
		this.typeList = typeList;
		this.convert = ArrayHandler.ROW_PROCESSOR;
	}
	
	public List<?> handle1(MultiSheetResultSet rs) throws Exception {
		List<List<?>> list = new ArrayList<List<?>>();
		for(Class<?> cls : typeList){
			List<?> beanList = this.convert.toBeanList(rs.getMultiResultSet().get(cls.getSimpleName().toLowerCase()), cls);
			list.add(beanList);
		}
		return list;
	}

	@Override
	public Map<String, Object> handle(MultiSheetResultSet rs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		for(Class<?> cls : typeList){
//			this.convert.toMap(rs.getMultiResultSet().get(cls.getSimpleName().toLowerCase()));
			List<?> beanList = this.convert.toBeanList(rs.getMultiResultSet().get(cls.getSimpleName().toLowerCase()), cls);
			map.put(cls.getSimpleName().toLowerCase(), beanList);
		}
		return map;
	}

	
}
