<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<freeze:html >
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<title>Ӧ����־��ҳ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" />  
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/gwssi.jquery.panel.css" />  
<script type="text/javascript">
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//�ж��Ƿ�ΪIE6,������ʾbody�Ĺ�����
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
<freeze:title caption="Ӧ����־��ѯͳ��"/>
<freeze:form action="/txn62000000">
<style type="text/css">
  html { _overflow-x: hidden; _overflow-y: auto; }
</style>
<!--���忪ʼ-->
<table id='tb1' width="95%" align="center" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td width="49%">
	<div class="ui-widget" style="margin:2px;">
           <div class="fg-toolbar ui-widget-header ui-helper-clearfix">  
                <div class="fg-title" style="font-size:14px;"> ���10��ϵͳ����ʹ�����  <input id='startDate' value="���10��" size="22" style="display:none;"/></div>
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
                <div class="fg-title" style="font-size:14px;">  ���10��ϵͳʹ���������ǰ5��  <input id='startDate5' value="���10��" size="22" style="display:none;"/></div>
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
                <div class="fg-title" style="font-size:14px;">   ���������������  <input id='startDate3' value="����" size="22" style="display:none;"/></div>
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
                <div class="fg-title" style="font-size:14px;">  ���10��ʹ�ô���ǰ5�������ƣ�  <input id='startDate4' value="���10��" size="22" style="display:none;" /></div>
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
                <div class="fg-title" style="font-size:14px;"> ���10��ϵͳʹ������������ܣ�  <input id='startDate2' value="���10��" size="22" style="display:none;"/></div>
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
                <div class="fg-title" style="font-size:14px;">   ���10�������������  <input id='startDate6' value="���10��" size="22" style="display:none;"/></div>
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