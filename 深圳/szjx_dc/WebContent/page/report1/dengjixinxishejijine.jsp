<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	$(document).ready(function getcode(){
		/* $('#yeWuLeiBie').comboxfield('addOption', "全部","");
		$('#yeWuLeiBie').comboxfield('addOption', "工商","CH20");
		$('#yeWuLeiBie').comboxfield('addOption', "消委会","ZH18");
		$('#yeWuLeiBie').comboxfield('addOption', "知识产权","ZH19");
		$('#yeWuLeiBie').comboxfield('addOption', "价格检查","ZH20");
		$('#yeWuLeiBie').comboxfield('addOption', "质量监督","ZH21"); */
		
	/* 	$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/getRegCode.do",
			data : "begintime=" + begintime + "&endtime=" + endtime+"&regcode="+regcode
					+ "&timess=" + new Date() + "&id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var  i = 0; i < dataObj.length; i++) {
					$('#regcode').comboxfield('addOption', dataObj[i].name, dataObj[i].regdepcode);
				}
				}
		}); */
	});
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		
		var shejiketi = aa.data.shejiketi;
		var infotype = aa.data.infotype;
		var laiyuanfangshi = aa.data.laiyuanfangshi;
		var jieshoufangshi = aa.data.jieshoufangshi;
		var shijianjibie = aa.data.shijianjibie;
		var renyuanshenfen = aa.data.renyuanshenfen;
		var shejizhuti = aa.data.shejizhuti;
		var hangyeleibie = aa.data.hangyeleibie;
		var wangzhanleixing = aa.data.wangzhanleixing;
		var qiyeleixing = aa.data.qiyeleixing;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期");
				return;
			}
			if (begintime == endtime) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
				+ '\'' + begintime + '\'' + ',' 
				+ '\'' + endtime + '\'' + ',' 
				+ '\'' + shejiketi + '\'' +','
				+ '\'' + infotype + '\'' +','
				+ '\'' + laiyuanfangshi + '\'' +','
				+ '\'' + jieshoufangshi + '\'' +','
				+ '\'' + shijianjibie + '\'' +','
				+ '\'' + renyuanshenfen + '\'' +','
				+ '\'' + shejizhuti + '\'' +','
				+ '\'' + hangyeleibie + '\'' +','
				+ '\'' + wangzhanleixing + '\'' +','
				+ '\'' + qiyeleixing + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+ 
				"<div id=\"登记信息涉及金额统计表_22237\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=793 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:594pt'>\n" + 
				" <col width=363 style='mso-width-source:userset;mso-width-alt:11616;width:272pt'>\n" + 
				" <col width=143 span=2 style='mso-width-source:userset;mso-width-alt:4576;\n" + 
				" width:107pt'>\n" + 
				" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
				" <tr height=47 style='mso-height-source:userset;height:35.25pt'>\n" + 
				"  <td colspan=4 height=47 class=xl6622237 width=793 style='height:35.25pt;\n" + 
				"  width:594pt'>登记信息涉及金额统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6322237 style='height:14.25pt;border-top:none'>涉及客体类型</td>\n" + 
				"  <td class=xl6322237 style='border-top:none;border-left:none'>涉及金额</td>\n" + 
				"  <td class=xl6322237 style='border-top:none;border-left:none'>案值</td>\n" + 
				"  <td class=xl6322237 style='border-top:none;border-left:none'>经济损失</td>\n" + 
				" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/dengJiXinXiSheJiJinE.do",
			/* 			data : "begintime=" + begintime + "&endtime=" + endtime + "&infotype=" + infotype + "&yeWuLeiBie=" + yeWuLeiBie + "&timess=" + new Date() + "&id=" + Math.random(),
			 */data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"shejiketi" : shejiketi,
				"infotype" : infotype,
				"laiyuanfangshi" : laiyuanfangshi,
				"jieshoufangshi" : jieshoufangshi,
				"shijianjibie" : shijianjibie,
				"renyuanshenfen" : renyuanshenfen,
				"shejizhuti" : shejizhuti,
				"hangyeleibie" : hangyeleibie,
				"wangzhanleixing" : wangzhanleixing,
				"qiyeleixing" : qiyeleixing
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var i = 0, t = dataObj.length; i < t; i++) {
					for (var j = 0, x = dataObj[i].length; j < x; j++) {

						htmls += 
							"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6422237 width=363 style='height:14.25pt;border-top:\n" + 
							" none;width:272pt'>" + dataObj[i][j].name + "</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none'>" + dataObj[i][j].invoam + "</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none'>" + dataObj[i][j].caseval + "</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none'>" + dataObj[i][j].ecoloval + "</td>\n" + 
							"</tr>";

					}

					if (i + 1 < t) {
						htmls +=
							"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6422237 width=363 style='height:14.25pt;border-top:\n" + 
							" none;width:272pt;border-right:none;border-left:none'>　</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
							" <td class=xl6522237 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
							"</tr>";
						}
				}
				/* for (var i = 0, t = dataObj.length; i < t; i++) {
					for (var j = 0, x = dataObj[i].length; j < x; j++) {

						htmls += "<tr height=19 style='height:14.25pt'>\n" + " <td height=19 class=xl647528 style='height:14.25pt;border-top:none'>" + dataObj[i][j].name + "</td>\n" + " <td class=xl657528 style='border-top:none;border-left:none'>" + 0 + "</td>\n" + "</tr>";
					}

					if (i + 1 < t) {
						htmls += "<tr height=19 style='height:18pt'>\n" + " <td class=xl657528 style='height:18pt;border-top:none;border-right:none;border-left:none'></td>\n" + " <td class=xl657528 style='border-top:none;border-left:none;border-right:none'></td>\n" + "</tr>";
					}
				} */
				htmls += '</table>' + '</div>';
			}
		});
		var newWim = open("dengjixinxishejijine_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 500);

	}
