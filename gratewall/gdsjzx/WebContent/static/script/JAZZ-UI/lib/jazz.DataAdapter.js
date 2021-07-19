(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.DataAdapter
	 * @description 
	 */
	$.DataAdapter = {
		submit: function(params){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.submit(params);
		},
		vtypeset: ['gridpanel', 'formpanel', 'comboxtreefield', 'comboxfield', 'icon']
	};

	$.dataAdapterObject = function() {
		
		 this.options = {
			 sourceobject: null,   //组件对象   
		     contextpath: '',
			
			 name: null,   //组件对应的名称
			 vtype: null,  //vtype类型
			 
			 components: null, 
			
			 url: null,
			 type: 'post',
	    	 data: null,        //数据
			 datatype: 'json',  //数据类型
			 async: true,       //true false  默认true 异步
			 success: null,    
		     error: null,
		     callback: null,    //回调函数
		    
		     params: null      //参数{}
	     };
	 };
	 
	 $.dataAdapterObject.prototype = {
	    
		//UI调用
	    createAdapter: function(){
			this.jazzData = new Object();
			this.dataArray = new Array();
			this.jazzData["data"] = this.dataArray;
	 	},
	 	
	 	/**
	 	 * @desc 添加参数
	 	 * @param {name}  key值 
	 	 * @param {value} value值
	 	 */
	 	addAttr: function(name, value) {
	 		if (!!name && !!vtype) {
	 			var temp = {};
	 			temp['name'] = name;
	 			temp['vtype'] = 'attr';
	 			temp['value'] = value;
	 			this.dataArray[name] = value;
	 		}
	 	},	 	
	 	
	 	/**
	 	 * 添加form格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */		 	
	 	addForm: function(name, vtype, formData) {
	 		var obj = new Object();
	 		this.dataArray.push(formData);
	 		return obj;
	 	},

	 	/**
	 	 * 添加grid格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addGrid: function(name, vtype, gridData) {
	 		var obj = new Object();
//	 		obj["vtype"] = vtype;
//	 		if (!!name) {
//	 			gridObj["name"] = name;
//	 		}
//	 		if (!!gridData) {
//	 			obj["data"] = [];
//	 			obj["data"].push(gridData);
//	 		}
	 		this.dataArray.push(gridData);
	 		return obj;
	 	},
	 	
	 	/**
	 	 * 添加select格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addSelect: function(name, vtype, selectData){
	 		var obj = new Object();
	 		this.dataArray.push(selectData);
	 		return obj;	 		
	 	},
	 	
	 	/**
	 	 * 添加tree格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */
	 	addTree: function(name, vtype, treeData) {
	 		var obj = new Object();
	 		this.dataArray.push(treeData);
	 		return obj;
	 	},	 	
	 	
	 	/**
	 	 * 添加icon格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param iconData
	 	 * @returns {Object}
	 	 */
	 	addIcon: function(name, vtype, iconData){
	 		var obj = new Object();
	 		this.dataArray.push(iconData);
	 		return obj;	 		
	 	},	 	

	 	/**
	 	 * @desc 获取提交数据的string格式
	 	 * @returns string
	 	 */
	 	getJazzDataString: function() {
	 		return jazz.jsonToString(this.jazzData);
	 	},
	 	
	 	/**
	 	 * @desc 获取提交的JSON数据
	 	 * @returns {}
	 	 */
	 	getSubmitJsonData: function() {
	 		return this.jazzData;
	 	},
	 	
	 	/**
	 	 * @desc ajax提交方法
	 	 */
	 	ajaxMethod: function(){
	 		var $this = this;

    		//返回参数attr
			var initRes = {
				obj: {},
				setAttr: function(key, value){
					this.obj[key] = value;
				},
				getAttr: function(key){
					return this.obj[key];
				}
			};	 		

	 		var data = this.getSubmitJsonData();
	 		
			$.ajax({
				url: $this.options.url,
				type: $this.options.type,
				datatype: $this.options.datatype,
				data: data,
				async: $this.options.async,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				error: $this.options.error || function() {
				    jazz.error({detail: "与服务器连接断开，请尝试重新登录或与管理员联系!"});
				},
				success: function(responseObj){
					if(responseObj['exception']){
						jazz.error({detail: '<font color="blue" >错误名称</font> : ' + responseObj['exceptionName']
		                        + '<br><font color="blue" >错误信息</font> : ' + responseObj['exceptionMes']});
						return false;
					}
					//返回给回调函数的数据
					var callbackdata = null;
					
					var jsonData = responseObj['data'];
				    if(typeof(jsonData) == "object"){
				    	if(jsonData != null && $.isArray(jsonData)){
				    		var newJsonData = [];
				    		$.each(jsonData, function(i, jsonObj){
				    			var vtype = jsonObj['vtype'];
								if(vtype=='attr'){
									initRes.setAttr(jsonObj['name'], jsonObj['data']);
								}
								if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
									newJsonData.push(jsonData[i]);
								}
				    			//var componentObj = $('div[name="'+name+'"]').data(vtype);
								//componentObj.getValue(jsonObj);
				    		});
				    		callbackdata = newJsonData;
				    	}
				    }else{
				    	//与定义格式不符时，原样返回
				    	callbackdata = jsonData;
				    }				    	
					
					if($.isFunction($this.options.callback)){
						$this.options.callback.call(this, callbackdata, $this.options.sourceobject, initRes);
					}				    
				}
			}); 		
	 	},
		
	 	/**
	 	 * @desc UI表单调用
	 	 */
		ajaxSubmit: function () {
			//组件集合
			var components = this.options.components;
			if($.isArray(components)){
				for(var i=0, len=components.length; i<len; i++){
					var compJson = $('div[name="'+components[i]+'"]');
					var vtype = compJson.attr('vtype');
					var data = compJson[vtype]('getValue');

					//是否在vtype集合中定义，如果不存在则不解析
					if($.inArray(vtype, this.options.vtypeset) >= 0){
						if(vtype=='formpanel'){
							this.addForm(this.options.name, vtype, data);
						}
						if(vtype=='gridpanel'){
							this.addForm(this.options.name, vtype, data);
						}
						if(vtype=='comboxfield'){
							this.addForm(this.options.name, vtype, data);
						}
						if(vtype=='icon'){
							this.addForm(this.options.name, vtype, data);
						}
						if(vtype=='comboxtreefield'){
							this.addForm(this.options.name, vtype, data);
						}					
						
					}else{
						alert('解析的vtype类型错误！！！ ');
						return false;
					}
			
				}
			}
			
			//解析需要提交的参数
			var params = this.options.params;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='attr';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}
			this.ajaxMethod();
		},
		
		/**
		 * @desc 提交方法
		 * @param {params} ajax提交的参数对象
		 * @param {$sourcethis} 该参数会通过回调函数原样返回
		 */
		submit: function(params, $sourcethis){
			//数据提交
			//var isContinue = true;
			
	        //if(isContinue){
	            this.createAdapter();
	            $.extend(this.options, params);
	            this.options.sourceobject = $sourcethis;
	        	this.ajaxSubmit();
	        //}
		}
	 
	 };
	 
     $(function(){
		  //返回参数attr
		  var initRes = {
			  obj: {},
			  setAttr: function(key, value){
				  this.obj[key] = value;
			  },
			  getAttr: function(key){
				  return this.obj[key];
			  }
		 };
		 var initPageData = $('#OptimusData').attr('data');
		 if(typeof(initPageData) != 'object'){
			 initPageData = jazz.stringToJson(initPageData);
		 }
		 var jsonData = initPageData['data'];
		 if($.isArray(jsonData)){
				$.each(jsonData, function(i, jsonObj){
					var vtype = jsonObj['vtype'], name = jsonObj['name'];
					if(vtype=='attr'){
						initRes.setAttr(name, jsonObj['data']);
					}
					if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
						var componentObj = $('div[name="'+name+'"]').data(vtype);
						if(componentObj){
							componentObj.setValue(jsonObj);	
						}
					}
				});			 
		 }
		 if($.isFunction(window.initData)){
		     //页面初始化数据
		     initData(initRes);
	     }
     });

})(jQuery);