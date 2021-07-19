package com.ly.vuecontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.util.MyStringUtil;
import com.ly.dao.impl.StockDao;

//https://www.cnblogs.com/heyuxiu/p/5972187.html
@RestController
@RequestMapping("api")
public class FindStockController {

	@Autowired
	private StockDao stockDao;

	@SuppressWarnings("unchecked")
	@RequestMapping("/findStock")
	public Map findStock(String code) {
		Map ret = new HashMap();
		Map params = new HashMap();
		if(StringUtils.isNotEmpty(code)){
			params.put("stock", code);
			List lists = null;
			if(MyStringUtil.isInteger(code)){
				lists = stockDao.findByPage("from Stock t where t.code  like '%'||?||'%' ",0,20,code);
			}else{
				lists = stockDao.findByPage("from Stock t where t.pinyin  like '%'||?||'%' ",0,20,code);
			}
			ret.put("list", lists);
		}else{
			ret.put("msg", "代码不能为空");
		}
		
		return ret;

	}

}
