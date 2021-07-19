	var tree;
	var centerTabs;
	var tabsMenu;
	$(function(){
		
		$('#user_login_loginDialog').show().dialog({
			modal : true,
			title : '系统登录',
			closable : false,
			buttons : [ {
				text : '注册',
				handler : function() {
//					$('#user_reg_regDialog').dialog('open');
				}
			}, {
				text : '登录',
				handler : function() {
					/*var tab = $('#user_login_loginTab').tabs('getSelected');
					tab.find('form').submit();*/
				}
			} ]
		});

		//左边的树
		 tree = $('#tree').tree({
			 url : '../../../../admin/torch/tree.do',
			 lines : true,
			 onClick : function(node) {
				  if (node.attributes.url) {
					 var url =  node.attributes.url;
						addTab(node);
				} 
			}
		 });
		 
		 //右边的tab
		 tabsMenu = $('#tabsMenu').menu({
				onClick : function(item) {
					var curTabTitle = $(this).data('tabTitle');
					var type = $(item.target).attr('type');

					if (type === 'refresh') {
						refreshTab(curTabTitle);
						return;
					}

					if (type === 'close') {
						var t = centerTabs.tabs('getTab', curTabTitle);
						if (t.panel('options').closable) {
							centerTabs.tabs('close', curTabTitle);
						}
						return;
					}

					var allTabs = centerTabs.tabs('tabs');
					var closeTabsTitle = [];

					$.each(allTabs, function() {
						var opt = $(this).panel('options');
						if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
							closeTabsTitle.push(opt.title);
						} else if (opt.closable && type === 'closeAll') {
							closeTabsTitle.push(opt.title);
						}
					});

					for ( var i = 0; i < closeTabsTitle.length; i++) {
						centerTabs.tabs('close', closeTabsTitle[i]);
					}
				}
			});

			centerTabs = $('#centerTabs').tabs({
				fit : true,
				border : false,
				onContextMenu : function(e, title) {
					e.preventDefault();
					tabsMenu.menu('show', {
						left : e.pageX,
						top : e.pageY
					}).data('tabTitle', title);
				}
			});
			
			
		/*		var formParam = {
					url : '${pageContext.request.contextPath}/userController/login.action',
					success : function(result) {
						var r = $.parseJSON(result);
						if (r.success) {
							$('#user_login_loginDialog').dialog('close');

							$('#sessionInfoDiv').html(formatString('[<strong>{0}</strong>]，欢迎你！您使用[<strong>{1}</strong>]IP登录！', r.obj.loginName, r.obj.ip));

							$('#layout_east_onlineDatagrid').datagrid('load', {});
						} else {
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					}
				};*/

				/*$('#user_login_loginInputForm').form(formParam);
				$('#user_login_loginDatagridForm').form(formParam);
				$('#user_login_loginCombogridForm').form(formParam);

				$('#user_login_loginTab').tabs({
					fit : true,
					border : false,
					onSelect : function(title, index) {
						if (index == 1) {
							var opts = $('#user_login_loginCombogrid').combogrid('options');
							if (!opts.url) {
								$('#user_login_loginCombogrid').combogrid({
									url : '${pageContext.request.contextPath}/userController/combogrid.action'
								});
							}
						} else if (index == 2) {
							var opts = $('#user_login_loginCombobox').combobox('options');
							if (!opts.url) {
								$('#user_login_loginCombobox').combobox({
									url : '${pageContext.request.contextPath}/userController/combobox.action'
								});
							}
						}
					}
				});
*/
				$('#user_login_loginDialog').show().dialog({
					modal : true,
					title : '系统登录',
					closable : false,
					buttons : [ {
						text : '注册',
						handler : function() {
//							$('#user_reg_regDialog').dialog('open');
						}
					}, {
						text : '登录',
						handler : function() {
							/*var tab = $('#user_login_loginTab').tabs('getSelected');
							tab.find('form').submit();*/
						}
					} ]
				});

				/*$('#user_login_loginCombogrid').combogrid({
					panelWidth : 450,
					panelHeight : 200,
					idField : 'name',
					textField : 'name',
					pagination : true,
					fitColumns : true,
					required : true,
					rownumbers : true,
					mode : 'remote',
					delay : 500,
					pageSize : 5,
					pageList : [ 5, 10 ],
					columns : [ [ {
						field : 'name',
						title : '登录名',
						width : 150
					}, {
						field : 'createdatetime',
						title : '创建时间',
						width : 150
					}, {
						field : 'modifydatetime',
						title : '最后修改时间',
						width : 150
					} ] ]
				});

				$('#user_login_loginCombobox').combobox({
					valueField : 'name',
					textField : 'name',
					required : true,
					panelHeight : 'auto',
					delay : 500
				});*/

				var sessionInfo_userId = '${sessionInfo.userId}';
				if (sessionInfo_userId) {/*目的是，如果已经登陆过了，那么刷新页面后也不需要弹出登录窗体*/
					$('#user_login_loginDialog').dialog('close');
				}

		 
	});
	
	function addTab(node) {
		if (centerTabs.tabs('exists', node.text)) {
			centerTabs.tabs('select', node.text);
		} else {
			if (node.attributes.url && node.attributes.url.length > 0) {
			/*	if (node.attributes.url.indexOf('!druid.action') < 0) {数据源监控页面不需要开启等待提示
					$.messager.progress({
						text : '页面加载中....',
						interval : 100
					});
					window.setTimeout(function() {
						try {
							$.messager.progress('close');
						} catch (e) {
						}
					}, 100);
				}*/
				centerTabs.tabs('add', {
					title : node.text,
					closable : true,
					iconCls : node.iconCls,
					content : '<iframe src="../../../..'+node.attributes.url+'?treeId='+node.id+'&treePath='+node.attributes.levelPath+'" scrolling="auto" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(node.text);
						}
					} ]
				});
			} else {
				centerTabs.tabs('add', {
					title : node.text,
					closable : true,
					iconCls : node.iconCls,
					content : '<iframe src="../../../../404.jsp" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					tools : [ {
						iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(node.text);
						}
					} ]
				});
			}
		}
	}
	function refreshTab(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}
	
	function collapseAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('collapseAll', node.target);
		} else {
			tree.tree('collapseAll');
		}
	}
	function expandAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('expandAll', node.target);
		} else {
			tree.tree('expandAll');
		}
	}
	
	