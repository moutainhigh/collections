package com.gwssi.report.query_jiean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.report.service.QueryJieAnService;

@SuppressWarnings({ "rawtypes","unchecked" })
@Controller
@RequestMapping("/queryJieAn")
public class QueryJieAnController {
	@Autowired
	private QueryJieAnService query;
	private QueryJieAnUtils qu = new QueryJieAnUtils();
	public final static SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	
	/**
	 * 获取登记部门树形结构
	 */
	@RequestMapping("/getRegDepTree")
	@ResponseBody
	public  List<Map> getRegDepTree (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getRegDepTree();
	}
	
	/**
	 * 获取问题类型树形结构
	 */
	@RequestMapping("/getWenTiLeiXingTree")
	@ResponseBody
	public  List<Map> getWenTiLeiXingTree (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getWenTiLeiXingTree();
	}
	
	/**
	 * 获取涉及客体类型树形结构
	 */
	@RequestMapping("/getKeTiLeiXingTree")
	@ResponseBody
	public  List<Map> getKeTiLeiXingTree (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getKeTiLeiXingTree();
	}
	
	/**
	 * 获取涉及主体类型树形结构
	 */
	@RequestMapping("/getZhuTiLeiXingTree")
	@ResponseBody
	public  List<Map> getZhuTiLeiXingTree (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getZhuTiLeiXingTree();
	}
	
	/**
	 * 获取行业类型树形结构
	 */
	@RequestMapping("/getHangYeLeiXingTree")
	@ResponseBody
	public  List<Map> getHangYeLeiXingTree (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getHangYeLeiXingTree();
	}
	
	/**
	 * 获取违法行为种类树形结构
	 */
	@RequestMapping("/getWeiFaZhongLeiTree")
	@ResponseBody
	public  List<Map> getWeiFaZhongLeiTree (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getWeiFaZhongLeiTree();
	}
	
