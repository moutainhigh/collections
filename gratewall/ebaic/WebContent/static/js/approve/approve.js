define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon, util){
	
	/**
	 * 查询当前业务需要显示哪些页签
	 */
	function getTabNameList(){
//		var url = '../../../approve/process/getTabNames.do' ;
//		var gid = jazz.util.getParameter("gid");	
//		var params = { 'gid':gid };
//		var success = function(resultData){
//			if(resultData.exceptionMes){
//				jazz.error(resultData.exceptionMes);
//			}else{
//				initTabs(resultData);// 显示页签
//			}
//		};
//		var error = function(response){
//			history.go(-1);
//		}
//		util.ajax(url, params, success, error);
		
		var gid = jazz.util.getParameter("gid");
		$.ajax({
			url:'../../../approve/process/getTabNames.do',
			type : "post",
			async:false,
			data:{
				'gid':gid
			},
			dataType : "json",
			success : function(resultData) {
				// 显示页签
				if(resultData.exceptionMes){
					jazz.error(resultData.exceptionMes);
				}else{
					initTabs(resultData);
				}
			},
			error :  function(responseObj) {
			    if(responseObj["responseText"]){
			    	var err = jazz.stringToJson(responseObj["responseText"]);
			    	if(err['exceptionMes']){
			    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes'],function(){
			    			history.go(-1);
			    		});				    					    		
			    	}else{
			    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
			    	}
			    }else{
			    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
			    }
			    
			    return false;
			}
		});
	}
	/**
	 * 显示页签
	 */
	function initTabs(tabNameStr){
		var tabNames = tabNameStr.split(",");
		$("div[name='tabCount']").hiddenfield("setValue", tabNames.length);
		var tabLabels = {
//			'censor_identity':'身份认证',
			'identity_check':'身份认证',
			'approve_main':'审核内容',
			'processAllInfo':'全部内容',
			'approve_doc':'申请文书'
		};
		if(!tabNames){
			return ;
		}
		if(!$.isArray(tabNames)){
			return ;
		}
		
		var tabTitleHtml = "<ul>";
		var tabContentHtml = "";
		for(var i=0, len=tabNames.length;i<len;++i ){
			var tabName = tabNames[i];
			tabTitleHtml += "<li name='"+tabName+"'>"+tabLabels[tabName]+"</li>";
		}
		tabTitleHtml += "</ul>" ;
		$(".tabTitle").html(tabTitleHtml);
        
		var oLi = $(".tab").find("li");

        for (var j = 0; j < oLi.length; j++) {
            oLi[j].index = j;
            oLi[j].onclick = function() {
                showTab(this.index,'click');
            }
       }
	}
	
	function showTab(selectedTabIdx,type){
        var index = selectedTabIdx;
        // 切换tab标题显示
        var oLi = $(".tab").find("li");
        for (var j = 0; j < oLi.length; j++) {
            oLi[j].className = "";
        }
        if(oLi[index]){
        	oLi[index].className = "currentTab";
        	var url = oLi[index].getAttribute('name') + ".html?gid="+jazz.util.getParameter("gid");
            $("#frametabcontent").attr("src", url);
        }
        if(type=='click'){
        	$("div[name='curIndex']").hiddenfield("setValue", selectedTabIdx);
        	var tabCount = $("div[name='tabCount']").hiddenfield('getValue');
        	if(selectedTabIdx==0){
        		$("div[name='preStep']").hide();
        		$("div[name='nextStep']").show();
        	}
        	if(selectedTabIdx==(parseInt(tabCount)-1)){
        		$("div[name='nextStep']").hide();
        		$("div[name='preStep']").show();
        	}
        }
    }
    function preStep(){
    	var tabCount = $("div[name='tabCount']").hiddenfield('getValue');
    	var curIndex = $("div[name='curIndex']").hiddenfield('getValue');
    	curIndex = parseInt(curIndex)-1;
    	if(curIndex>=0){
    		$("div[name='curIndex']").hiddenfield("setValue",curIndex);
    		showTab(curIndex,'click');
    	}
    }
    function nextStep(){
    	var tabCount = $("div[name='tabCount']").hiddenfield('getValue');
    	var curIndex = $("div[name='curIndex']").hiddenfield('getValue');
    	if(!curIndex){
    		curIndex=0;
    	}
    	curIndex = parseInt(curIndex)+1;
    	$("div[name='curIndex']").hiddenfield("setValue", curIndex);
    	showTab(curIndex,'click');
    }
    function returnBack(){
    	window.location.href="../../../page/approve/mytask/mytask_list.html";
    }
    var module = {
    	_init : function (){
    		 require(['domReady'], function (domReady) {
   			  domReady(function () {
   				getTabNameList();
   	    		$("#frameExampleApprovecontent").attr("src", "examineOpinion.html?gid="+jazz.util.getParameter("gid"));
   	    		//绑定事件
   				$("div[name='preStep']").off('click').click(preStep);
   				$("div[name='nextStep']").off('click').click(nextStep);
   				$("div[name='returnButton']").off('click').click(returnBack);
   				$("div[name='preStep']").hide();
        		$("div[name='nextStep']").show();
   	    		showTab(0,'init');
   	    		
   	    		// 打开股东出资详情对话框
	    		window.openbConAmDetailWindow = function(investorId){
	    			var url = "processSubConAmDetail.html?investorId="+investorId;
	    			util.openWindow("conAmDetailW", "股东出资明细表", url , "650" , "350",false);
	    		};
	    		
	    		
   			  });
   			});
    	}
    };
    module._init();
    return module;
});