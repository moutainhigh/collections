<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>查看已认领视图信息</title>
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
<freeze:errors/>
<br />
<freeze:form action="/txn80003205">
<freeze:block property="record" caption="查看视图信息" width="95%" columns="1">
		<freeze:hidden property="object_schema" caption="用户:" />
		<freeze:cell property="sys_name" caption="主题名称:"  />
        <freeze:cell property="view_name" caption="视图名称:" />
        <freeze:cell property="view_use" caption="视图用途:" colspan="2" width="100%" />
        <freeze:cell property="view_script" caption="视图脚本:" colspan="2" width="100%"/> 
    </freeze:block>
    <p align="center" class="print-menu">
    <table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type="button" name="record_goBackNoUpdate" value="返 回" class="menu" onclick="func_record_goBack()" /></td><td class='btn_right'></td></tr></table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>