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
	
	/* $(document).ready(function getcode(){
		
	var date = new Date;
		var year = date.getFullYear();
		for (var i = 11; i > 0; i--) {
			$("#tongjinianfen").comboxfield('addOption', year - i, year - i);
		}
		$("#tongjinianfen").comboxfield('addOption', year, year);
		$("#tongjinianfen").comboxfield("reload");

	}); */

	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		//var hbegintime = aa.data.hbegintime;
		//var hendtime = aa.data.hendtime;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				jazz.warn("初始日期大于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			jazz.warn("日期为空");
			return;
		}
		var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' + 
				'\'' + begintime + '\''  +','+
				//'\'' + endtime + '\''  +','+
				//'\'' + hbegintime + '\''  +','+
				'\'' + endtime + '\''
				+')" > 下     载</a>' + '</div>' 
		+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' +
		"<div id=\"全市质量监督案件统计表（举报）_8629\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=720 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:540pt'>\n" + 
		" <col width=120 span=6 style='mso-width-source:userset;mso-width-alt:3840;\n" + 
		" width:90pt'>\n" + 
		" <tr height=27 style='height:20.25pt'>\n" + 
		"  <td colspan=6 height=27 class=xl668629 width=720 style='height:20.25pt;\n" + 
		"  width:540pt'>全市质量监督案件统计表（举报）</td>\n" + 
		" </tr>\n" + 
		" <tr height=19 style='height:14.25pt'>\n" + 
		"  <td height=19 class=xl638629 style='height:14.25pt;border-top:none'>产品名称</td>\n" + 
		"  <td class=xl638629 style='border-top:none;border-left:none'>申诉总数</td>\n" + 
		"  <td class=xl638629 style='border-top:none;border-left:none'>处理总数</td>\n" + 
		"  <td class=xl638629 style='border-top:none;border-left:none'>移送司法机关数</td>\n" + 
		"  <td class=xl638629 style='border-top:none;border-left:none'>涉及产品价值</td>\n" + 
		"  <td class=xl638629 style='border-top:none;border-left:none'>挽回经济损失</td>\n" + 
		" </tr>";
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/zhiLiangJianDuJuBao.do",
			data : {
				"begintime"		  : begintime ,
		        "endtime"         : endtime
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				if (data==null||data=="null"||data.length==0) {
					alert("无数据");
					return;
				}
				var dataObj = eval("(" + data + ")");
				for (var i = 0,j=dataObj.length; i < j; i++) {
					htmls+=
						"<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl648629 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
						"  <td class=xl658629 style='border-top:none;border-left:none'>"+dataObj[i].cnt+"</td>\n" + 
						"  <td class=xl658629 style='border-top:none;border-left:none'>"+dataObj[i].chulishu+"</td>\n" + 
						"  <td class=xl658629 style='border-top:none;border-left:none'>"+dataObj[i].sifa+"</td>\n" + 
						"  <td class=xl658629 style='border-top:none;border-left:none'>"+dataObj[i].jiazhi+"</td>\n" + 
						"  <td class=xl658629 style='border-top:none;border-left:none'>"+dataObj[i].sunshi+"</td>\n" + 
						" </tr>";
					
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("zhiliangjiandu_jubao_down.jsp", "_blank");
					window.setTimeout(function() {
						newWim.document.body.innerHTML = htmls;
					}, 500); 
			}
		}); 

	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="全市质量监督案件统计表（举报） 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
			
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