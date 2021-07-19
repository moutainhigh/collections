Ext.ns('wcm.special.design');

/**
*顶层设计页面控制器
*/
var PageController = {

	/**
	*顶层设计页面初始化的入口
	*/
	init : function(){
		//渲染可视化设计页面的内容
		this.renderVisualTemplate(sVisualTemplateContent);
		//初始化工具栏
		wcm.special.design.ToolBar.init();
	},
	/**
	*获取内部设计页面
	*/
	getMainWin : function(){
		return $('page').contentWindow;
	},
	/**
	*保存页面可视化内容到服务器
	*/
	saveVisualTemplate : function(sVisualTemplateHtml, fCallBack){
		BasicDataHelper.call('wcm61_visualtemplate', 'saveToTemplate', {
			objectId : -1,
			objectType : -1,
			templateId : nTemplateId,
			html : sVisualTemplateHtml
		}, true, function(transport, json){
			if(fCallBack) fCallBack(transport, json);
		});
	},
	/**
	*渲染可视化设计页面的内容
	*/
	renderVisualTemplate : function(sHtml){
		/*
		*确保页面一定存在wcm-mustExistId，这个节点将作为撤销重做的容器
		*如果不存在，则自动创建
		*/
		if(!/wcm-mustExistId/.test(sHtml)){
			sHtml = sHtml.replace(/(<body[^>]*>)((?:.|\n)*)(<\/body>)/i, "$1<div id='wcm-mustExistId'>$2</div>$3");
		}

		//写入可视化内容
		var doc = this.getMainWin().document;
		doc.open();
		doc.write(sHtml || "");
		//如果不是IE，则直接在这里关闭，如果是IE，则需要在domready的时候关闭，否则在IE8下会报错
		//不能直接用isIE，因为IE9也需要调用close
		if(window.addEventListener){
			doc.close();
		}
	},
	/**
	*由于设计过程中，数据库中存储的资源变量和
	*可视化模板页面中真实使用的资源变量不一致，数据库中为全集，
	*所以需要进行同步，清除不必要的垃圾信息。
	*/
	synchronizeWidgetInstances : function(){
		//TODO需要根据内部页面是否发生变化进行以下处理
		BasicDataHelper.call('wcm61_visualtemplate', 'synchronizeWidgetInstances', {
			templateId : nTemplateId
		}, true);		
	},
	/**
	*顶层设计页面卸载的入口
	*/
	destroy : function(){
		this.synchronizeWidgetInstances();
		wcm.special.design.ToolBar.destroy();
	},
	clipboard:{}
};

Event.observe(window, 'load', function(){
	PageController.init();
});

Event.observe(window, 'unload', function(){
	PageController.destroy();
	// 解锁
	if(nTemplateId != 0){
		LockerUtil.unlock(nTemplateId, nObjType);
	}
});
/**操作树显示和隐藏事件**/
Event.observe(window, 'load', function(){
	Event.observe($("toggleNav"),"click",function(){
		var dom = $('main');
		Element[Element.hasClassName(dom, 'hideNavTree') ? 'removeClassName' : 'addClassName'](dom, 'hideNavTree');

		if(!Element.hasClassName(dom, 'hideNavTree')){
			if($('TreeNav') && $('TreeNav').contentWindow){
				$('TreeNav').contentWindow.synchronizeHeight();
			}
		}
	});
});
/********************************************************************/
/**可能涉及到需要定制修改的部分**/
/********************************************************************/

