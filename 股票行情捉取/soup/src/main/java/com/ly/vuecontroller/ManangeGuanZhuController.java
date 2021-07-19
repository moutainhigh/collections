package com.ly.vuecontroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.dao.impl.Important_Stock_Dao;
import com.ly.dao.impl.StockDao;
import com.ly.pojo.Important_Stock;
import com.ly.pojo.Stock;

@RestController
@RequestMapping("api")
public class ManangeGuanZhuController {

	@Autowired
	private Important_Stock_Dao imp;
	@Autowired
	private StockDao stDao;
	
	@RequestMapping("add")
	public Map addImport(Important_Stock im){
		Map map  = new HashMap();
		if(im!=null&&im.getCode()!=null){
			List<Stock> sts = stDao.find(" from Stock where code = ? ", im.getCode());
			if(sts!=null&&sts.size()>0){
				im.setName(sts.get(0).getName());
				im.setAddtime(new Date().toLocaleString());
				
				imp.update(im);
				map.put("msg", "添加"+im.getCode()+"成功");
			}else{
				map.put("msg", "无此股票代码");
			}
		}else{
			map.put("msg", "添加失败,代码不能为空");
		}
		
		return map;
	}
	@RequestMapping("del")
	public Map delImport(Important_Stock im){
		Map map  = new HashMap();
		if(im!=null&&im.getCode()!=null){
			imp.delete(im);
			map.put("msg", "删除"+im.getCode()+"成功");
		}else{
			map.put("msg", "删除失败,代码不能为空");
		}
		return map;
	}
	
	
	@RequestMapping("list")
	public List list(){
		List<Stock> sts = stDao.find(" from Important_Stock t ORDER BY STR_TO_DATE(t.addtime ,'%Y-%m-%d %H:%i:%s') desc ");
		return sts;
	}
}
