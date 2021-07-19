var ObjectTypeConst_CONTENTEXTFIELD = 10000;//to be a global var?
Object.extend(PageContext,{
	init : function(_params){
		var objId = getParameter("ObjectId");
		var hostId = getParameter("HostId");
		var hostType = getParameter("HostType");
		this.params = {"ObjectId":objId,"HostId":hostId,"HostType":hostType};		
		
		this.serviceId = "wcm6_extendfield";

		this.helpers = {};
		this.fOnSuceesses = {};	
		
		this.helpers["DataTypes"] = new com.trs.web2frame.DataHelper('wcm6_extendfield');
//		this.helpers["DataTypes"] = $dataHelper(com.trs.wcm.datasource.extendfield.SupportedDataTypes);
		this.fOnSuceesses["DataTypes"] = function(_transport,_json){		
			var sValue = TempEvaler.evaluateTemplater('create_area', _json);			
			Element.update($('area_holder'), sValue);

			var selFieldType = $("selFieldType");
			Event.observe(selFieldType,'change',function(_element){
				var selected = this.options[this.selectedIndex];
				if(selected.getAttribute("_type") == 12){
					$("attr_fieldlength_in").value = "100";//set default length to 100.		
					Element.show($("attr_fieldlength_in"));
					Element.show($("attr_fieldlength"));
				}else{
					$("attr_fieldlength_in").value = selected.getAttribute("_maxLength");
					Element.hide($("attr_fieldlength"));
				}
			}.bind(selFieldType));
			
			//FIXME:添加最大长度的校验,应该有更好的方法.			
			var types = selFieldType.options;
			var maxLength = 0;
			for(var i=0;i<types.length;i++){
				if(types[i]._type == 12){
					maxLength = types[i]._maxLength;
					break;
				}
			}
			var attrlenth = $("attr_fieldlength_in");
			var validation = attrlenth.validation;
			validation += ",max:'" + maxLength +"'";			
			attrlenth.setAttribute("validation",validation);

			PageContext.makePhysicalFieldsSelect();		
			
			$("HostId").value = PageContext.params.HostId;
			$("HostType").value = PageContext.params.HostType;
			
			ValidationHelper.addValidListener(function(){
				FloatPanel.disableCommand('savebtn', false);
			}, "addEditForm");
			ValidationHelper.addInvalidListener(function(){
				FloatPanel.disableCommand('savebtn', true);
			}, "addEditForm");
			ValidationHelper.initValidation();
		};

		this.helpers["ExtendFieldAttribute"] = new com.trs.web2frame.DataHelper('wcm6_extendfield');
//		this.helpers["ExtendFieldAttribute"] = $dataHelper(com.trs.wcm.datasource.extendfield.ExtendFieldAttribute);
		this.fOnSuceesses["ExtendFieldAttribute"] = function(_transport,_json){			
			_json = _json['CONTENTEXTFIELD'];			
			var sValue = TempEvaler.evaluateTemplater('edit_area', _json);						
			Element.update($('area_holder'), sValue);
			if(PageContext.params.HostId){
				$("HostId").value = PageContext.params.HostId
				$("HostType").value = PageContext.params.HostType
			}
			ValidationHelper.initValidation();			
		};
		
		this.helpers["ExistsFields"] = new com.trs.web2frame.DataHelper('wcm6_extendfield');
//		this.helpers["ExistsFields"] = $dataHelper(com.trs.wcm.datasource.extendfield.ExistsFields);
		this.fOnSuceesses["ExistsFields"] = function(_transport,_json){			
		
			var fieldCombox = PageContext.fieldCombox;//new com.trs.combox.Combox("FieldName");			
			fieldCombox.clearAllItems();			
			fieldCombox.addItem("<div style='font-weight:bold;font-size:16px;padding:8px 2px;border-bottom:1px dashed;background:aliceblue'>系统已有扩展字段</div>",null,true);

			var xmlDoc = _transport.responseXML;			
			var fields = xmlDoc.getElementsByTagName("ExtendedField");
			var size = fields.length;
			var itemDesc = null;
			var field = null;
			for(var i=0;i<size;i++){
				field = fields[i];
				var _name = getChildNodeValue(field,"FIELDNAME").toUpperCase();
				if(_name == "EDITOR") continue;//EDITOR system field,no use.
				var _length = getChildNodeValue(field,"FIELDMAXLEN");
				var _type = getChildNodeValue(field,"FIELDTYPE","Id").toUpperCase();//wenyh@2008-07-30 uppercased.
				var _typeName = getChildNodeValue(field,"FIELDTYPE","Name").toUpperCase();
				itemDesc = "<div style='padding:2px 1px;border-bottom:1px dotted #e9'>";
				itemDesc += "<span style='width:100px;padding:4px 1px'>" +  _name + "</span>";
				itemDesc +=  "<span style='width:60px;padding:4px 1px'>" + _typeName + "</span>";
				itemDesc += "<span  style='width:60px;padding:4px 1px'>长度=" + _length  + "</span>";
				itemDesc += "</div>";

				fieldCombox.addItem(itemDesc,{FieldName:_name,FieldType:_type,FieldLength:_length,ExtFieldId:getChildNodeValue(field,"EXTFIELDID")});
			}			
		};
	},	
	loadPhysicalFields : function(){
		Object.extend(PageContext.params,{PageSize:-1});
		this.helpers["ExistsFields"].call('queryExtendFields',PageContext.params,false,this.fOnSuceesses["ExistsFields"]);
	},
	makePhysicalFieldsSelect : function(){
		Event.observe($("cancelFieldSelected"),'click',function(){
			$("FieldName").value = "";
			$("FieldName").disabled = false;
			$("FieldName").focus();

			$("selFieldType").disabled = false;
			$("selFieldType").value= -1;

			$("ExtFieldId").value = 0;

			$("FieldDesc").value = "";
			//$("syncchildren").checked = false;
			Element.hide($("cancelFieldSelected"));
			PageContext.fieldCombox.setNoSelected();
		});

		this.fieldCombox = new com.trs.combox.Combox("FieldName");
		this.fieldCombox.setWidth("280px");
		this.fieldCombox.setHeight("180px");
		this.fieldCombox.sendRequest = function (){
			//show loading.
			PageContext.fieldCombox.addItem("<center><div style='padding-top:35px'><img src='../images/loading.gif'></div></center>");
			PageContext.loadPhysicalFields();
		};
		this.fieldCombox.onOptionClick = function(_event,_json){			
			if(_json == null || _json == void(0)){
				this.FieldName.value = "";
				this.FieldName.disabled = false;
				this.FieldName.focus();

				this.FieldType.value = "-1";
				this.FieldType.disabled = false;	
				
				ValidatorHelper.forceValid(this.FieldName);
				return false;
			}

			this.FieldName.value = _json.FieldName;			
			ValidatorHelper.forceValid(this.FieldName);
			this.FieldName.disabled = true;

			if(this.FieldDesc.value == ""){
				this.FieldDesc.focus();
			}
			
			this.FieldType.value=_json.FieldType;
			ValidatorHelper.forceValid(this.FieldType);
			this.FieldType.disabled = true;

			
			this.FieldLength.value = _json.FieldLength;

			this.ExtFieldId.value = _json.ExtFieldId;

			Element.show($("cancelFieldSelected"));

			return false;
		}.bind(this);

		this.FieldName = $("FieldName");
		this.FieldDesc = $("FieldDesc");
		this.FieldType = $("selFieldType");
		this.FieldLength = $("attr_fieldlength_in");
		this.ExtFieldId = $("ExtFieldId");
	},
	loadDataTypes : function(){		
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.serviceId,'getSupportedDataTypes',{},false,this.fOnSuceesses['DataTypes']);		
	},
	loadFieldInfos : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.serviceId,'findById',this.params,true,this.fOnSuceesses['ExtendFieldAttribute']);
	}
});
PageContext.init();

