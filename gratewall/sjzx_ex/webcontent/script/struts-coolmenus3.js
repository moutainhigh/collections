/**************
Copyright (c) 2001 Thomas Brattli (www.dhtmlcentral.com)

eXperience DHTML coolMenus - Get it at  www.dhtmlcentral.com
Version 3.02
This script can be used freely as long as all copyright messages are
intact. 

(You can delete the comments below to save space)

This script takes over for the old Coolmenus2 and CoolFrameMenu

Visit www.dhtmlcentral.com/coolmenus/ 
for the latest version of the script.

Tutorial: http://www.dhtmlcentral.com/tutorial.asp

Support: http://www.dhtmlcentral.com/forums/forum.asp?FORUM_ID=2&CAT_ID=1&Forum_Title=CoolMenus

Known bugs:
Netscape 6: When using padding in the layers you can sometimes get
strange visual effects on the lowest menu item. Only way to fix is to not use padding. 

Opera: This menu is very close to working on Opera, but as far as I could 
figure out opera don't support innerHTML or document.createElement() which
makes the changing of the text inside the submenus immpossible. If anyone 
know a solution to this please let me know.

Explorer 4 for mac: It will not work in this browser, nothing does. 

Explorer 5 for mac: It works fine, but like Netscape 6 it's a little slow and you can get strange visual effects sometimes. 

Script checked and working with:
PC: 
Netscape 4.03 - Netscape 4.04 -Netscape 4.08 - Netscape 4.73 - Netscape 6 - Netscape 6.01
Internet Explorer 5.0 - Internet Explorer 5.5 -Internet Explorer 6.0
MAC:
Netscape 4 - Explorer 5

Btw: There is basically just one explanation to why this code is sort of "scrambled": I wanted this file be as small as possible..
If you want it to be smaller feel free to remove all comments (except for the copyright)
**************/

/*************
Pageobject
***************/
function makePageCoords(win,fr){
	if(!win) win=window
	this.x=0;this.x2=(bw.ns4 || bw.ns6)?win.innerWidth-1:win.document.body.offsetWidth;
	if(!fr&&bw.ie) this.x2-=20; else if(!fr&&bw.ns4) this.x2-=4; else if(bw.ns6) this.x2+=1
	this.y=0;this.y2=(bw.ns4 || bw.ns6)?win.innerHeight:win.document.body.offsetHeight;
	if(bw.ns4 && win.aspect == 'list') this.x2+=5; if(!fr&&bw.ie) this.y2-=4; else if(bw.ns4&&fr) this.y2+=4
	this.y2orig=this.y2; this.x50=this.x2/2; this.y50=this.y2/2; return this;
}
/*************
Debugging function
***************/
function debug(txt,ev){if(mDebugging==2) self.status=txt; else alert(txt); if(ev) eval(ev); return false}
/************
Scroll function
*************/
function cm_checkScrolled(obj){
	if(bw.ns4 || bw.ns6) obj.scrolledY=obj.win.pageYOffset
	else obj.scrolledY=obj.win.document.body.scrollTop
	if(obj.scrolledY!=obj.lastScrolled){
		if(!obj.useframes){
			for(i=0;i<obj.l[0].num;i++){var sobj=obj.l[0].o[i].oBorder; sobj.moveY(sobj.y+(obj.scrolledY-obj.lastScrolled))}
			if(obj.usebar) obj.oBar.moveY(obj.oBar.y+(obj.scrolledY-obj.lastScrolled))
		}
		obj.lastScrolled=obj.scrolledY; page.y=obj.scrolledY; page.y2=page.y2orig+obj.scrolledY
		if(!obj.useframes || bw.ie){ clearTimeout(obj.tim); obj.isover=0; obj.hideSubs(1,0)}
	}if((bw.ns4 || bw.ns6) && !obj.useframes) setTimeout("cm_checkScrolled("+obj.name+")",200)
}
/***********************
Checking if the values are % or not.
***************/
function cm_checkp(num,w,check,istop,ds){
	if(num){ var p=istop?toppage:page
		if(num.toString().indexOf("%")!=-1){if(w || (check && this.aspect=='menu')) num=(p.x2*parseFloat(num)/100)
		else num=(p.y2*parseFloat(num)/100)
		}else num=eval(num)
	}else num=0; return num
}
/************
Making DIV objects + DIV objects code
*************/
function cm_makeObj(obj,name,level,win,nest,o){
	if(o&&(bw.ns4||bw.ns6)) this.evnt=o
	else this.evnt=bw.dom?win.document.getElementById(obj):bw.ie4?win.document.all[obj]:bw.ns4?nest?win.document[nest].document[obj]:win.document[obj]:0;
	if(!this.evnt) return debug('There seems to be an error with this layer:\nFrame: '+win+'\nLayer: '+nest + "." + obj)
	this.css=bw.dom||bw.ie4?this.evnt.style:bw.ns4?this.evnt:0;	
	this.ref=bw.dom || bw.ie4?win.document:bw.ns4?this.css.document:0;
	this.hideIt=cm_hideIt; 
	this.showIt=cm_showIt; 
	this.writeIt=cm_writeIt; 
	this.setactive=cm_setactive; 
	this.addEvents=cm_addEvents; 
	this.moveIt=cm_moveIt; 
	this.clipTo=cm_clipTo; 
	if(name) this.parent=name; 
	this.moveY=cm_moveY; 
	this.l=level; 
	this.clipOut=cm_clipOut; 
	this.filterIt=cm_filterIt; 
	this.inMenu = cm_inMenu;
	this.obj = obj + "Object"; 	
	eval(this.obj + "=this"); 
	this.tim=10; 
	this.clipy=0; 
	return this
}

function cm_writeIt(text)
{
	if(!this.img1){
		if(bw.ns4){
			this.ref.write(text);
			this.ref.close();
		}
		else{
			// alert( this.evnt.parentNode.innerHTML );
			this.evnt.innerHTML=text;
		}
	}
}


// 判断座标是否在菜单里面
function cm_inMenu( x, y )
{
	if( this.evnt == null ){
		return false;
	}
	
	var	obj = this.evnt;
	var	px = obj.offsetLeft;
	var	py = obj.offsetTop;
	
	while( obj = obj.offsetParent ){
		px += obj.offsetLeft;
		py += obj.offsetTop;
	}
	
	var	wt = this.evnt.clientWidth;
	var	ht = this.evnt.clientHeight;
	
	if( px < x && px + wt > x && py < y && py + ht > y ){
		return true;
	}
	else{
		return false;
	}
}


function cm_moveY(y){this.y=y; this.css.top=y}
function cm_moveIt(x,y){this.x=x; this.y=y; this.css.left=this.x;this.css.top=this.y}
function cm_showIt(){this.css.visibility="visible"; this.vis=1}; 
function cm_hideIt(){this.css.visibility="hidden"; this.vis=0}
function cm_clipOut(px,w,ystop,tim,name){
	if(!this.vis) return; if(this.clipy<ystop-px){this.clipy+=px; this.clipTo(0,w,this.clipy,0,1)
		this.tim=setTimeout(this.obj+".clipOut("+px+","+w+","+ystop+","+tim+",'"+name+"')",tim)
	}else{if(bw.ns6){this.hideIt();}; this.clipTo(0,w,ystop,0,1); if(bw.ns6){this.showIt()}}
}
function cm_filterIt(f){if(this.evnt.filters[0]) this.evnt.filters[0].Stop(); else this.css.filter=f; this.evnt.filters[0].Apply(); this.showIt(); this.evnt.filters[0].Play();}

