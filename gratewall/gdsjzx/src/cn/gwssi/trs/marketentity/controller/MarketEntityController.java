package cn.gwssi.trs.marketentity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.trs.marketentity.service.MarketEntityService;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import cn.gwssi.resource.FreemarkerUtil;

@Controller
@RequestMapping("/reg")
public class MarketEntityController {

	private static  Logger log=Logger.getLogger(MarketEntityController.class);

	@Autowired
	private MarketEntityService marketEntityService;

	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{
		HttpServletRequest reqs=req.getHttpRequest();
		String priPid=req.getParameter("priPid");//获取主体身份代码
		int flag =  Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());
		String sourceflag=req.getParameter("sourceflag");
		String regno=req.getParameter("regno");
		String type=req.getParameter("type");
		String economicproperty=req.getParameter("economicproperty");
		Map<String,String> params =new HashMap<String,String>();
		//Map basicdata=null;
		List<Map> list =null;
		params.put("priPid", priPid);
		params.put("regno", regno);
		if("null".equals(sourceflag)){
			params.put("sourceflag", null);
		}else{
			params.put("sourceflag", sourceflag);
		}
		switch (flag) {
			case 0://基本信息
				if("3".equals(economicproperty)){//外资信息
					list = marketEntityService.findRegJbxx(params,"2");
				}else {
					if("0".equals(type)){//个体信息
						list = marketEntityService.findRegJbxx(params,"1");
					}else {//内资信息
						list = marketEntityService.findRegJbxx(params,"0");
					}
				}
				if(list!=null && list.size()>0){
					res.addForm("formpanel",list.get(0), null);
				}
				break;
			case 1://隶属信息
				res.addGrid("jbxxGrid", marketEntityService.findRegLsxx(params), null);
				break;
			case 2://迁移信息
				res.addGrid("jbxxGrid", marketEntityService.findRegQrQcxx(params), null);
				break;
			case 3://股权信息
				res.addGrid("jbxxGrid1", marketEntityService.findRegGqdjxx(params), null);
				break;
			case 4://股权出质
				res.addGrid("jbxxGrid1", marketEntityService.findRegGqczxx(params), null);
				//res.addGrid("jbxxGrid",marketEntityService.queryRegRyxx(params), null);
				break;
			case 5://变更信息
				res.addGrid("jbxxGrid", marketEntityService.findRegBgxx(params), null);
				//res.addGrid("jbxxGrid1", marketEntityService.queryRegGqdjxx(params), null);
				break;
			case 6://清算信息
				res.addGrid("jbxxGrid", marketEntityService.findRegQsxx(params), null);
				//res.addGrid("jbxxGrid2", marketEntityService.queryRegGqczxx(params), null);
				break;
			case 7://注吊销信息
				res.addGrid("jbxxGrid", marketEntityService.findRegZdxxx(params), null);
				//res.addGrid("jbxxGrid", marketEntityService.queryRegDjgdxx(params), null);
				break;
			case 8://证照信息
				res.addGrid("jbxxGrid", marketEntityService.findRegZzxx(params), null);
				//res.addGrid("jbxxGrid", marketEntityService.queryRegZzxx(params), null);
				break;
			case 9://自热人出资信息
				/*list = marketEntityService.queryRegJbxx(params,"0");
				if(list!=null&& list.size()>0) {
					basicdata=list.get(0);
					String commitment =(String) basicdata.get("regcap");
					List<Map> tzrjcz=marketEntityService.queryRegTzrjczxx(params);
					if(tzrjcz.size()>0){
						if(commitment.contains("(万元[人民币])")){
							commitment = commitment.replace("(万元[人民币])", "");
						}
						tzrjcz.get(0).put("commitment", commitment);
					}*/
					//List<Map> tzrjcz=marketEntityService.queryRegTzrjczxx(params);
					res.addGrid("jbxxGrid",marketEntityService.findRegTzrjczxx(params), null);
					//res.addPage("commitment");
					//res.addGrid("jbxxGrid", marketEntityService.queryRegQsxx(params), null);
					break;
				//}
			case 10://人员信息
				res.addGrid("jbxxGrid",marketEntityService.findRegRyxx(params), null);
				//res.addGrid("jbxxGrid", marketEntityService.queryRegZdxxx(params), null);
				break;
			case 11://法人
				res.addGrid("jbxxGrid",marketEntityService.findFrTzrjczxx(params), null);
				//res.addGrid("jbxxGrid", marketEntityService.queryRegBgxx(params), null);
				break;
			case 12:
				res.addGrid("jbxxGrid", marketEntityService.findRegGdxx(params), null);
				break;
			case 13:
				res.addGrid("jbxxGrid", marketEntityService.findRegCzywxx(params), null);
				break;
			case 14:
				//res.addGrid("jbxxGrid", marketEntityService.queryRegYwhjxx(params), null);
				res.addGrid("jbxxGrid1", marketEntityService.findRegFddbr(params), null);
				break;
			case 15:
				res.addGrid("jbxxGrid2", marketEntityService.findRegDjgdxx(params), null);
				break;
			case 16:
				res.addGrid("jbxxGrid1", marketEntityService.findRegMcjbxx(params), null);

				break;
			case 17:
				res.addGrid("jbxxGrid", marketEntityService.findRegZzxx(params), null);
				//res.addGrid("jbxxGrid", marketEntityService.queryRegGjcyxx(params), null);
				break;
			case 18:
				res.addGrid("jbxxGrid", marketEntityService.findRegDbsy(params), null);
				break;
			case 19:
				res.addGrid("jbxxGrid", marketEntityService.findRegTzrjczxx(params), null);
				break;
			case 20://此处开始是市场监管 (守重)
				res.addGrid("jbxxGrid", marketEntityService.findszInfo(params), null);
				break;
			case 21://合同签订履行信息
				res.addGrid("jbxxGrid", marketEntityService.findHtqdlxInfo(params), null);
				break;
			case 22://合同管理机构正式表
				res.addGrid("jbxxGrid", marketEntityService.findHtgljgInfo(params), null);
				break;
			case 23://拍卖信息
				res.addGrid("jbxxGrid", marketEntityService.findPmInfo(params), null);
				break;
			case 24://拍卖后备案信息 
				res.addGrid("jbxxGrid", marketEntityService.findPmhbaInfo(params), null);
				break;
			case 25://拍卖后备案请求核准结果  
				res.addGrid("jbxxGrid", marketEntityService.findPmhbajgInfo(params), null);
				break;
			case 26://拍卖前备案请求核准结果   
				res.addGrid("jbxxGrid", marketEntityService.findPmqbajgInfo(params), null);
				break;
			case 27://抵押登记（）
				res.addGrid("jbxxGrid", marketEntityService.findDydjInfo(params), null);
				break;
			case 28://抵押人情况（）
				res.addGrid("jbxxGrid", marketEntityService.findDyrqkInfo(params), null);
				break;
			case 29://抵押物清单
				res.addGrid("jbxxGrid", marketEntityService.findDywqdInfo(params), null);
				break;
			case 30://抵押人信息
				res.addGrid("jbxxGrid", marketEntityService.findDyrInfo(params), null);
				break;
			case 31://抵押权人信息
				res.addGrid("jbxxGrid", marketEntityService.findDyqrInfo(params), null);
				break;
			case 32://其他行政處罰(其他)
				res.addGrid("jbxxGrid", marketEntityService.findOtherXzcfInfo(params), null);
				break;
			case 33://其他行政許可(其他)
				res.addGrid("jbxxGrid", marketEntityService.findOtherXzxkInfo(params), null);
				break;
			case 34://(商標)基本信息
				res.addGrid("jbxxGrid", marketEntityService.findSbInfo(params), null);
				break;
			case 35://(商標)商品信息
				res.addGrid("jbxxGrid", marketEntityService.findSbspInfo(params), null);
				break;
			case 36://(商標)商标代理人
				res.addGrid("jbxxGrid", marketEntityService.findSbdlrInfo(params), null);
				break;
			case 37://(商標)商标共有人
				res.addGrid("jbxxGrid", marketEntityService.findSbgyrInfo(params), null);
				break;
			case 38://(商標)商标申请人
				res.addGrid("jbxxGrid", marketEntityService.findSbsqrInfo(params), null);
				break;
			case 39://(老赖)失信违法被执行人
				res.addGrid("jbxxGrid", marketEntityService.findLaolaiInfo(params), null);
				break;
			case 40://(农资)农资企业信息
				res.addGrid("jbxxGrid", marketEntityService.findnzqyInfo(params), null);
				break;
			case 41://(农资)农资供销企业信息
				res.addGrid("jbxxGrid", marketEntityService.findnzgxqyInfo(params), null);
				break;
			case 42://(农资)农资企业商品信息
				res.addGrid("jbxxGrid", marketEntityService.findnzqysbInfo(params), null);
				break;
			case 43://(农资)农资商品标准信息
				res.addGrid("jbxxGrid", marketEntityService.findnzspbzInfo(params), null);
				break;
			case 44://(农资)抽样工单信息
				res.addGrid("jbxxGrid", marketEntityService.findcygdInfo(params), null);
				break;
			case 45://(农资)检测报告信息
				res.addGrid("jbxxGrid", marketEntityService.findjcbgInfo(params), null);
				break;
			case 46://(农资)检验项目结果信息
				res.addGrid("jbxxGrid", marketEntityService.findjyxmjgInfo(params), null);
				break;
			case 47://(案件)基本信息
				res.addGrid("jbxxGrid", marketEntityService.findanjianInfo(params), null);
				break;
			case 48://(案件)违法行为及处罚信息
				res.addGrid("jbxxGrid", marketEntityService.findwfxwjcfInfo(params), null);
				break;
			case 49://(案件)当事人信息
				res.addGrid("jbxxGrid", marketEntityService.finddsrInfo(params), null);
				break;
			case 50://(案件)行政处罚决定书
				res.addGrid("jbxxGrid", marketEntityService.findxzcfjdsInfo(params), null);
				break;
			case 51://(广告)广告经营基本信息
				res.addGrid("jbxxGrid", marketEntityService.findggjyjbInfo(params), null);
				break;
			case 52://(广告)从业人员信息
				res.addGrid("jbxxGrid", marketEntityService.findggcyryInfo(params), null);
				break;
			case 53://(广告)广告审查员信息
				res.addGrid("jbxxGrid", marketEntityService.findggscrInfo(params), null);
				break;
			case 54://广告分支机构信息
				res.addGrid("jbxxGrid", marketEntityService.findggfzjgInfo(params), null);
				break;
			case 55://广告监管行政措施信息
				res.addGrid("jbxxGrid", marketEntityService.findggjgxzcsInfo(params), null);
				break;
			case 56://异常名录
				res.addGrid("jbxxGrid", marketEntityService.findYCMLxx(params), null);
				break;
			default:
				break;
		}
	}

	@RequestMapping("/queryCount")
	@ResponseBody
	public int querydata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		int i =marketEntityService.findRegCount(null);
		return i;
	}

	@ResponseBody
	@RequestMapping("scztjbxx")
	public Map<String,Object> sczt(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String priPid=req.getParameter("priPid");//获取主体身份代码
		int flag = Integer.parseInt(req.getParameter("flag")==null?"999":"".equals(req.getParameter("flag").trim())?"999":req.getParameter("flag").trim());
		String sourceflag=req.getParameter("sourceflag");
		String type=req.getParameter("type");
		String economicproperty=req.getParameter("economicproperty");
		String strIndex=null;
		Map<String,String> params =new HashMap<String,String>();
		params.put("priPid", priPid);
		params.put("sourceflag", sourceflag);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		switch (flag) {
			case 0:
				List<Map> list =null;
				/*if(StringUtils.isNotBlank(economicproperty)){
					if("2".equals(economicproperty)){
						list = marketEntityService.queryRegJbxx(params,"1");
					}else if("3".equals(economicproperty)){
						list = marketEntityService.queryRegJbxx(params,"2");
					}else{
						list = marketEntityService.queryRegJbxx(params,"0");
					}
				}else{
					list = marketEntityService.queryRegJbxx(params,"0");
				}*/
				if("3".equals(economicproperty)){//外资信息
					list = marketEntityService.findRegJbxx(params,"2");
				}else {
					if("0".equals(type)){//个体信息
						list = marketEntityService.findRegJbxx(params,"1");
					}else {//内资信息
						list = marketEntityService.findRegJbxx(params,"0");
					}
				}
				if(type!=null && type.trim()!=""){
					strIndex=type.substring(0, 1);
				}
				/*if(type!=null && type.trim()!=""){
					strIndex=type.substring(0, 1);
					if("1".equals(strIndex) || "2".equals(strIndex) || "3".equals(strIndex) || "4".equals(strIndex) || "5".equals(type)){
						list = marketEntityService.queryRegJbxx(params,"0");
					}else if("0".equals(strIndex)){
						list = marketEntityService.queryRegJbxx(params,"1");		 
					}else if("6".equals(strIndex) || "7".equals(strIndex) ){
						list = marketEntityService.queryRegJbxx(params,"2");
					}else{
						list = marketEntityService.queryRegJbxx(params,"0");
					}
				}else{
					list = marketEntityService.queryRegJbxx(params,"0");
					
				}*/
				if(list!=null && list.size()>0){
					dataMap = list.get(0);
				}
			default:
				break;
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			returnMap.put("entname", dataMap.get("entname"));
			returnMap.put("regno", dataMap.get("regno"));
			returnMap.put("enttype", dataMap.get("enttype"));
			returnMap.put("commitment", dataMap.get("regcap"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap = marketEntityService.findshowRegJbxx(strIndex);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}
				//datasMap.put("weaponMap", dataMap);  
				datasMap.put("weaponMap", sortMap);
				returnString = FreemarkerUtil.returnString(ConfigManager.getProperty("freemarkerSFileName"), datasMap);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		returnMap.put("returnString", returnString);
		return returnMap;
	}

}