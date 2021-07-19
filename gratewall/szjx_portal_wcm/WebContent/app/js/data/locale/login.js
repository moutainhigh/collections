if(!window.wcm) wcm = {};
if(!wcm.LANG) wcm.LANG = {};
WCMLANG = wcm.LANG;
if(!wcm.apply)wcm.apply = function(o, c){if(o && c){for(var p in c){o[p] = c[p];}}return o;}
wcm.apply(wcm.LANG, {
	/*index*/
	INDEX_1 : '窗口可能被拦截工具拦截，当前页面将退出！\n',
	INDEX_2 : '请先关闭可能的拦截工具，\n',
	INDEX_3 : '您是否确认不采用全屏模式操作?'
});