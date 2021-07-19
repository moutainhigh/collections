<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>查看已认领存储过程信息</title>
</head>

<script language="javascript">
// 返回
function func_record_goBack()
{
    goBack();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    
}

_browse.execute( __userInitPage );

</script>
<freeze:body>
<freeze:title caption="查看存储过程信息"/>
<freeze:errors/>

<freeze:form action="/txn80005205">
<freeze:block property="record" caption="查看存储过程信息" width="95%" columns="1">
		<freeze:hidden property="object_schema" caption="用户:" />
		<freeze:cell property="sys_name" caption="主题名称:"  />
        <freeze:cell property="view_name" caption="存储过程名称:" />
        <freeze:cell property="view_use" caption="存储过程用途:" colspan="2" width="20%" />
        <freeze:hidden property="view_script" caption="存储过程脚本:" colspan="2" width="30%" /> 
    </freeze:block>
    <p align="center" class="print-menu">
    	<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type="button" name="record_goBackNoUpdate" value="关 闭" class="menu" onclick="func_record_goBack()" /></td><td class='btn_right'></td></tr></table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>