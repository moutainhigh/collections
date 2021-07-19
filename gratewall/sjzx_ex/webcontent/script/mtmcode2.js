

// �˵����,ȫ�ֱ��
var MTMNumber = 1;


// ͼ��
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
	
	this.menuText = "�������";
	//this.rootIcon = "menu_new_root.gif";
	this.rootIcon = "menu_new_root2.gif";
	
	// ͼ��
	this.items = new Array();
	this.addIcon = icon_addIcon;
	this.getFullName = icon_getFullName;
}

function MTMIcon(iconfile, match, type) {
	this.file = iconfile;
	this.match = match;
	this.type = type;
}

// ͼ��
function icon_addIcon(item) {
	this.items[this.items.length] = item;
}

// ͼ���ļ���·��
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


// �˵���
function MTMenuItem(text, url, target, tooltip, icon, openIcon, id, isFloder)
{
	// ȡtarget��url
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
	
	// ͼ��
	this.icon = icon ? icon : "";
	this.openIcon = openIcon ? openIcon : "";
	
	// �ڲ�����
	this.parentNode  = null;	// �ϼ��ڵ�,��һ��ڵ� = menu
	this.expanded    = false;	// �Ƿ�չ����־
	
	// �ӽڵ㣬����ͨ��AJAX�ӿڴӷ���������
	this.submenu     = null;
	if( isFloder ){
		this.submenu = new MTFloder( this );
	}
	
	// ���
	this.deep = 1;
	this.getDeep = item_getDeep;
	
	// �Ƿ�ѡ��:����״̬--true/false
	this.checked = false;
	this.prepareCheckbox = item_prepareCheckbox;
	this.getCheckFlag = item_getCheckFlag;
	this.setCheckFlag = item_setCheckFlag;

	this.number = MTMNumber++;
	
	
	// ����
	this.getRootMenu = item_getRootMenu;
	this.addSubItem = item_addSubItem;
	this.lookupItem = item_lookupItem;
	this.isLast = item_isLastNode;
	this.isChild = item_isChild;
	this.setImage = item_setImage;
	this.fetchIcon = item_fetchIcon;
	this.prepareLineImage = item_prepareLineImage;
	this.processFloderNode = item_processFloderNode;
	
	// ��ͬ��������ʾ��ʽ��һ��
	this.prepareNodeImage = item_prepareNodeImage;
	this.displayItem = item_displayItem;
	this.makeLink = item_makeLink;
	
	// ���ýڵ�Ļ״̬
	this.setActive = item_setActive;
	
	// �����ڲ���Ų��ҽڵ㣬�ڲ�ʹ��
	this.lookupItemByNumber = item_lookupItemByNumber;
	
	// ͨ���ص��������������ӽڵ�
	this.processNode = item_processNode;
	
	// �޸Ľڵ����ʾ��ʽ��listbar �� ����
	this.setMenuType = item_setMenuType;
	
	// �ָ��ڵ��״̬
	this.restoreStatus = item_restoreStatus;
	
	// չ���������ڵ�
	this.expand = item_expandFloder;
}

// ȡ���˵���Ϣ
function item_getRootMenu()
{
	var p = this;
	while( p.parentNode != null ){
		p = p.parentNode;
	}
	
	return p;
}

// ȡ�ڵ�Ĳ��
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

// ���Ӳ˵��ڵ�
function item_addSubItem( menuNode )
{
	if( this.submenu == null ){
		this.submenu = new MTFloder( this );
	}
	
	// ���ӽڵ�
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
		
		// ����Ŀ¼�ڵ��ͼ��
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
	
	// չ����־
	if( menuNode.expanded ){
		var pobj = this;
		while( pobj != null ){
			pobj.expanded = true;
			pobj = pobj.parentNode;
		}
	}
	
	return menuNode;
}

// ���ҽڵ�
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

// ���ҽڵ�
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


// ͨ���ص��������������ӽڵ�
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


// �жϽڵ��Ƿ���Ŀ¼������һ��
function item_isLastNode()
{
	var	pnode = this.parentNode.submenu;
	return (pnode.items[pnode.items.length - 1] == this) ? true : false;
}

// �ж��Ƿ��ǵ�ǰ�ڵ���ӽڵ�
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

// �޸�Ŀ¼��ͼ��
// menu��list����ʾͼ�ꡢlistbar����ʾͼƬ������
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
	
	// ��ʼ��ʱ��ͬ������
	img1 = menuIcons.getFullName( img1 );
	img2 = menuIcons.getFullName( img2 );
	var nodeid = this.number;
	var func = function(){ showImage( menu, img1, img2, nodeid ) };
	setTimeout( func, 5 );
}

