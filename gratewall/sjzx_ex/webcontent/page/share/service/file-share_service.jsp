<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.net.URLEncoder"%>

<%@ page import="java.io.OutputStream"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.gwssi.common.util.JSONUtils"%>
<%@ page import="com.gwssi.common.util.JsonDataUtil"%>
<%@ page import="com.gwssi.common.util.ValueSetCodeUtil"%>
<%@ page import="com.gwssi.log.sharelog.vo.ShareLogContext"%>

<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="cn.gwssi.common.component.logger.TxnLogger"%>

<%@ page import="java.io.OutputStreamWriter"%>
<%@ page import="java.io.Writer"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="com.gwssi.share.service.vo.VoTableColumns"%>

<%-- template single/single-table-update.jsp --%>

<%
 Logger	logger		= TxnLogger.getLogger("JSP");	// 日志
		
ValueSetCodeUtil ValueSet = new ValueSetCodeUtil();
ShareLogContext cont = new ShareLogContext();

DataBus context = (DataBus) request.getAttribute("freeze-databus");
//System.out.println(context);
DataBus record_show = context.getRecord("record");
DataBus record = context.getRecord("record2");
DataBus record_target = context.getRecord("record3");





String service_targets_name = record_target.getValue("service_targets_name");
String service_targets_no = record_target.getValue("service_targets_no");
String service_password = record_target.getValue("service_password");


String service_name = record_show.getValue("service_name");//服务名称

String interface_id = record.getValue("interface_id");//基础接口
//interface_id = ValueSet.getCodeStr( cont,  "共享服务_接口名称",  interface_id);

String service_targets_id = record.getValue("service_targets_id");//服务对象
//service_targets_id = ValueSet.getCodeStr( cont,  "资源管理_共享服务对象名称",  service_targets_id);

String service_state = record.getValue("service_state");//服务状态
//service_state = ValueSet.getCodeStr( cont,  "资源管理_归档服务状态",  service_state);

String service_type = record.getValue("service_type");//服务类型
//service_type = ValueSet.getCodeStr( cont,  "资源管理_数据源类型",  service_type);

String is_month_data = "N".equals(record_show.getValue("is_month_data"))?"不限制":"限制";//服务类型
String visit_period = record_show.getValue("visit_period");//服务类型

String service_no = record_show.getValue("old_service_no");//服务编号
String sql = record.getValue("sql");//sql语句
String service_description = record_show.getValue("service_description");//服务说明
String regist_description = record_show.getValue("regist_description");//备案说明
String column_name_cn = record.getValue("column_name_cn");//服务所选字段
String jsondata = record.getValue("jsoncolumns");//json条件

//logger.debug("jsondata="+jsondata);
JSONArray jsoncolumns=JsonDataUtil.getJsonArray(jsondata,"columns");//用于展示的字段
JSONArray jsonconditions = JsonDataUtil.getJsonArray(jsondata,"conditions");//先定好参数的条件
JSONArray jsonparams = JsonDataUtil.getJsonArray(jsondata,"params");//需要输入参数的条件


StringBuffer column_table = new StringBuffer(); 
String select_column = "";//选择字段
List<String> table_list = new ArrayList(); 
List<VoTableColumns> column_list = new ArrayList(); 

for (int i = 0; i < jsoncolumns.size(); i++) {
	JSONObject object=(JSONObject)jsoncolumns.get(i);

	String table_name_cn = ((JSONObject)object.get("table")).get("name_cn").toString();
	String table_name_en = ((JSONObject)object.get("table")).get("name_en").toString();
	String column_name_en = ((JSONObject)object.get("column")).get("name_en").toString();
	String column_name_cn2 = ((JSONObject)object.get("column")).get("name_cn").toString();
	String column_name_alias = object.get("alias").toString();
	String column_type = object.get("column_type").toString();
	String column_length = object.get("column_length").toString();
	
	VoTableColumns Vo = new VoTableColumns();
	Vo.set_column_length(column_length);
	Vo.set_column_name_alias(column_name_alias);
	Vo.set_column_name_cn(column_name_cn2);
	Vo.set_column_name_en(column_name_en);
	Vo.set_column_type(column_type);
	Vo.set_table_name_cn(table_name_cn);
	Vo.set_table_name_en(table_name_en);
	
	column_list.add(Vo);
	if(!table_list.contains(table_name_en+"@"+table_name_cn))
		table_list.add(table_name_en+"@"+table_name_cn);
}
column_table.append("<table width='90%'>");
column_table.append("<tr><TD width='25%'><b>字段中文名</b></TD><TD width='25%'><b>字段英文名</b></TD><TD width='20%'><b>字段别名</b></TD><TD width='15%'><b>字段类型</b></TD><TD width='15%'><b>字段长度</b></TD></tr>");
for (int i = 0; i < table_list.size(); i++) {
	String[] tablename = table_list.get(i).split("@");
	//column_table.append("<table width='90%'><tr><TD colspan='5'><h4>表名："+tablename[1]+"("+tablename[0]+")</h4></td></tr>");
	System.out.println(column_table.toString());
	for (int j = 0; j < column_list.size(); j++) {
		if(column_list.get(j).get_table_name_en().equals(tablename[0])){
		column_table.append("<tr><TD>"+column_list.get(j).get_column_name_cn()+"</TD><TD>"+column_list.get(j).get_column_name_en()+"</TD><TD>"+column_list.get(j).get_column_name_alias()+"</TD><TD>"+column_list.get(j).get_column_type()+"</TD><TD>"+column_list.get(j).get_column_length()+"</TD></tr>");
		//column_list.remove(column_list.get(j));
		}
	}
}
column_table.append("</table><br>");
System.out.println("3333333");

