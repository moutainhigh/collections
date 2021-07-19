Ext.ns('wcm.RightDef', 'wcm.Right', 'wcm.Rights', 'wcm.RightView', 'wcm.RightView.PageNav');

(function(){
	/*
	*@class wcm.RightDef
	*/
	Ext.apply(wcm.RightDef, {
		items : {},//eg.{"DOCUMENT" :[{...}]}
		depends : {}, //eg.{33:[30,34],...}
		similars : {}, //eg.{33:[18,56],...}
		add : function(item){
			var sType = item['Type'];
			if(!sType) return;
			sType = item['Type'] = sType.toUpperCase();
			this.items[sType] = this.items[sType] || [];
			this.items[sType].push(item);
			this.initDepends(item);
			this.initSimilars(item);
		},
		initDepends : function(item){
			if(!item["Depends"]) return;
			var index = item["Index"];
			var depends = this.depends;
			if(!depends[index]){
				depends[index] = [];
			}
			Array.prototype.push.apply(depends[index], item["Depends"].split(","));
		},
		initSimilars : function(item){
			if(!item["Similars"]) return;
			var index = item["Index"];
			var similars = this.similars;
			if(!similars[index]){
				similars[index] = [];
			}
			Array.prototype.push.apply(similars[index], item["Similars"].split(","));			
		},
		getDepends : function(_nRightIndex){
			return _nRightIndex != null ? this.depends[_nRightIndex] : this.depends;
		},
		getSimilars : function(_nRightIndex){
			return _nRightIndex != null ? this.similars[_nRightIndex] : this.similars;
		},
		getItems : function(sType){
			return sType != null ? this.items[sType.toUpperCase()] : this.items;
		}
	});


	/*
	*@class wcm.Right
	*/
	wcm.Right = function(_item){
		var item = {};
		for(var sKey in _item){
			item[sKey.toUpperCase()] = _item[sKey];
		}
		this.item = item;
		//build the the array of right value.
		var sValue = item["RIGHTVALUE"];
		var nLen = sValue.length;
		this.arValue = new Array();
		for(var i=nLen; i>0; i--){
			this.arValue[nLen-i] = sValue.charAt(i-1);
		}
	};

	//private method
	var setRight = function(_nRightIndex, _bHasRight){
		//判断是否需要处理
		if(this.rightIndexsCache[_nRightIndex]) return;

		//将已经处理过的缓存起来
		this.rightIndexsCache[_nRightIndex] = true;

		var aIndex = [_nRightIndex];
		var sValue = null;
		if(_bHasRight){//Depends只是在选中状态下才有效	
			sValue = 1;
			var depends = wcm.RightDef.getDepends(_nRightIndex);
			if(depends != null){
				aIndex = aIndex.concat(depends);	
			}
		}else{//Similar只是在非选中状态下才有效	
			sValue = 0;
			var similars = wcm.RightDef.getSimilars(_nRightIndex);
			if(similars != null){
				aIndex = aIndex.concat(similars);	
			}
		}

		//处理本权限位的相似或依赖
		var arValue = this.arValue;
		var arValueChanged = [];
		for (var i = 0; i < aIndex.length; i++){
			if(arValue[aIndex[i]] == sValue){
				continue;
			}
			arValue[aIndex[i]] = sValue;
			arValueChanged.push(aIndex[i]);
		}	

		//递归处理相似或依赖产生的相似和依赖
		for (var i = 1; i < arValueChanged.length; i++){
			setRight.call(this, arValueChanged[i], _bHasRight);
		}			
	};

	Ext.apply(wcm.Right.prototype, {
		ignores : {'NAME':true},
		hasRight : function(_nRightIndex){
			_nRightIndex = parseInt(_nRightIndex, 10);
			if((_nRightIndex + 1) > this.arValue.length)
				return false;
			return (this.arValue[_nRightIndex] == 1);	
		},		
		setRight : function(_nRightIndex, _bHasRight){
			if(this.arValue[_nRightIndex] == _bHasRight) return;

			//由于需要递归，所以需要在第一次调用的时候定义已调用的Cache
			this.rightIndexsCache = {};
			//递归调用
			setRight.call(this, _nRightIndex, _bHasRight);
			//递归结束后，需要清除Cache
			delete this.rightIndexsCache;

			//设置设置后的rightValue值

			//keep 0 in no right bit.
			var aResult = [];
			var arValue = this.arValue;
			var len = arValue.length;
			for (var i = len - 1; i >= 0; i--){
				aResult[len - i - 1] = arValue[i] || 0;
			}
			var sNewRightValue = aResult.join("");

			//返回rightValue是否改变
			return this.setAttribute("rightValue", sNewRightValue) != sNewRightValue;
		},
		setAttribute : function(sKey, sValue){
			sKey = sKey.toUpperCase();
			var sOldValue = this.item[sKey];
			this.item[sKey] = sValue;
			if(sOldValue != sValue){
				wcm.Rights.onChange();
			}
			return sOldValue;
		},
		getAttribute : function(sKey){
			return this.item[sKey.toUpperCase()];
		},
		getKey : function(){
			return this.getAttribute("OprType") + "_" + this.getAttribute("OprId");
		},
		toXmlInfo : function(){
			var aResult = ["<WCMRIGHT><PROPERTIES>\n"];
			var item = this.item;
			var ignores = this.ignores;
			for (var sKey in item){
				if(ignores[sKey]) continue;
				aResult.push("\t<", sKey, ">", item[sKey], "</", sKey, ">\n");
			}
			aResult.push("</PROPERTIES></WCMRIGHT>");
			return aResult.join("");
		},
		getTypeHTML : function(){
			var nOprType = this.getAttribute("OprType");
			switch(nOprType){
				case TYPE_WCMOBJ_USER:
					return "<div class='icon_user' title='"+(wcm.LANG.AUTH_24||'用户')+"' >&#160;</div>";
				case TYPE_WCMOBJ_GROUP:
					return "<div class='icon_group' title='"+(wcm.LANG.AUTH_25||'用户组')+"' >&#160;</div>";
				case TYPE_WCMOBJ_ROLE:
					return "<div class='icon_role' title='"+(wcm.LANG.AUTH_26||'角色')+"' >&#160;</div>";
				default:
					return wcm.LANG.AUTH_27||"未知";
			}
		}
	});

	/*
	*@class wcm.Rights
	*/
	Ext.apply(wcm.Rights, {
		items : [],
		size : function(){
			return this.items.length;
		},
		getAt : function(index){
			return this.items[index];
		},
		getAttributes : function(_nType, _sAttr){
			var items = this.items;
			var aResult = [];
			for (var i = 0; i < items.length; i++){
				var right = items[i];
				if(right.getAttribute("OPRTYPE") == _nType){
					aResult.push(right.getAttribute(_sAttr));
				}
			}
			return aResult;
		},
		getIndexs : function(_nType){
			var items = this.items;
			var aResult = [];
			for (var i = 0; i < items.length; i++){
				var right = items[i];
				if(right.getAttribute("OPRTYPE") == _nType){
					aResult.push(i);
				}
			}
			return aResult;
		},
		add : function(item){
			this.onChange();
			this.items.push(new wcm.Right(item));
		},
		onChange : function(){
			//override...
		},
		removeAt : function(index){
			this.onChange();
			this.items.splice(index, 1);
		},
		removeAll : function(){
			this.onChange();
			this.items = [];
		},
		toXmlInfo : function(){
			var aResult = [];
			aResult.push("<WCMRIGHTS>\n");
			for (var i = 0, length = this.size(); i < length; i++){
				aResult.push("\t");
				aResult.push(this.getAt(i).toXmlInfo());
				aResult.push("\n");
			}
			aResult.push("</WCMRIGHTS>");
			return aResult.join("");
		},
		sort : function(compare){
			this.items = this.items.sort(compare);
		}
	});

	//private
	var findElement = function(tempNode, tagName){
		tagName = tagName.toLowerCase();
		while(tempNode != null){
			if(tempNode.tagName.toLowerCase() == tagName){
				return tempNode;
			}
			tempNode = tempNode.parentNode;
		}
		return null;
	};

	/*
	*@class wcm.RightView
	*/
	
	Ext.apply(wcm.RightView, {
		mode : 'view',//edit
		order : null,
		tabIndex : 0,
		
		setMode : function(mode){
			this.mode = mode;
		},

		getMode : function(){
			return this.mode;
		},

		setTabIndex : function(index){
			this.tabIndex = index;
		},

		render : function(){
			//build the params
			var sType = m_aRightTypeIds[this.tabIndex];
			var sRenderTo = 'id_TRSSimpleTab' + this.tabIndex;

			Element.update(sRenderTo, this.html(sType));
			this.bindEvents();
			PageContext.drawNavigator(wcm.RightView.PageNav.getInfo());
		},

		html : function(sType){
			var aHtml = [];
			//init fixed columns of head.
			aHtml.push('<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6" class="rightbox">');
			this.initHead(aHtml, sType);
			this.initBody(aHtml, sType);
			aHtml.push('</TABLE>');
			return aHtml.join("");
		},

		initHead : function(aHtml, sType){
			//init fixed columns of head.
			aHtml.push(
				'<TR class="grid_head" align="center">',
					this.mode == 'edit' 
					? 
					'<TD class="colIndex" style="cursor:pointer;" _type="selectAll">'+(wcm.LANG.AUTH_21||'全选')+'</TD>' 
					: 
					'<TD class="colIndex">'+(wcm.LANG.AUTH_22||'序号')+'</TD>',
					'<TD class="colType">'+(wcm.LANG.AUTH_23||'类型')+'</TD>',
					'<TD class="colName">',
						'<A href="#" _type="sort">'+ (wcm.LANG.AUTH_20||'名称'),
							this.order == 1 ? '▲' : '', 
							this.order == -1 ? '▼' : '', 
						'</A>',
					'</TD>'
			);

			//init dynamic columns of head.
			var aRightDef = wcm.RightDef.getItems(sType);
			for (var i = 0; i < aRightDef.length; i++){
				var item = aRightDef[i];
				if(this.mode == 'edit'){
					aHtml.push(
						'<TD class="headdesc"',
						' title="', item["Desc"], '['+(wcm.LANG.AUTH_19||'点击后可以选择一列')+']"',
						' _type="selectCol"',	
						' style="cursor:pointer;"',
						'>', item["Name"], '</TD>'
					);
				}else{
					aHtml.push(
						'<TD class="headdesc"',
						' title="', item["Desc"], '"',
						'>', item["Name"], '</TD>'
					);
				}
			}
			if(this.mode == 'edit')
				aHtml.push('<TD style="width:50px;">'+(wcm.LANG.AUTH_18||'删除')+'</TD>');
			aHtml.push('</TR>');
			return aHtml;
		},

		initBody : function(aHtml, sType){
			var PageNav = wcm.RightView.PageNav;
			PageNav.setInfo({Num : wcm.Rights.size()});
			var startIndex = PageNav.getStartIndex();
			var endIndex = PageNav.getEndIndex();
			var aRightDef = wcm.RightDef.getItems(sType);
			var Rights = wcm.Rights;
			//为了统一，没有记录也显示一个框，提示没有记录
			if(endIndex<=startIndex&&this.mode != 'edit'){
				aHtml.push(
					'<tr id=\"grid_NoObjectFound\">',
						'<td class=\"no_object_found\" colspan=\"'+(aRightDef.length+3)+'\">'+(wcm.LANG.AUTH_29||'不好意思, 没有找到符合条件的对象!')+'</td>',
					'</tr>'
				);
				return aHtml;
			}else if(endIndex<=startIndex&&this.mode == 'edit'){
				aHtml.push(
					'<tr id=\"grid_NoObjectFound\">',
						'<td class=\"no_object_found\" colspan=\"'+(aRightDef.length+4)+'\">'+(wcm.LANG.AUTH_29||'不好意思, 没有找到符合条件的对象!')+'</td>',
					'</tr>'
				);
				return aHtml;
			}
			for (var i = startIndex; i < endIndex; i++){
				var right = Rights.getAt(i);

				//init fixed columns of body.
				rowClass = "grid_row " + (((i+1)%2)==1 ? "grid_row_odd" : "grid_row_even");
				aHtml.push(
					'<TR class="', rowClass, '">',
						'<TD align=center>', (i+1),
						this.mode == 'edit'
						?
						'<input type="checkbox" _type="selectRow" index="' + i + '"/>'
						:
						'',
						'</TD>',
						'<TD align=center>', right.getTypeHTML(), '</TD>',
						'<TD class="colName" align=left',						
						' style="padding-left:5px;"',
						' title="', right.getAttribute("Name"), '"',
						'>', right.getAttribute("Name"), '</TD>'
				);

				//init dynamic columns of body.
				for (var j = 0; j < aRightDef.length; j++){	
					var item = aRightDef[j];
					nRightIndex = item["Index"];
					if(this.mode == 'edit'){
						aHtml.push(
							'<TD align="center" title="', item["Desc"],'">',
								'<input type="checkbox"',
								' index=', i,
								' name="', right.getKey(), '"',
								' _type="selectCell"',
								' value="', nRightIndex, '"',
								right.hasRight(nRightIndex) ? " checked " : ""
						);
						aHtml.push('></TD>');
					}else{
						aHtml.push(
							'<TD align="center" title="', item["Desc"], '">',
								'<div class="',
								right.hasRight(nRightIndex) ? 'icon_hasright' : 'icon_noright',
							'">&#160;</div></TD>'
						);
					}
				}
				if(this.mode == 'edit'){
					aHtml.push(
						'<TD align=center>',
							'<div title="'+(wcm.LANG.AUTH_17||'除当前设置信息')+'" class="icon_delete_row"',
							' _type="removeRow"',
							' index=', i,
						'>&#160;</div></TD>'
					);
				}
				aHtml.push('</TR>');		
			}
			return aHtml;
		},
	
		refresh : function(right){
			//重现载入,刷新所有
			if(right == null) {
				reload();
				return;
			}

			//刷新一行
			var aCbx = document.getElementsByName(right.getKey());
			for (var i = 0; i < aCbx.length; i++){
				aCbx[i].checked = right.hasRight(aCbx[i].value);
			}
		},

		bindEvents : function(){
			if(this.bindedEvent) return;
			this.bindedEvent = true;
			Event.observe('tabContentTd', 'click', this.dispatchEvent, false);
		},

		dispatchEvent : function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var type = srcElement.getAttribute("_type");
			if(!type || !wcm.RightView[type]) return;
			wcm.RightView[type](srcElement, event);			
		},

		selectCol : function(srcElement){
			var cellIndex = srcElement.cellIndex;
			var bChecked = false;
			var rows = findElement(srcElement, 'table').rows;

			//judge need checked whether or not
			for (var i = 1; i < rows.length; i++){
				var cell = rows[i].cells[cellIndex];
				if(!cell)continue;
				var cbx = cell.getElementsByTagName("input")[0];
				if(!cbx.checked){
					bChecked = true;
					break;
				}
			}

			//
			for (var i = 1; i < rows.length; i++){
				var cell = rows[i].cells[cellIndex];
				if(!cell)continue;
				var cbx = cell.getElementsByTagName("input")[0];
				this.execSelectCell(cbx, bChecked);
			}

			this.refresh();
		},


		selectAll : function(srcElement){
			//get all the checkbox.
			var oTable = findElement(srcElement, 'table');
			var aCbx = oTable.getElementsByTagName("input");
			var bChecked = false;
			//judge need checked whether or not
			for (var i = 0; i < aCbx.length; i++){
				if(aCbx[i].getAttribute("_type") != 'selectCell'){
					continue;
				}
				if(!aCbx[i].checked){
					bChecked = true;
					break;
				}
			}

			//exec selected
			for (var i = 0; i < aCbx.length; i++){
				if(aCbx[i].getAttribute("_type") != 'selectCell'){
					continue;
				}
				this.execSelectCell(aCbx[i], bChecked);
			}

			this.refresh();
			
			//对行首的checkbox做相应的变化
			var aCbxFor = document.getElementsByTagName("input");
			for(var i = 0; i < aCbxFor.length; i++){
				if(aCbxFor[i].getAttribute("_type") != 'selectRow'){
					continue;
				}
				if(!aCbxFor[i].checked){
					if(bChecked){
						aCbxFor[i].checked = true;
					}
				}else{//由于每次都会重新加载，这个时候这列都会没有选上，所以下面的没有用，但为了表示这个意思就加上了
					aCbxFor[i].checked = false;
				}
				
			}

		},

		selectRow : function(srcElement){
			//find the tr element.
			var tempNode = findElement(srcElement, 'tr');
			if(!tempNode) return;

			//get the all checkboxs.
			var aCbx = tempNode.getElementsByTagName("input");

			var hasRight = srcElement.checked;
			var bChanged = false;
			for (var i = 1; i < aCbx.length; i++){
				bChanged = this.execSelectCell(aCbx[i], hasRight) || bChanged;
			}

			if(bChanged){
				var index = srcElement.getAttribute("index");
				var right = wcm.Rights.getAt(index);
				this.refresh(right);
			}
		},

		selectCell : function(srcElement){
			var hasRight = srcElement.checked;
			if(this.execSelectCell(srcElement, hasRight)){
				var index = srcElement.getAttribute("index");	
				var right = wcm.Rights.getAt(index);
				this.refresh(right);
			}			
		},

		execSelectCell : function(srcElement, hasRight){
			var index = srcElement.getAttribute("index");	
			var right = wcm.Rights.getAt(index);
			var rightIndex = srcElement.value;	
			return right.setRight(rightIndex, hasRight);
		},

		removeRow : function(srcElement){
			var index = srcElement.getAttribute("index");
			wcm.Rights.removeAt(index);
			this.refresh();
		},
		
		sort : function(srcElement, event){
			Event.stop(event);
			if(!this.order){
				this.order = -1;
			}
			var order = this.order = -this.order;
			wcm.Rights.sort(function(oRight1, oRight2){
				var oRight1 = oRight1 || null;
				var oRight2 = oRight2 || null;

				if (oRight1 == null && oRight2 == null) return 0;
				if (oRight1 == null) return -order;
				if (oRight2 == null) return order;
				
				var result= oRight2.getAttribute("OprType") - oRight1.getAttribute("OprType");
				if (result != 0) return result > 0 ? order : -order;
				
				var sName1 = oRight1.getAttribute("Name");
				var sName2 = oRight2.getAttribute("Name");
				if (sName1 > sName2) return order;
				if (sName1 < sName2) return -order;
				
				return 0;
			});
			this.refresh();
		}
	});


	/*
	*@class wcm.RightView.PageNav
	*/
	Ext.apply(wcm.RightView.PageNav, {
		info : {
			PageSize : screen.width > 1024 ? 15 :10,
			CurrPageIndex : 1,
			PageCount : 10,
			Num : 100
		},
		setInfo : function(_info){
			var info = this.info;
			Ext.apply(info, _info || {});
			var num = _info["Num"];
			if(num != undefined){
				info.PageCount = Math.ceil(num / info.PageSize);
			}

			//keep the CurrPage valid
			if(info.CurrPageIndex > info.PageCount){
				info.CurrPageIndex = 1;
			}
		},
		getInfo : function(){
			return this.info;			
		},
		getStartIndex : function(){
			var info = this.getInfo();
			return info.PageSize * (info.CurrPageIndex - 1);
		},
		getEndIndex : function(){
			var info = this.getInfo();
			var startIndex = this.getStartIndex();
			return Math.min(startIndex + info.PageSize, info.Num);
		}
	});

	/*
	*@override the PageContext.PageNav
	*/
	Ext.apply(PageContext.PageNav, {
		go : function(_iPage, _maxPage){
			_iPage = _iPage <= 0 ? 1 : (_iPage > _maxPage ? _maxPage : _iPage);
			wcm.RightView.PageNav.setInfo({"CurrPageIndex" : _iPage});
			reload();
		}
	});
})();

