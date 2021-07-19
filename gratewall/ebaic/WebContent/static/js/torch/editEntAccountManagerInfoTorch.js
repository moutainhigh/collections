define(['require', 'jquery', 'entCommon','util'], function(require, $, entCommon,util){
var torch = {
	/**
	 * 拼接参数
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
	query_primaryValues : function(){
	    return "&managerId=" + jazz.util.getParameter('managerId')
	},
	edit_fromNames : [ 'editEntAccountManagerInfo_Form'],
	/**
	 * 回显
	 */
	editEntAccountManagerInfoQuery : function(){
		var updateKey= "&wid=editEntAccountManagerInfo";
		$.ajax({
				url:'../../../torch/service.do?fid=editEntAccountManagerInfo'+updateKey+torch.query_primaryValues(),
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var jsonData = data.data;
					if($.isArray(jsonData)){
						 for(var i = 0, len = jsonData.length; i<len; i++){
							 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
							 //初始化操作权限
						    	var param ={
									url: '../../../apply/entManagerAccount/queryOpr.do',
									params:{managerId:jsonData[i].data.managerId},
									async:true,
									callback:function(data,param,res){
										var operation = res.getAttr("operation");
										var oprs = operation.split(",");
										var opera = $(".wraplist input");
										for(var j=0;j<opera.length;j++){
											for(var k=0;k<oprs.length;k++){
												if(opera[j].value==oprs[k]){
													opera[j].checked=true;
												}
											}
										}
									}
								};
							$.DataAdapter.submit(param,this);
						 }
					 }
					
				}
			});
	},
	/**
	 * 保存
	 */
	editEntAccountManagerInfoSave : function(){
		var params={		 
			 url:"../../../torch/service.do?fid=editEntAccountManagerInfo"+torch.edit_primaryValues(),
			 components: torch.edit_fromNames,
			 callback: function(jsonData,param,res){
				 torch.editEntAccountManagerInfoReset();//清空所有的用户输入记录
				 util.closeWindow("editManager",true);
				 jazz.info("保存成功");
			 }
		};
		$.DataAdapter.submit(params);
	},
	/**
	 * 修改成功之后，清空所有的输入内容
	 */
    editEntAccountManagerInfoReset : function(){
		for( x in torch.edit_fromNames){
			$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
		} 
		var opera = $(".wraplist input");
		for(var i=0;i<opera.length;i++){
			opera[i].checked=false;
		}
	},
	/**
	 * 发送手机验证码。
	 */
	sendVercode:function(){
		var mobile = $("div[name='mobile']").textfield("getValue");
		var managerId = $("div[name='managerId']").hiddenfield("getValue");
		if(!mobile){
			jazz.error("移动电话不能为空。");
			return;
		}
		//1.移动电话校验，校验是否已被企业用户或管理员用户使用
		var param ={
				url: '../../../apply/entManagerAccount/checkMobileIsUsed.do',
				params:{mobile:mobile,managerId:managerId},
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
	/**
	 * 上一页跳转
	 */
	backStep:function(){
		util.closeWindow('editManager',true);
//		window.close();
		//window.location.href="../../../page/apply/ent_account/queryEntAccountManagerList.html";
	},
	/**
	 * 初始化
	 */
	 _init : function(){
		 require(['domReady'], function (domReady) {
		    domReady(function () {
		    	//同意企业账户管理员可代为操作企业账户的权利义务时，保存按钮可用；不同意时，保存按钮禁用。
		    	$("#agree").click(function(){
		    		if($("#agree")[0].checked){
		    			//$("div[name='editEntAccountManagerInfoToolbar']").css("display","block");
		    			$("div[name='editEntAccountManagerInfo_save_button']").button("option","disabled",false);
		    		}else{
		    			$("div[name='editEntAccountManagerInfo_save_button']").button("option","disabled",true);
		    		}
		    	});
		    	/*$("div[name='editEntAccountManagerInfo_save_button']").css("display","block");*/
		    	 //发送验证码
				  $("#sendCheckNum").click(function(){
					  torch.sendVercode();
				  });
				torch.editEntAccountManagerInfoQuery();
				
				//回车提交查询
				$(document).keypress(function(e) {
					if (e.which == 13) {
						//回车查询
						torch.editEntAccountManagerInfoSave();
					}
				});
				$("div[name='editEntAccountManagerInfo_save_button']").off('click').on('click',torch.editEntAccountManagerInfoSave);
				$("div[name='editEntAccountManagerInfo_reset_button']").off('click').on('click',torch.editEntAccountManagerInfoReset);
				$("div[name='editEntAccountManagerInfo_back_button']").off('click').on('click',torch.backStep);
				$("article").css("margin-top","-20px");
			 });
		});
	 }
};
torch._init();
//torch._getCheckBox();
return torch;

});
