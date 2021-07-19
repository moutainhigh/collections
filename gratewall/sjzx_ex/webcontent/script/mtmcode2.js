

// 菜单序号,全局编号
var MTMNumber = 1;


// 图标
function MTMfloderIcon( xtype )
{
	
	this.xtype = xtype;
	this.imageDirectory = "/script/menu-images/";
	
	this.emptyIcon = "menu_pixel.gif";
	//this.floderClosed = "menu_folder_closed.gif";
	this.floderClosed = "menu_folder_closed2.gif";
	//this.floderOpen = "menu_folder_open.gif";
	this.floderOpen = "menu_folder_open2.gif";
	
	if( xtype == 'tree' || xtype == 'check' ){
		this.nodeIcon = "menu-icon2.gif";
		
		this.menuBar = "menu_bar.gif";
		this.treeMinus = "menu_tee_minus.gif";
		this.cornerMinus = "menu_corner_minus.gif";
		this.treePlus = "menu_tee_plus.gif";
		this.cornerPlus = "menu_corner_plus.gif";
		this.cornerLine = "menu_corner.gif";
		this.treeLine = "menu_tee.gif";
	}
	else{
		if( xtype == 'list' ){
		//this.nodeIcon = "menu-icon.gif";
			this.nodeIcon = "menu-icon2.gif";
		}
		else{
			this.nodeIcon = "listbar_node.gif";
		}
		
		this.menuBar = "menu_pixel.gif";
		this.treeMinus = "menu_pixel.gif";
		this.cornerMinus = "menu_pixel.gif";
		this.treePlus = "menu_pixel.gif";
		this.cornerPlus = "menu_pixel.gif";
		this.cornerLine = "menu_pixel.gif";
		this.treeLine = "menu_pixel.gif";
	}
	
	this.menuText = "长城软件";
	//this.rootIcon = "menu_new_root.gif";
	this.rootIcon = "menu_new_root2.gif";
	
	// 图标
	this.items = new Array();
	this.addIcon = icon_addIcon;
	this.getFullName = icon_getFullName;
}

function MTMIcon(iconfile, match, type) {
	this.file = iconfile;
	this.match = match;
	this.type = type;
}

// 图标
function icon_addIcon(item) {
	this.items[this.items.length] = item;
}

// 图标文件的路径
function icon_getFullName( fileName )
{
	if( fileName.charAt(0) == '/' ){
		if( fileName.indexOf(_browse.contextPath+'/') != 0 ){
			fileName = _browse.contextPath + fileName;
		}
	}
	else{
		fileName = _browse.contextPath + this.imageDirectory + fileName;
	}
	
	return fileName;
}


// 菜单项
function MTMenuItem(text, url, target, tooltip, icon, openIcon, id, isFloder)
{
	// 取target和url
	if( url == null || url == '' ){
		this.url = '';
		this.target = '';
	}
	else{
		this.url = url;
		this.target = (target == null) ? '' : target;
	}
	
	this.text = text;
	this.tooltip = tooltip == null ? text : tooltip;
	this.id = id;
	
	// 图标
	this.icon = icon ? icon : "";
	this.openIcon = openIcon ? openIcon : "";
	
	// 内部变量
	this.parentNode  = null;	// 上级节点,第一层节点 = menu
	this.expanded    = false;	// 是否展开标志
	
	// 子节点，可能通过AJAX接口从服务器下载
	this.submenu     = null;
	if( isFloder ){
		this.submenu = new MTFloder( this );
	}
	
	// 层次
	this.deep = 1;
	this.getDeep = item_getDeep;
	
	// 是否被选中:三种状态--true/false
	this.checked = false;
	this.prepareCheckbox = item_prepareCheckbox;
	this.getCheckFlag = item_getCheckFlag;
	this.setCheckFlag = item_setCheckFlag;

	this.number = MTMNumber++;
	
	
	// 方法
	this.getRootMenu = item_getRootMenu;
	this.addSubItem = item_addSubItem;
	this.lookupItem = item_lookupItem;
	this.isLast = item_isLastNode;
	this.isChild = item_isChild;
	this.setImage = item_setImage;
	this.fetchIcon = item_fetchIcon;
	this.prepareLineImage = item_prepareLineImage;
	this.processFloderNode = item_processFloderNode;
	
	// 不同的树，显示方式不一致
	this.prepareNodeImage = item_prepareNodeImage;
	this.displayItem = item_displayItem;
	this.makeLink = item_makeLink;
	
	// 设置节点的活动状态
	this.setActive = item_setActive;
	
	// 根据内部编号查找节点，内部使用
	this.lookupItemByNumber = item_lookupItemByNumber;
	
	// 通过回调函数处理所有子节点
	this.processNode = item_processNode;
	
	// 修改节点的显示方式：listbar 和 其它
	this.setMenuType = item_setMenuType;
	
	// 恢复节点的状态
	this.restoreStatus = item_restoreStatus;
	
	// 展开或缩进节点
	this.expand = item_expandFloder;
}

// 取根菜单信息
function item_getRootMenu()
{
	var p = this;
	while( p.parentNode != null ){
		p = p.parentNode;
	}
	
	return p;
}

// 取节点的层次
function item_getDeep()
{
	var r = this.getRootMenu();
	if( r.rootNode != r ){
		return this.deep - r.rootNode.deep;
	}
	else{
		return this.deep;
	}
}

// 增加菜单节点
function item_addSubItem( menuNode )
{
	if( this.submenu == null ){
		this.submenu = new MTFloder( this );
	}
	
	// 增加节点
	this.submenu.items.push( menuNode );
	menuNode.parentNode = this;
	menuNode.deep = this.deep + 1;
	
	var menu = this.getRootMenu();
	if( menu.xtype != 'listbar' ){
		var menuIcons = menu.menuIcons;
		if( this.icon == '' ){
			this.icon = menuIcons.floderClosed;
		}
		
		if( this.openIcon == '' ){
			this.openIcon = menuIcons.floderOpen;
		}
		
		// 设置目录节点的图标
		if( menuNode.submenu != null ){
			if( menuNode.icon == '' ){
				menuNode.icon = menuIcons.floderClosed;
			}
			
			if( menuNode.openIcon == '' ){
				menuNode.openIcon = menuIcons.floderOpen;
			}
		}
	}
	else{
		menuNode.prepareNodeImage = item_listbar_prepareNodeImage;
		menuNode.displayItem = item_listbar_displayItem;
		menuNode.makeLink = item_listbar_makeLink;
	}
	
	// 展开标志
	if( menuNode.expanded ){
		var pobj = this;
		while( pobj != null ){
			pobj.expanded = true;
			pobj = pobj.parentNode;
		}
	}
	
	return menuNode;
}

// 查找节点
function item_lookupItem( menuId )
{
	if( this.id == menuId ){
		return this;
	}
	
	if( this.submenu == null ){
		return null;
	}
	
	var ii;
	var item;
	for( ii=0; ii<this.submenu.items.length; ii++ ){
		item = this.submenu.items[ii].lookupItem( menuId );
		if( item != null ){
			return item;
		}
	}
	
	return null;
}

// 查找节点
function item_lookupItemByNumber( number )
{
	if( this.number == number ){
		return this;
	}
	
	if( this.submenu == null ){
		return null;
	}
	
	var ii;
	var item;
	for( ii=0; ii<this.submenu.items.length; ii++ ){
		item = this.submenu.items[ii].lookupItemByNumber( number );
		if( item != null ){
			return item;
		}
	}
	
	return null;
}


// 通过回调函数处理所有子节点
function item_processNode( callback )
{
	var pid = (this.parentNode.parentNode == null) ? null : this.parentNode.id;
	callback( this, pid );
	if( this.submenu != null ){
		for( var ii=0; ii<this.submenu.items.length; ii++ ){
			this.submenu.items[ii].processNode( callback );
		}
	}
}


