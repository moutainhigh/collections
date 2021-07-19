if(!window.wcm) wcm = {};
if(!wcm.LANG) wcm.LANG = {};
WCMLANG = wcm.LANG;
if(!wcm.apply)wcm.apply = function(o, c){if(o && c){for(var p in c){o[p] = c[p];}}return o;}
wcm.apply(wcm.LANG, {
	INCLUDE_ALERT_1	: '已经复制到剪切板中！',
	INCLUDE_ALERT_2	: '传入无效的参数对象，对话框初始化失败！',
	INCLUDE_ALERT_3	: '页面加载超时，请重新刷新!',
	INCLUDE_ALERT_4	: '没有选中任何站点，请选择！',
	INCLUDE_TITLE_1 : '系统信息',
	INCLUDE_TITLE_2 : '成功信息',
	INCLUDE_TITLE_3 : '提示信息',
	INCLUDE_TITLE_4 : '查看详细警告信息',
	INCLUDE_TITLE_5 : '隐藏详细警告信息',
	INCLUDE_TITLE_6 : '查看详细错误信息',
	INCLUDE_TITLE_7 : '隐藏详细错误信息',
	INCLUDE_TITLE_8 : '警告信息',
	INCLUDE_TITLE_9 : '显示详细信息',
	INCLUDE_TITLE_10 : '显示详细信息',
	INCLUDE_TITLE_11 : '隐藏详细信息',
	INCLUDE_TITLE_12 : "未选择任何节点！",
	INCLUDE_BUTTON_1 : '确定',
	INCLUDE_BUTTON_2 : '取消',
	INCLUDE_BUTTON_3 : '复制到剪切板',
	INCLUDE_BUTTON_4 : '关闭窗口',
	INCLUDE_TEXTWAREHOUSE : '文字库'
});