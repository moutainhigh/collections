<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������ֶα��б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// ������������ֶα�
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_column.jsp", "������������ֶα�", "modal" );
	page.addRecord();
}

// �޸���������ֶα�
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002504.do", "�޸���������ֶα�", "modal" );
	page.addParameter( "record:sys_rd_column_id", "primary-key:sys_rd_column_id" );
	page.updateRecord();
}

// ɾ����������ֶα�
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002505.do", "ɾ����������ֶα�" );
	page.addParameter( "record:sys_rd_column_id", "primary-key:sys_rd_column_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ��ѯ��������ֶα�
function func_record_viewRecord()
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
	var page = new pageDefine( "/txn80002506.do", "�鿴��������ֶα�", "modal" );
	page.addParameter( "record:sys_rd_column_id", "primary-key:sys_rd_column_id" );
	page.updateRecord();
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002501.do
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
<freeze:title caption="��ѯ��������ֶα��б�"/>
<freeze:errors/>

<freeze:form action="/txn80002501">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
  	 <freeze:hidden property="sys_rd_table_id" caption="�������id" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_code" caption="�����" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="table_name" caption="�����������" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="column_code" caption="�ֶ���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="�ֶ�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="column_type" caption="�ֶ�����" valueset="�ֶ���������"  style="width:95%"/>
      <freeze:browsebox property="column_codeindex" caption="ϵͳ���뼯����" show ="mix" valueset="ȡϵͳ���뼯" style="width:95%"/>
  </freeze:block>
	<br />
  <freeze:grid property="record" caption="�ֶ��б�" keylist="sys_rd_column_id" width="95%" navbar="bottom" fixrow="false" multiselect="false" onclick="func_record_viewRecord();">
    <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="80002505" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="�鿴" txncode="80002206" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:button name="record_addRecord" caption="������������ֶα�" txncode="80002503" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="80002504" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_goBack" caption="����" hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
     
      <freeze:cell property="column_code" caption="�ֶ���" style="width:20%" />
      <freeze:cell property="column_name" caption="�ֶ�������" style="width:15%" />
      <freeze:cell property="column_type" caption="�ֶ�����" valueset="�ֶ���������" style="width:10%" />
      <freeze:cell property="column_length" caption="�ֶγ���" style="width:8%" />
      <freeze:cell property="column_codeindex" caption="ϵͳ���뼯����" style="width:15%" />
      
      <freeze:cell property="use_type" caption="�Ƿ���Ч" valueset="��������" style="width:8%" />
      
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="�����ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:12%" />
      <freeze:hidden property="table_code"  caption="�������" style="width:20%" />
      <freeze:hidden property="alia_name" caption="�ֶα���" style="width:20%" />
      <freeze:hidden property="code_name" caption="ϵͳ������" style="width:20%" />
      <freeze:hidden property="standard_status" caption="�ϱ�״̬" style="width:10%" />
      <freeze:hidden property="is_extra_col" caption="�Ƿ�Ϊ�ز��ֶ�" style="width:10%" />
      <freeze:hidden property="sys_column_type" caption="�ֶ�����" style="width:10%" />
      <freeze:hidden property="jc_data_element" caption="��������Ԫ" style="width:10%" />
      <freeze:hidden property="jc_data_index" caption="�������뼯" style="width:10%" />
      <freeze:hidden property="domain_value" caption="ֵ��" style="width:10%" />
      <freeze:hidden property="unit" caption="������λ" style="width:12%" />
      <freeze:hidden property="claim_operator" caption="������" style="width:12%" />
      <freeze:hidden property="claim_date" caption="����ʱ��" style="width:10%" />
      <freeze:hidden property="default_value" caption="ȱʡֵ" style="width:16%" />
      <freeze:hidden property="is_null" caption="�Ƿ�Ϊ��" style="width:10%" />
      <freeze:hidden property="is_primary_key" caption="�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_index" caption="�Ƿ�����" style="width:10%" />
      <freeze:hidden property="changed_status" caption="�ֶα仯״̬" style="width:10%" />
      <freeze:hidden property="sync_sign" caption="ͬ����ʶ��" style="width:12%" />
      <freeze:hidden property="description" caption="˵��" style="width:20%" visible="false" />
      <freeze:hidden property="column_level" caption="�ֶμ���" style="width:10%" />
      <freeze:hidden property="basic_flag" caption="�����ֶα�ʶ" style="width:16%" />
      <freeze:hidden property="data_from" caption="������Դ" style="width:16%" />
      <freeze:hidden property="data_from_column" caption="������Դ�ֶ�" style="width:16%" />
      <freeze:hidden property="transform_desp" caption="����ת������" style="width:20%" />
      <freeze:hidden property="sort" caption="����" style="width:10%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
