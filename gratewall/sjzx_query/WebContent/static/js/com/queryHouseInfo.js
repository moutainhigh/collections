define(
		[ 'jquery', 'jazz', 'util' ],
		function($, jazz, util) {
			var torch = {};
			torch = {
				/**
				 * 模块初始化
				 */
				_init : function() {
					$_this = this;
					require([ 'domReady' ], function(domReady) {
						domReady(function() {
							// 页面元素处理
							$_this.loadData();
							$("#reset_button").off('click').on('click',
									torch.resetEditCondition);
							$("#save_button").off('click').on('click',
									$_this.saveData);
						});
					});
				},
				/**
				 * 根据页面打开时父页面中的楼栋编码查询该楼栋中的房屋信息
				 */
				loadData : function() {
					var id = jazz.util.getParameter("buildingCode") || "";
					var gridUrl = "../../houseCode/getHouseInfo.do?buildingCode="
							+ id;
					$("#queryHouseInfoGrid").gridpanel("option", 'dataurl',
							gridUrl);
					$("#queryHouseInfoGrid").gridpanel('reload');
				},
				/**
				 * 根据房屋编码查询房屋信息
				 */
				saveData : function() {
					var houseNum = $('#houseNum').textfield("getValue");
					var houseAdd = $('#houseAdd').textfield("getValue");
					if (!houseNum && !houseAdd) {
						$_this.loadData();
						//jazz.warn("请输入至少一项查询条件");
						return false;
					}
					$('#save_button').button('disable');
					$("#save_button").off('click');
					var timer = null;
					clearTimeout(timer);
					timer = setTimeout(function() {
						$('#save_button').button('enable');
						$("#save_button").on('click', $_this.saveData);
					}, 1000);

					var gridUrl = "../../houseCode/getHouseInfoByForm.do";
					$("#queryHouseInfoGrid").gridpanel("option", 'datarender',
							torch.backFunction);
					$("#queryHouseInfoGrid").gridpanel("option", 'dataurl',
							gridUrl);
					$("#queryHouseInfoGrid").gridpanel('query',
							[ 'queryHouseInfoPanel' ], null);

				},
				/**
				 *回调函数,可进行页面渲染或者其他操作
				 */
				backFunction : function(event, obj) {
					var data = obj.data;
					if (data.length == 0) {
						jazz.warn("该查询没有精确匹配到证照信息");
						$_this.loadData();
					} else {
						$(".nodata").css("display", "none");
					}
					return data;
				},
				// html中获取当前页的 上下文路径
				getContextPath : function() {
					var pathName = document.location.pathname;
					var index = pathName.substr(1).indexOf("/");
					var result = pathName.substr(0, index + 1);
					return result;
				},
				/**
				 *重置form面板的数据
				 */
				resetEditCondition : function() {
					for (x in torch.edit_fromNames) {
						$("div[name='" + torch.edit_fromNames[x] + "']")
								.formpanel("reset");

					}
					$('#select').comboxfield('setValue', '1', null);
				},
				edit_fromNames : [ 'queryHouseInfoPanel' ]
			};

			torch._init();
			return torch;
		});
