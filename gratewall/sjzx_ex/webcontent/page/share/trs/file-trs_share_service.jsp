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

//start  检索字段信息
StringBuffer column_table = new StringBuffer(); 
//column_table.append("<table width='90%'>");
//column_table.append("<tr><TD width='30%'><b>表名</b></TD><TD width='30%'><b>字段名</b></TD><TD width='20%'><b>显示字段</b></TD><TD width='20%'><b>检索字段</b></TD></tr>");

if(record_show.getValue("trs_data_base")!=null &&!"".equals(record_show.getValue("trs_data_base"))){
	
	String trs_db_str = java.util.ResourceBundle.getBundle("trs").getString("searchDB");
	String trs_db_str_cn = java.util.ResourceBundle.getBundle("trs").getString("searchDBCN");
	String trs_db_ctrl = java.util.ResourceBundle.getBundle("trs").getString("searchDBctrl");
	String[] trs_dbs = trs_db_str.split(",");
	String[] trs_dbs_cn = trs_db_str_cn.split(",");
	
	String trs_column = record_show.getValue("trs_column");
	String trs_search_column = record_show.getValue("trs_search_column");
	String trs_data_base = record_show.getValue("trs_data_base");
	
	String[] tables_en = trs_data_base.split(",");
	String[] d_columns = trs_column.split(";");
	String[] s_columns = trs_search_column.split(";");
	
	if(tables_en.length>1){
		column_table.append("[说明:]如果配置多个检索表，由于各表之间字段不一致，检索字段为系统默认的字段，无需设置。<br>");
	}
	
	for(int t=0;t<tables_en.length;t++){
		String table_en = tables_en[t];//每个表名
		String table_cn = "";
		
		
		for(int t2=0;t2<trs_dbs.length;t2++){
			if(trs_dbs[t2].endsWith(table_en)){
				table_cn = trs_dbs_cn[t2];
			}
		}
		
		column_table.append("<table width='90%'>");
		if(tables_en.length>1){
			column_table.append("<tr><td colspan='2' align='left'><b>"+table_cn+"</b></td></tr>");
			column_table.append("<tr><TD width='50%'><b>字段名</b></TD><TD width='50%'><b>显示字段</b></TD></tr>");
		}else{
			
			column_table.append("<tr><td colspan='3' align='left'><b>"+table_cn+"</b></td></tr>");
			column_table.append("<tr><TD width='40%'><b>字段名</b></TD><TD width='30%'><b>显示字段</b></TD><TD width='30%'><b>检索字段</b></TD></tr>");
		}
		
		
		String table_columns_key = java.util.ResourceBundle.getBundle("trs").getString(table_en+"_key");
		String table_columns_value = java.util.ResourceBundle.getBundle("trs").getString(table_en+"_value");
		String[] columns_en = table_columns_key.split(",");
		String[] columns_cn = table_columns_value.split(",");
		for(int t1=0;t1<columns_en.length;t1++){
			String column_name = columns_en[t1];
			//column_table.append("<tr><td>"+column_name+"</td>");
			int d_index = d_columns[t].indexOf(":");
			int s_index = s_columns[t].indexOf(":");
			
			boolean display_flag = false;
			boolean search_flag = false;
			
			StringBuffer tmp_str = new StringBuffer("");
			
			tmp_str.append("<tr><td>"+columns_cn[t1]+"</td>");
			
			if(d_index!=-1){
				if(d_columns[t].substring(d_index+1).indexOf(column_name)!=-1){
					//column_table.append("<td>显示</td>");
					display_flag = true;
					tmp_str.append("<td>显示</td>");
				}else{
					//column_table.append("<td></td>");
					tmp_str.append("<td></td>");
				}
			}else{
				//column_table.append("<td></td>");
				tmp_str.append("<td></td>");
			}
			
			if(tables_en.length<2){
				if(s_index!=-1){
					if(s_columns[t].substring(s_index+1).indexOf("all")!=-1){
						//column_table.append("<td>检索</td>");
						if(tables_en.length>1){
							//search_flag = true;
							tmp_str.append("<td></td>");
						}else{
							search_flag = true;
							tmp_str.append("<td>检索</td>");
						}
						
					}else {
						if(s_columns[t].substring(s_index+1).indexOf(column_name)!=-1){
							//column_table.append("<td>检索</td>");
							if(tables_en.length>1){
								//search_flag = false;
								tmp_str.append("<td></td>");
							}else{
								search_flag = true;
								tmp_str.append("<td>检索</td>");
							}
							
						}else{
							//column_table.append("<td></td>");
							tmp_str.append("<td></td>");
						}	
					}
				}else{
					//column_table.append("<td></td");
					tmp_str.append("<td></td>");
				}
				tmp_str.append("</tr>");
			}
			
			
			
			
			
			if(display_flag || search_flag){
				column_table.append(tmp_str.toString());
			}
		}
		
		column_table.append("</table><br>");
		
	}
	

}



//end  检索字段信息



String service_targets_name = record_target.getValue("service_targets_name");
String service_targets_no = record_target.getValue("service_targets_no");
String service_password = record_target.getValue("service_password");


String service_name = record_show.getValue("trs_service_name");//服务名称


String service_targets_id = record.getValue("service_targets_id");//服务对象
//service_targets_id = ValueSet.getCodeStr( cont,  "资源管理_共享服务对象名称",  service_targets_id);

String service_state = record.getValue("service_state");//服务状态
//service_state = ValueSet.getCodeStr( cont,  "资源管理_归档服务状态",  service_state);

String service_type = record.getValue("service_type");//服务类型
//service_type = ValueSet.getCodeStr( cont,  "资源管理_数据源类型",  service_type);

//String is_month_data = "N".equals(record_show.getValue("is_month_data"))?"不限制":"限制";//服务类型
//String visit_period = record_show.getValue("visit_period");//服务类型

String service_no = record_show.getValue("trs_service_no");//服务编号
String service_description = record_show.getValue("service_description");//服务说明
//String regist_description = record_show.getValue("regist_description");//备案说明



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
String filename = service_targets_id+"的"+service_name+"配置信息";
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
sb.append("<tr><td width='30%' align='right'>【服务说明】：</td><td width='70%'>").append(service_description);
sb.append("</td></tr>");
sb.append("</table><br/>");

sb.append("字段信息<br/>").append(column_table);


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