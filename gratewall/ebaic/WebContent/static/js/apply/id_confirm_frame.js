define(['require','jquery','common'], function(require, $, common){
	var confirm = {
		_init : function(){
			var $_this = this;
			require(['domReady'], function (domReady) {
				domReady(function () {
					$_this.getPersonList();
					$("#check").on("click",confirm.getChecked);
				});
			});
		},
		/*获取为提交和未通过的自然人股东*/
		getChecked:function(){
			if(this.checked==true){
				$("#name").css("color","#8cc0e7");
				$("#cp").children(".tabbox").children(".tabcardgreen").css("display","none");
				$("#ncp").children(".tabbox").children(".tabcardgreen").css("display","none");
				$("#representative").children(".tabbox").children(".tabcardgreen").css("display","none");
			}else{
				$("#name").css("color","#666");
				$("#cp").children(".tabbox").children(".tabcardgreen").css("display","inline-block");
				$("#ncp").children(".tabbox").children(".tabcardgreen").css("display","inline-block");
				$("#representative").children(".tabbox").children(".tabcardgreen").css("display","inline-block");
			}
		},
		/*获取自然人股东列表*/
		getPersonList:function(){
			var gid=jazz.util.getParameter('gid')||'';
			var url="../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewIdentity&ui=data&gid="+gid;
			$("#invPersonGridPanel").gridpanel("option","datarender",confirm.invPersonRender);
			$("#invPersonGridPanel").gridpanel("option","dataurl",url);
			$("#invtabGridPanel").gridpanel("option","datarender",confirm.invPersonRender);
			$("#invtabGridPanel").gridpanel("option","dataurl",url);
			$("#invPersonGridPanel").gridpanel("reload");
		},
		invPersonRender: function(item,data){
    		var gid=jazz.util.getParameter('gid')||'';
    		var data=data.data;
    		var person=[];
    		var nonperson=[];
    		var representative=[];
    		var state=[];
    		var len=data.length;
    		for(var i=0;i<len;i++){
    			if(data[i].c=="自然人股东"){
    				person.push(data[i]);
    			}else if(data[i].c=="非自然人股东"){
    				nonperson.push(data[i]);
    			}else{
    				representative.push(data[i]);
    			}   			
    		}
    		if(person.length==0||person==null){
    			$(".person").css("display","none");
    		}
    		if(nonperson.length==0||nonperson==null){
    			$(".nonperson").css("display","none");
    		}
    		if(representative.length==0||representative==null){
    			var html='<div class="tabcarduncertain">'+
			     				'<span class="uncertain">未设定</span>'+
			     			'</div>';
    		}
    		$("#representative").append(html);
    		$("#cp").renderTemplate({templateName:"tab_card",insertType:"prepend"},{"rows":person,"gid":gid});
    		$("#ncp").renderTemplate({templateName:"tab_card",insertType:"prepend"},{"rows":nonperson,"gid":gid});
    		$("#representative").renderTemplate({templateName:"tab_card",insertType:"prepend"},{"rows":representative,"gid":gid});
    	}
	};
	confirm._init();
	return confirm;
});


