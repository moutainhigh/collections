<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="../../../include/public_server.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="site_piechart.sitepublishpiechart"> 站点发稿量饼状图 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/source/wcmlib/WCMConstants.js"></script>
  <script src="../../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../../js/easyversion/calendar3.js" type="text/javascript"></script>
	
  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/search_bar/search_bar.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/tab/tab.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/flash_data_render.js" type="text/javascript"></script>
  <script language="javascript" src="../flash_data_options.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/swfobject.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/json2.js" type="text/javascript"></script>
  <!--引入分页JS-->
  <script language="javascript" src="../../js/page_nav/page.js" type="text/javascript"></script>
  <!--Ajax-->
  <script src="../../../js/data/locale/ajax.js"></script>
  <script src="../../../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
  <link href="../../js/page_nav/page.css" rel="stylesheet" type="text/css" />
  <link href="../../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../graph-list.css" rel="stylesheet" type="text/css" />
 </head>
<style type="text/css">
	.export_btn{
		display:none;
	}
</style>
 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="site_piechart.sitepublishpiechart">
					站点发稿量饼状图
				</div>
			</div>
		</div>
				<div class="data">
			<div class="flash-chart" id="flash-chart">
				<div class="flash-container">
					<div class="" id="_flash_chart_">
					</div>
				</div>
			</div>
		</div>
		<div class="rightdata" id="rightdata">
			<div class="data-info-div">
				<div class="data-desc" WCMAnt:param="site_piechart.countway">统计方式：</div>
				<div id="data-select"></div>
				<div class="" id="data-options"></div>
			</div>
		</div>
		<div class="list-navigator" id="list-navigator">
		</div>
	</div>
	<div class="foot">
		<div class="" id="tab-nav">
		</div>
	</div>
  </div>
 </body>
</html>
  <script language="javascript">
  <!--
	Event.observe(window,"load",function(){
		Stat.SearchBar.UI.init(SEARCH_CONFIG.SITE_DEFAULT);

		Ext.apply(TABS_CONFIG.SITE_DEFAULT,{
			default_item:"piechart"
		});
		Stat.Tab.UI.init(TABS_CONFIG.SITE_DEFAULT);
		wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		Flash.Data.Render.init({data_url:"site_piechartdata.jsp",flash_render_el:"_flash_chart_",select_el:"data-select",options_el:"data-options"});
	
	});
	
  //-->
  </script>