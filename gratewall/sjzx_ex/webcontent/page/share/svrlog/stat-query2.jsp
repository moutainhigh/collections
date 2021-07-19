<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>���ݽ���ͳ��</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="���ݽ���ͳ��"/>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String execute_type = "";
	if(db != null){
		execute_type = db.getRecord("select-key").getValue("execute_type");
	}
%>
<freeze:errors/>
<script language="javascript">

function createOption(optionValue,optionText){
	var oOption = document.createElement("option");
	oOption.value = optionValue;
	oOption.text = optionText;
	oOption.title = optionText;
	return oOption;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	var execute_typeObj = document.getElementById("select-key:execute_type");
	if(execute_typeObj){
		execute_typeObj.options.add(createOption("�ṩ","�ṩ"));
		execute_typeObj.options.add(createOption("�ɼ�","�ɼ�"));
	}
	
	var execute_type = "<%= execute_type == null ? "" : execute_type%>";
   	if(execute_type){
 		if(execute_typeObj.options.length != 0){
   			for(var i = 0; i< execute_typeObj.options.length; i++){
   				if(execute_typeObj.options[i].value == execute_type){
   					execute_typeObj.options[i].selected = true;
   				}
   			}
   		}
   	}
}

function func_doQuery(){
	var startObj = document.getElementById("select-key:execute_start_time");	
	var endObj = document.getElementById("select-key:execute_end_time");
	if(!checkInput(startObj,'����ʼʱ�䡿','3',false)||!checkInput(endObj,'������ʱ�䡿','3',false)){
	    return;
	}
	var start = startObj.value;
	var end = endObj.value;
	if(start){
		var s_date = new Date();
		var currYear = s_date.getYear();
		var currMonth = s_date.getMonth() + 1;
		var currDay = s_date.getDate();
		var today = currYear+'-';
		if(currMonth>9){
	    today += currMonth+'-';
		}else{
	    today += '0'+currMonth+'-';
		}
		if(currDay>9){
	    today += currDay;
		}else{
	    today += '0'+currDay;
		}
		if(start>today){
            alert("����ʼʱ�䡿��ʼ���ڲ��ܳ������죡");
            document.getElementById("select-key:execute_start_time").select();
            return false;
		}		
	}
	
	if(start&&end){
        if(start>end){
            alert("������ʱ�䡿�������ڱ��������ʼʱ�䣡");
            document.getElementById("select-key:execute_end_time").select();
            return false;
        }		
	}
	
	
  var page = new pageDefine( "/txn50207004.do", "���ݽ���ͳ��");
  page.addValue(getFormFieldText("select-key:user_id"),"select-key:user_id");
  page.addParameter("select-key:execute_start_time","select-key:execute_start_time");
  page.addParameter("select-key:execute_end_time","select-key:execute_end_time");
  page.addValue(getFormFieldText("select-key:sys_svr_service_id"),"select-key:sys_svr_service_id");
  page.addParameter("select-key:execute_type","select-key:execute_type");
  page.goPage( );
}

function reset(){
	setFormFieldValue("select-key:user_id", 0, "");
	setFormFieldValue("select-key:execute_start_time", 0, "");
	setFormFieldValue("select-key:execute_end_time", 0, "");
	setFormFieldValue("select-key:_tmp_sys_svr_service_id", 0, "");
	setFormFieldValue("select-key:execute_type", 0, " ");
}

function queryUserServices(){
	var user_id = getFormFieldValue("select-key:user_id");
	if(!user_id){
		return;
	}else{
		var param = 'userbianma='+user_id;
		return param;
	}
}

function __clear(){
	setFormFieldValue("select-key:sys_svr_service_id", 0, "");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn50207004">
  <freeze:block property="select-key" caption="��ѯ����" width="95%">
      <freeze:button name="record_addRecord" caption=" �� ѯ " enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
      <freeze:button name="record_resetRecord" caption=" �� �� " enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
      <freeze:browsebox property="user_id" caption="�û�����" style="width:90%" valueset="���ݽ�������" show="name" onchange="__clear();" />
      <freeze:browsebox property="sys_svr_service_id" caption="��������" style="width:90%" valueset="���ݽ�������" parameter="queryUserServices()" show="name" />
      <freeze:datebox property="execute_start_time"  caption="��ʼʱ��" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="execute_end_time" caption="��ʼʱ��" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table>
      <freeze:select property="execute_type" caption="��������" style="width:90%" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="���ݽ���ͳ���б�" width="95%" keylist="sys_svr_user_name" rowselect="false" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="@rowid" caption="���" align="middle" style="width:5%"/>
    <freeze:cell property="sys_svr_user_name" caption="�û�����" width="30%" />
    <freeze:cell property="sys_svr_service_name" caption="��������" width="30%" />
    <freeze:cell property="records_mount" caption="���ؼ�¼��"  align="middle" width="10%"/>
    <freeze:cell property="query_times" caption="��ѯ����" width="10%"/>
    <freeze:cell property="execute_type" caption="��������" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
