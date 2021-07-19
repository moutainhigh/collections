<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/detail-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ��������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-jc_sys.jsp", "��������", "modal" );
	
	// ���������Ϣ
	page.addParameter("select-key:db_username","record:db_username");
	page.addParameter( "select-key:db_name", "record:db_name" );
	page.addParameter("select-key:sys_rd_data_source_id","record:sys_rd_data_source_id");
	page.addRecord();
}

// �޸�����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn8000114.do", "�޸�����", "modal" );
	page.addParameter( "record:sys_rd_system_id", "primary-key:sys_rd_system_id" );
	page.updateRecord();
}

// ɾ������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn8000115.do", "ɾ������" );
	page.addParameter( "record:sys_rd_system_id", "primary-key:sys_rd_system_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	// /txn8000101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".grid-headrow td:eq(0)").css("width","30");
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn8000111">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
  	  <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" style="width:95%"/>
      <freeze:hidden property="db_username" caption="�û�" datatype="string" style="width:39%"/>
      <freeze:text property="db_name" caption="����Դ����" datatype="string" colspan="2" style="width:39%" readonly="true"/>
      <freeze:text property="sys_no" caption="������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="sys_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�����б�" nowrap="true" keylist="sys_rd_system_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������" enablerule="0" hotkey="ADD" align="right" icon="/script/button-icon/icon_add.gif" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�����" enablerule="1" hotkey="UPDATE" icon="/script/button-icon/icon_update.gif" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������" enablerule="2" hotkey="DELETE" icon="/script/button-icon/icon_delete.gif" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_goBackNoUpdate" caption="�� ��" enablerule="0" hotkey="CLOSE" align="right" icon="/script/button-icon/icon_goback.gif" onclick="func_record_goBackNoUpdate();"/>
      <freeze:cell property="sys_rd_system_id" caption="����" visible="false"/>
      <freeze:cell property="sys_no" caption="������" style="width:20%" />
      <freeze:cell property="sys_name" caption="��������" style="width:20%" />
      <freeze:cell property="sys_simple" caption="ҵ��ϵͳ" style="width:20%" />
      <freeze:cell property="memo" caption="��ע" style="width:20%"  />
      <freeze:cell property="isshow" caption="�Ƿ����" valueset="��������" style="width:10%"  />
      <freeze:hidden property="db_schame" caption="���ݿ�ģʽ" />
      <freeze:hidden property="timestamp" caption="ʱ���" />
      <freeze:hidden property="sort" caption="����" />
      <freeze:hidden property="db_name" caption="����Դ" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
