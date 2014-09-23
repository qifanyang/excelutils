package com.tobe.excelutils.code;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tobe.excelutils.ExlUtils;

public class ExcelLoader {
	
	private List<String> sheetFilterList = new ArrayList<String>();
	
	/**大于0该参数才有效,结果集开始行,前面的不加入结果集*/
	private int startDataRow = -1;
	/**大于0该参数才有效,结果集结束行,后面的不加入结果集*/
	private int endDataRow = -1;
	
	public ExcelLoader(){
		sheetFilterList.add("changelog");
		sheetFilterList.add("说明");
	}
	
	public List<String> getSheetFilterList() {
		return sheetFilterList;
	}
	
	private boolean sheetFilter(String name){
		if(null == name || name.length() == 0){
			return false;
		}
		
		for(String s : sheetFilterList){
			if(name.indexOf(s) != -1){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否过滤掉该行,不过滤返回true, 否则返回false
	 * 
	 * 过滤器,true表示通过过滤
	 * @param rowNumber
	 * @return
	 */
	public boolean rowFilter(int rowNumber){
		if(startDataRow < endDataRow){
			return true;
		}
		if(startDataRow > 0 && endDataRow > 0){
			if(startDataRow <= rowNumber && rowNumber <= endDataRow){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<SheetBean> loadname(String fileName) {
		ArrayList<SheetBean> result = new ArrayList<SheetBean>();
		try {
			// 获得文件输入流
			FileInputStream in = new FileInputStream(fileName);
			// 获得Excel文件
			XSSFWorkbook wb = new XSSFWorkbook(in);
			// 获得工作表数量
			int sheetNumber = wb.getNumberOfSheets();
			for (int i = 0; i < sheetNumber; i++) {
				// 获得工作表
				XSSFSheet sheet = wb.getSheetAt(i);
//				// 更改日志略过
//				if (sheet.getSheetName() == null || "changelog".equals(sheet.getSheetName()) || sheet.getSheetName().indexOf("说明") != -1) {
//					continue;
//				}
				if(!sheetFilter(sheet.getSheetName())){
					continue;
				}
				// 数据工作表
				else {
					String name = sheet.getSheetName();
					SheetBean datas = new SheetBean();
					datas.setFilename(fileName);
					datas.setName(name);
					result.add(datas);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得excel数据
	 * 
	 * @param fileName
	 * @param dataLoad 是否加载数据
	 * @return
	 */
	public ArrayList<SheetBean> load(String fileName, boolean dataLoad) {
		ArrayList<SheetBean> result = new ArrayList<SheetBean>();
		int currentRow = 0;
		int currentCell = 0;
		try {
			// 获得文件输入流
			InputStream in = ExlUtils.getDataSource(fileName);

			// 获得Excel文件
			XSSFWorkbook wb = new XSSFWorkbook(in);

			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			// 获得工作表数量
			int sheetNumber = wb.getNumberOfSheets();
			for (int i = 0; i < sheetNumber; i++) {
				// 获得工作表
				XSSFSheet sheet = wb.getSheetAt(i);
				// 更改日志略过,忽略的sheet
//				if (sheet.getSheetName() == null || "changelog".equals(sheet.getSheetName()) || sheet.getSheetName().indexOf("说明") != -1) {
//					continue;
//				}
				if(!sheetFilter(sheet.getSheetName())){
					continue;
				}
				// 数据工作表
				else {
					String name = sheet.getSheetName();

					SheetBean datas = new SheetBean();

					datas.setFilename(fileName);
					datas.setName(name);

					// 前五行为字段描述
					String[][] fields = new String[5][];
					// Returns the logical row (not physical) 0-based. If you
					// ask for a row that is not
					// defined you get a null. This is to say row 4 represents
					// the fifth row on a sheet.
					// getRow方法是基于0的, 没有的话返回空
					XSSFRow row = sheet.getRow(4);// 第五行是类字段描述,对应数据库表中的列
					int cellNumber = row.getPhysicalNumberOfCells();// 11

					// 读取表字段信息
					for (int j = 0; j < 5; j++) {
						currentRow = j;
						row = sheet.getRow(j);
						if (row == null)
							continue;
						fields[j] = new String[cellNumber];//fields[0].length为字段数量
						for (int k = 0; k < cellNumber; k++) {
							currentCell = k;
							XSSFCell cell = row.getCell(k);
							if (cell == null)
								continue;
							fields[j][k] = cell.toString();
						}
					}

					// Excel描述文件已经扫描完毕,fields[0].length为字段数量
					for (int j = 0; j < fields[0].length; j++) {
						// 不读取
						if (fields[1][j] == null || "".equals(fields[1][j].trim()))
							continue;
						// f[1]为数据库字段名字
						// 字段信息
						Field field = new Field();
						field.setName(fields[1][j].trim());
						if (fields[2] == null || fields[2][j] == null || "".equals(fields[2][j].trim())) {
							// 默认为int型
							field.setClassName("int");
							field.setJavaClassName("int");
							field.setDbClassName("INTEGER");
						} else {
							field.setClassName(fields[2][j].trim());
							if (fields[2][j].trim().indexOf("varchar") != -1) {
								field.setJavaClassName("String");
								field.setDbClassName("VARCHAR");
							} else if (fields[2][j].trim().indexOf("bigint") != -1) {
								field.setJavaClassName("long");
								field.setDbClassName("BIGINT");
							} else if (fields[2][j].trim().indexOf("smallint") != -1) {
								field.setJavaClassName("short");
								field.setDbClassName("SMALLINT");
							} else if (fields[2][j].trim().indexOf("tinyint") != -1) {
								field.setJavaClassName("byte");
								field.setDbClassName("TINYINT");
							} else if (fields[2][j].trim().indexOf("int") != -1) {
								field.setJavaClassName("int");
								field.setDbClassName("INTEGER");
							} else if (fields[2][j].trim().indexOf("blob") != -1) {
								field.setJavaClassName("byte[]");
								field.setDbClassName("LONGVARBINARY");
							} else if (fields[2][j].trim().indexOf("text") != -1) {
								field.setJavaClassName("String");
								field.setDbClassName("LONGVARCHAR");
							}
						}
						field.setExplain(fields[4][j]);
						field.setCell(j);

						// 查看是否有主键
						if (fields[0].length > j && fields[0][j] != null && !"".equals(fields[0][j].trim())) {
							if ((int) Double.parseDouble(fields[0][j]) == 1) {
								datas.getKeys().add(field);
								datas.setKey(field);
							}
						}

						datas.getFields().add(field);
					}

					// 设置主键
					if (datas.getKeys().size() > 1) {
						// 字段信息
						Field field = new Field();

						StringBuffer buf = new StringBuffer();
						for (int j = 0; j < datas.getKeys().size(); j++) {
							buf.append("_" + fields[1][datas.getKeys().get(j).getCell()]);
						}
						if (buf.length() > 0)
							buf.deleteCharAt(0);
						field.setName(buf.toString());
						field.setClassName("varchar(255)");
						field.setDbClassName("VARCHAR");
						field.setJavaClassName("String");
						buf = new StringBuffer();
						for (int j = 0; j < datas.getKeys().size(); j++) {
							buf.append("+" + fields[4][datas.getKeys().get(j).getCell()]);
						}
						if (buf.length() > 0)
							buf.deleteCharAt(0);
						field.setExplain(buf.toString());
						field.setCell(-1);
						datas.getFields().add(0, field);

						datas.setKey(field);
					}

					if (dataLoad && sheet.getPhysicalNumberOfRows() > 5) {
						String[][] data = new String[sheet.getPhysicalNumberOfRows() - 5][datas.getFields().size()];
						for (int j = 5; j < sheet.getPhysicalNumberOfRows(); j++) {
							currentRow = j;
							row = sheet.getRow(j);
							if (row == null)
								continue;
							for (int k = 0; k < datas.getFields().size(); k++) {
								currentCell = k;
								Field field = datas.getFields().get(k);
								if (field.getCell() == -1) {
									// -1的话为主键,可能是其它列构成的,这里用表达式拼接, 例如: id_lv
									StringBuffer buf = new StringBuffer();
									for (int l = 0; l < datas.getKeys().size(); l++) {
										XSSFCell cell = row.getCell(datas.getKeys().get(l).getCell());
										if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
											buf.append("_" + String.valueOf((int) evaluator.evaluate(cell).getNumberValue()));
										} else {
											if ("int".equals(datas.getKeys().get(l).getClassName())
													|| "bigint".equals(datas.getKeys().get(l).getClassName())
													|| "smallint".equals(datas.getKeys().get(l).getClassName())
													|| "tinyint".equals(datas.getKeys().get(l).getClassName())) {
												buf.append("_" + String.valueOf((long) cell.getNumericCellValue()));
											} else {
												buf.append("_" + cell.toString().trim());
											}
										}
									}
									if (buf.length() > 0)
										buf.deleteCharAt(0);
									data[j - 5][k] = buf.toString();
								} else {
									XSSFCell cell = row.getCell(field.getCell());
									if (cell == null || ("").equals(cell.toString().trim())) {
										if ("int".equals(field.getClassName()) || "bigint".equals(field.getClassName())
												|| "smallint".equals(field.getClassName()) || "tinyint".equals(field.getClassName()))
											data[j - 5][k] = "0";
										else
											data[j - 5][k] = "";
									} else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
										data[j - 5][k] = String.valueOf((int) evaluator.evaluate(cell).getNumberValue());
									} else {
										if ("int".equals(field.getClassName()) || "bigint".equals(field.getClassName())
												|| "smallint".equals(field.getClassName()) || "tinyint".equals(field.getClassName())) {
											data[j - 5][k] = String.valueOf((long) Math.round(cell.getNumericCellValue())); // 四舍五入
										} else {
											if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
												data[j - 5][k] = String.valueOf((long) cell.getNumericCellValue());
											else
												data[j - 5][k] = cell.toString().trim();
										}
									}
								}
							}
						}

						datas.setDatas(data);
					}
					result.add(datas);
				}
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
