package cn.gwssi.trs.controller;

import cn.gwssi.resource.CodeToValue;
import cn.gwssi.resource.Conts;
import cn.gwssi.resource.FreemarkerUtil;
import cn.gwssi.trs.service.TrsService;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/datatrs")
public class TrsController {
		
	private static  Logger log=Logger.getLogger(TrsController.class);
	
	@Autowired
	private TrsService trsService;
	  
	@RequestMapping("/querydata")
	@ResponseBody
	public Map querydata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String queryKeyWord = req.getParameter("queryKeyWord");
		Map map =null;
		String code="0";
		String msg="成功";
		if(StringUtils.isNotBlank(queryKeyWord)){
			Pattern pattern = Pattern.compile(Conts.REGULAR_EXPRESSION);
			Matcher matcher = pattern.matcher(queryKeyWord);
			boolean b= matcher.matches();
			if(!matcher.matches()){//特殊字符过滤
				String pages=req.getParameter("pages");
				String labelFlag = req.getParameter("labelFlag");
				String type = req.getParameter("type");
				try {
					type = URLDecoder.decode(type , "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if("12315".equals(type)){
					map = trsService.queryAjdata(pages,queryKeyWord,labelFlag);
				}else if("市场主体".equals(type)){
					map = trsService.querydata(pages,queryKeyWord,labelFlag);
				}else if("年度报告".equals(type)){
					map = trsService.queryAnnualdata(pages,queryKeyWord,labelFlag);
				}else if("案件信息".equals(type)){
					map = trsService.queryCaseData(pages,queryKeyWord,labelFlag);
				}
				code="0";
				msg="成功";
			}else{
				code="1";
				msg="全文检索不支持特殊字符(包含:+、-、/、(、)、<、>、[、]、@、!、&、^、=、*)";
			}
		}else{
			code="1";
			msg="输入栏不能为空!";
			//直接返回
		}
		map.put("code", code);
		map.put("msg", msg);
		return map; 
	}
	
