package com.dj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dj.dao.Data01Dao;
import com.dj.dao.Data02Dao;
import com.dj.dao.Data03Dao;
import com.dj.dao.Data04Dao;
import com.dj.dao.Data05Dao;
import com.dj.dao.Data06Dao;
import com.dj.dao.Data07Dao;
import com.dj.dao.Data08Dao;
import com.dj.dao.Data09Dao;
import com.zx.microserver.register.annotation.ZxApiOperation;
import com.zx.microserver.register.annotation.ZxApiParam;
import com.zx.microserver.register.annotation.ZxApiRequestParams;
import com.zx.microserver.register.annotation.ZxApiResultParams;
import com.zx.microserver.register.annotation.ZxApiStatusCode;
import com.zx.microserver.register.annotation.ZxApiStatusCodes;
import com.zx.microserver.register.plugin.push.register.util.DataTypeEnum;

@RestController
public class DJController {

	@Autowired
	private Data01Dao data01Dao;

	@Autowired
	private Data02Dao data02Dao;

	@Autowired
	private Data03Dao data03Dao;

	@Autowired
	private Data04Dao data04Dao;

	@Autowired
	private Data05Dao data05Dao;

	@Autowired
	private Data06Dao data06Dao;

	@Autowired
	private Data07Dao data07Dao;

	@Autowired
	private Data08Dao data08Dao;
	
