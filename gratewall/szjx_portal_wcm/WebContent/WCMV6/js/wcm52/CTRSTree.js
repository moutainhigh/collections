/** 
 * @fileoverview 
 * 此文件定义了CTRSTree对象，完成树形结构的构造<BR>
 *  Copyright: 		www.trs.com.cn<BR>
 *  Company: 		TRS Info. Ltd.<BR>
 *  Author:			CH<BR>
 *  Created:		2004-09-24 08:38<BR>
 *  Vesion:			1.0<BR>
 *  Last EditTime:	2004-11-24/2004-11-24<BR>
 *	Update Logs:<BR>
 *		CH@2004-09-24 Created File<BR>
 *	Note:<BR>
 *		<BR>
 *	Depends:<BR>
 *		CTRSTree_res_default.js<BR>
 *	Examples:<BR>
 *		../test/CTRSTree_test.html<BR>
 * @author CH cao.hui@trs.com.cn
 * @version 1.o
 */
/**
 * CTRSTree构造函数
 * @class CTRSTree类
 * @constructor
 * @param {String} _sInitCheckValues 初始化选择型树形结构的ID序列(可以不传入)
 * @param {Object} _oTRSTreeRes TRSTree资源对象，默认为TREE_ICON_RES
 * @param {String} _sTarget 树中结点点击操作的Target
 * @param {String} _sDisabledIds 禁用的ID序列
 * @return CWCMAction的一个实例
 */
function CTRSTree(_sInitCheckValues, _oTRSTreeRes, _sTarget, _sDisabledIds){
//Define CTRSTree's Properties
	/** @private */
	this.arRootTRSItems	= [];
	/** @private */
	this.hTRSItems		= {};	
	/** @private */
	this.oTRSTreeRes	= _oTRSTreeRes || TREE_ICON_RES;
	/** @private */
	this.sTarget		= _sTarget || this.oTRSTreeRes['target'];
	this.nType			= TRSTREE_TYPE_NORMAL;
	/** @private */
	this.arDisabledId   = new Array();
	if(_sDisabledIds) this.arDisabledId = _sDisabledIds.split(",");

	/** @private */
	this.oSelectedItem	= null;

	/** @private */
	this.sId			= m_arTRSTrees.length;
	m_arTRSTrees[this.sId] = this;	

	this.setInitCheckValues = CTRSTree_setInitCheckValues;	
	this.setInitCheckValues(_sInitCheckValues);

//Define CTRSTree's Methods
	/** @private */
	this.onMouseOver	= function(_sItemId){
		this.hTRSItems[_sItemId].updateWindowStatus();
	}

	/** @private */
	this.onMouseOut		= function(_sItemId){
		this.hTRSItems[_sItemId].updateWindowStatus(true);
	}

	/** @private */
	this.select = function (_sItemId) { return this.hTRSItems[_sItemId].select(); };

	this.addItem			= TRSTree_addItem;
	this.addRootItem		= TRSTree_addRootItem;
	this.toString			= TRSTree_toString;
	this.draw				= TRSTree_draw;
	this.toHTML				= TRSTree_toHTML;
	this.open				= TRSTree_open;
	this.openRoot			= CTRSTree_openRoot;
	this.openAll			= CTRSTree_openAll;
	this.onClickCheckBox	= TRSTree_onClickCheckBox;
	this.addCheckValue		= TRSTree_addCheckValue;
	this.removeCheckValue	= TRSTree_removeCheckValue;
	//wenyh@20060821	the CTRSTree_sCheckValue init value may be null.	
	this.getCheckValue		= function(){return (CTRSTree_sCheckValue==null)?"":CTRSTree_sCheckValue.replace(new RegExp(CHECK_VALUE_DELIM, "g"), ",");}
	this.onClickRadio		= TRSTree_onClickRadio;
	this.getRadioValue		= function(){return CTRSTree_sRadioValue;}	
	this.locate				= TRSTree_locate;
	this.reload				= TRSTree_reload;
	
//Init This CTRSTree Info	
}

/**
 * 给指定的父节点添加一个子节点
 * @param {String} _sName 节点显示名称
 * @param {String} _sId	节点ID（必须唯一）
 * @param {String} _sToolTip 节点提示信息
 * @param {String} _sHref 节点点击后的操作（可以是一个链接也可以是一个JavaScript）,
 *			例如：document_list.jsp或者onClickChannel(13)
 * @param {String} _sParentId 指定的父节点
 * @param {boolean} _bHasChildren 当前指定节点是否包含子节点（可以不指定，由程序自动维护）
 *			如果强行指定具有子节点，但是没有给当前节点追加子节点，那么系统会自动从服务器载入
 */
