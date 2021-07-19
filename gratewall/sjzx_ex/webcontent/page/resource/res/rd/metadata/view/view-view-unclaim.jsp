<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>查看未认领视图信息</title>
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
<freeze:title caption="视图信息"/>
<freeze:errors/>

<freeze:form action="/txn80003104">
<freeze:block property="record" caption="视图信息" width="95%" columns="1">
		
        <freeze:cell property="unclaim_table_code" caption="视图名称:" />
        <freeze:cell property="remark" caption="视图用途:"  />
        <freeze:cell property="object_script" caption="视图脚本:" />
    </freeze:block>
    <p align="center" class="print-menu">
    <table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type="button" name="record_goBackNoUpdate" value="关 闭" class="menu" onclick="func_record_goBack()" /></td><td class='btn_right'></td></tr></table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>