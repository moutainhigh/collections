<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加计量单位类别信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存计量单位管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存计量单位管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存计量单位管理' );	// /txn301051.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn301051.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

function   checkIt()       
 {   var loc=getFormFieldValue("record:dwlb_dm");
       if(!/^[a-z0-9A-Z]+$/.test(loc))  
          {alert("计量单位类别代码中不应含有特殊字符、汉字或为空"); 
          return false;}
          else
          {
          return true;
          }
     }
function   check()       
 {   var loc=getFormFieldValue("record:dwlb_cn_mc");
      if(/[\'@$!`~?#,;^&\*\.\\]+/.test(loc))  
          {alert("计量单位类别中文名称中不应含有特殊字符或为空"); 
          return false;}
          else
          {
          return true;
          }
     }

     


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加计量单位类别信息"/>
<freeze:errors/>

<freeze:form action="/txn301053">
  <freeze:block property="record" caption="增加计量单位类别信息" width="95%" columns="1" nowrap="true">
      
      <freeze:button name="record_saveAndContinue" caption="保存" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndExit();"/>
      
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="dwlb_dm" caption="计量单位类别代码" validator="checkIt()"  datatype="string"  maxlength="2" minlength="2" style="width:95%"/>
      <freeze:text property="dwlb_cn_mc" caption="计量单位类别中文名称" validator="check()" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="dwlb_cn_ms" caption="计量单位类别中文描述" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
      <freeze:text property="dwlb_en_mc" caption="计量单位类别英文名称" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
      <freeze:text property="dwlb_en_ms" caption="计量单位类别英文描述" datatype="string" maxlength="255" colspan="2" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
