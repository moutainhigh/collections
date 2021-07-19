<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%
long totalCount=0l;
DataBus db = (DataBus)request.getAttribute("freeze-databus");
System.out.println(db);
if(db!=null){
	DataBus db2 = db.getRecord("select-key");
	if(db2!=null){
		if(null ==db2.getValue("totalCount")){
			totalCount=5000l;
		}else{
			totalCount = db2.getLong("totalCount");
		}
		
	}
	
}

%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��־��ѯ�б�</title>
</head>

<script language="javascript">

//������־��Ϣ
function moreRecord(){
	var page = new pageDefine( "/txn620100101.do", "������־��ѯ" );
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

function exportExcel(){
  var totalCount = "<%=totalCount%>";
  var numPerPage = 5000;
 if(totalCount>numPerPage){
  var page = new pageDefine( "/txn620100120.do", "���õ���ҳ����", "modal", 300, 100);
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
	document.forms[0].action ="/txn620100119.do?select-key:fromCount="+fromCount+"&select-key:toCount="+toCount;
	document.forms[0].submit();
	document.forms[0].action ="/txn620100106.do";
	
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	var types = getFormAllFieldValues("record:type");
	for (var i=0; i < operationSpan.length; i++){
	
		if("ϵͳ����"==types[i]){
			operationSpan[i].innerHTML = "<a title='�鿴��־' onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'><div class='query'></div></a>";
		}
		
	}	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���������־��ѯ�б�"/>
<freeze:errors/>

<freeze:form action="/txn620100106">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="username" caption="�û��˺�" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:hidden property="query_time" caption="����ʱ��" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:datebox property="query_date_start" caption="����ʱ��" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="query_date_end" caption="����ʱ��" style="width:100%" colspan="0"/>
    </td></tr></table> 
      <freeze:browsebox property="orgid" caption="��������" valueset="����" show="name" style="width:90%"/>
      <freeze:text property="ipaddress" caption="IP" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="type" caption="��־����" valueset="��������"  style="width:90%" />
  </freeze:block>
<br />
  <freeze:grid property="record" caption="���������־��ѯ�б�" keylist="username" width="95%" rowselect="false" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="export"  caption="����" enablerule="0"  align="right" onclick='exportExcel();'/>
      <freeze:button name="record_addRecord" caption="����" txncode="620100101" enablerule="0" hotkey="MORE" align="right" onclick="moreRecord();"/>
      <freeze:hidden property="flowno" caption="��ˮ��"  style="width:10%" />
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="username" caption="�û��˺�" style="width:12%" align="center"/>
      <freeze:cell property="orgid" caption="��������" valueset="����" style="width:25%" align="center"/>
      <freeze:cell property="type" caption="��־����" valueset="��������" style="width:15%" align="center" />
      <freeze:cell property="query_time" caption="����ʱ��" style="width:20%" align="center"/>
      <freeze:cell property="ipaddress" caption="IP" style="8%" align="center"/>
  	  <freeze:cell property="operation" caption="����" align="center" style="width:5%" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
