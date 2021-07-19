/** 
 * @ �������� / )չԭ�η��� 
 *
 * @author  Him
 * @version 1.0 
 */
 
var isMoz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
var isIE     = (typeof window.ActiveXObject != 'undefined'); 
//��ӹ��������洢�����
var DomCache = null;
/**
 * )չ�� Package �ؼ���.
 * @param {String} pkname package name
 * 
 */
 function getRow()
			{
				var element = event.srcElement;
				if (element.tagName != null)
				{
					while (element.tagName.toUpperCase() != "TR")
					{
						element = element.parentNode;
						if (element == null)
							break;
					}
					return element;
				}
				return null; 
			}
window.Package=Package;
function Package(pkname) {
  var pkNames   = pkname.split(".");
  var pn,prevpn = "";
  for(var i=0; i<pkNames.length; i++) {
    pn = pkNames[i];
    if (typeof(pn) != "function") {
      var _s = "window."+prevpn+pn+"=new Object();";
	  if(isIE)
		execScript(_s, "JavaScript");
	  if(isMoz)
		window.eval(_s);
    }
	prevpn = pn+".";
  }
}
/**
 * )չ�� Import �ؼ���  �� ��̬����.js �� .css
 * @param {String} url file name
 * @param {String} type file type,as .js,.css
 */
window.Import=Import;
function Import(url, type){
  var i, 
      base, 
      src = "System.js", 
      scripts = document.getElementsByTagName("script"); 
  for (i = 0; i < scripts.length; i++) {
      if (scripts[i].src.match(src)) {
          base = scripts[i].src.replace(src, "");
          break;
      }
  }
  if (type == "css") {
      document.write("<" + "link href=\"" + base + url + "\" rel=\"stylesheet\" type=\"text/css\"></" + "link>");
  } else {
      document.write("<" + "script src=\"" + base + url + "\"></" + "script>");
  }
}
/**
 * Construct a package to impl include files.
 * @class FunctionIsExist is the ext keyword FunctionIsExist.  
 * @param {String} url file name
 * @param {String} type file type,as .js,.css
 */
function FunctionIsExist(func_name){
	if(func_name)
		_System.Out(func_name+" is exist.");
}
Package("js.lang");
js.lang.Object=Object;

String.prototype.Trim = function(){return  this.replace(/^\s*(.*?)[\s\n]*$/g,  '$1');} 
String.prototype.LeftTrim = function(){return this.replace(/^\s+/g,"");} 
String.prototype.RightTrim = function(){return this.replace(/\s+$/g,""); } 
String.prototype.Equals = function(str){
	if(this == str) return true;
	return false;
}
function System(){}
System.prototype.In = function(title, default_value) {return prompt(title, default_value);}
System.prototype.Out = function(message) {alert(message);}
System.prototype.Err = function(message) {alert(message);}
System.prototype.MessageBox = function(message) { 	alert(message);}
System.prototype.Exit = function(message) { window.close();}
System.prototype.GetBrowserType = function() { 
	if(isIE)
		return "IE";
	else if(isMoz)
		return "Moz";
	return "Other";
}
//���ýӿ�
window._System = new System();
window.In     = _System.In;
window.Out  = _System.Out;
window.Err    = _System.Err;
window.Exit   = _System.Exit;
window.GetBrowserType = _System.GetBrowserType;
/*window.$ = function (us){
				var elements = [];
				for (var i = 0; i < arguments.length; i++) {
					var element = arguments[i];
					if (typeof element == 'string')
						element = document.getElementById(element);
					
					if (arguments.length == 1)
						return element;

					elements.push(element);
				}
				return elements;
}*/
window.$V = function (us){return document.getElementById(us).value;}
window.$R = function (us){return document.getElementById(us).realvalue;}
window.$N = function (us){return document.getElementsByName(us);}
window.$T = function (us){return document.getElementsByTagName(us);}
window.$E = function (str){return encodeURIComponent(str);}
window.Color=new Color();

