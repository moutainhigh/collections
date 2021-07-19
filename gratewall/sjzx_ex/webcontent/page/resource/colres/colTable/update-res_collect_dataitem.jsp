<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>

<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸Ĳɼ���������Ϣ</title>
</head>

<script language="javascript">
var is_name_used = 1;
// �� ��
function func_record_saveRecord(){
	var en=getFormFieldValue('record:dataitem_name_en');
	if(!checkEnName(en,'����������')){
		return false;
	}
	var dataitem_type=getFormFieldValue('record:dataitem_type');
	var dataitem_long=getFormFieldValue('record:dataitem_long');
	if(!checkItemTypeLength(dataitem_type,dataitem_long)){//У���������
		return false;
	}
	
	if(!check_int(dataitem_long)){//У���������
	return false;
	}
	var item=getFormFieldValue('record:dataitem_name_en');
	if(!checkItem(item,"1","����������")){
	  	return false;
	 }
  	item=getFormFieldValue('record:dataitem_name_cn');
	if(!checkItem(item,"100","��������")){
	  return false;
	}
	item=getFormFieldValue('record:dataitem_type');
	if(!checkItem(item,"100","����������")){
	  return false;
	}
	item=getFormFieldValue('record:dataitem_long');
	if(!checkItemLength(item,"4","�������")){
	  return false;
	}
	item=getFormFieldValue('record:code_table');
	if(!checkItem(item,"100","��Ӧ�����")){
	  return false;
	}
	item=getFormFieldValue('record:dataitem_long_desc');
  	if(!checkItemLength(item,"2000","����������")){
  	  return false;
  	}
	var page = new pageDefine("/txn20202000.ajax", "������������Ƿ��Ѿ�ʹ��");	
	page.addParameter("record:dataitem_name_en","primary-key:dataitem_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.addParameter("record:collect_dataitem_id","primary-key:collect_dataitem_id");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		
		if(is_name_used>0){
  			alert("�������Ѵ��ڣ�����������");
  		}else{
	  		  
  		       var collect_dataitem_id = getFormFieldValue('record:collect_dataitem_id');
  		       var collect_table_id = getFormFieldValue('record:collect_table_id');
			   var dataitem_name_en = getFormFieldValue('record:dataitem_name_en');
			   var dataitem_name_cn = getFormFieldValue('record:dataitem_name_cn');
			   var dataitem_type = getFormFieldValue('record:dataitem_type');
			   var dataitem_long = getFormFieldValue('record:dataitem_long');
			   var is_key = getFormFieldValue('record:is_key');
			   var code_table = getFormFieldValue('record:code_table');
			   var dataitem_long_desc = getFormFieldValue('record:dataitem_long_desc');
			  
			   var page = new pageDefine( "/txn20202009.ajax", "����ɼ���������Ϣ");
			   page.addValue(collect_dataitem_id,"record:collect_dataitem_id");
			   page.addValue(collect_table_id,"record:collect_table_id");
			   page.addValue(dataitem_name_en,"record:dataitem_name_en");
			   page.addValue(dataitem_name_cn,"record:dataitem_name_cn");
			   page.addValue(dataitem_type,"record:dataitem_type");
			   page.addValue(dataitem_long,"record:dataitem_long");
			   page.addValue(is_key,"record:is_key");
			   page.addValue(code_table,"record:code_table");
			   page.addValue(dataitem_long_desc,"record:dataitem_long_desc");
			   page.callAjaxService('updateItem');
  		}
}
function updateItem(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("����ɹ�!");
		}
		
}

// �� �沢�ر�
function func_record_saveAndExit()
{
	var en=getFormFieldValue('record:dataitem_name_en');
	if(!checkEnName(en,'����������')){
		return false;
	}
	var dataitem_type=getFormFieldValue('record:dataitem_type');
	var dataitem_long=getFormFieldValue('record:dataitem_long');
	if(!checkItemTypeLength(dataitem_type,dataitem_long)){//У���������
	return false;
	}
	if(!check_int(dataitem_long)){//У���������
	return false;
	}
	var page = new pageDefine("/txn20202000.ajax", "������������Ƿ��Ѿ�ʹ��");	
	page.addParameter("record:dataitem_name_en","primary-key:dataitem_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.addParameter("record:collect_dataitem_id","primary-key:collect_dataitem_id");
	page.callAjaxService('nameCheckCallback2');
}
function nameCheckCallback2(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("�����������Ѵ��ڣ�����������");
  		}else{
  		   saveAndExit( '', '����ɼ��������' );
  		}
}


// �� ��
function func_record_goBack()
{
	goBack();	// /txn20202001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸Ĳɼ���������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20202002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID" style="width:95%"/>
  </freeze:frame>
  
  <freeze:block property="tableinfo" caption="�ɼ����ݱ���Ϣ" width="95%">
     <freeze:cell property="service_targets_id" caption="����������ƣ�"   show="name" valueset="��Դ����_�����������"  style="width:95%"/>
     <freeze:cell property="table_name_en" caption="�����ƣ�"   show="name" valueset="��Դ����_�����������"  style="width:95%"/>
     <freeze:cell property="table_name_cn" caption="���ݱ���������"   style="width:95%"/>
     <freeze:cell property="table_type" caption="�����ͣ�"  valueset="��Դ����_������" style="width:95%"/>
     <freeze:cell property="table_desc" caption="��������" colspan="2"  style="width:98%"/>
  </freeze:block>
  <br/>
  <freeze:block property="record" caption="�޸Ĳɼ���������Ϣ" width="95%">
  	  <freeze:button name="record_saveAndExit" caption="����" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="dataitem_name_en" caption="����������" datatype="string" maxlength="100" notnull="true"  style="width:95%"/>
      <freeze:text property="dataitem_name_cn" caption="��������" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:select property="dataitem_type" caption="����������" show="name" valueset="��Դ����_����������" notnull="true" style="width:95%"/>
      <freeze:text property="dataitem_long" caption="�������"   style="width:95%"/>
      <freeze:checkbox property="is_key" caption="�Ƿ�����" valueset="��Դ����_������ʶ" value="0" style="width:95%"/>
      <freeze:select property="code_table" caption="��Ӧ�����" valueset="��Դ����_��Ӧ�����" value="00" notnull="true"  style="width:95%"/>
      <freeze:textarea property="dataitem_long_desc" caption="����������" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