/*
*给顶部工具栏注册相应的处理函数
*/
wcm.special.design.ToolBar.addItems(
	/**
	*保存当前设计页面中的可视乎内容
	*/
	{
		oprKey : 'saveHtml',
		desc : '保存',
		title : '保持当前的可视化模板内容',
		cls : 'saveHtml',
		order : 1,
		disabled : function(){
			try{
				//如果设计页面有修改，需要提示保存
				return !window.isPageChanged;
			}catch(error){
			}
			return true;
		},
		cmd : function(){
			//保存前选删除标记
			PageController.getMainWin().wcm.special.widget.InstanceMgr.unmark();
			PageController.getMainWin().wcm.special.layout.LayoutUI.unmark();
			//保存前删除布局的标记
			PageController.getMainWin().wcm.special.layout.OperUI.unIdentify();
			// 获取模板内容
			var sVisualTemplateHtml = PageController.getMainWin().PageController.getVisualTemplate();
			PageController.saveVisualTemplate(sVisualTemplateHtml, function(transport){
				window.isPageChanged = false;
				wcm.special.design.ToolBar.refresh();
				var nTemplateId0 = Ext.result(transport) || nTemplateId;
				var params = {
					"ObjId":nTemplateId0,
					"ObjType":nObjType,
					"ActionFlag":"true"
				};
				var ajaxRequest = new Ajax.Request(WCMConstants.WCM6_PATH + "include/cmsobject_locked.jsp",{
					method:'get', 
					asynchronous:false, 
					parameters:$toQueryStr(params)
				});
				alert('已保存');
			});
		}
	},
	/**
	*用于切换导航树的显示
	*/
	{
		oprKey : 'preview',
		desc : '预览',
		title : '预览当前页面，查看发布效果',
		cls : 'structure',
		visibled : function(){
			return false;
		},
		order : 2,
		cmd : function(){
				var sVisualTemplateHtml = PageController.getMainWin().PageController.getVisualTemplate();
				var postData = {
					'objectId' : nHostId, 
					'objectType' : nHostType,
					'html' : sVisualTemplateHtml
				};
				BasicDataHelper.call('wcm61_visualtemplate', 'preview', postData,true, function(transport, json){
					
					var sUrl = transport.responseText;
					var win = window.open(sUrl);
				});
		}
	},
	/**
	*用于设置子对象的模板
	*/
	{
		oprKey : 'showOpers',
		desc : '切换页面',
		title : '切换到其他栏目可视化模板的设计...',
		cls : 'showOpers',
		order : 3,
		visibled : function(){//但从可视化模板来说，不应该存在切换模板的功能，该功能仅对专题有效果
			return nSpecialId != 0;
		},
		cmd : function(){
			var dom = $('main');
			wcm.CrashBoarder.get('special_edit').show({
				title : '切换栏目页面设置',
				src : 'subspecial_template_set.jsp?SpecialId=' + nSpecialId+"&TemplateId="+nTemplateId +"&nChannelId="+ nChannelId,
				width:'400px',
				height:'350px',
				maskable:true,
				callback : function(url){
					this.close();
					//列表页面转跳
					location.href=url;
				}
			});
		}
	},
	/**
	* 切换模板
	*/
	{
		oprKey : 'switchTemplate',
		desc : '切换栏目模板',
		title : '更换模板到栏目并显示更换后可视化模板的设计...',
		cls : 'switchTemplate',
		order : 3,
		visibled : function(){//但从可视化模板来说，不应该存在切换模板的功能，该功能仅对专题有效果
			return nSpecialId != 0 && nHostType == 101;
		},
		cmd : function(){
			var dom = $('main');
			wcm.CrashBoarder.get('switchTemplate_edit').show({
				title : '切换栏目模板设置',
				src : 'switchTemplate/template_list.html?nChannelId=' + nChannelId+"&TemplateId="+nTemplateId + "&specialId=" + nSpecialId,
				width:'680px',
				height:'450px',
				maskable:true,
				callback : function(url){
					//this.close();
					location.href=url;
				}
			});
		}
	},
	/**
	*类似创建当前模板
	*/
	{
		oprKey : 'copyVisualTemp',
		desc : '类似创建',
		title : '在指定栏目下类似创建新模板',
		cls : 'copyVisualTemp',
		order : 4,
		visibled : function(){//还需要考虑
			//return nSpecialId != 0;
		},
		cmd : function(){
			var dom = $('main');
			wcm.CrashBoarder.get('special_edit').show({
				title : '选择创建到的栏目',
				src : 'copyVisualTemplateToChnls.jsp?TemplateId=' +nTemplateId,
				width:'360px',
				height:'350px',
				maskable:true,
				callback : function(param){
					this.close();
					//列表页面转跳
					//location.href=url;
				}
			});
		}
	},
	/**
	*类似创建当前模板
	*/
	{
		oprKey : 'saveAsMaster',
		desc : '保存为母板',
		title : '保存为母板',
		cls : 'saveAsMaster',
		order : 4.5,
		visibled : function(){//还需要考虑
			//return nSpecialId != 0;
			return false;
		},
		cmd : function(){
			var dom = $('main');
			wcm.CrashBoarder.get('save_visual_template_as_master').show({
				title : '保存为母板',
				src : 'master/master_addedit_from_visual_template.jsp?TemplateId=' +nTemplateId+"&TemplateDesc="+encodeURIComponent("xx"),
				width:'580px',
				height:'330px',
				maskable:true,
				callback : function(param){
					this.close();
					//列表页面转跳
					//location.href=url;
				}
			});
		}
	},
	/**
	*执行撤销操作
	*/
	{
		oprKey : 'undo',
		desc : '撤销',
		cls : 'undo',
		order : 5,
		disabled : function(){
			try{
				return !PageController.getMainWin().PageController.execute('testUndo');
			}catch(error){
			}
			return true;
		},
		cmd : function(){
			PageController.getMainWin().PageController.execute('undo');
		}
	},
	/**
	*执行重做操作
	*/
	{
		oprKey : 'redo',
		desc : '重做',
		cls : 'redo',
		order : 6,
		disabled : function(){
			try{
				return !PageController.getMainWin().PageController.execute('testRedo');
			}catch(error){
			}
			return true;
		},
		cmd : function(){
			PageController.getMainWin().PageController.execute('redo');
		}
	},
	/**
	*执行重做操作
	*/
	{
		oprKey : 'setPageBackgroundImage',
		desc : '设置背景图片',
		cls : 'setPageBackgroundImage',
		order : 6,
		cmd : function(){
			var dom = PageController.getMainWin().document.body;
			wcm.CrashBoarder.get('setPageBackgroundImage-win').show({
				title : '设置页面背景图片',
				src : 'upload_background_image.html',
				width:'780px',
				height:'350px',
				maskable:true,
				appendParamsToUrl : false,
				params : {
					background : dom.style.background,
					backgroundRepeat : dom.style.backgroundRepeat,
					backgroundPosition : dom.style.backgroundPosition
				},
				callback : function(result){
					dom.style.background = result['background'];
					window.isPageChanged = true;
					wcm.special.design.ToolBar.refresh();
				}
			});
		}
	},
	{
		oprKey : 'setPageWidth',
		desc : '设置页面宽度',
		cls : 'setPageWidth',
		order : 6,
		cmd : function(){
			var dom = PageController.getMainWin().document.body;
			var templateBoxes = document.getElementsByClassName("template_box", dom);
			var sWidth = "";
			if(templateBoxes && templateBoxes.length > 0){
				sWidth = Element.getStyle(templateBoxes[0],"width");
			}
			wcm.CrashBoarder.get('setPageWidth-win').show({
				title : '设置页面宽度',
				src : 'page_width_set.html',
				width:'300px',
				height:'120px',
				maskable:true,
				appendParamsToUrl : false,
				params : {
					width : sWidth
				},
				callback : function(result){
					for(var i= 0; i< templateBoxes.length;i++){
						templateBoxes[i].style.width = result['width'];
					}
					window.isPageChanged = true;
					wcm.special.design.ToolBar.refresh();
				}
			});
		}
	},
	/**
	*关闭设计页面
	*/
	{
		oprKey : 'closeMe',
		desc : '关闭',
		title : '关闭当前设计页面',
		cls : 'closeMe',
		order : 7,
		cmd : function(){
			//如果设计页面有修改，需要提示保存
			if(!window.isPageChanged){
				window.close();
				return;
			}
			Ext.Msg.confirm("页面发生了改变，是否先进行保存?", {
				yes : function(){
					//保存页面当前的可视化模板信息
					var sVisualTemplateHtml = PageController.getMainWin().PageController.getVisualTemplate();
					PageController.saveVisualTemplate(sVisualTemplateHtml, function(){
						window.close();
					});
				},
				no : function(){
					window.close();
				}
			});
		}
	}
);


