/**
 * Project Name:tzupload
 * File Name:TzUploadController.java
 * Package Name:tzupload
 * Date:2015��11��5������9:09:13
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.tz.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tz.util.TmFileUtil;
import com.tz.util.TzConstant;
import com.tz.util.date.TmDateUtil;

/**
 * ClassName:TzUploadController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年11月5日 下午9:09:13 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
@Controller
@RequestMapping("/upload")
public class TzUploadController extends BaseController{
	
	@ResponseBody /*上传成功以后以response.getWriter()进入输出数据*/
	@RequestMapping(value = "/file")
	public String tzupload(@RequestParam("doc") MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException, JSONException {
		if(!file.isEmpty()){
			String dirPath = request.getParameter("dir");
			String ymd = TmDateUtil.getCurrentDate("yyyyMMdd");
			String rpath = "/exam/"+dirPath+"/"+ymd;
			String path = request.getRealPath(rpath);
			File parent = new File(path);
			if(!parent.exists())parent.mkdirs();
//			System.out.println("获取文件数据================="+file.getBytes());
//			System.out.println("获取文件的MINE类型================="+file.getContentType());
//			System.out.println("获取文件的名称================="+file.getName());
//			System.out.println("获取上传文件的原名================="+file.getOriginalFilename());
//			System.out.println("获取文件的大小================="+file.getSize());
//			System.out.println("判断是否有文件上传================="+file.isEmpty());
			HashMap<String, Object> map = new HashMap<String,Object>();
			String oldName = file.getOriginalFilename();
			long size = file.getSize();
			String sizeString = TmFileUtil.countFileSize(size);
			String ext = TmFileUtil.getExtNoPoint(oldName);
			String newFileName = TmFileUtil.generateFileName(oldName, 10,getUserId(),"yyyyMMddHHmmss");
			String url = rpath+"/"+newFileName;
			file.transferTo(new File(parent, newFileName));
			map.put("oldname",oldName);
			map.put("ext",ext);
			map.put("sizeString",sizeString);
			map.put("size",size);
			map.put("name",newFileName);
			map.put("url",url);
			return JSONUtil.serialize(map);
		}else{
			return null;
		}
	}
	
}
