package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.utils.MyStringUtil;
import com.ly.dao.StockDao;
import com.ly.pojo.Buy;

//https://www.cnblogs.com/heyuxiu/p/5972187.html
@RestController
@RequestMapping("api")
public class FindStockController {

	@Autowired
	private StockDao stockDao;

	@RequestMapping("/findStock")
	public Map findStock(String code) {
		Map ret = new HashMap();
		Map params = new HashMap();
		params.put("stock", code);
		List lists = null;
		if(MyStringUtil.isInteger(code)){
			lists = stockDao.findSingleStockByCode(params);
		}else{
			lists = stockDao.findSingleStockPinYin(params);
		}
		ret.put("list", lists);
		return ret;

	}


}
