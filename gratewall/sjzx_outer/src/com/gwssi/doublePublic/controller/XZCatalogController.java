package com.gwssi.doublePublic.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.doublePublic.service.CatalogService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/catalog")
public class XZCatalogController {
	private static Logger logger = Logger.getLogger(XZXKController.class);

	@Autowired
	private CatalogService service;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/loading_catalog")
	public void getList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		logger.info("行政许可和行政处罚事项目录");

		String pageIndex = req.getParameter("pageIndex");
		String pageSize = req.getParameter("pageSize");
		String keyWord = req.getParameter("keyword");
		String title = req.getParameter("title");
		
		
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}

		System.out.println(" ==>" + keyWord);
		List<Map> list = service.getList(pageIndex, pageSize, keyWord,title);
		if (list != null && list.size() > 0) {
			resp.addAttr("list", list);
		}
	}

}
