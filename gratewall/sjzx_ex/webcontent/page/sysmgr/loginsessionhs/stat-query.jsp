<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>用户访问统计</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="用户访问统计"/>
<freeze:errors/>

<script language="javascript">
//机构选择
function sjjg_select(){
	selectJG("tree","select-key:jgid_fk","select-key:orgname");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

function func_doQuery(){
	var jgid_fk = getFormFieldValue("select-key:jgid_fk");
	var login_date_from = getFormFieldValue("select-key:login_date_from");
	var login_date_to = getFormFieldValue("select-key:login_date_to");
	if(!jgid_fk && !login_date_from && !login_date_to ){
		alert("提示：必须输入至少一个查询条件");
		return false;
	}
	
	var startObj = document.getElementById("select-key:login_date_from");	
	var endObj = document.getElementById("select-key:login_date_to");
	
	if(!checkInput(startObj,'【登录日期】','3',false)||!checkInput(endObj,'【登录日期】','3',false)){
	    return false;
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
            alert("【登录日期】开始日期不能超过今天！");
            document.getElementById("select-key:login_date_from").select();
            return false;
		}		
	}
	
	if(start&&end){
        if(start>end){
            alert("【登录日期】结束日期必须大于开始日期！");
            document.getElementById("select-key:login_date_to").select();
            return false;
        }		
	}
	
	
  var page = new pageDefine( "/txn81100002.do", "用户访问统计");
  page.addParameter("select-key:jgid_fk","select-key:jgid_fk");
  page.addParameter("select-key:orgname","select-key:orgname");
  page.addParameter("select-key:login_date_from","select-key:login_date_from");
  page.addParameter("select-key:login_date_to","select-key:login_date_to");
  page.goPage( );
}

function reset(){
	setFormFieldValue("select-key:jgid_fk", 0, "");
	setFormFieldValue("select-key:orgname", 0, "");
	setFormFieldValue("select-key:login_date_from", 0, "");
	setFormFieldValue("select-key:login_date_to", 0, "");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn81100002">
  <freeze:block property="select-key" caption="用户访问统计"  width="95%">   
    <freeze:button name="record_addRecord" caption=" 查 询 " enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
    <freeze:button name="record_resetRecord" caption=" 重 填 " enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
    <freeze:text property="orgname" caption="所属机构" style="width:70%" readonly="true" postfix="（<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();'>选择</a>）" />
    <freeze:datebox property="login_date_from"  caption="登录日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>至</td><td width='45%'>
      <freeze:datebox property="login_date_to" caption="登录日期" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table>
    <freeze:hidden property="jgid_fk"></freeze:hidden>     
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="用户访问统计列表" width="95%" keylist="yhid_pk" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>
    <freeze:cell property="jgmc_all" caption="机构名称" width="40%" />
    <freeze:cell property="yhxm" caption="用户姓名" width="30%" />
    <freeze:cell property="login_times" caption="访问次数" width="24%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