function showImage( menu, img1, img2, id )
{
	var doc = menu.menuWindow.document;
	
	// �滻����
	var	imgs = doc.getElementsByName( 'ti' + id );
	for( var ii=0; ii<imgs.length; ii++ ){
		if( menu.isChildElement(imgs[ii]) ){
			imgs[ii].src = img1;
			break;
		}
	}
	
	// �滻icon
	var	imgs = doc.getElementsByName( 'ri' + id );
	for( var ii=0; ii<imgs.length; ii++ ){
		if( menu.isChildElement(imgs[ii]) ){
			imgs[ii].src = img2;
			break;
		}
	}
}


// ȡ�ڵ��ͼ������
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

// ����check-box
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

// ȡcheck�ı�־��checked��uncheck��half(����ѡ��)
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

// ����check-box��ֵ
function item_setCheckFlag()
{
	if(this.getRootMenu().xtype != 'check' ){
		return;
	}
	
	// ���ҽڵ�
	var doc = this.getRootMenu().menuWindow.document;
	var flagBox = doc.getElementById( this.id );
	if( flagBox != null ){
		// ȡѡ��״̬
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

// ����Ŀ¼��ͼ��
// menu����ʾ��������������ʾ
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
	
	// �Ƿ���Ҫ��չ�ڵ㣺�����ӽڵ��Ŀ¼�ڵ�
	if( this.submenu != null ){
		isrc = isrc + ' onclick="' + rootMenu.getMenuName() + '.processFloderNode()"';
	}
	
	isrc = isrc + ' src="' + menuIcons.getFullName(img) + '" width="18px" height="18px" align="left" border="0" vspace="0" hspace="0">';
	return isrc;
}

// ���ɽڵ�ͼ��
// menu��list��ʾͼ�ꡢlistbar��ʾͼƬ�����֣���Ҫ�޸�
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

// ����
function item_makeLink( )
{
	var tempString = '';
	var rootMenu = this.getRootMenu();
	if( rootMenu.getSelectRowEventFlag() == false ){
		tempString = '<span style="cursor:hand"';
		
		// ��ʾ��Ϣ
		if( this.tooltip ){
			tempString += ' title="' + this.tooltip + '" ';
		}
		
		tempString += '>';
	}
	
	return tempString;
}

// listbar������
function item_listbar_makeLink()
{
	var tempString = '';
	var rootMenu = this.getRootMenu();
	if( rootMenu.getSelectRowEventFlag() == false ){
		tempString = '<span style="cursor:hand"';
		
		// ��ʾ��Ϣ
		if( this.tooltip ){
			tempString += ' title="' + this.tooltip + '" ';
		}
		
		// ��Ⱥ͸߶�
		if( this.getDeep() == 1 ){
			var ptop = (menu.rowHeight1 - 16)/2
			tempString += 'style="width:100%;height:100%;padding-top:' + ptop + 'px"'
		}
		
		tempString += '>';
	}
	
	return tempString;
}


// ����Ŀ¼�ڵ㣬չ�����������ڵ�
function item_processFloderNode( )
{	
	var rootMenu = this.getRootMenu();
	if( rootMenu.expandFlag == false ) return;
	
	// �޸�չ����־
	this.expanded = (this.expanded) ? false : true;
	
	// ����ϼ��˵��Ƿ��Ѿ�չ��
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
				
				// �ж��Ƿ�ȴ������ӽڵ�
				if( this.submenu.items.length == 0 ){
					this.expanded = (this.expanded) ? false : true;
				}
				
				// �޸�ͼ��
				this.setImage();
			}
			
			break;
		}
	}
	setTimeout( function(){ changeLindHand() }, 500);
}

// չ���������˵�
// flag = trueʱչ���˵���falseʱ�����˵�
function item_expandFloder( flag )
{
	// ȱʡչ���˵�
	if( flag == null ){ flag = true; }
	
	// �Ƿ��ʼ���Լ����
	var rootMenu = this.getRootMenu();
	if( rootMenu.expandNodeNumber > 0 ){
		var nodeid = this.number;
		setTimeout( function(){rootMenu.expand(nodeid, flag)}, 5 );
		return;
	}
	
	// û���ӽڵ� �� ��֧��չ��
	if( this.submenu == null || rootMenu.expandFlag == false ){ return; }
	
	// ����ϼ��˵��Ƿ��Ѿ�չ��
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
	
	// ���״̬һ�£�������
	if( flag == expFlag ){ return; }
	
	// �޸�չ����־����processFloderNode�л�����״̬
	this.expanded = (flag) ? false : true;
	
	// չ���������˵�
	this.processFloderNode();
}


