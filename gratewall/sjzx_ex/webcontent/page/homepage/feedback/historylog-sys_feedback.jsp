<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-historylog.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>����������޸���־</title>
</head>

<script language="javascript">

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn711001.do
}

// �����޸Ĺ����ֶ�
function _setModifyField()
{
	// ȡTABLE
	var	table = document.getElementsByName( "record" );
	if( table.length < 1 ){
		return;
	}
	
	// ȡÿ�е����ݣ����бȽ�
	var	rows = table[0].rows;
	for( var ii=1; ii<rows.length-1; ii++ ){
		var cells1 = rows[ii].cells;
		var cells2 = rows[ii+1].cells;
		for( var jj=0; jj<cells1.length-2; jj++ ){
			var ct = cells2[jj].innerText;
			if( cells1[jj].innerText != ct && ct != '' ){
				cells1[jj].style.color = "red";
			}
		}
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	_setModifyField();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����������޸���־"/>
<freeze:errors/>

<freeze:form action="/txn711007">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_feedback_id" caption="�������ID" style="width:95%"/>
  </freeze:frame>

  <freeze:grid property="record" caption="����������޸���־" keylist="h_record_id_" checkbox="false" width="95%" navbar="bottom" fixrow="true">
      <freeze:cell property="sys_feedback_id" caption="�������ID" style="width:19%" />
      <freeze:cell property="content" caption="�����������" style="width:20%" visible="false" />
      <freeze:cell property="publish_date" caption="����ʱ��" style="width:17%" />
      <freeze:cell property="author" caption="������" style="width:32%" />
      <freeze:cell property="status" caption="��Ч��־" style="width:10%" visible="false"/>
      <freeze:cell property="description" caption="������" style="width:32%" />
      <freeze:cell property="h_record_id_" caption="��ˮ��" style="width:14%" visible="false" />
      <freeze:cell property="h_back_time_" caption="�޸�ʱ��" style="width:16%" />
      <freeze:cell property="h_back_user_" caption="�޸���" style="width:14%" />
  </freeze:grid>
  
  <p align="center">
  <input type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>

</freeze:form>
</freeze:body>
</freeze:html>
