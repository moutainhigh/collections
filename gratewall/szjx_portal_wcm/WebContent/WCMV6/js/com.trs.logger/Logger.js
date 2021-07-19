$package('com.trs.logger');
var myActualTop = (top.actualTop||top);
if(myActualTop==self){
	com.trs.logger.Logger=Class.create('logger.Logger');
	com.trs.logger.Logger.prototype={
		initialize: function(){
			this.lines=new Array([],[],[],[],[]);
			this.newlines=new Array([],[],[],[],[]);
			this.quietlines=[[],[],[],[],[]];
			this.colors=new Array("green","blue","gold","red","black");
			this.levelNames=new Array("DEBUG","INFO","WARN","ERROR","FATAL");
			this.levelImgs=new Array("img/debug.png","img/info.png","img/warn.png","img/error.png","img/fatal.png");
			this.page=com.trs.util.Common.BASE+'com.trs.logger/blank.htm';
			this.defaultPattern="%g%5p:%m(%F)";//
			this.style=1;//Win为Dialog,FF为Div方式
			this.level=3;//级别为Error
			this.firstWindow=true;//同步
			this.patternDate="";
			this.allShow=true;
			this.mode='';
			this.oldInnerHtml=null;
			this.setPattern(this.defaultPattern);
			this._onerror=this._fatal.bind(this);
			if(this.level<5&&this.level>=-1)
			{
//				Event.observe(window, 'error',this._onerror, false);
//				this.error_observing=true;
			}
			Event.observe(window, 'unload', this.close.bind(this), false);
		},
		_fatal:function(message,url,line){
			var s=message+"["+line+"@"+url+"]";
			try{
				throwerror();
			}catch(err){
				this.fatal(s,err);
			}
			return true;
		},
		setPattern:function(s){
			this.pattern=s;
			try
			{
				s=s.replace(/.*\%(\d)p.*/g,"$1");
				this.patternLevelInfo=eval(s);
			}catch(error){
				this.patternLevelInfo=0;
			}
			s=this.pattern;
			s=s.replace(/.*\%d\{(.*)\}.*/g,"$1");
			this.patternDate=s;
		},
		setStyle:function(s){
			this.style=s;
		},
		setLevel:function(s){
			this.level=s;
			if(this.level>=5||this.level<-1){
				if(this.error_observing)
				{
//					Event.stopObserving(window, 'error', this._onerror, false);
//					this.error_observing=false;
				}
			}
			else{
				if(!this.error_observing)
				{
//					Event.observe(window, 'error',this._onerror, false);
//					this.error_observing=true;
				}
			}
		},
		setQuiet:function(bQuiet,sFile){
			if(bQuiet){
				this.mode	= 'quiet';
				if(this.outputStream==null){
					sFile		=(sFile)?sFile:'C:\\log_'+new Date().getTime()+'.txt';
					this.outputStream = this.getOutputStream(sFile);
				}
			}
			else{
				this.mode	= '';
				this.closeQuietEcho();
			}
		},
		xmlEscapeText:function(s) {
			return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
		},
		xmlEscapeAttr:function(s) {
			return xmlEscapeText(s).replace(/\"/g, '&quot;');
		},
		xmlEscapeTags:function(s) {
			return s.replace(/</g, '&lt;').replace(/>/g, '&gt;');
		},
		xmlEscape:function(s,xml) {
			s=(s)?s:'';
			if(xml==true){
				var s0 = s.replace(/</g, '\n<');
				var s1 = this.xmlEscapeText(s0);
				var s2 = s1.replace(/\s*\n(\s|\n)*/g, '<br/>');
				s=s2;
			}
			else{
				s=this.xmlEscapeText(s);
			}
			return s;
		},
		format:function(msg,level,call){
			var pattern=this.pattern;
			pattern=pattern.replace(/\%\d+p/g,this.levelNames[level].substring(0,this.patternLevelInfo));
			if(this.mode!='quiet'){
				if(this.style==2){
					pattern=pattern.replace(/\%g/g,"<img src='"+com.trs.util.Common.BASE+'com.trs.logger/'+this.levelImgs[level]+"'>");
				}
				else if(this.style==1){
					pattern=pattern.replace(/\%g/g,"<img src='"+this.levelImgs[level]+"'>");
				}
			}
			else{
				pattern=pattern.replace(/\%g/g,"");
			}
			pattern	= pattern.replace(/\%m/g,msg);
			call	= (call!=null&&call!='')?'['+call+']':'';
			pattern=pattern.replace(/\%F/g,call);
			return pattern;
		},
		debug : function(s,xml){
			if(this.level==0){
				s=Object.parseString(s);
				s=this.xmlEscape(s,xml);
				s=this.format(s,0,'');
				this.newlines[0].push(s);
				this.lines[0].push(s);
				this.show();
			}
			else if(this.mode=='quiet'&&this.outputStream){
				s=Object.parseString(s);
				s=this.format(s,0,'');
				this.quietEcho(s);
			}
			else if(this.level==-1){
				this.quietlines[0].push(s);
			}
		},
		info :function(s,xml){
			if(this.level<=1&&this.level>=0){
				s=Object.parseString(s);
				s=this.xmlEscape(s,xml);
				s=this.format(s,1,'');
				this.newlines[1].push(s);
				this.lines[1].push(s);
				this.show();
			}
			else if(this.mode=='quiet'&&this.outputStream){
				s=Object.parseString(s);
				s=this.format(s,1,'');
				this.quietEcho(s);
			}
			else if(this.level==-1){
				this.quietlines[1].push(s);
			}
		},
		warn :function(s,xml){
			if(this.level<=2&&this.level>=0){
				s=Object.parseString(s);
				s=this.xmlEscape(s,xml);
				s=this.format(s,2,'');
				this.newlines[2].push(s);
				this.lines[2].push(s);
				this.show();
			}
			else if(this.mode=='quiet'&&this.outputStream){
				s=Object.parseString(s);
				s=this.format(s,2,'');
				this.quietEcho(s);
			}
			else if(this.level==-1){
				this.quietlines[2].push(s);
			}
		},
		error : function(s,err,xml){
			if(this.level<=3&&this.level>=0){
				s	= Object.parseString(s);
				if(err){
					s	+= err.message+':\n'+(err.stack||this.getStackTrace());
				}
				s	= this.xmlEscape(s,xml);
				s	= this.format(s,3,this.getStackTrace());
				this.newlines[3].push(s);
				this.lines[3].push(s);
				this.show();
			}
			else if(this.mode=='quiet'&&this.outputStream){
				s=Object.parseString(s);
				if(err){
					s	+= err.message+':\n'+(err.stack||this.getStackTrace());
				}
				s=this.format(s,3,this.getStackTrace());
				this.quietEcho(s);
			}
			else if(this.level==-1){
				s	= Object.parseString(s);
				if(err){
					s	+= err.message+':\n'+(err.stack||this.getStackTrace());
				}
				this.quietlines[3].push(s);
			}
		},
		fatal:function(s,err,xml){
			if(this.level<=4&&this.level>=0){
				s	= Object.parseString(s);
				if(err){
					s	+= err.message+':\n'+(err.stack||this.getStackTrace());
				}
				s	= this.xmlEscape(s,xml);
				s	= this.format(s,4,this.getStackTrace());
				this.newlines[4].push(s);
				this.lines[4].push(s);
				this.show();
			}
			else if(this.mode=='quiet'&&this.outputStream){
				s=Object.parseString(s);
				if(err){
					s	+= err.message+':\n'+(err.stack||this.getStackTrace());
				}
				s=this.format(s,4,this.getStackTrace());
				this.quietEcho(s);
			}
			else if(this.level==-1){
				this.quietlines[4].push(s);
			}
		},
		isInfoEnabled:function(){
			return this.level<=1;
		},
		isDebugEnabled:function(){
			return this.level==0;
		},
		clear:function(){
			var l = null;
			if(this.style==2){
				l=this.div();
			}
			else if(this.style==1){
				l=this.window();
			}
			lChilds=l.childNodes;
			for(var i=0;i<lChilds.length;i++){
				var tmp=l.removeChild(lChilds[i]);
				delete tmp;
			}
			l.innerHTML = '';
			for(var i=0;i<this.lines.length;i++){
				this.lines[i]=[];
				this.newlines[i]=[];
			}
			this.oldInnerHtml='';
		},
		show:function(s) {
			var l = null;
			if(!s&&this.allShow){
				var s="";
				if(this.level<0)this.level=5;
				for(var i=this.level;i<=4;i++){
					if(this.lines[i].length==0)continue;
					s += '<font color="'+this.colors[i]+'">'+this.lines[i].join('<br/>') + '</font><br/>';
					this.lines[i] = [];
				}
			}
			if(!this.allShow){
				this.showAll();
			}
			else if(s!=''){
				var l = this.showPanel();
				if(l){
					var div=null;
					if(this.style==2){
						div=document.createElement("DIV");
					}
					else{
						div=window.__logger_dialog.document.createElement("DIV");
					}
					div.innerHTML=s;
					var divChilds=div.childNodes;
					for(var i=0;i<divChilds.length;i++){
						l.appendChild(divChilds[i].cloneNode(true));
					}
					for(var i=divChilds.length-1;i>=0;i--){
						var tmp=div.removeChild(divChilds[i]);
						delete tmp;
					}
					delete div;
					l.scrollTop=l.scrollHeight;
				}
			}
		},	
		showAll:function(){
			var l = this.showPanel();
			if(!this.allShow){
				lChilds=l.childNodes;
				for(var i=0;i<lChilds.length;i++){
					var tmp=l.removeChild(lChilds[i]);
					delete tmp;
				}
				l.innerHTML=this.oldInnerHtml;
				this.allShow=true;
				l.scrollTop=l.scrollHeight;
			}
		},
		showLevel:function(lel){
			var l = this.showPanel();
			var s='';
			if(lel<0||lel>4)return;
			s+=this.newlines[lel].join('<br/>');
			if(this.allShow){
				this.oldInnerHtml=l.innerHTML;
				this.allShow=false;
			}
			if(l){
				lChilds=l.childNodes;
				for(var i=0;i<lChilds.length;i++){
					l.removeChild(lChilds[i]);
				}
				l.innerHTML = '';
				l.innerHTML='<font color="'+this.colors[lel]+'">'+s+'</font>';
				l.scrollTop = l.scrollHeight;
			}
		},
		showPanel:function(){
			var l=null;
			if(this.style==2){
				l=this.div();
			}
			else if(this.style==1){
				l=this.window();
			}
			return l;
		},
		div:function(){
			var l =$('com_trs_log');
			if (!l) {
				var caller=this;
				var tab1 = document.createElement('div');
				tab1.id='com_trs_log_tab';
				tab1.style.position = 'absolute';
				tab1.style.right = '5px';
				tab1.style.top = '5px';
				tab1.style.width = '500px';
				tab1.style.height = '25px';
				tab1.style.border = '1px solid gray';
				tab1.align='right';
				tab1.style.fontSize='9pt';
				tab1.style.zIndex='999';
				document.body.appendChild(tab1);
				var button=null;

				button=document.createElement('button');
				button.innerHTML="All";
				button.onclick=function(){caller.show();};
				tab1.appendChild(button);

				button=document.createElement('button');
				button.innerHTML="Debug";
				button.onclick=function(){caller.showLevel(0);};
				tab1.appendChild(button);

				button=document.createElement('button');
				button.innerHTML="Info";
				button.onclick=function(){caller.showLevel(1);};
				tab1.appendChild(button);


				button=document.createElement('button');
				button.innerHTML="Warn";
				button.onclick=function(){caller.showLevel(2);};
				tab1.appendChild(button);

				button=document.createElement('button');
				button.innerHTML="Error";
				button.onclick=function(){caller.showLevel(3);};
				tab1.appendChild(button);

				button=document.createElement('button');
				button.innerHTML="Fatal";
				button.onclick=function(){caller.showLevel(4);};
				tab1.appendChild(button);

				if(window.clipboardData){
					button = document.createElement('button');
					button.innerHTML="<font color='blue'>Copy</font>";
					button.onclick=function(){window.clipboardData.setData("Text",$('com_trs_log').innerText);}
					tab1.appendChild(button);
				}
				button = document.createElement('button');
				button.innerHTML="<font color='blue'>Erase</font>";
				button.onclick=function(){caller.clear();};
				tab1.appendChild(button);

				button = document.createElement('button');
				button.innerHTML="<font color='blue'>X</font>";
				button.onclick=function(){
					var tab=$("com_trs_log_tab");
					document.body.removeChild(tab);
					var log=$('com_trs_log');
					document.body.removeChild(log);
					caller.setLevel(5);
				};
				tab1.appendChild(button);

				l = document.createElement('div');
				l.id = 'com_trs_log';
				l.style.position = 'absolute';
				l.style.right = '5px';
				l.style.top = '30px';
				l.style.width = '500px';
				l.style.height = '240px';
				l.style.overflow = 'auto';
				l.style.backgroundColor = '#f0f0f0';
				l.style.border = '1px solid gray';
				l.style.fontSize = '9pt';
				l.style.padding = '5px';
				l.style.zIndex='999';
				document.body.appendChild(l);
			}
			return l;
		},
		window:function(){
			if(!window.showModelessDialog){
				this.style=2;
				return this.div();
			}
			var error=false;	
			try{
				window.__logger_dialog.document;
			}catch(err){
				error=true;
			}
			if(error&&!this.firstWindow){
				return null;
			}
			else if(error&&this.firstWindow){
				var caller=this;
				this.firstWindow=false;
				var button=null;
				var dialog=null;
				dialog=window.showModelessDialog(this.page,window,"status:no;resizable:yes;dialogHeight:330px;dialogWidth:490px;unadorne:yes");
				window.__logger_dialog=dialog;
				this.firstWindow=true;
			}
			var l=window.__logger_dialog.document.getElementById("com_trs_log");
			return l;
		},
		getFunctionName:function(aFunction){
			if(aFunction){
				try{
					var name = aFunction.toString().match(/function (\w*)/)[1];
				}catch(err){
					name=null;
				}
			}
			if ((name == null) || (name.length == 0)){
				name = aFunction._functionName;
			}
			if(name == null){
				name = '~';
			}
			return name;
		},
		getStackTrace:function(){
			var result = '';
			if (_IE){// IE, not ECMA
				var a=arguments.caller;
				var stack=[];
				stack.push(a.callee);
				for(;;){
					a = a.caller;
					if (!a||stack.include(a)) {
						break;
					}
					else{
						stack.push(a);
					}
				}
				stack.shift();
				return stack.join('\n----------');
			}
			return result;
		},
		close:function(){
			try{
				window.__logger_dialog.close();
			}catch(err){}  
		},
		quietEcho:function(s){
			if(this.outputStream==null)return;
			s	= s+'\n';
			if(_IE){
				this.outputStream.Write(s);
			}
			else{
				var s = this._convertFromUnicode("UTF8", s);
				this.outputStream.write(s, s.length);
			}
		},
		closeQuietEcho:function(){
			if(this.outputStream==null)return;
			if(_IE){
				this.outputStream.Close();
			}
			else{
				this.outputStream.close();
				this.outputStream=null;
			}
		},
		getOutputStream:function(_file){
			if(_IE){
				try {
					var fso = new ActiveXObject("Scripting.FileSystemObject");
					var oTextFile = fso.OpenTextFile(_file,8,true,true);
					oTextFile.Write('Start Log...'+new Date().toUTCString()+'\n');
					return oTextFile;
				} catch(e) {
					alert(e.message);
					throw new Error("写文件'"+_file+"'失败:" + e.message);
				}
			}
			else 
			{
				try {
					netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
					netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserWrite");
					var file = Components.classes["@mozilla.org/file/local;1"]
									.createInstance(Components.interfaces.nsILocalFile);
					file.initWithPath(_file);
					if (!file.exists())
						file.create(0, 0664);
					var outputStream = Components.classes["@mozilla.org/network/file-output-stream;1"]
									.createInstance(Components.interfaces.nsIFileOutputStream);
					outputStream.init(file, 0x02|0x10, 0644, 0);
					var sXML='Start Log...'+new Date().toUTCString()+'\n';
					outputStream.write(sXML, sXML.length);
					return outputStream;
				} catch(e) {
					throw new Error("写文件'"+_file+"'失败:\n" + e.stack);
				}
			}
		},
		_convertFromUnicode : function(_sCharset, _sSrc){ 
			try {
				var unicodeConverter = Components.classes["@mozilla.org/intl/scriptableunicodeconverter"]
							.createInstance(Components.interfaces.nsIScriptableUnicodeConverter); 
				unicodeConverter.charset = _sCharset; 
				return unicodeConverter.ConvertFromUnicode( _sSrc ); 
			}catch(e){
			}
			return _sSrc; 
		}
	};

	window.__logger_dialog=null;
	/*
	Event.observe(window, 'load', function(){
										if(!_IE){
											var elements=document.getElementsByTagName("*");
											for(var i=0;i<elements.length;i++){
												var tagName=elements[i].constructor;
												if(tagName==HTMLAnchorElement||tagName==HTMLInputElement){
													elements[i].focus();
													break;
												}
											}
										}
										else{
											var a=document.createElement("A");
											if(document.body.firstChild){
												document.body.insertBefore(a,document.body.firstChild);
											}
											else{
												document.body.appendChild(a);
											}
											a.focus();
										}
									}, false);
	*/
	Event.observe(document, 'keydown', function(E){
										var event=(E)?E:window.event;
										//alert(event.keyCode);
										if(event.altKey&&event.shiftKey){
											if(event.keyCode==76){//ALT+SHIFT+L
												var p=window.prompt("设置Logger的级别\n(0-DEBUG,1-INFO,2-WARN,3-ERROR,4-FATAL)",4);
												var logger=getLogger();
												p=(p||'0').trim();
												if(p!='0'&&p!='1'&&p!='2'&&p!='3'&&p!='4')p='5';
												logger.setLevel(p);
												logger.show("----LEVEL="+p+"----<br>");
											}
											else if(event.keyCode==80){//ALT+SHIFT+P
												var p=window.prompt("设置Logger的Pattern\n(%g表示img,%m表示提示信息,%F表示堆栈信息)","%g:%m(%F)");
												var logger=getLogger();
												logger.setPattern(p);
												logger.show("----PATTERN="+p+"----");
											}
											else if(event.keyCode==49){//ALT+SHIFT+1
												var logger=getLogger();
												com.trs.util.CommonHelper.copy(logger.quietlines[1].join('\n'));
											}
											else if(event.keyCode==50){//ALT+SHIFT+2
												var logger=getLogger();
												com.trs.util.CommonHelper.copy(logger.quietlines[2].join('\n'));
											}
											else if(event.keyCode==51){//ALT+SHIFT+3
												var logger=getLogger();
												com.trs.util.CommonHelper.copy(logger.quietlines[3].join('\n'));
											}
											else if(event.keyCode==52){//ALT+SHIFT+4
												var logger=getLogger();
												com.trs.util.CommonHelper.copy(logger.quietlines[4].join('\n'));
											}
											else if(event.keyCode==48){//ALT+SHIFT+0
												var logger=getLogger();
												com.trs.util.CommonHelper.copy(logger.quietlines[0].join('\n'));
											}
											else if(event.keyCode==67){//ALT+SHIFT+C
												var logger=getLogger();
												logger.quietlines=[[],[],[],[],[]];
											}
											else if(event.keyCode==81){
												var p=window.prompt("设置Logger的文件路径","C:\\log.txt");
												var logger=getLogger();
												logger.setQuiet(true,p);
											}
											else if(event.keyCode==88){
												var logger=getLogger();
												logger.setQuiet(false);
											}
										}
									}, false);

	function getLogger(){
		if(!window.____logger){
			window.____logger=new com.trs.logger.Logger();
			window.____logger.setPattern("%g:%m%F");
			window.____logger.setLevel(5);
		}
		return window.____logger;
	}
	function $log(){
		if(!window.____logger){
			window.____logger=new com.trs.logger.Logger();
			window.____logger.setPattern("%g%3p:%m%F");
			window.____logger.setLevel(5);
		}
		return window.____logger;
	}
	var $logger	= $log();
	$logger.LogError2Server = true;
	$logger.ServerLogLevel = 3;
	if($logger.LogError2Server&&window.BasicDataHelper){
		$logger.setLevel(-1);
		$logger.setPattern('%5p:%m(%F)');
		setInterval(function(){
			try{
				var aCombine = [];
				var logs = null;
				var hasLog = false;
				for(var i=4;i>=$logger.ServerLogLevel&&i>=0;i--){
					logs = $logger.quietlines[i];
					if(logs.length>0){
						hasLog = true;
						aCombine.push(BasicDataHelper.combine('wcm6_log', 'recordBrowserLog', {"LogType":(4-i),"LogInfo":logs.join('\n')}));
					}
				}
				if(hasLog){
					BasicDataHelper.multiCall(aCombine, function(){$logger.quietlines=[[],[],[],[],[]];});			
				}
			}catch(err){
			}
		},1*60*1000);
	}
}
else if(myActualTop.$log){
	$log = myActualTop.$log;
	com.trs.logger.Logger = myActualTop.com.trs.logger.Logger;
	$logger	= myActualTop.$logger;
	$logger.setLevel(-1);
}
else{
	alert('top must import com.trs.logger.Logger');
}
window.onerror = function(message, url, line){
	//if($logger.level>=-1&&$logger.level<5){
		var s = "错误信息：" + message + "\n文件："+ url +"\n出错位置：第" + line + "行\n";
		if(_IE){
			s = message+"["+line+"@"+url+"]";
			var a = arguments.caller;			
			var stack = [];
			stack.push(a.callee);
			while(true){
				a = a.caller;
				if (!a||stack.include(a.callee)) {
					break;
				}
				stack.push(a.callee);
			}
			stack = stack.join('\n------------------------\n');
			if(top.getParameter('isdebug')==1){
				alert( s + stack );
			}
			else{
				//TODO logger
			}
		}
		else{
			try{
				throwerror();
			}catch(err){
				if(top.getParameter('isdebug')==1){
					alert(s+err.stack);
				}
				else{
					//TODO logger
				}
			}
		}
		if($logger.stopError){
			return true;
		}
	//}
	return false;
}
