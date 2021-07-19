package com.gwssi.query.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.cache.Cache;
import com.gwssi.cache.CacheManager;
import com.gwssi.ip.service.SafeVisitService;
import com.gwssi.ip.util.IPUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.query.service.HouseCodeService;

///oracle ===http://blog.csdn.net/arenzhj/article/details/16902141
@Controller
@RequestMapping("/houseCode")
public class HouseCodeControoler {
	private static Logger log = Logger.getLogger(HouseCodeControoler.class);

	@Resource
	private HouseCodeService houseCodeService;

	@Autowired
	private SafeVisitService safeService;

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}

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
	@SuppressWarnings({ "unchecked" })
	@RequestMapping("/getStrCode")
	public void getStrCode(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		HttpServletRequest request = req.getHttpRequest();
		String param = request.getParameter("regioncode");
		param = URLDecoder.decode(param, "utf-8");
		param = URLDecoder.decode(param, "utf-8");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (null == CacheManager.getCacheInfo(param)) {
			list = houseCodeService.getStrCode(param, request);
			CacheManager.putCache(param,
					new Cache(param, list, System.currentTimeMillis()
							+ (60 * 60 * 24 * 1000), false));
			resp.addTree("region", list);
		} else if (CacheManager.cacheExpired(CacheManager.getCacheInfo(param))) {
			list = houseCodeService.getStrCode(param, request);
			CacheManager.clearOnly(param);
			CacheManager.putCache(param,
					new Cache(param, list, System.currentTimeMillis()
							+ (60 * 60 * 24 * 1000), false));
			resp.addTree("region", list);
		} else {
			resp.addTree("region", (List<Map<String, Object>>) CacheManager
					.getCacheInfo(param).getValue());
		}

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
		String certCode = (String) request.getSession().getAttribute("certCode"); // 把Sesssion中的值取出来
		
		String tokens = req.getParameter("tokens");
		if(tokens!=null){
			tokens = URLDecoder.decode(tokens, "utf-8");
		}
		
		
		String imageCode = req.getParameter("imageCode");
		if(imageCode!=null){
			imageCode = URLDecoder.decode(imageCode, "utf-8");
		}
		if (imageCode.equals(certCode)||tokens!=null) {
			String ip = IPUtil.getIpPropAddr(request);
			int ipCounts = safeService.count(ip);
			log.info("当前IP =====》 " + ip + "查询次数=========》" + ipCounts);
			if (ipCounts <= 80) {
				safeService.saveIPLog(ip);

				String qu = req.getParameter("qu");
				qu = URLDecoder.decode(qu, "utf-8");
				String jieDao = req.getParameter("jieDao");
				jieDao = URLDecoder.decode(jieDao, "utf-8");
				String sheQu = req.getParameter("sheQu");
				sheQu = URLDecoder.decode(sheQu, "utf-8");
				String lu = req.getParameter("lu");
				lu = URLDecoder.decode(lu, "utf-8");
				String jianZhuWu = req.getParameter("jianZhuWu");
				jianZhuWu = URLDecoder.decode(jianZhuWu, "utf-8");
				String louDong = req.getParameter("louDong");
				louDong = URLDecoder.decode(louDong, "utf-8");
				String louCeng = req.getParameter("louCeng");
				louCeng = URLDecoder.decode(louCeng, "utf-8");
				String fangHao = req.getParameter("fangHao");
				fangHao = URLDecoder.decode(fangHao, "utf-8");
				String FangWuBianMa = req.getParameter("FangWuBianMa");
				FangWuBianMa = URLDecoder.decode(FangWuBianMa, "utf-8");
				String FangWuDiZhi = req.getParameter("FangWuDiZhi");
				FangWuDiZhi = URLDecoder.decode(FangWuDiZhi, "utf-8");
				String page = req.getParameter("page");
				String pageSize = "10";
				Map form = new HashMap();
				if (qu != null && qu.length() != 0) {
					form.put("qu", qu);
				}
				if (jieDao != null && jieDao.length() != 0) {
					form.put("jieDao", jieDao);
				}
				if (sheQu != null && sheQu.length() != 0) {
					form.put("sheQu", getLike(sheQu));
				}
				if (lu != null && lu.length() != 0) {
					form.put("lu", getLike(lu));
				}
				if (jianZhuWu != null && jianZhuWu.length() != 0) {
					form.put("jianZhuWu", getLike(jianZhuWu));
				}
				if (louDong != null && louDong.length() != 0) {
					form.put("louDong", getLike(louDong));
				}
				if (louCeng != null && louCeng.length() != 0) {
					form.put("louCeng", louCeng);
				}
				if (fangHao != null && fangHao.length() != 0) {
					form.put("fangHao", getLike(fangHao));
				}
				if (FangWuBianMa != null && FangWuBianMa.length() != 0) {
					form.put("FangWuBianMa", getLike(FangWuBianMa));
				}
				if (FangWuDiZhi != null && FangWuDiZhi.length() != 0) {
					form.put("FangWuDiZhi", getLike(FangWuDiZhi));
				}
				List<Map> list = houseCodeService.getBuildingInfo(form, page,
						pageSize, request);
				String count = null;
				if (list == null || list.size() == 0) {
					count = "0";
				} else {
					count = houseCodeService.getHouseListCount(form);
				}
				resp.addGrid("queryHouseCodeGrid", list);
				resp.addAttr("count", count);
				resp.addAttr("tokens", getUUID());
			} else {
				resp.addAttr("toMany", "limit");
			}
		} else {
			resp.addAttr("toMany", "vcodes");
		}

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
		param = URLDecoder.decode(param, "utf-8");
		String floor = req.getParameter("floor");
		floor = URLDecoder.decode(floor, "utf-8");
		String page = request.getParameter("page");
		page = URLDecoder.decode(page, "utf-8");
		String pageSize = "10";
		List<Map> list = houseCodeService.getHouseInfo(param, floor, page,
				pageSize, request);
		String count = null;
		if (list == null || list.size() == 0) {
			count = "0";
		} else {
			count = houseCodeService.getHouseCount(param, floor, request);
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
		houseAdd = URLDecoder.decode(houseAdd, "utf-8");
		String houseNum = req.getParameter("houseNum");
		houseNum = URLDecoder.decode(houseNum, "utf-8");
		String buildingNum = req.getParameter("buildingNum");
		buildingNum = URLDecoder.decode(buildingNum, "utf-8");
		String floor = req.getParameter("floor");
		floor = URLDecoder.decode(floor, "utf-8");
		Map form = new HashMap();
		if (houseAdd != null && houseAdd.length() != 0) {
			form.put("houseAdd", getLike(houseAdd.trim()));
		}
		if (houseNum != null && houseNum.length() != 0) {
			form.put("houseNum", getLike(houseNum.trim()));
		}
		if (floor != null && floor.length() != 0) {
			form.put("floor", floor.trim());
		} else {
			form.put("floor", "abcd");
		}
		List<Map> list = houseCodeService.getHouseInfoByForm(form, buildingNum,
				request);
		resp.addGrid("queryHouseInfoGrid", list);
	}

	public String getLike(String str) {
		return "%" + str.trim() + "%";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getJieDaoAddress")
	public void getJieDaoAddress(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		
		
		HttpServletRequest request = req.getHttpRequest();
		String param = req.getParameter("jieDao");
		if(param!=null){
			param = URLDecoder.decode(param, "utf-8");
			//List list = houseCodeService.getJieDaoAddress(param);
			
			List list = new ArrayList<Map<String, Object>>();
			if (null == CacheManager.getCacheInfo(param)) {
				list = houseCodeService.getJieDaoAddress(param,request);
				CacheManager.putCache(param,	new Cache(param, list, System.currentTimeMillis()+ (60 * 60 * 24 * 1000), false));
				resp.addAttr("jieDao", list);
			} else if (CacheManager.cacheExpired(CacheManager.getCacheInfo(param))) {
				list = houseCodeService.getJieDaoAddress(param, request);
				CacheManager.clearOnly(param);
				CacheManager.putCache(param,new Cache(param, list, System.currentTimeMillis()+ (60 * 60 * 24 * 1000), false));
				resp.addAttr("jieDao", list);
			} else {
				resp.addAttr("jieDao", (List<Map<String, Object>>) CacheManager.getCacheInfo(param).getValue());
			}
		}else{
			resp.addAttr("msg","请选择街道");
		}
	}
}
