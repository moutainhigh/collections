package com.gwssi.ebaic.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.pic.CertPicUtil;
import com.gwssi.rodimus.util.FileUtil;

/**
 * 执照图片。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/common/cert")
public class CertController {
	
	@RequestMapping("/show")
	public void show(OptimusRequest request,OptimusResponse response){
		try{
			String regNo = request.getParameter("regNo");
			String phyFilePath = CertPicUtil.genCertPicture(regNo);
	        String contentType = "";
	        String filename = "";
	        FileUtil.download(contentType, filename,"", phyFilePath, response.getHttpResponse());
		}catch(Exception e){
			e.printStackTrace();
			response.addResponseBody(e.getMessage());
		}
	}

	
	
}
