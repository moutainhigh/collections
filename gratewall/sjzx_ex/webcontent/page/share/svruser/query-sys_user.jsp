<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����û��б�</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
</head>

<script language="javascript">

// ���ӷ������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_svr_user.jsp", "���ӷ������", "modal" , 800, 350);
	page.addRecord();
}

// �޸ķ������
function func_record_updateRecord(idx)
{
	//var index = getSelectedRowid("record");
	var id = getFormFieldValue("record:sys_svr_user_id", idx);
	var page = new pageDefine( "/txn50201004.do", "�޸ķ������", "modal" , 800, 350 );
	page.addValue( id, "record:sys_svr_user_id" );
	page.updateRecord();
}

// ɾ���������
function func_record_deleteRecord()
{
	var index = getSelectedRowid("record");
	var canDelete = true;//����Ѿ������˷����򲻿���ɾ��
	var id = getFormFieldValues("record:sys_svr_user_id");
	
	$.get("<%=request.getContextPath()%>/txn50201007.ajax?record:sys_svr_user_id="+id, function(xml){
		var errCode = _getXmlNodeValue( xml, "/context/error-code" );
		if(errCode != "000000"){
		    alert("�������" + _getXmlNodeValue( xml, "/context/error-desc" ));
		}else{
    		var cfgid = _getXmlNodeValue( xml, "record:sys_svr_config_id" );
    		if(cfgid){
    			alert("�û��Ѿ������÷��񣬽�ֹɾ����");
				document.SysSvrUserRowsetForm.submit();
    		}else{
				var name = getFormFieldValues("record:login_name");
				var page = new pageDefine( "/txn50201005.do", "ɾ���������" );
				page.addParameter( "record:sys_svr_user_id", "record:sys_svr_user_id" );
				//page.addValue( name, "record:login_name" );
				page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
				clickFlag = 0;
				checkAllMenuItem();
				checkButtonByData();
			}
		}
	});
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var states = getFormAllFieldValues("record:state");
	var names = getFormAllFieldValues("record:user_name");
	var password = getFormAllFieldValues("record:password");
	var login_name = getFormAllFieldValues("record:login_name");
	var ip_bind = getFormAllFieldValues("record:ip_bind");
	var ids = getFormAllFieldValues("record:sys_svr_user_id");
	for(var i=0;i<ids.length;i++){
	   document.getElementsByName("span_record:oper")[i].innerHTML +='<a href="#" title="�޸�" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>'+
	   '&nbsp;&nbsp;<a href="#" title="����" onclick="func_viewConfig(\''+ids[i]+'\',\''+states[i]+'\',\''+names[i]+'\',\''+password[i]+'\',\''+ip_bind[i]+'\',\''+login_name[i]+'\');"><div class="config"></div></a>';
	}
	
	var stateTds = document.getElementsByName("span_record:state");//"span_record:state");
	for(var i=0; i<states.length; i++){
		if(states[i] == '1'){
			// stateTds[i].setAttribute("align","right");
			stateTds[i].style.textAlign = 'right';
		}
	}
	
	checkButtonByData();
	
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}

function func_viewConfig(uid, state, name, password,ip_bind, login_name){
	if(state == "0"){
		var page = new pageDefine( "/txn50200011.do", "�û����񵼺���" );
		//tree-sys_svr.jsp
		page.addValue(uid, "info-base:sys_svr_user_id");
		page.addValue(name, "info-base:user_name");
		page.addValue(password, "info-base:password");
		page.addValue(ip_bind, "info-base:ip_bind");
		page.addValue(login_name, "info-base:login_name");
		page.goPage();
		//window.location.href="tree-sys_svr.jsp?sys_svr_user_id="+uid+"&name="+name+"&password="+password+"ip_bind="+ip_bind+"login_name"+login_name;
	}else{
		alert("��ǰ�û��ѱ�ͣ�ã��������ô��û���");
	}
}

function startUser(){
	var id = getFormFieldValues("record:sys_svr_user_id");
	var name = getFormFieldValues("record:login_name");
	$.get("<%=request.getContextPath()%>/txn50201006.ajax?record:sys_svr_user_id=" + id + "&record:state=0&record:login_name="+name, function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
			alert("�������"+xml.selectSingleNode("//context/error-desc").text);
			return;
		}
		//document.SysSvrUserRowsetForm.submit();
		window.location='/txn50200001.do';
	});
}

function stopUser(){
	var id = getFormFieldValues("record:sys_svr_user_id");
	var name = getFormFieldValues("record:login_name");
	$.get("<%=request.getContextPath()%>/txn50201006.ajax?record:sys_svr_user_id=" + id + "&record:state=1&record:login_name="+name, function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
			alert("�������"+xml.selectSingleNode("//context/error-desc").text);
			return;
		}
		window.location='/txn50200001.do';
	});
}

