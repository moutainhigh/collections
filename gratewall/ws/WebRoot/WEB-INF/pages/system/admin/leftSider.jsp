<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- http://www.jb51.net/article/84408.htm -->
<style>

</style>
<div class="leftWrap row">
	<div class="menus">
		<a href="#stockSetting" class="nav-header collapsed" data-toggle="collapse">
			<i class="glyphicon glyphicon-cog"></i>
			正常股票
			<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
		</a>
		<ul id="stockSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
		<li>
				<a href="javascript:;" data-url="zhangFuBang">
					<i class="glyphicon glyphicon-user"></i>
					涨幅榜
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="dieFuBang">
					<i class="glyphicon glyphicon-user"></i>
					跌幅榜
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="allStockList">
					<i class="glyphicon glyphicon-user"></i>
					所有
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="cons">
					<i class="glyphicon glyphicon-th-list"></i>
					控制台
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="mostImp">
					<i class="glyphicon glyphicon-asterisk"></i>
					最重要的
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="imp">
					<i class="glyphicon glyphicon-edit"></i>
					重要的
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="recentImp">
					<i class="glyphicon glyphicon-eye-open"></i>
					最近关注
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="recentWaitImp">
					<i class="glyphicon glyphicon-eye-open"></i>
					最近待关注
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="longHu">
					<i class="glyphicon glyphicon-eye-open"></i>
					龙虎榜
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="addStock">
					<i class="glyphicon glyphicon-eye-open"></i>
					添加股票
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="editStock">
					<i class="glyphicon glyphicon-eye-open"></i>
					编辑股票
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="delStock">
					<i class="glyphicon glyphicon-eye-open"></i>
					删除股票
				</a>
			</li>
		</ul>
	</div>
	
	
	
	<div class="menus">
		<a href="#stockSMSSetting" class="nav-header collapsed" data-toggle="collapse">
			<i class="glyphicon glyphicon-cog"></i>
			短信股票
			<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
		</a>
		<ul id="stockSMSSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
		<li>
				<a href="javascript:;" data-url="zhangSMSFuBang">
					<i class="glyphicon glyphicon-user"></i>
					涨幅榜
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="dieSMSFuBang">
					<i class="glyphicon glyphicon-user"></i>
					跌幅榜
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="allSMSStockList">
					<i class="glyphicon glyphicon-user"></i>
					所有短信股票
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="addSMSStock">
					<i class="glyphicon glyphicon-eye-open"></i>
					添加短信股票
				</a>
			</li>
			<li>
				<a href="javascript:;" data-url="editSMSStock">
					<i class="glyphicon glyphicon-eye-open"></i>
					编辑短信股票
				</a>
			</li>
			
			<li>
				<a href="javascript:;" data-url="delSMSStock">
					<i class="glyphicon glyphicon-eye-open"></i>
					删除短信股票
				</a>
			</li>
		</ul>
	</div>
	
	
	
	
	
	

	<div class="menus">
		<a href="#emailSetting" class="nav-header collapsed" data-toggle="collapse">
			<i class="glyphicon glyphicon-cog"></i>
			短信配置
			<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
		</a>
		<ul id="emailSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-user"></i>
					用户管理
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-th-list"></i>
					菜单管理
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-asterisk"></i>
					角色管理
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-edit"></i>
					修改密码
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-eye-open"></i>
					日志查看
				</a>
			</li>
		</ul>
	</div>


	<div class="menus">
		<a href="#phoneSMSSetting" class="nav-header collapsed" data-toggle="collapse">
			<i class="glyphicon glyphicon-cog"></i>
			邮件配置
			<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
		</a>
		<ul id="phoneSMSSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-user"></i>
					用户管理
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-th-list"></i>
					菜单管理
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-asterisk"></i>
					角色管理
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-edit"></i>
					修改密码
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="glyphicon glyphicon-eye-open"></i>
					日志查看
				</a>
			</li>
		</ul>
	</div>
</div>
