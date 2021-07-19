package com.ly.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.common.utils.Page;
import com.ly.dao.DayLineUpByAutoDao;
import com.ly.dao.WeekLineUpByAutoDao;
import com.ly.pojo.StockDayLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;

@Controller
@RequestMapping("services")
public class DayUpController {
	
	@Autowired
	private DayLineUpByAutoDao dayLineUpByAutoDao;

	@RequestMapping("/dayUpList")
	@ResponseBody
	public Page<StockDayLineUpByAuto> getWeekList(String pageNum,String pageSize) {
		Map map = new HashMap();
		
		int pageNo = 1;
		int size = 10;
		if (pageNum != null&&!pageNum.equals("0")) {
			pageNo = Integer.valueOf(pageNum);
		}
		if (pageSize != null&&!pageSize.equals("0")) {
			size = Integer.valueOf(pageSize);
		}
		
		
		int firstIndex = (pageNo - 1) * size;
        //到第几条数据结束
        int lastIndex = pageNo * size;
		
		
		map.put("pageIndex",firstIndex );
		map.put("pageSize",size );
		List list = dayLineUpByAutoDao.getAllByParam(map);
		int total = dayLineUpByAutoDao.getCountByParam(map);

		Page<StockDayLineUpByAuto> pages = new Page<StockDayLineUpByAuto>();
		pages.setPageNo(firstIndex/size+1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}
}
