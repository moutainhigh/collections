package com.baobiao.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class YeFileDirCopy {
	private static Logger logger = Logger.getLogger(YeFileDirCopy.class);
	
	
	public void toolsOfYe(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		// System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		String dataStr = df.format(new Date());
		//先清空当前日期已经存在的文件夹
		delFolder("D:\\backup\\cognosFile"+dataStr);
		
		// 需要复制的目标文件或目标文件夹
		String pathname = "D:\\cognosFile";
		File file = new File(pathname);
		// 复制到的位置
		String topathname = "D:\\backup";
		File toFile = new File(topathname);
		if(!toFile.exists()){
			toFile.mkdirs();
		}
		try {
			copy(file, toFile);
			System.out.println("cognosFile拷贝成功");
			logger.info("cognosFile拷贝成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("====>将拷贝的 cognosFile 开始文件重命名为 cognosFile + 当前日期，该文件夹所在路径为D:\\backup\\cognosFile将更改为D:\\backup\\cognosFile"+dataStr);
		System.out.println("====>将拷贝的 cognosFile 开始文件重命名为 cognosFile + 当前日期，该文件夹所在路径为D:\\backup\\cognosFile将更为D:\\backup\\cognosFile"+dataStr);
		
		//将拷贝完成之后的文件夹改名改名
		File file1 = new File("D:\\backup\\cognosFile");
		File rename = new File("D:\\backup\\cognosFile"+dataStr) ;
		// 将原文件夹更改为A，其中路径是必要的。注意
		file1.renameTo(rename);
		System.out.println("cognosFile改名成功，改名之后文件名为【" + rename.getName()+"】 ,文件对应路径为" + rename);
		logger.info("cognosFile改名成功，改名之后文件名为：【" + rename.getName()+"】 ,文件对应路径为" + rename);
	}
	
	

	public  void copy(File file, File toFile)  {

		byte[] b = new byte[1024];
		int a;
		FileInputStream fis;
		FileOutputStream fos;
		try {
			if (file.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath = filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath = toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());
				File copy = new File(toFilepath);
				// 复制文件夹
				if (!copy.exists()) {
					copy.mkdir();
				}
				// 遍历文件夹
				for (File f : file.listFiles()) {
					copy(f, copy);
				}
			} else {
				if (toFile.isDirectory()) {
					String filepath = file.getAbsolutePath();
					filepath = filepath.replaceAll("\\\\", "/");
					String toFilepath = toFile.getAbsolutePath();
					toFilepath = toFilepath.replaceAll("\\\\", "/");
					int lastIndexOf = filepath.lastIndexOf("/");
					toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());
					// 写文件
					File newFile = new File(toFilepath);
					fis = new FileInputStream(file);
					fos = new FileOutputStream(newFile);
					while ((a = fis.read(b)) != -1) {
						fos.write(b, 0, a);
					}
				} else {
					// 写文件
					fis = new FileInputStream(file);
					fos = new FileOutputStream(toFile);
					while ((a = fis.read(b)) != -1) {
						fos.write(b, 0, a);
					}
				}
				//关闭一定不能忘记
				fis.close();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public  void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	
	public  boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	}
}