</script>

</head>
<body >
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="登记信息涉及金额统计表  统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='shejiketi' id="shejiketi" vtype="comboxfield"
			multiple="false" label="涉及客体类别" labelAlign="right"
			dataurl='[{"text":"工商","value":"CH20"},{"text":"消委会","value":"ZH18"},
					{"text":"知识产权","value":"ZH19"},{"text":"价格检查","value":"ZH20"},{"text":"质量监督","value":"ZH21"}]'
			labelwidth='100px' width="410"></div>
		<div name='infotype' id="infotype" vtype="comboxfield"
			multiple="false" label="信息件类型" labelAlign="right"
			dataurl='[{"text":"行政监察件","value":"8"},{"text":"市场监管投诉","value":"1"},{"text":"举报","value":"2"},{"text":"咨询","value":"3"},{"text":"建议","value":"4"},{"text":"消委会投诉","value":"6"},{"text":"其他","value":"9"}]'
			labelwidth='100px' width="410"></div>
		<div name='laiyuanfangshi' id="laiyuanfangshi" vtype="comboxfield"
			multiple="false" label="来源方式" labelAlign="right"
			dataurl='[{"text":"直接到咨询举报申诉中心","value":"01"},{"text":"维权联络点","value":"02"},{"text":"消协","value":"03"},{"text":"协作单位","value":"04"},{"text":"销售企业","value":"05"},{"text":"服务企业","value":"06"},{"text":"生产企业","value":"07"},{"text":"工商部门","value":"08"},{"text":"人大","value":"09"},{"text":"政协","value":"10"},{"text":"其他行政部门","value":"11"},{"text":"其他","value":"99"}]'
			labelwidth='100px' width="410"></div>
		<div name='jieshoufangshi' id="jieshoufangshi" vtype="comboxfield"
			multiple="false" label="接收方式" labelAlign="right"
			dataurl='[{"text":"电话(语音)","value":"1"},{"text":"短信","value":"2"},{"text":"来人","value":"3"},{"text":"来函","value":"4"},{"text":"传真","value":"5"},{"text":"互联网络","value":"6"},{"text":"留言","value":"7"},{"text":"市府12345转件","value":"8"},{"text":"其他","value":"9"},{"text":"政府在线","value":"a"},{"text":"电子邮件","value":"b"},{"text":"直通车","value":"c"},{"text":"来信","value":"d"},{"text":"上级部门交办","value":"e"},{"text":"民心桥","value":"f"},{"text":"其它部门转办","value":"g"},{"text":"三打两建办","value":"h"},{"text":"局长邮箱","value":"i"},{"text":"异地消费申诉转办件","value":"j"},{"text":"QQ服务","value":"k"},{"text":"12358全国价格举报平台来件","value":"l"},{"text":"微信平台","value":"m"},{"text":"315消费通","value":"n"},{"text":"消委会转件","value":"o"},{"text":"全国12315平台转件","value":"p"}]'
			labelwidth='100px' width="410"></div>
		<div name='shijianjibie' id="shijianjibie" vtype="comboxfield"
			multiple="false" label="事件级别" labelAlign="right"
			dataurl='[{"text":"特急","value":"1"},
						{"text":"紧急","value":"2"},
						{"text":"一般","value":"3"}]'
			labelwidth='100px' width="410"></div>
		<div name='renyuanshenfen' id="renyuanshenfen" vtype="comboxfield"
			multiple="false" label="人员身份" labelAlign="right"
			dataurl='[{"text":"城镇","value":"1"},
						{"text":"农村","value":"2"},
						{"text":"港澳台同胞","value":"3"},
						{"text":"外藉","value":"4"},
						{"text":"军人","value":"5"},
						{"text":"其他","value":"9"}]'
			labelwidth='100px' width="410"></div>
		<div name='shejizhuti' id="shejizhuti" vtype="comboxfield"
			multiple="false" label="涉及主体类型" labelAlign="right"
			dataurl='[{"text":"企业","value":"10"},{"text":"生产企业","value":"11"},{"text":"销售企业","value":"12"},{"text":"服务企业","value":"13"},{"text":"其他企业","value":"19"},{"text":"个体工商户","value":"20"},{"text":"自然人","value":"30"},{"text":"其他","value":"90"}]'
			labelwidth='100px' width="410"></div>
		<div name='hangyeleibie' id="hangyeleibie" vtype="comboxfield"
			multiple="false" label="行业类别" labelAlign="right"
			dataurl='[{"text":"生产","value":"200"},
					{"text":"服务","value":"300"},
					{"text":"仓储","value":"400"},
					{"text":"运输","value":"500"},
					{"text":"其他","value":"900"},
					{"text":"销售","value":"100"},
					{"text":"销售-超市","value":"101"},
					{"text":"销售-食杂店","value":"102"},
					{"text":"销售-百货店","value":"103"},
					{"text":"销售-购物中心","value":"104"},
					{"text":"销售-集贸市场","value":"105"},
					{"text":"销售-专业市场","value":"106"},
					{"text":"销售-专卖店","value":"107"},
					{"text":"销售-电视购物","value":"108"},
					{"text":"销售-邮购","value":"109"},
					{"text":"销售-网上商店","value":"110"},
					{"text":"销售-小商铺","value":"111"},
					{"text":"销售-直销店","value":"112"},
					{"text":"销售-其他销售","value":"199"}]'
			labelwidth='100px' width="410"></div>
			<div name='wangzhanleixing' id="wangzhanleixing" vtype="comboxfield"
			multiple="false" label="网站类型" labelAlign="right"
			dataurl='[{"text":"交易平台类","value":"01"},{"text":"应用类","value":"02"},{"text":"服务类","value":"03"},{"text":"互联网门户","value":"04"},{"text":"其他","value":"05"}]'
			labelwidth='100px' width="410"></div>
			<div name='qiyeleixing' id="quyeleixing" vtype="comboxfield"
			multiple="false" label="企业类型" labelAlign="right"
			dataurl='[{"text":"内资公司","value":"1000"},
						{"text":"　有限责任公司","value":"1100"},
						{"text":"　　有限责任公司(国有独资)","value":"1110"},
						{"text":"　　有限责任公司(外商投资企业投资)","value":"1120"},
						{"text":"　　　有限责任公司(外商投资企业合资)","value":"1121"},
						{"text":"　　　有限责任公司(外商投资企业与内资合资)","value":"1122"},
						{"text":"　　　有限责任公司(外商投资企业法人独资)","value":"1123"},
						{"text":"　　有限责任公司(自然人投资或控股)","value":"1130"},
						{"text":"　　有限责任公司(国有控股)","value":"1140"},
						{"text":"　　一人有限责任公司","value":"1150"},
						{"text":"　　　有限责任公司(自然人独资)","value":"1151"},
						{"text":"　　　有限责任公司（自然人投资或控股的法人独资）","value":"1152"},
						{"text":"　　　有限责任公司（非自然人投资或控股的法人独资）","value":"1153"},
						{"text":"　　其他有限责任公司","value":"1190"},
						{"text":"　股份有限公司","value":"1200"},
						{"text":"　　股份有限公司(上市)","value":"1210"},
						{"text":"　　　股份有限公司(上市、外商投资企业投资)","value":"1211"},
						{"text":"　　　股份有限公司(上市、自然人投资或控股)","value":"1212"},
						{"text":"　　　股份有限公司(上市、国有控股)","value":"1213"},
						{"text":"　　　其他股份有限公司(上市)","value":"1219"},
						{"text":"　　股份有限公司(非上市)","value":"1220"},
						{"text":"　　　股份有限公司(非上市、外商投资企业投资)","value":"1221"},
						{"text":"　　　股份有限公司(非上市、自然人投资或控股)","value":"1222"},
						{"text":"　　　股份有限公司(非上市、国有控股)","value":"1223"},
						{"text":"　　　其他股份有限公司(非上市)","value":"1229"},
						{"text":"内资分公司","value":"2000"},
						{"text":"　有限责任公司分公司","value":"2100"},
						{"text":"　　有限责任公司分公司(国有独资)","value":"2110"},
						{"text":"　　有限责任公司分公司(外商投资企业投资)","value":"2120"},
						{"text":"　　　有限责任公司分公司(外商投资企业合资)","value":"2121"},
						{"text":"　　　有限责任公司分公司(外商投资企业与内资合资)","value":"2122"},
						{"text":"　　　有限责任公司分公司(外商投资企业法人独资)","value":"2123"},
						{"text":"　　有限责任公司分公司(自然人投资或控股)","value":"2130"},
						{"text":"　　有限责任公司分公司(国有控股)","value":"2140"},
						{"text":"　　一人有限责任公司分公司","value":"2150"},
						{"text":"　　　有限责任公司分公司(自然人独资)","value":"2151"},
						{"text":"　　　有限责任公司分公司(自然人投资或控股的法人独资)","value":"2152"},
						{"text":"　　　有限责任公司分公司（非自然人投资或控股的法人独资）","value":"2153"},
						{"text":"　　其他有限责任公司分公司","value":"2190"},
						{"text":"　股份有限公司分公司","value":"2200"},
						{"text":"　　股份有限公司分公司(上市)","value":"2210"},
						{"text":"　　　股份有限公司分公司(上市、外商投资企业投资)","value":"2211"},
						{"text":"　　　股份有限公司分公司(上市、自然人投资或控股)","value":"2212"},
						{"text":"　　　股份有限公司分公司(上市、国有控股)","value":"2213"},
						{"text":"　　　其他股份有限公司分公司(上市)","value":"2219"},
						{"text":"　　股份有限公司分公司(非上市)","value":"2220"},
						{"text":"　　　股份有限公司分公司(非上市、外商投资企业投资)","value":"2221"},
						{"text":"　　　股份有限公司分公司(非上市、自然人投资或控股)","value":"2222"},
						{"text":"　　　股份有限公司分公司(国有控股)","value":"2223"},
						{"text":"　　　其他股份有限公司分公司(非上市)","value":"2229"},
						{"text":"内资企业法人","value":"3000"},
						{"text":"　全民所有制","value":"3100"},
						{"text":"　集体所有制","value":"3200"},
						{"text":"　股份制","value":"3300"},
						{"text":"　股份合作制","value":"3400"},
						{"text":"　联营","value":"3500"},
						{"text":"内资非法人企业、非公司私营企业及内资非公司企业分支机构","value":"4000"},
						{"text":"　事业单位营业","value":"4100"},
						{"text":"　　国有事业单位营业","value":"4110"},
						{"text":"　　集体事业单位营业","value":"4120"},
						{"text":"　社团法人营业","value":"4200"},
						{"text":"　　国有社团法人营业","value":"4210"},
						{"text":"　　集体社团法人营业","value":"4220"},
						{"text":"　内资企业法人分支机构(非法人)","value":"4300"},
						{"text":"　　全民所有制分支机构(非法人)","value":"4310"},
						{"text":"　　集体分支机构(非法人)","value":"4320"},
						{"text":"　　股份制分支机构","value":"4330"},
						{"text":"　　股份合作制分支机构","value":"4340"},
						{"text":"　经营单位(非法人)","value":"4400"},
						{"text":"　　国有经营单位(非法人)","value":"4410"},
						{"text":"　　集体经营单位(非法人)","value":"4420"},
						{"text":"　非公司私营企业","value":"4500"},
						{"text":"　　合伙企业","value":"4530"},
						{"text":"　　　普通合伙企业","value":"4531"},
						{"text":"　　　特殊普通合伙企业","value":"4532"},
						{"text":"　　　有限合伙企业","value":"4533"},
						{"text":"　　个人独资企业","value":"4540"},
						{"text":"　　合伙企业分支机构","value":"4550"},
						{"text":"　　　普通合伙企业分支机构","value":"4551"},
						{"text":"　　　特殊普通合伙企业分支机构","value":"4552"},
						{"text":"　　　有限合伙企业分支机构","value":"4553"},
						{"text":"　　个人独资企业分支机构","value":"4560"},
						{"text":"　联营","value":"4600"},
						{"text":"　股份制企业(非法人)","value":"4700"},
						{"text":"外商投资企业","value":"5000"},
						{"text":"　有限责任公司","value":"5100"},
						{"text":"　　有限责任公司(中外合资)","value":"5110"},
						{"text":"　　有限责任公司(中外合作)","value":"5120"},
						{"text":"　　有限责任公司(外商合资)","value":"5130"},
						{"text":"　　有限责任公司(外国自然人独资)","value":"5140"},
						{"text":"　　有限责任公司(外国法人独资)","value":"5150"},
						{"text":"　　有限责任公司(外国非法人经济组织独资)","value":"5160"},
						{"text":"　　其他","value":"5190"},
						{"text":"　股份有限公司","value":"5200"},
						{"text":"　　股份有限公司(中外合资、未上市)","value":"5210"},
						{"text":"　　股份有限公司(中外合资、上市)","value":"5220"},
						{"text":"　　股份有限公司(外商合资、未上市)","value":"5230"},
						{"text":"　　股份有限公司(外商合资、上市)","value":"5240"},
						{"text":"　　其他","value":"5290"},
						{"text":"　非公司","value":"5300"},
						{"text":"　　非公司外商投资企业(中外合作)","value":"5310"},
						{"text":"　　非公司外商投资企业(外商合资)","value":"5320"},
						{"text":"　　其他","value":"5390"},
						{"text":"　外商投资合伙企业","value":"5400"},
						{"text":"　　普通合伙企业","value":"5410"},
						{"text":"　　特殊普通合伙企业","value":"5420"},
						{"text":"　　有限合伙企业","value":"5430"},
						{"text":"　　其他","value":"5490"},
						{"text":"　外商投资企业分支机构","value":"5800"},
						{"text":"　　分公司","value":"5810"},
						{"text":"　　非公司外商投资企业分支机构","value":"5820"},
						{"text":"　　办事处","value":"5830"},
						{"text":"　　外商投资合伙企业分支机构","value":"5840"},
						{"text":"　　其他","value":"5890"},
						{"text":"台、港、澳投资企业","value":"6000"},
						{"text":"　有限责任公司","value":"6100"},
						{"text":"　　有限责任公司(台港澳与境内合资)","value":"6110"},
						{"text":"　　有限责任公司(台港澳与境内合作)","value":"6120"},
						{"text":"　　有限责任公司(台港澳合资)","value":"6130"},
						{"text":"　　有限责任公司(台港澳自然人独资)","value":"6140"},
						{"text":"　　有限责任公司(台港澳法人独资)","value":"6150"},
						{"text":"　　有限责任公司(台港澳非法人经济组织独资)","value":"6160"},
						{"text":"　　有限责任公司(台港澳与外国投资者合资)","value":"6170"},
						{"text":"　　其他","value":"6190"},
						{"text":"　股份有限公司","value":"6200"},
						{"text":"　　股份有限公司(台港澳与境内合资、未上市)","value":"6210"},
						{"text":"　　股份有限公司(台港澳与境内合资、上市)","value":"6220"},
						{"text":"　　股份有限公司(台港澳合资、未上市)","value":"6230"},
						{"text":"　　股份有限公司(台港澳合资、上市)","value":"6240"},
						{"text":"　　股份有限公司(台港澳与外国投资者合资、未上市)","value":"6250"},
						{"text":"　　股份有限公司(台港澳与外国投资者合资、上市)","value":"6260"},
						{"text":"　　其他","value":"6290"},
						{"text":"　非公司","value":"6300"},
						{"text":"　　非公司台、港、澳企业(台港澳与境内合作)","value":"6310"},
						{"text":"　　非公司台、港、澳企业(台港澳合资)","value":"6320"},
						{"text":"　　其他","value":"6390"},
						{"text":"　港、澳、台投资合伙企业","value":"6400"},
						{"text":"　　普通合伙企业","value":"6410"},
						{"text":"　　特殊普通合伙企业","value":"6420"},
						{"text":"　　有限合伙企业","value":"6430"},
						{"text":"　　其他","value":"6490"},
						{"text":"　台、港、澳投资企业分支机构","value":"6800"},
						{"text":"　　分公司","value":"6810"},
						{"text":"　　非公司台、港、澳投资企业分支机构","value":"6820"},
						{"text":"　　办事处","value":"6830"},
						{"text":"　　港、澳、台投资合伙企业分支机构","value":"6840"},
						{"text":"　　其他","value":"6890"},
						{"text":"外国（地区）企业","value":"7000"},
						{"text":"　外国（地区）公司分支机构","value":"7100"},
						{"text":"　　外国(地区)无限责任公司分支机构","value":"7110"},
						{"text":"　　外国(地区)有限责任公司分支机构","value":"7120"},
						{"text":"　　外国(地区)股份有限责任公司分支机构","value":"7130"},
						{"text":"　　外国(地区)其他形式公司分支机构","value":"7190"},
						{"text":"　外国企业常驻代表机构","value":"7200"},
						{"text":"　外国(地区)企业在中国境内从事生产经营活动","value":"7300"},
						{"text":"　　分公司","value":"7310"},
						{"text":"　　其他","value":"7390"},
						{"text":"集团","value":"8000"},
						{"text":"　内资集团","value":"8100"},
						{"text":"　外资集团","value":"8500"},
						{"text":"其他类型","value":"9000"},
						{"text":"　农民专业合作社","value":"9100"},
						{"text":"　农民专业合作社分支机构","value":"9200"},
						{"text":"　个体工商户","value":"9500"},
						{"text":"　自然人","value":"9600"},
						{"text":"　其他","value":"9900"}]' labelwidth='100px' width="410"></div>
						
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>