/*
*-----------------------------------button actions start----------------
*/
function getArguments(type){
	return [wcm.Rights.getAttributes(type, "OprId"), wcm.Rights.getAttributes(type, "OprType")];
}

function getOperatorSelectURL(_nType){
	var sUrl = "";
	switch(_nType){
		case TYPE_WCMOBJ_USER:
			sUrl = "auth/user_select_index.jsp";
			break;
		case TYPE_WCMOBJ_GROUP:
			sUrl = "auth/group_select_index.jsp";
			break;
		case TYPE_WCMOBJ_ROLE:
			sUrl = "auth/role_select_index.jsp";
			break;
		default:
			sUrl = "auth/user_select_index.jsp";
			break;
	}
	return WCMConstants.WCM6_PATH + sUrl;
}

function addOperator(_nType, sReturnValue){
	if(sReturnValue == null || sReturnValue.length == 0)return;

	var arDeleteIndex = wcm.Rights.getIndexs(_nType);
	var arDeleteId = wcm.Rights.getAttributes(_nType, "OprId");

	var arSelectId = sReturnValue[0];
	var arSelectName = sReturnValue[1]; 

	//遍历删除
	for(var i = arDeleteId.length - 1; i >= 0; i--){
		var bRemove = true;
		for(var j = 0; j < arSelectId.length;j++){
			if(arDeleteId[i] == arSelectId[j]){
				arSelectId[j] = -1;
				bRemove = false;
			}
		}
		if(bRemove) wcm.Rights.removeAt(arDeleteIndex[i]);
	}
	
	//遍历添加
	for(var i = 0; i < arSelectId.length; i++){
		if (arSelectId[i] == -1) continue;
		wcm.Rights.add({
			"OprType" : _nType,
			"OprId" : arSelectId[i],
			"Name" : arSelectName[i],
			"RightValue" : "0",
			"RightId" : 0
		});
	}

	//刷新页面	
	reload();
}

