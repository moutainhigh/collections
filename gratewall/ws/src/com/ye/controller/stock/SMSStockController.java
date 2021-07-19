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
import com.bo.domain.SMSStock;
import com.bo.domain.Stock;
import com.ye.controller.base.BaseController;
import com.ye.monitor.from.TencentWeb;
import com.ye.service.StockService;

@Controller
@RequestMapping("/smsStock")
public class SMSStockController extends BaseController{

	@Autowired
	private StockService stockService;
	
	@Autowired
	private TencentWeb tencentWeb;
	
	
	@RequestMapping(value="/getAllSMSStockByPage",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllStockListByPage(String offset,String limit,String codes) throws UnsupportedEncodingException{
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		
		//Map map =  stockService.getAllSMSStockByPage(index, pageSize);
		codes =  new String(codes.getBytes("iso-8859-1"),"utf-8");
		Map map ;
		map =  stockService.getSMSStockByCodeOrName(index,pageSize,codes);
		
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		
		return str;
	}
	
	
	
	@RequestMapping(value="/getZhangFuStock",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getZhangFuStock(String offset,String limit,String codes) throws UnsupportedEncodingException{
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		codes =  new String(codes.getBytes("iso-8859-1"),"utf-8");
		Map map =  stockService.getSMSZhangFuStock(index,pageSize,codes);
		Stock stock = new Stock();
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getDieFuStock",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDieFuStock(String offset,String limit,String codes) throws UnsupportedEncodingException{
		int index = Integer.valueOf(offset);
		int pageSize = Integer.valueOf(limit);
		codes =  new String(codes.getBytes("iso-8859-1"),"utf-8");
		Map map =  stockService.getSMSDieFuStock(index,pageSize,codes);
		String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return str;
	}
	
	
	
	
	
	@RequestMapping(value="/change",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changType(String code,String type,String changeTo){
		System.out.println(code);
	    SMSStock stock = stockService.getSMSStockByStockCode(code);
	    
		//System.out.println(stock.getStockName() + "===>更新类型" + type + "成功" );
		int txt = Integer.valueOf(changeTo);
		if(type.equals("isconsole")){
			//stock.setIsconsole(txt);
		}else if(type.equals("mostimp")){
		//	stock.setMostImp(txt);
		}else if(type.equals("isimp")){
			//stock.setIsImp(txt);
		}else if(type.equals("waitimp")){
			//	stock.setWaitImp(txt);
		}else if(type.equals("longhuBang")){
				//stock.setLonghuBang(txt);
		}else {
		//	stock.setIsRecentImport(txt);
		}
		
		//stockService.update(stock);
		
		//String str = JSON.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return "1";
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
	
	
	
	//添加股票的时候读取相关信息的
			@RequestMapping(value="/getSMSStockInfo",produces="application/json;charset=UTF-8")
			@ResponseBody
			public String getSMSStockRelateInfo(String code){
				//juHeWeb.getRequest1(code);
				Map map =  tencentWeb.getStockRelatInfo(code);
				if(map!=null){
					return JSON.toJSONString(map);
				}else{
					return "0";
				}
			}
			
			@RequestMapping(value="/getSMSStockIsEixt",produces="application/json;charset=UTF-8")
			@ResponseBody
			public String getSMSStockIsEixt(String code){
				//juHeWeb.getRequest1(code);
				Stock stock = stockService.getStockByStockCode(code);
				if(stock!=null){
					return "1";
				}else{
					return "0";
				}
			}
}
