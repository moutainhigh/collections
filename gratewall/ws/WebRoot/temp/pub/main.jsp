<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="container-fluid " style="margin-top: 66px">
	<style>
.bar_module {
	background: #2E2E2E
}

.bar_module a {
	color: #fff;
	display: block;
	font-size: 12px;
}

.bar_module .navTitle {
	height: 48px;
	line-height: 48px;
	padding-left: 15px;
}

.bar_module i {
	font-size: 14px;
}

.bar_module  li a {
	padding-left: 5rem;
}

.nav>li>a:focus,.nav>li>a:hover {
	background: #444;
}

/*信息显示按钮编辑*/
[class^="u-icon"] {
	display: inline-block;
	color: #fff;
	vertical-align: middle;
}
/*手机上的切换标签*/
.u-icon-toggle {
	position: relative;
	width: 60px;
	height: 30px;
	border-radius: 30px;
	box-shadow: 0 0 0 1px #e5e5e5;
}

.on.u-icon-toggle,.on .u-icon-toggle /*因为这里可能会在父元素上加on 也可能在子元素上加on  所以*/
	{
	box-shadow: 0 0 0 1px #4089e8;
	background-color: #4089e8;
}

.u-icon-toggle i {
	position: absolute;
	top: 0;
	left: 0;
	width: 30px;
	height: 30px;
	-webkit-box-shadow: 0 0 2px #bbb;
	border-radius: 100%;
	background-color: #fff;
	-webkit-transition: 300ms linear;
	-webkit-transform: translate3d(0, 0, 0);
}

.on.u-icon-toggle i,.on .u-icon-toggle i {
	-webkit-transform: translate3d(30px, 0, 0);
}

@media screen and (max-width: 980px) {
	.wrap {
		max-width: 640px;
		min-width: 480px;
	}
}

.highlight {
	color: red
}

.loading-layer {
	position: absolute;
	top: 41%;
	z-index: 9999;
	left: 40%;
	background: #000;
	color: #fff;
}

tbody tr:hover {
	background: #ccc !important;
}

