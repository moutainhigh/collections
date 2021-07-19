<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="java.io.OutputStream"%>


<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");

//System.out.println(" \n in print word page \n ----------------------------------------------------------------------- \n" +context);


DataBus info_base = context.getRecord("info_base");
DataBus info_limit = context.getRecord("db_limit");
DataBus info_config = context.getRecord("info-config");

String shared_columns = info_config.getValue("shared_columns");//�����ֶ�
String user_params = info_config.getValue("user_params");//�û�����
String sys_params = info_config.getValue("sys_params");//ϵͳ����
System.out.println("sys_params:"+sys_params);
System.out.println("svr_code:"+info_base.getValue("svr_code"));
String svr_name = info_base.getValue("svr_name");
String svr_code  = info_base.getValue("svr_code"); 
String open_time =info_limit.getValue("open_time"); 
String limit_conn = info_limit.getValue("limit_conn");
String wu = "��";
DataBus record = context.getRecord("record");
String  password = info_base.getValue("password");
String user_name = info_base.getValue("user_name");
String login_name = info_base.getValue("login_name");
String is_ip_bind = info_base.getValue("is_ip_bind");
String ip_bind = "��";
if(is_ip_bind!=null&&is_ip_bind.equals("1")){
	ip_bind = record.getValue("ip_bind");
}


response.reset();
response.setContentType("application/x-download;charset=UTF-8");
String cname=URLDecoder.decode("config.doc" ,"UTF-8");
response.setHeader("Content-Disposition", "attachment;filename="+cname);
OutputStream os=response.getOutputStream();
StringBuffer sb = new StringBuffer();

sb.append("<html><style type='text/css'>table{border-collapse:collapse;}  table,td,th{border:1px solid black;}</style><body>");
sb.append("<div align=center style='margin-left:30px'><h2>").append(svr_name).append("�ӿ�������Ϣ");
sb.append("</h2></div><div style='margin-left:30px'><table width='90%'>");
sb.append("<td width='20%' align='right'>���û�����</td><td>").append(user_name);
sb.append("</td></tr>");
sb.append("<tr><td width='20%' align='right'>���˺š���</td><td>").append(login_name);
sb.append("</td></tr>");
sb.append("<tr><td width='20%' align='right'>�����롿��</td><td>").append(password);
sb.append("</td></tr>");
sb.append("<tr><td width='20%' align='right'>��������롿��</td><td>").append(svr_code);
sb.append("</td></tr>");

sb.append("<tr><td width='20%' align='right'>����IP����</td><td>").append(ip_bind);
sb.append("</td></tr>");

sb.append("<tr><td width='20%' align='right'>������ʱ�䡿��</td><td>").append(open_time);
sb.append("</td></tr>");

sb.append("<tr><td width='20%' align='right'>���޶���������</td><td>");
if(limit_conn!=null){
	sb.append(limit_conn);
}else{
	sb.append(wu);
}
sb.append("</td></tr>");

sb.append("<tr><td width='20%' align='right'>�������ֶΡ���</td><td>");
if(shared_columns!=null){
	sb.append(shared_columns);
}else{
	sb.append(wu);
}
sb.append("</td></tr>");
sb.append("<tr><td width='20%' align='right'>��ϵͳ��������</td><td>");
if(StringUtils.isNotBlank(sys_params)){
	sb.append(sys_params);
}else{
	sb.append(wu);
}
sb.append("</td></tr>");

sb.append("<tr><td width='20%' align='right'>���û���������</td><td>");
if(StringUtils.isNotBlank(user_params)){
	sb.append(user_params);
}else{
	sb.append(wu);
}
sb.append("</td></tr>");
sb.append("</table><br/>");
String temp="";

Recordset rs=context.getRecordset("info-column");
for(int i=0;i<rs.size();i++){
   DataBus db=rs.get(i);
   String table_code=db.getValue("table_code");
   String table_name = db.getValue("table_name");
   String column_code = db.getValue("column_code");
   String column_name = db.getValue("column_name");
   String typename = db.getValue("typename");
   String column_length = db.getValue("column_length");
   if (i==0){
	   sb.append("<table width='80%'><tr ><td colspan='4' align='center' ><h4>"+table_name+"("+table_code+")��Ĺ����ֶ���Ϣ</h4></td></tr>");
	   sb.append("<tr><td><b>�ֶ�������</b></td><td ><b>�ֶ�Ӣ����</b></td><td><b>�ֶ�����</b></td><td><b>�ֶγ���</b></td></tr>");
	   sb.append("<tr><td>"+column_name+"</td><td>"+column_code+"</td><td>"+typename+"</td><td>"+column_length+"</td></tr>");
   }else{
	   if(!temp.equals(table_code)){
		   sb.append("</table><br>");
		   sb.append("<table width='80%'><tr ><td colspan='4' align='center'><h4>"+table_name+"("+table_code+")��Ĺ����ֶ���Ϣ</h4></td></tr>");
		   sb.append("<tr><td><b>�ֶ�������</b></td><td><b>�ֶ�Ӣ����</b></td><td><b>�ֶ�����</b></td><td><b>�ֶγ���</b></td></tr>");
		   sb.append("<tr><td>"+column_name+"</td><td>"+column_code+"</td><td>"+typename+"</td><td>"+column_length+"</td></tr>"); 
	   }else{
		   sb.append("<tr><td>"+column_name+"</td><td>"+column_code+"</td><td>"+typename+"</td><td>"+column_length+"</td></tr>");
		   
	   }
   }
   
   temp=table_code;
}
sb.append("</table>");




sb.append("</div></body></html>");
System.out.println(sb.toString());
byte[] b = sb.toString().getBytes();
os.write(b);
os.flush();
os.close();
//os.close();
%>
<script>
alert('xxx');
</script>