/*
 * EasyFramework Control 类
 * 用于动态构建基本控件
 */
/*
  * SELECT 
  */
function Select(parent)
{
 var pdiv,obj=getDocument(parent).createElement('SELECT');
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 obj.Add  = SELE_add;
 obj.Clear= SELE_clear;
 obj.Locate = SELE_Locate;
 obj.oncontextmenu=div_oncontextmenu;
 DefaultStyle.SetStyle(obj);
 return obj;
}
//清空
function SELE_clear()
{
  var l=this.options.length;
  for (var i=0; i<l; i++){
    this.options.remove(0);
  }
}
//增加
function SELE_add(text, value)
{
	var oOption = getDocument(this).createElement("OPTION");
	this.options.add(oOption);
	oOption.innerHTML = text;
	oOption.value         = value;
	if (arguments.length==4 && arguments[2].length == arguments[3].length )
		setEx_Attribute(oOption,arguments[2],arguments[3]);
}
function SELE_Locate()
{   
  var v=this.value;
  for(var i=0;i<this.options.length;i++){
    if(this.options[i].value==v){idx=i;break;}
  }
  if(this.options[idx])this.options[idx].selected=true;
}

function setEx_Attribute(obj,arrAtt,arrValue){
	for (var i=0;i<arrAtt.length;i++)obj.setAttribute(arrAtt[i], arrValue[i]);
}
/*
  * BUTTON   (INPUT TYPE=BUTTON) 
  */
function Button(parent)
{
  var obj=new Input(parent,'button');
  return obj;
}
/*
  * IMAGE   (INPUT TYPE=IMAGE) 
  */
function Image(parent)
{
  var obj=new Input(parent,'image');
  return obj;
}
function SpeedButton(parent){
  var obj=new Div(parent);
  obj.Text = new Span(obj);
  obj.Text.style.position = 'relative';
  obj.align = "center";
  obj.Text.style.top=3;
  obj.Text.style.cursor = 'pointer';
  obj.style.cursor = 'pointer';
  obj.style.width=75;
  obj.style.height=21;
  obj.onmouseover=SpeedButton_onmouseover;
  obj.onmouseout=SpeedButton_onmouseleave;
  obj.onblur=SpeedButton_onmouseleave;
  obj.SetUp=function(){this.style.backgroundColor=this.Parent.style.backgroundColor;this.style.borderColor=this.style.backgroundColor;}
  obj.style.borderWidth=1;
  obj.style.borderStyle='solid';
  obj.SetFocus=function(){this.style.borderColor=clShadow;this.style.backgroundColor=clButton;}
  obj.SetUp();

  return obj;
}
function SpeedButton_onmouseover(){
	this.SetFocus();
}
function SpeedButton_onmouseleave(){
	this.SetUp();
}
/*
  * BUTTON   (INPUT TYPE=SUBMIT) 
  */
function Submit(parent)
{
  return new Input(parent,'submit');
}
/*
  * BUTTON   (INPUT TYPE=RESET) 
  */
function Reset(parent)
{
  return new Input(parent,'reset');
}
/*
  * 上传  (INPUT TYPE=FILE) 
  */
function FileUpload(parent)
{
  return new Input(parent,'file');
}
/*
  * 复选框  (INPUT TYPE=CHECKBOX) 
  */
function CheckBox(parent)
{
  var obj=new Input(parent,'checkbox');
  return obj;
}
/*
  * 单选框  (INPUT TYPE=RADIO) 
  */
function Radio(parent)
{
  var obj=new Input(parent,'radio');
  obj.onclick=Radio_onclick;
  return obj;
}
function Radio_onclick()
{
  this.checked=!this.checked;
}
/*
  * 密码输入框  (INPUT TYPE=PASSWORD) 
  */
function PassWord(parent){
  return new Input(parent,'password');
}
/*
  * 普通输入框  (INPUT TYPE=TEXT) 
  */
function Edit(parent)
{
  var obj=new Input(parent,'text');
  obj.onmouseover=EDIT_onmouseover;
  return obj;
}
function EDIT_onmouseover(){
  this.focus();
}
/*
  * 普通输入框  (INPUT TYPE=TEXT) 
  */
function Input(parent,type)
{
 var pdiv,obj=getDocument(parent).createElement('input');
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 obj.setAttribute('type',type);
 pdiv.appendChild(obj);
 obj.Parent=pdiv;
 obj.oncontextmenu=div_oncontextmenu;
 if(type.toUpperCase()!='IMAGE'&&type.toUpperCase()!='IMG'
   &&type.toUpperCase()!='RADIO'&&type.toUpperCase()!='CHECKBOX')
	DefaultStyle.SetStyle(obj);
 return obj;
}
/*
  * SPAN  
  */