// 判断节点是否是目录中最后的一个
function item_isLastNode()
{
	var	pnode = this.parentNode.submenu;
	return (pnode.items[pnode.items.length - 1] == this) ? true : false;
}

// 判断是否是当前节点的子节点
function item_isChild( node )
{
	if( this.id == node.id ){
		return true;
	}
	
	if( this.submenu == null || this.submenu.items.length == 0 ){
		return false;
	}
	
	var ii;
	for( ii=0; ii<this.submenu.items.length; ii++ ){
		if( this.submenu.items[ii].isChild(node) ){
			return true;
		}
	}
	
	return false;
}

// 修改目录的图标
// menu和list：显示图标、listbar：显示图片或文字
function item_setImage( )
{
	if( this.submenu == null ){
		return;
	}
	
	var menu = this.getRootMenu();
	var menuIcons = menu.menuIcons;
	if( this.expanded ){
		var img1 = (this.isLast()) ? menuIcons.cornerMinus : menuIcons.treeMinus;
		var	img2 = this.openIcon;
	}
	else{
		var	img1 = (this.isLast()) ? menuIcons.cornerPlus : menuIcons.treePlus;
		var	img2 = this.icon;
	}
	
	// 初始化时，同步处理
	img1 = menuIcons.getFullName( img1 );
	img2 = menuIcons.getFullName( img2 );
	var nodeid = this.number;
	var func = function(){ showImage( menu, img1, img2, nodeid ) };
	setTimeout( func, 5 );
}

function showImage( menu, img1, img2, id )
{
	var doc = menu.menuWindow.document;
	
	// 替换线条
	var	imgs = doc.getElementsByName( 'ti' + id );
	for( var ii=0; ii<imgs.length; ii++ ){
		if( menu.isChildElement(imgs[ii]) ){
			imgs[ii].src = img1;
			break;
		}
	}
	
	// 替换icon
	var	imgs = doc.getElementsByName( 'ri' + id );
	for( var ii=0; ii<imgs.length; ii++ ){
		if( menu.isChildElement(imgs[ii]) ){
			imgs[ii].src = img2;
			break;
		}
	}
}


// 取节点的图标名称
function item_fetchIcon( ) 
{
	var menuIcons = this.getRootMenu().menuIcons;
	var icons = menuIcons.items;
	for(var i = 0; i < icons.length; i++) {
		if((icons[i].type == 'any') && (this.url.indexOf(icons[i].match) != -1)) {
			return(icons[i].file);
		}
		else if((icons[i].type == 'pre') && (this.url.indexOf(icons[i].match) == 0)) {
			return(icons[i].file);
		}
		else if((icons[i].type == 'post') && (this.url.indexOf(icons[i].match) != -1)) {
			if((this.url.lastIndexOf(icons[i].match) + icons[i].match.length) == this.url.length) {
				return(icons[i].file);
			}
		}
	}
	
	return(menuIcons.nodeIcon);
}

// 生成check-box
function item_prepareCheckbox()
{
	if( this.getRootMenu().xtype != 'check' ){
		return "";
	}
	
	var	isrc = '<input type="checkbox" style="width:17px; height:17px;"';
	isrc += ' name="';
	isrc += this.getRootMenu().menuName;
	isrc += '_check" id="';
	isrc += this.id;
	isrc += '" onclick="';
	isrc += this.getRootMenu().menuName;
	isrc += '.processCheckbox()">';
	return isrc;
}

// 取check的标志：checked、uncheck、half(部分选择)
function item_getCheckFlag()
{
	var checkedNumber = 0;
	var uncheckNumber = 0;
	
	if( this.submenu != null && this.submenu.items.length > 0 ){
		for( var ii=0; ii<this.submenu.items.length; ii++ ){
			var flag = this.submenu.items[ii].getCheckFlag();
			if( flag == 'helf' ){
				return 'half';
			}
			
			if( flag == 'checked' ){
				if( uncheckNumber > 0 ){
					return 'half';
				}
				
				checkedNumber += 1;
			}
			else{
				if( checkedNumber > 0 ){
					return 'half';
				}
				
				uncheckNumber += 1;
			}
		}
	}
	else{
		if( this.checked ){
			checkedNumber += 1;
		}
		else{
			uncheckNumber += 1;
		}
	}
	
	if( checkedNumber > 0 ){
		return "checked";
		
	}
	else{
		return "uncheck";
	}
}

// 设置check-box的值
function item_setCheckFlag()
{
	if(this.getRootMenu().xtype != 'check' ){
		return;
	}
	
	// 查找节点
	var doc = this.getRootMenu().menuWindow.document;
	var flagBox = doc.getElementById( this.id );
	if( flagBox != null ){
		// 取选中状态
		var flag = this.getCheckFlag();
		
		if( flag == 'checked' ){
			flagBox.indeterminate = false;
			flagBox.checked = true;
		}
		else if( flag == 'uncheck' ){
			flagBox.indeterminate = false;
			flagBox.checked = false;
		}
		else{
			flagBox.indeterminate = true;
		}
	}
}

// 生成目录的图标
// menu：显示线条、其它不显示
function item_prepareLineImage()
{
	var rootMenu = this.getRootMenu();
	if( rootMenu.xtype == 'list' || rootMenu.xtype == 'listbar' ) return "";
	
	var img = null;
	var menuIcons = rootMenu.menuIcons;
	if(this.submenu) {
		if(this.expanded || rootMenu.expandFlag == false){
			img = (this.isLast()) ? menuIcons.cornerMinus : menuIcons.treeMinus;
		}
		else {
			img = (this.isLast()) ? menuIcons.cornerPlus : menuIcons.treePlus;
		}
	}
	else{
		img = (this.isLast()) ? menuIcons.cornerLine : menuIcons.treeLine;
	}
	
	if( img == null || img == "" ){
		return "";
	}
	
	var	isrc = '<img id="ti' + this.number + '" name="ti' + this.number + '"';
	
	// 是否需要扩展节点：包含子节点的目录节点
	if( this.submenu != null ){
		isrc = isrc + ' onclick="' + rootMenu.getMenuName() + '.processFloderNode()"';
	}
	
	isrc = isrc + ' src="' + menuIcons.getFullName(img) + '" width="18px" height="18px" align="left" border="0" vspace="0" hspace="0">';
	return isrc;
}

// 生成节点图标
// menu和list显示图标、listbar显示图片或文字；需要修改
function item_prepareNodeImage()
{
	if(this.submenu) {
		var img = (this.expanded) ? this.openIcon : this.icon;
		var iwidth = 'width="17px" height="17px"';
	}
	else{
		var img = (this.icon != "") ? this.icon : this.fetchIcon();
		var iwidth = 'width="17px" height="17px"';
	}
	
	if( img == null || img == '' ){
		return "";
	}
	
	var	isrc = '<img id="ri' + this.number + '" name="ri' + this.number + '" ' + iwidth;
	
	var menuIcons = this.getRootMenu().menuIcons;
	isrc = isrc + ' src="' + menuIcons.getFullName(img) + '" align="left" border="0" vspace="1" hspace="0">';
	return isrc;
}

function item_listbar_prepareNodeImage()
{
	if( this.submenu || this.getDeep() == 1 ){
		var img = (this.expanded) ? this.openIcon : this.icon;
	}
	else{
		var img = (this.icon != "") ? this.icon : this.fetchIcon();
	}
	
	if( img == null || img == '' ){
		return "";
	}
	
	var	isrc = '<img id="ri' + this.number + '" name="ri' + this.number + '" ';
	if( this.icon != '' ){
		isrc += 'alt="' + this.text + '"';
	}
	
	var menuIcons = this.getRootMenu().menuIcons;
	isrc = isrc + ' src="' + menuIcons.getFullName(img) + '" align="left" border="0" hspace="0">';
	
	return isrc;
}

