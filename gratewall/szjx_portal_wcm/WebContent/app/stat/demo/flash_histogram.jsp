<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="../../../include/public_server.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="flash_histogram.3d">3D柱状图</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../js/easyversion/calendar3.js" type="text/javascript"></script>
	
  <script language="javascript" src="../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../js/search_bar/search_bar.js" type="text/javascript"></script>
  <script language="javascript" src="../js/tab/tab.js" type="text/javascript"></script>

  <script language="javascript" src="../js/swfobject.js" type="text/javascript"></script>
  <link href="../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../css/common.css" rel="stylesheet" type="text/css" />
  <style type="text/css">
	ul{
		padding:0;
		margin:0;
	}
	ul li{
		list-style:none;
		line-height:23px;
	}
	.content .data{
		text-align:center;
		overflow:hidden;
		padding:30px;
	}
	.container .content .data .flash-chart{
		width:75%;
		border:none;
		height:100%;
	}
	.container .content .data .data-select-td{
		width:250%;
		border:none;
		vertical-align:top;
	}
	.container .content .data .data-select-td .data-info-div{
		width:100px;
		cursor:normal;
		border:2px solid #cacaca;
		padding:10px 30px;
		background:#f3f3f3;
	}
	.container .content .data .data-select-td .data-info-div div{
		text-align:left;
	}
	.container .content .data .data-select-td .data-type{
		text-align:left;
	}
	.container .content .data .data-select-td li input{
		vertical-align:middle;
	}
	.container .content .data .data-select-td li label{
		vertical-align:middle;
	}
	.container .content .data .data-select-td .data-desc{
		line-height:25px;
	}
  </style>
 </head>

 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="flash_histogram.pic">
					用户发稿量柱状图
				</div>
			</div>
		</div>
		<div class="data">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tbody>
				<tr>
					<td class="flash-chart">
						<div id="flash-chart">
						</div>
					</td>
					<td class="data-select-td">
						<div class="data-info-div">
							<div class="data-desc" WCMAnt:param="flash_histogram.method">统计方式：
							</div>
							<div class="data-select">
								<select name="">
									<option WCMAnt:param="flash_histogram.doctype">文档类型统计</option>
								</select>
							</div>
							<div class="" id="data-type">
								<ul>
									<li><input type="radio" name="" value=""><label WCMAnt:param="flash_histogram.new">新稿</label></li>
									<li><input type="radio" name="" value=""><label WCMAnt:param="flash_histogram.edited">已编</label></li>
								</ul>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
			</table>
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
		Ext.apply(SEARCH_CONFIG.USER_DEFAULT,{
			select_item:[{
				text:"发稿人",
				value:"username"
			},{
				text:"所属部门",
				value:"gname"
			}]
		});
		Stat.SearchBar.UI.init(SEARCH_CONFIG.USER_DEFAULT);

		Ext.apply(TABS_CONFIG.USER_DEFAULT,{
			default_item:"barchart"
		});
		Stat.Tab.UI.init(TABS_CONFIG.USER_DEFAULT);
		wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	});
	var dataurl = "bar-3d.txt";
	swfobject.embedSWF("../open-flash-chart.swf", "flash-chart", 700, 400, "9.0.0",null,{"data-file":dataurl,"loading":"正在装载数据"});
	
	
  //-->
  </script>