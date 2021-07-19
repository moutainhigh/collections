//建立一个全局变量
var wordfile = "";
var pdfOrword = "";
//生成下载纸制的WORD文档
function getEBook()
{
	pdfOrword = document.getElementById("WORDPDF").value;
	createEBook();
	downloadword();
}
//生成纸制文件
function createEBook()
{
	showProcess();
	var sURI = "/WCMV6/gkml/ebook/govinfo_xml_create_offline.jsp";
	// 获取输入的参数
	var sParameters = "";
	// 定义成功后的响应函数
	var fOnComplete = function(_transport, _oResult)
	{
		wordfile = _transport.responseText.trim();
	};
		
	// 定义出现异常后的响应函数
	var fOnException = function(_transport, _oResultInfoObject)
	{
		var pStatus = [3, 404];
		if( pStatus.include(_transport.status) )
		{
			alertGB("服务器连接不成功，请确认当前运行环境或者上下文参数！");
			return;
		}
		hideProcess();
		var sDefaultMsg = '与服务器交互时出现错误！';
		document.write(_transport.responseText);
	};
	// 将JSP地址传入给服务器端
	var oParameters = sParameters.toQueryParams();
	oParameters["_URI_"] = sURI;
	oParameters["STARTYEAR"] = $("STARTYEAR").value;
	BasicDataHelper.doURIGet(oParameters, fOnComplete, fOnException);
}

//循环遍历分析是否已经生成完毕，如果生成完毕则进行下载
//连续试探，发送下载电子书的请求
//生成纸制文件
function downloadword()
{
	var sURI = "/WCMV6/gkml/ebook/download_word.jsp";
	// 获取输入的参数
	var sParameters = "";
	// 定义成功后的响应函数
	var fOnComplete = function(_transport, _oResult)
	{
		var sBookName = _transport.responseText.trim();
		if(sBookName == 'USERISNG')
		{
			alertGB("另外一个人正在生成纸制目录<BR/>请稍候");
			hideProcess();
			return;
		}
		else if(sBookName == 'false')
		{
			setTimeout(downloadword, 5000);
			return;
		}
		else if(sBookName == "No File Create")
		{
			alertGB("电子书生成失败<BR/>后台发生异常");
			hideProcess();
			return;
		}
		//电子书已经创建完成
		hideProcess();
		var sUrl = "/wcm/WCMV6/gkml/ebook/read_file.jsp?FileName=" + encodeURIComponent(sBookName)+"&DownName="+$("STARTYEAR").value+"."+pdfOrword;
		wordfile = "";
		download(sUrl);
	};
	// 定义出现异常后的响应函数
	var fOnException = function(_transport, _oResultInfoObject)
	{
		var pStatus = [3, 404];
		if( pStatus.include(_transport.status) )
		{
			alertGB("服务器连接不成功，请确认当前运行环境或者上下文参数！");
			return;
		}

		var sDefaultMsg = '与服务器交互时出现错误！';
		document.write(_transport.responseText);
	};
	// 将JSP地址传入给服务器端
	var oParameters = sParameters.toQueryParams();
	oParameters["_URI_"] = sURI;
	oParameters["PDFWORD"] = pdfOrword;
	oParameters["wordfile"] = wordfile;
	BasicDataHelper.doURIGet( oParameters, fOnComplete, fOnException);
}
//显示进度条
function showProcess()
{
	Element.show("loading");
	Element.show("shield");
}
//隐藏进度条
function hideProcess()
{
	Element.hide("loading");
	Element.hide("shield");
}
String.prototype.trim = function(){
	return this.replace(/^\s+|\s+$/g, "");
};
function download(sUrl){
	var frm = top.$('iframe4download');
	if(!frm) {
		frm = top.document.createElement('IFRAME');
		frm.id = "iframe4download";
		frm.style.display = 'none';
		top.document.body.appendChild(frm);
	}
	frm.src = sUrl;		
}
