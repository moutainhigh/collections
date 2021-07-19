/**
 * WebForm, like window
 *
*/
//WebForm
WebForm.prototype.SetIcon=function(url){};
WebForm.prototype.SetTitle=function(title){};
WebForm.prototype.Add=function(object){};
WebForm.prototype.Normal=function(){};
WebForm.prototype.Minimize=function(){};
WebForm.prototype.Maximize=function(){};
WebForm.prototype.Close=function(){};
WebForm.prototype.Show=function(){};
WebForm.prototype.Hide=function(){};
WebForm.prototype.State=1;
WebForm.prototype.NORMAL=1;
WebForm.prototype.MINIMIZE=2;
WebForm.prototype.MAXIMIZE=3;
WebForm.prototype.SHOW=4;
WebForm.prototype.HIDDEN=5;

function WebForm(parent)
{
  var obj = new Table(parent);
  obj.style.borderStyle='solid';
  obj.style.borderWidth=1;
  obj.style.borderColor=DefaultStyle.BorderColor;
  obj.TitleRow=new TR(obj);
  obj.tbody.appendChild(obj.TitleRow);
  obj.TitleRow.style.paddingTop=1;
  obj.TitleRow.style.paddingBottom=3;
  obj.TitleRow.Owner=obj;
//  obj.TitleRow.onmousemove=WebForm_TitleRow_mousemove;
  obj.TitleRow.onmouseup=WebForm_TitleRow_mouseup;
  obj.TitleRow.onmousedown=WebForm_TitleRow_mousedown;
  obj.TitleRow.ondblclick=WebForm_TitleRow_doubleclick;
  obj.Col1=new TD(obj.TitleRow);
  obj.Col1.style.borderWidth=0;
  obj.TitleRow.appendChild(obj.Col1);
  obj.Col2=new TD(obj.TitleRow);
  obj.Col2.style.width='99%';
  obj.Col2.style.borderWidth=0;
  obj.TitleRow.appendChild(obj.Col2);
  obj.TitleText=new Span(obj.Col2);
  obj.TitleText.style.fontWeight='bold';
  obj.TitleText.style.color=DefaultStyle.HighlightColor;
  obj.TitleText.style.whiteSpace="nowrap";
  obj.TitleText.style.cursor='default';
  obj.Col3=new TD(obj.TitleRow);
  obj.Col3.style.borderWidth=0;
  obj.TitleRow.appendChild(obj.Col3);
  obj.Col4=new TD(obj.TitleRow);
  obj.Col4.style.borderWidth=0;
  obj.TitleRow.appendChild(obj.Col4);
  obj.Col5=new TD(obj.TitleRow);
  obj.Col5.style.borderWidth=0;
  obj.TitleRow.appendChild(obj.Col5);
  obj.Col3.style.padding=1;
  obj.Col4.style.padding=1;
  obj.Col5.style.padding=1;
  obj.Col3.style.paddingBottom=3;
  obj.Col4.style.paddingBottom=3;
  obj.Col5.style.paddingBottom=3;
  obj.Col1.style.backgroundRepeat='repeat-x';
  obj.Col1.style.backgroundImage='url('+jcl_ResourceURL+'webform/titlebg.gif)';
  obj.Col2.style.backgroundImage='url('+jcl_ResourceURL+'webform/titlebg.gif)';
  obj.Col2.style.backgroundRepeat='repeat-x';
  obj.Col3.style.backgroundImage='url('+jcl_ResourceURL+'webform/titlebg.gif)';
  obj.Col3.style.backgroundRepeat='repeat-x';
  obj.Col4.style.backgroundImage='url('+jcl_ResourceURL+'webform/titlebg.gif)';
  obj.Col4.style.backgroundRepeat='repeat-x';
  obj.Col5.style.backgroundImage='url('+jcl_ResourceURL+'webform/titlebg.gif)';
  obj.Col5.style.backgroundRepeat='repeat-x';
  obj.Col1.Owner=obj.Col2.Owner=obj.Col3.Owner=obj.Col4.Owner=obj.Col5.Owner=obj;

  obj.MinButton=new Image(obj.Col3);
  obj.MinButton.src=jcl_ResourceURL+'webform/minwindow.gif';
  obj.MaxButton=new Image(obj.Col4);
  obj.MaxButton.src=jcl_ResourceURL+'webform/maxwindow.gif';
  obj.CloseButton=new Image(obj.Col5);
  obj.CloseButton.src=jcl_ResourceURL+'webform/closewindow.gif';
  obj.CloseButton.onclick=WebForm_Close;
  obj.MinButton.onclick=WebForm_Minimize;
  obj.MaxButton.onclick=WebForm_Maximize;
  obj.MinButton.Owner=obj;
  obj.MaxButton.Owner=obj;
  obj.CloseButton.Owner=obj;

  obj.BodyRow=new TR(obj);
  obj.BodyRow.style.height='99%';
  obj.tbody.appendChild(obj.BodyRow);
  obj.BodyRowCol=new TD(obj.BodyRow);
  obj.BodyRowCol.style.borderWidth=0;
  obj.BodyRow.appendChild(obj.BodyRowCol);
  obj.BodyRowCol.colSpan=5;


  obj.FootRow=new TR(obj);
  obj.tbody.appendChild(obj.FootRow);
  obj.FootRowCol1=new TD(obj.FootRow);
  obj.FootRowCol1.style.borderWidth=0;
  obj.FootRow.appendChild(obj.FootRowCol1);
  obj.FootRowCol1.colSpan=4;
  obj.FootRow.style.backgroundColor=DefaultStyle.PanelColor;

  obj.FootRowCol2=new TD(obj.FootRow);
  obj.FootRowCol2.style.borderWidth=0;
  obj.FootRow.appendChild(obj.FootRowCol2);
  obj.FootRowCol2.colSpan=1;

  obj.FootRowCol2.style.backgroundRepeat='no-repeat';
  obj.FootRowCol2.style.backgroundImage='url('+jcl_ResourceURL+'webform/sizewindow.gif)';;
  obj.FootRowCol2.style.cursor='se-resize';
//  obj.FootRowCol2.onmousemove=WebForm_FootRowCol2_mousemove;
  obj.FootRowCol2.onmouseup=WebForm_TitleRow_mouseup;
  obj.FootRowCol2.onmousedown=WebForm_TitleRow_mousedown;

  obj.FootRowCol2.Owner=obj;

  obj.StatusBar=new StatusBar(obj.FootRowCol1);

  obj.FootRow.style.backgroundRepeat='repeat';
  obj.FootRow.style.backgroundImage='url('+jcl_ResourceURL+'webform/statusbarbg.gif)';

  obj.cellSpacing=0;
  obj.cellPadding=0;

  obj.Owner=obj;
  obj.NORMAL=1;
  obj.MINIMIZE=2;
  obj.MAXIMIZE=3;
  obj.SHOW=4;
  obj.HIDDEN=5;

  obj.SetIcon=WebForm_SetIcon;	
  obj.SetTitle=WebForm_SetTitle;	
  obj.Add=WebForm_Add;	
  obj.Normal=WebForm_Normal;
  obj.Minimize=WebForm_Minimize;
  obj.Maximize=WebForm_Maximize;
  obj.Close=WebForm_Close;
  obj.Show=WebForm_Show;
  obj.Hide=WebForm_Close;

  obj.State=obj.NORMAL;
  return obj;
}
function WebForm_SetIcon(url){
   var td=this.Col1;
   td.innerHTML='';
   td.Icon=new Image(td);
   td.Icon.src=url;
   td.Icon.style.height=15;
}

