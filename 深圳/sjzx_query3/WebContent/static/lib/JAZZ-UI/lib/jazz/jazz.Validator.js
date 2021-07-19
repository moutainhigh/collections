(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	
	/**
	 * @version 0.5
	 * @name jazz_validator
	 * @description 校验类。
	 */    
    jazz_validator = {
		/**
		 *@type Object
		 *@desc 正则参数
		 */		    		
	    reg: {
	        number:/^[-]?\d+(\.\d+)?([Ee][-+]?[1-9]+)?$/i,
	        numberInt:/^[-]?\d+$/i,
	        numberFloat:/^[-]?\d+\.\d+$/i,
	        numberPlusInt: /^\d+$/i,
	        //numberScience:/^[+|-]?\d+\.?\d*[E|e]{1}[+]{1}\d+$/, 
	        character:/^[\u4e00-\u9fa5A-Za-z]+$/i,
	        chinese:/^[\u4e00-\u9fa5]+$/i,
	        twoBytes:/^[^\x00-\xff]+$/i,
	        english:/^[A-Za-z]+$/i,
	        number$character:/^[\u4e00-\u9fa5A-Za-z0-9]+$/i,
	        number$english:/^[\w]+$/i,
	        qq:/^[1-9]\d{5,11}$/i,
	        telephone:/^((\(0\d{2,3}\))|(0\d{2,3}-))?[1-9]\d{6,7}(-\d{1,4})?$/i,
	        cellphone:/^0?1\d{10}$/i,
	        postal:/^\d{6}$/i,
	        currency:/^\$[-+]?\d+(\.\d+)?([Ee][-+]?[1-9]+)?$/i,
	        email:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/i,
	        url:/^(http|https|ftp):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/i
	    },
	    
		/**
		 *@type Object
		 *@desc 提示信息
		 */		    
	    msg:{
	        't':'',
	        'number':jazz.config.i18n.num,
	        'numberInt':jazz.config.i18n.numInt,
	        'numberFloat':jazz.config.i18n.numFloat,
	        'numberPlusInt':jazz.config.i18n.numberPlusInt,
	        'numberScience':jazz.config.i18n.numScience,
	        'character':jazz.config.i18n.character,
	        'chinese':jazz.config.i18n.chinese,
	        'twoBytes':jazz.config.i18n.twoBytes,
	        'english':jazz.config.i18n.english,
	        'date':jazz.config.i18n.date,
	        'number$character':jazz.config.i18n.numChar,
	        'number$english':jazz.config.i18n.numEnglish,
	        'qq':jazz.config.i18n.qq,
	        'telephone':jazz.config.i18n.telephone,
	        'cellphone':jazz.config.i18n.cellphone,
	        'idcard':jazz.config.i18n.idcard,
	        'postal':jazz.config.i18n.postal,
	        'currency':jazz.config.i18n.currency,
	        'email':jazz.config.i18n.email,
	        'url':jazz.config.i18n.url,
	        'and':jazz.config.i18n.and1,
	        'or':jazz.config.i18n.or,
	        'solo':'',
	        'must':jazz.config.i18n.must,
	        'contrast':jazz.config.i18n.contrast,
	        'range':jazz.config.i18n.range,
	        'customCheckStyle':jazz.config.i18n.customCheckStyle,
	        'length':jazz.config.i18n.length1,
	        'customFunction':jazz.config.i18n.customFunction
	    },
	    
		/**
         * @desc 身份证信息
         * @param {idcard} 需要验证的字符串
         * @return boolean
		 * @example this.checkIdcard(idcard)
         */
        checkIdcard: function(idcard){
        	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}; 
        	var idcard,Y,JYM; 
        	var S,M; 
        	var idcard_array = new Array(); 
        	idcard_array = idcard.split(""); 

        	//地区检验 
        	if(area[parseInt(idcard.substr(0,2))]==null) return false; 

        	//身份号码位数及格式检验 
        	switch(idcard.length){ 
//	        	case 15: 
//		        	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
//		        	ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
//		        	} else { 
//		        	ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
//		        	} 
//
//		        	if(ereg.test(idcard)) return true;
//		        	else return false; 
//		        	break;
        	
	        	case 18: 
	        	//18位身份号码检测 
	        	//出生日期的合法性检查 
	        	//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
	        	//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
	        		if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
	        			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
	        		} else { 
	        			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
	        		} 
	        		if(ereg.test(idcard)){//测试出生日期的合法性 
			        	//计算校验位 
			        	S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
			        	+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
			        	+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
			        	+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
			        	+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
			        	+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
			        	+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
			        	+ parseInt(idcard_array[7]) * 1 
			        	+ parseInt(idcard_array[8]) * 6 
			        	+ parseInt(idcard_array[9]) * 3 ; 
			        	Y = S % 11; 
			        	M = "F"; 
			        	JYM = "10X98765432"; 
			        	M = JYM.substr(Y,1);//判断校验位 
			        	if(M == idcard_array[17]) return true; //检测ID的校验位 
			        	else return false; 
	        		} 
	        		else return false; 
	        		break;
	        	
	        	default:
	        		return false; 
	        		break; 
        	} 
        },
	 
		/**
         * @desc 效验身份证信息
         * @param {val} 需要验证的字符串
         * @return boolean
		 * @example this.idcard(val)
         */        
        idcard: function(val) {
            var value = val;
            if (value == "") return true;
            return this.checkIdcard(value);
        },

		/**
         * @desc 数字定义
         * @param {val} 输入需要验证的数值
         * @param {zl}  区间范围第一个参数值
         * @param {xl}  区间范围第二个参数值
         * @return boolean
		 * @example this.numberDefine(val, zl, xl)
         */         
        numberDefine: function(val, zl, xl){
        	var value = val;
        	if(value=="")return true;
            if(!this.reg.number.test(value))return false;
            var valueStrArray=value.split(".");
            var zlength= zl;
            if(valueStrArray.length==2)
            	 return valueStrArray[0].length<=zlength && valueStrArray[1].length<=xl;
            else return valueStrArray[0].length <= zlength;
        },

		/**
         * @desc 排除字符
         * @param {val}  输入需要排除的字符
         * @param {value}  可以排除的字符
         * @return boolean
		 * @example this.customCheckStyle(val, value)
         */        
        customCheckStyle: function(val, value) {
        	//var text = this.getElValue(e);
        	var text = val;
        	var reList=value;
        	for(var i=0;i<text.length;i++){
        		var c=text.charAt(i);
        		if(reList.indexOf(c)>=0){
                   return false;
        		}
        	}
        	  return true;
        },

		/**
         * @desc 执行自定义函数时使用
         * @param {str} 函数名称
         * @return array
		 * @example this.getFunc(str)
         */         
        getFunc: function(str) {
            if (!str) {
                str = '';
            }
            var res = [];
//            if (str != "") {
//                var method = str.substring(0, str.indexOf("(")) || undefined;
//                var arg = str.substring(str.indexOf("(") + 1, str.indexOf(")")) || undefined;
//                if ((arg && arg.trim() == "") || arg == undefined) {
//                    arg = "";
//                } else {
//                    arg = "," + arg;
//                }
//
//          }
            res.push(eval(str));
            return res;
        },   

		/**
         * @desc 数字值比较
         * @param {val}  输入需要比较的数字
         * @param {v1}   比较范围第一个参数
         * @param {v2}   比较范围第二个参数
         * @return boolean
         */        
        contrast: function(val, v1, v2) {
        	var value = val;
            if (value == "")
                return true;
            if (this.reg.number.test(value)) {
                var flag = eval(value + v1);
                if (v2!='')
                    flag = flag ? eval(value + v2) : false;
                return flag;
            } else {
                return false;
            }
        },

		/**
         * @desc 字符长度
         * @param {item} 设定的参数
         * @param {val}  输入需要效验长度的字符
         * @param {begin} 开始位置
         * @param {end}  结束位置
         * @return boolean
         */        
        length: function(item, val, begin, end) {
            var value = val;
            if (value == "")
                return true;
            var len = this.getLen(value);
            if(item.indexOf(",")>=0){
            	return (len >= begin && len <= end);
            }else{
                if(end!="")
                return (len > begin && len < end);
                else return len == begin;
            }
        },

		/**
         * @desc 判定中文、字符长度
         * @param {str} 需要效验的字符
         * @return number
         */         
        getLen: function(str) {
            var len = 0;
            for (var i = 0; i < str.length; i++) {
                var strCode = str.charCodeAt(i);
                var strChar = str.charAt(i);
                if ((strCode > 65248) || (strCode == 12288) || this.reg.chinese.test(strChar))
                    len = len + 2;
                else
                    len = len + 1;
            }
            return len;
        },

		/**
         * @desc 测试正则表达式
         * @param {val} 输入需要效验的字符
         * @param {value} 效验的正则表达式
         * @return boolean
         */          
        testRegexp: function(val, rule) {
            if(typeof(rule)!='regexp'){
        		//转成RegExp对象
        		if(this.testRegex('\\/\\^', rule)){
                    rule = rule.substring(1, rule.length);
        		}
                if(this.testRegex('\\/\\i', rule)){
        			rule = rule.substring(0, rule.length-2);
                }
        	    rule = new RegExp(rule); 
        	}
            var value = val;
            if (value == "")
                return true;
            return rule.test(value);
        },
        
		/**
         * @desc 测试正则是否匹配
         * @param {regex} 效验的正则表达式
         * @param {rule} 效验的正则表达式
         * @return boolean
         */          
        testRegex: function(regex, rule) {
            return ((typeof regex == 'string') ? new RegExp(regex) : regex).test(rule);
        },
        
		/**
         * @desc 外部调用的效验方法
         * @param {val} 输入需要验证的字符串
         * @param {rule} 效验的正则表达式
         * @param {regMsg} 自定义函数时的提示消息
         * @return boolean
		 * @example jazz_validator.doValidator(val, rule, regMsg);
         */
		doValidator: function(val, rule, regMsg, compThis){
			var msg = [];
		    var customFunctionReturnObj = null; //自定义函数使用
			var ruleArray = [], type='solo';
			if(!!rule){
				if(rule.indexOf('_')>=0){             //与关系
					ruleArray = rule.split('_');
					type='and';
				}else if(rule.indexOf('||')>=0){      //或关系
					ruleArray = rule.split('||');
					type='or';
				}else{                                //单个校验
					ruleArray[0] = rule;
				}
				var flag=(type!='or')?true:false;
	            var state=flag, stateArray = [];

	            $.each(ruleArray, function(index, item) {
	            	var tempMSG = "";
	                if(item.indexOf('must') >= 0){
	                	if($.trim(val)=='') state = false;
	                	else state = true;
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
	                		tempMSG = jazz_validator.msg.t + jazz_validator.msg.must;
	                	}else{
	                		tempMSG = regMsg+'';
	                	}
	                }else if(/^(\w+_)?date;.+/.test(item)){
	                	var items = item.split(';');
	                	var d = items[1] || "yyyy-MM-dd"; 
	                	//var f = jazz.util.parseDataByDataFormat({"cellvalue": $.trim(val), "datatype": "date", "dataformat": d});
	                	var c = jazz.dateformat.isDate($.trim(val), d);
	                	if(c==false){
	                		if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
		                		tempMSG = jazz_validator.msg.t + "时间格式输入有误，请输入（"+d+"）格式！";
		                	}else{
		                		tempMSG = regMsg+'';
		                	}
	                		state = false;
	                	}else{
	                		state = true;
	                	}
	                }else if(item.indexOf("number") >=0 && item.indexOf(",") >=0){
	                	var defineArray = item.substring(item.indexOf("(")+1,item.indexOf(")")).split(",");
	                	var zl = defineArray[0]-defineArray[1];
	                	var xl = defineArray[1];
	                	state = jazz_validator.numberDefine(val, zl, xl);
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
	                		tempMSG = jazz_validator.msg.t + "数字,且整数部分最多"+ zl +"位,且小数部分最多" + xl + "位";
	                	}else{
	                		tempMSG = regMsg+'';
	                	}
	                	item = 'numberDefine';
	            	
	            	}else if (jazz_validator.reg[item]) {  
	            		//postal url telephone number$character数字汉字加英文字母    qq  email cellphone currency
	            		//numberInt numberFloat numberScience
	            		//chinese english
	                	state = (state == flag) ? jazz_validator.testRegexp(val, jazz_validator.reg[item]+"") : state;
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
	                		tempMSG = jazz_validator.msg.t + (jazz_validator.msg[item] || '');
	                	}else{
	                		tempMSG = regMsg+'';
	                	}
	                }else if(item.indexOf('regexp')>=0){ //正则
		            	//自定义正则表达式。例如：rule="regexp;^\d{1}$" 第一个参数为regexp是固定的，第二个参数为自定义的正则表达式，中间使用;分割
                    	state = (state == flag) ? jazz_validator.testRegexp(val, item.split(";")[1]) : state;
                    	tempMSG = jazz_validator.msg.t + ((!!regMsg)? regMsg: '');
                    	item = 'regexp';

	                }else if(item.indexOf('customCheckStyle')>=0){
	            	    //校验特殊字符的，例如，rule="customCheckStyle;*,"不允许出现*和,
	                	state = jazz_validator.customCheckStyle(val, item.split(";")[1]);
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
	                		tempMSG = jazz_validator.msg.t + jazz_validator.msg.customCheckStyle + item.split(";")[1];
	                	}else{
	                		tempMSG = regMsg+'';
	                	}
	                }else if(item.indexOf('customFunction')>=0){   //自定义
	            		var items = item.split(';');
	            		customFunctionReturnObj = jazz_validator.getFunc(items[1]);
	            		state = customFunctionReturnObj[0].state;
	            		tempMSG = jazz_validator.msg.t + customFunctionReturnObj[0].msg;
	            		item = 'customFunction';
	            		if(state == undefined || state == "undefined" || state == true){ state = true; tempMSG = ""; }
	            		
	            	}else if(item.indexOf('contrast')>=0){   //数字值比较
	            		//用户输入的数字值比较。
	            		//支持单范围比较 例如：rule="contrast;>=5" 第一个参数为contrast是固定的，第二个参数为比较范围及比较值，中间使用;分割
                        //支持双范围比较 例如：rule="contrast;>=5;<=6" 第一个参数为contrast是固定的，第二、三个参数为比较范围及比较值，中间使用;分割
                        //比较范围支持常用的所有类型 例如：> >= == < <= !=
	                    var contrastValue1 = item.split(";")[1], contrastValue2 = "";
	                    if (item.split(";").length == 3) {
	                    	contrastValue2 = item.split(";")[2];
	                    }
	                    state = jazz_validator.contrast(val, contrastValue1, contrastValue2);
	                    if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
	                    	tempMSG = jazz_validator.msg.t + jazz_validator.msg.contrast + contrastValue1 +",  "+ contrastValue2;
	                    }else{
	                    	tempMSG = regMsg+'';
	                    }
	                    item = 'contrast';
	                    
		            }else if(item.indexOf('length')>=0){//输入长度比较
		            	//用户输入的字符串长度必须在某个范围之内。
		            	//例如1：rule="length;2;4" 第一个参数为length是固定的，第二个参数为最小值，第三个参数为最大值，中间使用;分割相当于"用户输入的字符串长度在2,4之间，不包括边界值"
		            	//例如2：rule="length;2,4" 第一个参数为length是固定的，第二个参数为最小值，第三个参数为最大值，中间使用;分割相当于"用户输入的字符串长度在2,4之间，包括边界值"
		            	//例如3：rule="length;5" 第一个参数为length是固定的，第二个参数为长度值，相当于"用户输入的字符串长度只能为5"
		            	//汉字算两个字符
	                	var tempItemArray=item.split(";"), begin="", end="";
	                	if(tempItemArray.length==3){
		                    begin = item.split(";")[1];
		                    end = item.split(";")[2];
	                	}else{
	                		if(tempItemArray[1].indexOf(",")>=0){
	                			begin = tempItemArray[1].split(",")[0];
	    	                    end = tempItemArray[1].split(",")[1];
	                		}else{
	                			begin = tempItemArray[1];
	                		}
	                	}
	                    state = jazz_validator.length(tempItemArray[1], val, begin, end);
	                    if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
	                    	tempMSG = jazz_validator.msg.t + jazz_validator.msg['length'] + begin + (!!end ? ("和" + end  + "之间"):"");
	                    }else{
	                    	tempMSG = regMsg+'';
	                    }
	                    item = 'length';
	                    
		            }else if(item.indexOf('idcard')>=0){
		            	state = jazz_validator.idcard(val);
		            	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)==''){
		            		tempMSG = jazz_validator.msg.t + jazz_validator.msg.idcard;
		            	}else{
	                    	tempMSG = regMsg+'';
		            	}
		            	item = 'idcard';
		            }
	                
	                if(!state && tempMSG){
	                	msg.push(tempMSG);
	                }
	                
