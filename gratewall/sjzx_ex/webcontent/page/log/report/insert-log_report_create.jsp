<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<freeze:html width="650" height="350">
<head>
<title>����ϵͳʹ�����������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	
	saveRecord( '', '������־��������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '������־��������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	var syear = getFormFieldValue("record:year");
	var smonth = getFormFieldValue("record:month");
	var month = parseInt(smonth);
	if(month<10){
		smonth = "0"+smonth;
	}
	var date = syear+smonth;
	var year = parseInt(date);
	
	if(year < 201307)
	{
		alert("�û����ã�ϵͳֻ������2013��6���Ժ���±��ͽ�ֹ�����µ��±���");
		return;
	}else{
		checkReportName();	
	}
	
	//saveAndExit( '', '������־��������' );
}

function checkReportName(){
	var reportName = getFormFieldValue("record:report_name");
	var page = new pageDefine("/txn620200110.ajax", "У�鱨��");
	page.addValue(reportName, "select-key:report_name" );
	page.callAjaxService("callBack");

}

function callBack(errCode, errDesc ,xmlResults ){
	if(errCode!='000000'){
	    if(errCode=='999999'){
		    alert("�����Ѿ����ڣ�")
		    return false; 
	    }else{
	    	 alert("���ɱ���ʱ��������")
			 return false; 
	    }
		
	}
	saveAndExit( '', '������־��������' );
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn620200101.do
}
var months = ['һ','��','��','��','��','��','��','��','��','ʮ','ʮһ','ʮ��'];

//�·ݱ䶯
function changeMonth(){
	var type = getFormFieldValue("record:report_type");
	var type_name;
	if(type=='1'){
		type_name='�±�';
	}else if(type='0'){
		type_name='�걨';
	}else{
		type_name='';
	}
	var syear = getFormFieldValue("record:year");
	var smonth = getFormFieldValue("record:month");
    var reportName ='ϵͳʹ���������'+syear+'��'+smonth+'��'+type_name;
	setFormFieldValue("record:report_name",reportName);
}

//��ݱ䶯
function changeYear(){
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth();
    var syear = getFormFieldValue("record:year");
	var smonth = getFormFieldValue("record:month");
	var monthObj = document.getElementById("record:month");
	if(year==syear){
	   //ɾ������Ԫ��
	   monthObj.options.length=0;
	   for(var i = 1; i <= month; i++){
			var option = document.createElement("option");
			option.appendChild(document.createTextNode(months[i-1]+"��"));
			if(i < 10){
				i = ""+i;
			}
			option.value = i;
			if(smonth == i){
				option.selected = true;
			}
			monthObj.appendChild(option);
	  }
	}else{
	  //ɾ������Ԫ��
      monthObj.options.length=0;
		  for(var i = 1; i <= 12; i++){
			var option = document.createElement("option");
			option.appendChild(document.createTextNode(months[i-1]+"��"));
			if(i < 10){
				i = ""+i;
			}
			option.value = i;
			if(smonth == i){
				option.selected = true;
			}
			monthObj.appendChild(option);
		  }
	}
	//alert(monthObj.options.length);
	syear = getFormFieldValue("record:year");
	smonth = getFormFieldValue("record:month");
	var reportName ='ϵͳʹ���������'+syear+'��'+smonth+'���±�';
	setFormFieldValue("record:report_name",reportName);

}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var yearObj = document.getElementById("record:year");
	yearObj.innerHTML = "";
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth();
	for(var i = parseInt(year-1); i <= year; i++){
		var option = document.createElement("option");
		option.value = i;
		option.appendChild(document.createTextNode(i+"��"));
		if(year == i){
			option.selected = true;
		}
		yearObj.appendChild(option);
	}
	//��ʼ���·�
    var monthObj = document.getElementById("record:month");
    monthObj.options.length=0;
	for(var i = 1; i <= month; i++){
			var option = document.createElement("option");
			option.appendChild(document.createTextNode(months[i-1]+"��"));
			if(i < 10){
				i = ""+i;
			}
			option.value = i;
			if(month == i){
				option.selected = true;
			}
			monthObj.appendChild(option);
	  }
	if(month!='0'){
		var reportName ='ϵͳʹ���������'+year+'��'+month+'���±�';
		setFormFieldValue("record:report_name",reportName);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����ϵͳʹ�����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn620200103">
  <freeze:block property="record" caption="����ϵͳʹ�����������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="log_report_create_id" caption="��־����id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="report_type" caption="��������" colspan="2" value="1" valueset="��������" style="width:95%" onchange="setReportName();"/>
      <freeze:select property="year" caption="�������" style="width:88%" onchange="changeYear();"/>
      <freeze:select property="month" caption="�����·�" style="width:88%" onchange="changeMonth();"/>
      <freeze:text property="report_name" caption="��������" colspan="2" style="width:95%" readonly="true"/>
      <freeze:hidden property="create_date" caption="��������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="last_mender" caption="����޸���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="publish_date" caption="��������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="publish_person" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="state" caption="״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="operate" caption="����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="filename" caption="�ļ���" maxlength="1000" style="width:98%"/>
      <freeze:hidden property="path" caption="·��" maxlength="4000" style="width:98%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>