function WebForm_SetTitle(title){
   this.TitleText.innerHTML=title;
}

function WebForm_Add(object){
  this.BodyRowCol.appendChild(object);
  object.style.width="100%";
//  object.style.height="100%";
}
function WebForm_Show(){
  this.Owner.style.display='';
  this.Owner.State=this.Owner.SHOW;
}
function WebForm_Close(){
  this.Owner.style.display='none';
  this.Owner.State=this.Owner.HIDE;
}
function WebForm_Minimize(){
  this.Owner.MaxButton.src=jcl_ResourceURL+'webform/resetwindow.gif';
  this.Owner.MaxButton.onclick=WebForm_Normal;
  if(this.Owner.State==this.Owner.NORMAL){
	this.Owner.OldLeft=this.Owner.offsetLeft;
	this.Owner.OldTop=this.Owner.offsetTop;
	this.Owner.OldWidth=this.Owner.offsetWidth;
	this.Owner.OldHeight=this.Owner.offsetHeight;
  }
  this.Owner.BodyRow.style.display='none';
  this.Owner.FootRow.style.display='none';
  this.Owner.style.height=20;
  this.Owner.style.width=100;
  if(this.Owner.OldLeft){
    this.Owner.style.left=this.Owner.OldLeft;
    this.Owner.style.top=this.Owner.OldTop;
  }
  this.Owner.State=this.Owner.MINIMIZE;
}
function WebForm_Maximize(){
  this.Owner.MaxButton.src=jcl_ResourceURL+'webform/resetwindow.gif';
  this.Owner.MaxButton.onclick=WebForm_Normal;
  if(this.Owner.State==this.Owner.NORMAL){
	this.Owner.OldLeft=this.Owner.offsetLeft;
	this.Owner.OldTop=this.Owner.offsetTop;
	this.Owner.OldWidth=this.Owner.offsetWidth;
	this.Owner.OldHeight=this.Owner.offsetHeight;
  }
  if(this.Owner.BodyRow.style.display=='none'){
    this.Owner.BodyRow.style.display='';
    this.Owner.FootRow.style.display='';
  }
  this.Owner.style.height="99%";
  this.Owner.style.width="100%";
  this.Owner.style.left="";
  //this.Owner.style.top="";
  this.Owner.style.top=0;
  this.Owner.State=this.Owner.MAXIMIZE;
}

