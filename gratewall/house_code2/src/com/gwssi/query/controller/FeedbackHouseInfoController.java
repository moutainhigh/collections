package com.gwssi.query.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
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
		String fangWuDiZhi = request.getParameter("fangWuDiZhi");
		fangWuDiZhi=URLDecoder.decode(fangWuDiZhi, "utf-8");
		String jingBanRen = request.getParameter("jingBanRen");
		jingBanRen=URLDecoder.decode(jingBanRen, "utf-8");
		String jingBanRenShouJi = request.getParameter("jingBanRenShouJi");
		jingBanRenShouJi=URLDecoder.decode(jingBanRenShouJi, "utf-8");
		String fanKuiYuanYin = request.getParameter("fanKuiYuanYin");
		fanKuiYuanYin=URLDecoder.decode(fanKuiYuanYin, "utf-8");
		String fangWuDiZhiMiaoShu = request.getParameter("fangWuDiZhiMiaoShu");
		fangWuDiZhiMiaoShu=URLDecoder.decode(fangWuDiZhiMiaoShu, "utf-8");
		String qiDengYeWuBianHao=req.getParameter("qiDengYeWuBianHao");
		qiDengYeWuBianHao=URLDecoder.decode(qiDengYeWuBianHao,"utf-8");
		String yanZhengMa=req.getParameter("yanZhengMa");
		yanZhengMa=URLDecoder.decode(yanZhengMa,"utf-8");
		String qu1=request.getParameter("qu1");
		qu1=URLDecoder.decode(qu1,"utf-8");
		String jieDao1=request.getParameter("jieDao1");
		jieDao1=URLDecoder.decode(jieDao1,"utf-8");
		String sheQu1=request.getParameter("sheQu1");
		sheQu1=URLDecoder.decode(sheQu1,"utf-8");
		Map map=new HashMap();
		map.put("qiYeMingCheng", isEmpty(qiYeMingCheng));
		map.put("fangWuDiZhi", isEmpty(fangWuDiZhi));
		map.put("jingBanRen", isEmpty(jingBanRen));
		map.put("jingBanRenShouJi", isEmpty(jingBanRenShouJi));
		map.put("fanKuiYuanYin", isEmpty(fanKuiYuanYin));
		map.put("fangWuDiZhiMiaoShu", isEmpty(fangWuDiZhiMiaoShu));
		map.put("qiDengYeWuBianHao", isEmpty(qiDengYeWuBianHao));
		map.put("yanZhengMa", yanZhengMa);
		map.put("qu1",isEmpty(qu1));
		map.put("jieDao1", isEmpty(jieDao1));
		map.put("sheQu1",isEmpty(sheQu1));
		String rspcode = feedbackHouseInfoService.feedbackInfo(map,request);
		res.addAttr("msg",rspcode);
	}
	
	public String isEmpty(String str){
		return str==null||str.length()==0?"null":str.trim();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/queryFeedback")
	public void queryFeedback(OptimusRequest req,OptimusResponse res) throws UnsupportedEncodingException{
		HttpServletRequest request = req.getHttpRequest();
		String qiYeMingCheng = request.getParameter("qiYeMingCheng");
		qiYeMingCheng=URLDecoder.decode(qiYeMingCheng, "utf-8");
		String fangWuDiZhi = request.getParameter("fangWuDiZhi");
		fangWuDiZhi=URLDecoder.decode(fangWuDiZhi, "utf-8");
		String jingBanRen = request.getParameter("jingBanRen");
		jingBanRen=URLDecoder.decode(jingBanRen, "utf-8");
		String jingBanRenShouJi = request.getParameter("jingBanRenShouJi");
		jingBanRenShouJi=URLDecoder.decode(jingBanRenShouJi, "utf-8");
		String fanKuiYuanYin = request.getParameter("fanKuiYuanYin");
		fanKuiYuanYin=URLDecoder.decode(fanKuiYuanYin, "utf-8");
		String fangWuDiZhiMiaoShu = request.getParameter("fangWuDiZhiMiaoShu");
		fangWuDiZhiMiaoShu=URLDecoder.decode(fangWuDiZhiMiaoShu, "utf-8");
		String qiDengYeWuBianHao=req.getParameter("qiDengYeWuBianHao");
		qiDengYeWuBianHao=URLDecoder.decode(qiDengYeWuBianHao,"utf-8");
		String qu1=req.getParameter("qu1");
		qu1=URLDecoder.decode(qu1,"utf-8");
		String jieDao1=req.getParameter("jieDao1");
		jieDao1=URLDecoder.decode(jieDao1,"utf-8");
		String sheQu1=req.getParameter("sheQu1");
		sheQu1=URLDecoder.decode(sheQu1,"utf-8");
		Map map=new HashMap();
		map.put("qiYeMingCheng", qiYeMingCheng);
		map.put("fangWuDiZhi", fangWuDiZhi);
		map.put("jingBanRen", jingBanRen);
		map.put("jingBanRenShouJi", jingBanRenShouJi);
		map.put("fanKuiYuanYin", fanKuiYuanYin);
		map.put("fangWuDiZhiMiaoShu", fangWuDiZhiMiaoShu);
		map.put("qiDengYeWuBianHao", qiDengYeWuBianHao);
		map.put("qu1",qu1);
		map.put("jieDao1", jieDao1);
		map.put("sheQu1",sheQu1);
		Map maps=feedbackHouseInfoService.queryFeedback(map,request);
		try {
			res.addAttr("msg",maps);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	
}
