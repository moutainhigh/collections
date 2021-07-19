package com.gwssi.doublePublic.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.doublePublic.service.XZXKService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

//行政许可
@Controller
@RequestMapping("/xzxk")
public class XZXKController {
	private static Logger logger = Logger.getLogger(XZXKController.class);

	@Autowired
	private XZXKService service;

	@RequestMapping("/loading_xzxk_list")
	public void getList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		logger.info("查询行政许可列表");

		String pageIndex = req.getParameter("pageIndex");
		String pageSize = req.getParameter("pageSize");
		String keyWord = req.getParameter("keyword");
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
		
		if(keyWord!=null) {
			keyWord = keyWord.trim();//去空
		}
		
		//System.out.println(" ==>" + keyWord);
		List<Map> list = service.getList(pageIndex, pageSize,keyWord);
		if (list != null && list.size() > 0) {
			resp.addAttr("list", list);
		}
	}

	@RequestMapping("/getXZXKDetail")
	public void getXZCFDetail(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		logger.info("查询行政许可详情");

		String id = req.getParameter("id");
		Map map = service.findDetailById(id);

		resp.addAttr("mapData", map);

	}
}
