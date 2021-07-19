define(['require','jquery', 'common','util','../widget/Address'], function(require, $, common,util){
	var license = {};
	license = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
			  domReady(function () {
			    	//绑定页面方法
			    	$_this.bindingClik();
			    	//查看是否已经选择取照方式
			    	$_this.queryLinceseGetType();
			  });
			});
    	},
    	bindingClik:function(){
    		/**
    		 * 改变取照方式
    		 */
    		$('#licenseWay').radiofield('option','itemselect',license.onLicenseWayChange);
    		
    		/**
			 * 绑定提交寄递信息方法 
			 */
			$("#submit_ems_button").on('click').on('click',license.submitEms);
			/**
			 * 绑定确认自提方法 
			 */
			$("#sure_selfget_button").on('click').on('click',license.sureSelfGet);
			/**
			 * 绑定返回方法
			 */
			$('#back_button').on('click',license.goBack);
			/**
			 * 绑定发送验证码按钮
			 */
			$("#mobilebtn").on('click',license.sendmobileCode);	
    	},
    	/**
    	 * 查询取照方式
    	 */
    	queryLinceseGetType:function(){
    		var gid = jazz.util.getParameter('gid');
			var params={		 
				 url:"../../../torch/service.do?fid=queryReqAllColumns&wid=06c1fbabdb294d529b394932ef256c4c&gid="+gid,
				 callback: function(jsonData,param,res){
					 var licenseGetType = jsonData.data.licenseGetType||"";
					 if(licenseGetType==""){//未选择过取照方式
						 $("#deliveryInfo").hide();
						 $("#licenseInfo").hide();
						 $("#applySetupPostReceiverSave_Form").hide();
						 $("#sure_selfget_button").hide();
						 $("#submit_ems_button").hide();
					 }
					 
					 if(licenseGetType=="1"){//EMS
						 $(".license-way-label").hide();
						 $("#licenseWay").hide();
						 $("#licenseInfo").hide();
						 $("#sure_selfget_button").hide();
						 $("#submit_ems_button").hide();
						 $("#applySetupPostReceiverSave_Form").hide();
						 $("#deliveryInfo").show();
						 license.queryPostReceiverInfo();
					 }
					 
					 if(licenseGetType=="2"){//自取
						 $(".license-way-label").hide();
						 $("#licenseWay").hide();
						 $("#deliveryInfo").hide();
						 $("#applySetupPostReceiverSave_Form").hide();
						 $("#sure_selfget_button").hide();
						 $("#submit_ems_button").hide();
						 $("#licenseInfo").show();
						 $("#printLicense").css('display','block');
						 $(".licenseInfo-zero").show();
						 license.querySelfGetInfo();
					 }
					 
				 }
			};
			$.DataAdapter.submit(params);
    	},
    	/**
    	 * 取照方式改变
    	 */
    	onLicenseWayChange:function (enent,ui){
			var oVal=ui.value;
    		var gid = jazz.util.getParameter('gid');
    		if(oVal == "1"){//寄递
				$("#licenseInfo").hide();
				$("#applySetupPostReceiverSave_Form").show();
				$("#submit_ems_button").show();
				$("#sure_selfget_button").hide();
				var params={		 
         				 url:"../../../torch/service.do?fid=systemOrgConfigInfo&wid=9ae132441d464376930a30dd3f3450db&gid="+gid,
         				 type:'post',
         				 callback: function(jsonData,param,res){
         					var regOrgData = jsonData.data;
         					$("div[name='postRegNo']").textfield("setValue",regOrgData.orgName);
	       					$("div[name='postAddress']").textfield("setValue",regOrgData.orgAdd);
	       					$("div[name='postMobile']").textfield("setValue",regOrgData.lkmanTel);
         				 }
  				};
  				$.DataAdapter.submit(params);
				//获取收件人
				var gid = jazz.util.getParameter('gid');
				$("#receiverName").comboxfield('option','dataurl','../../../dmj/queryPostReceiver.do?gid='+gid);
				//地址
				$("#Prov-City-Other").Address({provCtrlId:'prov',cityCtrlId:'city'},'',{proValue:'110000',cityValue:''});
				common.computeHeight();
			}
			if(oVal == "2"){//自取
				$("#licenseInfo").show();
				$("#applySetupPostReceiverSave_Form").hide();
				$("#sure_selfget_button").show();
				$("#submit_ems_button").hide();
				license.querySelfGetInfo();
			}
    	},
    	/**
		 * 查询寄递信息
		 */
		queryPostReceiverInfo:function(){
			var gid = jazz.util.getParameter('gid');
			var params={		 
				 url:"../../../delivery/order/queryDetailStatus.do?gid="+gid,
				 callback: function(jsonData,param,res){
					 var array = jsonData.data.rows;
					 if(array.length>0){//有订单信息
						 $("#deliveryInfo").show();
						 $("#deliveryInfo-third").text("订单编号：EMS "+array[0].ddhm);
					 }
				 }
			};
			$.DataAdapter.submit(params);
		},
    	/**
    	 * 查询自取信息
    	 */
    	querySelfGetInfo:function(){
    		var gid = jazz.util.getParameter('gid');
    		var params={		 
      				 url:"../../../torch/service.do?fid=systemOrgConfigInfo&wid=9ae132441d464376930a30dd3f3450db&gid="+gid,
      				 type:'post',
      				 callback: function(jsonData,param,res){
      					var regOrgData = jsonData.data;
      					$("div[name='licenseInfo-first']").text("您的营业执照发放机关为:"+regOrgData.orgName);
      					var now = new Date();
      					var year = now.getFullYear();
      					var month = now.getMonth()+1;
      					var day = now. getDate();
      					var msg = "请您于"+year+"年"+month+"月"+day+"日起3个工作日后前来领取您的营业执照。";
      					$("div[name='licenseInfo-third']").text(msg);
	   					$("div[name='licenseInfo-four']").text("详细地址："+regOrgData.orgAdd);
	   					$("div[name='licenseInfo-five']").text("联系电话："+regOrgData.lkmanTel);
	   					//$("#printLicense").css('display','block');
	   					$("#printLicense").off('click').on('click',function(){
	   						
	   						window.location.href = "../../../page/apply/req/printLisence.html?gid="+gid;
	   					});
      				 }
				};
				$.DataAdapter.submit(params);
    	},
    	/**
		 * 邮寄信息提交
		 */
		submitEms:function(){
			var gid = jazz.util.getParameter('gid');
			jazz.confirm("您选择接收营业执照的方式【寄递】，请仔细确认邮寄信息，提交后信息不可修改。",function(){
				var licenseWay = $("#licenseWay").radiofield("getValue");
				var params={		 
					 url:"../../../torch/service.do?fid=applySetupPostReceiver&wid=applySetupPostReceiverSave&gid="+gid+"&licenseWay="+licenseWay,
					 components:[ 'applySetupPostReceiverSave_Form'],
					 callback: function(jsonData,param,res){
						 jazz.info("提交成功",function(){
							 parent.location.href="../../../page/apply/person_account/home.html";
						 });
					 }
				};
				$.DataAdapter.submit(params);
			},function(){
				
			});
		},
		/**
		 * 确定是自提方式
		 */
		sureSelfGet:function(){
			var gid = jazz.util.getParameter('gid');
			var licenseWay = $("#licenseWay").radiofield("getValue");
			jazz.confirm("您选择接收营业执照的方式【自取】，提交后信息不可修改。",function(){
				var params={		 
					 url:"../../../req/sureSelfGet.do",
					 type:"post",
					 params:{
						 gid:gid,
						 licenseWay:licenseWay
					 },
					 callback: function(jsonData,param,res){
						 jazz.info("提交成功",function(){
							// $("#printLicense").css('display','block');
							 parent.location.href="../../../page/apply/req/getLicense.html?gid="+gid;
						 });
					 }
				};
				$.DataAdapter.submit(params);
			},function(){
				
			});
		},
		/**
		 * 返回列表
		 */
    	goBack:function(){
    		window.location.href = "../../../page/apply/person_account/home.html";
    	},
    	/**
    	 * 发送验证码
    	 */
		sendmobileCode : function (){
			var gid = jazz.util.getParameter('gid');
    		var params={		 
    				 url:'../../../common/sms/vercode/sendToUser.do',
    				 type:"post",
    				 params:{
    					 gid:gid
    				 },
    				 callback: function(jsonData,param,res){
    					 jazz.info("发送成功");
    					 license.setTimeButton();
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
    	
    };
    license._init();
    return license;
});