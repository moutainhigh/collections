<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />

<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
</style>
</head>
<body>

	


<div id="p" class="easyui-panel" title="话务员登记量报表  统计条件" style="width: 100%; height: 100%; padding: 10px;">
		<div style="margin-bottom: 20px; width: 22%; float: left;">
			<input  id="beginTime" class="easyui-datetimebox" label="开始时间:" labelPosition="left" style="width: 100%;">
		</div>
		<div style="margin-bottom: 20px; width: 22%; float: left; margin-left: 10px;">
			<input id="endTime" class="easyui-datetimebox" label="结束时间:" labelPosition="left" style="width: 100%;">
		</div>
		<div style="float: left; margin-left: 10px;">
			<select id="deptCodes" class="easyui-combobox" name="dept" style="width: 300px; margin-left: 10px;" label="登记部门" labelPosition="left"  data-options="valueField:'regdepcode', textField:'name',panelHeight:'120',panelWidth:'220'">
				<!-- <option value="6100">咨询举报申诉中心</option> -->
			</select>
		</div>
		<div style="clear: both;width: 42%;margin: 0 auto;">
			<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 20%" id="btnQuery">查询</a> 
			<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="width: 20%;" id="btnClear">重置</a> 
		</div>
	</div>
	
	
	<script type="text/javascript" charset="UTF-8">
	var rootPath = "/dc";

	$(document).ready(function getcode() {
		$('#deptCodes').combobox({
			url:rootPath + "/quert12315Controller/getRegCode.do"
		});
		
	});
	
	
	$("#btnQuery").off("click").on('click', function(){
		//$("#btnQuery").off("click");
		$('#btnQuery').linkbutton('disable');
		setTimeout(function(){
			queryUrl();
			$('#btnQuery').linkbutton('enable');
		}, 50);
    });;
	
    
	
	function queryUrl() {
			
		var begintime = $("#beginTime").textbox('getValue');
		var endtime = $("#endTime").textbox('getValue');
		var regcode = $('#deptCodes').combobox('getValue');
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("开始大于结束时间");
				return;
			}
			/* if (begintime == endtime) {
				alert("初始日期等于截止日期");
				return;
			} */
		}
		/* if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		} */
		
		if (begintime.length == 0 ){
			alert("开始时间不能为空");
			return;
		}
		if (endtime.length == 0 ){
			alert("结束时间不能为空");
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
				+ ','
				+ '\''
				+ regcode
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+ '<div id="表样1_5167" align=center x:publishsource="Excel"'+
			'style="overflow: auto;">'
				+ '<table border=0 cellpadding=0 cellspacing=0 width=1872'+
			'style=\'border-collapse: collapse; table-layout: fixed; width: 1404pt\''+
			'id=\'table_t\'>'
				+ '<col width=72 span=26 style=\'width: 54pt\'>'
				+ '<tr height=35 style=\'height: 26.25pt\'>'
				+ '<td colspan=27 height=35 class=xl635167 width=1872'+
						'style=\'border-right: 1.0pt solid black; height: 26.25pt; width: 1404pt\'>话务员登记量报表</td>'
				+ '</tr>'
				+ '<tr height=55 style=\'height: 41.25pt\'>'
				+ '<td height=55 class=xl665167 width=72'+
			'style=\'height: 41.25pt; width: 54pt\'><span'+
			'lang=EN-US>姓名</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>总数</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>电话</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>短信</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>来人</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>来函</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>传真</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>互联网络</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>留言</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 60pt\'><span'+
			'lang=EN-US>市府12345转件</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>其他</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>政府在线</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>电子邮件</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>直通车</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>来信</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>上级部门交办</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>民心桥</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>其它部门转办</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>QQ</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'><span'+
			'lang=EN-US>微信</span></td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>三打两建办</td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>局长邮箱</td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>异地消费申诉转办件</td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>全国价格举报平台来件</td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>消费通</td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>消委会转件</td>'
				+ '<td class=xl675167 width=72 style=\'border-top: none; width: 54pt\'>全国12315平台转件</td>'
				+ '</tr>';
		
				
				$.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryYuWuDj.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&regcode=" + regcode + "&timess=" + new Date()
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						$('#btnQuery').linkbutton('enable');
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += '<tr height=20 style=\'height: 15.0pt\'>'
									+ '<td height=20 class=xl685167 width=72'+
							'style=\'height: 15.0pt; width: 54pt\'>'
									+ dataObj[i].姓名
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].总数
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].电话
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].短信
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].来人
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].来函
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].传真
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].互联网络
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].留言
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].市府12345转件
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].其他
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].政府在线
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].电子邮件
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].直通车
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].来信
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].上级部门交办
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].民心桥
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].其它部门转办
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].qq服务
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].微信平台
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].三打两建办
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].局长邮箱
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].异地消费申诉转办件
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].全国价格举报平台来件
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].消费通
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].消委会转件
									+ '</td>'
									+ '<td class=xl695167 width=72 style=\'width: 54pt\'>'
									+ dataObj[i].全国12315平台转件
									+ '</td>'
									+ '</tr>';

						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("huawu_dj_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);

	}
</script>
</body>
</html>