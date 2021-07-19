package com.gwssi.comselect.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadTxtUtil {

	public static List readTxtFile(String filePath) {
		Map map =null;
		List params = new ArrayList();
		 try { 
		        String encoding="GBK"; 
		        File file=new File(filePath); 
		        if(file.isFile() && file.exists()){ //判断文件是否存在 
		          InputStreamReader read = new InputStreamReader( 
		          new FileInputStream(file),encoding);//考虑到编码格式 
		          BufferedReader bufferedReader = new BufferedReader(read); 
		          String lineTxt = null; 
		          while((lineTxt = bufferedReader.readLine()) != null){ 
		            System.out.println(lineTxt);
		            map = new HashMap();
		            map.put("lineTxt", lineTxt);
		            params.add(map);
		          } 
		          read.close(); 
		    }else{ 
		      System.out.println("找不到指定的文件"); 
		    } 
		    } catch (Exception e) { 
		      System.out.println("读取文件内容出错"); 
		      e.printStackTrace(); 
		    } 
		
		return params;
		
	}

	
	public static void main(String[] args) {
		   String filePath = "C:\\demo\\te.txt"; 
		   ReadTxtUtil.readTxtFile(filePath); 
	}
}
