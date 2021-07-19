package com.gwssi.trs.controller;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;







import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;


import com.gwssi.trs.service.FoodService;
import com.gwssi.util.FoodCacheUtile;
import com.gwssi.util.FreemarkerUtil;



@Controller
@RequestMapping("/food")
public class FoodController {

	private static  Logger log=Logger.getLogger(MarketEntityController.class);

	@Autowired
	private FoodService foodService;

	/**基本信息（列表）
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{	
		String priPid = (String)req.getAttr("priPid");
		String lictype = (String)req.getAttr("lictype");
		Map<String,String> params = new HashMap<String,String>();
		List<Map> result = null;
		params.put("priPid", priPid);
		result = foodService.queryList(params, lictype);		
		res.addGrid("jbxxGrid", result, null);
	}

//	@RequestMapping("/queryCount")
//	@ResponseBody
//	public int querydata(OptimusRequest req, OptimusResponse res) throws OptimusException {
//		int i =caseService.queryRegCount(null);
//		return i;
//	} 

	/**
	 * 基本信息（表单）
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@ResponseBody
	@RequestMapping("scztjbxx")
	public Map<String,Object> sczt(OptimusRequest req, OptimusResponse res) throws OptimusException {
	
		String priPid=req.getParameter("id");//获取主体身份代码		
		String lictype=req.getParameter("lictype");//企业类型
		Map<String,String> params =new HashMap<String,String>();
		params.put("priPid", priPid);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();	
		List<Map> list =null;
		
		list = foodService.queryJbxx(params,lictype); //查询食品餐饮数据
		
		
		if(list!=null && list.size()>0){
			dataMap = list.get(0);
		}
		else{
			dataMap.put(null, null);
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String returnString = "";
		if(dataMap!=null && dataMap.size()>0){
			
			returnMap.put("caseType", dataMap.get("caseType"));
			try {
				Map<String,Object> datasMap = new HashMap<String,Object>();
				Map<String,Object> sortMap =FoodCacheUtile.getLinkedHashMap(lictype);
				for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
					sortMap.put(entry.getKey(), dataMap.get(entry.getValue()));
				} 
				datasMap.put("weaponMap", sortMap); 
				returnString = FreemarkerUtil.returnString("food.ftl", datasMap);
				sortMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
		returnMap.put("returnString", returnString);
		returnMap.put("entName", dataMap.get("entName"));
		returnMap.put("unitName", dataMap.get("unitName"));
		returnMap.put("companyName", dataMap.get("companyName"));
		returnMap.put("applicantName", dataMap.get("applicantName"));
		return returnMap;
	}
	
	/*
	 * 食品许可证列表详情
	 */
	
	@RequestMapping("/formdetail")
	public void formdetail(OptimusRequest req, OptimusResponse res)throws OptimusException{	
		String id = req.getParameter("id");
		String lictype = req.getParameter("lictype");
		Map<String,String> params = new HashMap<String,String>();
		List<Map> result = null;
		params.put("id", id);
		result = foodService.queryListDetail(params, lictype);
		if(result!=null || result.size()>0){
			res.addForm("formpanel", result.get(0), null);
		}
		else{
			res.addForm("formpanel", null, null);
		}
	}
	
	/*
	 * 食品许可证抽样报告
	 */
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@(DESCRIPTION= (ADDRESS=(PROTOCOL=TCP)(HOST=10.0.3.153)(PORT=1521)) (ADDRESS=(PROTOCOL=TCP)(HOST=10.0.3.159)(PORT=1521)) (LOAD_BALANCE=yes)(CONNECT_DATA=(SERVER=DEDICATED) (SERVICE_NAME=racdb5)))";
	public static final String USERNAME = "dc_dc";
	public static final String PASSWORD = "dc_dc";
	
	@RequestMapping("/sample")
	public void sample(OptimusRequest req, OptimusResponse response)throws OptimusException{
		Connection conn = null;
		Statement sta = null;
		ResultSet rs = null;
		String id = (String)req.getParameter("id");
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			sta = conn.createStatement();
			// image字段为BLOG字段
			String sql = "select IMAGE_CONTENT from dc_f1_SELF_MEDIA where sample_detail_product = '" + id + "'";
			System.out.println("sql____________________" + sql);
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				// 输出文件名
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("image_content");			
				InputStream input = blob.getBinaryStream();
				
				response.getHttpResponse().reset();
				response.getHttpResponse().setContentType("application/x-download;charset=GBK");
				String downFileName = "抽检报告.pdf";
				response.getHttpResponse().setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(downFileName, "UTF-8"));
				
				OutputStream out = response.getHttpResponse().getOutputStream();
				int len = (int) blob.length();
				byte buffer[] = new byte[len];
				while ((len = input.read(buffer)) != -1) {
					out.write(buffer, 0, len);
					out.flush();
				}
				
				out.close();
				input.close();
	
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				sta.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
