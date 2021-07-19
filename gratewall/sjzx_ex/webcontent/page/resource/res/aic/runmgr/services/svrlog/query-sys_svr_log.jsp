<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ������־�б�</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String userId = db.getRecord("select-key").getValue("sys_svr_user_id");
	String svrId = db.getRecord("select-key").getValue("sys_svr_service_id");
	String state = db.getRecord("select-key").getValue("state");
%>
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
	var stateObj = document.getElementById("select-key:state");
	if(stateObj){
		stateObj.options.add(createOption("�ɹ�","�ɹ�"));
		stateObj.options.add(createOption("ʧ��","ʧ��"));
	}
	
	var state = "<%= state == null ? "" : state%>";
   	if(state){
 		if(stateObj.options.length != 0){
   			for(var i = 0; i< stateObj.options.length; i++){
   				if(stateObj.options[i].value == state){
   					stateObj.options[i].selected = true;
   				}
   			}
   		}
   	} 
	
	$.get("<%= request.getContextPath()%>/txn50201001.ajax?select-key:showall=true&select-key:state=0", function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
	      alert("�������"+errDesc);
	      return;
	    }else{
	    	var userSelect = document.getElementById("select-key:sys_svr_user_id");
	    	var users = xml.selectNodes("//record");
	    	for(var i = 0; i < users.length; i++){
	    		var uId = users[i].selectSingleNode("sys_svr_user_id").text;
	    		var uName = users[i].selectSingleNode("user_name").text;
	    		userSelect.options.add(createOption(uId, uName));
	    	}
	    	var userId = "<%= userId == null ? "" : userId%>";
	    	if(userId){
	    		if(userSelect.options.length != 0){
	    			for(var i = 0; i< userSelect.options.length; i++){
	    				if(userSelect.options[i].value == userId){
	    					userSelect.options[i].selected = true;
	    				}
	    			}
	    		}
	    	} 
	    	//��ѯ���з���
	    	$.get("<%= request.getContextPath()%>/txn50202001.ajax?select-key:showall=true", function(xml){
				if(errCode != '000000'){
			      alert("�������"+errDesc);
			      return;
			    }else{
			    	var svrSelect = document.getElementById("select-key:sys_svr_service_id");
			    	var svrs = xml.selectNodes("//record");
			    	for(var i = 0; i < svrs.length; i++){
			    		var sId = svrs[i].selectSingleNode("sys_svr_service_id").text;
	    				var sName = svrs[i].selectSingleNode("svr_name").text;
			    		svrSelect.options.add(createOption(sId, sName));
			    	}
			    	
			    	var svrId = "<%= svrId == null ? "" : svrId%>";
			    	if(svrId){
			    		if(svrSelect.options.length != 0){
			    			for(var i = 0; i< svrSelect.options.length; i++){
			    				if(svrSelect.options[i].value == svrId){
			    					svrSelect.options[i].selected = true;
			    				}
			    			}
			    		}
			    	}
				}
			});
	    }
	});
}

function createOption(v,t){
  var oOption = document.createElement("option");
  oOption.value = v;
  oOption.text = t;
  oOption.title = t;
  return oOption;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ������־�б�"/>
<freeze:errors/>

<freeze:form action="/txn50207001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_svr_user_id" caption="�û�����" style="width:95%"/>
      <freeze:select property="sys_svr_service_id" caption="��������" style="width:95%"/>
      <freeze:datebox property="execute_start_time" caption="��ʼʱ��" datatype="string" numberformat="1" style="width:95%"/>
      <freeze:datebox property="execute_end_time" caption="����ʱ��" datatype="string"  numberformat="1" style="width:95%"/>
      <freeze:select property="state" caption="��ѯ״̬" style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="������־�б�" keylist="sys_svr_log_id" width="95%" checkbox="false" rowselect="false" navbar="bottom" fixrow="false">
      <freeze:cell align="middle" property="@rowid" caption="���" style="width:5%"/>
      <freeze:hidden property="sys_svr_log_id" caption="������־���" style="width:10%" />
      <freeze:cell property="sys_svr_user_name" caption="�������" style="width:15%" />
      <freeze:cell property="sys_svr_service_name" caption="��������" style="width:15%" />
      <freeze:cell property="execute_start_time" caption="ִ�п�ʼʱ��" datatype="date" style="width:15%" />
      <freeze:cell property="execute_end_time" caption="ִ�н���ʱ��" datatype="date" style="width:15%" />
      <freeze:cell property="state" caption="ִ�н��" style="width:5%" />
      <freeze:cell property="records_mount" caption="���ؼ�¼��" style="width:10%" />
      <freeze:cell property="error_msg" caption="��������" style="width:10%" />
      <freeze:cell property="client_ip" caption="�ͻ���IP" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
