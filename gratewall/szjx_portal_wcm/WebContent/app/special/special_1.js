// 处理上传到临时目录的图片，在 upload_dowith 页面中使用
function dealWithUploadedPicFile(_saveFileHttpPath, _saveFileName){
	if(_saveFileHttpPath.indexOf("upload")<0){
		Ext.Msg.alert("上传文件失败");
	}
	if(!_saveFileHttpPath&&_saveFileHttpPath=="")
		return;
	$("img_ViewThumb").src = "./../../file/read_image.jsp?FileName=" + _saveFileName;
	$("ViewThumb").value = _saveFileName;
}
Event.observe(window, 'load',function(){
	var aMasters = document.getElementsByName("master");
	if(aMasters){
		for(var i = 0; i<aMasters.length; i++){
		Event.observe(aMasters[i], 'click', function(event){
				Event.stop(event);
				selectMaster(this);
			}.bind(aMasters[i]));
		}
	}	
});

var MasterConfig = [
{
	masterType : "1",
	valueId : 'indexMasterId',
	textId : 'indexMasterName'
},
{
	masterType : "1",
	valueId : 'outlineMasterId',
	textId : 'outlineMasterName'
},
{
	masterType : "2",
	valueId : 'detailMasterId',
	textId : 'detailMasterName'
}
];

function selectMaster(dom){	
	var index = dom.getAttribute("index");
	var config = MasterConfig[index];
	var title = (index == 0)? "选择首页基准母板" : ((index == 1) ? "选择分类首页母板" : "选择文档页面母板");
	var sMasterName = "";
	var nLen = 0;
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){//判断ie
		sMasterName = ($(config["textId"]).outerText.trim() ==(wcm.LANG.WEBSITE_NONE||"无"))?"":$(config["textId"]).outerText;
	}else{
		sMasterName = ($(config["textId"]).innerHTML.trim()==(wcm.LANG.WEBSITE_NONE||"无"))?"":$(config["textId"]).innerHTML;
		sMasterName = sMasterName.trim();
	}
	wcm.MasterSelector.selectMaster(
		{
			masterType : config["masterType"],
			checkedMId : ($(config["valueId"]).value.trim() == "0" ? "" : $(config["valueId"]).value.trim()),
			checkedMName : sMasterName
		},
		function(_args){
			var checkedMId = (_args.selectedId.trim() == "") ? "0" : _args.selectedId;
			var checkedMName = (_args.selectedName.trim() == "") ? "无" : _args.selectedName;			
			$(config["valueId"]).value = checkedMId;
			Element.update(config["textId"], checkedMName);
		},
		title
	);
}

// 将缩略图还原为默认状态
function resumeThumb(){
	if($("ViewThumb").value == ""){
		Ext.Msg.alert("未发现上传的缩略图！");
		return;
	}
	Ext.Msg.confirm('您确定要清除此专题缩略图吗？', {
		yes : function(){
			$("img_ViewThumb").src = "images/zt_wt.gif";
			$("ViewThumb").value = "";
		}
	});
}

function save(){
	if($("SpecialName").value.trim() == ""){
		Ext.Msg.alert("请输入专题名称！",function(){
			$("SpecialName").focus();
		});
		return;
	}
	
	if($("SpecialDesc").value.length > 600){
		Ext.Msg.alert("专题描述超长！",function(){
			$("SpecialDesc").select();
		});
		return;
	}
	var indexMaster = $("indexMasterId").value.trim();
	var outlineMaster = $("outlineMasterId").value.trim();
	var detailMaster = $("detailMasterId").value.trim();
	if($("indexMasterId").value.trim() == "0"){
		Ext.Msg.alert("请选择首页基准母板！");
		return;
	}else{
		//首页基准母板、分类首页母板和文档页面母板Id组装成专题母板Id的字符串
		$("MasterIds").value = indexMaster+","+outlineMaster+","+detailMaster;
	}
	if($("styleName").value == "-1"){
		Ext.Msg.alert("请选择专题风格！");
		return;
	}

	wcmXCom.get('ParambtnSave').disable();
	BasicDataHelper.call("wcm61_special",'save',$('addEditForm'),true,function(_trans,_json){
		location.href = "special_2.jsp?ObjectId=" + $v(_json,"RESULT") + "&specialName=" + encodeURIComponent($("SpecialName").value) + "&isNew=" + $("isNew").value;
		wcm.CrashBoarder.get(window).getCrashBoard().setSize("720px","360px");
	}, function(_trans, _json){
		Ext.Msg.alert($transHtml($v(_json,"FAULT.MESSAGE")));
	});	
}

function checkSpecialName(){
	var oPostData = {
		"SpecialName" : $("SpecialName").value,
		"ObjectId" : $("ObjectId").value
	};
	BasicDataHelper.call("wcm61_special",'existsSimilarName',oPostData,true,function(_trans,_json){
		if(com.trs.util.JSON.value(_json, "RESULT") == 'true'){
			ValidationHelper.failureRPCCallBack(String.format("已经存在名称为【{0}】的专题！",$("SpecialName").value))
        }else{
            ValidationHelper.successRPCCallBack();
        }
	});	
}

function init(){
	//注册校验成功时执行的回调函数
	ValidationHelper.addValidListener(function(){
		//按钮有效处理
		wcmXCom.get('ParambtnSave').enable();
	}, "addEditForm");

	//注册校验失败时执行的回调函数
	ValidationHelper.addInvalidListener(function(){
		//按钮失效处理
		wcmXCom.get('ParambtnSave').disable();
	}, "addEditForm");

	//初始化页面中需要校验的元素
	ValidationHelper.initValidation();
}