//	                if (index == 0) {
//	                    msg += tempMSG;
//	                } else {
//	                    msg += (!!tempMSG)?(jazz_validator.msg[type] + tempMSG):"";
//	                }
	                
	                stateArray[index] = state;
	            });
	            
	            var rs = true;
	            if(type=='and') {
	            	rs = this._rAndState(stateArray, type);
	            } else if(type=='or') {
	            	rs = this._rOrState(stateArray, type);
	            } else {
	            	rs = state;
	            }
	            
	            if(!!compThis){
	            	compThis.options.isverify = rs;
	            }
	            return {'state':rs, 'msg':msg.join(";")};
			}else{
				if(!!compThis){
					compThis.options.isverify = true;
				}
				return {'state': true, 'msg':msg.join()};
			}			
			
		},
		
		/**
         * @desc 确定返回状态
         * @param {stateArray} 状态数组
         * @param {type}  
         * @return boolean
         */   		
        _rAndState: function(stateArray, type){
        	var r = true;
        	$.each(stateArray, function(i, state){
               	if(state==false)
               		r = false;
			});
        	return r;
		},
		
		/**
         * @desc 确定返回状态
         * @param {stateArray} 状态数组
         * @param {type}  
         * @return boolean
         */
		_rOrState: function(stateArray, type){
			var r = false;
        	$.each(stateArray, function(i, state){
               	if(state==true)
               		r = true;
			});
        	return r;
		}
    };
});
