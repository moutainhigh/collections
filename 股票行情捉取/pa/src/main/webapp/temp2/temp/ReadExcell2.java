package com.ly.excell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//http://www.cnblogs.com/hongten/p/java_poi_excel_xls_xlsx.html
//https://blog.csdn.net/gxx_csdn/article/details/79085713
//https://www.cnblogs.com/hhhshct/p/7255915.html
import org.springframework.stereotype.Component;

//@Component
public class ReadExcell2 {
	private static Logger logger = Logger.getLogger(ReadExcell2.class);
	
	
	public void readExcell(String filePath) {
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		List<Map<String, String>> list = null;
		String cellData = null;
		// String filePath = "C:\\mytemp\\001.xlsx";
		//String filePath = "C:\\new_tdx\\T0002\\export\\临时条件股20181202.xlsx";
		// String columns[] = {"代码","名称","现价"};
		wb = readExcel(filePath);
		if (wb != null) {
			// 用来存放表中数据
			list = new ArrayList<Map<String, String>>();
			// 获取第一个sheet
			sheet = wb.getSheetAt(0);
			// 获取最大行数
			int rownum = sheet.getPhysicalNumberOfRows()-1;//去掉最后一行的   数据来源:通达信																												
			// 获取第一行
			row = sheet.getRow(0);
			// 获取最大列数
			int colnum = row.getPhysicalNumberOfCells();
			for (int i = 1; i < rownum; i++) {
				Map<String, String> map = new HashMap<String, String>();
				row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < colnum; j++) {
						cellData = (String) getCellFormatValue(row.getCell(j));
						logger.debug(cellData + " ");
						// map.put(columns[j], cellData);
					}
				} else {
					break;
				}
				// list.add(map);
				System.out.println();

			}
		}
		// 遍历解析出来的list
		for (Map<String, String> map : list) {
			for (Entry<String, String> entry : map.entrySet()) {
				System.out.print(entry.getKey() + ":" + entry.getValue() + ",");
			}
			System.out.println();
		}

	}

	// 读取excel
	public static Workbook readExcel(String filePath) {
		Workbook wb = null;
		if (filePath == null) {
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				return wb = null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	public static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			// 判断cell类型
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: {
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// 判断cell是否为日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					// 转换为日期格式YYYY-mm-dd
					cellValue = cell.getDateCellValue();
				} else {
					// 数字
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				cellValue = cell.getRichStringCellValue().getString();
				break;
			}
			default:
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}

}