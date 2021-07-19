package com.ly.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.common.utils.Page;
import com.ly.dao.MonthLineUpByAutoDao;
import com.ly.dao.WeekLineUpByAutoDao;
import com.ly.pojo.Buy;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;

//https://blog.csdn.net/baidu_25343343/article/details/70478467
//https://www.cnblogs.com/iwang5566/p/6421805.html
//http://www.cnblogs.com/knowledgesea/archive/2013/01/03/2841554.html
//http://www.cnblogs.com/knowledgesea/archive/2012/05/19/2508924.html
//http://www.cnblogs.com/jingping/p/3925976.html
//https://blog.csdn.net/HezhezhiyuLe/article/details/82757881
//https://blog.csdn.net/m0_38082783/article/details/73737283
@Controller
@RequestMapping("services")
public class MonthController {

	@Autowired
	private MonthLineUpByAutoDao weekLineUpStockInfoDao;

	@RequestMapping("/monthList")
	@ResponseBody
	public Page<StockMonthLineUpByAuto> getMonthUpList(String pageNum,String pageSize) {
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
		List list = weekLineUpStockInfoDao.getAllByParam(map);
		int total = weekLineUpStockInfoDao.getCountByParam(map);

		Page<StockMonthLineUpByAuto> pages = new Page<StockMonthLineUpByAuto>();
		pages.setPageNo(firstIndex/size+1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}
}
