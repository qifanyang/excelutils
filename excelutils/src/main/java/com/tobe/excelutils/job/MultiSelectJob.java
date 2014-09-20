package com.tobe.excelutils.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.MultiSheetResultSet;
import com.tobe.excelutils.SelectSQL;

/**
 * 执行多sheet查询，返回{@link MultiSheetResultSet}
 * @author TOBE
 *
 */
public class MultiSelectJob implements IJob<MultiSheetResultSet> {

	private InputStream dataSource;
	/**
	 *支持多select. list的索引位置和sheet索引对应 
	 */
	private List<SelectSQL> sqlList;
	
	/**
	 *支持多select. list的索引位置和sheet索引对应 
	 */
	public MultiSelectJob(InputStream dataSource, List<SelectSQL> sqlList) {
		this.dataSource = dataSource;
		this.sqlList = sqlList;
	}
	
	/**
	 * 所有的sheet用同一个selectSql
	 * @param dataSource
	 * @param sql
	 */
	public MultiSelectJob(InputStream dataSource, SelectSQL sql) {
		this.dataSource = dataSource;
		this.sqlList = new ArrayList<SelectSQL>();
		this.sqlList.add(sql);
	}
	
	@Override
	public MultiSheetResultSet excute() {
		MultiSheetResultSet rs = new MultiSheetResultSet();
		
		try {
			XSSFWorkbook wb = new XSSFWorkbook(dataSource);
			int sheetNum = wb.getNumberOfSheets();
			for(int i = 0; i < sheetNum; i++){
				SelectSQL sql = null;
				if(sqlList.size() > i){
					sql = sqlList.get(i);
				}else{
					sql = sqlList.get(0);
				}
				SelectJob selectJob = new SelectJob(wb, sql, i);
				ExcelResultSet excelResultSet = selectJob.excute();
				String sheetName = wb.getSheetName(i);
				rs.getMultiResultSet().put(sheetName.toLowerCase(), excelResultSet);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
