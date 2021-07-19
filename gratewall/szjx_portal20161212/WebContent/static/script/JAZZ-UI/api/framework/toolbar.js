/**
 * EasyFramework.Class.ToolBar 
 *
*/
ToolBar.prototype.Add                = function(object){};
ToolBar.prototype.AddSeparator = function(){};
function ToolBar(parent)
{
  var obj = new Table(parent);
  obj.Buttons=new Array();
  obj.style.borderStyle='solid';
  obj.style.borderWidth=0;
  obj.style.borderColor=DefaultStyle.BorderColor;
  obj.style.backgroundImage='url('+jcl_ResourceURL+'toolbar/toolbarbg.gif)';
  obj.style.backgroundRepeat='repeat-x';
  obj.BodyRow =   new TR(obj);
  obj.tbody.appendChild(obj.BodyRow);
  obj.Col1=new TD(obj.BodyRow);
  obj.Col1.style.borderWidth=0;
  obj.Col1.style.width=1;
  obj.LeftSep=new Image(obj.Col1);
  obj.LeftSep.src=jcl_ResourceURL+"toolbar/separator.gif";
  obj.BodyRow.appendChild(obj.Col1);
  obj.RightCol=new TD(obj.BodyRow);
  obj.RightCol.style.borderWidth=0;
  obj.RightCol.style.width="99%";
  obj.BodyRow.appendChild(obj.RightCol);
  obj.cellSpacing=0;
  obj.cellPadding=0;
  obj.Add=ToolBar_Add;
  obj.AddSeparator=ToolBar_AddSeparator;
  return obj;
}
function ToolBar_Add(button){
  var newCol=new TD(this.BodyRow);
  newCol.style.borderWidth=0;
  newCol.style.paddingTop=1;
  newCol.style.paddingBottom=1;
  this.BodyRow.insertBefore(newCol, this.RightCol);
  newCol.appendChild(button);
  if(button.src==null)
	this.Buttons[this.Buttons.length]=button;
  button.Owner=this;
}

function ToolBar_AddSeparator(){
  var button = new Image(this);
  button.src=jcl_ResourceURL+"toolbar/separator_h.gif";
  this.Add(button);
}
//ToolButton
ToolButton.prototype=function(enabled){};
function ToolButton(parent){
  var obj= new Div(parent);
  obj.Image=new Image(obj);
  obj.DisableImage=null;
  obj.EnableImage=null;
  obj.style.cursor='pointer';
  obj.style.padding=2;
  obj.style.paddingBottom=3;
  obj.style.borderWidth=0;
  obj.style.borderStyle="solid";
  obj.style.borderColor=DefaultStyle.MenuBorderColor;

  obj.onmouseover=ToolButton_onmouseover;
  obj.onmouseout=ToolButton_onmouseleave;
  obj.SetEnable=ToolButton_SetEnable;
  return obj;
}
function ToolButton_SetEnable(enabled){
  this.disabled=!enabled;
  if(enabled) {
    if(this.EnableImage)
	  this.Image.src=this.EnableImage;
	if(this.OldOnClick)this.onclick=this.OldOnClick;
  }
  else {
    if(this.DisableImage) {
	  if(!this.EnableImage)
		 this.EnableImage=this.Image.src;
	  this.Image.src=this.DisableImage;
	}
	if(this.onclick)this.OldOnClick=this.onclick;
	this.onclick=null;
  }
}

function  ToolButton_onmouseover(event)
{
  if(this.disabled)return;
  this.style.padding=1;
  this.style.paddingBottom=2;
  this.style.borderWidth=1;
  this.style.backgroundColor=DefaultStyle.SelectedColor;
  if(!this.parentNode.disabled)
	this.parentNode.focus();
}
function  ToolButton_onmouseleave(event)
{
  this.style.padding=2;
  this.style.paddingBottom=3;
  this.style.borderWidth=0;
  this.style.backgroundColor="";
}
