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
<script type="text/javascript">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	function getJiDu(mouth){
		if (mouth=="01"||mouth=="02"||mouth=="03") {
			return "一";
		}else if(mouth=="04"||mouth=="05"||mouth=="06"){
			return "二";
		}else if(mouth=="07"||mouth=="08"||mouth=="09"){
			return "三";
		}else if (mouth=="10"||mouth=="11"||mouth=="12"){
			return "四";
		}else {
			return "";
		}
	}
	
	function getJiDu1(mouth){
		if (mouth=="01"||mouth=="02"||mouth=="03") {
			return "1";
		}else if(mouth=="04"||mouth=="05"||mouth=="06"){
			return "2";
		}else if(mouth=="07"||mouth=="08"||mouth=="09"){
			return "3";
		}else if (mouth=="10"||mouth=="11"||mouth=="12"){
			return "4";
		}else {
			return "";
		}
	}
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期!");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ','
				+ '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+
				"<div id=\"jichabiao01_22988\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=720 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:540pt'>\n" + 
				" <col width=72 span=4 style='width:54pt'>\n" + 
				" <col class=xl6322988 width=72 span=2 style='width:54pt'>\n" + 
				" <col width=72 span=3 style='width:54pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <tr height=25 style='height:18.75pt'>\n" + 
				"  <td colspan=10 height=25 class=xl8122988 width=720 style='border-right:.5pt solid black;\n" + 
				"  height:18.75pt;width:540pt'>"+endtime.substring(0,4)+"年第"+getJiDu(endtime.substring(5,7))+"季度投诉举报基础数据报送表</td>\n" + 
				" </tr>\n" + 
				" <tr height=18 style='height:13.5pt'>\n" + 
				"  <td height=18 class=xl8422988 colspan=8 style='height:13.5pt'>填报单位：（公章）<span\n" + 
				"  style='mso-spacerun:yes'>                    </span>"+endtime.substring(0,4)+"年<span\n" + 
				"  style='mso-spacerun:yes'>  </span>第 "+getJiDu1(endtime.substring(5,7))+" 季度<span style='mso-spacerun:yes'>      \n" + 
				"  </span>单位：件</td>\n" + 
				"  <td class=xl8522988>　</td>\n" + 
				"  <td class=xl8622988>　</td>\n" + 
				" </tr>\n" + 
				" <tr height=18 style='height:13.5pt'>\n" + 
				"  <td colspan=3 height=18 class=xl7222988 width=216 style='border-right:.5pt solid black;\n" + 
				"  height:13.5pt;width:162pt'>分类</td>\n" + 
				"  <td class=xl6422988 width=72 style='border-top:none;width:54pt'>代码</td>\n" + 
				"  <td class=xl6422988 width=72 style='border-top:none;width:54pt'>食品</td>\n" + 
				"  <td class=xl6422988 width=72 style='border-top:none;width:54pt'>保健食品</td>\n" + 
				"  <td class=xl6422988 width=72 style='border-top:none;width:54pt'>药品</td>\n" + 
				"  <td class=xl6422988 width=72 style='border-top:none;width:54pt'>化妆品</td>\n" + 
				"  <td class=xl6422988 width=72 style='border-top:none;width:54pt'>医疗器械</td>\n" + 
				"  <td class=xl6522988 width=72 style='border-top:none;width:54pt'>总计</td>\n" + 
				" </tr>\n" + 
				" <tr height=18 style='height:13.5pt'>\n" + 
				"  <td colspan=3 height=18 class=xl7222988 width=216 style='border-right:.5pt solid black;\n" + 
				"  height:13.5pt;width:162pt'>甲</td>\n" + 
				"  <td class=xl6522988 width=72 style='border-left:none;width:54pt'>乙</td>\n" + 
				"  <td class=xl6622988 width=72 style='border-left:none;width:54pt'>1</td>\n" + 
				"  <td class=xl6622988 width=72 style='border-left:none;width:54pt'>2</td>\n" + 
				"  <td class=xl6622988 width=72 style='border-left:none;width:54pt'>3</td>\n" + 
				"  <td class=xl6622988 width=72 style='border-left:none;width:54pt'>4</td>\n" + 
				"  <td class=xl7522988 width=72 style='border-left:none;width:54pt'>5</td>\n" + 
				"  <td class=xl6622988 width=72 style='border-top:none;width:54pt'>6</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/jiChaBiao01.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						htmls+=
							"<tr height=18 style='height:13.5pt'>\n" +
							"  <td rowspan=6 height=108 class=xl6722988 width=72 style='border-bottom:.5pt solid black;\n" + 
							"  height:81.0pt;border-top:none;width:54pt'>投诉举报信息接收渠道统计</td>\n" + 
							"  <td class=xl7822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>电话</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>01</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.电话.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.电话.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.电话.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.电话.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.电话.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第一部分.电话.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>网络</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>02</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.网络.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.网络.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.网络.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.网络.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.网络.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第一部分.网络.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>信件</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>03</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.信件.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.信件.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.信件.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.信件.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.信件.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第一部分.信件.医疗器械+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>走访</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>04</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.走访.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.走访.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.走访.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.走访.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.走访.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第一部分.走访.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>其他</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>05</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.其他.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.其他.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.其他.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.其他.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.其他.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第一部分.其他.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>合计</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>06</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.合计.食品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.合计.保健食品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.合计.药品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.合计.化妆品+"</td>\n" + 
							"  <td class=xl7722988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第一部分.合计.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+(dataObj.第一部分.合计.合计行/2)+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td rowspan=5 height=90 class=xl6722988 width=72 style='border-bottom:.5pt solid black;\n" + 
							"  height:67.5pt;border-top:none;width:54pt'>投诉举报信息接收类型统计</td>\n" + 
							"  <td rowspan=2 class=xl8022988 width=72 style='border-top:none;width:54pt'>投诉举报</td>\n" + 
							"  <td class=xl8022988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>受理</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>07</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报受理.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报受理.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报受理.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报受理.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报受理.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第二部分.投诉举报受理.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl6722988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>不受理</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>08</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报不受理.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报不受理.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报不受理.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报不受理.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.投诉举报不受理.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第二部分.投诉举报不受理.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-left:none;\n" + 
							"  width:54pt'>咨询</td>\n" + 
							"  <td class=xl7922988 width=72 style='width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>09</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.咨询.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.咨询.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.咨询.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.咨询.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.咨询.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第二部分.咨询.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>意见建议</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>10</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.建议.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.建议.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.建议.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.建议.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.建议.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第二部分.建议.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>合计</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>11</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.合计.食品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.合计.保健食品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.合计.药品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.合计.化妆品+"</td>\n" + 
							"  <td class=xl7722988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第二部分.合计.医疗器械+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;width:54pt'>"+(dataObj.第二部分.合计.合计行/2)+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td rowspan=6 height=108 class=xl6722988 width=72 style='border-bottom:.5pt solid black;\n" + 
							"  height:81.0pt;border-top:none;width:54pt'>受理投诉举报环节统计</td>\n" + 
							"  <td class=xl7822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>研制</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>12</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.研制.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.研制.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.研制.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.研制.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.研制.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第三部分.研制.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>生产</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>13</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.生产.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.生产.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.生产.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.生产.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.生产.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第三部分.生产.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>流通</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>14</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.流通.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.流通.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.流通.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.流通.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.流通.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第三部分.流通.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>消费</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>15</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.消费.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.消费.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.消费.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.消费.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.消费.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第三部分.消费.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>其他</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>16</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.其他.食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.其他.保健食品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.其他.药品+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.其他.化妆品+"</td>\n" + 
							"  <td class=xl7622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.其他.医疗器械+"</td>\n" + 
							"  <td class=xl6822988 width=72 style='border-top:none;width:54pt'>"+dataObj.第三部分.其他.合计行+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=18 style='height:13.5pt'>\n" + 
							"  <td height=18 class=xl7822988 width=72 style='height:13.5pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>合计</td>\n" + 
							"  <td class=xl7922988 width=72 style='border-top:none;width:54pt'>　</td>\n" + 
							"  <td class=xl6622988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>17</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.合计.食品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.合计.保健食品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.合计.药品+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.合计.化妆品+"</td>\n" + 
							"  <td class=xl7722988 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>"+dataObj.第三部分.合计.医疗器械+"</td>\n" + 
							"  <td class=xl6922988 width=72 style='border-top:none;width:54pt'>"+(dataObj.第三部分.合计.合计行/2)+"</td>\n" + 
							" </tr>";
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("jichabiao01_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 1000);		
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="稽查1表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			
		<!-- <div id="tbegintime" name='tbegintime' valuetip='默认为上年同期...' name='tbegintime' vtype="datefield"
			label="同比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="tendtime" name='tendtime' valuetip='默认为上年同期...' vtype="datefield" label="同比截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div> -->
			
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
