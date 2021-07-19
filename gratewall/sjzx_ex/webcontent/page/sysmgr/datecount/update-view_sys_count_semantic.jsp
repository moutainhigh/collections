<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>Ԥ����������</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	if(!_validate()){
		return;
	}
	saveAndExit( '', '����Ԥ������' );	// /txn81200001.do
	
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g,"");
}

function _validate(){
	var max_value = getFormFieldValue("record:max_value");
    if(trim(max_value) == ""){
    	alert("����д������ֵ����");
    	document.getElementById("record:max_value").focus();
    	return false;
    }
	var min_value = getFormFieldValue("record:min_value");
    if(trim(min_value) == ""){
    	alert("����д������ֵ����");
    	document.getElementById("record:min_value").focus();
    	return false;
    }
    var reg = new RegExp("^[-]?[0-9]*[0-9][0-9]*$");
	if (reg.test(max_value)==false){
		alert("������ֵ��ֻ��Ϊ������");
	    document.getElementById("record:max_value").select();
		return false;
	}
	if (reg.test(min_value)==false){
		alert("������ֵ��ֻ��Ϊ������");
	    document.getElementById("record:min_value").select();
		return false;
	}
	if(min_value == max_value){
		alert("������ֵ���롾����ֵ��������ͬ��");
		document.getElementById("record:max_value").select();
		return false;
	}
	if(parseInt(min_value) > parseInt(max_value)){
		alert("������ֵ������С�ڡ�����ֵ����");
		document.getElementById("record:max_value").select();
		return false;
	}
	return true;
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn81200001.do
}

function __userInitPage()
{
	document.getElementById("record:max_value").focus();
	window.onunload = null;
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="Ԥ����������"/>
<freeze:errors/>

<freeze:form action="/txn81200005">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="table_class_id" caption="����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="Ԥ����������" width="95%" >
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="table_class_id" caption="����ID" datatype="string" style="width:95%"/>
      <freeze:hidden property="value_id" caption="Ԥ������ID" datatype="string" style="width:95%"/>
      <freeze:cell property="table_name" caption="��Ӣ������" datatype="string" style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="����������" datatype="string" style="width:95%"/>
      <freeze:cell property="class_state" caption="״̬��־��" datatype="string" style="width:95%" colspan="2"/>
      <freeze:text property="min_value" caption="����ֵ" datatype="string" style="width:95%"/>
  	  <freeze:text property="max_value" caption="����ֵ" datatype="string" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
