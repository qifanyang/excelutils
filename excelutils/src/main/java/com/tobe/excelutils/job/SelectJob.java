package com.tobe.excelutils.job;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tobe.excelutils.ExcelResultSet;
import com.tobe.excelutils.SelectSQL;

/**执行查询,返回一个sheet的结果集
 * 默认第一行为标题栏
 * 
 * */
public class SelectJob implements IJob<ExcelResultSet>{
	
	private InputStream dataSource;
	private SelectSQL sql;
	private Workbook wb;
	//要查询的sheetIndex, 默认为0
	private int sheetIndex;
	
	public SelectJob(InputStream dataSource, SelectSQL sql) {
		this.dataSource = dataSource;
		this.sql = sql;
	}
	
	public SelectJob(Workbook wb, SelectSQL sql, int sheetIndex){
		this.wb = wb;
		this.sql = sql;
		this.sheetIndex = sheetIndex;
	}
	
	/**
	 * 遍历每一行,放入ExcelResultSet,遇到NULL单元格和空白单元格不处理,该行不放入ExcelResultSet
	 */
	public ExcelResultSet excute() {
		ExcelResultSet rs = new ExcelResultSet();
		try {
				// 执行查询操作
				// 查询字段列表全部转为小写
				if(wb == null){
					//多sheet查询时,会从构造方法中传入wb,同时dataSource为null
					wb = new XSSFWorkbook(dataSource);
				}
				rs.setWb(wb);
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				rs.setEvaluator(evaluator);
				Sheet sheet = wb.getSheetAt(sheetIndex);
				Iterator<Row> rowIterator = sheet.rowIterator();
	
				boolean isFirstRow = true;
				while (rowIterator.hasNext()) {
					boolean addRow = false;//默认该行记录不加入结果集,rowFilter判断是否加入
					int titleIndex = 0;
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					boolean isBlank = false;// 有空行,读取cell.getCellType()为3
					// 遍历每一行,放入ExcelResultSet,遇到空不处理,不放入ExcelResultSet
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if(null == cell){
							isBlank = true;//null等同于空白处理
							break;
						}
						
						if(isFirstRow){
							String title = cell.toString().trim();
							if(!sql.show(title)){
								rs.getHeaders().add("");
							}else {
								rs.getHeaders().add(title);
							}
						}else{
							int cellType = cell.getCellType();
							if (cellType == Cell.CELL_TYPE_BLANK || cellType == Cell.CELL_TYPE_ERROR) {
								isBlank = true;
								break;
							}
							
							//空白不会到这里
							addRow = sql.whereFilter(rs, cell, titleIndex);
							if(!addRow){
								break;
							}
							titleIndex++;//没一列加1
						}
					}
	
					// if (!isFirstRow && !isBlank) {
					// list.add(instance);
					// }
					//不是第一行 ，并且不是空白,并且可以加入行List， 三个条件同时满足，当前行才会出现在结果集中
					if (!isFirstRow && !isBlank && addRow) {
						rs.getRowList().add(row);
					}
	
					isFirstRow = false;
				}
		
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			return rs;
		}
		
		
}