var Err_HasDets = 'Master table has detail data.please do it after while you delete details.';var Err_OpenErr = '��DataSet �򿪷������.:';var Err_NoOpen = 'DataSet ��δ��.';var Err_Null       = "��ݲ���Ϊ��.";var Err_Long      = '��ݳ�����󳤶�����.';var Err_Number  ='��Ч�������.';var Err_DateTime='��Ч������/ʱ��';var Err_NoURL    ="δ����URL.";var Err_ReadOnly="���Ϊֻ��״̬.";var Err_NoField   ="δ�����ֶ�";var Err_NoDataSet="û��DataSet.";var clBlack		= "#000000";
var clMaroon	= "#000080";var clGreen		= "#008000";var clOlive		= "#008080";var clNavy		= "#800000";var clPurple		= "#800080";
var clTeal			= "#808000";var clGray		= "#808080";var clSilver		= "#C6C3C6";var clRed			= "#FF0000";var clLime			= "#00FF00";
var clYellow		= "#00FFFF";var clBlue			= "#0000FF";var clFuchsia	= "#FF00FF";var clAqua		= "#FFFF00";var clLtGray		= "#C0C0C0";
var clDkGray		= "#808080";var clWhite		= "#FFFFFF";var clMoneyGreen = "#C0DCC0";var clSkyBlue        = "#F0CAA6";var clCream          = "#F0FBFF";
var clMedGray       = "#A4A0A0";var clNone             = "#FFFFFF";var clDefault          = "#CCCCFF";var StandardColorsCount = 16;var ExtendedColorsCount = 4;
var clBlue0='#9CBEE7';var clButton='#CECFCE';var clShadow="#ADAAAD";var clHightGray = "#F7F3F7";var clBorder = "#9C9A9C";var DecPoint=".";
var jcl_ResourceURL         = "jcl/images/";
var image_AppendRecord = "newrecord.gif";
var image_DeleteRecord   = "deleterecord.gif";
var image_SaveRecord      = "saverecord.gif";
var image_First = "image_first.gif";
var image_First_Disable = "image_first_disable.gif"; 
var isInspect=false;
window.VirtualPath="";
window.Exception=true;
if(window.opener)window.Parent=window.opener;
if(window.dialogArguments){window.Parent=window.dialogArguments;}
function Color(){var obj=new Object();	obj.XP_LowBlue='#A5C7F7';	obj.XP_SilverWhite='#F7F7F7';obj.XP_LowWhite='#FFEFC6';	obj.XP_Purple='#6B6984';
	obj.Blue=clBlue;obj.White=clWhite;obj.Red=clRed;obj.Black=clBlack;obj.Gray=clGray;obj.Silver=clSilver;	obj.Lime=clLime;obj.Yellow=clYellow;
	obj.Fuchsia=clFuchsia;obj.Aqua=clAqua;obj.LtGray=clLtGray;return obj;}
XP_Style.prototype.SetStyle=function(obj){};
XP_Style.prototype.SetFont=function(obj){};
function XP_Style(){var obj=new Object();obj.BorderColor='#94A6B5';obj.BgColor=Color.XP_LowBlue; obj.MenuBorderColor='#002C94';obj.SelectedColor='#FFEFC6';
	obj.HighlightColor=Color.White;obj.PanelColor="#F7F7F7";	obj.DefaultColor=Color.White;obj.GridLineColor="#E7E7E7";obj.SelectedRowColor="#FFE7B5"
	obj.BorderStyle='solid';obj.BorderWidth=1;obj.SetFont=XP_Style_setFont;obj.SetStyle=XP_Style_setStyle;return obj;}
function XP_Style_setFont(obj){ obj.style.fontFamily='Verdana';obj.style.fontSize='11'; obj.style.color=clBlack;}
function XP_Style_setStyle(obj){obj.style.borderStyle=this.BorderStyle;obj.style.borderColor=this.BorderColor;obj.style.borderWidth=this.BorderWidth;}

