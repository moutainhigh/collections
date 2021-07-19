define(['require','jquery', '../torch/member_editTorch','../widget/Address','../util','validator'], function(require, $, torch,address,util,validator){
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
					var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
					if(mbrFlag==2){//若是经理，产生方式必须为聘任
						$("#posBrForm").comboxfield('removeOption', '02');
		    			$("#posBrForm").comboxfield('removeOption', '03');
		    			$("#posBrForm").comboxfield('addOption', '聘任', '04');
		    			$("#posBrForm").comboxfield('setValue', '04');
					}
					//事件绑定
					$_this.bindingClick();
					//页面元素处理
					$_this.loadData();
					//加载job
					$_this.loadJob();
					////若董事有兼任经理，初始化经理信息
//					$_this.initManagerData();
					//函数导出
					util.exports("birthdayDateVerify",$_this.birthdayDateVerify());
				});
    		});
    	},
    	loadData : function() {
			var updateKey = "&wid=db54346ae2184ca5bd08801bb7e68bb7";
			$.ajax({
				url : '../../../torch/service.do?fid=applySetupMbrEdit'
						+ updateKey + torch.edit_primaryValues(),
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					var jsonData = data.data;
					if ($.isArray(jsonData)) {
						for (var i = 0, len = jsonData.length; i < len; i++) {
							$("div[name='" + jsonData[i].name + "']").formpanel("setValue", jsonData[i] || {});
							var formData = jsonData[i].data ;
							mbr.initUi(formData);
						}
						$("#Prov-City-Other").Address({provCtrlId:'houseAddProv',cityCtrlId:'houseAddCity'},'',{proValue:jsonData[0].data.houseAddProv,cityValue:jsonData[0].data.houseAddCity});
						//如果是外籍 则民族默认显示为'无'
						if(!(jsonData[0].data.country == '446') && !(jsonData[0].data.country == '158') && !(jsonData[0].data.country == '344')
								&& !(jsonData[0].data.country == '156')){
							$("div[name = 'nation']").comboxfield('setValue','00');
						}
					}
				}
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
    	 * 查询董事里是否有兼任经理的，若有,回显该董事的信息
    	 */
    	initManagerData:function(){
    		var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
    		if(mbrFlag==2){//编辑经理时
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
    	initUi:function(data){
    		//1.户籍登记地址
//    		$("#Prov-City-Other").Address({provCtrlId:'houseAddProv',cityCtrlId:'houseAddCity'});
    		//2.如果是监事，显示supsType监事类型，否则隐藏
    		var mbrFlag = jazz.util.getParameter('mbrFlag')||'';
    		if(mbrFlag==3){
    			var isSuped = jazz.util.getParameter('isSuped')||'';
    			if(isSuped == "0"){//不设立监事会
    				$("#supsType").hide();
        			$("#supsType").comboxfield('option','rule','');
        			$("#supsType").comboxfield('setValue','');
    			}else{//设监事会
    				$("#supsType").show();
        			$("#supsType").comboxfield('option','rule','must');
    			}
    		}else{
    			$("#supsType").hide();
    			$("#supsType").comboxfield('option','rule','');
    		}
    		if(mbrFlag==1){//董事
    			$("#isManager").show();
    			$("#isManager").comboxfield('option','rule','must');
    		}else{
    			$("#isManager").hide();
    			$("#isManager").comboxfield('option','rule','');
    		}
    		mbr.onCountryChange(data);
    		mbr.onCerTypeChange(data);
    		
    		/**
    		 * 3.将已有股东、董事的姓名带出来，选择姓名后，已经填写过的信息带出来；
    		 * 1 董事： 可以从已有股东中选择，也可以自己添加
    		 * 2 经理： 可以从已有股东和董事中选择，也可以自己添加
    		 * 3 监事： 董事、高级管理人员（经理、财务）不得兼任监事。
    		 */
    		mbr.queryInvesterNames();
    	},
    	loadJob : function(){
			var entmemberId =  jazz.util.getParameter('entmemberId')||'';
			var positionType = jazz.util.getParameter('mbrFlag')||'';
    		if(!entmemberId){
    			jazz.info("entmemberId不能为空。");
    			return ;
    		}
    		if(!positionType){
    			jazz.info("positionType不能为空。");
    			return ;
    		}
    		var url = '../../../apply/setup/member/job.do';
    		var params = {
		        url : url,
		        params:{ entmemberId : entmemberId  , positionType : positionType },
		        async:false,
		        callback : function(data, param, res) {
		        	var job = res.getAttr("job") || {};
		        	if(job){
		        		var supsType = job.supsType || '';
		        		var psnjobId = job.psnjobId || '';
		        		var posBrForm = job.posBrForm || '';
		        		var offYears = job.offYears || '';
		        		$("div[name='psnjobId']").hiddenfield("setValue", psnjobId);
		        		$("div[name='supsType']").comboxfield("setValue", supsType);
		        		$("div[name='posBrForm']").comboxfield("setValue", posBrForm);
		        		$("div[name='offYears']").comboxfield("setValue", offYears);
		        	}
		        	
		        	var isSuped = jazz.util.getParameter('isSuped')||'';
	    			if(isSuped == "0"){//不设立监事会,监事类型的值设为空
	    				$("div[name='supsType']").comboxfield("setValue", "");
	    			}
		        } 	
		    };  	
    		$.DataAdapter.submit(params);
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
    		        	id : id 
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
    		        		$("div[name='houseAddProv']").comboxfield("setValue",data.prov || '');
    		        		$("div[name='houseAddCity']").comboxfield("setValue",data.city || '');
    		        		$("div[name='houseAddOther']").textfield("setValue",data.other || '');
    		        		$("div[name='isManager']").comboxfield("setValue",data.isManager || '');
    		        		mbr.onCerNoChange();
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
        		if(!natDateVal){
        			result.state=true;
        		}
        		var array = natDateVal.split("-");
        		var natDate = new Date(array[0],array[1],array[2]);
        		
        		var time = new Date();
        		var year = time.getFullYear()-18;
        		var month = time.getMonth()+1;
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
    	onCountryChange:function(event,ui){
    		var country = '';
    		if(event.country){
    			country = event.country || '';
    		}else{
    			country = ui.newValue || '';
    		}
    		if(country!="156"){
    			$("#houseAddProv").comboxfield("option","label","<font color='red'>*</font>中国居住地址");
    			$("div[name='liteDeg']").comboxfield("option","rule","");
    			$("div[name='polStand']").comboxfield("option","rule","");
    		}else{
    			$("#houseAddProv").comboxfield("option","label","<font color='red'>*</font>户籍登记地址");
    			$("div[name='liteDeg']").comboxfield("option","rule","must");
    			$("div[name='polStand']").comboxfield("option","rule","must");
    		}
    		
    		if(country != '446' && country != '158'  && country != '344' 
					&& country !='156'){
				$("div[name = 'nation']").comboxfield('setValue','00');
			}else{
				$("div[name = 'nation']").comboxfield('setValue','');
			}
    		
    		
    		//拼接json
    		//var jsonString = "{'data':[{'data':[{'text':'中华人民共和国居民身份证','value':'1'},{'text':'军人离(退)休证','value':'5'},{'text':'其他有效身份证件','value':'9'}],'vtype':'codeset'}]}";
    		mbr.onCountryChange2China();
    	},
    	/**
    	 * 证件类型改变时，证件号的校验方式改变
    	 */
    	onCerTypeChange:function (data){
    		var cerType= data.cerType || '';//$("div[name='cerType']").comboxfield("getText");
    		if(cerType=="1"){
    			$("div[name='cerNo']").textfield("option","rule","must_idcard_length;0;40");
    		}else{
    			$("div[name='cerNo']").textfield("option","rule","must_length;0;40");
    		}
    	},
    	/**
    	 * 证件号码写完时，将出生日期，性别选中
    	 */
    	onCerNoChange:function (){
    		var cerType = $("div[name='cerType']").comboxfield("getValue");
    		if(cerType=='1'){//身份证
    			var flog=$("div[name='cerNo']").has(".jazz-helper-hidden").is("div");
    			if(flog){
    				var cerno=$("div[name='cerNo']").textfield("getValue");
    				if(cerno  ){
    					if(cerno.length==18){
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
    					if(cerno.length==15){//15位身份证号
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
    		}
    	},
    	
    	//国籍改变为中国时 该主要人员有效证件只能是身份证 , 军人离休证 ,或其他有效证件
    	onCountryChange2China : function(){
    		var country = $("div[name = 'country']").comboxfield('getValue');
    		
    		//获取证件对象 
    		var cerTypeObj = $("div[name = 'cerType']");
    		
    		if(country == '156'){
    			cerTypeObj.comboxfield('removeOption','4');
    			cerTypeObj.comboxfield('removeOption','X');
    			cerTypeObj.comboxfield('removeOption','Y');
    			cerTypeObj.comboxfield('removeOption','Z');
    			cerTypeObj.comboxfield('removeOption','T');
    			cerTypeObj.comboxfield('removeOption','2');
    			cerTypeObj.comboxfield('removeOption','3');
    			cerTypeObj.comboxfield('reload');
    		}
    	}
    };
    mbr._init();
    /* 返回模块 */
    return mbr;
});