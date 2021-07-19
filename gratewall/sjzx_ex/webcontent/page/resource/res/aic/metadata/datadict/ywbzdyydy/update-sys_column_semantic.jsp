<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改业务字段信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存业务字段定义' );	// /txn30403001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30403001.do
}

function getParameter()
{
	var sys_id = getFormFieldValue("record:sys_id");
	if (sys_id == null || sys_id == "")
	{	
        alert("请选择业务主题");
	    return;	
	}else{
		var parameter = 'sys_id=' + sys_id;
		return parameter;
	}
}

function resetTableNo()
{
	if (getFormFieldValue("record:table_no") == null || getFormFieldValue("record:table_no") == "")
	{
	}
	else{
		setFormFieldValue("record:table_no",0,"")
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var sys_id = getFormFieldValue("record:sys_id");
	var table_no = getFormFieldValue("record:table_no");
	if (sys_id == null || sys_id == "")
	{
		resetTableNo();
	}
	setFormFieldValue("record:old_column_name",0,getFormFieldValue("record:column_name"));
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改业务字段信息"/>
<freeze:errors/>

<freeze:form action="/txn30403002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="column_no" caption="业务字段编码" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改业务字段信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="column_no" caption="字段编码" datatype="string" maxlength="8" style="width:95%"/>
      <freeze:hidden property="old_column_name" caption="修改前字段名" datatype="string" style="width:95%"/>
      <freeze:hidden property="column_byname" caption="字段名别名" datatype="string" style="width:95%"/>
      <freeze:browsebox property="sys_id" caption="业务主题" notnull="true" valueset="业务系统" show="name" style="width:95%" data="code" onchange="resetTableNo()"/>
      <freeze:browsebox property="table_no" caption="表中文名" notnull="true" valueset="业务表代码对照表" show="name" data="code" style="width:95%" parameter="getParameter()"/>
      <freeze:text property="column_name" caption="业务字段名" datatype="string" maxlength="60" minlength="1" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="业务字段中文名" datatype="string" maxlength="100" minlength="1" style="width:95%"/>
      <freeze:hidden property="column_order" caption="检索顺序" datatype="number" maxlength="8" minlength="1" style="width:95%"/>
      <freeze:select property="edit_type" caption="字段类型" notnull="true" valueset="字段类型" style="width:95%"/>
      <freeze:text property="edit_content" caption="字段长度" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="demo" caption="备注" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br/>
  <div align="center">
	  <div style="width:95%;" align="left">
	  	<h3>使用说明：</h3>
	  	<ul>
			<li>1、如果是代码字段，需在备注中填写代码表（如CA01）</li>
		</ul>
	  </div>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
