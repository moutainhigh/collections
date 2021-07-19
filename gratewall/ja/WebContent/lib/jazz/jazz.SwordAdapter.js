(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.DataAdapter
	 * @description
	 */
	$.DataAdapter = {
		submit: function(params, $this){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.submit(params, $this);
		},
		submit2: function(params,  $this){
			var adapter = new $.dataAdapterObject();
			adapter.submit(params,  $this);
		},
		query: function(params){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.createAdapter();
	 		adapter.options.components = params['components'];
	 		adapter.componentsData2();
	 		return adapter.dataArray;
		}
	};

	$.dataAdapterObject = function() {
		
		 this.options = {
			 sourceobject: null,   //组件对象   
		     contextpath: '',
			 
			 name: null,   //组件对应的名称 
			 vtype: null,  //vtype类型
		     tid: null,          
			 ctrl: null,			 
			 
			 components: null, 
			
			 url: null,
			 type: 'post',
	    	 data: null,        //数据
			 dataType: 'json',  //数据类型

	    	 async: true,       //true false  默认true 异步
			 success: null,    
		     error: null,
		     callback: null,    //回调函数
		     params: null,      //参数{}
		     pageparams: null,  //分页所需要的必要参数
		     queryparams: null  //查询条件必要参数
	     };
	 };
	 
	 $.dataAdapterObject.prototype = {
	    
		//UI调用
	    createAdapter: function(){
			this.jazzData = new Object();
			this.dataArray = new Array();
			this.jazzData["data"] = this.dataArray;
			
			//响应数据转换
			this.c_jazzData = new Object();
			this.c_dataArray = new Array();
			this.c_jazzData["data"] = this.c_dataArray;
			
			//请求数据转换
			this.r_jazzData = new Object();
			this.r_dataArray = new Array();
			this.r_jazzData["data"] = this.r_dataArray;
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
	 	 * @param formData
	 	 */		 	
	 	addForm: function(name, vtype, formData) {
	 		this.dataArray.push(formData);
	 	},
	 	
	 	/**
	 	 * 添加form格式数据
	 	 * @param {form} form表单的对象
	 	 */	 	
	 	addFormToAttr: function(form){
	 		var data = form.getChildrenComponent();
	 		var $this = this;
			$.each(data, function(i, obj){
        		var vtype = $(obj).attr("vtype");
        		var isInited = $(obj).isComponentInited();
        		if(isInited && vtype!='toolbar' && vtype!='button'){
        			var children = $(obj).data(vtype);
        	 		var _newDataObj = {}, value = children.getValue();
        	 		_newDataObj["vtype"] = "attr";
        	 		_newDataObj["name"] = children.options.name;
        	 		_newDataObj["data"] = value;
        	 		$this.dataArray.push(_newDataObj);      
        		}
			});
	 	},

	 	/**
	 	 * 添加grid格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addGrid: function(name, vtype, gridData) {
//	 		obj["vtype"] = vtype;
//	 		if (!!name) {
//	 			gridObj["name"] = name;
//	 		}
//	 		if (!!gridData) {
//	 			obj["data"] = [];
//	 			obj["data"].push(gridData);
//	 		}
	 		this.dataArray.push(gridData);
	 	},
	 	
	 	/**
	 	 * 添加select格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addSelect: function(name, vtype, selectData){
	 		this.dataArray.push(selectData);
	 	},
	 	
	 	/**
	 	 * 添加tree格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */
	 	addTree: function(name, vtype, treeData) {
	 		this.dataArray.push(treeData);
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
		 * @desc 处理URL参数
		 */
		urlParams: function(){
	 		if(!!this.options.contextpath){
	 			this.options.url =  this.options.contextpath + '/ajax.sword?random=' + Math.random();
	 		}else{ 
	 			this.options.url = 'ajax.sword?random=' + Math.random();
	 		}
		},	 	
	 	
	 	/**
	 	 * @desc ajax提交方法
	 	 */
	 	ajaxSubmit: function(){
	 		var $this = this;

//    		//用于转换返回参数attr
//			var initRes = {
//				obj: {},
//				setAttr: function(key, value){
//					this.obj[key] = value;
//				},
//				getAttr: function(key){
//					return this.obj[key];
//				}
//			};			
//			var postData = 'postData='+this.getJazzDataString();

			this._convertJazzToSword();
			var postData = {};
			postData["postData"] = jazz.jsonToString(this.r_jazzData);
						
			this.urlParams();
			
			//alert($this.options.url+'***提交数据**转换sword后***'+jazz.jsonToString(postData));
			//jazz.log($this.options.url+"***转换成sowrd后的数据***"+jazz.jsonToString(this.r_jazzData));
			
			$.ajax({
				url: $this.options.url,
				type: $this.options.type,
				dataType: $this.options.dataType,
				data: postData,
				async: $this.options.async,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				error: $this.options.error || function(responseObj) {
					   jazz.error("与服务器连接断开，请尝试重新登录或与管理员联系!");
				},
				success: function(responseObj){    
				    if(!responseObj){
				    	return false;
				    }
				    
				    //jazz.log('***responseObj***'+JSON.stringify(responseObj));
				    
				    if(responseObj['exception']){
						jazz.error('<font color="blue" >错误名称</font> : ' + responseObj['exceptionName']
		                         + '<br><font color="blue" >错误信息</font> : ' + responseObj['exceptionMes']);
						return false;
					}
					
					var callbackdata = null;
					
					/* 获取 res.addAttr();的值 */
					var initRes = {
						obj: {},
						setAttr: function(key, value){
							this.obj[key+""] = value;
						},
						getAttr: function(key){
							return this.obj[key+""];
						},
						setMessage: function(message){
							this.obj['message'] = message;
						},
						getMessage: function(){
							return this.obj['message'];
						}
					};
					
					var message = responseObj["message"];
					if(!!message){
						initRes.setMessage(message);
					}
					
					responseObj = $this._convertSwordToJazz(responseObj);

					var dataObj = responseObj["data"];
					var dataLength = dataObj.length;
					if(dataLength > 0){
						var singlton=[]; j=0;
						
						//循环dataObj
						for(var i=0, len=dataLength; i<len; i++){
							var item = dataObj[i];
							if(item["vtype"] == "attr"){
								initRes.setAttr(item["name"], item["data"]);
							}else{
								//非attr的处理
								//根据vtype处理
								singlton[j] = item;
								j += 1;
								
							}
							
						}
						
						if(singlton.length>1){
							callbackdata = eval(responseObj);
						}else{
							callbackdata = singlton[0] || {};
						}
						
						
						/*for(var i=0, len=dataLength; i<len; i++){
							var item = dataObj[i];
							if(item["vtype"] == "attr"){
								initRes.setAttr(item["name"], item["data"]);	
								attrNum += 1;
							}else{
								singlton = item;
							}
						}
						if(dataLength - attrNum){
							
						}
						
						if(flag){
							callbackdata = eval(responseObj.data[0]);
						}else{
							callbackdata = eval(responseObj);
						}
						
						var dataNum = 1;
						if(!!dataObj){
							if(dataObj.length>1){
								$.each(dataObj, function(i, obj){
									  if(!!obj.data){
										  dataNum++;
									  }
								});
							}
						}
						if(dataNum>1){
							callbackdata = eval(responseObj);
						}
						
						*/
						
					}
					
					if(!!$this.options.callback){
						$this.options.callback.call(this, callbackdata, $this.options.sourceobject, initRes);
					}
					
				}
			}); 		
	 	},
	 	
	 	_convertJazzToSword: function(){
	 		//jazz.log('请求数据=='+this.getJazzDataString());
	 		var data = this.getSubmitJsonData();

	 		var arrdata = data["data"], pageinfo = {};
	 		for(var i=0, len=arrdata.length; i<len; i++){
	 			var item = arrdata[i];
	 			if(item["vtype"]=="formpanel" || item["vtype"]=="querypanel"){
	 				this._formpanelToSwordform(item);
	 			}else if(item["vtype"]=="attr"){
	 				this._attrToAttr(item);
	 			}else if(item["vtype"]=="pagination"){
	 				this._paginationToAttr(item, pageinfo);
	 			}
	 		}
	 		
	 		if(pageinfo.hasOwnProperty("rows") && pageinfo.hasOwnProperty("pageNum")){
		 		var rows = pageinfo["rows"] || 10;
				var pageNum = pageinfo["pageNum"] || 1;
				if(rows){
					rows = parseInt(rows);
				}
				if(pageNum){
					pageNum = parseInt(pageNum);
				}
				this.r_jazzData["rows"] = rows;
				this.r_jazzData["pageNum"] = pageNum;
				this.r_jazzData["queryType"] = "page";
	 		}
	 		
	 		if(this.options.ctrl != null){
	 			this.r_jazzData["ctrl"] = this.options.ctrl;
	 		}else if(this.options.tid != null){
	 			this.r_jazzData["tid"] = this.options.tid;
	 		}else{
	 			var url = this.options.url;
	 			if(url){
	 				var arr0 = [], arr1 = [];
	 				arr0 = url.split("tid=");
	 				arr1 = arr0[1].split("&");
	 				this.r_jazzData["tid"] = arr1[0];
	 			}
	 		}
	 	},
	 	
	 	_paginationToAttr: function(dataItem, pageinfo){
	 		var name = dataItem["name"];
	 		var data = dataItem["data"];
	 		if(name=="page"){
	 			name = "pageNum";
	 		}else if(name=="pagerows"){
	 			name = "rows";
	 		}
	 		pageinfo[name] = data;
	 		
// 			if(name=="page"){
// 				name = "pageNum";
// 				data = parseInt(data) || 1;
// 			}
// 			if(name=="pagerows"){
// 				name = "rows";
// 				data = parseInt(data) || 10;
// 			}
// 			this.r_jazzData["queryType"] = "page";
// 			this.r_jazzData[name] = data;	 				
//	 		this.r_jazzData["queryType"] = "page";
//	 		
//	 		this.r_jazzData["rows"] = data || 10;
//	 		this.r_jazzData["pageNum"] = 1;
	 	},
	 	
	 	_attrToAttr: function(dataItem){
	 		var _newDataObj = {};
	 		_newDataObj["sword"] = "attr";
	 		_newDataObj["name"] = dataItem["name"]+"";
	 		_newDataObj["value"] = dataItem["data"]+"";
	 		this.r_dataArray.push(_newDataObj);
	 	},
	 	
	 	_formpanelToSwordform: function(dataItem){
	 		var name = dataItem["name"];
	 		var data = dataItem["data"];
	 		var _newDataObj = {};
	 		_newDataObj["sword"] = "SwordForm";
	 		_newDataObj["name"] = name;
 			var comObj = {};
 			var j=0;
	 		for(var d in data){
	 			var x = {}; 
	 			if(data[d]){
	 				x["value"] = data[d];
	 			}else{
	 				x["value"] = '';
	 			}
	 			comObj[d] = x;
	 		} j = j+1;
	 		_newDataObj["data"] = comObj;
	 		
	 		this.r_dataArray.push(_newDataObj);	
	 	},
	 	
	 	
	 	_convertSwordToJazz: function(responseObj){
	 		var $this = this;
	 		var dataObj = responseObj["data"];
	 		if($.isArray(dataObj)){
	 			for(var i=0, len=dataObj.length; i<len; i++){
	 				var dataItem = dataObj[i];
	 				if(dataItem["sword"]=="SwordGrid"){            //转 gridpanel
	 					$this._SowrdgridToGridpanel(dataItem);
	 				}else if(dataItem["sword"]=="SwordForm"){
	 					$this._SwordformToFormpanel(dataItem);
	 				}else if(dataItem["sword"]=="SwordSelect"){
	 					$this._SwordtreeToCombox(dataItem);
	 				}else if(dataItem["sword"]=="SwordTree"){
	 					$this._SwordtreeToTree(dataItem);
	 				}else if(dataItem["type"]=="" || dataItem["type"]=="attr"){
	 					$this._SwordtreeToAttr(dataItem);
	 				}
	 			}
	 		}
	 		
	 		//jazz.log("***响应数据    由sowrd转jazz后***"+jazz.jsonToString(this.c_jazzData));
	 		
	 		return this.c_jazzData;
	 	},
	 	
	 	_SwordtreeToAttr: function(dataItem, f){
	 		var name = dataItem["name"];
	 		var value = dataItem["value"];
	 		var _newDataObj = {};
	 		_newDataObj["vtype"] = "attr";
	 		_newDataObj["name"] = name;
	 		_newDataObj["data"] = value;
	 		
	 		if(!f){
	 			this.c_dataArray.push(_newDataObj);
	 		}
	 		
	 		return _newDataObj;
	 	},
	 	
	 	_SwordtreeToTree: function(dataItem, f){
/*	 		var name = dataItem["name"];
	 		var dataObj = dataItem["data"];
	 		
	 		var _newDataObj = {};
	 		_newDataObj["name"] = name; 
 			var data = [];
 			for(var i=0, len=dataObj.length; i<len; i++){
 				var item = dataObj[i];
 				var _newItem = {};
 				for(var j in item){
 					var v = item[j];
 					if(!!v){
 						_newItem[j] = v; 						
 					}else{
 						_newItem[j] = "";
 					}
 				}
 				data.push(_newItem);
 			}
 			_newDataObj["data"] = data;
 			if(!f){
 				this.c_dataArray.push(_newDataObj);
 			}
 			return _newDataObj;*/
	 		
 			if(!f){
 				this.c_dataArray.push(dataItem);
 			}	 		
	 		return dataItem;
	 	},

	 	_SwordtreeToCombox: function(dataItem, f){
	 		var name = dataItem["name"];
	 		var dataObj = dataItem["data"];
	 		
	 		var _newDataObj = {};
	 		_newDataObj["name"] = name;
 			var comObj = {}, data = [];
 			for(var i=0, len=dataObj.length; i<len; i++){
 				var item = dataObj[i];
 				var _newItem = {};
 				_newItem["text"] = item["label"];
 				_newItem["value"] = item["value"];
 				data.push(_newItem);
 			}
 			comObj[name] = data;
 			_newDataObj["data"] = comObj;
 			if(!f){
 				this.c_dataArray.push(_newDataObj);
 			}
 			return _newDataObj;
	 	},
	 	
	 	_SwordformToFormpanel: function(dataItem, f){
	 		var name = dataItem["name"];
	 		var data = dataItem["data"];
	 		var _newDataObj = {};
 			_newDataObj["name"] = name;
 			_newDataObj["vtype"] = "formpanel";	
 			if(data){
 				var item = {};
 				for(var i in data){
 					var v = data[i]["value"];
 					if(!!v){
 						item[i] = v;
 					}else{
 						item[i] = "";
 					}
 				}
 				_newDataObj["data"] = item;
 			}else{
 				_newDataObj["data"] = {};
 			}
 			
 			if(!f){
 				this.c_dataArray.push(_newDataObj);
 			}
 			return _newDataObj;
	 	},
	 	
	 	_SowrdgridToGridpanel: function(dataItem, f){
	 		var trs = dataItem["trs"];
	 		var name = dataItem["name"];
	 		var totalRows = dataItem["totalRows"];
	 		var pageNum = dataItem["pageNum"];
	 		var rows = dataItem["rows"]; 
	 		
	 		var _newDataObj = {};
 			_newDataObj["name"] = name;
 			_newDataObj["vtype"] = "gridpanel";	
	 		if($.isArray(trs)){
	 			var data = {};
	 			data["totalrows"] = totalRows;
	 			data["page"] = pageNum;
	 			data["pagerows"] = rows;
	 			var row = [];
	 			for(var i=0, len=trs.length; i<len; i++){
	 				var item = {};
	 				var tr = trs[i];
	 				var tds = tr["tds"];
 					for(var j in tds){
 						var v = tds[j]["value"];
 						if(!!v){
 							item[j] = v; 							
 						}else{
 							item[j] = "";
 						}
	 				}
 					row.push(item);
	 			}
	 			data["rows"] = row;
	 			_newDataObj["data"] = data;
	 		}else{
	 			_newDataObj["data"] = {};
	 		}
	 		
	 		if(!f){
	 		   this.c_dataArray.push(_newDataObj);
	 		}
	 		return _newDataObj;
	 	},

	 	
	 	/**
	 	 * @desc 创建UI组件的JSON数据
	 	 */
		componentsData: function () {
			var flag=true;
			//组件集合
			var components = this.options.components;
			if(!components){
				var _name = this.options.name;
				if(_name){
					var array = []; array.push(_name);
					components = array;
				}
			}
			
			if($.isArray(components)){
				for(var i=0, len=components.length; i<len; i++){
					var name = components[i];
					var compJson = $('div[name="'+name+'"]');
					var vtype = compJson.attr('vtype');
					
					//验证
					if(vtype=='formpanel' || vtype=='querypanel')
					{
						compJson.find('div[vtype]').each(function(index, element){
							var el = $(element);
							var vtype=el.attr('vtype');
							var rule=el.attr('rule');
							var $field = el.data(vtype), options = $field.options;
							if(rule && vtype!="gridpanel" && vtype!="hiddenfield" && vtype!="checkboxfield" && vtype!="radiofield" && options.isrule){
								var msg = options.msg;
								var f = jazz.doTooltip($field, $field.getText(), rule, msg);
							    if(f==false){
							    	flag=false;
							    	$field._validateStyle(f);
							    }
							}
						}); 
					}
					if(!flag)
					{return false;}
					
					var data = compJson[vtype]('getValue');
					
					if(vtype=='formpanel' || vtype=='querypanel'){
						this.addForm(this.options.name, vtype, data);
					}else
					if(vtype=='gridpanel'){
						this.addGrid(this.options.name, vtype, data);
					}else
					if(vtype=='icon'){
						this.addIcon(this.options.name, vtype, data);
					}else
					if(vtype=='comboxtreefield'){
						this.addTree(this.options.name, vtype, data);
					}					
				}
			}
			
			var qparams = this.options.queryparams;
			if(qparams){
				var $this = this;
				$.each(qparams, function(){
					$this.dataArray.push(this);
				});
			};
			
			//分页提交的必要参数
			var params = this.options.pageparams;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='pagination';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
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
			return flag;
		},
		
	 	/**
	 	 * @desc 创建UI组件的JSON数据
	 	 */
		componentsData2: function () {
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

			//组件集合
			var components = this.options.components;
			
			if($.isArray(components)){
				for(var i=0, len=components.length; i<len; i++){
					var name = components[i];
					var compJson = $('div[name="'+name+'"]');
					var vtype = compJson.attr('vtype');

					//是否在vtype集合中定义，如果不存在则不解析
					if(vtype=='formpanel' || vtype=='querypanel'){
						var data = compJson[vtype]('getValue');
						this.addForm(this.options.name, vtype, data);
						this.addFormToAttr(compJson, name);
					}
			
				}
			}
		},		
		
//		/**
//		 * @desc 查询方法
//		 * @param {params} ajax提交的参数对象
//		 * @param {$sourcethis} 该参数会通过回调函数原样返回
//		 */		
//		query: function(params){
//			 this.createAdapter();
//			 $.extend(this.options, params);
//			 this.componentsData2();
//			 //this.ajaxSubmit();
//		},
		
		/**
		 * @desc 提交方法
		 * @param {params} ajax提交的参数对象
		 * @param {$sourcethis} 该参数会通过回调函数原样返回
		 */
		submit: function(params, $sourcethis){
			//数据提交
			var flag=true;
            this.createAdapter(); 
            $.extend(this.options, params);
            this.options.sourceobject = $sourcethis;
        	flag=this.componentsData();
        	if(flag==false){
        		return false;
        	}
        	this.ajaxSubmit();
		},
		
		/**
		 * @desc 初始化加载数据
		 */
		initLoadData: function(){

			var data = jQuery('#SwordPageData').attr('data');
			var initRes = {};
			if(data){
				var dataObj = jazz.stringToJson(data);
				
				var $this = new jQuery.dataAdapterObject();
				
				//存在需要加载的数据
				if(dataObj["data"].length>0){
					initRes = {
						obj: new Object(),
						setAttr: function(key, value){
							this.obj[key] = value;
						},
						getAttr: function(key){
							return this.obj[key];
						}
					};
					
					var items = dataObj["data"];
					for(var i=0, len=items.length; i<len; i++){
						var item = items[i];
						if(item["sword"] == 'SwordSelect'){
							var b = $this._SwordtreeToCombox(item, true);
							var name = b["name"];
							for(var p in b.data){
								b = b.data[p];
							}
							if(name){
								var form = $("div[name='"+name+"']"), vtype = form.attr("vtype");
								form[vtype]("setValue", b);
							}
							//jazz.log('初始化****combox***'+jazz.jsonToString(b));
						}else if(item["sword"] == 'SwordTree'){
							var b = $this._SwordtreeToTree(item, true);
							var name = b["name"];
							if(name){
								var form = $("div[name='"+name+"']"), vtype = form.attr("vtype");
								form[vtype]("setValue", b);
							}							
							//jazz.log('初始化****tree***'+jazz.jsonToString(b));
						}else if(item["sword"] == 'SwordForm'){
							var b = $this._SwordformToFormpanel(item, true);
							var name = b["name"];
							if(name){
								var form = $("div[name='"+name+"']"), vtype = form.attr("vtype");
								form[vtype]("setValue", b);
							}
							//jazz.log('初始化****formpanel***'+jazz.jsonToString(b));
						}else if(item["sword"] == 'SwordGrid'){
							var b = $this._SowrdgridToGridpanel(item, true);
							var name = b["name"];
							if(name){		
								var form = $("div[name='"+name+"']"), vtype = form.attr("vtype");
								//var gridpanel = form.data("gridpanel");
								form[vtype]("reload",b);
							}
						}else if(item["type"] == ""){
							initRes.setAttr(item["name"], item["value"]);
						}
					}
					
				}   
			}
			if ($.isFunction(window.initData)){
				setTimeout(function(){
					initData(initRes);
				},500);
			}
			
			if ($.isFunction(window.initPage)){
				initPage(initRes);
			}
	    }		
	 
	 };
	 
})(jQuery);

function initPageLoadData() {
	//setTimeout(function(){
		var obj = new jQuery.dataAdapterObject();
		obj.initLoadData();
	//}, 300);
}