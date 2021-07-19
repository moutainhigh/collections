package cn.gwssi.sjbd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.sjbd.service.SjbdService;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/datacompre")
public class Sjbdcontroller{
	
	private static  Logger log=Logger.getLogger(Sjbdcontroller.class);
	@Autowired
	private SjbdService sjbdService;
	
	@RequestMapping("/queryZzjgDm")
	public void queryZzjgDm(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		
		System.out.println("paramss=="+params);
		List<Map> list=sjbdService.queryZzjgDm(params);
		
		//1是企业法人，2是企业非法人，B是个体
		for(Map map:list){
			String subentitytype=(String) map.get("subentitytype");
			if("1".equals(subentitytype)){
				subentitytype="企业法人";
			}
			if("2".equals(subentitytype)){
				subentitytype="企业非法人";
			}
			if("B".equals(subentitytype)){
				subentitytype="个体";
			}
			if("9".equals(subentitytype)){
				subentitytype="其他机构";
			}
			map.put("subentitytype", subentitytype);
		}
		res.addGrid("zzjgGrid", list, null);
	}
	
	/**不需要写，如果这样设计，需要忠旺那边接入服务器推技术
	 * @param req
	 * @param res
	 */
//	@RequestMapping("/writeZzjgDm")
//	public void writeZzjgDm(OptimusRequest req, OptimusResponse res){
//		String orgno=req.getParameter("orgno");
//		//调用忠旺接口，写入值过去
//	}
//	
	
	/**获取正式表中完全比中数据，直接返回前台，由忠旺写入页面
	 * 调用链接http://localhost:8088/sjbd/datacompre/returnTrue.do?entityNo=69b71f2d-012e-1000-e000-0b310a0e0115
	 * @param req  参数必须有entityNo
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("/returnTrue")
	public String returnTrue(OptimusRequest req, OptimusResponse res) throws OptimusException{
		String entityNo=req.getParameter("entityNo");//获取主体身份代码
		String returnValue=sjbdService.getZzjgdmByEntityNoToTrue(entityNo);
		log.debug("开始获取正式表中比中信息====================:传入主体身份号码="+entityNo+",返回比中组织机构代码="+returnValue);
		
		System.out.println("aa" + "([{datavalue:'" + returnValue + "'}])");
		
		//return "{\"data\":[{\"data\":{\"rows\":[]},\"name\":\"zzjgGrid\",\"vtype\":\"gridpanel\"}]}";
		return "{\"datavalue\":\"123\"}";
	}
	/**
	 * 获取历史表中比中的，展开界面，然后将结果进行展示，展示结果未质检那边提供数据，需要两条sql，一条查历史，然后通过历史关联质检提供数据/将结果展示给客户，需要对数据进行回调
	 * 第一次是直接展示历史中数据，之后点击查询是查询质检办提供数据
	 * @return 
	 * @throws OptimusException 
	 */
	@RequestMapping("/returnHistoryExsists")
	public void returnHistoryExsists(OptimusRequest req, OptimusResponse res) throws OptimusException{
		Map<String,String> params = req.getForm("formpanel");//获取参数
		System.out.println("paramss=="+params);
		Map mapParameter =new HashMap();
		List list=null;
		//返回历史中存在的数据
		String entityNo=(String) req.getParameter("entityNo");//获取主体身份代码、在历史比中查询//可查出历史年份，就是比中年份，再加质检办给的数据，进行展示
		
		//System.out.println(entityNo);
		if(entityNo!=null){
			//历史比中/直接查询历史比中的数据，加比中年份还有质检那边数据，企业名称
			 list=sjbdService.getZzjgdmByEntityNoToHistory(entityNo);
			//存在字段orgno,registerNo，corpName，entityNo，approveDate     
			
			for(int i=0;i<list.size();i++){
				Map map=(Map) list.get(i);
				
				String subentitytype=(String) map.get("subentitytype");
				if("1".equals(subentitytype)){
					subentitytype="企业法人";
				}
				if("2".equals(subentitytype)){
					subentitytype="企业非法人";
				}
				if("B".equals(subentitytype)){
					subentitytype="个体";
				}
				if("9".equals(subentitytype)){
					subentitytype="其他机构";
				}
				map.put("subentitytype", subentitytype);
			}
			
		}
		res.addGrid("zzjgGrid", list, null);
	}
	/*
	 * 左侧栏的导航条信息
	 * */
	@RequestMapping("/querytreedata")
	@ResponseBody
	public Object querytreedata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		List obj=new ArrayList();
		obj.add("基本信息1");
		obj.add("基本信息2");
		return obj;
	}
}
