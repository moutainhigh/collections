<%
/*
 * @Header 机构新建详细页面
 * @Revision
 * @Date 2007-03-01
 * @author adaFang
 * =====================================================
 *  深圳审计项目组
 * =====================================================
 */
%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>新建机构信息</title>
<style>
.odd1_b,.odd2_b{
	white-space:nowrap;
}
</style>
</head>

<freeze:body>
<freeze:errors/>
<freeze:title caption="机构详细信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>
<script language="javascript">
// 保存信息
function func_record_saveRecord(){

    if(checkPlxh()){
  	//saveRecord( "", "保存机构信息" );
	    var page = new pageDefine( "/txn806003.ajax", "保存机构信息" );	
	    
	    var sjjgname = document.getElementById("record:sjjgname").value;
	    var sjjgid_fk = document.getElementById("record:sjjgid_fk").value;
	    var plxh = document.getElementById("record:plxh").value;
	    var sfyx = document.getElementById("record:sfyx").value;
	    var jgmc = document.getElementById("record:jgmc").value;
	    var jgbh = document.getElementById("record:jgbh").value;
	    var jgjc = document.getElementById("record:jgjc").value;
	    var jglx = document.getElementById("record:jglx").value;
	    var bz = document.getElementById("record:bz").value;
	    var jgfzr = document.getElementById("record:jgfzr").value;
	    
	    page.addValue(sjjgid_fk, "record:sjjgid_fk");
		page.addValue(sjjgname, "record:sjjgname");
	    page.addValue(plxh,"record:plxh"); 
		page.addValue(sfyx, "record:sfyx");
	    page.addValue(jgmc,"record:jgmc");   
	    page.addValue(jgbh, "record:jgbh");   
	    page.addValue(jgjc,"record:jgjc");   
	    page.addValue(jglx, "record:jglx");    
	    page.addValue(bz, "record:bz");   
	    page.addValue(jgfzr, "record:jgfzr"); 
	       
		page.callAjaxService("back_ajax"); 
	} 	
}

function back_ajax(errCode,errDesc,xmlResults){
	 
	 if(errCode=='000000'){
       var jgid = _getXmlNodeValues(xmlResults, 'record:jgid_pk');
       var param = "select-key:selectedid="+jgid;
       window.parent.location.href = "<%=request.getContextPath()%>/txn806001.do?"+param;       
	 }else{
	     alert(errDesc);
	 }	
}
function checkPlxh(){
	var jgmc = document.getElementById("record:jgmc").value;
	if(jgmc.length==0){
		alert("请输入【机构名称】");
		return false;
	}
	var plxh = document.getElementById("record:plxh").value;
    reg = new RegExp("^[0-9]*[0-9][0-9]*$");
	if (reg.test(plxh)==false){
		alert("请正确输入【排列序号】");
		document.getElementById("record:plxh").select();
		return false;
	}	
	return true;    
}
// 返回列表
function func_record_goBack(){
  window.parent.location.reload();
}

//** 校验输入的机构编号为数字和英文字母格式
function checkOrgCode(){
	var jgbh = document.getElementById("record:jgbh").value;
	if(!validateWebPath(jgbh)){
		alert("机构编号输入非法字符！请输入数字及英文字母A-Z或a-z的组合。");
		return false;
	}
	return true;
}
//机构类型
function checkJglx(){
	var jglx = document.getElementById("record:jglx").value;
	if(jglx==""){
		alert("请先选择机构类型");
		return false;
	}
	return true;
}
      
</script>

<freeze:form action="/txn806003">
  <freeze:block property="record" caption="新建机构信息" width="100%" >
    <freeze:button name="record_saveRecord" caption="保 存" txncode="806003" hotkey="SAVE" onclick="func_record_saveRecord();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="jgid_pk" caption="机构ID" style="width:90%"/>
    <freeze:hidden property="jgbh" caption="机构编号" style="width:90%" datatype="string" maxlength="20" />
    <freeze:hidden property="sjjgname" caption="上级机构" style="width:90%"/>
    <freeze:hidden property="sjjgid_fk" caption="上级机构" style="width:90%" />
    
    <freeze:cell property="sjjgname" caption="上级机构：" style="width:90%" colspan="2"/>
    <freeze:text property="jgmc" caption="机构名称" style="white-space:nowrap;width:75%" notnull="true" datatype="string" maxlength="60" minlength="1"/>
    <freeze:hidden property="jgjc" caption="机构简称" style="width:90%" datatype="string" maxlength="60"/>
    <freeze:hidden property="jglx" caption="机构类型" style="width:90%" />
        
    <freeze:hidden property="jgfzr" caption="机构负责人" style="width:60%" readonly="true"/>
    <freeze:hidden property="jgfzr_tmp" caption="机构负责人" style="width:90%" />
       
    <freeze:hidden property="sfyx" value="0"/>
    <freeze:text property="plxh" caption="排列序号" style="width:75%" notnull="true" datatype="number" maxlength="5"  minlength="1"/>
    <freeze:textarea property="bz" caption="备注" style="width:90%" maxlength="255" rows="5" colspan="2" valign="center" />
  </freeze:block>

</freeze:form>
<script language="javascript">
	//过滤特殊字符
	$(document).ready(function(){
		$("input").filterInput();
	});
</script>
</freeze:body>
</freeze:html>