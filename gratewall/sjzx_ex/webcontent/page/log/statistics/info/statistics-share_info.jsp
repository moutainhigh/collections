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
<title>��ѯϵͳ��־�б�</title>
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
	//TODO ��ť��Ҫ�滻
	function _menuClick(id) {
		func_show_selectPic();
	}
	function func_show_selectPic() {
		if (getFormFieldValue('select-key:service_targets') == '') {
			alert("����ѡ��������");
			return;
		}
		var show_type = document.getElementById('chart_type').value;
		var page = new pageDefine("/txn6020009.do", "�鿴ͼƬ");
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

	// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
	<freeze:title caption="�����쳣���ͳ��" />

	<freeze:errors />
	<freeze:form action="/txn6020001">
		<freeze:block theme="query" property="select-key" caption="��ѯ����"
			width="95%">
			<freeze:browsebox property="service_targets" caption="�����������"
				show="name" valueset="��Դ����_�����������" style="width:37%" colspan="2" />
			<tr valign="middle" id="row_1" height="32">
				<td id='label:select-key:query_index'>ͳ������</td>
				<td>
				<select name="select-key:query_index" id="select-key:query_index" style="width: 90%;" onchange="toChageDate();" notnull="false" checkFlag="true" readOnly="false" _value="month" fieldCaption="ͳ������" _inited="true" _default="month">
					<OPTION value=year>��</OPTION>
					<OPTION value=halfYear>����</OPTION>
					<OPTION value=quarter>����</OPTION>
					<OPTION selected value=month>��</OPTION>
				</select>
				</td>
				<td width="15%" class="odd12"><span id="choose_Date">ѡ��ʱ�䣺</span></td>
				<td width="35%" class="odd12">
				    <!-- �� -->
				    <input id="query_date_year" style="display: none; width: 90%" type="text" class="Wdate" value="<%=year%>"
					onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" />
					<!-- �� -->
					<input id="query_date_month" type="text" style="width: 90%" class="Wdate"  value="<%=month%>"
					onFocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2009��01��',maxDate:'%y��%M��'})" />
					<!-- ����-->
					<span id="query_date_quarter" style="display: none;"> 
					 <input id="query_quarter_year"  width: 90%" type="text" class="Wdate"
					  onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" value="<%=year%>" />
						 <select id="query_quarter" >
						  <option value="1">��һ����</option>
						  <option value="2">�ڶ�����</option>
						  <option value="3">��������</option>
						  <option value="4">���ļ���</option>
						</select>
					</span>	
					<span id="query_date_halfyear" style="display: none;"> 
					 <input id="query_halfyear_year"  width: 90%" type="text" class="Wdate" value="<%=year%>"
					  onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" />
						<select id="query_halfyear">
						  <option value="1">�ϰ���</option>
						  <option value="2">�°���</option>
						</select>
					</span>	
				</td>
			</tr>
		</freeze:block>
		<br />
		<div align="center">
			<div align="right" style="margin-right: 30px;">
			   
				<a href="javascript:void('');" onclick="doQuery('chart');" ><span id="chartId" class="selectMenu">ͼ��</span></a>|
			   
			    <a href="javascript:void('');"  onclick="doQuery('table');"><span id="tableId">���</span></a>
			</div>
			<iframe id='showPic' scrolling='no' frameborder='0' align="middle"
				width='95%' style="display: none; height: 480px;"></iframe>
		</div>
		<input type="hidden" id="chart_type" value="chart" />
		<input type="hidden" id="query_date" value="chart" />
	</freeze:form>
</freeze:body>
</freeze:html>