function addUser(){
	var type = TYPE_WCMOBJ_USER;
	FloatPanel.open({
		src : getOperatorSelectURL(type) + "?TransferName=1&FilterRight=0",
		title : wcm.LANG.AUTH_USER_SELECT || '用户选择',
		dialogArguments : getArguments(type),
		callback : addOperator.bind(window, type)
	});
}

function addGroup(){
	var type = TYPE_WCMOBJ_GROUP;
	FloatPanel.open({
		src : getOperatorSelectURL(type) + "?TransferName=1&GroupIds=" + wcm.Rights.getAttributes(type, "OprId") + "&TreeType=1&FilterRight=0",
		title : wcm.LANG.AUTH_GROUP_SELECT || '用户组选择',
		dialogArguments : getArguments(type),
		callback : addOperator.bind(window, type)
	});
}

function addRole(){
	var type = TYPE_WCMOBJ_ROLE;
	FloatPanel.open({
		src : getOperatorSelectURL(type) + "?TransferName=1&RoleIds=" + wcm.Rights.getAttributes(type, "OprId") + "&FilterRight=0",
		title : wcm.LANG.AUTH_ROLE_SELECT || '角色选择',
		dialogArguments : getArguments(type),
		callback : addOperator.bind(window, type)
	});
}

function removeAll(){
	if(wcm.Rights.size() <= 0){
		return;
	}
	var PageNav = wcm.RightView.PageNav;
	var info = PageNav.getInfo();
	Ext.Msg.confirm((wcm.LANG.AUTH_1 || "确认删除")
		+ (info.PageCount > 1 ? (wcm.LANG.AUTH_2 || "本页") : "")
		+ (wcm.LANG.AUTH_3 || "所有权限设置?"), function(){
		var startIndex = PageNav.getStartIndex();
		var endIndex = PageNav.getEndIndex();
		for (var i = endIndex - 1; i >= startIndex; i--){
			wcm.Rights.removeAt(i);
		}

		//刷新页面
		reload();
	});
}
/*
*-----------------------------------button actions end----------------
*/

