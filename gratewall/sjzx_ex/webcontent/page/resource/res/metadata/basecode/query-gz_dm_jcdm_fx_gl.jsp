<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/master-table-query.jsp --%>
<freeze:html width="950" height="650">
<head>
<title>��ѯ��������б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ��ѯ��ϸ����
function _loadDetail()
{
	_formSubmit( null, '��ѯ��ϸ����... ...' );
}

// �� ��
function func_record_goBackNoUpdate()
{
	 goBackWithUpdate("/txn301011.do");	// /txn301051.do
}
// �� ��
function func_record_goBack()
{
	goBack();	// 
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' title='�޸�' href='#'><div class='edit'></div></a>";
	}		
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn3010111">

  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="jc_dm_id" caption="����ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="�������" datatype="string"style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="��������" datatype="string" style="width:95%"/>    
  </freeze:block>
  <br>
  <freeze:grid checkbox="false" rowselect="false" property="record" caption="��������б�" keylist="jcsjfx_id" width="95%" navbar="bottom" fixrow="false">
     <freeze:button name="record_goBack" caption="����" hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
     <freeze:hidden property="sy_zt" caption="ʹ��״̬" style="width:30%" />
      <freeze:cell property="jcsjfx_id" caption="���ݷ���ID" style="width:10%" visible="false"/>
      <freeze:hidden property="jc_dm_id" caption="����ID" style="width:16%" />
      <freeze:cell property="jcsjfx_dm" caption="���ݷ������" style="width:45%" />
      <freeze:cell property="jcsjfx_mc" caption="���ݷ�������" style="width:50%" />
      <freeze:cell property="jcsjfx_cjm" caption="���ݷ���㼶��" style="width:16%" visible="false" />
      <freeze:cell property="jcsjfx_fjd" caption="���ݷ���ڵ����" style="width:16%" visible="false" />
      <freeze:cell property="szcc" caption="���ڲ��" style="width:10%" visible="false" />
      <freeze:cell property="xssx" caption="��ʾ˳��" style="width:10%" visible="false" />
      <freeze:cell property="sfmx" caption="�Ƿ���ϸ" style="width:10%" visible="false" />
      <freeze:cell property="fx_ms" caption="��������" style="width:15%" visible="false" />
      <freeze:cell property="sy_zt" caption="ʹ��״̬" valueset="�������ݷ���ʹ��״̬" style="width:30%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
