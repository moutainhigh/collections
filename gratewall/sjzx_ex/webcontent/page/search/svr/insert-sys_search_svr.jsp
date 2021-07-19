<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="600">
<head>
<title>增加检索服务信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<style>
#columnTable .odd1{
	height: 30px;
	background:url(<%=request.getContextPath()%>/module/layout/layout-weiqiang/images_new/tr1.jpg) repeat;
	background-position: left bottom;
	padding:5px 0px 5px 5px;
}
#columnTable .even1{
	height: 30px;
	background:url(<%=request.getContextPath()%>/module/layout/layout-weiqiang/images_new/tr2.jpg) repeat;
	background-position: left bottom;
	padding:5px 0px 5px 5px;
}

#columnTable .odd2{
	height: 30px;
	background:url(<%=request.getContextPath()%>/module/layout/layout-weiqiang/images_new/tr4.jpg) repeat;
	background-position: left bottom;
	padding:5px 0px 5px 5px;
}
#columnTable .even2{
	height: 30px;
	background:url(<%=request.getContextPath()%>/module/layout/layout-weiqiang/images_new/tr3.jpg) repeat;
	background-position: left bottom;
	padding:5px 0px 5px 5px;
}
</style>
</head>

<script language="javascript">
var cache = new Array;

// 保 存
function func_record_saveRecord()
{
//先清空缓存
  cache = [];
   $("#columnTable tr").each(
   	function(i,e){
   		if(i>0){
   			var $td = $(this).children('td');
   			var s = {};
   			s.name=$td[1].innerText;
   			s.type=$td[2].innerText;
   			s.indexType=$td.eq(3).find("select").val()
   			s.weight=$td.eq(4).find("input").val()
   			cache.push(s);
   		}
   	}
   );
   
   if(cache.length>0){
   	var aa = new Array;
   	 for(var j=0; j<cache.length; j++){
   	 //名称、类型、索引类型、权重
	   	 var ss = cache[j].name+","+cache[j].type+","+cache[j].indexType+","+cache[j].weight
	   	 aa.push(ss);
   	 }
    var page = new pageDefine( "/txn50010203.ajax", "保存检索接口服务信息");
	page.addValue(aa.join(";"),"record:svr_param_str");
	page.addValue($('#svr_name').val(),"record:svr_name");
	page.addValue($('#db_name_str').val(),"record:svr_db");
	page.addValue($('#searchTemplate').val(),"record:svr_template");
	
	var s1 = $('#svr_name').val();
	var s2 = $('#searchTemplate').val();
	if(s1==""||s1==null){
		alert("请填入检索接口名称！");
		
	}else{
		if(s2==""||s2==null){
			alert("请保存检索接口结果模板！");
		}else{
			page.callAjaxService("callBack3()");
		}
	}
   }
}

function callBack3(errCode, errDesc, xmlResults){
	if(errCode!='000000'){
			alert("保存检索接口时出现错误！")
			return
		}else{
			alert('保存成功！');
	}
	goBack();
}
// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存检索服务' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存检索服务' );	// /txn50010201.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50010201.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	initDbNameStr();
}

function initDbNameStr(){
	var page = new pageDefine("/txn50030132.ajax", "查询TRS检索数据库");
	page.callAjaxService("callBack()");
}

function callBack(errCode, errDesc, xmlResults){
		if(errCode!='000000'){
			alert("查询TRS检索库出现错误！")
			return
		}
	var db_name_str =  _getXmlNodeValue(xmlResults,'record:db_name_str');
	
	if(db_name_str!=""){
		var a = db_name_str.split(",");
		if(a.length>0){
			var s = "";
			for(var j=0; j<a.length; j++){
				s+="<option value="+a[j]+">"+a[j]+"</option>";
			}
		
			$("#db_name_str").html(s);	
		}
	}
}

function getColumns(){
	 var url = "/txn50030131.ajax" ;
	 url+="?select-key:trsDb="+$("#db_name_str").val();
	 
	  var page = new pageDefine(url, "查询TRS检索数据库字段信息");
	// page.addParameter( $("#db_name_str").val(), "select-key:trsDb");
	 page.callAjaxService("callBack2()");
	 
}

