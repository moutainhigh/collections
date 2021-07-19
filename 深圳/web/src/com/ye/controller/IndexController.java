package com.ye.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ye.SystemCode;
import com.ye.bus.ReportCacheToRedisBusAync;
import com.ye.redis.dao.RedisDao;


@RestController
public class IndexController extends BaseController{
	private static Logger logger = Logger.getLogger(IndexController.class); // 获取logger实例
	
	@Autowired
	private RedisDao redisDao;
	
	
	@Autowired
	private ReportCacheToRedisBusAync reportCacheToRedisBusAync;
	
	//https://www.cnblogs.com/commissar-Xia/p/7759484.html
	/*
	 * *：通配任意多个字符

		?：通配单个字符
		
		[]：通配括号内的某一个字符
	 */
	@RequestMapping("keys")
	public List   getKeys(String pattern) {
		Set<String> sets = redisDao.keys("*" + pattern + "*");
		List<String> keys = new ArrayList<String>();
		for (String string : sets) {
			keys.add(string);
		}
	 logger.debug("==> 清空redis ");
	  redisDao.delete(keys);
	  return keys;
	}
	
	
	
	
	
	@RequestMapping("create")
	public Map getDataByTransformer(String timeStr) {
		if(!StringUtils.isEmpty(timeStr)) {
			reportCacheToRedisBusAync.queryFromDbToRedis(timeStr);
			logger.debug("==> 请求生成中=.... ");
			return SystemCode.CODE_0000000();
		}else {
			return SystemCode.CODE_0000002();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	 //压缩文件    
   public void ZipFiles(File[] srcfile, File zipfile) {    
       byte[] buf = new byte[1024];    
       try {    
           ZipOutputStream out = new ZipOutputStream(new FileOutputStream(    
                   zipfile));    
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
