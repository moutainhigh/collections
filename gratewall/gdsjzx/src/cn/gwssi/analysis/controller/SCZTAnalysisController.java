package cn.gwssi.analysis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.analysis.service.SCZTAnalysisService;

import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;



@Controller
@RequestMapping("/scztanalysis")
public class SCZTAnalysisController {

	@Autowired
	private SCZTAnalysisService service;
	// 市场主体概况
	@RequestMapping("/enterpriseCodeInfo")
	@ResponseBody
	public List<Map> getEnterpriseCodeInfo(OptimusRequest req,
			OptimusResponse res) {
		String jjxz= req.getParameter("jjxz");
		String qylx= req.getParameter("qylx");
		String zcxs= req.getParameter("zcxs");
		String cydl= req.getParameter("cydl");
		String cy= req.getParameter("cy");
		String zjgm= req.getParameter("zjgm");

		List<Map> list=service.getScztGK(jjxz,qylx,zcxs,cydl,cy,zjgm);

		return list;
	}	


}
