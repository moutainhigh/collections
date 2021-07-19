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
<%@ page import="jxl.Workbook"%>
<%@ page import="jxl.format.Colour"%>
<%@ page import="jxl.format.UnderlineStyle"%>
<%@ page import="jxl.write.DateFormat"%>
<%@ page import="jxl.write.DateTime"%>
<%@ page import="jxl.write.Label"%>
<%@ page import="jxl.write.NumberFormat"%>
<%@ page import="jxl.write.WritableCellFormat"%>
<%@ page import="jxl.write.WritableFont"%>
<%@ page import="jxl.write.WritableSheet"%>
<%@ page import="jxl.write.WritableWorkbook"%>

<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");

//System.out.println(" \n in print word page \n ----------------------------------------------------------------------- \n" +context);


DataBus info_config = context.getRecord("info_config");
String shared_columns = info_config.getValue("shared_columns");//共享字段
String user_params = info_config.getValue("user_params");//用户参数
String sys_params = info_config.getValue("sys_params");//系统参数
DataBus info_base = context.getRecord("info_base");
String svr_name = info_base.getValue("svr_name");
String is_pause  = info_base.getValue("is_pause"); 
String limit_week  = info_base.getValue("limit_week"); 
String is_limit_number  = info_base.getValue("is_limit_number"); 
String max_records  = info_base.getValue("max_records"); 
String svr_code  = info_base.getValue("svr_code"); 
String open_time =info_base.getValue("open_time"); 
String is_limit_total= info_base.getValue("is_limit_total"); 
String is_limit_time= info_base.getValue("is_limit_time"); 
String limit_conn = info_base.getValue("limit_conn");
String wu = "无";
DataBus record = context.getRecord("record");
String  password = record.getValue("password");
String user_name = record.getValue("user_name");
String login_name = record.getValue("login_name");
String is_ip_bind = record.getValue("is_ip_bind");
String ip_bind = "";
if(is_ip_bind!=null&&is_ip_bind.equals("1")){
	ip_bind = record.getValue("ip_bind");
}
Recordset rs=context.getRecordset("info-column");

response.reset();
response.setContentType("application/x-download;charset=GBK");
response.setHeader("Content-Disposition", "attachment;filename=config.doc");
OutputStream os=response.getOutputStream();
StringBuffer sb = new StringBuffer();

sb.append("<html><style type='text/css'>table{border-collapse:collapse;}  table,td,th{border:1px solid black;}</style><body>");
sb.append("<div align=center style='margin-left:30px'><h2>").append(svr_name).append("接口配置信息");
sb.append("</h2></div><div style='margin-left:30px'>");
sb.append("【用户】：").append(user_name);
sb.append("<br>");
sb.append("【账号】：").append(login_name);
sb.append("<br>");
sb.append("【密码】：").append(password);
sb.append("<br>");
sb.append("【服务代码】：").append(svr_code);
sb.append("<br>");

sb.append("【绑定IP】:").append(ip_bind);
sb.append("<br>");

sb.append("【开放时间】：").append(open_time);
sb.append("<br>");

sb.append("【限定条件】：");
if(limit_conn!=null){
	sb.append(limit_conn);
}else{
	sb.append(wu);
}
sb.append("<br>");

sb.append("【共享字段】:");
if(shared_columns!=null){
	sb.append(shared_columns);
}else{
	sb.append(wu);
}
sb.append("<br>");

sb.append("【用户参数】:");
if(user_params!=null){
	sb.append(user_params);
}else{
	sb.append(wu);
}
sb.append("<br>");
sb.append("【系统参数】:");
if(sys_params!=null){
	sb.append(sys_params);
}else{
	sb.append(wu);
}
sb.append("<br>");
String temp="";
for(int i=0;i<rs.size();i++){
   DataBus db=rs.get(i);
   String table_code=db.getValue("table_code");
   String table_name = db.getValue("table_name");
   String column_code = db.getValue("column_code");
   String column_name = db.getValue("column_name");
   String typename = db.getValue("typename");
   String column_length = db.getValue("column_length");
   
   
   if (i==0){
	  
	   sb.append("<table width='80%'><tr ><td colspan='4' align='center' ><h4>"+table_name+"("+table_code+")表的共享字段信息</h4></td></tr>");
	   sb.append("<tr><td><b>字段中文名</b></td><td ><b>字段英文名</b></td><td><b>字段类型</b></td><td><b>字段长度</b></td></tr>");
	   sb.append("<tr><td>"+column_name+"</td><td>"+column_code+"</td><td>"+typename+"</td><td>"+column_length+"</td></tr>");
   }else{
	   if(!temp.equals(table_code)){
		   sb.append("</table><br>");
		   sb.append("<table width='80%'><tr ><td colspan='4' align='center'><h4>"+table_name+"("+table_code+")表的共享字段信息</h4></td></tr>");
		   sb.append("<tr><td><b>字段中文名</b></td><td><b>字段英文名</b></td><td><b>字段类型</b></td><td><b>字段长度</b></td></tr>");
		   sb.append("<tr><td>"+column_name+"</td><td>"+column_code+"</td><td>"+typename+"</td><td>"+column_length+"</td></tr>"); 
	   }else{
		   sb.append("<tr><td>"+column_name+"</td><td>"+column_code+"</td><td>"+typename+"</td><td>"+column_length+"</td></tr>");
		   
	   }
   }
   
   temp=table_code;
}

sb.append("</table>");

sb.append("</div></body></html>");
byte[] b = sb.toString().getBytes();
os.write(b);
os.flush();
os.close();
//os.close();
%>
<script>
alert('xxx');
</script>