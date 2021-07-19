<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/master-table-query.jsp --%>
<freeze:html width="950" height="650">
<head>
<title>��ѯ��������б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���ӻ����������
function func_record_addRecord()
{
    var sy_zt = getFormFieldValue("record:sy_zt",0);
    if(sy_zt=='0'){
       	alert("�����ݷ�����ͣ�ã��������ӽڵ�");
		return;
    }
    var jc_dm_id = getFormFieldValue("select-key:jc_dm_id");   
	var page = new pageDefine( "/txn3010108.do", "���ӻ����������", "modal" );
	page.addValue(jc_dm_id,"record:jc_dm_id");     
	page.addRecord();
}

// �޸Ļ����������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn3010104.do", "�޸Ļ����������", "modal" );
	page.addParameter( "record:jcsjfx_id", "primary-key:jcsjfx_id" );
	page.updateRecord();
}

// ɾ�������������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn3010105.do", "ɾ�������������" );
	page.addParameter( "record:jcsjfx_id", "primary-key:jcsjfx_id" );
	page.addParameter( "record:jcsjfx_mc", "select-key:jcsjfx_mc" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

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
<freeze:title caption="��ѯ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn3010101">

  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="jc_dm_id" caption="����ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="�������" datatype="string"style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="��������" datatype="string" style="width:95%"/>    
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��������б�" keylist="jcsjfx_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="3010103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="3010105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBackNoUpdate" caption="�� ��" enablerule="0" hotkey="CLOSE" align="right" onclick="func_record_goBackNoUpdate();"/>
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
      <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
