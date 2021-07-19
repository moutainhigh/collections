package com.gwssi.ebaic.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.common.service.BjcaService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.util.StringUtil;

import sun.misc.BASE64Decoder;

/**
 * 显示身份证照片。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/apply/rodimus/idPic")
public class IdPicController {
	
	@Autowired
	BjcaService bjcaService ; 
	
	@RequestMapping("/show")
	public void getFile(OptimusRequest request,OptimusResponse optimusResponse){
		String name = request.getParameter("name");  
		try {
			name = java.net.URLDecoder.decode(name,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String cerNo = request.getParameter("cerNo");
		IdentityCardBO bo = IdentityCardUtil.getIdentityCardInfo(name,cerNo); 
		HttpServletResponse resposne = optimusResponse.getHttpResponse();
		String picData = bo.getPicData();
		if(bo==null || StringUtil.isBlank(bo.getPicData()) ){
			resposne.setCharacterEncoding("UTF-8");
			resposne.setContentType("text/html;charset=utf-8");
			try {
				resposne.getWriter().write("Name or cerNo is wrong.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}
		if( StringUtil.isBlank(picData)){
			resposne.setCharacterEncoding("UTF-8");
			resposne.setContentType("text/html;charset=utf-8");
			try {
				resposne.getWriter().write("Name or cerNo is wrong.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream os = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(picData);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			os = resposne.getOutputStream();
			os.write(b);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
}
