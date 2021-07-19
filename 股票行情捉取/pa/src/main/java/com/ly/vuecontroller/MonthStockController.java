package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.utils.Page;
import com.ly.dao.MonthLineUpByAutoDao;
import com.ly.pojo.StockMonthLineUpByAuto;

@RestController
@RequestMapping("api")
public class MonthStockController {
	@Autowired
	private MonthLineUpByAutoDao weekLineUpStockInfoDao;

	@RequestMapping("changxianList")
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