function WebForm_Normal(){
  this.Owner.MaxButton.src=jcl_ResourceURL+'webform/maxwindow.gif';
  this.Owner.MaxButton.onclick=WebForm_Maximize;
  if(this.Owner.BodyRow.style.display=='none'){
	this.Owner.BodyRow.style.display='';
	this.Owner.FootRow.style.display='';
  }

  if(this.Owner.OldWidth){
    this.Owner.style.left=this.Owner.OldLeft;
    this.Owner.style.top=this.Owner.OldTop;
    this.Owner.style.width=this.Owner.OldWidth;
    this.Owner.style.height=this.Owner.OldHeight;
  }
  this.Owner.State=this.Owner.NORMAL;

}

// mouse draging event to move webform
function WebForm_TitleRow_mousedown(event){
	if(!event)event=window.event;
	if(event.button==MouseEvent.LeftButton){//left button
	  if(isIE)
		this.setCapture();
	  else {
		window.captureEvents(Event.MOUSEMOVE);
	  }
  	  if(this==this.Owner.TitleRow)
	    this.onmousemove=WebForm_TitleRow_mousemove;
	  if(this==this.Owner.FootRowCol2)
	    this.onmousemove=WebForm_FootRowCol2_mousemove;
      var x=event.x ? event.x : event.pageX;
      var y=event.y ? event.y : event.pageY;
	  this.Owner.OldX=x;
	  this.Owner.OldY=y;
	  this.Owner.DragOldWidth=this.Owner.offsetWidth;
	  this.Owner.DragOldHeight=this.Owner.offsetHeight;
	  this.Owner.DragOldLeft=this.Owner.offsetLeft;
	  this.Owner.DragOldTop=this.Owner.offsetTop;
	  this.Owner.IsDrag=true;
	}
}
// mouse draging event to move webform
function WebForm_TitleRow_mousemove(event){
	if(this.Owner.IsDrag){//left button
  	  if(!event)event=window.event;
      var x=event.x ? event.x : event.pageX;
      var y=event.y ? event.y : event.pageY;
      this.Owner.style.left=this.Owner.DragOldLeft+(x-this.Owner.OldX);
	  this.Owner.style.top=this.Owner.DragOldTop+(y-this.Owner.OldY);
	}
}

function WebForm_TitleRow_mouseup(event){
    this.Owner.IsDrag=false;
	if(isIE)
		this.releaseCapture();
	else{
		window.releaseEvents(Event.MOUSEMOVE);
		this.Owner.TitleRow.onmousemove=null;
		this.Owner.FootRowCol2.onmousemove=null;
	}
}

// mouse draging event to resize webform
function WebForm_FootRowCol2_mousemove(event){
	if(!event)event=window.event;
	if(this.Owner.IsDrag){//left button
      var x=event.x ? event.x : event.pageX;
      var y=event.y ? event.y : event.pageY;
      this.Owner.style.width=this.Owner.DragOldWidth+(x-this.Owner.OldX);
      this.Owner.style.height=this.Owner.DragOldHeight+(y-this.Owner.OldY);
	}
}
function WebForm_TitleRow_doubleclick(event){
	if(this.Owner.State==this.Owner.NORMAL)
		this.Owner.Maximize();
	else {
		this.Owner.Normal();
	}

}

//StatusBar
StatusBar.prototype.SetText=function(text){};
function StatusBar(parent){
  var obj = new Table(parent);
  obj.style.width='100%';
  obj.style.height=18;
  obj.style.borderWidth=0;
  obj.Row=new TR(obj);
  obj.tbody.appendChild(obj.Row);
  obj.Col1=new TD(obj.Row);
  obj.Col1.style.borderWidth=1;
  obj.Row.appendChild(obj.Col1);
  obj.Col1.style.borderWidth=0;
  obj.cellSpacing=0;
  obj.cellPadding=0;

  obj.SetText=StatusBar_SetText;
  return obj;
}

function StatusBar_SetText(text){
  this.Col1.innerHTML=text;
}