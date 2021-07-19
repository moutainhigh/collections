package com.ye.controller.stock;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bo.domain.Stock;
import com.ye.controller.base.BaseController;
import com.ye.monitor.from.TencentWeb;
import com.ye.service.StockService;
//https://www.cnblogs.com/feihong84/p/5747775.html
//中文乱码
//http://blog.csdn.net/bo_wen_/article/details/50218095
//http://blog.csdn.net/caisini_vc/article/details/49913991
@Controller
@RequestMapping("/stock")
public class StockController extends BaseController{

	@Autowired
	private TencentWeb tencentWeb;
	
	@Autowired
	private StockService stockService;
	
	@RequestMapping(value="/getAllStockByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllStockListByPage(String offset,String limit,String codes) throws UnsupportedEncodingException{
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		
	//	Map map =  stockService.getStockListByPage(index, pageSize);
		
		//http://blog.csdn.net/john1337/article/details/76277617
		//http://blog.csdn.net/tengxing007/article/details/73822714
		//String str = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		//String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		codes =  new String(codes.getBytes("iso-8859-1"),"utf-8");
		Map map =  stockService.getStockByCodeOrName(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	
	//控制台
	@RequestMapping(value="/getConsolesByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getConsolesByPage(String offset,String limit,String codes) throws UnsupportedEncodingException{
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		//Map map =  stockService.getConsolesByPage(index,pageSize);
		codes =  new String(codes.getBytes("iso-8859-1"),"utf-8");
		Map map =  stockService.getConsolesByPage(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);		
		return str;
	}
	//是否最重要
	@RequestMapping(value="/getMostImpByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMostImpStockByPage(String offset,String limit,String codes) throws UnsupportedEncodingException{
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		codes =  new String(codes.getBytes("iso-8859-1"),"utf-8");
		Map map =  stockService.getMostImpStockByPage(index,pageSize,codes);
		//Map map =  stockService.getMostImpStockByPage(index,pageSize);
		
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	//是否重要
	@RequestMapping(value="/imp",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String imp(String offset,String limit,String codes){
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		Map map =  stockService.imp(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	

	
	@RequestMapping(value="/getZhangFuStock",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getZhangFuStock(String offset,String limit,String codes){
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		Map map =  stockService.getZhangFuStock(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	
	
	@RequestMapping(value="/getDieFuStock",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDieFuStock(String offset,String limit,String codes){
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		Map map =  stockService.getDieFuStock(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	
	
	//最近关注
	@RequestMapping(value="/getRecentImpByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllRecentWaitStockListByPage(String offset,String limit,String codes){
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		Map map =  stockService.getRecentImpListByPage(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	
	//最近待关注
	@RequestMapping(value="/getRecentWaitImpByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getRecentWaitImpByPage(String offset,String limit,String codes){
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		Map map =  stockService.getRecentWaitImpByPage(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	
	

	@RequestMapping(value="/getLongHuByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getLongHuByPage(String offset,String limit,String codes){
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		Map map =  stockService.getLongHuByPage(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	
	
	
	
	
	
	//进行增，改，删
	@RequestMapping(value="/addStockInfo",produces="application/json;charset=UTF-8")
	public String addStockInfo(){
		return null;
		
	}
	
	@RequestMapping(value="/editStockInfo",produces="application/json;charset=UTF-8")
	public String editStockInfo(){
		return null;
		
	}
	
	@RequestMapping(value="/delStockInfo",produces="application/json;charset=UTF-8")
	public String delStockInfo(){
		return null;
	}
	
	
	@RequestMapping(value="/change",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changType(String code,String type,String changeTo){
		
		System.out.println(type);
		Stock stock = stockService.getStockByStockCode(code);
		System.out.println(stock.getStockName() + "===>更新类型" + type + "成功" );
		int txt = Integer.valueOf(changeTo);
		if(type.equals("isconsole")){
			stock.setIsconsole(txt);
		}else if(type.equals("mostimp")){
			stock.setMostImp(txt);
		}else if(type.equals("isimp")){
			stock.setIsImp(txt);
		}else if(type.equals("waitimp")){
				stock.setWaitImp(txt);
		}else if(type.equals("longhuBang")){
				stock.setLonghuBang(txt);
		}else {
			stock.setIsRecentImport(txt);
		}
		try {
			stockService.update(stock);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return "1";
	}

	
	@RequestMapping(value="/updateInfoByWeb",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateInfoByWeb(String stockCode,String stockPrice,String rates,String type){
		Stock stock = stockService.getStockByStockCode(stockCode);
		if(type.equals("price")){
			stock.setPointerPrice(Double.valueOf(stockPrice));
		}else{
			stock.setPointerRates(Double.valueOf(rates));
		}
		stockService.update(stock);
		System.out.println(stock.getStockName());
		return "1";
	}
	
	
	
	//添加股票的时候读取相关信息的
		@RequestMapping(value="/getStockInfo",produces="application/json;charset=UTF-8")
		@ResponseBody
		public String getStockRelateInfo(String code){
			//juHeWeb.getRequest1(code);
			Map map =  tencentWeb.getStockRelatInfo(code);
			if(map!=null){
				return JSON.toJSONString(map);
			}else{
				return "0";
			}
		}
		
		@RequestMapping(value="/getStockIsEixt",produces="application/json;charset=UTF-8")
		@ResponseBody
		public String getStockIsEixt(String code){
			//juHeWeb.getRequest1(code);
			Stock stock = stockService.getStockByStockCode(code);
			if(stock!=null){
				return "1";
			}else{
				return "0";
			}
		}
}