// 连接
function item_makeLink( )
{
	var tempString = '';
	var rootMenu = this.getRootMenu();
	if( rootMenu.getSelectRowEventFlag() == false ){
		tempString = '<span style="cursor:hand"';
		
		// 提示信息
		if( this.tooltip ){
			tempString += ' title="' + this.tooltip + '" ';
		}
		
		tempString += '>';
	}
	
	return tempString;
}

// listbar的链接
function item_listbar_makeLink()
{
	var tempString = '';
	var rootMenu = this.getRootMenu();
	if( rootMenu.getSelectRowEventFlag() == false ){
		tempString = '<span style="cursor:hand"';
		
		// 提示信息
		if( this.tooltip ){
			tempString += ' title="' + this.tooltip + '" ';
		}
		
		// 宽度和高度
		if( this.getDeep() == 1 ){
			var ptop = (menu.rowHeight1 - 16)/2
			tempString += 'style="width:100%;height:100%;padding-top:' + ptop + 'px"'
		}
		
		tempString += '>';
	}
	
	return tempString;
}


// 处理目录节点，展开还是缩进节点
function item_processFloderNode( )
{	
	var rootMenu = this.getRootMenu();
	if( rootMenu.expandFlag == false ) return;
	
	// 修改展开标志
	this.expanded = (this.expanded) ? false : true;
	
	// 检查上级菜单是否已经展开
	var pnode = this.parentNode;
	while( pnode.constructor == MTMenuItem && pnode != rootMenu.rootNode ){
		if( pnode.expanded == false ){
			pnode.processFloderNode();
			return;
		}
		
		pnode = pnode.parentNode;
	}
	
	// find row index
	var menuTable = rootMenu.menuTable;
	for( var idx=0; idx<menuTable.rows.length; idx++ ){
		if( menuTable.rows[idx].id == this.number ){
			if( this.submenu != null ){
				if( this.expanded ){
					this.submenu.expandSubMenu( idx/2 + 1 );
				}
				else{
					this.submenu.hideSubMenu( idx/2 + 1 );
				}
				
				// 判断是否等待下载子节点
				if( this.submenu.items.length == 0 ){
					this.expanded = (this.expanded) ? false : true;
				}
				
				// 修改图标
				this.setImage();
			}
			
			break;
		}
	}
	setTimeout( function(){ changeLindHand() }, 500);
}

// 展开或缩进菜单
// flag = true时展开菜单、false时缩进菜单
function item_expandFloder( flag )
{
	// 缺省展开菜单
	if( flag == null ){ flag = true; }
	
	// 是否初始化以及完成
	var rootMenu = this.getRootMenu();
	if( rootMenu.expandNodeNumber > 0 ){
		var nodeid = this.number;
		setTimeout( function(){rootMenu.expand(nodeid, flag)}, 5 );
		return;
	}
	
	// 没有子节点 或 不支持展开
	if( this.submenu == null || rootMenu.expandFlag == false ){ return; }
	
	// 检查上级菜单是否已经展开
	var expFlag = this.expanded;
	if( expFlag == true ){
		var pnode = this.parentNode
		while( pnode != rootMenu.rootNode ){
			if( pnode.expanded == false ){
				expFlag = pnode.pnode;
				break;
			}
			
			pnode = this.parentNode;
		}
	}
	
	// 如果状态一致，不处理
	if( flag == expFlag ){ return; }
	
	// 修改展开标志，在processFloderNode中会重新状态
	this.expanded = (flag) ? false : true;
	
	// 展开或缩进菜单
	this.processFloderNode();
}


// menu和list显示线条、listbar显示空格；需要修改
function item_displayItem( )
{
	var str = "";
	var menu = this.getRootMenu()
	var menuIcons = menu.menuIcons;
	
	// 生成节点前的线条
	var height = 13;
	var pnode = this.parentNode;
	while( pnode != menu.rootNode ){
		height = 18;
		str = ((pnode.isLast()) ? menu.makeImage(menuIcons.emptyIcon) : menu.makeImage(menuIcons.menuBar)) + str;
		pnode = pnode.parentNode;
	}
	
	// 文字前的图标:processFloderNode
	str += this.prepareLineImage();
	str += this.prepareCheckbox();
	
	// 超链接
	str += this.makeLink();
	
	// check 不显示图片，处理太麻烦了，图片会换行
	if( menu.xtype != 'check' ){
		str += this.prepareNodeImage( );
		if( this.submenu != null && height < 17 ){
			height = 17;
		}
	}
	else{
		height = 17;
	}
	
	// 调整高度
	if( height > 13 ){
		var lineHeight = parseInt(height) + 1;
		if( lineHeight > 18 ){
			lineHeight = 18;
		}
		
		str += '<span style="VERTICAL-ALIGN: bottom; LINE-HEIGHT: ' + lineHeight + 'px">' + '&nbsp;' + this.text + '</span>';
	}
	else{
		str += '<span style="vertical-align: bottom; line-height: 18px;">&nbsp;' + this.text + '</span>';
	}
	
	if( menu.getSelectRowEventFlag() == false ){
		str += '</span>';
	}
	
	return str;
}


// 处理listbar的节点
function item_listbar_displayItem( )
{
	var str = "";
	var menu = this.getRootMenu();
	var menuIcons = menu.menuIcons;
	
	// 超链接
	str += this.makeLink();
	
	// 图标
	if( this.icon != '' ){
		str += this.prepareNodeImage( );
	}
	else{
		if( this.getDeep() == 1 ){
			str += '　&nbsp;&nbsp;' + this.text;
		}
		else{
			var img = this.prepareNodeImage( );
			str += menu.makeImage(menuIcons.emptyIcon) + img + '<span style="line-height:18px; vertical-align: bottom;">&nbsp;' + this.text + '</span>';
		}
	}
	
	if( menu.getSelectRowEventFlag() == false ){
		str += '</span>';
	}
	
// alert( str );
	return str;
}

// 设置节点的活动状态 flag=true活动、flag=false不活动
function item_setActive( flag )
{
	// 是否是支节点
	if( this.submenu != null && this.submenu.items.length > 0 ){
		return;
	}
	
	var menu = this.getRootMenu();
	if( menu.xtype != 'listbar' ){
		return;
	}
	
	if( flag ){
		var img = this.openIcon;
	}
	else{
		var img = this.icon;
	}
	
	if( img == null || img == '' ){
		return;
	}
	
	// 显示图标
	var menuIcons = menu.menuIcons;
	img = menuIcons.getFullName( img );
	var nodeid = this.number;
	setTimeout( function(){showNodeImage(menu,img,nodeid)}, 5 );
}

function showNodeImage( menu, img, id )
{
	var doc = menu.menuWindow.document;
	var	imgs = doc.getElementsByName( 'ri' + id );
	for( var ii=0; ii<imgs.length; ii++ ){
		if( menu.isChildElement(imgs[ii]) ){
			imgs[ii].src = img;
			break;
		}
	}
}


// 修改节点的显示方式：listbar 和 其它
function item_setMenuType( menuType )
{
	if( menuType == 'listbar' ){
		this.prepareNodeImage = item_listbar_prepareNodeImage;
		this.displayItem = item_listbar_displayItem;
		this.makeLink = item_listbar_makeLink;
		
		// 修改分支图标
		if( this.submenu != null ){
			var menuIcons = this.getRootMenu().menuIcons;
			if( this.icon == menuIcons.floderClosed ){
				this.icon = '';
			}
			
			if( this.openIcon == menuIcons.floderOpen ){
				this.openIcon = '';
			}
		}
	}
	else{
		this.prepareNodeImage = item_prepareNodeImage;
		this.displayItem = item_displayItem;
		this.makeLink = item_makeLink;
		
		// 修改分支图标
		if( this.submenu != null ){
			var menuIcons = this.getRootMenu().menuIcons;
			if( this.icon == null || this.icon == '' ){
				this.icon = menuIcons.floderClosed;
			}
			
			if( this.openIcon == null || this.openIcon == '' ){
				this.openIcon = menuIcons.floderOpen;
			}
		}
	}
	
	// 修改所有的子节点的类型
	if( this.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			items[ii].setMenuType( menuType );
		}
	}
}