window.XP_Style=new XP_Style();
window.DefaultStyle=XP_Style;
MouseEvent.prototype.LeftButton=1;MouseEvent.prototype.RightButton=2;
MouseEvent.MiddleButton=4;
function MouseEvent(){	var obj=new Object();obj.LeftButton=1;obj.RightButton=2;obj.MiddleButton=4;return obj;}
window.MouseEvent=new MouseEvent();
 
 Array.prototype.has    = array_has;	
 Array.prototype.remove    = array_remove;
 Array.prototype.removeAt = array_removeAt;
 
function array_has(val) { var i;for(i = 0; i < this.length; i++) {if(this[i] == val)  {return true; } } return false;}
function array_remove(val){var i; var j;for(i = 0; i < this.length; i++) {if(this[i] == val) { for(j = i; j < this.length - 1; j++) {this[j] = this[j + 1];  }  this.length = this.length - 1; } } } 
function array_removeAt(index){var i;if(index < this.length) { for(i = index; i < this.length - 1; i++) {this[i] = this[i + 1]; } this.length = this.length - 1; }}
//----------------------------------------------------------------------
//StringBuffer ����t���ַ� 
function StringBuffer(){
	this.arr = [];  
}
//t���ַ�
StringBuffer.prototype.append = function(str){
	this.arr.push(str.toString());
};
StringBuffer.prototype.insert = function(offset,str){
	try{
		if(offset>=this.size()-1)this.append(str);
		else{
			var temp = [];
			temp.push(str.toString());
			temp.push(this.arr[offset]);
			this.arr.splice(offset,offset,temp);
		}
	}catch(e){
		Err(e.message);
	}
};
StringBuffer.prototype.size = function(){
	return this.arr.length+1;
}
//������arguments[0]�ָ���ַ�.
StringBuffer.prototype.toString = function(){
	var joinStr='';
	if(arguments.length==1) joinStr = arguments[0];
	return this.arr.join(joinStr);
};
 //----------------------------------------------------------------------
function getPosLeft(oElement, parent) {var x=0; el=oElement; while(el){ if(el.offsetParent==parent) break; x += el.offsetLeft;   el=el.offsetParent;  } return x;}  
function getPosTop(oElement, parent) { var y=0; el=oElement; while(el){ if(el.offsetParent==parent) break; y += el.offsetTop;  el=el.offsetParent;  } return y;} 

window.Show         =function(url,title,ops){var bars;if(ops)bars=ops;else bars='menubar=yes,status=yes,toolbar=yes,scrollbars=yes,resizable=yes';return open(url,title,bars);}
window.ShowModal=function(url,ops){var o;if(ops)o=ops;else o='resizable:no';window.showModalDialog(url,window,o);}
function getDocument(object) {
  var doc;
  if(object)
	 doc=object.ownerDocument
  else doc=window.document;
  return doc;
}
function GetXMLDOM() {var xmlDoc;   xmlDoc = new ActiveXObject("Microsoft.XMLDOM");return xmlDoc; }/*���ܸ�Ϊdom5*/
function GetXMLHTTP() 
{ 
	var xmlhttp=null;
	try 
	{ xmlhttp=new ActiveXObject("Msxml2.XMLHTTP"); 	} catch(e) { 
		try { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); } catch(ex) {xmlhttp=null;} 
	} 
	if ( !xmlhttp && typeof XMLHttpRequest != "undefined" ) { xmlhttp=new XMLHttpRequest(); } 
	return xmlhttp; 
} 
window.getDom = function (strPath,strValue)
{
				var oDom	= new ActiveXObject("MSXML2.DOMDocument.5.0");
				oDom.async	= false;
				if (strPath != null){oDom.load(strPath) ? oDom : null;if (oDom!=null){
						if (oDom.documentElement.getAttribute("state")!=null){showAlert(oDom);}
					}return oDom==null ? null : oDom;
				}else{
					oDom.loadXML(strValue) ? oDom : null;if (oDom!=null){
						if (oDom.documentElement.getAttribute("state")!=null){showAlert(oDom);}
					}return 	oDom==null ? null : oDom;
				}
}
window. getNamedObj = function(strTagName)
{
	var element = event.srcElement;
	if (element.tagName != null )
	{
		while (element.tagName.toUpperCase() != strTagName)
		{
			element = element.parentNode;
			if (element == null)
				break;
		}
		return element;
	}
	return null;
}
function parseXML(st){var result;result = GetXMLDOM(); result.loadXML(st); return result;}
function XMLToStr(xml){return xml.xml; }
function DateTimeToStr(d){if(isNaN(d))return;var s = d.toLocaleString() ;return s;}
function DateToStr(d){if(isNaN(d))return;var s = d.toLocaleString();return s;}
function StrToTimeLong(value) {var r=/[\u4e00-\u9fa5]/;value=value.replace(r, "/");value=value.replace(r, "/");  value=value.replace(r, "");var v=Date.parse(value); return v;}
/*
  * ����ת��
  */
