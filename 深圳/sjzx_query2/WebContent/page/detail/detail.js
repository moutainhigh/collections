/**
 * 页面加载时执行。
 */
$(function(){
	var queryString = new String(window.location.search);
	var url = "../../detail/data.do" + queryString;
	$.getJSON(url,function(responseData) {
		if(!responseData){
			alert('加载数据失败。');
		}else{
			renderCurrentPage("content","template",responseData);
			hideNav();// 隐藏目录
		}
	});  
});
/**
 * 隐藏目录。
 */
function hideNav(){
	var hideNav = getUrlParam('hideNav');
	if(hideNav=='1'){
		$('#nav').hide();
		$('#detailPageHeader').width('980px');
	}
}

/**
 * 渲染当前页面。
 * 
 * @param contentId 存放渲染结果的页面元素ID。
 * @param templateId 存放模板的页面元素ID。
 * @param data 数据。
 */
function renderCurrentPage(contentId, templateId, data){
	var content = $('#'+contentId);
	var template = $("#"+templateId).html();
	render(content,template,data);
}

/**
 * 模板渲染。
 * 
 * @param element 页面元素
 * @param tpl 模板字符串
 * @param data 数据
 */
function render(element, tpl, data){
	var render = etpl.compile(tpl);
	var text = render(data);
	$(element).html(text);
}
/**
 * 
 * @param name
 * @returns
 */
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var ret = window.location.search.substr(1).match(reg); //匹配目标参数
	if (ret != null){
		var paramValue = ret[2];
		paramValue = decodeURI(paramValue);
		return paramValue;
	}else{
		return ''; //返回参数值
	}
}