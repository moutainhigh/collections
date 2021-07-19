/**
 * file : Address.js
 * 
 * { 
 * 		options的参数： 三个comboxfield 的id     rovCtrlId: '', cityCtrlId : 'otherCtrlId' 
 * }
 */
define(['jquery', 'jazz'], function($, jazz){

    var Address;
    Address = function(element, options,url,params) {//url:json文件路径
      url = "../../../static/js/widget/address_data.json";
      this.element = element;
      this._init(options,url,params);
    };
    Address.dafaults = {
    	provCtrlId :'',
    	cityCtrlId : '',
    	otherCtrlId: ''
    };
    Address.defaultParams = {
    		proValue :'',
    		cityValue : ''
    };
    Address.prototype.changeProvSetCity = function(proValue,cityValue){
    	
    };
    Address.prototype._init = function(options,url,params){
    	this.options = $.extend(true, {}, Address.dafaults, options);
    	this.params = $.extend(true, {}, Address.defaultParams, params);
    	var provCtrlId =this.options.provCtrlId;
 	    var cityCtrlId =this.options.cityCtrlId;
 	    var otherCtrlId =this.options.otherCtrlId;
 	    var proValue = this.params.proValue;
 	    var cityValue = this.params.cityValue;
 	    
 	    
    	//读取json文件
        $.getJSON(url,function(data){
        		
        		var province = data.data[0].data; //省份
        		var city = "";//市
        		//加载第一级省份数据
        		$('#'+provCtrlId).comboxfield("setValue",province);
        		
        		//根据第一级的value值唯一 确定，  加载第二级数据
        	    $('#'+provCtrlId).comboxfield("option", "itemselect", function(event, ui){
        			$.each(province,function(i,json){
                		var jsonString ={};
                		var province_val =  json['value'];
	            		if(ui.value == province_val){
	            			jsonString = json['sub']||[];
	            			if(jsonString==""){
	            				$('#'+cityCtrlId).comboxfield("option","rule","");
	            				
	            			}else{
	            				$('#'+cityCtrlId).comboxfield("option","rule","must");
	            				city = data.data[0].data[i]['sub']; //市
	            			}
	            			$('#'+cityCtrlId).comboxfield("setValue",jsonString);
	            		}
        			});
        	    });
        	  //根据第二级的value值唯一 确定，  加载第三级数据
    	      $('#'+cityCtrlId).comboxfield("option", "itemselect", function(event, ui){
    				$.each(city,function(i,json){
                		var jsonString ={};
                		var city_val =  json['value'];
		            		if(ui.value == city_val){
		            			jsonString = json['sub'];
		            			$('#'+otherCtrlId).comboxfield("setValue",jsonString);
		            		}
        			});
    	     });
            
    	      /**
    	       * 回显地址信息 chaiyoubing   2016-07-16  add
    	       */
    	      if(proValue&&cityValue){
    	    	  	var jsonStr ="";
	     	    	$('#'+provCtrlId).comboxfield("setValue",proValue);
	     	    	for(var i=0;i<province.length;i++){
	     	    		var proTemp = province[i].value;
	     	    		if(proTemp==proValue){
	     	    			jsonStr = province[i]['sub'];
	     	    		}
	     	    	}
	     	    	$('#'+cityCtrlId).comboxfield("setValue",jsonStr);
	      			$('#'+cityCtrlId).comboxfield("setValue",cityValue);
	     	   }
    	      
    	      /**
    	       * 回显地址信息 chaiyoubing   2016-07-16  add
    	       */
    	      if(proValue&&cityValue==''){
    	    	  	var jsonStr ="";
	     	    	$('#'+provCtrlId).comboxfield("setValue",proValue);
	     	    	for(var i=0;i<province.length;i++){
	     	    		var proTemp = province[i].value;
	     	    		if(proTemp==proValue){
	     	    			jsonStr = province[i]['sub'];
	     	    		}
	     	    	}
	     	    	$('#'+cityCtrlId).comboxfield("setValue",jsonStr);
	     	   }
    	      
        });
    };

    $.fn.Address = function(options,url,params) {
      this.each(function(){
    	  new Address($(this), options,url,params);
      });
    };
});

