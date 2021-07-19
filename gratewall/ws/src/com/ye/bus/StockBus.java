package com.ye.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.Stock;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ye.dao.StockDao;

@Transactional
@Service
public class StockBus {
	public static Logger log = LogManager.getLogger(StockBus.class);

	@Autowired
	private StockDao stockDao;

	public List<Stock> selectStockAllList() {
		List<Stock> lists = null;
		lists = stockDao.selectStockAllList();
		return lists;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getStockByCodeOrName(Integer index, Integer size,String prama) {
		PageHelper.offsetPage(index, size);
		List<Stock> list = stockDao.getStockByCodeOrName(prama);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
		
		//List<Stock> lists = stockDao.getStockByCodeOrName(prama);
		//return lists;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getZhangFuStock(Integer index, Integer size,String codes) {
		PageHelper.offsetPage(index, size);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.getZhangFuStock(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getDieFuStock(Integer index, Integer size,String codes) {
		PageHelper.offsetPage(index, size);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.getDieFuStock(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	
	public List<Stock> selectNoNameStockList() {
		List<Stock> lists = stockDao.selectNoNameStockList();
		return lists;
	}

	public int updateStock(Stock stock) {
		return stockDao.update(stock);
	}

	public int save(Stock stock) {
		return stockDao.save(stock);
	}

	public Stock getStockByStockCode(String code) {
		return stockDao.getStockByStockCode(code);
	}


	//https://www.cnblogs.com/ljdblog/p/6725094.html
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getStockListByPage(Integer index, Integer size) {
		PageHelper.offsetPage(index, size);
		List<Stock> list = stockDao.selectStockAllList();
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getMostImpStockByPage(Integer index, Integer size,String codes) {
		PageHelper.offsetPage(index, size);
		Map paramMap = new HashMap();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.selectMostImpList(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getRecentImpListByPage(Integer index, Integer size,String codes) {
		PageHelper.offsetPage(index, size);
		Map paramMap = new HashMap();
		paramMap.put("codes", codes);
	//	List<Stock> list = stockDao.selectMostImpList(paramMap);
		List<Stock> list = stockDao.selectAllRecentImpList(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getRecentWaitImpByPage(int index, int pageSize,String codes) {
		PageHelper.offsetPage(index, pageSize);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.selectWaitImpList(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getLongHuByPage(int index, int pageSize,String codes) {
		PageHelper.offsetPage(index, pageSize);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.selectLongHuList(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getConsolesByPage(int index, int pageSize,String codes) {
		PageHelper.offsetPage(index, pageSize);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.selectConsoleStockList(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map imp(int index, int pageSize,String codes) {
		PageHelper.offsetPage(index, pageSize);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<Stock> list = stockDao.imp(paramMap);
		PageInfo<Stock> pageInfo = new PageInfo<Stock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}


	
	
	
}