// menu��list��ʾ������listbar��ʾ�ո���Ҫ�޸�
function item_displayItem( )
{
	var str = "";
	var menu = this.getRootMenu()
	var menuIcons = menu.menuIcons;
	
	// ���ɽڵ�ǰ������
	var height = 13;
	var pnode = this.parentNode;
	while( pnode != menu.rootNode ){
		height = 18;
		str = ((pnode.isLast()) ? menu.makeImage(menuIcons.emptyIcon) : menu.makeImage(menuIcons.menuBar)) + str;
		pnode = pnode.parentNode;
	}
	
	// ����ǰ��ͼ��:processFloderNode
	str += this.prepareLineImage();
	str += this.prepareCheckbox();
	
	// ������
	str += this.makeLink();
	
	// check ����ʾͼƬ������̫�鷳�ˣ�ͼƬ�ỻ��
	if( menu.xtype != 'check' ){
		str += this.prepareNodeImage( );
		if( this.submenu != null && height < 17 ){
			height = 17;
		}
	}
	else{
		height = 17;
	}
	
	// �����߶�
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


// ����listbar�Ľڵ�
function item_listbar_displayItem( )
{
	var str = "";
	var menu = this.getRootMenu();
	var menuIcons = menu.menuIcons;
	
	// ������
	str += this.makeLink();
	
	// ͼ��
	if( this.icon != '' ){
		str += this.prepareNodeImage( );
	}
	else{
		if( this.getDeep() == 1 ){
			str += '��&nbsp;&nbsp;' + this.text;
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

// ���ýڵ�Ļ״̬ flag=true���flag=false���
function item_setActive( flag )
{
	// �Ƿ���֧�ڵ�
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
	
	// ��ʾͼ��
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


// �޸Ľڵ����ʾ��ʽ��listbar �� ����
function item_setMenuType( menuType )
{
	if( menuType == 'listbar' ){
		this.prepareNodeImage = item_listbar_prepareNodeImage;
		this.displayItem = item_listbar_displayItem;
		this.makeLink = item_listbar_makeLink;
		
		// �޸ķ�֧ͼ��
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
		
		// �޸ķ�֧ͼ��
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
	
	// �޸����е��ӽڵ������
	if( this.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			items[ii].setMenuType( menuType );
		}
	}
}


// �ָ��ڵ��״̬���ӱ��ݵ����ݻָ�
function item_restoreStatus( nodeStatus )
{
	this.checked = nodeStatus.checked;
	this.expanded = nodeStatus.expanded;
	
	// ���������ӽڵ�
	if( this.submenu != null && nodeStatus.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			// ���ҽڵ�
			for( var jj=0; jj<nodeStatus.submenu.items.length; jj++ ){
				var node = nodeStatus.submenu.items[jj];
				if( node.id == items[ii].id ){
					// �ָ��ڵ�
					items[ii].restoreStatus( node );
					break;
				}
			}
		}
	}
}




/*****************************************
�˵���Ŀ¼�ڵ���Ϣ
*****************************************/
function MTFloder( parentNode )
{
	// �ӽڵ��б�
	this.items   = new Array();
	
	// �ϼ��ڵ㣬������menu
	this.parentNode = parentNode;
	
	// ����
	this.getRootMenu = floder_getRootMenu;
	this.hideSubMenu = floder_hideSubMenu;
	this.expandSubMenu = floder_expandSubMenu;
	this.closeSubs = floder_closeSubs;
	this.setCheckFlag = floder_setCheckFlag;
	this.getCheckValue = floder_getCheckValue;
	this.setCheckValue = floder_setCheckValue;
}



// ȡ���˵���Ϣ
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

// չ���ڵ��µ�������ǰ�򿪵Ľڵ�
// idx �еı��
function floder_expandSubMenu( idx )
{
	if( idx == null || idx < 1 ) idx = 1;
	
	// �˵���Ϣ
	var rootMenu = this.getRootMenu();
	
	// �ж��Ƿ���Ҫ��������
	if( this.parentNode != rootMenu && rootMenu.loadFloderFunc != null ){
		if( this.items.length == 0 ){
			// ��û�������ӽڵ�
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

// ���ӽڵ�
function _node_addCell(rootMenu, menuNode, rowid)
{
	rootMenu.expandNodeNumber ++;
	var h = menuNode.displayItem();
	var rootId = rootMenu.rootNode;
	setTimeout( function(){rootMenu.addCell(h, menuNode, rowid, rootId)}, 5 );
}


// �������в��ǵ�ǰ����ڵ��Ŀ¼�ڵ�
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

// ����check��־
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

// ��ȡcheck��־
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

// ����ÿ���ڵ��ѡ���־
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
*   list ����ʾ-/+                                                            *
*   tree ��ʾ-/+                                                              *
*   check ��ʾ��ѡ��                                                          *
*   listbar ����outlook�Ĺ�����                                               *
******************************************************************************/

function MTMenu(name, xtype)
{
	// ����
	if( xtype == 'listbar' ){
		this.xtype = xtype;
		
		// �Ƿ��Զ��ر������Ѿ��򿪵�Ŀ¼
		this.MTMSubsAutoClose = true;
	}
	else if( xtype != 'list' && xtype != 'check' ){
		this.xtype = 'tree';
		
		// �Ƿ��Զ��ر������Ѿ��򿪵�Ŀ¼
		this.MTMSubsAutoClose = false;
	}
	else{
		this.xtype = xtype;
		
		// �Ƿ��Զ��ر������Ѿ��򿪵�Ŀ¼
		this.MTMSubsAutoClose = false;
	}
	
	// �Ƿ����ڳ�ʼ��
	this.initFlag = false;
	
	// �Ƿ���ڵ�չ��������������false=���еĽڵ㶼չ����true=ͨ�����������չ��
	this.expandFlag = true;
	
	// ������
	this.onMouseEventFunction = null;
	
	// ���˵���״̬�ı�ʱ������������������Ա���˵���״̬��
	this.onMenuStatusChange = null;
	
	// ��ҳ
	this.MTHomePage = null;
	
	// Ŀ��ҳ������
	this.MTMDefaultTarget = 'main';
	
	// �˵�������ĵ�:���ɲ˵������Ͳ˵���ʾ����������ҳ����
	this.menuWindow = window;
	
	// TABLE���
	this.menuTable = null;
	
	// �ȴ�չ���Ľڵ�����:�ڲ�ʹ�ã���չ���˵���ʱ���ֹ�ٴ�չ���˵��������˵�
	this.expandNodeNumber = 0;
	
	// �ָ���ĸ߶� �� ��ɫ
	this.splitHeight = 0;
	this.splitColor = 0;
	
	// listbar�ĵ�һ���˵��м��
	this.rowHeight1 = 28;
	this.rowheight2 = 19;
	
	// �˵�����ʽ
	this.rootNodeStyle = null;		// ���ڵ����ʽ
	this.nodeStyle = null;			// ��֧�ڵ����ʽ
	this.nodeActiveStyle = null;	// ��ķ�֧�ڵ����ʽ
	this.nodeSelectedStyle = null;	// ѡ�еķ�֧�ڵ����ʽ
	this.itemStyle = null;			// ���ܽڵ����ʽ
	this.itemActiveStyle = null;	// ��Ĺ��ܽڵ����ʽ
	this.itemSelectedStyle = null;	// ѡ�еĹ��ܽڵ����ʽ
	
	// ѡ�����ִ������� ���� ѡ������[<tr>]����������[check]��[tree]��֧���������
	this.selectRowEventFlag = false;
	this.getSelectRowEventFlag = menu_getSelectRowEventFlag;
	
	// MTMenuIcons ͼ����Ϣ
	this.menuIcons = new MTMfloderIcon( this.xtype );
	
	this.menuName = name;
	this.parentNode = null;
	this.expanded   = false;
	this.submenu = new MTFloder( this );
	this.addSubItem = menu_addSubItem;
	
	// ���һ�εĲ˵��ڵ�
	this.cur_menuNode = null;
	this.lookupItem = menu_lookupSubItem;
	
	// �����ڲ���Ų��ҽڵ㣬�ڲ�ʹ��
	this.lookupItemByNumber = menu_lookupItemByNumber;
	
	// ���ڵ㣬���ø��ڵ�
	this.rootNode = this;
	this.setRootNode = menu_setRootNode;
	
	// ȡ�������Ƶĺ���:�������ܲ��ڵ�ǰ�����ж���
	this.getMenuName = menu_getMenuName;
	
	// ���û�ڵ� �� ���
	this.selectedNode = null;
	this.setSelectedNode = menu_setSelectedNode;		// ����ѡ�еĲ˵���
	this.setMenuNodeStyle = menu_setMenuNodeStyle;		// ���ò˵������ʽ
	
	// ������
	this.setMenuType = menu_setMenuType;			// ���ò˵�����ʽ
	this.addItem = menu_MTMAddItem;					// ���Ӳ˵���ڽṹ�У�
	this.addCell = menu_MTMAddCell;					// ���Ӳ˵��ڵ㣨����Ļ�ϣ�
	this.makeImage = menu_MTMakeImage;				// ����ͼƬ
	this.startMenu = menu_MTMStartMenu;				// ��ʼ���ɲ˵�
	this.expand = menu_expandFloder;
	
	// ��괦����
	this.mouseEventProcessFlag = false;				// �Ƿ����ڴ�������¼�����������������¼�ͬʱ����
	this.mouseEventProcess = menu_mouseEventProcess;
	this.processFloderNode = menu_processFloderNode;
	
	// �������нڵ�ĺ���
	this.processNode = menu_processNode;
	
	// �����˵��¼�
	this.fireEvent = menu_fireEvent;
	
	// ��������һ��ҳ��
	this.goPage = menu_goPage;
	
	// check��ʽ�Ľ��
	this.processCheckbox = menu_processCheckbox;
	this.setCheckValue = menu_setCheckValue;
	this.getCheckValue = menu_getCheckValue;
	
	// �ָ��˵���״̬�����ߴ�ԭ������Ĳ˵��лָ����в˵��ڵ��״̬
	this.restoreStatus = menu_restoreStatus;
	
	// �Ƿ��ǵ�ǰ�˵��µ���Ԫ�أ�ҳ���Ͽ����������˵�ʹ����ͬ����Դ
	this.isChildElement = menu_isChildElement;
	
	// �������ݵĺ���
	this.loadFloderFunc = null;
}

// ȡ�˵��������Ƶĺ���:����˵��ı��������������ж��壬����Ҫʵ���������
function menu_getMenuName()
{
	return this.menuName;
}

// ѡ�����ִ������� ���� ѡ������[<tr>]��������
function menu_getSelectRowEventFlag()
{
	if( this.xtype == 'tree' || this.xtype == 'check' ){
		return false;
	}
	else{
		return this.selectRowEventFlag;
	}
}

// ���ø��ڵ�
function menu_setRootNode( menuId )
{
	if( menuId == null || menuId == '' || menuId == 'root' ){
		var _rootNode = this;
	}
	else{
		var _rootNode = this.lookupItem( menuId );
		if(_rootNode == null ){
			alert( '�˵�����û���ҵ��˵��ڵ� --> ' + menuId );
			_rootNode = this;
		}
	}
	
	if( (this.initFlag || this.expandNodeNumber > 0) && this.rootNode == _rootNode ){
		alert( '�벻��Ƶ��ˢ��ҳ��' );
		return false;
	}
	
	this.rootNode = _rootNode;
	return true;
}

// ���ò˵�����ʽ
function menu_setMenuType( xtype )
{
	if( this.xtype == xtype ){
		return;
	}
	
	if( xtype == 'listbar' ){
		this.xtype = xtype;
		
		// �Ƿ��Զ��ر������Ѿ��򿪵�Ŀ¼
		this.MTMSubsAutoClose = true;
	}
	else if( xtype != 'list' && xtype != 'check' ){
		this.xtype = 'tree';
		
		// �Ƿ��Զ��ر������Ѿ��򿪵�Ŀ¼
		this.MTMSubsAutoClose = false;
	}
	else{
		this.xtype = xtype;
		
		// �Ƿ��Զ��ر������Ѿ��򿪵�Ŀ¼
		this.MTMSubsAutoClose = false;
	}
	
	// �������нڵ������
	if( this.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			items[ii].setMenuType( this.xtype );
		}
	}
	
	// �޸�ͼ��
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

// ���ӽڵ�
function menu_addSubItem( menuNode )
{
	// ����Ŀ¼�ڵ��ͼ��
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

// ���ҽڵ�
function menu_lookupSubItem( menuId )
{
	var ii;
	var item;
	
	// ��������ʵĽڵ��²���
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
	
	// ����Ŀ¼
	for( ii=0; ii<this.submenu.items.length; ii++ ){
		item = this.submenu.items[ii].lookupItem( menuId );
		if( item != null ){
			this.cur_menuNode = item;
			return item;
		}
	}
	
	return null;
}


// �����ڲ���Ų��ҽڵ�
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


// ���Ӳ˵��ڵ�
function menu_MTMAddItem( pid, text, url, target, tooltip, icon, openIcon, id )
{
	// text, url, target, tooltip, icon, openIcon, id
	var obj = new MTMenuItem(text, url, target, tooltip, icon, openIcon, id);
	
	// ���ӽڵ�
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
	
	// �ж��Ƿ�����չ��
	if( this.initFlag || this.expandNodeNumber > 0 ){
		var mm = this;
		setTimeout( function(){mm.startMenu(flag, startMenuId);}, 10 );
		return;
	}
	
	// ���ø��ڵ�
	if( startMenuId != null ){
		if( this.setRootNode(startMenuId) == false ) return;
	}
	
	this.initFlag = true;
	
	// ȡtable
	this.menuTable = this.menuWindow.document.getElementById(this.menuName);
	
	// ����TABLE����ʽ
	this.menuTable.cellSpacing='0';
	this.menuTable.cellPadding='0';
	this.menuTable.border='0';
	this.menuTable.style.tableLayout = 'fixed';
	this.menuTable.onselectstart = function(){ return false; };
	
	// ��ղ˵�����
	if( _browse.DOMable || _browse.browserType == "IE" ) {
		while(this.menuTable.rows.length > 0) {
			this.menuTable.deleteRow(0);
		}
	}
	
	// ���ɸ��ڵ�
	var	str = "";
	if( this.rootNode == this ){
		// ���ɸ�
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
		// ���ɸ�
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
	
	// ���ɸ�
	this.expandNodeNumber ++;
	this.addCell( str );
	
	// �����Ӳ˵�
	if( this.rootNode.submenu != null ){
		this.rootNode.submenu.expandSubMenu();
	}
	
	// ��ʼ������
	this.expanded = true;
	this.initFlag = false;
	
	// ��պ���
	var menuDefine = this;
	_browse._onunload.push( function(){_menu_release(menuDefine)} );
}


// չ���ڵ�
function menu_expandFloder( nodeNumber, flag )
{
	// ���ҽڵ�
	var node = this.lookupItemByNumber( nodeNumber );
	if( node == null ){
		return;
	}
	
	node.expand( flag );
}


// menu��list��ʾ������ͼƬ��listbar����ʾ����Ҫ�޸�
function menu_MTMakeImage(thisImage) 
{
	if( thisImage == null || thisImage == "" ){
		return "";
	}
	
	return '<img src="' + this.menuIcons.getFullName(thisImage) + '" width="18px" height="18px" align="left" border="0" vspace="0" hspace="0">';
}

// �����У�����list��ʽ����һ�еĸ߶���Ҫ���õıȽ�С
// rowid �к�
// rootNode �����жϲ˵��Ĺؽڵ��Ƿ��Ѿ��仯����Ϊչ�����첽��
function menu_MTMAddCell(thisHTML, node, rowid, rootNode)
{
	// �жϹؽڵ��Ƿ��Ѿ��仯
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
		
		// ����<TR>
		var myRow = this.menuTable.insertRow(rowid);
		myRow.id = (node == null) ? 't0' : node.number;
		myRow.name = (node == null || node.parentNode == null || node.parentNode.parentNode == null) ? 't0' : node.parentNode.number;
		
		// ����<TD>
		var myCell = myRow.insertCell(myRow.cells.length);
		myCell.noWrap = true;
		myCell.innerHTML = thisHTML;
		myCell.vAlign = "bottom";
		
		// �����¼�������
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
		
		// ������ʽ
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
		
		// listbar,���ñ�����
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
	
	// �ָ��
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
	
	// ����checkbox��״̬
	if( node != null ){
		node.setCheckFlag();
		
		// �Ƿ��ڵ�
		if( node == this.selectedNode ){
			this.setSelectedNode( node );
		}
	}
	
	// չ���˵����
	this.expandNodeNumber --;
}


// ��ڵ����ʽ
function menu_setSelectedNode( node )
{
	// ȡ�ڵ�ı��
	var nodeID = (node == null) ? null : node.number;
	
	// ǰһ����ڵ�ı��
	var selectedNodeID = (this.selectedNode == null) ? null : this.selectedNode.number;
	
	// �������еĽڵ�Ϊû��ѡ��״̬
	var src = null;
	for( var i=0; i<this.menuTable.rows.length; i++ ){
		var row = this.menuTable.rows[i];
		if( row.id == selectedNodeID ){
			this.setMenuNodeStyle( row, 0 );
			
			// ���ýڵ��״̬
			var item = this.lookupItemByNumber( row.id );
			if( item != null ){
				item.setActive( false );
			}
		}
		
		// �ж��Ƿ�ѡ�еĲ˵���
		if( row.id == nodeID ){
			src = row;
		}
	}
	
	// ����ѡ�еĽڵ�
	if( this.selectedNode != node ){
		var nd1 = this.selectedNode;
		this.selectedNode = node;
		
		// ����ԭ��ѡ�еĽڵ����ʽ
		if( nd1 != null ){
			this.setMenuNodeStyle( nd1, 0 );
		}
	}
	
	if( src != null ){
		// ������ʽ
		this.setMenuNodeStyle( node, 0 );
				
		// ���ýڵ��״̬
		node.setActive( true );
	}
}


// ���ò˵��ڵ��״̬: nodeStatus��[0=����״̬��1=�״̬��2=ѡ��״̬]
function menu_setMenuNodeStyle( row, nodeStatus )
{
	if( row.constructor == MTMenuItem ){
		var node = row;
		
		// ȡ<TR>
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
	
	// �ж��Ƿ�
	if( this.selectedNode == node ){
		nodeStatus = 2;
	}
	
	// ��Ҫ������ʽ�Ķ���
	var styleObject = null;
	if( this.getSelectRowEventFlag() == false ){
		styleObject = row.lastChild.lastChild;
	}
	else{
		styleObject = row;
	}
		
	// ��ʽ��������
	var className = null;
	if( node != null ){
		// ȡ��ʽ
		// flag��[0=����״̬��1=�״̬��2=ѡ��״̬]
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
		
		// ������ʽ
		if( className != null && className != '' && styleObject.className != className ){
			styleObject.className = className;
			
			// �����е���ʽ������ˢ��<SPAN>�����򲻻���ʾ����
			if( styleObject != row ){
				row.className = '';
			}
		}
	}
	
	// ������ɫ
	if( nodeStatus == 0 || className == null || className == '' ){
		if( nodeStatus == 0 ){
			var color = '';
		}
		else{
			var color = 'blue';
		}
		
		// ������ʽ
		styleObject.style.color = color;
	}
}



// check��ʽ�Ľ��
function menu_setCheckValue( value )
{
	if( this.xtype != 'check' ) return;
	
	// ��������
	value = (value == null || value == '') ? '' : ';' + value.replace(/,/g, ';') + ';';
	this.submenu.setCheckValue( value );
	
	// ����checkbox��״̬
	var objs = this.menuWindow.document.getElementsByName( this.menuName + '_check' );
	for( var ii=0; ii<objs.length; ii++ ){
		var node = this.lookupItem( objs[ii].id );
		if( node != null ){
			node.setCheckFlag();
		}
	}
}

// ȡ���
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


// �����¼�
function menu_fireEvent( menuId, eventName )
{
	if( eventName == null ){
		eventName = 'onclick';
	}
	
	// ��ʼ������Ժ�ִ��
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
	
	// չ��Ŀ¼
	if( nd.parentNode.expanded == false ){
		nd.parentNode.processFloderNode();
		
		// �ȴ�Ŀ¼չ��
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
	
	// �����¼� �� �жϴ���<A>���¼� ���� <TR>���¼�
	if( this.getSelectRowEventFlag() ){
		// �����еĶ���
		row.fireEvent( eventName );
	}
	else{
		var	col = row.lastChild;
		var	a = col.lastChild;
		a.fireEvent( eventName );
		
		// ����ҳ
		if( eventName == 'onclick' ){
			// ����ҳ��
			this.goPage( nd.url, nd.target, nd.text );
		}
	}
}


// ���ĵ�����¼�
function menu_mouseEventProcess( eventName )
{
	 
	// ��������������¼�ͬʱ����
	if( this.mouseEventProcessFlag ) return;
	this.mouseEventProcessFlag = true;
	
	// ��ȡ<TR>
	var	src = this.menuWindow.event.srcElement;
	while( src.tagName.toUpperCase() != 'TR' ){
		src = src.parentNode;
	}
	//alert(src.id)
	// �ж��Ƿ���ڵ�
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
				// ����ѡ�еĽڵ�
				this.setSelectedNode( null );
				
				// ����ҳ��
				this.goPage( xurl, target );
			}
		}
		
		this.mouseEventProcessFlag = false;
		return;
	}
	
	// find node
	var	nd = this.lookupItemByNumber( src.id );
	
	// ���õ�ǰѡ�еĲ˵�
	if( eventName == 'onclick' ){
		this.setSelectedNode( nd );
	}
	
	if( eventName == 'onmouseout' ){
		parent.status = '��ӭʹ��';
		
		// ���ó�������ʽ
		this.setMenuNodeStyle( nd, 0 );
	}
	else if( eventName == 'onmouseover' ){
		var	exn = 'ִ��';
		if( nd.submenu != null ){
			exn = (nd.expanded) ? '�۵�' : 'չ��';
		}
		
		if( nd.tooltip == null || nd.tooltip == '' ){
			nd.tooltip = nd.text;
		}
		
		parent.status = exn + '[' + nd.id + '][' + nd.tooltip + ']';
		
		// ���óɻ��ʽ
		this.setMenuNodeStyle( nd, 1 );
	}
	
	// �Ƿ����չ��Ŀ¼
	var rc = false;

	// ִ�й���
	if( this.onMouseEventFunction != null ){
		if( typeof(this.onMouseEventFunction) == 'string' ){
			var	func = this.onMouseEventFunction + "('" + eventName + "', nd, src )";
			var rc = eval( func );
		}
		else{
			var rc = this.onMouseEventFunction( eventName, nd, src );
		}
	}
	
	// �Ƿ�չ��Ŀ¼
	if( eventName == 'onclick' || eventName == 'ondblclick' ){
		if( nd.submenu != null ){
			// �Ƿ��Զ����������Ľڵ�
			if( this.expandNodeNumber == 0 ){
				if( nd.expanded != true && this.MTMSubsAutoClose ){
					this.rootNode.submenu.closeSubs(nd);
				}
				
				// ����Ŀ¼
				if( nd.expanded != true || eventName == 'ondblclick' || (rc != true && nd.url == '') ){
					nd.processFloderNode();
				}
			}
		}
		else if( this.xtype == 'check' ){
			// ����check״̬
			var obj = this.menuWindow.document.getElementById(nd.id);
			if( obj != null ){
				obj.checked = !obj.checked;
				this.processCheckbox( obj );
			}
		}
	}
	
	// �˵���״̬�Ѿ��޸�
	if( this.onMenuStatusChange != null && (eventName == 'onclick' || eventName == 'ondblclick') ){
		if( typeof(this.onMenuStatusChange) == 'string' ){
			eval( this.onMenuStatusChange + "('" + this.menuName + "')" );
		}
		else{
			this.onMenuStatusChange( this.menuName );
		}
	}
	
	// ����ҳ��
	if( eventName == 'onclick' || eventName == 'ondblclick' ){
		this.goPage( nd.url, nd.target, nd.text );
	}
	
	// ��괦�����
	this.mouseEventProcessFlag = false;
}

// +/-ͼ�������¼�
function menu_processFloderNode()
{
	// ��֧��չ�� ���� ����չ���˵�ʱ����ֹ�������
	if( this.expandFlag == false || this.expandNodeNumber > 0 ) return;
	
	// find node
	var	src = this.menuWindow.event.srcElement;
	var	nd = this.lookupItemByNumber( src.parentNode.parentNode.id );
	
	if( nd.submenu != null ){
		// �Ƿ��Զ����������Ľڵ�
		if( !nd.expanded && this.MTMSubsAutoClose ){
			this.rootNode.submenu.closeSubs(nd);
		}
		
		// ����Ŀ¼
		nd.processFloderNode();
	}
}

// ����Ŀ¼
function _menu_processFloderNode( id )
{
	
}

// ����checkbox
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
	
	// �����ϼ��ڵ��״̬
	node = node.parentNode;
	while( node.parentNode != null ){
		node.setCheckFlag();
		node = node.parentNode;
	}
	 
}

// ͨ���ص������������нڵ�
function menu_processNode( callback )
{
	for( var ii=0; ii<this.submenu.items.length; ii++ ){
		this.submenu.items[ii].processNode( callback );
	}
}


// �ָ��˵���״̬�����ߴ�ԭ������Ĳ˵��лָ����в˵��ڵ��״̬
function menu_restoreStatus( menuStatus )
{
	// ��ǰѡ�еĽڵ�
	if( menuStatus.selectedNode != null ){
		var nid = menuStatus.selectedNode.id;
		this.selectedNode = this.lookupItem( nid );
	}
	
	// �����ӽڵ��״̬
	if( this.submenu != null && menuStatus.submenu != null ){
		var items = this.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			// ���ҽڵ�
			for( var jj=0; jj<menuStatus.submenu.items.length; jj++ ){
				var node = menuStatus.submenu.items[jj];
				if( node.id == items[ii].id ){
					// �ָ��ڵ�
					items[ii].restoreStatus( node );
					break;
				}
			}
		}
	}
}

	
// �Ƿ��ǵ�ǰ�˵��µ���Ԫ�أ�ҳ���Ͽ����������˵�ʹ����ͬ����Դ
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


// ��ת��<a ...></a>ָ����ҳ��
function menu_goPage( xurl, target, description )
{
	// �Ƿ�ָ����ҳ���ַ
	if( xurl == null || xurl == '' ){
		return;
	}
	
	if( target == '' ){
		target = this.MTMDefaultTarget;
	}
	
	// ȡ������
	var tmp = _getHrefParameter( xurl, 'page' );
	if( tmp != null ){
		var txnCode = _getTxnCodeFromHref( tmp );
		if( txnCode != null ){
			// ����
			var txnName = _getHrefParameter( xurl, 'cmd' );
			ptr = txnName.lastIndexOf( '/' );
			if( ptr > 0 ){
				txnName = txnName.substring( ptr+1 );
			}
			
			if( description != null && description != txnName ){
				txnName = description + '-' + txnName;
			}
			
			// ��ˮ��
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
	
	// �ӳٴ���
	navigateTo( this.menuWindow, xurl, target, "resizable,scrollbars" );
}

// �ͷ�DOM����
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
