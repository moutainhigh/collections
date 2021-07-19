package com.ye.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bo.domain.SMSStock;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ye.dao.SMSStockDao;

@Transactional
@Component
public class SMSStockBus {

	public static Logger log = LogManager.getLogger(SMSStockBus.class);

	@Autowired
	private SMSStockDao smsStockDao;

	public List<SMSStock> getNoNameSMSStock() {
		return smsStockDao.getNoNameSMSStock();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAllSMSStockByPage(int index, int pageSize) {
		PageHelper.offsetPage(index, pageSize);
		List<SMSStock> list = smsStockDao.selectAllSMSStockList();
		PageInfo<SMSStock> pageInfo = new PageInfo<SMSStock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getSMSStockByCodeOrName(int index, int pageSize,String param) {
		PageHelper.offsetPage(index, pageSize);
		List<SMSStock> list = smsStockDao.getSMSStockByCodeOrName(param);
		PageInfo<SMSStock> pageInfo = new PageInfo<SMSStock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
	
	public List<SMSStock> selectAllSMSStockList() {
		return smsStockDao.selectAllSMSStockList();
	}


	public int updateSMSStock(SMSStock SMSStock) {
		try {
			smsStockDao.update(SMSStock);
			return 1;
		} catch (Exception e) {
			log.log(Level.ERROR, "发生异常了" + e);
			return 0;
		}
	}

	
	public int saveDailyRecode(SMSStock recordDaily) {
		try {
			smsStockDao.save(recordDaily);
			return 1;
		} catch (Exception e) {
			log.log(Level.ERROR, "发生异常了" + e);
			return 0;
		}
	}

	public SMSStock getSMSStockByStockCode(String code) {
		return smsStockDao.getSMSStockByStockCode(code);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getZhangFuStock(Integer index, Integer size,String codes) {
		PageHelper.offsetPage(index, size);
		Map paramMap = new HashMap<>();
		paramMap.put("codes", codes);
		List<SMSStock> list = smsStockDao.getZhangFuStock(paramMap);
		PageInfo<SMSStock> pageInfo = new PageInfo<SMSStock>(list);
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
		List<SMSStock> list = smsStockDao.getDieFuStock(paramMap);
		PageInfo<SMSStock> pageInfo = new PageInfo<SMSStock>(list);
		long total = pageInfo.getTotal(); // 获取总记录数见https://www.cnblogs.com/shanheyongmu/p/5864047.html
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return map;
	}
	
}
