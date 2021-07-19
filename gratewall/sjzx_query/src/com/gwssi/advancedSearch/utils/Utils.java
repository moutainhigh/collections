package com.gwssi.advancedSearch.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gwssi.optimus.core.exception.OptimusException;

	public class Utils{
		
		@SuppressWarnings({ "deprecation", "rawtypes" })
		public static void ExcelOut(List<Map> list,String excelTitle,
				List<String> displayInfo,List<String> rowDataKeys,int[] excelFreezeParam,
				List<String> code2StrKeys,List<Map<String,String>> codes2String,HttpServletResponse response) throws IOException, OptimusException{
			
			Long t1 = new Date().getTime();//导出excel开始时间记录
			
			//创建excel表格对象
			XSSFWorkbook xs=new XSSFWorkbook();
			XSSFSheet sheet=xs.createSheet(excelTitle);
			
			sheet.setColumnWidth(0, 5*256);
			sheet.setColumnWidth(1, 25*256);
			sheet.setColumnWidth(2, 25*256);
			sheet.setColumnWidth(3, 40*256);
			sheet.setColumnWidth(4, 10*256);
			sheet.setColumnWidth(5, 15*256);
			
			sheet.setColumnWidth(6, 40*256);
			sheet.setColumnWidth(7, 15*256);
			sheet.setColumnWidth(8, 40*256);
			sheet.setColumnWidth(9, 15*256);
			sheet.setColumnWidth(10, 25*256);
			sheet.setColumnWidth(11, 15*256);
			
			XSSFRow row=sheet.createRow((short)0);//数据展示行对象创建
			if(excelFreezeParam.length!=4){
				throw new OptimusException("冻结窗口参数错误!");
			}
			//冻结窗口  可根据需求更改
			sheet.createFreezePane(excelFreezeParam[0],excelFreezeParam[1],excelFreezeParam[2],excelFreezeParam[3]);
			
			//数据展示行填充标题
			String display = "";
			for(int i = 0;i<displayInfo.size();i++){
				display = displayInfo.get(i);
				Cell ztCell = row.createCell((short)i);
				row.setHeightInPoints(12);
				ztCell.setCellValue(display);
				XSSFCellStyle ztStyle = (XSSFCellStyle) xs.createCellStyle();  
				Font ztFont = xs.createFont();  
	            ztFont.setFontHeightInPoints((short)9);    // 将字体大小设置为9px  
	            ztFont.setFontName("宋体");             // 将“华文行楷”字体应用到当前单元格上  
	            ztStyle.setFont(ztFont);                    // 将字体应用到样式上面  
	            if(display.equals("姓名")){
					 ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色” 
				}
	            ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	            ztCell.setCellStyle(ztStyle);
				
			}
			
			String rowDatakey = "";
			Boolean flag = true;
			//数据展示
			//有需要转换代码的情况
			if(code2StrKeys!=null && code2StrKeys.size()!=0 && codes2String!=null && codes2String.size()!=0){
				for(int i = 0;i<list.size();i++){   //遍历数据集合
					
					XSSFRow rowdata=sheet.createRow((short)(i+1));
					Map map =list.get(i);
					for(int y = 0;y<rowDataKeys.size();y++){  //遍历Key集合
						rowDatakey = rowDataKeys.get(y);
						for(String Str : code2StrKeys){
							if(rowDatakey.equals(Str)){
								for(int z = 0;z<codes2String.size();z++){   //如果有代码转字符串的话 遍历代码和字符串对应关系集合
									Map<String,String> code2Str = codes2String.get(z);
									Set<Entry<String, String>> entrySet = code2Str.entrySet();
									for(Entry<String, String> entry : entrySet){
										rowdata.createCell((short)y).setCellValue(entry.getValue());
										flag = false;//该单元格以被赋值 后面无需再次赋值
									}
								}
							}
						}
						if(flag){//如果不是代码转字符串的情况下 还是正常赋值 从Map中取值
							rowdata.createCell((short)y).setCellValue(map.get(rowDatakey)==null?"":map.get(rowDatakey).toString());  
						}else{
							//再次更改标记
							flag = true;
						}
					}
				}
			}else{  //不需转换代码
				for(int i = 0;i<list.size();i++){   //遍历数据集合
					Row rowdata=sheet.createRow((short)(i+1));
					rowdata.setHeightInPoints(11);  
					Map map =list.get(i);
					for(int y = 0;y<rowDataKeys.size();y++){  //遍历Key集合
						rowDatakey = rowDataKeys.get(y);
						Cell ztCell = rowdata.createCell(y); 
						String text = map.get(rowDatakey)==null?"":map.get(rowDatakey).toString();
						ztCell.setCellValue(text);  
						 XSSFCellStyle ztStyle = (XSSFCellStyle) xs.createCellStyle();  
						//rowdata.createCell((short)y).setCellValue(map.get(rowDatakey)==null?"":map.get(rowDatakey).toString());
						 // 创建字体对象  
			            Font ztFont = xs.createFont();  
			            ztFont.setFontHeightInPoints((short)9);    // 将字体大小设置为18px  
			            ztFont.setFontName("宋体");             // 将“华文行楷”字体应用到当前单元格上  
			            ztStyle.setFont(ztFont);                    // 将字体应用到样式上面 
			            if(y==7||y==9||y==11){
			            	//System.out.println(text);
			            	ztStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			            }
			            ztCell.setCellStyle(ztStyle);               // 样式应用到该单元格上  
			              
					}
				}
			}
			
			long t2=new Date().getTime();
			System.out.println("写入excel共耗时："+(t2-t1));
			
			//以下为excel导出部分
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");//设置日期格式
			String dowmFileRight =  df.format(new Date());
		   
			response.reset();
			response.setContentType("applicat'ion/x-download;charset=GBK");

			String downFileName = excelTitle + "_" +dowmFileRight+ ".xlsx";

			response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
		
			OutputStream out=response.getOutputStream();
		   
			xs.write(out);
			out.flush();
			out.close();
		}
		
		
		
		
		
		
		
		@SuppressWarnings({ "deprecation", "rawtypes" })
		public static void ExcelTZROut(List<Map> list,String excelTitle,
				List<String> displayInfo,List<String> rowDataKeys,int[] excelFreezeParam,
				List<String> code2StrKeys,List<Map<String,String>> codes2String,HttpServletResponse response) throws IOException, OptimusException{
			
			Long t1 = new Date().getTime();//导出excel开始时间记录
			
			//创建excel表格对象
			XSSFWorkbook xs=new XSSFWorkbook();
			XSSFSheet sheet=xs.createSheet(excelTitle);
			
			sheet.setColumnWidth(0, 5*256);
			sheet.setColumnWidth(1, 25*256);
			sheet.setColumnWidth(2, 25*256);
			sheet.setColumnWidth(3, 40*256);
			sheet.setColumnWidth(4, 10*256);
			sheet.setColumnWidth(5, 15*256);
			
			
			XSSFRow row=sheet.createRow((short)0);//数据展示行对象创建
			if(excelFreezeParam.length!=4){
				throw new OptimusException("冻结窗口参数错误!");
			}
			//冻结窗口  可根据需求更改
			sheet.createFreezePane(excelFreezeParam[0],excelFreezeParam[1],excelFreezeParam[2],excelFreezeParam[3]);
			
			//数据展示行填充标题
			String display = "";
			for(int i = 0;i<displayInfo.size();i++){
				display = displayInfo.get(i);
				Cell ztCell = row.createCell((short)i);
				row.setHeightInPoints(12);
				ztCell.setCellValue(display);
				XSSFCellStyle ztStyle = (XSSFCellStyle) xs.createCellStyle();  
				Font ztFont = xs.createFont();  
	            ztFont.setFontHeightInPoints((short)9);    // 将字体大小设置为9px  
	            ztFont.setFontName("宋体");             // 将“华文行楷”字体应用到当前单元格上  
	            ztStyle.setFont(ztFont);                    // 将字体应用到样式上面  
	            if(display.equals("姓名")){
					 ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色” 
				}
	            ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	            ztCell.setCellStyle(ztStyle);
				
			}
			
			String rowDatakey = "";
			Boolean flag = true;
			//数据展示
			//有需要转换代码的情况
			if(code2StrKeys!=null && code2StrKeys.size()!=0 && codes2String!=null && codes2String.size()!=0){
				for(int i = 0;i<list.size();i++){   //遍历数据集合
					
					XSSFRow rowdata=sheet.createRow((short)(i+1));
					Map map =list.get(i);
					for(int y = 0;y<rowDataKeys.size();y++){  //遍历Key集合
						rowDatakey = rowDataKeys.get(y);
						for(String Str : code2StrKeys){
							if(rowDatakey.equals(Str)){
								for(int z = 0;z<codes2String.size();z++){   //如果有代码转字符串的话 遍历代码和字符串对应关系集合
									Map<String,String> code2Str = codes2String.get(z);
									Set<Entry<String, String>> entrySet = code2Str.entrySet();
									for(Entry<String, String> entry : entrySet){
										rowdata.createCell((short)y).setCellValue(entry.getValue());
										flag = false;//该单元格以被赋值 后面无需再次赋值
									}
								}
							}
						}
						if(flag){//如果不是代码转字符串的情况下 还是正常赋值 从Map中取值
							rowdata.createCell((short)y).setCellValue(map.get(rowDatakey)==null?"":map.get(rowDatakey).toString());  
						}else{
							//再次更改标记
							flag = true;
						}
					}
				}
			}else{  //不需转换代码
				for(int i = 0;i<list.size();i++){   //遍历数据集合
					Row rowdata=sheet.createRow((short)(i+1));
					rowdata.setHeightInPoints(11);  
					Map map =list.get(i);
					for(int y = 0;y<rowDataKeys.size();y++){  //遍历Key集合
						rowDatakey = rowDataKeys.get(y);
						Cell ztCell = rowdata.createCell(y); 
						String text = map.get(rowDatakey)==null?"":map.get(rowDatakey).toString();
						ztCell.setCellValue(text);  
						 XSSFCellStyle ztStyle = (XSSFCellStyle) xs.createCellStyle();  
						//rowdata.createCell((short)y).setCellValue(map.get(rowDatakey)==null?"":map.get(rowDatakey).toString());
						 // 创建字体对象  
			            Font ztFont = xs.createFont();  
			            ztFont.setFontHeightInPoints((short)9);    // 将字体大小设置为18px  
			            ztFont.setFontName("宋体");             // 将“华文行楷”字体应用到当前单元格上  
			            ztStyle.setFont(ztFont);                    // 将字体应用到样式上面 
			            if(y==7||y==9||y==11){
			            	//System.out.println(text);
			            	ztStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			            }
			            ztCell.setCellStyle(ztStyle);               // 样式应用到该单元格上  
			              
					}
				}
			}
			
			long t2=new Date().getTime();
			System.out.println("写入excel共耗时："+(t2-t1));
			
			//以下为excel导出部分
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");//设置日期格式
			String dowmFileRight =  df.format(new Date());
		   
			response.reset();
			response.setContentType("applicat'ion/x-download;charset=GBK");

			String downFileName = excelTitle + "_" +dowmFileRight+ ".xlsx";

			response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
		
			OutputStream out=response.getOutputStream();
		   
			xs.write(out);
			out.flush();
			out.close();
		}

}