function TRSTree_addItem(_sName, _sId, _sToolTip, _sHref, _sParentId, _bHasChildren, _oWCMObj){
	//1.Valid Paramerters
	var oNewItem = this.hTRSItems[_sId];
	if(oNewItem != null){
		//TODO
		//CTRSAction_alert("The Node Item Has Exist!\n" + oNewItem);
		//return oNewItem;
	}
	var oParentItem = null;
	if(_sParentId != null && _sParentId.length>0){
		oParentItem = this.hTRSItems[_sParentId];
		if(oParentItem == null){
			CTRSAction_alert("The Parent Item Has Not Existed!\nParent Id:[" + _sParentId + "]");
			return null;
		}
	}

	//2.Add
	oNewItem = new TRSTreeItem(_sName, _sId, _sToolTip, _sHref, oParentItem, _bHasChildren, false, this, _oWCMObj);
	//3.Init Checked 
	if((this.nType == TRSTREE_TYPE_CHECKBOX || this.nType == TRSTREE_TYPE_RADIOBOX) 
		&& this.bInitCheck){
		var sTemp = CHECK_VALUE_DELIM + CTRSTree_sCheckValue + CHECK_VALUE_DELIM;
		oNewItem.checked = (sTemp.indexOf(CHECK_VALUE_DELIM + _sId + CHECK_VALUE_DELIM)>=0) && (!oNewItem.beDisabledItem(oNewItem.sId));;
	}
	this.hTRSItems[_sId] = oNewItem;
	return oNewItem;
}

/**
 * 给指定的树添加一个根节点
 * @param {String} _sName 节点显示名称
 * @param {String} _sId	节点ID（必须唯一）
 * @param {String} _sToolTip 节点提示信息
 * @param {String} _sHref 节点点击后的操作（可以是一个链接也可以是一个JavaScript）,
 *			例如：document_list.jsp或者onClickChannel(13)
 * @param {boolean} _bHasChildren 当前指定节点是否包含子节点（可以不指定，由程序自动维护）
 *			如果强行指定具有子节点，但是没有给当前节点追加子节点，那么系统会自动从服务器载入
 */
function TRSTree_addRootItem(_sName, _sId, _sToolTip, _sHref, _bHasChildren, _oWCMObj){
	//1.Valid Paramerters
	var oNewItem = this.hTRSItems[_sId];
	if(oNewItem != null){
		//TODO
		//CTRSAction_alert("The Root Item Has Existed!\n" + oNewItem);
		//return oNewItem;
	}

	//2.Add
	oNewItem = new TRSTreeItem(_sName, _sId, _sToolTip, _sHref, null, _bHasChildren, true, this, _oWCMObj);
	this.arRootTRSItems[this.arRootTRSItems.length] = oNewItem;		
	this.hTRSItems[_sId] = oNewItem;
	return oNewItem;
}

/**
 * 在当前位置输出当前的树形结构的HTML代码
 */
function TRSTree_draw(_sDisplayElementId, _nTreeWidth){
	var oTimer = new CDebugTimer();
	oTimer.start();
	if(_sDisplayElementId){
		document.getElementById(_sDisplayElementId).innerHTML = "<div style=\"overflow:auto;height:100%;width:"+_nTreeWidth+";\">"
			+ this.toHTML()
			+ "</div>";

	}else{
		document.write(this.toHTML());
	}
	oTimer.stop();
	if(TRSTREE_ISDEUG)
		CTRSAction_alert("Root write HTML:["+oTimer.getTime()+"]ms");

	
	oTimer.start();	
	if(this.OpenAllNode){		
		this.openAll();
	}else{
		this.openRoot();
	}
	oTimer.stop();
	if(TRSTREE_ISDEUG)
		CTRSAction_alert("Root open:["+oTimer.getTime()+"]ms");

}

/**
 * 设置初始化选择框的值序列
 * @param {String} _sInitCheckValues 初始化的值序列
 */
function CTRSTree_setInitCheckValues(_sInitCheckValues){	
	this.sInitCheckValues =  _sInitCheckValues;//初始化Check Box的Values,当Type为TRSTREE_TYPE_CHECKBOX有效
	
	if(_sInitCheckValues && _sInitCheckValues.length>0){
		if(CTRSTree_sCheckValue == null)
			CTRSTree_sCheckValue	= _sInitCheckValues.replace(new RegExp(",", "g"), CHECK_VALUE_DELIM);
		if(CTRSTree_sRadioValue == null)
			CTRSTree_sRadioValue	= _sInitCheckValues;
		
		this.bInitCheck = true;
		this.sInitCheckValues = "," + _sInitCheckValues + ",";
	}
}

