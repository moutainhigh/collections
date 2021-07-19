define(['require', 'jquery', 'entCommon'], function(require, $, entCommon){
	var torch = {
		
		edit_primaryValues : function(){
			return "&entId=" + jazz.util.getParameter('entId');
		},
		edit_fromNames : [ 'entSndAdd_Form'],
		/**
		 *保存
		 */
		cp_rs_entsndAddSave : function(){
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
			
			//保存
			var params={		 
				 url:"../../../torch/service.do?fid=cp_rs_entsndAdd"+torch.edit_primaryValues(),
				 components: torch.edit_fromNames,
				 callback: function(jsonData,param,res){
					 torch.back_button();//返回主页
					 jazz.info("保存成功");	
				 }
			};
			$.DataAdapter.submit(params);
		},
		/**
		 * 重置
		 */
		cp_rs_entsndAddReset : function(){
			for( x in torch.edit_fromNames){
				$("div[name='"+torch.edit_fromNames[x]+"']").formpanel("reset");
			} 
		},
		/**
		 * 返回企业登录主页
		 */
		back_button:function(){
			window.location.href = "../../../page/apply/ent_account/home.html";
		},
		/**
		 * 党组织 团组织 工会组织，点击‘否’时，隐藏对应人数框
		 */
		radioSelectInit:function(){
			//1.党组织选否时，隐藏对应人数框
	    	$("div[name='hasParty']").radiofield("option", "itemselect", function(event, obj){  
	    		if(obj.value==2){//选否时
	    			$("div[name='partyNumber']").textfield("setValue",0);
	    			$("div[name='partyNumber']").textfield("option","rule","");
//	    			$("div[name='partyNumber']").textfield("option","disabled",true);
	    			$("div[name='partyNumber']").hide();
	    		}else{
	    			$("div[name='partyNumber']").textfield("option","rule","must");
//	    			$("div[name='partyNumber']").textfield("option","disabled",false);
	    			$("div[name='partyNumber']").show();
	    		}
	    	});
	    	//2.团组织选否时，隐藏对应人数框
	    	$("div[name='hasLeague']").radiofield("option", "itemselect", function(event, obj){  
	    		if(obj.value==2){//选否时
	    			$("div[name='leagueNumber']").textfield("setValue",0);
	    			$("div[name='leagueNumber']").textfield("option","rule","");
//	    			$("div[name='partyNumber']").textfield("option","disabled",true);
	    			$("div[name='leagueNumber']").hide();
	    		}else{
	    			$("div[name='leagueNumber']").textfield("option","rule","must");
//	    			$("div[name='partyNumber']").textfield("option","disabled",false);
	    			$("div[name='leagueNumber']").show();
	    		}
	    	});
	    	//3.公会组织选否时，隐藏对应人数框
	    	$("div[name='hasUnion']").radiofield("option", "itemselect", function(event, obj){  
	    		if(obj.value==2){//选否时
	    			$("div[name='unionNumber']").textfield("setValue",0);
	    			$("div[name='unionNumber']").textfield("option","rule","");
//	    			$("div[name='partyNumber']").textfield("option","disabled",true);
	    			$("div[name='unionNumber']").hide();
	    		}else{
	    			$("div[name='unionNumber']").textfield("option","rule","must");
//	    			$("div[name='partyNumber']").textfield("option","disabled",false);
	    			$("div[name='unionNumber']").show();
	    		}
	    	});
		},
		/**
		 * 初始化
		 */
		 _init : function(){
			 require(['domReady'], function (domReady) {
			    domReady(function () {
				 
					$("div[name='cp_rs_entsndAdd_save_button']").off('click').on('click',torch.cp_rs_entsndAddSave);
					$("div[name='cp_rs_entsndAdd_reset_button']").off('click').on('click',torch.cp_rs_entsndAddReset);
					$("div[name='cp_rs_entsndAdd_goback_button']").off('click').on('click',torch.back_button);//返回
					//党组织 团组织 工会组织，点击‘否’时，隐藏对应人数框
					torch.radioSelectInit();
				 });
			});
		 }
	};
	torch._init();
	return torch;
});
