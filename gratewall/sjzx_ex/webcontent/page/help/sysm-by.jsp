<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="description" content="" />
<script type="text/javascript" src="page/help/helpRes/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="page/help/helpRes/myLib.js"></script>
<script type="text/javascript">

	$(function() {
		myLib.progressBar();
	});

	$.include([ 'page/help/helpRes/jquery-ui-1.8.18.custom.min.js', 'page/help/helpRes/jquery.winResize.js', 'page/help/helpRes/jquery-smartMenu-min.js', 'page/help/helpRes/desktop.js' ]);
	$.include([ 'page/help/helpRes/desktop.css', 'page/help/helpRes/jquery-ui-1.8.18.custom.css', 'page/help/helpRes/jquery-smartMenu.css' ]);
	$.include([ 'page/help/helpRes/index.js' ]);

	$(function() {
		myLib.stopProgress();
	});
</script>
<style type="text/css">
/*桌面导航按钮*/
#navBar{width:330px;height:32px;line-height:32px;position:absolute;left:50%;margin-left:-100px;top:0px;background:url(page/help/helpRes/navBg1.png) no-repeat center center;text-align:center;z-index:1099;}
#navBar a{display:inline-block;width:24px;height:24px;background:url(page/help/helpRes/navbg.png) repeat left top;margin:4px 3px 0 0;outline:none; }
#navBar a:hover{background-position:-48px top; }
#navBar a.currTab{background-position:-24px top; }
/*桌面图标*/
#desktopPanel{width:100%;height:auto;position:relative;z-index:70;}
#desktopInnerPanel{position:relative;background:url(page/help/helpRes/bg.png) repeat;}




</style>
</head>
<body>
	<a href="http://www.jsfoot.com/" class="powered_by">Powered by jsfoot.com</a>
	<div id="wallpapers"></div>
	<div id="navBar">
		<a href="#a" class="currTab" title="系统概览"></a>
		<a href="#b" class="currTab" title="资源管理"></a>
		<a href="#c" class="currTab" title="共享服务"></a>
		<a href="#d" class="currTab" title="采集任务"></a>
		<a href="#e" class="currTab" title="运行监控"></a>
		<a href="#f" class="currTab" title="日志管理"></a>
		<a href="#g" class="currTab" title="系统管理"></a>
		
	</div>
	<div id="desktopPanel">
		<div id="desktopInnerPanel">
		<ul id="a" class="deskIcon">
				
				
			</ul>
			<ul id="b" class="deskIcon currDesktop">
				<li class="desktop_icon" id="fwdxgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						服务对象管理<s></s>
					</div></li>
				
				<li class="desktop_icon" id="sjkgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						数据库管理<s></s>
					</div></li>
                
                <li class="desktop_icon" id="cjzygl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						采集资源管理<s></s>
					</div></li>
					
					<li class="desktop_icon" id="gxzyck"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						共享资源查看<s></s>
					</div></li>
					
					<li class="desktop_icon" id="bzgfgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						标准规范管理<s></s>
					</div></li>
					
					<li class="desktop_icon" id="fwsjgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						服务时间管理<s></s>
					</div></li>
			</ul>
			
			<ul  id="c" class="deskIcon">
				
				<li class="desktop_icon" id="jkgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						接口管理<s></s>
					</div></li>
					<li class="desktop_icon" id="fwpz"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						服务配置<s></s>
					</div></li>
					
			</ul>
			
			<ul id="d" class="deskIcon">
				
				<li class="desktop_icon" id="rwpz"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						任务配置<s></s>
					</div></li>
					<li class="desktop_icon" id="cjrw"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						采集任务<s></s>
					</div></li>
					
			</ul>
			
			<ul id="e" class="deskIcon">
				
				<li class="desktop_icon" id="ssjk"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						实时监控<s></s>
					</div></li>
					<li class="desktop_icon" id="jkzbgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						监控指标管理<s></s>
					</div></li>
					<li class="desktop_icon" id="jkzbgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						警情发现与管理<s></s>
					</div></li>
			</ul>
			
			<ul id="f" class="deskIcon">
				
				<li class="desktop_icon" id="rzcx"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						日志查询<s></s>
					</div></li>
					<li class="desktop_icon" id="rztj"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						日志统计<s></s>
					</div></li>
					<li class="desktop_icon" id="sybg"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						使用报告<s></s>
					</div></li>
			</ul>
			
			<ul id="g" class="deskIcon">
				
				<li class="desktop_icon" id="xtzxyh"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						系统在线用户<s></s>
					</div></li>
					<li class="desktop_icon" id="qxgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						权限管理<s></s>
					</div></li>
					<li class="desktop_icon" id="gnqx"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						功能权限<s></s>
					</div></li>
					<li class="desktop_icon" id="tzgl"><span class="icon"><img src="page/help/helpRes/icon.png" /> </span>
					<div class="text">
						通知管理<s></s>
					</div></li>
			</ul>
		</div>
	</div>
	<!--desktopPanel end-->
	<div id="taskBarWrap">
		<div id="taskBar">
			<div id="leftBtn">
				<a href="#" class="upBtn"></a>
			</div>
			<div id="rightBtn">
				<a href="#" class="downBtn"></a>
			</div>
			<div id="task_lb_wrap">
				<div id="task_lb"></div>
			</div>
		</div>
	</div>
	




