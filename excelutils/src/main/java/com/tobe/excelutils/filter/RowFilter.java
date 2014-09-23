package com.tobe.excelutils.filter;

public class RowFilter implements IFilter{

	/** 大于0该参数才有效 */
	private int startRow = -1;
	/** 大于0该参数才有效 */
	private int endRow = -1;
	
	private int curRow = -1;

	
	public RowFilter(){}
	
	public RowFilter(int startRow, int endRow) {
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getCurRow() {
		return curRow;
	}

	public void setCurRow(int curRow) {
		this.curRow = curRow;
	}

	@Override
	public boolean filter() {
		if(curRow < 0){
			return true;
		}
		
		if(startRow < endRow){
			return true;
		}
		if(startRow > 0 && endRow > 0){
			if(startRow <= curRow && curRow <= endRow){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}

}
