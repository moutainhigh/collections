<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="../../../include/public_server.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="flash_barchart.title"> 用户发稿量柱状图 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/source/wcmlib/WCMConstants.js"></script>
	
  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>

  <script language="javascript" src="../../js/swfobject.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/json2.js" type="text/javascript"></script>
  <!--画FLASH组件，如果有特殊情况，需要继承该对象  Flash.Data.Render  参考doc_stat/flash_data_options.js-->
  <script language="javascript" src="../../js/flash_data_render.js" type="text/javascript"></script>
  <!--
   注：这里是引用的文档发稿量上独有的js，如果没有右边的下拉选项则可以不引用该文件

   这里面会初始化右边的下拉选项等，只是一个flash可以不引入这个JS
  -->
  <script language="javascript" src="../../doc_stat/flash_data_options.js" type="text/javascript"></script>

  <link href="../../css/common.css" rel="stylesheet" type="text/css" />

  <!--
   注：这里是引用的文档发稿量上独有的CSS，如果没有右边的下拉选项则可以不引用该文件
  -->
  <link href="../../doc_stat/flash_chart_common.css" rel="stylesheet" type="text/css" />
  <!--Ajax-->
  <script src="../../../js/data/locale/ajax.js"></script>
  <script src="../../../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
 </head>

 <body>
  <div class="container">
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="flash_barchart.pic">
					用户发稿量柱状图
				</div>
			</div>
		</div>
		<div class="data">
			<div class="data-table">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tbody>
					<tr>
						<td class="flash-chart" id="flash-chart">
							<div class="flash-container">
								<div class="" id="_flash_chart_">
								</div>
							</div>
						</td>
						<td class="data-select-td">
							<div class="data-info-div">
								<div class="data-desc" WCMAnt:param="flash_barchart.method">统计方式：
								</div>
								<div id="data-select">
								</div>
								<div class="" id="data-options">
								</div>
							</div>
						</td>
					</tr>
				</tbody>
				</table>
			</div>
		</div>
  </div>
 </body>
</html>
  <script language="javascript">
  <!--
	
	Event.observe(window,"load",function(){
		/**
		   注：这里是引用的文档发稿量上独有的js，如果没有右边的下拉选项则可以不引用该文件
		   这里面会初始化右边的下拉选项等，如果没有这部分，可以使用如下代码，加载数据
			Flash.Data.Render.init({data_url:"../../include/doc_stat/user/barchartdata.jsp",flash_render_el:"_flash_chart_");
		*/
		Flash.Data.Render.init({data_url:"../../include/doc_stat/user/barchartdata.jsp",flash_render_el:"_flash_chart_",select_el:"data-select",options_el:"data-options"});
	
	});
	
  //-->
  </script>