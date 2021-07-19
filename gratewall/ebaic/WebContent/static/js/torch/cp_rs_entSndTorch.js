define(['require', 'jquery', 'entCommon'], function(require, $, entCommon){
var torch = {
		/**
		 * 初始化
		 */
		 _init : function(){
		 require(['domReady'], function (domReady) {
		    domReady(function () {
		    	//回车提交表单相对应的信息
		    	$(document).keypress(function(e) {
					if (e.which == 13) {
						//回车查询
						torch.cp_rs_entsndSave();
					}
				});
				torch.cp_rs_entsndQuery();
				
				$("div[name='cp_rs_entsnd_goback_button']").off('click').on('click',torch.back_button);//返回
				$("div[name='cp_rs_entsnd_save_button']").off('click').on('click',torch.cp_rs_entsndSave);
//				$("div[name='cp_rs_entsnd_goback_button']").off('click').on('click',torch.cp_rs_entsndReset);
				//党组织 团组织 工会组织，点击‘否’时，隐藏对应人数框
				
				//外地人数div获取焦点触发自动计算人数方法
				$("div[name = 'othercityNumber']").off('click').on('click',torch.countOtherCityNumbers);
				
				$("div[name = 'resParSecSign']").radiofield('setValue','2');
				$("div[name = 'hasParty']").radiofield('setValue','2');
				$("div[name = 'hasLeague']").radiofield('setValue','2');
				$("div[name = 'hasUnion']").radiofield('setValue','2');
				$("div[name = 'resParMSign']").radiofield('setValue','2');
				$("div[name = 'anOrgParSign']").radiofield('setValue','2');
				
				torch.radioSelectInit();
			 });
			});
		 $("div[name='entSnd_Form']").css("margin","0 auto");
		 
		 },
	
	edit_primaryValues : function(){
		
	    return "&entId=" + jazz.util.getParameter('entId');
	},
	edit_fromNames : [ 'entSnd_Form'],
	/**
	 * 表单回显
	 */
	cp_rs_entsndQuery : function(){
		var updateKey= "&wid=cp_rs_entsnd";
		$.ajax({
				url:'../../../torch/service.do?fid=cp_rs_entsnd'+updateKey+torch.edit_primaryValues(),
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var jsonData = data.data;
					
					if($.isArray(jsonData)){
						 for(var i = 0, len = jsonData.length; i<len; i++){
							 $("div[name='"+jsonData[i].name+"']").formpanel("setValue",jsonData[i] || {});
						 }
					 }
				}
			});
	},
	/**
	 * 保存
	 */
	cp_rs_entsndSave : function(){
		//校验
	    var members = $("div[name='members']").textfield("getValue");
		var incityNumber = $("div[name='incityNumber']").textfield("getValue");
		var othercityNumber = $("div[name='othercityNumber']").textfield("getValue");
		var womenNumber = $("div[name='womenNumber']").textfield("getValue");
		var laidoffNumber = $("div[name='laidoffNumber']").textfield("getValue");
		var partyNumber = $("div[name='partyNumber']").textfield("getValue");
		var leagueNumber = $("div[name='leagueNumber']").textfield("getValue");
		var unionNumber = $("div[name='unionNumber']").textfield("getValue");
		
		if(!members){
			jazz.error("从业人数不能为空。");
			return;
		}
		if(!incityNumber){
			jazz.error("本市人数不能为空。");
			return;
		}
		if(!othercityNumber){
			jazz.error("外地人数不能为空。");
			return;
		}
		if((parseInt(incityNumber)+parseInt(othercityNumber))!=parseInt(members)){
			jazz.warn("本市人数和外地人数之和与从业人数不一致。");
			return;
		}
		if(!womenNumber){
			jazz.error("女性人数不能为空。");
			return;
		}
		if(!laidoffNumber){
			jazz.error("安置下岗失业人数。");
			return;
		}
		if(!partyNumber){
			jazz.error("党员（预备党员）人数不能为空。");
			return;
		}
		if(!leagueNumber){
			jazz.error("团员人数不能为空。");
			return;
		}
		if(!unionNumber){
			jazz.error("工会会员人数不能为空。");
			return;
		}
		
		jazz.confirm("是否保存？",function(){
			var params={		 
					 url:"../../../torch/service.do?fid=cp_rs_entsnd"+torch.edit_primaryValues(),
					 components: torch.edit_fromNames,
					 callback: function(jsonData,param,res){
							 //torch.back_button();//返回主页
							 jazz.info("保存成功");
					 }
				}
			$.DataAdapter.submit(params);
		});
	},
	cp_rs_entsndReset : function(){
			/*for( x in torch.edit_fromNames){
				$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
			} */
			},
	/**
	 * 返回企业登录主页
	 */
	back_button:function(){
		window.location.href = "../../../page/apply/ent_account/home.html";
	},
	/**
	 * 1.回显时，初始化radio单选框状态，‘否’时，隐藏对应人数框
	 * 2.党组织 团组织 工会组织，点击‘否’时，隐藏对应人数框
	 */
	radioSelectInit:function(){
		//1.党组织选否时，隐藏对应人数框
		if($("div[name='hasParty']").radiofield("getValue")==2){
			$("div[name='partyNumber']").textfield("setValue",0);
			$("div[name='partyNumber']").textfield("option","rule","");
//			$("div[name='partyNumber']").textfield("option","disabled",true);
			$("div[name='partyNumber']").hide();
		}
    	$("div[name='hasParty']").radiofield("option", "itemselect", function(event, obj){  
    		if(obj.value==2){//选否时
    			$("div[name='partyNumber']").textfield("setValue",0);
    			$("div[name='partyNumber']").textfield("option","rule","");
//    			$("div[name='partyNumber']").textfield("option","disabled",true);
    			$("div[name='partyNumber']").hide();
    		}else{
    			$("div[name='partyNumber']").textfield("option","rule","must");
//    			$("div[name='partyNumber']").textfield("option","disabled",false);
    			$("div[name='partyNumber']").show();
    		}
    	});
    	//2.团组织选否时，隐藏对应人数框
    	if($("div[name='hasLeague']").radiofield("getValue")==2){
    		$("div[name='leagueNumber']").textfield("setValue",0);
			$("div[name='leagueNumber']").textfield("option","rule","");
//			$("div[name='partyNumber']").textfield("option","disabled",true);
			$("div[name='leagueNumber']").hide();
		}
    	$("div[name='hasLeague']").radiofield("option", "itemselect", function(event, obj){  
    		if(obj.value==2){//选否时
    			$("div[name='leagueNumber']").textfield("setValue",0);
    			$("div[name='leagueNumber']").textfield("option","rule","");
//    			$("div[name='partyNumber']").textfield("option","disabled",true);
    			$("div[name='leagueNumber']").hide();
    		}else{
    			$("div[name='leagueNumber']").textfield("option","rule","must");
//    			$("div[name='partyNumber']").textfield("option","disabled",false);
    			$("div[name='leagueNumber']").show();
    		}
    	});
    	//3.公会组织选否时，隐藏对应人数框
    	if($("div[name='hasUnion']").radiofield("getValue")==2){
    		$("div[name='unionNumber']").textfield("setValue",0);
			$("div[name='unionNumber']").textfield("option","rule","");
//			$("div[name='partyNumber']").textfield("option","disabled",true);
			$("div[name='unionNumber']").hide();
		}
    	$("div[name='hasUnion']").radiofield("option", "itemselect", function(event, obj){  
    		if(obj.value==2){//选否时
    			$("div[name='unionNumber']").textfield("setValue",0);
    			$("div[name='unionNumber']").textfield("option","rule","");
//    			$("div[name='partyNumber']").textfield("option","disabled",true);
    			$("div[name='unionNumber']").hide();
    		}else{
    			$("div[name='unionNumber']").textfield("option","rule","must");
//    			$("div[name='partyNumber']").textfield("option","disabled",false);
    			$("div[name='unionNumber']").show();
    		}
    	});
	},
	
	//根据从业人数和本市人数计算外地人数
	countOtherCityNumbers : function(){
		var members = $("div[name='members']").textfield("getValue");
		var incityNumber = $("div[name='incityNumber']").textfield("getValue");
		//根据从业人数和本市人数自动显示出外市人数
		if(members && incityNumber){
			$("div[name = 'othercityNumber']").textfield("setValue",(parseInt(members))-(parseInt(incityNumber)));
		}
	}
	
};
torch._init();
return torch;

});
