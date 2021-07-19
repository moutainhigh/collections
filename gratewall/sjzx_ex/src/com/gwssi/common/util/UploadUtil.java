package com.gwssi.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UploadUtil
{
	 /**
     * ���ָ���ļ�����չ��
     * ���δ�ҵ��򷵻ؿ��ַ���
     * 
     * @param fileName ָ���ļ����ļ���
     * @return �ļ�����չ��
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
     * ������������ ������ ���������
     * @param input ����������
     * @param output ���������
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
