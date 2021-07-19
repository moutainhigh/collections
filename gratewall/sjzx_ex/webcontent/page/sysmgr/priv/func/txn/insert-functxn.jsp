<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>增加功能交易信息</title>
</head>

<freeze:body>
<freeze:title caption="增加功能交易信息"/>
<freeze:errors/>

<script language="javascript">
function func_record_saveRecord(){
  saveRecord( "", "保存功能交易信息" );
}

function func_record_saveAndContinue(){
  saveAndContinue( "", "保存功能交易信息并解析" );
}

function func_record_saveAndExit(){
  saveAndExit( "", "保存功能交易信息并返回", "/txn980311.do" );
}

function func_record_goBack(){
  goBack( "/txn980311.do" );
}

// 下载交易信息
function loadTxnInfo()
{
	var txncode = getFormFieldValue( 'record:txncode' );
	beginLoadData( 'txninfo', 'iface=' + txncode );
}

function getTxnCode()
{
	return null;
}

function saveData(errCode, errDesc)
{
	if( errCode != '000000' ){
		alert( errCode + ':' + errDesc );
	}
	else{
		var desc = txninfo[0][9];
		var txnType = txninfo[0][3];
		if( txnType == 'system' ){
			alert( '系统交易[' + txninfo[0][2] +':' + desc + ']不需要授权' );
			setFormFieldValue( 'record:txncode', 0, '' );
		}
		else if( txnType != 'check' ){
			alert( '公共交易[' + txninfo[0][2] +':' + desc + ']不需要授权' );
			setFormFieldValue( 'record:txncode', 0, '' );
		}
		else{
			setFormFieldValue( 'record:txnname', 0, desc );
		}
	}
}
</script>

<freeze:form action="/txn980313">
  <freeze:block property="record" caption="增加功能交易信息" width="95%" columns="1" captionWidth="0.2">
    <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="funccode" caption="功能代码" style="width:90%"/>
    <freeze:text property="txncode" caption="交易代码" style="width:40%" datatype="string" maxlength="6" minlength="1" onchange="loadTxnInfo()"/>
    <freeze:text property="txnname" caption="交易名称" style="width:90%" datatype="string" maxlength="64"/>
    <freeze:hidden property="status" caption="状态" style="width:40%" value="1"/>
    <freeze:text property="memo" caption="备注信息" style="width:90%" datatype="string" maxlength="256"/>
  </freeze:block>
  
  <freeze:data name="txninfo" property="action" fields="" txnCode="iface" parameter="getTxnCode()" onload="saveData(errCode, errDesc)"/>
</freeze:form>
</freeze:body>
</freeze:html>
