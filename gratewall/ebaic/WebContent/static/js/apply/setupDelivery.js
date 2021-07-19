define(['require','jquery','common','util'], function(require, $,common,util){
	var delivery = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					// 根据模板处理页面样式
					//common.computeHeight();
					
					//获取名称信息
					$_this.getDomForUse();
					//获取用户移动电话
					$_this.getUserMobile();
					//绑定页面方法
					$_this.bindingClick();
					var gid = jazz.util.getParameter('gid');
					$("#receiverName").comboxfield('option','dataurl','../../../dmj/queryPostReceiver.do?gid='+gid);
					//$("#receiverName").comboxfield('reload');
				});
			});
		},
		edit_fromNames : [ 'applySetupPostReceiverSave_Form'],
		//上一页跳转
		backStep:function(){
			util.closeWindow('tipPage');
		},
		/**
		 * 绑定页面方法
		 */
		bindingClick:function(){
			/**
			 * 绑定提交方法 
			 */
			$("#save_button").on('click').on('click',delivery.postReceiverSave);
			
			//绑定发送验证码按钮
			$("#mobilebtn").on('click',delivery.sendmobileCode);	
			$('#close_button').on('click',delivery.closeDom);
			
		},
		/**
		 * 邮寄信息提交
		 */
		postReceiverSave:function(){
			var gid = jazz.util.getParameter('gid');
			jazz.confirm("请确认邮寄信息，提交后不可更改。",function(){
				var params={		 
					// url:"../../../../torch/service.do?fid=applySetupPostReceiver&wid=applySetupPostReceiverSave&gid="+gid,
					url:"../../../torch/service.do?fid=applySetupPostReceiver&wid=applySetupPostReceiverSave&gid="+gid,
					 components: delivery.edit_fromNames,
					 callback: function(jsonData,param,res){
						 $.ajax({
							//url:"../../../../apply/setup/submit/cpSubmit.do",
							 url:"../../../apply/setup/submit/cpSubmit.do",
							data:{gid:gid},
							type:"post",
							success:function(data){
								jazz.info("提交成功，您将在两个工作日内收到反馈结果，请留意系统信息与短信提醒。",function(){
									// parent.location.href="../../../../page/apply/person_account/home.html";
									 parent.location.href="../../../page/apply/person_account/home.html";
									 util.closeWindow('tipPage');
									 
								 });
							},
							error : function(responseObj) {
							    if(responseObj["responseText"]){
							    	var err = jazz.stringToJson(responseObj["responseText"]);
							    	if(err['exceptionMes']){
							    		jazz.error('<font color="blue" >错误信息</font> : ' + err['exceptionMes']);				    					    		
							    	}else{
							    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
							    	}
							    }else{
							    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
							    }
							    
							    return false;
							}
						 });
						 		
					 }
				};
				$.DataAdapter.submit(params);
			},function(){
				
			});
			
    		
		},
		/**
		 * 获取常用地址
		 */
		getDomForUse:function(){
			
			var gid = jazz.util.getParameter('gid');
			var params={		 
					 //url:"../../../../torch/service.do?fid=applySetupPostReceiver&wid=applySetupPostReceiverDomList&gid="+gid,
					url:"../../../torch/service.do?fid=applySetupPostReceiver&wid=applySetupPostReceiverDomList&gid="+gid,
					 callback: function(jsonData,param,res){
						 if(jsonData){
							 var rows = jsonData.data.rows;
							 var html ="";
							 $.each(rows,function(i,v){
								html+=('<tr><td><a href="#" prov="'+v.receiverProv
										//+'" recname="'+v.receiverName
										//+'" mobile="'+v.receiverMobile
										+'" >'+v.receiverDom+'</a></td></tr>')
							 });
							 $('#domList').append(html);
							 $('#domList a').live('click',delivery.setForm);
							 if(rows && rows.length>0){				 
								 //有常用地址，添加按钮
								 $('#save_button').before('<div id="dom_button" class="commonBt" >选择常用地址</div>');
								 //为按钮绑定事件
								 $('#dom_button').live('click',function(){
										$('#domDiv').show();
									});
							 }
							 
						 }	
					 }
			};
			$.DataAdapter.submit(params);
		},
		getUserMobile:function(){
			$.ajax({
				url:"../../../security/auth/info/getUserInfo.do",
				type:"post",
				success:function(data){
					if(data){
						data = $.parseJSON(data);
						var mobile = data.data[0].data.mobile;
						$('#mobile').textfield('setValue',mobile);
					}
					
				},
				error:function(data){
					jazz.error(data);
				}
			});
		},
		setForm:function(e){
			var obj = e.srcElement?e.srcElement:e.target;
			var prov = $(obj).attr('prov');
			var dom = $(obj).text();
			//var name = $(obj).attr('recname');
			//var mobile = $(obj).attr('mobile');
			$('div[name="receiverDom"]').textfield('setValue',dom);
			$('div[name="receiverProv"]').comboxfield('setValue',prov);
			//$('div[name="receiverMobile"]').textfield('setValue',mobile);
			//$('div[name="receiverName"]').textfield('setValue',name);
			$('#domDiv').hide();
		},
		sendmobileCode : function (){
			
    		var params={		 
    				 url:'../../../common/sms/vercode/sendToUser.do',
    				 callback: function(jsonData,param,res){
    					 jazz.info("发送成功");
    					 delivery.setTimeButton();
    				 }
    			};
    		$.DataAdapter.submit(params);
	    		
    	},
		setTimeButton : function(){
    		var i=60;
    		$("#mobilebtn").attr("disabled","disabled");
    		$("#mobilebtn").val("重新发送("+i+")");
    		$('#mobilebtn').css("cursor","default");
    		$('#mobilebtn').css("color","#BBB");

    		var id = setInterval(function(){
    			i--;
    			$("#mobilebtn").val("重新发送("+i+")");
    			if(i==0){
    				clearInterval(id);
    				$('#mobilebtn').removeAttr("disabled");
    				$("#mobilebtn").val("获取验证码");
    				$('#mobilebtn').css("cursor","pointer");
    				$('#mobilebtn').css("color","#666;");
    				$('#mobilebtn').css("background","#F7F7F7;");
    			}
    		},1000);
    	},
    	closeDom:function(){
    		$('#domDiv').hide();
    	}
		
		
		
	};
	delivery._init();
	return delivery;
});


