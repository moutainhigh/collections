<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸��������Ϣ</title>
<style type="text/css">
.cls-no{
	display:none;
}
.cls-yes{
	display:block;
}
</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '�����������' );	// /txn80002201.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002201.do
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record:sys_rd_data_source_id')
    				+ '&input-data:sys_name=' + getFormFieldValue('record:sys_name');
	return parameter;
}

function funTBChanged(){
	var rdVaule = getFormFieldValue('record:table_type');
	
	if(rdVaule=='1'){
	setDisplay("4",false);
	setDisplay("5",false);
	setDisplay("6",false);
	setFormFieldNotnull('record:sys_rd_system_id',0,true);	
	}else {
	
	setDisplay("4",false);
	setDisplay("5",false);
	setDisplay("6",false);
	setFormFieldNotnull('record:sys_rd_system_id',0,false);	
	}
}

function setDisplay(id,b){
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

function getZdParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:sys_rd_table_id=' + getFormFieldValue('record:sys_rd_table_id')
    				+ '&input-data:sys_name=' + getFormFieldValue('record:sys_name');
	return parameter;
}

//�����������뼯��������
function getTCodeParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:table_code=' + getFormFieldValue('record:table_code');
	return parameter;
}

/**
 * ȡ������
 */
function getZBPKParameter(){
	var sys_rd_data_source_id = getFormFieldValue('record:sys_rd_data_source_id');
	var parent_table = getFormFieldValue('record:parent_table');
	var sys_name = getFormFieldValue('record:sys_name');
    var parameter = 'input-data:parent_table='+parent_table
    	+'&input-data:sys_rd_data_source_id='+sys_rd_data_source_id 
    	+'&input-data:sys_name='+sys_name ;
	return parameter;
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$("input[type='checkbox']").after("<label class='checkboxNew'></label>");
	$("input[type='checkbox']").css("display","");
	$("input[type='checkbox']").css("margin-left","-1000");
	$(".checkboxNew").each(function(index){
		$(this).click(function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}else{
				$(this).css("background-position-y","bottom");
			}
		});
		$(this).next()[0].onclick=function(){};
		$($(this).next()[0]).css("hand","text");
	});

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
	
	setFormFieldValue("record:authority",authority);
	
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
		$($(this).next()[0]).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev().prev()[0].click();
			if($(this).prev().prev()[0].checked){
				$($(this).prev()[0]).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
	document.getElementById('label:record:table_type').innerHTML='�����ͣ�';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged()">
<freeze:title caption="�޸��������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_table_id" caption="���ݱ�ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸���������Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:text property="table_code" caption="�����" datatype="string" maxlength="100"  style="width:95%"/>
      <freeze:browsebox property="sys_rd_system_id" caption="��������" show="name" valueset="���������ȡҵ������" parameter="getTCodeParameter()" notnull="true" style="width:95%"/>
      <freeze:text property="first_record_count" caption="������" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="table_primary_key" caption="����" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:radio property="table_type" caption="������" valueset="������" notnull="true" onclick="funTBChanged();" colspan="2" style="width:95%"/>
      <freeze:text property="table_name" caption="�����������" colspan="2" notnull="true" datatype="string" maxlength="100" style="width:39%"/>
      <freeze:browsebox property="parent_table" caption="����" show="mix" valueset="��������Դȡ�����" parameter="getParameter()" colspan="2" style="width:95%" />
      <freeze:browsebox property="parent_pk" caption="��������" show="mix" valueset="��������ȡ��������" parameter="getZBPKParameter()" colspan="2" style="width:95%" />
      <freeze:browsebox property="table_fk" caption="���"  show="mix" valueset="���������ȡ����������" parameter="getZdParameter()" colspan="2" style="width:95%" />     
      <freeze:checkbox property="authority" caption="Ȩ��" colspan="2" valueset="Ȩ��"  style="width:95%"/>
      <!-- <freeze:checkbox property="is_query" caption="Ȩ��" colspan="2" valueset="Ȩ��"  style="width:95%"/>-->
      <freeze:text property="table_use" caption="��;" colspan="2" datatype="string" maxlength="100" style="width:95%"/>
  	  <freeze:textarea property="memo" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      
      <freeze:hidden property="is_query" caption="�Ƿ�ɲ�ѯ" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_trans" caption="�Ƿ�ɹ���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_download" caption="�Ƿ������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="table_index" caption="����" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="δ�����id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_name" caption="ҵ������" datatype="string" maxlength="32" style="width:95%"/>
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

</freeze:form>
</freeze:body>
</freeze:html>
