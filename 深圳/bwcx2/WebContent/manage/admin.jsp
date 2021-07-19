<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文章发布系统</title>
<link href="../statics/inner/layout.css" rel="stylesheet" type="text/css" />
<script src="../statics/js/jquery-V30.min.js" type="text/javascript"></script>
<%-- <script src="${path}/statics/js/tzcommon.js"></script> --%>
<style>
.select{
	background: #fff;
}
</style>
</head>
<body>
	<div class="header" id="header" style="width: auto;">
		<a href="http://www.pageadmin.net" target="_blank" class="logo"></a>

		<div class="box">
			<div class="left topmenu" id="topmenu">
				<ul>

				</ul>
			</div>
		</div>
	</div>


	<div class="main" id="main" style="height: 564px; width: auto;">
		<div class="leftarea" id="leftarea">


			<div class="leftmenu-classname icon-menu-switch">
				<span class="fa fa-bars"></span>
				<span id="className">用户后台管理系统</span>
			</div>
			<div class="leftmenu" id="leftmenu">
				<div id="menu33" style="display: block;">
					<ul>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="49" class="" target="mainContainer"><i class="fa-fw fa fa-th"></i> <span>管理站点</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a  href="../manage/edit.jsp?type=1" class="shut select" target="mainContainer"  onclick="publistNews(this)"><span>图文滚动</span></a>
								</li>
							
								<li>
									<a  href="../manage/edit.jsp?type=2" class="shut" target="mainContainer" onclick="publistNews(this)"><span>媒体新闻</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=3" class="" target="mainContainer" onclick="publistNews(this)"><span>理论学习</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=4" class="shut" target="mainContainer" onclick="publistNews(this)"><span>方案计划</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=5" class="" target="mainContainer" onclick="publistNews(this)"><span>影像资料</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=6" class="" target="mainContainer" onclick="publistNews(this)"><span>征求意见</span></a>
								</li>
							</ul>
						</li>
<!-- 						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="53" class="" target="mainContainer"><i class="fa-fw fa fa-file-text-o"></i> <span>内容管理</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-menuid="info_80" href="/admin/InfoData/?table=news" class="" target="mainContainer"><span>新闻中心</span></a>
								</li>
								<li>
									<a data-menuid="info_81" href="/admin/InfoData/?table=product" class="" target="mainContainer"><span>产品中心</span></a>
								</li>
								<li>
									<a data-menuid="info_88" href="/admin/InfoData/?table=kehu" class="" target="mainContainer"><span>客户</span></a>
								</li>
								<li>
									<a data-menuid="info_82" href="/admin/InfoData/?table=feedback" class="" target="mainContainer"><span>在线留言</span></a>
								</li>
								<li>
									<a data-menuid="info_83" href="/admin/InfoData/?table=test" class="" target="mainContainer"><span>学籍管理</span></a>
								</li>
								<li>
									<a data-menuid="info_87" href="/admin/InfoData/?table=baoming" class="" target="mainContainer"><span>报名表</span></a>
								</li>
								<li>
									<a data-menuid="info_86" href="/admin/InfoData/?table=chengji" class="" target="mainContainer"><span>成绩表</span></a>
								</li>
							</ul>
						</li>
 						-->					
 					</ul>
				</div>

			</div>






		</div>
		<div class="rightarea" id="rightarea">
			<div class="historytab" id="historytab" style="width: 3200px;">
				<ul></ul>
			</div>
			<iframe name="mainContainer" id="mainContainer" src="../manage/edit.jsp?type=1" frameborder="0" scrolling="auto" style="border: none; width: 3220px; height: 564px;" width="100%" height="100%" allowtransparency="true"></iframe>
		</div>
	</div>


	<script>

	
		var $middlearea = $("#middlearea");
		var $mainContainer = $("#mainContainer");

		if (!Array.prototype.map) //ie专用
			Array.prototype.map = function(fn, scope) {
				var result = [], ri = 0;
				for (var i = 0, n = this.length; i < n; i++) {
					if (i in this) {
						result[ri++] = fn.call(scope, this[i], i, this);
					}
				}
				return result;
			};
		var WindowSize = function() {
			return [ "Height", "Width" ].map(function(name) {
				return window["inner" + name] || document.compatMode === "CSS1Compat" && document.documentElement["client" + name] || document.body["client" + name]
			});
		}
		var $historytab = $("#historytab");
		var leftWidth = 220;
		var heights, widths;
		function WinSize() {
			var str = WindowSize();
			var strs = new Array();
			strs = str.toString().split(",");
			heights = strs[0] - 50;
			widths = strs[1];
			$('#main').height(heights);
			var setHeight = heights;
			if ($historytab.length > 0) {
				//setHeight = heights - 26; //26是底部historytab高度
			}
			var mainWidth = widths - leftWidth;
			if (mainWidth < 800) {
				mainWidth = 800;
			}
			$mainContainer.width(mainWidth).height(setHeight);
			if (strs[1] < 1024) {
				$('#header').css('width', 1024 + 'px');
				$('#main').css('width', 1024 + 'px');
			} else {
				$('#header').css('width', 'auto');
				$('#main').css('width', 'auto');
			}
		}

		WinSize();
		$(window).resize(function() {
			WinSize();
		});

		$.ajaxSetup({
			cache : false
		});
		
		
		function publistNews(obj){
			var _this = $(obj);
			_this.addClass("select").parent().siblings().find("a").removeClass("select")
			
		}
		
	
		
	</script>
</body>
</html>