//定位到指定的节点上，指定的节点为按照路径构造的ID序列
/**
 * 定位到指定的位置
 * @param {String} _sPathIds 定位的路径（按照路径构造的ID序列）
 * @param {boolean} _bLocateParent 是否定位到父节点上
 * @param {boolean} _bReloadParent 是否再次载入父节点的子节点
 */
function TRSTree_locate(_sPathIds, _bLocateParent, _bReloadParent){
	if(_sPathIds == null  || _sPathIds.length==0){
		return;
	}
	var arTemp = _sPathIds.split("#");
	if(!arTemp[0])
		return;
	var arPath = arTemp[0].split(",");
	var nParentCount = arPath.length-1, oParentItem = null;
	for(var i=0; i<nParentCount; i++){
		var sItemId = arPath[i];
		var oItem = this.hTRSItems[sItemId];
		
		if(oItem == null){//载入指定节点的父节点
			if(i==0){
				CTRSAction_alert("指定的根节点["+sItemId+"]不存在！");
				return;
			}
			for(var nParentIndex=(i-1); nParentIndex<nParentCount; nParentIndex++){
				var sParentId	= arPath[nParentIndex];
				oParentItem = this.hTRSItems[sParentId];	
				if(oParentItem == null){
					CTRSAction_alert("指定的父节点["+i+"]不存在！");
					return;
				}
				oParentItem.display();
			}
			break;
		}
		
		oItem.display();
		oParentItem = oItem;
	}

	var sLocateItemId = null;
	if(_bLocateParent){
		oParentItem.reload();
		oParentItem.select();
		sLocateItemId = oParentItem.sId;
	}else{	
		sLocateItemId = arPath[nParentCount];
		var oLocateItem = this.hTRSItems[sLocateItemId];
		if(oLocateItem == null || _bReloadParent)
			oParentItem.reload();

		oLocateItem = this.hTRSItems[sLocateItemId];
		if(oLocateItem == null){//子对象不存在定位到父对象上
			oLocateItem = oParentItem;
		}
		oLocateItem.select();
	}
	
	var sHref = window.location.href;
	var nPose = sHref.indexOf("#");
	if(nPose>0){
		sHref = sHref.substring(0, nPose);
	}
	window.location.href = sHref + "#" + this.sId + "_" + sLocateItemId;
}




//Record Radio Value
/** @private **/
var CTRSTree_sRadioValue = "";
/** @private **/
//var CTRSTree_sCheckValue = null;
var CTRSTree_sCheckValue = null;
//wenyh@20060820,CTRSTree_sCheckValue需要使用null为初始值,否则会出现原地已选上的值丢失的情况
//var CTRSTree_sCheckValue = "";//wenyh@20060814,修改初始值为空串

/** @private **/
function CDebugTimer(){
	this.lStartTime = 0;
	this.lStopTime	= 0;

	this.start = function(){
		this.lStartTime = (new Date()).getTime();
		
	}

	this.stop = function(){
		this.lStopTime = (new Date()).getTime();
		
	}

	this.getTime = function(){
		return parseInt(this.lStopTime - this.lStartTime);
	}
}

/** @private **/
function CTRSTree_openRoot(){
	for(var i=0; i<this.arRootTRSItems.length; i++){		
		this.arRootTRSItems[i].open();
	}
}

function CTRSTree_openAll(){
	for(var i=0; i<this.arRootTRSItems.length; i++){		
		this.arRootTRSItems[i].openAll();
	}
}

/** @private **/
function TRSTree_open(_sItemId){
	var oTRSItem = this.hTRSItems[_sItemId];
	oTRSItem.open();
}


/** @private **/
function TRSTree_toHTML(){
	//1.预先载入图片
	var o_icone = new Image(), o_iconl = new Image();
	o_icone.src = this.oTRSTreeRes['icon_e'];
	o_iconl.src = this.oTRSTreeRes['icon_l'];
	this.oTRSTreeRes['im_e'] = o_icone;
	this.oTRSTreeRes['im_l'] = o_iconl;
	for (var i = 0; i < 64; i++){
		if (this.oTRSTreeRes['icon_' + i]) {
			var o_icon = new Image();
			this.oTRSTreeRes['im_' + i] = o_icon;
			o_icon.src = this.oTRSTreeRes['icon_' + i];
		}
	}

	
	//2.Generate Tree HTML
	var oTimer = new CDebugTimer();
	var sHTML = "";;
	for(var i=0; i<this.arRootTRSItems.length; i++){		
		oTimer.start();
		sHTML += this.arRootTRSItems[i].toHTML();
		oTimer.stop();
		if(TRSTREE_ISDEUG)
			CTRSAction_alert("Root toHTML:["+oTimer.getTime()+"]ms");
	}

	
	return sHTML;
}

