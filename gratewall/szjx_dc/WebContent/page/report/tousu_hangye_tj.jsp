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
		var hendtime = aa.data.hendtime;
		var hbegintime = aa.data.hbegintime;
		var checktype=aa.data.checktype;
		var types;
		if (checktype==1){
			types="投诉";
		}else  if (checktype==2){
			types="举报";
		}else{
			types="举报/投诉";
		}
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
		var htmls= '<div>'
			+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
			+ begintime
			+ '\''
			+ ','
			+ '\''
			+ endtime
			+ '\''
			+ ','
			+ '\''
			+ hendtime
			+ '\''
			+ ','
			+ '\''
			+ hbegintime
			+ '\''
			+ ','
			+ '\''
			+ checktype
			+ '\''
			+ ')" > 下     载</a>'
			+ '</div>'
			+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
			+"<div id=\"投诉举报行业异动提示表_11414\" align=center x:publishsource=\"Excel\">\n" +
"\n" + "<table border=0 cellpadding=0 cellspacing=0 width=662 style='border-collapse:\n" + 
" collapse;table-layout:fixed;width:498pt'>\n" + 
" <col width=182 style='mso-width-source:userset;mso-width-alt:5824;width:137pt'>\n" + 
" <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>\n" + 
" <col width=102 style='mso-width-source:userset;mso-width-alt:3264;width:77pt'>\n" + 
" <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>\n" + 
" <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>\n" + 
" <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>\n" + 
" <tr height=37 style='mso-height-source:userset;height:27.75pt'>\n" + 
"  <td colspan=6 height=37 class=xl6911414 width=662 style='border-right:1.0pt solid black;\n" + 
"  height:27.75pt;width:498pt'>"+types+"行业异动提示表</td>\n" + 
" </tr>\n" + 
" <tr height=19 style='height:14.25pt'>\n" + 
"  <td rowspan=2 height=38 class=xl6611414 width=182 style='height:28.5pt;\n" + 
"  width:137pt'>涉及客体</td>\n" + 
"  <td rowspan=2 class=xl6611414 width=89 style='width:67pt'>承办量</td>\n" + 
"  <td colspan=2 class=xl6711414 width=200 style='border-right:.5pt solid black;\n" + 
"  border-left:none;width:151pt'>同比变化</td>\n" + 
"  <td colspan=2 class=xl6711414 width=191 style='border-right:.5pt solid black;\n" + 
"  border-left:none;width:143pt'>环比变化</td>\n" + 
" </tr>\n" + 
" <tr height=19 style='height:14.25pt'>\n" + 
"  <td height=19 class=xl6411414 width=102 style='height:14.25pt;border-top:\n" + 
"  none;border-left:none;width:77pt'>数量增减</td>\n" + 
"  <td class=xl6411414 width=98 style='border-top:none;border-left:none;\n" + 
"  width:74pt'>比例增减</td>\n" + 
"  <td class=xl6411414 width=96 style='border-top:none;border-left:none;\n" + 
"  width:72pt'>数量增减</td>\n" + 
"  <td class=xl6411414 width=95 style='border-top:none;border-left:none;\n" + 
"  width:71pt'>比例增减</td>\n" + 
" </tr>";
		$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/touSuHangYe.do",
			data : "begintime=" + begintime + "&endtime=" + endtime
					+ "&timess=" + new Date() + "&id=" + Math.random()
					+"&hendtime="+hendtime+"&hbegintime="+hbegintime+"&checktype="+checktype,
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var i = 0,b=dataObj.length; i < b; i++) {
					htmls+=
						"<tr height=19 style='height:14.25pt'>\n" +
						" <td height=19 class=xl6511414 width=182 style='height:14.25pt;border-top:\n" + 
						" none;width:137pt'>"+dataObj[i].涉及客体+"</td>\n" + 
						" <td class=xl6311414 width=89 style='border-top:none;border-left:none;\n" + 
						" width:67pt'><span lang=EN-US>"+dataObj[i].承办量+"　</span></td>\n" + 
						" <td class=xl6311414 width=102 style='border-top:none;border-left:none;\n" + 
						" width:77pt'><span lang=EN-US>"+dataObj[i].同比数量增减+"　</span></td>\n" + 
						" <td class=xl6311414 width=98 style='border-top:none;border-left:none;\n" + 
						" width:74pt'><span lang=EN-US>"+dataObj[i].同比比例增减+"%　</span></td>\n" + 
						" <td class=xl6311414 width=96 style='border-top:none;border-left:none;\n" + 
						" width:72pt'><span lang=EN-US>"+dataObj[i].环比数量增减+"　</span></td>\n" + 
						" <td class=xl6311414 width=95 style='border-top:none;border-left:none;\n" + 
						" width:71pt'><span lang=EN-US>"+dataObj[i].环比比例增减+"%　</span></td>\n" + 
						"</tr>";
				}
				htmls += '</table>' + '</div>';
			}
		});
		var newWim = open("tousu_hangye_tj_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);
	}
			</script>
	</head>
	<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="投诉举报行业异动提示表 统计条件">
		<div id="begintime" name='begintime'  vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hbegintime" name='hbegintime'  vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hendtime" name='hendtime' vtype="datefield" label="环比截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			<div name="checktype" vtype="checkboxfield" label="信息件类型"
			labelalign="left" width="1000"
			dataurl="[{value: '1',text: '投诉'},{value: '2',text: '举报'}]"
			class="jazz-field-comp jazz-checkboxfield-comp"
			style="width: 1000px; margin-left: 35px; padding-top: 15px;"></div>
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
			