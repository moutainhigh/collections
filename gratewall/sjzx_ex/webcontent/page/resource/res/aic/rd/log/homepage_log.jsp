<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<freeze:html >
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<title>应用日志首页</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" />  
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/gwssi.jquery.panel.css" />  
<script type="text/javascript">
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//判断是否为IE6,是则显示body的滚动条
	if(typeof document.body.style.maxHeight === "undefined") {
		document.body.scroll = 'yes';
		$("#tb1").css("width","95%");
	}
}

_browse.execute( '__userInitPage()' );
</script>
</head>
<%
 	DataBus db =(DataBus)request.getAttribute("freeze-databus");
 	db = db.getRecord("xmlRecord");
 	String chartXML = db.getValue("chartXML");
 	String chartXML24 = db.getValue("chartXML24");
 	String chartXMLTop5 = db.getValue("chartXMLTop5");
 	String chartXMLByOrganization = db.getValue("chartXMLByOrganization");
 	String chartXMLByFun = db.getValue("chartXMLByFun");
 	String chartXMLByTotal = db.getValue("chartXMLByTotal");
 %>
<freeze:body style="">
<freeze:title caption="应用日志查询统计"/>
<freeze:form action="/txn62000000">
<style type="text/css">
  html { _overflow-x: hidden; _overflow-y: auto; }
</style>
<!--主体开始-->
<table id='tb1' width="95%" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td width="49%">
	<div class="ui-widget" style="margin:2px;">
           <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;"> 最近10天系统总体使用情况  <input id='startDate' value="最近10天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div>    
               
            <div class="ui-widget-content" >
                <div id="chartContainer" style="height:220px;"></div>  
            </div>
        </div>
     </td>
     <td>
     	<div class="ui-widget" style="margin:2px;">
            <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;">  最近10天系统使用情况区县前5名  <input id='startDate5' value="最近10天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div> 
            <div class="ui-widget-content" >
             	 <div id="chartContainer4" style="height:220px;">   
                 </div>
            </div>
        </div>
     </td>
     </tr><tr>
     <td width="49%">
    	 <div class="ui-widget" style="margin:2px;">
            <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;">   今天在线人数情况  <input id='startDate3' value="今天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div>  
            <div class="ui-widget-content" >
                <div id="chartContainer5" style="height:200px;"></div>  
            </div>
        </div>
     </td>

	<td >
		  <div class="ui-widget" style="margin:2px;">
             <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;">  最近10天使用次数前5名所（科）  <input id='startDate4' value="最近10天" size="22" style="display:none;" /></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div>  
            <div class="ui-widget-content" >
                <div id="chartContainer3" style="height:200px;"></div>  
            </div>
        </div>
	</td>
	</tr>
<tr>
	<td width="49%">
		<div class="ui-widget" style="margin:2px;">
            <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;"> 最近10天系统使用情况（按功能）  <input id='startDate2' value="最近10天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div>  
            <div class="ui-widget-content" >
                <div id="chartContainer2" style="height:200px;"></div>  
            </div>
         </div>  
	</td>
	<td >
	  <div class="ui-widget" style="margin:2px;">
             <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;">   最近10天最大在线人数  <input id='startDate6' value="最近10天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div> 
            <div class="ui-widget-content" >
                <div id="chartContainer6" style="height:200px;"></div>  
            </div>
        </div>
	</td>
</tr>
</table>
</freeze:form>

</freeze:body>
<script type="text/javascript"><!--         

	  var myChart = new FusionCharts( "FusionCharts/MSLine.swf", 
      "c001", "100%", "100%", "0", "1" );
      myChart.setXMLData('<%=chartXMLByTotal%>'); 
      //myChart.setXMLUrl("../data/xml/sys_use_situation.xml");  
	  myChart.render("chartContainer");   
	 
	  var myChart2 = new FusionCharts( "FusionCharts/Pie3D.swf", 
      "c002", "100%", "100%", "0", "1" );
      //myChart2.setXMLUrl("../data/xml/sys_use_situ_fun.xml");
      myChart2.setXMLData('<%=chartXMLByFun%>');  
	  myChart2.render("chartContainer2");   
	  
	  var myChart3 = new FusionCharts( "FusionCharts/Bar2D.swf", 
      "c003", "100%", "100%", "0", "1" );
      myChart3.setXMLData('<%=chartXMLTop5%>');
      //myChart3.setXMLUrl("../data/xml/use_count_top5.xml");  
	  myChart3.render("chartContainer3");  
	 
	  var myChart4 = new FusionCharts( "FusionCharts/Bar2D.swf", 
      "c003", "100%", "100%", "0", "1" );
      myChart4.setXMLData('<%=chartXMLByOrganization%>');
      //myChart4.setXMLUrl("../data/xml/sys_use_situ_org.xml");  
	  myChart4.render("chartContainer4");  
	
	 var myChart5 = new FusionCharts( "FusionCharts/Line.swf", 
      "c002", "100%", "100%", "0", "1" );
      myChart5.setXMLData('<%=chartXML%>'); 
	  myChart5.render("chartContainer6"); 
	  
	   var myChart = new FusionCharts( "FusionCharts/Line.swf", 
      "c001", "100%", "100%", "0", "1" );
      myChart.setXMLData('<%=chartXML24%>'); 
      //myChart.setXMLUrl("../data/xml/online24.xml");  
	  myChart.render("chartContainer5"); 

    // -->     
    </script>
</freeze:html>