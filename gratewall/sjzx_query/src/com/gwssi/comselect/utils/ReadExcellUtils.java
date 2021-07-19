package com.gwssi.comselect.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class ReadExcellUtils {
	public static final String EXTENSION_XLS = "xls";// excel 2003及以下
	public static final String EXTENSION_XLSX = "xlsx";// excel 2007及以上
	
	
	/**
	 * 获取一个文件的后缀(带有点)
	 * 
	 * @param fileName
	 *            文件名
	 * @return 返回文件的后缀
	 */
	public static String getExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos == -1)
			return "";
		return fileName.substring(pos, fileName.length());
	}

	/**
	 * 获取一个文件的后缀(不带有点)
	 * 
	 * @param fileName
	 *            文件名
	 * @return 返回文件的后缀
	 */
	public static String getExtNoPoint(String fileName) {
		if (fileName.lastIndexOf(".") == -1)
			return "";
		int pos = fileName.lastIndexOf(".") + 1;
		return fileName.substring(pos, fileName.length());
	}

	/**
	 * 预读文件是否存在及是否excel
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	public  static void preReadCheck(String filePath) {
		// 常规检查
		File file = new File(filePath);
		if (!file.exists()) {
			// throw new FileNotFoundException("传入的文件不存在：" + filePath);
		}
		if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
			// throw new FileFormatException("传入的文件不是excel");
		}
	}

	
	/**
	 * 
	 * @param columnNum
	 *            指定要匹配哪一列,默认下标从0，即第一列开始
	 * @param filePath
	 *            传入文件路径
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map readExcel(String filePath,String flag) {
		
		HashMap map = new HashMap();
		// 检查
		preReadCheck(filePath);
		//preReadCheck("C:/demo/fddbr.xlsx");
		
		
		
		
		// 获取workbook对象
		Workbook workbook = null;
		List arryParams = new ArrayList();
		Integer counts = 0;
		
		HashMap mapPrams  = null;
		try {
			InputStream is = new FileInputStream(filePath);
			//workbook = new XSSFWorkbook(is); // 只做 2003
			workbook = WorkbookFactory.create(is); 
			
			
			// 读文件 一个sheet一个sheet地读取
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				int firstRowIndex = sheet.getFirstRowNum();
				int lastRowIndex = sheet.getLastRowNum();
				counts = lastRowIndex;
				for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
					Row currentRow = sheet.getRow(rowIndex);// 当前行
					
					if(flag.equals("1")){
						mapPrams  = new HashMap();
						String name = getCellValue(currentRow.getCell(0), true);//投资人名字
						if(!isEmpty(name)){
							mapPrams.put("name", name);
							arryParams.add(mapPrams);
						}
					
					}else if(flag.equals("2")){
						mapPrams  = new HashMap();
						String cerno = getCellValue(currentRow.getCell(0), true);//投资人身份证
						if(!isEmpty(cerno)){
							mapPrams.put("cerno", cerno);
							arryParams.add(mapPrams);
						}
					}else{
						mapPrams  = new HashMap();
						String name = getCellValue(currentRow.getCell(0), true);//投资人身份证
						String cerno = getCellValue(currentRow.getCell(1), true);//投资人的证件号码
						if(!isEmpty(name)){
							mapPrams.put("name", name);
						}
						if(!isEmpty(cerno)){
							mapPrams.put("cerno", cerno);
						}
						arryParams.add(mapPrams);
					}
				}
			}
			map.put("fileItemCounts", counts);//文件excel总行数
			map.put("list",arryParams);
			map.put("flag", flag);//存放选择哪个Sql语句操作的内容
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @param treatAsStr
	 *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getCellValue(Cell cell, boolean treatAsStr) {
		if (cell == null) {
			return "";
		}

		if (treatAsStr) {
			// 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
			// 加上下面这句，临时把它当做文本来读取
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
	
	

	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
}
