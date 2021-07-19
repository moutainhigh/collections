package com.ly.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//https://www.cnblogs.com/bignew/p/6594734.html
//https://blog.csdn.net/strutce/article/details/76618857
//https://www.cnblogs.com/wcyBlog/p/4039497.html
//https://blog.csdn.net/qq_35246620/article/details/68487904
//https://blog.csdn.net/xdnloveme/article/details/78035065
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.common.utils.Page;
import com.ly.dao.BuyDao;
import com.ly.dao.SellDao;
import com.ly.dao.WeekLineUpByAutoDao;
import com.ly.dao.WeekLineUpByHandDao;
import com.ly.pojo.Buy;
import com.ly.pojo.Sell;
import com.ly.pojo.StockWeekLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByHand;

//https://www.cnblogs.com/asfeixue/p/4363372.html
//http://blog.sina.com.cn/s/blog_4ea0151a0102yn9q.html
/*手机按钮
http://www.bootcss.com/p/buttons/
*/
//https://blog.csdn.net/u012260672/article/details/50922459
@Controller
@RequestMapping("services")
public class StockController {
	@Autowired
	private BuyDao buyDao;

	@Autowired
	private SellDao sellDao;

	@Autowired
	private WeekLineUpByAutoDao weekLineUpByAutoDao;

	@Autowired
	private WeekLineUpByHandDao weekLineUpByHandDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editBuy")
	@ResponseBody
	public Map editBuyInfo(Buy buy) {
		Map ret = new HashMap();
		Map params = new HashMap();
		params.put("stock", buy);
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
		if (StringUtils.isNotEmpty(buy.getCode())) {
			try {
				buyDao.save(buy);
				ret.put("msg", "保存成功");
			} catch (Exception e) {
				ret.put("msg", "保存失败，请稍后再试");
			}
			return ret;
		} else {
			ret.put("msg", "代码不能为空");
			return ret;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editSell")
	@ResponseBody
	public Map editSellInfo(Sell sell) {
		Map ret = new HashMap();
		if (StringUtils.isNotEmpty(sell.getCode())) {
			try {
				sellDao.save(sell);
				ret.put("msg", "保存成功");
			} catch (Exception e) {
				ret.put("msg", "保存失败，请稍后再试");
			}
			return ret;
		} else {
			ret.put("msg", "代码不能为空");
			return ret;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/delBuy")
	@ResponseBody
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

		if (StringUtils.isNotEmpty(buy.getCode()))

		{
			try {
				buyDao.del(buy);
				;
				ret.put("msg", "保存成功");
			} catch (Exception e) {
				ret.put("msg", "保存失败，请稍后再试");
			}
			return ret;
		} else {
			ret.put("msg", "代码不能为空");
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/delSell")
	@ResponseBody
	public Map delSellInfo(Sell sell, HttpServletRequest request) {
		Map ret = new HashMap();
		if (StringUtils.isNotEmpty(sell.getCode())) {
			try {
				sellDao.del(sell);
				ret.put("msg", "保存成功");
			} catch (Exception e) {
				ret.put("msg", "保存失败，请稍后再试");
			}
			return ret;
		} else {
			ret.put("msg", "代码不能为空");
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sellList")
	@ResponseBody
	public Page<Sell> getSell(String pageNum, String pageSize) {
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

		Page<Sell> pages = new Page<Sell>();
		pages.setPageNo(firstIndex / size + 1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/buyList")
	@ResponseBody
	public Page<Buy> getBuy(String pageNum, String pageSize) {
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
		List list = buyDao.getBuyByParam(map);
		int total = buyDao.getBuyTotalByParam(map);

		Page<Buy> pages = new Page<Buy>();
		pages.setPageNo(firstIndex / size + 1);
		pages.setStart(firstIndex);
		pages.setTotalCount(total);
		pages.setPageList(list);
		return pages;
	}
}