/** @private **/
function TRSTree_beDisabledItem(_sId){//CTRSAction_alert(this.oTRSTree + "-->" +this.oTRSTree.arDisabledId);
	var nLength = this.oTRSTree.arDisabledId.length || 0;
	//var nLength = 0;
	if(nLength <= 0) return false;
	for(var i=0; i<nLength; i++){
		if(_sId == this.oTRSTree.arDisabledId[i]) return true;
	}
	return false;
}


/** @private **/
function TRSTree_toString(){
	var sInfo = "The CTRSTree Information:\n"
				+"Root Element Size:["+(this.arRootTRSItems.length)+"]\n";					
	return sInfo;
}


/** @private **/
function TRSTreeItem_init(){
	if(this.oParentItem != null){
		this.oRootItem	= this.oParentItem.oRootItem;
		this.oParentItem.addChild(this);
		this.nDepth		= this.oParentItem.nDepth+1;
		this.nIndex		= this.oParentItem.arChildren.length-1;
	}
	if(this.bRoot){
		this.nDepth		= 0;
		this.oRootItem	= this;
		this.nIndex		= this.oTRSTree.arRootTRSItems.length-1;
		this.oParentItem=this;
	}
}

/** @private **/
function TRSTreeItem_select(b_deselect){
	if (!b_deselect) {
		var oOldSelectedItem = this.oTRSTree.oSelectedItem;
		this.oTRSTree.oSelectedItem = this;
		if (oOldSelectedItem) oOldSelectedItem.select(true);
	}

	var o_iicon = document.images['i_img' + this.oTRSTree.sId + '_' + this.sId];
	if (o_iicon){
		var sNewImageSrc = this.getIcon();
		if(sNewImageSrc != o_iicon.src){
			//CTRSAction_alert(sNewImageSrc);
			//CTRSAction_alert(o_iicon.src);
			//o_iicon.src = sNewImageSrc;
		}
	}
	//get_element('i_txt' + this.oTRSTree.sId + '_' + this.sId).style.fontWeight = b_deselect ? 'normal' : 'bold';
	var sElementId = this.oTRSTree.sId + '_' + this.sId;
	var oElement = get_element(sElementId);
	if(oElement != null)
		oElement.className = b_deselect ? 'tree_normal' : 'tree_selected';

	this.updateWindowStatus();
	
	//return Boolean(this.sHref);
	return true;
}

/** @private **/
function TRSTreeItem_getPublicEventHTML(){
	return "onmouseover='m_arTRSTrees[" + this.oTRSTree.sId + "].onMouseOver(\"" + this.sId + "\");' "
		+"onmouseout='m_arTRSTrees[" + this.oTRSTree.sId + "].onMouseOut(\"" + this.sId + "\");'";
}

/** @private **/
function TRSTreeItem_getNodeFlagImgName(){
	return "j_img" + this.oTRSTree.sId + "_" + this.sId ;
}
/** @private **/
function TRSTreeItem_getNodeImgHTML(){
	if(this.nDepth<=0){
		//return "";
	}

	var arOffset = [], oTRSItemTemp = this.oParentItem;
	for (var i = this.nDepth; i > 0; i--) {
		arOffset[i] = '<img src="' + this.oTRSTree.oTRSTreeRes[oTRSItemTemp.isLast() ? 'icon_e' : 'icon_l'] + '" '
					+'border="0" align="absbottom">';
		oTRSItemTemp = oTRSItemTemp.oParentItem;
	}

	if(!this.bHasChildren){//元素节点
		return arOffset.join('')
			+ "<img src=\"" + this.getIcon(true) + "\" border=0 align=absbottom>";
	}

	return arOffset.join("") 
		+ "<a href='###' onclick='m_arTRSTrees[" + this.oTRSTree.sId + "].open(\"" + this.sId + "\");return false;' "
		+ this.getPublicEventHTML()
		+" >"
		+"<img src=\"" + this.getIcon(true) + "\" border=0 align=absbottom name=\""+this.getNodeFlagImgName()+"\"></a>";		
}

/** @private **/
function TRSTreeItem_getItemDivId(){
	return 'i_div' + this.oTRSTree.sId + '_' + this.sId;
}

/** @private **/
function TRSTreeItem_getItemDiv(){
	var oItemDiv = get_element(this.getItemDivId());
	if (!oItemDiv){
		CTRSAction_alert("Error Exception:Not Found ["+this.getItemDivId()+"]Div")
		return null;
	}
	return oItemDiv;
}

/** @private **/
function TRSTreeItem_getItemSpanId(){
	return 'i_Span' + this.oTRSTree.sId + '_' + this.sId;
}

/** @private **/
function TRSTreeItem_getItemSpan(){
	var oItemSpan = get_element(this.getItemSpanId());
	if (!oItemSpan){
		CTRSAction_alert("Error Exception:Not Found ["+this.getItemSpanId()+"]Span")
		return null;
	}
	return oItemSpan;
}

