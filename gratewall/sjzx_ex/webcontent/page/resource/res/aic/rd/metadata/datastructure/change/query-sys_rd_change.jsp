<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����¼���б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���ӱ����¼��
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_change.jsp", "���ӱ����¼��", "modal" );
	page.addRecord();
}

// �޸ı����¼��
function func_record_updateRecord()
{
	var result =getFormFieldValues("record:change_result");

	if(result!="1"){
		var page = new pageDefine( "/txn80002304.do", "�޸ı����¼��", "modal" );
		page.addParameter( "record:sys_rd_change_id", "record:sys_rd_change_id" );
		page.addParameter( "record:sys_rd_table_id", "record:sys_rd_table_id" );
		page.updateRecord();
	}else
	{
		alert("��ѡ��δ������¼����������������");
		_formSubmit( null, '���ڴ����¼ ... ...');

	}
	
}

// ɾ�������¼��
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002305.do", "ɾ�������¼��" );
	page.addParameter( "record:sys_rd_change_id", "primary-key:sys_rd_change_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �鿴�������
function func_record_viewRecord()
{
	
	var page = new pageDefine( "/txn80002306.do", "�������", "modal" );
	page.addParameter( "record:sys_rd_change_id", "primary-key:sys_rd_change_id" );
	page.updateRecord();
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".radioNew").each(function(index){
		if(index<2){
			$(this).click(function(){
				$(".radioNew:lt(2)").css("background-position-y","bottom");
				$(this).css("background-position-y","top");
				$(this).prev()[0].click();
			});
			$(this).next()[0].onclick=function(){
				$(this).prev()[0].click();
				$(".radioNew:lt(2)").css("background-position-y","bottom");
				$($(".radioNew")[index]).css("background-position-y","top");
			};
		}else{
			$(this).click(function(){
				$(".radioNew:gt(1)").css("background-position-y","bottom");
				$(this).css("background-position-y","top");
				$(this).prev()[0].click();
			});
		}
	});
	for(var ii=0;ii<2;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
	//document.getElementById('label:select-key:change_result').innerHTML='��������';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����¼�б�"/>
<freeze:errors/>

<freeze:form action="/txn80002301">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="sys_rd_change_id" caption="������" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="table_name" caption="�����" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="column_name" caption="�ֶ�" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="change_item" caption="�������" valueset="�������"  style="width:90%"/>
      <freeze:radio property="change_result" caption="������"  valueset="������" style="width:90%"/>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="��ѯ�����¼�б�" keylist="change_result" width="95%" navbar="bottom" rowselect="false" fixrow="false" multiselect="false">
      <freeze:button name="record_addRecord" caption="���ӱ����¼��" txncode="80002303" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_updateRecord" caption="������" txncode="80002304" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_viewRecord" caption="�������"   txncode="80002306" enablerule="1" hotkey="VIEW" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�������¼��" txncode="80002305" enablerule="1" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      
      
      
      <freeze:cell property="db_name" caption="����Դ����" style="width:10%" />
      <freeze:cell property="table_name" caption="�����" style="width:15%" />
      <freeze:cell property="column_name" caption="�ֶ�" style="width:15%" />
      <freeze:cell property="change_item" caption="�������" valueset="�������" style="width:10%" />
      <freeze:cell property="change_before" caption="���ǰ����" valueset="�ֶ���������" style="width:20%" />
      <freeze:cell property="change_after" caption="���������" valueset="�ֶ���������" style="width:20%" />
      <freeze:cell property="change_result" caption="������" valueset="������" style="width:10%" />
      
      <freeze:hidden property="sys_rd_change_id" caption="������" style="width:10%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:12%" />
      <freeze:hidden property="db_username" caption="�û�����" style="width:20%" />
      <freeze:hidden property="table_name_cn" caption="�����������" style="width:20%" />
      <freeze:hidden property="column_name_cn" caption="�ֶ�������" style="width:20%" />
      <freeze:hidden property="change_oprater" caption="�����" style="width:20%" />
      <freeze:hidden property="change_time" caption="���ʱ��" style="width:12%" />
      <freeze:hidden property="change_reason" caption="���ԭ��" style="width:20%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
      
      <freeze:hidden property="sys_rd_table_id" caption="�������ID" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
