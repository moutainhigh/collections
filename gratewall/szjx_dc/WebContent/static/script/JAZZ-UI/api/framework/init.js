function afterload(){
	init();
	window.title="中软睿剑业务基础平台web框架";
	WebForm1.SetIcon("images/ie.gif");
	WebForm1.SetTitle("中软睿剑业务基础平台web框架");
 	WebForm1.Add(ToolBar1);	
	WebForm1.Add(Splitter1);
	WebForm1.StatusBar.SetText("完成");
	WebForm1.Maximize();
	BuildToolBar();
}
function init(){
	window.WebForm1=new WebForm();
	window.ToolBar1=new ToolBar(WebForm1);
	window.Splitter1=new Splitter(WebForm1);
	window.Div1=new Div();
	window.IFrame1=new IFrame(Div1,'IFrame1');
	//window.Div4=new Div(Div1);
	window.Div2=new Div();
	window.Div3=new Div(Div2);
	window.Span1=new Span(Div3);
	
	WebForm1.Name='WebForm1';
	WebForm1.style.height ='600';
	WebForm1.style.width  ='800';
	WebForm1.style.position='absolute';
	WebForm1.style.left='33';
	WebForm1.style.top='6';
	WebForm1.style.fontStyle='normal';
	
	ToolBar1.Name='ToolBar1';
	ToolBar1.style.height='21';
	ToolBar1.style.width='100%';
	ToolBar1.style.position='static';
	ToolBar1.style.left='6';
	ToolBar1.style.top='31';
    
	Splitter1.Name='Splitter1';
	Splitter1.style.height='100%';
	Splitter1.style.position='static';
	Splitter1.style.left='1';
	Splitter1.style.top='62';
	Splitter1.style.borderLeft='0';
	Splitter1.style.borderRight='0';
	Splitter1.LeftComponent=Div2;
	Splitter1.RightComponent=Div1;
	Splitter1.DividerLocation=200;
	
	Div1.Name='Div1';
	Div1.style.height='546';
	Div1.style.width='597';
	Div1.style.position='absolute';
	Div1.style.left='203';
	Div1.style.top='0';
	
	IFrame1.Name='IFrame1';
	IFrame1.style.height='100%';
	IFrame1.style.width='100%';
	IFrame1.style.position='static';
	IFrame1.style.left='10';
	IFrame1.style.top='13';
	IFrame1.style.borderWidth='0';
	IFrame1.style.borderStyle='none';
	IFrame1.style.visibility='visible';
	IFrame1.align='center';
	
	
	Div2.Name='Div2';
	Div2.id='Div2';
	Div2.style.height='400px';
	Div2.style.width='100%';
	Div2.style.position='absolute';
	Div2.style.left='0';
	Div2.style.top='0';
	Div2.style.background = "#dfe8f6";
	Div3.Name='Div3';
	Div3.style.height='19';
	Div3.style.width='100%';
	Div3.style.position='static';
	Div3.style.left='1';
	Div3.style.top='1';
	Div3.style.backgroundColor='#F7F3EF';
	
	
	Span1.Name='Span1';
	Span1.style.height='21';
	Span1.style.position='absolute';
	Span1.style.left='20';
	Span1.style.top='2';
	Span1.innerHTML="SWORD-WEB";
	
	WebForm1.Add(ToolBar1);
	WebForm1.Add(Splitter1);
	
	Splitter1.SetLeftComponent(Div2);
	Splitter1.SetRightComponent(Div1);
	Splitter1.SetDividerLocation(230);
	//动态替换IFrame1的src就可改变右边的内容
	IFrame1.src = "modules/index.html";
	IFrame1.Show();
	window.status='中软睿剑业务基础平台(SWORD)';
	createTree();
	
}
 
//build toolbar buttons 
function BuildToolBar(){
	var button1 = new ToolButton();
	button1.Image.src = "images/new.gif";
	button1.title = "New File";
	ToolBar1.Add(button1);
	var button2 = new ToolButton();
	button2.Image.src = "images/open.gif";
	ToolBar1.Add(button2);
	ToolBar1.AddSeparator();
	var button3 = new ToolButton();
	button3.Image.src = "images/save.gif";
	ToolBar1.Add(button3);
	var button4 = new ToolButton();
	button4.Image.src = "images/saveAll.gif";
	ToolBar1.Add(button4);
	var button5 = new ToolButton();
	button5.Image.src = "images/print.gif";
	ToolBar1.Add(button5);
	ToolBar1.AddSeparator();
	var button6 = new ToolButton();
	button6.Image.src = "images/undo.gif";
	ToolBar1.Add(button6);
	var button7 = new ToolButton();
	button7.Image.src = "images/redo.gif";
	ToolBar1.Add(button7);
	var button8 = new ToolButton();
	button8.Image.src = "images/cut.gif";
	ToolBar1.Add(button8);
	var button9 = new ToolButton();
	button9.Image.src = "images/copy.gif";
	ToolBar1.Add(button9);
	var button10 = new ToolButton();
	button10.Image.src = "images/paste.gif";
	ToolBar1.Add(button10);
	ToolBar1.AddSeparator();
	var button11 = new ToolButton();
	button11.Image.src = "images/help.gif";
	ToolBar1.Add(button11);
}
/*
<div id="menu">
<ul>
<li><a title="网站标准" href="http://www.w3cn.org/webstandards.html">什么是网站标准</a></li>
<li><a title="标准的好处" href="http://www.w3cn.org/benefits.html">使用标准的好处</a></li>
<li><a title="怎样过渡" href="http://www.w3cn.org/howto.html">怎样过渡</a></li>
<li><a title="相关教程" href="http://www.w3cn.org/tutorial.html">相关教程</a></li>
<li><a title="工具" href="http://www.w3cn.org/tools.html">工具</a></li>
<li><a title="资源及链接" href="http://www.w3cn.org/resources.html">资源及链接</a></li>
</ul>
</div>
*/
function createTree(){
    
	var html = "<div  sword='SwordTree' name='SwordTreeXML' dataType='xml' onNodeClick='redirectURL();' tid='modules/config.xml'   displayTag='title'  id = 'tree'  style= 'border:1px ;height:100%;width:290px;' ></div>";
	Div2.innerHTML = html;
	
}

function redirectURL(item){
   if($chk(item.get("path")))
	 IFrame1.src=item.get("path");
}

function createTree1(){
   var x = $.dom({xmlurl:'modules/config.xml'});
   var div = document.createElement("div");
   div.id = "menu";
   var ul = document.createElement('ul');
   div.appendChild(ul);
   var menu = x.selectNodes("/menu/menu");
   for(var i=0;i<menu.length;i++){
      var li = document.createElement("li");
      var a = document.createElement("a");
       a.title = menu[i].getAttribute('title');
       a.path = menu[i].getAttribute("path");
       a.href="#";
       a.onclick=openMenu;
       a.innerHTML = menu[i].getAttribute('title');
       li.appendChild(a);
       ul.appendChild(li);
   }
   Div2.appendChild(div);
}
function openMenu(event){
  event = event?event:window.event;
  var obj = event.srcElement||event.target;
  IFrame1.src=obj.path;
}