function FormatFloat(value,mask){return BasicFormat(value,mask,'FormatNumber');}
function FormatDate(varDate, bstrFormat, varDestLocale){return BasicFormat(varDate,bstrFormat,'FormatDate',varDestLocale);}
function FormatTime(varTime, bstrFormat, varDestLocale){return BasicFormat(varTime,bstrFormat,'FormatTime',varDestLocale);}
function BasicFormat(value,mask,action,param){
	var xmlDoc;var xslDoc;	var v='<formats><format><value>'+value+'</value><mask>'+mask+'</mask></format></formats>';
	xmlDoc=parseXML(v);  var x; x='<xsl:stylesheet xmlns:xsl="uri:xsl">'	 ;	x+='<xsl:template match="/">';
	x+='<xsl:eval>'+action+'('+value+',"'+mask+'"';	if(param)x+=','+param;x+=')</xsl:eval>';x+='</xsl:template></xsl:stylesheet>';
	xslDoc=parseXML(x);var s; s= xmlDoc.transformNode(xslDoc);return s;}
function isOddNumber(value){
  var v = parseInt(value/2);
  if(v*2!=value) return true;
  else return false;} 
function Strings(){ this.Strings=new Array(); this.Count=0; this.Add=function(v){var a=this.Strings;a[a.length]=v;this.Count=a.length};this.Clear=function(){this.Strings.length=0;this.Count=0;};}
//###################################################################################################
function Items(parent){
  this.Parent=parent;
  this.Item=new Array();
  this.Count=0;
  this.Add=Items_Add;
  this.Clear=Items_Clear;
  this.Delete=Items_Delete;
  return this;
}
function Items_Add(item)
{
  if(this.Parent)this.Parent.appendChild(item);
  this.Item[this.Item.length]=item;
  item.Index=this.Item.length-1;
  item.Parent=this.Parent;
  this.Count=this.Item.length;
}
function Items_Delete(item)
{
  _arrayremove(this.Item,item.Index);
  if(this.Parent)this.Parent.removeChild(item);
  item.removeNode(true);
  this.Count=this.Item.length;
}
function Items_Clear()
{
   for(var i=0;i<this.Item.length;i++){
	   this.Delete(this.Item[i]);
   }
}
new Params();
Params.prototype.Count;
Params.prototype.Add=function(param){this.Param[param.Name]=param;this.Paramx[this.Paramx.length]=param;this.Count=this.Paramx.length;}
Params.prototype.Clear=function(){this.Paramx.length=0;this.Param=new Array();this.Count=0;}
function Params(){
  this.Param=new Array();
  this.Paramx=new Array();
}
new Param(null,null,null);
Param.prototype.Name;
Param.prototype.Value;
Param.prototype.Type;
function Param(name,value,type)
{
  this.Name=name;this.Value=value;this.Type=type;
}
//###################################################################################################

function jcl_err(e)
{
  if(window.Exception){alert(e);}else throw e;
}

