<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="statics/easy/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="statics/easy/themes/icon.css">
<script type="text/javascript" src="statics/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="statics/easy/jquery.easyui.min.js"></script>
<title>文章查找</title>
<style>
* {
	margin: 0;
	padding: 0
}

.l-btn-text {
	padding: 0 6px;
}

.l-btn-focus {
	outline: none;
}

body {
	font-size: 12px;
	background: #F1F1F1;
	font-family: "微软雅黑";
}

a {
	text-decoration: none;
}

a:visited {
	color: #333;
	text-decoration: none;
}

a:link {
	color: #333;
	text-decoration: none;
}

a:focus {
	outline: none;
}

a:hover {
	color: #ff6600;
}

ul,li {
	list-style: none;
}

.box-header {
	height: 65px;
	position: relative;
}

.box-wrap {
	height: 25px;
	margin-top: 10px;
	color: #666666;
	font-size: 14px;
}

.box-main {;
	margin: 0 auto;
	width: 1024px;
}

.box-left {
	width: 240px;
	background: #F8F8F8;
	float: left;
	height: 660px;
	padding-top: 5px;
}

.box-left ul li a {
	padding-left: 32px;
	display: block;
	width: 168px;
	height: 38px;
	border-bottom: 1px solid #e0e0e0;
	background: url(statics/images/xx_l_style.png) left center no-repeat;
	font-size: 14px;
	line-height: 38px;
	text-align: center;
}

a.choose {
	color: #ff6600;
	background: url(statics/images/xx_sl_style.png) left center no-repeat
		!important;
}

.box-right {
	float: left;
	background: #fff;
	width: 780px;
	height: 660px;
	padding-top: 5px;
}

.clear {
	clear: both
}

.box-footer {
	height: 40px;
	margin-top: 25px;
	background-color: #185AAE;
	text-align: center;
	color: #fff;
	font: 14px/40px Microsoft YaHei;
	background-color: #185AAE;
}

td {
	border-style: solid !important;
}

.datagrid-cell {
	padding: 4px 0px 4px 2px;
	font-size: 14px;
	white-space: normal !important;
	text-align: left;
}

div[class~= ~datagrid-cell-c1-] {
	text-align: left
}

/*
定义隔行换式
*/
.datagrid-row-alt {
	background: #efefef;
}

