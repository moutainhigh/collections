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
							// 初始化列表数据
							$("#save_button").off('click').on('click',
									$_this.queryCFList);
							$("#reset_button").off('click').on('click',
									torch.resetEditCondition);
							torch.getRegorg();
							$("div[name='region']").comboxfield("option",
									"change", $_this._getAdminbrancode);
							util.exports('editFunc', $_this.editFunc);
						});
					});
				},
				/**
				 * 根据数据库中的数据查询区域信息以供前端选择
				 */
				getRegorg : function() {
					var params = {
						url : '../../houseCode/getRegCode.do',
						callback : function(data, param, res) {
							for ( var i in data.data) {
								$("div[name='region']").comboxfield(
										'addOption', data.data[i].text,
										data.data[i].value);
							}
						}
					};
					$.DataAdapter.submit(params);
				},
				/**
				 * 根据根据区域信息进行街道信息的查询以供前端进行选择
				 */
				_getAdminbrancode : function() {
					$("div[name='street']").comboxfield("option", "disabled",
							true);
					var text = $('div[name="region"]').comboxfield('getValue');
					if (text != "") {
						$("div[name='street']").comboxfield("option",
								"disabled", false);
						$("div[name='street']").comboxfield(
								"option",
								"dataurl",
								"../../houseCode/getStrCode.do?regioncode="
										+ text);
						$("div[name='street']").comboxfield("reload");
					}
				},
				/**
				 * 查询楼栋信息
				 */
				queryCFList : function() {
					var buildingname = $('#buildingName').textfield("getValue");
					var buildingadd = $('#buildingAdd').textfield("getValue");
					var buildingcode = $('#buildingCode').textfield("getValue");
					var street = $('#street').comboxfield("getValue");
					var region = $('#region').comboxfield("getValue");
					if (!buildingname && !buildingadd && !buildingcode
							&& !street && !region) {
						jazz.warn("请输入至少一项查询条件");
						return false;
					}
					$('#save_button').button('disable');
					$("#save_button").off('click');
					var timer = null;
					clearTimeout(timer);
					timer = setTimeout(function() {
						$('#save_button').button('enable');
						$("#save_button").on('click', $_this.queryCFList);
					}, 500);
					var gridUrl = "../../houseCode/getBuildingInfo.do";
					$("#queryHouseCodeGrid").gridpanel("option", 'datarender',
							torch.backFunction);
					$("#queryHouseCodeGrid").gridpanel("option", 'dataurl',
							gridUrl);
					$("#queryHouseCodeGrid").gridpanel('query',
							[ 'queryHouseCodePanel' ], null);
				},
				/**
				 * 列表回调函数，可进行渲染或其他操作
				 */
				backFunction : function(event, obj) {
					var data = obj.data;
					if (data.length == 0) {
						jazz.warn("该查询没有精确匹配到证照信息");
					} else {
						$(".nodata").css("display", "none");
					}
					for (var i = 0; i < data.length; i++) {
						data[i]["opt"] = '<a href="javascript:void(0)" onclick="ebaic.editFunc(\''
								+ data[i]["buildingCode"]
								+ '\')">'
								+ "房屋信息"
								+ '</a>';
					}
					return data;
				},
				/**
				 * 根据楼栋信息在打开的新窗口中显示该楼栋下的房屋
				 */
				editFunc : function(buildingcode) {
					util
							.openWindow(
									"queryHouseInfoGrid",
									"房屋信息",
									$_this.getContextPath()
											+ "/page/comselect/queryHouseInfo.html?buildingCode="
											+ buildingcode, 800, 576);
				},
				//从html中获取当前页的 上下文路径
				getContextPath : function() {
					var pathName = document.location.pathname;
					var index = pathName.substr(1).indexOf("/");
					var result = pathName.substr(0, index + 1);
					return result;
				},
				/**
				 * 重置方法
				 */
				resetEditCondition : function() {
					for (x in torch.edit_fromNames) {
						$("div[name='" + torch.edit_fromNames[x] + "']")
								.formpanel("reset");

					}
					$('#select').comboxfield('setValue', '1', null);
					$("div[name='street']").comboxfield("option", "disabled",
							true);

				},
				edit_fromNames : [ 'queryHouseCodePanel']
			};
			torch._init();
			return torch;
		});
