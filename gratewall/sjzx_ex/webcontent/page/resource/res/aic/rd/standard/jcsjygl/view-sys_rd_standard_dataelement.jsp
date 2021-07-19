<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="600" height="350">
<head>
<title>查看基础数据元</title>
<style>
body{
background:#ffffff;
}
</style>
</head>

<script language="javascript">

// 返回
function func_record_goBack()
{
    goBack();
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

_browse.execute( __userInitPage );

</script>
<freeze:body>
	<freeze:title caption="查看基础数据元" />
	<freeze:errors />

	<freeze:form action="/txn7000306">
		<freeze:block property="record"  caption="查看基础数据元"
			captionWidth="0.45" width="95%">
			<freeze:hidden property="sys_rd_standard_dataelement_id" caption="基础数据元ID"
				datatype="string" style="width:95%" />

			<freeze:cell property="identifier" caption="标识符" datatype="string"
				style="width:95%" />
			<freeze:cell property="standard_category" caption="规范类型"
				valueset="规范类型" style="width:95%" />

			<freeze:cell property="cn_name" caption="中文名称" style="width:95%" />
			<freeze:cell property="en_name" caption="英文名称" style="width:95%" />

			<freeze:cell property="column_nane" caption="字段名" datatype="string"
				style="width:95%" />
			<freeze:cell property="data_type" caption="数据类型" valueset="字段数据类型"
				style="width:95%" />

			<freeze:cell property="data_format" caption="格式" datatype="string"
				style="width:95%" />
			<freeze:cell property="value_domain" caption="值域" datatype="string"
				style="width:95%" />

			<freeze:cell property="unit" caption="计量单位" datatype="string"
				style="width:95%" />
			<freeze:cell property="synonyms" caption="同义词" datatype="string"
				style="width:95%" />

			<freeze:cell property="version" caption="版本" datatype="string"
				style="width:95%" />
			<freeze:cell property="representation" caption="说明" datatype="string"
				style="width:95%" />

			<freeze:cell property="memo" caption="备注" style="width:98%" />
			
		</freeze:block>
		<p align="center" class="print-menu">
			<!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
			
		<table cellspacing="0" cellpadding="0" class="button_table">
			<tr>
				<td class="btn_left"></td>
				<td>
					<input type="button" name="record_goBackNoUpdate" value="关闭"
						class="menu" onclick="func_record_goBackNoUpdate();"
						style='width: 60px'>
				</td>
				<td class="btn_right"></td>
			</tr>
		</table>
		</p>
		<!--  <p align="center" class="print-hide">&nbsp;</p>-->
	</freeze:form>
</freeze:body>
</freeze:html>