String service_condition = "";//服务查询条件
for (int i = 0; i < jsonconditions.size(); i++) {
	JSONObject object=(JSONObject)jsonconditions.get(i);
	 service_condition+=((JSONObject)object.get("logic")).get("name_cn").toString()+object.get("leftParen").toString()
	 +"["+((JSONObject)object.get("table")).get("name_cn").toString()+"] 的 " + ((JSONObject)object.get("column")).get("name_cn").toString() 
	 +" <span style='color:red'> "+((JSONObject)object.get("paren")).get("name_cn").toString() +" </span>"
	 +((JSONObject)object.get("param_value")).get("name_cn").toString()+object.get("rightParen").toString()+"<br/>";
}

String service_param = "";//用户输入参数
for (int i = 0; i < jsonparams.size(); i++) {
	JSONObject object=(JSONObject)jsonparams.get(i);
	service_param+= ((JSONObject)object.get("logic")).get("name_cn").toString()+object.get("leftParen").toString()
	+" ["+((JSONObject)object.get("table")).get("name_cn").toString()+"] 的 "+((JSONObject)object.get("column")).get("name_cn").toString()
	+" ["+((JSONObject)object.get("column")).get("name_en").toString()+"] "
	+" <span style='color:red'> "
	+((JSONObject)object.get("paren")).get("name_cn").toString()+" </span>"+((JSONObject)object.get("param_value")).get("name_cn").toString()
	+object.get("rightParen").toString()+"<br/>";
}

String limit_data = record_show.getValue("limit_data");//限制条件json

StringBuffer limit_table = new StringBuffer(); 
limit_table.append("<tr><TD width='10%'>星期</TD><TD width='30%'>开始时间-结束时间</TD><TD width='15%'>访问次数</TD><TD width='15%'>每次访问总数</TD><TD width='15%'>每天访问总数</TD></tr>");

if(limit_data == null || limit_data.equals(""))
{
}
else
{
JSONArray limit_datas = JsonDataUtil.getJsonArray(limit_data,"data");

for (int i = 0; i < limit_datas.size(); i++) {
	JSONObject object=(JSONObject)limit_datas.get(i);
	
	limit_table.append("<tr><TD>"+((JSONObject)object.get("week")).get("name_cn").toString()+"</TD><TD>"+object.get("datesStr").toString()
			+"</TD><TD>"+object.get("times_day").toString()+"</TD><TD>"+object.get("count_dat").toString()+"</TD><TD>"
			+object.get("total_count_day").toString()+"</TD></tr>");
}
}
response.reset();
response.setContentType("application/x-download;charset=UTF-8");
String filename = service_targets_id+"的"+service_name+"接口配置信息";
//String cname=URLDecoder.decode("config.doc" ,"UTF-8");
String cname=URLEncoder.encode(filename ,"UTF-8");
response.setHeader("Content-Disposition", "attachment;filename="+cname+".doc");
OutputStream os=response.getOutputStream();
OutputStreamWriter write = new OutputStreamWriter(os,"utf-8");  
StringBuffer sb = new StringBuffer(); 

sb.append("<html><style type='text/css'>table{border-collapse:collapse;}  table,td,th{border:1px solid black;}</style><body>");
sb.append("<div align=center style='margin-left:30px'><h2>").append(service_name).append("配置信息");
sb.append("</h2></div><div style='margin-left:30px'>");
sb.append("服务描述<br/>");
sb.append("<table width='90%'>");
//sb.append("<tr><td width='30%' align='right'>【基础接口】：</td><td width='70%'>").append(interface_id);
//sb.append("</td></tr>");
//sb.append("<tr><td width='30%' align='right'>【服务对象】：</td><td width='70%'>").append(service_targets_id);
//sb.append("</td></tr>");
//sb.append("<tr><td width='30%' align='right'>【服务状态】：</td><td width='70%'>").append(service_state);
//sb.append("</td></tr>");

sb.append("<tr><td width='30%' align='right'>【用户名称】：</td><td width='70%'>").append(service_targets_name);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【账号】：</td><td width='70%'>").append(service_targets_no);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【密码】：</td><td width='70%'>").append(service_password);
sb.append("</td></tr>");

sb.append("<tr><td width='30%' align='right'>【服务类型】：</td><td width='70%'>").append(service_type);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【服务编号】：</td><td width='70%'>").append(service_no);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【服务状态】：</td><td width='70%'>").append(service_state);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【限制访问当月数据】：</td><td width='70%'>").append(is_month_data);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【访问时间间隔(天)】：</td><td width='70%'>").append(visit_period);
sb.append("</td></tr>");
//sb.append("<tr><td width='30%' align='right'>【sql语句】：</td><td width='70%' style='word-break:break-all'>").append(sql);
//sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【服务说明】：</td><td width='70%'>").append(service_description);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【备案说明】：</td><td width='70%'>").append(regist_description);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【共享字段】：</td><td width='70%'>").append(column_name_cn);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【服务查询条件】：</td><td width='70%'>").append(service_condition);
sb.append("</td></tr>");
sb.append("<tr><td width='30%' align='right'>【用户参数】：</td><td width='70%'>").append(service_param);
sb.append("</td></tr>");
sb.append("</table><br/>");

sb.append("共享字段信息<br/>").append(column_table);


sb.append("服务限制条件<br/>");
sb.append("<table width='90%'>")
		.append(limit_table);

sb.append("</table><br/>");
sb.append("</div></body></html>");
//System.out.println(sb.toString());
byte[] b = sb.toString().getBytes();
//System.out.println(sb.toString());
write.write(sb.toString());  
write.close();  
//os.write(b);
os.flush();
os.close();
%>