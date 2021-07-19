//主页面
Event.observe(window, 'load', function(){
	var pageStyleId = getParameter("PageStyleId") || 0;
	$('list').src = "./pagestyle_edit_main_index.jsp?PageStyleId=" + pageStyleId;

	initUserName();
});
/**
*该方法用于解决直接设置iframe的src时，sUrl中参数超长问题。
*方法内部会解析sUrl中的链接参数并按照aPostParams参数的需求
*将链接参数转成post方式提交。
*@param iFrameName
*           指定要设置src的iframe元素的name
*@param sUrl
*           指定iframe元素要设置的要设置src的地址
*@param aPostParams
*           指定sUrl中的哪些参数要用post方式提交，默认为sUrl中的所有参数
*@param oPostParamsJson
*           不在sUrl中的，需要post传递的参数
*/
function setIFrameByPost(iFrameName, sUrl, aPostParams,oPostParamsJson){
      //1.分析出sUrl中的真实地址和链接参数.
      var result = sUrl.match(/^([^\?]+)\??(.*)$/);
      var params = {};
      if(result && result[1]) sUrl = result[1];
      //parseQuery方法用于将链接参数转成一个json对象.
      if(result && result[2]) params = result[2].parseQuery();
      //2.创建临时的form元素
      var aParams = [];
      //构造form的临时id
      var sId = 'frm-' + new Date().getTime();
      var aHtml = ['<form method="post" id="', sId, '" target="', iFrameName, '">'];
      for (var sKey in params){
            if(!aPostParams || aPostParams.include(sKey)){
                  //将需要以post方式提交的参数，创建为临时的hidden元素
                  //方法$transHtml用于转义html中的特殊字符
                  aHtml.push('<input type="hidden" name="', 
                        sKey + '" value="', 
                        $transHtml(params[sKey]) + '" />'
                  );
            }else{
                  aParams.push(sKey + "=" + params[sKey]);
            }
      }
	  if(oPostParamsJson){
		  for (var sKey in oPostParamsJson){
			  //将需要以post方式提交的参数，创建为临时的hidden元素
			  //方法$transHtml用于转义html中的特殊字符
			  aHtml.push('<input type="hidden" name="', 
					sKey + '" value="', 
					$transHtml(oPostParamsJson[sKey]) + '" />'
			  );
		  }
	  }
      aHtml.push('</form>');
      var dom = document.createElement("div");
      document.body.appendChild(dom);
      dom.innerHTML = aHtml.join("");
      //将非post提交的参数继续添加到url中
      document.getElementById(sId).action = sUrl + "?" + aParams.join("&");
      //3.提交这个form，同时将设置iframe的内容
      document.getElementById(sId).submit();
      //4.消耗临时的form元素
      document.body.removeChild(document.getElementById(sId).parentNode);
}

function initUserName(){
	var sUserName = decodeURIComponent(getParameter("userName"))||" ";
	$('user').innerHTML = sUserName;
}