//监听权限是否变化
Ext.apply(wcm.Rights, {
	onChange : function(){
		m_bRightChanged = true;
	}
});


/*
*-----------------------------------save action start----------------
*/
function onSave(){
	var Rights = wcm.Rights;
	if(m_nObjType == OBJ_DOCUMENT && Rights.size() != 0){//当前有对象
		var found = false;
		for (var i = 0, length = Rights.size(); i < length; i++){
			var right = Rights.getAt(i);
			if(right.getAttribute("OprType") == TYPE_WCMOBJ_USER
					&& right.getAttribute("OprId") == loginUserId){
				found = true;
				break;
			}
		}
		if(!found){
			var msg = wcm.LANG.AUTH_4 || "您没有添加当前登录用户，会造成当前登录用户对当前设置文档的权限丢失。\n";
			msg += (wcm.LANG.AUTH_5 || "需要立刻添加当前用户并设置其权限吗？");
			if(confirm(msg)){
				Rights.add({
					"OprType" : TYPE_WCMOBJ_USER,
					"OprId" : loginUserId,
					"Name" : loginUserName,
					"RightValue" : "111111111111111111111111111111111111111111111111111111111111111",
					"RightId" : 0
				});

				//刷新页面	
				reload();
				return;
			}
		}
	}
	var msg = wcm.LANG.AUTH_6 || "确定保存当前对权限的修改?";
	
	if(m_bHasChildren){
		selectImpartMode();
	}else{
		Ext.Msg.confirm(msg, {ok : postRights2Server});
	}
}

