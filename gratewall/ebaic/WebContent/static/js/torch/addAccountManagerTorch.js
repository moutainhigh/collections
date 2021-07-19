define(['require', 'jquery', 'entCommon','util'], function(require, $, entCommon,util){
	var torch = {
		/**
		 * 拼参
		 * @returns {String}
		 */
		edit_primaryValues : function(){
			var opera = $(".wraplist input:checked");
			var operation = "";
			if(opera.length>0){
				for(var i=0;i<opera.length-1;i++){
					operation = operation+opera[i].value+",";
				}
				operation = operation + opera[opera.length-1].value;
			}else{
				jazz.warn("请选择管理员操作权限！");
			}
		    return "&operation="+operation+"&managerId=" + jazz.util.getParameter('managerId')
		},
		edit_fromNames : [ 'addCpAccountManager_Form'],
		/**
		 * 保存
		 */
		add_cp_account_managerSave : function(){
			//保存
	       var params={		 
				 url:"../../../../torch/service.do?fid=add_cp_account_manager"+torch.edit_primaryValues(),
				 components: torch.edit_fromNames,
				 callback: function(jsonData,param,res){
					 if(jsonData.data.managerId){
						 $('#insertId').val(jsonData.data.manager_id);
						 torch.add_cp_account_managerReset();//重置用户输入信息
//						 util.closeWindow("addManager",true);
						 jazz.confirm("保存成功", function() {
							 window.location.href="queryEntAccountManagerList.html";
						 });
					 }else{
						 jazz.info("保存失败");	
					 }
				 }
			};
	       
			$.DataAdapter.submit(params);
		},
		/**
		 * 重置
		 */
		add_cp_account_managerReset : function(){
			for( x in torch.edit_fromNames){
				$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
			} 
		},
		/**
		 * 发送手机验证码。
		 */
		sendVercode:function(){
			var mobile = $("div[name='mobile']").textfield("getValue");
			if(!mobile){
				jazz.error("移动电话不能为空。");
				return;
			}
			//1.移动电话校验，校验是否已被企业用户或管理员用户使用
			var param ={
					url: '../../../apply/entManagerAccount/checkMobileIsUsed.do',
					params:{mobile:mobile,managerId:"add"},
					async:true,
					callback:function(data,param,res){
						var isUsed = res.getAttr("isUsed");
						if(isUsed){
							jazz.error("该移动电话已被注册使用。");
							return;
						}else{
							//2.给手机发送验证码
							var param ={
								url: '../../../common/sms/vercode/send.do',
								params:{mobile:mobile},
								async:true,
								callback:function(data,param,res){
									var result = res.getAttr("result");
									if(result=="success"){
										var i=60;
										$('#sendCheckNum').attr("disabled","disabled");
										$('.sendCheckNum').css("color","#333");
										$('#sendCheckNum').text("重新发送("+i+")");
										jazz.info("短信发送成功。");
										var id=setInterval(function(){
											i--;
											$('#sendCheckNum').text("重新发送("+i+")");
											if(i==0){
												clearInterval(id);
												$('#sendCheckNum').removeAttr("disabled");
												$('#sendCheckNum').text("发送验证码");
												$('.sendCheckNum').css("color","#148AC0");
											}
										},1000);
										/*jazz.info("获取短信验证码成功!");
										$('#sendCheckNum').text("已发送");
										$('#sendCheckNum').attr("disabled",true);
										setTimeout(function(){
														$('#sendCheckNum').removeAttr("disabled");
														$('#sendCheckNum').text("发送验证码");
												   },
												   entCommon.getSmsVercodeValidateTimeInSeconds()*1000
										);*/
									}else{
										jazz.error("获取短信验证码失败!");
										return;
									}
								}
							};
							$.DataAdapter.submit(param,this);
						}
					}
			};
			$.DataAdapter.submit(param,this);
		},
		back_button:function(){
			window.location.href="queryEntAccountManagerList.html";
		},
		agree:function(){
			if($("#agree").is(':checked')){
    			$("div[name='add_cp_account_manager_save_button']").button("option","disabled",false);
    			$("div[name='add_cp_account_manager_save_button']").off('click').on('click',torch.add_cp_account_managerSave);
			}else{
    			$("div[name='add_cp_account_manager_save_button']").button("option","disabled",true);
    		}
		},
		checkLepAccount : function(){
			$.ajax({
				url : "../../../apply/entAccHome/checkLepAccount.do",
				async:false,
				type:'post',
				dataType : 'json',
				success : function(data){
					if(data == '1'){//为法人账号
						$(".legal").show();
					}else{
						$(".manager").show()
					}
				}
			});
		},
		/**
		 * 初始化
		 */
		 _init : function(){
			 require(['domReady'], function (domReady) {
			    domReady(function () {
			    	//同意企业账户管理员可代为操作企业账户的权利义务时，保存按钮可用；不同意时，保存按钮禁用。
			    	$("#agree").on('click',function(){
			    		torch.agree();
			    	});
			    	
			    	 //发送验证码
					  $("#sendCheckNum").click(function(){
						  torch.sendVercode();
					  });
			    	//回车提交表单相对应的信息
			    	$(document).keypress(function(e) {
						if (e.which == 13) {
							//回车保存
							torch.add_cp_account_managerSave();
						}
					});
			    	if($("div[name='add_cp_account_manager_save_button']").attr('disabled')==false){
			    		$("div[name='add_cp_account_manager_save_button']").off('click').on('click',torch.add_cp_account_managerSave);
			    	}
					$("div[name='add_cp_account_manager_back_button']").on("click",torch.back_button);
					//$("div[name='add_cp_account_manager_reset_button']").off('click').on('click',torch.add_cp_account_managerReset);
					
					//判断是否是法人账号登陆 从而决定在创建管理员时是否出现企业管理员权限选择选项
					torch.checkLepAccount();
				 });
			});
				$("div[name='name']").css("height","auto");
		 }
	
	};
	torch._init();
	return torch;
});
