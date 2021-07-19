<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�û��б�</title>
<style type="text/css">
.odd2_b {
	white-space: nowrap;
}
</style>
</head>
<freeze:body>
<freeze:title caption="�û��б�"/>
<freeze:errors/>

<script language="javascript">
// �½��û�
function func_record_addRecord(){
  var param = "record:jgid_fk="+document.getElementById("select-key:jgid_fk").value;
  param += "&record:jgname="+document.getElementById("select-key:jgname").value;
  var page = new pageDefine( "org-insert-yhgl.jsp?"+param, "�����û�", "modal", 650, 400);
  page.addRecord( );
}

// �޸��û�
function func_record_updateRecord(){
  var page = new pageDefine( "/txn807014.do", "�޸��û�", "modal", 650, 400);
  page.addParameter("record:yhid_pk","primary-key:yhid_pk");
  page.addParameter("record:jgid_fk","primary-key:jgid_fk");
  page.updateRecord( );

}

// ע���û�
function func_record_deleteRecord(){
  var page = new pageDefine( "/txn807005.do", "ע���û�");
  page.addParameter("record:yhid_pk","primary-key:yhid_pk");
  page.deleteRecord("�Ƿ�ע��ѡ�е��û���");
}

_browse.execute(function(){	
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'>�޸�</a>";
	}	
	var states = getFormAllFieldValues("record:sfyx");
	var stateTds = document.getElementsByName("span_record:sfyx");
	for(var i=0; i<states.length; i++){
		if(states[i] == '1'){
			stateTds[i].style.textAlign = 'right';
		}
	}		
});


function checkButtonByData(){
    
	var sfyxArray = getFormFieldValues("record:sfyx");	
	if(sfyxArray==""){return;}
	var sFlag = false;
	var eFlag = false;
	
	for( var i=0;i<sfyxArray.length;i++){
	   if(!sFlag){
		   if(sfyxArray[i]=="0"){
		      document.getElementById("record_record_deleteRecord_s").disabled = "-1";
		      sFlag = true;
		   }
	   }
	   if(!eFlag){
		   if(sfyxArray[i]=="1"){
		      document.getElementById("record_record_deleteRecord_e").disabled = "-1";
		      eFlag = true;
		   }
	   }	
	}
	if(!sFlag){
       document.getElementById("record_record_deleteRecord_s").removeAttribute("disabled");
	}
	if(!eFlag){
       document.getElementById("record_record_deleteRecord_e").removeAttribute("disabled");
	}
}

</script>

<freeze:form action="/txn807011">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="100%" >
    <freeze:text property="yhzh" caption="�û��˺�" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:text property="yhxm" caption="�û�����" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:hidden property="jgid_fk"/>
    <freeze:hidden property="jgname"/>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="�û��б�" keylist="yhid_pk" width="100%" navbar="bottom" rowselect="false" fixrow="false" checkbox="false">
    <%--
    <freeze:button name="record_addRecord" caption="�����û�" txncode="807013" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
    <freeze:button name="record_updateRecord" caption="�޸��û�" txncode="807014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
    <freeze:button name="record_deleteRecord" caption="ע���û�" txncode="807005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>  
     --%>   
    <freeze:hidden property="yhid_pk" caption="�û�ID" style="width:10%"/>
    <freeze:hidden property="jgid_fk" caption="����ID" style="width:10%"/>
    <freeze:hidden property="jgname" caption="����name" style="width:10%"/>  
    <freeze:hidden property="sfyx" caption="ʹ��״̬" style="width:10%"/>  
    <freeze:cell property="yhzh" caption="�û��˺�" style="width:10%"/>	
    <freeze:cell property="yhxm" caption="�û�����" style="width:8%"/>
    <freeze:cell property="jgname" caption="��������" style="width:22%" />   
    <freeze:cell property="rolenames" caption="��ɫ" style="width:17%"/>
    <freeze:cell property="sfyx" caption="ʹ��״̬" style="width:8%" valueset="syzt"/>    
    <freeze:cell property="maxline" caption="���ͬʱ������" style="width:10%" />
    <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
