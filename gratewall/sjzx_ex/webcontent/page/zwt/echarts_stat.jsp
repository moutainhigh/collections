<!DOCTYPE html>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<html>
<head>
<title>服务对象概览-统计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    
<%
     DataBus context = (DataBus) request.getAttribute("freeze-databus");
     String target_name=context.getRecord("select-key").getValue("service_targets_name");
     String stime_str=context.getRecord("record").getValue("stime_str");
     String snum_str=context.getRecord("record").getValue("sstat_num_str");
     String scount_str=context.getRecord("record").getValue("sstat_count_str");
     String sinfo_str=context.getRecord("record").getValue("sstat_info_str");
     String ctime_str=context.getRecord("collect").getValue("ctime_str");
     String cnum_str=context.getRecord("collect").getValue("cstat_num_str");
     String ccount_str=context.getRecord("collect").getValue("cstat_count_str");
     String cinfo_str=context.getRecord("collect").getValue("cstat_info_str");
     String svrId = request.getParameter("select-key:service_targets_id");
%>

<script type="text/javascript" src="/page/zwt/js/echarts-all.js" charset="utf-8"></script>
<script type="text/javascript">
	var share_info=<%=sinfo_str%>;	
	var collec_info=<%=cinfo_str%>;
	var sinfo_array=[];
	var cinfo_array=[];
	
    function DoRefresh(){
       myChart.showLoading();
       setTimeout(refresh, 1000);
    }
    function servicePart(){
		var svrId = '<%=svrId%>';
		var svrName = '<%=target_name%>';
		document.getElementById('select-key:service_targets_id').value = svrId;
		document.getElementById('select-key:service_targets_name').value = svrName;
		document.getElementById('form1').submit();
	}
    function initshare(){
    	var share_option = {
  		      title: {
  		          text: '<%=target_name%>共享数据统计'
  		      },
  		      tooltip: {
  		          trigger: 'axis',
  		          formatter: function(params) {
  		              var res = params[1][1];
  		              var res_info=sinfo_array[params[1][1]];
  		              res += '<br/>共享数据量：' + params[0][2]+"条";
  		              res += '<br/>共享次数：' + params[1][2]+"次";
  		              res += '<br/>平均响应时间：' + res_info[2]+"秒";
  		              res += '<br/>出错次数：' + res_info[3]+"次";
  		              return res;
  		          }
  		      },
  		      legend: {
  		          data: ['共享数据量', '共享次数']
  		      },
  		      toolbox: {
  		          show: true,
  		          feature: {
  		              dataZoom: true,
  		              magicType: ['line', 'bar'],
  		              restore: true,
  		              saveAsImage: false
  		          }
  		      },
  		      dataZoom: {
  		          show: true,
  		          realtime: true,
  		          start: 50,
  		          end: 100
  		      },
  		      xAxis: [{
  		          type: 'category',
  		          boundaryGap: true,
  		          data: [
  		            <%=stime_str%>
  		          ]
  		      }],
  		      yAxis: [{
  		          type: 'value',
  		          scale: true,
  		          precision: 2,
  		          splitNumber: 10,
  		          splitArea: {
  		              show: true
  		          }
  		      }, {
  		          type: 'value',
  		          scale: true,
  		          splitNumber: 10,
  		          splitArea: {
  		              show: true
  		          }
  		      }],
  		      series: [{
  		          name: '共享数据量',
  		          type: 'bar',
  		          yAxisIndex: 1,
  		          symbol: 'none',
					

  		          data: [<%=snum_str%>]
  		      }, {
  		          name: '共享次数',
  		          type: 'line',
	  		        itemStyle: {
					       normal: {
					           color: "#1f77b4",
					           lineStyle: {
					               color : "#1f77b4"
					           }
					       }
					   },
  		          symbol: 'none',
  		          data: [<%=scount_str%>]
  		      }]
  		  };
  		var chartShare = echarts.init(document.getElementById('share_graphic'));
  		chartShare.setOption(share_option);
    }
    function initcollect(){
    	var collect_option = {
  		      title: {
  		          text: '<%=target_name%>采集数据统计'
  		      },
  		      tooltip: {
  		          trigger: 'axis',
  		          formatter: function(params) {
  		              var res = params[1][1];
  		              var res_info=cinfo_array[params[1][1]];
  		              res += '<br/>采集数据量：' + params[0][2]+"条";
  		              res += '<br/>采集次数：' + params[1][2]+"次";
  		              res += '<br/>平均响应时间：' + res_info[2]+"秒";
  		              res += '<br/>出错次数：' + res_info[3]+"次";
  		              return res;
  		          }
  		      },
  		      legend: {
  		          data: ['采集数据量', '采集次数']
  		      },
  		      toolbox: {
  		          show: true,
  		          feature: {
  		              dataZoom: true,
  		              magicType: ['line', 'bar'],
  		              restore: true,
  		              saveAsImage: true
  		          }
  		      },
  		      dataZoom: {
  		          show: true,
  		          realtime: true,
  		          start: 50,
  		          end: 100
  		      },
  		      xAxis: [{
  		          type: 'category',
  		          boundaryGap: true,
  		          data: [
  		            <%=ctime_str%>
  		          ]
  		      }],
  		      yAxis: [{
  		          type: 'value',
  		          scale: true,
  		          precision: 2,
  		          splitNumber: 10,
  		          splitArea: {
  		              show: true
  		          }
  		      }, {
  		          type: 'value',
  		          scale: true,
  		          splitNumber: 10,
  		          splitArea: {
  		              show: true
  		          }
  		      }],
  		      series: [{
  		          name: '采集数据量',
  		          type: 'bar',
  		          yAxisIndex: 1,
  		          symbol: 'none',
  		          data: [<%=cnum_str%>]
  		      }, {
  		          name: '采集次数',
  		          type: 'line',
  		        itemStyle: {
				       normal: {
				           color: "#1f77b4",
				           lineStyle: {
				               color : "#1f77b4"
				           }
				       }
				   },
  		          symbol: 'none',
  		          data: [<%=ccount_str%>]
  		      }]
  		  };
  		var chartShare = echarts.init(document.getElementById('collect_graphic'));
  		chartShare.setOption(collect_option);
    }
    function init(){
    	if(share_info){
    		sinfo_array=share_info[0];
    		initshare();
    	}
    	if(collec_info){
    		cinfo_array=collec_info[0];    	
        	initcollect();
    	}
    }
    
  </script>
  <style>
  .nav {margin:0 auto; text-align:center; font-weight:bold; border-bottom:3px solid #579cc6;}
.nav a {display:inline-block; margin:0 3px; height:25px; background:url(/page/zwt/images/bg-index.jpg) left bottom no-repeat; padding-left:15px; color:#666; text-decoration:none; cursor:pointer;}
.nav a span {display:inline-block; height:25px; line-height:25px; background:url(/page/zwt/images/bg-index.jpg) right bottom no-repeat; padding-right:15px;}
.nav a.sel, .nav a:hover {background:url(/page/zwt/images/bg-index.jpg) left top no-repeat; color:#FFF;}
.nav a.sel span, .nav a:hover span {background:url(/page/zwt/images/bg-index.jpg) right top no-repeat;}
.nav a.set {background:url(/page/zwt/images/bg-index.jpg) left top no-repeat; color:#FFF; }
.nav a.set span {background:url(/page/zwt/images/texiao/bg-index.jpg) right top no-repeat;}
  
  </style>
   
</head>

<body onload="init()" style="padding:0;margin:0;">
<table align="center" style="width:95%;border-collapse:collapse;margin-top: 3px;" cellpadding="0" cellspacing="0">
	<!-- <tr><td>
	<div class="nav">
		<a href="javascript:;" onclick="javascript:servicePart()"><span>服务配置信息</span></a>
		<a class="sel" href="javascript:;" ><span>交换数据统计</span></a>
	</div></td>
	</tr> -->
	<tr><td style="padding-top:3px;">
		<div style="width:98%; height: 400px;" id="share_graphic"></div>
	</td></tr>
	<tr><td style="padding-top:3px;">
		<div style="width:98%; height: 400px;" id="collect_graphic"></div>
	</td></tr>
</table>
 <form action="/txn53000111.do" method="post" id="form1" name="form1" target="_self">
  <input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id"/>
  <input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name"/>
</form>

</body>

</html>
