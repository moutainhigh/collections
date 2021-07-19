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
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		//var regs=["食药准入处","罗湖局","福田局","南山局","宝安局","龙岗局","盐田局","光明局","坪山局","龙华局","大鹏局",'深汕监管局'];
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
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+','
				+ '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+"<div id=\"食药准入_30409\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=601 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:451pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=100 span=5 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
				" width:75pt'>\n" + 
				" <tr height=27 style='height:20.25pt'>\n" + 
				"  <td colspan=6 height=27 class=xl7430409 width=601 style='border-right:.5pt solid black;\n" + 
				"  height:20.25pt;width:451pt'>食药准入"+"业务工作完成情况报表（食品）</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=2 rowspan=4 height=76 class=xl6330409 width=201 style='height:\n" + 
				"  57.0pt;width:151pt'>单位</td>\n" + 
				"  <td colspan=2 rowspan=2 class=xl6430409 width=200 style='width:150pt'>食品生产许可</td>\n" + 
				"  <td colspan=2 class=xl6930409 width=200 style='border-right:.5pt solid black;\n" + 
				"  border-left:none;width:150pt'>食品经营许可</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=2 height=19 class=xl7130409 width=200 style='border-right:.5pt solid black;\n" + 
				"  height:14.25pt;border-left:none;width:150pt'>（不含原餐饮许可数据）</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl6730409 width=100 style='border-bottom:.5pt solid black;\n" + 
				"  height:28.5pt;border-top:none;width:75pt'>受理数(件)</td>\n" + 
				"  <td rowspan=2 class=xl6730409 width=100 style='border-bottom:.5pt solid black;\n" + 
				"  border-top:none;width:75pt'>办理数(件)</td>\n" + 
				"  <td class=xl6530409 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>受理数</td>\n" + 
				"  <td class=xl6530409 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>办理数</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6530409 width=100 style='height:14.25pt;border-top:\n" + 
				"  none;border-left:none;width:75pt'>(件)</td>\n" + 
				"  <td class=xl6530409 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>(件)</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/queryFoodstuff/queryShiPinZhunRu.do",
					data : "begintime=" + begintime+"&endtime="+endtime
							+ "&timess=" + new Date() ,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
					
						
						 for (var i = 0;i<dataObj.length; i++) {
							
							 
							if(dataObj[i].nyFlag=="当月数"){
								htmls+="<tr height=19 style='height:14.25pt'>\n" +
								"  <td rowspan=2 height=38 class=xl6630409 width=101 style='height:28.5pt;\n" + 
								"  border-top:none;width:76pt'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl6430409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>当月数</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品生产受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品生产办理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品经营受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品经营办理+"</td>\n" + 
								" </tr>";
							}else{
								htmls+="<tr height=19 style='height:14.25pt'>\n" + 
								"  <td height=19 class=xl6430409 width=100 style='height:14.25pt;border-top:\n" + 
								"  none;border-left:none;width:75pt'>本年度累计</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品生产受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品生产办理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品经营受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].食品经营办理+"</td>\n" + 
								" </tr>"; 
								
							}
							 
							 
						 }
							
						
						
						/* for (var i = 0;i= dataObj.length; i++) {
							var regss=regs[i];
							var regyear=regss+"年";
							var regmonth=regss+"月";
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td rowspan=2 height=38 class=xl6630409 width=101 style='height:28.5pt;\n" + 
								"  border-top:none;width:76pt'>"+regss+"</td>\n" + 
								"  <td class=xl6430409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>当月数</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regmonth].食品生产受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regmonth].食品生产办理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regmonth].食品经营受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regmonth].食品经营办理+"</td>\n" + 
								" </tr>\n" +
								" <tr height=19 style='height:14.25pt'>\n" + 
								"  <td height=19 class=xl6430409 width=100 style='height:14.25pt;border-top:\n" + 
								"  none;border-left:none;width:75pt'>本年度累计</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regyear].食品生产受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regyear].食品生产办理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regyear].食品经营受理+"</td>\n" + 
								"  <td class=xl7330409 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[regyear].食品经营办理+"</td>\n" + 
								" </tr>"; 
								
						}*/
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("shiyao_zhunru_down.jsp", "_blank");
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
		title="食药准入业务工作完成情况报表（食品）  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="endtime" name='endtime' name='endtime' vtype="datefield"
			label="截止日期" labelAlign="right" labelwidth='100px' width="310"></div>
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