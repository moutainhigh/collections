define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
					    	$("#btnSave").click(function(event){
					    		$_this.Save();
					    	});
					    	$("#btnReSet").click(function(event){
					    		$_this.ReSet();
					    	});
					    	$("#btnClose").click(function(event){
					    		$('.jazz-ie6-position-fixed', window.parent.document).css("display","none");
					    		window.parent.document.getElementById("winContent").style.display="none";
					    	});
					    });
				});
	    	},
	    	
	    	Save : function(){
	    		//var domList = document.getElementsByTagName("input");
	    		var domList = $(".add");
	    		var str ="";
	    		var roleCode = jazz.util.getParameter("roleCode");
	    		
	    		for(var i=0;i<domList.length && domList[i];i++){
	    			if(i!=(domList.length-1)){
	    				str += domList[i].value+",";     
	    			}else{
	    				str += domList[i].value;
	    			}
	    		}
	    	
	    		$.ajax({
    				url : "../../otherselect/SaveUserInfo.do",
    				data :{
    					userList : str,
    					roleCode : roleCode
    				},
    				async : false,
    				type : 'post',
    				dataType : 'text',
    				//contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    				success : function(data) {
    					if("1"==data){
    						alert("添加成功！");
							window.parent.document.getElementById("winContent").style.display="none";
							window.parent.location.reload();
    					}else{
    						alert("添加失败！");
    					}
    				},
    				error : function() {
    					alert("保存数据失败");
    				}
    			});
	    	},
	    	
	    	ReSet : function(){
	    		$(".add").val("");
	    	},
	    	getContextPath: function (){
	    		var pathName = document.location.pathname;//  /page/mgr/torch/sysconfig/functionList.html
	    		var index = pathName.substr(1).indexOf("/");//  4
	    		var result = pathName.substr(0,index+1);//    /page
//	    		alert(pathName);
	    		return result;
	    	}
	};
torch._init();
return torch;
});
