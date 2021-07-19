package com.gwssi.advancedSearch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.advancedSearch.service.AdvanceQueryService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/advQuery")
@SuppressWarnings("rawtypes")
public class AdvanceQueryController extends BaseController{
	
	
	
	private static final String USER_PARAM_SIGN = "（参数值）";
	@Autowired
	private AdvanceQueryService advancedQueryService;
	
	@RequestMapping("/getTopic")
	public void getTopic(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		List<Map> lstTopic=  advancedQueryService.getTopic();
		resp.addAttr("topicData",lstTopic);
	}
	
	@RequestMapping("/getTable")
	public void getTable(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String topicId=req.getParameter("topicId");
		List<Map> lstTable=  advancedQueryService.getTableByTopic(topicId);
		resp.addAttr("tableData", lstTable);
	}
	
	@RequestMapping("/getColumn")
	public void getColumn(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String tableId=req.getParameter("tableId");
		List<Map> lstColumn=  advancedQueryService.getColumnByTable(tableId);
		resp.addAttr("tableData", lstColumn);
	}
	
	@RequestMapping("/testsql")
	public void getAdvQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String sql=req.getParameter("testsql");
		if(sql==null){
			resp.addResponseBody("sql语句不能为空");
		}
		boolean flag = false;
		flag = advancedQueryService.check(sql);
		if(!flag){
			resp.addResponseBody("sql语句拼接异常");
		}else{
			List<Map> lstForm=  advancedQueryService.getAdvQueryList(sql);
			resp.addAttr("tableData", lstForm);
		};
	}
	
	
	
	@RequestMapping("/getQueryAdvList")
	public void getQueryAdvList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String sql=req.getParameter("sql");
		if(sql==null){
			throw new OptimusException("获取参数失败");
		}
		List<Map> lstForm=  advancedQueryService.getAdvQueryList(sql);
		resp.addAttr("tableData", lstForm);
	}
	
	@RequestMapping("/saveAdvInfo")
	public void saveAdvQueryInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String params= req.getParameter("datas");
		 advancedQueryService.saveAdvQueryInfo(params);
		 resp.addAttr("save","1");
	}
	
	
	@RequestMapping("/getList")
	public void getList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("formpanel");
		List<Map> lstColumn= advancedQueryService.getList(form);
		System.out.println(lstColumn.toString());
		resp.addGrid("gridpanel",lstColumn);
	}
	
	@RequestMapping("/delQuery")
	public void delQuery(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		advancedQueryService.delQuery(id);
		resp.addAttr("back","success");
	}
	
	@RequestMapping("/getQueryById")
	public void getQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("queryInfoFormPannel", advancedQueryService.getQueryById(id));
	}
	
	@RequestMapping("/doQuery")
	public void getHtml(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		resp.addResponseBody(advancedQueryService.getHtmlById(id));
	}
	
	
public static void main(String[] args) {
		//String id = "{\"baseInfo\":{\"queryName\":\"tt\",\"tableNames\":[{\"name_en\":\"DC_RA_UNIT_LOGOFF\",\"name_cn\":\"个体工商户注销信息\",\"column_en\":\"PRIPID\",\"column_cn\":\"主体身份代码\",\"column_ais\":\"\"},{\"name_en\":\"DC_RA_UNIT_LICE_INFO\",\"name_cn\":\"个体工商户证照信息\",\"column_en\":\"PRIPID\",\"column_cn\":\"主体身份代码\",\"column_ais\":\"\"},{\"name_en\":\"DC_RA_UNIT_LICE_INFO\",\"name_cn\":\"个体工商户证照信息\",\"column_en\":\"BLICTYPE\",\"column_cn\":\"证照类型CA50\",\"column_ais\":\"\"},{\"name_en\":\"DC_RA_UNIT_LOGOFF\",\"name_cn\":\"个体工商户注销信息\",\"column_en\":\"CANID\",\"column_cn\":\"注销信息ID\",\"column_ais\":\"\"}],\"sql\":\"SELECT DC_RA_UNIT_LOGOFF.PRIPID,DC_RA_UNIT_LICE_INFO.PRIPID,DC_RA_UNIT_LICE_INFO.BLICTYPE,DC_RA_UNIT_LOGOFF.CANID FROM DC_RA_UNIT_LOGOFF,DC_RA_UNIT_LICE_INFO WHERE  DC_RA_UNIT_LOGOFF.CANID=DC_RA_UNIT_LICE_INFO.BLICID \",\"condition\":[[{\"code\":\"DC_RA_UNIT_LOGOFF\",\"key\":\"c5d40459da134940b8c5feb7838a9b97\",\"title\":\"个体工商户注销信息\"},{\"code\":\"DC_RA_UNIT_LICE_INFO\",\"key\":\"63cc9ba223ea4af7aedd5a866fea7775\",\"title\":\"个体工商户证照信息\"}]],\"queryParam\":[],\"relationList\":[[\"{data:[{\\"leftTable\\" : {\\"id\\" : \\"c5d40459da134940b8c5feb7838a9b97\\",\\"name_en\\" : \\"DC_RA_UNIT_LOGOFF\\",\\"name_cn\\" : \\"个体工商户注销信息\\"} ,\\"leftColumn\\" : {\\"id\\" : \\"9dbab6bd5a214570983d00f65d84abe0\\",\\"name_en\\" : \\"CANID\\",\\"name_cn\\" : \\"注销信息ID\\"} ,\\"middleParen\\" : \\"=\\" ,\\"rightTable\\" : {\\"id\\" : \\"63cc9ba223ea4af7aedd5a866fea7775\\",\\"name_en\\" : \\"DC_RA_UNIT_LICE_INFO\\",\\"name_cn\\" : \\"个体工商户证照信息\\"} ,\\"rightColumn\\" : {\\"id\\" : \\"df515c89c59844109e4ce28fc393098b\\",\\"name_en\\" : \\"BLICID\\",\\"name_cn\\" : \"证照信息ID"}}]}\"]]}}";
	}
}
