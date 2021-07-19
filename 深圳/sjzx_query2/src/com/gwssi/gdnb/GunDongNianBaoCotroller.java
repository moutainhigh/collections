package com.gwssi.gdnb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.gwssi.gdnb.service.GunDongNianBaoService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@RequestMapping("gdnb")
@Controller
public class GunDongNianBaoCotroller {
	SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
	Date now=new Date();
	
	
	@Autowired
	private GunDongNianBaoService gunDongNianBaoService;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/gdnbDetail")
	public void getZhfxInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String timeStr = req.getParameter("time");
		if(StringUtils.isEmpty(timeStr)) {
			timeStr = myFmt1.format(now);
		}
		timeStr = timeStr.replace("-","");
		List<Map> zhfxInfo = gunDongNianBaoService.queryCode_value(timeStr);
		resp.addResponseBody(JSONArray.toJSONString(zhfxInfo));
	} 
	
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("/gdnbSon")
	public void gdnbSon(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String code = req.getParameter("code");
		String timeStr = req.getParameter("time");
		
		if(StringUtils.isEmpty(timeStr)) {
			timeStr = myFmt1.format(now);
		}
		
		timeStr = timeStr.replace("-","");
		if(StringUtils.isNotEmpty(code)) {
			List<Map> zhfxInfo = gunDongNianBaoService.queryGdnbSon(code,timeStr);
			resp.addResponseBody(JSONArray.toJSONString(zhfxInfo));
		}else {
			Map map = new HashMap();
			map.put("msg", "部门编码不能为空");
			resp.addResponseBody(JSONArray.toJSONString(map));
		}
	
	} 
	
	
	@RequestMapping("/queryChuShi")
	public void queryChuShi(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		/*String code = req.getParameter("code");
		String timeStr = req.getParameter("time");
		
		if(StringUtils.isEmpty(timeStr)) {
			timeStr = myFmt1.format(now);
		}
		
		timeStr = timeStr.replace("-","");
		if(StringUtils.isNotEmpty(code)) {
		
		}else {
			Map map = new HashMap();
			map.put("msg", "部门编码不能为空");
			resp.addResponseBody(JSONArray.toJSONString(map));
		}*/
	
		
		
		List<Map> zhfxInfo = gunDongNianBaoService.queryQuShi();
		resp.addResponseBody(JSONArray.toJSONString(zhfxInfo));
	} 
}
