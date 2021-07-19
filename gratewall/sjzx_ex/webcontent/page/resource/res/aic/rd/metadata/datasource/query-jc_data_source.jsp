<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-2/master-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����Դ�б�</title>
<style>
.even2,.even1{padding-left:0px;}
</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ��������Դ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-jc_data_source.jsp", "��������Դ", "modal");
	page.addRecord();
}

// �޸�����Դ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn8000104.do", "�޸�����Դ", "modal");
	page.addParameter( "record:sys_rd_data_source_id", "primary-key:sys_rd_data_source_id" );
	page.updateRecord();
}

// ɾ������Դ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn8000105.do", "ɾ������Դ" );
	page.addParameter( "record:sys_rd_data_source_id", "primary-key:sys_rd_data_source_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

/**
 * 
 */
function func_syncTask(){
	var page = new pageDefine( "sync-jc_data_source.jsp", "ͬ������Դ","modal" );
	page.addParameter("record:sys_rd_data_source_id","record:sys_rd_data_source_id");
	page.addParameter("record:db_server","record:db_server");
	page.addParameter("record:db_name","record:db_name");
	page.addParameter("record:db_schema","record:db_schema");
	page.addParameter("record:db_username","record:db_username");
	page.updateRecord();
}

// ά����ϸ��[jc_sys]
function func_go_jc_sys()
{
	// ά����ϸ��ʱ����Ҫ���ݺ�����[jc_data_source]�����
	var page = new pageDefine( "/txn8000111.do", "ҵ��ϵͳ��" );
	page.addParameter("record:db_username","select-key:db_username");
	page.addParameter( "record:db_name", "select-key:db_name" );
	page.addParameter("record:sys_rd_data_source_id","select-key:sys_rd_data_source_id");
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var sync = document.getElementsByName("span_record:sync_menu");
	for(var ii=0;ii<sync.length;ii++){
		sync[ii].innerHTML = '<font title="ͬ������Դ" style="cursor:hand;" color="#333">&nbsp;&nbsp;<b>ͬ��</b></font>';
	}
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
	$(".grid-headrow td:eq(0)").css("width","30");
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="��ѯ����Դ�б�"/>
<freeze:errors/>

<freeze:form action="/txn8000101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="db_name" caption="����Դ����" valueset="�������ļ�ȡ���ݿ��б�" colspan="2" style="width:36.5%"/>
      
  </freeze:block>
<br />
  <freeze:grid property="record" caption="��ѯ����Դ�б�" nowrap="true" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" multiselect="false" rowselect="false" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������Դ" enablerule="0" hotkey="ADD" icon="/script/button-icon/icon_add.gif" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�����Դ" enablerule="1" hotkey="UPDATE" icon="/script/button-icon/icon_update.gif" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������Դ" enablerule="2" hotkey="DELETE" icon="/script/button-icon/icon_delete.gif" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="go_jc_sys" caption="ҵ������" enablerule="1" align="right" onclick="func_go_jc_sys();"/>
      <freeze:cell property="sys_rd_data_source_id" caption="ID" visible="false"/>
      <freeze:cell property="db_name" caption="����Դ����" style="width:10%" />
      <freeze:cell property="db_type" caption="����Դ����" style="width:10%" />
      <freeze:cell property="db_server" caption="���ݿ�����" style="width:10%" />
      <freeze:cell property="db_username" caption="�û�" style="width:10%" />
      <freeze:cell property="sync_date" caption="ͬ������" style="width:10%" />
      <freeze:cell property="creator" caption="������" style="width:10%" />
      <freeze:cell property="create_date" caption="����ʱ��" style="width:10%" />
      <freeze:link property="sync_menu" caption="ͬ������Դ" value="ͬ��" style="width:10%" onclick="func_syncTask();" />
      
      <freeze:cell property="db_url" caption="������Ϣ" visible="false"/>
      <freeze:cell property="db_driver" caption="��������" visible="false"/>
      <freeze:cell property="value_class" caption="����ת����" visible="false"/>
      <freeze:cell property="merge_flag" caption="�ϲ�����������Ϣ" visible="false"/>
      <freeze:cell property="db_isolation" caption="������뼶" visible="false"/>
      <freeze:cell property="sync_table" caption="�������ݱ�" visible="false"/>
      <freeze:cell property="db_transaction" caption="��������" visible="false"/>
      <freeze:hidden property="db_schema" caption="���ݿ�ģʽ" style="width:12%" />
      <freeze:cell property="db_svrname" caption="ʵ����" visible="false"/>
      <freeze:cell property="db_address" caption="����IP" visible="false"/>
      <freeze:cell property="db_port" caption="�˿�" visible="false"/>
      <freeze:cell property="timestamp" caption="ʱ���" visible="false"/>
      <freeze:cell property="memo" caption="��ע" visible="false" />
      <freeze:cell property="sync_flag" caption="ͬ����־" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
