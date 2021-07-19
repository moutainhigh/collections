var MAX_Z_INDEX=30
var col_menu=[]; //menu collection
function menu(width)
{
	var menu=this
	
	this.item=[]
	this.isShow=false
	this.bar=null
	this.body=document.createElement("table")
	document.body.insertAdjacentElement("beforeEnd",this.body)
/*----------设置菜单的边框线和阴影色以及菜单的背景色--------------*/
	this.body.style.cssText="position:absolute;;border-collapse:collapse;border-spacing:1;border:1 solid #0B2565;background-color:white;color:black;display:none;shadow(color=gray,direction=135);table-layout:fixed;filter:progid:DXImageTransform.Microsoft.Shadow(color='#cccccc', Direction=135, Strength=4);"
	this.body.style.pixelWidth=width==null?200:width
	var run=function(cmd,a0,a1,a2)
	{
		if(typeof(cmd)=="string")
		{	try{return eval(cmd);}
			catch(E){alert("run script string error:\n"+cmd);}
		}
		else if(typeof(cmd)=="function"){return cmd(a0,a1,a2);}
	}

/*-------------添加菜单项-------------------*/
	this.add=function(Text,exeType,exeArg,target)
	{
		
		var item=menu.body.insertRow()
		item.style.cssText="font-size:10pt;cursor:default;height:24;"
		var col1=item.insertCell()
		var col2=item.insertCell()
		col2.innerHTML=Text
		col2.style.cssText="height:24;padding-left:5;width:100%;color:black"
	/*-----------设置菜单项第一列的背景色------------------------------*/
		col1.style.cssText="background:expression(bgcolor);width:26;height:24;text-align:center;"
		//col1.style.backgroundColor=init_skinFaceColor;
		item.subMenu=null
		item.enable=true
		item.selected=false
	   
		item.select=function()  //菜单条被选中
		{   
			         
		      col1.innerHTML="√"
		}

	    item.unselect=function()  //菜单条未被选中
		{
          col1.innerHTML=""
		}

		item.setcolor1=function ()
	     {
		   col1.style.backgroundColor=bgcolor;
		   col2.style.cssText="height:24;padding-left:5;width:100%;color:black"
	     }

	    item.setcolor2=function ()
	     {
           this.style.backgroundColor=skinFaceColor;
		   col2.style.cssText="height:24;padding-left:5;width:100%;color:white"
	     }

		

		item.execute=new Function()
   
		item.remove=function(){item.removeNode(true);}

		exeType=exeType==null?"":exeType
  /*--------点击菜单项后执行的相应动作类型--------------*/
		switch(exeType.toLowerCase())
		{
			case "hide":   //隐藏菜单
				item.execute=function()
				 {menu.hide();}
				break;
			case "url":    //指向一个网址
				if(typeof(exeArg)!="string")break;
				if(target==null||target=="")target="_blank";
				item.execute=function()
					{
					  menu.hide();
					  open(exeArg,target);
					}
				break;
			case "js":    //执行一个JS命令
				if(typeof(exeArg)!="string")break;
				item.execute=function()
					{
					  menu.hide();
					  eval(exeArg)
					}
				break;
			case "sub":  //打开一个子菜单
				if(typeof(exeArg.body)=="undefined")break;
				item.execute=function()
					{
					  menu.show(exeArg);
					}
				col2.innerHTML ="<span style='width:90%;'>"+col2.innerHTML+"</span><span style='font-family:Wingdings 3;'>}</span>";
				item.subMenu=exeArg;
				break;
		}
		menu.item[menu.item.length]=item
  /*-------------鼠标经过菜单条-----------------*/
		item.onmouseover=function()
		{
			for(var i=0;i<menu.item.length;i++)
			{
			  if (menu.item[i]!=this)
			       menu.item[i].setcolor1()
			    else
				   menu.item[i].setcolor2()
			}
			col1.style.backgroundColor=skinFaceColor//"#B7BED3"
     		item.style.color=item.enable?"black":"gray";
			item.style.backgroundColor=skinFaceColor//"#B7BED3";
			col1.style.border=col2.style.border="1 solid #0B2565"
			col1.style.borderRight=col2.style.borderLeft="0"
			for(var i=0;i<menu.item.length;i++)
			   {
				 if(menu.item[i].subMenu!=null)
					menu.item[i].subMenu.hide();
			   }
			if(item.subMenu!=null)menu.show(item.subMenu)
		}
  /*-------------鼠标离开菜单条----------------*/
		item.onmouseout=function()
		{
	       
		    col1.style.backgroundColor=bgcolor;
			col2.style.cssText="height:24;padding-left:5;width:100%;color:black"
			item.style.backgroundColor="white";
			col1.style.border=col2.style.border=""
		}
  /*------------鼠标按下和弹起-----------------*/
		item.onmousedown=item.onmouseup=function(){event.cancelBubble=true;}
  /*------------点击鼠标-----------------------*/	
		item.onclick=function()
		  {
			for(var i=0;i<menu.item.length;i++)
			{
			  if (menu.item[i]!=this)
			       menu.item[i].setcolor1()
			    else
				   menu.item[i].setcolor2()
			}
			
			event.cancelBubble=true;
            
			/*this.firstcolor();/*设置菜单项第一列的颜色*/
			//col1.style.backgroundColor=bgcolor;
			if(this.enable)
			  {
				if(!this.selected)
				  {
					this.select();
					for(i=0;i<menu.item.length;i++)
					  if(menu.item[i]!=this)
					  {
						  menu.item[i].unselect();
						  //col1.style.backgroundColor=bgcolor;
					  }
					this.selected=true
				  }
			else
			  {
				this.unselect();
				this.selected=false
			  }
				run(item.execute);
			  }
		  }
		return item
	}
  

/*----------------添加一个具有网址链接的菜单项-----------*/
	this.addLink=function(url_,text,target)
	{
		if(text==null || text=="")text=url_
		if(target==null || target=="")target="_blank"
		return menu.add(text,"url",url_,target)
	}
	/*this.firstcolor()=function()
		{
          for(i=0;i<menu.item.length;i++)
			  col1.style.backgroundColor=bgcolor;
		}*/

/*------------添加分隔条--------------*/
	this.seperate=function()
	{
		var row=menu.body.insertRow();
		row.style.cssText="padding-left:0;font-size:10pt;height:8;cursor:default;width:100%;border:0;";
		var col1=row.insertCell(); 
		col1.style.cssText="background:expression(bgcolor);width:26;height:100%;text-align:center;";
		
		var col2=row.insertCell(); 
		col2.style.cssText="height:8;padding-left:5;text-align:right;";
		col2.innerHTML="<hr width='97%'>";
	}

/*-----------显示菜单项--------------*/
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
				  if(m.offsetHeight<document.body.offsetHeight-event.clientY)
				{	y=event.clientY+document.body.scrollTop-event.offsetY}
				  else
				{	y=event.clientY-m.offsetHeight+document.body.scrollTop-event.offsetY}
			  }
			   else
			  {
				x=event.clientX+document.body.scrollLeft-event.offsetX-2
				y=event.clientY+document.body.scrollTop+a[0].offsetHeight-event.offsetY-4
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
		menu.isShow=true;
		event.cancelBubble=true;
	}

