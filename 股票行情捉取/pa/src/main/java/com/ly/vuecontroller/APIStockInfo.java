package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.utils.Page;
import com.ly.dao.BuyDao;
import com.ly.dao.MonthLineUpByAutoDao;
import com.ly.dao.SellDao;
import com.ly.dao.StockDao;
import com.ly.dao.WeekLineUpByAutoDao;
import com.ly.dao.WeekLineUpByHandDao;
import com.ly.pojo.Buy;
import com.ly.pojo.Sell;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByHand;
import com.ly.pojo.Stocks;

@RestController
@RequestMapping("pro")
public class APIStockInfo {
	@Autowired
	private BuyDao buyDao;

	@Autowired
	private SellDao sellDao;

	@Autowired
	private WeekLineUpByAutoDao weekLineUpByAutoDao;

	@Autowired
	private WeekLineUpByHandDao weekLineUpByHandDao;

	@Autowired
	private MonthLineUpByAutoDao monthLineUpByAutoDao;
	
	@Autowired
	private StockDao stockDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editBuy")
	public Map editBuyInfo(Buy buy) {
		Map ret = new HashMap();
		Map params = new HashMap();
		params.put("stock", buy);
		
		List<Stocks> lists=  stockDao.findSingleStockByCodeForBuy(params);
		if(lists!=null&lists.size()>0){
			Stocks sts = lists.get(0);
			buy.setName(sts.getName());
			StockWeekLineUpByAuto auto = weekLineUpByAutoDao.getSingleByParam(params);
			if (auto != null) {
				auto.setIsAddToBuy("1");
				weekLineUpByAutoDao.save(auto);
			}
			StockWeekLineUpByHand hand = weekLineUpByHandDao.getSingleByParam(params);

			if (hand != null) {
				hand.setIsAddToBuy("1");
				weekLineUpByHandDao.save(hand);
			}

			StockMonthLineUpByAuto month = monthLineUpByAutoDao.getSingleByParam(params);

			if (month != null) {
				month.setIsAddToBuy("1");
				monthLineUpByAutoDao.save(month);
			}

			if (StringUtils.isNotEmpty(buy.getCode())) {
				try {
					buyDao.save(buy);
					ret.put("msg", "保存成功");
				} catch (Exception e) {
					ret.put("msg", "保存失败，请稍后再试");
				}
			} else {
				ret.put("msg", "代码不能为空");

			}
		}else{
			ret.put("msg", "代码不存在");
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editSell")
	public Map editSellInfo(Sell sell) {
		Map ret = new HashMap();
		Map params = new HashMap();
		params.put("stock", sell);
		List<Stocks> lists=  stockDao.findSingleStockByCodeForBuy(params);
		if(lists!=null&lists.size()>0){
			Stocks sts = lists.get(0);
			sell.setName(sts.getName());
			if (StringUtils.isNotEmpty(sell.getCode())) {
				try {
					sellDao.save(sell);
					ret.put("msg", "保存成功");
				} catch (Exception e) {
					ret.put("msg", "保存失败，请稍后再试");
				}
			} else {
				ret.put("msg", "代码不能为空");
				
			} 
		}else{
			ret.put("msg", "股票不存在");
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/delBuy")
	public Map deleBuyInfo(Buy buy) {
		Map ret = new HashMap();
		Map params = new HashMap();
		params.put("stock", buy);
		StockWeekLineUpByAuto auto = weekLineUpByAutoDao.getSingleByParam(params);
		if (auto != null) {
			auto.setIsAddToBuy("0");
			weekLineUpByAutoDao.save(auto);
		}
		StockWeekLineUpByHand hand = weekLineUpByHandDao.getSingleByParam(params);
		if (hand != null) {
			hand.setIsAddToBuy("0");
			weekLineUpByHandDao.save(hand);
		}
		StockMonthLineUpByAuto month = monthLineUpByAutoDao.getSingleByParam(params);
		if (month != null) {
			month.setIsAddToBuy("0");
			monthLineUpByAutoDao.save(month);
		}

		if (StringUtils.isNotEmpty(buy.getCode())) {
			try {
				buyDao.del(buy);
				ret.put("msg", "保存成功");
			} catch (Exception e) {
				ret.put("msg", "保存失败，请稍后再试");
			}
		} else {
			ret.put("msg", "代码不能为空");
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/delSell")
	public Map delSellInfo(Sell sell) {
		Map ret = new HashMap();
		if (StringUtils.isNotEmpty(sell.getCode())) {
			try {
				sellDao.del(sell);
				ret.put("msg", "保存成功");
			} catch (Exception e) {
				ret.put("msg", "保存失败，请稍后再试");
			}
		} else {
			ret.put("msg", "代码不能为空");
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sellList")
	public Page<Sell> getSell(String pageNum, String pageSize) {
		Page<Sell> pages = new Page<Sell>();
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
		List list = sellDao.getSellByParam(map);
		int total = sellDao.getSellTotalByParam(map);
		pages.setPageNo(firstIndex / size + 1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/buyList")
	public Page<Buy> getBuy(String pageNum, String pageSize) {
		Map map = new HashMap();
		Page<Buy> pages = new Page<Buy>();
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
		List list = buyDao.getBuyByParam(map);
		int total = buyDao.getBuyTotalByParam(map);

		pages.setPageNo(firstIndex / size + 1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}
	
	
}
