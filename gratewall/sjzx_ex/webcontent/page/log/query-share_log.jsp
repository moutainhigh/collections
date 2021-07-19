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
<title>��ѯ������־�б�</title>
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
	// ����������ӣ�ҳ�������ɺ���û���ʼ������
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
		var page = new pageDefine(url, "��ѯ������־�б�","modal");
		page.goPage();
	}
	
	function func_record_addRecord(){
		var page = new pageDefine( "update-share_archive.jsp", "������־�鵵","modal");
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
<!-- <freeze:button name="record_addRecord" caption="�鵵"  enablerule="0"  align="right" onclick=""/> 
        <freeze:select property="return_codes" caption="����״̬" valueset="��Դ����_һ�����״̬"   show="name"    style="width:95%"/>
       -->
<freeze:body>
	<freeze:title caption="��ѯ������־�б�" />
	<freeze:errors />

	<freeze:form action="/txn6010011">
		<freeze:block theme="query" property="select-key" caption="��ѯ����"
			width="95%">
			<freeze:select property="targets_type" caption="��������"  onchange="changeTarget()"
				valueset="��Դ����_�����������" show="name" style="width:95%" />
			<freeze:browsebox property="service_targets_name" caption="�������"  parameter="getParameter()" valueset="��Դ����_�����������_��������" data="name"  show="name" style="width:95%"/>
			
			<freeze:hidden property="service_no" caption="������" style="width:95%" />
			<freeze:datebox property="created_time_start" caption="ʱ�䷶Χ"
				datatype="string" maxlength="30"
				prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>"
				style="width:100%" />
			</td>
			<td width='5%'>&nbsp;����</td>
			<td width='45%'><freeze:datebox property="created_time_end"
					caption="ʱ�䷶Χ" datatype="string" maxlength="30" style="width:100%"
					colspan="0" /></td>
			<td width='5%'></td>
			</tr>
			</table>
		</freeze:block>
		<br />
		<freeze:grid property="record" rowselect="false" checkbox="false" caption="��ѯ������־�б�"
			keylist="log_id" width="95%" navbar="bottom" >
			<freeze:button name="record_backupRecord" caption="�鵵" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
			<freeze:hidden property="log_id" caption="����" style="width:18%" />

			<freeze:cell property="@rowid" caption="���" style="width:5%"  align="center"/>
			<freeze:cell property="service_targets_name" align="center" caption="�������"
				style="width:10%" />
			<freeze:cell property="service_name" caption="��������" align="center" style="width:20%" />
			<freeze:cell property="service_start_time" caption="����ʼʱ��" align="center"
				style="width:18%" />
			<freeze:cell property="consume_time" caption="��ʱ(��)" align="center"
				style="width:10%" />
			<freeze:cell property="record_amount" caption="��������" align="center"
				style="width:8%" />
			<freeze:cell property="return_codes" caption="����״̬" align="center"
				style="width:12%" />
			<freeze:cell property="oper" caption="����" align="center" style="width:5%" />
				
			<freeze:hidden property="targets_type" caption="��������" valueset="��Դ����_�����������" align="center" style="width:10%" />
			<freeze:hidden property="service_no" caption="������" align="center" style="width:10%" />
			<freeze:hidden property="access_ip" caption="����IP" align="center"
				style="width:10%" />	
			

		</freeze:grid>

	</freeze:form>
</freeze:body>
</freeze:html>
