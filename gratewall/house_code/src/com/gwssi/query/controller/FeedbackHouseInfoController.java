package com.gwssi.query.controller;


import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.query.service.FeedbackHouseInfoService;


@Controller
@RequestMapping("/feedbackController")
public class FeedbackHouseInfoController {
	@Autowired
	private FeedbackHouseInfoService feedbackHouseInfoService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/feedbackInfo")
	public void feedbackInfo(OptimusRequest req,OptimusResponse res) throws Exception{
		
		HttpServletRequest request = req.getHttpRequest();
		
		String qiYeMingCheng = request.getParameter("qiYeMingCheng");
		qiYeMingCheng=URLDecoder.decode(qiYeMingCheng, "utf-8");
		String zhuSuo = request.getParameter("zhuSuo");
		zhuSuo=URLDecoder.decode(zhuSuo, "utf-8");
		String jingBanRen = request.getParameter("jingBanRen");
		jingBanRen=URLDecoder.decode(jingBanRen, "utf-8");
		String jingBanRenShouJi = request.getParameter("jingBanRenShouJi");
		jingBanRenShouJi=URLDecoder.decode(jingBanRenShouJi, "utf-8");
		String fangWuBianMa = request.getParameter("fangWuBianMa");
		fangWuBianMa=URLDecoder.decode(fangWuBianMa, "utf-8");
		String wenTiMiaoShu = request.getParameter("wenTiMiaoShu");
		wenTiMiaoShu=URLDecoder.decode(wenTiMiaoShu, "utf-8");
		Map map=new HashMap();
		map.put("qiYeMingCheng", isEmpty(qiYeMingCheng));
		map.put("zhuSuo", isEmpty(zhuSuo));
		map.put("jingBanRen", isEmpty(jingBanRen));
		map.put("jingBanRenShouJi", isEmpty(jingBanRenShouJi));
		map.put("fangWuBianMa", isEmpty(fangWuBianMa));
		map.put("wenTiMiaoShu", isEmpty(wenTiMiaoShu));
		String rspcode = feedbackHouseInfoService.feedbackInfo(map,request);
		res.addAttr("msg",rspcode);
	}
	
	public String isEmpty(String str){
		return str==null||str.length()==0?"null":str;
	}
}