function editButton(){
	var index = getSelectedRowid("record");
	var state = getFormFieldText("record:state",index);
	
	if(state == '0'){
		document.getElementById("record_record_stopRecord").disabled = false;
		document.getElementById("record_record_startRecord").disabled = true;
		document.getElementById("record_record_updateRecord").disabled = false;
	}else{
		document.getElementById("record_record_stopRecord").disabled = true;
		document.getElementById("record_record_startRecord").disabled = false;
		document.getElementById("record_record_updateRecord").disabled = false;
	}
}

function func_record_initPwd(){
	var id = getFormFieldValues("record:sys_svr_user_id");
	var name = getFormFieldValues("record:login_name");
	$.get("<%=request.getContextPath()%>/txn50201010.ajax?record:sys_svr_user_id=" + id + "&record:password=222222&record:login_name="+name, function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
			alert("�������"+xml.selectSingleNode("//context/error-desc").text);
			return;
		}
		alert("�����ɹ���");
		document.SysSvrUserRowsetForm.submit();
	});
}

_browse.execute( '__userInitPage()' );


function checkButtonByData(){
    
	var sfyxArray = getFormFieldValues("record:state");	
	if(sfyxArray==""){return;}
	var sFlag = false;
	var eFlag = false;
	
	for( var i=0;i<sfyxArray.length;i++){
	   if(!sFlag){
		   if(sfyxArray[i]=="1"){
		      document.getElementById("record_record_stopRecord").disabled = "-1";
		      sFlag = true;
		   }
	   }
	   if(!eFlag){
		   if(sfyxArray[i]=="0"){
		      document.getElementById("record_record_startRecord").disabled = "-1";
		      eFlag = true;
		   }
	   }	
	}
	if(!sFlag){
       document.getElementById("record_record_stopRecord").removeAttribute("disabled");
	}
	if(!eFlag){
       document.getElementById("record_record_startRecord").removeAttribute("disabled");
	}
}

_browse.execute(function(){
	var gridDataTable = document.getElementById("record");
	for ( var i = 1; i < gridDataTable.rows.length ; i++ ){
		gridDataTable.rows[i].attachEvent("onclick", checkButtonByData);
	}
	var lab= document.getElementById("record:_select-all");
	if(lab){
	    if(window.addEventListener) { 
			 lab.addEventListener("onclick", checkButtonByData, false); 
		} else if(window.attachEvent) { 
			  lab.attachEvent("onclick", checkButtonByData); 
	    } 
	}
});
</script>
<freeze:body>
<freeze:title caption="�����û��б�"/>
<freeze:errors/>

<freeze:form action="/txn50200001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
  	  <freeze:browsebox property="sys_svr_user_id" caption="�û�����"  valueset="�����û�����" show="name" style="width:90%"/>
      <freeze:text property="login_name" caption="�û��˺�" datatype="string" maxlength="20" style="width:90%"/>
      <freeze:select property="user_type" caption="�û�����" valueset="user_type" show="name" style="width:90%"/>
      <freeze:select property="state" caption="�û�״̬" valueset="��������û�״̬GXFW" style="width:90%"/>
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="�����û��б�" keylist="sys_svr_user_id" width="95%" navbar="bottom" multiselect="false" fixrow="false" rowselect="false">
      <freeze:button name="record_addRecord" caption="����"  enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_startRecord" caption="����"  enablerule="2" hotkey="UPDATE" align="right" onclick="startUser();"/>
      <freeze:button name="record_stopRecord" caption="ͣ��"  enablerule="2" hotkey="UPDATE" align="right" onclick="stopUser();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��"  enablerule="2" hotkey="DELETE" align="right" visible="true" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_initPwd" caption="�����ʼ��"  enablerule="2" hotkey="DELETE" align="right" onclick="func_record_initPwd();" visible="false"/>
      <freeze:hidden property="sys_svr_user_id" caption="���������"/>
      <freeze:hidden property="state" caption="״̬"/>
      <freeze:hidden property="password" caption="����"/>
      <freeze:hidden property="login_name" />
      <freeze:cell property="user_name" caption="�û�����" style="width:20%"  />
      <freeze:cell property="user_type" caption="�û�����" style="width:10%"  valueset="user_type" />
      <freeze:cell property="svr_num" caption="���������" align="center" style="width:10%" />
      <freeze:cell property="clt_num" caption="�ɼ������" align="center" style="width:10%" />
      <freeze:cell property="state" style="width:10%" valueset="��������û�״̬GXFW" caption="״̬"  align="right"/>
      <freeze:cell property="ip_bind" caption="��IP" style="width:15%"  align="center"/>
      <freeze:cell property="oper" caption="����" align="center" style="width:10%" />
     
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
