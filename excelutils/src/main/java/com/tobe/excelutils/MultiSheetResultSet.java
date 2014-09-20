package com.tobe.excelutils;

import java.util.HashMap;
import java.util.Map;

import com.tobe.excelutils.job.MultiSelectJob;

/**
 * 多Sheet结果集，查询见{@link MultiSelectJob}
 * 
 * @author TOBE
 * 
 */
public class MultiSheetResultSet {

	/**
	 * key为sheet的名字
	 */
	private Map<String, ExcelResultSet> multiResultSet = new HashMap<String, ExcelResultSet>(5);

	public Map<String, ExcelResultSet> getMultiResultSet() {
		return multiResultSet;
	}

	public void setMultiResultSet(Map<String, ExcelResultSet> multiResultSet) {
		this.multiResultSet = multiResultSet;
	}

}
