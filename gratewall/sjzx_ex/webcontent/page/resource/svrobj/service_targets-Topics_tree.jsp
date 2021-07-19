<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html>
<head>
	<%
		DataBus db = (DataBus) request.getAttribute("freeze-databus");
			Recordset rs = db.getRecordset("record");
	%>

	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<link rel="StyleSheet"
		href="<%=request.getContextPath()%>/script/dtree/dtree.css"
		type="text/css" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/script/dtree/dtree.js"></script>
	<title></title>
</head>

<script type="text/javascript" >
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$('#wrapper').height($('body').height()-60);
	$('.dtree').height($('#wrapper').height());
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body >
	<freeze:title caption="" />
	<freeze:errors />
	<table id="wrapper" width="95%" align="center" cellpadding="0" cellspacing="0" >
	<!--<div id="wrapper" style="width: 100%; margin: 0 auto; ">
		-->
		<tr>
		<td style="width:150px;height:450px" valign="top">
		<div class="dtree"
			style="padding-left:5px;overflow-y:scroll;width: 150px; float: left;margin-left: 5px; border: solid 1px; height: 412px;border-color: rgb(207,207,254);">
			<p >
				<a href="javascript: d.openAll();">展开</a> |
				<a href="javascript: d.closeAll();">收起</a>
			</p>
			<script type="text/javascript">
		d = new dTree('d');
		d.add(0,-1,'共享资源列表');
		var i=1;
		var value;
		<%
		while(rs.hasNext()){
			DataBus data = (DataBus) rs.next();
			String id = data.getValue("id");
			String p_id = data.getValue("p_id");
			String name = data.getValue("name");
			String type = data.getValue("type");
			String url = "/txn20301021.do?select-key:share_table_id="+id;
			if(!(type.equals("table")))
			{
			   	%>
			   	 d.add('<%=id%>','<%=p_id%>','<%=name%>');
			   	<%
			}
			else{
				%>
				 d.add('<%=id%>','<%=p_id%>','<%=name%>','<%=url%>','<%=name%>','table_frame');
			    if(i==1){
			    value='<%=id%>';
			    }
			    i++;
			    <%
			}
		}
		%>
	    
		document.write(d);
		  d.openTo(value,true);
	</script>

		</div>
		</td>
		<td valign="top" style="height: 420px;">
		<div id="operation"
			style="float: left; margin-left: 10px; border: solid 1px;border-color: rgb(207,207,254);">
			<table height="100%" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td >
						<iframe name="table_frame"  scrolling="no" frameborder="0"
							id="table_frame"
							src=""
							style="width: 100%;"></iframe>
					</td>
				</tr>
			</table>
		</div>
		</td>
		</tr>
		</table>
	<!--</div>
--><script>
        document.getElementsByName(value)[0].click();
</script>
</freeze:body>
</freeze:html>