// 恢复节点的状态，从备份的数据恢复
function item_restoreStatus( nodeStatus )
{
	this.checked = nodeStatus.checked;
	this.expanded = nodeStatus.expanded;
	
	// 处理所有子节点
	if( this.submenu != null && nodeStatus.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			// 查找节点
			for( var jj=0; jj<nodeStatus.submenu.items.length; jj++ ){
				var node = nodeStatus.submenu.items[jj];
				if( node.id == items[ii].id ){
					// 恢复节点
					items[ii].restoreStatus( node );
					break;
				}
			}
		}
	}
}




/*****************************************
菜单的目录节点信息
*****************************************/
function MTFloder( parentNode )
{
	// 子节点列表
	this.items   = new Array();
	
	// 上级节点，可能是menu
	this.parentNode = parentNode;
	
	// 函数
	this.getRootMenu = floder_getRootMenu;
	this.hideSubMenu = floder_hideSubMenu;
	this.expandSubMenu = floder_expandSubMenu;
	this.closeSubs = floder_closeSubs;
	this.setCheckFlag = floder_setCheckFlag;
	this.getCheckValue = floder_getCheckValue;
	this.setCheckValue = floder_setCheckValue;
}



// 取根菜单信息
function floder_getRootMenu()
{
	var p = this.parentNode;
	while( p.parentNode != null ){
		p = p.parentNode;
	}
	
	return p;
}

// hide
function floder_hideSubMenu( idx )
{
	var	items = this.items;
	var menuTable = this.getRootMenu().menuTable;
	
	for( var i=0; i<items.length; i++ ){
		if( idx * 2 >= menuTable.rows.length ){
			alert( idx * 2 );
		}
		
		menuTable.deleteRow( idx * 2 );
		menuTable.deleteRow( idx * 2 );
		
		// check sub item
		if( items[i].submenu && items[i].expanded ){
			items[i].submenu.hideSubMenu( idx );
		}
	}
}

// 展开节点下的所有以前打开的节点
// idx 行的编号
function floder_expandSubMenu( idx )
{
	if( idx == null || idx < 1 ) idx = 1;
	
	// 菜单信息
	var rootMenu = this.getRootMenu();
	
	// 判断是否需要下载数据
	if( this.parentNode != rootMenu && rootMenu.loadFloderFunc != null ){
		if( this.items.length == 0 ){
			// 还没有下载子节点
			rootMenu.loadFloderFunc( this.parentNode );
			return idx;
		}
	}
	
	var items = this.items;
	for (var i = 0; i < items.length; i++) {
		_node_addCell( rootMenu, items[i], idx );
		
		idx = idx + 1;
		if( items[i].submenu && (items[i].expanded || rootMenu.expandFlag == false) ){
			idx = items[i].submenu.expandSubMenu( idx );
		}
	}
	
	return idx;
}

// 增加节点
function _node_addCell(rootMenu, menuNode, rowid)
{
	rootMenu.expandNodeNumber ++;
	var h = menuNode.displayItem();
	var rootId = rootMenu.rootNode;
	setTimeout( function(){rootMenu.addCell(h, menuNode, rowid, rootId)}, 5 );
}


// 缩进所有不是当前点击节点的目录节点
function floder_closeSubs(clickedItem) {
	var i, j;
	var foundMatch = false;
	for( i = 0; i < this.items.length; i++ ){
		if( this.items[i].submenu && this.items[i].expanded ){
			if( this.items[i].number == clickedItem.number ){
				foundMatch = true;
				for( j = 0; j < this.items[i].submenu.items.length; j++ ){
					if( this.items[i].submenu.items[j].expanded ){
						this.items[i].submenu.items[j].processFloderNode();
					}
				}
			}
			else {
				if(foundMatch) {
					this.items[i].processFloderNode();
				}
				else {
					foundMatch = this.items[i].submenu.closeSubs(clickedItem);
					if(!foundMatch) {
						this.items[i].processFloderNode();
					}
				}
			}
		}
	}
	
	return(foundMatch);
}

// 设置check标志
function floder_setCheckFlag( flag )
{
	var doc = this.getRootMenu().menuWindow.document;
	for( var ii=0; ii<this.items.length; ii++ ){
		var item = this.items[ii];
		var obj = doc.getElementById( item.id );
		if( obj != null ){
			obj.checked = flag;
		}
		
		if( item.submenu != null && item.submenu.items.length > 0 ){
			item.submenu.setCheckFlag( flag );
		}
		else{
			item.checked = flag;
		}
	}
}

// 获取check标志
function floder_getCheckValue()
{
	var result = '';
	for( var ii=0; ii<this.items.length; ii++ ){
		var item = this.items[ii];
		if( item.submenu != null && item.submenu.items.length > 0 ){
			result = result + item.submenu.getCheckValue( );
		}
		else{
			if( item.checked ){
				result = result + ';' + item.id;
			}
		}
	}
	
	return result;
}

// 设置每个节点的选择标志
function floder_setCheckValue( value )
{
	for( var ii=0; ii<this.items.length; ii++ ){
		var item = this.items[ii];
		if( item.submenu != null && item.submenu.items.length > 0 ){
			item.submenu.setCheckValue( value );
		}
		else{
			var v = ';' + item.id + ';';
			if( value.indexOf(v) >= 0 ){
				item.checked = true;
			}
			else{
				item.checked = false;
			}
		}
	}
}


/******************************************************************************
* Define the Menu object.                                                     *
* xtype :                                                                     *
*   list 不显示-/+                                                            *
*   tree 显示-/+                                                              *
*   check 显示复选框                                                          *
*   listbar 类似outlook的功能条                                               *
******************************************************************************/