function cm_setactive(on,name,frmmouse){
	if(!name) name=this.name; var tobj=this.parent.m[name]
	if(tobj.img){
		if(tobj.img2){
			if(on){
				var color=tobj.c2; 
				this.ref.images[tobj.img].src=tobj.img2;
			}
			else{
				var color=tobj.c1; 
				this.ref.images[tobj.img].src=tobj.img1
			}
		}
	}
	
	if(on){
		var color=tobj.c2; 
		var fcolor=tobj.c4; 
		var re=tobj.c3
	}
	else{
		var color=tobj.c1; 
		var fcolor=tobj.c3; 
		var re=tobj.c4
	}
	
	if(color){
		if(bw.dom || bw.ie4){
			this.css.backgroundColor=color;
		}
		else if(bw.ns4){
			if(color=="transparent"){
				color=null;
			}
			
			this.css.bgColor=color
		}
	}
	
	if(fcolor && !bw.ns4){
		var	obj = document.getElementById( 'td_' + name );
		if( obj == null ){
			obj = this.evnt;
		}
		
		if(bw.ie4) obj.style.color=fcolor; 
		else if(this.evnt.childNodes[0]) obj.style.color=fcolor
	}
	else if(fcolor&&frmmouse){
		t=this.parent.m[name].text; 
		t=t.replace(re,fcolor); 
		this.writeIt(t); 
		if(on) this.addEvents(name,this.parent.name,tobj.lnk,this.parent.useclick)
	}
	
	if(tobj.l==0&&bw.ns6){
		this.parent.l[0].o[tobj.num].oBorder.hideIt(); 
		this.parent.l[0].o[tobj.num].oBorder.showIt();
	} //Stupid fix for netscape 6....
}


function cm_clipTo(t,r,b,l,w){
	if(bw.ns4){
		this.css.clip.top=t;
		this.css.clip.right=r; 
		this.css.clip.bottom=b;
		this.css.clip.left=l;
	}
	else{
		this.css.clip="rect("+t+","+r+","+b+","+l+")"; 
		if(w){
			this.css.width=r; 
			this.css.height=b
		}
	};
	this.width=r; 
	this.height=b;
}


function cm_addEvents(n,name,url,useclick)
{
	this.evnt.onmouseover=new Function(name+".mover('"+n+"')"); 
	this.evnt.onmouseout=new Function(name+".mmout('"+n+"')")
	if(!url && useclick) ev=new Function(name+".mover('"+n+"',1)")
	else ev=new Function(name+".go('"+n+"')")
	if(bw.ns4){this.ref.captureEvents(Event.MOUSEDOWN); this.ref.onmousedown=ev}
	else this.evnt.onclick=ev
}
/************
Making menu object
*************/
function cm_makeMenu(name,parent,text,link,target,width,height,img1,img2,onclick,onmouseover,onmouseout)
{
	var tt;
	this.m[name]=new Object(); 
	var obj=this.m[name]; 
	obj.name=name;
	obj.subs=new Array(); 
	obj.parent=parent; 
	obj.lnk=(link==0||link=='')?"":link; 
	obj.target=target;
	
	if (link && link != "" ){
		if (link.toLowerCase().indexOf("http://") == 0 || link.toLowerCase().indexOf("ftp://") == 0)
		{
			;
		}
		else if (link.toLowerCase().lastIndexOf(".do") == link.length - 3)
		{
			link = rootPath + link;
		}
		else if (link.indexOf("/") == 0 && link.toLowerCase().lastIndexOf(".jsp") == link.length - 4)
		{
			link = rootPath + link;
		}
		else if (link.indexOf("/") != 0 && link.toLowerCase().lastIndexOf(".jsp") == link.length - 4)
		{
			link = rootPath + menuPath + link;
		}
	}
	if ( img1 && img1!="" ){
		if( img1.substring('/') != 0 ){
			img1 = '/' + img1;
		}
		
		img1 = rootPath + img1;
	}
	if ( img2 && img2!="" ){
		if( img2.substring('/') != 0 ){
			img2 = '/' + img2;
		}
		
		img2 = rootPath + img2;
	}

	if(parent!="" && parent){
		this.m[parent].subs[this.m[parent].subs.length]=name; 
		l=this.m[parent].l+1
	}
	else l=0;
	
	obj.l=l; 
	prop1=l<this.level.length?this.level[l]:this.level[this.level.length-1]; 
	prop2=this.level[0];
	
	if(this.l.length<=l){
		this.l[l]=new Object(); 
		this.l[l].num=0; 
		if(l==0) this.l[l].names=new Array()
		this.l[l].clip=prop1["clip"]||prop2["clip"]||0; 
		this.l[l].clippx=prop1["clippx"]||prop2["clippx"]||0
		this.l[l].cliptim=prop1["cliptim"]||prop2["cliptim"]||0; 
		this.l[l].filter=prop1["filter"]||prop2["filter"]||0
		this.l[l].border=prop1["border"]||prop2["border"]; 
		this.l[l].maxnum=0
		this.l[l].bordercolor=prop1["bordercolor"]||prop2["bordercolor"];
		s=prop1["align"]||prop2["align"]; 
		if(s=="left") s=1; 
		else if(s=="right") s=0; 
		else if(s=="top") s=3; 
		else if(s=="bottom") s=2; 
		this.l[l].align=s; 
		this.aobj[l]=-1;
		this.l[l].height=prop1["height"]||prop2["height"]; 
		this.l[l].width=prop1["width"]||prop2["width"];
		this.l[l].style=prop1["style"]||prop2["style"]; 
		this.l[l].tc=cmTxtColor||prop1.textcolor||prop2.textcolor; 
		this.l[l].offsetX=String(prop1["offsetX"])!="undefined"?prop1["offsetX"]:prop2["offsetX"]
		this.l[l].offsetY=String(prop1["offsetY"])!="undefined"?prop1["offsetY"]:prop2["offsetY"]
	}
	
	if(l==0) this.l[l].names[this.l[l].names.length]=name
	if(parent!="" && parent){
		obj.num=this.m[parent].subs.length-1
	}
	else obj.num=this.l[l].num
	this.l[l].num++; 
	prop=l<this.level.length?this.level[l]:this.level[this.level.length-1]
	obj.width=this.checkp(width?width:prop1.width?prop1.width:prop2.width,1,0,1);
	obj.height=this.checkp(height?height:prop1.height?prop1.height:prop2.height,0,0,1);
	
	if(parent!="" && parent){
		if(this.m[parent].subs.length>this.l[l].maxnum) this.l[l].maxnum=this.m[parent].subs.length 
		if(this.m[parent].totheight==0) this.m[parent].totheight=this.l[l].border
		this.m[parent].totheight+=obj.height+ this.l[l].border
		if(this.m[parent].maxwidth<obj.width) this.m[parent].maxwidth=obj.width+this.l[l].border*2
	}
	else{
		this.l[l].maxnum=this.l[l].names.length; 
		this.totwidth+=obj.width; 
		this.totheight+=obj.height
		this.maxwidth=this.maxwidth>obj.width?this.maxwidth:obj.width; 
		this.maxheight=this.maxheight>obj.height?this.maxwidth:obj.height
		
		// 宽度
		if( this.aspect == 'list' ){
			if( this.barx + this.barwidth < this.fromleft + this.maxwidth ){
				this.barwidth = this.fromleft + this.maxwidth - this.barx;
			}
		}
	}
	
	if(bw.ns4&&this.useNS4links&&(l==0||!this.useframes) ||(l==0&&img1)){
		tt=img1&&l==0?this.useclick?this.name+".mover('"+name+"',1);":this.name+".go('"+name+"');":"";text='<a href="#" onclick="'+tt+'return false" class="clNS4">'+text+'</a>'
	}
	if(img1){
		obj.preimg1=new Image(); 
		obj.preimg1.src=img1
	};
	if(img2){
		obj.preimg2=new Image(); 
		obj.preimg2.src=img2
	}
	if(img2) obj.img="imgCMenu"+name; 
	else obj.img=0; 
	obj.img1=img1||""; 
	obj.img2=img2||""; 
	obj.subx=-1;
	obj.c1=cmBGColorOff||prop1.bgcoloroff||prop2.bgcoloroff; 
	obj.c2=cmBGColorOn||prop1.bgcoloron||prop2.bgcoloron;
	obj.c3=cmTxtColor||this.l[l].tc; 
	obj.c4=cmHoverColor||prop1.hovercolor||prop2.hovercolor;  
	obj.suby=-1;
	obj.mclick=onclick||""; 
	obj.mover=onmouseover||""; 
	obj.mout=onmouseout||"";	
	obj.totheight=0; 
	obj.maxwidth=0; 
	
	// 处理文字，还要处理toolbar
	if(bw.ns4){
		text='<font size="'+(prop1.NS4fontSize||prop2.NS4fontSize)+'" face="'+(prop1.NS4font||prop2.NS4font)+'" color="'+(cmTxtColor||this.l[l].tc)+'">'+text+'</font>'
	}
	
	if(parent!="" && parent){
		text = '<td nowrap id="td_' + name + '" style="' + this.level[l].style + '" width="80%">' + text + '</td>';
		
		if(img1){
			text = '<td align="center" width="10%"><img src="'+img1+'" border="0" name="imgCMenu'+name+'"></td>' + text;
		}
		else{
			text = '<td width="10%"></td>' + text;
		}
	}
	else{
		if(img1){
			text='<img src="'+img1+'" border="0" name="imgCMenu'+name+'">&nbsp;&nbsp;' + text;
		}
	}
	
	obj.text=text; 
}
/************
Onmouseout
*************/
function cm_mout(name,cl){
	if(!name&&cl&&!this.isover){
		this.isclicked=0; 
		this.hideSubs(1,0,0,0,1);  
		this.aobj[0]=-1; 
		return
	}
	
	if(!name) return; 
	var l=this.m[name].l;
	
	if((this.m[name].subs.length==0||!this.loaded)||(this.useclick&&!this.isclicked)){
		if((this.aobj[l+1]==-1||l>=this.l.length-1)&&this.aobj[l]!=-1){
			this.aobj[l].setactive(0,0,1); 
			this.aobj[l]=-1;
		}
	}
	
	if(this.m[name].mout!="") eval(this.m[name].mout)
	if(this.useclick){
		this.isover=0; 
		return
	};
	 
	clearTimeout(this.tim); 
	if(!(!bw.ie&&this.useframes&&l==0&&this.aobj1)){ 
		this.isover=0; 
		this.aobj1=0; 
		this.tim=setTimeout(this.name+".hideSubs(1,0,0,0,1)",this.wait)
	} 
}


