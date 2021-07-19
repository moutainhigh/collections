package com.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.dao.RedisDao;
import com.report.service.CongYeRenYuanService;
import com.report.util.DataGrid;
import com.report.util.PageInfo;
import com.report.util.UUIDUtils;
import com.report.util.excell.CYRYUtil;
import com.report.util.excell.Data01ExcelUtil;

@Controller
public class ChongYeRenYuanController {

	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private CongYeRenYuanService congYeRenYuanService;
	
	@RequestMapping(value = "/ry")
	public String doIndex() {
		return "ry";
	}

	
	
	
	@ResponseBody
	@RequestMapping(value="/listry")
	public DataGrid  getItemsListForDown(String time,PageInfo dgparam) {
		System.out.println(dgparam.toString());
		List list = congYeRenYuanService.getFileToDown(time,dgparam);
		Integer total = congYeRenYuanService.getTotal(time,dgparam);
		DataGrid dg = new DataGrid();
		dg.setRows(list);
		dg.setTotal(total);
		return dg;
	}
	
	
	
	
	//http://localhost:8080/my/downry?startTime=2019-01-31
	@RequestMapping("/downry")
	public void down(HttpServletResponse response, String startTime) throws IOException, ParseException {
		List<String> fileNames = new ArrayList<String>();
		String fileOutPath = "c://downs//" + startTime + "//";
		File file = new File(fileOutPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String fileName = fileOutPath;
		List list01 = redisDao.get("cyry" + startTime, List.class);
		String file01 = fileName + "业务数据【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";
		
		fileNames.add(file01);
		
		CYRYUtil.createExcell(list01,file01);
		
		
		String zipFilePath = fileOutPath + "data" + startTime + ".zip";
		// 导出zip
		File zip = new File(zipFilePath);
		// 将excel文件生成压缩文件
		File srcfile[] = new File[fileNames.size()];
		for (int j = 0, n1 = fileNames.size(); j < n1; j++) {
			srcfile[j] = new File(fileNames.get(j));
		}
		ZipFiles(srcfile, zip);

		response.setContentType("application/zip");
		response.setHeader("Location", zip.getName());
		response.setHeader("Content-Disposition", "attachment; filename=" + zip.getName());
		OutputStream outputStream = response.getOutputStream();
		InputStream inputStream = new FileInputStream(zipFilePath);
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();

		try {
			delAllFile(fileOutPath); // 删除完里面所有内容
			java.io.File myFilePath = new java.io.File(fileOutPath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
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
