define([ 'require', 'jquery', 'entCommon', 'pdfobject' ],function(require, $, entCommon, PDFObject) {
			var torch = {
				_init : function() {
					var $_this = this;// 指代当前torch对像
					require([ 'domReady' ], function(domReady) {
						domReady(function() {
							$_this.loadPDF();
							$_this.initPreview();// 初始化显示内容
							$_this.getClientHeight();
							// 绑定页面方法
							$_this.bingingBtnEvents();

							$("#backButton").on('click', $_this.backStep);
							$("#close").on('click', $_this.close)
							$("#showPicDiv").on('click', $_this.close);
						});
					});
				},

				loadPDF : function() {
					var gid = jazz.util.getParameter('gid')||"";
					var params = {
		    		        url : '../../../req/getPdfRelativePath.do',
		    		        params:{ gid:gid},
		    		        callback : function(data, param, res) {
		    		        	var row = res.getAttr("result");
		    		        	var pdfURL = "";
		    		        	var pageNo = "";
		    		        	if(row){
		    		        		pdfURL = row.docRelativePath||"";
		    		        		pageNo = row.pageNo||"";
		    		        	}
								var options = {
									pdfOpenParams : {
										navpanes : 0,
										toolbar : 0,
										statusbar : 0,
										view : "FitV",
										page:pageNo
									}
								};
								PDFObject.embed(pdfURL, "#PDF", options);
		    		        },
		    		        error : function(responseObj) {
		    				    if(responseObj["responseText"]){
		    				    	var err = jazz.stringToJson(responseObj["responseText"]);
		    				    	if(err['exceptionMes']){
		    				    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes'],function(){
		    				    		});				    					    		
		    				    	}else{
		    				    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
		    				    	}
		    				    }else{
		    				    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
		    				    }
		    				    
		    				    return false;
	    				}
		    		};  	
					$.DataAdapter.submit(params);
				},

				// 绑定按钮事件
				bingingBtnEvents : function() {
					$("div[name='backToApp']").on("click", torch.backApp);
					$("div[name='confirm_button']").on("click", torch.confirm);
					$("div[name='back_button']").on("click", function(){
						//window.history.go(-1);//返回上一页	
						var back = document.referer;  //得到来源页
						if(back==null||back==undefined){
							window.location.href="../../../page/apply/business_verify/home.html";
						}else{
							window.location.href= back;  //点击哪里来，就回到哪里
						}
					});
				},

				// 初始化
				initPreview : function() {
					var html = "";
					html="<div class='hint-boxtop initInfo'>"+
							"			<p class='noticeInfo inits'>1、工商登记全程电子化业务申请必须经过企业法定代表人及全体股东对申请内容进行确认后才允许提交，如发现申请内容存在问题，可退回给申请人修改。</p>"+
							"			<p class='noticeInfo'>2、提交后如被登记机关退回需修改的，重新提交时仍需再次确认。</p>"+
							"			<p class='noticeInfo'>3、法定代表人及股东不能及时完成上述操作的，可授权申请人代为确认，法人及股东可以对信息进行查询，如有问题请及时终止业务。</p>"+
							"			<p class='noticeInfo'>4、无论选择何种提交方式，均代表其对全部申请内容的认可，并承担相应的法律责任。</p>"+
							"			<div class='full-line initsLine'></div>"+
							"			<div class='enterBtns'>"+
							"				<span id='enter-font' class='commonBt hasKnow' name='hasKnow' style='cursor: pointer'>我知道了</span>"+
							"			</div>"+
							"		</div>";
					
					
					var win = jazz.widget({
						vtype : 'window',
						name : 'showTips',
						title : '操作提示',
						titlealign : 'left',
						titledisplay : true,
						width : 550,
						height : 330,
						visible : true,
						modal:true,
						closable:false,
						content : html,
					});
					// 打开窗口

					win.window('open');
					
					$("span[name='hasKnow']").on("click",function() {
						var win = $("div[name='showTips']");
						if (win != null) {
							win.window('close');
						}
					});
				},

				// 业务确认
				confirm : function() {
					
					var html = "";
					html = "<div class='hint-boxtop entConfirmTop'>"
							+ "			<span class='applicantfont'></span>"
							+ "			<p class='noticeInfo'>如果当前业务提交后，经工作人员审批后被退回修改，需要重新提交申请，提交时仍需要法定代表人和股东对申请内容进行确认。</p>"
							+ "			<p class='noticeInfo'>您可以选择授权申请人代为确认，申请人确认行为等同于您的确认行为。</p>"
							+ "			<div class='textline'></div>"
							+ "			<p class='noticeInfo' style='position: relative;'>请确认是否授权申请人在后续的业务办理过程中对企业基本信息、股东信息、董事、经理、监事信息、住所产权证明文件、公司章程等内容进行修改</p>"
							//+ "			<div name='withAuth' class='ckbox' vtype='checkboxfield' label='' labelalign='right' width='120' ></div>"
							+ "			<div class='full-line confirmLine'></div>"
							+ "			<div class='enterConfirmBtns'>"
							+ "				<span id='enter-font' class='commonBt entBtn' style='cursor: pointer' name='bisConfirm'>业务确认</span>"
							+ "				<span id='enter-font' class='commonBt entBtn' style='cursor: pointer' name='bisConfirmCancel'>取消</span>"
							+ "			</div></div>";

					var win = jazz.widget({
						vtype : 'window',
						name : 'confirmSubmit',
						title : '确认提交',
						titlealign : 'left',
						titledisplay : true,
						width : 700,
						height : 340,
						modal:true,
						visible : true,
						content : html,
						items:[{
				        	vtype: "checkboxfield",
				        	name: "withAuth",
				        	width: 95,
				        	label: "",
				        	dataurl:[{value: '1',text: '确认授权'}]       	
				        }]
					});
					// 打开窗口

					win.window('open');
					
					$("div[name='withAuth']").css({"position":"absolute","bottom":"118px","left":"316px"});
					// 重新绑定事件
					$("span[name='bisConfirm']").on("click",function() {
						var gid = jazz.util.getParameter('gid');
						var url = "../../../apply/setup/submit/confirmSubmit.do";
						var withAuth = 0;
						var ckbox = $("div[name='withAuth']").checkboxfield('getValue');//withAuth就是页面上 是否勾选授权，选中1，没有选为0
						if(ckbox){
							withAuth = 1;
						}
						var params = {
							url : url,
							params : {
								gid : gid,
								withAuth : withAuth
							},
							async : false,
							callback : function(data, param,res) {
								
								var rows = res.getAttr("msg");
								console.log(rows);
								
								var type = res.getAttr("user_type");
								jazz.info(rows, function() {
									if($.trim(rows)=="没有完成实名身份认证，不能进行业务确认"){
										return;
									}
									else if($.trim(rows)=="没有查询到业务相关的人员信息"){
										return;
									}
									else if($.trim(rows)=="只有股东或法定代表人才能执行确认操作"){
										return;
									}else{
										torch.backHome(type)
									}
									
									//;
								});
							}
						};
						$.DataAdapter.submit(params);

					});

					$("span[name='bisConfirmCancel']").on("click", function() {
						var win = $("div[name='confirmSubmit']");
						if (win != null) {
							win.window('close');
						}

					});

				},

				// 退回申请人
				backApp : function() {
					var html = "";
					html = "<div class='hint-boxtop backToAppWindosTop'>"
							+ "			<span class='applicantfont'></span>"
							+ "			<p class='noticeInfo'>"
							+ "				<span class='backToAppText ml_8 fl'>"
							+ "					操作人:"
							+ "					<i class='backToAppText' name='operter'></i>"
							+ "				</span>"
							+ "				<span class='backToAppText fr'>"
							+ "					操作时间："
							+ "					<i class='backToAppText' name='operTime'></i>"
							+ "				</span>"
							+ "			</p>"+"<div style='clear:both;'> <span style='margin-left:21px'>退回原因:</span><br/>"
							+ "			<div name='reason' style='margin-left:22px;margin-top:6px;' colspan='2' rowspan='1' vtype='textareafield' label='' rule='must'  width='442' height='140' ></div>"
							+ "			</div><div class='enterBackToBtns'>"
							+ "				<span id='enter-font' class='commonBt entBtn' style='cursor: pointer' name='backToAppBtn'>退回申请人</span>"
							+ "				<span id='enter-font' class='commonBt entBtn' style='cursor: pointer' name='backTopCancelBtn'>取消</span>"
							+ "			</div></div>";

					var win = jazz.widget({
						vtype : 'window',
						name : 'backToAppWindow',
						title : '退回申请人',
						titlealign : 'left',
						titledisplay : true,
						width : 500,
						height : 340,
						modal:true,
						visible : true,
						content : html,
					});

					// 打开窗口
					win.window('open');
					var time = torch.getTime();
					$("i[name='operTime']").html(time);
					$("i[name='operter']").html($("#entName").html());
					
					$("span[name='backTopCancelBtn']").on("click", function() {
						var win = $("div[name='backToAppWindow']");
						if (win != null) {
							win.window('close');
						}

					});

					$("span[name='backToAppBtn']").on("click", function() {
						
						var gid = jazz.util.getParameter('gid');
						var url = "../../../apply/setup/submit/backToApp.do"
						var reason = $("div[name='reason']").textareafield('getValue');// 取textarea的值。
						if(torch.isEmpty(reason)){
							return;
						}
						var params = {
							url : url,
							params : {
								gid : gid,
								reason : reason
							},
							async : false,
							callback : function(data, param, res) {
								var rows = res.getAttr("msg");
								jazz.info(rows, function() {
									torch.backHome();
								});
							}
						};
						$.DataAdapter.submit(params);

					});

				},

				// 返回首页
				backHome : function(type) {
					var url = "";
					if($.trim(type)=="ent"){//企业用户
						url = "business_verify";
					}else{//个人用户
						url="person_account";
					}
					
					window.location.href = "../../../page/apply/"+url+"/home.html";

				},
				getTime:function(){
					var date = new Date();
					var time = date.toLocaleDateString().split("/")
					if(time[1]<10){
						time[1]="0"+time[1];
					}
					if(time[2]<10){
						time[2]="0"+time[2];
					}
					return time[0]+"年"+time[1]+"月"+time[2]+"日";
				},
				
				getClientHeight:function() {
					//content-top的标题高度为35
					var contentHeight = $(window).height()-70-42-10;//减去上边10px的margin值
					var btnHeight= $(".contentBt").height();//计算当前按钮的高度
					var btnMTop = parseInt($(".contentBt").css("margin-top"));
					var btnMBottom = parseInt($(".contentBt").css("margin-bottom"));
					var calucatleheight = contentHeight - btnHeight - btnMTop -btnMBottom;
					$(".container").css("height",contentHeight);
					$('#PDF').css("height",calucatleheight-35);
					return contentHeight;
				},
				isEmpty:function(val) {
					val = $.trim(val);
					if (val == null)
						return true;
					if (val == undefined || val == 'undefined')
						return true;
					if (val == "")
						return true;
					if (val.length == 0)
						return true;
					if (!/[^(^\s*)|(\s*$)]/.test(val))
						return true;
					return false;
				}
			};
			torch._init();
			
			
			return torch;

		});

