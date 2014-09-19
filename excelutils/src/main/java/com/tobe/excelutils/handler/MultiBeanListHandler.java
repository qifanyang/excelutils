package com.tobe.excelutils.handler;

import java.util.ArrayList;
import java.util.List;

import com.tobe.excelutils.MultiSheetResultSet;
import com.tobe.excelutils.MultiSheetSetHandler;
import com.tobe.excelutils.RowProcessor;

public class MultiBeanListHandler<T> implements MultiSheetSetHandler<List<List<T>>> {

	/**名字要和excel的sheet对应, 必须一样, 可以忽略大小写*/
	private final List<Class<T>> typeList;
	
	private final RowProcessor convert;
	
	public MultiBeanListHandler(List<Class<T>> typeList){
		this.typeList = typeList;
		this.convert = ArrayHandler.ROW_PROCESSOR;
	}
	
	@Override
	public List<List<T>> handle(MultiSheetResultSet rs) throws Exception {
		ArrayList<List<T>> list = new ArrayList<List<T>>();
		for(Class<T> cls : typeList){
			List<T> beanList = this.convert.toBeanList(rs.getMultiResultSet().get(cls.getSimpleName().toLowerCase()), cls);
			list.add(beanList);
		}
		return list;
	}

}