function DC_onpropertychange(sender)
{
  var n;
  if(isIE) {
	  n=event.propertyName;
	  sender=this;
  }
  else
	  n='DataSet';
  switch(n){
	case 'DataSet':
	  if(sender.OldDataSet==sender.DataSet)return;
	  if(sender.OldDataSet)sender.OldDataSet.RemoveLink(sender);
      if(sender.DataSet)sender.DataSet.LinkObj(sender);
	  sender.OldDataSet=sender.DataSet;
	  break;
  }
}

function addEventTimerListener(obj,func){listenerFuncList[listenerFuncList.length]=func;listenerObjList[listenerObjList.length]=obj;return listenerFuncList.length-1;}
function removeEventTimerListener(index){	_arrayremove(listenerFuncList, index);_arrayremove(listenerObjList, index);}
function clearEventTimerListener(index){listenerFuncList.length=0;listenerObjList.length=0;}
function processEventTimerListener(){	for(var i=0;i<window.listenerFuncList.length;i++){listenerFuncList[i](listenerObjList[i]);}}
function startEventListener(){window.listenerFuncList=new Array();	window.listenerObjList=new Array();window.eventDelay=300;	window.eventTimer=setInterval(processEventTimerListener, window.eventDelay); }
function stopEventListener(){clearInterval(window.eventTimer); }
if(isMoz){startEventListener();}
function Reflector()
{
	var obj = new Object();
    obj.getProperties=function(obj){
        var props = "";
		var type;
        for (idx in obj)
        {
			type = typeof(obj[idx]);
			if(type!='function')
               props += idx+":"+type+";";
        }
        return props;
    }
    obj.getMethods=function(obj){
        var props = "";
		var type;
        for (idx in obj)
        {
			type = typeof(obj[idx]);
			if(type=='function')
               props += idx+":"+type+";";
        }
        return props;
    }	
    obj.getMembers=function(obj){
        var props = "";
		var type;
		var member;
		var pos;
        for (idx in obj)
        {
			member = obj[idx];
			if(member) {
				type = typeof(obj[idx]);
				if(type=='function') {
					member = member.toString();
					pos = member.indexOf('(');
					if(pos!=-1) {
						member = member.substring(pos);
						pos = member.indexOf(')');
						if(pos!=-1)
							member = member.substring(0,pos+1);
					}
					props += idx+":"+type+member+";";
				}
				else if(type=='object'){
					type = Object_GetClassName(obj[idx]);
					props += idx+":"+type+";";
				}
				else props += idx+":"+type+";";
			}
        }
        return props;
    }	
	return obj;
}
function _arrayremove(changingarray,index){
	if(changingarray)	{
		var newarray1=changingarray.slice(0,index);
		var newarray2=changingarray.slice(index+1,changingarray.length);
		changingarray.length=0;
		changingarray=newarray1.concat(newarray2);
		return changingarray;
	}
}

function _arraypush(changingarray,newarraynode,index) {
   if ( changingarray ) {
     var newarray1 = changingarray.slice(0,index);
     var newarray2 = changingarray.slice(index,changingarray.length);
     newarray1[newarray1.length] = newarraynode;
     changingarray.length = 0;
     changingarray = newarray1.concat(newarray2);
     return changingarray;
  }
}

function _arrayswap(changingarray,indexfirst,index) {
   if ( changingarray ) {
      var m=changingarray[indexfirst];
      changingarray[indexfirst] = changingarray[index];
      changingarray[index] = m;
   }
}

function setStyle(obj){obj.style.fontFamily='Verdana';obj.style.fontSize='12';}
function setFont(obj){obj.style.fontFamily='Verdana';obj.style.fontSize='12';obj.style.color=clBlack;}
function unitToValue(value){
	if(value!=null) {
	  var length=value.length;
	  return parseFloat(value.substring(0,length-2));
	}
	else return 0;
}
function Rectangle(x,y,w,h){this.x=x;this.y=y;this.width=w;this.height=h;this.isContainXY=Rectangle_isContainXY;}
function Rectangle_isContainXY(x,y){ var result=false;
  if(x>this.x&&x<(this.x+this.width)&&y>this.y&&y<(this.y+this.height)){result=true;}return result;}