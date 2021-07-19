<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="1000" height="400">
<head><title>�ֶ�ִ�вɼ�����</title></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

function RunManual(){
	var page = new pageDefine( "/txn30182103.ajax","ִ��");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.addParameter("record:collect_type","record:collect_type");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.addParameter("record:file_name_en","record:file_name_en");
	page.addParameter("record:collect_table","record:collect_table");
	page.addParameter("record:collect_mode","record:collect_mode");
	page.addParameter("record:file_sepeator","record:file_sepeator");
	page.callAjaxService("resultBack");
}

function resultBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var count=_getXmlNodeValue(xmlResults, 'rsBack:count');
		var errorMsg=_getXmlNodeValue(xmlResults, 'rsBack:errorMsg');
		alert("ִ�н��� \n��������������"+count+"\n������Ϣ��"+errorMsg);		
	}
}

//��������������ʾ��Ӧ�����ò���
function showParams(){
	var collectType = getFormFieldValue("record:collect_type");
	if(collectType=='02'){
		$('#row_4').show();
	}else{
		$('#row_4').hide();
	}
	
	
}
function getSourceParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
	var collectType = getFormFieldValue("record:collect_type");
	var service_targets_id=getFormFieldValue('record:service_targets_id');
	if(!(service_targets_id && collectType)){
		alert("����ѡ����������ɼ�����!");
		return;
	}
    var parameter = "input-data:service_targets_id="+ service_targets_id+"&input-data:collectType="+collectType;
	return parameter;
}

function getTableParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
	var service_targets_id=getFormFieldValue('record:service_targets_id');
	if(!service_targets_id){
		alert("����ѡ��������!");
		return;
	}
    var parameter='input-data:service_targets_id='+service_targets_id;
	return parameter;
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$('#row_4').hide();
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="������������"/>
<freeze:errors/>

<freeze:form action="/txn30182103">

	<freeze:block property="record" caption="�ɼ�����������Ϣ"  width="95%">
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"   style="width:95%"/>
      <freeze:browsebox property="collect_type" caption="�ɼ�����" show="name" notnull="true" valueset="�ɼ�����_�ɼ�����" style="width:95%" onchange="showParams()"/>
      <freeze:browsebox property="data_source_id" caption="����Դ" show="name" valueset="��Դ����_��������Ӧ����Դ" notnull="true" parameter="getSourceParameter()"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="�ɼ���"   notnull="true" valueset="��Դ����_��������Ӧ�ɼ���" show="name" parameter="getTableParameter();" style="width:95%" />
      <freeze:browsebox property="collect_mode" caption="�ɼ���ʽ"  notnull="true" show="name" valueset="��Դ����_�ɼ���ʽ"  style="width:95%"  />
	  <freeze:cell caption="ִ�в���:" colspan="2"> </freeze:cell>
	  <freeze:text property="file_name_en" caption="�ļ�ȫ��" notnull="true" maxlength="100" style="width:95%"></freeze:text>
	  <freeze:text property="file_sepeator" caption="�зָ���" notnull="true" maxlength="5" style="width:95%"></freeze:text>
	  
	</freeze:block>
	<br />
	<div style="width:100%;text-align:center" >
		<input type="button" value="ִ������" onclick="RunManual();" />
	</div>
	
</freeze:form>
</freeze:body>
</freeze:html>
