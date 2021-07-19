package com.gwssi.common.rodimus.report.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;



public class FileUtil {

	/**
	 * Read File as String .
	 * 
	 * @param targetFile
	 * @return
	 * @throws OptimusException 
	 */
	public static String getFileText(File targetFile) {
		StringBuffer ret = new StringBuffer("");
		BufferedReader br = null;
		try{
			if(targetFile!=null && targetFile.isFile() && targetFile.exists()){
				FileInputStream fileInputStream = new FileInputStream(targetFile);
				InputStreamReader reader = new InputStreamReader(fileInputStream,"GBK");
				br = new BufferedReader(reader);
				String line = null;
				while ((line = br.readLine()) != null) {
					ret.append(line);
				}
			}else{
				throw new RuntimeException("File not exists.");
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found:"+e.getMessage(),e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(),e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret.toString();
	}
	
	
}