var m_nImpartMode = 0;
function selectImpartMode(){
	m_nImpartMode = 0;
	wcm.CrashBoarder.get('DIALOG_SELECT_IMPART_MODE').show({
		src : WCMConstants.WCM6_PATH + 'auth/impart_mode_select.html',
		title : wcm.LANG.AUTH_7 || '子栏目权限传递模式',
		width : '360px',
		height : '170px',
		callback : function(params){
			m_nImpartMode = params.ImpartMode;
			postRights2Server();
		}
	});
}

var tempInt = 0;
function postRights2Server(){
	tempInt = 0;
	window._changeAndSave_ = true;
	var frmAction = document.frmAction;
	if(frmAction == null){
		CTRSAction_alert(wcm.LANG.AUTH_8 || "没有定义");
	}
	//ProcessBar.start();
	frmAction.RightsXML.value = wcm.Rights.toXmlInfo();
	
	var objIdArray = getParameter("ObjId").split(",");
	for(var i = 0; i < objIdArray.length; i++){
		var postData = {
			ImpartModes : m_nImpartMode,
			ObjType : $F('ObjType'),
			ObjId : objIdArray[i],
			ResetChildrenRights : $F('ResetChildrenRights'),
			RightsXML : $F('RightsXML')
		};
		new Ajax.Request(WCMConstants.WCM6_PATH + "auth/right_set_dowith.jsp", {
			method:'post', 
			parameters:$toQueryStr(postData), 
			onSuccess : success.bind(window, postData, objIdArray),
			on500 : on500,
			onFailure:(objIdArray.length <= 1 ? Ext.emptyFn : failure),
			contentType:'application/x-www-form-urlencoded'
		});
	}
}

