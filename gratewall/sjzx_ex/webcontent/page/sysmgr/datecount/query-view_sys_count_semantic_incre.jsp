<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>Ԥ����������</title>
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
%>

</head>

<script language="javascript">

function func_record_updateRecord(idx)
{
	var id = getFormFieldValue("record:table_class_id", idx);
	var page = new pageDefine( "/txn81200004.do", "��ѯԤ�����������޸�", "modal" , 650, 350 );
	page.addValue( id, "primary-key:table_class_id" );
	page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var selectObject = document.getElementById("select-key:sys_name");
	selectObject.onchange = function(){
		queryZTTables(selectObject.options[selectObject.selectedIndex].sysname);
	}
	queryAllZT();
	
	var ids = getFormAllFieldValues("record:table_class_id");
	for(var i=0;i<ids.length;i++){
	   document.getElementsByName("span_record:oper")[i].innerHTML +='<a href="#" onclick="func_record_updateRecord('+i+')">�޸�</a>';
	}
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

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="Ԥ����������"/>
<freeze:errors/>

<freeze:form action="/txn81200003">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_name" caption="��������" style="width:90%"/>
      <freeze:select property="table_name_cn" caption="��������" style="width:90%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="Ԥ�������б�" keylist="table_class_id" width="95%" navbar="bottom" fixrow="false" checkbox="false" multiselect="false">
      <freeze:hidden property="table_class_id" caption="�������"/>
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>
      <freeze:cell property="table_name_cn" caption="��������" style="width:20%" />
      <freeze:cell property="table_name" caption="����" style="width:20%" />
      <freeze:cell property="class_sort" caption="��������" style="width:10%" />
      <freeze:cell property="class_state" caption="����״̬" style="width:10%" />
      <freeze:cell property="min_value" caption="����ֵ" align="middle" style="width:10%" />
      <freeze:cell property="max_value" caption="����ֵ" align="middle" style="width:10%" />
      <freeze:cell property="oper" caption="����" align="middle" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
