<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>使用情况统计</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
#navbar{
	display:none;
}
</style>
</head>

<freeze:body>
<freeze:title caption="使用情况统计"/>
<freeze:errors/>

<script language="javascript">
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

function func_doQuery(){
    /*
	var startDate = getFormFieldValue("select-key:startDate");
	var endDate = getFormFieldValue("select-key:endDate");
	if(!startDate && !endDate ){
		alert("提示：必须输入至少一个查询条件");
		return false;
	}*/
	
	var startObj = document.getElementById("select-key:startDate");	
	var endObj = document.getElementById("select-key:endDate");
	
	if(!checkInput(startObj,'【日期】','3',false)||!checkInput(endObj,'【日期】','3',false)){
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
            alert("【开始日期】不能超过今天！");
            document.getElementById("select-key:startDate").select();
            return false;
		}		
	}
	
	if(start&&end){
        if(start>end){
            alert("【日期】结束日期必须大于开始日期！");
            document.getElementById("select-key:endDate").select();
            return false;
        }		
	}
	
	
  var page = new pageDefine( "/txn81100003.do", "使用情况统计");
  page.addParameter("select-key:startDate","select-key:startDate");
  page.addParameter("select-key:endDate","select-key:endDate");
  page.goPage( );
}

function reset(){
	setFormFieldValue("select-key:startDate", 0, "");
	setFormFieldValue("select-key:endDate", 0, "");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn81100002">
  <freeze:block property="select-key" caption="用户访问统计"  width="95%">   
    <freeze:button name="record_addRecord" caption=" 查 询 " enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
    <freeze:button name="record_resetRecord" caption=" 重 填 " enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
    
    <freeze:datebox property="startDate" caption="日期自" style="width:90%" datatype="date"/> 
    <freeze:datebox property="endDate" caption="至" style="width:90%" datatype="date"/>   
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="使用情况统计列表" width="95%" keylist="jgid_pk" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="@rowid" caption="序号" align="middle" style="width:6%"/>
    <freeze:cell property="jgmc" caption="机构名称" width="20%" />
    <freeze:cell property="query_times" caption="数据查询次数" width="18%" />
    <freeze:cell property="login_times" caption="登录次数" width="18%"/>
    <freeze:cell property="download_times" caption="下载次数" width="18%"/>
    <freeze:cell property="download_total" caption="下载条数" width="18%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