function callBack2(errCode, errDesc, xmlResults){
		if(errCode!='000000'){
			alert("查询TRS检索库字段信息时出现错误！")
			return
		}
		 
	var column_name =  _getXmlNodeValues(xmlResults,'record:column_name');
	var column_type = _getXmlNodeValues(xmlResults,'record:column_type');
	var db_name =  _getXmlNodeValues(xmlResults,'record:db_name');
	if(column_name!=""&&column_name.length>0){
		createColumns(column_name,column_type,db_name)
	}
}

function createColumns(column_name,column_type,db_name){
   
	var e = document.getElementById("columnTable");
	$("#columnTable").html('');
	
	var tbody = document.createElement("tbody");
	
	var th = document.createElement("TR");
	th.setAttribute("class","grid-headrow");
	var h0 = document.createElement("TD");
	var x0 = document.createTextNode("序号");
	var h1 = document.createElement("TD");
	var x1 = document.createTextNode("字段名称" );
	var h2 = document.createElement("TD");
	var x2 = document.createTextNode("字段类型" );
	var h3 = document.createElement("TD");
	var x3 = document.createTextNode("索引类型" );
	var h4 = document.createElement("TD");
	var x4 = document.createTextNode("权重" );
	
	h0.appendChild(x0);
	h1.appendChild(x1);
	h2.appendChild(x2);
	h3.appendChild(x3);
	h4.appendChild(x4);
	
	th.appendChild(h0);
	th.appendChild(h1);
	th.appendChild(h2);
	th.appendChild(h3);
	th.appendChild(h4);
	tbody.appendChild(th);
	
	for(var j=0; j<column_name.length; j++){
		var tr = document.createElement("TR");
		var td0 = document.createElement("TD");
		td0.setAttribute("class","even"+(2-j%2));
		var tx0 = document.createTextNode( (j+1) );//序号
		var td1 = document.createElement("TD");
		td1.setAttribute("class","odd"+(2-j%2));
		var tx1 = document.createTextNode( column_name[j] );//字段名称
		var td2 = document.createElement("TD");
		td2.setAttribute("class","even"+(2-j%2));
		var tx2 = document.createTextNode( column_type[j] );//字段类型
		var td3 = document.createElement("TD");
		td3.setAttribute("class","odd"+(2-j%2));
		var sel1= document.createElement("SELECT");
		sel1.options.add(new Option("全文检索","1"));
		sel1.options.add(new Option("只显示","0"));
		
		var td4 = document.createElement("TD");
		td4.setAttribute("class","even"+(2-j%2));
		var  input   =   document.createElement( "INPUT");       
        input.type   =   "text"; 
        input.value   =  1; 
        input.width="20";
		
		td0.appendChild(tx0);
		td1.appendChild(tx1);
		td2.appendChild(tx2);
		td3.appendChild(sel1);
		td4.appendChild(input);
		
		tr.appendChild(td0);
		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		tr.appendChild(td4);
		
		tbody.appendChild(tr);
	}
	
	e.appendChild(tbody);
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加检索服务"/>
<freeze:errors/>

<freeze:block property="record" caption="增加检索服务信息" width="95%">
	<tr><td width="15%">检索服务名称:</td><td width="35%"><input type="text" style="width:70%" id="svr_name" value="" /></td>
	<td width="15%">检索数据库：</td><td width="35%"><select id="db_name_str"  style="width:70%" onchange="getColumns();"/></select></td></tr>
    <tr><td width="15%" valign="middle">检索模板：</td><td width="75%" colspan="3">
    	<table id="columnTable"  width="100%" align="center" cellpadding="0" cellspacing="0" border="0"  ></table>
    	</td></tr>   
    <tr><td colspan="4"><textarea name="searchTemplate" id="searchTemplate" rows="10" cols="100" ></textarea></td></tr>
	<freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
	<freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>     
</freeze:block>

</freeze:body>
</freeze:html>
