/**
 * file : util.js
 * 
 * 工具方法。
 * 
 * 规范： 需要增加工具方法，请联系shiyameng，或者自己写好代码后，提供给shiyameng。
 * 
 */
define(['jquery', 'jazz'], function($, jazz){
	var util = {
		
		/**
		 * 导出对象（函数/变量），使页面可见。
		 * 如果 name 参数的值为 “myFunc” ，则页面上可以通过 ebaic.myFunc 访问导出的对象。
		 * 由于会导致出现全局变量，所以慎用！
		 * 
		 */
		exports : function(name,o){
			if(!window.ebaic){
				window.ebaic = {} ;
			}
			window.ebaic[name] = o;
		},
		/** 
		 * 表单重置。
		 */
		resetForm : function (formId){  
    		$("#"+formId).formpanel('reset');  
    		$("[name='"+formId+"']").formpanel('reset');  
    	},
    	/** 
		 * 表单查询。
		 */
    	queryForm : function (queryformName,resultGridpanelId, dataurl){
    	  $('#'+resultGridpanelId).gridpanel('option', 'dataurl',dataurl);
    	  $('#'+resultGridpanelId).gridpanel('query', [ queryformName]);
    	},
    	
    	ajax : function(url,params,successCallback, errorCallback ,isAsync){
    		url = url || '';
    		params = params || {};
    		//successCallback = successCallback || function(){} ;
    		isAsync = isAsync || true;
    		var params = {
		        url : url ,
		        params : params,
		        async : isAsync,
		        callback : successCallback	,
		        error : function(responseObj) {
				    if(responseObj["responseText"]){
				    	var err = jazz.stringToJson(responseObj["responseText"]);
				    	if(err['exceptionMes']){
				    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes'],function(){
				    			if(errorCallback){
				    				errorCallback(responseObj);
				    			}
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
    	
    	/**
    	 * 打开窗口。
    	 *
    	 */
    	openWindow : function (name, title, url , width , height,colsable){
    		colsable = colsable || true ;
    		var win = jazz.widget({
    			vtype : 'window',
    			name : name,
    			title : title,
    			titlealign : 'left',
    			titledisplay : true,
    			modal : true,
    			frameurl : url,
    			height : height,
    			width : width,
    			closable : colsable
    		});
    		// 打开窗口
    		win.window('open');
    		return win;
    	},
    	
    	
    	/**
    	 * 关闭窗口
    	 */
    	closeWindow:function (name,reload) {
    		if(top != self){
    			var oWin = $("div[name='" + name + "']", window.parent.document);
    			var close = oWin.find('.jazz-titlebar-icon-close');
    			if(close){
    				if(reload){
    					parent.location.reload();
    				}
    				close.click();
    			}
    			
    		}else{
    			var oWin = $("div[name='" + name + "']");
        		if(oWin){
        			oWin.window("close");
        		}
    		}   		
    	},
    	
    	encodeURI : function(s){
    		//因为有空格等特殊字符，所以编译两遍
    		s = encodeURI(s);
    		s = encodeURI(s);
    		return s;
    	}
			
	};
	return util;
});