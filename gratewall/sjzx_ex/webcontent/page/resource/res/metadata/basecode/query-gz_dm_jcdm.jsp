<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/master-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���ӻ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-gz_dm_jcdm.jsp", "���ӻ�������", "modal" );
	page.addRecord();
}

// �޸Ļ�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn301014.do", "�޸Ļ�������", "modal" );
	page.addParameter( "record:jc_dm_id", "primary-key:jc_dm_id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn301015.do", "ɾ����������" );
	page.addParameter( "record:jc_dm_id", "primary-key:jc_dm_id" );

	page.addParameter( "record:jc_dm_id", "select-key:jc_dm_id" );
	page.addParameter( "record:jc_dm_mc", "select-key:jc_dm_mc" );	
	page.addParameter( "record:jc_dm_dm", "select-key:jc_dm_dm" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	//page.callAjaxService('doCallback');
}

function doCallback(errCode, errDesc, xmlResults)
{
    if (errCode != '000000'){
		alert(errDesc);
		return;
	}
}

function func_go_gz_dm_jcdm_fx()
{
	// ά����ϸ��ʱ����Ҫ���ݺ�����[gz_zb_ml]�����
	var page = new pageDefine( "/txn3010101.do", "�������ݷ����", "model", 950, 650);
	page.addParameter( "record:jc_dm_id", "select-key:jc_dm_id" );
	page.addParameter( "record:jc_dm_mc", "select-key:jc_dm_mc" );
	page.addParameter( "record:jc_dm_dm", "select-key:jc_dm_dm" );
	page.addParameter( "record:jc_dm_bzly", "select-key:jc_dm_bzly" );
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a title='�޸�' onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'><div class='edit'></div></a>&nbsp;<a title='����ά��' onclick='setCurrentRowChecked(\"record\");func_go_gz_dm_jcdm_fx();' href='#'><div class='config'></div></a>";
	}	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn301011">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%" nowrap="true">
      <freeze:text property="jc_dm_dm" caption="����" datatype="string" maxlength="255" style="width:90%"/>
      <freeze:text property="jc_dm_mc" caption="��������" datatype="string" maxlength="255" style="width:90%"/>
      <freeze:select property="jc_dm_bzly" caption="��׼��Դ" valueset="���������׼��Դ"  style="width:90%"/>
      <freeze:text property="jcsjfx_mc" caption="��������" datatype="string" style="width:90%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="�����б�"  keylist="jc_dm_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="301013" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="301015" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="jc_dm_id" caption="����ID" style="width:28%" visible="false"/>
      <freeze:cell property="jc_dm_dm" caption="����" style="width:20%" />
      <freeze:cell property="jc_dm_mc" caption="��������" style="width:30%" />
      <freeze:cell property="jc_dm_bzly" caption="��׼��Դ"  valueset="���������׼��Դ" style="width:30%" />
      <freeze:cell property="jc_dm_ms" caption="��������" style="width:30%" visible="false" />
      <freeze:cell property="operation" caption="����" align="center" style="width:10%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
