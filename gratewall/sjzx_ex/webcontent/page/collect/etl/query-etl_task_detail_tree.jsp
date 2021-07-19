<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="800" height="400">
<head>
	<%
		DataBus db = (DataBus) request.getAttribute("freeze-databus");
	    
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

<script language="javascript">
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
  
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
	<freeze:title caption="" />
	<freeze:errors />
	<% 
	String workflow_id="";
	String workflow_run_id="";
	String worklet_run_id="";
	if(!db.getRecord("record").isEmpty()){
		workflow_id=db.getRecord("record").getValue("workflow_id");
		workflow_run_id=db.getRecord("record").getValue("workflow_run_id");
		worklet_run_id=db.getRecord("record").getValue("child_run_id");
	}else{
		workflow_id=db.getRecord("select-key").getValue("workflow_id");
		workflow_run_id=db.getRecord("select-key").getValue("workflow_run_id");
	}
	%>
	<div style="width: 100%; margin: 0 auto; height: 400px;">
		<div class="dtree"
			style="padding-left:5px;width: 180px; float: left;margin-left: 5px; border: solid 1px; height: 412px;border-color: rgb(207,207,254);">
			<p>
				<a href="javascript: d.openAll();">չ��</a> |
				<a href="javascript: d.closeAll();">����</a>
			</p>
	   <script type="text/javascript">
		d = new dTree('d');
		d.add(0,-1,'ETL����');
		d.add('1','0','���ڵ�','/txn30300007.do?select-key:workflow_id=<%=workflow_id%>&select-key:workflow_run_id=<%=workflow_run_id%>',
						  '���ڵ�','table_frame');
		<freeze:loop property="record">
		  d.add('<freeze:out value="${record.child_run_id}"/>','<freeze:out value="${record.worklet_run_id}"/>','<freeze:out value="${record.work_desc}"/>',
				  '/txn30300007.do?select-key:workflow_id=<freeze:out value="${record.workflow_id}"/>&select-key:workflow_run_id=<freeze:out value="${record.workflow_run_id}"/>&select-key:worklet_run_id=<freeze:out value="${record.child_run_id}"/>',
						  '<freeze:out value="${record.instance_name}"/>','table_frame');
		</freeze:loop>
		document.write(d);
	   </script>
</div>
		<div id="operation" style="float: left; margin-left: 10px; border: solid 1px;border-color: rgb(207,207,254);">
			<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td >
						<iframe name="table_frame"  scrolling="no" frameborder="0"
							id="table_frame"
							src="/txn30300007.do?select-key:workflow_id=<%=workflow_id%>&select-key:workflow_run_id=<%=workflow_run_id%>&select-key:worklet_run_id=<%=worklet_run_id%>"
							style="width: 100%; height: 400px;"></iframe>
					</td>
				</tr>
			</table>
		</div>

	</div>
</freeze:body>
</freeze:html>