function cm_popupMenu( name )
{
	if( window.event == null ){
		return false;
	}
	
	//if( window.event.button != 2 ) return false;
	
	// 取座标
	var	suby;
	var	subx;
	if (document.layers){
		subx = window.event.x;
		suby = window.event.y;
	}
	else{
		subx = window.event.clientX ;
		suby = window.event.clientY;
	}
	
	subx = subx - 3 + document.body.scrollLeft; 
	suby = suby - 3 + document.body.scrollTop;
	
	this.popup( name, subx, suby );
}

function cm_popup( name, subx, suby )
{
	if( isNaN(subx) ){
		subx = -1;
	}
	
	if( isNaN(suby) ){
		suby = -1;
	}
	
	if( suby <= 0 ){
		if( window.event == null ){
			return;
		}
		
		if (document.layers){
			subx = window.event.x;
			suby = window.event.y;
		}
		else{
			subx = window.event.clientX ;
			suby = window.event.clientY;
		}
		
		subx = subx + document.body.scrollLeft;
		suby = suby + document.body.scrollTop;
	}
	
	// 定位座标
	this.m[name].subx = subx; 
	this.m[name].suby = suby;
	this.lastScrolled = document.body.scrollTop;
	this.m[name].scrollY = this.lastScrolled;
	this.isresized = false;
	
	clearTimeout(this.tim); 
	this.isover=1; 
	var l=this.m[name].l;
	if(this.aobj[l].name==name){
		if(this.aobj[l+1]!=-1 && l<this.l.length-1){
			for(i=l+1; i<this.l.length; i++){
				// 判断鼠标是否在菜单的里面
				if( this.l[i].oBorder.inMenu( this.m[name].subx, this.m[name].suby ) ){
					return;
				}
			}
		}
	}
	
	var num=this.m[name].num; 
	var obj=this.l[l].o[num]
	if(this.aobj[l].name!=name){
		if(this.aobj[l]!=-1){
			this.aobj[l].setactive(0,0,1);
		}
		
		this.aobj[l]=obj;
		this.aobj[l].name=name;
		obj.setactive(1,0,1);
	}
	
	if(l==1){
		this.aobj1=1;
		if(l==0 && cl && this.useclick){
			this.isclicked=1;
		}
	}
	
	if(!this.isclicked&&this.useclick){
		return;
	}
	
	if(!this.loaded){
		return;
	}
	
	// 显示
	this.showSubs(name,l,num);
	
	// 判断鼠标是否在菜单中
	if( this.l[l+1].oBorder.inMenu( this.m[name].subx, this.m[name].suby ) == false ){
		this.tim = setTimeout( this.name+".hideSubs(1,1,0,0,1)", this.wait );
	}
}

