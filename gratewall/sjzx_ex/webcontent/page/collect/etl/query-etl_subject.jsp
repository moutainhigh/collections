<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<freeze:html width="650" height="350">
<head>
<title>��ѯetl�����б�</title>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
String txnList=context.getRecord("oper-data").getValue("txnList");

%>
</head>

<script language="javascript">

// ����etl����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-etl_subject.jsp", "����etl����", "modal" );
	page.addRecord();
}

// �޸�etl����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30300004.do", "�޸�etl����", "modal" );
	page.addParameter( "record:etl_id", "primary-key:etl_id" );
	page.updateRecord();
}

// ɾ��etl����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30300005.do", "ɾ��etl����" );
	page.addParameter( "record:etl_id", "primary-key:etl_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

function toDetail(wid){
	var url="txn30300006.do?select-key:workflow_id="+wid;
    var page = new pageDefine( url, "��ѯETL������ϸ","modal","800","500");
    page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids=document.getElementsByName("span_record:@rowid");
	var names=document.getElementsByName("span_record:workflow_desc");
	var wids=document.getElementsByName("record:workflow_id");
	var html="";
	for(var i=0;i<ids.length;i++){
		html='<a href="javascript:void(\'\');" onclick="toDetail('+wids[i].value+');">'+names[i].innerHTML+'</a>';
		names[i].innerHTML=html;
		ids[i].innerHTML=(i+1);
		
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯetl�����б�"/>
<freeze:errors/>

<freeze:form action="/txn30300001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>


<%
 if(txnList.indexOf("30300003")!=-1){
%>
  <freeze:grid property="record" caption="��ѯetl�����б�" keylist="etl_id" width="95%"  fixrow="true">
      <freeze:button name="record_addRecord" caption="����etl����" txncode="30300003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="30300004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>&nsbp;&nsbp;&nsbp;&nsbp;
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="30300005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="etl_id" caption="����ID" style="width:5%" />
      <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
      <freeze:cell property="subj_desc" caption="����������" style="width:10%" />
      <freeze:cell property="workflow_desc" caption="��������" style="width:15%" />
      <freeze:cell property="workflow_remark" caption="����ע" style="width:25%" />
      <freeze:cell property="inteval" caption="��������" style="width:15%" />
      <freeze:cell property="start_time" caption="��ʼִ��ʱ��" style="width:8%" align="center"/>
      <freeze:cell property="add_type" caption="���ݴ�������" valueset="��Դ����_�ɼ���ʽ" style="width:8%" align="center"/>
      <freeze:hidden property="workflow_id" caption="����ID" style="width:10%" />
      <freeze:hidden property="workflow_name" caption="��������" />
  </freeze:grid>
<%}else{ %>

   <freeze:grid property="record" caption="��ѯetl�����б�" keylist="etl_id" width="95%" checkbox="false" rowselect="false" >
      <freeze:button name="record_addRecord" caption="����etl����" txncode="30300003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="30300004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>&nsbp;&nsbp;&nsbp;&nsbp;
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="30300005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="etl_id" caption="����ID" style="width:5%" />
      <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
      <freeze:cell property="subj_desc" caption="����������" style="width:10%" />
      <freeze:cell property="workflow_desc" caption="��������" style="width:15%" />
      <freeze:cell property="workflow_remark" caption="����ע" style="width:25%" />
      <freeze:cell property="inteval" caption="��������" style="width:15%" />
      <freeze:cell property="start_time" caption="��ʼִ��ʱ��" style="width:8%" align="center"/>
      <freeze:cell property="add_type" caption="���ݴ�������" valueset="��Դ����_�ɼ���ʽ" style="width:8%" align="center"/>
      <freeze:hidden property="workflow_id" caption="����ID" style="width:10%" />
      <freeze:hidden property="workflow_name" caption="��������" />
  </freeze:grid>

<%} %>

</freeze:form>
</freeze:body>
</freeze:html>
