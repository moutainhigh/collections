<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<head>
<title>认领视图</title>
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
function func_record_saveRecord11()
{
   saveAndExit( "", "", "/txn80003101.do" );
   //saveAndExit( "认领成功", "认领失败", '' );
  ///saveRecord( '', '认领成功' );
  //window.close();
}

// 返回
function func_record_goBack()
{
    goBack();
}
/**
 * 
 */
function funTBChanged(){

}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{	
}
function setDisplay(id,b){
}

_browse.execute( __userInitPage );

</script>
<freeze:body>
<freeze:title caption="认领视图"/>
<freeze:errors/>

<freeze:form action="/txn80003103">
    <freeze:block property="record" caption="认领视图" width="95%">
        <freeze:hidden property="sys_rd_claimed_view_id" caption="已认领视图主键" style="width:75%"/>
        <freeze:hidden property="sys_rd_unclaim_table_id" caption="物理表" style="width:75%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="数据源id" style="width:75%"/>
        <freeze:hidden property="object_schema" caption="模式" style="width:75%"/>
        <freeze:hidden property="unclaim_table_code" caption="视图名称"  style="width:75%"/>
        <freeze:hidden property="data_object_type" caption="对象类型" value="V" style="width:75%"/>
        <freeze:cell property="unclaim_table_code" caption="视图名称"  style="width:75%"/>
        <freeze:select property="sys_rd_system_id" caption="主题名称" valueset="业务主题"  style="width:95%"/>
        <freeze:textarea property="remark" caption="视图用途" colspan="3" rows="3" maxlength="1000" minlength="1" style="width:98%"/>
        <freeze:textarea property="object_script" caption="视图脚本" colspan="3" rows="10" minlength="1" style="width:98%"/>
        <freeze:button name="record_saveRecord11" caption="保 存" icon="/script/button-icon/icon_ok.gif" onclick="func_record_saveRecord11();"/>
        <freeze:button name="record_goBack" caption="返 回"icon="/script/button-icon/icon_cancel.gif" onclick="func_record_goBack();"/>  		        
    </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>