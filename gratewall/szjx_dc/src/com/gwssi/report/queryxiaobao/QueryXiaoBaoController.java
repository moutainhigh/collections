package com.gwssi.report.queryxiaobao;

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
import com.gwssi.report.service.QueryXiaoBaoService;



@Controller
@RequestMapping("/queryXiaoBao")
public class QueryXiaoBaoController {
	@Autowired
	private QueryXiaoBaoService query;
	private QueryXiaoBaoUtil qu = new QueryXiaoBaoUtil();
	/**
	 * @param req
	 * @param res
	 * 督办统计报表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/duBan")
	@ResponseBody
	public List<Map> duBan(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.duBan(beginTime, endTime, 1, req);
	}

	/**
	 * 下载 --督办统计报表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downDuBan")
	@ResponseBody
	public void downDuBan(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.duBan(beginTime, endTime, 2, req);
		String str = qu.downDuBan(num);
		String filename = "督办统计报表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 分派至监管所信息件数据统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/fenPai")
	@ResponseBody
	public List<Map> fenPai(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.fenPai(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --分派至监管所信息件数据统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downFenPai")
	@ResponseBody
	public void downFenPai(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.fenPai(beginTime, endTime, 2, req);
		String str = qu.downFenPai(num);
		String filename = "分派至监管所信息件数据统计" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 各分局科所投诉举报登记办理情况
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/fenJuBanLi")
	@ResponseBody
	public List<Map> fenJuBanLi(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.fenJuBanLi(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --各分局科所投诉举报登记办理情况
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downFenJuBanLi")
	@ResponseBody
	public void downFenJuBanLi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.fenJuBanLi(beginTime, endTime, 2, req);
		String str = qu.downFenJuBanLi(num);
		String filename = "各分局科所投诉举报登记办理情况" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 回访统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/huiFang")
	@ResponseBody
	public List<Map> huiFang(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.huiFang(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --回访统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downHuiFang")
	@ResponseBody
	public void downHuiFang(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.huiFang(beginTime, endTime, 2, req);
		String str = qu.downHuiFang(num);
		String filename = "回访统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 举报件处理情况统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/juBao")
	@ResponseBody
	public List<Map> juBao(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.juBao(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --举报件处理情况统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downJuBao")
	@ResponseBody
	public void downJuBao(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.juBao(beginTime, endTime, 2, req);
		String str = qu.downJuBao(num);
		String filename = "举报件处理情况统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * @param req
	 * @param res
	 * 市场监管投诉件处理情况统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/touSu")
	@ResponseBody
	public List<Map> touSu(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.touSu(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --市场监管投诉件处理情况统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downTouSu")
	@ResponseBody
	public void downTouSu(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.touSu(beginTime, endTime, 2, req);
		String str = qu.downTouSu(num);
		String filename = "市场监管投诉件处理情况统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 咨询件处理情况统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/ziXun")
	@ResponseBody
	public List<Map> ziXun(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.ziXun(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --咨询件处理情况统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downZiXun")
	@ResponseBody
	public void downZiXun(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.ziXun(beginTime, endTime, 2, req);
		String str = qu.downZiXun(num);
		String filename = "咨询件处理情况统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 环节工作量统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/huanJie")
	@ResponseBody
	public List<Map> huanJie(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.huanJie(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --环节工作量统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downHuanJie")
	@ResponseBody
	public void downHuanJie(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.huanJie(beginTime, endTime, 2, req);
		String str = qu.downHuanJie(num);
		String filename = "环节工作量统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 信息件登记量统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/xinXiDengJi")
	@ResponseBody
	public List<Map> xinXiDengJi(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String regCode = req.getParameter("regcode");
		return query.xinXiDengJi(beginTime, endTime,hBeginTime,hEndTime,regCode,1, req);
	}
	
	/**
	 * 下载 --信息件登记量统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downXinXiDengJi")
	@ResponseBody
	public void downXinXiDengJi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		String regCode = req.getParameter("regcode");
		boolean rc=regCode!=null &&regCode.length()!=0;
		String depName=rc?query.getDepName(regCode):"全部";
		List<Map>  num = query.xinXiDengJi(beginTime, endTime,hBeginTime,hEndTime,regCode,2, req);
		String str = qu.downXinXiDengJi(num);
		String filename = "信息件登记量统计表("+depName+")" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 归档审核统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/guiDang")
	@ResponseBody
	public List<Map> guiDang(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		return query.guiDang(beginTime, endTime,hBeginTime,hEndTime,1, req);
	}
	
	/**
	 * 下载 --归档审核统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downGuiDang")
	@ResponseBody
	public void downGuiDang(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String hBeginTime = req.getParameter("hbegintime");
		String hEndTime = req.getParameter("hendtime");
		List<Map>  num = query.guiDang(beginTime, endTime,hBeginTime,hEndTime,2, req);
		String str = qu.downGuiDang(num);
		String filename = "归档审核统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 被督办部门类型统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/beiDuBan")
	@ResponseBody
	public List<Map> beiDuBan(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.beiDuBan(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --被督办部门类型统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downBeiDuBan")
	@ResponseBody
	public void downBeiDuBan(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.beiDuBan(beginTime, endTime, 2, req);
		String str = qu.downBeiDuBan(num);
		String filename = "被督办部门类型统计表" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 直接解答数量统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/zhiJieJieDa")
	@ResponseBody
	public List<Map> zhiJieJieDa(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.zhiJieJieDa(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --直接解答数量统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downZhiJieJieDa")
	@ResponseBody
	public void downZhiJieJieDa(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.zhiJieJieDa(beginTime, endTime, 2, req);
		String str = qu.downZhiJieJieDa(num);
		String filename = "直接解答数量统计" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 咨询建议业务范围分类统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/ziXunJianYiYeWuFanWei")
	@ResponseBody
	public List<Map> ziXunJianYiYeWuFanWei(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		return query.ziXunJianYiYeWuFanWei(beginTime, endTime, 1, req);
	}
	
	/**
	 * 下载 --咨询建议业务范围分类统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downZiXunJianYiYeWuFanWei")
	@ResponseBody
	public void downZiXunJianYiYeWuFanWei(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		List<Map>  num = query.ziXunJianYiYeWuFanWei(beginTime, endTime, 2, req);
		String str = qu.downZiXunJianYiYeWuFanWei(num);
		String filename = "咨询建议业务范围分类统计" + "(" + beginTime + "至" + endTime + ")_"
				+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 申诉举报基本问题分类统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/shenSuJuBaoJiBenWenTi")
	@ResponseBody
	public List<Map> shenSuJuBaoJiBenWenTi(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infoType = req.getParameter("infotype");
		String yeWuLeiBie = req.getParameter("yeWuLeiBie");
		return query.shenSuJuBaoJiBenWenTi(beginTime, endTime,infoType,yeWuLeiBie, 1, req);
	}
	
	/**
	 * 下载 --咨询建议业务范围分类统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downShenSuJuBaoJiBenWenTi")
	@ResponseBody
	public void downShenSuJuBaoJiBenWenTi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infoType = req.getParameter("infotype");
		String yeWuLeiBie = req.getParameter("yeWuLeiBie");
		List<Map>  num = query.shenSuJuBaoJiBenWenTi(beginTime, endTime,infoType,yeWuLeiBie, 2, req);
		String str = qu.downShenSuJuBaoJiBenWenTi(num);
		String filename = "咨询建议业务范围分类统计" + "(" + beginTime + "至" + endTime +")_"+getInfType(infoType)+"_"+ getYeWuLeiBie(yeWuLeiBie)+
				"_"+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 申诉举报涉及客体类型统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/shenSuJuBaoSheJiKeTi")
	@ResponseBody
	public List<List<Map>> shenSuJuBaoSheJiKeTi(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infoType = req.getParameter("infotype");
		String yeWuLeiBie = req.getParameter("yeWuLeiBie");
		return query.shenSuJuBaoSheJiKeTi(beginTime, endTime,infoType,yeWuLeiBie, 1, req);
	}
	
	/**
	 * 下载 --申诉举报涉及客体类型统计
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/downShenSuJuBaoSheJiKeTi")
	@ResponseBody
	public void downShenSuJuBaoSheJiKeTi(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String infoType = req.getParameter("infotype");
		String yeWuLeiBie = req.getParameter("yeWuLeiBie");
		List<List<Map>>  num = query.shenSuJuBaoSheJiKeTi(beginTime, endTime,infoType,yeWuLeiBie, 2, req);
		String str = qu.downShenSuJuBaoSheJiKeTi(num);
		String filename = "申诉举报涉及客体类型统计（"  + beginTime + "至" + endTime +"）_"+getInfType(infoType)+"_"+ getYeWuLeiBie(yeWuLeiBie)+
				"_"+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 登记信息涉及金额统计表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/dengJiXinXiSheJiJinE")
	@ResponseBody
	public List<List<Map>> dengJiXinXiSheJiJinE(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		Map map=new HashMap<String,String>();
		map.put("shejiketi", req.getParameter("shejiketi"));
		map.put("infotype", req.getParameter("infotype"));
		map.put("laiyuanfangshi", req.getParameter("laiyuanfangshi"));
		map.put("jieshoufangshi", req.getParameter("jieshoufangshi"));
		map.put("shijianjibie", req.getParameter("shijianjibie"));
		map.put("renyuanshenfen", req.getParameter("renyuanshenfen"));
		map.put("shejizhuti", req.getParameter("shejizhuti"));
		map.put("hangyeleibie", req.getParameter("hangyeleibie"));
		map.put("wangzhanleixing", req.getParameter("wangzhanleixing"));
		map.put("qiyeleixing", req.getParameter("qiyeleixing"));
		map.put("begintime",beginTime);
		map.put("endtime", endTime);
		map.put("flag", "1");
		return query.dengJiXinXiSheJiJinE(map, 1, req);
	}
	
	
	/**
	 * 下载 --登记信息涉及金额统计表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/downDengJiXinXiSheJiJinE")
	@ResponseBody
	public void downDengJiXinXiSheJiJinE(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		Map map=new HashMap<String,String>();
		map.put("shejiketi", req.getParameter("shejiketi"));
		map.put("infotype", req.getParameter("infotype"));
		map.put("laiyuanfangshi", req.getParameter("laiyuanfangshi"));
		map.put("jieshoufangshi", req.getParameter("jieshoufangshi"));
		map.put("shijianjibie", req.getParameter("shijianjibie"));
		map.put("renyuanshenfen", req.getParameter("renyuanshenfen"));
		map.put("shejizhuti", req.getParameter("shejizhuti"));
		map.put("hangyeleibie", req.getParameter("hangyeleibie"));
		map.put("wangzhanleixing", req.getParameter("wangzhanleixing"));
		map.put("qiyeleixing", req.getParameter("qiyeleixing"));
		map.put("begintime",beginTime);
		map.put("endtime", endTime);
		map.put("flag", "1");
		List<List<Map>>  num = query.dengJiXinXiSheJiJinE(map, 2, req);
		String str = qu.downDengJiXinXiSheJiJinE(num);
		String filename = "登记信息涉及金额统计表("  + beginTime + "至" + endTime +")"+
				"_"+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	
	/**
	 * @param req
	 * @param res
	 * 服务站--消费者权益服务站统计表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/fuWuZhanXiaoFeiZhe")
	@ResponseBody
	public List<Map> fuWuZhanXiaoFeiZhe(HttpServletRequest req, HttpServletResponse res) {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String stationtype = req.getParameter("stationtype");
		String regno = req.getParameter("regno");
		String status = req.getParameter("status");
		return query.fuWuZhanXiaoFeiZhe(beginTime, endTime,stationtype,regno,status, 1, req);
	}
	
	/**
	 * 下载 --消费者权益服务站统计表
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/downFuWuZhanXiaoFeiZhe")
	@ResponseBody
	public void downFuWuZhanXiaoFeiZhe(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		String endTime = req.getParameter("endtime");
		String beginTime = req.getParameter("begintime");
		String stationtype = req.getParameter("stationtype");
		String regno = req.getParameter("regno");
		String status = req.getParameter("status");
		List<Map>  num = query.fuWuZhanXiaoFeiZhe(beginTime, endTime,stationtype,regno,status, 2 ,req);
		String str = qu.downFuWuZhanXiaoFeiZhe(num);
		String filename = "消费者权益服务站统计表("  + beginTime + "至" + (endTime==null||endTime.length()==0?this.getTimeString().substring(0,8):endTime) +")"+
				"_"+ this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
	}
	
	/**
	 * @param req
	 * @param res
	 * 服务站--服务站信息件统计
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/fuWuZhanXinXiJian")
	@ResponseBody
	public List<Map> fuWuZhanXinXiJian(HttpServletRequest req, HttpServletResponse res) {
		Map map=new HashMap();
		map.put("paifakaishi", req.getParameter("paifakaishi"));
		map.put("paifajieshu", req.getParameter("paifajieshu"));
		map.put("jiedankaishi", req.getParameter("jiedankaishi"));
		map.put("jiedanjieshu", req.getParameter("jiedanjieshu"));
		map.put("huifukaishi", req.getParameter("huifukaishi"));
		map.put("huifujieshu", req.getParameter("huifujieshu"));
		map.put("banjiekaishi", req.getParameter("banjiekaishi"));
		map.put("banjiejieshu", req.getParameter("banjiejieshu"));
		map.put("shensuren", req.getParameter("shensuren"));
		map.put("shensuduixiang", req.getParameter("shensuduixiang"));
		map.put("jingbanren", req.getParameter("jingbanren"));
		map.put("tiaojiechenggong", req.getParameter("tiaojiechenggong"));
		map.put("flag","1");
		return query.fuWuZhanXinXiJian(map, 1, req);
	}
	
	/**
	 * 下载 --服务站信息件统计表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/downFuWuZhanXinXiJian")
	@ResponseBody
	public void downFuWuZhanXinXiJian(HttpServletRequest req,
			HttpServletResponse response) throws SQLException, OptimusException {
		Map map=new HashMap();
		map.put("paifakaishi", req.getParameter("paifakaishi"));
		map.put("paifajieshu", req.getParameter("paifajieshu"));
		map.put("jiedankaishi", req.getParameter("jiedankaishi"));
		map.put("jiedanjieshu", req.getParameter("jiedanjieshu"));
		map.put("huifukaishi", req.getParameter("huifukaishi"));
		map.put("huifujieshu", req.getParameter("huifujieshu"));
		map.put("banjiekaishi", req.getParameter("banjiekaishi"));
		map.put("banjiejieshu", req.getParameter("banjiejieshu"));
		map.put("shensuren", req.getParameter("shensuren"));
		map.put("shensuduixiang", req.getParameter("shensuduixiang"));
		map.put("jingbanren", req.getParameter("jingbanren"));
		map.put("tiaojiechenggong", req.getParameter("tiaojiechenggong"));
		map.put("flag","1");
		List<Map>  num = query.fuWuZhanXinXiJian(map, 2 ,req);
		String str = qu.downFuWuZhanXinXiJian(num);
		String filename = "消费者权益服务站统计表"  +
				 this.getTimeString()
				+ ".xls";
		this.down(response,filename,str);
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
	
	/**
	 * 根据前台选择的业务类别代码来选择业务类别中文名
	 */
	public String getYeWuLeiBie(String yeWuCode) {
		String yeWu = "";
		if (yeWuCode != null && "CH20".equals(yeWuCode)) {
			yeWu = "工商";
		} else if (yeWuCode != null && "ZH18".equals(yeWuCode)) {
			yeWu = "消委会";
		} else if (yeWuCode != null && "ZH19".equals(yeWuCode)) {
			yeWu = "知识产权";
		} else if (yeWuCode != null && "ZH20".equals(yeWuCode)) {
			yeWu = "价格检查";
		} else if (yeWuCode != null && "ZH21".equals(yeWuCode)) {
			yeWu = "质量监督";
		} else {
			yeWu = "全部";
		}
		return yeWu;
	}
	
	/**
	 * 根据前台选择的信息件类型代码来选择信息件类型中文名，发布环境为JDK1.6
	 */
	public String getInfType(String infoCode) {
		String infotype = "";
		if (infoCode != null && "1".equals(infoCode)) {
			infotype = "投诉";
		} else if (infoCode != null && "2".equals(infoCode)) {
			infotype = "举报";
		} else if (infoCode != null && "3".equals(infoCode)) {
			infotype = "咨询";
		} else {
			infotype = "咨询举报投诉";
		}
		return infotype;
	}
}

