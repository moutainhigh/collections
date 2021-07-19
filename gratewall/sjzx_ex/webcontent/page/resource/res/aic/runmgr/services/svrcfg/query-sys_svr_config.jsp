<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
</head>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String userId = db.getRecord("select-key").getValue("sys_svr_user_id");
	String svrId = db.getRecord("select-key").getValue("sys_svr_service_id");
%>
<script language="javascript">
window.onload = function(){
	
}

// ���ӷ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_svr_config.jsp", "���ӷ�������");
	page.addParameter("record:sys_svr_config_id","record:sys_svr_config_id");
	page.addParameter("record:sys_svr_user_id","record:sys_svr_user_id");
	page.addParameter("record:sys_svr_service_id","record:sys_svr_service_id");
	page.addRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var states = getFormAllFieldValues("record:state");
	var stateTds = document.getElementsByName("span_record:state");//"span_record:state");
	for(var i=0; i<states.length; i++){
		if(states[i] == '1'){
			// stateTds[i].setAttribute("align","right");
			stateTds[i].style.textAlign = 'right';
		}else{
			stateTds[i].style.textAlign = 'left';
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
			
function func_record_deleteRecord()
{
	var name = getFormFieldValues("record:svr_name");
	var uname = getFormFieldValues("record:user_name");
	var page = new pageDefine( "/txn50203005.do", "ɾ����������" );
	page.addParameter( "record:sys_svr_config_id", "select-key:sys_svr_config_id" );
	page.addValue( name, "select-key:svr_name" );
	page.addValue( uname, "select-key:user_name" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

function locateOption(obj, v){
	if(v != "null"){
		var selObj = document.getElementById(obj);
		for(var i = 0; i < selObj.options.length; i++){
			var a = selObj.options[i].value;
			//alert(obj+": "+a+" == "+v+" ==>"+(a == v));
			if(a == v){
				selObj.options[i].selected = true;
				break;
			}else{
				selObj.options[i].selected = false;
			}
		}
	}
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
<freeze:title caption="�����б�"/>
<freeze:errors/>

<freeze:form action="/txn50203001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="login_name" caption="�û�����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="sys_svr_user_id" caption="�û�����"  style="width:95%"/>
      <freeze:text property="svr_code" caption="�������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="sys_svr_service_id" caption="��������" style="width:95%"/>
      <freeze:datebox property="create_date_from" caption="����������"  numberformat="1" style="width:95%"/>
      <freeze:datebox property="create_date_to" caption="����������"  numberformat="1" style="width:95%"/>
      <freeze:select property="state" caption="״̬" valueset="��������û�״̬GXFW" style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="�����б�" keylist="sys_svr_config_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="50203003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="50203005" align="center" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_svr_config_id" caption="����������ñ���" style="width:15%" />
      <freeze:hidden property="sys_svr_user_id" caption="����������" style="width:15%" />
      <freeze:hidden property="sys_svr_service_id" caption="����������" style="width:15%" />
      <freeze:hidden property="state" caption="״̬"/>
      <freeze:cell property="svr_code" caption="�������" style="width:15%"  />
      <freeze:cell property="svr_name" caption="��������" style="width:15%"  />
      <freeze:cell property="user_name" caption="�û�����" style="width:15%"  />
      <freeze:cell property="login_name" caption="�û�����" style="width:15%"  />
      <freeze:cell property="state" caption="�û�״̬" valueset="��������û�״̬GXFW" style="width:5%"  />
      <freeze:cell property="create_date" caption="��������" style="width:10%"  />
      <freeze:cell property="create_by" caption="������" style="width:10%"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
<script language="javascript">


</script>
</freeze:html>