function MTMenu(name, xtype)
{
	// 类型
	if( xtype == 'listbar' ){
		this.xtype = xtype;
		
		// 是否自动关闭其它已经打开的目录
		this.MTMSubsAutoClose = true;
	}
	else if( xtype != 'list' && xtype != 'check' ){
		this.xtype = 'tree';
		
		// 是否自动关闭其它已经打开的目录
		this.MTMSubsAutoClose = false;
	}
	else{
		this.xtype = xtype;
		
		// 是否自动关闭其它已经打开的目录
		this.MTMSubsAutoClose = false;
	}
	
	// 是否正在初始化
	this.initFlag = false;
	
	// 是否处理节点展开和缩进动作：false=所有的节点都展开，true=通过鼠标缩进和展开
	this.expandFlag = true;
	
	// 处理函数
	this.onMouseEventFunction = null;
	
	// 当菜单的状态改变时，调用这个方法｛可以保存菜单的状态｝
	this.onMenuStatusChange = null;
	
	// 首页
	this.MTHomePage = null;
	
	// 目标页面名称
	this.MTMDefaultTarget = 'main';
	
	// 菜单输出的文档:生成菜单变量和菜单显示可能在两个页面中
	this.menuWindow = window;
	
	// TABLE表格
	this.menuTable = null;
	
	// 等待展开的节点数量:内部使用，在展开菜单的时候禁止再次展开菜单或收缩菜单
	this.expandNodeNumber = 0;
	
	// 分割符的高度 和 颜色
	this.splitHeight = 0;
	this.splitColor = 0;
	
	// listbar的第一级菜单行间距
	this.rowHeight1 = 28;
	this.rowheight2 = 19;
	
	// 菜单的样式
	this.rootNodeStyle = null;		// 根节点的样式
	this.nodeStyle = null;			// 分支节点的样式
	this.nodeActiveStyle = null;	// 活动的分支节点的样式
	this.nodeSelectedStyle = null;	// 选中的分支节点的样式
	this.itemStyle = null;			// 功能节点的样式
	this.itemActiveStyle = null;	// 活动的功能节点的样式
	this.itemSelectedStyle = null;	// 选中的功能节点的样式
	
	// 选中文字触发动作 还是 选中整行[<tr>]触发动作；[check]和[tree]不支持这个参数
	this.selectRowEventFlag = false;
	this.getSelectRowEventFlag = menu_getSelectRowEventFlag;
	
	// MTMenuIcons 图标信息
	this.menuIcons = new MTMfloderIcon( this.xtype );
	
	this.menuName = name;
	this.parentNode = null;
	this.expanded   = false;
	this.submenu = new MTFloder( this );
	this.addSubItem = menu_addSubItem;
	
	// 最近一次的菜单节点
	this.cur_menuNode = null;
	this.lookupItem = menu_lookupSubItem;
	
	// 根据内部编号查找节点，内部使用
	this.lookupItemByNumber = menu_lookupItemByNumber;
	
	// 根节点，设置根节点
	this.rootNode = this;
	this.setRootNode = menu_setRootNode;
	
	// 取变量名称的函数:变量可能不在当前窗口中定义
	this.getMenuName = menu_getMenuName;
	
	// 设置活动节点 和 编号
	this.selectedNode = null;
	this.setSelectedNode = menu_setSelectedNode;		// 设置选中的菜单项
	this.setMenuNodeStyle = menu_setMenuNodeStyle;		// 设置菜单项的样式
	
	// 处理函数
	this.setMenuType = menu_setMenuType;			// 设置菜单的样式
	this.addItem = menu_MTMAddItem;					// 增加菜单项（在结构中）
	this.addCell = menu_MTMAddCell;					// 增加菜单节点（在屏幕上）
	this.makeImage = menu_MTMakeImage;				// 生成图片
	this.startMenu = menu_MTMStartMenu;				// 开始生成菜单
	this.expand = menu_expandFloder;
	
	// 鼠标处理函数
	this.mouseEventProcessFlag = false;				// 是否正在处理鼠标事件，不允许两个鼠标事件同时处理
	this.mouseEventProcess = menu_mouseEventProcess;
	this.processFloderNode = menu_processFloderNode;
	
	// 处理所有节点的函数
	this.processNode = menu_processNode;
	
	// 触发菜单事件
	this.fireEvent = menu_fireEvent;
	
	// 导航到下一个页面
	this.goPage = menu_goPage;
	
	// check方式的结果
	this.processCheckbox = menu_processCheckbox;
	this.setCheckValue = menu_setCheckValue;
	this.getCheckValue = menu_getCheckValue;
	
	// 恢复菜单的状态，或者从原来保存的菜单中恢复所有菜单节点的状态
	this.restoreStatus = menu_restoreStatus;
	
	// 是否是当前菜单下的子元素：页面上可能有两个菜单使用相同的资源
	this.isChildElement = menu_isChildElement;
	
	// 下载数据的函数
	this.loadFloderFunc = null;
}

// 取菜单变量名称的函数:如果菜单的变量在其它窗口中定义，则需要实现这个方法
function menu_getMenuName()
{
	return this.menuName;
}

// 选中文字触发动作 还是 选中整行[<tr>]触发动作
function menu_getSelectRowEventFlag()
{
	if( this.xtype == 'tree' || this.xtype == 'check' ){
		return false;
	}
	else{
		return this.selectRowEventFlag;
	}
}

// 设置根节点
function menu_setRootNode( menuId )
{
	if( menuId == null || menuId == '' || menuId == 'root' ){
		var _rootNode = this;
	}
	else{
		var _rootNode = this.lookupItem( menuId );
		if(_rootNode == null ){
			alert( '菜单错误：没有找到菜单节点 --> ' + menuId );
			_rootNode = this;
		}
	}
	
	if( (this.initFlag || this.expandNodeNumber > 0) && this.rootNode == _rootNode ){
		alert( '请不用频繁刷新页面' );
		return false;
	}
	
	this.rootNode = _rootNode;
	return true;
}

// 设置菜单的样式
function menu_setMenuType( xtype )
{
	if( this.xtype == xtype ){
		return;
	}
	
	if( xtype == 'listbar' ){
		this.xtype = xtype;
		
		// 是否自动关闭其它已经打开的目录
		this.MTMSubsAutoClose = true;
	}
	else if( xtype != 'list' && xtype != 'check' ){
		this.xtype = 'tree';
		
		// 是否自动关闭其它已经打开的目录
		this.MTMSubsAutoClose = false;
	}
	else{
		this.xtype = xtype;
		
		// 是否自动关闭其它已经打开的目录
		this.MTMSubsAutoClose = false;
	}
	
	// 设置所有节点的类型
	if( this.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			items[ii].setMenuType( this.xtype );
		}
	}
	
	// 修改图标
	var icons = this.menuIcons;
	if( xtype == 'tree' || xtype == 'check' ){
		icons.nodeIcon = "menu-icon2.gif";
		
		icons.menuBar = "menu_bar.gif";
		icons.treeMinus = "menu_tee_minus.gif";
		icons.cornerMinus = "menu_corner_minus.gif";
		icons.treePlus = "menu_tee_plus.gif";
		icons.cornerPlus = "menu_corner_plus.gif";
		icons.cornerLine = "menu_corner.gif";
		icons.treeLine = "menu_tee.gif";
	}
	else{
		if( xtype == 'list' ){
			icons.nodeIcon = "menu-icon2.gif";
		}
		else{
			icons.nodeIcon = "listbar_node.gif";
		}
		
		icons.menuBar = "menu_pixel.gif";
		icons.treeMinus = "menu_pixel.gif";
		icons.cornerMinus = "menu_pixel.gif";
		icons.treePlus = "menu_pixel.gif";
		icons.cornerPlus = "menu_pixel.gif";
		icons.cornerLine = "menu_pixel.gif";
		icons.treeLine = "menu_pixel.gif";
	}
}

// 增加节点
function menu_addSubItem( menuNode )
{
	// 设置目录节点的图标
	if( menuNode.submenu != null ){
		if( menuNode.icon == '' ){
			menuNode.icon = this.menuIcons.floderClosed;
		}
		
		if( menuNode.openIcon == '' ){
			menuNode.openIcon = this.menuIcons.floderOpen;
		}
	}
	
	menuNode.parentNode = this;
	this.submenu.items.push( menuNode );
	return menuNode;
}

// 查找节点
function menu_lookupSubItem( menuId )
{
	var ii;
	var item;
	
	// 在最近访问的节点下查找
	if( this.cur_menuNode != null ){
		if( menuId == this.cur_menuNode.id ){
			return this.cur_menuNode;
		}
		
		var pnode = this.cur_menuNode.parentNode;
		while( pnode.parentNode != null ){
			item = pnode.lookupItem( menuId );
			if( item != null ){
				this.cur_menuNode = item;
				return item;
			}
			
			pnode = pnode.parentNode;
		}
	}
	
	// 遍历目录
	for( ii=0; ii<this.submenu.items.length; ii++ ){
		item = this.submenu.items[ii].lookupItem( menuId );
		if( item != null ){
			this.cur_menuNode = item;
			return item;
		}
	}
	
	return null;
}


// 根据内部编号查找节点
function menu_lookupItemByNumber( number )
{
	var ii;
	var item;
	for( ii=0; ii<this.submenu.items.length; ii++ ){
		item = this.submenu.items[ii].lookupItemByNumber( number );
		if( item != null ){
			return item;
		}
	}
	
	return null;
}


