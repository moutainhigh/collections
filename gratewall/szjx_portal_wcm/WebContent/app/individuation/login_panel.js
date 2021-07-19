//登陆页面的设置
var LoginTabDealer = {
	lastClickBackgroundImgRow : null, //最后单击的BackgroundImg行
	backgroundImgContainer : null,//backgroundImg容器
	serviceName : 'wcm6_individuation',
	saveMethod : 'save',
	disabledBackgroundSelected : false,

	initLoginTabEvent : function(){

		Event.observe('randomBackground', 'click', function(){
			//this.backgroundImgContainer.disabled = $('randomBackground').checked;
			this.backgroundImgContainer.setAttribute("disabled", $('randomBackground').checked);
			this.disabledBackgroundSelected = $('randomBackground').checked;
			$('fileNameControl').style.display=  $('randomBackground').checked ? "none" : "";
		}.bind(this));

		//邦定背景图片的click事件
		Event.observe(this.backgroundImgContainer, 'click', function(event){
			if(arguments.callee.inited){
				if(LoginTabDealer.disabledBackgroundSelected == true) return;
			}
			arguments.callee.inited = true;
			if(LoginTabDealer.notTriggerEvent) return;
			event = window.event || event;
			var srcElement = Event.element(event);
			var imgName = srcElement.getAttribute('simpleName');
			if(!imgName){//单击的不是背景图片行
				return;
			}
			LoginTabDealer.toggleBackgroundImgStyle(LoginTabDealer.lastClickBackgroundImgRow, false);
			LoginTabDealer.lastClickBackgroundImgRow = srcElement;
			LoginTabDealer.toggleBackgroundImgStyle(LoginTabDealer.lastClickBackgroundImgRow, true);
			var previewImg = $('previewImg');
			if(srcElement.getAttribute("belongToCurrUser")){
				$('deleteImgBtn').disabled = false;
			}else{
				$('deleteImgBtn').disabled = true;
			}
			if(srcElement.getAttribute("isLocal")){// 表示是本地文件
				previewImg.src = imgName;
				$('uploadImgBtn').disabled = false;
				$('deleteImgBtn').disabled = false;
			}else{
				previewImg.src = '/webpic/images/login/background/' + encodeURI(imgName);
				$('uploadImgBtn').disabled = true;
			}
		});

		//初始化文件上传相关事件
		function initUploadRelative(){
			LoginTabDealer.setUploadRelateElement();
			//图片浏览完成之后的回调
			$('fileNameControl').onchange = function(){
				var fileNameDom = $('fileNameControl');
				if(!fileNameDom.value.match(/.+\.(bmp|gif|jpg|png|jpeg)/i)){
						alert(String.format("请传入格式正确的图片,可接受的格式为：[*.bmp;*.gif;*jpg;*.png;*.jpeg]"));
						return;
				}
				var fileInfo = LoginTabDealer.parseFilePath(fileNameDom.value);
				if(fileInfo.fileName.length > 50){
					alert(wcm.LANG['INDIVIDUAL_12'] || "文件名过长，请先改为较短的文件名");
					return;
				}
				var imgRowObj = LoginTabDealer.createBackgroundImgRow(fileNameDom.value, fileInfo.fileName, fileInfo.fileExtend);
				imgRowObj.setAttribute("isLocal", true);// 表示是本地文件
				imgRowObj.scrollIntoView();
				imgRowObj.fireEvent("onclick");
				//alert(wcm.LANG['INDIVIDUAL_29'] || "图片浏览成功，必须点击上传才能有效！");
				//LoginTabDealer.browseDom.disabled = true;
				$('fileNameControl').style.display="none";
			};
		}
		initUploadRelative();

		//单击＂上传＂按钮执行的操作
		Event.observe('uploadImgBtn', 'click', function(){
				LoginTabDealer.notTriggerEvent = true;
				$('roller').style.display = '';
				$('uploadImgBtn').disabled = true;
				LoginTabDealer.setUploadRelateElement();
				var fileNameDom = $('fileNameControl');
				var fileNameValue = fileNameDom.value.substring(fileNameDom.value.lastIndexOf("\\")+1);
				var sParams = "fileNameParam=fileNameControl&fileNameValue="+encodeURI(encodeURI(fileNameValue));
				YUIConnect.setForm('formId', true, Ext.isSecure);
				YUIConnect.asyncRequest('POST',
				'file_upload_dowith.jsp?'+sParams, {
					"upload" : function(_transport){
						LoginTabDealer.notTriggerEvent = false;
						$('roller').style.display = 'none';
						//LoginTabDealer.browseDom.disabled = false;
						$('formId').reset();
						$('fileNameControl').style.display="";
						LoginTabDealer.notTriggerEvent = false;
						$('roller').style.display = 'none';

						var sResponseText = _transport.responseText;
						if(sResponseText){
							eval("var result="+sResponseText);
							if(result["Error"]){
									alert((wcm.LANG['INDIVIDUAL_13'] || "上传文件失败！\n") + result["Error"]);
									$('uploadImgBtn').disabled = false
							}else{
								uploadSuccessCallBack(result);
							}
						//iframeDom.setAttribute("src", 'file_upload.html');
						}else{
							initUploadRelative();
						}
					}
				});
			function uploadSuccessCallBack(result){//上传成功之后执行的回调
				var simpleName = result['Message'];
				LoginTabDealer.lastClickBackgroundImgRow.removeAttribute("isLocal");
				LoginTabDealer.lastClickBackgroundImgRow.setAttribute("belongToCurrUser", true);
				LoginTabDealer.lastClickBackgroundImgRow.setAttribute("simpleName", simpleName);
				LoginTabDealer.lastClickBackgroundImgRow.fireEvent("onclick");
			}
		});

		//单击＂删除＂按钮执行的操作
		Event.observe('deleteImgBtn', 'click', function(event){
			if(!confirm(wcm.LANG['INDIVIDUAL_14'] || "确定删除当前背景图片？")) return;
			var currImgRow = LoginTabDealer.lastClickBackgroundImgRow;
			if(currImgRow.getAttribute("isLocal")){
				//LoginTabDealer.browseDom.disabled = false;
				$('formId').reset();
				$('fileNameControl').style.display="";
				var node = Element.next(currImgRow)
						|| getFirstHTMLChild(LoginTabDealer.backgroundImgContainer);
				backgroundImgContainer.removeChild(currImgRow);
				delete currImgRow;
				if(node){
					node.scrollIntoView();
					node.fireEvent("onclick");
				}
				return;
			}
			LoginTabDealer.notTriggerEvent = true;
			$('roller').style.display = '';
			$('uploadImgBtn').disabled = true;
			var url = 'delete_background.jsp';
			var fileName = encodeURIComponent(LoginTabDealer.lastClickBackgroundImgRow.getAttribute("simpleName"));
			new Ajax.Request(url, {
				contentType : 'application/x-www-form-urlencoded',
				parameters:"fileName=" + fileName,
				onSuccess : function(transport){
					var docText = transport.responseText;
					if(docText.indexOf("true") >= 0){
						var currImgRow = LoginTabDealer.lastClickBackgroundImgRow;
						LoginTabDealer.lastClickBackgroundImgRow = Element.next(currImgRow);
						backgroundImgContainer.removeChild(currImgRow);
						delete currImgRow;
					}else{
						alert(wcm.LANG['INDIVIDUAL_15'] || "执行非正常操作，图片并未删除！");
					}
				},
				onFailure : LoginTabDealer.failure.bind(LoginTabDealer, wcm.LANG['INDIVIDUAL_16'] || "删除背景图片失败!"),
				onComplete: function(){
					LoginTabDealer.notTriggerEvent = false;
					$('roller').style.display = 'none';
					var node = LoginTabDealer.lastClickBackgroundImgRow
							|| getFirstHTMLChild(LoginTabDealer.backgroundImgContainer);
					if(node){
						node.scrollIntoView();
						node.fireEvent("onclick");
					}
				}
			});
		});
	},

	//设置有关上传控件的引用
	setUploadRelateElement : function(){
	},

	//发送Ajax请求生成Background列表
	createBackground : function(successCallBack){
		var url = 'get_background.jsp';
		new Ajax.Request(url, {
			contentType : 'application/x-www-form-urlencoded',
			onSuccess : function(transport){
				var imgFiles = transport.responseText.trim().split(",");

				for (var i = 0; i < imgFiles.length; i++){
					if(imgFiles[i] == '')continue;
					var fileInfo = LoginTabDealer.parseFilePath(imgFiles[i]);
					LoginTabDealer.createBackgroundImgRow(imgFiles[i], fileInfo.fileName, fileInfo.fileExtend);
				}
				(successCallBack || Ext.emptyFn)();
			},
			onFailure : LoginTabDealer.failure.bind(LoginTabDealer, wcm.LANG['INDIVIDUAL_17'] || "获得可用背景图片失败")
		});
	},

	parseFilePath : function(filePath){
		filePath = decodeURI(decodeURI(filePath));
		var dotIndex = filePath.lastIndexOf(".");
		var fileExtend = filePath.substr(dotIndex+1);
		var fileName = filePath.substr(0, dotIndex);
		fileName = fileName.replace(/\\/g, "/");
		var slashIndex = fileName.lastIndexOf("/");
		fileName = fileName.substr(slashIndex+1);
		return {fileExtend : fileExtend, fileName : fileName};
	},

	failure : function(message){//Ajax请求失败执行的回调函数
		alert("Ajax " + (wcm.LANG['INDIVIDUAL_18'] || "请求失败") + "！\n" + message);
	},

	//切换活动/非活动行的样式
	toggleBackgroundImgStyle : function(domObj, isActive){
		if(domObj){
			if(isActive){
				domObj.style.backgroundColor = 'highlight';
				if(domObj.getAttribute("isLocal")){
					domObj.style.color = "yellow";
				}else{
					domObj.style.color = 'white';
				}
			}else{
				domObj.style.backgroundColor = "#fff";
				if(domObj.getAttribute("isLocal")){
					domObj.style.color = "green";
				}else{
					domObj.style.color = 'black';
				}
			}
		}
	},

	/*
	*返回保存的变量名值对序列
	*/
	setSaveParams : function(aCombine, oHelper){
		if(!LoginTabDealer.lastClickBackgroundImgRow ||
				LoginTabDealer.lastClickBackgroundImgRow.getAttribute("isLocal")){
			return;
		}
		if(LoginTabDealer.lastClickBackgroundImgRow){
			aCombine.push(oHelper.Combine(this.serviceName, this.saveMethod, {
				objectId	: $('previewImg').getAttribute("objectId"),
				paramName	: 'background',
				paramValue	: LoginTabDealer.lastClickBackgroundImgRow.getAttribute("simpleName")
			}));
		}
		var cookie = "background=" + LoginTabDealer.lastClickBackgroundImgRow.getAttribute("simpleName");
		var expires = new Date(new Date().getTime() + 24 * 60 * 60 * 1000 * 365).toGMTString();
		topHandler.document.cookie = cookie + ";expires=" + expires;
		cookie = "randomBackground=" + ($('randomBackground').checked ? "1" : "0");
		topHandler.document.cookie = cookie + ";expires=" + expires;
		cookie = "userid=" + topHandler.m_sUserId;
		topHandler.document.cookie = cookie + ";expires=" + expires;
	},

	createBackgroundImgRow : function(imgFullName, fileName, fileExt){
		var imgRowObj = document.createElement("div");
		imgRowObj.innerHTML = fileName;
		imgRowObj.setAttribute("simpleName", imgFullName);
		if(imgFullName.indexOf("/") >= 0){
			imgRowObj.setAttribute("belongToCurrUser", true);
		}
		imgRowObj.className = "backgroundImgRow backgroundImgRow_" + fileExt;
		LoginTabDealer.backgroundImgContainer.appendChild(imgRowObj);
		return imgRowObj;
	},

	initLoginTabValue : function(){
		var backgroundCon = topHandler.m_CustomizeInfo["background"] || {};
		$('previewImg').setAttribute("objectId", backgroundCon.objectId || 0),
		this.createBackground(function successCallBack(){
			var node = getFirstHTMLChild(LoginTabDealer.backgroundImgContainer);
			while(node){
				if(node.getAttribute("simpleName").endsWith(backgroundCon["paramValue"] || "")){
					break;
				}
				node = Element.next(node);
			}
			node = node || getFirstHTMLChild(LoginTabDealer.backgroundImgContainer);
			if(node){
				node.scrollIntoView();
				node.fireEvent("onclick");
			}
			//LoginTabDealer.backgroundImgContainer.disabled = $('randomBackground').checked;
			LoginTabDealer.disabledBackgroundSelected = $('randomBackground').checked;
			LoginTabDealer.backgroundImgContainer.setAttribute("disabled", $('randomBackground').checked);
			$('fileNameControl').style.display=  $('randomBackground').checked ? "none" : "";			
		});
	},

	initLoginTab : function(){//初始化登陆页
		this.backgroundImgContainer = $('backgroundImgContainer');
		this.initLoginTabEvent();
		this.initLoginTabValue();
	}
};

function getFirstHTMLChild(node){//得到当前节点的第一个HTML孩子节点
	if(node == null)return null;
	var tempNode = null;
	for (var i = 0; i < node.childNodes.length; i++){
		if(node.childNodes[i].nodeType == 1){
			tempNode = node.childNodes[i];
			break;
		}
	}
	return tempNode;
}

//点击个性化设置页面的"登陆"节点时，触发的动作
/*
function loginTabLoad(){
	if(!LoginTabDealer.isLoaded){
		LoginTabDealer.initLoginTab();
		LoginTabDealer.isLoaded = true;
	}
}
*/


Event.observe(window, 'load', LoginTabDealer.initLoginTab.bind(LoginTabDealer));
