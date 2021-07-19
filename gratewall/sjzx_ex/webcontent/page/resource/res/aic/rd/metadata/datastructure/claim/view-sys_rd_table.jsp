<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="400">
<head>
<title>�鿴�������Ϣ</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">


// �� ӡ
function func_record_printDocument()
{
	print(document);
}

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$("input[type='checkbox']").after("<label class='checkboxNew'></label>");
	$("input[type='checkbox']").css("display","none");
	var is_query = getFormFieldValue("record:is_query");
	var is_trans = getFormFieldValue("record:is_trans");
	var is_download = getFormFieldValue("record:is_download");
	var authority="";
	
	if(is_query=='1'){
		authority='0';
		$(".checkboxNew")[0].style.backgroundPositionY='top';
	}
	if(is_trans=='1'){
		if(authority.length!=0){
			authority=authority+',1';
		}else{
			authority='1';
		}		
		$(".checkboxNew")[1].style.backgroundPositionY='top';
	}	
	if(is_download=='1'){
		if(authority.length!=0){
			authority=authority+',2';
		}else{
			authority='2';
		}	
		$(".checkboxNew")[2].style.backgroundPositionY='top';
	}

	if(authority.length!=0){
		setFormFieldValue("record:authority",authority);
	}else{
		setFormFieldValue("record:authority"," ");
	}
	
	
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002207">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_table_id" caption="����" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="�鿴��������Ϣ" width="95%">
     
      <freeze:cell property="table_code" caption="�����" datatype="string"  style="width:95%" />
      <freeze:cell property="sys_name" caption="��������" valueset="��������" valueset="ҵ������" datatype="string"  style="width:95%" />
      <freeze:cell property="first_record_count" caption="������" datatype="string"   style="width:95%" />
      <freeze:cell property="table_primary_key" caption="����" datatype="string"  style="width:95%" />
      <freeze:cell property="table_index" caption="����" datatype="string"  style="width:95%" visible="false"/>
      <freeze:cell property="table_type" caption="������" valueset="������" datatype="string"  style="width:95%" />
      <freeze:cell property="table_name" caption="�����������" datatype="string"  style="width:95%" />
      <freeze:cell property="parent_table" caption="����" datatype="string"  style="width:95%" />
      <freeze:cell property="parent_pk" caption="��������" datatype="string"  style="width:95%" />
      <freeze:cell property="table_fk" caption="���" datatype="string"  style="width:95%" /> 
      <freeze:checkbox property="authority" caption="Ȩ��"  valueset="Ȩ��"   style="width:95%" readonly="true"/> 
      <freeze:cell property="table_use" caption="��;" colspan="2" datatype="string"  style="width:95%" />
  	  <freeze:cell property="memo" caption="��ע"   style="width:95%" colspan="2"/>
  	  
  	  <freeze:hidden property="is_query" caption="�Ƿ�ɲ�ѯ" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_trans" caption="�Ƿ�ɹ���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_download" caption="�Ƿ������" datatype="string" maxlength="1" style="width:95%"/>
  	  <freeze:hidden property="sys_rd_unclaim_table_id" caption="δ�����id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_system_id" caption="ҵ������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="���ݱ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_no" caption="ҵ��������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_record_count" caption="���һ��ͬ��������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="gen_code_column" caption="�ִܾ����ֶ�" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="prov_code_column" caption="ʡ�ִ����ֶ�" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="city_code_column" caption="�оִ����ֶ�" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="content" caption="�����ֶ�����" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="claim_operator" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="��������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="�仯״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="object_schema" caption="��ģʽ" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="table_no" caption="���ݱ���" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="table_sql" caption="���ݱ�sql" maxlength="3000" style="width:98%"/>
      <freeze:hidden property="table_sort" caption="�����ֶ�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="table_dist" caption="�����ֶ�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="table_time" caption="ʱ���ֶ�" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="sort" caption="����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
  
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  			<input type="button" name="record_goBackNoUpdate" value="�ر�" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  		</td>
		<td class="btn_right"></td>
	</tr>
  </table>
  </p>

</freeze:form>
</freeze:body>
</freeze:html>
