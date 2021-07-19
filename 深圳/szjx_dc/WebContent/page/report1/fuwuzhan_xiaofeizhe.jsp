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
		
		var stationtype = aa.data.stationtype;
		var regno = aa.data.regno;
		var status = aa.data.status;
		/* if (begintime.length != 0 && endtime.length != 0) {
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
		} */
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
				+ '\'' + begintime + '\'' + ',' 
				+ '\'' + endtime + '\'' + ',' 
				+ '\'' + stationtype + '\'' +','
				+ '\'' + regno + '\'' +','
				+ '\'' + status + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+ "<div id=\"消费者权益服务站统计表_2978\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1081 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:811pt'>\n" + 
				" <col width=289 style='mso-width-source:userset;mso-width-alt:9248;width:217pt'>\n" + 
				" <col width=72 span=11 style='width:54pt'>\n" + 
				" <tr height=50 style='mso-height-source:userset;height:37.5pt'>\n" + 
				"  <td colspan=12 height=50 class=xl632978 width=1081 style='height:37.5pt;\n" + 
				"  width:811pt'>消费者权益服务站统计表<span style='mso-spacerun:yes'> </span></td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl642978 style='height:28.5pt;border-top:none'>服务站类别</td>\n" + 
				"  <td colspan=10 class=xl642978 style='border-left:none'>服务站数量</td>\n" + 
				"  <td rowspan=2 class=xl642978 style='border-top:none'>合计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl642978 style='height:14.25pt;border-top:none;\n" + 
				"  border-left:none'>福田</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>罗湖</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>南山</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>盐田</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>宝安</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>龙岗</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>光明</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>坪山</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>龙华</td>\n" + 
				"  <td class=xl642978 style='border-top:none;border-left:none'>大鹏</td>\n" + 
				" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/fuWuZhanXiaoFeiZhe.do",
			/* 			data : "begintime=" + begintime + "&endtime=" + endtime + "&infotype=" + infotype + "&yeWuLeiBie=" + yeWuLeiBie + "&timess=" + new Date() + "&id=" + Math.random(),
			 */data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"stationtype" : stationtype,
				"regno" : regno,
				"status" : status
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var i = 0, t = dataObj.length; i < t; i++) {

						htmls += 
							"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl652978 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].福田+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].罗湖+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].南山+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].盐田+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].宝安+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].龙岗+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].光明+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].坪山+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].龙华+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].大鹏+"</td>\n" + 
							" <td class=xl662978 style='border-top:none;border-left:none'>"+dataObj[i].合计+"</td>\n" + 
							"</tr>";

					}
				htmls += '</table>' + '</div>';
			}
		});
		var newWim = open("fuwuzhan_xiaofeizhe_down.jsp", "_blank");
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
		title="消费者权益服务站统计表  统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='stationtype' id="stationtype" vtype="comboxfield"
			multiple="false" label="服务站类型" labelAlign="right"
			dataurl='[{"text":"商品流通","value":"100"},
					{"text":"　　综合型购物商场","value":"101"},
					{"text":"　　超市","value":"102"},
					{"text":"　　主营、兼营食品的商店、便利店","value":"103"},
					{"text":"　　不经营食品的商店","value":"104"},
					{"text":"　　农贸市场（含农改超）","value":"105"},
					{"text":"　　家具建材专业市场","value":"106"},
					{"text":"　　汽车及其配件专业市场","value":"107"},
					{"text":"　　通信电子产品专业市场","value":"108"},
					{"text":"　　其他专业市场","value":"109"},
					{"text":"　　销售代理","value":"110"},
					{"text":"　　其他商品流通企业","value":"130"},
					{"text":"服务行业","value":"200"},
					{"text":"　　通信","value":"201"},
					{"text":"　　餐饮","value":"202"},
					{"text":"　　洗衣","value":"203"},
					{"text":"　　装饰装修","value":"204"},
					{"text":"　　家电维修","value":"205"},
					{"text":"　　汽车维修","value":"206"},
					{"text":"　　美容美发","value":"207"},
					{"text":"　　家政服务","value":"208"},
					{"text":"　　旅游","value":"209"},
					{"text":"　　邮政快递","value":"210"},
					{"text":"　　电子商务","value":"211"},
					{"text":"　　原行政村自然村股份公司","value":"212"},
					{"text":"　　银行","value":"213"},
					{"text":"　　保险","value":"214"},
					{"text":"　　物业管理","value":"215"},
					{"text":"　　学校","value":"216"},
					{"text":"　　医院","value":"217"},
					{"text":"　　交通运输","value":"218"},
					{"text":"　　其他服务类企业","value":"230"},
					{"text":"行业协会","value":"300"},
					{"text":"　　行业协会","value":"301"},
					{"text":"生产加工","value":"400"},
					{"text":"　　生产加工","value":"401"},
					{"text":"其它","value":"900"}]'
			labelwidth='100px' width="410"></div>
		<div name='regno' id="regno" vtype="comboxfield"
			multiple="false" label="区域" labelAlign="right"
			dataurl='[{"text":"福田","value":"4000"},
					{"text":"罗湖","value":"4100"},
					{"text":"南山","value":"4200"},
					{"text":"盐田","value":"4300"},
					{"text":"宝安","value":"4400"},
					{"text":"龙岗","value":"4500"},
					{"text":"光明","value":"4600"},
					{"text":"坪山","value":"4700"},
					{"text":"龙华","value":"4800"},
					{"text":"大鹏","value":"4900"}]'
			labelwidth='100px' width="410"></div>
		<div name='status' id="status" vtype="comboxfield"
			multiple="false" label="服务站状态" labelAlign="right"
			dataurl='[{"text":"良好","value":"1"},
						{"text":"正常","value":"2"},
						{"text":"欠佳","value":"3"},
						{"text":"撤站","value":"4"}]'
			labelwidth='100px' width="410"></div>
		
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