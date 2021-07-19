package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.utils.Page;
import com.ly.dao.BuyDao;
import com.ly.dao.MonthLineUpByAutoDao;
import com.ly.dao.SellDao;
import com.ly.dao.WeekLineUpByAutoDao;
import com.ly.dao.WeekLineUpByHandDao;
import com.ly.pojo.Buy;
import com.ly.pojo.Sell;
import com.ly.pojo.StockMonthLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByAuto;
import com.ly.pojo.StockWeekLineUpByHand;

//@RestController
//@RequestMapping("pro")
public class APIStockInfo2 {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editBuy")
	@ResponseBody
	public Map editBuyInfo(Buy buy, String token) {
		Map ret = new HashMap();
		if (token != null) {
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

			StockMonthLineUpByAuto month = monthLineUpByAutoDao.getSingleByParam(params);

			if (month != null) {
				month.setIsAddToBuy("1");
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
		} else {
			ret.put("msg", "用户未登录");
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/editSell")
	@ResponseBody
	public Map editSellInfo(Sell sell, String token) {
		Map ret = new HashMap();
		if (token != null) {
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
		} else {
			ret.put("msg", "用户未登录");
		}
		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/delBuy")
	@ResponseBody
	public Map deleBuyInfo(Buy buy, String token) {
		Map ret = new HashMap();
		if (token != null) {
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
				weekLineUpByHandDao.save(hand);
			}

			if (StringUtils.isNotEmpty(buy.getCode())) {
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
		} else {
			ret.put("msg", "用户未登录");
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/delSell")
	@ResponseBody
	public Map delSellInfo(Sell sell, String token) {
		Map ret = new HashMap();
		if (token != null) {
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
		} else {
			ret.put("msg", "用户未登录");
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sellList")
	@ResponseBody
	public Page<Sell> getSell(String pageNum, String pageSize, String token) {
		Page<Sell> pages = new Page<Sell>();
		Map map = new HashMap();
		if (token != null) {
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
		}
		return pages;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/buyList")
	@ResponseBody
	public Page<Buy> getBuy(String pageNum, String pageSize, String token) {
		Map map = new HashMap();
		Page<Buy> pages = new Page<Buy>();
		if (token != null) {
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
		}
		return pages;
	}
}
