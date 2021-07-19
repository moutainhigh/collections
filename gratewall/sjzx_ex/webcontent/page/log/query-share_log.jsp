<%@page import="cn.gwssi.common.context.TxnContext"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享日志列表</title>
<%
	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String start_date = df.format(calendar.getTime());
		String end_date = df.format(new Date());
		TxnContext context = (TxnContext) request
				.getAttribute("freeze-databus");
		if (context.getRecord("select-key").isEmpty()) {
			if (null == context.getRecord("select-key").getValue(
					"created_time_start")
					&& null == context.getRecord("select-key")
							.getValue("created_time_end")) {
				context.getRecord("select-key").setValue(
						"created_time_start", start_date);
				context.getRecord("select-key").setValue(
						"created_time_end", end_date);
			}
		}
%>
</head>

<script language="javascript">
	// 请在这里添加，页面加载完成后的用户初始化操作
	function __userInitPage() {

		var value = getFormAllFieldValues("record:log_id");

		for ( var i = 0; i < value.length; i++) {
			var val = value[i];

			document.getElementsByName("span_record:oper")[i].innerHTML = '<a href="javascript:func_record_querycode(\''
					+ val + '\');" title="" ><div class="detail"></a>';

		}

	}
	function func_record_querycode(log_id) {

		var url = "txn6010006.do?select-key:log_id=" + log_id;
		var page = new pageDefine(url, "查询共享日志列表","modal");
		page.goPage();
	}
	
	function func_record_addRecord(){
		var page = new pageDefine( "update-share_archive.jsp", "共享日志归档","modal");
		page.addRecord();
	}
function changeTarget()
{
	if (getFormFieldValue('select-key:targets_type') != null && getFormFieldValue('select-key:targets_type') != "")
	{
		setFormFieldValue("record:service_targets_name",0,"");
	}
}	
function getParameter(){
    var parameter = "input-data:targets_type="+ getFormFieldValue('select-key:targets_type');
	return parameter;
}

	_browse.execute('__userInitPage()');
</script>
<!-- <freeze:button name="record_addRecord" caption="归档"  enablerule="0"  align="right" onclick=""/> 
        <freeze:select property="return_codes" caption="返回状态" valueset="资源管理_一般服务状态"   show="name"    style="width:95%"/>
       -->
<freeze:body>
	<freeze:title caption="查询共享日志列表" />
	<freeze:errors />

	<freeze:form action="/txn6010011">
		<freeze:block theme="query" property="select-key" caption="查询条件"
			width="95%">
			<freeze:select property="targets_type" caption="对象类型"  onchange="changeTarget()"
				valueset="资源管理_服务对象类型" show="name" style="width:95%" />
			<freeze:browsebox property="service_targets_name" caption="服务对象"  parameter="getParameter()" valueset="资源管理_服务对象名称_二级级联" data="name"  show="name" style="width:95%"/>
			
			<freeze:hidden property="service_no" caption="服务编号" style="width:95%" />
			<freeze:datebox property="created_time_start" caption="时间范围"
				datatype="string" maxlength="30"
				prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>"
				style="width:100%" />
			</td>
			<td width='5%'>&nbsp;至：</td>
			<td width='45%'><freeze:datebox property="created_time_end"
					caption="时间范围" datatype="string" maxlength="30" style="width:100%"
					colspan="0" /></td>
			<td width='5%'></td>
			</tr>
			</table>
		</freeze:block>
		<br />
		<freeze:grid property="record" rowselect="false" checkbox="false" caption="查询共享日志列表"
			keylist="log_id" width="95%" navbar="bottom" >
			<freeze:button name="record_backupRecord" caption="归档" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
			<freeze:hidden property="log_id" caption="主键" style="width:18%" />

			<freeze:cell property="@rowid" caption="序号" style="width:5%"  align="center"/>
			<freeze:cell property="service_targets_name" align="center" caption="服务对象"
				style="width:10%" />
			<freeze:cell property="service_name" caption="服务名称" align="center" style="width:20%" />
			<freeze:cell property="service_start_time" caption="服务开始时间" align="center"
				style="width:18%" />
			<freeze:cell property="consume_time" caption="耗时(秒)" align="center"
				style="width:10%" />
			<freeze:cell property="record_amount" caption="共享条数" align="center"
				style="width:8%" />
			<freeze:cell property="return_codes" caption="返回状态" align="center"
				style="width:12%" />
			<freeze:cell property="oper" caption="操作" align="center" style="width:5%" />
				
			<freeze:hidden property="targets_type" caption="对象类型" valueset="资源管理_服务对象类型" align="center" style="width:10%" />
			<freeze:hidden property="service_no" caption="服务编号" align="center" style="width:10%" />
			<freeze:hidden property="access_ip" caption="访问IP" align="center"
				style="width:10%" />	
			

		</freeze:grid>

	</freeze:form>
</freeze:body>
</freeze:html>