Ext.apply(Element, {
	over : function(dom, fn, b){
		Event.observe(dom, 'mouseover', function(event){
			fn(event);
		}, b);
	},
	out : function(dom, fn, b){
		Event.observe(dom, 'mouseout', function(event){
			var toElement = event.toElement;
			if($(dom).contains(toElement)) return;
			fn(event);
		}, b);
	}
});

/*
*	将对象显示在可见区域的中间
*	不使用默认的scrollInToView方法，原方法会导致其它一些问题，如树节点文字显示不对，Top操作栏变小等问题
*	由于IE6的特性，需要通过计算来获取元素的offsetTop值，提供方法如下：getOffsetTop
*/
function scrollInToView(el,win){
	var el = $(el);
	win = win || window;
	var height=0;
	// 处理模板文件不同头部的问题，不同的头部导致浏览器可见高度获取方式不一样
	// 目前通过这种方式来判断是使用body获取还是使用documentElement获取
	if(win.document.documentElement.clientHeight==0){
		height= win.document.body.clientHeight-el.offsetHeight;
		var offsetTop=getOffsetTop(el);
		scrollTo(win.document.body,{x:0,y:height<0?offsetTop:offsetTop-height/2},500);
		//win.document.body.scrollTop = height<0?offsetTop:offsetTop-height/2;
	}else{
		var offsetTop=getOffsetTop(el);
		height = win.document.documentElement.clientHeight-el.offsetHeight;
		scrollTo(win.document.documentElement,{x:0,y:height<0?offsetTop:offsetTop-height/2},500);
		//win.document.documentElement.scrollTop = height<0?el.offsetTop:el.offsetTop-height/2;
	}
}
/*
*	获取元素的相对于body的高度距离值，由于某些原因（如父元素的浮动属性）导致
*	在获取到的offsetTop值与该元素有关，现提供如下方法。
*/
function getOffsetTop(el){ 
	var nTp=0; 
	var offsetParent = el; 
	while (offsetParent!=null && offsetParent!=document.body) { 
		nTp+=offsetParent.offsetTop; 
		offsetParent=offsetParent.offsetParent; 
	}
	return nTp;
}

function scrollTo(el,targetPosi,time){
	// 每次移动时间,20毫秒
	var everyTime = 100;
	var orignPosi = {x:el.scrollLeft,y:el.scrollTop};
	// 移动多少次
	var times = time/everyTime;
	var floatLeft={x:0,y:0};
	// 每次移动距离
	var everyDistance = {x:(targetPosi.x-orignPosi.x)/times,y:(targetPosi.y-orignPosi.y)/times};
	for(var i=0;i<times;i++){
		setTimeout(function(){
			// 做处理：由于scrollTop每次的赋值都会做取整处理，导致累积误差变大，与初始有偏差
			var floatNumY = el.scrollTop +everyDistance.y+floatLeft.y;
			el.scrollTop = parseInt(floatNumY);
			floatLeft.y = floatNumY - el.scrollTop;
			
			var floatNumX = el.scrollLeft +everyDistance.x+floatLeft.x;
			el.scrollLeft = parseInt(floatNumX);
			floatLeft.x = floatNumX - el.scrollTop;
		},everyTime);
	};
}
