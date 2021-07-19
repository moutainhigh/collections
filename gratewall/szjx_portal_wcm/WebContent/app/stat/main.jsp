<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@include file="../include/public_server.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="main.jsp.performanceassesssystem"> 绩效考核系统 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <link href="main.css" rel="stylesheet" type="text/css" />

  <script language="javascript" src="../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script language="javascript" src="js/swfobject.js" type="text/javascript"></script>
  <script language="javascript" src="js/common.js" type="text/javascript"></script>
  <script language="javascript" src="main.js" type="text/javascript"></script>
  <script language="javascript">
  <!--
	/*
	注：TimeItem=2:表示为当月
		TimeItem=1:表示为当天
		TimeItem=3:表示为当季
		TimeItem=4:表示为今年
	*/
	/*
	*  柱状图数据请求URL
	&desc="+encodeURI("本月新增文档类型统计柱状图")
	*/
	var dataurlForBar = "main/barchartdata.jsp?TimeItem=2";
	/*
	*  饼状图数据请求URL
	&desc="+encodeURI("本月新增文档状态统计饼状图")
	*/
	var dataurlForPie = "main/piechartdata.jsp?TimeItem=2";
	/*
	*  走势图数据请求URL
	&desc="+encodeURI("本月每天发稿量走势图")
	*/
	var dataurl = "main/trendchartdata.jsp?TimeItem=2&TimeStep=1";
	/*
	*  画三个flash
	*/
	
	
	
  //-->
  </script>
  <style type="text/css">
  .head-l{
	  display:none;
  }
  
  </style>
 </head>

 <body>
	<div class="container" id="container">
		<table border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr style="height:50%">
					<td width="33%">
						<!--本月新增文档类型统计-->
						<!--
						<div class="head-l">
							<div class="r">
								<div class="c"><span class="title">本月新增文档类型统计柱状图</span><span class="detail"><a href="doc_stat/user/user_datatable.jsp?TimeItem=2">详细</a></span>
								</div>
							</div>
						</div>
						-->
						<div class="head-title">
							<a href="doc_stat/user/user_datatable.jsp?TimeItem=2" WCMAnt:param="main.jsp.doc.one">本月新增文档类型统计柱状图</a>
						</div>
						<div class="body-l">
							<div class="r">
								<div class="c">
									<div class="flash_chart"  id="month_doc_type_barflash_data" >
										<span style='color:red' WCMAnt:param="main.jsp.tips">你的浏览器不支持flash的显示，可能是你在加载项中禁用了该控件或者没有安装该控制，请先启用或者下载flash插件。</span>
									</div>
									<script language="javascript">
									<!--
										swfobject.embedSWF("open-flash-chart.swf", "month_doc_type_barflash_data", 220, 130, "9.0.0","expressInstall.swf",{"data-file":encodeURIComponent(dataurlForBar),"loading":"正在装载数据",save_image_message:"保存为图片"},{wmode:"opaque"});
									//-->
									</script>
								</div>
							</div>
						</div>
						<div class="foot-l">
							<div class="r">
								<div class="c">
								</div>
							</div>
						</div>
					</td>
					<td width="33%">
						<!--本月新增文档状态统计-->
						<!--
						<div class="head-l">
							<div class="r">
								<div class="c"><span class="title">本月新增文档状态统计饼状图</span><span class="detail"><a href="doc_stat/user/user_datatable.jsp?TimeItem=2">详细</a></span>
								</div>
							</div>
						</div>
						-->
						<div class="head-title">
							<a href="doc_stat/user/user_datatable.jsp?TimeItem=2" WCMAnt:param="main.jsp.doc.two">本月新增文档状态统计饼状图</a>
						</div>
						<div class="body-l">
							<div class="r">
								<div class="c">
								<div class="flash_chart" id="month_doc_status_pieflash_data">
								<span style='color:red'  WCMAnt:param="main.jsp.tips">你的浏览器不支持flash的显示，可能是你在加载项中禁用了该控件或者没有安装该控制，请先启用或者下载flash插件。</span>
								</div>
								<script language="javascript">
								<!--
									swfobject.embedSWF("open-flash-chart.swf", "month_doc_status_pieflash_data", 220, 130, "9.0.0","expressInstall.swf",{"data-file":encodeURIComponent(dataurlForPie),"loading":"正在装载数据",save_image_message:"保存为图片"},{wmode:"opaque"});
								//-->
								</script>
								</div>
							</div>
						</div>
						<div class="foot-l">
							<div class="r">
								<div class="c">
								</div>
							</div>
						</div>
					</td>
					<td rowspan="2" style="position:relative">
						<div class="head-l" style="display:block ">
							<div class="r">
								<div class="c"><span class="title"  WCMAnt:param="main.jsp.pub.doc.rank">本月发布文档点击量排行（TOP15）</span><span class="detail"><a href="hitscount_stat/document/document_hitscount_table.jsp?TimeItem=2"  WCMAnt:param="main.jsp.detail">详细</a></span>
								</div>
							</div>
						</div>
						<div class="body-l" id="hitscount" style="overflow:hidden;">
							<div class="r">
								<div class="c">
								<iframe  src="main/document_hitscount_stat.jsp" width="100%" height="100%" frameBorder=0>
								</iframe>
								</div>
							</div>
						</div>
						<div class="foot-l">
							<div class="r">
								<div class="c">
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<!--本月每天发稿量走势图-->
					<!--
						<div class="head-l">
							<div class="r">
								<div class="c"><span class="title">本月每天发稿量走势图</span><span class="detail"><a href="doc_stat/user/user_datatable.jsp?TimeItem=2">详细</a></span>
								</div>
							</div>
						</div>
						-->
						<div class="head-title">
							<a href="doc_stat/user/user_datatable.jsp?TimeItem=2"  WCMAnt:param="main.jsp.fgl.trend.chart">本月每天发稿量走势图</a>
						</div>
						<div class="body-l">
							<div class="r">
								<div class="c">
									<div class="" id="month_flash_chart_data">
										<span style='color:red'  WCMAnt:param="main.jsp.tips">你的浏览器不支持flash的显示，可能是你在加载项中禁用了该控件或者没有安装该控制，请先启用或者下载flash插件。</span>
									</div>
									<script language="javascript">
									<!--
										swfobject.embedSWF("open-flash-chart.swf", "month_flash_chart_data", 500, 130, "9.0.0","expressInstall.swf",{"data-file":encodeURIComponent(dataurl),"loading":"正在装载数据",save_image_message:"保存为图片"},{wmode:"opaque"});
									//-->
									</script>
								</div>
							</div>
						</div>
						<div class="foot-l">
							<div class="r">
								<div class="c">
								</div>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
 </body>
</html>