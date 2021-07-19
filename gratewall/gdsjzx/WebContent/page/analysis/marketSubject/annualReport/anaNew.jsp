<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/highstock.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/modules/no-data-to-display.src.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/picker.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/commonUtil.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/anaNew.js"
	type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelectChange.css" rel="stylesheet" type="text/css" media="all">
<link href="<%=request.getContextPath()%>/static/css/analysis/analysisReport.css" rel="stylesheet" type="text/css" media="all">
<title>年度报表</title>

</head>
<body>
<div id="Container">
    <div class="headertop" id="Header">
    时间：
    <input class="btu" type="radio" name="dateType" id="x" value="0" checked="checked"><label for="x">日</label>
    <input class="btu" type="radio" name="dateType" id="y" value="1"><label for="y">月</label>
	<label id="showstart" for="开始">从</label><input class="btu" id="showstarttime" type="text" readonly="readonly" class="Wdate"
			value='2016-05-01' onclick="createWdatePickerStart();" /> 
	<label id="showend" for="结束">至</label><input class="btu" id="showendtime" type="text" readonly="readonly" class="Wdate"
			value='2016-07-01' onclick="createWdatePickerEnd();"/>
	
	</br>
	数据类型：
	<select id="dataType" name="dept" class="btu_select" style="width: 120px;">
	    <option selected value="001" _code="1">市场主体概况</option>
		<option value="002" _code="2">企业设立登记</option>
		<option value="003" _code="3">市场变更</option>
		<option value="004" _code="4">市场注吊销</option>
	</select>
	度量值：
	<select id="measure" name="dept" class="btu_select" style="width: 120px;">
		<option selected value='001001'>期末实有户数</option>
		<option value='001002'>本期登记户数</option>
		<option value='002001'>期末实有注册资本(金)(万元)</option>
		<option value='001005'>本期注吊销户数</option>
		<option value='002009'>本期注销注册资本(金)(万元)</option>
	</select>
	
    <hr>
    区域：
    <select id="regorg_p" multiple name="dept" class="btu_select" style="width: 120px;">
    	<option value='001'>省局</option>
		<option value='002'>珠三角</option>
		<option value='003'>粤东</option>
		<option value='004'>粤西</option>
		<option value='005'>粤北</option>
	</select>
	单位：
	<select id="regorg" multiple name="dept" class="btu_select" style="width: 120px;">
		<option value="440000" p="001">	广东省工商行政管理局</option>
		<option value="440003" p="002">	横琴新区</option>
		<option value="440100" p="002">	广东省广州市工商行政管理局</option>
		<option value="440200" p="005">	广东省韶关市工商行政管理局</option>
		<option value="440300" p="002">	广东省深圳市工商行政管理局</option>
		<option value="440400" p="002">	广东省珠海市工商行政管理局</option>
		<option value="440500" p="003">	广东省汕头市工商行政管理局</option>
		<option value="440600" p="002">	广东省佛山市工商行政管理局</option>
		<option value="440606" p="002">	广东省佛山市顺德区工商行政管理局</option>
		<option value="440700" p="002">	广东省江门市工商行政管理局</option>
		<option value="440800" p="004">	广东省湛江市工商行政管理局</option>
		<option value="440900" p="004">	广东省茂名市工商行政管理局</option>
		<option value="441200" p="002">	广东省肇庆市工商行政管理局</option>
		<option value="441300" p="002">	广东省惠州市工商行政管理局</option>
		<option value="441400" p="003">	广东省梅州市工商行政管理局</option>
		<option value="441500" p="003">	广东省汕尾市工商行政管理局</option>
		<option value="441600" p="003">	广东省河源市工商行政管理局</option>
		<option value="441700" p="004">	广东省阳江市工商行政管理局</option>
		<option value="441800" p="005">	广东省清远市工商行政管理局</option>
		<option value="441900" p="002">	广东省东莞市工商行政管理局</option>
		<option value="442000" p="002">	广东省中山市工商行政管理局</option>
		<option value="445100" p="003">	广东省潮州市工商行政管理局</option>
		<option value="445200" p="003">	广东省揭阳市工商行政管理局</option>
		<option value="445300" p="004">	广东省云浮市工商行政管理局</option>
	</select>
	
    </br>
    行业1：
    <select  id="indus_p1" class="btu_select" multiple name="dept"  style="width:120px" >
    	<option selected value="-1">全选</option>
		<option value="1">第一产业</option>
		<option value="2">第二产业</option>
		<option value="3">第三产业</option>
    </select> 
    行业2：
    <select  id="indus_p2" class="btu_select" name="dept" multiple style="width: 150px;">
    	<option value="-1">	全选</option>
    	<option value="1000">	内资公司</option>
		<option value="1100">	有限责任公司</option>
		<option value="1110">	有限责任公司(国有独资)</option>
		<option value="1120">	有限责任公司(外商投资企业投资)</option>
		<option value="1121">	有限责任公司(外商投资企业合资)</option>
		<option value="1122">	有限责任公司(外商投资企业与内资合资)</option>
		<option value="1123">	有限责任公司(外商投资企业法人独资)</option>
    </select>
    行业3：
    <select  id="indus" class="btu_select" name="dept" multiple style="width: 150px;">
    	<option value="-1">	全选</option>
    	<option value="1000">	内资公司</option>
		<option value="1100">	有限责任公司</option>
		<option value="1110">	有限责任公司(国有独资)</option>
		<option value="1120">	有限责任公司(外商投资企业投资)</option>
		<option value="1121">	有限责任公司(外商投资企业合资)</option>
		<option value="1122">	有限责任公司(外商投资企业与内资合资)</option>
		<option value="1123">	有限责任公司(外商投资企业法人独资)</option>
    </select>
    
    </br>
    企业：
    <select  id="ent" class="btu_select" name="dept" multiple style="width: 150px;">
    	<option value="1000">	内资公司</option>
		<option value="1100">	有限责任公司</option>
		<option value="1110">	有限责任公司(国有独资)</option>
		<option value="1120">	有限责任公司(外商投资企业投资)</option>
		<option value="1121">	有限责任公司(外商投资企业合资)</option>
		<option value="1122">	有限责任公司(外商投资企业与内资合资)</option>
		<option value="1123">	有限责任公司(外商投资企业法人独资)</option>
		<option value="1124">	有限责任公司(外商投资企业投资，私营)</option>
		<option value="1130">	有限责任公司(自然人投资或控股)</option>
		<option value="1140">	有限责任公司(国有控股)</option>
		<option value="1150">	一人有限责任公司</option>
		<option value="1151">	有限责任公司(自然人独资)</option>
		<option value="1152">	有限责任公司（自然人投资或控股的法人独资）</option>
		<option value="1153">	有限责任公司（非自然人投资或控股的法人独资）</option>
		<option value="1190">	其他有限责任公司</option>
		<option value="1200">	股份有限公司</option>
		<option value="1210">	股份有限公司(上市)</option>
		<option value="1211">	股份有限公司(上市、外商投资企业投资)</option>
		<option value="1212">	股份有限公司(上市、自然人投资或控股)</option>
		<option value="1213">	股份有限公司(上市、国有控股)</option>
		<option value="1214">	股份有限公司(上市、外商投资企业投资，私营)</option>
		<option value="1219">	其他股份有限公司(上市)</option>
		<option value="1220">	股份有限公司(非上市)</option>
		<option value="1221">	股份有限公司(非上市、外商投资企业投资)</option>
		<option value="1222">	股份有限公司(非上市、自然人投资或控股)</option>
		<option value="1223">	股份有限公司(非上市、国有控股)</option>
		<option value="1224">	股份有限公司(非上市、外商投资企业投资，私营)</option>
		<option value="1229">	其他股份有限公司(非上市)</option>
		<option value="2000">	内资分公司</option>
		<option value="2100">	有限责任公司分公司</option>
		<option value="2110">	有限责任公司分公司(国有独资)</option>
		<option value="2120">	有限责任公司分公司(外商投资企业投资)</option>
		<option value="2121">	有限责任公司分公司(外商投资企业合资)</option>
		<option value="2122">	有限责任公司分公司(外商投资企业与内资合资)</option>
		<option value="2124">	有限责任公司分公司(外商投资企业投资，私营)</option>
		<option value="2130">	有限责任公司分公司(自然人投资或控股)</option>
		<option value="2140">	有限责任公司分公司(国有控股)</option>
		<option value="2150">	一人有限责任公司分公司</option>
		<option value="2151">	有限责任公司分公司(自然人独资)</option>
		<option value="2152">	有限责任公司分公司(自然人投资或控股的法人独资)</option>
		<option value="2153">	有限责任公司分公司（非自然人投资或控股的法人独资）</option>
		<option value="2190">	其他有限责任公司分公司</option>
		<option value="2200">	股份有限公司分公司</option>
		<option value="2210">	股份有限公司分公司(上市)</option>
		<option value="2211">	股份有限公司分公司(上市、外商投资企业投资)</option>
		<option value="2212">	股份有限公司分公司(上市、自然人投资或控股)</option>
		<option value="2213">	股份有限公司分公司(上市、国有控股)</option>
		<option value="2219">	其他股份有限公司分公司(上市)</option>
		<option value="2220">	股份有限公司分公司(非上市)</option>
		<option value="2221">	股份有限公司分公司(非上市、外商投资企业投资）</option>
		<option value="2222">	股份有限公司分公司(非上市、自然人投资或控股)</option>
		<option value="2223">	股份有限公司分公司(非上市、国有控股)</option>
		<option value="2224">	股份有限公司分公司(非上市、外商投资企业投资、私营)</option>
		<option value="2229">	其他股份有限公司分公司(非上市)</option>
		<option value="3000">	内资企业法人</option>
		<option value="3100">	全民所有制</option>
		<option value="3200">	集体所有制</option>
		<option value="3300">	股份制</option>
		<option value="3400">	股份合作制</option>
		<option value="3500">	联营</option>
		<option value="4000">	内资非法人企业、非公司私营企业</option>
		<option value="4100">	事业单位营业</option>
		<option value="4110">	国有事业单位营业</option>
		<option value="4120">	集体事业单位营业</option>
		<option value="4200">	社团法人营业</option>
		<option value="4210">	国有社团法人营业</option>
		<option value="4220">	集体社团法人营业</option>
		<option value="4300">	内资企业法人分支机构(非法人)</option>
		<option value="4310">	全民所有制分支机构(非法人)</option>
		<option value="4320">	集体分支机构(非法人)</option>
		<option value="4330">	股份制分支机构</option>
		<option value="4340">	股份合作制分支机构</option>
		<option value="4400">	经营单位(非法人)</option>
		<option value="4410">	国有经营单位(非法人)</option>
		<option value="4420">	集体经营单位(非法人)</option>
		<option value="4500">	非公司私营企业</option>
		<option value="4530">	合伙企业</option>
		<option value="4531">	普通合伙企业</option>
		<option value="4532">	特殊普通合伙企业</option>
		<option value="4533">	有限合伙企业</option>
		<option value="4540">	个人独资企业</option>
		<option value="4550">	合伙企业分支机构</option>
		<option value="4551">	普通合伙企业分支机构</option>
		<option value="4552">	特殊普通合伙企业分支机构</option>
		<option value="4553">	有限合伙企业分支机构</option>
		<option value="4560">	个人独资企业分支机构</option>
		<option value="4600">	联营</option>
		<option value="4700">	股份制企业(非法人)</option>
		<option value="5000">	外商投资企业</option>
		<option value="5100">	有限责任公司</option>
		<option value="5110">	有限责任公司(中外合资)</option>
		<option value="5120">	有限责任公司(中外合作)</option>
		<option value="5130">	有限责任公司(外商合资)</option>
		<option value="5140">	有限责任公司(外国自然人独资)</option>
		<option value="5150">	有限责任公司(外国法人独资)</option>
		<option value="5160">	有限责任公司(外国非法人经济组织独资)</option>
		<option value="5190">	其他</option>
		<option value="5200">	股份有限公司</option>
		<option value="5210">	股份有限公司(中外合资、未上市)</option>
		<option value="5220">	股份有限公司(中外合资、上市)</option>
		<option value="5230">	股份有限公司(外商合资、未上市)</option>
		<option value="5240">	股份有限公司(外商合资、上市)</option>
		<option value="5290">	其他</option>
		<option value="5300">	非公司</option>
		<option value="5310">	非公司外商投资企业(中外合作)</option>
		<option value="5320">	非公司外商投资企业(外商合资)</option>
		<option value="5390">	其他</option>
		<option value="5400">	外商投资合伙企业</option>
		<option value="5410">	外商投资普通合伙企业</option>
		<option value="5420">	外商投资特殊普通合伙企业</option>
		<option value="5430">	外商投资有限合伙企业</option>
		<option value="5490">	其他</option>
		<option value="5600">	三来一补</option>
		<option value="5610">	来料加工</option>
		<option value="5620">	来件装配</option>
		<option value="5630">	来图来样生产</option>
		<option value="5640">	贸易补偿</option>
		<option value="5800">	外商投资企业分支机构</option>
		<option value="5810">	分公司</option>
		<option value="5820">	非公司外商投资企业分支机构</option>
		<option value="5830">	办事处</option>
		<option value="5840">	外商投资合伙企业分支机构</option>
		<option value="5890">	其他</option>
		<option value="6000">	台、港、澳投资企业</option>
		<option value="6100">	有限责任公司</option>
		<option value="6110">	有限责任公司(台港澳与境内合资)</option>
		<option value="6120">	有限责任公司(台港澳与境内合作)</option>
		<option value="6130">	有限责任公司(台港澳合资)</option>
		<option value="6140">	有限责任公司(台港澳自然人独资)</option>
		<option value="6150">	有限责任公司(台港澳法人独资)</option>
		<option value="6160">	有限责任公司(台港澳非法人经济组织独资)</option>
		<option value="6170">	有限责任公司(台港澳与外国投资者合资)</option>
		<option value="6190">	其他</option>
		<option value="6200">	股份有限公司</option>
		<option value="6210">	股份有限公司(台港澳与境内合资、未上市)</option>
		<option value="6220">	股份有限公司(台港澳与境内合资、上市)</option>
		<option value="6230">	股份有限公司(台港澳合资、未上市)</option>
		<option value="6240">	股份有限公司(台港澳合资、上市)</option>
		<option value="6250">	股份有限公司(台港澳与外国投资者合资、未上市)</option>
		<option value="6260">	股份有限公司(台港澳与外国投资者合资、上市)</option>
		<option value="6290">	其他</option>
		<option value="6300">	非公司</option>
		<option value="6310">	非公司台、港、澳企业(台港澳与境内合作)</option>
		<option value="6320">	非公司台、港、澳企业(台港澳合资)</option>
		<option value="6390">	其他</option>
		<option value="6400">	港、澳、台投资合伙企业</option>
		<option value="6410">	普通合伙企业</option>
		<option value="6420">	特殊普通合伙企业</option>
		<option value="6430">	有限合伙企业</option>
		<option value="6490">	其他</option>
		<option value="6800">	台、港、澳投资企业分支机构</option>
		<option value="6810">	分公司</option>
		<option value="6820">	非公司台、港、澳投资企业分支机构</option>
		<option value="6830">	办事处</option>
		<option value="6840">	港、澳、台投资合伙企业分支机构</option>
		<option value="6890">	其他</option>
		<option value="7000">	外国（地区）企业</option>
		<option value="7100">	外国（地区）公司分支机构</option>
		<option value="7110">	外国(地区)无限责任公司分支机构</option>
		<option value="7120">	外国(地区)有限责任公司分支机构</option>
		<option value="7130">	外国(地区)股份有限责任公司分支机构</option>
		<option value="7190">	外国(地区)其他形式公司分支机构</option>
		<option value="7200">	外国(地区)企业常驻代表机构</option>
		<option value="7300">	外国(地区)企业在中国境内从事经营活动</option>
		<option value="7310">	分公司</option>
		<option value="7390">	其他</option>
		<option value="8000">	集团</option>
		<option value="9000">	其他类型</option>
		<option value="9100">	农民专业合作经济组织</option>
		<option value="9200">	农民专业合作社分支机构</option>
		<option value="9900">	其他</option>
		<option value="9999">	个体</option>
    </select>
    
    </br>
    资金规模1：
    <select  id="dom_p" class="btu_select" name="dept" multiple style="width:120px" >
		<option value="001">内资</option>
		<option value="002">私营</option>
		<option value="003">农合</option>
    </select> 
    资金规模2：
    <select  id="dom" class="btu_select" name="dept" multiple style="width:120px" >
		<option value="001001" p="001">	期末实有企业注册资本（金）100万以下</option>
		<option value="001002" p="001">	期末实有企业注册资本（金）100-1000万</option>
		<option value="001003" p="001">	期末实有企业注册资本（金）1000万-1亿元</option>
		<option value="001004" p="001">	期末实有企业注册资本（金）1亿元以上</option>
		<option value="002001" p="002">	期末实有注册资本（金）100~500万元</option>
		<option value="002002" p="002">	期末实有注册资本（金）500~1000万</option>
		<option value="002003" p="002">	期末实有注册资本（金）1000万-1亿元</option>
		<option value="002004" p="002">	期末实有注册资本（金）1亿元以上</option>
		<option value="003001" p="003">	出资总额100~500万元</option>
		<option value="003002" p="003">	出资总额500-1000万</option>
		<option value="003003" p="003">	出资总额1000万-1亿元</option>
		<option value="003004" p="003">	出资总额1亿元以上</option>
    </select> 
    
    </br>
    经济性质1：
    <select  id="econ_p" class="btu_select" name="dept" multiple style="width:120px" >
		<option value="1">内资</option>
		<option value="2">私营</option>
		<option value="3">外企</option>
    </select> 
    经济性质2：
    <select  id="econ" class="btu_select" name="dept" multiple style="width:120px" >
    	<option value="1" p="1">内资(非私营)</option>
		<option value="11" p="1">国有</option>
		<option value="12" p="1">集体</option>
		<option value="19" p="1">内资(未知)</option>
		<option value="2" p="2">私营</option>
		<option value="3" p="3">外资</option>
    </select> 
    
    </br>
    组织形式1：
    <select  id="organ_p" class="btu_select" name="dept" multiple style="width:120px" >
    	<option value="0">个体工商户</option>
    	<option value="1">公司</option>
    	<option value="2">合伙企业</option>
    	<option value="3">个人独资企业</option>
    	<option value="4">内资非公司企业法人</option>
    	<option value="5">内资非法人企业</option>
    	<option value="6">外资非公司企业</option>
    	<option value="7">外国（地区）企业</option>
    	<option value="8">集团</option>
    	<option value="9">农民专业合作经济组织</option>
    </select> 
    组织形式2：
    <select  id="organ" class="btu_select" name="dept" multiple style="width:120px" >
		<option value="0" p="0">个体工商户</option>
		<option value="1" p="1">公司</option>
		<option value="11" p="1">有限责任公司</option>
		<option value="12" p="1">股份有限公司</option>
		<option value="2" p="2">合伙企业</option>
		<option value="21" p="2">普通合伙企业</option>
		<option value="22" p="2">特殊普通合伙企业</option>
		<option value="23" p="2">有限合伙企业</option>
		<option value="3" p="3">个人独资企业</option>
		<option value="4" p="4">内资非公司企业法人</option>
		<option value="41" p="4">全民所有制</option>
		<option value="42" p="4">集体所有制</option>
		<option value="43" p="4">股份制</option>
		<option value="44" p="4">股份合作制</option>
		<option value="45" p="4">联营</option>
		<option value="5" p="5">内资非法人企业</option>
		<option value="51" p="5">事业单位营业</option>
		<option value="52" p="5">社团法人营业</option>
		<option value="53" p="5">经营单位（非法人）</option>
		<option value="54" p="5">联营</option>
		<option value="55" p="5">股份制企业（非法人）</option>
		<option value="6" p="6">外资非公司企业</option>
		<option value="61" p="6">与境内合作</option>
		<option value="62" p="6">境外合资</option>
		<option value="63" p="6">直通车企业</option>
		<option value="64" p="6">其他</option>
		<option value="7" p="7">外国（地区）企业</option>
		<option value="71" p="7">外国（地区）公司分支机构</option>
		<option value="72" p="7">外国（地区）企业常驻代表机构</option>
		<option value="73" p="7">外国（地区）企业在中国境内从事经营活动</option>
		<option value="8" p="8">集团</option>
		<option value="9" p="9">农民专业合作经济组织</option>
    </select> 
    
    <hr>
    自变量:
    <select  id="out_x" class="btu_select" name="dept" style="width:120px" >
    	<option selected value="time" code="0">时间</option>
    	<option value="regorg_p" code="1">区域</option>
    	<option value="regorg" code="1">行政</option>
    	<option value="indus_p1" code="2">行业1</option>
    	<option value="indus_p2" code="2">行业2</option>
    	<option value="indus" code="2">行业3</option>
    	<option value="ent" code="3">企业</option>
    	<option value="dom_p" code="4">资金规模1</option>
    	<option value="dom" code="4">资金规模2</option>
    	<option value="econ_p" code="5">经济性质1</option>
    	<option value="econ" code="5">经济性质2</option>
    	<option value="organ_p" code="6">组织形式1</option>
    	<option value="organ" code="6">组织形式2</option>
    </select> 
    因变量:
   <select  id="out_y" class="btu_select" name="dept" style="width:120px" >
    	<option selected value="regorg_p" code="1">区域</option>
    	<option value="regorg" code="1">行政</option>
    	<option value="indus_p1" code="2">行业1</option>
    	<option value="indus_p2" code="2">行业2</option>
    	<option value="indus" code="2">行业3</option>
    	<option value="ent" code="3">企业</option>
    	<option value="dom_p" code="4">资金规模1</option>
    	<option value="dom" code="4">资金规模2</option>
    	<option value="econ_p" code="5">经济性质1</option>
    	<option value="econ" code="5">经济性质2</option>
    	<option value="organ_p" code="6">组织形式1</option>
    	<option value="organ" code="6">组织形式2</option>
    </select> 
    <input id="selData" class="bt" type="button" value="提交" onclick="initCharts();"/>
  </div>
  </div>
  <hr>
	<div id="Content">
       	<div id="container" style="width: 98%;"></div>
    </div>

</body>
<script type="text/javascript">
function initCharts(){
	var showstarttime=$("#showstarttime").val();
	var showendtime=$("#showendtime").val();
	var dateType = $('input[name="dateType"]:checked ').val();//时间类型
	var measureCode = $("#measure option:selected").val(); //measureCode
	var isBG = ($("#dataType option:selected").val() == '003' ? '1' : '0');
	var requestData = {startTime:showstarttime, endTime: showendtime, dateType: dateType,
			           measure: measureCode, isBG: isBG};
	getRegorgData(requestData);//区域
	getIndusData(requestData);//行业
	getEntData(requestData);//企业
	getDomData(requestData);//资金规模
	getEconData(requestData);//经济性质
	getOrganData(requestData);//组织形式
	requestData.out = $("#out_x option:selected").val() + ',' +  $("#out_y option:selected").val();
	var req = {};
	getMultSelectedValue(req, ['indus_p1', 'indus_p2', 'indus'], ['indus_p1', 'indus_p2', 'indus'], 0, null);
	return ;
    $.ajax({
		url: '<%=request.getContextPath()%>/analysis/anaAllPageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:requestData,
		success : function(data_all) {
			if(data_all != null){
				var x = new Array();
				var series = new Array();
				var names = new Array();
				if(data_all.length > 0) {
					x.push(data_all[0].c0);
					names.push(data_all[0].c1);
					var i = 1;
					var j = 0;
					//日期以及名称去重复
					for( ; i < data_all.length; i ++){
						if(data_all[i].c0 != x[j]){//新增时间点
							x.push(data_all[i].c0);
							j ++;
						}
						//名字不重复列表
						addIfAbsent(names, data_all[i].c1);
					}
					//拼装series数据,series数组长度应为名称数组长度
					for(i = 0; i < names.length; i ++) {
						series.push({name:names[i],data: new Array()});
					}
					//插入数据
					var obj_t;
					for(i = 0 ; i < data_all.length; i ++){
						(series[getIndex(names, data_all[i].c1)]).data.push({x:getIndex(x,data_all[i].c0),y:data_all[i].s});
					}
					//组装charts数据
				}
				//画图     step ...图例的长度        max是x轴坐标
				$('#container').highcharts({
			        chart: {type: 'column'},
			        title: {text: $("#busType option:selected").html()},
			        scrollbar: {enabled: true},
			        xAxis: {categories: x,
			            labels: {rotation: -45,align: 'right',style: { font: 'normal 13px 宋体'}}
			        },
			        scrollbar : {
			            enabled:true
			        },
			         plotOptions: {
	        			column: {
		               	pointWidth: 30,
		              }
	               	},
			        yAxis: {min: 0,title: {text: ''}},
			        legend: {
			        	title:{text:'企业类型：'},
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -0,y: 0,
			            borderWidth: 1,
			        },
			        plotOptions: {
			            column: {
			                borderWidth: 0,
			               	pointWidth: 30,
					        pointPadding : 0.5,
			            },
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        series: series
			    });
			}
		}
	}); 
}

//返回数组中位置
function getIndex(array, value){
	for(var i = 0 ; i < array.length; i ++){
		if(array[i] == value)
			return i;
	}
	return -1;
}

//数组中不存在则添加
function addIfAbsent(array, value){
	for(var i = 0; i < array.length; i ++){
		if(array[i] == value)
			return;
	}
	array.push(value);
}
</script>
</html>
