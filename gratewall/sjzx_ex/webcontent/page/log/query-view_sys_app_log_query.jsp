<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%
long totalCount=0l;
DataBus db = (DataBus)request.getAttribute("freeze-databus");
if(db!=null){
	DataBus db2 = db.getRecord("select-key");
	if(db2!=null){
		totalCount = db2.getLong("totalCount");
	}
	System.out.println(totalCount+"---------------\n");
}

%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��־��ѯ�б�</title>
</head>

<script language="javascript">

//�������6����
function retrunRecent6mongthRecord(){
	var page = new pageDefine( "/txn620100106.do", "������־��ѯ" );
	page.addRecord();
}
// ������־��ѯ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_sys_app_log_query.jsp", "������־��ѯ", "modal" );
	page.addRecord();
}

// �޸���־��ѯ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100104.do", "�޸���־��ѯ", "modal" );
	page.addParameter( "record:username", "primary-key:username" );
	page.updateRecord();
}

// ɾ����־��ѯ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100105.do", "ɾ����־��ѯ" );
	page.addParameter( "record:username", "primary-key:username" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
function funTBChanged(){
	var type = getFormFieldValue('select-key:type');
	alert("type:"+type);
	
	if(type=='4'){
	setAllFieldVisible("record:ipaddress",false);
	}else{
	setAllFieldVisible("record:ipaddress",true);
	}
}
//�鿴��־����
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "�鿴ҵ����־��Ϣ", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	var types = getFormAllFieldValues("record:type");
	for (var i=0; i < operationSpan.length; i++){
	
		if("ϵͳ����"==types[i]){
			operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'>�鿴��־</a>";
		}
		
	}	
}

function exportExcel(){
  var totalCount = "<%=totalCount%>";
  var numPerPage = 5000;
 if(totalCount>numPerPage){
  var page = new pageDefine( "/txn620100118.do", "���õ���ҳ����", "modal", 300, 100);
  page.addValue(totalCount,"select-key:totalCount");
  page.addValue(numPerPage,"select-key:numPerPage");
  page.goPage( );
	 
 }else{
	
 /*
 	$("input[type='button']").each(function(){
 		var s = $(this).val();
 		if(s&&s=="����"){
 			
 			//$(this).attr("disabled","");
 		//	alert( $(this).attr("disabled") );
 		}
 		//alert( $(this).attr("disabled"))
 	})
 */
 	clickFlag=0;
  	doExport(1,totalCount);	
  	
 }
 
}

function doExport(fromCount, toCount){
	document.forms[0].action ="/txn620100117.do?select-key:fromCount="+fromCount+"&select-key:toCount="+toCount;
	document.forms[0].submit();
	document.forms[0].action ="/txn620100101.do";
	
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯȫ����־��ѯ�б�"/>
<freeze:errors/>

<freeze:form action="/txn620100101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="username" caption="�û��˺�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="query_time" caption="����ʱ��" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:datebox property="query_date_start" caption="����ʱ��" prefix="<table width='95%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="query_date_end" caption="����ʱ��" style="width:100%" colspan="0"/>
    </td></tr></table> 
      <freeze:browsebox property="orgid" caption="��������" valueset="����" show="name" style="width:95%"/>
      <freeze:text property="ipaddress" caption="IP" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="type" caption="��־����" valueset="��������"  style="width:95%" />
  </freeze:block>
<br />
  <freeze:grid property="record" caption="ȫ����־��ѯ�б�" keylist="username" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="export"  caption="����" enablerule="0"  align="right" onclick='exportExcel();'/>
      <freeze:button name="export"  caption="����" enablerule="0"  align="right" onclick='retrunRecent6mongthRecord();'/>
      <freeze:button name="record_addRecord" caption="������־��ѯ" txncode="620100103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸���־��ѯ" txncode="620100104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����־��ѯ" txncode="620100105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="username" caption="�û��˺�" style="width:10%" align="center"/>
      <freeze:cell property="orgid" caption="��������" valueset="����" style="width:28%" align="center"/>
      <freeze:cell property="type" caption="��־����" valueset="��������" style="width:100px" align="center"/>
      <freeze:cell property="query_time" caption="����ʱ��" style="width:20%" align="center"/>
      <freeze:cell property="ipaddress" caption="IP" style="width:100px" align="center"/>
  	  <freeze:cell property="operation" caption="����" align="center" style="width:8%" align="center"/>
  	  <freeze:hidden property="flowno" caption="��ˮ��"  style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