/** @private **/
function TRSTreeItem_getIcon (_bJunction) {
	//处理自定义节点图片的情况
	if(!_bJunction){
		var sSrc;
		var iconType = null;
		if(this.iconType){
			iconType = this.iconType;
		}else if(this.oWCMObj){
			iconType = this.oWCMObj.getProperty("CHNLTYPE");
		}

		if(iconType != null){
			if(!this.bHasChildren)
				sSrc = this.oTRSTree.oTRSTreeRes["icon_n_"+iconType];
			else
				sSrc = this.oTRSTree.oTRSTreeRes["icon_n_"+iconType+"_folder"];
		} else {
			sSrc = this.oTRSTree.oTRSTreeRes["icon_n_"+this.sId];
		}
		if(sSrc != null)return sSrc;
	}

	//根据规则返回图片
	var sIconId = 'im_' + ((this.nDepth ? 0 : 32) + (this.bHasChildren ? 16 : 0) 
			+ (this.bHasChildren && this.bOpened ? 8 : 0) 
			+ (!_bJunction && this.oTRSTree.oSelectedItem == this ? 4 : 0) + (_bJunction ? 2 : 0) 
			+ (_bJunction && this.isLast() ? 1 : 0));
	//CTRSAction_alert(this.oTRSTree.oTRSTreeRes[sIconId].src);
	return this.oTRSTree.oTRSTreeRes[sIconId].src;
}

/** @private **/
function TRSTreeItem_updateWindowStatus(_bClear){
	window.setTimeout('window.status="' + (_bClear ? '' : this.sToolTip)+ '  ItemID:['+this.sId+']"', 10);
}

/**
 * 根据指定的父节点ID动态载入子节点的列表（抽象方法）
 *（动态载入子节点的树形结构需要实现此方法）
 * @param {TRSTree} 树形结构对象（CTRSTree的实例）
 * @param {String} 当前父节点的ID
 * @param {TRSTreeItem} 当前父节点对象
 */
function TRSTreeItem_loadChildren(_oTRSTree, _sParentId, _oCurrItem){
	//_oTRSTree.addItem("第一个子节点名称", "第一个子节点ID", "第一个子节点提示", "第一个子节点链接", _sParentId, false);
	//_oTRSTree.addItem("第二个子节点名称", "第二个子节点ID", "第二个子节点名称", "第二个子节点链接", _sParentId, false);
	CTRSAction_alert("function TRSTreeItem_loadChildren(_oTRSTree, _sParentId, _oCurrItem){...} Not Implemented!");	
}

function TRSTreeItem_openAll(){
	this.open();

	if(this.arChildren == null)return;

	for (var i = 0; i < this.arChildren.length; i++)
			this.arChildren[i].openAll();
}

/**
 * @private
 */
function TRSTreeItem_open() {
	if(!this.bHasChildren)
		return;
	//Get Item Div
	var oItemDiv = this.getItemDiv();
	if (oItemDiv == null){
		return;
	}
	
	//Set Item Div HTML and Display
	if(this.bHasChildren && (this.arChildren==null || this.arChildren.length==0))
	{//Need Loading....
		var arOffset = [], oTRSItemTemp = this;
		for (var i = this.nDepth; i > 0; i--) {
			arOffset[i] = '<img src="' + this.oTRSTree.oTRSTreeRes[oTRSItemTemp.isLast() ? 'icon_e' : 'icon_l'] + '" '
						+'border="0" align="absbottom">';
			oTRSItemTemp = oTRSItemTemp.oParentItem;
		}

		oItemDiv.innerHTML = arOffset.join('') + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+"<a class=\"t" + this.oTRSTree.sId + "i\" onclick=\"return false;\"><font color=blue>....</font></a>";
		oItemDiv.style.display = (this.bOpened ? 'none' : 'block');
		this.bLoading = true;
		TRSTreeItem_loadChildren(this.oTRSTree, this.sId, this);
		
		//Not Load Children
		if((this.arChildren==null || this.arChildren.length==0)){
			
			this.bHasChildren = false;
			this.arChildren = null;
			var oItemSpan = this.getItemSpan();			
			if(oItemSpan == null){
				return;
			}
			
			if(!oItemSpan.nodeName && oItemSpan.length){
				oItemSpan = oItemSpan[0];
			}
			
			oItemSpan.innerHTML = this.toHTML(true);
			return;
		}
	}
	
	if (!oItemDiv.innerHTML || (this.bLoading && this.arChildren)) {
		this.bLoading = false;
		var arChildrenHTML = [];
		for (var i = 0; i < this.arChildren.length; i++)
			arChildrenHTML[i]= this.arChildren[i].toHTML();
		oItemDiv.innerHTML = arChildrenHTML.join('');
	}
	oItemDiv.style.display = (this.bOpened ? 'none' : 'block');
	
	//Upate Item Statuses
	this.bOpened = !this.bOpened;
	var o_jicon = document.images['j_img' + this.oTRSTree.sId + '_' + this.sId],
		o_iicon = document.images['i_img' + this.oTRSTree.sId + '_' + this.sId];
	if (o_jicon) o_jicon.src = this.getIcon(true);
	else{
		var oDiv = document.getElementById("div_" + this.oTRSTree.sId + '_' + this.sId);
		if(oDiv)
			oDiv.innerHTML = this.getNodeImgHTML();
	}
	if (o_iicon) o_iicon.src = this.getIcon();
	
	//Upate Window Statuses
	this.updateWindowStatus();
}

