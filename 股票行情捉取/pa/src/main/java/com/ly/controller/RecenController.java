package com.ly.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.common.utils.Page;
import com.ly.dao.RecentStockDao;
import com.ly.pojo.RecentStock;
import com.ly.pojo.StockWeekLineUpByAuto;

@Component
@RequestMapping("wait")
public class RecenController {

	@Autowired
	private RecentStockDao recentStockDao;

	@RequestMapping("/list")
	@ResponseBody
	public Page<RecentStock> getBuy(String pageNum, String pageSize) {
		Map map = new HashMap();

		int pageNo = 1;
		int size = 10;
		if (pageNum != null && !pageNum.equals("0")) {
			pageNo = Integer.valueOf(pageNum);
		}
		if (pageSize != null && !pageSize.equals("0")) {
			size = Integer.valueOf(pageSize);
		}

		int firstIndex = (pageNo - 1) * size;
		// 到第几条数据结束
		int lastIndex = pageNo * size;

		map.put("pageIndex", firstIndex);
		map.put("pageSize", size);
		List list = recentStockDao.getListByParam(map);
		int total = recentStockDao.getTotalByParam(map);
		Page<RecentStock> pages = new Page<RecentStock>();
		pages.setPageNo(firstIndex / size + 1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}
}
