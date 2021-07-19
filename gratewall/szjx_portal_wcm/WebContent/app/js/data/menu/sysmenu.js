Ext.ns("wcm.menu.TabAdapter", "wcm.menu.OperatorAdapter");
//system menu register info.
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);

	/*快速通道*/
	reg({
		key : 'KSTD',
		desc : wcm.LANG['SYSMENU_1'] || '快速通道',
		hotKey : 'Q',
		order : 1,
		cls : 'wcm-hide-display',
		items: [
			{
				key : 'quickLocate',
				desc : wcm.LANG['SYSMENU_2'] || '快速定位',
				order : 1,
				cmd : function(event){
					wcm.CrashBoarder.get('quicklocate').show({
						title : wcm.LANG['SYSMENU_2'] || '快速定位',
						src : WCMConstants.WCM6_PATH + "nav_tree/quicklocate.html",
						left: '180px',
						top: '60px',
						width: '400px',
						height: '270px',
						reloadable : false,
						params : [],
						maskable : true,
						callback : function(info){
							//focus node with info
							$('nav_tree').contentWindow.focus(info[0], info[1], null, function(){
								try{
									var treeNodeDom = $('nav_tree').contentWindow.$(info.join('_'));
									if(treeNodeDom==null)return;
									treeNodeDomA = treeNodeDom.getElementsByTagName('A')[0];
									if(treeNodeDomA==null)return;
									var oTreeNode = $('nav_tree').contentWindow.wcm.TreeNode.fly(treeNodeDomA);
									var bReturn = oTreeNode.click();
									if(bReturn!==false){
										oTreeNode.afterclick();
									}
								}catch(err){}
							});
							this.close();
						}
					});
				}
			}
		]
	});

	wcm.menu.OperatorAdapter = {
		execute : function(opers, event){
			var sysOpers = event.getContext().sysOpers;
			opers = opers[0].concat(opers[1]);
			var menuItems = [];
			for (var i = 0; i < opers.length; i++){
				var oper = opers[i];
				if(oper["key"] == 'seperate'){
					menuItems.push({type : 'separate', order : i});
					continue;
				}
				menuItems.push({
					desc : oper["desc"],
					parent : 'opers',
					order : i,
					params : {dynamic:''},
					cmd : sysOpers.exec.bind(window, oper, event)
				});
			}
			return menuItems;
		}
	};

	/*操作任务*/
	reg({
		key : 'CZRW',
		desc : wcm.LANG['SYSMENU_3'] || '操作任务',
		hotKey : 'O',
		order : 2,
		items: [
			{key : 'add',	desc : (wcm.LANG['SYSMENU_4'] || '新建'), order : 1},
			{key : 'import',desc : (wcm.LANG['SYSMENU_5'] || '导入'), order : 2},
			{key : 'export',desc : (wcm.LANG['SYSMENU_6'] || '导出'), order : 3},
			{type : 'separate', order : 4},
			{
				key : 'opers',
				type : 'dynamic',
				order : 5,
				items : function(event){
					try{
						if(!event || !event.getContext()){
							return;
						}
						var opers = event.getContext().sysOpers.getOpers(event);
						return wcm.menu.OperatorAdapter.execute(opers, event);
					}catch(error){
					}
				}
			}
		]
	});

	/*视图*/
	reg({
		key : 'ST',
		desc : wcm.LANG['SYSMENU_7'] || '视图',
		hotKey : 'V',
		order : 3
	});
	reg({
		type:'checkItem',
		key : 'navigate',
		desc : wcm.LANG['SYSMENU_44'] || '导航栏',
		parent : 'ST',
		order : 1,
		cmd : function(event){
			var hideCls = Element.hasClassName('main_center_container', 'hide_west');
			var sMethod = hideCls ? 'expand' : 'collapse';
			wcm.Layout[sMethod]('west', 'main_center_container');
		},
		cls : function(event, descNode){
			var hideCls = Element.hasClassName('main_center_container', 'hide_west');
			var sMethod = hideCls ? 'removeClass' : 'addClass';
			Ext.fly(descNode)[sMethod]('checkItem');
		}
	});

	function getMainSearch(){
		try{
			var mainWin = $('main').contentWindow;
			var excludeRegExp = /[\?&](?:ISSEARCH|DISABLETAB|SITETYPE|SITEID|CHANNELID|RIGHTVALUE|ISVIRTUAL|CHANNELTYPE|TABURL)=[^&]*(?:&$)?/ig;
			var currMainSearch = mainWin.location.search.replace(excludeRegExp, "");
			var joinRegExpget = /^[\?&]?/;
			currMainSearch = currMainSearch.replace(joinRegExpget,"");
		}catch(error){
			return '';
		}
	}

	reg({
		type:'checkItem',
		key : 'classic',
		desc : wcm.LANG['SYSMENU_43'] || '经典模式',
		parent : 'ST',
		order : 0.5,
		cmd : function(event){
			window.m_bClassicList = !window.m_bClassicList;
			$MsgCenter.$main({params : getMainSearch()}).redirect();
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[window.m_bClassicList ? 'addClass' : 'removeClass']('checkItem');
		}
	});

	var operpanelhided = false;
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_MAINPAGE,
		beforeinit : function(event){
			$MsgCenter.$main()["operpanel" + (operpanelhided?'hide' : 'show')]();
		}
	});
	reg({
		type:'checkItem',
		key : 'attribute',
		desc : wcm.LANG['SYSMENU_8'] || '属性栏',
		parent : 'ST',
		order : 2,
		cmd : function(event){
			$MsgCenter.$main()["operpanel" + (operpanelhided?'show' : 'hide')]();
			operpanelhided = !operpanelhided;
		},
		cls : function(event, descNode){
			event = wcm.MenuContext.getEvent('mainpage');
			try{
				Ext.fly(descNode)[event.getContext().operEnable ? 'removeClass' : 'addClass']('disabled')
			}catch(error){
			}
			Ext.fly(descNode)[operpanelhided ? 'removeClass' : 'addClass']('checkItem')
		}
	});

	wcm.menu.TabAdapter = {
		execute : function(defaultTab, tabs, event){
			var tabItems = tabs.items;
			var menuItems = [];
			var context = event.getContext();
			for (var i = 0; i < tabItems.length; i++){
				var tab = tabItems[i];
				tab = tabs[tab.type.toLowerCase()];
				if(Ext.isFunction(tab.isVisible)
						&& !tab.isVisible(context)){
					continue;
				}
				menuItems.push({
					type: defaultTab.type == tab.type ? 'radioItem' : '',
					desc : Ext.kaku(tab["desc"], null, context),
					parent : 'workwindow',
					order : i,
					params : {dynamic:''},
					cmd : getTabMgr().exec.bind(window, tab, true)
				});
			}
			return menuItems;
		}
	};
	var getTabMgr = function(){
		try{
			var mainWin = $('main').contentWindow;
			return mainWin.wcm.TabManager || wcm.TabManager;
		}catch(error){
			return wcm.TabManager;
		}
	};
	reg({
		key : 'workwindow',
		desc : wcm.LANG['SYSMENU_9'] || '工作窗口',
		parent : 'ST',
		order : 3,
		cls : function(event, descNode){
			if(!event || !event.getContext()){
				return;
			}
			var hostType = event.getHost().getType();
			if(hostType == WCMConstants.OBJ_TYPE_CHANNELMASTER){
				hostType = WCMConstants.OBJ_TYPE_CHANNEL;
			}
			var tabMgr = getTabMgr();
			var context = event.getContext();
			var defaultTab = tabMgr.getDefaultTab(hostType, context);
			Ext.fly(descNode)[!defaultTab ? 'addClass' : 'removeClass']('disabled');
		},
		items : [
			{
				type : 'dynamic',
				order : 1,
				items : function(event, itemNode){
					if(!event || !event.getContext()){
						return;
					}
					var hostType = event.getHost().getType();
					if(hostType == WCMConstants.OBJ_TYPE_CHANNELMASTER){
						hostType = WCMConstants.OBJ_TYPE_CHANNEL;
					}
					var tabMgr = getTabMgr();
					var context = event.getContext();
					var defaultTab = tabMgr.getDefaultTab(hostType, context);
					if(!defaultTab) return;
					var tabs = tabMgr.getTabsByInfo(hostType, true, context);
					return wcm.menu.TabAdapter.execute(defaultTab, tabs, event);
				}
			}
		]
	});
	var getMyFlag = function(sPrefix){
		return sPrefix + "_" + m_sUserId + "_" + window.location.hostname.replace(/\.|-/g, "_");
	}
	var skipTo = function(jsonObj){//弹出新窗口，跳转到原52页面
		if(jsonObj["Path"]){
			var winObj = $openMaxWin('/wcm/console/index/index.jsp?Path=' + jsonObj["Path"], getMyFlag('WCM52'));
			if(winObj){
				winObj.focus();
			}else{
				var msg = "窗口可能被拦截工具拦截，当前操作失效" + "！\n";
				msg +=    "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
				msg +=    "为此给你带来不便，我们深表歉意！";
				Ext.Msg.alert(msg);
			}
		}else{
			Ext.Msg.alert( "想跳转到52页面时，竟然都不存在path参数！！！");
		}
	}
	function hasOperRight(menuName){
		if(v6To52[menuName]== '' || v6To52[menuName]== 'undefined'){return true;}
		return globalTabDisabled[v6To52[menuName]];
	}
	window.gSkipTo = skipTo;


	/*协作服务*/
	reg({
		key : 'XZFW',
		desc : '协作服务',
		hotKey : 'C',
		order : 4
	});
	reg({
		key : 'calendar',
		desc :  '日程安排',
		parent : 'XZFW',
		order : 2,
		cmd : function(event){
			skipTo({Path:'calendar,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('calendar') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'communication',
		desc :  '通讯录',
		parent : 'XZFW',
		order : 3,
		cmd : function(event){
			skipTo({Path:'contact,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('contact') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'favorite',
		desc :  '收藏夹',
		parent : 'XZFW',
		order : 5,
		cmd : function(event){
			skipTo({Path:'favorite,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('favorite') ? 'addClass' : 'removeClass']('disabled');
		}
	});

	/*个人服务*/
	reg({
		key : 'GRFW',
		desc :  '个人服务',
		hotKey : 'P',
		order : 5
	});
	reg({
		key : 'myinformation',
		desc :'我的信息',
		parent : 'GRFW',
		order : 1,
		cmd : function(event){
			skipTo({Path:'myInformation,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('myInformation') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'myright',
		desc : '我的权限',
		parent : 'GRFW',
		order : 2,
		cmd : function(event){
			var sFeatures = "status=yes,toolbar=no,menubar=no,location=no,resizable =yes";
			window.open('auth/operator/right_view.jsp?OperId=' + m_sUserId + '&OperType=' + userObjType, getMyFlag('myright'), sFeatures);
		}
	});
	reg({
		type : 'separate',
		parent : 'GRFW',
		order : 3
	});
	reg({
		key : 'individuate',
		desc :'个性化定制',
		parent : 'GRFW',
		order : 4,
		cmd : function(event){
			var isAdmin = false;
			if(wcm.AuthServer.isAdmin())
				isAdmin = true;
			var sUrl = 'individuation/individual.html?path=login&isAdmin=' + isAdmin;
			if(window.showModalDialog){
				var sFeatures = "dialogHeight:450px;dialogWidth:560px;status:no;scroll:no";
				if(!Ext.isIE){
					var l	= window.screen.width/2 - 280, t = window.screen.height/2 - 150;
					sFeatures += ";dialogTop:"+t+"px;dialogLeft:"+l+"px";
				}
				window.showModalDialog(sUrl, top, sFeatures);

			}else{
				var sFeatures = "height=450px,width=560px,status=no,scroll=no";
				var l	= window.screen.width/2 - 280, t = window.screen.height/2 - 150;
					sFeatures += ",top="+t+"px,left="+l+"px";
				window.open(sUrl, getMyFlag('individuate'), sFeatures);
			}
		}
	});

	/*管理工具*/
	reg({
		key : 'XTGL',
		desc : '系统管理',
		hotKey : 'M',
		order : 6
	});
	reg({
		key : 'publishmonitor',
		desc : '发布监控',
		parent : 'XTGL',
		order : 1,
		cmd : function(event){
			skipTo({Path:'publicMonitor,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('publicMonitor') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'stathome',
		desc : '统计分析',
		parent : 'XTGL',
		order : 2,
		cmd : function(event){
			skipTo({Path:'statHome,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('statHome') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'actionlog',
		desc :'操作日志',
		parent : 'XTGL',
		order : 3,
		cmd : function(event){
			skipTo({Path:'actionLog,0'});
		}
	});
	reg({
		key : 'usercontrol',
		desc : '用户管理',
		parent : 'XTGL',
		order : 4,
		cmd : function(event){
			skipTo({Path:'userControl,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('userControl') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	if(Ext.isDebug()){
		reg({
			type : 'separate',
			parent : 'XTGL',
			order : 5
		});
		reg({
			key : 'wcmtools',
			desc :'WCM工具',
			parent : 'XTGL',
			order : 7,
			cmd : function(event){
				window.open("/wcm/wcm_use/index.html","wcmtools");
			},
			cls : function(event, descNode){
				Ext.fly(descNode)[hasOperRight('wcmTools') ? 'addClass' : 'removeClass']('disabled');
			}
		});
	}
	/*系统配置*/
	reg({
		key : 'XTPZ',
		desc : '配置管理',
		parent : 'XTGL',
		order : 6
	});
	reg({
		key : 'otherconfig',
		desc : '属性配置',
		parent : 'XTPZ',
		order : 1,
		cmd : function(event){
			skipTo({Path:'otherconfig,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('otherconfig') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'systemconfig',
		desc : '系统配置',
		parent : 'XTPZ',
		order : 2,
		cmd : function(event){
			skipTo({Path:'systemconfig,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('systemconfig') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'sechedual',
		desc : '计划调度',
		parent : 'XTPZ',
		order : 3,
		cmd : function(event){
			skipTo({Path:'sechedual,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('sechedual') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'hotword',
		desc : '热词管理',
		parent : 'XTPZ',
		order : 4,
		cmd : function(event){
			skipTo({Path:'hotword,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('hotWord') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'searchconfig',
		desc : '检索配置',
		parent : 'XTPZ',
		order : 5,
		cmd : function(event){
			$openMaxWin(WCMConstants.WCM6_PATH + 
				'search/search_list.jsp');
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('searchconfig') ? 'addClass' : 'removeClass']('disabled');
		}
	});

	/*选件*/
	reg({
		key : 'XJ',
		desc : '选件',
		hotKey : 'X',
		order : 8
	});
	reg({
		key : 'NRHD',
		desc : '内容互动',
		parent : 'XJ',
		order : 1,
		items : [
			{
				key : 'questionnaire',
				desc : '问卷调查',
				order : 1,
				cmd : function(event){
					skipTo({Path:'questionnaire,0'});
				},
				cls : function(event, descNode){
					Ext.fly(descNode)[hasOperRight('questionnaire') ? 'addClass' : 'removeClass']('disabled');
				}
			},
			{
				key : 'commentonline',
				desc : '在线评论',
				order : 2,
				cmd : function(event){
					skipTo({Path:'commentonLine,0'});
				},
				cls : function(event, descNode){
					Ext.fly(descNode)[hasOperRight('commentonLine') ? 'addClass' : 'removeClass']('disabled');
				}
			}
		],
		cls : function(event, descNode){
			Ext.fly(descNode)[(hasOperRight('questionnaire') && hasOperRight('commentonLine'))  ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'adpluin',
		desc : '广告管理',
		parent : 'XJ',
		order : 2,
		cmd : function(event){
			skipTo({Path:'adPluin,0'});
		},
		cls : function(event, descNode){
			if(!bAdMainEnable) {
				descNode.style.display = "none";
				return;
			}
			Ext.fly(descNode)[hasOperRight('adPluin') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'autoinfor',
		desc : '智能信息处理',
		parent : 'XJ',
		order : 3,
		cmd : function(event){
			skipTo({Path:'autoInfor,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('autoInfor') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'infoview_list',
		desc :'自定义表单',
		parent : 'XJ',
		order : 4,
		cmd : function(event){
			skipTo({Path:'infoview_list,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[(hasOperRight('infoview_list') || !RegsiterMgr.isValidPlugin('infoview')) ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'infogate',
		desc : '数据网关',
		parent : 'XJ',
		order : 5,
		cmd : function(event){
			skipTo({Path:'infogate,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('infogate') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'subscribe',
		desc : '邮件订阅',
		parent : 'XJ',
		order : 6,
		cmd : function(event){
			skipTo({Path:'subscribe,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('subscribe') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	reg({
		key : 'interview',
		desc : '嘉宾访谈',
		parent : 'XJ',
		order : 6,
		cmd : function(event){
			skipTo({Path:'interview,0'});
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[hasOperRight('interview') ? 'addClass' : 'removeClass']('disabled');
		}
	});
	/*注册专题制作选件入口*/
	reg({
		key : 'special',
		desc : '专题制作',
		parent : 'XJ',
		order : 7,
		cmd : function(event){
			var winObj = $openMaxWin('special/index.html?AdvanceManageStyle=1&userName='+encodeURIComponent(m_sUserName), getMyFlag('special'));
			if(winObj){
				winObj.focus();
			}else{
				var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
				msg +=   "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
				msg +=   "为此给你带来不便，我们深表歉意！";
				Ext.Msg.alert(msg);
			}
		},
		cls : function(event, descNode){
			//
		}
	});

	/*注册政府信息公开选件入口*/
	reg({
		key : 'govinfo',
		desc : '政府信息公开',
		parent : 'XJ',
		order : 8,
		cmd : function(event){
			var winObj = open('../WCMV6/gkml/2menu.jsp', 'replace');
			if(winObj){
				winObj.focus();
			}else{
				var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
				msg +=    "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
				msg +=    "为此给你带来不便，我们深表歉意！";
				Ext.Msg.alert(msg);
			}
		},
		cls : function(event, descNode){
			Ext.fly(descNode)[!g_IsRegister['metadata'] ? 'addClass' : 'removeClass']('disabled');
		}
	});

	/*注册绩效考核选件入口*/
	reg({
		key : 'stat',
		desc : '绩效考核',
		parent : 'XJ',
		order : 9,
		cmd : function(event){
			var winObj = open('stat/index.jsp', window.location.hostname.replace(/\.|-/g, "_")+"_stat");
			if(winObj){
				winObj.focus();
			}else{
				var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
				msg +=   "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
				msg +=   "为此给你带来不便，我们深表歉意！";
				Ext.Msg.alert(msg);
			}
		},
		cls : function(event, descNode){
			//Ext.fly(descNode)[!g_IsRegister['metadata'] ? 'addClass' : 'removeClass']('disabled');
		}
	});

	/*注册帮助菜单项*/
	reg({
		key : 'BZ',
		desc :'帮助',
		hotKey : 'H',
		order : 9
	});
	reg({
		key : 'backfeedOnline',
		desc : '在线反馈',
		parent : 'BZ',
		order : 1,
		cmd : function(event){
			window.open('http://www.trs.com.cn', window.location.hostname.replace(/\.|-/g, "_") + 'backfeedOnline');
		}
	});
	reg({
		key : 'contact',
		desc :'联系我们',
		parent : 'BZ',
		order : 2,
		cmd : function(event){
			if(!Ext.isIE) return;
			var oLink = $("link_for_mail");
			if(!oLink){
				oLink = document.createElement("a");
				oLink.id = 'link_for_mail';
				oLink.href = "mailto:support@trs.com.cn";
				oLink.style.display = 'none';
				document.body.appendChild(oLink);
			}
			oLink.click();
		},
		cls : function(event, descNode){
			if(Ext.isIE) return;
			if($('link_for_mail')) return;
			new Insertion.Bottom(descNode, '<a id="link_for_mail" href="mailto:support@trs.com.cn" class="mailto"></a>');
		}
	});
	reg({
		key : 'aboutwcm',
		desc :'关于WCM',
		parent : 'BZ',
		order : 3,
		cmd : function(event){
		/*2013.01.06将about.html更改为about.jsp*/
			if(window.showModalDialog){
				var sFeatures = "dialogHeight:350px;dialogWidth:410px;status:no;scroll:no;";
				showModalDialog("main/about.jsp", null, sFeatures);
			}else{
				var sFeatures = "height:350px,width:410px,status:no;scroll:no;";
				window.open('main/about.jsp', window.location.hostname.replace(/\.|-/g, "_") + 'aboutwcm', sFeatures);
			}
		}
	});
})();

wcm.menu.HostSelectAdapter = {
	execute : function(event){
		var host = event.getHost();
		var hostType = host.getIntType();
		var oPostData = {};
		if(hostType==101){
			oPostData.channelids = host.getId();
			oPostData.CurrChannelId = host.getId();
		}else if(hostType == 1){
			oPostData.sitetype = host.getId();
		}else{
			oPostData.siteids = host.getId();
			oPostData.CurrSiteId = host.getId();
		}
		return oPostData;
	}
};