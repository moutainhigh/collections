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
		$('#regcode').comboxfield('addOption', "咨询举报申诉中心","6100");
		$.ajax({
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
		});
	});
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var regcode = aa.data.regcode;
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
				+ '<td colspan=26 height=35 class=xl635167 width=1872'+
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
				+ '</tr>';
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryYuWuDj.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&regcode="+regcode
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
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
									+ dataObj[i].消委会转件 + '</td>' + '</tr>';

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

</head>
<body >
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="话务员登记量报表  统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		 <div name='regcode' id="regcode" vtype="comboxfield"  multiple="ture" label="登记部门"
		labelAlign="right" dataurl="return getcode()"labelwidth='100px' width="410" ></div>
		
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