/*----------------隐藏菜单-----------------*/
	this.hide=function()
	{
		menu.body.style.display="none";
		menu.isShow=false;
	}

	this.hideAll=function()
		{
		  for(var i=0;i<col_menu.length;i++)
			  col_menu[i].hide();
		}

	this.hideOthers=function()
		{
		  for(var i=0;i<col_menu.length;i++)
			  if(col_menu[i]!=menu)
			     col_menu[i].hide();
		}
	col_menu[col_menu.length]=this
	document.body.onclick=this.hideAll
	
}


/*-------------建立菜单栏-------------------*/
function menu_bar(top,left,width)
{
	var mb=this
	this.item=[]
	this.menu=[]
	this.body=document.createElement("div")
	document.body.insertAdjacentElement("beforeEnd",this.body)
	this.body.style.cssText="background:expression(bgcolor);position:absolute;cursor:default;padding:2;height:25;z-index:5;font-size:10pt;top:"+top+";left:"+left+";width:"+width;
    
	var chkShow=function()
		{
		 for(var i=0;i<mb.menu.length;i++)
			 if(mb.menu[i].isShow)
			   return true;
	     return false;
		}

/*--------------添加菜单栏对象-------------*/	
	this.add=function(Text,menu)
	{
		var item=document.createElement("span")
		mb.body.insertAdjacentElement("beforeEnd",item)
	//	item.style.cssText="background:expression(skinFaceColor);margin:0 7 0 3;padding:2 4 2 4;text-align:center;height:23;"
		item.style.cssText="margin:0 7 0 3;padding:2 4 2 4;text-align:center;height:23;"
		item.innerText=Text
		item.setbgcolor=function ()
	     {
		   this.style.backgroundColor=bgcolor;
	     }

	    item.setskinFaceColor=function ()
	     {
          this.style.backgroundColor=skinFaceColor;
	     }

		item.setfontcolor1=function()
		{
			this.style.cssText="color:black;margin:0 7 0 3;padding:2 4 2 4;text-align:center;height:23;"
        }

		item.setfontcolor2=function()
        {
		this.style.cssText="color:blue;margin:0 7 0 3;padding:2 4 2 4;text-align:center;height:23;"
		}


		item.hideborder=function()
		{
			this.style.border="none";
		}

		
  /*-----------鼠标经过-----------------*/
		item.onmouseover=function()
		{
			
			this.style.border="1 solid #F3C246"
			mb.body.style.backgroundColor=bgcolor;
			
			for(i=0;i<mb.item.length;i++)
			if(mb.item[i]!=this)
			      { 		
			       mb.item[i].setbgcolor();
				   mb.item[i].setfontcolor1();
				  }
				else
			     {
				   mb.item[i].setskinFaceColor();
				   mb.item[i].setfontcolor2();
				  }
			
			if(chkShow())
				{
				  for(var i=0;i<col_menu.length;i++)
					  col_menu[i].hide();
				  menu.show(item);
				}
		}
  /*------------鼠标离开-------------------*/
		item.onmouseout=function()
		{
			if(!menu.isShow)
			{
				
					item.setbgcolor();/*init_skinFaceColor;*/
					item.setfontcolor1();
					for(i=0;i<mb.item.length;i++)
					  mb.item[i].hideborder();
				  
				
			}
		}
  /*--------------鼠标按下和弹起-----------*/
		item.onmousedown=item.onmouseup=function(){event.cancelBubble=true;menu.show(item)}
  /*--------------点击鼠标-----------------*/		
		item.onclick=function()
			{
			  event.cancelBubble=true;
			  menu.show(item);
			  mb.body.style.backgroundColor =bgcolor;
			  for(i=0;i<mb.item.length;i++)
			if(mb.item[i]!=this)
			      { 		
			       mb.item[i].setbgcolor();
				   mb.item[i].setfontcolor1();
				  }
				else
			     {
				   mb.item[i].setskinFaceColor();
				   mb.item[i].setfontcolor2();
				  }
			
			 // item.setfontcolor2();
			  //item.style.backgroundColor=skinFaceColor//bgcolor;
			}
		mb.item[mb.item.length]=item
		mb.menu[mb.menu.length]=menu
		menu.bar=item
		return item
	}
}