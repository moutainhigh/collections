<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/detail-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ������λ�б�</title>
</head>

<script language="javascript">

// ���Ӽ�����λ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-gz_zb_jldw.jsp", "���Ӽ�����λ", "modal" );
	
	// ���������Ϣ
	page.addParameter( "select-key:dwlb_dm", "record:dwlb_dm" );	
	page.addParameter( "select-key:dwlb_cn_mc", "record:dwlb_cn_mc" );	
	page.addRecord();
}

// �޸ļ�����λ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn301064.do", "�޸ļ�����λ", "modal" );
	page.addParameter( "record:jldw_dm", "primary-key:jldw_dm" );
	page.addParameter( "select-key:dwlb_cn_mc", "select-key:dwlb_cn_mc" );
	page.updateRecord();
}

// ɾ��������λ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn301065.do", "ɾ��������λ" );
	page.addParameter( "record:jldw_dm", "select-key:jldw_dm" );
	page.addParameter( "record:jldw_cn_mc", "select-key:jldw_cn_mc" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}


// �� ��
function func_record_goBackNoUpdate()
{
	 goBackWithUpdate("/txn301051.do");	// /txn301051.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ������λ�б�"/>
<freeze:errors/>

<freeze:form action="/txn301061">
  <freeze:frame property="select-key" width="95%">
  <freeze:hidden property="dwlb_dm" caption="������λ������"/>
  <freeze:hidden property="dwlb_cn_mc" caption="������λ�����������"/>
  </freeze:frame>

  <freeze:grid property="record" caption="������λ�б�" keylist="jldw_dm" width="95%" navbar="bottom" fixrow="false">
      <freeze:if test="${ !(empty select-key.dwlb_dm) }">
        <freeze:button name="record_addRecord" caption="����" txncode="301063" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      </freeze:if>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="301064" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="301065" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBackNoUpdate" caption="�� ��" enablerule="0" hotkey="CLOSE" align="right" onclick="func_record_goBackNoUpdate();"/>     
      <freeze:cell property="jldw_dm" caption="������λ����" style="width:17%" />
      <freeze:cell property="dwlb_dm" caption="������λ������" style="width:10%" visible="false"/>
      <freeze:cell property="jldw_cn_mc" caption="������λ��������" style="width:34%" />
      <freeze:cell property="jldw_sjz" caption="������λ����ֵ" style="width:17%" />
      <freeze:cell property="jldw_en_mc" caption="������λӢ������" style="width:32%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
