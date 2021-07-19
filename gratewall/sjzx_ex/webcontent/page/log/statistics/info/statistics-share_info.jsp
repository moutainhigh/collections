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
		//func_show_selectPic('chart');
		$("input[name='form_reset']").click(function(){
						$(".Wdate").val("");
						toChageDate();
			});
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
	<freeze:title caption="服务异常情况统计" />

	<freeze:errors />
	<freeze:form action="/txn6020001">
		<freeze:block theme="query" property="select-key" caption="查询条件"
			width="95%">
			<freeze:browsebox property="service_targets" caption="所属服务对象"
				show="name" valueset="资源管理_服务对象名称" style="width:37%" colspan="2" />
			<tr valign="middle" id="row_1" height="32">
				<td id='label:select-key:query_index'>统计粒度</td>
				<td>
				<select name="select-key:query_index" id="select-key:query_index" style="width: 90%;" onchange="toChageDate();" notnull="false" checkFlag="true" readOnly="false" _value="month" fieldCaption="统计粒度" _inited="true" _default="month">
					<OPTION value=year>年</OPTION>
					<OPTION value=halfYear>半年</OPTION>
					<OPTION value=quarter>季度</OPTION>
					<OPTION selected value=month>月</OPTION>
				</select>
				</td>
				<td width="15%" class="odd12"><span id="choose_Date">选择时间：</span></td>
				<td width="35%" class="odd12">
				    <!-- 年 -->
				    <input id="query_date_year" style="display: none; width: 90%" type="text" class="Wdate" value="<%=year%>"
					onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" />
					<!-- 月 -->
					<input id="query_date_month" type="text" style="width: 90%" class="Wdate"  value="<%=month%>"
					onFocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2009年01月',maxDate:'%y年%M月'})" />
					<!-- 季度-->
					<span id="query_date_quarter" style="display: none;"> 
					 <input id="query_quarter_year"  width: 90%" type="text" class="Wdate"
					  onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" value="<%=year%>" />
						 <select id="query_quarter" >
						  <option value="1">第一季度</option>
						  <option value="2">第二季度</option>
						  <option value="3">第三季度</option>
						  <option value="4">第四季度</option>
						</select>
					</span>	
					<span id="query_date_halfyear" style="display: none;"> 
					 <input id="query_halfyear_year"  width: 90%" type="text" class="Wdate" value="<%=year%>"
					  onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" />
						<select id="query_halfyear">
						  <option value="1">上半年</option>
						  <option value="2">下半年</option>
						</select>
					</span>	
				</td>
			</tr>
		</freeze:block>
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
