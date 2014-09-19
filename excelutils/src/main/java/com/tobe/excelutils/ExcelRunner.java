package com.tobe.excelutils;

import java.io.InputStream;

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
	//
	public <T> T query(ISQL sql, ExcelSetHandler<T> rsh, Object... params) throws Exception {
		return query(sql,true, rsh, params);
	}
	
	public <T> T query(ISQL sql,boolean closeIns, ExcelSetHandler<T> rsh, Object... params) throws Exception {
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
	
	
	public <T> T multiQuery(ISQL sql, MultiSheetSetHandler<T> rsh, Object... params) throws Exception {
		return multiQuery(sql,true, rsh, params);
	}
	
	public <T> T multiQuery(ISQL sql,boolean closeIns, MultiSheetSetHandler<T> rsh, Object... params) throws Exception {
		T result = null;
		IJob<MultiSheetResultSet> job;
		StatementType stmtType = sql.stmtType();
		
		switch (stmtType) {
		case SELECT:
			job = new MultiSelectJob(dataSource, (SelectSQL)sql);
			break;
		default:
			throw new RuntimeException("暂时不支持该操作");
		}
		
		multirs = job.excute();
		result = rsh.handle(multirs);
		
		if(closeIns){
			if(null != dataSource){
				dataSource.close();
			}
		}
		return result;
	}

}
