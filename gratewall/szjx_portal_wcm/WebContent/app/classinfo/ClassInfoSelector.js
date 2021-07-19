Ext.ns("wcm.ClassInfoSelector");
(function(){
	Ext.apply(wcm.ClassInfoSelector, {
		/**
		*@_sIds		    当前选中的分类法ID
		*@_fCallBack	返回结果之后，执行的回调函数。函数参数为
		*				_args:{selectedIds: [id1,id2...], selectedNames: [name1,name2...]}
		*/
		selectClassInfo : function(_sIds, fCallBack){
			var sTitle = wcm.LANG.CLASSINFO_28 || '选择分类法';
			var sURL = WCMConstants.WCM6_PATH + "classinfo/classinfo_select_list.html";			
			wcm.CrashBoarder.get('classInfo_Select').show({
				title : sTitle,
				src : sURL,
				width: '410px',
				height: '270px',
				reloadable : true,
				params :  {selId : parseInt(_sIds)},
				maskable : true,
				callback : function(_args){
					if(fCallBack){
						fCallBack(_args)
					}
				}
			});
		},
		/**
		*@_oParams		Object
		*				objectId   当前所属的分类法ID
		*				selectedValue  默认选中的节点
		*@_fCallBack	返回结果之后，执行的回调函数。函数参数为
		*				arg1:{ids: [id1,id2...], names: [name1,name2...]}
		*/
		selectClassInfoTree : function(params, fCallBack){
			if(!params || !params["objectId"]){
				Ext.Msg.alert( wcm.LANG.CLASSINFO_29 || "没有指定分类法ID信息");
				return;
			}
			if(!params["objectName"]){
				this.findById(params["objectId"], params, function(transport, json){
					Object.extend(params, {objectName: com.trs.util.JSON.value(json, "CLASSINFO.CNAME")});
					this._selectClassInfoTree.call(this, params, fCallBack);
				}.bind(this));
			}else{
				this._selectClassInfoTree.apply(this, arguments);
			}
		},
		_selectClassInfoTree: function(params, fCallBack){
			var sTitle = wcm.LANG.CLASSINFO_30 || '选择分类法节点';
			var sURL = WCMConstants.WCM6_PATH + "classinfo/classinfo_select_tree.html?treeType="+(params["treeType"]||0);
			wcm.CrashBoarder.get('classInfo_Select_Tree').show({
				title : sTitle,
				src : sURL,
				width: '410px',
				height: '270px',
				params : params,
				reloadable : true,
				maskable : true,
				callback : function(_args){
					if(fCallBack){
						fCallBack(_args)
					}
				}
			});
		},
		findById : function(_nObjectId, _parameters, fCallBack){
			var oParams = Object.extend({objectId : _nObjectId}, _parameters || {});
			BasicDataHelper.Call('wcm6_ClassInfo', 'findById', oParams, true, function(transport, json){
				if(fCallBack) fCallBack(transport, json);
			});
		},
		exists : function(oParams, fValid, fInvalid){
			BasicDataHelper.Call('wcm6_ClassInfo', 'exists', oParams, true, function(transport, json){
				$v = com.trs.util.JSON.value;
				var sClassInfoId = $v(json, "CLASSINFO.CLASSINFOID");
				var cName = $v(json, "CLASSINFO.CNAME");
				if(!cName){
					(fInvalid || Prototype.emptyFunction)("notFound");
				}else{
					(fValid || Prototype.emptyFunction)('', sClassInfoId, cName, transport, json);
				}
			});
		}
	});
})();
