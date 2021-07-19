<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="pub.jsp"%>
<!-- http://blog.csdn.net/u013347241/article/details/51915821 -->
<!-- http://blog.csdn.net/u010688587/article/details/53641910 -->
<body>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">短信股涨幅榜</h3>
	</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-4">
				<div class="input-group" style="padding-bottom: 8px">
					<input type="text" id="queryInputs" class="form-control" placeholder="请输入代码或者名称">
				</div>
				<!-- /input-group -->
			</div>
		</div>
		<table class="table" id="list"></table>
	</div>
</div>
<script>
	function initTalbe() {
		$('#list').bootstrapTable(
				{
					url : "smsStock/getZhangFuStock",
					dataType : "json",
					pagination : true,
					search : false,//显示搜索框
					striped : true,
					sidePagination : "server",//服务端处理分页,
					queryParams : queryParams,
					columns : [
							{
								field : 'stockCode',
								title : '代码',
								formatter : function(value, row, index) {
									if (row.currentPrice > row.prevClose) {
										return "<span class='red'>" + value + "</span>";
									} else if (row.currentPrice == row.prevClose) {
										return "<span class='#ccc'>" + value + "</span>";
									} else {
										return "<span class='green'>" + value + "</span>";
									}
								}
							},
							{
								field : 'stockName',
								title : '名称',
								formatter : function(value, row, index) {
									if (row.currentPrice > row.prevClose) {
										return "<span class='red'>" + value + "</span>";
									} else if (row.currentPrice == row.prevClose) {
										return "<span class='#ccc'>" + value + "</span>";
									} else {
										return "<span class='green'>" + value + "</span>";
									}
								}
							},
							{
								field : 'isconsole',
								title : '控制台',
								formatter : function(value, row, index) {
									if (value == 0) {
										return "<span class='onedit btn-success notAllowed'  onclick='edit(this)' data-type='isconsole' data-stockcode='" + row.stockCode
												+ "'>否</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='isconsole'>是</span>";
									} else {
										return "<span class='onedit btn-success notAllowed' onclick='edit(this)' data-type='isconsole' data-stockcode='" + row.stockCode
												+ "'>是</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='isconsole'>否</span>";
									}
								}
							},
							{
								field : 'mostImp',
								title : '是否最重要',
								formatter : function(value, row, index) {
									if (value == 0) {
										return "<span class='onedit btn-success notAllowed'  onclick='edit(this)' data-type='mostimp' data-stockcode='" + row.stockCode
												+ "'>否</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='mostimp'>是</span>";
									} else {
										return "<span class='onedit btn-success notAllowed' onclick='edit(this)' data-type='mostimp' data-stockcode='" + row.stockCode
												+ "'>是</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='mostimp'>否</span>";
									}
								}
							},
							{
								field : 'isImp',
								title : '是否重要',
								formatter : function(value, row, index) {
									if (value == 0) {
										return "<span class='onedit btn-success notAllowed'  onclick='edit(this)' data-type='isimp' data-stockcode='" + row.stockCode
												+ "'>否</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='isimp'>是</span>";
									} else {
										return "<span class='onedit btn-success notAllowed' onclick='edit(this)' data-type='isimp' data-stockcode='" + row.stockCode + "'>是</span>"
												+ "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='isimp'>否</span>";
									}
								}
							},
							{
								field : 'isRecentImport',
								title : '是否最近关注',
								formatter : function(value, row, index) {
									if (value == 0) {
										return "<span class='onedit btn-success notAllowed'  onclick='edit(this)' data-type='isrecentimport' data-stockcode='" + row.stockCode
												+ "'>否</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='isrecentimport'>是</span>";
									} else {
										return "<span class='onedit btn-success notAllowed' onclick='edit(this)' data-type='isrecentimport' data-stockcode='" + row.stockCode
												+ "'>是</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='isrecentimport'>否</span>";
									}
								}
							},
							{
								field : 'waitImp',
								title : '是否最近待关注',
								formatter : function(value, row, index) {
									if (value == 0) {
										return "<span class='onedit btn-success notAllowed'  onclick='edit(this)' data-type='waitimp' data-stockcode='" + row.stockCode
												+ "'>否</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='waitimp'>是</span>";
									} else {
										return "<span class='onedit btn-success notAllowed' onclick='edit(this)' data-type='waitimp' data-stockcode='" + row.stockCode
												+ "'>是</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='waitimp'>否</span>";
									}
								}
							},
							{
								field : 'longhuBang',
								title : '龙虎榜',
								formatter : function(value, row, index) {
									if (value == 0) {
										return "<span class='onedit btn-success notAllowed'  onclick='edit(this)' data-type='longhubang' data-stockcode='" + row.stockCode
												+ "'>否</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='longhubang'>是</span>";
									} else {
										return "<span class='onedit btn-success notAllowed' onclick='edit(this)' data-type='isconsole' data-stockcode='" + row.stockCode
												+ "'>是</span>" + "<span class='onedit btn-warning nonselects' onclick='edit(this)' data-stockcode='" + row.stockCode
												+ "' data-type='longhubang'>否</span>";
									}
								}
							}, {
								field : 'thumbsImg',
								title : 'K线',
								formatter : function(value, row, index) {
									return "<img src='" + value + "' onclick='showRelate(this)' data-stockcode = '" + row.stockCode + "' style='width:30px;height:30px'/>"
								}
							}, {
								field : 'currentPrice',
								title : '当前',
								formatter : function(value, row, index) {
									if (row.currentPrice > row.prevClose) {
										return "<span class='red'>" + value + "</span>";
									} else if (row.currentPrice == row.prevClose) {
										return "<span class='#ccc'>" + value + "</span>";
									} else {
										return "<span class='green'>" + value + "</span>";
									}
								}
							}, {
								field : 'prevClose',
								title : '昨收'
							}, {
								field : 'todayMinPrice',
								title : '今日最低'
							}, {
								field : 'todayMaxPrice',
								title : '今日最高'
							} ,
							{
								field : 'pointerPrice',
								title : '提醒价格'
							},
							{
								field : 'pointerRates',
								title : '提醒幅度'
							},
							{
								field : 'addDate',
								title : '添加日期'
							},
							{
								field : 'types',
								title : '类型'
							}],
					data : list
				});
	}

	$(function() {
		initTalbe();
		queryStockByCodeOrName();
	});

	function edit(obj) {
		var _this = $(obj);
		if (!_this.hasClass("notAllowed")) {
			_this.toggleClass("notAllowed nonselects").siblings().toggleClass("notAllowed nonselects");
			var type = _this.data("type");
			var html = _this.html();
			var txt = "0";
			if (html != "否") {
				txt = "1";
			}
			var code = _this.data("stockcode")
			$.ajax({
				url : 'stock/change',
				data : {
					code : code,
					type : type,
					changeTo : txt
				},
				beforeSend : function() {

				},
				success : function(data) {

				},
				error : function(data) {

				}
			})
		}
	}

	function queryStockByCodeOrName() {
		$("input").on("input propertychange", function() {
			$('#list').bootstrapTable(('refresh')); // 很重要的一步，刷新url！
			
		});
	}

	function queryParams(params) {
		return {
			codes :$("#queryInputs").val(), // 请求时向服务端传递的参数
			limit : params.limit, // 每页显示数量
			offset : params.offset, // SQL语句偏移量
		}
	}
</script>
</body>
</html>