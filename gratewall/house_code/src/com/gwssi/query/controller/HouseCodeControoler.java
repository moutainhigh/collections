package com.gwssi.query.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.query.service.HouseCodeService;

@Controller
@RequestMapping("/houseCode")
public class HouseCodeControoler {
	@Resource
	private HouseCodeService houseCodeService;

	/**
	 * 区域查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getRegCode")
	public void getRegCode(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addTree("region",
				houseCodeService.getRegCode(null, req.getHttpRequest()));
	}

	/**
	 * 街道查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getStrCode")
	public void getStrCode(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		HttpServletRequest request = req.getHttpRequest();
		String param = request.getParameter("regioncode");
		param=URLDecoder.decode(param, "utf-8");
		param=URLDecoder.decode(param, "utf-8");
		resp.addTree("region", houseCodeService.getStrCode(param, request));
	}

	/**
	 * 查询楼栋信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBuildingInfo")
	public void getBuildingInfo(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		HttpServletRequest request = req.getHttpRequest();
		String qu = req.getParameter("qu");
		qu=URLDecoder.decode(qu, "utf-8");
		String jieDao = req.getParameter("jieDao");
		jieDao=URLDecoder.decode(jieDao, "utf-8");
		String sheQu=req.getParameter("sheQu");
		sheQu=URLDecoder.decode(sheQu, "utf-8");
		String lu=req.getParameter("lu");
		lu=URLDecoder.decode(lu, "utf-8");
		String jianZhuWu=req.getParameter("jianZhuWu");
		jianZhuWu=URLDecoder.decode(jianZhuWu, "utf-8");
		String louDong=req.getParameter("louDong");
		louDong=URLDecoder.decode(louDong, "utf-8");
		String louCeng=req.getParameter("louCeng");
		louCeng=URLDecoder.decode(louCeng, "utf-8");
		String fangHao=req.getParameter("fangHao");
		fangHao=URLDecoder.decode(fangHao, "utf-8");
		String FangWuBianMa=req.getParameter("FangWuBianMa");
		FangWuBianMa=URLDecoder.decode(FangWuBianMa, "utf-8");
		//FangWuDiZhi
		String FangWuDiZhi=req.getParameter("FangWuDiZhi");
		FangWuDiZhi=URLDecoder.decode(FangWuDiZhi, "utf-8");
		String page = req.getParameter("page");
		String pageSize="10";
	//	qu : dataFrom.qu,
		//jieDao : dataFrom.jieDao,
	/*	sheQu : dataFrom.sheQu,
		lu : dataFrom.lu,
		jianZhuWu : dataFrom.jianZhuWu,
		louDong:dataFrom.louDong,
		louCeng:dataFrom.louCeng,
		fangHao:dataFrom.fangHao,
		FangWuBianMa:dataFrom.FangWuBianMa*/
		
		
		Map form = new HashMap();
		if (qu!=null&&qu.length()!=0) {
			form.put("qu", qu);
		}
		if (jieDao!=null&&jieDao.length()!=0) {
			form.put("jieDao", jieDao);
		}
		/*if (louDongMingCheng!=null&&louDongMingCheng.length()!=0) {
			form.put("buildingName", "%"+louDongMingCheng+"%");
		}
		if (louDongDiZhi!=null&&louDongDiZhi.length()!=0) {
			form.put("buildingAdd", "%"+louDongDiZhi+"%");
		}
		if (louDongBianMa!=null&&louDongBianMa.length()!=0) {
			form.put("buildingCode", "%"+louDongBianMa+"%");
		}*/
		if (sheQu!=null&&sheQu.length()!=0) {
			form.put("sheQu", getLike(sheQu));
		}
		if (lu!=null&&lu.length()!=0) {
			form.put("lu",getLike(lu));
		}
		if (jianZhuWu!=null&&jianZhuWu.length()!=0) {
			form.put("jianZhuWu", getLike(jianZhuWu));
		}
		if (louDong!=null&&louDong.length()!=0) {
			form.put("louDong", getLike(louDong));
		}
		if (louCeng!=null&&louCeng.length()!=0) {
			form.put("louCeng", louCeng);
		}
		if (fangHao!=null&&fangHao.length()!=0) {
			form.put("fangHao", getLike(fangHao));
		}
		if (FangWuBianMa!=null&&FangWuBianMa.length()!=0) {
			form.put("FangWuBianMa", getLike(FangWuBianMa));
		}
		if (FangWuDiZhi!=null&&FangWuDiZhi.length()!=0) {
			form.put("FangWuDiZhi", getLike(FangWuDiZhi));
		}
		List<Map> list = houseCodeService.getBuildingInfo(form, page,pageSize,request);
		String count=null;
		if (list==null||list.size()==0) {
			count="0";
		}else{
			count = houseCodeService.getHouseListCount(form);
		}
		resp.addGrid("queryHouseCodeGrid", list);
		resp.addAttr("count", count);
		}
	

	/**
	 * 根据楼栋编号查询房屋信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getHouseInfo")
	public void getHouseInfo(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		HttpServletRequest request = req.getHttpRequest();
		String param = request.getParameter("buildingCode");
		param=URLDecoder.decode(param, "utf-8");
		String floor=req.getParameter("floor");
		floor=URLDecoder.decode(floor, "utf-8");
		String page = request.getParameter("page");
		page=URLDecoder.decode(page, "utf-8");
		String pageSize="10";
		List<Map> list = houseCodeService.getHouseInfo(param,floor,page,pageSize, request);
		String count=null;
		if (list==null||list.size()==0) {
			count="0";
		}else{
			count = houseCodeService.getHouseCount(param,floor,request);
		}
		resp.addGrid("queryHouseInfoGrid", list);
		resp.addAttr("count", count);
	}

	/**
	 * 二级菜单中根据form表单查询房屋信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getHouseInfoByForm")
	public void getHouseInfoByForm(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		HttpServletRequest request = req.getHttpRequest();
		String houseAdd = req.getParameter("houseAdd");
		houseAdd=URLDecoder.decode(houseAdd, "utf-8");
		String houseNum = req.getParameter("houseNum");
		houseNum=URLDecoder.decode(houseNum, "utf-8");
		String buildingNum = req.getParameter("buildingNum");
		buildingNum=URLDecoder.decode(buildingNum, "utf-8");
		String floor=req.getParameter("floor");
		floor=URLDecoder.decode(floor, "utf-8");
		Map form = new HashMap();
		if (houseAdd!=null&&houseAdd.length()!=0) {
			form.put("houseAdd", getLike(houseAdd));
		}
		if (houseNum!=null&&houseNum.length()!=0) {
			form.put("houseNum", getLike(houseNum));
		}
		if (floor!=null&&floor.length()!=0) {
			form.put("floor", floor);
		}else{
			form.put("floor", "abcd");
		}
		List<Map> list = houseCodeService.getHouseInfoByForm(form,buildingNum,
				request);
		resp.addGrid("queryHouseInfoGrid", list);
	}
	
	
	public void queryFedback(OptimusRequest req, OptimusResponse resp) throws UnsupportedEncodingException{
		HttpServletRequest request = req.getHttpRequest();
		String entName = req.getParameter("entName");
		entName=URLDecoder.decode(entName, "utf-8");
		String houseNumber = req.getParameter("houseNumber");
		houseNumber=URLDecoder.decode(houseNumber, "utf-8");
		Map form=new HashMap();
		houseCodeService.queryFeedback(form,request);
			
		}
	
	
	public String getLike(String str){
		return "%"+str+"%";
	}
}
