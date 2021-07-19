/* 
 * Splitter 类  创建多框架UI
 */
function Splitter(parent){
  var obj = new Table(parent);
  obj.style.width=600;
  obj.style.height=400;
  obj.style.borderWidth=1;
  obj.style.borderStyle='solid';
  obj.style.borderColor=DefaultStyle.BorderColor;
  obj.cellSpacing=0;
  obj.cellpadding=0;
  obj.style.tableLayout='fixed';
  //设置方向 1-横向 0-纵向
  obj.SetOrientation = Splitter_SetOrientation; 
  //设置各个部分内容 
  obj.SetLeftComponent	= obj.SetTopComponent=Splitter_SetLeftComponent;
  obj.SetRightComponent	= obj.SetBottomComponent=Splitter_SetRightComponent;
  obj.onselectstart=function(){return false;}
  //默认纵向
  obj.SetOrientation(0);
  //初始宽度/高度
  obj.SetDividerLocation=Splitter_SetDividerLocation;
  return obj;
}
function Splitter_SetDividerLocation(pos){
  if(this.Orientation==0){
	this.LeftContainer.style.width=pos;
  } else{
	this.LeftContainer.style.height=pos;
  }
  this.UpdateUI();
}
//设置方向 1-横向 0-纵向
function Splitter_SetOrientation(orientation){
  this.Orientation=orientation;
  if(this.row1!=null)this.tbody.removeChild(this.row1);
  if(this.row2!=null)this.tbody.removeChild(this.row2);
  if(this.row3!=null)this.tbody.removeChild(this.row3);
  if(!this.DividerLine) this.DividerLine=new Image();

  if(orientation==0){
	 this.row1              =this.AppendRow();
	 this.LeftContainer=this.TopContainer=this.AppendCell(this.row1);
	 this.Divider=this.AppendCell(this.row1);
	 this.RightContainer=this.BottomContainer=this.AppendCell(this.row1);
	 if(isIE)
		this.Divider.style.width=5;
	 else
		this.Divider.style.width=3;
     this.Divider.style.borderWidth=1;
     this.Divider.style.borderTop=0;
     this.Divider.style.borderBottom=0;
	 this.Divider.style.cursor='col-resize';
     this.Divider.style.backgroundRepeat='repeat-y';
	 this.Divider.style.backgroundImage='url('+jcl_ResourceURL+'splitter/splitter_hbg.gif)';
	 this.DividerLine.src=this.DividerLine.splitter_bg=jcl_ResourceURL+'splitter/splitter_sel_hbg.gif';
	 this.DividerLine.style.cursor='col-resize';
  }
  else{
	 this.row1=this.AppendRow();
	 this.LeftContainer=this.TopContainer=this.AppendCell(this.row1);
	 this.row2=this.AppendRow();
	 this.Divider=this.AppendCell(this.row2);
	 this.row3=this.AppendRow();
	 this.RightContainer=this.BottomContainer=this.AppendCell(this.row3);
	 if(isIE)
		this.Divider.style.height=5;
	 else
		this.Divider.style.height=3;
     this.Divider.style.borderWidth=1;
	 this.Divider.style.borderLeft=0;
	 this.Divider.style.borderRight=0;
	 this.Divider.style.cursor='row-resize';
     this.Divider.style.backgroundRepeat='repeat-x';
	 this.Divider.style.backgroundImage='url('+jcl_ResourceURL+'splitter/splitter_vbg.gif)';
	 this.DividerLine.src=this.DividerLine.splitter_bg=jcl_ResourceURL+'splitter/splitter_sel_vbg.gif';
	 this.DividerLine.style.cursor='row-resize';
  	 this.DividerLine.style.width='100%';
  }
  this.LeftContainer.style.borderWidth=0;
  this.RightContainer.style.borderWidth=0;
/*  this.LeftContainer.style.borderStyle='solid';
  this.RightContainer.style.borderStyle='solid';
  this.LeftContainer.style.borderColor=DefaultStyle.BorderColor;
  this.RightContainer.style.borderColor=DefaultStyle.BorderColor;  */
  this.Divider.SpaceImage=new Image(this.Divider);
  this.Divider.SpaceImage.src=jcl_ResourceURL+'splitter/space.gif';

  this.Divider.style.borderStyle='solid';
  this.Divider.style.borderColor=DefaultStyle.BorderColor;

  this.DividerLine.style.borderWidth=0;
  this.DividerLine.style.position='absolute';
  this.DividerLine.style.display='none';
  this.DividerLine.Splitter=this;
  this.Divider.Splitter=this;
  this.Divider.onmousedown=Splitter_Divider_onmousedown;
  this.DividerLine.onmouseup=Splitter_DividerLine_onmouseup;
  this.onmousemove=Splitter_onmousemove;
  this.onmouseup=Splitter_onmouseup;
  this.DividerLine.onselectstart=function(){return false;}
  this.UpdateUI=Splitter_UpdateUI;
}
function Splitter_UpdateUI(){
  if(this.LeftComponent)if(this.LeftComponent.UpdateUI)this.LeftComponent.UpdateUI();
  if(this.RightComponent)if(this.RightComponent.UpdateUI)this.RightComponent.UpdateUI();
}
//拖动方法
function Splitter_onmousemove(event){
	if(!this.dragging)return;
	if(!event)event=window.event;
	if(event.button==MouseEvent.LeftButton){//left button
	  if(this.Orientation==0){
		  var x=event.clientX;
		  if(x>0){
			x=x-this.oldX;
			this.DividerLine.style.left=this.DividerLine.offsetLeft+x;
			this.oldX=event.clientX;
		  }
	  }
	  else{
		  var y=event.clientY;
		  if(y>0){
			y=y-this.oldY;
			this.DividerLine.style.top=this.DividerLine.offsetTop+y;
			this.oldY=event.clientY;
		  }
	  }
	}
}
function Splitter_DividerLine_onmouseup(event){
	if(!this.Splitter.dragging)return;
	if(!event)event=window.event;
    if(this.Splitter.Orientation==0){
		var x=getPosLeft(this)-getPosLeft(this.Splitter)-1;
		if(x<0)x=0;
	    this.Splitter.LeftContainer.style.width=x;
	}
	else{
		var y=getPosTop(this)-getPosTop(this.Splitter)-1;
		if(y<0)y=0;
	    this.Splitter.LeftContainer.style.height=y;
	}
	this.style.display='none';
	this.Splitter.style.cursor='default';
	this.Splitter.dragging=false;
	this.Splitter.UpdateUI();
}
function Splitter_onmouseup(event){
	if(!this.dragging)return;
	if(!event)event=window.event;
    if(this.Orientation==0){
		var x=getPosLeft(this.DividerLine)-getPosLeft(this)-1;
		if(x<0)x=0;
		this.LeftContainer.style.width=x;
	}
	else{
		var y=getPosTop(this.DividerLine)-getPosTop(this)-1;
		if(y<0)y=0;
		this.LeftContainer.style.height=y;
	}
	this.DividerLine.style.display='none';
	this.style.cursor='default';
	this.dragging=false;
	this.UpdateUI();
	if(isIE)
		this.releaseCapture();
	else{
		window.releaseEvents(Event.MOUSEMOVE);
		this.onmousemove=null;
	}
}
function Splitter_Divider_onmousedown(event){
	if(!event)event=window.event;
	this.Splitter.DividerLine.style.display='';
	if(event.button==MouseEvent.LeftButton){//left button
	  if(this.Splitter.Orientation==0){
		 this.Splitter.DividerLine.style.width=4;
	  	 this.Splitter.DividerLine.style.height=this.Splitter.offsetHeight-2;
  		 this.Splitter.DividerLine.style.top=getPosTop(this.Splitter)+1;
		 this.Splitter.DividerLine.style.left=getPosLeft(this)+1;
	  }
	  else{
		 this.Splitter.DividerLine.style.height=4;
	  	 this.Splitter.DividerLine.style.width=this.Splitter.offsetWidth-2;
  		 this.Splitter.DividerLine.style.left=getPosLeft(this.Splitter)+1;
		 this.Splitter.DividerLine.style.top=getPosTop(this)+1;
	  }
	  this.Splitter.dragging=true;
      this.Splitter.style.cursor=this.style.cursor;
	  this.Splitter.oldX=event.clientX;
	  this.Splitter.oldY=event.clientY;
	  if(isIE)
		this.Splitter.setCapture();
	  else {
		window.captureEvents(Event.MOUSEMOVE);
		this.Splitter.onmousemove=Splitter_onmousemove;
	  }
	}
}
function Splitter_SetLeftComponent(child){
	this.LeftContainer.appendChild(child);
	this.LeftComponent=child;
	child.style.position='relative';
	child.style.left='';
	child.style.top='';
	child.style.height='100%';
	child.style.width='100%';
	child.style.overflow='auto';
	this.UpdateUI();
}
function Splitter_SetRightComponent(child){
	this.RightContainer.appendChild(child);
	this.RightComponent=child;
	child.style.position='relative';
	child.style.left='';
	child.style.top='';
	child.style.height='100%';
	child.style.width='100%';
	child.style.overflow='auto';
	this.UpdateUI();
}