function Span(parent)
{
 var pdiv,obj=getDocument(parent).createElement('span');
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 obj.oncontextmenu=div_oncontextmenu;
 if(isMoz)
	addEventTimerListener(obj,SPAN_InnerText_onpropertychange);
 return obj;
}

function SPAN_InnerText_onpropertychange(sender){
	if(sender.oldInnerText!=sender.innerText){
		sender.innerHTML=sender.innerText;
		sender.oldInnerText=sender.innerText;
	}		
}
/*
  * 超链接  
  */
function Link(parent)
{
 var pdiv,obj=getDocument(parent).createElement('a');
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 obj.oncontextmenu=div_oncontextmenu;
 return obj;
}
/*
  * TextArea  
  */
function TextArea(parent)
{
 var pdiv,obj=getDocument(parent).createElement('textarea');
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 obj.oncontextmenu=div_oncontextmenu;
 return obj;
}
/*
  * TABLE  
  */
function Table(parent)
{
  var pdiv,obj=getDocument(parent).createElement('table');
  if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
  pdiv.appendChild(obj);
  obj.border=1;
  obj.cellSpacing=0;
  obj.cellPanding=0;
  obj.thead = getDocument(parent).createElement('thead');
  obj.appendChild(obj.thead);
  obj.tbody = getDocument(parent).createElement('tbody');
  obj.appendChild(obj.tbody);
  obj.AppendRow =Table_AppendRow;
  obj.AppendCell =Table_AppendCell;
  obj.AddRow      =Table_AppendRow;
  obj.AddCell      =Table_AppendCell;
  if(isIE)
	obj.onselectstart=function(){return false;}
  else
	obj.style.MozUserSelect="none";
  return obj;
}
function Table_AppendRow(obj)
{
  var row=new TR(obj);
  if(!obj)
	obj = this.tbody;
  obj.appendChild(row);
  return row;
}
function Table_AppendCell(row)
{
  var cell = new TD(row);
  row.appendChild(cell);
  return cell;
}
function TR(parent){
  var row=getDocument(parent).createElement('tr');
  return row;
}
function TD(parent){
  var cell=getDocument(parent).createElement('td');
  return cell;
}
function Rect(parent)
{
  var obj=new Div(parent);
  obj.style.borderStyle='solid';
  obj.style.borderWidth=1;
//  obj.style.position='absolute';
  return obj;
}
/*
  * DIV  
  */
function Div(parent)
{
 var pdiv,obj=getDocument(parent).createElement('Div');
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 obj.Parent=pdiv;
 obj.oncontextmenu=div_oncontextmenu;
/*  if(isIE)
	obj.onselectstart=function(){return false;}
  else
	obj.style.MozUserSelect="none";*/
 return obj;
}
function div_oncontextmenu(event)
{
  if(!event)event=window.event;
	var popupmenu=this.PopupMenu;
	if(popupmenu)if(popupmenu.AutoPopup)this.PopupMenu.Popup(event.clientX,event.clientY);
    event.returnValue = false;
    event.cancelBubble=true;
}
/*
  * ProgressBar  
  */
function ProgressBar(parent)
{
  obj=new Div(parent);
  obj.ProgressBar=new Div(obj);
  obj.Step=0.1;
  obj.ProgressBar.style.backgroundColor=clMaroon;
  obj.ProgressBar.style.width = "0%";
  obj.ProgressBar.style.height = "100%";
  obj.style.borderWidth=1;
  obj.style.borderStyle='solid';

  obj.SetStep=ProgressBar_SetStep;
  return obj;
}
function ProgressBar_SetStep(step){
  if(step>100)step=100;
  this.Step=step;
  this.ProgressBar.style.width =step+"%";
}

/*
 *GroupBox
 */
function GroupBox(parent)
{
  obj=new Div(parent);
  obj.Container=new Div(obj);
  obj.Container.style.width = "95%";
  obj.Container.style.height = "95%";
  obj.Container.style.borderWidth=1;
  obj.Container.style.borderStyle='solid';
  obj.TitleLabel=new Span(obj);
  obj.TitleLabel.innerHTML="Group";
  obj.TitleLabel.style.position='absolute';
  obj.TitleLabel.style.left=10;
  obj.TitleLabel.style.top=-8;
  obj.TitleLabel.style.backgroundColor=clWhite;
  return obj;
}
//Line
function Line(parent)
{
  var pdiv,obj=getDocument(parent).createElement('hr');
  if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
  pdiv.appendChild(obj);
  obj.oncontextmenu=div_oncontextmenu;

  obj.size=1;
  obj.color=clBlack;
  return obj;
}

