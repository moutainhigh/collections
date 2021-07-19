<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.web.util.RequestUtil,cn.gwssi.common.web.context.UserContext"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="com.gwssi.dw.runmgr.etl.txn.ETLConstants" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="com.gwssi.common.util.ExcelWrite"%>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = context.getRecordset("record");  
String[] Titles = {"功能大类","功能小类","所属分局","使用次数"} ;
String[][] Contents;
String title="查询日志统计列表";
response.reset();
response.setContentType("aplication/vnd.ms-excel");
response.addHeader("Content-Disposition","inline; filename=" + new String(title.getBytes("GBK"),"ISO8859_1") + ".xls"); 
OutputStream os=response.getOutputStream();;

Contents=new String[rs.size()][5];

for(int i=0;i<rs.size();i++)
{
	DataBus db = (DataBus)rs.get(i);
	Contents[i][0]=db.getValue("first_func_name");
	Contents[i][1]=db.getValue("func_name");
	Contents[i][2]=db.getValue("sjjgname");
	Contents[i][3]=db.getValue("querytimes");
}
ExcelWrite ew = new ExcelWrite();
try {
	ew.expordExcel(os, "第一页", Titles, Contents);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>


