define(
		[ 'require', 'jquery', 'entCommon' ],
		function(require, $, entCommon) {
			var torch = {
				_init : function() {
					require([ 'domReady' ], function(domReady) {
						domReady(function() {
					    	$("#filtrate").toggle(torch.showCondition,torch.hideCondition);//按条件筛选的显示隐藏
					    	$("div[name='dropdownpanel_appBeginDate']").on('mouseenter',torch.showCondition);
					    	$("div[name='dropdownpanel_appEndDate']").on('mouseenter',torch.showCondition);
					    	$("#myOperationGrid").gridpanel('hideColumn', 'requisitionId');
					    	$("#myOperationGrid").gridpanel("option","datarender",torch.myOperation_datarender);
					    	$("#myOperationGrid").gridpanel("option","dataurl","../../../../torch/service.do?fid=entAccountIndex&wid=myOperation");
							$("#myOperationGrid").gridpanel("reload");	
						})
					})
				},

				myOperation_datarender : function(event, obj) {
					var data = obj.data;
					var code = "";
					var label = "";
					var url = "";
					for (var i = 0; i < data.length; i++) {
						if (!data[i]) {
							continue;
						}//
						var htm = "<div class='table-panel-tr'>"
								+ "<span class='index-span-index'></span>"
								+ "<div class='ent-name omit'><p style='width:160px;text-align:center'>"
								+ data[i]['operationType']
								+ "</p></div>"
								+ "<div class='ent-name omit'><p style='width:200px;text-align:center'>经办人 : "
								+ data[i]['linkman']
								+ "</p></div>"
								+ "<div class='ent-name omit'><p style='width:100px;text-align:center'>"
								+ data[i]['appDate']
								+ "</p></div>"
								+ "<div class='state' text='state'><p style='width:100px;text-align:center'>"
								+ data[i]['state']
								+ "</p></div><div class='ent-name omit'>";
						// 按钮
						$.each(data[i]["opr"], function(index, val) {
							code = val.code;
							label = val.label;
							url = val.url;
							// 稍后通过class:setupInit绑定事件
							htm += "<a  href='" + url + "' class='" + code
									+ "' style='margin-left:20px;'>" + label
									+ "</a>";

						});
						htm += "</div></div>";
						data[i]["custom"] = htm;

						// 企业账号： 点击按条件筛选，业务状态显示的顺序和个数请按照个人账户登录后显示
						// if(flag == 0){
						// if(data[i]['state'] == '等待审查'){
						// $("#busiState").append( "<span id='2'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '通过'){
						// $("#busiState").append( "<span id='3'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '未通过'){
						// $("#busiState").append( "<span id='4'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '已核准'){
						// $("#busiState").append( "<span id='8'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '已驳回'){
						// $("#busiState").append( "<span id='9'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '审查中'){
						// $("#busiState").append( "<span id='15'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '待法人确认'){
						// $("#busiState").append( "<span id='17'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }else if(data[i]['state'] == '已终止'){
						// $("#busiState").append( "<span id='12'
						// class='condition-item'>"+data[i]['state']+"</span>");
						// }
						//					
						// if(data[i]['operationType'] == '普通设立'){
						// $("#operType").append( "<span id='10'
						// class='condition-item' >设立</span>");
						// }else if(data[i]['operationType'] == '普通变更'){
						// $("#operType").append( "<span id='20'
						// class='condition-item' >变更</span>");
						// }else if(data[i]['operationType'] == '增减补换证照'){
						// $("#operType").append( "<span id='40'
						// class='condition-item' >备案</span>");
						// }else if(data[i]['operationType'] == '市内迁入'){
						// $("#operType").append( "<span id='50'
						// class='condition-item' >注销</span>");
						// }else if(data[i]['operationType'] == '集团补换照'){
						// $("#operType").append( "<span id='65'
						// class='condition-item' >增减补换证照</span>");
						// }else if(data[i]['operationType'] == '股权质押'){
						// $("#operType").append( "<span id='92'
						// class='condition-item' >股权出质</span>");
						// }
						// }
					}
					// flag = 1;
					return data;
				},

				/**
				 * 点击按按条件筛选，显示
				 * 
				 */
				showCondition : function() {
					$(".query-panel").css("display", "block");
					$("#filtrate img").css("transform", "rotate(180deg)");
					$("#myOperationGrid").css("border", "1px solid #bfe0f9");
					$("#myOperationGrid")
							.css("border-width", "0px 1px 1px 1px");
					$("#triangle-up").css("display", "block");
				},

				/**
				 * 点击按按条件筛选，隐藏
				 */
				hideCondition : function() {
					$("#linkman").val("").focus();
					$(".query-panel").css("display", "none");
					$("#filtrate img").css("transform", "rotate(360deg)");
					$("#myOperationGrid").css("border", "0px");
					$("#triangle-up").css("display", "none");
				},
				/**
				 * 业务类型、业务状态、经办人名、申请时间条件查询
				 * 
				 * @param e
				 */

				queryDataByState : function(e) {
					// torch.hideCondition();
					$(e.target).addClass("selected");
					$("#query-img").removeClass("selected");
					$(e.target).siblings().removeClass("selected");

					var state = $("#busiState").find(".selected").attr("id");
					var operType = $("#operType").find(".selected").attr("id");
					var appBeginDate = $("#appBeginDate").datefield("getValue");
					var appEndDate = $("#appEndDate").datefield("getValue");
					var linkman = torch.powerTrim($("#linkman").val(), "g");
					// $.trim($("#linkman").val());
					var params = {
						appBeginDate : appBeginDate,
						appEndDate : appEndDate,
						operationType : operType,
						state : state,
						linkman : linkman
					};
					$("#myOperationGrid").gridpanel("option", 'datarender',
							torch.myOperation_datarender);
					$("#myOperationGrid")
							.gridpanel("option", 'dataurl',
									"../../../torch/service.do?fid=entAccountIndex&wid=myOperation");
					$("#myOperationGrid").gridpanel("option", "dataurlparams",
							params);
					$("#myOperationGrid").gridpanel("reload");

				},

				resetDataByState : function() {
					$("#appBeginDate").datefield("setValue", "");
					$("#appEndDate").datefield("setValue", "");
					$("#linkman").attr('value', '');
					$("#linkman").attr('placeholder', '经办人');

				},
				/**
				 * 业务类型、业务状态样式变化
				 * 
				 * @param e
				 */
				changeStyle : function(e) {
					$(e.target).addClass("selected");
					$("#query-img").removeClass("selected");
					$(e.target).siblings().removeClass("selected");
				},

				bindConfirm : function() {
					$(".circle1").on('click', function() {
						jazz.warn("暂未开放，敬请期待。");
					});
				},

				/**
				 * 点击按条件筛选，业务状态显示的顺序和个数请按照个人账户登录后显示
				 */
				operationStatus : function() {
					var data = '';
					$
							.ajax({
								url : "../../../apply/entAccHome/getOperationStatusList.do",
								type : "post",
								dataType : "json",
								success : function(data) {

								}
							});
					return data;
				},

				// 去除中间空格方法
				powerTrim : function(str, a) {
					var result;
					result = str.replace(/(^\s+)|(\s+$)/g, "");
					if (a.toLowerCase() == "g")
						result = str.replace(/\s/g, "");
					return result;
				}
			};
			torch._init();
			return torch;

		});
