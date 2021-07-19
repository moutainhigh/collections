<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>查询系统在线用户列表</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="查询系统在线用户列表"/>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "查看业务日志信息", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}
//机构选择
function sjjg_select(){
selectJG("tree","select-key:jgid_fk","select-key:orgname");
}

function selectYh(){
    var parameter = getFormFieldValue('select-key:jgid_fk');
    if(parameter==""){
      alert("请先选择机构");
      return;
    }
	return 'jgid_fk=' + parameter;
}
function resetYu(){
    setFormFieldValue('select-key:opername',0,"");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'>查看日志</a>";
	}		
}

function func_doQuery(){
	document.forms[0].submit();
}

function clearFormFieldValue(){
	document.forms[0].reset();
	document.getElementById("select-key:orgname").value = "";
	document.getElementById("select-key:username").value = "";
	document.getElementById("select-key:jgid_fk").value = "";
	document.getElementById("select-key:opername").value = "";
	
	setFormFieldValue("select-key:count_date",' ');
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn81100001">
  <freeze:block theme="query" property="select-key" caption="查询系统在线用户"  width="95%">   
    <freeze:button name="record_addRecord" caption=" 查 询 " txncode="60210003" enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
    <freeze:button name="record_restRecord" caption=" 重 填 " txncode="60210003" enablerule="0" hotkey="RESET" align="right" onclick="reset();"/>
    <freeze:text property="orgname" caption="所属机构" style="width:70%"  readonly="true" postfix="（<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();resetYu();'>选择</a>）"></freeze:text>
    <freeze:hidden property="jgid_fk"></freeze:hidden>
    <freeze:select property="rolenames" caption="角色" valueset="用户角色信息" style="width:90%" show="code"/>
    <freeze:text property="opername" caption="用户姓名" style="width:90%" datatype="string" /> 
    <freeze:text property="username" caption="用户帐号" style="width:90%" datatype="string" />   
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="系统在线用户" keylist="flowno" width="95%" rowselect="false" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="yhzh" caption="用户帐号" width="10%" />
    <freeze:cell property="yhxm" caption="用户姓名" width="10%" />
    <freeze:cell property="rolenames" caption="角色" />
    <freeze:cell property="jgname" caption="机构名称" width="18%"/>
    <freeze:cell property="ipaddress" caption="IP地址" width="13%"/>
    <freeze:cell property="login_time" caption="登录时间" datatype="date" style="width:140px"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
