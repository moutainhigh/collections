Ext.ns('wcm.ObjectMember', 'wcm.ObjectMembers', 'wcm.ViewOper', 'wcm.ViewOper.PageNav');

(function(){

	/*
	*@class wcm.ObjectMember
	*/
	wcm.ObjectMember = function(_item){
		var item = {};
		for(var sKey in _item){
			item[sKey.toUpperCase()] = _item[sKey];
		}
		this.item = item;
	};

	Ext.apply(wcm.ObjectMember.prototype, {
		ignores : {'NAME':true},
		isVisible :function(){
			var isVisible = this.getAttribute('Visible');
			if(isVisible == "1"){
				return true;
			}
			return false;
		},
		canOperate :function(){
			var canOperate = this.getAttribute('DoOperation');
			if(canOperate == "1"){
				return true;
			}
			return false;
		},
		setObjMember : function(_sValue,_bChecked){
			var sCheck;
			if(_bChecked){
				sCheck = "1";
			}else{
				sCheck = "0";
			}
			switch(_sValue){
				case 'View':
					if(this.canOperate()){
						if(_bChecked){
							return true;
						}else{
							this.setAttribute("DoOperation","0");
							return this.setAttribute("Visible","0") != "0";
						}
					}else{
						return this.setAttribute("Visible",sCheck) != sCheck;
					}
				case 'Oper':
					if(!this.isVisible()){//不可访问
						if(!_bChecked){//取消可操作，直接返回是否改变可操作属性
							return this.setAttribute("DoOperation",sCheck) != sCheck;
						}else{//设置可操作，同时设置可访问和可操作
							this.setAttribute("Visible","1");
							return this.setAttribute("DoOperation","1") != "1";
						}
					}else{
						return this.setAttribute("DoOperation",sCheck) != sCheck;
					}
			}
		},
		setAttribute : function(sKey, sValue){
			sKey = sKey.toUpperCase();
			var sOldValue = this.item[sKey];
			this.item[sKey] = sValue;
			if(sOldValue != sValue){
				wcm.ObjectMembers.onChange();
			}
			return sOldValue;
		},
		getAttribute : function(sKey){
			return this.item[sKey.toUpperCase()];
		},
		getKey : function(){
			return this.getAttribute("MemberType") + "_" + this.getAttribute("MemberId");
		},
		toXmlInfo : function(){
			var aResult = ["<XWCMOBJECTMEMBER><PROPERTIES>\n"];
			var item = this.item;
			var ignores = this.ignores;
			for (var sKey in item){
				if(ignores[sKey]) continue;
				aResult.push("\t<", sKey, ">", item[sKey], "</", sKey, ">\n");
			}
			aResult.push("</PROPERTIES></XWCMOBJECTMEMBER>");
			
			return aResult.join("");
		},
		getTypeHTML : function(){
			var nOprType = this.getAttribute("MemberType");
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
	*@class wcm.ObjectMembers
	*/
	Ext.apply(wcm.ObjectMembers, {
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
				var objectMember = items[i];
				if(objectMember.getAttribute("MemberType") == _nType){
					aResult.push(objectMember.getAttribute(_sAttr));
				}
			}
			return aResult;
		},
		getIndexs : function(_nType){
			var items = this.items;
			var aResult = [];
			for (var i = 0; i < items.length; i++){
				var objectMember = items[i];
				if(objectMember.getAttribute("MemberType") == _nType){
					aResult.push(i);
				}
			}
			return aResult;
		},
		add : function(item){
			this.onChange();
			this.items.push(new wcm.ObjectMember(item));
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
			aResult.push("<XWCMOBJECTMEMBERS>\n");
			for (var i = 0, length = this.size(); i < length; i++){
				aResult.push("\t");
				aResult.push(this.getAt(i).toXmlInfo());
				aResult.push("\n");
			}
			aResult.push("</XWCMOBJECTMEMBERS>");
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
	*@class wcm.ViewOper
	*/
	
	Ext.apply(wcm.ViewOper, {
		mode : 'view',//edit
		order : null,
		
		setMode : function(mode){
			this.mode = mode;
		},

		getMode : function(){
			return this.mode;
		},

		render : function(){
			Element.update("content", this.html());
			this.bindEvents();
			PageContext.drawNavigator(wcm.ViewOper.PageNav.getInfo());
		},

		html : function(){
			var aHtml = [];
			//init fixed columns of head.
			aHtml.push('<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6" class="rightbox">');
			this.initHead(aHtml);
			this.initBody(aHtml);
			aHtml.push('</TABLE>');
			return aHtml.join("");
		},

		initHead : function(aHtml){
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
			if(this.mode == 'edit'){
				aHtml.push(
					'<TD class="headdesc"',
					' title="', "可访问", '['+(wcm.LANG.AUTH_19||'点击后可以选择一列')+']"',
					' _type="selectCol"',	
					' style="cursor:pointer;"',
					'>', "可访问", '</TD>',
					'<TD class="headdesc"',
					' title="', "可操作", '['+(wcm.LANG.AUTH_19||'点击后可以选择一列')+']"',
					' _type="selectCol"',	
					' style="cursor:pointer;"',
					'>', "可操作", '</TD>'
				);
			}else{
				aHtml.push(
					'<TD class="headdesc"',
					' title="', "可访问", '"',
					'>', "可访问", '</TD>',
					'<TD class="headdesc"',
					' title="', "可操作", '"',
					'>', "可操作", '</TD>'
				);
			}
			if(this.mode == 'edit')
				aHtml.push('<TD style="width:50px;">'+(wcm.LANG.AUTH_18||'删除')+'</TD>');
			aHtml.push('</TR>');
			return aHtml;
		},

		initBody : function(aHtml){
			var PageNav = wcm.ViewOper.PageNav;
			PageNav.setInfo({Num : wcm.ObjectMembers.size()});
			var startIndex = PageNav.getStartIndex();
			var endIndex = PageNav.getEndIndex();
			var ObjectMembers = wcm.ObjectMembers;
			//为了统一，没有记录也显示一个框，提示没有记录
			if(endIndex<=startIndex&&this.mode != 'edit'){
				aHtml.push(
					'<tr id=\"grid_NoObjectFound\">',
						'<td class=\"no_object_found\" colspan=\"5\">'+(wcm.LANG.AUTH_29||'不好意思, 没有找到符合条件的对象!')+'</td>',
					'</tr>'
				);
				return aHtml;
			}else if(endIndex<=startIndex&&this.mode == 'edit'){
				aHtml.push(
					'<tr id=\"grid_NoObjectFound\">',
						'<td class=\"no_object_found\" colspan=\"6\">'+(wcm.LANG.AUTH_29||'不好意思, 没有找到符合条件的对象!')+'</td>',
					'</tr>'
				);
				return aHtml;
			}
			for (var i = startIndex; i < endIndex; i++){
				var objMember = ObjectMembers.getAt(i);

				//init fixed columns of body.
				rowClass = "grid_row " + (((i+1)%2)==1 ? "grid_row_odd" : "grid_row_even");
				aHtml.push(
					'<TR class="', rowClass, '">',
						'<TD align=center>', (i+1),
						this.mode == 'edit'? 
						
						'<input type="checkbox" _type="selectRow" index="' + i + '"/>'
						:
						'',
						'</TD>',
						'<TD align=center>', objMember.getTypeHTML(), '</TD>',
						'<TD class="colName" align=left',						
						' style="padding-left:5px;"',
						' title="', objMember.getAttribute("Name"), '"',
						'>', objMember.getAttribute("Name"), '</TD>'
				);

				//init dynamic columns of body.
				if(this.mode == 'edit'){
					aHtml.push(
						'<TD align="center" title="可访问">',
							'<input type="checkbox"',
							' index=', i,
							' name="', objMember.getKey(), '"',
							' _type="selectCell"',
							' value="View"',
							objMember.isVisible() ? " checked " : "",
						'></TD>',
						'<TD align="center" title="可操作">',
							'<input type="checkbox"',
							' index=', i,
							' name="', objMember.getKey(), '"',
							' _type="selectCell"',
							' value="Oper"',
							objMember.canOperate() ? " checked " : "",
						'></TD>',
						'<TD align=center>',
							'<div title="'+(wcm.LANG.AUTH_17||'删除当前设置信息')+'" class="icon_delete_row"',
							' _type="removeRow"',
							' index=', i,
						'>&#160;</div></TD>'
					);
				}else{
					aHtml.push(
						'<TD align="center" title="可访问">',
							'<div class="',
							objMember.isVisible() ? 'icon_hasright' : 'icon_noright',
						'">&#160;</div></TD>',
						'<TD align="center" title="可操作">',
							'<div class="',
							objMember.canOperate() ? 'icon_hasright' : 'icon_noright',
						'">&#160;</div></TD>'
					);
				}

				aHtml.push('</TR>');		
			}
			return aHtml;
		},
	
		refresh : function(objMember){
			//重新载入,刷新所有
			if(objMember == null) {
				reload();
				return;
			}

			//刷新一行
			var aCbx = document.getElementsByName(objMember.getKey());
			aCbx[0].checked = objMember.isVisible();
			aCbx[1].checked = objMember.canOperate();
		},

		bindEvents : function(){
			if(this.bindedEvent) return;
			this.bindedEvent = true;
			Event.observe('content', 'click', this.dispatchEvent, false);
		},

		dispatchEvent : function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var type = srcElement.getAttribute("_type");
			if(!type || !wcm.ViewOper[type]) return;
			wcm.ViewOper[type](srcElement, event);			
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
				// 兼容没有一行中没有input的情况
				if(!cbx)
					continue;

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

				// 兼容没有一行中没有input的情况
				if(!cbx)
					continue;

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

			var bChecked = srcElement.checked;
			var bChanged = false;
			for (var i = 1; i < aCbx.length; i++){
				bChanged = this.execSelectCell(aCbx[i], bChecked) || bChanged;
			}

			if(bChanged){
				var index = srcElement.getAttribute("index");
				var right = wcm.ObjectMembers.getAt(index);
				this.refresh(right);
			}
		},

		selectCell : function(srcElement){
			var bCheched = srcElement.checked;
			if(this.execSelectCell(srcElement, bCheched)){
				var index = srcElement.getAttribute("index");	
				var objMember = wcm.ObjectMembers.getAt(index);
				this.refresh(objMember);
			}			
		},

		execSelectCell : function(srcElement, bCheched){
			var index = srcElement.getAttribute("index");	
			var objectMember = wcm.ObjectMembers.getAt(index);
			var value = srcElement.value;
			return objectMember.setObjMember(value, bCheched);
		},

		removeRow : function(srcElement){
			var index = srcElement.getAttribute("index");
			wcm.ObjectMembers.removeAt(index);
			this.refresh();
		},
		
		sort : function(srcElement, event){
			Event.stop(event);
			if(!this.order){
				this.order = -1;
			}
			var order = this.order = -this.order;
			wcm.ObjectMembers.sort(function(oObjMember1, oObjMember2){
				var oObjMember1 = oObjMember1 || null;
				var oObjMember2 = oObjMember2 || null;

				if (oObjMember1 == null && oObjMember2 == null) return 0;
				if (oObjMember1 == null) return -order;
				if (oObjMember2 == null) return order;
				
				var result= oObjMember2.getAttribute("MemberType") - oObjMember1.getAttribute("MemberType");
				if (result != 0) return result > 0 ? order : -order;
				
				var sName1 = oObjMember1.getAttribute("Name");
				var sName2 = oObjMember2.getAttribute("Name");
				if (sName1 > sName2) return order;
				if (sName1 < sName2) return -order;
				
				return 0;
			});
			this.refresh();
		}
	});


	/*
	*@class wcm.ViewOper.PageNav
	*/
	Ext.apply(wcm.ViewOper.PageNav, {
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
			wcm.ViewOper.PageNav.setInfo({"CurrPageIndex" : _iPage});
			reload();
		}
	});
})();

/*
*-----------------------------------button actions start----------------
*/
function getArguments(type){
	return [wcm.ObjectMembers.getAttributes(type, "MemberId"), wcm.ObjectMembers.getAttributes(type, "MemberType")];
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
	//debugger
	if(sReturnValue == null || sReturnValue.length == 0)return;

	var arDeleteIndex = wcm.ObjectMembers.getIndexs(_nType);
	var arDeleteId = wcm.ObjectMembers.getAttributes(_nType, "MemberId");

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
		if(bRemove) wcm.ObjectMembers.removeAt(arDeleteIndex[i]);
	}
	
	//遍历添加
	for(var i = 0; i < arSelectId.length; i++){
		if (arSelectId[i] == -1) continue;
		wcm.ObjectMembers.add({
			'MemberType' : _nType,
			'MemberId' : arSelectId[i], 
			'Name' : arSelectName[i], 
			'Visible' : "0", 
			'DoOperation' : "0",
			'OBJECTMEMBERID':0
		});
	}

	//刷新页面	
	reload();
}

function addUser(){
	var type = TYPE_WCMOBJ_USER;
	FloatPanel.open({
		src : getOperatorSelectURL(type) + "?FilterRight=0&TransferName=1",
		title : wcm.LANG.AUTH_USER_SELECT || '用户选择',
		dialogArguments : getArguments(type),
		callback : addOperator.bind(window, type)
	});
}

function addGroup(){
	var type = TYPE_WCMOBJ_GROUP;
	FloatPanel.open({
		src : getOperatorSelectURL(type) + "?FilterRight=0&TransferName=1&GroupIds=" + wcm.ObjectMembers.getAttributes(type, "MemberId") + "&TreeType=1",
		title : wcm.LANG.AUTH_GROUP_SELECT || '用户组选择',
		dialogArguments : getArguments(type),
		callback : addOperator.bind(window, type)
	});
}

function addRole(){
	var type = TYPE_WCMOBJ_ROLE;
	FloatPanel.open({
		src : getOperatorSelectURL(type) + "?TransferName=1&RoleIds=" + wcm.ObjectMembers.getAttributes(type, "MemberId") + "&FilterRight=0",
		title : wcm.LANG.AUTH_ROLE_SELECT || '角色选择',
		dialogArguments : getArguments(type),
		callback : addOperator.bind(window, type)
	});
}

function removeAll(){
	if(wcm.ObjectMembers.size() <= 0){
		return;
	}
	var PageNav = wcm.ViewOper.PageNav;
	var info = PageNav.getInfo();
	Ext.Msg.confirm((wcm.LANG.AUTH_1 || "确认删除")
		+ (info.PageCount > 1 ? (wcm.LANG.AUTH_2 || "本页") : "")
		+ "所有设置?", function(){
		var startIndex = PageNav.getStartIndex();
		var endIndex = PageNav.getEndIndex();
		for (var i = endIndex - 1; i >= startIndex; i--){
			wcm.ObjectMembers.removeAt(i);
		}

		//刷新页面
		reload();
	});
}
/*
*-----------------------------------button actions end----------------
*/

//监听可访问可操作成员是否变化
Ext.apply(wcm.ObjectMembers, {
	onChange : function(){
		m_bChanged = true;
	}
});


/*
*-----------------------------------save action start----------------
*/
function onSave(){
	var ObjectMembers = wcm.ObjectMembers;
	var msg = "确定保存当前的修改?";
	
	if(m_bHasChildren){
		selectImpartMode();
	}else{
		Ext.Msg.confirm(msg, {ok : postObjs2Server});
	}
}

var m_nImpartMode = 0;
function selectImpartMode(){
	m_nImpartMode = 0;
	wcm.CrashBoarder.get('DIALOG_SELECT_IMPART_MODE').show({
		src : WCMConstants.WCM6_PATH + 'auth/impart_mode_select.html',
		title : '子栏目访问控制传递模式',
		width : '320px',
		height : '150px',
		callback : function(params){
			m_nImpartMode = params.ImpartMode;
			postObjs2Server();
		}
	});
}

function postObjs2Server(){
	tempInt = 0;
	window._changeAndSave_ = true;
	var frmAction = document.frmAction;
	if(frmAction == null){
		CTRSAction_alert(wcm.LANG.AUTH_8 || "没有定义");
	}
	//ProcessBar.start();
	frmAction.ObjectMembersXML.value = wcm.ObjectMembers.toXmlInfo();

	var postData = {
		ObjType : m_nObjType,
		ObjId : m_nObjId,
		ImpartModes : m_nImpartMode,
		ObjectMembersXML : frmAction.ObjectMembersXML.value
	}
	new Ajax.Request(WCMConstants.WCM6_PATH + "auth/view_oper_set_dowith.jsp",{
		method:'post',
		parameters:$toQueryStr(postData),
		onSuccess : success.bind(window, postData),
		contentType:'application/x-www-form-urlencoded'
	});
}

function success(postData, originalRequest){
	m_bChanged = false;
	if(!needRefresh && originalRequest.responseText.trim() == "true"){
			needRefresh = true;
	}
	setTimeout(function(){
		if(needRefresh){
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

/*
*-----------------------------------save action end----------------
*/


function reload(){
	wcm.ViewOper.render();
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
	var mode = wcm.ViewOper.getMode();
	if(mode == 'edit'){//改为查看模式
		//如果做过修改，提示用户保存修改
		if(m_bChanged){
			var sMsg = "保存本次修改吗?";
			Ext.Msg.confirm(sMsg, {
				ok : function(){
					postObjs2Server();
				},
				cancel : function(){
					m_bChanged = false;
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
	wcm.ViewOper.setMode(mode);
	Element.update('toggleModeBox', getToggleHTML(mode));

	reload();
}
/*
*-----------------------------------toggle mode set end----------------
*/

Event.observe(window, 'load', function(){
	//judge whether or not appending the toggleModeBox
	var rightValue = getParameter("RightValue");
	if((m_nObjType == OBJ_WEBSITE 
			&& wcm.AuthServer.checkRight(rightValue, WEBSITE_SETRIGHT))
			|| (m_nObjType == OBJ_CHANNEL 
				&& wcm.AuthServer.checkRight(rightValue, CHANNEL_SETRIGHT))){
		Element.update('toggleModeBox', getToggleHTML(wcm.ViewOper.getMode()));
	}
	m_bChanged = false;

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
});

//离开页面时，提示保存修改
Event.observe(window, 'beforeunload', function(){
	if(m_bChanged && confirm("保存本次修改吗？")){
		postObjs2Server();
	}
});

Event.observe(window, 'beforeunload', function(){
	try{
		if(window.opener && window._changeAndSave_){
			window.opener.$MsgCenter.$main().afteredit();
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