// 增加菜单节点
function menu_MTMAddItem( pid, text, url, target, tooltip, icon, openIcon, id )
{
	// text, url, target, tooltip, icon, openIcon, id
	var obj = new MTMenuItem(text, url, target, tooltip, icon, openIcon, id);
	
	// 增加节点
	if( pid == null || pid == '' ){
		this.cur_menuNode = obj;
		return this.addSubItem( obj );
	}
	else{
		var node = (typeof(pid) == 'string') ? this.lookupItem(pid) : pid;
		if( node != null ){
			this.cur_menuNode = obj;
			return node.addSubItem( obj );
		}
	}
}

/******************************************************************************
* Functions to draw the menus.                                                 *
******************************************************************************/
function menu_MTMStartMenu( flag, startMenuId )
{
	if(_browse.browserType == "O" && _browse.majVersion == 5) {
		if( flag ){
			parent.onload = this.startMenu;
			return;
		}
	}
	
	// 判断是否正在展开
	if( this.initFlag || this.expandNodeNumber > 0 ){
		var mm = this;
		setTimeout( function(){mm.startMenu(flag, startMenuId);}, 10 );
		return;
	}
	
	// 设置根节点
	if( startMenuId != null ){
		if( this.setRootNode(startMenuId) == false ) return;
	}
	
	this.initFlag = true;
	
	// 取table
	this.menuTable = this.menuWindow.document.getElementById(this.menuName);
	
	// 设置TABLE的样式
	this.menuTable.cellSpacing='0';
	this.menuTable.cellPadding='0';
	this.menuTable.border='0';
	this.menuTable.style.tableLayout = 'fixed';
	this.menuTable.onselectstart = function(){ return false; };
	
	// 清空菜单内容
	if( _browse.DOMable || _browse.browserType == "IE" ) {
		while(this.menuTable.rows.length > 0) {
			this.menuTable.deleteRow(0);
		}
	}
	
	// 生成根节点
	var	str = "";
	if( this.rootNode == this ){
		// 生成根
		if( this.xtype != 'listbar' && this.menuIcons.menuText != null && this.menuIcons.menuText != '' ){
			var spanStart = '<span id="root" style="line-height:20px; vertical-align:bottom';
			if( this.MTHomePage != null && this.MTHomePage != '' ){
				spanStart += ';cursor:hand';
			}
			else{
				spanStart += ';cursor:default';
			}
			
			spanStart += '"';
			
			str = this.makeImage( this.menuIcons.rootIcon );
			str += spanStart + '>&nbsp;' + this.menuIcons.menuText + '</span>';
		}
		else{
			str = '<span id="root"></span>';
		}
	}
	else{
		// 生成根
		if( this.xtype != 'listbar' ){
			var spanStart = '<span id="root" style="line-height:20px; vertical-align:bottom';
			if( this.rootNode.url != null && this.rootNode.url != '' ){
				spanStart += ';cursor:hand';
			}
			else{
				spanStart += ';cursor:default';
			}
			
			spanStart += '"';
			
			if( this.rootNode.tooltip ){
				spanStart += ' title="' + this.rootNode.tooltip + '" ';
			}
			
			str = this.makeImage( this.rootNode.icon );
			str += spanStart + '>&nbsp;' + this.rootNode.text + '</span>';
		}
		else{
			str = '<span id="root"></span>';
		}
	}
	
	// 生成根
	this.expandNodeNumber ++;
	this.addCell( str );
	
	// 生成子菜单
	if( this.rootNode.submenu != null ){
		this.rootNode.submenu.expandSubMenu();
	}
	
	// 初始化结束
	this.expanded = true;
	this.initFlag = false;
	
	// 清空函数
	var menuDefine = this;
	_browse._onunload.push( function(){_menu_release(menuDefine)} );
}


// 展开节点
function menu_expandFloder( nodeNumber, flag )
{
	// 查找节点
	var node = this.lookupItemByNumber( nodeNumber );
	if( node == null ){
		return;
	}
	
	node.expand( flag );
}


// menu和list显示线条等图片、listbar不显示；需要修改
function menu_MTMakeImage(thisImage) 
{
	if( thisImage == null || thisImage == "" ){
		return "";
	}
	
	return '<img src="' + this.menuIcons.getFullName(thisImage) + '" width="18px" height="18px" align="left" border="0" vspace="0" hspace="0">';
}

