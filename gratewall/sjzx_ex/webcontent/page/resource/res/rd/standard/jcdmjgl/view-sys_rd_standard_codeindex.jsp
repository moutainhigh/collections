<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="750" height="350">
<head>
<title>查看基础代码集信息</title>
<style>
body{
background:#ffffff;
}
</style>
</head>

<script language="javascript">

// 打 印
function func_record_printDocument()
{
	print(document);
}

// 返 回
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
<freeze:body styleClass='body-div'>
<freeze:title caption="查看基础代码集信息"/>
<freeze:errors/>

<freeze:form action="/txn7000406">


  <freeze:block  property="record" caption="查看基础代码集信息" width="95%">
      <freeze:cell property="sys_rd_standar_codeindex" caption="标识符" datatype="string" style="width:95%"/>
      
      <freeze:cell property="codeindex_name" caption="代码集名称" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_codeindex_version" caption="版本" datatype="string" style="width:95%"/>
      
      <freeze:cell property="description" caption="说明" datatype="string" style="width:95%"/>
      <freeze:cell property="representation" caption="表示" datatype="string" style="width:95%"/>
      
      <freeze:cell property="codeindex_category" caption="代码集类型" valueset="代码集类型" style="width:95%"/>
      <freeze:cell property="code_table" caption="代码表" datatype="string" style="width:95%"/>
      
      <freeze:cell property="coding_methods" caption="编码方法" style="width:98%"/>
      
  </freeze:block>
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
		<tr>
			<td class="btn_left"></td>
			<td><input type="button" name="record_goBackNoUpdate" value="关闭" class="menu" onclick="func_record_goBack()" style='width:60px' >
			</td><td class="btn_right"></td>
		</tr></table>
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
