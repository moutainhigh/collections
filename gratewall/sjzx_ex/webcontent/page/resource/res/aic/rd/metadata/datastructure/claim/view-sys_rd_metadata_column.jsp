<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%
//DataBus context = (DataBus) request.getAttribute("freeze-databus");
//System.out.println("insert-sys_rd_table.jsp"+context);
//String num = context.getRecord("record").getValue("cur_record_count");
//System.out.println("insert-sys_rd_table.jsp:num:"+num);
%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="200">
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui/jquery.ui.core.js"></script>
<head>
<title>字段详情</title>
</head>

<script language="javascript">


function func_record_goBackNoUpdate()
{

      goBackNoUpdate();	
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$("span[id='span_record:content']").each(function(){
		var s = $(this).html();
		$(this).html(s.split(';').join(';<br>'))
	})
}



_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看字段引用情况"/>
<freeze:errors/>

<freeze:form action="/txn8000601">


  <freeze:block  property="record" caption="查看字段引用情况" width="95% " columns="1">
      <freeze:cell property="column_code" caption="字段名" datatype="string"  style="width:95%"/>
      <freeze:cell property="column_name" caption="字段中文名" datatype="string"  style="width:95%"/>
      <freeze:cell property="content" caption="字段引用情况" datatype="string"  style="width:95%"/>
  </freeze:block>

  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  
		<table cellspacing="0" cellpadding="0" class="button_table">
			<tr>
				<td class="btn_left"></td>
				<td>
					<input type="button" name="record_goBackNoUpdate" value="关闭"
						class="menu" onclick="func_record_goBackNoUpdate();"
						style='width: 60px'>
				</td>
				<td class="btn_right"></td>
			</tr>
		</table>
		</p>

</freeze:form>
</freeze:body>
</freeze:html>
