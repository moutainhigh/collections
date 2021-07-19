define([ 'require', 'jquery', 'approvecommon', 'util' ], function(require, $,
		approvecommon, util) {

	var g = {
		/**
		 * 模块初始化。
		 */
		_init : function() {
			require([ 'domReady' ], function(domReady) {
				domReady(function() {
					// 绑定事件
					g.hideBingding();
					$("[name='checkCer']").on('click', g.checkIdentityCard);
					$("[name='resetCer']").on('click', g.resetIdentityCard);
					$("[name='check_mobile']").on('click', g.checkVerCode);
					$("[name='sendCheckNum']").on('click', g.sendVerCode);
					$("#identity_type").on('click', g.showIdentitymsg);
					$("div[name='identity_type']").css("margin-left","-13px");
					$("[name='identity_success']").on('click',g.identity_success);
					$("[name='identity_fail']").on('click', g.identity_fail);
					$("#checkLoginName").on('click', g.checkLoginName);
					$("[name='binding']").on('click', g.bingding);
					$("[name='reset_password']").on('click', g.resetPassword);
					$("[name='creatAndBinding']").on('click',g.creatAndbingding);
					$("#cerType").comboxfield('option','itemselect',g.onCerTypeChange);
					$("[name='saveCer']").on('click',g.saveCer);
					$("#identity_type").radiofield('option', 'itemselect',g.showIdentitymsg);
					$("div[name='localRegformpanel']").css("height","160px");
				});
			});

		},
		/**
		 * 加载页面时隐藏绑定账号信息
		 */
		hideBingding : function() {
			$("#saveCer").hide();
			$("#cerType").comboxfield("setValue",'1');
			$("#cerPic").hide();
			$("#identity_msg").hide();
			$("#infos").hide();
			$("#hasBinding").hide();
			$("#noneBinding").hide();
			$("#boxborder").hide();
			$("#submit").hide();
		},
		
		onCerTypeChange:function(){
			var cerType = $("#cerType").comboxfield("getValue");
			if(cerType!='1'){
				jazz.info("人员只能做法定代表人，不能做申请人，申请人必须为身份证！");
				$("#checkCer").hide();
				$("#saveCer").show();
			}else{
				$("#checkCer").show();
				$("#saveCer").hide();
			}
		},

		/**
		 * 查验证件
		 */
		checkIdentityCard : function() {
			$("#identity_type").radiofield('setValue','');
			var name = $('#name').textfield("getValue");
			var cerNo = $("#cerNo").textfield("getValue");
			if (!cerNo) {
				jazz.warn("请输入身份证号码！");
				return false;
			}
			if (!name) {
				jazz.warn("请输入姓名！");
				return false;
			}
			
			$.ajax({
				url:'../../../approve/identity/checkIsIdentity.do',
				data:{
					name:name,
					cerNo:cerNo,
					cerType:'1'
				},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data == false){
						jazz.info("已经通过实名认证!");
					}else{
						var picUrl = '../../../apply/rodimus/idPic/show.do?name='
							+ util.encodeURI(name) + '&cerNo=' + cerNo;
					//$("#cerPic").show();
					//$("#cerPic").attr("src",picUrl);
					$.ajax({
						url : "../../../approve/identity/checkIdentityCard.do",
						data : {
							name : name,
							cerNo : cerNo
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if (data != null && data.data != null) {
								var temp = "" +data.data[0].data +"";//转成string
								if(temp.trim() == "undefined"){
									jazz.info("身份信息未通过校验！");
								}else{
									$("#cerPic").show();
									$("#cerPic").attr("src",picUrl);
									var cerNo_hide = data.data[0].data.cerNo;
									$('#cerNo_hide').hiddenfield("setValue", cerNo_hide);
									$("#name").textfield("option", "readonly", true);
									$("#cerNo").textfield("option", "readonly", true);
									$("#cerType").comboxfield("option", "readonly", true);
									$("div[name='checkCer']").hide();
								}
								/*
								if (data != null && data.data[0].data.folk != null) {
									var cerNo_hide = data.data[0].data.cerNo;
									$('#cerNo_hide').hiddenfield("setValue", cerNo_hide);
									$("#name").textfield("option", "readonly", true);
									$("#cerNo").textfield("option", "readonly", true);
									$("#cerType").comboxfield("option", "readonly", true);
									$("div[name='checkCer']").hide();
									jazz.info("查验证件通过！");
								} else {
									jazz.info("身份信息未通过校验！");
								}*/
							} else {
								jazz.info("身份信息未通过校验！");
							}
						}
					});
					}
				}
			});

		},
		
		/**
		 * 保存非身份证的证件信息
		 */
		saveCer:function(){
			var name = $('#name').textfield("getValue");
			var cerNo = $("#cerNo").textfield("getValue");
			var cerType = $("#cerType").comboxfield("getValue");
			if (!cerNo) {
				jazz.warn("请输入证件号码！");
				return false;
			}
			if(!cerType){
				jazz.warn("请选择证件类型！");
				return false;
			}
			if (!name) {
				jazz.warn("请输入姓名！");
				return false;
			}
			
			
			$.ajax({
				url:'../../../approve/identity/checkIsIdentity.do',
				data:{
					name:name,
					cerNo:cerNo,
					cerType:cerType
				},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data == false){
						jazz.info("已经通过实名认证");
					}else{
						$.ajax({
							url:'../../../approve/identity/saveCer.do',
							data:{
								name:name,
								cerNo:cerNo,
								cerType:cerType
							},
							type:'post',
							dataType:'json',
							success:function(data){
								if (data != null && data.data != null) {
									if(data.data[0].data=='success'){
										$("#name").textfield("option", "readonly", true);
										$("#cerNo").textfield("option", "readonly", true);
										$("#cerType").comboxfield("option", "readonly", true);
										jazz.info("保存成功！");
									}
								}else{
									jazz.warn("保存失败！");
								}
								
							},
							error:function(data){
								jazz.warn("保存失败！");
							}
						});
					}
				}
			});
			
		},
		
		/**
		 * 判断是否已经经过认证
		 */
		checkIsIdentity:function(name,cerNo,cerType){
			$.ajax({
				url:'../../../approve/identity/checkIsIdentity.do',
				data:{
					name:name,
					cerNo:cerNo,
					cerType:cerTpe
				},
				type:'post',
				dataType:'json',
				succes:function(data){
					if(data == false){
						jazz.info("已经通过实名认证");
					}else{
						
					}
				}
			});
		},
		

		/**
		 * 重置验证身份证输入框
		 */
		resetIdentityCard : function() {
			$("#name").textfield('option', 'readonly', false);
			$("#cerNo").textfield('option', 'readonly', false);
			$("#name").textfield('reset');
			$("#cerNo").textfield('reset');
			$("#cerPic").attr("src", "");
			$("div[name='checkCer']").show();
			$("#mobile").textfield('reset');
			$("#verCode").textfield('reset');
			$("#approve_msg_Text").textareafield('reset');
			//$("#cur_name").textfield('reset');
			$("#cur_name").html();
			$("#newPwd").textfield('reset');

			$("#login_name").textfield('reset');
			$("#email").textfield('reset');
			$("#address").textfield('reset');
			//$("#sex").radiofield('reset');

			g.hideBingding();

		},

		/**
		 * 校验手机
		 */
		checkVerCode : function() {
			
			$("#approve_msg").hide();
			$("#identity_type").radiofield('setValue','');
			$("#boxborder").hide();
			$("#submit").hide();
			$("#noneBinding").hide();
			var cerNo = "";
			var mobile = $("#mobile").textfield("getValue");
			var verCode = $("#verCode").textfield("getValue");
			var cerType = $("#cerType").comboxfield("getValue");
			if(cerType=='1'){
				cerNo = $("#cerNo_hide").hiddenfield("getValue");
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			if (!cerNo && cerType=='1') {
				jazz.warn("请先进行身份认证！");
			}
			if (!mobile) {
				jazz.warn("请输入移动电话码！");
			}
			if (!verCode) {
				jazz.warn("请输入手机验证码！");
			}
			$.ajax({
				url : '../../../approve/identity/saveMobile.do',
				data : {
					cerNo : cerNo,
					cerType:cerType,
					mobile : mobile,
					verCode : verCode
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					var result = data.data[0].data;
					if (result == 'success') {
						$("#tips").html("移动电话校验成功!");
						$("#tips").css("color", "#4BB877");
						$("#checkFlag").hiddenfield('setValue','success');
					} else {
						$("#tips").html("移动电话校验失败!");
						$("#tips").css("color", "red");
						$("#checkFlag").hiddenfield('setValue','');
					}
				},
				error : function() {
					jazz.warn("移动电话检验失败!");
					$("#checkFlag").hiddenfield('setValue','');
				}

			});
		},

		/**
		 * 发送验证码
		 */
		sendVerCode : function() {
			var mobile = $("#mobile").textfield("getValue");
			var cerType = $("cerType").comboxfield('getValue');
			var cerNo = $("#cerNo_hide").hiddenfield("getValue");
			if (!cerNo && cerType == '1') {
				jazz.warn("请先进行身份认证！");
				return false;
			}
			if (!mobile) {
				jazz.warn("请输入移动电话码！");
				return false;
			}
			
			
			$.ajax({
				url : '../../../common/sms/vercode/send.do?mobile='
						+ mobile,
				type : "post",
				success : function() {
					// 启动按钮倒计时
					g.setTimeButton();
					jazz.info("短信验证码已发出");
				}
			});
			
		},

		/* 发送验 证码时间倒计时 */
		setTimeButton : function() {
			var i = 60;
			$("#sendCheckNum").attr("disabled", "disabled");
			$("#sendCheckNum").html("重新发送(" + i + ")");
			$('#sendCheckNum').css({
				"cursor" : "not-allowed",
				"width" : "100",
				"color" : "#BBB",
				"background" : "#F7F7F7"
			});
			var id = setInterval(function() {
				i--;
				$("#sendCheckNum").html("重新发送(" + i + ")");
				if (i == 0) {
					clearInterval(id);
					$('#sendCheckNum').removeAttr("disabled");
					$("#sendCheckNum").html("获取验证码");
					$('#sendCheckNum').css({
						"cursor" : "pointer",
						"color" : "#666",
						"background" : "#2e9ee4"
					});
				}
			}, 1000);
		},

		/**
		 * 认证通过
		 */
		identity_success : function() {
			var cerNo = $('#cerNo_hide').hiddenfield('getValue');
			var cerType = $("#cerType").comboxfield('getValue');
			if(cerType=='1'){
				cerNo = $("#cerNo_hide").hiddenfield("getValue");
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			
			if (!cerNo && cerType == '1') {
				jazz.warn("请先进行身份和移动电话码认证！");
				return false;
			}
			var params = {
				url : '../../../approve/identity/approve.do',
				params : {
					cerNo : cerNo,
					cerType:cerType,
					flag : "1"
				},
				callback : function(data, r, res) {
					if (res.getAttr('result') == 'success') {
						jazz.info("保存成功！");
						window.location.href="../../../page/approve/sysidentify/identityList.html";
					} else {
						jazz.info("保存失败！");
					}
				}
			};
			$.DataAdapter.submit(params);

		},

		/**
		 * 认证不通过
		 */
		identity_fail : function() {
			var cerNo = $('#cerNo_hide').hiddenfield('getValue');
			var cerType = $("#cerType").comboxfield("getValue");
			if(cerType=='1'){
				cerNo = $("#cerNo_hide").hiddenfield("getValue");
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			var approveMsg = $("[name='approve_msg_Text']").textareafield(
					'getValue');
			if (!cerNo && cerType == '1') {
				jazz.warn("请先进行身份和移动电话码认证！");
				return false;
			}
			if (!approveMsg) {
				jazz.warn("请输入审查意见！");
				return false;
			}
			var params = {
				url : '../../../approve/identity/approve.do',
				params : {
					cerNo : cerNo,
					cerType:cerType,
					approveMsg : approveMsg,
					flag : "2"
				},
				callback : function(data, r, res) {
					if (res.getAttr('result')) {
						jazz.info("保存成功！");
						window.location.href="../../../page/approve/sysidentify/identityList.html";
					} else {
						jazz.info("保存失败！");
					}
				}
			};
			$.DataAdapter.submit(params);

		},

		/**
		 * 校验账号名称是否重复
		 */
		checkLoginName : function() {
			var loginName = $("#login_name").textfield('getValue');
			if (!loginName) {
				jazz.warn("请输入账号名称！");
			}
			$.ajax({
				url : '../../../approve/identity/checkLoginName.do',
				data : {
					loginName : loginName
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data && data == '1') {
						jazz.warn("该账号名称已经存在，请重新输入！");
						return false;
					} else {
						jazz.info("该账号名称可以使用");
						$("#creatAndBinding").show();
					}
				}

			});
		},

		/**
		 * 账号绑定
		 */
		bingding : function() {
			var cerNo = "";
			var cerType = $("#cerType").comboxfield('getValue');
			if(cerType=='1'){
				cerNo = $('#cerNo_hide').hiddenfield('getValue');
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			var curName = $('#cur_name').val();
			var params = {
				url : '../../../approve/identity/bingding.do',
				params : {
					cerNo : cerNo,
					cerType:cerType,
					curName : curName
				},
				callback : function(data, r, res) {
					if (res.getAttr('result') == 'success') {
						jazz.info("账号绑定成功！");
					} else {
						jazz.warn("账号绑定失败！");
					}
				}
			};
			$.DataAdapter.submit(params);

		},

		/**
		 * 重置密码
		 */
		resetPassword : function() {
			var cerNo = "";
			var cerType = $("#cerType").comboxfield('getValue');
			if(cerType=='1'){
				cerNo = $('#cerNo_hide').hiddenfield('getValue');
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			var params = {
				url : '../../../approve/identity/resetPassword.do',
				params : {
					cerNo : cerNo,
					cerType:cerType
				},
				callback : function(data, r, res) {
					var newPwd = res.getAttr('newPassword');
					if (newPwd) {
						jazz.info("重置密码成功！新密码为:" + newPwd);
						/*
						 * $('#newPwd').textfield('setValue',newPwd);
						 * $("#newPwd").show();
						 * $('#newPwd').textfield('option','readonly',true);
						 */
					} else {
						jazz.info("重置密码失败！");
					}
				}
			};
			$.DataAdapter.submit(params);
		},

		/**
		 * 生成账号并绑定
		 */
		creatAndbingding : function() {

			var cerNo = "";
			var cerType = $("#cerType").comboxfield('getValue');
			var loginName = $('#login_name').textfield('getValue');
			var email = $('#email').textfield('getValue');
			var address = $('#address').textfield('getValue');
			//var sex = $('radiofield').textfield('getValue');
			
			if(cerType=='1'){
				cerNo = $('#cerNo_hide').hiddenfield('getValue');
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			
			if (!cerNo && cerType == '1') {
				jazz.warn("请先进行身份和移动电话码认证！");
				return false;
			}

			if (!loginName) {
				jazz.warn("请输入申请账号！");
				return false;
			}
			if (!email) {
				jazz.warn("请输入申请邮箱！");
				return false;
			}
			if (!address) {
				jazz.warn("请输入联系地址！");
				return false;
			}
			

			var params = {
				url : '../../../approve/identity/createAndBinding.do',
				components : [ 'localRegformpanel' ],
				params : {
					cerNo : cerNo,
					cerType:cerType
				},
				callback : function(data, r, res) {
					if (res.getAttr('result')) {
						jazz.info("账号创建绑定成功！");
						window.location.href="../../../page/approve/sysidentify/identityList.html";
					} else {
						jazz.info("账号创建绑定失败！");
					}

				}
			};
			$.DataAdapter.submit(params);

		},

		showIdentitymsg : function() {
			$("#identity_msg").hide();
			var cerNo = $('#cerNo_hide').hiddenfield('getValue');
			var cerType = $("#cerType").comboxfield("getValue");
			if(cerType=='1'){
				cerNo = $("#cerNo_hide").hiddenfield("getValue");
			}else{
				cerNo = $("#cerNo").textfield("getValue");
			}
			if (!cerNo && cerType=='1') {
				jazz.warn("请先进行证件审查!");
				return false;
			}
			
			/*var mobileValidate =  $("[name='mobile']").textfield("getValue");
			var verCode = $("[name='verCode']").textfield("getValue");*/
			///
			/*if (!mobileValidate&&verCode) {
				jazz.warn("请通过手机认证!");
				return false;
			}*/
			
			var tips = $("#tips").html();
			//if(tips==""||tips=="移动电话校验失败!"){
			var checkFlag = $("#checkFlag").hiddenfield('getValue');
			if(tips=="移动电话校验失败!"){
				jazz.warn("请通过移动电话审查!");
				return;
			}else{
				var type = $("#identity_type").radiofield('getValue');
				// 0没有通过认证，下方的账号绑定信息不显示
				if (type == '0') {
					$("#infos").hide(); // 隐藏账户绑定内容
					$("#identity_msg").show();// 显示审核意见
					$("#hasBinding").hide();
					$("#noneBinding").hide();
					$("#boxborder").hide();
					$("#submit").hide();
				}
				// 1是可以通过的认证，下面的账号信息绑定将显示出来
				if (type == '1'&&checkFlag=='success') {
					$("#infos").show();
					$("#boxborder").show();
					$("#submit").show();
					g.checkApplyUserExisit(cerNo,cerType);
				}
			}
		},

		/**
		 * 是否存在该证件号码的的用户信息
		 */
		checkApplyUserExisit : function(cerNo,cerType) {
			var params = {
				url : '../../../approve/identity/checkApplyUserExisit.do',
				params : {
					cerType:cerType,
					cerNo : cerNo
				},
				callback : function(data, r, res) {
					var result = res.getAttr('result');
					if (result!=undefined && result !=null) {
						var loginName = res.getAttr('result').loginName;
						$("#hasBinding").show();
						$("#noneBinding").hide();
						$("#cur_name").val(loginName);
					} else {
						$("#noneBinding").show();
						$("#hasBinding").hide();
					}

				}

			};
			$.DataAdapter.submit(params);
		}

	};
	g._init();
	return g;
});