function success(postData, objIdArray, originalRequest){
	m_bRightChanged = false;
	tempInt++;
	if(!needRefresh && originalRequest.responseText.trim() == "true"){
			needRefresh = true;
	}
	if(tempInt == objIdArray.length){
		setTimeout(function(){
			if(OBJ_DOCUMENT == m_nObjType){
				window.close();
			}else if(needRefresh){
				//$nav().doAfterModifyFocusNode(true, $nav().refreshMain);
				var sObjType = null;
				if(m_nObjType==OBJ_WEBSITE){
					sObjType = WCMConstants.OBJ_TYPE_WEBSITE;
				}else if(m_nObjType==OBJ_CHANNEL){
					sObjType = WCMConstants.OBJ_TYPE_CHANNEL;
				}
				CMSObj.createFrom({objId:m_nObjId,objType:sObjType}, null).afteredit();
			}else if($('toggleModeId')){
				if(document.all){ 
					$('toggleModeId').click();
				}else{  //for ff
					var evt=document.createEvent("MouseEvents");
					evt.initEvent("click",true,true);
					$('toggleModeId').dispatchEvent(evt);
				}
			}
		}, 100);
	}
}

function failure(){
	//ProcessBar.next();
	setTimeout(function(){
		//ProcessBar.exit();
		Ext.Msg.$timeAlert(String.format(wcm.LANG.AUTH_16 || "修改失败,已经成功修改了{0}个",tempInt), 3);
	}, 100);	
}