	/**
	 * 获取行政处罚措施种类
	 */
	@RequestMapping("/getXingZhengChuFaSelect")
	@ResponseBody
	public  List<Map> getXingZhengChuFaSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getXingZhengChuFaSelect();
	}
	
	/**
	 * 获取执行类别种类
	 */
	@RequestMapping("/getZhiXingLeiBieSelect")
	@ResponseBody
	public  List<Map> getZhiXingLeiBieSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getZhiXingLeiBieSelect();
	}
	
	/**
	 * 获取接收方式类型
	 */
	@RequestMapping("/getJieShouFangShiSelect")
	@ResponseBody
	public  List<Map> getJieShouFangShiSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getJieShouFangShiSelect();
	}
	
	/**
	 * 获取网站类型
	 */
	@RequestMapping("/getWangZhanLeiXingSelect")
	@ResponseBody
	public  List<Map> getWangZhanLeiXingSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getWangZhanLeiXingSelect();
	}
	
	/**
	 * 获取侵权类型
	 */
	@RequestMapping("/getQinQuanLeiXingSelect")
	@ResponseBody
	public  List<Map> getQinQuanLeiXingSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getQinQuanLeiXingSelect();
	}
	
	/**
	 * 获取未履行义务类别
	 */
	@RequestMapping("/getYiWuLeiXingSelect")
	@ResponseBody
	public  List<Map> getYiWuLeiXingSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getYiWuLeiXingSelect();
	}
	
	/**
	 * 获取信息件类型
	 */
	@RequestMapping("/getInfoTypeSelect")
	@ResponseBody
	public  List<Map> getInfoTypeSelect (HttpServletRequest req, HttpServletResponse res){
		
	 return query.getInfoTypeSelect();
	}
	
	/**
	 * 获取处罚种类
	 */
	@RequestMapping("/getChuFaZhongLeiSelect")
	@ResponseBody
	public  List<Map> getChuFaZhongLeiSelect (HttpServletRequest req, HttpServletResponse res){
		
		return query.getChuFaZhongLeiSelect();
	}
	
	
	/**
	 * 获取业务范围
	 */
	@RequestMapping("/getYeWuFanWeiTree")
	@ResponseBody
	public  List<Map> getYeWuFanWeiTree (HttpServletRequest req, HttpServletResponse res){
		
		return query.getYeWuFanWeiTree();
	}
	
	/**
	 * 获取投诉方人员身份
	 */
	@RequestMapping("/getRenYuanShenFenSelect")
	@ResponseBody
	public  List<Map> getRenYuanShenFenSelect (HttpServletRequest req, HttpServletResponse res){
		
		return query.getRenYuanShenFenSelect();
	}
	
	/**
	 * 获取被诉方企业类型
	 */
	@RequestMapping("/getQiYeLeiXingTree")
	@ResponseBody
	public  List<Map> getQiYeLeiXingTree (HttpServletRequest req, HttpServletResponse res){
		
		return query.getQiYeLeiXingTree();
	}
	/**
	 * 获取关键字类型
	 */
	@RequestMapping("/getGuanJianZiSelect")
	@ResponseBody
	public  List<Map> getGuanJianZiSelect (HttpServletRequest req, HttpServletResponse res){
		
		return query.getGuanJianZiSelect();
	}
	
	
	
	/**
	 * 结案信息 by处罚种类
	 */
	
	@RequestMapping("/chuFaZhongLei")
	@ResponseBody
	public  List<Map> chuFaZhongLei(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		return query.chuFaZhongLei(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by处罚种类
	 */
	@RequestMapping(value = "/downChuFaZhongLei")
	@ResponseBody
	public void downChuFaZhongLei(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		List<Map>  num = query.chuFaZhongLei(map, 2, req);
		String str = qu.tableUtilByName(num,"处罚问题");
		String filename = "结案信息申诉举报处罚问题统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by办结部门
	 */
	@RequestMapping("/banJieBuMen")
	@ResponseBody
	public  List<Map> banJieBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.banJieBuMen(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by办结部门
	 */
	@RequestMapping(value = "/downBanJieBuMen")
	@ResponseBody
	public void downBanJieBuMen(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.banJieBuMen(map, 2, req);
		String str = qu.tableUtilByName(num,"办结部门");
		String filename = "结案信息申诉举报办结部门统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by申诉举报基本问题
	 */
	@RequestMapping("/wenTiLeiXing")
	@ResponseBody
	public  List<Map> wenTiLeiXing(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		//map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.wenTiLeiXing(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by申诉举报基本问题
	 */
	@RequestMapping(value = "/downWenTiLeiXing")
	@ResponseBody
	public void downWenTiLeiXing(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		//map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.wenTiLeiXing(map, 2, req);
		String str = qu.tableUtilByName(num,"申诉举报基本问题");
		String filename = "结案信息申诉举报基本问题统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by侵权类型
	 */
	@RequestMapping("/qinQuanLeiXing")
	@ResponseBody
	public  List<Map> qinQuanLeiXing(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		//map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.qinQuanLeiXing(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by侵权类型
	 */
	@RequestMapping(value = "/downQinQuanLeiXing")
	@ResponseBody
	public void downQinQuanLeiXing(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		//map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.qinQuanLeiXing(map, 2, req);
		String str = qu.tableUtilByName(num,"侵权类型");
		String filename = "结案信息申诉举报侵权类型统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by涉及客体
	 */
	@RequestMapping("/sheJiKeTi")
	@ResponseBody
	public  List<List<Map>> sheJiKeTi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		//map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.sheJiKeTi(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by涉及客体
	 */
	@RequestMapping(value = "/downSheJiKeTi")
	@ResponseBody
	public void downSheJiKeTi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		//map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<List<Map>>  num = query.sheJiKeTi(map, 2, req);
		String str = qu.excelFileStreamForObject(num);
		String filename = "结案信息申诉举报涉及客体统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by未履行义务
	 */
	@RequestMapping("/yiWuLeiXing")
	@ResponseBody
	public  List<Map> yiWuLeiXing(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		//map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.yiWuLeiXing(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by未履行义务
	 */
	@RequestMapping(value = "/downYiWuLeiXing")
	@ResponseBody
	public void downYiWuLeiXing(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		//map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.yiWuLeiXing(map, 2, req);
		String str = qu.tableUtilByName(num,"未履行义务");
		String filename = "结案信息申诉举报未履行义务统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by消费事件
	 */
	@RequestMapping("/xiaoFeiShiJian")
	@ResponseBody
	public  List<Map> xiaoFeiShiJian(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.xiaoFeiShiJian(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by消费事件
	 */
	@RequestMapping(value = "/downXiaoFeiShiJian")
	@ResponseBody
	public void downXiaoFeiShiJian(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.xiaoFeiShiJian(map, 2, req);
		String str = qu.tableUtilByName(num,"消费事件");
		String filename = "结案信息申诉举报消费事件统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by行政强制措施类型
	 */
	@RequestMapping("/xingZhengCuoShi")
	@ResponseBody
	public  List<Map> xingZhengCuoShi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		//map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.xingZhengCuoShi(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by行政强制措施类型
	 */
	@RequestMapping(value = "/downXingZhengCuoShi")
	@ResponseBody
	public void downXingZhengCuoShi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		//map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.xingZhengCuoShi(map, 2, req);
		String str = qu.tableUtilByName(num,"行政强制措施类型");
		String filename = "结案信息申诉举报行政强制措施类型统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息 by执行类别
	 */
	@RequestMapping("/zhiXingLeiBie")
	@ResponseBody
	public  List<Map> zhiXingLeiBie(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		//map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		return query.zhiXingLeiBie(map,1,req);
	}
	
	/**
	 * 下载 --结案信息 by执行类别
	 */
	@RequestMapping(value = "/downZhiXingLeiBie")
	@ResponseBody
	public void downZhiXingLeiBie(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime")); 
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("ketileixing",req.getParameter("ketileixing"));
		map.put("zhutileixing",req.getParameter("zhutileixing"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("weifazhonglei",req.getParameter("weifazhonglei"));
		map.put("xingzhengchufa",req.getParameter("xingzhengchufa"));
		//map.put("zhixingleibie",req.getParameter("zhixingleibie"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("wangzhanleixing",req.getParameter("wangzhanleixing"));
		map.put("qinquanleixing",req.getParameter("qinquanleixing"));
		map.put("yiwuleixing",req.getParameter("yiwuleixing"));
		map.put("chufazhonglei",req.getParameter("chufazhonglei"));
		List<Map>  num = query.zhiXingLeiBie(map, 2, req);
		String str = qu.tableUtilByName(num,"执行类别");
		String filename = "结案信息申诉举报执行类别统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * 结案信息咨询建议by承办部门
	 */
	@RequestMapping("/zj_ChengBanBuMen")
	@ResponseBody
	public  List<Map> zj_ChengBanBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_ChengBanBuMen(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by承办部门
	 */
	@RequestMapping("/downZJ_ChengBanBuMen")
	@ResponseBody
	public  void downZJ_ChengBanBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<Map>  num = query.zj_ChengBanBuMen(map, 2, req);
		String str = qu.tableUtilByNameForZiXunJianYi(num,"承办部门");
		String filename = "结案信息咨询建议承办部门统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 结案信息咨询建议by接收方式
	 */
	@RequestMapping("/zj_JieShouFangShi")
	@ResponseBody
	public  List<Map> zj_JieShouFangShi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_JieShouFangShi(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by接收方式
	 */
	@RequestMapping("/downZJ_JieShouFangShi")
	@ResponseBody
	public  void downZJ_JieShouFangShi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<Map>  num = query.zj_JieShouFangShi(map, 2, req);
		String str = qu.tableUtilByNameForZiXunJianYi(num,"接收方式");
		String filename = "结案信息咨询建议信息件接收方式统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	
	
	
	/**
	 * 结案信息咨询建议by行业类型
	 */
	@RequestMapping("/zj_HangYeLeiXing")
	@ResponseBody
	public  List<Map> zj_HangYeLeiXing(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_HangYeLeiXing(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by行业类型
	 */
	@RequestMapping("/downZJ_HangYeLeiXing")
	@ResponseBody
	public  void downZJ_HangYeLeiXing(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<Map>  num = query.zj_HangYeLeiXing(map, 2, req);
		String str = qu.tableUtilByNameForZiXunJianYi(num,"行业类型");
		String filename = "结案信息咨询建议信息件行业类型统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	
	/**
	 * 结案信息咨询建议by咨询所属部门
	 */
	@RequestMapping("/zj_SuoShuBuMen")
	@ResponseBody
	public  List<Map> zj_SuoShuBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_SuoShuBuMen(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by咨询所属部门
	 */
	@RequestMapping("/downZJ_SuoShuBuMen")
	@ResponseBody
	public  void downZJ_SuoShuBuMen(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<Map>  num = query.zj_SuoShuBuMen(map, 2, req);
		String str = qu.tableUtilByNameForZiXunJianYi(num,"咨询所属部门");
		String filename = "结案信息咨询建议信息件咨询所属部门统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	
	/**
	 * 结案信息咨询建议by业务范围
	 */
	@RequestMapping("/zj_YeWuFanWei")
	@ResponseBody
	public  List<Map> zj_YeWuFanWei(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_YeWuFanWei(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by业务范围
	 */
	@RequestMapping("/downZJ_YeWuFanWei")
	@ResponseBody
	public  void downZJ_YeWuFanWei(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<Map>  num = query.zj_YeWuFanWei(map, 2, req);
		String str = qu.tableUtilByNameForZiXunJianYi(num,"业务范围");
		String filename = "结案信息咨询建议信息件业务范围统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 结案信息咨询建议by基本问题
	 */
	@RequestMapping("/zj_JiBenWenTi")
	@ResponseBody
	public  List<Map> zj_JiBenWenTi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_JiBenWenTi(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by基本问题
	 */
	@RequestMapping("/downZJ_JiBenWenTi")
	@ResponseBody
	public  void downZJ_JiBenWenTi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<Map>  num = query.zj_JiBenWenTi(map, 2, req);
		String str = qu.tableUtilByNameForZiXunJianYi(num,"基本问题");
		String filename = "结案信息咨询建议信息件基本问题统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 结案信息咨询建议by涉及客体
	 */
	@RequestMapping("/zj_SheJiKeTi")
	@ResponseBody
	public  List<List<Map>> zj_SheJiKeTi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		return query.zj_SheJiKeTi(map,1,req);
	}
	
	/**
	 * 下载---结案信息咨询建议by涉及客体
	 */
	@RequestMapping("/downZJ_SheJiKeTi")
	@ResponseBody
	public  void downZJ_SheJiKeTi(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String,String> map=new HashMap();
		map.put("begintime",req.getParameter("begintime"));
		map.put("endtime",req.getParameter("endtime"));
		map.put("hbegintime",req.getParameter("hbegintime"));
		map.put("hendtime",req.getParameter("hendtime"));
		map.put("infotype",req.getParameter("infotype"));
		map.put("dengjibumen",req.getParameter("dengjibumen"));
		map.put("chengbanbumen",req.getParameter("chengbanbumen"));
		map.put("jieshoufangshi",req.getParameter("jieshoufangshi"));
		map.put("suoshubumen",req.getParameter("suoshubumen"));
		map.put("wentileixing",req.getParameter("wentileixing"));
		map.put("yewufanwei",req.getParameter("yewufanwei"));
		map.put("renyuanshenfen",req.getParameter("renyuanshenfen"));
		map.put("hangyeleixing",req.getParameter("hangyeleixing"));
		map.put("qiyeleixing",req.getParameter("qiyeleixing"));
		List<List<Map>>  num = query.zj_SheJiKeTi(map, 2, req);
		String str = qu.excelFileStreamForZiXunJianYiObject(num);
		String filename = "结案信息咨询建议信息件涉及客体统计报表" + "(" + req.getParameter("begintime") + "至" + req.getParameter("endtime") + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 消委会投诉增长或下降居前十位的商品和服务
	 */
	@RequestMapping("/xiaoWeiHuiQianShi")
	@ResponseBody
	public  List<Map> xiaoWeiHuiQianShi(HttpServletRequest req,
			HttpServletResponse res) {
		String tongjinianfen=req.getParameter("tongjinianfen");
		if (tongjinianfen==null||tongjinianfen.trim().length()!=4) {
			return null;
		}
		String shangnian=req.getParameter("shangnian");
		if (shangnian==null||shangnian.trim().length()!=4) {
			return null;
		}
		return query.xiaoWeiHuiQianShi(tongjinianfen,shangnian,1,req);
	}
	
	/**
	 * 下载---消委会投诉增长或下降居前十位的商品和服务
	 */
	@RequestMapping("/downXiaoWeiHuiQianShi")
	@ResponseBody
	public  void downXiaoWeiHuiQianShi(HttpServletRequest req,
			HttpServletResponse res) {
		String tongjinianfen=req.getParameter("tongjinianfen");
		String shangnian=req.getParameter("shangnian");
		List<Map> num = query.xiaoWeiHuiQianShi(tongjinianfen,shangnian,2,req);
		String str = qu.downXiaoWeiHuiQianShi(tongjinianfen,shangnian,num);
		String filename = "消委会投诉增长或下降居前十位的商品和服务" + "(" + tongjinianfen + "年"  + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 电子商务投诉热点情况统计表
	 */
	@RequestMapping("/dianZiShangWuReDian")
	@ResponseBody
	public  Map<String,Map<String,String>> dianZiShangWuReDian(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		String hbegintime=req.getParameter("hbegintime");
		String hendtime=req.getParameter("hendtime");
		return query.dianZiShangWuReDian(begintime,endtime,hbegintime,hendtime,1,req);
	}
	
	/**
	 * 下载---电子商务投诉热点情况统计表
	 */
	@RequestMapping("/downDianZiShangWuReDian")
	@ResponseBody
	public  void downDianZiShangWuReDian(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		String hbegintime=req.getParameter("hbegintime");
		String hendtime=req.getParameter("hendtime");
		Map<String, Map<String, String>> num = query.dianZiShangWuReDian(begintime,endtime,hbegintime,hendtime,2,req);
		String str = qu.downDianZiShangWuReDian(num);
		String filename = "电子商务投诉热点情况统计表" + "(" + begintime + "至" + endtime+ ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 电子商务投诉情况统计报表
	 */
	@RequestMapping("/dianZiShangWuTouSu")
	@ResponseBody
	public  List<List<Map>> dianZiShangWuTouSu(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		String hbegintime=req.getParameter("hbegintime");
		String hendtime=req.getParameter("hendtime");
		return query.dianZiShangWuTouSu(begintime,endtime,hbegintime,hendtime,1,req);
	}
	
	/**
	 * 下载---电子商务投诉情况统计报表
	 */
	@RequestMapping("/downDianZiShangWuTouSu")
	@ResponseBody
	public  void downDianZiShangWuTouSu(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		String hbegintime=req.getParameter("hbegintime");
		String hendtime=req.getParameter("hendtime");
		List<List<Map>> num = query.dianZiShangWuTouSu(begintime,endtime,hbegintime,hendtime,2,req);
		String str = qu.downDianZiShangWuTouSu(num);
		String filename = "电子商务投诉情况统计报表" + "(" + begintime + "至" + endtime+ ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 电子商务消费投诉情况统计报表
	 */
	@RequestMapping("/dianZiShangWuXiaoFei")
	@ResponseBody
	public  List<Map> dianZiShangWuXiaoFei(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		return query.dianZiShangWuXiaoFei(begintime,endtime,1,req);
	}
	
	/**
	 * 下载---电子商务消费投诉情况统计报表
	 */
	@RequestMapping("/downDianZiShangWuXiaoFei")
	@ResponseBody
	public  void downDianZiShangWuXiaoFei(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		List<Map> num = query.dianZiShangWuXiaoFei(begintime,endtime,2,req);
		String str = qu.downDianZiShangWuXiaoFei(num);
		String filename = "电子商务消费投诉情况统计报表" + "(" + begintime + "至" + endtime+ ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 全市质量监督案件统计表（举报）
	 */
	@RequestMapping("/zhiLiangJianDuJuBao")
	@ResponseBody
	public  List<Map> zhiLiangJianDuJuBao(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		return query.zhiLiangJianDuJuBao(begintime,endtime,1,req);
	}
	
	/**
	 * 下载---全市质量监督案件统计表（举报）
	 */
	@RequestMapping("/downZhiLiangJianDuJuBao")
	@ResponseBody
	public  void downZhiLiangJianDuJuBao(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		List<Map> num = query.zhiLiangJianDuJuBao(begintime,endtime,2,req);
		String str = qu.downZhiLiangJianDuJuBao(num);
		String filename = "全市质量监督案件统计表（举报）" + "(" + begintime + "至" + endtime+ ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	/**
	 * 全市质量监督案件统计表（举报及申诉）
	 */
	@RequestMapping("/zhiLiangJianDuJuBaoShenSu")
	@ResponseBody
	public  List<Map> zhiLiangJianDuJuBaoShenSu(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		return query.zhiLiangJianDuJuBaoShenSu(begintime,endtime,1,req);
	}
	
	/**
	 * 下载---全市质量监督案件统计表（举报及申诉）
	 */
	@RequestMapping("/downZhiLiangJianDuJuBaoShenSu")
	@ResponseBody
	public  void downZhiLiangJianDuJuBaoShenSu(HttpServletRequest req,
			HttpServletResponse res) {
		String begintime=req.getParameter("begintime");
		String endtime=req.getParameter("endtime");
		List<Map> num = query.zhiLiangJianDuJuBaoShenSu(begintime,endtime,2,req);
		String str = qu.downZhiLiangJianDuJuBaoShenSu(num);
		String filename = "全市质量监督案件统计表（举报及申诉）" + "(" + begintime + "至" + endtime+ ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(res,filename,str);
	}
	
	
	/**
	 * @param response
	 * @param filename
	 * @param sb
	 * 下载函数
	 */
	public void down(HttpServletResponse response, String filename, String sb) {
		InputStream inputStream = getStringStream(sb.toString());
		OutputStream outputStream = null;
		byte[] b = new byte[102400];
		int len = 0;
		try {
			outputStream = response.getOutputStream();
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(filename, "UTF-8"));
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将一个字符串转化为输入流
	 */
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes("UTF-8"));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 获取一个精确到秒的时间字符串
	 */
	public String getTimeString(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
}
