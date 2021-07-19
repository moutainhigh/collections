<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html  width="650" height="350">
<head>
<title>查询数据权限类型管理列表</title>
</head>

<style>
	select#s1{
		width:245px;
	}
	
	select#s2{
		width:245px;
	}
</style>

<script language="javascript">

// 增加数据权限类型管理
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataobject.jsp", "增加数据权限类型管理", "modal" );
	page.addRecord();
}

// 修改数据权限类型管理
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103014.do", "修改数据权限类型管理", "modal" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.updateRecord();
}

// 删除数据权限类型管理
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103015.do", "删除数据权限类型管理" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.deleteRecord( "是否删除选中的记录" );
}

//保存功能配置关系
function func_record_saveFunctions(){
	var img = window.event.srcElement;
	if(img.tagName.toUpperCase()=="IMG"){
		var row = img.parentElement.parentElement.parentElement;
		var checkbox = row.firstChild.firstChild;
		checkbox.checked=true;
		//alert(row);
		var page = new pageDefine( "/txn103016.do", "配置数据权限类型" );
		page.addParameter( "record:objectid", "record:objectid" );
		page.addParameter( "record:functionIds", "record:functionIds" );
		//var objectid = document
		//var functionIds = 
		//page.addValue(objectid,"record:objectid");
		//page.addValue(functionIds,"record:functionIds");
		page.callService();
		//page
	}
}

//
function func_record_clearRecord(){
	if(window.confirm("确认清除！")){
		var page = new pageDefine( "/txn103019.do", "删除数据权限类型配置" );
		page.addParameter( "record:objectid", "record:objectid" );
		page.callService("正在清空数据权限类型配置！");
	}else{
		var grid = getGridDefine("record", 0); 
		clickFlag = 0; 
		grid.checkMenuItem();
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询数据权限类型管理列表"/>
<freeze:errors/>

<freeze:form  action="/txn103011">
  <freeze:block property="select-key" submit=" 查 询 " caption="查询条件" width="95%">
      <freeze:text property="objectsource" caption="数据权限来源" datatype="string" maxlength="500" style="width:95%"/>
  </freeze:block>

  <freeze:grid  property="record" caption="查询数据权限类型管理列表" keylist="objectid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_updateRecord" caption="修改数据权限类型管理" txncode="103014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_clearRecord" caption="清空功能配置关系" txncode="103014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_clearRecord();"/>
      
      <freeze:cell property="objectid" caption="数据权限类型代码" style="width:20%" visible="false"/>
      <freeze:cell property="objectsource" caption="数据权限来源" style="width:20%" visible="true" />
      <freeze:browsebox property="functionIds" caption="配置功能关系" valueset="功能点代码对照集" show="name" style="width:60%"  multiple="true" namebox="rolenames" width="500" height="500"/>
  	  <freeze:link  property="save" caption="保存配置功能关系"  img="images/save.jpg" style="width:20%;"  onclick="func_record_saveFunctions()" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
