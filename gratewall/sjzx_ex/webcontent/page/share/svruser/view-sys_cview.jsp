<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�û��Զ�����ͼ��Ϣ</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");
System.out.println("in view-sys_cview.jsp is \n"+db);

%>
</head>

<script language="javascript">

_browse.execute(function(){
	
});
</script>
<freeze:body>
<freeze:errors/>

 	<table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="1"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td  class="secTitle">&nbsp;������Ϣ</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="border:2px solid #006699;" width="100%"  cellspacing="0" cellpadding="0">
							<tr class="framerow">
		         				<td class="even1" align="center" width="15%">�������ͣ�</td><td class="odd1" width="35%"><freeze:out value="${info-base.stype}"/></td>
		         				<td class="even1" align="center" width="15%">������룺</td><td class="odd1" width="35%"><freeze:out value="${info-base.svr_code}"/></td>
		        			</tr>
		      
		       				<tr class="framerow">
		         				<td class="even2" align="center" width="15%">������Ա��</td><td class="odd2" width="35%"><freeze:out value="${info-base.create_by}"/></td>
		         				<td class="even2" align="center" width="15%">����ʱ�䣺</td><td class="odd2" width="35%"><freeze:out value="${info-base.create_date}"/></td>
		        			</tr>
						
						</table>
					</td>
				</tr>
			    
			    
			   
	</table>	
    <br>

     <table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td  class="secTitle">&nbsp;������Ϣ</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr>
			    	<td>
			    		<table style="border:2px solid #006699;" width="100%"  cellspacing="0" cellpadding="0">
			    			<tr class="framerow">
		         				<td class="even1" align="center" width="15%">��Ȩ��</td><td class="odd1" width="85%"><freeze:out value="${info-config.grant_table}"/></td>
		        			</tr>
			    			<tr class="framerow">
		         				<td  class="even2" align="center">��ѯSQL��</td><td class="odd2"><freeze:out value="${info-config.query_sql}"/></td>
		        			</tr>
			    		</table>
			    	</td>
			    </tr>
			    
		        
	</table>	

</freeze:body>
</freeze:html>
