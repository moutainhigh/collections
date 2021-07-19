<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<freeze:html >
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<title>数据增长情况</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" />  
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/gwssi.jquery.panel.css" />  
</head>
<%
 	DataBus db =(DataBus)request.getAttribute("freeze-databus");
 	db = db.getRecord("xmlRecord");
 	String chartXMLIncre = db.getValue("chartXMLIncre");
 	String chartXMLIncreTop10 = db.getValue("chartXMLIncreTop10");
 	String chartXMLMarketEntityFull = db.getValue("chartXMLMarketEntityFull");
 	String chartXMLMarketEntityIncre = db.getValue("chartXMLMarketEntityIncre");
 %>
<freeze:body>
<freeze:form action="/txn81200001">
<freeze:title caption=""/>
<div align="right" style="width: 98%">
      <freeze:button name="moreRecord" caption="更多" enablerule="2" align="right" 
          styleClass="menu" onclick="opermore();" visible="true"/>
 </div>
<table width="95%" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td width="50%">
    
        <div class="ui-widget" style="margin:2px;">
           <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;">  最近10天系统数据增量情况  <input id='startDate' value="最近10天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div>    
               
            <div class="ui-widget-content" >
                <div id="chartContainer" style="height:195px;"></div>  
            </div>
        </div>
    </td>
   
    
     <td width="50%">
        <div class="ui-widget"  style="margin:2px;">
            <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;"> 最近10天数据表增量前5名 <input id='startDate2' value="最近10天" size="22" style="display:none;"/></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div>   -->
           </div>  
            <div class="ui-widget-content" >
                <div id="chartContainer2" style="height:195px;"></div>  
            </div>
        </div>
   
    </td>
     
    
    <tr>
    <td width="50%">
        <div class="ui-widget" style="margin:2px;">
             <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;">  最近10天市场主体数据情况(全量)  <input id='startDate4' value="最近10天" size="22" style="display:none;" /></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div>  
            <div class="ui-widget-content" >
                <div id="chartContainer3" style="height:195px;"></div>  
            </div>
        </div>
    </td>
    <td width="50%">
        <div class="ui-widget" style="margin:2px;">
            <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;"> 最近10天市场主体新设立情况  <input id='startDate5' value="最近10天" size="22" style="display:none;" /></div>
            	<!--
                <div class="fg-buttonset ui-helper-clearfix">
                    <a href="#" class="fg-button fg-button-icon-solo"><span class="ui-icon ui-icon-search"></span> Print</a>
          	    </div> -->
           </div> 
            <div class="ui-widget-content" >
             	 <div id="chartContainer4" style="height:195px;">   
                 </div>
            </div>
          </div>
    </td>
</tr>
</table>

</freeze:form>
</freeze:body>
<script type="text/javascript"><!--         

	  var myChart = new FusionCharts( "FusionCharts/Line.swf", 
      "c001", "100%", "100%", "0", "1" );
      myChart.setXMLData('<%=chartXMLIncre%>');
      //myChart.setXMLUrl("../data/xml/increase_situ.xml");  
	  myChart.render("chartContainer");   
	 
	
	  
	  var myChart2 = new FusionCharts( "FusionCharts/Column2D.swf", 
      "c002", "100%", "100%", "0", "1" );
      myChart2.setXMLData('<%=chartXMLIncreTop10%>');
      //myChart2.setXMLUrl("../data/xml/increase_top10.xml");  
	  myChart2.render("chartContainer2");
	  
	  var myChart3 = new FusionCharts( "FusionCharts/MSLine.swf", 
      "c001", "100%", "100%", "0", "1" );
      myChart3.setXMLData('<%=chartXMLMarketEntityFull%>');
      //myChart3.setXMLUrl("../data/xml/enty_data_situ.xml");  
	  myChart3.render("chartContainer3");  
	
	  
	 var myChart4 = new FusionCharts( "FusionCharts/MSLine.swf", 
      "c001", "100%", "100%", "0", "1" );
      myChart4.setXMLData('<%=chartXMLMarketEntityIncre%>');
      //myChart4.setXMLUrl("../data/xml/enty_increase.xml");  
	  myChart4.render("chartContainer4"); 
	
 
 // 增加系统使用情况报告
function opermore()
{
	var page = new pageDefine( "/txn81200001.do", "更多" );
	page.addRecord();
}
 

    // -->     
    </script>
</freeze:html>