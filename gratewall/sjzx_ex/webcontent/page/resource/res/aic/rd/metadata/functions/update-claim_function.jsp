<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<head>
<title>修改函数</title>
<style type="text/css">
.cls-no{
	display:none;
}
.cls-yes{
	display:block;
}
</style>
</head>

<script language="javascript">

// 保存
function func_record_saveRecord()
{
   saveAndExit( '', '保存函数' );
}



// 返回
function func_record_goBack()
{
    goBack();
}


</script>
<freeze:body>
<freeze:title caption="修改函数"/>
<freeze:errors/>

<freeze:form action="/txn80004202">
    <freeze:block property="record" caption="修改函数" width="95%">
        <freeze:hidden property="sys_rd_claimed_view_id" caption="主键" style="width:95%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="主键" style="width:95%"/>
        <freeze:cell property="view_name" caption="函数名称:"   style="width:95%"/>
        <freeze:select property="sys_rd_system_id" caption="主题名称:" valueset="业务主题" style="width:95%"/>
        
        <freeze:textarea property="view_use" caption="函数用途" colspan="2" rows="3" maxlength="1000" minlength="1" style="width:98%" />
        <freeze:hidden property="view_script" caption="函数脚本" colspan="2"  minlength="1" style="width:95%" />
        
  	
        <freeze:button name="record_saveRecord" caption="保 存" icon="/script/button-icon/icon_ok.gif" onclick="func_record_saveRecord();"/>
        <freeze:button name="record_goBack" caption="返 回"icon="/script/button-icon/icon_cancel.gif" onclick="func_record_goBack();"/>  		        
    </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>