/**
 * @private
 */
function TRSTreeItem_display(){
	if(this.bOpened)
		return;
	this.open();
}

/**
 * @private
 */
function TRSTreeItem_reload(){
	//insure get data from server
	this.bHasChildren = true;

	//set this.arChildren to null
	if(this.arChildren != null){
		this.arChildren.splice(0, this.arChildren.length);
		this.arChildren = null;
		this.bOpened = false;
	}

	this.open();
}




/**
 * @private
 */
function TRSTreeItem_addChild(_TRSTreeItem){
	if(this.arChildren == null)
		this.arChildren = new Array();

	this.arChildren[this.arChildren.length] = _TRSTreeItem;
	this.bHasChildren	= true;
}

/**
 * @private
 */
function TRSTreeItem_toString(){
	var sInfo = "Name:["+this.sName+"] Id:["+this.sId+"]  Href:["+this.sHref+"] \n"
				+"ParentId:["+(this.oParentItem?this.oParentItem.sId:"")+"] "
				+"Children:["+(this.arChildren?this.arChildren.length:0)+"]\n"
				+(this.bRoot?"":"Not ")+"Is Root Node!\n";
	return sInfo;
}

/**
 * @private
 */
function TRSTreeItem_isLast(){
	if(this.oParentItem == this)return false;

	return this.nIndex == this.oParentItem.arChildren.length - 1
}

function TRSTree_GetSelfDivId(_oTRSTreeItem){
	if(_oTRSTreeItem == null)
		return;

	return 'div_' + _oTRSTreeItem.oTRSTree.sId + '_' + _oTRSTreeItem.sId;
}


/**
 * @private
 */
function TRSTreeItem_toHTML(_bExcludeSpan){
	var sHTML = _bExcludeSpan?'':'<span id="'+this.getItemSpanId()+'" title="'+this.sToolTip+'">';

	sHTML += '<table cellpadding="0" cellspacing="0" border="0"><tr><span ';
	if(this.bRoot)
		sHTML += ' Name="spRootNode" ObjId=' + this.sId.substring(1) + ' ';
	else
		sHTML += ' Name="spSubNode" ObjId=' + this.sId + ' ';
	if(this.nMenuIndex != null)
		sHTML += ' MenuIndex="' + this.nMenuIndex + '" ';
	if(this.sRightValue != null && this.sRightValue.length > 0)
		sHTML += ' RightValue="' + this.sRightValue + '" ';
	if(this.oParentItem){
		//var sParentId = this.oParentItem.sId;
		//WSW @ 2005-6-16 修改bug，需要把可能的整形值转成字符串类型，否则可能在下一步的substring出错。
		var sParentId = this.oParentItem.sId + "";
		if(sParentId.substring(0, 1) == "S")
			sHTML += ' ParentType=103 '+'ParentId='+sParentId.substring(1);
		else
			sHTML += ' ParentType=101 '+'ParentId='+sParentId;
	}else{
		CTRSAction_alert('why parent is null??');
	}
	
	var sSelfDivId = TRSTree_GetSelfDivId(this);

	sHTML += ' ><td nowrap><div style="display:inline" name="' + sSelfDivId + '" id="' + sSelfDivId + '">'
		+ this.getNodeImgHTML()
		+'</div><img src="' + this.getIcon() + '" border="0" align="absbottom" name="i_img' + this.oTRSTree.sId + '_' + this.sId + '"'
		+' class="t' + this.oTRSTree.sId + 'im">'
		+ this.getTypeHTML()
		+ ((this.sHref==null || this.sHref=="")?"<a onclick=\"return false;\">":
			"<a name=\""+this.oTRSTree.sId+"_"+this.sId+"\" ID=\""+this.sId+"\" href='" + this.sHref + "' target='" + this.oTRSTree.sTarget+ "' "
		+" onclick='return m_arTRSTrees[" + this.oTRSTree.sId + "].select(\"" + this.sId + "\");'"
		+" onmousedown='return m_arTRSTrees[" + this.oTRSTree.sId + "].select(\"" + this.sId + "\");'"
		+" ondblclick='m_arTRSTrees[" + this.oTRSTree.sId + "].open(\"" + this.sId + "\");' "
		+ this.getPublicEventHTML()		
		+' class="t0i" id="' + this.oTRSTree.sId + '_' + this.sId + '">')
		+ this.sName + '</a>'
		+'</td></span></tr></table>'
		+ '<div id="' + this.getItemDivId() + '" style="display:'+(this.oTRSTree.OpenAllNode?'inline':'none')+';"></div>';
		//TODO Children的优化

	sHTML += _bExcludeSpan ? '':"</span>";		

	//prompt("test", sHTML);
//		+ (this.bHasChildren ? '<div id="' + this.getItemDivId() + '" style="display:none"></div>' : '')
//		+"</span>";

	return sHTML;
}


