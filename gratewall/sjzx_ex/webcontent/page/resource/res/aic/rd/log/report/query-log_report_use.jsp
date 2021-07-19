<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����������б�</title>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
%>
</head>

<script language="javascript">

// ���ӱ���������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-log_report_use.jsp", "���ӱ���������", "modal" );
	page.addRecord();
}

// �޸ı���������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620200204.do", "�޸ı���������", "modal" );
	page.addParameter( "record:log_report_use_id", "primary-key:log_report_use_id" );
	page.updateRecord();
}

// ɾ������������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620200205.do", "ɾ������������" );
	page.addParameter( "record:log_report_use_id", "primary-key:log_report_use_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �޸�ϵͳʹ���������
function addRecord(id)
{
	var page = new pageDefine( "/txn620200108.ajax", "�޸�ϵͳʹ���������");
	page.addValue( id, "select-key:log_report_create_id" );
	page.callAjaxService('addRecordBack');
}

function addRecordBack(errCode, errDesc, xmlResults){
      if(errCode == '000000'){
        
	  }
}

function toTurn(url,id){

  var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+url;
  document.getElementById('testa').href=url;
  document.getElementById('testa').click();
  addRecord(id);
  
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//var rootPath = '<%=request.getContextPath()%>';
	//var operate = getFormAllFieldValues("record:operate1");
	//var path = getFormAllFieldValues("record:path");
	//var filename = getFormAllFieldValues("record:filename");
	//var ids = getFormAllFieldValues("record:log_report_create_id");
	
	//for(var i=0;i<operate.length;i++){
	//	var url = filename[i];
	//	var id = ids[i];
	//	document.getElementsByName("span_record:operate")[i].innerHTML ="<a href='javascript:void(0);' onclick='toTurn(\""+url+"\",\""+id+"\");'>"+operate[i]+"</a>";
	//}
	
	//var rootPath = '<%=request.getContextPath()%>';
	//var operate = getFormAllFieldValues("record:operate");
	//var path = getFormAllFieldValues("record:path");
	//var filename = getFormAllFieldValues("record:filename");
	//var ids = getFormAllFieldValues("record:log_report_create_id");
	
	//for(var i=0;i<operate.length;i++){
	//	var URL = rootPath+"/downloadFile?file="+path[i]+"&&fileName="+filename[i];
	//	var id = ids[i];
	//	alert("new");
	//	document.getElementsByName("span_record:operate")[i].innerHTML ="<a href='javascript:void(0);' onclick='toTurn(\""+url+"\",\""+id+"\");'>"+operate[i]+"</a>";
		//document.getElementsByName("span_record:operate")[i].innerHTML ="<a href=\""+URL+"\">"+operate[i]+"</a>";
	//}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����������б�"/>
<freeze:errors/>

<freeze:form action="/txn620200201">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="log_report_use_id" caption="��־�������id" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="report_name" caption="��������"  style="width:90%"/>
      <freeze:datebox property="create_date_start" caption="�ϴ�����" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="create_date_end" caption="�ϴ�����" style="width:100%" colspan="0"/>
    </td></tr></table>
    <freeze:text property="creator" caption="�ϴ���"  style="width:90%"/>
      <freeze:datebox property="publish_date_start" caption="��������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="publish_date_end" caption="��������" style="width:100%" colspan="0"/>
    </td></tr></table>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="��ѯ�����������б�" rowselect="false" keylist="log_report_use_id" width="95%" navbar="bottom" fixrow="false" multiselect="true" checkbox="false">
      <freeze:button name="record_addRecord" caption="���ӱ���������" txncode="620200203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸ı���������" txncode="620200204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������������" txncode="620200205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:hidden property="log_report_use_id" caption="��־�������id" />
      <freeze:hidden property="log_report_create_id" caption="��־��������id" />
      <freeze:cell property="report_name" caption="��������" style="width:28%"  />
      <freeze:cell property="creator" caption="�ϴ���" style="width:12%" />
      <freeze:cell property="create_date" caption="�ϴ�����" style="width:12%" />
      <freeze:cell property="publish_date" caption="��������" style="width:12%" />
      <freeze:cell property="browser_person" caption="�����" style="width:10%" />
      <freeze:cell property="timestamp" caption="�������" style="width:20%" />
      <freeze:hidden property="browser_date" caption="�������" />
      <freeze:hidden property="operate" caption="����" />
      <freeze:hidden property="filename" caption="�ļ���" />
      <freeze:hidden property="path" caption="·��" />
      
  </freeze:grid>
<a id="testa" style="display:none;"></a>
</freeze:form>
</freeze:body>
</freeze:html>
