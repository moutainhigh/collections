package com.bwcx.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.mime.MimeUtility;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwcx.dao.DocumentDao;
import com.bwcx.pojo.DocumentEntity;

import nl.bitwalker.useragentutils.UserAgent;

@RestController
public class GetAttachController {

	
	@Autowired
	private DocumentDao documentDao;
	
		/*
		
		@RequestMapping(value="/attach/{docId}")
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
		        response.setContentType("multipart/form-data");
		        String downloadFileName = new String(fileName.getBytes("UTF-8"), "UTF-8");
		        response.setHeader("Content-Disposition", "attachment;fileName=" + downloadFileName);
		        response.setCharacterEncoding("UTF-8");
		        OutputStream stream = new BufferedOutputStream(response.getOutputStream());  
		        stream.write(data);  
		        stream.flush();  
		        stream.close();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}*/
		
			/*@RequestMapping(value="/attach/{docId}")
		  public ResponseEntity<byte[]> export(@PathVariable String docId) throws IOException {  
			    Map map = new HashMap();
				map.put("docId", docId);
				DocumentEntity de = documentDao.getDocumentDetail(map);
				String fileName = de.getFilename();
		        HttpHeaders headers = new HttpHeaders();   
		        
		        
		        String uploadURL = "c:\\upload_bwcx\\" + fileName ;
		        
		        File file = new File(uploadURL);
		        
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
		        headers.setContentDispositionFormData("attachment", fileName);    
		       
		        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
		                                              headers, HttpStatus.CREATED);    
		    }*/
		
		
		
		@RequestMapping(value="/attach/{docId}")
		 public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request,@PathVariable String docId)  throws IOException { 
				Map map = new HashMap();
				map.put("docId", docId);
				DocumentEntity de = documentDao.getDocumentDetail(map);
				String fileName = de.getFilename();
				String filePath = "c:\\upload_bwcx\\" + fileName ;
		        FileSystemResource file = new FileSystemResource(filePath); 
		        //文件名编码，解决乱码问题
		       // String fileName = file.getFilename();
		        
		        //解决文件名乱码问题
		       // String  fileName = URLEncoder.encode(file.getFilename(), StandardCharsets.UTF_8.toString());
		        String userAgentString = request.getHeader("User-Agent");
		        String browser = UserAgent.parseUserAgentString(userAgentString).getBrowser().getGroup().getName();
		       // String t=browser;
		        if(browser.equals("Chrome") || browser.equals("Internet Exploer") || browser.equals("Safari")) {
		        	fileName = URLEncoder.encode(fileName,"utf-8").replaceAll("\\+", "%20");
		        } else {
		        	fileName = MimeUtility.decodeText(fileName) ;  //encodeWord(fileName);
		        }

		        HttpHeaders headers = new HttpHeaders();  
		        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");   
		        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName)); 
		        headers.add("Pragma", "no-cache");  
		        headers.add("Expires", "0");  
		        return ResponseEntity  
		                .ok()  
		                .headers(headers)  
		                .contentLength(file.contentLength())  
		                .contentType(MediaType.parseMediaType("application/octet-stream"))  
		                .body(new InputStreamResource(file.getInputStream()));  
		    } 
}
