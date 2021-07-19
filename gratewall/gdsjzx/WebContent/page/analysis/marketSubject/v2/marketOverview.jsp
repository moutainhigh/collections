<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/highstock.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/modules/no-data-to-display.src.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/picker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/analysis/commonUtil.js" type="text/javascript"></script>

<%-- <script src="<%=request.getContextPath()%>/static/script/jquery.bgiframe.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.multiSelect.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/script/jquery.multiSelectChange.css" rel="stylesheet" type="text/css" media="all">
 --%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/multiple-select.css" />
<script src="<%=request.getContextPath()%>/static/script/multiple-select.js"></script>
<style type="text/css">
table {
	width: 100%;
	border: solid #e5e5e5;
	border-width: 1px 0px 0px 1px;
}

.td1 {
	text-align: right;
	border: 1px solid;
	width: 10%;
	border: solid #add9c0;
	border-width: 0px 1px 1px 0px;
}

.td2 {
	border: 1px solid;
	width: 15%;
	text-align: left;
	border: solid #add9c0;
	border-width: 0px 1px 1px 0px;
}

btu form-control2 {
	width: 100%;
}

.btu_select _ent {
	width: 80%;
}

input[type="text"] {
	display: inline;
	height: 28px;
	padding: 6px 12px;
	font-size: 14px;
	line-height: 1.428571429;
	width: 100%;
	color: #555555;
	vertical-align: middle;
	background-color: #ffffff;
	background-image: none;
	border-radius: 4px;
		border: none;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-webkit-transition: border-color ease-in-out 0.15s, box-shadow
		ease-in-out 0.15s;
	transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
	color: #555555;
}

.form-control2 {
	display: inline;
	height: 28px;
	padding-left: 8px;
	width: 100%;
	font-size: 14px;
	line-height: 1.428571429;
	color: #999;
	vertical-align: middle;
	background-color: #ffffff;
	background-image: none;
	border: none;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-webkit-transition: border-color ease-in-out 0.15s, box-shadow
		ease-in-out 0.15s;
	transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
	font-size: 14px;
}





/*edit by yenairong,edit style begin*/
input{border: 0 ;border:none;outline:none;}
input:focus{border: 0 ;border:none;outline:none;}
select{
	appearance:none;
  -moz-appearance:none;
  -webkit-appearance:none;
}