function getChildNodeValue(_parentNode,_name,_attr){
	var childNode = _parentNode.selectSingleNode(_name);
	if(childNode != null){
		if(_attr){
			return childNode.getAttribute(_attr) || "";
		}
		return childNode.text;
	}

	return "";
}

function saveExtField(){	
	if(!ValidationHelper.doValid('addEditForm')){
		return false;
	}

	this.doSave = function(){
		ProcessBar.init('执行进度，请稍候...');
        ProcessBar.addState('正在提交数据');
        ProcessBar.addState('成功完成');
        ProcessBar.start();
        ProcessBar.next();

		PageContext.helpers["DataTypes"].save('addEditForm',true,function (){
			ProcessBar.next();
			$MessageCenter.sendMessage("main",'PageContext.loadExtendfields','PageContext',PageContext.params);
			FloatPanel.close();
		});	
	}
	
	var zSync = $("ContainsChildren").value = $("syncchildren").checked;
	if(zSync){
		var okFunc = function(){
			$dialog().hide();			
			this.doSave();
		}.bind(this);

		$confirm("确定要同步创建到子对象吗？",okFunc,function (){$dialog().hide()},"同步创建确认");
	}else{
		this.doSave();
	}
	
	return false;
}

function resetForm(){
	if(PageContext.params.ObjectId == 0){
		$("FieldName").value = "";
		$("FieldName").disabled = false;
		$("selFieldType").disabled = false;
		$("selFieldType").value= -1;
		$("ExtFieldId").value = 0;
	}

	$("FieldDesc").value = "";
	$("FieldDesc").focus();
	$("syncchildren").checked = false;	

	return false;
}

function checkFieldName(){
	Object.extend(PageContext.params,{"DBFieldName":$F("FieldName")});

	PageContext.helpers["DataTypes"].call("existsSimilarName",PageContext.params,false,function(_transport,_json){		
		if(com.trs.util.JSON.value(_json, "Report.IS_SUCCESS") == 'true'){
            ValidationHelper.successRPCCallBack();
        }else{
            ValidationHelper.failureRPCCallBack(com.trs.util.JSON.value(_json, "Report.TITLE"));
        }
	});
}

LockerUtil.register2(PageContext.params.ObjectId,ObjectTypeConst_CONTENTEXTFIELD,true,"savebtn");
Event.observe(window,'load',function(){
	var params = PageContext.params;
	if(params.ObjectId >0 ){		
		PageContext.loadFieldInfos();
	}else{
		PageContext.loadDataTypes();		
	};

	params = null;	
});