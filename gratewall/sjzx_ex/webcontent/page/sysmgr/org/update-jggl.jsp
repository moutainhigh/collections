<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>修改机构信息</title>
<style>
.odd2_b,.odd1_b {
	white-space: nowrap;
}
</style>
</head>
<freeze:body>
<freeze:title caption="修改机构信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>
<script language="javascript">
// 保存信息
function func_record_saveAndExit(){

 	if(checkParentOrg()&&checkPlxh()){
  		saveRecord( "","修改机构信息失败");
  		refreshOrgTree();
	}else{
		return false;
	}
}

//**** 刷新左侧菜单树，如果修改了机构名称或上级机构刷新否则不刷新树
function refreshOrgTree(){

  var sjjg = document.getElementById("record:sjjgid_fk").value;
  var jgid = document.getElementById("record:jgid_pk").value; 
  var jgmc = document.getElementById("record:jgmc").value;

  if(jgmc != oldJgmc || sjjg != oldSjjgid_fk){

   var param = "select-key:selectedid="+jgid;

    window.parent.parent.location.href = "<%=request.getContextPath()%>/txn806001.do?"+param;
  }
	
}

function func_record_goBack(){
    //goBack( "/txn806001.do" );
	window.parent.parent.location.reload();
}
//新建下级机构
function func_record_addNextOrg(){

	var win = parent.parent.getIframeByName('tree_view');
      	if( win == null ){
      		return false;
      	}
   var page = new pageDefine( "/txn806007.do", "增加机构", "modal", 650,400);
   page.addParameter("record:jgid_pk","record:jgid_pk");
   page.addParameter("record:jgid_pk","record:sjjgid_fk");
   page.addParameter("record:jgmc","record:sjjgname");
   page.addRecord( null,win);

}

// 删除机构
function func_record_deleteOrg(){

	var win = parent.parent.getIframeByName('tree_view');
      	if( win == null ){
      		return false;
      	}
  var page = new pageDefine( "/txn806005.do", "删除机构");
  page.addParameter("primary-key:jgid_pk","primary-key:jgid_pk"); 
  page.addParameter("record:jgmc","primary-key:jgmc"); 
  page.deleteRecord("是否注销该机构？");	 
 
}

//**** 判断所选上级机构是否为本机构
function checkParentOrg(){
  var sjjg = document.getElementById("record:sjjgid_fk").value;
  var jgid = document.getElementById("record:jgid_pk").value;

	if(sjjg==jgid){
	    alert("上级机构不能为本机构，请选择其他机构！");
		// 置空上级机构选择项
		document.getElementById("record:sjjgid_fk").value = "";
        document.getElementById("record:sjjgname").value = "";
		return false;
	}else{
		return true;
	}
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
function checkPlxh(){

	/*
	var plxh = document.getElementById("record:plxh").value;
    reg = new RegExp("^[0-9]*[0-9][0-9]*$");
	if (reg.test(plxh)==false){
		alert("请正确输入【排列序号】");
		document.getElementById("record:plxh").select();
		return false;
	}	
	*/
	return true;    
}
</script>

<freeze:form action="/txn806002">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="jgid_pk" caption="机构ID" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改机构信息" width="100%" >
    <freeze:button name="record_saveAndExit" caption="保 存" txncode="806002" hotkey="SAVE" onclick="func_record_saveAndExit();"/>

    <freeze:hidden property="jgid_pk" caption="机构ID" style="width:90%"/>
    <freeze:hidden property="sjjgname" caption="上级机构" style="width:90%"/>
    <freeze:hidden property="jgbh" caption="机构编号" style="width:90%" datatype="string" maxlength="20"/>
    <freeze:cell property="sjjgname" caption="上级机构：" style="width:90%" colspan="2" />
    <freeze:hidden property="sjjgid_fk" caption="上级机构" style="width:90%" />
    <freeze:text property="jgmc" caption="分组名称" style="width:75%" datatype="string" maxlength="60" minlength="1"/>
    <freeze:text property="plxh" caption="排列序号" style="width:75%" datatype="string" maxlength="10"/>
    <freeze:textarea property="bz" caption="备注" style="width:90%" maxlength="255" rows="5" colspan="2" valign="center"/>
  </freeze:block>

</freeze:form>
<script language="javascript">
	var oldJgmc = document.getElementById("record:jgmc").value;
	var oldSjjgid_fk = document.getElementById("record:sjjgid_fk").value;
	//过滤特殊字符
	$(document).ready(function(){
		$("input").filterInput();
	});
</script>
</freeze:body>
</freeze:html>
