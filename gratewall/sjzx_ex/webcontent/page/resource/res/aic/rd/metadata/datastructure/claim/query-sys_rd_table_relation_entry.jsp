<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�������б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ����������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table.jsp", "����������", "modal" );
	page.addRecord();
}

// �޸�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002204.do", "�޸�������", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// ɾ��������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002205.do", "ɾ��������" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ��ѯ������
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn80002206.do", "�鿴������", "modal" );
	page.addParameter( "record:sys_rd_table_id", "primary-key:sys_rd_table_id" );
	page.updateRecord();
}

// �ֶι���
function func_record_columnRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn80002501.do", "�鿴������" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:table_code", "select-key:table_code" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.updateRecord();
}

// �ֶι���
function func_record_columnRecord_button()
{
	
	var page = new pageDefine( "/txn80002501.do", "�鿴������" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:table_code", "select-key:table_code" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.updateRecord();
}

// ά������ϵ
function func_record_editRelation()
{
	
	var page = new pageDefine( "/txn80002606.do", "ά������ϵ" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record:sys_rd_system_id", "select-key:sys_rd_system_id" );
	page.addParameter( "record:table_name", "select-key:table_name" );
    page.addParameter( "record:table_code", "select-key:table_code" );
	page.goPage();
}


// �� ��
function func_record_goBack()
{
	goBack();	// 
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
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
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�������"/>
<freeze:errors/>

<freeze:form action="/txn80002208">
   <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_rd_data_source_id" caption="����Դ����" show="name" valueset="ȡ����Դ"  style="width:90%"  />
      <freeze:select property="sys_rd_system_id" caption="��������" valueset="ҵ������"   style="width:90%"/>
      <freeze:text property="table_code" caption="�����" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="table_name" caption="�����������" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="table_type" caption="������" valueset="������" colspan="2" style="width:36.5%"/>
      
  </freeze:block>
<br />
  <freeze:grid property="record" caption="��ѯ�������б�" keylist="sys_rd_table_id" width="95%" navbar="bottom"  fixrow="false" multiselect="false"  >
      <freeze:button name="record_addRecord" caption="����������" txncode="80002203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="80002204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="80002205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="�鿴" txncode="80002206" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="�ֶι���" txncode="80002207" enablerule="1" hotkey="" align="right" onclick="func_record_columnRecord_button();" visible="false"/>
      <freeze:button name="record_editRelation" caption="ά�����ϵ" txncode="80002606" enablerule="1" hotkey="" align="right" onclick="func_record_editRelation();"/>
      <freeze:button name="record_goBack" caption="�� ��"  hotkey="CLOSE" align="right" onclick="func_record_goBack();" visible="false"/>
      
      
      <freeze:cell property="table_code" caption="�����" style="width:24%" ondblclick="func_record_columnRecord();"  />
      <freeze:link property="table_name" caption="�����������" style="width:20%" ondblclick="func_record_columnRecord();"/>
      <freeze:cell property="table_type" caption="������" valueset="������" style="width:10%" ondblclick="func_record_columnRecord();" />
      <freeze:cell property="sys_rd_system_id" caption="��������" valueset="ҵ������" style="width:16%" ondblclick="func_record_columnRecord();" />
      
      <freeze:cell property="claim_operator" caption="������" style="width:12%" />
      <freeze:cell property="claim_date" caption="��������" style="width:10%" />
      
      <freeze:hidden property="table_no" caption="���ݱ���" style="width:12%" />
      <freeze:hidden property="table_sql" caption="���ݱ�sql" style="width:20%"  />
      <freeze:hidden property="table_sort" caption="�����ֶ�" style="width:20%" />
      <freeze:hidden property="table_dist" caption="�����ֶ�" style="width:20%" />
      <freeze:hidden property="table_time" caption="ʱ���ֶ�" style="width:20%" />
      <freeze:hidden property="parent_table" caption="������" style="width:20%" />
      <freeze:hidden property="parent_pk" caption="����������" style="width:20%" />
      <freeze:hidden property="table_fk" caption="�븸���������" style="width:20%" />
      <freeze:hidden property="first_record_count" caption="����������" style="width:10%" />
      <freeze:hidden property="last_record_count" caption="���һ��ͬ��������" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="���ݱ�ID" style="width:10%" />
      <freeze:hidden property="sys_rd_system_id" caption="ҵ������ID" style="width:12%" />
      <freeze:hidden property="sys_no" caption="ҵ��������" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:12%" />
      <freeze:hidden property="table_primary_key" caption="������" style="width:20%" />
      <freeze:hidden property="table_index" caption="������" style="width:20%" />
      <freeze:hidden property="table_use" caption="��;" style="width:20%" />
      <freeze:hidden property="gen_code_column" caption="�ִܾ����ֶ�" style="width:12%" />
      <freeze:hidden property="prov_code_column" caption="ʡ�ִ����ֶ�" style="width:12%" />
      <freeze:hidden property="city_code_column" caption="�оִ����ֶ�" style="width:12%" />
      <freeze:hidden property="content" caption="�����ֶ�����" style="width:12%" />
      <freeze:hidden property="changed_status" caption="�仯״̬" style="width:10%" />
      <freeze:hidden property="object_schema" caption="��ģʽ" style="width:16%" />
      <freeze:hidden property="memo" caption="��ע" style="width:20%"  />
      <freeze:hidden property="is_query" caption="�Ƿ�ɲ�ѯ" style="width:10%" />
      <freeze:hidden property="is_trans" caption="�Ƿ�ɹ���" style="width:10%" />
      <freeze:hidden property="is_download" caption="�Ƿ������" style="width:10%" />
      <freeze:hidden property="sort" caption="����" style="width:10%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
