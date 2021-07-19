<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
<title>查询系统日志列表</title>
<style type="text/css">
.selectMenu {
	padding: 2px;
	background-color: #2B92E8;
	COLOR: #fff;
}
</style>
<%
Calendar calendar=Calendar.getInstance();
int year=calendar.get(Calendar.YEAR);
calendar.add(Calendar.MONTH, -1);
DateFormat df=new SimpleDateFormat("yyyy-MM");
String month=df.format(calendar.getTime());
%>
</head>

<script type="text/javascript">
	//TODO 按钮还要替换
	function _menuClick(id) {
		func_show_selectPic();
	}
	function func_show_selectPic() {
		if (getFormFieldValue('select-key:service_targets') == '') {
			alert("请先选择服务对象");
			return;
		}
		var show_type = document.getElementById('chart_type').value;
		var page = new pageDefine("/txn6020009.do", "查看图片");
		page.addParameter("select-key:service_targets",
				"select-key:service_targets_id");
		var target_name = document.getElementById('select-key:_tmp_service_targets').value;
		page.addValue(target_name, "select-key:service_targets_name");
		page.addParameter("select-key:query_index", "select-key:query_index");
		page.addValue(show_type, "select-key:show_type");
		page.addValue($("#show_num").val(), "select-key:show_num");
		var data_select = getFormFieldValue('select-key:query_index');
		var query_date = '';
		if (data_select == 'quarter') {
			query_date = $('#query_quarter_year').val() + "-"+ $('#query_quarter').val();
		}else if(data_select == 'halfYear'){
			query_date = $('#query_halfyear_year').val() + "-"+ $('#query_halfyear').val();
		} else {
			query_date = document.getElementById('query_date_' + data_select).value;
		}
		page.addValue(query_date, "select-key:query_date");
		var win = window.frames('showPic');
		document.getElementById("showPic").style.display = "block";
		page.goPage(null, win);
	}

	// 请在这里添加，页面加载完成后的用户初始化操作
	function __userInitPage() {
		document.getElementById('form_choose').submit();
	}

	function toChageDate() {
		var data_select = getFormFieldValue('select-key:query_index');
		if (data_select != '') {
			document.getElementById('query_date_year').style.display = "none";
			document.getElementById('query_date_month').style.display = "none";
			document.getElementById('query_date_quarter').style.display = "none";
			document.getElementById('query_date_halfyear').style.display = "none";
			document.getElementById('choose_Date').style.display = "block";
			document.getElementById('query_date_' + data_select).style.display = "block";
		} else {
			document.getElementById('choose_Date').style.display = "none";
			document.getElementById('query_date_year').style.display = "none";
			document.getElementById('query_date_month').style.display = "none";
			document.getElementById('query_date_quarter').style.display = "none";
			document.getElementById('query_date_halfyear').style.display = "none";
		}
	}

	function doQuery(type) {
		document.getElementById('chart_type').value = type;
		document.getElementById('chartId').className = '';
		document.getElementById('tableId').className = '';
		document.getElementById(type + "Id").className = 'selectMenu';
		func_show_selectPic();
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
	

	
	<freeze:form action="/txn6020091">
	
		<br />
		<div align="center">
			<div align="right" style="margin-right: 30px;">
			   
				<a href="javascript:void('');" onclick="doQuery('chart');" ><span id="chartId" class="selectMenu">图表</span></a>|
			   
			    <a href="javascript:void('');"  onclick="doQuery('table');"><span id="tableId">表格</span></a>
			</div>
			<iframe id='showPic' scrolling='no' frameborder='0' align="middle"
				width='95%' style="display: none; height: 480px;"></iframe>
		</div>
		<input type="hidden" id="chart_type" value="chart" />
		<input type="hidden" id="query_date" value="chart" />
	</freeze:form>
</freeze:body>
</freeze:html>
