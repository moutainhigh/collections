<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui/jquery.ui.core.js"></script>
<head>
<title>查询表关联关系</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

function func_record_viewRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	
	var page = new pageDefine( "view-sys_rd_metadata_column.jsp", "查看字段详情", "modal" );
	page.addParameter( "record:column_code", "record:column_code" );
	page.addParameter( "record:column_name", "record:column_name" );
	page.addParameter( "record:content", "record:content" );
	
	page.goPage();
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$("span[id='span_record:content']").each(function(){
		var s = $(this).html();
		$(this).html(s.split(';').join(';<br>'))
	})
	
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询已认领列表"/>
<freeze:errors/>

<freeze:form action="/txn8000601">
   <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="column_code" caption="字段名" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="column_name" caption="字段中文名" datatype="string" maxlength="100" style="width:90%"/>
      
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询数据元列表" keylist="column_code" width="95%" navbar="bottom" checkbox="false" fixrow="false" multiselect="false" rowselect="false">
      <freeze:button name="column_view" caption="详细"  enablerule="1"  align="right" onclick="func_record_viewRecord()" visible="false"/>
      <freeze:hidden property="column_code" caption="字段ID" style="width:10%" />
      <freeze:cell property="column_name" caption="字段中文名" style="width:15%" />
      <freeze:cell property="column_code" caption="字段名" style="width:15%" />
      <freeze:cell property="content" caption="字段引用情况" style="width:30%" />
      </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
