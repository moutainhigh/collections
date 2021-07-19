<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/master-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ������λ����б�</title>
</head>

<script language="javascript">

// ���Ӽ�����λ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-gz_zb_jldw_fl.jsp", "���Ӽ�����λ���", "modal" );
	page.addRecord();

}

// �޸ļ�����λ���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn301054.do", "�޸ļ�����λ���", "modal" );
	page.addParameter( "record:dwlb_dm", "primary-key:dwlb_dm" );
	page.updateRecord();
}

// ɾ��������λ���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn301055.do", "ɾ��������λ���" );
	page.addParameter( "record:dwlb_dm", "select-key:dwlb_dm" );
	page.addParameter( "record:dwlb_cn_mc", "select-key:dwlb_cn_mc" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ά����ϸ��[gz_zb_jldw]
function func_go_gz_zb_jldw()
{
	// ά����ϸ��ʱ����Ҫ���ݺ�����[gz_zb_jldw_fl]�����
	var page = new pageDefine( "/txn301061.do", "������λ��" );
	page.addParameter( "record:dwlb_dm", "select-key:dwlb_dm" );
	page.addParameter( "record:dwlb_cn_mc", "select-key:dwlb_cn_mc" );
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ������λ����б�"/>
<freeze:errors/>

<freeze:form action="/txn301051">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%" captionWidth="0.4" nowrap="true">
      <freeze:text property="dwlb_dm" caption="������λ������" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:text property="dwlb_cn_mc" caption="������λ�����������" datatype="string" maxlength="255" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="������λ����б�" keylist="dwlb_dm" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="301053" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="301054" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="301055" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="go_gz_zb_jldw" caption="������λ" txncode="301061" enablerule="1" align="right" onclick="func_go_gz_zb_jldw();"/>
      <freeze:cell property="dwlb_dm" caption="������λ������" style="width:15%" />
      <freeze:cell property="dwlb_cn_mc" caption="������λ�����������" style="width:19%" />
      <freeze:cell property="dwlb_cn_ms" caption="������λ�����������" style="width:25%" />
      <freeze:cell property="dwlb_en_mc" caption="������λ���Ӣ������" style="width:19%" />
      <freeze:cell property="dwlb_en_ms" caption="������λ���Ӣ������" style="width:22%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
