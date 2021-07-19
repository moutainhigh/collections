<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������ѯ�б�</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<% 
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	String selectKeySysName = context.getRecord("select-key").getValue("sys_name");
	String selectKeyTableName  = context.getRecord("select-key").getValue("table_name_cn");
	String classState  = context.getRecord("select-key").getValue("class_state");
%>
</head>

<script language="javascript">

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var selectObject = document.getElementById("select-key:sys_name");
	selectObject.onchange = function(){
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
		setStateObj();
	}
	var tblObj = document.getElementById("select-key:table_name_cn");
	tblObj.onchange = function(){
		setStateObj();
	}
	
	initClassState();
	
	queryAllZT();
	var nos=document.getElementsByName('span_record:no');
	var currentPage="<freeze:out value='${attribute-node.record_start-row}'/>";
	for(var i=0;i<nos.length;i++){
	    nos[i].innerHTML=parseInt(currentPage)+i;
	}
	
	//var countDate = getFormFieldValue("select-key:count_date");
	//document.getElementById("label:record:count_incre").innerText = countDate + " ����";
	//document.getElementById("label:record:count_full").innerText = countDate + " ȫ��";
}

function initClassState(){
	var class_state = document.getElementById("select-key:class_state");
	var selectKeyState = '<%=classState%>';
	var option = document.createElement("option");
    option.value = "1";
    var text = document.createTextNode("��ҵ");
	option.appendChild(text);
	if(selectKeyState == '1'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	var option = document.createElement("option");
    option.value = "2";
    var text = document.createTextNode("����");
	option.appendChild(text);
	if(selectKeyState == '2'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	var option = document.createElement("option");
    option.value = "3";
    var text = document.createTextNode("ע��");
	option.appendChild(text);
	if(selectKeyState == '3'){
		option.selected = true;
	}
	class_state.appendChild(option);
	
	
}

//��ѯ����������Ϣ
function queryAllZT(){
	$.get("<%=request.getContextPath()%>/txn50202006.ajax", function(xml){
		var selectObject = document.getElementById("select-key:sys_name");
		if (selectObject){
			//�������������
			selectObject.innerHTML = "";
			//����Ĭ��ѡ��
			var option = document.createElement("option");
			option.value = "";
			var text = document.createTextNode("ȫ��");
			option.sysname = '';
	        option.appendChild(text);
	        selectObject.appendChild(option);
	        
	        //ѭ���������������Ϣ
			var nodeArray = xml.selectNodes("//record");
			for (var i = 0; i < nodeArray.length; i++){
	            var nodeElement = nodeArray.item(i);
	            var optionValue = nodeElement.selectSingleNode("sys_id");
	            var optionText = nodeElement.selectSingleNode("sys_name");
	            var option = document.createElement("option");
	            option.value = optionText.text;
	            var text = document.createTextNode(optionText.text);
	            if('<%=selectKeySysName%>' == optionText.text){
	            	option.selected = true;
	            }
				option.sysname = optionValue.text;
	            option.appendChild(text);
	            selectObject.appendChild(option);
            }
		}
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
	});
}

function queryZTTables(ztParam){
	if(ztParam != ''){
		$.get("<%=request.getContextPath()%>/txn60210008.ajax?record:sys_id=" + ztParam, function(xml){
			var selectObject = document.getElementById("select-key:table_name_cn");
			if (selectObject){
				//�������������
				selectObject.innerHTML = "";
				//����Ĭ��ѡ��
				var option = document.createElement("option");
				option.value = "";
				var text = document.createTextNode("ȫ��");
		        option.appendChild(text);
		        selectObject.appendChild(option);
		        
		        //ѭ��������б���Ϣ
				var nodeArray = xml.selectNodes("//record");
				for (var i = 0; i < nodeArray.length; i++){
		            var nodeElement = nodeArray.item(i);
		            var optionValue = nodeElement.selectSingleNode("table_no");
		            var optionText = nodeElement.selectSingleNode("table_name_cn");
		            var option = document.createElement("option");
		            option.value = optionText.text;
		            var text = document.createTextNode(optionText.text);
		            if('<%=selectKeyTableName%>' == optionText.text){
		            	option.selected = true;
		            }
		            option.appendChild(text);
		            selectObject.appendChild(option);
	            }
			}
		});
	}
}

function func_doQuery(){
	var date = getFormFieldValue("select-key:count_date");
	if(date.trim() == ''){
		alert("��ѡ��ͳ�����ڡ�");
		return;
	}
	document.forms[0].submit();
}

function reset(){
	document.forms[0].reset();
	var date = new Date();
	
	setFormFieldValue("select-key:count_date",' ');
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��������ѯ�б�"/>
<freeze:errors/>

<freeze:form action="/txn81200001">
  <freeze:block property="select-key" caption="��ѯ����" width="95%">
      <freeze:button name="record_addRecord" caption=" �� ѯ " txncode="60210003" enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
      <freeze:button name="record_addRecord" caption=" �� �� " txncode="60210003" enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
      <freeze:select property="sys_name" caption="��������" style="width:90%"/>
      <freeze:select property="table_name_cn" caption="��������" style="width:90%"/>
      <freeze:datebox property="count_date" caption="ͳ������" numberformat="1" style="width:90%" />
      <freeze:select property="class_state" caption="��ҵ״̬" style="width:90%" />
  </freeze:block>
  <br/>

	   <freeze:grid property="record" caption="�������б�" width="95%" keylist="ad_mon_bc_rept_id" 
	             checkbox="false" navbar="bottom" >
	   <freeze:button name="record_addRecord" caption="����"  enablerule="0" hotkey="ADD" align="right" onclick="toExport();"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
       <freeze:button name="record_updateRecord" caption="����"  enablerule="0" hotkey="UPDATE" align="right" onclick="func_record_goBack();"/>

	      <freeze:cell property="no" caption="���" style="width:5%" align="center" />
	      <freeze:cell property="sys_name" caption="��������" style="width:15%" />
	      <freeze:cell property="table_name_cn" caption="��������" style="width:10%" />
	      <freeze:cell property="table_name" caption="����" style="width:15%" />
	      <freeze:cell property="class_sort" caption="��������" style="width:7%" />
	      <freeze:cell property="class_state" caption="����״̬" style="width:7%" />
	      <freeze:cell property="count_date" caption="ͳ������" style="width:10%" />
	      <freeze:cell property="count_full" caption="����ȫ��" style="width:7%" />   
	      <freeze:cell property="count_incre" caption="��������" style="width:7%" />    
	  </freeze:grid>
  
</freeze:form>
<form method="post" action="<%=request.getContextPath()%>/sysmgr/datecount/query-view_sys_count_semantic_his_download.jsp" target="_blank" >
	<input type="hidden" id="htmlStr" name="htmlStr" />
</form>
</freeze:body>
<script language="javascript">
function setStateObj(){
	var sysname = getFormFieldValue("select-key:sys_name");
	var tblname = getFormFieldValue("select-key:table_name_cn");
	var sysArray = ['��ҵ�Ǽ�����','����Ǽ�����','���˿�����'];
	var tblArray = ['��ҵ(����)','��Ҫ��Ա','Ͷ����','���幤�̻�������','�ʼ෨�˿�'];
	
	if(sysname != ''){//������ⲻ�ǡ�ȫ����
		if(exist(sysArray, sysname)){//�ж��Ƿ�����״̬������
			if(tblname != ''){//������ǡ�ȫ����
				if(exist(tblArray, tblname)){//�ж��Ƿ�����״̬�ı�
					setFormFieldDisabled("select-key:class_state", 0, false);
				}else{
					setFormFieldDisabled("select-key:class_state", 0, true);
				}
			}else{
				setFormFieldDisabled("select-key:class_state", 0, false);
			}
		}else{
			setFormFieldDisabled("select-key:class_state", 0, true);
		}
	}else{
		setFormFieldDisabled("select-key:class_state", 0, false);
	}
}

function exist(arrayObj, findValue){
	if(arrayObj){
		for(var i = 0; i < arrayObj.length; i++){
			if(findValue == arrayObj[i]){
				return true;
			}
		}
	}
	return false;
}

function toExport(){
    clickFlag=0;
    document.forms[0].action   = '/txn81200006.do';
    document.forms[0].submit();
}

// �� ��
function func_record_goBack()
{
    clickFlag=0;
	//goBack();	// /txn80002501.do
	var page = new pageDefine( "/txn62010030.do", "��������ͳ��" );
	page.updateRecord();
}
</script>
</freeze:html>
