<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="com.gwssi.common.constant.ExConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="1000" height="850">
<head>
<title>�޸Ĺ���ftp������Ϣ</title>
<style>
.frame-body{table-layout: auto !important;width:95%}
.pages .frame-body{table-layout: fixed;width:100%}

</style>



<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
</head>
<script language="javascript">


// �� ��
function func_record_saveRecord(){

	//saveRecord( '', '���湲��ftp����' );
	
	var item=getFormFieldValue('record1:datasource_id');
  	if(!checkItem(item,"100","����Դ")){
  		    return false;
  	}
  	item=getFormFieldValue('record1:file_name');
  		if(!checkItem(item,"50","�ļ�����")){
  		  return false;
  	}
  	item=getFormFieldValue('record1:file_type');
  	if(!checkItem(item,"100","�ļ�����")){
  		    return false;
  	}
  	/* 
  	var nameType= getFormFieldValue('record1:name_type');
	var check=/^((-\d+)|(0+))$/; //��������
	if(nameType=='D'){
		var day_num=getFormFieldValue('record1:day_num');
		if(day_num.length>5){
			alert("����������ƫ�ơ��������Ƴ���");
			return false;
		}
		if(!check.test(day_num)){
			alert("����������ƫ�ơ�Ҫ������һ����������0");
			return false;
		}
		
	}else if(nameType=='M'){
		var month=getFormFieldValue('record1:month');
		if(month.length>5){
			alert("�������·ݡ��������Ƴ���");
			return false;
		}
		if(!check.test(month)){
			alert("�������·ݡ�Ҫ������һ����������0");
			return false;
		}
		var day_month=getFormFieldValue('record1:day_month');
		if(!day_month){
			alert("���������ڡ���ѡ��һ����Ч����");
			return false;
		}
	}else{
		alert("�����򡾺�׺������ʽ������");
		return false;
	}
  	 */
  	
	var page = new pageDefine("/txn40401002.ajax", "���湲��ftp����");	 ///��
	page.addParameter("record1:ftp_service_id","record1:ftp_service_id");
	page.addParameter("record1:service_id","record1:service_id");
	page.addParameter("record1:service_no","record1:service_no");
	page.addParameter("record1:service_name","record1:service_name");
	page.addParameter("record1:service_targets_id","record1:service_targets_id");
	page.addParameter("record1:datasource_id","record1:datasource_id");
	page.addParameter("record1:file_name","record1:file_name");
	page.addParameter("record1:file_type","record1:file_type");
	page.addParameter("record1:srv_scheduling_id","record1:srv_scheduling_id");
	page.callAjaxService('saveData');
	
}
function saveData(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		   $('#reload')[0].click();
		}
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40401001.do
	
}



//���Ʋɼ�����
function chooseCjzq(){
var ftp_service_id=getFormFieldValue('record1:ftp_service_id');
	if(ftp_service_id==null||ftp_service_id==""){
	    alert("������д������Ϣ!");
	    clickFlag=0;
	    return false;
    }
	window.showModalDialog("/page/share/ftp/scheduling/insert-share_srv_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
	var collectType = "<%=ExConstant.TYPE_SJYLX_FTP%>";
	
	var service_targets_id=getFormFieldValue('record1:service_targets_id');
    var parameter = "input-data:service_targets_id="+ service_targets_id+"&input-data:collectType="+collectType;
	return parameter;
}


function setNameType(obj){
	var nameType=$(obj).val();
	if(nameType=='D'){
		$('#row_3').hide();
		$('#row_4').show();
		
	}else if(nameType=='M'){
		$('#row_4').hide();
		$('#row_3').show();
	}
}

//����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage1()
{
/* 	$(".radioNew").hide();
	$(".radioNew").prev().show();
	var nameType= getFormFieldValue('record1:name_type');
	//alert(nameType);
	if(nameType=='D'){
		$('#row_3').hide();
		$('#row_4').show();
		
	}else if(nameType=='M'){
		$('#row_4').hide();
		$('#row_3').show();
	} */
}

_browse.execute( '__userInitPage1()' );
</script>
<freeze:body>
<freeze:title caption="ftp�����������"/>
<freeze:errors/>

<freeze:form action="/txn40401002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="ftp_service_id" caption="FTP����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record1" caption="�޸Ĺ���ftp������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="����" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="window.close();"/>
      <freeze:hidden property="ftp_service_id" caption="FTP����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_no" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_name" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="�������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:browsebox property="datasource_id" caption="����Դ" show="name" valueset="��Դ����_��������Ӧ����Դ" notnull="true" parameter="getParameter()"  style="width:95%"/>
      <freeze:text property="scheduling_day1" caption="�������" datatype="string" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="����" onclick="chooseCjzq()" class="FormButton">
      <freeze:text property="file_name" caption="�ļ�����" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:select property="file_type" caption="�ļ�����" valueset="�������_�ļ�����" value="01" notnull="true" style="width:95%"/>
     <%--  
      <freeze:radio property="name_type" caption="��׺������ʽ"  value="M" colspan="2" valueset="�ļ����ں�׺������ʽ"  style="width:95%" onclick="setNameType(this);" ></freeze:radio>
      <freeze:text property="month" caption="�·�"  notnull="false" title="������һ��������0������,0��ʾ����,-1��ʾ��һ���£���������"   style="width:95%;"  />
      <freeze:browsebox property="day_month" caption="����" align="center" notnull="false" show="name" valueset="��Դ����_����ѡ��"  show="name" style="width:95%"/>
      <freeze:text property="day_num" caption="����ƫ��"  notnull="false" title="������һ��������0������,0��ʾ����,-1��ʾǰһ�죬��������"   style="width:95%;"  />
      --%> 
      
      <freeze:hidden property="scheduling_type" caption="�ƻ���������"  />
      <freeze:hidden property="start_time" caption="�ƻ�����ʼʱ��"  />
      <freeze:hidden property="end_time" caption="�ƻ��������ʱ��"  />
      <freeze:hidden property="scheduling_week" caption="�ƻ���������"  />
      <freeze:hidden property="scheduling_day" caption="�ƻ���������"  />
      <freeze:hidden property="scheduling_count" caption="�ƻ�����ִ�д���"  />
      <freeze:hidden property="interval_time" caption="ÿ�μ��ʱ��"  />
      <freeze:hidden property="srv_scheduling_id" caption="�������" />
  </freeze:block>
</freeze:form>
<freeze:include href="/page/share/ftp/service/update-share_ftp_patameter.jsp"></freeze:include>
</freeze:body>
</freeze:html>
