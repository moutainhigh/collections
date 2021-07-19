<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>用户列表</title>
<style type="text/css">
.odd2_b {
	white-space: nowrap;
}
</style>
</head>
<freeze:body>
<freeze:title caption="用户列表"/>
<freeze:errors/>

<script language="javascript">
// 新建用户
function func_record_addRecord(){
  var param = "record:jgid_fk="+document.getElementById("select-key:jgid_fk").value;
  param += "&record:jgname="+document.getElementById("select-key:jgname").value;
  var page = new pageDefine( "org-insert-yhgl.jsp?"+param, "增加用户", "modal", 650, 400);
  page.addRecord( );
}

// 修改用户
function func_record_updateRecord(){
  var page = new pageDefine( "/txn807014.do", "修改用户", "modal", 650, 400);
  page.addParameter("record:yhid_pk","primary-key:yhid_pk");
  page.addParameter("record:jgid_fk","primary-key:jgid_fk");
  page.updateRecord( );

}

// 注销用户
function func_record_deleteRecord(){
  var page = new pageDefine( "/txn807005.do", "注销用户");
  page.addParameter("record:yhid_pk","primary-key:yhid_pk");
  page.deleteRecord("是否注销选中的用户？");
}

_browse.execute(function(){	
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'>修改</a>";
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
  <freeze:block theme="query" property="select-key" caption="查询条件" width="100%" >
    <freeze:text property="yhzh" caption="用户账号" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:text property="yhxm" caption="用户姓名" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:hidden property="jgid_fk"/>
    <freeze:hidden property="jgname"/>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="用户列表" keylist="yhid_pk" width="100%" navbar="bottom" rowselect="false" fixrow="false" checkbox="false">
    <%--
    <freeze:button name="record_addRecord" caption="增加用户" txncode="807013" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
    <freeze:button name="record_updateRecord" caption="修改用户" txncode="807014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
    <freeze:button name="record_deleteRecord" caption="注销用户" txncode="807005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>  
     --%>   
    <freeze:hidden property="yhid_pk" caption="用户ID" style="width:10%"/>
    <freeze:hidden property="jgid_fk" caption="机构ID" style="width:10%"/>
    <freeze:hidden property="jgname" caption="机构name" style="width:10%"/>  
    <freeze:hidden property="sfyx" caption="使用状态" style="width:10%"/>  
    <freeze:cell property="yhzh" caption="用户账号" style="width:10%"/>	
    <freeze:cell property="yhxm" caption="用户姓名" style="width:8%"/>
    <freeze:cell property="jgname" caption="所属机构" style="width:22%" />   
    <freeze:cell property="rolenames" caption="角色" style="width:17%"/>
    <freeze:cell property="sfyx" caption="使用状态" style="width:8%" valueset="syzt"/>    
    <freeze:cell property="maxline" caption="最大同时在线数" style="width:10%" />
    <freeze:cell property="operation" caption="操作" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
