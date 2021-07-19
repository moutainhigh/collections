Object.extend(PageContext,{
	init : function(_params){
		Object.extend(this.params,_params || {});
		this.helpers = {};
		this.fOnSuceesses = {};
		this.helpers["ExtendFieldAttribute"] = $dataHelper(com.trs.wcm.datasource.extendfield.ExtendFieldAttribute);
		this.fOnSuceesses["ExtendFieldAttribute"] = function(_transport,_json){
			UITransformer.transformAll($('extendfield_attribute'));			
			Element.show(this.ExtFieldAttr);
		}
	},
	_init_ : function(){
		this.CRTOperator = $("attr_column_crtextfield");
		this.Operators = $("attr_column_extfield_operators");
		this.ExtfieldDetail = $("attr_column_extfield_detail");
		this.HostDetail = $("attr_column_host");
		this.ExtFieldAttr = $("extendfield_attribute");
		this.inited = true;
	},
	response : function(_args){			
		if(!this.inited){
			this._init_();
		}
		var crtTitle = "字段创建任务";		
		if(_args['channelid']){
			crtTitle = "栏目" + crtTitle;
			this.operatorParams = {"HostId":_args['channelid'],"HostType":"101"};
		}else if(_args['siteid']){
			crtTitle = "站点" + crtTitle;
			this.operatorParams = {"HostId":_args['siteid'],"HostType":"103"};
		}else{
			crtTitle = "系统" + crtTitle;
			this.operatorParams = {"HostId":"0","HostType":"1"};
		}
		Object.extend(this.params,this.operatorParams);
		Object.extend(this.operatorParams,{"OperatorType":"create"});
		CRTOperator.loadData(crtTitle,"wcm6_extendfield","getOperators",this.operatorParams);
		Element.show(this.CRTOperator);
		
		
		var selectedObjIds = _args['ObjectIds'];		
		if(selectedObjIds.length > 0){
			if(selectedObjIds.length == 1){
				Object.extend(this.operatorParams,{"OperatorType":"extendfield"});
				Object.extend(PageContext.params,{"ObjectId":selectedObjIds});
			}else{
				Object.extend(this.operatorParams,{"OperatorType":"extendfields"});
				Object.extend(PageContext.params,{"ObjectIds":selectedObjIds.join(',')});
			}
			

			OTHOperators.loadData("扩展字段操作任务","wcm6_extendfield","getOperators",this.operatorParams);
			this.showSelectField(selectedObjIds);
			Element.show(this.Operators);
		}else{
			this.showHostInfo();			
		}
	},
	showHostInfo : function(){
		Element.show(this.HostDetail);
		Element.hide(this.ExtfieldDetail);
		Element.hide(this.Operators);
	},
	showSelectField : function(_selectedObjIds){		
		Element.hide(this.HostDetail);

		if(_selectedObjIds.length == 1){
			var id = {"ObjectId":_selectedObjIds[0]};
			this.helpers["ExtendFieldAttribute"].callBind("extendfield_attribute","findById",id,false,this.fOnSuceesses["ExtendFieldAttribute"]);
			Element.hide($('multiselected'));
		}else{
			Element.hide($('extendfield_attribute'));
			var sHtml = '选中了';
			sHtml += _selectedObjIds.length;
			sHtml += '个扩展字段';
			Element.update($('selectedNums'),sHtml);
			Element.show($('multiselected'));
		}
		Element.show(this.ExtfieldDetail);
	}
});
PageContext.init();

//create operate
var CRTOperator = Object.extend(new com.trs.wcm.Operators("create_operators",com.trs.wcm.datasource.extendfield.CRTOperators),{
	_fireEvent : function(_sObject,_sOptType){
		$ExtendFieldMgr.setParams(PageContext.params);
		$ExtendFieldMgr.create();
	},
	_callBack : function(){
		//alert('created callback');
	}
});

//operates excepte create.
var OTHOperators = Object.extend(new com.trs.wcm.Operators("extendfield_operators",com.trs.wcm.datasource.extendfield.Operators),{
	_fireEvent : function(_sObject,_sOptType){
		var opt = _sOptType.toLowerCase();
		if($ExtendFieldMgr[opt]){
			$ExtendFieldMgr.setParams(PageContext.params);
			$ExtendFieldMgr[opt]();
		}else{
			alert("No Such Operator!");
		}
	},
	_callBack : function(){
		//alert('call back');
	}
});