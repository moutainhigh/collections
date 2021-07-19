<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<title>查询系统日志列表</title>
</head>

<script language="javascript">
/* var checkName="";
function formSubmit(){
	console.log(checkName);
}
function checkchange(){
	if($('#nameinput').val()==checkName){
		return;
	}else{
		checkName=$('#nameinput').val();
		formSubmit();
	}
} */
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	/*
	var value=getFormAllFieldValues("record:first_page_query_id");
	
	for(var i=0; i<value.length; i++){
		var val=value[i];
	
	document.getElementsByName("span_record:oper")[i].innerHTML='<a href="javascript:func_record_querycode(\''+val+'\');" title="" >查看详细</a>';
	
    }
    */
   /*  var t2html='<div class="pack-up" id="t2" >'
		+'<ul class="pack-list" id="t2_ul" ><li class="disabled" id="username"><a>操作人姓名</a></li>'
		+'<li ><input id="nameinput" type="text" style="width:180px;" onpropertychange="checkchange()" ></input></li></ul></div>';
	$('#t1').after(t2html);//添加粒度选择框 */	
    //$('#t2').find('.pack-list li:eq(1)').hide();
	
	var date_s1 = document.getElementsByName("span_record:query_time");
	for(var ii=0; ii<date_s1.length; ii++){
		date_s1[ii].innerHTML = date_s1[ii].innerHTML.substr(0,10);
		
	}
	
	
	
}
function func_record_querycode(first_page_query_id){
   
    var url="txn601016.do?select-key:first_page_query_id="+first_page_query_id;
    var page = new pageDefine( url, "查询系统日志列表", "系统日志" );
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询系统操作日志列表"/>
<freeze:errors/>
<gwssi:panel action="txn601011" target="" parts="t1,t2" styleClass="wrapper">
 	<gwssi:cell id="t1" name="操作人姓名" key="username" data="username" />
	<gwssi:cell id="t2" name="操&nbsp;作&nbsp;时&nbsp;间" key="created_time" data="created_time" date="true"/>
   
 </gwssi:panel>

<freeze:form action="/txn601011">
  
<br/>
  <freeze:grid property="record"   checkbox="false" caption="查询系统日志列表" keylist="first_page_query_id" width="95%" rowselect="false" navbar="bottom" >
      <freeze:cell property="@rowid" caption="序号" align="middle" style="width:5%"/>
      <freeze:hidden property="username" caption="操作人账号"  align="center"  style="width:8%" />
      <freeze:cell property="opername" caption="操作人姓名"   align="center"  style="width:10%" />
      <freeze:cell property="orgname" caption="操作人所在机构"  align="center"   style="width:20%" />
      <freeze:cell property="first_cls" caption="操作分类" align="center"style="width:15%" />
      <freeze:cell property="second_cls" caption="具体操作" align="center" style="width:15%" />
      <freeze:cell property="query_time" caption="操作时间"  align="center"  style="width:10%" />
      
      <freeze:hidden property="query_date" caption="操作日期" style="width:10%" />
      <freeze:hidden property="username" caption="用户名" style="width:12%" />
      <freeze:hidden property="opername" caption="操作名称" style="width:12%" />
      <freeze:hidden property="orgid" caption="组织ID" style="width:12%" />
      <freeze:hidden property="orgname" caption="组织名称" style="width:16%" />
      <freeze:hidden property="ipaddress" caption="ip地址" style="width:12%" />
      <freeze:hidden property="first_cls" caption="first_cls" style="width:20%" />
      <freeze:hidden property="second_cls" caption="second_cls" style="width:20%" />
      <freeze:hidden property="operfrom" caption="operfrom" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
