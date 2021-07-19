<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
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
%>
<script language="javascript">

// ���ӷ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_svr_config.jsp", "���ӷ�������");
	page.addRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:sys_svr_config_id");
	for(var i=0; i<ids.length; i++){
	   document.getElementsByName("span_record:oper")[i].innerHTML +='<a href="#" onclick="func_query_config('+i+')">����</a>';
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

function func_query_config(idx){
	var svrId = getFormFieldValue("record:sys_svr_service_id", idx);
	var usrId = getFormFieldValue("record:sys_svr_user_id", idx);
	var page = new pageDefine( "detail-sys_svr_service.jsp", "��ѯ����������Ϣ" );
	page.addValue(svrId, "sys_svr_service_id");
	page.addValue(usrId, "sys_svr_user_id");
	page.addParameter("select-key:login_name", "select-key:login_name");
	page.addParameter("select-key:sys_svr_user_id", "select-key:sys_svr_user_id");
	page.addParameter("select-key:svr_code", "select-key:svr_code");
	page.addParameter("select-key:sys_svr_service_id", "select-key:sys_svr_service_id");
	page.addParameter("select-key:create_date_from", "select-key:create_date_from");
	page.addParameter("select-key:create_date_to", "select-key:create_date_to");
	page.addParameter("select-key:state", "select-key:state");
	page.goPage();
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��������б�"/>
<freeze:errors/>

<freeze:form action="/txn50204001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="login_name" caption="�û�����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="sys_svr_user_id" caption="�û�����"  style="width:95%"/>
      <freeze:text property="svr_code" caption="�������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="sys_svr_service_id" caption="��������" style="width:95%"/>
      <freeze:datebox property="create_date_from" caption="����������"  numberformat="1" style="width:95%"/>
      <freeze:datebox property="create_date_to" caption="����������"  numberformat="1" style="width:95%"/>
      <freeze:select property="state" caption="�û�״̬" valueset="��������û�״̬GXFW" style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="��������б�" keylist="sys_svr_config_id" width="95%" checkbox="false" rowselect="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="50203003" enablerule="1" hotkey="ADD" align="right" onclick="func_query_config();" visible="false"/>
      <freeze:hidden property="sys_svr_config_id" caption="����������ñ���" style="width:15%" />
      <freeze:hidden property="sys_svr_service_id" caption="����������" style="width:15%" />
      <freeze:hidden property="sys_svr_user_id" caption="����������" style="width:15%" />
      <freeze:cell property="svr_code" caption="�������" style="width:15%"  />
      <freeze:cell property="svr_name" caption="��������" style="width:15%"  />
      <freeze:cell property="user_name" caption="�û�����" style="width:15%"  />
      <freeze:cell property="login_name" caption="�û�����" style="width:15%"  />
      <freeze:cell property="state" caption="�û�״̬" valueset="��������û�״̬GXFW" style="width:5%"  />
      <freeze:cell property="create_by" caption="���񴴽���" style="width:10%"  />
      <freeze:cell property="create_date" caption="���񴴽�����" style="width:10%"  />
      <freeze:cell property="oper" caption="����" align="center" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
