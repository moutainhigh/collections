package com.gwssi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.dao.EBaseinfoDao;

@Controller
public class LoadingDataController {
	private static Logger logger = Logger.getLogger(LoadingDataController.class);

	@Autowired
	private EBaseinfoDao ebdao;

	@RequestMapping({ "grid03" })
	@ResponseBody
	public Map getTableGrid03(String page, String rows) {
		logger.debug("grid03===>");
		Map paramMap = new HashMap();
		int start = Integer.valueOf(page).intValue();
		int pageSize = Integer.valueOf(rows).intValue();

		int end = start * pageSize;
		start = (start - 1) * pageSize;

		paramMap.put("pageBegin", Integer.valueOf(start));
		paramMap.put("pageEnd", Integer.valueOf(end));

		Map resultMap = new HashMap();

		Integer total = ebdao.data03Total(paramMap);
		List list = ebdao.data03(paramMap);

		resultMap.put("total", total);
		resultMap.put("rows", list);

		return resultMap;
	}
	

	@RequestMapping({ "grid06" })
	@ResponseBody
	public Map getTableGrid06(String page, String rows) {
		logger.debug("grid06===>");
		Map paramMap = new HashMap();
		int start = Integer.valueOf(page).intValue();
		int pageSize = Integer.valueOf(rows).intValue();

		int end = start * pageSize;
		start = (start - 1) * pageSize;

		paramMap.put("pageBegin", Integer.valueOf(start));
		paramMap.put("pageEnd", Integer.valueOf(end));

		Map resultMap = new HashMap();

		Integer total = ebdao.data06Total(paramMap);
		List list = ebdao.data06(paramMap);
		// System.out.println(total);

		resultMap.put("total", total);
		resultMap.put("rows", list);

		return resultMap;
	}
	
	
	
	
	@RequestMapping({ "grid20" })
	@ResponseBody
	public Map getTableGrid20(String page, String rows) {
		logger.debug("grid20===>6100");
		Map paramMap = new HashMap();
		int start = Integer.valueOf(page).intValue();
		int pageSize = Integer.valueOf(rows).intValue();

		int end = start * pageSize;
		start = (start - 1) * pageSize;

		paramMap.put("pageBegin", Integer.valueOf(start));
		paramMap.put("pageEnd", Integer.valueOf(end));

		Map resultMap = new HashMap();

		Integer total = ebdao.data20Total(paramMap);
		List list = ebdao.data20(paramMap);
		// System.out.println(total);

		resultMap.put("total", total);
		resultMap.put("rows", list);

		return resultMap;
	}
	
	
	@RequestMapping({ "grid21" })
	@ResponseBody
	public Map getTableGrid21(String page, String rows) {
		logger.debug("grid21===>6299");
		Map paramMap = new HashMap();
		int start = Integer.valueOf(page).intValue();
		int pageSize = Integer.valueOf(rows).intValue();

		int end = start * pageSize;
		start = (start - 1) * pageSize;

		paramMap.put("pageBegin", Integer.valueOf(start));
		paramMap.put("pageEnd", Integer.valueOf(end));

		Map resultMap = new HashMap();

		Integer total = ebdao.data21Total(paramMap);
		List list = ebdao.data21(paramMap);
		// System.out.println(total);

		resultMap.put("total", total);
		resultMap.put("rows", list);

		return resultMap;
	}
}