// 增加行，对于list方式，第一行的高度需要设置的比较小
// rowid 行号
// rootNode 用于判断菜单的关节点是否已经变化，因为展开是异步的
function menu_MTMAddCell(thisHTML, node, rowid, rootNode)
{
	// 判断关节点是否已经变化
	if( rootNode != null && this.rootNode != rootNode ){
		this.expandNodeNumber --;
		return;
	}
	
	thisHTML = thisHTML.replace( /[`]/g, "'" );
	
	if(_browse.DOMable || _browse.browserType == "IE" ){
		if( rowid == null || rowid == '' ){
			rowid = this.menuTable.rows.length;
		}
		else{
			rowid = 2 * parseInt( rowid );
		}
		
		// 插入<TR>
		var myRow = this.menuTable.insertRow(rowid);
		myRow.id = (node == null) ? 't0' : node.number;
		myRow.name = (node == null || node.parentNode == null || node.parentNode.parentNode == null) ? 't0' : node.parentNode.number;
		
		// 插入<TD>
		var myCell = myRow.insertCell(myRow.cells.length);
		myCell.noWrap = true;
		myCell.innerHTML = thisHTML;
		myCell.vAlign = "bottom";
		
		// 设置事件处理函数
		var eventObject;
		if( this.getSelectRowEventFlag() ){
			eventObject = myRow;
		}
		else{
			eventObject = myRow.lastChild.lastChild;
		}
		
		var _menu = this;
		eventObject.ondblclick = function(){_menu.mouseEventProcess('ondblclick')};
		eventObject.onclick = function(){_menu.mouseEventProcess('onclick')};
		eventObject.onmouseup = function(){_menu.mouseEventProcess('onmouseup')};
		eventObject.onmouseover = function(){_menu.mouseEventProcess('onmouseover'); return true;};
		eventObject.onmouseout = function(){_menu.mouseEventProcess('onmouseout'); return true;};
		
		// 设置样式
		if( node == null ){
			if( this.rootNodeStyle != null ){
				eventObject.className = this.rootNodeStyle;
			}
		}
		else if( node.submenu != null ){
			if( this.nodeStyle != null ){
				eventObject.className = this.nodeStyle;
			}
		}
		else{
			if( this.itemStyle != null ){
				eventObject.className = this.itemStyle;
			}
		}
		
		// listbar,设置背景等
		if( this.xtype == 'listbar' && node != null ){
			if( node.getDeep() == 1 ){
				if( node.icon == '' ){
					myRow.height = this.rowHeight1 + 'px';
					var bgFile = this.menuIcons.getFullName('button-bg.gif');
					myCell.style.backgroundImage = "url('" + bgFile + "')";
					myCell.style.backgroundRepeat = "no-repeat";
				}
			}
			else{
				if( node.icon == '' ){
					myCell.vAlign = "bottom";
					if( node.parentNode.submenu.items[0] == node ){
						myRow.height = this.rowheight2 + 1 + 'px';
					}
					else{
						myRow.height = this.rowheight2 + 'px';
					}
				}
			}
		}
		else if( this.xtype == 'list' && node != null ){
			var rowHeight = getObjectStyleValue( myRow, "height" );
			if( rowHeight == null ){
				myRow.height = '17px';
			}
		}
		
		myRow = null;
		myCell = null;
		eventObject = null;
	}
	else {
		var id = (node == null) ? 't0' : node.number;
		var str = '<tr id="' + id + '" name="' + pid + '">';
		str += '<td';
		str += ' valign="bottom" nowrap>' + thisHTML + '<\/td><\/tr>';
		this.menuWindow.document.writeln( str );
	}
	
	// 分割符
	if(_browse.DOMable || _browse.browserType == "IE" ){
		rowid = parseInt(rowid) + 1;
		var myRow = this.menuTable.insertRow(rowid);
		if( this.splitHeight == 0 ){
			myRow.style.display = 'none';
		}
		else{
			myRow.height = this.splitHeight;
			myRow.bgColor = this.splitColor;
		}
		
		myRow.insertCell(myRow.cells.length);
		myRow = null;
	}
	else{
		var str = '<tr height="' + this.splitHeight + '" bgcolor="' + this.splitColor + '"><td> </td></tr>';
		this.menuWindow.document.writeln( str );
	}
	
	// 设置checkbox的状态
	if( node != null ){
		node.setCheckFlag();
		
		// 是否活动节点
		if( node == this.selectedNode ){
			this.setSelectedNode( node );
		}
	}
	
	// 展开菜单完成
	this.expandNodeNumber --;
}


// 活动节点的样式
function menu_setSelectedNode( node )
{
	// 取节点的编号
	var nodeID = (node == null) ? null : node.number;
	
	// 前一个活动节点的编号
	var selectedNodeID = (this.selectedNode == null) ? null : this.selectedNode.number;
	
	// 设置所有的节点为没有选中状态
	var src = null;
	for( var i=0; i<this.menuTable.rows.length; i++ ){
		var row = this.menuTable.rows[i];
		if( row.id == selectedNodeID ){
			this.setMenuNodeStyle( row, 0 );
			
			// 设置节点的状态
			var item = this.lookupItemByNumber( row.id );
			if( item != null ){
				item.setActive( false );
			}
		}
		
		// 判断是否选中的菜单项
		if( row.id == nodeID ){
			src = row;
		}
	}
	
	// 保存选中的节点
	if( this.selectedNode != node ){
		var nd1 = this.selectedNode;
		this.selectedNode = node;
		
		// 设置原来选中的节点的样式
		if( nd1 != null ){
			this.setMenuNodeStyle( nd1, 0 );
		}
	}
	
	if( src != null ){
		// 设置样式
		this.setMenuNodeStyle( node, 0 );
				
		// 设置节点的状态
		node.setActive( true );
	}
}


// 设置菜单节点的状态: nodeStatus：[0=正常状态、1=活动状态、2=选中状态]
function menu_setMenuNodeStyle( row, nodeStatus )
{
	if( row.constructor == MTMenuItem ){
		var node = row;
		
		// 取<TR>
		row = null;
		for( var i=0; i<this.menuTable.rows.length; i++ ){
			if( this.menuTable.rows[i].id == node.number ){
				row = this.menuTable.rows[i];
				break;
			}
		}
		
		if( row == null ){
			return;
		}
	}
	else{
		var node = this.lookupItemByNumber( row.id );
	}
	
	// 判断是否活动
	if( this.selectedNode == node ){
		nodeStatus = 2;
	}
	
	// 需要设置样式的对象
	var styleObject = null;
	if( this.getSelectRowEventFlag() == false ){
		styleObject = row.lastChild.lastChild;
	}
	else{
		styleObject = row;
	}
		
	// 样式单的名称
	var className = null;
	if( node != null ){
		// 取样式
		// flag：[0=正常状态、1=活动状态、2=选中状态]
		if( node.submenu != null ){
			if( nodeStatus == 1 ){
				className = this.nodeActiveStyle;
			}
			else if( nodeStatus == 2 ){
				className = this.nodeSelectedStyle;
			}
			else{
				className = this.nodeStyle;
			}
		}
		else{
			if( nodeStatus == 1 ){
				className = this.itemActiveStyle;
			}
			else if( nodeStatus == 2 ){
				className = this.itemSelectedStyle;
			}
			else{
				className = this.itemStyle;
			}
		}
		
		// 设置样式
		if( className != null && className != '' && styleObject.className != className ){
			styleObject.className = className;
			
			// 设置行的样式，用于刷新<SPAN>，否则不会显示内容
			if( styleObject != row ){
				row.className = '';
			}
		}
	}
	
	// 设置颜色
	if( nodeStatus == 0 || className == null || className == '' ){
		if( nodeStatus == 0 ){
			var color = '';
		}
		else{
			var color = 'blue';
		}
		
		// 设置样式
		styleObject.style.color = color;
	}
}



// check方式的结果
function menu_setCheckValue( value )
{
	if( this.xtype != 'check' ) return;
	
	// 设置内容
	value = (value == null || value == '') ? '' : ';' + value.replace(/,/g, ';') + ';';
	this.submenu.setCheckValue( value );
	
	// 设置checkbox的状态
	var objs = this.menuWindow.document.getElementsByName( this.menuName + '_check' );
	for( var ii=0; ii<objs.length; ii++ ){
		var node = this.lookupItem( objs[ii].id );
		if( node != null ){
			node.setCheckFlag();
		}
	}
}

// 取结果
function menu_getCheckValue()
{
	if( this.xtype != 'check' ){
		return '';
	}
	
	var result = this.submenu.getCheckValue();
	if( result == '' || result == null ){
		return '';
	}
	
	return result.substring(1);
}


// 触发事件
function menu_fireEvent( menuId, eventName )
{
	if( eventName == null ){
		eventName = 'onclick';
	}
	
	// 初始化完成以后执行
	if( this.expandNodeNumber > 0 ){
		var f = this.getMenuName() + '.fireEvent( "' + menuId + '", "' + eventName + '" )';
		setTimeout( f, 200 );
		return;
	}
	
	// find node
	var	nd = this.lookupItem( menuId );
	if( nd == null ){
		return;
	}
	
	// 展开目录
	if( nd.parentNode.expanded == false ){
		nd.parentNode.processFloderNode();
		
		// 等待目录展开
		if( this.expandNodeNumber > 0 ){
			var f = this.getMenuName() + '.fireEvent( "' + menuId + '", "' + eventName + '" )';
			setTimeout( f, 200 );
			return;
		}
	}
	
	var row = this.menuWindow.document.getElementById( nd.number );
	if( row == null ){
		return;
	}
	
	// 触发事件 ： 判断触发<A>的事件 还是 <TR>的事件
	if( this.getSelectRowEventFlag() ){
		// 触发行的动作
		row.fireEvent( eventName );
	}
	else{
		var	col = row.lastChild;
		var	a = col.lastChild;
		a.fireEvent( eventName );
		
		// 导航页
		if( eventName == 'onclick' ){
			// 导航页面
			this.goPage( nd.url, nd.target, nd.text );
		}
	}
}


// 正文的鼠标事件
function menu_mouseEventProcess( eventName )
{
	 
	// 不允许两个鼠标事件同时处理
	if( this.mouseEventProcessFlag ) return;
	this.mouseEventProcessFlag = true;
	
	// 获取<TR>
	var	src = this.menuWindow.event.srcElement;
	while( src.tagName.toUpperCase() != 'TR' ){
		src = src.parentNode;
	}
	//alert(src.id)
	// 判断是否根节点
	if( src.id == 't0' ){
		if( eventName == 'onclick' ){
			var nd = this.rootNode;
			if( nd == this ){
				var xurl = this.MTHomePage;
				var target = this.MTMDefaultTarget ? this.MTMDefaultTarget : "";
			}
			else{
				var xurl = nd.url;
				var target = nd.target;
			}
			
			if( xurl != null ){
				// 设置选中的节点
				this.setSelectedNode( null );
				
				// 导航页面
				this.goPage( xurl, target );
			}
		}
		
		this.mouseEventProcessFlag = false;
		return;
	}
	
	// find node
	var	nd = this.lookupItemByNumber( src.id );
	
	// 设置当前选中的菜单
	if( eventName == 'onclick' ){
		this.setSelectedNode( nd );
	}
	
	if( eventName == 'onmouseout' ){
		parent.status = '欢迎使用';
		
		// 设置成正常样式
		this.setMenuNodeStyle( nd, 0 );
	}
	else if( eventName == 'onmouseover' ){
		var	exn = '执行';
		if( nd.submenu != null ){
			exn = (nd.expanded) ? '折叠' : '展开';
		}
		
		if( nd.tooltip == null || nd.tooltip == '' ){
			nd.tooltip = nd.text;
		}
		
		parent.status = exn + '[' + nd.id + '][' + nd.tooltip + ']';
		
		// 设置成活动样式
		this.setMenuNodeStyle( nd, 1 );
	}
	
	// 是否继续展开目录
	var rc = false;

	// 执行功能
	if( this.onMouseEventFunction != null ){
		if( typeof(this.onMouseEventFunction) == 'string' ){
			var	func = this.onMouseEventFunction + "('" + eventName + "', nd, src )";
			var rc = eval( func );
		}
		else{
			var rc = this.onMouseEventFunction( eventName, nd, src );
		}
	}
	
	// 是否展开目录
	if( eventName == 'onclick' || eventName == 'ondblclick' ){
		if( nd.submenu != null ){
			// 是否自动隐藏其他的节点
			if( this.expandNodeNumber == 0 ){
				if( nd.expanded != true && this.MTMSubsAutoClose ){
					this.rootNode.submenu.closeSubs(nd);
				}
				
				// 处理目录
				if( nd.expanded != true || eventName == 'ondblclick' || (rc != true && nd.url == '') ){
					nd.processFloderNode();
				}
			}
		}
		else if( this.xtype == 'check' ){
			// 设置check状态
			var obj = this.menuWindow.document.getElementById(nd.id);
			if( obj != null ){
				obj.checked = !obj.checked;
				this.processCheckbox( obj );
			}
		}
	}
	
	// 菜单的状态已经修改
	if( this.onMenuStatusChange != null && (eventName == 'onclick' || eventName == 'ondblclick') ){
		if( typeof(this.onMenuStatusChange) == 'string' ){
			eval( this.onMenuStatusChange + "('" + this.menuName + "')" );
		}
		else{
			this.onMenuStatusChange( this.menuName );
		}
	}
	
	// 导航页面
	if( eventName == 'onclick' || eventName == 'ondblclick' ){
		this.goPage( nd.url, nd.target, nd.text );
	}
	
	// 鼠标处理结束
	this.mouseEventProcessFlag = false;
}

// +/-图标的鼠标事件
function menu_processFloderNode()
{
	// 不支持展开 或者 正在展开菜单时，禁止这个函数
	if( this.expandFlag == false || this.expandNodeNumber > 0 ) return;
	
	// find node
	var	src = this.menuWindow.event.srcElement;
	var	nd = this.lookupItemByNumber( src.parentNode.parentNode.id );
	
	if( nd.submenu != null ){
		// 是否自动隐藏其他的节点
		if( !nd.expanded && this.MTMSubsAutoClose ){
			this.rootNode.submenu.closeSubs(nd);
		}
		
		// 处理目录
		nd.processFloderNode();
	}
}

// 处理目录
function _menu_processFloderNode( id )
{
	
}

// 处理checkbox
function menu_processCheckbox( src )
{
	if( src == null ){
		src = this.menuWindow.event.srcElement;
	}
	
	var node = this.lookupItem( src.id );
	if( node.submenu != null && node.submenu.items.length > 0 ){
		node.submenu.setCheckFlag( src.checked );
	}
	else if( node.checked == true ){
		node.checked = false;
	}
	else{
		node.checked = true;
	}
	
	// 设置上级节点的状态
	node = node.parentNode;
	while( node.parentNode != null ){
		node.setCheckFlag();
		node = node.parentNode;
	}
	 
}

// 通过回调函数处理所有节点
function menu_processNode( callback )
{
	for( var ii=0; ii<this.submenu.items.length; ii++ ){
		this.submenu.items[ii].processNode( callback );
	}
}


// 恢复菜单的状态，或者从原来保存的菜单中恢复所有菜单节点的状态
function menu_restoreStatus( menuStatus )
{
	// 当前选中的节点
	if( menuStatus.selectedNode != null ){
		var nid = menuStatus.selectedNode.id;
		this.selectedNode = this.lookupItem( nid );
	}
	
	// 所有子节点的状态
	if( this.submenu != null && menuStatus.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			// 查找节点
			for( var jj=0; jj<menuStatus.submenu.items.length; jj++ ){
				var node = menuStatus.submenu.items[jj];
				if( node.id == items[ii].id ){
					// 恢复节点
					items[ii].restoreStatus( node );
					break;
				}
			}
		}
	}
}

	
// 是否是当前菜单下的子元素：页面上可能有两个菜单使用相同的资源
function menu_isChildElement( obj )
{
	while( obj != null ){
		if( obj == this.menuTable ){
			return true;
		}
		
		obj = obj.parentNode;
	}
	
	return false;
}


// 跳转到<a ...></a>指定的页面
function menu_goPage( xurl, target, description )
{
	// 是否指定了页面地址
	if( xurl == null || xurl == '' ){
		return;
	}
	
	if( target == '' ){
		target = this.MTMDefaultTarget;
	}
	
	// 取交易码
	var tmp = _getHrefParameter( xurl, 'page' );
	if( tmp != null ){
		var txnCode = _getTxnCodeFromHref( tmp );
		if( txnCode != null ){
			// 名称
			var txnName = _getHrefParameter( xurl, 'cmd' );
			ptr = txnName.lastIndexOf( '/' );
			if( ptr > 0 ){
				txnName = txnName.substring( ptr+1 );
			}
			
			if( description != null && description != txnName ){
				txnName = description + '-' + txnName;
			}
			
			// 流水号
			var flowId = _getHrefParameter( xurl, 'inner-flag:flowno' );
			if( flowId == null ){
				var	dd = new Date();
				var stamp = '' + dd.getTime();
				var ptr = stamp.length - 10;
				flowId = stamp.substring( ptr );
				xurl = _addHrefParameter( xurl, 'inner-flag:flowno', flowId );
			}
			
			_browse.addOperatorLogger( flowId, txnCode, txnName, true, null );
		}
		else if( xurl.indexOf('/freeze.main?txn-code=show&path=') == 0 ){
			var iptr = tmp.indexOf( '?' );
			if( iptr > 0 ){
				tmp = tmp.substring( 0, iptr );
			}
			
			iptr = tmp.lastIndexOf( '.' );
			if( iptr > 0 ){
				var pageType = tmp.substring( iptr + 1 );
				if( pageType == 'htm' || pageType == 'html' ){
					xurl = xurl.substring( '/freeze.main?txn-code=show&path='.length );
					xurl = xurl.replaceAll( '&page=', '' );
					iptr = xurl.indexOf( '&cmd=' );
					if( iptr > 0 ){
						xurl = xurl.substring( 0, iptr );
					}
				}
			}
		}
	}
	
	// 延迟处理
	navigateTo( this.menuWindow, xurl, target, "resizable,scrollbars" );
}

// 释放DOM对象
function _menu_release( menuDefine )
{
	var menuTable = menuDefine.menuTable;
	if( menuTable != null ){
		var rowNumber = menuTable.rows.length;
		for( var ii=0; ii<rowNumber; ii++ ){
			menuTable.deleteRow( 0 );
		}
		
		menuTable = null;
	}
	
	var propertyName;
	for( propertyName in menuDefine ){
		menuDefine[propertyName] = null;
	}
}

//this.menuWindow.document.write( '<p><textarea name="menuMonitor" id="menuMonitor" rows="20" cols="80"></textarea></p>' );