	@Autowired
	private Data09Dao data09Dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data01",name="根据法定代表人姓名和身份证号码返回企业主体信息", method = RequestMethod.GET)
    // 请求参数说明
    @ZxApiRequestParams({
            @ZxApiParam(name = "name",  dataType = DataTypeEnum.String, paramType = "request", remark = "姓名"),
            @ZxApiParam(name = "cerno", dataType = DataTypeEnum.String, paramType = "request", remark = "证件号"),
    })
    // 返回值说明作用于注解上
    @ZxApiResultParams({
            @ZxApiParam(name = "entname", required = true, dataType = DataTypeEnum.String, remark = "企业名称"),
            @ZxApiParam(name = "regcap", required = true, defaultValue = "0", dataType = DataTypeEnum.String, remark = "注册资本"),
            @ZxApiParam(name = "dom", required = true, dataType =DataTypeEnum.String, remark = "注册地址"),
            @ZxApiParam(name = "unifsocicrediden", required = true,  dataType =DataTypeEnum.String, remark = "统一信代码"),
            @ZxApiParam(name = "regorg", required = true, dataType =DataTypeEnum.String, remark = "登记机关"),
            @ZxApiParam(name = "apprdate", required = true , dataType =DataTypeEnum.String, remark = "核准时间"),
            @ZxApiParam(name = "nsrh", required = true, dataType =DataTypeEnum.String, remark = "纳税人识别号"),
            @ZxApiParam(name = "regno", required = true, dataType =DataTypeEnum.String, remark = "注册地址"),
            @ZxApiParam(name = "gph", required = true, dataType =DataTypeEnum.String, remark = "股票号"),
            @ZxApiParam(name = "estdate", required = true,dataType =DataTypeEnum.String, remark = "成立日期"),
            @ZxApiParam(name = "opfrom", required = true,dataType =DataTypeEnum.String, remark = "经营开始时间"),
            @ZxApiParam(name = "opto", required = true,dataType =DataTypeEnum.String, remark = "经营结束时间"),
            @ZxApiParam(name = "qyjc", required = true,dataType =DataTypeEnum.String, remark = "企业简称"),
            @ZxApiParam(name = "qyywm", required = true,dataType =DataTypeEnum.String, remark = "英文名"),
            @ZxApiParam(name = "zym", required = true,dataType =DataTypeEnum.String, remark = "曾用名"),
            @ZxApiParam(name = "lerep", required = true,dataType =DataTypeEnum.String, remark = "法定代表人"),
            @ZxApiParam(name = "cerno", required = true,dataType =DataTypeEnum.String, remark = "法定代表人身份证号"),
            @ZxApiParam(name = "enttype", required = true,dataType =DataTypeEnum.String, remark = "企业类型"),
            @ZxApiParam(name = "cbrs", required = true,dataType =DataTypeEnum.String, remark = "参保人数"),
            @ZxApiParam(name = "industryphy", required = true,dataType =DataTypeEnum.String, remark = "所属行业"),
            @ZxApiParam(name = "opscope", required = true,dataType =DataTypeEnum.String, remark = "经营范围"),
    })
	// api信息说明
    @ZxApiOperation(name = "根据法定代表人姓名和身份证号码返回企业主体信息",path="/data01.do", remark = "根据法定代表人姓名和身份证号码返回企业主体信息")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData01(String name, String cerno) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(name) && StringUtils.isEmpty(cerno)) {
			ret.put("msg", "姓名name、身份证 cerno两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(name)) {
				map.put("name", name);
			}

			if (StringUtils.isNotEmpty(cerno)) {
				map.put("cerno", cerno);
			}
			try {
				list = data01Dao.getEntInfoByNameAndCerno(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				ret.put("code", "2");
				e.printStackTrace();
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data02",name="根据股东、董监事人员、财务等高管姓名和身份证号码返回企业主体信息", method = RequestMethod.GET)
	 @ZxApiRequestParams({
         @ZxApiParam(name = "name",  dataType = DataTypeEnum.String, paramType = "request", remark = "姓名"),
         @ZxApiParam(name = "cerno", dataType = DataTypeEnum.String, paramType = "request", remark = "证件号"),
 })
 // 返回值说明作用于注解上
 @ZxApiResultParams({
         @ZxApiParam(name = "entname", required = true, dataType = DataTypeEnum.String, remark = "企业名称"),
         @ZxApiParam(name = "regcap", required = true, defaultValue = "0", dataType = DataTypeEnum.String, remark = "注册资本"),
         @ZxApiParam(name = "dom", required = true, dataType =DataTypeEnum.String, remark = "注册地址"),
         @ZxApiParam(name = "unifsocicrediden", required = true,  dataType =DataTypeEnum.String, remark = "统一信代码"),
         @ZxApiParam(name = "regorg", required = true, dataType =DataTypeEnum.String, remark = "登记机关"),
         @ZxApiParam(name = "apprdate", required = true , dataType =DataTypeEnum.String, remark = "核准时间"),
         @ZxApiParam(name = "nsrh", required = true, dataType =DataTypeEnum.String, remark = "纳税人识别号"),
         @ZxApiParam(name = "regno", required = true, dataType =DataTypeEnum.String, remark = "注册地址"),
         @ZxApiParam(name = "gph", required = true, dataType =DataTypeEnum.String, remark = "股票号"),
         @ZxApiParam(name = "estdate", required = true,dataType =DataTypeEnum.String, remark = "成立日期"),
         @ZxApiParam(name = "opfrom", required = true,dataType =DataTypeEnum.String, remark = "经营开始时间"),
         @ZxApiParam(name = "opto", required = true,dataType =DataTypeEnum.String, remark = "经营结束时间"),
         @ZxApiParam(name = "qyjc", required = true,dataType =DataTypeEnum.String, remark = "企业简称"),
         @ZxApiParam(name = "qyywm", required = true,dataType =DataTypeEnum.String, remark = "英文名"),
         @ZxApiParam(name = "zym", required = true,dataType =DataTypeEnum.String, remark = "曾用名"),
         @ZxApiParam(name = "lerep", required = true,dataType =DataTypeEnum.String, remark = "法定代表人"),
         @ZxApiParam(name = "cerno", required = true,dataType =DataTypeEnum.String, remark = "法定代表人身份证号"),
         @ZxApiParam(name = "enttype", required = true,dataType =DataTypeEnum.String, remark = "企业类型"),
         @ZxApiParam(name = "cbrs", required = true,dataType =DataTypeEnum.String, remark = "参保人数"),
         @ZxApiParam(name = "industryphy", required = true,dataType =DataTypeEnum.String, remark = "所属行业"),
         @ZxApiParam(name = "opscope", required = true,dataType =DataTypeEnum.String, remark = "经营范围"),
         @ZxApiParam(name = "post", required = true,dataType =DataTypeEnum.String, remark = "担任职务"),
 })
    // api信息说明
	 @ZxApiOperation(name = "根据股东、董监事人员、财务等高管姓名和身份证号码返回企业主体信息",path="/data02.do", remark = "根据股东、董监事人员、财务等高管姓名和身份证号码返回企业主体信息。")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData02(String name, String cerno) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(name) && StringUtils.isEmpty(cerno)) {
			ret.put("msg", "姓名name、身份证 cerno两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(name)) {
				map.put("name", name);
			}

			if (StringUtils.isNotEmpty(cerno)) {
				map.put("cerno", cerno);
			}
			try {
				list = data02Dao.getEntInfoByNameAndCerno(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				e.printStackTrace();
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data03",name="根据统一社会信用代码/企业名称返回企业主体信息", method = RequestMethod.GET)
	 @ZxApiRequestParams({
         @ZxApiParam(name = "unif",  dataType = DataTypeEnum.String, paramType = "request", remark = "统一社会信用代码"),
         @ZxApiParam(name = "entname", dataType = DataTypeEnum.String, paramType = "request", remark = "企业名称"),
 })
 // 返回值说明作用于注解上
 @ZxApiResultParams({
         @ZxApiParam(name = "entname", required = true, dataType = DataTypeEnum.String, remark = "企业名称"),
         @ZxApiParam(name = "regcap", required = true, defaultValue = "0", dataType = DataTypeEnum.String, remark = "注册资本"),
         @ZxApiParam(name = "dom", required = true, dataType =DataTypeEnum.String, remark = "注册地址"),
         @ZxApiParam(name = "unifsocicrediden", required = true,  dataType =DataTypeEnum.String, remark = "统一信代码"),
         @ZxApiParam(name = "regorg", required = true, dataType =DataTypeEnum.String, remark = "登记机关"),
         @ZxApiParam(name = "apprdate", required = true , dataType =DataTypeEnum.String, remark = "核准时间"),
         @ZxApiParam(name = "nsrh", required = true, dataType =DataTypeEnum.String, remark = "纳税人识别号"),
         @ZxApiParam(name = "regno", required = true, dataType =DataTypeEnum.String, remark = "注册地址"),
         @ZxApiParam(name = "gph", required = true, dataType =DataTypeEnum.String, remark = "股票号"),
         @ZxApiParam(name = "estdate", required = true,dataType =DataTypeEnum.String, remark = "成立日期"),
         @ZxApiParam(name = "opfrom", required = true,dataType =DataTypeEnum.String, remark = "经营开始时间"),
         @ZxApiParam(name = "opto", required = true,dataType =DataTypeEnum.String, remark = "经营结束时间"),
         @ZxApiParam(name = "qyjc", required = true,dataType =DataTypeEnum.String, remark = "企业简称"),
         @ZxApiParam(name = "qyywm", required = true,dataType =DataTypeEnum.String, remark = "英文名"),
         @ZxApiParam(name = "zym", required = true,dataType =DataTypeEnum.String, remark = "曾用名"),
         @ZxApiParam(name = "lerep", required = true,dataType =DataTypeEnum.String, remark = "法定代表人"),
         @ZxApiParam(name = "cerno", required = true,dataType =DataTypeEnum.String, remark = "法定代表人身份证号"),
         @ZxApiParam(name = "enttype", required = true,dataType =DataTypeEnum.String, remark = "企业类型"),
         @ZxApiParam(name = "cbrs", required = true,dataType =DataTypeEnum.String, remark = "参保人数"),
         @ZxApiParam(name = "industryphy", required = true,dataType =DataTypeEnum.String, remark = "所属行业"),
         @ZxApiParam(name = "opscope", required = true,dataType =DataTypeEnum.String, remark = "经营范围"),
 })
    // api信息说明
    @ZxApiOperation(name = "根据统一社会信用代码/企业名称返回企业主体信息",path="/data03.do", remark = "根据统一社会信用代码/企业名称返回企业主体信息")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData03(String unif, String entname) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(unif) && StringUtils.isEmpty(entname)) {
			ret.put("msg", "统一信用代码unif，企业名称entname两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(unif)) {
				map.put("unif", unif);
			}

			if (StringUtils.isNotEmpty(entname)) {
				map.put("entname", entname);
			}
			try {
				list = data03Dao.getEntInfoByUnifAndEntName(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				e.printStackTrace();
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data04", name="根据统一社会信用代码/企业名称返回股东信息",method = RequestMethod.GET)
    // 请求参数说明
	 @ZxApiRequestParams({
         @ZxApiParam(name = "unif",  dataType = DataTypeEnum.String, paramType = "request", remark = "统一社会信用代码"),
         @ZxApiParam(name = "entname", dataType = DataTypeEnum.String, paramType = "request", remark = "企业名称"),
 })
 // 返回值说明作用于注解上
 @ZxApiResultParams({
         @ZxApiParam(name = "gdlx", required = true, dataType = DataTypeEnum.String, remark = "股东类型,1个人，2公司"),
         @ZxApiParam(name = "inv", required = true, defaultValue = "0", dataType = DataTypeEnum.String, remark = "姓名或企业名称"),
         @ZxApiParam(name = "certype", required = true, dataType =DataTypeEnum.String, remark = "证件或证照类型"),
         @ZxApiParam(name = "cerno", required = true, dataType =DataTypeEnum.String, remark = "证件/证照号码"),
         @ZxApiParam(name = "subconam", required = true,  dataType =DataTypeEnum.String, remark = "认缴出资额"),
         @ZxApiParam(name = "subconam_date", required = true, dataType =DataTypeEnum.String, remark = "认缴出资日期"),
         @ZxApiParam(name = "conprop", required = true, dataType =DataTypeEnum.String, remark = "持股比例"),
         @ZxApiParam(name = "acconam", required = true, dataType =DataTypeEnum.String, remark = "实缴出资额"),
         @ZxApiParam(name = "acconam_fs", required = true, dataType =DataTypeEnum.String, remark = "实缴方式"),
 })
    // api信息说明
    @ZxApiOperation(name = "根据统一社会信用代码/企业名称返回股东信息",path="/data04.do", remark = "根据统一社会信用代码/企业名称返回股东信息")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData04(String unif, String entname) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(unif) && StringUtils.isEmpty(entname)) {
			ret.put("msg", "统一信用代码unif，企业名称entname两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(unif)) {
				map.put("unif", unif);
			}

			if (StringUtils.isNotEmpty(entname)) {
				map.put("entname", entname);
			}
			try {
				list = data04Dao.getEntInfoByUnifAndEntName(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				e.printStackTrace();
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data05",name="根据统一社会信用代码/企业名称返回董监事、财务等高管主要人员信息", method = RequestMethod.GET)
    // 请求参数说明
	 @ZxApiRequestParams({
         @ZxApiParam(name = "unif", dataType = DataTypeEnum.String, paramType = "request", remark = "统一社会信用代码"),
         @ZxApiParam(name = "entname", dataType = DataTypeEnum.String, paramType = "request", remark = "企业名称"),
 })
 // 返回值说明作用于注解上
 @ZxApiResultParams({
         @ZxApiParam(name = "persname", required = true, dataType = DataTypeEnum.String, remark = "姓名或企业名称"),
         @ZxApiParam(name = "certype", required = true, defaultValue = "0", dataType = DataTypeEnum.String, remark = "证件或证照类型"),
         @ZxApiParam(name = "cerno", required = true, dataType =DataTypeEnum.String, remark = "证件/证照号码"),
         @ZxApiParam(name = "post", required = true,  dataType =DataTypeEnum.String, remark = "职务"),
         @ZxApiParam(name = "start_date", required = true, dataType =DataTypeEnum.String, remark = "任职开始时间"),
         @ZxApiParam(name = "end_date", required = true, dataType =DataTypeEnum.String, remark = "结束时间"),
 })
    // api信息说明
    @ZxApiOperation(name = "根据统一社会信用代码/企业名称返回董监事、财务等高管主要人员信息",path="/data05.do", remark = "根据统一社会信用代码/企业名称返回董监事、财务等高管主要人员信息")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData05(String unif, String entname) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(unif) && StringUtils.isEmpty(entname)) {
			ret.put("msg", "统一信用代码unif，企业名称entname两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(unif)) {
				map.put("unif", unif);
			}

			if (StringUtils.isNotEmpty(entname)) {
				map.put("entname", entname);
			}
			try {
				list = data05Dao.getEntInfoByUnifAndEntName(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				e.printStackTrace();
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data06",name="根据统一社会信用代码/企业名称返回企业所有变更记录", method = RequestMethod.GET)
    // 请求参数说明
	 @ZxApiRequestParams({
         @ZxApiParam(name = "unif",  dataType = DataTypeEnum.String, paramType = "request", remark = "统一社会信用代码"),
         @ZxApiParam(name = "entname", dataType = DataTypeEnum.String, paramType = "request", remark = "企业名称"),
	 })
    // 返回值说明作用于注解上
    @ZxApiResultParams({
            @ZxApiParam(name = "altitem", required = true, dataType = DataTypeEnum.String, remark = "变更事项"),
            @ZxApiParam(name = "altdate", required = true, dataType = DataTypeEnum.String, remark = "变更时间"),
            @ZxApiParam(name = "altbe", required = true, dataType =DataTypeEnum.String, remark = "变更前"),
            @ZxApiParam(name = "altaf", required = true, dataType =DataTypeEnum.String, remark = "变更后"),
            @ZxApiParam(name = "al_sm", required = true, dataType =DataTypeEnum.String, remark = "变更说明")
    })
    // api信息说明
    @ZxApiOperation(name = "根据统一社会信用代码/企业名称返回企业所有变更记录",path="/data06.do", remark = "根据统一社会信用代码/企业名称返回企业所有变更记录")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData06(String unif, String entname) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(unif) && StringUtils.isEmpty(entname)) {
			ret.put("msg", "统一信用代码unif，企业名称entname两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(unif)) {
				map.put("unif", unif);
			}

			if (StringUtils.isNotEmpty(entname)) {
				map.put("entname", entname);
			}
			try {
				list = data06Dao.getEntInfoByUnifAndEntName(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				e.printStackTrace();
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data07",name="根据姓名和身份证号码返回老赖数据", method = RequestMethod.GET)
    // 请求参数说明
	 @ZxApiRequestParams({
         @ZxApiParam(name = "name",  dataType = DataTypeEnum.String, paramType = "request", remark = "姓名"),
         @ZxApiParam(name = "cerno", dataType = DataTypeEnum.String, paramType = "request", remark = "证件号"),
	 })
    // 返回值说明作用于注解上
    @ZxApiResultParams({
            @ZxApiParam(name = "case_code", required = true, dataType = DataTypeEnum.String, remark = "案件号"),
            @ZxApiParam(name = "iname", required = true,  dataType = DataTypeEnum.String, remark = "被执行人姓名"),
            @ZxApiParam(name = "cardnum", required = true, dataType =DataTypeEnum.String, remark = "证件号"),
            @ZxApiParam(name = "age", required = true, dataType =DataTypeEnum.String, remark = "年龄"),
            @ZxApiParam(name = "sex_name", required = true,  dataType =DataTypeEnum.String, remark = "性别"),
            @ZxApiParam(name = "court_name", required = true,  dataType =DataTypeEnum.String, remark = "执行法院/判决机构"),
            @ZxApiParam(name = "area_id", required = true, dataType =DataTypeEnum.String, remark = "地域编号"),
            @ZxApiParam(name = "area_name", required = true, dataType =DataTypeEnum.String, remark = "地域名称"),
            @ZxApiParam(name = "gist_cid", required = true,  dataType =DataTypeEnum.String, remark = "执行依据文号"),
            @ZxApiParam(name = "gist_unit", required = true,  dataType =DataTypeEnum.String, remark = "作出执行依据单位"),
            @ZxApiParam(name = "performance", required = true, dataType =DataTypeEnum.String, remark = "被执行人履行情况"),
            @ZxApiParam(name = "disreput_type_name", required = true, dataType =DataTypeEnum.String, remark = "失信被执行人情形"),
            @ZxApiParam(name = "publish_date", required = true,  dataType =DataTypeEnum.String, remark = "发布时间"),
            @ZxApiParam(name = "reg_date", required = true,  dataType =DataTypeEnum.String, remark = "立案时间"),
            @ZxApiParam(name = "performed_part", required = true,  dataType =DataTypeEnum.String, remark = "已履行部分"),
            @ZxApiParam(name = "unperform_part", required = true,  dataType =DataTypeEnum.String, remark = "未履行部分"),
    })
	
	
    // api信息说明
    @ZxApiOperation(name = "根据姓名和身份证号码返回老赖数据",path="/data07.do", remark = "根据姓名和身份证号码返回老赖数据")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData07(String name, String cerno) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(name) && StringUtils.isEmpty(cerno)) {
			ret.put("msg", "姓名name、身份证 cerno两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(name)) {
				map.put("name", name);
			}

			if (StringUtils.isNotEmpty(cerno)) {
				map.put("cerno", cerno);
			}
			try {
				list = data07Dao.getEntInfoByNameAndCerno(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				ret.put("code", "2");
				e.printStackTrace();
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data08", method = RequestMethod.GET,name="根据统一社会信用代码/企业名称返回严重违法失信数据")
    // 请求参数说明
	@ZxApiRequestParams({
        @ZxApiParam(name = "unif", dataType = DataTypeEnum.String, paramType = "request", remark = "统一社会信用代码"),
        @ZxApiParam(name = "entname", dataType = DataTypeEnum.String, paramType = "request", remark = "企业名称"),
	})
    // 返回值说明作用于注解上
    @ZxApiResultParams({
            @ZxApiParam(name = "pripid", required = true, dataType = DataTypeEnum.String, remark = "主体身份代码（目前就是使用统一社会信用代码）"),
            @ZxApiParam(name = "entname", required = true, dataType = DataTypeEnum.String, remark = "企业（机构）名称"),
            @ZxApiParam(name = "unifsocicrediden", required = true, dataType =DataTypeEnum.String, remark = "统一社会信用代码"),
            @ZxApiParam(name = "regno", required = true, dataType =DataTypeEnum.String, remark = "注册号"),
            @ZxApiParam(name = "persname", required = true, dataType =DataTypeEnum.String, remark = "法定代表人/负责人人员姓名"),
            @ZxApiParam(name = "certype", required = true, dataType =DataTypeEnum.String, remark = "法定代表人/负责人证件类型"),
            @ZxApiParam(name = "cerno", required = true, dataType =DataTypeEnum.String, remark = "法定代表人/负责人证件号码"),
            @ZxApiParam(name = "illrea", required = true, dataType =DataTypeEnum.String, remark = "载入事由"),
            @ZxApiParam(name = "illrea_cn", required = true, dataType =DataTypeEnum.String, remark = "列入事由/情形（中文名称）"),
            @ZxApiParam(name = "createtime", required = true, dataType =DataTypeEnum.String, remark = "列入日期"),
            @ZxApiParam(name = "resoleunitno", required = true, dataType =DataTypeEnum.String, remark = "列入作出决定机关"),
            @ZxApiParam(name = "resoleunit", required = true, dataType =DataTypeEnum.String, remark = "列入作出决定机关（中文名称）"),
            @ZxApiParam(name = "decideno", required = true, dataType =DataTypeEnum.String, remark = "列入文号"),
            @ZxApiParam(name = "remrea", required = true, dataType =DataTypeEnum.String, remark = "移除理由"),
            @ZxApiParam(name = "removereason", required = true, dataType =DataTypeEnum.String, remark = "移出事由（中文名称）"),
            @ZxApiParam(name = "removetime", required = true, dataType =DataTypeEnum.String, remark = "移除时间"),
            @ZxApiParam(name = "move_resoleunitno", required = true, dataType =DataTypeEnum.String, remark = "移出作出决定机关"),
            @ZxApiParam(name = "move_resoleunit", required = true, dataType =DataTypeEnum.String, remark = "移出作出决定机关（中文名称）"),
            @ZxApiParam(name = "move_decideno", required = true, dataType =DataTypeEnum.String, remark = "移出列入文号"),
    })
    // api信息说明
    @ZxApiOperation(name = "根据统一社会信用代码/企业名称返回严重违法失信数据",path="/data08.do", remark = "根据统一社会信用代码/企业名称返回严重违法失信数据。")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData08(String unif, String entname) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(unif) && StringUtils.isEmpty(entname)) {
			ret.put("msg", "统一信用代码unif，企业名称entname两者不能同时为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(unif)) {
				map.put("unif", unif);
			}

			if (StringUtils.isNotEmpty(entname)) {
				map.put("entname", entname);
			}
			try {
				list = data08Dao.getEntInfoByUnifAndEntName(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				e.printStackTrace();
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
			}
			return ret;
		}
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/data09", method = RequestMethod.GET,name="根据日期返回相关统计数据")
    // 请求参数说明
	@ZxApiRequestParams({
        @ZxApiParam(name = "stime", dataType = DataTypeEnum.String, paramType = "request", remark = "开始日期"),
        @ZxApiParam(name = "etime", dataType = DataTypeEnum.String, paramType = "request", remark = "结束日期"),
	})
    // 返回值说明作用于注解上
    @ZxApiResultParams({
            @ZxApiParam(name = "ent_cnt", required = true, dataType = DataTypeEnum.String, remark = "企业主体信息数据量）"),
            @ZxApiParam(name = "member_cnt", required = true, dataType = DataTypeEnum.String, remark = "董监事人员、财务高管信息数据量"),
            @ZxApiParam(name = "invest_cnt", required = true, dataType = DataTypeEnum.String, remark = "股东信息数据量"),
            @ZxApiParam(name = "alt_cnt", required = true, dataType =DataTypeEnum.String, remark = "企业所有变更记录数据量"),
            @ZxApiParam(name = "laolai_cnt", required = true, dataType =DataTypeEnum.String, remark = "老赖数据量"),
            @ZxApiParam(name = "dishonesty_cnt", required = true, dataType =DataTypeEnum.String, remark = "严重违法失信数据量"),
    })
    // api信息说明
    @ZxApiOperation(name = "根据日期返回相关统计数据",path="/data09.do", remark = "根据日期返回相关统计数据")
    // 返回状态码说明
    @ZxApiStatusCodes({
            @ZxApiStatusCode(code = "200", remark="请求成功")
    })
	public Map getData09(String stime,String etime) {
		Map map = new HashMap();
		Map ret = new HashMap();
		List list = null;
		if (StringUtils.isEmpty(stime) && StringUtils.isEmpty(etime)) {
			ret.put("msg", "开始stime和结束etime不能为空");
			ret.put("code", "0");
			ret.put("list", "");
			return ret;
		} else {
			if (StringUtils.isNotEmpty(stime)) {
				map.put("stime", stime);
			}

			if (StringUtils.isNotEmpty(etime)) {
				map.put("etime", etime);
			}
			try {
				list = data09Dao.getDataByTime(map);
				ret.put("code", "1");
				ret.put("msg", "数据请求成功");
				ret.put("list", list);

			} catch (Exception e) {
				ret.put("code", "2");
				ret.put("msg", "系统繁忙，请稍后再试");
				ret.put("list", "");
				e.printStackTrace();
			}
			return ret;
		}
	}
}