//Direction
function Direction(){
	this.Horizontal = 0;
	this.Vertical = 1;
	return this;
}
window.Direction = new Direction();

//RadioGroup
RadioGroup.prototype.Show=null;
RadioGroup.prototype.Direction=0;//0:horizontal,1:vertical
RadioGroup.prototype.OnClick=null; //radion的onclick

function RadioGroup(parent){
	var pdiv,obj=getDocument(parent).createElement('div');
	if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
    pdiv.appendChild(obj);
	obj.Button=new Array(); 
	obj.Span=new Array(); 
	obj.value;
	obj.Items=new Strings(); 
	obj.Values=new Strings();
	obj.SubDiv=new Array();
	obj.Show=RadioGroup_createradio;
	obj.CreateRadio_H=RadioGroup_createradio_h;	
	obj.CreateRadio_V=RadioGroup_createradio_v;	
	obj.removeradios=RadioGroup_removeradios;
    obj.oncontextmenu=div_oncontextmenu;
	return obj;
}
function RadioGroup_createradio(){
	if(this.Direction==0){
		this.CreateRadio_H();
	}
	else{
		this.CreateRadio_V();
	}
}
function RadioGroup_createradio_h(){ 
  if(this.FrameDiv)this.removeradios();
  var item=this.Items.Strings;
  var values=this.Values.Strings;
  this.FrameDiv=getDocument(this).createElement('div');
  this.appendChild(this.FrameDiv);
  for(var i=0;i<item.length;i++){
	this.Button[i]=getDocument(this).createElement('input');
	this.SubDiv[i]=getDocument(this).createElement('div');
	this.Span[i]=getDocument(this).createElement('span');
	this.FrameDiv.appendChild(this.SubDiv[i]);
	this.Button[i].type="radio"; 	
    if(!values[i])values[i]=item[i];
	this.Button[i].value=values[i];
	this.Span[i].innerText=item[i];		
	this.Button[i].parent=this;				
	this.Button[i].onclick=this.OnClick;
	this.SubDiv[i].appendChild(this.Button[i]);
	this.SubDiv[i].appendChild(this.Span[i]);
  }		
}
function RadioGroup_createradio_v(){
  if(this.FrameDiv)this.removeradios();
  var item=this.Items.Strings;
  var values=this.Values.Strings;
  this.FrameDiv=new Table(this);
  this.FrameDiv.style.borderWidth=0;
  var row=this.FrameDiv.AppendRow(this.FrameDiv.tbody);
  var cell;
  for(var i=0;i<item.length;i++){
	cell=this.FrameDiv.AppendCell(row);
	cell.style.borderWidth=0;
	this.Button[i]=getDocument(this).createElement('input');
	this.SubDiv[i]=getDocument(this).createElement('div');
	this.Span[i]=getDocument(this).createElement('span');
	this.Button[i].type="radio"; 	
    if(!values[i])values[i]=item[i];
	this.Button[i].value=values[i];
	this.Span[i].innerText=item[i];			
	this.Button[i].parent=this;				
	this.Button[i].onclick=RadioGroup_onclick;
	this.SubDiv[i].appendChild(this.Button[i]);
	this.SubDiv[i].appendChild(this.Span[i]);
	cell.appendChild(this.SubDiv[i]);	
  }		
}
function RadioGroup_removeradios()
{
   this.removeChild(this.FrameDiv);
   this.FrameDiv.removeNode(true);
}
function RadioGroup_onclick(){
	for(var i=0;i<this.parent.Items.Strings.length;i++){ 
		this.parent.Button[i].checked=false;
	}
	this.checked=true;
	this.parent.value=this.value;
	this.Select=this;
	if(this.parent.OnClick)this.parent.OnClick(this.parent, this);
}

//Form
function Form(parent)
{
 var pdiv,obj=getDocument(parent).createElement("form");
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 return obj;
}
//IFrame
function IFrame(parent,name)
{
 var xml="<iframe name="+name+" ></iframe>";
 if(isMoz)
	xml="iframe";
 var pdiv,obj=getDocument(parent).createElement(xml);
 if(parent){pdiv=parent;}else pdiv=getDocument(parent).body;
 pdiv.appendChild(obj);
 obj.Show=IFrame_Show;
 return obj;
}
function IFrame_Show(){
  if(isMoz){
	this.contentWindow.document.write(this.contentWindow.document.body.innerHTML);
  }
}