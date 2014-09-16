package com.tobe.excelutils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public class ExlUtils {

	public static int getIntRealValue(ExcelResultSet rs, Cell cell){
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = rs.getWb().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return Double.valueOf(cellValue.getNumberValue()).intValue();
		}
		return Double.valueOf(cell.getNumericCellValue()).intValue();
	}
	
	
	public static String getStringRealValue(ExcelResultSet rs, Cell cell){
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = rs.getWb().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return cellValue.getStringValue();
		}
		return cell.toString().trim();
	}
	
	public static Object getObjectRealValue(ExcelResultSet rs, Cell cell){
		int cellType = cell.getCellType();
		if(cellType == Cell.CELL_TYPE_FORMULA){
			FormulaEvaluator evaluator = rs.getWb().getCreationHelper().createFormulaEvaluator();
			CellValue cellValue = evaluator.evaluate(cell);
			return cellValue.getStringValue();
		}
		return cell.toString().trim();
	}

}
