var MAX_Z_INDEX=30
var col_menu=[]; //menu collection
function menu(width)
{
	var menu=this
	this.item=[]
	this.subMenu=null
	this.isShow=false
	this.bar=null
	//this.body=document.createElement("table")
	this.body=document.createElement("div")
	document.body.insertAdjacentElement("beforeEnd",this.body)
	this.body.style.cssText="position:absolute;;border-collapse:collapse;border-spacing:1;border:1 solid #0B2565;background-color:white;color:black;display:none;shadow(color=gray,direction=135);table-layout:fixed;filter:progid:DXImageTransform.Microsoft.Shadow(color='#cccccc', Direction=135, Strength=4);"
	this.body.style.pixelWidth=width==null?180:width
	var run=function(cmd,a0,a1,a2)
	{
		if(typeof(cmd)=="string")
		{	try{return eval(cmd);}
			catch(E){alert("run script string error:\n"+cmd);}
		}
		else if(typeof(cmd)=="function"){return cmd(a0,a1,a2);}
	}
	this.add=function(Text,exeType,exeArg,target)
	{
		//var item=menu.body.insertRow()
		//item.style.cssText="font-size:10pt;cursor:default;height:24;"
		//var col1=item.insertCell()
		//var col2=item.insertCell()
		var item=document.createElement("span")
		menu.body.insertAdjacentElement("beforeEnd",item)
		item.style.cssText="padding-top:2;padding-left:14;font-size:10pt;height:21;cursor:default;color:black;background-color:white;width:100%;"
		item.innerHTML=Text
		//col2.innerHTML=Text
		//col2.style.cssText="height:24;padding-left:5;width:100%;"
		//col1.style.cssText="width:26;background-color:buttonface;height:24;text-align:center;"
		//var icon=new Image()
		//if(typeof(ico)=="string")if(icons[ico]!=null)ico=icons[ico].src
		//if(ico!=null && ico!="")//{icon.src=ico;
		//col1.insertAdjacentElement("afterBegin","");}
		menu.item[menu.item.length]=item
		item.enable=true
		item.selected=false
		item.execute=new Function()
		item.remove=function(){item.removeNode(true);
	}
		item.select=function()  
		{   
			         
		      var img=new Image
		      img.src="images/ok2.bmp"
			  item.insertAdjacentElement("afterBegin",img)
			  //var item1=document.createElement("span")
		      //item.insertAdjacentElement("afterBegin",item1)
			  //item.innerHTML="¡Ì"
			  
			  
		}
	    item.unselect=function()
		{
          item.removeChild(item.firstChild)
		  

		}

		exeType=exeType==null?"":exeType
		switch(exeType.toLowerCase())
		{
			case "hide":
				item.execute=function(){menu.hide();}
				break;
			case "url":
				if(typeof(exeArg)!="string")break;
				if(target==null||target=="")target="_self";
				item.execute=function(){menu.hide();open(exeArg,target);}
				break;
			case "js":
				if(typeof(exeArg)!="string")break;
				item.execute=function(){menu.hide();eval(exeArg)}
				break;
			case "sub":
				if(typeof(exeArg.body)=="undefined")break;
				item.execute=function(){menu.show(exeArg);}
				item.innerHTML ="<span style='width:90%;'>"+item.innerHTML+"</span><span style='font-family:Wingdings 3;'>}</span>";
				item.subMenu=exeArg;
				break;
		}
		
		item.onmouseover=function()
		{
			
			item.style.color=item.enable?"black":"gray";
			item.style.backgroundColor="#B7BED3";
			item.style.borderLeft="0"
			for(var i=0;i<menu.item.length;i++){if(menu.item[i].subMenu!=null)menu.item[i].subMenu.hide();}
			if(item.subMenu!=null)menu.show(item.subMenu)
		}
		item.onmouseout=function()
		{
			item.style.filter=""
			item.style.backgroundColor="white";
		}
		item.onmousedown=item.onmouseup=function(){event.cancelBubble=true;}
		item.onclick=function()
		  {
			event.cancelBubble=true;
			if(this.enable)
				{
				 if (!this.selected){this.select();this.selected=true}
			     else {this.unselect();this.selected=false}
				}
		    run(item.execute);
		  }
		return item
	}
	this.addLink=function(url_,text,target)
	{
		if(text==null || text=="")text=url_
		if(target==null || target=="")target="_parent"
		return menu.add(text,"url",url_,target)
	}
	this.seperate=function(){menu.body.insertAdjacentHTML("beforeEnd","<hr style='width:96%;'>");}
	this.show=function()
	{
		var a=arguments;
		var x,y,m=menu.body
		if(a.length==0)
		{
			x=event.clientX+document.body.scrollLeft+document.body.scrollLeft
			y=event.clientY+document.body.scrollTop
		}
		else if(a.length==1 && typeof(a[0])=="object")
		{
			if(typeof(a[0].body)!="undefined")
			{
				m=a[0].body
				m.style.display="block"
				if(m.style.pixelWidth<document.body.offsetWidth-event.x)
				{	x=menu.body.style.pixelLeft+menu.body.offsetWidth}
				else
				{	x=menu.body.style.pixelLeft-m.style.pixelWidth}
				if(m.offsetHeight<document.body.offsetHeight-event.y)
				{	y=event.y+document.body.scrollTop-event.offsetY}
				else
				{	y=event.y-m.offsetHeight+document.body.scrollTop}
			}
			else
			{
				x=event.x+document.body.scrollLeft-event.offsetX-2
				y=event.y+document.body.scrollTop+a[0].offsetHeight-event.offsetY-4
			}
		}
		else if(a.length==2 && typeof(a[0])=="number" && typeof(a[1])=="number")
		{
			x=a[0];y=a[1];
		}
		else{alert("arguments type or number not match!");return;}
		for(var i=0;i<menu.item.length;i++)menu.item[i].style.color=menu.item[i].enable?"black":"gray"
		m.style.pixelLeft=x;
		m.style.pixelTop=y;
		m.style.display="block";
		m.style.zIndex=++MAX_Z_INDEX
		event.cancelBubble=true;
		menu.isShow=true;
		if(menu.bar!=null)
		{
			menu.bar.style.backgroundColor=skinFaceColor;
			//menu.bar.style.border="1 solid gray"
			menu.bar.style.filter="progid:DXImageTransform.Microsoft.Shadow(color='white', Direction=100, Strength=6);"
		}
		
	}
	this.hide=function()
	  {
		menu.body.style.display="none";
		menu.isShow=false;
		if(menu.bar!=null)
		  {
			//menu.bar.style.border="1 solid buttonface";
			menu.bar.style.backgroundColor=skinFaceColor;
			menu.bar.style.filter=""
		  }
	  }
	this.hideAll=function()
	   {for(var i=0;i<col_menu.length;i++)col_menu[i].hide();}
	
		col_menu[col_menu.length]=this
	
		document.body.onclick=this.hideAll
}
function menu_bar(top,left,width,parent)
{
 if(parent){pdiv=parent;}else pdiv=document.body;//»ñÈ¡¸¸¿é
	var mb=this
	this.item=[]
	this.menu=[]
	this.body=document.createElement("div")
    pdiv.appendChild(this.body);
	//pdiv.insertAdjacentElement("afterBegin",this.body)
//	document.body.insertAdjacentElement("beforeEnd",this.body)
	this.body.style.cssText="position:absolute;cursor:default;padding:2;height:25;z-index:5;font-size:10pt;color:black;top:"+top+";left:"+left+";width:"+width
	this.body.style.backgroundColor =skinFaceColor;
	var chkShow=function()
	  {	for(var i=0;i<mb.menu.length;i++)if(mb.menu[i].isShow)return true;return false;}
	this.add=function(Text,menu)
	{
		var item=document.createElement("span")
		mb.body.insertAdjacentElement("beforeEnd",item)
		item.style.cssText="margin:0 7 0 3;padding:2 4 2 4;text-align:center;height:23;background-color:skinFaceColor;color:black";
		item.style.backgroundColor =skinFaceColor;
	   item.innerText=Text
		item.onmouseover=function()
		{
			//this.style.border="1 solid #0B2565";
			this.style.backgroundColor="#B7BED3";
			if(chkShow()){for(var i=0;i<col_menu.length;i++)col_menu[i].hide();menu.show(item);}
		}
		item.onmouseout=function()
		{
			if(!menu.isShow)
			{	//item.style.border="1 solid buttonface"
				this.style.backgroundColor=skinFaceColor;
			}
		}
		item.onmousedown=item.onmouseup=function(){event.cancelBubble=true;menu.show(item);
		//this.style.border="1 inset";
		}
		item.onclick=function(){event.cancelBubble=true;menu.show(this)}
		mb.item[mb.item.length]=item
		mb.menu[mb.menu.length]=menu
		menu.bar=item
		return item
	}
}