.box_bgc_ef{background: #eff6fa;}
.box_bgc_fb{background:#fbfdfd;}

.ms-choice{
	background: #eff6fa;
	border-radius:0px !important;
}

.form-control2 {border-radius: 0px !important;}
.td1{border: 0 none;font-family: "微软雅黑";font-weight: bold;}
.td2{border: 0 none;border-right: 1px dashed #ccc; }

/*提交和重置按钮*/
.box-btn{
	background-position-x:3px;
	border: 0 none;
	outline: none;
	width: 73px;
	height: 26px;
}

.btn_submit{background:url(/gdsjzx/page/sjbd/img/30.png) no-repeat;}
.btn_reset{background:url(/gdsjzx/page/sjbd/img/31.png) no-repeat;}
/*edit ended*/

</style>

<script type="text/javascript">
var allAreaInfo = [];
//初始化筛选
$(function () {
	showFirst();
	//初始化行业/产业菜单
	initMultSelect('indus_p1');
	initMultSelect('indus_p2');
	initMultSelect1('indus');
	//initIndus3();
	//区域及单位
	initMultSelect('regorg_p');
	initMultSelect1('regorg');
	//初始页面隐藏(区域及单位)
	//$("._regorg").hide();
	//企业
	initMultSelect1('ent');
	//经济性质
	initMultSelect('econ_p');
	initMultSelect1('econ');
	//组织形式
	initMultSelect('organ_p');
	
	//An internal error occurred during: "Updating status for Tomcat v7.0 Server at localhost...".unable to create new native thread
	initMultSelect1('organ');
	//资金规模
	initMultSelect('dom_p');
	initMultSelect1('dom');
	//获取地市名与code对照表,从后台获取,需要更名
	initAllAreaInfo();
	//吊销与概况更换，对应时间条件也需要变更(一个本期，一个期末)
	/* $("#dataType").change(function(){
		if(this.value == '1'){//主体概况
			$(".just_endtime").hide();
		}else{
			$(".just_endtime").show();
		}
	}); */
	//设置统计图高度
	var h = $(window).height() - document.getElementById('header_table').clientHeight;
	$("#container").css('height',h);
	$("#container2").css('height',h);
	initCharts();
})

//

//根据地市名获取对应code（二级查询）
function getAreaCode(value){
	if(value != null && value != ""){
		for(var i = 0 ; i < allAreaInfo.length; i ++){
			if(value == allAreaInfo[i].v)
				return allAreaInfo[i].c;
		}
	}
}
//加载所有地市名称与代码映射表
function initAllAreaInfo() {
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/allAreaInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:{},
		success : function(data) {
			//[{"xc":"440000","qv":"广东省","xv":"广东省工商行政管理局","qc":"001"}] 
			if(data != null){
				for(var i = 0; i < data.length; i ++){
					allAreaInfo.push({v:data[i].xv, c:data[i].xc});
				}
			}
		}});
}

/* function initIndus3() {
	$("#indus").append('<option selected value="-1">全选</option>');
	var t;
	for(var i = 0; i < _indus3_data.length; i ++){
		t = _indus3_data[i];
		$("#indus").append('<option value="'+t.c+'" p="'+t.p+'">'+t.v+'</option>');
	}
	initMultSelect('indus');
} */

function showFirst(){
	$("._second").hide();
	$("._first").show();
}
function showSecond(){
	$("._first").hide();
	$("._second").show();
}
</script>
<title>市场主体概况</title>
</head>
<body>
<div id="Container">
    <div class="headertop" id="Header">
  		<table id="header_table">
				<tr class="box_bgc_ef">
					<td class="td1">日期:</td>
					<td class='td2'><label id="showstart" class="just_endtime"
						style="display: none" for="开始">从 </label><input
						style="display: none" class="just_endtime btu" id="showstarttime"
						type="text" readonly="readonly" class="Wdate" value='2016-05-01'
						onclick="createWdatePickerStart();" /> <label id="showend"
						class="just_endtime" style="display: none" for="结束">至 </label><input
						class="btu form-control2" style="background: #eff6fa" id="showendtime" type="text"
						readonly="readonly" class="Wdate" value='2016-06-01'
						onclick="createWdatePickerEnd();" /></td>
					<td class="td1">指标:</td>
					<td class="td2"><select id="dataType" name="dept"
						class="form-control2" style="background: #eff6fa">
							<option selected value="1">期末实有</option>
							<option value="2">期末注吊销</option>
					</select></td>
					<td class="td1">区域:</td>
					<td class="td2"><select style="display:none" id="regorg_p" multiple name="dept">
							<option value='001'>直属局</option>
							<option value='002'>珠三角</option>
							<option value='003'>粤东</option>
							<option value='004'>粤西</option>
							<option value='005'>粤北</option>
					</select></td>
					<td class="td1">行政区划:</td>
					<td class="td2"><select style="display:none" id="regorg" multiple name="dept">
							<option value="440000" p="001">直属局</option>
							<option value="440100" p="002">广州市</option>
							<option value="440200" p="005">韶关市</option>
							<option value="440300" p="002">深圳市</option>
							<option value="440400" p="002">珠海市</option>
							<option value="440500" p="003">汕头市</option>
							<option value="440600" p="002">佛山市</option>
							<option value="440700" p="002">江门市</option>
							<option value="440800" p="004">湛江市</option>
							<option value="440900" p="004">茂名市</option>
							<option value="441200" p="002">肇庆市</option>
							<option value="441300" p="002">惠州市</option>
							<option value="441400" p="003">梅州市</option>
							<option value="441500" p="003">汕尾市</option>
							<option value="441600" p="003">河源市</option>
							<option value="441700" p="004">阳江市</option>
							<option value="441800" p="005">清远市</option>
							<option value="441900" p="002">东莞市</option>
							<option value="442000" p="002">中山市</option>
							<option value="445100" p="003">潮州市</option>
							<option value="445200" p="003">揭阳市</option>
							<option value="445300" p="004">云浮市</option>
							<option value="440606" p="002">顺德区</option>
							<option value="440003" p="002">横琴新区</option>
					</select></td>

				</tr>


				<tr class="box_bgc_fb">
					<td class="td1">企业类型:</td>
					<td class="td2"><select style="display:none" id="ent" name="dept" multiple>
							<option value="1000">内资公司</option>
							<option value="1100">有限责任公司</option>
							<option value="1110">有限责任公司(国有独资)</option>
							<option value="1120">有限责任公司(外商投资企业投资)</option>
							<option value="1121">有限责任公司(外商投资企业合资)</option>
							<option value="1122">有限责任公司(外商投资企业与内资合资)</option>
							<option value="1123">有限责任公司(外商投资企业法人独资)</option>
							<option value="1124">有限责任公司(外商投资企业投资，私营)</option>
							<option value="1130">有限责任公司(自然人投资或控股)</option>
							<option value="1140">有限责任公司(国有控股)</option>
							<option value="1150">一人有限责任公司</option>
							<option value="1151">有限责任公司(自然人独资)</option>
							<option value="1152">有限责任公司（自然人投资或控股的法人独资）</option>
							<option value="1153">有限责任公司（非自然人投资或控股的法人独资）</option>
							<option value="1190">其他有限责任公司</option>
							<option value="1200">股份有限公司</option>
							<option value="1210">股份有限公司(上市)</option>
							<option value="1211">股份有限公司(上市、外商投资企业投资)</option>
							<option value="1212">股份有限公司(上市、自然人投资或控股)</option>
							<option value="1213">股份有限公司(上市、国有控股)</option>
							<option value="1214">股份有限公司(上市、外商投资企业投资，私营)</option>
							<option value="1219">其他股份有限公司(上市)</option>
							<option value="1220">股份有限公司(非上市)</option>
							<option value="1221">股份有限公司(非上市、外商投资企业投资)</option>
							<option value="1222">股份有限公司(非上市、自然人投资或控股)</option>
							<option value="1223">股份有限公司(非上市、国有控股)</option>
							<option value="1224">股份有限公司(非上市、外商投资企业投资，私营)</option>
							<option value="1229">其他股份有限公司(非上市)</option>
							<option value="2000">内资分公司</option>
							<option value="2100">有限责任公司分公司</option>
							<option value="2110">有限责任公司分公司(国有独资)</option>
							<option value="2120">有限责任公司分公司(外商投资企业投资)</option>
							<option value="2121">有限责任公司分公司(外商投资企业合资)</option>
							<option value="2122">有限责任公司分公司(外商投资企业与内资合资)</option>
							<option value="2124">有限责任公司分公司(外商投资企业投资，私营)</option>
							<option value="2130">有限责任公司分公司(自然人投资或控股)</option>
							<option value="2140">有限责任公司分公司(国有控股)</option>
							<option value="2150">一人有限责任公司分公司</option>
							<option value="2151">有限责任公司分公司(自然人独资)</option>
							<option value="2152">有限责任公司分公司(自然人投资或控股的法人独资)</option>
							<option value="2153">有限责任公司分公司（非自然人投资或控股的法人独资）</option>
							<option value="2190">其他有限责任公司分公司</option>
							<option value="2200">股份有限公司分公司</option>
							<option value="2210">股份有限公司分公司(上市)</option>
							<option value="2211">股份有限公司分公司(上市、外商投资企业投资)</option>
							<option value="2212">股份有限公司分公司(上市、自然人投资或控股)</option>
							<option value="2213">股份有限公司分公司(上市、国有控股)</option>
							<option value="2219">其他股份有限公司分公司(上市)</option>
							<option value="2220">股份有限公司分公司(非上市)</option>
							<option value="2221">股份有限公司分公司(非上市、外商投资企业投资）</option>
							<option value="2222">股份有限公司分公司(非上市、自然人投资或控股)</option>
							<option value="2223">股份有限公司分公司(非上市、国有控股)</option>
							<option value="2224">股份有限公司分公司(非上市、外商投资企业投资、私营)</option>
							<option value="2229">其他股份有限公司分公司(非上市)</option>
							<option value="3000">内资企业法人</option>
							<option value="3100">全民所有制</option>
							<option value="3200">集体所有制</option>
							<option value="3300">股份制</option>
							<option value="3400">股份合作制</option>
							<option value="3500">联营</option>
							<option value="4000">内资非法人企业、非公司私营企业</option>
							<option value="4100">事业单位营业</option>
							<option value="4110">国有事业单位营业</option>
							<option value="4120">集体事业单位营业</option>
							<option value="4200">社团法人营业</option>
							<option value="4210">国有社团法人营业</option>
							<option value="4220">集体社团法人营业</option>
							<option value="4300">内资企业法人分支机构(非法人)</option>
							<option value="4310">全民所有制分支机构(非法人)</option>
							<option value="4320">集体分支机构(非法人)</option>
							<option value="4330">股份制分支机构</option>
							<option value="4340">股份合作制分支机构</option>
							<option value="4400">经营单位(非法人)</option>
							<option value="4410">国有经营单位(非法人)</option>
							<option value="4420">集体经营单位(非法人)</option>
							<option value="4500">非公司私营企业</option>
							<option value="4530">合伙企业</option>
							<option value="4531">普通合伙企业</option>
							<option value="4532">特殊普通合伙企业</option>
							<option value="4533">有限合伙企业</option>
							<option value="4540">个人独资企业</option>
							<option value="4550">合伙企业分支机构</option>
							<option value="4551">普通合伙企业分支机构</option>
							<option value="4552">特殊普通合伙企业分支机构</option>
							<option value="4553">有限合伙企业分支机构</option>
							<option value="4560">个人独资企业分支机构</option>
							<option value="4600">联营</option>
							<option value="4700">股份制企业(非法人)</option>
							<option value="5000">外商投资企业</option>
							<option value="5100">有限责任公司</option>
							<option value="5110">有限责任公司(中外合资)</option>
							<option value="5120">有限责任公司(中外合作)</option>
							<option value="5130">有限责任公司(外商合资)</option>
							<option value="5140">有限责任公司(外国自然人独资)</option>
							<option value="5150">有限责任公司(外国法人独资)</option>
							<option value="5160">有限责任公司(外国非法人经济组织独资)</option>
							<option value="5190">其他</option>
							<option value="5200">股份有限公司</option>
							<option value="5210">股份有限公司(中外合资、未上市)</option>
							<option value="5220">股份有限公司(中外合资、上市)</option>
							<option value="5230">股份有限公司(外商合资、未上市)</option>
							<option value="5240">股份有限公司(外商合资、上市)</option>
							<option value="5290">其他</option>
							<option value="5300">非公司</option>
							<option value="5310">非公司外商投资企业(中外合作)</option>
							<option value="5320">非公司外商投资企业(外商合资)</option>
							<option value="5390">其他</option>
							<option value="5400">外商投资合伙企业</option>
							<option value="5410">外商投资普通合伙企业</option>
							<option value="5420">外商投资特殊普通合伙企业</option>
							<option value="5430">外商投资有限合伙企业</option>
							<option value="5490">其他</option>
							<option value="5600">三来一补</option>
							<option value="5610">来料加工</option>
							<option value="5620">来件装配</option>
							<option value="5630">来图来样生产</option>
							<option value="5640">贸易补偿</option>
							<option value="5800">外商投资企业分支机构</option>
							<option value="5810">分公司</option>
							<option value="5820">非公司外商投资企业分支机构</option>
							<option value="5830">办事处</option>
							<option value="5840">外商投资合伙企业分支机构</option>
							<option value="5890">其他</option>
							<option value="6000">台、港、澳投资企业</option>
							<option value="6100">有限责任公司</option>
							<option value="6110">有限责任公司(台港澳与境内合资)</option>
							<option value="6120">有限责任公司(台港澳与境内合作)</option>
							<option value="6130">有限责任公司(台港澳合资)</option>
							<option value="6140">有限责任公司(台港澳自然人独资)</option>
							<option value="6150">有限责任公司(台港澳法人独资)</option>
							<option value="6160">有限责任公司(台港澳非法人经济组织独资)</option>
							<option value="6170">有限责任公司(台港澳与外国投资者合资)</option>
							<option value="6190">其他</option>
							<option value="6200">股份有限公司</option>
							<option value="6210">股份有限公司(台港澳与境内合资、未上市)</option>
							<option value="6220">股份有限公司(台港澳与境内合资、上市)</option>
							<option value="6230">股份有限公司(台港澳合资、未上市)</option>
							<option value="6240">股份有限公司(台港澳合资、上市)</option>
							<option value="6250">股份有限公司(台港澳与外国投资者合资、未上市)</option>
							<option value="6260">股份有限公司(台港澳与外国投资者合资、上市)</option>
							<option value="6290">其他</option>
							<option value="6300">非公司</option>
							<option value="6310">非公司台、港、澳企业(台港澳与境内合作)</option>
							<option value="6320">非公司台、港、澳企业(台港澳合资)</option>
							<option value="6390">其他</option>
							<option value="6400">港、澳、台投资合伙企业</option>
							<option value="6410">普通合伙企业</option>
							<option value="6420">特殊普通合伙企业</option>
							<option value="6430">有限合伙企业</option>
							<option value="6490">其他</option>
							<option value="6800">台、港、澳投资企业分支机构</option>
							<option value="6810">分公司</option>
							<option value="6820">非公司台、港、澳投资企业分支机构</option>
							<option value="6830">办事处</option>
							<option value="6840">港、澳、台投资合伙企业分支机构</option>
							<option value="6890">其他</option>
							<option value="7000">外国（地区）企业</option>
							<option value="7100">外国（地区）公司分支机构</option>
							<option value="7110">外国(地区)无限责任公司分支机构</option>
							<option value="7120">外国(地区)有限责任公司分支机构</option>
							<option value="7130">外国(地区)股份有限责任公司分支机构</option>
							<option value="7190">外国(地区)其他形式公司分支机构</option>
							<option value="7200">外国(地区)企业常驻代表机构</option>
							<option value="7300">外国(地区)企业在中国境内从事经营活动</option>
							<option value="7310">分公司</option>
							<option value="7390">其他</option>
							<option value="8000">集团</option>
							<option value="9000">其他类型</option>
							<option value="9100">农民专业合作经济组织</option>
							<option value="9200">农民专业合作社分支机构</option>
							<option value="9900">其他</option>
							<option value="9999">个体</option>
					</select></td>

					<td class="td1">产业:</td>
					<td class="td2"><select style="display:none" id="indus_p1" multiple name="dept">
							<option value="1">第一产业</option>
							<option value="2">第二产业</option>
							<option value="3">第三产业</option>
					</select></td>
					<td class="td1">二级产业:</td>
					<td class="td2"><select style="display:none" id="indus_p2" name="dept" multiple>
							<option value="A" p="1">农、林、牧、渔业</option>
							<option value="B" p="2">采矿业</option>
							<option value="C" p="2">制造业</option>
							<option value="D" p="2">电力、燃气及水的生产和供应业</option>
							<option value="E" p="2">建筑业</option>
							<option value="F" p="3">交通运输、仓储和邮政业</option>
							<option value="G" p="3">信息传输、计算机服务软件业</option>
							<option value="H" p="3">批发和零售业</option>
							<option value="I" p="3">住宿和餐饮业</option>
							<option value="J" p="3">金融业</option>
							<option value="K" p="3">房地产业</option>
							<option value="L" p="3">租赁和商务服务业</option>
							<option value="M" p="3">科学研究、技术服务和地质勘查业</option>
							<option value="N" p="3">水利、环境和公共设施管理业</option>
							<option value="O" p="3">居民服务和其他服务业</option>
							<option value="P" p="3">教育</option>
							<option value="Q" p="3">卫生、社会保障和社会福利业</option>
							<option value="R" p="3">文化、体育和娱乐业</option>
							<option value="S" p="3">公共管理和社会组织</option>
							<option value="T" p="3">国际组织</option>
					</select></td>
					<td class="td1">三级产业</td>
					<td class="td2"><select style="display:none" id="indus" name="dept" multiple>
					</select></td>

				</tr>


				<tr class="box_bgc_ef">
					<td class="td1">经济性质:</td>
					<td class="td2"><select style="display:none" id="econ_p" name="dept" multiple>
							<option value="1">内资</option>
							<option value="2">私营</option>
							<option value="3">外企</option>
					</select></td>
					<td class="td1">二级经济性质:</td>
					<td class="td2"><select id="econ"style="display:none"  name="dept" multiple>
							<option value="1" p="1">内资(非私营)</option>
							<option value="11" p="1">国有</option>
							<option value="12" p="1">集体</option>
							<option value="19" p="1">内资(未知)</option>
							<option value="2" p="2">私营</option>
							<option value="3" p="3">外资</option>
					</select></td>

					<td class="td1">组织形式:</td>
					<td class="td2"><select style="display:none" id="organ_p" name="dept" multiple>
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
					</select></td>
					<td class="td1">二级组织形式:</td>
					<td class="td2"><select style="display:none" id="organ" name="dept" multiple>
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
					</select></td>

				</tr>


				<tr class="box_bgc_fb">
					<td class="td1">资金规模:</td>
					<td class="td2"><select style="display:none" id="dom" name="dept" multiple>
							<option value="001001" p="001">期末实有企业注册资本（金）100万以下</option>
							<option value="001002" p="001">期末实有企业注册资本（金）100-1000万</option>
							<option value="001003" p="001">期末实有企业注册资本（金）1000万-1亿元</option>
							<option value="001004" p="001">期末实有企业注册资本（金）1亿元以上</option>
							<option value="002001" p="002">期末实有注册资本（金）100~500万元</option>
							<option value="002002" p="002">期末实有注册资本（金）500~1000万</option>
							<option value="002003" p="002">期末实有注册资本（金）1000万-1亿元</option>
							<option value="002004" p="002">期末实有注册资本（金）1亿元以上</option>
							<option value="003001" p="003">出资总额100~500万元</option>
							<option value="003002" p="003">出资总额500-1000万</option>
							<option value="003003" p="003">出资总额1000万-1亿元</option>
							<option value="003004" p="003">出资总额1亿元以上</option>
					</select></td>
					<td class="td1" colspan="6"></td>

				<tr>
				<td class="td1 _first" colspan="8" style='text-align:center;padding-top:4px;background: #f8fbfd;border: 1px solid #E4E4E4;border-left:none;border-right:none;'>
				<input id="selData" class="btn_submit box-btn"  type="button" value="" onclick="initCharts();" /> 
				<input id="selData" class="btn_reset box-btn"  type="button" value="" onclick="initCharts();" /> 
				<!-- <input id="selData" class="bt _first"  style="background-color: #41A6E0;color: white;"  type="button" value="提交" onclick="initCharts();" />  -->
				<input class="btu" style="display: none" type="radio" id="dateType" name="gender" id="x" value="0" checked="checked">
				<td class="td1 _second" style="display:none;text-align:center;" colspan="8" style='text-align:center;'>
				<input id="back" style="background-color: #41A6E0;color: white;"class="bt _second" type="button" value="提交" onclick="initSecondCharts();" /> 
				&nbsp;&nbsp;
				<input id="selData2"  style="background-color: #41A6E0;color: white;" class="bt _second" type="button" value="返回" onclick="showFirst();" /> 
				</td>
				
				</tr>
				</td> 

				</tr>

			</table>
  	</div>
<div id="Content">
       	<div id="container_fir" class="_first" style="width: 98%;"> 
			<div id="container"></div>
		</div>

		<div id="container_sec"  class="_second"  style="width: 98%;display:none">
			 <div id="container2" style=""></div>
		</div>
    </div>

</body>
<script type="text/javascript">
function c(name){
	if(name == '第一产业')
		return '1';
	if(name == '第二产业')
		return '2';
	if(name == '第三产业')
		return '3';
	return '0';
}
//第二层数据
function initSecondCharts(regorgCode, regorgName){
	//$("select").multipleSelect("setSelects", [13, 14]);//设置选择的值
	showSecond();
	//一天的数据，开始=结束
	var showendtime=$("#showendtime").val();
	var showstarttime=showendtime;
	var dateType = '0';//时间类型,一天
	var measureCode = ($("#dataType").val() == '1' ? "001001,002001" : "001007,001008"); //1为主体概况,2为注吊销
	var isBG = '0'; //非变更表
	var requestData = {startTime:showstarttime, endTime: showendtime, dateType: dateType,
			           measure: measureCode, isBG: isBG, isBoth:'1'};
	getMultSelectedValue(requestData,['indus_p1','indus_p2','indus'],['indus_p1','indus_p2','indus'], 0, null);//行业
	getMultSelectedValue(requestData,['ent'],['ent'], 0, null);//企业
	getMultSelectedValue(requestData,['econ_p','econ'],['econ_p','econ'], 0, null);//经济性质
	getMultSelectedValue(requestData,['organ_p','organ'],['organ_p','organ'],0,null);//组织形式
	getMultSelectedValue(requestData,['dom_p','dom'],['dom_p','dom'],0,null);//资金规模
	if(regorgCode == null){ //区域地市
		getMultSelectedValue(requestData,['regorg_p','regorg'],['regorg_p','regorg'], 0, null);
	}else{
		requestData.regorg = regorgCode;
		//更新下拉框值（未实现）
		//updateSelect('regorg',regorgCode);
	}
	//根据产业选择设置自变量
	if(requestData.indus){
		requestData.out = 'indus';
	}else if(requestData.indus_p2){
		requestData.out = 'indus_p2';
	}else{
		requestData.out = 'indus_p1';
	}
	var titleNames = $("#dataType").val() == '1' ? 
			["期末实有户数","期末实有注册资本(金)(万元)"]:["累计注销户数","累计吊销户数"];
	var isLogout = $("#dataType").val() == '1' ? false : true;
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/anaAllPageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:requestData,
		success : function(data_all) {
			if(data_all != null){
				var x = new Array();//各个地级市
				var s = new Array(); //户数
				var s1 = new Array();//资本金
				var sumS = 0;//户数总和
				var sumS1 = 0;//资本金综合 || 吊销综合
				var t;
				var _temp = new Array();
				//产业排序
				data_all.sort(function(a, b){
					return c(a.c0) - c(b.c0);
				});
				for(var i = 0; i < data_all.length; i ++) {
					t = data_all[i];
					t.s = t.s == null ? 0 : t.s;
					t.s1 = t.s1 == null ? 0 : t.s1;
					x.push(t.c0);
					s.push(t.s);
					s1.push(t.s1); 
					sumS += t.s;
					sumS1 = add( sumS1 ,t.s1);
				}
				
				$('#container2').highcharts({
			        chart: {zoomType: 'xy'},
			        title: {
			            text: (regorgName ? regorgName + " - " : "") +  ($("#dataType").val() == '1' ? "期末实有" : "期末注吊销")
			        },
			        subtitle: {
			            text: titleNames[0]+ "-总数: " + sumS + " "+titleNames[1]+"-总数: " + sumS1
			        },
			        xAxis: [{
			            categories: x,
			            crosshair: true
			        }],
			        yAxis: [{ title: {text: isLogout ? '' : titleNames[1],style: {color: Highcharts.getOptions().colors[1]}},
			            labels: {format: (isLogout ?  '{value} ': '{value} 万元'),style: {color: Highcharts.getOptions().colors[1]}},
			            opposite: true
			        },{ // 户数
			            labels: {format: '{value} ',style: {color: Highcharts.getOptions().colors[0] }},
			            title: {text: isLogout ? '户数' : titleNames[0],style: {color: Highcharts.getOptions().colors[0]}}
			        }],
			        tooltip: {shared: true},
			        legend: {
			        	title:{text:$("#dataType").val() == '1' ? '期末实有:' : '累计:'},
			        	labelFormatter:function(){
			                //return this.name.replace('期末实有','').replace('累计','');
			        		return this.name.replace('期末实有','').replace('累计','').replace('(金)(万元)','<br>(金)(万元)');
			            },
			        	layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -0,y: -0,
			            borderWidth: 1,
			            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
			        },
			        series: [{
			            name: titleNames[0],
			            type: 'column',
			            yAxis: isLogout ? 0 : 1,
			            data: s,
			            tooltip: {
			                valueSuffix: ' 户'
			            }

			        }, {
			            name: titleNames[1],
			            type: isLogout ? 'column' : 'spline',
			            data: s1,
			            tooltip: {
			                valueSuffix: isLogout ? '户' : ' 万元'
			            }
			        }]
			    });
			}
		}
	});
}

