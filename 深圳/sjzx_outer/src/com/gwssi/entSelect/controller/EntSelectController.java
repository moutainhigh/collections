package com.gwssi.entSelect.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.entSelect.service.EntSelectService;
import com.gwssi.entSelect.service.SafeVisitService;
import com.gwssi.entSelect.util.IPUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/entEnt")
public class EntSelectController {

	private String printHTML;

	private boolean isCorrectCode = false; // 是否是正确的验证码

	@Autowired
	private EntSelectService entSelectService;

	@Autowired
	private SafeVisitService safeService;

	/**
	 * 企业基本信息
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/detail")
	public void detailedInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException {

		// 记录IP
		String ip = IPUtil.getIpPropAddr(req.getHttpRequest());
		int count = safeService.count(ip);
		String unifsocicrediden = req.getParameter("unifsocicrediden").trim();
		String entname = req.getParameter("entname").trim();
		String flag = req.getParameter("flag").trim();
		if (count <= 30) {
			if (flag.equals("1")) {
				List<Map> results = entSelectService.queryJbxx(unifsocicrediden, entname);
				if (results.size() > 20) {
					resp.addAttr("data", "error");
				} else {
					safeService.saveIPLog(ip);
					resp.addAttr("data", results);
				}
			} else {
				resp.addAttr("data", "0");
			}
		} else {
			resp.addAttr("data", "您查询次数过多，请稍后再查！");
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/entList")
	public void detailByLike(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String ip = IPUtil.getIpPropAddr(req.getHttpRequest());
		int count = safeService.count(ip);

		String unifsocicrediden = req.getParameter("unifsocicrediden");
		String entname = req.getParameter("entname");
		String flag = req.getParameter("flag");
		if (count <= 19) {
			// String isFull = req.getParameter("isFull");
			if (flag.equals("1")) {
				List<Map> results = entSelectService.queryJbxxList(unifsocicrediden, entname);
				if (results.size() > 20) {
					resp.addAttr("data", "error");
				} else {
					safeService.saveIPLog(ip);
					resp.addAttr("data", results);
				}
			} else {
				resp.addAttr("data", "0");
			}
		} else {
			resp.addAttr("data", "您查询次数过多，请稍后再查！");
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/tag")
	public void getEntDetailBytagSwitchId(OptimusRequest req, OptimusResponse resp) throws OptimusException {

		String flag = req.getParameter("flag");
		String tagId = req.getParameter("tagId");
		String id = req.getParameter("id");
		String opetype = req.getParameter("opetype");

		if (flag.equals("1")) {
			if (tagId.equals("0")) {

			} else if (tagId.equals("1")) { // 许可信息
				List<Map> res = entSelectService.queryXueKe(id);
				resp.addAttr("data", res);
			} else if (tagId.equals("2")) { // 股东信息
				List<Map> res = entSelectService.queryGuDong(id, opetype);
				resp.addAttr("data", res);
			} else if (tagId.equals("3")) { // 成员信息
				List<Map> res = entSelectService.queryChenYuan(id);
				resp.addAttr("data", res);
			} else if (tagId.equals("4")) { // 变更信息
				List<Map> res = entSelectService.queryBianGen(id);
				resp.addAttr("data", res);
			} else if (tagId.equals("5")) { // 股权质押信息
				List<Map> res = entSelectService.queryGuQuanZhiYa(id);
				resp.addAttr("data", res);
			} else if (tagId.equals("6")) { // 动产抵押信息
				String regno = req.getParameter("regno");
				String unifsocicrediden = req.getParameter("unifsocicrediden");
				List<Map> res = entSelectService.queryDongChandiYa(id, regno, unifsocicrediden);
				resp.addAttr("data", res);
			} else if (tagId.equals("7")) { // 法院冻结信息
				List<Map> res = entSelectService.queryFaYuanDongJie(id);
				resp.addAttr("data", res);
			} else if (tagId.equals("8")) { // 经营异常信息
				List<Map> res = entSelectService.queryJinYin(id);
				resp.addAttr("data", res);
			} else if (tagId.equals("9")) { // 严重违法失信信息
				List<Map> res = entSelectService.queryYanZhongWeiFa(id);
				resp.addAttr("data", res);
			}
		} else {
			resp.addAttr("data", "0");
		}

	}

	// 查询集团的母公司
	@SuppressWarnings("rawtypes")
	@RequestMapping("/entMother")
	public void getEntMother(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id").trim();

		List<Map> list = entSelectService.getEntMother(id);
		resp.addAttr("data", list);
	}

	/*
	 * 企业变更详细信息
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/biangeng")
	public void getEntBGDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id").trim();
		String alttime = req.getParameter("alttime").trim();
		String pregino = req.getParameter("pregino").trim();
		
		List<Map> res = typeChange(entSelectService.queryBiangengDetail(id, alttime,pregino));
		resp.addAttr("data", res);
	}

	/**
	 * 企业变更撤销
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/chexiao")
	public void getEntBGRevocation(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id").trim();
		String alttime = req.getParameter("alttime").trim();
		List<Map> list = entSelectService.queryBianGenRecoxtion(id, alttime);
		if (list != null && list.size() > 0) {
			resp.addAttr("data", list);
		} else {
			Map map = new HashMap();
			map.put("altitemcode", null);
			list.add(map);
			resp.addAttr("data", list);
		}
	}

	/*
	 * 注册资本转码
	 */
	public List<Map> typeChange(List<Map> list) throws OptimusException {
		List<Map> res = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String altitemcode = (String) map.get("altitemcode");
			String altbe = (String) map.get("altbe");
			String altaf = (String) map.get("altaf");
			if (altitemcode != null && "20".equals(altitemcode)) {// 注册资本变更
				if (altbe != null) {
					String[] altbe_temp = altbe.split(",");
					if (altbe_temp.length > 1) {
						String a = (String) entSelectService.queryTypeChange(altbe_temp[1]);
						String str = altbe_temp[0] + " " + a;
						map.put("altbe", str);
					}
				}

				if (altaf != null) {
					String[] altaf_temp = altaf.split(",");
					if (altaf_temp.length > 1) {
						String a = (String) entSelectService.queryTypeChange(altaf_temp[1]);
						String str = altaf_temp[0] + " " + a;
						map.put("altaf", str);
					}
				}
			}
			if (altitemcode != null && "05".equals(altitemcode)) {// 经营期限变更
				if (altbe != null) {
					String[] altbe_temp = altbe.split(",");
					if (altbe_temp.length > 1) {
						String str = "自" + altbe_temp[0] + "至" + altbe_temp[1];
						map.put("altbe", str);
					} else {
						map.put("altaf", "永续经营");
					}
				}

				if (altaf != null) {
					String[] altaf_temp = altaf.split(",");
					if (altaf_temp.length > 1) {
						String str = "自" + altaf_temp[0] + "至" + altaf_temp[1];
						map.put("altaf", str);
					} else {
						map.put("altaf", "永续经营");
					}
				}
			}
			res.add(map);

		}
		return res;
	}

	/*
	 * 企业变更详细信息(第二种需要查询表)
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/bgdetail")
	public void getEntBGDetail2(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String altitemcode = req.getParameter("altitemcode").trim();
		String regino = req.getParameter("regino").trim();
		String id = req.getParameter("id").trim();
		// String mainId = req.getParameter("mainId").trim();
		List<Map> res = entSelectService.queryBiangengDetail2(altitemcode, regino, id);
		resp.addAttr("data", res);
	}

	// 企业是否有异常名录,是否有严重违法失信，用于显示当前企业是否正常
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/entStatus")
	public void getEntIsNormal(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id").trim();

		// 查询严重违法失信企业
		int yzwf = entSelectService.getEntIsHonesty(id);
		// 查询企业状态是否为撤销登记状态，status=8为撤销登记
		int entStatus = entSelectService.getEntStatus(id);
		// 查询异常名录
		String ycml = entSelectService.getEntIsNormal(id);

		List<Map> list = new ArrayList();
		Map<String, String> map = new HashMap<String, String>();

		map.put("yzwf", String.valueOf(yzwf));
		map.put("ycml", ycml);
		map.put("entStatus", String.valueOf(entStatus));
		list.add(map);
		resp.addAttr("data", list);

	}

	// 年检
	@RequestMapping("/nj")
	public void entnj(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String entId = req.getParameter("entId");
		List list = entSelectService.getNJ(entId);
		resp.addAttr("msg", list);
	}

	// 年报
	@RequestMapping("/nb")
	public void entnb(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String pripid = req.getParameter("pripid");
		String opetype = req.getParameter("opetype");

		List list = null;
		if (opetype.equals("GT")) {
			list = entSelectService.getGtnb(pripid);
		} else {
			list = entSelectService.getNonGtnb(pripid);
		}
		resp.addAttr("data", list);
	}

	// 法定代表人姓名
	@RequestMapping("/fddbr")
	public void queryFDDBR(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		List list = entSelectService.queryFDDBR(id);
		resp.addAttr("data", list);
	}

	// 经营者姓名
	@RequestMapping("/jyz")
	public void queryJYZ(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		List list = entSelectService.queryJYZ(id);
		resp.addAttr("data", list);
	}

	// 执行事务合伙人姓名
	@RequestMapping("/zxhhr")
	public void queryZXHHR(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		List list = entSelectService.queryZXHHR(id);
		resp.addAttr("data", list);
	}

	// 委派代表
	@RequestMapping("/wpdb")
	public void wpdb(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		String entId = req.getParameter("entId");
		String invatt = req.getParameter("invatt");

		List list = entSelectService.wpdb(id, entId);
		resp.addAttr("data", list);

		/*
		 * if(!(invatt.equals("自然人"))){ List list = entSelectService.wpdb(id);
		 * resp.addAttr("data", list); }else{ resp.addAttr("data","0"); }
		 */

	}

	// 企业列表查询
	@SuppressWarnings("rawtypes")
	@RequestMapping("/listDetail")
	public void entListDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String unifsocicrediden = req.getParameter("unifsocicrediden");
		String entname = req.getParameter("entname");
		String regno = req.getParameter("regno");
		List<Map> results = entSelectService.queryJbxx(unifsocicrediden, entname);
		if (results != null && results.size() > 0) {
			resp.addAttr("data", results);
		} else {
			resp.addAttr("data", results);
		}
	}

	// 模糊查询出来20条记录里面调用的企业基本信息
	@SuppressWarnings("rawtypes")
	@RequestMapping("/tagDe")
	public void getListSwitchId(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String tagId = req.getParameter("tagId");
		String id = req.getParameter("id");
		String opetype = req.getParameter("opetype");
		if (tagId.equals("0")) {
		} else if (tagId.equals("1")) { // 许可信息
			List<Map> res = entSelectService.queryXueKe(id);
			resp.addAttr("data", res);
		} else if (tagId.equals("2")) { // 股东信息
			List<Map> res = entSelectService.queryGuDong(id, opetype);
			resp.addAttr("data", res);
		} else if (tagId.equals("3")) { // 成员信息
			List<Map> res = entSelectService.queryChenYuan(id);
			resp.addAttr("data", res);
		} else if (tagId.equals("4")) { // 变更信息
			List<Map> res = entSelectService.queryBianGen(id);
			resp.addAttr("data", res);
		} else if (tagId.equals("5")) { // 股权质押 信息
			List<Map> res = entSelectService.queryGuQuanZhiYa(id);
			resp.addAttr("data", res);
		} else if (tagId.equals("6")) { // 动产抵押信息
			String regno = req.getParameter("regno");
			String unifsocicrediden = req.getParameter("unifsocicrediden");
			List<Map> res = entSelectService.queryDongChandiYa(id, regno, unifsocicrediden);
			resp.addAttr("data", res);
		} else if (tagId.equals("7")) { // 法院冻结信息
			List<Map> res = entSelectService.queryFaYuanDongJie(id);
			resp.addAttr("data", res);
		}  else if (tagId.equals("8")) { // 经营异常信息
			List<Map> res = entSelectService.queryJinYin(id);
			resp.addAttr("data", res);
		} else if (tagId.equals("9")) { // 严重违法失信信息
			List<Map> res = entSelectService.queryYanZhongWeiFa(id);
			resp.addAttr("data", res);
		}
	}

	// 用于将打印内客
	@RequestMapping("/print")
	public void entPrint(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		// System.out.println(content);
		req.getHttpRequest().getSession().setAttribute("printTitle", title);
		req.getHttpRequest().getSession().setAttribute("printContent", content);
		resp.addAttr("data", 1);
	}

	// 获取打印内容
	@RequestMapping("/getPrint")
	public void getEntPrint(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String type = req.getParameter("type");
		String title = (String) req.getHttpRequest().getSession().getAttribute("printTitle");
		String content = (String) req.getHttpRequest().getSession().getAttribute("printContent");
		resp.addAttr("title", title);
		resp.addAttr("content", content);
	}

	/*
	 * 动产抵押信息（抵押人名称，抵押权人名称，抵押物名称与数量）
	 */
	@RequestMapping("/dcdy")
	public void getDCDY(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		String flag = req.getParameter("flag");
		List<Map> res = entSelectService.queryDCDYDetail(id, flag);
		resp.addAttr("data", res);
	}

	/*
	 * 企业分支机构
	 */
	@RequestMapping("/branch")
	public void queryBranch(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		List<Map> res = entSelectService.queryBranch(id);
		resp.addAttr("data", res);
	}

}
