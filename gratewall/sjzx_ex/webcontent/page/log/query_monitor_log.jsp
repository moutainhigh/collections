<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="cn.gwssi.common.context.TxnContext"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯϵͳ��־�б�</title>
</head>
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
					"startTime")
					&& null == context.getRecord("select-key")
							.getValue("endTime")) {
				context.getRecord("select-key").setValue("startTime",
						start_date);
				context.getRecord("select-key").setValue("endTime",
						end_date);
			}
		}
%>
<script language="javascript">



// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var value=getFormAllFieldValues("record:first_page_query_id");
	
	var date_s1 = document.getElementsByName("span_record:query_time");
	for(var ii=0; ii<date_s1.length; ii++){
		date_s1[ii].innerHTML = date_s1[ii].innerHTML.substr(0,10);
	}
	
}
function func_record_querycode(first_page_query_id){
    var url="txn601016.do?select-key:first_page_query_id="+first_page_query_id;
    var page = new pageDefine( url, "��ѯϵͳ��־�б�", "ϵͳ��־" );
	page.addRecord();
}

function check(){
	var start = document.getElementById("select-key:startTime").value;	
	var end = document.getElementById("select-key:endTime").value;
	if(start&&end){
        if(start>end){
            alert("[ʱ�䷶Χ]�������ڱ�����ڿ�ʼ���ڣ�");
            document.getElementById("select-key:endTime").select();
            return false;
        }		

	}
	//else if(!end){
		//alert("������[ʱ�䷶Χ]����Ϊ�գ�");
		//return false;
	//}
	return true;
}
function checkForm(){
	if(!check()){
		return false;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���м����־�б�"/>
<freeze:errors/>
<freeze:form action="/txn6010007"  onsubmit="return checkForm();">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:browsebox property="objectId" caption="��ض���" valueset="���м��_��ض���" show="name" data="name" style="width:95%"/>
      <%-- <freeze:browsebox property="propId"  caption="���ָ��"  valueset="���м��_���ָ��" show="name" data="name" style="width:95%"/>
       --%><freeze:datebox property="startTime"  caption="ʱ�䷶Χ" datatype="string" maxlength="30" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
      </td><td width='5%'>&nbsp;����</td><td width='45%'>
      <freeze:datebox property="endTime"  caption="ʱ�䷶Χ" datatype="string" maxlength="30" style="width:100%" colspan="0"/>
      </td><td width='5%'></td></tr></table>
  </freeze:block>
<br/>
  <freeze:grid property="record"   checkbox="false" caption="��ѯϵͳ��־�б�" keylist="object_id" width="95%" navbar="bottom" >
     
      <freeze:hidden property="object_id" caption="����" style="width:10%" />
      <freeze:cell property="@rowid" caption="���"  align="center"  style="width:50px; " />
      <freeze:cell property="object_id" caption="��ض���" valueset="���м��_��ض���"  align="center"  style="width:22%" />
      <%-- <freeze:cell property="index_name" caption="���ָ��"  align="center"  style="width:15%" /> --%>
      <%-- <freeze:cell property="monitor_task" caption="�����������"  align="center"   style="width:15%" /> --%>
      <freeze:cell property="monitor_value" caption="ʵ��ֵ"  align="center"    style="width:15%" />
      <freeze:cell property="threshold" caption="��ֵ"   align="center"     style="width:10%" />
      <freeze:cell property="monitor_msg" caption="�����Ϣ"   align="left"     style=" " />
      <%-- <freeze:cell property="task_start_time" caption="��ʼʱ��"    align="center"        style="width: 130px;" /> --%>
      <freeze:cell property="task_end_time" caption="���ʱ��"    align="center" style="width:135px;" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