//第一层数据
function initCharts(){
	//一天的数据，开始=结束
	var showendtime=$("#showendtime").val();
	var showstarttime=showendtime;
	var dateType = '0';//时间类型,一天
	var measureCode = ($("#dataType").val() == '1' ? "001001,002001" : "001007,001008"); //1为主体概况,2为注吊销
	var isBG = '0'; //非变更表
	//此页面默认21个地市
	var requestData = {startTime:showstarttime, endTime: showendtime, dateType: dateType,
			           measure: measureCode, isBG: isBG, isBoth:'1',};
	getMultSelectedValue(requestData,['indus_p1','indus_p2','indus'],['indus_p1','indus_p2','indus'], 0, null);//行业
	getMultSelectedValue(requestData,['ent'],['ent'], 0, null);//企业
	getMultSelectedValue(requestData,['econ_p','econ'],['econ_p','econ'], 0, null);//经济性质
	getMultSelectedValue(requestData,['organ_p','organ'],['organ_p','organ'],0,null);//组织形式
	getMultSelectedValue(requestData,['dom_p','dom'],['dom_p','dom'],0,null);//资金规模
	getMultSelectedValue(requestData,['regorg_p','regorg'],['regorg_p','regorg'],0,null);//区域
	requestData.out = 'regorg'; 
	//console.log(requestData);
	//var dataChangeNames = ["期末实有户数","期末实有注册资本(金)(万元)","本期注吊销户数","本期注销注册资本(金)(万元)"];
	var titleNames = $("#dataType").val() == '1' ? 
			["期末实有户数","期末实有注册资本(金)(万元)"]:["累计注销户数","累计吊销户数"];
	var isLogout = $("#dataType").val() == '1' ? false : true;
	$.ajax({
		url: '<%=request.getContextPath()%>/analysis/anaAllPageInfo.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		data:requestData,
		success : function(data_all) {
			if(data_all != null){
				var x = new Array();//各个地级市
				var s = new Array(); //户数
				var s1 = new Array();//资本金
				var sumS = 0;//户数总和
				var sumS1 = 0;//资本金综合
				var t;
				//根据户数排序
				data_all.sort(function(a,b){
					return  (b.s == null ? 0:b.s) - (a.s==null ? 0:a.s);
				});
				for(var i = 0; i < data_all.length; i ++) {
					t = data_all[i];
					t.s = t.s == null ? 0 : t.s;
					t.s1 = t.s1 == null ? 0 : t.s1;
					x.push(t.c0);
					s.push(t.s);
					s1.push(t.s1); 
					sumS += t.s;
					sumS1 = add( sumS1 ,t.s1);
				}
				$('#container').highcharts({
			        chart: {
			            zoomType: 'xy'
			        },
			        title: {
			            text: $("#dataType").val() == '1' ? "期末实有" : "期末注吊销"
			        },
			        subtitle: {
			            text: "总" + titleNames[0] + ": " + sumS + "   总" +titleNames[1]+ ": " + sumS1
			        },
			        credits:{
			        	enabled:false,
			        	text:'主体概况',
			        	href:''
			        },
			        xAxis: [{
			            categories: x,
			            crosshair: true
			        }],
			        yAxis: [{ title: {text: isLogout ? '' : titleNames[1],style: {color: Highcharts.getOptions().colors[1]}},
			            labels: {format: isLogout ? '{value} ' : '{value} 万元',style: {color: Highcharts.getOptions().colors[1]}},
			            opposite: true
			        },{ // 户数
			            labels: {format: '{value} ',style: {color: Highcharts.getOptions().colors[0] }},
			            title: {text:  isLogout ? '户数' : titleNames[0],style: {color: Highcharts.getOptions().colors[0]}}
			        } ],
			        plotOptions: {
			            series: {
			                cursor: 'pointer',
			                events: {
			                    click: function (e) {
			                        //alert(getAreaCode(e.point.category));
			                        var areaCode = getAreaCode(e.point.category);
			                        initSecondCharts(areaCode, e.point.category);
			                    }
			                }
			            }
			        },
			        tooltip: {shared: true},
			        legend: {
			        	title:{text:$("#dataType").val() == '1' ? '期末实有:' : '累计:'},
			        	labelFormatter:function(){
			                //return this.name.replace('期末实有','').replace('累计','');
			        		return this.name.replace('期末实有','').replace('累计','').replace('(金)(万元)','<br>(金)(万元)');
			            },
			        	layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -0,y: 0,
			            borderWidth: 1,
			            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
			        },
			        series: [{
			            name: titleNames[0],
			            type: 'column',
			            yAxis: isLogout ? 0 : 1,
			            data: s,
			            tooltip: {
			                valueSuffix: ' 户'
			            }

			        }, {
			            name: titleNames[1],
			            type: isLogout ? 'column' : 'spline',
			            data: s1,
			            tooltip: {
			                valueSuffix: isLogout ? ' 户' : ' 万元'
			            }
			        }]
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


$(function(){
	$("#ent").siblings().find("button").css("background","#fbfdfd");
	$("#indus_p1").siblings().find("button").css("background","#fbfdfd");
	$("#indus_p2").siblings().find("button").css("background","#fbfdfd");
	$("#indus").siblings().find("button").css("background","#fbfdfd");
	$("#dom").siblings().find("button").css("background","#fbfdfd");
});
</script>
</html>
