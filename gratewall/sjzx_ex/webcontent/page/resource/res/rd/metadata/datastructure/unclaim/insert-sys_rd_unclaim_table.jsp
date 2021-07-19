<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>����δ�������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '����δ�����' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����δ�����' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����δ�����' );	// /txn80002101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002101.do
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
<freeze:title caption="����δ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002103">
  <freeze:block property="record" caption="����δ�������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_rd_unclaim_table_id" caption="δ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_table_code" caption="δ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_table_name" caption="δ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="object_schema" caption="����ģʽ" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="tb_index_name" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_index_columns" caption="�����ֶ�" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="tb_pk_name" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_pk_columns" caption="�����ֶ�" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="cur_record_count" caption="��ǰ��¼����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:textarea property="remark" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="data_object_type" caption="���ݶ�������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