tbody tr td {
	font-size: 12px;
}
</style>
	<div class="row clearfix">
		<div class="col-md-2 column">
			<div class="sidebar-nav bar_module">
				<a href="#dashboard-menu" class="nav-header navTitle" data-toggle="collapse"> <i class="glyphicon glyphicon-blackboard"></i> 信息维护
				</a>
				<ul id="dashboard-menu" class="nav nav-list collapse in">
					<li><a href="javascript:;" data-href="stock/addStock" onclick="show(this)">添加股票</a></li>
					<li><a href="javascript:;" data-href="stock/editStock" onclick="show(this)">编辑股票</a></li>
					<li><a href="javascript:;" data-href="stock/delStock" onclick="show(this)">删除股票</a></li>
					<li><a href="javascript:;" data-href="stock/getAllStockList" onclick="show(this)">查询所有股票</a></li>
					<li><a href="javascript:;" data-href="stock/getConsoleStockList" onclick="show(this)">查询控制台显示</a></li>
					<li><a href="javascript:;" data-href="stock/getMostImpStockList" onclick="show(this)">查询最佳榜单</a></li>
					<li><a href="javascript:;" data-href="stock/getRecentImpStockList" onclick="show(this)">查询最近关注</a></li>
					<li><a href="javascript:;" data-href="stock/getWaitStockList" onclick="show(this)">查询待关注</a></li>
					<li><a href="javascript:;" data-href="stock/getLongHuStockList" onclick="show(this)">龙虎榜</a></li>
				</ul>

				<a href="#table-menu" class="nav-header navTitle" data-toggle="collapse"> <i class="glyphicon glyphicon-scale"></i> 短信股
				</a>
				<ul id="table-menu" class="nav nav-list collapse ">
					<li><a href="javascript:;" data-href="stock/addSMSStock" onclick="show(this)">添加短信股</a></li>
				</ul>
				<a href="#menu-menu" class="nav-header collapsed navTitle" data-toggle="collapse"> <i class="glyphicon glyphicon-oil"></i> 短信配置
				</a>
				<ul id="menu-menu" class="nav nav-list collapse">
					<li><a href="javascript:;" data-href="stock/addSMSSetting" onclick="show(this)">手机号码管理</a></li>
				</ul>

				<a href="#order-menu" class="nav-header navTitle" data-toggle="collapse"> <i class="glyphicon glyphicon-object-align-left"></i> 邮件配置
				</a>
				<ul id="order-menu" class="nav nav-list collapse">
					<li><a href="javascript:;" data-href="manage/addEmail" onclick="show(this)">添加收信人</a></li>
					<li><a href="javascript:;" data-href="manage/editEmail" onclick="show(this)">编辑收信人</a></li>
					<li><a href="javascript:;" data-href="manage/delEmail" onclick="show(this)">删除收信人</a></li>
				</ul>
			</div>
		</div>
		<div class="col-md-10 column">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">数据列表</h3>
				</div>
				<div class="panel-body">
					<div id="box">
						<div class="wrap">
							<div style='padding:10px;'>
								<form class='bs-example bs-example-form' role='form'>
									<div class='row'>
										<div class='col-lg-6'>
											<div class='input-group'>
												<input type='text' class='form-control' id='search' placeholder='请输入要查找的股票代码或名称'> <span class='input-group-btn'> <!-- <button class='btn btn-default' type='button'>快速查找股票!</button> -->
												</span>
											</div>
										</div>
									</div>
								</form>
							</div>
							<div id="contents"></div>
							<div>
								<ul id="pageLimit"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	function show(obj) {
		var _this = $(obj);
		var url = _this.data("href");
		_this.parent().addClass("select").siblings().removeClass("select");
		loadingData(url);
	}

	$(window).load(function() {
		loadingData("stock/getAllStockList");
	});

	function loadingData(url) {
		$.ajax({
			url : url,
			beforeSend : function(data) {
				loading();
			},
			data:{
				pageIndex:1,
				pageSize:20
			},
			success : function(data) {
				removeLoading();
				$("#contents").empty().html(data);
			},
			error : function(data) {

			}
		});
	}

	function editStock(obj) {
		var _this = $(obj);
		var code = _this.data("scode");
	}

	function updateInfo(stockCode, type, flag) {
		$.ajax({
			url : "stock/updateStock",
			data : {
				stockCode : stockCode,
				type : type,
				flag : flag
			},
			success : function(data) {

			},
			error : function(data) {

			}
		});

	}

	//查找
	$('#search').on('input propertychange', function() {
		var _this = $(this);
		var keyword = _this.val();
		$(".hightLights").each(function() {
			$(this).html($(this).text().replace(keyword, "<label style='color:red'>" + keyword + "</label>"));
			/* var _that = $(this);
			var str = _that.html();
			var regexKeyword = new RegExp(inputTxt, "igm");
			console.log(regexKeyword)
			_that.html(str.replace(regexKeyword, "<span class='highlight'>" + regexKeyword.source + "</span>"));  */

		});

		$.ajax({
			url : "stock/getStockByCodeOrName",
			type : "post",
			data : {
				keyword : keyword
			},
			beforeSend : function() {
				loading();
			},
			success : function(data) {
				removeLoading();
				$("#contents").empty().html(data);
			},
			error : function(data) {

			}
		})
	});

	$('#pageLimit').bootstrapPaginator({
		currentPage : 1,//当前的请求页面。
		totalPages : 20,//一共多少页。
		size : "normal",//应该是页眉的大小。
		bootstrapMajorVersion : 3,//bootstrap的版本要求。
		alignment : "right",
		numberOfPages : 5,//一页列出多少数据。
		itemTexts : function(type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页";
			case "next":
				return "下一页";
			case "last":
				return "末页";
			case "page":
				return page;
			}
		}
	});
</script>
<!-- http://www.17sucai.com/pins/demoshow/24678 -->
<!-- https://www.cnblogs.com/liyunhua/p/4533581.html css3-->
<!-- https://www.cnblogs.com/yiliang9117/p/7255482.html -->