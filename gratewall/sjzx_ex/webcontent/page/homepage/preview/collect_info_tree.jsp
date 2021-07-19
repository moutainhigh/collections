<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<%
	DataBus db = (DataBus) request.getAttribute("freeze-databus");
	Recordset rs = db.getRecordset("record");
%>

<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<link rel="StyleSheet" href="<%=request.getContextPath()%>/script/dtree/dtree.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/script/dtree/dtree.js"></script>
<title></title>
</head>

<script language="javascript">
	// 请在这里添加，页面加载完成后的用户初始化操作
	function __userInitPage() {
		if(document.getElementById("sd1"))
			document.getElementById("sd1").click();
		if($('#dtree_id').height() > 260){
			$('#dtree_id').css("overflow-y", "scroll");
			$('#dtree_id').height(260);
		}
	}
	//var targetName = '';
	_browse.execute('__userInitPage()');
</script>

<freeze:body>
	<freeze:errors />
	<div style="width: 100%; margin: 10px auto;">
		<div id="dtree_id" class="dtree"
			style="padding-left: 5px; width: 150px; float: left; margin-left: 5px; border: solid 1px; border-color: rgb(207, 207, 254);">

			<script type="text/javascript">
			<%
			String service_targets_name = "";
			if(rs.hasNext()){
				out.println("d = new dTree('d'); d.add(0, -1, '采集任务列表'); var i = 1;");
				while (rs.hasNext()) {
						DataBus data = (DataBus) rs.next();
						String collect_task_id = data.getValue("collect_task_id");
						String collect_type = data.getValue("collect_type");
						String service_name = data.getValue("task_name");
						service_targets_name = data.getValue("service_targets_name");
						String url = "#";
						if("00".equals(collect_type) || "05".equals(collect_type)){
							url = "/txn30101106.do?primary-key:collect_task_id="+ collect_task_id+"&record:service_targets_name="+service_targets_name;
						}else if("02".equals(collect_type)){
							url = "/txn30101115.do?primary-key:collect_task_id="+ collect_task_id+"&record:service_targets_name="+service_targets_name;
						}else if("01".equals(collect_type)){
							url = "/txn30101125.do?primary-key:collect_task_id="+ collect_task_id+"&record:service_targets_name="+service_targets_name;
						}else if("03".equals(collect_type)){
							url = "/txn30101135.do?primary-key:collect_task_id="+ collect_task_id+"&record:service_targets_name="+service_targets_name;
						}/* else {
							
						} */
						out.println("d.add('" + collect_task_id + "',0,'"
								+ service_name + "','"+url+"','"
										+ service_name + "','table_frame');");
					}
				}%>
				document.write(d);
				//d.openTo(value, true);
				<%
				//out.println(" targetName = '"+service_targets_name+"';");
				%>
			</script>

		</div>
		<div id="operation"
			style="float: left; margin-left: 10px; border: solid 1px; border-color: rgb(207, 207, 254);">
			<table height="100%" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><iframe name="table_frame" scrolling="no" frameborder="0"
							id="table_frame" src="" style="width: 100%; "></iframe>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script>
		//
	</script>
</freeze:body>
</freeze:html>
