<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ����ݿ��б�</title>
</head>

<script language="javascript">

function toDetail(wid){
	var url="txn30101081.do?select-key:workflow_id="+wid;
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
		ids[i].innerHTML=(i+1);
		html='<a href="javascript:void(\'\');" onclick="toDetail('+wids[i].value+');">'+names[i].innerHTML+'</a>';
		names[i].innerHTML=html;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="ETL�ɼ������б�"/>
<freeze:errors/>

<freeze:form action="/txn30101080">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯETL�ɼ�����" keylist="workflow_name" width="95%" checkbox="false" rowselect="false">
      <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
      <freeze:cell property="subj_desc" caption="��������" style="width:12%" align="center"/>
      <freeze:cell property="workflow_desc" caption="��������" style="width:40%" />
      <freeze:cell property="inteval" caption="�����������" style="width:16%" />
      <freeze:cell property="start_time" caption="��ʼʱ��" style="width:7%" align="center"/>
      <freeze:cell property="add_type" caption="���·�ʽ" style="width:7%" align="center"/>
      <freeze:hidden property="workflow_id" caption="����ID" />
  </freeze:grid>
<Br/>
</freeze:form>
</freeze:body>
</freeze:html>