/************
Onmouseover
*************/
function cm_mover(name,cl){
	clearTimeout(this.tim); this.isover=1; var l=this.m[name].l;
	if(this.aobj[l].name==name){
		if(this.aobj[l+1]!=-1 && l<this.l.length-1){
			this.aobj[l+1].setactive(0,0,1); 
			this.aobj[l+1]=-1; 
			this.hideSubs(l+2,1); 
			return;
		}
		else if((!this.useclick)||(this.useclick&&this.isclicked)) return
	}
	
	if(this.m[name].mover!="") eval(this.m[name].mover)
	
	var num=this.m[name].num; 
	var obj=this.l[l].o[num]
	if(this.aobj[l].name!=name){
		if(this.aobj[l]!=-1) this.aobj[l].setactive(0,0,1); 
		this.aobj[l]=obj; 
		this.aobj[l].name=name; 
		obj.setactive(1,0,1)
	}
	
	if(l==1)this.aobj1=1; 
	if(l==0 && cl && this.useclick) this.isclicked=1; 
	if(!this.isclicked&&this.useclick) return; 
	if(!this.loaded) return;
	
	// 需要重新计算座标
	this.m[name].suby = -1;
	this.showSubs(name,l,num,cl)
}
/************
Hiding subelements
*************/
function cm_hideSubs(l,system,cl,sys2,hc){
	if(this.isover && !system) return
	if(l==1 && this.aobj[0]!=-1&&!sys2){
		this.aobj[0].setactive(0,0,1);
		this.aobj[0]=-1
	}
	
	if(!this.loaded) return; 
	if(cl==1) return;
	
	for(i=l;i<this.l.length;i++){
		if(this.l[i].oBorder.vis==0) continue; 
		this.l[i].oBorder.hideIt(); 
		this.aobj[i]=-1;
	}
	
	if(hc&&this.hcode){
		eval(this.hcode); 
		this.hcode=""
	}
}
/************
Get x/y coords. Only the first time :)
*************/
function cm_getCoords(name,l,num,topalign,align,ln,border,cn,lev1b){
	if(cn==5){ this.m[name].subx=0; this.m[name].suby=0; return }//Just in case infinitive loops
	if(l==1) var pobj=this.l[l-1].o[num].oBorder
	else var pobj=this.l[l-1].oBorder
	var x=pobj.x; var y=pobj.y;
	if(l!=1){y+=this.l[l-1].o[num].y}
	pborder=this.l[l-1].border; 
	lx=x+pobj.width; rx=x-this.m[name].maxwidth- (this.l[l-1].offsetX*2)
	if(align==0){if(l==1){y+=border} if(l==1&&this.useframes) x=0; else x=lx
	}else if(align==1){x=rx; if(l==1){if(this.useframes) x=page.x2 - this.m[name].maxwidth; y+=border}}
	if((align==2||topalign==2)&&lev1b!=3){
		if(l!=1 && (align!=1&&align!=0)){if(topalign==1) x=rx; else x=lx}
		if(l==1) if(this.useframes) y=0; else y+=this.m[name].height+border+pborder;
	}if((align==3||topalign==3)&&lev1b!=2){
		if(l!=1&&align!=1&&align!=0){if(topalign==1) x=rx; else x=lx}
		if(this.useframes&&l==1) y=page.y2 - this.m[name].totheight - this.l[l-1].offsetY*2
		else y-=this.m[name].totheight - this.l[l].offsetY*2; if(l!=1||lev1b==3) y+=this.m[name].height
	}this.m[name].scrollY=this.lastScrolled; this.m[name].subx=x+this.l[l-1].offsetX; 
	this.m[name].suby=y+this.l[l-1].offsetY; if(this.useframes&&l==1&&align!=3) this.m[name].suby+=this.lastScrolled
	if(this.pagecheck&&(l!=1||!this.useframes)) this.checkPage(name,l,num,topalign,align,ln,border,cn)
}
/************
Checking page coords
*************/
function cm_checkPage(name,l,num,topalign,align,ln,border,cn){
	cn++; 
	if(this.m[name].subx+this.m[name].maxwidth>page.x2){
		if(align!=1){if(align==3&&topalign!=0) topalign=3; align=1; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}else if(this.m[name].subx<page.x){
		if(align!=0){if(align==3) topalign=3; align=0; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}else if((this.m[name].suby+this.m[name].totheight)>page.y2){
		if(l==1){topalign=3; this.getCoords(name,l,num,topalign,align,ln,border,cn,3)}
		else if(align!=3){ align=3; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}else if(this.m[name].suby<page.y){
		if(l==1){topalign=2; this.getCoords(name,l,num,topalign,align,ln,border,cn,2)}
		else if(align!=2){align=2; this.getCoords(name,l,num,topalign,align,ln,border,cn)}
	}
}

/************
Showing subelements
*************/
function cm_showSubs(name,l,num,cl)
{
	l+=1; 
	if(l>=this.l.length) return; 
	ln=this.m[name].subs.length
	
	if(ln==0){
		this.hideSubs(l,1,0,1); 
		return
	}
	else this.hideSubs(l+1,1); 
	
	var border=this.l[l].border; 
	this.aobj[l]=-1
	if(this.useframes&&(bw.ns4||bw.ns6)) cm_checkScrolled(this)
	
	if((this.m[name].subx==-1 || this.m[name].suby==-1) || this.m[name].scrollY!=this.lastScrolled || this.isresized){
		var topalign=this.l[0].align; 
		var align=this.l[l-1].align; 
		this.getCoords(name,l,num,topalign,align,ln,border,0)
	}
	
	// 调整座标
	var menuHeight = this.m[name].totheight;
	if( menuHeight > document.body.clientHeight ){
		menuHeight = document.body.clientHeight - 16;
	}
	
	if( this.isresized == false ){
		if( this.m[name].suby + menuHeight > document.body.clientHeight ){
			this.m[name].suby = document.body.clientHeight - menuHeight;
		}
	}
	
	var x=this.m[name].subx; 
	var y=this.m[name].suby;
	var bobj=this.l[l].oBorder; 
	bobj.hideIt(); 
	
	if(this.l[l-1].clip&&!(this.l[l-1].filter&&bw.filter)){
		clearTimeout(bobj.tim); 
		bobj.clipy=0; 
		bobj.clipTo(0,this.m[name].maxwidth,0,0);
	}
	else bobj.clipTo(0,this.m[name].maxwidth,menuHeight,0,1)
	
	bobj.moveIt(x,y); 
	
	var yy=border
	for(i=0;i<this.l[l].maxnum;i++){
		var obj=this.l[l].o[i]
		if(i<ln){
			var n=this.m[name].subs[i];
			obj.aname=n; 
			if(!bw.ns4||!this.NS4hover){
				var text = this.m[n].text;
				if( this.m[n].subs.length > 0 ){
					text = '<table onselectstart="return false" cellpadding="0" cellspacing="0" border="0" height="100%" width="100%"><tr>' + text + '<td width="10%" align="left" valign="center">' + cmSubMenuImage + '</td></tr></table>';
				}
				else{
					text = '<table onselectstart="return false" cellpadding="0" cellspacing="0" border="0" height="100%" width="100%"><tr>' + text + '<td width="10%" align="left" valign="center"></td></tr></table>';
				}
				
				obj.writeIt( text );
			}
			
			obj.addEvents(n,this.name,this.m[n].lnk,this.useclick); 
			var w=this.m[n].width; 
			var h=this.m[n].height
			if(obj.y!=yy) obj.moveY(yy); 
			yy+=h+border; 
			if(!obj.img) obj.setactive(0,n,1); 
			if(obj.width!=w||obj.height!=h) obj.clipTo(0,w,h,0,1); 
			obj.css.visibility="inherit"
		}
		else obj.hideIt()
	}
	
	if(this.l[l-1].filter&&bw.filter) bobj.filterIt(this.l[l-1].filter)
	else if(this.l[l-1].clip){
		bobj.showIt(); 
		bobj.clipOut(this.l[l-1].clippx,this.m[name].maxwidth,menuHeight,this.l[l-1].cliptim,name);
	}
	else bobj.showIt(); 
	
	//CHECKING FOR SELECT BOXES
	if(!bw.ns4&&this.checkselect){ 
		for(i=0;i<this.sel.length;i++){
			selx=0; 
			sely=0; 
			var selp;
			if(this.sel[i].offsetParent){
				selp=this.sel[i]; 
				while(selp.offsetParent){
					selp=selp.offsetParent; 
					selx+=selp.offsetLeft; 
					sely+=selp.offsetTop;
				}
			}
			
			selx+=this.sel[i].offsetLeft; 
			sely+=this.sel[i].offsetTop
			selw=this.sel[i].offsetWidth; 
			selh=this.sel[i].offsetHeight;
			
			if(((selx+selw)>this.m[name].subx 
				&& selx<(this.m[name].subx+this.m[name].maxwidth))
				&&((sely+selh)>this.m[name].suby 
				&& sely<(this.m[name].suby+menuHeight)))
			{
				if(this.sel[i].style.visibility!="hidden"){
					this.sel[i].level=l; 
					this.sel[i].style.visibility="hidden"; 
					this.hcode+=this.name+".sel["+i+"].style.visibility='visible';"
				}
			}
			else if(l<=this.sel[i].level) this.sel[i].style.visibility="visible"
		}
	}
	else if(bw.ns4&&this.hideForm){
		eval(this.hideForm+".visibility='hide'"); 
		this.hcode=this.hideForm+".visibility='show'"
	}
}

/************
Making all top elements
*************/
function cm_makeTop(rr){
	var m,rows,border,x,y,mpa
	m=this.menuplacement; 
	
	if( this.aspect == 'list' ){
		rows = 0;
	}
	else if( this.aspect == 'menu' || this.aspect == 'toolbar' ){
		rows = 1;
	}
	else{
		rows = 2;
	}
	
	// 修改菜单项的宽度
	if( this.aspect == 'list' ){
		for(j=0;j<this.l[0].maxnum;j++){
			this.m[this.l[0].names[j]].width = this.maxwidth;
		}
	}
	
	this.pxbetween=this.checkp(this.pxbetween,0,1,1)
	border=this.l[0].border;
	y=this.checkp(this.fromtop,0,0,1)+border;
	x=this.checkp(this.fromleft,0,0,1)+border
	if(m=="bottomcenter"||m=="bottom"){
		if(m=="bottomcenter") x=toppage.x2/2-(this.totwidth+border*this.l[0].num+this.pxbetween*(this.l[0].num-1))/2
		y=toppage.y2-this.maxheight-border
	}
	else if(m=="right") x=toppage.x2-this.maxwidth-border*2
	else if(m=="bottom") y=toppage.y2-this.maxheight-border*2
	else if(m=="center"){
		if(rows==0) x=toppage.x2/2 -  (this.maxwidth+border*2)/2; 
		else x=toppage.x2/2 - (this.totwidth + border*this.l[0].num +this.pxbetween*(this.l[0].num-1))/2
	}
	else if(m.toString().indexOf(",")>-1) mpa=1
	
	if(this.usebar){ 
		// 计算宽度
		if( this.aspect == 'menu' || this.aspect == 'toolbar' ){
			var	barw = 0;
			for(j=0;j<this.l[0].maxnum;j++){
				if( j != 0 ){
					barw += this.pxbetween;
				}
				
				barw += this.m[this.l[0].names[j]].width;
			}
			
			if( this.barx + this.barwidth < this.fromleft + barw ){
				this.barwidth = this.fromleft + barw - this.barx;
			}
		}
		
		var bx,by,bww,bh,oBb;
		oNS=bw.ns6?this.oNS[this.l[0].maxnum]:0
		this.oBar=new cm_makeObj('div'+this.name+'Bar',0,0,window,0,oNS)
		if(this.barx=="menu") bx=mpa&&rows?this.checkp(m[0],1,0,1)-border:x-border; 
		else{ bx=this.checkp(this.barx,1,0,1) }
		if(this.bary=="menu") by=mpa&&!rows?this.checkp(m[0],0,0,1)-border:y-border; 
		else by=this.checkp(this.bary,0,0,1);  
		this.oBar.moveIt(bx,by)
		
		if(this.barwidth=="menu"){
			bww=rows?mpa?(this.checkp(m[m.length-1],1,0,1)-bx)+this.m[this.l[0].names[this.l[0].num-1]].width+border:(this.totwidth +this.pxbetween*(this.l[0].num-1)):this.maxwidth; 
			bww+=!rows?border*2:0;
		}
		else bww=this.checkp(this.barwidth,1,0,1); 
		
		if(bw.ie&&rows&&this.barwidth=="100%"&&this.useframes) bww+=parseInt(self.document.body.leftMargin)*2
		if(this.barheight=="menu"){
			bh=!rows?mpa?(this.checkp(m[m.length-1],0,0,1)-by)+this.m[this.l[0].names[this.l[0].num-1]].height+border:(this.totheight + this.pxbetween*(this.l[0].num-1)):this.maxheight;
			bh+=rows?this.l[0].border*2:0;
		} 
		else bh=this.checkp(this.barheight,0,0,1);
		
		this.oBar.clipTo(0,bww,bh,0,1);
		if(this.barinheritborder&&border){
			oBb=new cm_makeObj('div'+this.name+'Barb',0,0,window,'div'+this.name+'Bar'); 
			oBb.moveIt(border,border); 
			oBb.clipTo(0,bww-border*2,bh-border*2,0,1); 
			oBb=null;
		}
	}
	
	this.l[0].o=new Array()
	for(j=0;j<this.l[0].maxnum;j++){
		this.l[0].o[j]=new cm_makeObj('div'+this.name+'0_'+j,this,0,window,'div'+this.name+'0_'+j+'b'); 
		if(bw.ns6) oNS=this.oNS[j]; 
		else oNS=0
		this.l[0].o[j].oBorder=new cm_makeObj('div'+this.name+'0_'+j+'b',0,0,window,0,oNS)
		obj=this.l[0].o[j]; 
		w=this.m[this.l[0].names[j]].width; 
		h=this.m[this.l[0].names[j]].height
		obj.addEvents(this.l[0].names[j],this.name,this.m[this.l[0].names[j]].lnk,this.useclick); 
		obj.clipTo(0,w,h,0,1)
		if(mpa){
			if(rows==1) x=this.checkp(m[j],0,1,1); 
			else y=this.checkp(m[j],0,0,1)
		}
		
		// 弹出菜单不显示
		if( this.aspect == 'popup' ){
			obj.css.display='none';
		}
		
		obj.moveIt(border,border); 
		obj.setactive(0,this.l[0].names[j])
		obj.oBorder.moveIt(x-border,y-border); 
		obj.oBorder.clipTo(0,w+border*2,h+border*2,0,1); 
		obj.oBorder.showIt()
		if(rows==0) y+=h+border+this.pxbetween
		else x+=w+border+this.pxbetween; 
		obj.showIt()
	}
	
	if(!rr){
		if(this.useclick) coolFMouseup+=this.name+".mmout('',1);"; 
		if(!this.useframes&&!bw.ns4) this.refresh()
		else if(!this.useframes&&bw.ns4){
			l=""; 
			if(onload){
				l=String(onload.toString()); 
				l=l.replace("function onload(event)",""); 
				l=l.slice(25,l.length-2)
			} 
			l+=this.name+ ".refresh();"; 
			onload=new Function(l)
		}
		
		if(this.resizecheck) setTimeout('window.onresize=new Function("'+this.name+'.resized()")',500)
		if(this.checkscroll){
			if(bw.ns4 || bw.ns6){
				if(this.checkscroll!=2&&this.useframes!=1){
					setTimeout("cm_checkScrolled("+this.name+")",200)
				}
			}
		}
		
		if(this.useframes&&!rr) this.checkFrame(0); 
		else this.win=window
	}
}


/************
Refreshing/making all sub elements
*************/
function cm_refresh(ev){
	var border,obj,oNS,oNS2
	if(this.useframes) page=new makePageCoords(this.win,this.useframes); else page=toppage
	for(i=1;i<this.l.length;i++){
		this.l[i].o=new Array();
		border=this.l[i].border;
		defheight=this.checkp(this.l[i].height)
		
		if(bw.ns4){
			oNS=new Layer(this.l[i].width,this.win); 
			oNS.zIndex=(500+i);
			if(this.l[i].border){
				oNS.bgColor=this.l[i].bordercolor;
			}
		}
		else if(bw.ns6){
			oNS=document.createElement("DIV");
			oNS.setAttribute("style",this.ns6styleb[i]);
			this.win.document.body.appendChild(oNS)
		}
		
		this.l[i].oBorder=new cm_makeObj('div'+this.name+i+"b",0,0,this.win,0,oNS)
		for(j=0;j<this.l[i].maxnum;j++){
			if(bw.ns4){oNS2=new Layer(this.l[i].width,oNS);}
			else if(bw.ns6){oNS2=document.createElement("DIV");	oNS2.setAttribute("style",this.ns6style[i]); oNS.appendChild(oNS2)}
			this.l[i].o[j]=new cm_makeObj('div'+this.name+i+'_'+j,this,i,this.win,0,oNS2)
			obj=this.l[i].o[j]; if(!obj.addEvents) return; obj.moveIt(border,(border+defheight)*j + border)
		}	
	}this.loaded=1;
	if(this.checkscroll&&bw.ie){this.win.document.body.onscroll=new Function("cm_checkScrolled("+this.name+")"); cm_checkScrolled(this)}
	if(this.useclick){this.win.document.onmouseup=new Function(coolFMouseup); if(this.useframes) document.onmouseup=new Function(coolFMouseup); }
	if(!bw.ns4&&this.checkselect) this.sel=bw.ie4?this.win.document.all.tags("SELECT"):this.win.document.getElementsByTagName("SELECT")
}
function cm_NS6_createElement(st,inn){el=document.createElement("DIV"); if(st) el.setAttribute("style",st); if(inn) el.innerHTML=inn; document.body.appendChild(el); return el}
/************
Making code
*************/
function cm_construct()
{
	this.level=null; 
	var str=""; 
	var str2="";
	var frstr=""; 
	var tempstr; 
	num=bw.ie?this.l.length:1;
	
	for(i=0;i<num;i++){
		if(i!=0) frstr+='<div id="div'+this.name+i+'b" style="'+this.ns6styleb[i]+'" class="cl'+this.name+i+'b">\n'
		for(j=0;j<this.l[i].maxnum;j++){
			tempstr='<div id="div'+this.name+i+'_'+j+'" '; 
			if(i!=0) tempstr+='style="'+this.ns6style[i]+'"'
			if(i==0){
				n=this.l[0].names[j];
				
				// 内容
				txt = this.m[n].text;
				if( this.m[n].subs.length > 0 ){
					if( this.aspect == 'list' ){	// columns
						txt = txt + '&nbsp;&nbsp;&nbsp;' + cmSubMenuImage;
					}
					else if( this.aspect == 'menu' ){	// rows
						txt = txt + '&nbsp;&nbsp;&nbsp;' + cmTopMenuImage;
					}
				}
				
				if(!bw.ns6){
					str+='<div id="div'+this.name+'0_'+j+'b" class="cl'+this.name+'b'+i+'">'
					str+=tempstr+' class="cl'+this.name+i+'">'+txt+'</div>'; str+='</div>\n'
				}
				else this.oNS[j]=cm_NS6_createElement(this.ns6styleb[i],tempstr+' class="cl'+this.name+i+'">'+txt+'</div>')
			}
			else frstr+=tempstr+'"></div>\n'
		}
		
		if(i!=0){frstr+='</div>\n'}
	}
	
	if(this.usebar){
		if(this.barinheritborder) str2='<div id="div'+this.name+'Barb"></div>'; 
		if(bw.ns6){
			this.oNS[this.l[0].maxnum]=cm_NS6_createElement(this.ns6styleb[this.ns6styleb.length-1],str2);
		}
		else{
			str+='<div id="div'+this.name+'Bar">';
			str+=str2+'</div>\n'
		}
	}
	
	if(!this.useframes&&bw.ie) str+=frstr; 
	else this.frstr=frstr;
	if(!bw.ns6) document.write(str)
	this.makeTop();	
	if(this.useframes) window.onerror=cm_check_error;
}


coolFrameError=0//Trapping external pages in frame error!
function cm_check_error(e){e=e.toLowerCase(); if(e.indexOf("access")>-1||e.indexOf("permission")>-1){coolFrameError=1; return true;}else return false}
/**************
Make styles
**************/
function cm_makeStyle()
{
	var str="<style>\n"; 
	var zindex=10150;  
	var c,w,st,bg;
	
	this.ns6style=new Array(); 
	this.ns6styleb=new Array();
	
	for(i=0;i<this.l.length;i++){
		if(i==0){
			if(bw.ns4){
				w="width:" +this.l[0].width+";"; 
				this.l[i].style=""
			} 
			else w=""; 
			
			str+='.cl'+this.name+i+'{position:absolute; '+w+' background-color:transparent; color:'+this.l[i].tc+';'+this.l[i].style+'; cursor:pointer; cursor:hand; visibility:inherit; z-index:'+zindex+'}\n'
		}
		else{
			st='position:absolute; '+this.l[i].style+';  cursor:pointer; cursor:hand; visibility:inherit; z-index:'+zindex; 
			this.ns6style[i]=st
		}
		
		bc=this.l[i].border?bw.ns4?'layer-background-color:'+this.l[i].bordercolor:'; background-color:'+this.l[i].bordercolor:"";
		if(!bw.ns4) bc+="; overflow:hidden"
		tempstr='position:absolute; clip:rect(0,0,0,0); visibility:hidden; '+bc+'; z-index:'+(zindex-50)
		if(i==0&&!bw.ns6) str+='.cl'+this.name+'b'+i+'{'+tempstr+'}\n'; 
		else this.ns6styleb[i]=tempstr; 
		zindex+=100
	}	
	
	if(this.usebar){
		bg=this.barinheritborder?this.l[0].bordercolor:this.barcolor
		st='z-index:10080; position:absolute; background-color:'+bg+'; layer-background-color:'+bg
		if(!bw.ns6) str+='#div'+this.name+'Bar{'+st+'}\n'; 
		else this.ns6styleb[this.ns6styleb.length]=st
		
		if(this.barinheritborder){
			str+='#div'+this.name+'Barb{z-index:10085; position:absolute; background-color:'+this.barcolor+'; layer-background-color:'+this.barcolor+'}\n'
		}
	}
	
	if(bw.ns4) str+="A.clNS4{text-decoration:none; padding:"+this.NS4padding+"}\n"; 
	document.write(str+"\n</style>\n")
}

/************
Refreshing page if it's resized
*************/
function cm_resized(){
	page2=new makePageCoords(window,this.useframes); 
	if(page2.x2!=toppage.x2 || page2.y2!=toppage.y2){
		if(!bw.ns4){
			toppage=new makePageCoords(window,this.useframes); this.makeTop(1)
			if(!this.useframes) page=toppage; this.isresized=1; eval(this.resizecode)
		}else{this.win.location.reload(); location.reload()}
	}if(!bw.ns4&&this.useframes){page=new makePageCoords(this.win,this.useframes)}
}
/************
Going to another page
*************/
function cm_go(name){
	obj=this.m[name]; url=obj.lnk; target=obj.target; fc=obj.mclick
	if(url){
		if(this.useframes&&!coolFrameError) loc=this.win.location.href; else loc=location.href
		if(fc) eval(fc); url=this.checkFolder(loc.toString(),url); this.isover=0;
		this.hideSubs(1,0,1); this.isclicked=0; this.aobj[0]=-1
		if(String(target)=="undefined" || target=="" || target==0 || target=="_self"){
			this.win.location.href=url
		}else if(target=="_blank") window.open(url)
		else if(target=="_top" || target=="window") top.location.href=url  
		else if(top[target]) top[target].location.href=url
		else{fr=findFrame(target); if(fr) fr.location.href=url}
	}
	else if(fc){
		eval(fc);
	}
}
/************
Getting folders - THANKS TO DCAGE FOR THIS FIX
*************/
function cm_checkFolder(tmp,url){
	if(url.indexOf("mailto:")>-1 || url.indexOf("/")==0 || url.indexOf("http://")==0) return url
	else if(this.useframes && bw.ie || bw.ns6) return url
	var addr=''; var lvl=''; var off_cnt=0; var cnt=0;
	if(tmp.indexOf('file:')>-1 || tmp.charAt(1)==':') addr=this.offlineUrl;
	else if(tmp.indexOf('http:')>-1) addr=this.onlineUrl;
	for(var i=0;i<addr.length;i++){if(addr.charAt(i)=='\/') off_cnt+=1}
	for(var i=0;i<tmp.length;i++){if(tmp.charAt(i)=='\/'){ cnt+=1; if(cnt>off_cnt) lvl+='../'; }}
	return lvl + url
}
/************
Checkloaded for the frames version
*************/
function cm_checkLoaded(ev,ns){
	coolFName=eval(coolFName)
	var ok=0
	if(document.layers){ 
		if(ns){coolFName.refresh(); coolFName.nsload=1; ok=1; coolFrameError=0
		}else if(ev.target.name==coolFName.frame&&!coolFName.nsload){coolFName.refresh(); ok=1; coolFrameError=0; routeEvent(ev)}
	}else if(bw.ie){
		if (coolFName.win.document.readyState == "complete"){
			coolFName.win.document.body.insertAdjacentHTML("beforeEnd",coolFName.frstr)
			coolFName.win.document.body.onunload=cm_unloaded; coolFName.refresh(); ok=1; coolFrameError=0
		}else setTimeout("cm_checkLoaded()",200)
	}else if(bw.ns6){ 
		if(coolFName.win.document){
			if(coolFName.win.document.body){coolFName.win.addEventListener("unload", cm_unloaded, true); coolFName.refresh(); ok=1; coolFrameError=0}
			else setTimeout("cm_checkLoaded()",200)
		}else setTimeout("cm_checkLoaded()",200)
	}
}

function cm_unloaded(ev)
{
	coolFName=eval(coolFName); 
	if(!coolFName) return; 
	if(document.layers && ev.target.name!=coolFName.frame) return; 
	else coolFName.nsload=0; 
	coolFName.loaded=0; 
	if(!document.layers) setTimeout("cm_checkLoaded()",200)
}


/************
Make menu object
*************/
var coolFName=""; var onload; var coolFMouseup="";
function makeCoolMenu(name){
	coolFName=name; 
	this.name=name; 
	this.lastScrolled=0;
	this.win=window; 
	toppage=new 
	makePageCoords(window,parent.frames.length);
	this.aobj=new Array(); 
	this.m=new Array(); 
	this.l=new Array();
	this.level=new Array(); 
	this.resized=cm_resized;
	this.makeMenu=cm_makeMenu; 
	this.showSubs=cm_showSubs; 
	this.makeTop=cm_makeTop;
	this.getCoords=cm_getCoords; 
	this.checkPage=cm_checkPage;
	this.mmout=cm_mout; 
	this.mover=cm_mover; 
	this.checkp=cm_checkp;
	this.hideSubs=cm_hideSubs; 
	this.construct=cm_construct;
	this.makeStyle=cm_makeStyle; 
	this.refresh=cm_refresh;
	this.go=cm_go; 
	this.frstr=""; 
	this.mobj=new Array()
	this.totwidth=0; 
	this.totheight=0; 
	this.maxwidth=0; 
	this.maxheight=0
	this.tim=10; 
	this.loaded=0; 
	this.isover=false; 
	this.checkFrame=cm_checkFrame;
	this.checkFolder=cm_checkFolder; 
	this.hcode=""; 
	this.oNS=new Array(); 
	this.oNS2=new Array()
	bw.filter=(bw.ie6||bw.ver.indexOf("MSIE 5.5")>-1) && !bw.mac
	
	// 初始化
	this.setMenuType = cm_setMenuType;
	this.popupMenu = cm_popupMenu;
	this.popup = cm_popup;
}
/************
Find frame
*************/
function findFrame(frameName){
	obj=top; 
	var frameObj=0;
	for(i=0;i<obj.frames.length;i++){
		if(obj.frames[i].name==frameName){
			frameObj=obj.frames[i]; 
			break;
		}
		
		ln=obj.frames[i].frames.length;
		for(j=0;j<ln;j++){
			if(obj.frames[i].frames[j].name==frameName){
				frameObj=obj.frames[i].frames[j];  
				break;
			}
			
			ln2=obj.frames[i].frames[j].frames.length
			for(a=0;a<ln2;a++){
				if(obj.frames[i].frames[j].frames[a].name==frameName){
					frameObj=obj.frames[i].frames[j].frames[a]; 
					break;
				}
			}
		}
	}
	
	return frameObj;
}
/************
Checking for frame
*************/
function cm_checkFrame(num){
	var fr;
	if(num==10){
		debug('Frame: '+this.frame+' doesnt exist - Value: '+fr + " - Could not build menus."); 
		return;
	}
	
	if(!top.frames[this.frame]){
		fr=findFrame(this.frame);
	}
	else{
		fr=top.frames[this.frame];
	}
	
	if(!fr){
		num++; 
		setTimeout(this.name+".checkFrame("+num+")",500)
	}
	else{ 
		this.win=fr
		if(bw.ns4){
			top.frames.captureEvents(Event.UNLOAD); top.frames.captureEvents(Event.LOAD); top.frames.onunload=cm_unloaded;
			top.frames.onload = cm_checkLoaded;	setTimeout("cm_checkLoaded(0,1)",1000)
		}
		else if((bw.ie||bw.ns6)){
			setTimeout("cm_checkLoaded()",200);
		}
	}
}	











/*****************************************************************************
Default browsercheck - Leave this one'
******************************************************************************/
function lib_bwcheck(){ //Browsercheck (needed)
	this.ver=navigator.appVersion; this.agent=navigator.userAgent
	this.dom=document.getElementById?1:0
	this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0;
	this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom)?1:0;
	this.ie4=(document.all && !this.dom)?1:0;
	this.ie=this.ie4||this.ie5||this.ie6
	this.mac=this.agent.indexOf("Mac")>-1
	this.opera5=this.agent.indexOf("Opera 5")>-1
	this.ns6=(this.dom && parseInt(this.ver) >= 5) ?1:0;
	this.ns4=(document.layers && !this.dom)?1:0;
	this.bw=(this.ie6 || this.ie5 || this.ie4 || this.ns4 || this.ns6 || this.opera5 || this.dom)
	return this
}
var bw=new lib_bwcheck() //Making browsercheck object

var mDebugging=2 //General debugging variable. Set to 0 for no debugging, 1 for alerts or 2 for status debugging.



function initStrutsMenuInfo( strutsMenu )
{
	strutsMenu.useframes=0 //Do you want to use the menus as coolframemenu or not? (in frames or not) - Value: 0 || 1
	strutsMenu.frame="" //The name of your main frame (where the menus should appear). Leave empty if you're not using frames - Value: "main_frame_name"
	
	strutsMenu.useclick=0 //If you want the menu to be activated and deactivated onclick only set this to 1. - Value: 0 || 1
	
	/*If you set this to 1 you will get a "hand" cursor when moving over the links in NS4.
	NOTE: This does not apply to the submenus if the menu is used in frames due some mayor problems with NS4*/
	strutsMenu.useNS4links=1
	
	//After adding the "hover effect" for netscape as well, all styles are lost. But if you want padding add it here.
	strutsMenu.NS4padding=2
	
	//If you have select boxes close to your menu the menu will check for that and hide them if they are in the way of the menu.
	//This feature does unfortunatly not work in NS4!
	strutsMenu.checkselect=1
	
	/*If you choose to have this code inside a linked js, or if your using frames it's important to set these variables.
	This will help you get your links to link to the right place even if your files are in different folders.
	The offlineUrl variable is the actual path to the directory where you js file are locally.
	This is just so you can test it without uploading. Remember to start it with file:/// and only use slashes, no backward slashes!
	Also remember to end with a slash                                                                                                 */
	strutsMenu.offlineUrl="file:///C|/Inetpub/wwwroot/dhtmlcentral/" //Value: "path_to_menu_file_offline/"
	//The onlineUrl variable is the online path to your script. Place in the full path to where your js file is. Remember to end with a slash.
	strutsMenu.onlineUrl="http://www.dhtmlcentral.com/coolmenus/examples/withoutframes/" //Value: "path_to_menu_file_online/"
	
	strutsMenu.pagecheck=1 //Do you want the menu to check whether any of the subitems are out of the bouderies of the page and move them in again (this is not perfect but it hould work) - Value: 0 || 1
	strutsMenu.checkscroll=0 //Do you want the menu to check whether the page have scrolled or not? For frames you should always set this to 1. You can set this to 2 if you want this feature only on explorer since netscape doesn't support the window.onscroll this will make netscape slower (only if not using frames) - Value: 0 || 1 || 2
	strutsMenu.resizecheck=1 //Do you want the page to reload if it's resized (This should be on or the menu will crash in Netscape4) - Value: 0 || 1
	strutsMenu.wait=600 //How long to wait before hiding the menu on mouseout. Netscape 6 is a lot slower then Explorer, so to be sure that it works good enough there you should not have this lower then 500 - Value: milliseconds
	
	//Background bar properties
	strutsMenu.usebar=1 //If you want to use a background-bar for the top items set this on - Value: 1 || 0
	strutsMenu.barcolor="lightblue" //The color of the background bar - Value: "color"
	strutsMenu.barwidth="450" //The width of the background bar. Set this to "menu" if you want it to be the same width as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
	strutsMenu.barheight="menu" //The height of the background bar. Set this to "menu" if you want it to be the same height as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
	strutsMenu.barx=10 //The left position of the bar. Set this to "menu" if you want it be the same as the left position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
	strutsMenu.bary="menu" //The top position of the bar Set this to "menu" if you want it be the same as the top position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
	strutsMenu.barinheritborder=0 //Set this to 1 if you want the bar to have the same border as the top menus - Value: 0 || 1
	
	//Placement properties
	strutsMenu.aspect = 'menu'; //This controls whether the top items is supposed to be laid out in rows or columns. Set to [list] for columns and [menu] for row - Value list || menu || toolbar || tree || popup
	strutsMenu.fromleft=15 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
	strutsMenu.fromtop=20 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
	strutsMenu.pxbetween=2 //How much space you want between each of the top items. - Value: px || "%"
	
	/*You have several different ways to place the top items.
	You can have them right beside eachother (only adding the pxbetween variable)
	strutsMenu.menuplacement=0
	
	You can have them aligned to one of the sides - This is mostly when not using frames, but can be used in both conditions
	Values: (If you get strange results check the fromleft,fromtop and pxbetween variables above)
	For menus that are placed in columns (align=left or align=right (se below)) you can align them to the "right" or "center"
	For menus that are placed in rows (align=top or align=bottom (se below)) you can align them to the "bottom", "center" or "bottomcenter"
	strutsMenu.menuplacement="center"
	
	You can also set them directly in pixels: (Remember to have as many array members as you have top items)
	strutsMenu.menuplacement=new Array(10,200,400,600)
	
	Or you can place in percentage: (remember to use the ' ' around the numbers)
	
	
	Choose one of those options to get the desired results.
	*/
	strutsMenu.menuplacement=0
	
	/*
	Now we are ready for the properties of each level. For those of that have used the old
	coolmenus for coolframemenu I will try and explain how this works like this:
	level[0] = top items
	level[1] = sub items
	level[2] = sub2 items
	level[3] = sub3 items and so on....
	All menus will inherit the properties, and all properties does only HAVE to be spesifed on the top level.
	If a level doesn't have on property spesified it will look for it on the last level that was spesified,
	if it still doesn't exist it will get the properties from level[0]
	
	Which means that if you set the background color on level[0] to "black" and doesn't spesify any more levels or doesn't
	spesify the background color on the last level you spesified ALL menus will get the color from level[0]
	
	Did that make sense at all? This can be a little hard to understand, look at the different examples on my site
	and play with and I am sure you'll get what I mean.
	*/
	
	//TOP LEVEL PROPERTIES - ALL OF THESE MUST BE SPESIFIED FOR LEVEL[0]
	strutsMenu.level[0]=new Array() //Add this for each new level
	strutsMenu.level[0].width=110 //The default width for each level[0] (top) items. You can override this on each item by spesifying the width when making the item. - Value: px || "%"
	strutsMenu.level[0].height=20 //The default height for each level[0] (top) items. You can override this on each item by spesifying the height when making the item. - Value: px || "%"
	strutsMenu.level[0].bgcoloroff="lightblue" //The default background color for each level[0] (top) items. You can override this on each item by spesifying the backgroundcolor when making the item. - Value: "color"
	strutsMenu.level[0].bgcoloron="blue" //The default "on" background color for each level[0] (top) items. You can override this on each item by spesifying the "on" background color when making the item. - Value: "color"
	strutsMenu.level[0].textcolor="black" //The default text color for each level[0] (top) items. You can override this on each item by spesifying the text color when making the item. - Value: "color"
	strutsMenu.level[0].hovercolor="white" //The default "on" text color for each level[0] (top) items. You can override this on each item by spesifying the "on" text color when making the item. - Value: "color"
	strutsMenu.level[0].style="padding:2px; font-family:宋体,tahoma,arial,helvetica; font-size:12px; font-weight:bold" //The style for all level[0] (top) items. - Value: "style_settings"
	strutsMenu.level[0].border=0 //The border size for all level[0] (top) items. - Value: px
	strutsMenu.level[0].bordercolor="blue" //The border color for all level[0] (top) items. - Value: "color"
	strutsMenu.level[0].offsetX=1 //The X offset of the submenus of this item. This does not affect the first submenus, but you need it here so it can be the default value for all levels. - Value: px
	strutsMenu.level[0].offsetY=1 //The Y offset of the submenus of this item. This does not affect the first submenus, but you need it here so it can be the default value for all levels. - Value: px
	strutsMenu.level[0].NS4font="宋体,tahoma,arial,helvetica"
	strutsMenu.level[0].NS4fontSize="2"
	
	/*New: Added animation features that can be controlled on each level.*/
	strutsMenu.level[0].clip=0 //Set this to 1 if you want the submenus of this level to "slide" open in a animated clip effect. - Value: 0 || 1
	strutsMenu.level[0].clippx=0 //If you have clip spesified you can set how many pixels it will clip each timer in here to control the speed of the animation. - Value: px
	strutsMenu.level[0].cliptim=0 //This is the speed of the timer for the clip effect. Play with this and the clippx to get the desired speed for the clip effect (be carefull though and try and keep this value as high or possible or you can get problems with NS4). - Value: milliseconds
	//Filters - This can be used to get some very nice effect like fade, slide, stars and so on. EXPLORER5.5+ ONLY - If you set this to a value it will override the clip on the supported browsers
	strutsMenu.level[0].filter=0 //VALUE: 0 || "filter specs"
	
	/*And last but not least the align variable.
	
	This spesifies how the submenus of this level comes out.
	Values:
	"bottom": The sub menus of this level will come out on the top of this item
	"top": The sub menus of this level will come out on the bottom of this item
	"left": The sub menus of this level will come out on the right of this item
	"right": The sub menus of this level will come out on the left of this item
	
	In generally "left" and "right" works best for menus in columns and "top" and "bottom" works best for menus in rows.
	But by all means feel free to play with it.
	
	If you have set pagecheck to 1 above this is what the pagecheck will change when reaching the bounderies of the page.
	If it reaches the right boundery and it's aligned left it will change the align to right and so on.
	*/
	strutsMenu.level[0].align="right" //Value: "top" || "bottom" || "left" || "right"
	
	//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to spesify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
	strutsMenu.level[1]=new Array() //Add this for each new level (adding one to the number)
	strutsMenu.level[1].width=150
	strutsMenu.level[1].height=20
	strutsMenu.level[1].style="padding:2px; font-family:宋体,tahoma, arial,helvetica; font-size:9pt"
	strutsMenu.level[1].align="bottom"
	strutsMenu.level[1].offsetX=-4
	strutsMenu.level[1].offsetY=0
	strutsMenu.level[1].border=1
	strutsMenu.level[1].bordercolor="#006699"
	strutsMenu.level[1].NS4font="宋体,tahoma,arial,helvetica"
	strutsMenu.level[1].NS4fontSize="1"
	
	//EXAMPLE SUB LEVEL[2] PROPERTIES - You have to spesify the properties you want different from LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
	strutsMenu.level[2]=new Array() //Add this for each new level (adding one to the number)
	strutsMenu.level[2].width=strutsMenu.level[1].width
	strutsMenu.level[2].height=20
	//strutsMenu.level[2].bgcoloroff="#009999"
	//strutsMenu.level[2].bgcoloron="#0099cc"
	strutsMenu.level[2].style="padding:2px; font-family:宋体,tahoma,arial,helvetica; font-size:9pt"
	strutsMenu.level[2].align="bottom"
	strutsMenu.level[2].offsetX=0
	strutsMenu.level[2].offsetY=0
	strutsMenu.level[2].border=1
	strutsMenu.level[2].bordercolor="#006699"
	strutsMenu.level[2].NS4font="宋体,tahoma,arial,helvetica"
	strutsMenu.level[2].NS4fontSize="1"
	
	//EXAMPLE SUB LEVEL[2] PROPERTIES - You have to spesify the properties you want different from LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
	strutsMenu.level[3]=new Array() //Add this for each new level (adding one to the number)
	strutsMenu.level[3].width=strutsMenu.level[1].width
	strutsMenu.level[3].height=20
	//strutsMenu.level[3].bgcoloroff="#009999"
	//strutsMenu.level[3].bgcoloron="#0099cc"
	strutsMenu.level[3].style="padding:2px; font-family:宋体,tahoma,arial,helvetica; font-size:9pt"
	strutsMenu.level[3].align="bottom"
	strutsMenu.level[3].offsetX=0
	strutsMenu.level[3].offsetY=0
	strutsMenu.level[3].border=1
	strutsMenu.level[3].bordercolor="#006699"
	strutsMenu.level[3].NS4font="宋体,tahoma,arial,helvetica"
	strutsMenu.level[3].NS4fontSize="1"
}

// 设置菜单的类型
function cm_setMenuType( type )
{
	// 设置相关的内容：固定参数
	this.barx = this.fromleft - 5;
	this.barcolor = cmBGColorOff;
	
	// 弹出菜单不显示
	this.aspect = type;
	if( this.aspect == 'popup' ){
		this.usebar = 0;
		this.level[0].align="bottom";
	}
	else if( this.aspect == 'list' ){
		this.level[0].align="right";
	}
	else if( this.aspect == 'menu' ){
		this.level[0].align="bottom";
	}
}


// Struts Menu specific javascript variables 

// menu arrows
cmTopMenuImage='<img src="' + rootPath + '/script/menu-images/Darrow.gif">'
cmSubMenuImage='<img src="' + rootPath + '/script/menu-images/Rarrow.gif">'

// normal menu colors
// this is required!
cmBGColorOn=''
cmBGColorOff=''
cmTxtColor=''
cmHoverColor=''


