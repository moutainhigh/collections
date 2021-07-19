<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
long totalCount=0;
long numPerPage=0;
if(context!=null){
	DataBus db = context.getRecord("select-key");
	totalCount= db.getLong("totalCount");
	numPerPage= db.getLong("numPerPage");	
}
%>
<head>
</head>

<script language="javascript">

function exportExcel(){
	var s = $('#pageNum').val()
	var aa = s.split("-");
	var a  =  window.dialogArguments
	if(a){
		 a[0].doExport(aa[0],aa[1]);
	} 
	window.close(); 
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var numPerPage="<%=numPerPage%>";
	var totalCount="<%=totalCount%>";
	var n = totalCount/numPerPage;
	var a = new Array;
	if(n>=0){
	 
        var m = Math.floor(n); //返回值为小于等于其数值参数的最大整数值。
        for(var j=1; j<=m ; j++){
        	var s1 = numPerPage*(j-1)+1;
        	var s2 = numPerPage*j
        	a.push( s1.toString()  +"-"+ s2.toString() );
        }
        if(n>0){
        	var s1 = numPerPage*m+1 ;
        	var s2 = totalCount.toString();
        	
            a.push( s1.toString()+ "-" + totalCount.toString());
        }
    }
    
	if(a.length>0){
		var s = "";
		for(var j=0; j<a.length; j++){
			s+="<option value="+a[j]+">"+(j+1)+"</option>";
		}
		$('#pageNum').html(s);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<div align="center">单次下载上限<%=numPerPage%>条，请选择要下载第 
<select id="pageNum"/>
</select>页 </div>
<div align="center">
		<table cellspacing="0" cellpadding="0" class="button_table">
			<tr>
				<td class="btn_left"></td>
				<td>
					<button class="menu" onclick="exportExcel();">
						下载
					</button>
				</td>
				<td class="btn_right"></td>
			</tr>
		</table>
	</div>
</freeze:body>
</freeze:html>
