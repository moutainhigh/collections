package com.bwcx.bus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileCopy {

	
	public static void copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		FileInputStream in = new FileInputStream(oldFile);
		FileOutputStream out = new FileOutputStream(file);
		try {
			byte[] buffer = new byte[2097152];
			int readByte = 0;
			while ((readByte = in.read(buffer)) != -1) {
				out.write(buffer, 0, readByte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
		}

	}
	
	
	public static void fileCopySwitch(String fileName) {
		Date date = new Date();
		
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMM");
		
		
		
		//\\szaic\mqs\trswcm\wcmdata.ear\pub.war\portal\zwyw\201907\W020190711586537298758.jpg
		String fromPath1 = "\\\\szaic\\mqs\\trswcm\\wcmdata.ear\\pub.war\\portal\\zwyw\\"+ format.format(date)+"\\"+fileName;
		String fromPath2 = "\\\\szaic\\mqs\\trswcm\\wcmdata.ear\\pub.war\\portal\\xwrd\\"+ format.format(date)+"\\"+fileName;
		
		
		//String tempSourceFileName = fileName.substring(0,fileName.lastIndexOf("."))+".png";
		
		
		
		File fileIsExits1 = new File(fromPath1);
		File fileIsExits2 = new File(fromPath2);
		
		boolean flag1 = fileIsExits1.exists();
		boolean flag2 = fileIsExits2.exists();
		
		
		
		//String toPath  ="C:\\Users\\changruan\\Desktop\\ye\\"+fileName ;
		String toPath  ="C:\\upload_bwcx\\"+fileName ;
		
		try {
			
			if(flag1) {
				copyFile(fileIsExits1.getPath(),toPath);
			}
			if(flag2) {
				copyFile(fileIsExits2.getPath(),toPath);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