function on500(transport, json){
	//ProcessBar.exit();
	var textDoc = transport.responseText.stripScripts();
	$('errorInfo').innerHTML = textDoc;
	Ext.Msg.fault({
		code		: $('errorNumber').innerText || '',
		message		: $('errorMessage').innerText || '',
		detail		: $('msgForCopy').innerText || ''
	}, wcm.LANG.AUTH_9 || '与服务器交互时出现错误', function(){
		/*
		$nav().refreshSiteType('0', null, function(){
			getFirstHTMLChild($nav().$("r_0")).click();
		});
		*/
	});
}
/*
*-----------------------------------save action end----------------
*/


function reload(){
	wcm.RightView.render();
	initTabStatus();
}


/*
*-----------------------------------toggle mode set start----------------
*/
function getToggleHTML(mode){
	return [
		'<div onclick="toggleMode(this);" id="toggleModeId"',
		' class="', mode == 'edit' ? 'icon_edit' : 'icon_view', '"',
		' title="', 
		mode == 'edit' 
		? 
		wcm.LANG.AUTH_10 || '当前为修改模式，&#13;单击转为查看模式' 
		:
		wcm.LANG.AUTH_11 || '当前为查看模式，&#13;单击转为修改模式', 
		'"',
		'></div>'
	].join("");
}

function toggleMode(imgObj){
	var mode = wcm.RightView.getMode();
	if(mode == 'edit'){//改为查看模式
		//如果做过修改，提示用户保存修改
		if(m_bRightChanged){
			var sMsg = wcm.LANG.AUTH_12 || "保存本次修改吗?";
			Ext.Msg.confirm(sMsg, {
				ok : function(){
					postRights2Server();
				},
				cancel : function(){
					window.location.reload();
				}
			});
			return;
		}
		mode = 'view';
		Element.hide('btnBox');
	}else{//改为编辑模式
		mode = 'edit';
		Element.show('btnBox');
	}
	wcm.RightView.setMode(mode);
	Element.update('toggleModeBox', getToggleHTML(mode));
	//设置tab需要重新载入
	m_oLoaded = {};
	reload();
}
/*
*-----------------------------------toggle mode set end----------------
*/

