(function(){
	com.trs.tree.TreeNav.methodType = 'get';

	//覆写动态获取指定节点的子节点HTML方法
	com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(_elElementLi){
		var nPos = _elElementLi.id.indexOf("_");
		if(nPos <= 0){
			alert("不符合规则！");
			return;
		}

		var sParentType	= _elElementLi.id.substring(0, nPos);
		var sParentId		= _elElementLi.id.substring(nPos+1);
		return "../nav_tree/tree_html_creator.jsp?Type=0&ParentType=" + sParentType + "&ParentId=" + sParentId + "&forIndividual=1";
	}

	com.trs.tree.TreeNav.observe('onload', function(){
		//展开树根节点
		var divElement = Element.first($('TreeView'));
		divElement.setAttribute("id", "s_"+(getParameter("siteId")||0));
		divElement.fireEvent("onclick");
		com.trs.tree.TreeNav.focus(divElement);

		BasicDataHelper.call('wcm61_website', 'findbyid', {objectId : getParameter("siteId")}, true, function(transport, json){
			var siteName = $v(json, "Website.sitename");
			Element.update(divElement, siteName);
		});


	
	});


	var deleteIds = [];
	var canOperate0 = com.trs.tree.TreeManager.prototype.canOperate;
	var delete0 = com.trs.tree.TreeManager.prototype['delete'];
	Object.extend(com.trs.tree.TreeManager.prototype, {
		canOperate : function(operType){
			if(canOperate0.apply(this, arguments)){
				return operType == 'delete';
			}
			return false;
		},
		'delete' : function(){
			var id = this._currentTreeNode_.getAttribute("name");
			id = id.split("_")[1];
			delete0.apply(this, arguments);
			if(this.params["isChanged"]){
				this.params["isChanged"] = false;
				deleteIds.push(id);
			}
		}
	});

	window.getDeleteChannelIds4MobileProtal = function(){
		return deleteIds.join(",");
	}
})();


(function(){
	var gHandler = null;
	var gCount = 0;

	//开始启动创建移动门户时的消息提示
	function startLoading(){
		$('btnOk').disabled = true;
		Element.show('progress-loading');
		Element.update('progress-current', "开始生成移动门户");
		Element.show('progress-current');
		gHandler = setInterval(function(){
			if(gCount < 100){
				gCount++;
				$('progress-loading').innerHTML = $('progress-loading').innerHTML + ".";
			}else{
				$('progress-loading').innerHTML = "";
			}
		}, 1000);
	}

	//移动门户创建完成时的消息提示
	function endLoading(){
		$('btnOk').disabled = false;
		Element.hide('progress-loading');
		Element.update('progress-current', "<b style='font-size:2em;'>移动门户生成已经完成，并提交发布任务至后台</b>");
		if(gHandler){
			clearInterval(gHandler);
			gHandler = null;
			gCount = 0;
		}
		setTimeout(function(){//fix ie8:先弹出窗口，再画出“移动门户生成完成”
			if(confirm("移动门户创建已完成，点击确定将关闭当前页面，进入ＷＣＭ主页面．")){
				window.close();
			}
		}, 100);
	}

	//与后台服务器通信，检测当前移动门户创建进度
	function detectCurrentProgress(){
		BasicDataHelper.call('wcm61_mobileportal', 'getCurrMessage', null, true, function(transport, json){
			var msg = transport.responseText;
			if(msg.trim() == "-1"){//移动门户生成完成
				endLoading();
			}else{
				Element.update('progress-current', msg);
				setTimeout(detectCurrentProgress, 2000);
			}
		});		
	}
	//与后台服务器通信，开始创建移动门户
	function createMobileProtal(){
		if(!confirm("确认要生成移动门户？")){
			return;
		}

		startLoading();
		
		var data = {
			SrcSiteId : getParameter('siteId'),
			DelChnlIds : getDeleteChannelIds4MobileProtal(),
			CreateOfficialWB : $('WeiboEnable-Yes').checked ? "1" : "0",
			CreatePushMsg : $('NotificationEnable-Yes').checked ? "1" : "0",
			DOCSDATE : $('DOCSDATE').value,
			TemplateFlag : $('TemplateFlag').value
		};

		BasicDataHelper.call('wcm61_mobileportal', 'createMobilePortal', data, true, function(transport, json){
			detectCurrentProgress();
		});
	}
	
	Event.observe(window, 'load', function(){
		//同步文档时间初始化
		wcm.TRSCalendar.get({
			input : 'DOCSDATE',
			handler : 'DOCSDATEBTN'
		});
		//绑定底部确定按钮
		Event.observe('btnOk', 'click', function(){
			createMobileProtal();
		});
		//绑定底部关闭按钮
		Event.observe('btnClose', 'click', function(){
			window.close();
		});

	});
})();