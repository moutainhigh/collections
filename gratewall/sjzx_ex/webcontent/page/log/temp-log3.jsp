<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询系统日志列表</title>
</head>

<script language="javascript">



// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
	var value=getFormAllFieldValues("record:first_page_query_id");
	
	for(var i=0; i<value.length; i++){
		var val=value[i];
	
	document.getElementsByName("span_record:oper")[i].innerHTML='<a href="javascript:func_record_querycode(\''+val+'\');" title="" >查看详细</a>';
	
    }
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
<freeze:title caption="服务对象统计"/>

<freeze:errors/>
<div align="center"><span style="color: #f00;">暂无数据，功能不可用</span></div>
<freeze:form action="/txn601011">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="opername" caption="服务对象"   style="width:95%"/>
      <freeze:select property="orgname" caption="统计周期"   style="width:95%"/>
      <freeze:select property="123" caption="展现方式"   style="width:95%"/>
      <freeze:select property="ssa" caption="排序"   style="width:95%"/>

  </freeze:block>
<br/>
  
  <freeze:grid property="record"   checkbox="false" caption="查询系统日志列表" keylist="first_page_query_id" width="95%" navbar="bottom" >
     
      
      <freeze:hidden property="first_page_query_id" caption="主键" style="width:10%" visible="false"/>
                  <freeze:cell property="opername" caption="监控对象"   align="center"  style="width:15%" />
                <freeze:cell property="username" caption="监控指标"  align="center"  style="width:15%" />
           
          
            <freeze:cell property="orgname" caption="监控指标值"  align="center"   style="width:15%" />
            <freeze:cell property="query_time" caption="是否警情"  align="center"    style="width:16%" />
            
            <freeze:cell property="state" caption="监控时间"   align="center"     style="width:15%" />
              <freeze:cell property="oper" caption="操作"    align="center"        style="width:16%" />
            
      
      <freeze:hidden property="count" caption="数量" style="width:10%" />
      <freeze:hidden property="num" caption="总数" style="width:10%" />
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