/**
 * @private
 */
function TRSTreeItem_getTypeHTML(){
	if(this.oTRSTree.nType == TRSTREE_TYPE_NORMAL || this.nDepth <= 0)
		return "";

	if(this.oTRSTree.nType == TRSTREE_TYPE_CHECKBOX){
		if(this.checked){
			this.oTRSTree.addCheckValue(this.sId);
		}
		var sHtml = null;
		if(this.IsMgrNode){
			sHtml = "<input type=checkbox name=\"TRSTreeNode\" value=\""+this.sId+"\" "
				+(this.checked?"checked":"")
				+" onclick=\"TRSTree_checkAllChildren('"+this.oTRSTree.sId+"', '"+this.getItemDivId()+"', this.checked);\"";
		}else{			
			sHtml = "<input type=checkbox name=\"TRSTreeNode\" value=\""+this.sId+"\" "
				+(this.checked?"checked":"")
				+" onclick=\"TRSTree_onClickCheckBox('"+this.oTRSTree.sId+"', this.value, this.checked);\"";				
		}
				
		if(this.beDisabledItem(this.sId))
			sHtml += " disabled ";
		sHtml += ">";
		return sHtml;
	}

	if(this.oTRSTree.nType == TRSTREE_TYPE_RADIOBOX){
		if(this.IsMgrNode){
			return "";
		}

		if(this.checked){
			CTRSTree_sRadioValue = this.sId;
		}
		var sHtml = "<input type=radio name=\"TRSTreeNode\" value=\""+this.sId+"\" " 
				+(this.checked?"checked":"")
				+" onclick='m_arTRSTrees[" + this.oTRSTree.sId + "].onClickRadio(this);'";
		if(this.beDisabledItem(this.sId))
			sHtml += " disabled ";
		sHtml += ">";
		return sHtml;
	}
}

var CHECK_VALUE_DELIM = "~~";
/**
 * @private
 */
function TRSTree_addCheckValue(_sValue){
	var sCheckValue = CTRSTree_sCheckValue;	
	if(sCheckValue == null || sCheckValue.length == 0 || sCheckValue=="NULL" || sCheckValue=="null"){
		CTRSTree_sCheckValue = _sValue;
		return true;
	}
	
	sCheckValue = CHECK_VALUE_DELIM + sCheckValue + CHECK_VALUE_DELIM;
	var nPose = sCheckValue.indexOf(CHECK_VALUE_DELIM + _sValue + CHECK_VALUE_DELIM);
	if(nPose>=0)
		return true;

	CTRSTree_sCheckValue += CHECK_VALUE_DELIM + _sValue;
}

/**
 * @private
 */
function TRSTree_removeCheckValue(_sValue){
	var sCheckValue = CTRSTree_sCheckValue;	
	if(sCheckValue == null || sCheckValue.length == 0){
		return true;
	}
	
	sCheckValue = CHECK_VALUE_DELIM + sCheckValue + CHECK_VALUE_DELIM;
	var nPose = sCheckValue.indexOf(CHECK_VALUE_DELIM + _sValue + CHECK_VALUE_DELIM);
	if(nPose==0){
		CTRSTree_sCheckValue = CTRSTree_sCheckValue.substring((_sValue + CHECK_VALUE_DELIM).length);
	}else if(nPose>0){
		CTRSTree_sCheckValue = CTRSTree_sCheckValue.substring(0, nPose-CHECK_VALUE_DELIM.length) 
			+ CTRSTree_sCheckValue.substring(nPose + _sValue.length);
	}		
}

