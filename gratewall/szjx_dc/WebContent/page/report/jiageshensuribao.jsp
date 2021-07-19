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
		var regs=["罗湖局","福田局","南山局","宝安局","龙岗局","盐田局","光明局","坪山局","龙华局","大鹏局"];
		var regss="";
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+

				"<div id=\"价格申诉举报情况日报_9970\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1293 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:970pt'>\n" + 
				" <col width=87 style='mso-width-source:userset;mso-width-alt:2784;width:65pt'>\n" + 
				" <col width=59 style='mso-width-source:userset;mso-width-alt:1888;width:44pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=158 style='mso-width-source:userset;mso-width-alt:5056;width:119pt'>\n" + 
				" <col width=355 style='mso-width-source:userset;mso-width-alt:11360;width:266pt'>\n" + 
				" <col width=211 style='mso-width-source:userset;mso-width-alt:6752;width:158pt'>\n" + 
				" <col width=126 style='mso-width-source:userset;mso-width-alt:4032;width:95pt'>\n" + 
				" <col width=225 style='mso-width-source:userset;mso-width-alt:7200;width:169pt'>\n" + 
				" <tr height=27 style='height:20.25pt'>\n" + 
				"  <td colspan=8 height=27 class=xl669970 width=1293 style='border-right:.5pt solid black;\n" + 
				"  height:20.25pt;width:970pt'>价格投诉举报情况日报</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl639970 width=87 style='height:14.25pt;border-top:none;\n" + 
				"  width:65pt'>单位名称</td>\n" + 
				"  <td class=xl639970 width=59 style='border-top:none;border-left:none;\n" + 
				"  width:44pt'>宗数</td>\n" + 
				"  <td class=xl639970 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>类别</td>\n" + 
				"  <td class=xl639970 width=158 style='border-top:none;border-left:none;\n" + 
				"  width:119pt'>申诉举报人</td>\n" + 
				"  <td class=xl639970 width=355 style='border-top:none;border-left:none;\n" + 
				"  width:266pt'>涉及主体</td>\n" + 
				"  <td class=xl639970 width=211 style='border-top:none;border-left:none;\n" + 
				"  width:158pt'>主要内容</td>\n" + 
				"  <td class=xl639970 width=126 style='border-top:none;border-left:none;\n" + 
				"  width:95pt'>登记编号</td>\n" + 
				"  <td class=xl639970 width=225 style='border-top:none;border-left:none;\n" + 
				"  width:169pt'>处理部门</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/jiaGeTouSuRiBao.do",
					data : "begintime=" + begintime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0,ii=regs.length; i < ii; i++) {
							regss=regs[i];
							if (dataObj[regss].length==0) {
								htmls+=
									"<tr height=19 style='height:14.25pt'>\n" +
									"  <td rowspan=1 height=19 class=xl649970 width=87 style='height:14.25pt;\n" + 
									"  border-top:none;width:65pt'>"+regss+"</td>\n" + 
									"  <td rowspan=1 class=xl649970 width=59 style='border-top:none;width:44pt'><span\n" + 
									"  lang=EN-US>0</span></td>\n" + 
									"  <td class=xl649970 width=72 style='border-top:none;border-left:none;\n" + 
									"  width:54pt'> </td>\n" + 
									"  <td class=xl649970 width=158 style='border-top:none;border-left:none;\n" + 
									"  width:119pt'>　</td>\n" + 
									"  <td class=xl649970 width=355 style='border-top:none;border-left:none;\n" + 
									"  width:266pt'> </td>\n" + 
									"  <td class=xl649970 width=211 style='border-top:none;border-left:none;\n" + 
									"  width:158pt'> </td>\n" + 
									"  <td class=xl669970 width=126 style='border-top:none;border-left:none;\n" + 
									"  width:95pt'><span lang=EN-US> </span></td>\n" + 
									"  <td class=xl649970 width=225 style='border-top:none;border-left:none;\n" + 
									"  width:169pt'> </td>\n" + 
									" </tr>";
									continue;
							}else if(dataObj[regss].length==1){
								htmls+=
									"<tr height=19 style='height:14.25pt'>\n" +
									"  <td rowspan="+dataObj[regss].length+" height=19 class=xl649970 width=87 style='height:14.25pt;\n" + 
									"  border-top:none;width:65pt'>"+regss+"</td>\n" + 
									"  <td rowspan="+dataObj[regss].length+" class=xl649970 width=59 style='border-top:none;width:44pt'><span\n" + 
									"  lang=EN-US>"+dataObj[regss].length+"</span></td>\n" + 
									"  <td class=xl649970 width=72 style='border-top:none;border-left:none;\n" + 
									"  width:54pt'>"+dataObj[regss][0].类别+"</td>\n" + 
									"  <td class=xl649970 width=158 style='border-top:none;border-left:none;\n" + 
									"  width:119pt'>"+dataObj[regss][0].申诉举报人+"　</td>\n" + 
									"  <td class=xl649970 width=355 style='border-top:none;border-left:none;\n" + 
									"  width:266pt'>"+dataObj[regss][0].涉及主体+"</td>\n" + 
									"  <td class=xl649970 width=211 style='border-top:none;border-left:none;\n" + 
									"  width:158pt'>"+dataObj[regss][0].主要内容+"</td>\n" + 
									"  <td class=xl669970 width=126 style='border-top:none;border-left:none;\n" + 
									"  width:95pt'><span lang=EN-US>"+dataObj[regss][0].登记编号+"</span></td>\n" + 
									"  <td class=xl649970 width=225 style='border-top:none;border-left:none;\n" + 
									"  width:169pt'>"+dataObj[regss][0].处理部门+"</td>\n" + 
									" </tr>";
									continue;
							}else{
								htmls+=
									"<tr height=19 style='height:14.25pt'>\n" +
									"  <td rowspan="+dataObj[regss].length+" height=19 class=xl649970 width=87 style='height:14.25pt;\n" + 
									"  border-top:none;width:65pt'>"+regss+"</td>\n" + 
									"  <td rowspan="+dataObj[regss].length+" class=xl649970 width=59 style='border-top:none;width:44pt'><span\n" + 
									"  lang=EN-US>"+dataObj[regss].length+"</span></td>\n" + 
									"  <td class=xl649970 width=72 style='border-top:none;border-left:none;\n" + 
									"  width:54pt'>"+dataObj[regss][0].类别+"</td>\n" + 
									"  <td class=xl649970 width=158 style='border-top:none;border-left:none;\n" + 
									"  width:119pt'>"+dataObj[regss][0].申诉举报人+"　</td>\n" + 
									"  <td class=xl649970 width=355 style='border-top:none;border-left:none;\n" + 
									"  width:266pt'>"+dataObj[regss][0].涉及主体+"</td>\n" + 
									"  <td class=xl649970 width=211 style='border-top:none;border-left:none;\n" + 
									"  width:158pt'>"+dataObj[regss][0].主要内容+"</td>\n" + 
									"  <td class=xl669970 width=126 style='border-top:none;border-left:none;\n" + 
									"  width:95pt'><span lang=EN-US>"+dataObj[regss][0].登记编号+"</span></td>\n" + 
									"  <td class=xl649970 width=225 style='border-top:none;border-left:none;\n" + 
									"  width:169pt'>"+dataObj[regss][0].处理部门+"</td>\n" + 
									" </tr>";
						for (var j = 1,jj=dataObj[regss].length; j < jj; j++) {
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl649970 width=72 style='height:14.25pt;border-top:none;\n" + 
							"  border-left:none;width:54pt'>"+dataObj[regss][j].类别+"</td>\n" + 
							"  <td class=xl649970 width=158 style='border-top:none;border-left:none;\n" + 
							"  width:119pt'>"+dataObj[regss][j].申诉举报人+"</td>\n" + 
							"  <td class=xl649970 width=355 style='border-top:none;border-left:none;\n" + 
							"  width:266pt'>"+dataObj[regss][j].涉及主体+"</td>\n" + 
							"  <td class=xl649970 width=211 style='border-top:none;border-left:none;\n" + 
							"  width:158pt'>"+dataObj[regss][j].主要内容+"</td>\n" + 
							"  <td class=xl669970 width=126 style='border-top:none;border-left:none;\n" + 
							"  width:95pt'><span lang=EN-US>"+dataObj[regss][j].登记编号+"</span></td>\n" + 
							"  <td class=xl649970 width=225 style='border-top:none;border-left:none;\n" + 
							"  width:169pt'>"+dataObj[regss][j].处理部门+"</td>\n" + 
							" </tr>";
								}
							}
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("jiagetousuribao_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 1000);		
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:1, columnwidth: ['100%']}" height="100%"
		title="价格投诉举报情况日报  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
			label="统计日期" labelAlign="center" labelwidth='100px' width="310"></div>
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
