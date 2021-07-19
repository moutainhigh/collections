package com.gwssi.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UploadUtil
{
	 /**
     * 获得指定文件的扩展名
     * 如果未找到则返回空字符串
     * 
     * @param fileName 指定文件的文件名
     * @return 文件的扩展名
     */
    public static String getExtension(String fileName) {
    	if(fileName==null || fileName.trim().length()==0) {
    		return "";
    	}
    	
    	fileName = fileName.trim();
    	int dotIndex = fileName.lastIndexOf(".");
    	if(dotIndex<=0) {
    		return "";
    	}
    	
    	return fileName.substring(dotIndex);
    }
    
    /**
     * 将数据输入流 交换到 数据输出流
     * @param input 数据输入流
     * @param output 数据输出流
     * @throws IOException
     */
    public static void exchangeStream(InputStream input, OutputStream output) throws IOException {
    	int readLen = 0;
    	byte[] readBytes = new byte[8 * 1024];
    	while((readLen = input.read(readBytes))>0) {
    		output.write(readBytes, 0, readLen);
    	}
    	output.flush();
    	output.close();
    	input.close();
    }
}