TRSSimpleTab.openItem = function(_elItemA, _nIndex){
	CTRSSimpleTab_openItem.apply(this, arguments);
	wcm.RightView.setTabIndex(_nIndex);		
	reload();
}

//init tab is visible or hidden
function initTabStatus(){
	var isVirtual = getParameter("IsVirtual");
	if(Ext.isTrue(isVirtual)){
		var channelType = getParameter("channelType");
		//由于只使用检索条件的栏目会变成虚栏目,但这3类权限设置都要有，所以不能这样return，否则都不会显示了
		//if(channelType == "" || channelType == 0) return; 
		var tableObj = document.getElementById("id_TRSSimpleTab").childNodes[0];
		if(!tableObj)return;
		if(channelType != "" && channelType != 0){
			tableArray = tableObj.getElementsByTagName("table");
			tableArray[m_aRightTypeIds["template"]].parentNode.parentNode.style.display = 'none';
		}
	}
	Element.removeClassName('tabTd', 'hideChild');
}

//set the width of footer.
function initFooterSize(){
	var footer = parent.$('south_tabs');
	if(footer){
		var width = 50;
		if(parent.Ext && parent.Ext.isStrict) width = 52;
		Event.observe(window, 'resize', function(){
			footer.style.width = (document.body.offsetWidth - width) + "px";
		});
		footer.style.width = (document.body.offsetWidth - width) + "px";
	}
}

Event.observe(window, 'load', function(){
	//judge whether or not appending the toggleModeBox
	var rightValue = getParameter("RightValue");
	if((m_nObjType == OBJ_WEBSITE 
			&& wcm.AuthServer.checkRight(rightValue, WEBSITE_SET_RIGHT))
			|| (m_nObjType == OBJ_CHANNEL 
				&& wcm.AuthServer.checkRight(rightValue, CHANNEL_SET_RIGHT))){
		Element.update('toggleModeBox', getToggleHTML(wcm.RightView.getMode()));
	}
	
	//document set right page.
	if(m_nObjType == OBJ_DOCUMENT 
			&& getParameter('mode') != 'view'){
		wcm.RightView.setMode('edit');
		Element.hide('toggleModeBox');
		Element.hide('tabTd');
		Element.show('btnBox');

		if(m_sObjIds.indexOf(",") >= 0){//设置多篇文档权限时，权限初始化为零
			var inputArray =  $('tabContentTd').getElementsByTagName('input');
			for(i = 0; i < inputArray.length; i++){
				if(inputArray[i].getAttribute("type") == 'checkbox'){
					inputArray[i].checked = false;
				}
			}
			Element.update('hostInfoTd', wcm.LANG.AUTH_15 || '重新设置多篇文档的权限');
		}
	}

	initFooterSize();
	m_bRightChanged = false;

	reload();
});
Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.getAttribute("name")=="valid"){
		if(document.all){ 
			dom.firstChild.fireEvent("onclick");
		}else{  //for ff
			var evt=document.createEvent("MouseEvents");
			evt.initEvent("click",true,true);
			dom.firstChild.dispatchEvent(evt);
		}
	}
})
Event.observe(window, 'beforeunload', function(){
	// 离开页面之前，如果做了权限修改，需要给用户提示保存修改的权限 by ffx @2011-2-12
	if(m_bRightChanged){
		// 这里需要注意，使用系统的confirm可以阻止页面的加载，但是Ext.Msg.confirm却不能实现
		if(confirm(wcm.LANG.AUTH_12 || "保存本次修改吗?")) {
			postRights2Server();
			return false;
		}
	}

	try{
		if(window.opener && window._changeAndSave_){
			window.opener.$MsgCenter.$main().afteredit();
			/*
			var topWin = window.opener.top.actualTop || window.opener.top;
			topWin.$MessageCenter.getMain().PageContext.refresh(topWin.location_search.replace("?", ''));
			*/
		}
	}catch(error){
		//兼容来自52页面的使用，需要定义 refreshMe 函数
		try{
			if(window.opener && window.opener.refreshMe) {
				window.opener.refreshMe();
			}
		}catch(err){
			//TODO
			alert(err.message);
		}
	}
});