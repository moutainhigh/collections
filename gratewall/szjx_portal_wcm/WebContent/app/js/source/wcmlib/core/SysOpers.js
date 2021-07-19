Ext.ns('wcm.SysOpers');
var sysOpersInit = function(){
	var m_myOpersBuffer = {};
	Ext.applyIf(wcm.SysOpers, {
		resolutionRelateInfo : {
			enable : true,
			hostDisplayNum : 3,
			objDisplayNum : 5,
			smallResolution : screen.width <= 1024
		},
		getDisplayNum : function(operInfo){
			var caller = wcm.SysOpers;
			var info = caller.resolutionRelateInfo;
			if(info.enable && info.smallResolution){
				if(operInfo.isHost) return info.hostDisplayNum;
				return info.objDisplayNum;
			}
			return operInfo.displayNum;
		},
		_preparedTypes : {},
		prepareOpers : function(type){
			var caller = wcm.SysOpers;
			type = type.toLowerCase();
			if(caller._preparedTypes[type])return;
			caller._preparedTypes[type] = true;
			var arrOpers = Object.deepClone(m_myOpersBuffer[type]) || [];
			var result = [];
			if(top.m_CustomizeInfo && top.m_CustomizeInfo.operator){				
				var hasFound = false;
				for(var name in top.m_CustomizeInfo.operator){
					if(name.toLowerCase()  == type.toLowerCase()){
						hasFound = true;
						var operKey = top.m_CustomizeInfo.operator[name].split(";")[1].split(":")[1];
						for(var m=0,nLength = operKey.split(",").length; m < nLength; m++ ){
							for(var n=0, nOperLength=arrOpers.length; n<nOperLength; n++){
								if(arrOpers[n].key == operKey.split(",")[m]){
									result.push(arrOpers[n]);
									break;
								}
							}
						}
					}
				}
				if(!hasFound){
					wcm.SysOpers.sortByOrderAsc(arrOpers,result);
					
				}
			}else{
				wcm.SysOpers.sortByOrderAsc(arrOpers,result);
			}
			caller.opers[type] = result;
		},
		sortByOrderAsc : function(arrOpers,result){
			while(arrOpers.length>0){
				var minOrder = parseFloat(arrOpers[0].order, 10) || (result.length+1);
				var index = 0;
				for(var i=1, n=arrOpers.length; i<n; i++){
					var tmpOrder = parseFloat(arrOpers[i].order, 10);
					if(tmpOrder &&  tmpOrder < minOrder){
						minOrder = tmpOrder;
						index = i;
					}
				}
				if(arrOpers[index]){
					result.push(arrOpers[index]);
				}
				arrOpers.splice(index, 1);
			}
		},
		getOperItem : function(o, p, wcmEvent){
			var caller = wcm.SysOpers;
			var tmpOper = caller._findOperItem(o, p);
			if(!wcmEvent)return tmpOper;
			if(!caller._isVisible(wcmEvent, tmpOper))return null;
			if(Ext.isFunction(tmpOper.isVisible) && !tmpOper.isVisible(wcmEvent)){
				return null;
			}
			return tmpOper;
		},
		_findOperItem : function(_type, _key){
			var caller = wcm.SysOpers;
			var type = (_type||'').toLowerCase();
			var opers = caller.getOpersByType(type);
			var key = (_key||'').toLowerCase();
			for(var i=0,n=opers.length;i<n;i++){
				if(opers[i].key.equalsI(key))
					return opers[i];
			}
		},
		_findOperItemInBuffer : function(_type, _key){
			var caller = wcm.SysOpers;
			var type = (_type||'').toLowerCase();
			var opers = m_myOpersBuffer[type];
			var key = (_key||'').toLowerCase();
			for(var i=0,n=opers.length;i<n;i++){
				if(opers[i].key.equalsI(key))
					return opers[i];
			}
		},
		/**
		 * o,p,q ===> _type, _key, _event
		 * o,p ===> _operItem, _event
		 */
		exec : function(o, p, q){
			var caller = wcm.SysOpers;
			if(Ext.isString(o)){
				return caller.exec0(caller._findOperItem(o, p), q);
			}
			return caller.exec0(o, p);
		},
		exec0 : function(operItem, event){
			if(!operItem)return;
			if(Ext.isFunction(operItem.fn)){
				return operItem.fn.apply(operItem, [event, operItem]);
			}
		},
		getOpersByType : function(type){
			if(!type)return[];
			wcm.SysOpers.prepareOpers(type);
			return wcm.SysOpers.opers[type.toLowerCase()] || [];
		},
		getOpers : function(info){
			return wcm.SysOpers.getOpersByInfo(info);
		},
		//返回默认显示的操作和隐藏的操作
		getOpersByInfo : function(info, event){
			var right = info.right;
			var type = info.type;
			var wcmEvent = event;
			//适应event为参数时的方式
			if(wcm.isEvent(info)){
				//var host = info.getCMSObj();
				var host = info.getObjsOrHost();
				if(host==null)return[[], []];
				right = host.getPropertyAsString('right', '');
				type = host.getType();
				if(host.length()>1){
					type = type + 's';
				}
				wcmEvent = info;
			}
			var caller = wcm.SysOpers;
			var opers = caller.getOpersByType(type);
			if(opers.length==0){
				return [[], []];
			}
			var activeOpers = [];
			for(var i=0,n=opers.length;i<n;i++){
				var tmpOper = opers[i];
				if(!caller._isVisible(info, tmpOper))continue;
				if(Ext.isFunction(tmpOper.isVisible) && !tmpOper.isVisible(wcmEvent)){
					continue;
				}
				activeOpers.push(tmpOper);
			}
			var cnt = 0, index=0, n=activeOpers.length;
			info.displayNum = caller.getDisplayNum(info) || activeOpers.length;
			var opers1 = [], opers2 = [];
			for(;cnt<info.displayNum&&index<n;index++){
				var tmpOper = activeOpers[index];
				if(caller.isSeparator(tmpOper, true))continue;
				cnt++;
				opers1.push(tmpOper);
			}
			var bLastSep = true;
			for(;index<n;index++){
				var tmpOper = activeOpers[index];
				if(bLastSep && (bLastSep=caller.isSeparator(tmpOper)))continue;
				if(bLastSep && !info.frompanel)continue;
				opers2.push(tmpOper);
			}
			return [opers1, opers2];
		},
		getOperByQuickKey : function(wcmEvent, quickKey, info){
			if(!quickKey)return null;
			if(!info){
				//var host = wcmEvent.getCMSObj();
				var host = wcmEvent.getObjsOrHost();
				if(host==null)return null;
				right = host.getPropertyAsString('right', '');
				type = host.getType();
				if(host.length()>1){
					type = type + 's';
				}
			}else{
				right = info.right;
				type = info.type;
			}
			var caller = wcm.SysOpers;
			var opers = caller.getOpersByType(type.toLowerCase());
			if(opers==null)return null;
			for(var i=0,n=opers.length;i<n;i++){
				var tmpOper = opers[i];
				if(!caller._isVisible(info || wcmEvent, tmpOper))continue;
				if(Ext.isFunction(tmpOper.isVisible) && !tmpOper.isVisible(wcmEvent)){
					continue;
				}
				if(Ext.isArray(tmpOper.quickKey)){
					if(tmpOper.quickKey.includeI(quickKey))return tmpOper;
				}
				else if(quickKey.equalsI(tmpOper.quickKey))return tmpOper;
			}
			return null;
		},
		getQuickKeys : function(){
			var result = {};
			var caller = wcm.SysOpers;
			for(var type in m_myOpersBuffer){
				var opers = m_myOpersBuffer[type];
				if(opers==null)continue;
				for(var i=0,n=opers.length;i<n;i++){
					var tmpOper = opers[i];
					if(!tmpOper || !tmpOper.quickKey)continue;
					if(Ext.isArray(tmpOper.quickKey)){
						tmpOper.quickKey.each(function(key){
							result[key] = true;
						});
						continue;
					}
					result[tmpOper.quickKey] = true;
				}
			}
			return result;
		},
		isSeparator : function(tmpOper, mustShow){
			return tmpOper.key==WCMConstants.OPER_TYPE_SEP && (!mustShow || !tmpOper.mustshow);
		},
		_isVisible : function(info, tmpOper){
			var right = info.right;
			if(wcm.isEvent(info)){
				right = info.getCMSObj().getPropertyAsString('right', '');
			}
			return this.isSeparator(tmpOper) || wcm.AuthServer.hasRight(right, tmpOper.rightIndex);
		},
		_forceGetOpers : function(type){
			var caller = wcm.SysOpers;
			var type = type.toLowerCase();
			var opers = m_myOpersBuffer[type];
			if(opers==null){
				opers = m_myOpersBuffer[type] = [];
			}
			return opers;
		},
		register : function(info){
			var items = info.items || {};
			var caller = wcm.SysOpers;
			caller.opers = caller.opers || {};
			if(Ext.isArray(items)){
				items.each(function(item){
					item = Ext.applyIf(item, info);
					caller._register(item);
				});
				return caller;
			}
			items = Ext.applyIf(items, info);
			if(items.type){
				caller._preparedTypes[items.type] = null;
			}
			caller._register(items);
			return caller;
		},
		_register : function(item){
			var caller = wcm.SysOpers;
			delete item.items;
			var opers = caller._forceGetOpers(item.type);
			var key = item.key;
//			for(var i=0, n=opers.length;i<n;i++){
//				if(opers[i]==null)continue;
//				if(opers[i].key.equalsI(key))return;
//			}
	//		item.order = item.order || (opers.length + 1);
	//		opers.splice(item.order-1, 0, item);
			opers.push(item);
		},
		unregister : function(_type, _key){
			if(!_type || !_key) return;

			//remove from m_myOpersBuffer
			var opers = m_myOpersBuffer[_type];
			if(!opers) return;
			for(var i=0;i<opers.length;i++){
				if(!opers[i]) continue;
				if(opers[i]['key'] == _key) break;
			}
			if(i >= opers.length) return;
			opers.splice(i, 1);

			//remove from wcm.SysOpers.opers
			var caller = wcm.SysOpers;
			var opers = (caller.opers || {})[_type];
			if(!opers) return;
			for(var i=0;i<opers.length;i++){
				if(!opers[i]) continue;
				if(opers[i]['key'] == _key) break;
			}
			if(i >= opers.length) return;
			opers.splice(i, 1);
		},
		unregisterAll : function(){
			var caller = wcm.SysOpers;
			for(var type in caller.opers){
				delete caller.opers[type];
			}
			caller.opers = null;
			m_myOpersBuffer = {};
		},
		myCreateInterceptor : function(thisFcn, fcn, scope){
			if(typeof fcn != "function"){
				return thisFcn;
			}
			var method = thisFcn;
			return function() {
				var result = fcn.apply(scope || this || window, arguments);
				if(Ext.isBoolean(result)){
					return result;
				}
				return method.apply(this || window, arguments);
			};
		},
		createInterceptor : function(item){
			var caller = wcm.SysOpers;
			if(!Ext.isFunction(item.isVisible))return;
			var operItem = caller._findOperItemInBuffer(item.type, item.key);
			if(operItem==null)return;
			operItem.isVisible = operItem.isVisible || function(){
				return true;
			};
			operItem.isVisible = caller.myCreateInterceptor(operItem.isVisible, item.isVisible);
		}
	});
};
sysOpersInit();