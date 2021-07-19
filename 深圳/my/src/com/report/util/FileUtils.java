package com.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//https://blog.csdn.net/youyou_yo/article/details/51980351
//https://blog.csdn.net/qq_36675996/article/details/80815815
public class FileUtils {
	  //压缩文件    
    public static void ZipFiles(File[] srcfile, File zipfile) {    
        byte[] buf = new byte[1024];    
        try {    
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));    
            for (int i = 0; i < srcfile.length; i++) {    
                FileInputStream in = new FileInputStream(srcfile[i]);    
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));    
                int len;    
                while ((len = in.read(buf)) > 0) {    
                    out.write(buf, 0, len);    
                }    
                out.closeEntry();    
                in.close();    
            }    
            out.close();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }  


    /*** 
     * 删除指定文件夹下所有文件 
     *  
     * @param path 文件夹完整绝对路径 
     * @return 
     */  
    public static  boolean delAllFile(String path) {  
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
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                flag = true;  
            }  
        }  
        return flag;  
    } 
}
