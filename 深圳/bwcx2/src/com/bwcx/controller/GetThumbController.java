package com.bwcx.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwcx.dao.DocumentDao;
import com.bwcx.pojo.DocumentEntity;

@RestController
public class GetThumbController {

	@Autowired
	private DocumentDao documentDao;
	
	@RequestMapping(value="/pic/{docId}")
	public void showPic(HttpServletRequest request,@PathVariable String docId,HttpServletResponse response){
		Map map = new HashMap();
		map.put("docId", docId);
		DocumentEntity de = documentDao.getDocumentDetail(map);
		String fileName = de.getFilename();
		
		
		//String uploadURL = "c:\\upload_bwcx\\" + docId + ".png";
		String uploadURL = "c:\\upload_bwcx\\" + fileName ;
		
		try {
			File filePath = new File(uploadURL);
			if(filePath.exists()){
				//读图片
				FileInputStream inputStream = new FileInputStream(filePath);  
		        int available = inputStream.available();
		        byte[] data = new byte[available];
		        inputStream.read(data);  
		        inputStream.close();  
		        //写图片
		        response.setContentType("image/png");  
		        response.setCharacterEncoding("UTF-8");
		        OutputStream stream = new BufferedOutputStream(response.getOutputStream());  
		        stream.write(data);  
		        stream.flush();  
		        stream.close();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
