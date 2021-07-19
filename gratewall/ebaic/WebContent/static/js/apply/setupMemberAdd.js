define(['require','jquery','jazz', '../torch/member_addTorch','../widget/Address','util','common'], function(require, $ , jazz, torch,address,util,common){
	/* 模块定义 */
	var mbr = {};
	mbr = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
				domReady(function () {
					//事件绑定
					$_this.bindingClick();
					//页面元素处理
					$_this.initUi();
					//若董事有兼任经理，初始化经理信息
//					$_this.initManagerData();
					//函数导出
					util.exports("birthdayDateVerify",$_this.birthdayDateVerify());
					
				});
    		});
    		
    	},
    	
    	/**
    	 * 事件绑定
    	 */
    	bindingClick:function (){
    		$("div[name='country']").comboxfield('option','change',mbr.onCountryChange);
    		$("div[name='natDate']").datefield("option","rule","must_customFunction;ebaic.birthdayDateVerify()");
    		$("div[name='cerType']").comboxfield("option","change",mbr.onCerTypeChange);
    		$("div[name='cerNo']").live("change",mbr.onCerNoChange);
    		$("div[name='name']").autocompletecomboxfield("option","change",mbr.onNameChange);
    	},
    	/**
    	 * 查询董事里是否有兼任经理的，若有经理回显该董事的信息
    	 */
    	initManagerData:function(){
    		var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
    		if(mbrFlag==2){//添加经理时
    			var gid = jazz.util.getParameter('gid')||'';
    			var params = {
    					url : '../../../apply/setup/member/isManager.do',
    					params:{
    						gid : gid ,
    					},
    					async:false,
    					callback : function(data, param, res) {
    						var data = res.getAttr("result")|| {};
    						if(data){
    							$("div[name='psnjobId']").hiddenfield("setValue", data.psnjobId || '');
    							$("div[name='supsType']").comboxfield("setValue", data.supsType || '');
    							$("div[name='posBrForm']").comboxfield("setValue", data.posBrForm || '');
    							$("div[name='offYears']").comboxfield("setValue", data.offYears || '');
    							
    							$("div[name='name']").autocompletecomboxfield("setValue",data.name || '');
    							if(data.cerType=="1"){//身份证
    								$("div[name='cerNo']").textfield("option",'rule',"must_idcard_length;0;40");
    							}else{
    								$("div[name='cerNo']").textfield("option",'rule',"must_length;0;40");
    							}
    							$("div[name='cerType']").comboxfield("setValue",data.cerType || '');
    							$("div[name='cerNo']").textfield("setValue",data.cerNo || '');
    							$("div[name='country']").comboxfield("setValue",data.country || '');
    							$("div[name='sex']").radiofield("setValue",data.sex || '');
    							$("div[name='liteDeg']").comboxfield("setValue",data.liteDeg || '');
    							
    							$("div[name='nation']").comboxfield("setValue",data.nation || '');//polStand
    							$("div[name='polStand']").comboxfield("setValue",data.polStand || '');
    							var date = data.natDate || '';
    							var d = date.split(" ");
    							$("div[name='natDate']").datefield("setValue",d[0] || '');
    							
    							$("div[name='houseAddProv']").comboxfield("setValue",data.houseAddProv || '');
    							$("div[name='houseAddCity']").comboxfield("setValue",data.houseAddCity || '');
    							$("div[name='houseAddOther']").textfield("setValue",data.houseAddOther || '');
    						}
    					} 	
    			};  	
    			$.DataAdapter.submit(params);
    		}
    	},
    	/**
    	 * 处理页面元素
    	 */
    	initUi:function(){
    		/**
    		 *   提示：	1、未处理的内容，执行董事设置方式未定，未写；
    		 *   	  	2、监事类型，没有在后台处理；
    		 *   		3、主要人员姓名没有写方法；
    		 *   		4、国籍、民族可搜索
    		 *   		5、保存时，只能保存一个
    		 *   		6、所有规则代码也必须写
    		 *   		7、删除
    		 *   		8、董事、经理兼任
    		 *   		9、保存法定代表人时，更新表be_wk_le_rep
    		 *   		10、重置改为关闭
    		 */
    		/**
    		 * 1.户籍登记地址
    		 */
    		$("#Prov-City-Other").Address({provCtrlId:'houseAddProv',cityCtrlId:'houseAddCity'});
    		/**
    		 * 2.如果是监事，显示supsType监事类型，否则隐藏；
    		 * 若是经理，产生方式必须为聘任
    		 */
    		var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
    		if(mbrFlag==3){
    			var isSupedValue = jazz.util.getParameter('isSuped')||'';
    			if(isSupedValue == "1"){//设立监事会
    				$("#supsType").show();
        			$("#supsType").comboxfield('option','rule','must');
    			}else{//不设立监事会
    				$("#supsType").hide();
        			$("#supsType").comboxfield('option','rule','');
    			}
    		}else{
    			$("#supsType").hide();
    			$("#supsType").comboxfield('option','rule','');
    		}
    		if(mbrFlag==1){//若是董事，显示是否兼任经理
    			$("#isManager").show();
    			$("#isManager").comboxfield('option','rule','must');
    		}else{
    			$("#isManager").hide();
    			$("#isManager").comboxfield('option','rule','');
    		}
    		if(mbrFlag==2){//若是经理，产生方式必须为聘任
    			$("#posBrForm").comboxfield('removeOption', '02');
    			$("#posBrForm").comboxfield('removeOption', '03');
    			$("#posBrForm").comboxfield('addOption', '聘任', '04');
    			$("#posBrForm").comboxfield('setValue', '04');
    		}
    		/**
    		 * 3.将已有股东、董事的姓名带出来，选择姓名后，已经填写过的信息带出来；
    		 * 1 董事： 可以从已有股东中选择，也可以自己添加
    		 * 2 经理： 可以从已有股东和董事中选择，也可以自己添加
    		 * 3 监事： 董事、高级管理人员（经理、财务）不得兼任监事。
    		 */
    		mbr.queryInvesterNames();
    	},
    	queryInvesterNames:function(){
    		var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
    		var gid = jazz.util.getParameter('gid')||'';
    		var url = '../../../dmj/queryInvesterNames.do?gid='+gid+'&mbrFlag='+mbrFlag;
    		$("div[name='name']").autocompletecomboxfield('option','dataurl',url);
    	},
    	onNameChange:function(event,ui){
    		var id = ui.newValue ;
			var params = {
    		        url : '../../../apply/setup/member/queryEntMemberOrInvester.do',
    		        params:{
    		        	id : id ,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	var data = res.getAttr("result");
    		        	if(data){
    		        		if(data.cerType=="1"){//身份证
    		        			$("div[name='cerNo']").textfield("option",'rule',"must_idcard_length;0;40");
    		        		}else{
    		        			$("div[name='cerNo']").textfield("option",'rule',"must_length;0;40");
    		        		}
    		        		$("div[name='cerType']").comboxfield("setValue",data.cerType || '');
    		        		$("div[name='cerNo']").textfield("setValue",data.cerNo || '');
    		        		$("div[name='country']").comboxfield("setValue",data.country || '');
    		        		$("div[name='sex']").radiofield("setValue",data.sex || '');
    		        		$("div[name='nation']").comboxfield("setValue",data.folk || '');
//    		        		$("div[name='houseAddProv']").comboxfield("setValue",data.prov || '');
//    		        		$("div[name='houseAddCity']").comboxfield("setValue",data.city || '');
    		        		$("div[name='houseAddOther']").textfield("setValue",data.other || '');
    		        		mbr.onCerNoChange();
    		        		
    		        		/**
    		        		 * 地址三段式回显
    		        		 */
    		        		$("#Prov-City-Other").Address({provCtrlId:'houseAddProv',cityCtrlId:'houseAddCity'},'',{proValue:data.prov,cityValue:data.city});
    		        	}
    		        } 	
    		    };  	
    		$.DataAdapter.submit(params);
    	},
    	/**
    	 * 出生日期校验。customFunction;birthdayDateVerify()
    	 * @param dateControlId 控件id
    	 * @returns
    	 */
    	birthdayDateVerify:function (){
    		return function (){
        		var result = {state:true,msg:''};
        		var natDateVal=$("div[name='natDate']").datefield("getValue");
        		//alert(natDateVal);
        		if(!natDateVal){
        			result.state=true;
        		}
        		var array = natDateVal.split("-");
        		var natDate = new Date(array[0],array[1],array[2]);
        		
        		var time = new Date();
        		var year = time.getFullYear()-18;
        		var month = time.getMonth()+1;
        		//alert(month);
        		var day  = time.getDate();
        		var today = new Date(year,month,day);
        		if ( natDate > today) {
        			result.state=false;
        			result.msg="添加的主要人员必须年满18周岁！";
        			return  result ;
        		}
        		
        		return  result ;		
        	};		
    	},
    	/**
    	 * 当国籍改变为非中国时，户籍登记地址文本改为，现居住地址
    	 */
    	onCountryChange:function(country,ui){
    		var country = '';
    		if(event.country){
    			country = event.country || '';
    		}else{
    			country = ui.newValue || '';
    		}
    		//var country=$("div[name='country']").comboxfield("getValue");
    		if(country!="156"){
    			$("#houseAddProv").comboxfield("option","label","<font color='red'>*</font>中国居住地址");
    			$("div[name='liteDeg']").comboxfield("option","rule","");
    			$("div[name='polStand']").comboxfield("option","rule","");
    		}else{
    			$("#houseAddProv").comboxfield("option","label","<font color='red'>*</font>户籍登记地址");
    			$("div[name='liteDeg']").comboxfield("option","rule","must");
    			$("div[name='polStand']").comboxfield("option","rule","must");
    		}
    	},
    	/**
    	 * 证件类型改变时，证件号的校验方式改变
    	 */
    	onCerTypeChange:function (){
    		var cerType=$("div[name='cerType']").comboxfield("getText");
    		if(cerType=="中华人民共和国居民身份证"){
    			$("div[name='cerNo']").textfield("option","rule","must_idcard_length;0;40");
    		}else{
    			$("div[name='cerNo']").textfield("option","rule","must_length;0;40");
    		}
    	},
    	/**
    	 * 证件号码写完时，将出生日期，性别选中
    	 */
    	onCerNoChange:function (){
    		var ruleValue = $("div[name='cerNo']").textfield("option","rule");
    		if(ruleValue=="must_idcard_length;0;40"){//身份证
    			var flog=$("div[name='cerNo']").has(".jazz-helper-hidden").is("div");
    			if(flog){
    				var cerno=$("div[name='cerNo']").textfield("getValue");
    				if(cerno && cerno.length==18){
    					if(Number(cerno.substring(16, 17))% 2==1){
    						$("div[name='sex']").radiofield("setValue","1");
    					}else{
    						$("div[name='sex']").radiofield("setValue","2");
    					}
    					var year=cerno.substring(6,10);
    					var month=cerno.substring(10,12);
    					var day=cerno.substring(12,14);
    					$("div[name='natDate']").datefield("setValue",year+'-'+month+'-'+day);				
    				}
    				if(cerno && cerno.length==15){//15位身份证号
						if(Number(cerno.substring(14, 15))% 2==1){
    						$("div[name='sex']").radiofield("setValue","1");
    					}else{
    						$("div[name='sex']").radiofield("setValue","2");
    					}
    					var year=cerno.substring(6,8);
    					year = "19"+year;
    					var month=cerno.substring(8,10);
    					var day=cerno.substring(10,12);
    					$("div[name='natDate']").datefield("setValue",year+'-'+month+'-'+day);
					}
    			}
    		}
    	},
    
    };
    mbr._init();
    return mbr;
});