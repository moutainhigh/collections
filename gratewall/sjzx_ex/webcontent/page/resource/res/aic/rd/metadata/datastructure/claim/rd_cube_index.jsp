<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import="utils.system"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
DataBus db = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = null;
try{
	rs = db.getRecordset("record");
}catch(Exception e){
	//fixme: deal with exceptions
}
%>
<script language="JavaScript">
function dates(name,num){
          this.name=name;
          this.num=num;
    }
var map={};
</script>
<% 
if(rs!=null){
    for(int i=0; i<rs.size(); i++) {
        DataBus singleRecord = (DataBus) rs.get(i);
        %>
        <script language="JavaScript">
        
        map["<%=i%>"]=new dates("<%=singleRecord.getValue("name")%>","<%=singleRecord.getValue("countnum")%>");
        </script>
        <% 
			}
	}


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<freeze:html >
  <head>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
  <script type="text/javascript">
function mouseOver(sys_rd_type)
{
  $('#content').load("<%=request.getContextPath()%>/txn80008880.ajax?primary-key:sys_rd_type="+sys_rd_type, 
  function(responseText,textStatus,XMLHttpRequest){
  alert(textStatus);
});

}
function mouseOut()
{

}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{    
	alert("ready");
}
</script>


  </head>
  
<freeze:body>
 <span onmouseover="mouseOver(1)" onmouseout="mouseOut()">���뼯</span>
 <span onmouseover="mouseOver(2)" onmouseout="mouseOut()">��</span>
 <div id="content">
 </div>
 <div id="show">
 <ul id="con1">

 </ul>
 </div>
   <script language="JavaScript">
       for ( var key in map ) 
       {
	   	var con = "<li>"+map[key].name+":"+map[key].num+"</li>";
       $('#con1').append(con);
      // document.getElementById(map[key].name).innerHTML = map[key].num;
       }
        </script>
    <script type="text/javascript">
  _browse.execute("__userInitPage()");
  </script>
  </freeze:body>
</freeze:html>