function TRSTree_checkAllChildren(_sTreeId, _sItemDivId, _bChecked){
	var oItemDiv = document.getElementById(_sItemDivId);
	if(oItemDiv == null){
		return;
	}

	var pElements = oItemDiv.getElementsByTagName("input");
	for(var i=0; i<pElements.length; i++){
		if(pElements[i].type == "checkbox" && !pElements[i].disabled){
			pElements[i].checked = _bChecked;
			TRSTree_onClickCheckBox(_sTreeId, pElements[i].value, _bChecked)
		}
	}
}

/**
 * @private
 */
function TRSTree_onClickCheckBox(_sTreeId, _sValue, _bChecked){
	var oTreeTemp = m_arTRSTrees[_sTreeId];
	oTreeTemp.hTRSItems[_sValue].checked = _bChecked;

	if(_bChecked){
		oTreeTemp.addCheckValue(_sValue);
	}else{
		oTreeTemp.removeCheckValue(_sValue);
	}
	
	return true;
}



/**
 * @private
 */
function TRSTree_onClickRadio(_elRadio){
	this.hTRSItems[_elRadio.value].checked = _elRadio.checked;
	if(_elRadio.checked){		
		CTRSTree_sRadioValue = _elRadio.value;		
	}else{
		if(CTRSTree_sRadioValue == _elRadio.value){
			CTRSTree_sRadioValue = "";			
		}
	}
	return true;
}

get_element = document.all ?
	function (s_id) { return document.all(s_id) } :
	function (s_id) { return document.getElementById(s_id) };

/**
 * @private
 */
function TRSTreeItem(_sName, _sId, _sToolTip, _sHref, _oParentItem, _bHasChildren, _bRoot, _oTRSTree, _oWCMObj){
//Define Properties
	this.sName			= _sName		|| "";
	this.sId			= _sId			|| "";
	this.sToolTip		= _sToolTip		|| this.sName;
	this.sHref			= _sHref		|| "";
	this.nDepth			= -1;

	this.oParentItem	= _oParentItem	|| null;
	this.arChildren		= null;
	this.oRootItem		= null;

	this.oWCMObj		= _oWCMObj || null;
	this.nMenuIndex		= (_oWCMObj)?_oWCMObj.getProperty("MENUINDEX"):"0";
	this.sRightValue =(_oWCMObj)?_oWCMObj.getProperty("RIGHTVALUE"):"";

	this.bRoot			= _bRoot		|| false;
	this.bHasChildren	= _bHasChildren || false;

	this.oTRSTree		= _oTRSTree;
	this.bOpened		= false;

	this.checked			= false;

//Define Methods
	this.addChild			= TRSTreeItem_addChild;
	this.open				= TRSTreeItem_open;
	this.openAll			= TRSTreeItem_openAll;
	this.display			= TRSTreeItem_display;
	this.select				= TRSTreeItem_select;

	this.isLast				= TRSTreeItem_isLast;
	this.getItemSpanId		= TRSTreeItem_getItemSpanId;
	this.getItemSpan		= TRSTreeItem_getItemSpan;
	this.getItemDivId		= TRSTreeItem_getItemDivId;
	this.getItemDiv			= TRSTreeItem_getItemDiv;
	this.getIcon			= TRSTreeItem_getIcon;
	this.getNodeFlagImgName	= TRSTreeItem_getNodeFlagImgName;
	this.getNodeImgHTML		= TRSTreeItem_getNodeImgHTML;
	this.getPublicEventHTML = TRSTreeItem_getPublicEventHTML;
	this.getTypeHTML		= TRSTreeItem_getTypeHTML;

	this.toString			= TRSTreeItem_toString;	
	this.toHTML				= TRSTreeItem_toHTML;

	this.updateWindowStatus	= TRSTreeItem_updateWindowStatus;
	this.init				= TRSTreeItem_init;

	this.reload			= TRSTreeItem_reload;

	this.beDisabledItem = TRSTree_beDisabledItem;
//Init This Item and Parent Item Info
	this.init();
}



function TRSTree_reload(_sItemId){
	var sItemId = _sItemId || "";
	if(sItemId == null || sItemId.length <= 0)
		return;

	var oItem = this.hTRSItems[sItemId];
	if(oItem == null)
		return;

	oItem.reload();
}

/** 
 * 树形结构的调试开关 
 * @type boolean
 */
var TRSTREE_ISDEUG			= false;
/** @const {int} 树形结构的类型：普通型（0） */
var TRSTREE_TYPE_NORMAL		= 0;
/** @const {int} 树形结构的类型：多选型（1） */
var TRSTREE_TYPE_CHECKBOX	= 1;
/** @const {int} 树形结构的类型：单选型（2） */
var TRSTREE_TYPE_RADIOBOX	= 2;
/** 
 * 记录当前页面所有的TRSTree对象
 * @type Array
 */
var m_arTRSTrees = [];
