package com.gwssi.application.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.gwssi.optimus.core.web.fileupload.OptimusFileItem;
import com.gwssi.optimus.util.UuidGenerator;


/**
 * @author xiaohan
 *
 */
public class FileUtil {
	
	
	/**
	 * 
	 * @param fileItem
	 * @param uploadPath
	 * @return
	 * @throws Exception
	 */
	public static Map saveFile(OptimusFileItem fileItem, String uploadPath)
			throws Exception {
		String separator = File.separator , dir =  separator + "system" + separator + "icons";
		File newDir = new File(uploadPath + separator + dir);
		if (!newDir.exists()) {
			newDir.mkdirs();
		}
		//文件扩展名
		String extName=fileItem.getExtName();
		
		
		String fileName=Calendar.getInstance().getTimeInMillis()+new Random().nextInt(1000)+"."+extName;
		String filePath = uploadPath + separator + dir  + separator + fileName;
		String path = uploadPath + dir + separator + fileName;
		Map map=new HashMap();
		fileItem.write(new File(filePath));
		map.put("fileName", fileItem.getName());
		map.put("path", path);
		return map;
	}
	
	/**
	 * 
	 * @param fileItem
	 * @param uploadPath
	 * @return
	 * @throws Exception
	 */
	public static Map saveDocFile(OptimusFileItem fileItem, String uploadPath)
			throws Exception {
		String separator = File.separator , dir =  separator + "system" + separator + "document";
		File newDir = new File(uploadPath + separator + dir);
		if (!newDir.exists()) {
			newDir.mkdirs();
		}
		//文件扩展名
		String extName=fileItem.getExtName();
		
		//文件名为随机数+扩展名
		//String fileName=Calendar.getInstance().getTimeInMillis()+new Random().nextInt(1000)+"."+extName;
		
		//文件名
		String name = fileItem.getName();
		String fileName=name.substring(0, name.lastIndexOf("."))+"+"+fileItem.getFileId()+"."+extName;
		String filePath = uploadPath + separator + dir  + separator + fileName;
		String path = uploadPath + dir + separator + fileName;
		Map map=new HashMap();
		fileItem.write(new File(filePath));
		map.put("fileName", fileItem.getName());
		path= dir + separator + fileName;
		map.put("path", path);
		return map;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static void deleteFile(String path)
			throws Exception {
		new File(path).delete();
	}	

}
