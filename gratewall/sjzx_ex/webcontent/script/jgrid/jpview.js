
function JpView(name,PacketUrl,top,left,parent)   //构造函数
{
 var pdiv,obj=document.createElement('Div');//创建一个控制节点
 if(parent){pdiv=parent;}else pdiv=document.body;//获取父块
  pdiv.appendChild(obj);
  obj.style.backgroundColor="#C0C0C0";
 // obj.style.backgroundImage ='url(bg2.gif)';
  obj.style.overflow = 'visible';
  obj.style.position='absolute';
  if(left) obj.style.left=left;
  if(top) obj.style.top=top;
  if(name){var s='window.'+name+'=obj';eval(s);}

 // window.moveTo(0,0)
 // window.resizeTo(screen.availWidth,screen.availHeight)
  obj.Name =name;
  obj.stPacketUrl = PacketUrl;
  obj.stPacketNode
  obj.ca= new Canvas(obj);
  obj.entryArray = []
  obj.entryCount=0;



   obj.getPacket=function ()
  {
//  alert(obj.stPacketUrl);
  //读出 XML 文件
  var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  xmlDoc.async = false ;
  xmlDoc.load(obj.stPacketUrl);
  //alert(xmlDoc.xml);
  if (xmlDoc){obj.stPacketNode=xmlDoc;}

  var root=obj.stPacketNode.documentElement;
   if(root.tagName.toUpperCase()=="BASERPT")
   var Count = root.childNodes.length;

  for(var i=0;i< Count;i++)
	{
	var node=root.childNodes.item(i);   //得到 <report> </report>对节点内容

	  switch(node.tagName.toUpperCase()) {
		 case 'LABELS':
			  obj.labelsNode=node;
		 //      alert(obj.labelsNode.xml);
		      obj.DrawLabels();
		      break;
		 case 'BEVELS':
			  obj.bevelsNode =node;
		      obj.DrawBevels();
		      break;
		 case 'ENTRIES':
			  obj.entriesNode = node;
		     obj.DrawEntries();
		      break;
		 case 'PLANARS':
			  obj.planarsNode  =node;
		      break;
	  }
	}
 
 }

 obj.DrawLabels = function ()
	{


    for(var i=0;i<obj.labelsNode.childNodes.length;i++)
	{

     obj.drawLabel(obj.labelsNode.childNodes.item(i));
	}
	}
 obj.drawLabel =function(node)
	{
      
    var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
    for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'CAPTION' :
		    Caption=subnode.text;
		   break;
	   case 'BOUND':
		    Bound=subnode.text;
	        break;
	   case 'FONT':
		    fontNode =subnode;
	       break;
	  }
	}
	 var label = document.createElement('span');
      obj.appendChild(label);
	  label.style.position='absolute';
	  var pos = Bound.split(',');
       label.innerText = Caption;
       label.style.left=pos[0];
       label.style.top=pos[1];
	   label.style.width=pos[2]-pos[0];
       label.style.height=pos[3]-pos[1];

       obj.setFont(label,fontNode);
	label.ondragstart= function ()
	{
	 this.setCapture();
	 label.xx=event.x-label.offsetLeft;
	 label.yy=event.y-label.offsetTop;
	}
  label.ondrag= function()
	{
       label.style.left=event.x-label.xx;
       label.style.top=event.y-label.yy;
	}
  label.ondragend=function ()
	{
	  this.releaseCapture();
	}


	}
 obj.setFont =function (el,node)
	{

      var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
    for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'NAME' :
		//    fontName=subnode.text;
	       el.style.fontFamily=subnode.text;
		   break;
	   case 'SIZE':
		    el.style.fontSize=subnode.text+'pt';
	        break;
	   case 'COLOR':
		    el.style.fontColor ="'"+subnode.text+"'";
	       break;
	   case 'STYLE':
		    break;
	  }


	}
 }
 obj.DrawBevels = function ()
	{

    for(var i=0;i<obj.bevelsNode.childNodes.length;i++)
	{

     obj.drawBevel(obj.bevelsNode.childNodes.item(i));
	}


	}

 obj.drawBevel = function (node)
	{
    var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
    for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'SHAPE' :
		    Shape=subnode.text;
		   break;
	   case 'BOUND':
		    Bound=subnode.text;
	        break;
	   case 'STYLE':
		    Style=subnode.text;
	        break;
	   case 'LINE':
		    lineNode =subnode;
	       break;
	  }
	}
	  var pos = Bound.split(',');
   
   	var line=document.createElement('v:line'); 
	pos[2]-=5;
	pos[3]-=5;
//	if(Shape=='topLine') {
	line.from=pos[0]+","+pos[1];	
	line.to=pos[2]+","+pos[3];
//	}
	line.position='absolute';
	obj.setLineStyle(line,lineNode);

	obj.appendChild(line);

	
	

	

	}
	obj.setLineStyle=function (line,node)
	{
      var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
    for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'WIDTH' :
	      line.Strokeweight=subnode.text;
		   break;
	   case 'SIZE':
	        break;
	   case 'COLOR':
		   line.strokecolor ="'"+subnode.text+"'";
	       break;
	   case 'STYLE':
		    break;
	  }


	 }
	}
 obj.DrawEntries = function ()
	{

    for(var i=0;i< obj.entriesNode.childNodes.length;i++)
	{

     obj.drawEntry( obj.entriesNode.childNodes.item(i));
	}


	}
 obj.drawEntry =function(node)
	{
    var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
    for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'CAPTION' :
		    Caption=subnode.text;
		   break;
	   case 'BOUND':
		    Bound=subnode.text;
	        break;
	   case 'SHAPE':
		    Shape=subnode.text;
	        break;
	   case 'INDEX':
		    Index =subnode.text;
	       break;
	   case 'KIND':
		    Kind =subnode.text;
	       break;
	   case "TAG":
		    Tag =subnode.text;
	       break;

	  }
	}
	
    if(Shape=="edit")
		{
		 entry= document.createElement('input');//添加一个指定元素
         obj.appendChild( entry);
		 idx=Tag+1-1;
		 obj.entryArray[obj.entryCount]=entry;
       //  obj.entryArray[Index].tag =Tag;
		 entry.Number=obj.entryCount;
		 obj.entryCount++;
	      entry.style.position='absolute';
	     var pos = Bound.split(',');
       //   entry.innerText = Caption;
          entry.style.left=pos[0];
          entry.style.top=pos[1];
	      entry.style.width=pos[2]-pos[0];
          entry.style.height=pos[3]-pos[1];
	 entry.ondragstart= function ()
	{
	  this.setCapture();
//	 this.xx=event.x-this.offsetLeft;
//	 this.yy=event.y-this.offsetTop;
	}
  entry.ondrag= function()
	{
//	  entry.style.left=event.x-this.xx;
 //     entry.style.top=event.y-this.yy;
	}
  entry.ondragend=function ()
	{
	//  this.releaseCapture();
	}


	  entry.onkeydown = function ()
			{

			  	switch(event.keyCode)
	             {
				     case 38://当按动向上箭头
					if(this.Number-1>0)
			   
					   obj.entryArray[this.Number-1].focus();
		
				   //  alert(this.tag-1);
					    break;
					case 40://当按动向下箭头
					case 13:
					// if(obj.entryArray[this.tag+1]=='definend')
					if(this.Number<obj.entryCount-1)
					              obj.entryArray[this.Number+1].focus();
				    break;
		
				    }
  
				}
			
     	 }
	 }

 obj.DrawPlanars = function ()
	{



	}
 return obj;

}
