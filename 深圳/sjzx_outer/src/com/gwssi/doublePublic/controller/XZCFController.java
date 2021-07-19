package com.gwssi.doublePublic.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.doublePublic.service.XZCFService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

//行政处罚
@Controller
@RequestMapping("/xzcf")
public class XZCFController extends BaseController {
	private static Logger logger = Logger.getLogger(XZXKController.class);

	@Autowired
	private XZCFService service;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/loading_xzcf_list")
	public void getXZXKList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		logger.info("查询行政处罚");

		String pageIndex = req.getParameter("pageIndex");
		String pageSize = req.getParameter("pageSize");
		String keyWord = req.getParameter("keyword");
		//System.out.println(keyWord);
		
		if(keyWord!=null) {
			keyWord = keyWord.trim();//去空
		}
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}

		List list = service.getList(pageIndex, pageSize,keyWord);
		if (list != null && list.size() > 0) {
			resp.addAttr("list", list);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getXZCFDetail")
	public void getXZCFDetail(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		logger.info("查询行政处罚详情");

		String id = req.getParameter("id");
		Map map = service.findDetailById(id);
		resp.addAttr("mapData", map);
	}
	

}
