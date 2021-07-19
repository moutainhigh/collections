package com.bwcx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwcx.dao.DocumentDao;
import com.bwcx.pojo.DocumentEntity;
import com.bwcx.utils.UUIDUtils;


@RestController
public class ContentController {

	@Autowired
	private DocumentDao documentDao;
	
	@RequestMapping("/getDetail")
	public Map getDetail(String docid) {
		Map map  = new HashMap();
		Map ret = new HashMap();
		if(StringUtils.isNotEmpty(docid)) {
			map.put("docId", docid);
			DocumentEntity doc = documentDao.getDocumentDetail(map);
			ret.put("ret",doc);
		}
		return ret;
		
	}
	
	@RequestMapping("/updateThumb")
	public String saveThumb(String docId,String fileName) {
		Map map = new HashMap();
		map.put("docid", docId);
		String thumURL = "pic/"+docId+".do";
		map.put("thumburl", thumURL);
		map.put("isHaveThumb", 1);
		map.put("filename", fileName);
		String ret = "0";
		try {
			documentDao.saveThumb(map);
			ret = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ret;
		
	}
	
	
	@RequestMapping("/updateIsFile")
	public String updateIsFile(String docId,String fileName) {
		Map map = new HashMap();
		map.put("docid", docId);
		String thumURL = "attach/"+docId+".do";
		map.put("attachfilesavepath", thumURL);
		map.put("isFile", 1);
		map.put("filename", fileName);
		String ret = "0";
		try {
			documentDao.saveFile(map);
			ret = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ret;
		
	}
	
	
	@RequestMapping("/save")
	public Map saveConent(DocumentEntity doc) {
		Map map = new HashMap();
		if(doc!=null) {
			/*String did = doc.getDocid();
			if(StringUtils.isEmpty(did)) {
				String id = UUIDUtils.getUUID();
				doc.setDocid(id);
			}*/
			String tempStr = doc.getContents();
			StringBuffer sb = new StringBuffer();
			StringBuffer sbEd = new StringBuffer();
			String [] temp = tempStr.split("\n");
			for (String string : temp) {
				sb.append("<p style='text-indent: 2em; margin-bottom: 15px;' >"+string+"</p>");
				sbEd.append("<p>"+string+"</p>");
			}
			
			//<p style='text-indent: 2em; margin-bottom: 15px;' ><p><videocontrols width="420" height="280" src="/bwcx//ueditor/jsp/upload/video/20190805/1564989964962031393.flv" data-setup="{}"></videocontrols></p></p>
				
				String orginStr = sb.toString();
				if(orginStr.contains("videocontrols")) {
					orginStr= orginStr.replace("<videocontrols width='420' height='280'", "<video controls width='1244' height='650'");
					orginStr = orginStr.replace("</videocontrols>", "</video>");	
				}
				
				
				if(orginStr.contains(orginStr)) {
					orginStr = orginStr.replace("<audio ", "<audio  style='display:table;margin:0 auto'");
				}
				
				
				
				doc.setTranformeContents(orginStr);
				
				
				
				
				//doc.setTranformeContents(sb.toString());
			
			
			doc.setContentToEd(sbEd.toString());
			
			
			try {
				documentDao.save(doc);
				map.put("msg", "success");
			}catch (Exception e) {
				e.printStackTrace();
				map.put("msg", "fail");
			}
		}
		
		return map;
	}
	
	
	@RequestMapping("/getDocsByChannel")
	public Map getDocsByChannel(String  cid,String pageNo,String pageSize) {
		Map map = new HashMap();
		Map ret = new HashMap();
		
		if(StringUtils.isEmpty(cid)) {
			cid = "1";
		}
		map.put("cid", cid);
		
		if(StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}

		if(StringUtils.isEmpty(pageSize)) {
			pageSize = "4";
		}
		
		 int parseSize = Integer.parseInt(pageSize);
		 int parsePage = Integer.parseInt(pageNo);
		 int begin = (parsePage-1) * parseSize;
		 int end = parsePage * parseSize;
		 
		 map.put("pageBegin", begin);
		 map.put("pageEnd", end);
		
		List list = documentDao.getDocumentsByChannel(map);
		Integer count = documentDao.getCount(map);
		ret.put("list", list);
		ret.put("count", count);
		return ret;
	}
	
	
	@RequestMapping("/getPubDocsByChannel")
	public Map getPuvDocsByChannel(String  cid,String pageNo,String pageSize) {
		Map map = new HashMap();
		Map ret = new HashMap();
		
		if(StringUtils.isEmpty(cid)) {
			cid = "1";
		}
		map.put("cid", cid);
		
		if(StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}

		if(StringUtils.isEmpty(pageSize)) {
			pageSize = "4";
		}
		
		 int parseSize = Integer.parseInt(pageSize);
		 int parsePage = Integer.parseInt(pageNo);
		 int begin = (parsePage-1) * parseSize;
		 int end = parsePage * parseSize;
		 
		 map.put("pageBegin", begin);
		 map.put("pageEnd", end);
		
		List list = documentDao.getPubDocumentsByChannel(map);
		Integer count = documentDao.getCount(map);
		ret.put("list", list);
		ret.put("count", count);
		return ret;
	}
	
	
	
	@RequestMapping("/getDocsByChannelForMore")
	public Map getDocsByChannelForMore(String  cid,String pageNo,String pageSize,String keyword) {
		Map map = new HashMap();
		Map ret = new HashMap();
		
		if(StringUtils.isEmpty(cid)) {
			cid = "1";
		}
		map.put("cid", cid);
		
		if(StringUtils.isEmpty(keyword)) {
			map.put("mytitle", "");
		}else {
			map.put("mytitle", keyword);
		}
		
		System.out.println("====> " +keyword);
		
		if(StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}

		if(StringUtils.isEmpty(pageSize)) {
			pageSize = "4";
		}
		
		 int parseSize = Integer.parseInt(pageSize);
		 int parsePage = Integer.parseInt(pageNo);
		 int begin = (parsePage-1) * parseSize;
		 int end = parsePage * parseSize;
		 
		 map.put("pageBegin", begin);
		 map.put("pageEnd", end);
		
		List list = documentDao.getPubDocumentsByChannel(map);
		Integer count = documentDao.getPubCount(map);
		ret.put("list", list);
		ret.put("count", count);
		return ret;
	}
	
	
	
	@RequestMapping("/getPubDocusWithThumb")
	public Map getDocumtsWithThumb(String  cid,String thum,String pageNo,String pageSize) {
		Map map = new HashMap();
		Map ret = new HashMap();
		if(StringUtils.isEmpty(cid)) {
			cid = "1";
		}
		
		
		if(StringUtils.isEmpty(thum)) {
			thum = "0";
		}
		
		map.put("cid", cid);
		
		if(StringUtils.isEmpty(pageNo)) {
			pageNo = "1";
		}

		if(StringUtils.isEmpty(pageSize)) {
			pageSize = "4";
		}
		 
		 map.put("cid", cid);
		 map.put("thum", thum);
		 int parseSize = Integer.parseInt(pageSize);
		 int parsePage = Integer.parseInt(pageNo);
		 int begin = (parsePage-1) * parseSize;
		 int end = parsePage * parseSize;
		 
		 map.put("pageBegin", begin);
		 map.put("pageEnd", end);
		 
		
		List list = documentDao.getPubDocusWithThumb(map);
		ret.put("list", list);
		return ret;
	}
	
	@RequestMapping("/del")
	public void del(String docid) {
		List list = new ArrayList();
		
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(docid)) {
			list.add(docid);
		}
		if(list!=null&&list.size()>0) {
			documentDao.deleteByBatch(list);
		}
		
		
	}
	
	
	
}
