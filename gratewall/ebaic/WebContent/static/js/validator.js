define(['jquery'], function($){
	var validator = {
		aa: function(t){
			var result = {};
		    if($('#'+t).textfield("getValue")=="a"){     //你可以自定义校验逻辑     
		        result["state"]=false;               //state值必须为false,否则不显示提示信息     
		        result["msg"]="自定义函数错误信息提示";     //msg为错误的提示信息     
		    }
		    return result; 
		},
		
		validateTradeTermNotNegative : function (id) {
    		var result = {
    				state : true
    		};
    		var val = $('#'+id).autocompletecomboxfield("getValue");
    		if(val != "999999"){
    			var length = val.length;
    			// 如果不是数字
    			var re = /^[1-9]+.?[0-9]*$/;
    			if (!(re.test(val)) || (length<1 || length>2)) {
    				result["state"] = false;
    				result["msg"] = "营业期限请输入1到99的整数";
    			}
    		}
    		
    		return result;
    	},
    	validateMoney:function(elem){
    		//出资情况验证
    		var val=$("#money"+elem).textfield("getValue");
    		if(Number(val)>0){
    			if(val.indexOf("e")!=-1 || val.indexOf("E")!=-1){
    				var result = {};
    				result["state"]=false;  //state值必须为false,否则不显示提示信息   
    			    result["msg"]="包含特殊字符"; //msg为错误的提示信息   
    			    return result;
    			}
    			var obj = jazz_validator.doValidator(val, "number(18,6)");
    			return obj;
    		}else{
    			var result = {};
    	        result["state"]=false;  //state值必须为false,否则不显示提示信息   
    	        result["msg"]="请输入一个大于零的数"; //msg为错误的提示信息   
    	        return result;
    		}

    	},
    	validateTime:function (id){
    		var date=$("#"+id).datefield("getValue");
    		var result={
    				state:false,
    			    msg:"请输入正确的时间（xxxx-xx-xx）！"
    			};
    		if(date==""){
    			result["state"]=true;
    			return result;
    		}else if(date.indexOf("-")!=-1){
    			var year=date.substring(0,2);
    			if(year=="19"||year=="20"){			
    				var time=date.split("-");
    				var state= new Date(time[0],time[1],time[2]).getDate()==date.substring(date.length-2);
    				result["state"]=state;
    			}
    			
    		}	
    		return result;
    	},
    	
    	validateCopyNoNotNegative : function(id) {
    		var result = {};
    		var val = $("#"+id).textfield("getValue");
    		// 如果不是数字
    		var re = /^[0-9][0-9]?$/;
    		if (!(re.test(val))) {
    			result["state"] = false;
    			result["msg"] = "执照副本数可以大于等于0份 但不应超过99份";
    		}
    		return result;
    	},
    	
    	
    	//校验行业代码
    	validateBzFormat:function(bz){
    		var result = {};
    		var val = $("#"+bz).textfield("getValue");
    	//行业代码格式为75XX 或 51XX_52XX
    		var re = /^75([a-zA-Z0-9]{2})$/;
    		var re1 = /^51([a-zA-Z0-9]{2})_52([a-zA-Z0-9]{2})$/;
    		if (!(re.test(val)) && !(re1.test(val))) {
    			result["state"] = false;
    			result["msg"] = "行业代码格式应为'75XX'或者'51XX_52XX'";
    		}
    		return result;
    	},
    	
    	validatePwdConfirm:function(){
    		var result={};
    		var re_pwd = $('#re_pwd').passwordfield("getValue");
    		var user_pwd = $('#user_pwd').passwordfield("getValue");
    		if(re_pwd != user_pwd){
    			result["state"] = false;
    			result["msg"] = "两次输入的密码不一致";
    		}
    		return result;
    	},
    	/**
    	 * 证件有效期检测:
    	 * 
    	 * 证件有效起始日期不能晚于有效截止日期
    	 * 有效日期必须大于当天
    	 * 证件有效期不能短于一年
    	 */
    	cerValDateVerify : function(fromDiv,toDiv){
    		var result = {state:true,msg:''};
    		var cerValFromDateVal = $("div[name = '"+fromDiv+"']").datefield("getValue");
    		var cerValToDateVal = $("div[name = '"+toDiv+"']").datefield("getValue");
//    		if(!cerValFromDate){
//    			result.state = true;
//    		}
    		
    		//分别获取证件起始日期和截止日期 用于计算证件有效起始日期不能晚于有效截止日期
    		var array1 = cerValFromDateVal.split("-");
    		var array2 = cerValToDateVal.split("-");
    		var cerValFromDate = new Date(array1[0],array1[1]-1,array1[2]);
    		var cerValToDate = new Date(array2[0],array2[1]-1,array2[2]);
    		
//    		//获取今日时间 用于和证件有效期截止日期比较 计算证件有效日期必须大于当天
//    		var time = new Date();
//    		var todayYear = time.getFullYear();
//    		var month = time.getMonth();
//    		var day  = time.getDate();
//    		var today = new Date(todayYear,month,day);
//    		
//    		//根据证件有效期截止日期获取一年前时间  用于计算证件有效期不能短于一年
//    		var lastYear = time.getFullYear()-1;
//    		var lastYearDay = new Date(lastYear,array2[1]-1,array2[2]);
    		
    		if(cerValFromDate > cerValToDate){
    			result.state = false;
    			result.msg = "证件有效期起始日期不能晚于有效期截止日期！";
    			return result;
    		}
//    		else if(cerValToDate <= today){
//    			result.state = false;
//    			result.msg = "有效期日期必须大于今天！";
//    			return result;
//    		}else if(cerValFromDate > lastYearDay){
//    			result.state = false;
//    			result.msg = "证件有效期不能短于一年!";
//    			return result;
//    		}
    		return result;
    	},
    	
    	/**
    	 * 主要人员选择国籍时 如果是中国 那么主要人员证件类型只能是 身份证 , 军人离休证 , 或其他有效证件
    	 * 
    	 */
    	cerTypeVerify : function(cerType){
    		var result = {state:true,msg:''};
    		//获取证件类型
    		var cerTypeVal = $("div[name = "+cerType+"]").comboxfield('getValue');
    		
    		//获取国籍
    		var countryType = $("div[name = 'country']").comboxfield('getValue');
    		
    		//如果国籍是中国
    		if(countryType == '156'){
    			if(cerTypeVal == '1' || cerTypeVal == '5' || cerTypeVal == '9'){
    				return result;
    			}else if(cerTypeVal == ''){
    				result.state = false;
    				result.msg = '请选择有效证件类型!';
    				return result;
    			}else{
    				result.state = false;
    				result.msg = '中国国籍的有效证件只能是中华人民共和国公民身份证,军人离休证或其他有效证件!';
    				return result;
    			}
    		}
    		return result;
    	},
    	
    	/**
    	 * 基本信息页面 房屋用途如果手动录入“住宅”系统应该给出提示信息，不能通过
    	 */
    	domUsageTypeVerify : function(id){
    		var result = {};
    		var val = $("#"+id).autocompletecomboxfield("getValue");
    		if (val == '住宅') {
    			result["state"] = false;
    			result["msg"] = "房屋用途不能是住宅。";
    		}
    		return result;
    	}
    	
	};
	window.validator = validator;
	return validator;
});