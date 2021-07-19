define(
		[ 'require', 'jquery', 'common', 'util' ],
		function(require, $, common, util) {

			var $this = {
				_init : function() {
					var that = this;
					require([ 'domReady' ], function(domReady) {

						domReady(function() {

							var user = common.getCurrentUser();
							if (user) {
								$("#currentUserNameSpan").text(user.name);
								// 首页身份认证
								that.checkLoginStatus(user);
							}

							var notice = "";
							if (user.Cresult == 'idCardFail') {
								notice = '身份证校验';
							}
							if (user.Mresult == 'mobFail') {
								notice = (notice == "" ? "手机认证"
										: (notice += ",手机认证"));
							}
							if (user.Iresult == 'identityFail') {
								notice = (notice == "" ? "实名认证"
										: (notice += ",实名认证"));
							}
							if (notice != "") {
								$("#validate-name").text("未进行" + notice);
							} else {
								// 如果身份证验证、身份验证、移动电话验证通过才进行查询
								// 我的业务列表的查询
								that.personAccountBusiList();
								// 设立入口业务委托查询
								that.setupAuthBusiList();
								// 最新办理业务
								that.personAccountHomeNewest();
							}
							// 绑定页面方法
							that.bindingClik();
							$("#name-input").bind('keyup',function(event){
								if(event.keyCode=='13'){
									$("#query-img").click();// 查询按钮
								}
							});

						});// enf domReady
					});
				},
				checkLoginStatus : function(user) {
					var tmp = $('#notice-todo').html();
					if (tmp) {// 避免重复调用
						return;
					}
					var notice = "";
					var i = 1;
					if (user.Iresult == 'identityFail') {
						notice = (notice == "" ? ('<p class="noticeInfo-indent" >实名认证是业务办理的前提，您尚未通过实名认证。</p>')
								: (notice += ('<p class="noticeInfo-indent">1、实名认证是业务办理的前提，您尚未通过实名认证。</p>')));
						i++;
					}
					if (user.Mresult == 'mobFail') {
						notice = (notice == "" ? ('<p class="noticeInfo-indent" >业务办理过程中，系统会向您发送短信，请完善个人移动电话信息。</p>')
								: (notice += ('<p class="noticeInfo-indent">2、业务办理过程中，系统会向您发送短信，请完善个人移动电话信息。</p>')));
						i++;
					}
					if (user.Cresult == 'idCardFail') {
						notice = (notice == "" ? ('<p class="noticeInfo-indent" >您的注册信息中姓名与身份证号码不一致，请完成修改。</p>')
								: (notice += ('<p class="noticeInfo-indent">3、您的注册信息中姓名与身份证号码不匹配，请完成修改。</p>')));
					}
					if (user.Iresult == 'identityFail'
							|| user.Mresult == 'mobFail'
							|| user.Cresult == 'idCardFail') {
						$('#notice-todo').append(notice);
						if(document.getElementsByClassName("noticeInfo-indent").length>1){
							document.getElementsByClassName("noticeInfo-indent")[0].innerHTML="1、实名认证是业务办理的前提，您尚未通过实名认证。";
						}
						
						$("#hint-box").show();
						$("#bg").show();
					}
					
					//如果有其中两个没有认证，则进入安全中心
					if ((user.Mresult == 'mobFail'&& user.Iresult == 'identityFail')
							||(user.Mresult == 'mobFail'&&user.Cresult == 'idCardFail')
							||(user.Mresult == 'identityFail'&&user.Cresult == 'idCardFail')) {
						$(".enter-font").on('click', this.accessSecuCenter);
					} 
					
					//如果只是手机，则进入手机认证
					else if (user.Mresult == 'mobFail') {
						$(".enter-font").on('click', this.accessMobCheck);
						$("#enter-font").text("进入手机认证");
					} 
					
					//如果只是实名，则进入实名认证
					else if (user.Iresult == 'identityFail') {
						$("#enter-font").text("进入实名认证");
						$(".enter-font").on('click', this.accessIndentCheck);
					} 
					
				},
				/**
				 * 绑定页面元素click方法
				 */
				bindingClik : function() {
					$(".company-establishleft").on('click', this.setupEnter);
					$(".company-establishright").toggle(this.showSetupAuthList,this.hideSetupAuthList);// 业务委托列表的显示隐藏
					$(".authSetupInit").live('click', this.authSetupInit);// 进入办理按钮
					$(".condition-item").live('click', this.queryDataByState);// 进入办理按钮
					$("#query-img").live('click', this.queryDataByState);// 查询按钮
					$("#filtrate").on('click',this.showCondition);
					$(".query-panel").on('mouseleave',this.hideCondition);

				},
				/* 回车搜索 */
				/*
				 * EnterPress:function(e){ alert("e"); var
				 * e=event||window.event||arguments.callee.caller.arguments[0];
				 * if(e&&e.keyCode==13){
				 * $("#query-img").live('click',this.queryDataByState);//查询按钮 } },
				 */
				/**
				 * 公司设立入口，跳转页面的方法
				 */
				setupEnter : function() {
					window.location.href = '../setup/enter.html';
				},
				/**
				 * 进入中心，跳转页面的方法
				 */
				accessSecuCenter : function() {
					window.location.href = '../security/index.html';
				},
				/**
				 * 进入手机验证
				 */
				accessMobCheck : function() {
					window.location.href = '../security/mobileAuthentication.html';
				},
				/**
				 * 进入实名认证
				 */
				accessIndentCheck : function() {
					window.location.href = '../security/indentity.html';
				},
				/* 进入身份认证 */
				accessIdCheck : function() {
					window.location.href = "../index.html";
				},
				/**
				 * 点击业务委托，显示的委托列表
				 */
				showSetupAuthList : function() {
					var count = $("#circle3").text();
					if (count == '0') {
						$("#authPersonPanel").hide();
						$(".auth-person-content").css("display", "none");
						jazz.info("暂时没有委托业务！");				
					} else {
						$(".company-establishright").addClass(
								"company-establishright-click");
						$(".company-establish").css("width", "102%");
						$(".company-establishleft").css("width", "48%");
						$("#authPersonPanel").show();
						$(".auth-person-content").css("display", "block");
					}

				},
				/**
				 * 点击业务委托，隐藏的委托列表
				 */
				hideSetupAuthList : function() {
					var count = $("#circle3").text();
					if (count != '0') {
						$(".company-establishright").removeClass(
								"company-establishright-click");
						$(".company-establish").css("width", "110%");
						$(".company-establishleft").css("width", "45%");
						$("#authPersonPanel").hide();
						$(".auth-person-content").css("display", "none");
					}

				},
				/**
				 * 点击按按条件筛选，显示
				 * 
				 */
				showCondition : function() {
					
					$(".query-panel").css("display", "block");
					$("#filtrate img").css("transform", "rotate(180deg)");
					$("#myBusiGridPanel").css("border", "1px solid #bfe0f9");
					$("#myBusiGridPanel")
							.css("border-width", "0px 1px 1px 1px");
					$("#triangle-up").css("display", "block");
				},

				/**
				 * 点击按按条件筛选，隐藏
				 */
				hideCondition : function() {
					setTimeout(function(){
						$("#name-input").val("").focus();
						$(".query-panel").css("display", "none");
						$("#filtrate img").css("transform", "rotate(360deg)");
						$("#myBusiGridPanel").css("border", "0px");
						$("#triangle-up").css("display", "none");
					},2000);
						
				},

				/**
				 * 个人账户我的业务列表
				 */
				personAccountBusiList : function() {
					$("#myBusiGridPanel").gridpanel("option", 'datarender',this.datarender);
					$("#myBusiGridPanel").gridpanel("option", 'dataurl','../../../torch/service.do?m=data&fid=personAccountBusiList');
					$("#myBusiGridPanel").gridpanel("reload");
				},
				/**
				 * 业务类型、业务状态、企业名称条件查询
				 * 
				 * @param e
				 */
				queryDataByState : function(e) {

					$(e.target).addClass("selected");
					$("#query-img").removeClass("selected");
					$(e.target).siblings().removeClass("selected");
					var selectState = $("#busiState").find(".selected").attr(
							"id");
					var operType = $("#operType").find(".selected").attr("id");
					var flag = $("#flag").find(".selected").attr("id");
					var entName = $("#name-input").val();
					var params = {
						selectState : selectState,
						operType : operType,
						entName : entName,
						flag : flag
					};
					$("#myBusiGridPanel").gridpanel("option", 'datarender',this.datarender);
					$("#myBusiGridPanel").gridpanel("option", 'dataurl','../../../torch/service.do?m=data&fid=personAccountBusiList');
					$('#myBusiGridPanel').gridpanel("option", "dataurlparams",params);
					$("#myBusiGridPanel").gridpanel("reload");
				},
				/**
				 * 我的任务列表 datarender 。
				 */
				datarender : function(item, rowsdata) {
					var data = rowsdata.data;
					if (data.length == 0) {// 没有数据，可修改页面样式
						return;
					}
					var code = "";
					var label = "";
					var url = "";
					for ( var i = 0; i < data.length; i++) {
						if (!data[i]) {
							continue;
						}

						var htm = "<div class='table-panel-tr'>"
								+ "<span class='index-span-index'></span>"
								+ "<div class='table-ent-name'>"
								+ "<div class='ent-name omit' title='"
								+ data[i]['entName']
								+ "'>"
								+ data[i]['entName']
								+ "</div>"
								+ "<span class='app-date' >"
								+ data[i]['appDate']
								+ "</span>"
								+ "</div>"
								/* + "<div class='flag'>"+data[i]['flag']+"</div>" */

								+ "<div class='flag'>"
								+ (data[i]['isLeRepAuth'] == '1' ? "<img src='../../../static/image/img/24.png' />"
										: "") + "</div>"
								+ "<div class='state' text='state'>"
								+ data[i]['state'] + "</div>";
						// 按钮
						$.each(data[i]["opr"], function(index, val) {
							code = val.code;
							label = val.label;
							url = val.url;
							// 稍后通过class:setupInit绑定事件
							htm += "<a href='" + url + "' class='" + code
									+ "'>" + label + "</a>";

						});
						htm += "</div>";
						data[i]["custom"] = htm;

					}
					// 业务授权
					$('a.authOpr').live(
							'click',
							function() {
								var hrefUrl = $(this).attr("href");
								util.openWindow('authOpr', '业务提交确认授权', hrefUrl,
										710, 660);
								return false;
							}),
					// 业务终止按钮事件重新绑定
					$('a.stopOpr').live('click', function() {
						var hrefUrl = $(this).attr('href');
						/* alert(hreUrl); */
						util.openWindow('stopReq', '业务终止', hrefUrl, 593, 370);

						return false;
					});
					// 删除业务按钮事件重新设置
					$('a.deleteOpr').live('click', function() {
						var hrefUrl = $(this).attr('href');
						jazz.confirm("删除后不能恢复，确定要删除这条业务吗？", function() {
							$.ajax({
								url : hrefUrl,
								type : 'post',
								success : function(data) {
									jazz.info("删除成功", function() {
										window.location.reload();
									});

								},
								error : function(data) {
									jazz.error("删除失败");
								}
							});
						}, function() {

						});

						return false;
					});
					return data;
				},
				/**
				 * 最新办理业务
				 */
				personAccountHomeNewest : function() {
					var params = {
						url : '../../../torch/service.do?m=data&fid=personAccountHomeNewest',
						async : false,
						callback : function(data, param, res) {
							var len = data.data.rows.length;
							/*if(len==0){
								//$(".sidebar-bottom .business").next().remove();//移除掉ul标签，并追加没有数据显示的内容
								//$(".sidebar-bottom").append("<span class='noData'>无</span");
							}*/
							
							 /*
								$(".sidebar-bottom").find("ul").html();
								$(".sidebar-bottom").html("<span>您当前暂无待办事项</span>");*/
							if (len > 0) {
								var newestBusiInfo = data.data.rows[0];
								if (newestBusiInfo) {
									var appDate = newestBusiInfo.appDate;
									appDate = appDate.substring(0, 10);
									// $("#newestEntName").addClass("omit");
									$("#newestEntName").text(
											newestBusiInfo.entName);
									$("#newestEntName").attr("title",
											newestBusiInfo.entName);
									$("#newestOptype").text(
											newestBusiInfo.operationType);
									$("#newestAppDate").text(appDate);
									$("#newestState")
											.text(newestBusiInfo.state);
									if (newestBusiInfo.opr[0]) {
										$("#modify").attr("href",
												newestBusiInfo.opr[0].url);
										$("#modify").html(
												newestBusiInfo.opr[0].label);
									}
								}
							} else {
								$("#modify").css("display", "none");
								$("#newestEntName").text(" 无");
								$("#newestEntName").attr("title", "无最新业务");
								$("#newestOptype").text("无");
								$("#newestAppDate").text("无");
								$("#newestState").text("无");

								/* jazz.info("未查询到最新办理的业务"); */
							}
						}
					};
					$.DataAdapter.submit(params);
				},

				setupAuthBusiList : function() {			
					$("#authPersonPanel").gridpanel("option", 'datarender',this.setupDatarender);
					$("#authPersonPanel").gridpanel("option", 'dataurl','../../../torch/service.do?m=data&fid=authEnterNameList');
					$("#authPersonPanel").gridpanel("reload");
				},
				/**
				 * 首页业务委托查询
				 */
				setupDatarender : function(item, rowsdata) {
					var data = rowsdata.data;
					var authCount = data.length;
					document.getElementById("circle3").innerHTML = authCount;
					if (authCount.length == 0) {// 没有数据，可修改页面样式
						return;
					}
					for ( var i = 0; i < authCount; i++) {
						entName = data[i]["entName"];
						notNo = data[i]["notNo"];
						nameId = data[i]["nameId"];
						var html = "<div class='auth-person-content'>"
								+ "<div class='ent-name'>" + entName + "</div>"
								+ "<div class='not-no'>" + notNo + "</div>"
								+ "<div style='display:none;'>" + nameId
								+ "</div>"
								+ "<div  class='authSetupInit'>进入办理</div>"
								+ "</div>";
						// class='authSetupInit'
						data[i]["auth-person-content"] = html;
					}
					return data;
				},
				/**
				 * 首页业务委托进入办理
				 */
				authSetupInit : function(e) {

					var nameId = $.trim($(e.target).prev().text() || '');
					var params = {
						url : '../../../apply/setup/entrance/cpNameListEnter.do',
						params : {
							nameId : nameId,
						},
						async : false,
						callback : function(data, param, res) {
							var gid = res.getAttr("gid") || '';
							if (gid) {
								window.location.href = '../../../page/apply/setup/basic_info.html?gid='
										+ gid;
							}
						}
					};
					$.DataAdapter.submit(params);
				}
			};
			$this._init();
			return $this;
		});