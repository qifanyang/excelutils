package com.tobe.excelutils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tobe.excelutils.job.IJob;
import com.tobe.excelutils.job.MultiSelectJob;
import com.tobe.excelutils.job.SelectJob;

public class ExcelRunner {

	private InputStream dataSource;
	
	private ExcelResultSet rs;
	
	private MultiSheetResultSet multirs;

	public ExcelRunner(InputStream dataSource) {
		this.dataSource = dataSource;
	}

	public ExcelRunner(String path) {
		dataSource = ExlUtils.getDataSource(path);
	}

	public InputStream getDataSource() {
		return dataSource;
	}

	public void setDataSource(InputStream dataSource) {
		this.dataSource = dataSource;
	}

	public ExcelResultSet getRs() {
		return rs;
	}
	
	//查询单个sheet
	public <T> T query(ISQL sql, ExcelSetHandler<T> rsh) throws Exception {
		return query(sql,true, rsh);
	}
	
	public <T> T query(ISQL sql,boolean closeIns, ExcelSetHandler<T> rsh) throws Exception {
		T result = null;
		IJob<ExcelResultSet> job;
		StatementType stmtType = sql.stmtType();
		
		switch (stmtType) {
		case SELECT:
			job = new SelectJob(dataSource, (SelectSQL)sql);
			break;
		default:
			throw new RuntimeException("暂时不支持该操作");
		}
		
		rs = job.excute();
		result = rsh.handle(rs);
		
		if(closeIns){
			if(null != dataSource){
				dataSource.close();
			}
		}
		return result;
	}
	
	
	/**查询多个sheet，返回list*/
	public List<?> multiQueryToList(List<SelectSQL> sql, MultiSheetListSetHandler rsh, Object... params) throws Exception {
		IJob<MultiSheetResultSet> job = new MultiSelectJob(dataSource, sql);
		
		multirs = job.excute();
		List<?> result = rsh.handle(multirs);
		
		if(null != dataSource){
				dataSource.close();
		}
		return result;
	}
	
	/**查询多个sheet，返回map*/
	public Map<String, Object> multiQueryToMap(List<SelectSQL> sql, MultiSheetMapSetHandler rsh) throws Exception {
		IJob<MultiSheetResultSet> job = new MultiSelectJob(dataSource, sql);
		
		multirs = job.excute();
		Map<String, Object> result = rsh.handle(multirs);
		
		if(null != dataSource){
			dataSource.close();
		}
		return result;
	}

}
