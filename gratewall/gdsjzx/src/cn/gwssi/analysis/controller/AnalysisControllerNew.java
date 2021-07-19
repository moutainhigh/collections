package cn.gwssi.analysis.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gwssi.analysis.service.AnalysisServiceNew;
import cn.gwssi.resource.CodeToValue;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 分析报表统一接口
 * @author wuyincheng ,Aug 23, 2016
 */
@Controller
@RequestMapping("/analysis")
public class AnalysisControllerNew {
	
	@Autowired
	private AnalysisServiceNew analysisServiceNew;
	
	/**
	 * -------时间维度
	 * dateType 0 - 按日  1 - 按月
	 * startTime 开始时间
	 * endTime 结束时间
	 * isBG  0 - 非变更信息  1 - 变更表  (识别取哪一张表数据)
	 * isBoth 0 - 仅输出一列统计值  1 - 两列（户数&资本金），measure="户数code,资本金code"
	 * -------分析指标维度
	 * regorg_p  区域    regorg 行政
	 * indus_p1 产业 1|2|3 indus_p2(ABCDEFG...- Z)   indus - 行业
	 * ent 企业
	 * dom_p 农资、私营、农合   dom 资金规模
	 * measure 度量维度
	 * econ_p 内资、私营、外企  econ 经济性质
	 * organ_p 组织形式第一级 organ 第二级
	 * 
	 * ------------------输出维度[列]
	 * out="a.name,b.name" 除sum(value),time之外的列;
	 *                    如果为户数&资本金,out.lenth = 1 "
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/anaAllPageInfo")
	@ResponseBody
	public List<Map> getYannualReportPageInfo(OptimusRequest req, OptimusResponse res) {
//	public String getYannualReportPageInfo(OptimusRequest req, OptimusResponse res) {
		String out = defaultValue(req.getParameter("out"), "time");  //x未指定则默认时间
		final String [] outData = out.split(",");
		String ent = req.getParameter("ent");//组长装数据
		if(StringUtils.isNotBlank(ent)){
			ent = initData(ent);
		}
		HashMap<String, String> conditions = new HashMap<String, String>(16);
		putIfNotNull(conditions,"startTime", req.getParameter("startTime"));
		putIfNotNull(conditions,"endTime",   req.getParameter("endTime"));
		putIfNotNull(conditions,"regorg_p",  req.getParameter("regorg_p"));
		putIfNotNull(conditions,"regorg",    req.getParameter("regorg"));
		putIfNotNull(conditions,"indus_p1",  req.getParameter("indus_p1"));
		putIfNotNull(conditions,"indus_p2",  req.getParameter("indus_p2"));
		putIfNotNull(conditions,"indus", 	 req.getParameter("indus"));
		putIfNotNull(conditions,"ent",   	 ent);//req.getParameter("ent")
		putIfNotNull(conditions,"dom_p",  	 req.getParameter("dom_p"));
		putIfNotNull(conditions,"dom",    	 req.getParameter("dom"));
		putIfNotNull(conditions,"measure",   req.getParameter("measure"));
		putIfNotNull(conditions,"econ_p", 	 req.getParameter("econ_p"));
		putIfNotNull(conditions,"econ",   	 req.getParameter("econ"));
		putIfNotNull(conditions,"organ_p",   req.getParameter("organ_p"));
		putIfNotNull(conditions,"organ",     req.getParameter("organ"));
		putIfNotNull(conditions,"measure",   req.getParameter("measure"));
		String dateType = defaultValue(req.getParameter("dateType"), "0");
		String isBG = defaultValue(req.getParameter("isBG"), "0");
		String isBoth = defaultValue(req.getParameter("isBoth"), "0");
		return analysisServiceNew.selectAnalysisData(dateType, isBG, isBoth, outData, conditions);
	}
	
	private String initData(String ent) {
		String returnStr="";
		try {
			String[] ents = ent.split(","); 
			Set<String> keySet = new HashSet<String>();
			for(String s:ents){
				keySet.add(CodeToValue.codeToValue("T_DM_QYLXDM_FATHER",s));//找到父亲，放入set
			}
			List<Map> params = CodeToValue.codeToValueList("T_DM_QYLXDM_FATHER");//获取所有的
			StringBuffer sbf = new StringBuffer("");
			for(Map<String,String> m : params){
				if(keySet.contains(m.get("text"))){
					sbf.append(m.get("value")).append(",");
				}
				/*for(Map.Entry<String, String> entry:m.entrySet()){ 
					System.out.println(entry.getValue());
					System.out.println(entry.getKey());
					if(keySet.contains(entry.getValue())){
						sbf.append(entry.getKey()).append(",");
					}
				}*/ 
			}
			returnStr = sbf.toString();
			if(StringUtils.isNotBlank(returnStr)){
				if(returnStr.contains(",")){
					returnStr = returnStr.substring(0,returnStr.length()-1);
				}
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	private static void putIfNotNull(Map<String, String> container, String key, String value){
		if(value != null)
			container.put(key, value);
	}
	
	private static String defaultValue(String v, String defaultValue){
		if(v == null || "".equals(v.trim()))
			return defaultValue;
		return v;
	}
	
}
