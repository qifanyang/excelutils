package com.tobe.excelutils;


public interface ExcelSetHandler<T> {

	T handle(ExcelResultSet rs) throws Exception;
}