<div>
		<div class="services_left_content">
			<ul>
				<div id="item">
					<h5>
						资源管理
					</h5>
				</div>
				<li>
					<a  href="/page/help/pageRes/服务对象管理.htm" target="content">服务对象管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/数据源管理.htm" target="content">数据源管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/采集资源管理.htm" target="content">采集资源管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/共享资源查看.htm" target="content">共享资源查看</a>
				</li>
				<li>
					<a href="/page/help/pageRes/标准规范管理.htm" target="content">标准规范管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/服务时间管理.htm" target="content">服务时间管理</a>
				</li>
				<div id="item">
					<h5>
						共享服务
					</h5>
				</div>
				<li>
					<a href="/page/help/pageRes/接口管理.htm" target="content">接口管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/服务配置.htm" target="content">服务配置</a>
				</li>
				<div id="item">
					<h5>
						采集任务
					</h5>

				</div>
			
				<li>
					<a href="/page/help/pageRes/采集任务.htm" target="content">采集任务</a>
				</li>
				<div id="item">
					<h5>
						运行监控
					</h5>
				</div>
				<li>
					<a href="/page/help/pageRes/运行监控.htm" target="content">实时监控</a>
				</li>
				<li>
					<a href="/page/help/pageRes/运行监控.htm" target="content">监控指标管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/运行监控.htm" target="content">警情发现与管理</a>
				</li>
				<div id="item">
					<h5>
						日志管理
					</h5>
				</div>
				<li>
					<a href="/page/help/pageRes/日志查询.htm" target="content">日志查询</a>
				</li>
				<li>
					<a href="/page/help/pageRes/日志统计.htm" target="content">日志统计</a>
				</li>
				<li>
					<a href="/page/help/pageRes/使用报告.htm" target="content">使用报告</a>
				</li>
				<div id="item">
					<h5>
						系统管理
					</h5>
				</div>
				<li>
					<a href="/page/help/pageRes/系统用户在线.htm" target="content">系统在线用户</a>
				</li>
				<li>
					<a href="/page/help/pageRes/权限管理.htm" target="content">权限管理</a>
				</li>
				<li>
					<a href="/page/help/pageRes/功能权限.htm" target="content">功能权限</a>
				</li>
				<li>
					<a href="/page/help/pageRes/通知管理.htm" target="content">通知管理</a>
				</li>

			</ul>
		</div>
	</div>

</body>
</html>
