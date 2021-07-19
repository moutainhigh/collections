<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="800" height="350">
<head>
<title>查询业务日志列表</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="查询系统管理日志"/>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "查看业务日志信息", "modal", 650, 400);
 //var page = new pageDefine( "/txn981214.do", "查看业务日志信息");
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}
//机构选择
function sjjg_select(){
  selectJG("tree","select-key:jgid_fk","select-key:orgname");
}

function selectYh(){
    var parameter = getFormFieldValue('select-key:jgid_fk');
    if(parameter==""){
      alert("请先选择机构");
      return;
    }
	return 'jgid_fk=' + parameter;
}
function resetYu(){
    setFormFieldValue('select-key:opername',0,"");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' title='查看日志' href='#'><div class='detail'></div></a>";
	}		
	document.getElementById('select-key:orgname').readOnly =true;
}

function clearChoose(){
	setFormFieldValue('select-key:orgname',0,"");
	setFormFieldValue('select-key:jgid_fk',0,"");
}

function clearFormFieldValue( formName )
{
	// 取FORM
	if( formName == null ){
		var	obj = window.event.srcElement;
		if( obj == null || obj.form == null ){
			return;
		}
		
		formName = obj.form.name;
	}
	
	// 取字段列表
	var	fieldList = _getFormFieldList( formName );
	if( fieldList == null ){
		return;
	}
	
	// 清空内容
	var	obj;
	var	value;
	for( var ii=0; ii<fieldList.length; ii++ ){
		field = fieldList[ii];
		
		// 先检查original:字段
		obj = document.getElementsByName('original:' + field.fieldName);
		if( obj != null && obj.length > field.index ){
			obj = obj[field.index];
			value = obj.value;
		}
		else{
			value = getFormFieldDefaultValue( field.fieldName, field.index );
			if( value == null ){
				value = '';
			}
		}
		
		setFormFieldValue( field.fieldName, field.index, value );
	}
	setFormFieldValue('select-key:orgname',0,"");
	setFormFieldValue('select-key:jgid_fk',0,"");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn981211">
  <freeze:block theme="query" property="select-key" caption="查询业务日志"  width="95%">   
    <freeze:text property="orgname" caption="所属机构" style="width:70%"  postfix="（<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();resetYu();'>选择</a>）"></freeze:text>
    <freeze:hidden property="jgid_fk"></freeze:hidden>
    <freeze:browsebox property="opername" caption="操作员姓名" style="width:90%"  valueset="机构下用户" show="name" data="name" parameter="selectYh();" /> 
    <freeze:text property="username" caption="用户帐号" style="width:90%" datatype="string" />   
    <freeze:datebox property="regdate_from" caption="操作日期" style="width:90%" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    	</td><td width='5%'>至</td><td width='45%'>
      <freeze:datebox property="regdate_to" caption="执行日期" style="width:100%" colspan="0"/>
        </td></tr></table>       
  </freeze:block>
  <freeze:grid property="record" caption="业务日志列表" keylist="flowno" width="95%" rowselect="false" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:hidden property="flowno" caption="流水号" width="10%" />
    <freeze:hidden property="reqflowno" caption="请求流水号" width="10%" />
    <freeze:cell property="@rowid" caption="序号" width="11%" align="center"/>
    <freeze:hidden property="username" caption="用户帐号" align="center" width="11%"/>
    <freeze:cell property="opername" caption="操作员姓名" align="center" width="12%"/>
    <freeze:cell property="orgid" caption="机构代码" width="10%" align="center" visible="false"/>
    <freeze:cell property="orgname" caption="机构名称" width="20%" align="center"/>
    <freeze:cell property="regdate" caption="操作日期" datatype="date" align="center" width="15%"/>
    <freeze:cell property="regtime" caption="操作时间" datatype="date" align="center" width="10%"/>
    <freeze:cell property="trdcode" caption="交易码" width="8%" visible="false"/>
    <freeze:cell property="trdname" caption="交易名称" align="center"/>
    <freeze:cell property="trdtype" caption="分类名称" width="12%" visible="false"/>
    <freeze:cell property="errcode" caption="错误代码" width="8%" visible="false"/>
    <freeze:cell property="operation" caption="操作" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