.openLink {
	margin-left: 29px;
	background: url(statics/images/icons2.gif) no-repeat 4px -144px ;
	width: 30px;
	display: block;
}
</style>
</head>
<body>

	<!-- box-header start -->
	<div class="box-header">
		<div class="header" width="100%" height="65px" style="text-align: center;" scrolling="no">
			<div class="top" style="background: url(statics/images/banner1.jpg) repeat-x; width: 100%;">
				<div class="topWrap" style="position: relative; width: 1000px; margin: auto;">
					<img src="statics/images/banner.jpg">
					<p id="userName" style="position: absolute; top: 41px; right: 5px;; color: white; font-family: '微软雅黑';">
						当前用户:
						<a href="####" style="color: #fff">
							<span id="loginName">长软测三</span>
						</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- box-header ended -->
	<div class="box-main">
		<div class="box-wrap">我的位置：首页>文章查找</div>

		<div class="box-left">
			<ul style="display: inline-block; padding: 0 12px;">
				<li>
					<a href="javascript:void(0);" class="choose" data-cid="57">热点新闻</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="58">通知公告</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="59">工作简报</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="60">学习园地</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="61">常用表格</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="62">常用链接</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="63">协查去函</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-cid="64">协查回函</a>
				</li>
			</ul>
			<img src="statics/images/building.png" width="186" height="314" alt="">
		</div>


		<div class="box-right">
			<form id="pt" style="padding-left: 36px;">
				<input type="hidden" id="channelId">
				<label>
					发稿人：
					<input class="easyui-textbox" style="width: 120px; height: 32px" name="createName" id="author">
				</label>
				<label>
					标题：
					<input class="easyui-textbox" style="width: 120px; height: 32px" name="title" id="title">
				</label>
				发稿时间：
				<input id="startTime" type="text" style="width: 120px; height: 32px" class="easyui-datetimebox" name="startCreateTime" data-options="" value=""
					style="width: 150px">
				—
				<input id="endTime" class="easyui-datetimebox" style="width: 120px; height: 32px" name="endCreateTime" data-options="" value=""
					style="width: 150px">


				<div style="padding: 5px; text-align: center;">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="query()">查询</a>
					<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clear()">重置</a> -->
				</div>

			</form>
			<table id="mydatagrid" data-options="singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'DOCTITLE',align:'center'" width="60%">文档标题</th>
						<th data-options="field:'CRTIME',align:'center',formatter:formatTimeStr" width="12%">发稿时间</th>
						<th data-options="field:'OPERUSER',align:'center'" width="10%">发稿人</th>
						<th data-options="field:'DOCCHANNEL',align:'center',formatter:formatOperSpeci" width="10%">所在栏目</th>
						<th data-options="field:'DOCPUBURL',align:'center',formatter:formatOper" width="10%">查看</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- box right end -->
		<div class="clear"></div>
	</div>
	<div class="box-footer">版权所有：深圳市市场和质量监督管理委员会&nbsp;&nbsp;技术支持：长城计算机软件与系统有限公司</div>
	<script>
		function isEmpty(val) {
			val = $.trim(val);
			if (val == null)
				return true;
			if (val == undefined || val == 'undefined')
				return true;
			if (val == "")
				return true;
			if (val.length == 0)
				return true;
			if (!/[^(^\s*)|(\s*$)]/.test(val))
				return true;
			return false;
		}

		function isNotEmpty(val) {
			return !isEmpty(val);
		}

		$(function() {
			loadingData();
		});
		function loadingData() {
			$('#mydatagrid').datagrid(
					{
						nowrap : false,//设置为true，当数据长度超出列宽时将会自动截取
						striped : true,//设置为true将交替显示行背景。
						pageSize : 20,//每页显示的记录条数 
						pageList : [10,20,30,40,50],//可以设置每页记录条数的列表   
						url : 'gwssi/portal?c=queryWCm&m=queryDocumentMessage',//url调用Action方法
						queryParams : {
							docChannelId : 57
						},
						loadMsg : '数据装载中......',
						fitColumns : true,//允许表格自动缩放，以适应父容器
						rownumbers : true,//行号 
						pagination : true,//分页
						rownumbers : true,
						onLoadSuccess : function() {
							$('.datagrid-btable').find('div.datagrid-cell')
									.css("text-align", "left");
						}
					//行数
					});
			//分页信息http://blog.csdn.net/longlongmylove/article/details/5975995
			var p = $('#mydatagrid').datagrid('getPager');
			$(p).pagination({
				beforePageText : '第',//页数文本框前显示的汉字   
				afterPageText : '页    共 {pages} 页',
				displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			/* onSelectPage:function(pageNumber,pageSize){  
			       alert(pageNumber);  
			       alert(pageSize); 
			}  */
			});
		}

		/**
		http://www.jeasyui.net/plugins/155.html
		必输项:pageNumber，pageSize，docChannelId
		选输项:title，startCreateTime，endCreateTime，createName
		 */
		function query() {
			var docChannelId = $("#channelId").val();
			var createName = $('#author').textbox('getValue');
			var title = $('#title').textbox('getValue');
			var startCreateTime = $('#startTime').textbox('getValue');
			var endCreateTime = $('#endTime').textbox('getValue');

			if (isEmpty(docChannelId)) {
				docChannelId = 57;
			}

			$('#mydatagrid').datagrid({
				queryParams : {
					docChannelId : docChannelId,
					createName : createName,
					title : title,
					startCreateTime : startCreateTime,
					endCreateTime : endCreateTime
				}
			});
			//分页信息http://blog.csdn.net/longlongmylove/article/details/5975995
			var p = $('#mydatagrid').datagrid('getPager');
			$(p).pagination({
				beforePageText : '第',//页数文本框前显示的汉字   
				afterPageText : '页    共 {pages} 页',
				displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			/* onSelectPage:function(pageNumber,pageSize){  
			       alert(pageNumber);  
			       alert(pageSize); 
			}  */
			});
		}

		function formatOper(val, row, index) {

			return '<a class="openLink" href="'+val+'" target="_blank" >&nbsp;</a>';
		}

		function formatTimeStr(val, row, index) {
			return new Date(row.CRTIME).toLocaleDateString();
		}

		function formatOperSpeci(val, row, index) {
			if (val == 57) {
				return '新闻投稿';
			} else if (val == 58) {
				return '通知公告';
			} else if (val == 59) {
				return '工作简报';
			} else if (val == 60) {
				return '学习园地';
			} else if (val == 61) {
				return '常用表格';
			} else if (val == 62) {
				return '常用链接';
			} else if (val == 63) {
				return '协查去函';
			} else {
				return '协查回函';
			}

		}

		/**/
		$(function() {
			$(".box-left a").click(function() {
				var _this = $(this);
				var cid = _this.data("cid");
				$("#channelId").val(cid);
				_this.parent().siblings().find("a").removeClass("choose");
				_this.addClass("choose");
				query();
			});

		});

		
	</script>
</body>
</html>