	@RequestMapping("/querytreedata")
	@ResponseBody
	public Object querytreedata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		List obj=new ArrayList();
		obj.add("基本信息1");
		obj.add("基本信息2");
		trsService.updatetaskmesure();
		return obj;
	}
	
	@RequestMapping("/querymapdata")
	@ResponseBody
	public List<Map> querymapdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		return trsService.selectMapData();
	}
	
	@RequestMapping("/queryGenealogyDdata")
	@ResponseBody
	public String queryGenealogyDdata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");// 获取主体身份代码
		String sourceflag = req.getParameter("sourceflag");
		String entname = req.getParameter("entname");
		String entype = req.getParameter("entype");
		String findGen=req.getParameter("findGen")==null?"3":"".equals(req.getParameter("findGen").trim())?"4":req.getParameter("findGen");
		String resultStr= trsService.selectGenealogyDdata(pripid,sourceflag,entname,entype,Integer.parseInt(findGen));
		/*String resultStr= trsService.selectGenealogyData(pripid,sourceflag,entname,entype,Integer.parseInt(findGen));
		log.info("真假："+StringUtils.isBlank(resultStr));
		if(StringUtils.isBlank(resultStr)){
			resultStr = trsService.selectGenealogyDdata(pripid,sourceflag,entname,entype,Integer.parseInt(findGen));
			trsService.insertGenealogyData(pripid,resultStr);
		}*/
		return resultStr;
	}
	
	@RequestMapping("/queryGenealogyDdataNew")
	@ResponseBody
	public String queryGenealogyDdataNew(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");// 获取主体身份代码
		String entname = req.getParameter("entname");
		String allPripid = req.getParameter("allPripid");
		String resultStr= trsService.selectGenealogyDdataNew(pripid,entname,allPripid);
		/*String resultStr= trsService.selectGenealogyData(pripid,sourceflag,entname,entype,Integer.parseInt(findGen));
		log.info("真假："+StringUtils.isBlank(resultStr));
		if(StringUtils.isBlank(resultStr)){
			resultStr = trsService.selectGenealogyDdata(pripid,sourceflag,entname,entype,Integer.parseInt(findGen));
			trsService.insertGenealogyData(pripid,resultStr);
		}*/
		return resultStr;
	}
	
	@RequestMapping("/queryGenealogyDdataNew1")
	@ResponseBody
	public String queryGenealogyDdataNew1(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid = req.getParameter("pripid");// 获取主体身份代码
		String entname = req.getParameter("entname");
		String allPripid = req.getParameter("allPripid");
		String findGen=req.getParameter("findGen")==null?"3":"".equals(req.getParameter("findGen").trim())?"4":req.getParameter("findGen");
		String resultStr= trsService.selectGenealogyDdataNew1(pripid,entname,allPripid,Integer.parseInt(findGen));
		return resultStr;
	}
	
	@ResponseBody
	@RequestMapping("zpDetail")
	public Map<String,Object> zpDetail(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid=req.getParameter("pripid");//获取主体身份代码
		String name=req.getParameter("name");
		String category=req.getParameter("category");
		String strIndex=null;
		log.info("类别："+category+"标识："+pripid+"名称："+name);
		List<Map> list = trsService.selectScztDetail(pripid,name,category,false);
		if("2".equals(category)){//高级人员
			strIndex = "50";
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		boolean flag=false;
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
			if("2".equals(category)){//高级人员
				dataMap.put("posbrform", CodeToValue.codeToValue("t_dm_zwcsfsdm",(String)dataMap.get("posbrform")));
				dataMap.put("sex", CodeToValue.codeToValue("t_dm_xbdm",(String)dataMap.get("sex")));
				dataMap.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype")));
				dataMap.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)dataMap.get("country")));
				dataMap.put("polstand", CodeToValue.codeToValue("T_DM_ZZMMDM",(String)dataMap.get("polstand")));
				dataMap.put("litedeg", CodeToValue.codeToValue("T_DM_WHCD",(String)dataMap.get("litedeg")));
				dataMap.put("nation", CodeToValue.codeToValue("T_DM_MZDM",(String)dataMap.get("nation")));
				returnMap.put("entname", "姓名:"+dataMap.get("name"));
				returnMap.put("regno", "证件号码:"+dataMap.get("cerno"));
				flag = true;
			}else{
				returnMap.put("entname", "主体名称:"+dataMap.get("entname"));
				returnMap.put("regno", "注册号:"+dataMap.get("regno"));
			}
		}else{//如果没有数据，查看是否是法人，或者是自然人
			if("0".equals(category)){//法人
				list = trsService.selectScztDetail(pripid,name,category,true);
				if(list!=null && list.size()>0){
					dataMap = list.get(0);
					dataMap.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype")));
					dataMap.put("currency", CodeToValue.codeToValue("t_dm_bzdm",(String)dataMap.get("currency")));
					dataMap.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)dataMap.get("country")));
					returnMap.put("entname", "投资人名称:"+dataMap.get("inv"));
					returnMap.put("regno", "投资人编号:"+dataMap.get("regno"));
					strIndex = "51";
				}
			}else if("2".equals(category)){//自然人
				list = trsService.selectScztDetail(pripid,name,category,true);
				if(list!=null && list.size()>0){
					dataMap = list.get(0);
					dataMap.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype")));
					dataMap.put("currency", CodeToValue.codeToValue("t_dm_bzdm",(String)dataMap.get("currency")));
					dataMap.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)dataMap.get("country")));
					returnMap.put("entname", "投资人名称:"+dataMap.get("inv"));
					returnMap.put("regno", "证件号码:"+dataMap.get("cerno"));
					strIndex = "52";
				}
			}
		}
		
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			String type = (String)dataMap.get("type");
			if(type!=null && type.trim()!=""){
				strIndex=type.substring(0, 1);
			}
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap = trsService.selectShowTab(strIndex);
				if(flag){//构建高级人员的显示内容
					if("中国".equals((String)dataMap.get("country"))){//去除外国和港澳
						sortMap.remove("来华日期");
						sortMap.remove("在华居住期限");
						sortMap.remove("特别行政区护照号");
						sortMap.remove("特别行政区护照签定日期");
						sortMap.remove("特别行政区护照有效期");
						sortMap.remove("通行证号");
						sortMap.remove("通行证签发日期");
						sortMap.remove("通行证有效日期");
						sortMap.remove("回乡证");
						sortMap.remove("回乡证签定日期");
						sortMap.remove("回乡证有效日期");
					}else if ("澳门".equals((String)dataMap.get("country")) || "香港".equals((String)dataMap.get("country"))){//去除外国
						sortMap.remove("来华日期");
						sortMap.remove("在华居住期限");
					}else{//去除港澳
						sortMap.remove("特别行政区护照号");
						sortMap.remove("特别行政区护照签定日期");
						sortMap.remove("特别行政区护照有效期");
						sortMap.remove("通行证号");
						sortMap.remove("通行证签发日期");
						sortMap.remove("通行证有效日期");
						sortMap.remove("回乡证");
						sortMap.remove("回乡证签定日期");
						sortMap.remove("回乡证有效日期");
					}
				}
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				}
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
	
	@ResponseBody
	@RequestMapping("zpOverview")
	public Map<String,Object> zpOverview(OptimusRequest req, OptimusResponse res) throws OptimusException {
		String pripid=req.getParameter("pripid");//获取主体身份代码
		String name=req.getParameter("name");
		String category=req.getParameter("category");
		log.info("类别："+category+"标识："+pripid+"名称："+name);
		List<Map> list = trsService.selectScztDetail(pripid,name,category,false);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		StringBuffer returnString = new StringBuffer("<table cellspacing=\"0\" cellpadding=\"0\">");
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
			if("2".equals(category)){//高级人员
				returnString.append("<tr class=\"row1\"><td class=\"col1\">证件号码：</td><td class=\"col2\">").append(dataMap.get("cerno")==null?"":dataMap.get("cerno")).append("</td></tr>");
				returnString.append("<tr class=\"row2\"><td class=\"col1\">证件类型：</td><td class=\"col2\">").append(CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype"))).append("</td></tr>");
				returnString.append("<tr class=\"row1\"><td class=\"col1\">职务：</td><td class=\"col2\">").append(dataMap.get("cnposition")==null?"":dataMap.get("cnposition")).append("</td></tr>");
				returnString.append("<tr class=\"row2\"><td class=\"col1\">性别：</td><td class=\"col2\">").append(CodeToValue.codeToValue("t_dm_xbdm",(String)dataMap.get("sex"))).append("</td></tr>");
				returnString.append("<tr class=\"row1\"><td class=\"col1\">籍贯：</td><td class=\"col2\">").append(dataMap.get("nativeplace")==null?"":dataMap.get("nativeplace")).append("</td></tr>");
				/*dataMap.put("posbrform", CodeToValue.codeToValue("t_dm_zwcsfsdm",(String)dataMap.get("posbrform")));
				dataMap.put("sex", CodeToValue.codeToValue("t_dm_xbdm",(String)dataMap.get("sex")));
				dataMap.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype")));
				dataMap.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)dataMap.get("country")));
				dataMap.put("polstand", CodeToValue.codeToValue("T_DM_ZZMMDM",(String)dataMap.get("polstand")));
				dataMap.put("litedeg", CodeToValue.codeToValue("T_DM_WHCD",(String)dataMap.get("litedeg")));
				dataMap.put("nation", CodeToValue.codeToValue("T_DM_MZDM",(String)dataMap.get("nation")));
				returnMap.put("entname", "姓名："+dataMap.get("name"));
				returnMap.put("regno", "证件号码："+dataMap.get("cerno"));*/
			}else{
				returnString.append("<tr class=\"row1\"><td class=\"col1\">注册号：</td><td class=\"col2\">").append(dataMap.get("regno")==null?"":dataMap.get("regno")).append("</td></tr>");
				returnString.append("<tr class=\"row2\"><td class=\"col1\">企业类型：</td><td class=\"col2\">").append(dataMap.get("enttype")==null?"":dataMap.get("enttype")).append("</td></tr>");
				returnString.append("<tr class=\"row1\"><td class=\"col1\">企业状态：</td><td class=\"col2\">").append(dataMap.get("servicestate")==null?"":dataMap.get("servicestate")).append("</td></tr>");
				returnString.append("<tr class=\"row2\"><td class=\"col1\">企业经济性质：</td><td class=\"col2\">").append(dataMap.get("economicproperty")==null?"":dataMap.get("economicproperty")).append("</td></tr>");
				returnString.append("<tr class=\"row1\"><td class=\"col1\">成立日期：</td><td class=\"col2\">").append(dataMap.get("estdate")==null?"":dataMap.get("estdate")).append("</td></tr>");
			}
		}else{//如果没有数据，查看是否是法人，或者是自然人
			if("0".equals(category)){//法人
				list = trsService.selectScztDetail(pripid,name,category,true);
				if(list!=null && list.size()>0){
					dataMap = list.get(0);
					returnString.append("<tr class=\"row1\"><td class=\"col1\">出资方式：</td><td class=\"col2\">").append(dataMap.get("conform")==null?"":dataMap.get("conform")).append("</td></tr>");
					returnString.append("<tr class=\"row2\"><td class=\"col1\">认缴出资额：</td><td class=\"col2\">").append(dataMap.get("subconam")==null?"":dataMap.get("subconam")+"万元").append("</td></tr>");
					returnString.append("<tr class=\"row1\"><td class=\"col1\">认缴出资日期：</td><td class=\"col2\">").append(dataMap.get("subcondata")==null?"":dataMap.get("subcondata")).append("</td></tr>");
					returnString.append("<tr class=\"row2\"><td class=\"col1\">实缴出资额：</td><td class=\"col2\">").append(dataMap.get("acconam")==null?"":dataMap.get("acconam")+"万元").append("</td></tr>");
					returnString.append("<tr class=\"row1\"><td class=\"col1\">实缴出资日期：</td><td class=\"col2\">").append(dataMap.get("acconbegdate")==null?"":dataMap.get("acconbegdate")).append("</td></tr>");
					/*dataMap.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype")));
					dataMap.put("currency", CodeToValue.codeToValue("t_dm_bzdm",(String)dataMap.get("currency")));
					dataMap.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)dataMap.get("country")));
					returnMap.put("entname", "投资人名称:"+dataMap.get("inv"));
					returnMap.put("regno", "注册号:"+dataMap.get("regno"));*/
				}
			}else if("2".equals(category)){//自然人
				list = trsService.selectScztDetail(pripid,name,category,true);
				if(list!=null && list.size()>0){
					dataMap = list.get(0);
					returnString.append("<tr class=\"row1\"><td class=\"col1\">证件号码：</td><td class=\"col2\">").append(dataMap.get("cerno")==null?"":dataMap.get("cerno")).append("</td></tr>");
					returnString.append("<tr class=\"row2\"><td class=\"col1\">证件类型：</td><td class=\"col2\">").append(CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype"))).append("</td></tr>");
					returnString.append("<tr class=\"row1\"><td class=\"col1\">出资方式：</td><td class=\"col2\">").append(dataMap.get("conform")==null?"":dataMap.get("conform")).append("</td></tr>");
					returnString.append("<tr class=\"row2\"><td class=\"col1\">认缴出资额：</td><td class=\"col2\">").append(dataMap.get("subconam")==null?"":dataMap.get("subconam")+"万元").append("</td></tr>");
					returnString.append("<tr class=\"row1\"><td class=\"col1\">实缴出资额：</td><td class=\"col2\">").append(dataMap.get("acconam")==null?"":dataMap.get("acconam")+"万元").append("</td></tr>");
					/*dataMap.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)dataMap.get("certype")));
					dataMap.put("currency", CodeToValue.codeToValue("t_dm_bzdm",(String)dataMap.get("currency")));
					dataMap.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)dataMap.get("country")));
					returnMap.put("entname", "投资人名称:"+dataMap.get("inv"));
					returnMap.put("regno", "证件号码:"+dataMap.get("cerno"));*/
				}
			}
		}
		returnString.append("</table>");
		returnMap.put("returnString", returnString);
		return returnMap;
	}
}
