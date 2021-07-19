<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文章发布系统</title>
<link href="../statics/inner/layout.css" rel="stylesheet" type="text/css" />
<script src="../statics/js/jquery-V30.min.js" type="text/javascript"></script>
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
									<a  href="../manage/edit.jsp?type=1" class="shut" target="mainContainer"><span>图文滚动</span></a>
								</li>
							
								<li>
									<a  href="../manage/edit.jsp?type=2" class="shut" target="mainContainer"><span>媒体新闻</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=3" class="" target="mainContainer"><span>理论学习</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=4" class="shut" target="mainContainer"><span>方案计划</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=5" class="" target="mainContainer"><span>影像资料</span></a>
								</li>
								<li>
									<a  href="../manage/edit.jsp?type=6" class="" target="mainContainer"><span>征求意见</span></a>
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
				<div id="menu34" style="display: none;">
					<ul>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="236" class="" target="mainContainer"><i class="fa-fw fa fa-user"></i> <span>用户管理</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="96" href="/admin/Member/" class="" target="mainContainer"><span>会员管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="109" href="/admin/Admin/" class="" target="mainContainer"><span>管理员管理</span></a>
								</li>
							</ul>
						</li>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="103" class="" target="mainContainer"><i class="fa-fw fa  fa-cog"></i> <span>用户系统</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="238" href="/admin/MemberSet/" class="" target="mainContainer"><span>会员系统设置</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="112" href="/admin/MemberGroup/" class="" target="mainContainer"><span>会员组管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="113" href="/admin/Department/" class="" target="mainContainer"><span>部门管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="110" href="/admin/Role/" class="" target="mainContainer"><span>角色管理</span></a>
								</li>
							</ul>
						</li>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="101" class="" target="mainContainer"><i class="fa-fw fa fa-user-plus"></i> <span>用户相关</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="212" href="/admin/LoginingUser/" class="" target="mainContainer"><span>登录用户管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="123" href="/admin/MemberLock/" class="" target="mainContainer"><span>登录锁定记录</span></a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div id="menu201" style="display: none;">
					<ul>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="282" class="" target="mainContainer"><i class="fa-fw fa fa-briefcase"></i> <span>我的应用</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="FinancialCenter" data-menuid="315" href="/P/FinancialCenter/Admin/" class="shut" target="mainContainer"><span>财务管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="PA-Oauth2" data-menuid="318" href="/P/PA-Oauth2/LoginConfig/" class="shut" target="mainContainer"><span>登录插件</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="PA-WeiXin" data-menuid="320" href="/P/PA-WeiXin/Server/" class="shut" target="mainContainer"><span>微信公众号</span></a>
								</li>
							</ul>
						</li>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="202" class="" target="mainContainer"><i class="fa-fw fa fa-user-circle"></i> <span>我的账户</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="206" href="/member/MyLogin/Security/" class="" target="mainContainer"><span>账户安全</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="245" href="/admin/MyLoginLog/" class="" target="mainContainer"><span>登录日志</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="311" href="/member/MyLogin/UserInfo/" class="" target="mainContainer"><span>资料修改</span></a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div id="menu35" style="display: none;">
					<ul>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="115" class="" target="mainContainer"><i class="fa-fw fa fa-wrench"></i> <span>常用工具</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="162" href="/admin/FileManager/" class="" target="mainContainer"><span>文件管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="308" href="/admin/OnlineUpgrade/" class="" target="mainContainer"><span>在线升级</span></a>
								</li>
							</ul>
						</li>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="175" class="" target="mainContainer"><i class="fa-fw fa fa-th-list"></i> <span>网站监控</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="148" href="/admin/CacheManager/" class="" target="mainContainer"><span>缓存管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="239" href="/admin/WebLog/" class="" target="mainContainer"><span>日志管理</span></a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div id="menu241" style="display: none;">
					<ul>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="242" class="" target="mainContainer"><i class="fa-fw fa fa-download"></i> <span>我的应用</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="243" href="/admin/PluginAdmin/" class="" target="mainContainer"><span>插件管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="271" href="/admin/PluginInstall/" class="" target="mainContainer"><span>插件安装</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="309" href="/admin/TemplateInstall/" class="" target="mainContainer"><span>模板安装</span></a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div id="menu4" style="display: none;">
					<ul>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="174" class="" target="mainContainer"><i class="fa-fw fa fa-wrench"></i> <span>系统管理</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="7" href="/admin/SysSet/" class="" target="mainContainer"><span>系统设置</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="16" href="/admin/Site/" class="" target="mainContainer"><span>站点管理</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="216" href="/admin/HttpCacheSolution/" class="" target="mainContainer"><span>http缓存方案</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="306" href="/admin/AccessKey/" class="" target="mainContainer"><span>AccessKey</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="171" href="/admin/AdminMenu/" class="" target="mainContainer"><span>管理菜单</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="172" href="/admin/MemberMenu/" class="" target="mainContainer"><span>会员菜单</span></a>
								</li>
							</ul>
						</li>
						<li class="rootLi">
							<a data-isfinal="0" data-pluginname="" data-menuid="21" class="" target="mainContainer"><i class="fa-fw fa fa-database"></i> <span>表管理</span> <span class="fa fa-fw fa-angle-right arrow-icon"></span></a>
							<ul>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="25" href="/admin/Entities/" class="" target="mainContainer"><span>系统表</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="23" href="/admin/InfoTable/" class="" target="mainContainer"><span>信息表</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="161" href="/admin/CategoryTable/" class="" target="mainContainer"><span>分类表</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="24" href="/admin/SubTable/" class="" target="mainContainer"><span>附属表</span></a>
								</li>
								<li>
									<a data-isfinal="1" data-pluginname="" data-menuid="310" href="/admin/AttachmentTable/" class="" target="mainContainer"><span>附件表</span></a>
								</li>
							</ul>
						</li>
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
	</script>
</body>
</html>