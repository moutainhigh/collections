define(['require','jquery', 'common','util','apply/setupApproveMsg'], function(require, $, common,util,approveMsg){
	/* 模块定义 */
	var mbr = {};
    mbr = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
				domReady(function () {
					// 处理主要人员主体信息模板
					$_this.renderTemplate();
					//查询董事、经理、监事的列表
					$_this.getAllMemberList();
					//查询是否设立董事会、监事会信息
					$_this.applySetupBeWkEntInfo();
					//选中主要人员信息页签
					$_this.reLocate();
					//绑定页面事件
					$_this.bindingClick();
					//处理页面高度
					common.computeHeight();	
					//处理分页标签
					mbr.TitlePage();
					//加载备选法定代表人
					mbr.getLegalDelegatedData();
					//回显法定代表人
					//mbr.reDeployLegalDelegate();
					//初始化时显示法定代表人提示信息
					mbr.changeFddbrNotice();
					//导出本模块函数
					util.exports('getLegalDelegatedData',$_this.getLegalDelegatedData);
					util.exports('getDirectorsList',$_this.getDirectorsList);
					util.exports('getSupervisorsList',$_this.getSupervisorsList);
					util.exports('getManagersList',$_this.getManagersList);
					$("img[name='back']").on('click',mbr.hideAdvice);//隐藏退回修改意见
					$(".modifyBack").on('click',mbr.showAdvice);//出现退回修改意见
				});
			
    		});
    	},
    	//点击出现退回修改意见
    	showAdvice:function(){
    		$(".advice").css("display","block");
    		$(".modifyBack").css("display","none");
    	},
    	//点击隐藏退回修改意见
    	hideAdvice:function(){
    		$(".advice").css("display",'none');
    		$(".modifyBack").css("display","block");
    	},
    	/**
    	 * 绑定页面事件
    	 */
    	bindingClick:function(){

    		//编辑卡片
    		$(".card").live("click",function(){
    			var entmemberId = $(this).attr("entmemberId") || '';
    			var positionType = $(this).attr("positionType") || '';
    			if(!entmemberId){
    				alert('未获取entmemberId。');
    				return ;
    			}
    			mbr._editMember(entmemberId,positionType);
			});
    		//新增卡片
    		$(".upload-card").live("click",function(){
    			var positionType = $(this).attr("positionType") || '';
    			if(!positionType){
    				alert('未获取职务类型参数。');
    				return ;
    			};
    			mbr._addMember(positionType);
			});
    		//删除
    		$("img[name='mbrDelBtn']").live("click",function(){
    			var entmemberId = $(this).parent().parent().attr("entmemberId") || '';
    			if(!entmemberId){
    				alert('未获取entmemberId。');
    				return ;
    			}
    			var positionType = $(this).parent().parent().attr("positionType") || '';
    			if(!positionType){
    				alert('未获取职务类型参数。');
    				return ;
    			};
    			mbr.deleteMbr(entmemberId, positionType);
    			return false;
			});
//    		//设为法定代表人
//    		$(".setup-legal").live("click",function(){
//    			var entmemberId = $(this).parent().parent().attr("entmemberId") || '';
//    			var positiontype= $(this).parent().parent().attr("positiontype") || '';
//    			if(!entmemberId){
//    				alert('未获取entmemberId。');
//    				return ;
//    			}
//    			if(!positiontype){
//    				alert('未获取positiontype。');
//    				return ;
//    			}
//    			mbr.setLegal(entmemberId,positiontype);
//    			return false;
//			});
    		//设为董事长
    		$(".setup-president").live("click",function(){
    			var entmemberId = $(this).parent().parent().attr("entmemberId") || '';
    			if(!entmemberId){
    				alert('未获取entmemberId。');
    				return ;
    			}
    			mbr.setPresident(entmemberId);	
    			mbr.getLegalDelegatedData();
    			return false;
			});
    		//设为监事会主席
    		$(".setup-chairman").live("click",function(){
    			var entmemberId = $(this).parent().parent().attr("entmemberId") || '';
    			if(!entmemberId){
    				alert('未获取entmemberId。');
    				return ;
    			}
    			mbr.setChairman(entmemberId);
    			return false;
			});
    		
    		$("#noSet").live("change",function(){
    			mbr.setSettingBoard();
    			
    		});
    		$("#yesSet").live("change",function(){
    			mbr.setSettingBoard();
    			
    		});
    		$("#supervisorNotSet").live("change",function(){
    			mbr.setSettingSusped();
    		});
			$("#supervisorYesSet").live("change",function(){
				mbr.setSettingSusped();
    		});
			
    		//下一步
    		$("#applySetupBasic_forward_button").on('click',mbr.saveOrNextStepMember);
    		//保存
    		$("#applySetupBasic_save_button").on('click',mbr.saveOrNextStepMember);
			//上一步
    		$("#applySetupBasic_back_button").on('click',mbr.backStep);
    		
    		$("#fddbr").comboxfield('option','change',mbr.changeLeRepStyle);
    	},
    	/**
    	 * 设置是否设置董事会
    	 */
    	setSettingBoard : function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		var isBoard = $("#yesSet").attr("checked")=="checked"? "1" : "0" ;
    		var params = {
    		        url : '../../../apply/setup/member/setSettingBoard.do',
    		        params:{ gid:gid, isBoard   : isBoard },
    		        callback : function(data, param, res) {
    		        	mbr.getDirectorsList();
    		        	mbr.getLegalDelegatedData();
    	    			mbr.changeFddbrNotice();
    		        },
    		        error : function(responseObj) {
    				    if(responseObj["responseText"]){
    				    	var err = jazz.stringToJson(responseObj["responseText"]);
    				    	if(err['exceptionMes']){
    				    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes'],function(){
    				    			//mbr.refreshList();
    				    		});				    					    		
    				    	}else{
    				    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    				    	}
    				    }else{
    				    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    				    }
    				    
    				    return false;
    				}
    		    };  	
    		 $.DataAdapter.submit(params);
    	},
    	/**
    	 * 设置是否设置监事会。
    	 */
    	setSettingSusped : function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		var isSusped = $("#supervisorYesSet").attr("checked")=="checked"? "1" : "0" ;
    		var params = {
    		        url : '../../../apply/setup/member/setSettingSusped.do',
    		        params:{ gid:gid, 	isSusped  : isSusped  },
    		        callback : function(data, param, res) {
    		        	mbr.getSupervisorsList();
    		        },
    		        error : function(responseObj) {
    				    if(responseObj["responseText"]){
    				    	var err = jazz.stringToJson(responseObj["responseText"]);
    				    	if(err['exceptionMes']){
    				    		jazz.info('<font color="blue" >错误信息</font> : ' + err['exceptionMes'],function(){
    				    			//mbr.refreshList();
    				    		});				    					    		
    				    	}else{
    				    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    				    	}
    				    }else{
    				    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
    				    }
    				    
    				    return false;
    				}
    		    };  	
    		 $.DataAdapter.submit(params);
    	},
    	/**
    	 * 删除主要人员。
    	 */
    	deleteMbr : function(entmemberId, positionType){
    		var gid = jazz.util.getParameter('gid')||'';
    		var obj = $("div[entmemberId='"+entmemberId+"'][positiontype='"+positionType+"']").find(".main2-font4");
    		var msg="确认删除该人员信息？";
    		if(obj && obj.length>0){
    			msg="该人员已被选为法定代表人，法定代表人信息也将被同时删除，确认删除该人员信息？";
    		}
    		
    		jazz.confirm(msg,function(){
    			var params = {
        		        url : '../../../apply/setup/member/delMbr.do',
        		        params:{
        		        	gid:gid,
        		        	positionType : positionType ,
        		        	entmemberId:entmemberId
        		        },
        		        async:false,
        		        callback : function(data, param, res) {
        		        	//mbr.refreshList();
        		        	if(positionType=='3'){
        		        		mbr.getSupervisorsList();
    						}else if(positionType=='1'){
    							mbr.getDirectorsList();
    							mbr.getLegalDelegatedData(obj);
    						}else if(positionType=='2'){
    							mbr.getManagersList();
    							mbr.getLegalDelegatedData(obj);
    							//经理删除之后，初始化董事‘是否兼任经理’为‘0’
    							//mbr.initBoardIsManager();
    						}
        		        	
        		        } 	
        		    };  	
        		$.DataAdapter.submit(params);
    		},function(){
    			
    		});
    	},
    	/**
    	 * 初始化董事‘是否兼任经理’字段为‘0’不兼任。
    	 */
    	initBoardIsManager:function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		var params = {
    		        url : '../../../apply/setup/member/initBoard.do',
    		        params:{
    		        	gid:gid,
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        } 	
    		    };  	
    		$.DataAdapter.submit(params);
    	},
    	/**
    	 * 设置董事长
    	 */
    	setPresident:function(entmemberId){
    		var gid = jazz.util.getParameter('gid')||'';
    		var setType = "P";
    		var params = {
    		        url : '../../../apply/setup/member/setPosition.do',
    		        params:{
    		        	gid:gid,
    		        	setType:setType,
    		        	entmemberId:entmemberId
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	//mbr.refreshList();
    		        	mbr.getDirectorsList();
						
    		        } 	
    		    };  	
    		$.DataAdapter.submit(params);
    	},
    	/**
    	 * 设置监事会主席
    	 */
    	setChairman:function(entmemberId){
    		var gid = jazz.util.getParameter('gid')||'';
    		var setType = "C";
    		var params = {
    		        url : '../../../apply/setup/member/setPosition.do',
    		        params:{
    		        	gid:gid,
    		        	setType:setType,
    		        	entmemberId:entmemberId
    		        },
    		        async:false,
    		        callback : function(data, param, res) {
    		        	//mbr.refreshList();
    		        	mbr.getSupervisorsList();
						
    		        } 	
    		    };  	
    		$.DataAdapter.submit(params);
    	},
    	
    	/**
    	 * 查询董事、经理、监事的列表
    	 */
    	getAllMemberList:function(){
    		mbr.getDirectorsList();
	    	mbr.getSupervisorsList();
	    	mbr.getManagersList();
	    	
    	},
    	/**
    	 * 查询董事
    	 */
    	getDirectorsList:function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		//董事
    		var direUrl = '../../../torch/service.do?m=data&fid=applySetupMbrAllList&wid=applySetupMbrDList&gid='+gid;
    		$("#directorsGridPanel").gridpanel("option",'datarender',this.direRender);
	    	$("#directorsGridPanel").gridpanel("option",'dataurl',direUrl);
	    	$("#directorsGridPanel").gridpanel("reload");
    	},
    	/**
    	 * 查询经理列表
    	 */
    	getManagersList:function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		
    		//经理
	    	var manaUrl = '../../../torch/service.do?m=data&fid=applySetupMbrAllList&wid=applySetupMbrMList&gid='+gid;
	    	$("#managersGridPanel").gridpanel("option",'datarender',this.managerRender);
	    	$("#managersGridPanel").gridpanel("option",'dataurl',manaUrl);
	    	$("#managersGridPanel").gridpanel("reload");
    	},
    	/**
    	 * 查询监事列表
    	 */
    	getSupervisorsList:function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		//监事
	    	var superUrl = '../../../torch/service.do?m=data&fid=applySetupMbrAllList&wid=applySetupMbrSList&gid='+gid;
	    	$("#supervisorsGridPanel").gridpanel("option",'datarender',this.superRender);
	    	$("#supervisorsGridPanel").gridpanel("option",'dataurl',superUrl);
	    	$("#supervisorsGridPanel").gridpanel("reload");
    	},
    	/**
    	 * 董事(mbrFlag=1)、经理(mbrFlag=2)、监事(mbrFlag=3)共同的datarender
    	 * @param item
    	 * @param diredata
    	 * @returns
    	 * 编辑链接 ： http://localhost:8080/page/apply/setup/member_edit.html?entmemberId=c69bfda127604d8e9d8e2b12f2c3622e
    	 * 新增链接 ：/page/apply/setup/member_add.html?gid=4481c646f22a4cdcbfc70d13c2e7a460&mbrFlag=1
    	 */
    	direRender:function(item,diredata){
    		$('.directors').empty();
			$("#executive").renderTemplate({templateName:"mbr_card",insertType:"after"},{rows:diredata.data,positionType:"1"}); 
			mbr.updateAddBtnShowDirector();
    	},
    	updateAddBtnShowDirector : function(){
    		//决定是否显示添加卡片
			var isBoard = $("#yesSet").attr("checked")=="checked"? "1" : "0" ;
    		// 董事人数
    		var directorCnt = $(".directors").find(".card").length ;
    		if(isBoard=="1"){
    			if(directorCnt<13){
    				// 显示加号
    				$(".directors").find(".upload-card").show();
    			}else{
    				// 隐藏加号
    				$(".directors").find(".upload-card").hide();
    			}
    		}else{
    			if(directorCnt<1){
    				// 显示加号
    				$(".directors").find(".upload-card").show();
    			}else{
    				// 隐藏加号
    				$(".directors").find(".upload-card").hide();
    			}
    		}
    	},
    	managerRender:function(item,diredata){
    		$('.managers').empty();
				$(".title").eq(1).renderTemplate({templateName:"mbr_card",insertType:"after"},{rows:diredata.data,positionType:"2"}); 
				mbr.updateAddBtnShowManager();
    	},
    	updateAddBtnShowManager : function(){
    		var cnt = $(".managers").find(".card").length ;
    		if(cnt>0){
				// 显示加号
				$(".managers").find(".upload-card").hide();
    		}else{
				// 显示加号
				$(".managers").find(".upload-card").show();
    		}
		},
		superRender:function(item,diredata){
			$('.conductors').empty();
			$(".radio").eq(1).renderTemplate({templateName:"mbr_card",insertType:"after"},{rows:diredata.data,positionType:"3"}); 
			mbr.updateAddBtnShowSusp();
		},
		updateAddBtnShowSusp : function(){
			//决定是否显示添加卡片
    		var isSusped = $("#supervisorYesSet").attr("checked")=="checked"? "1" : "0" ;
    		
    		// 监事人数
    		var suspCnt = $(".conductors").find(".card").length ;
    		if(isSusped=="1"){
				// 显示加号
				$(".conductors").find(".upload-card").show();
				$('.setup-chairman').show();
    		}else{
    			if(suspCnt<2){
    				// 显示加号
    				$(".conductors").find(".upload-card").show();
    			}else{
    				// 隐藏加号
    				$(".conductors").find(".upload-card").hide();
    			}
    			$('.setup-chairman').hide();
    		}
		},
		removeAllMemberList:function(){
			$(".card").remove();
			$(".upload-card").remove();
		},
		/**
		 * 编辑人员弹出框
		 */
		_editMember:function(entmemberId,positionType){
			var gid = jazz.util.getParameter('gid')||'';
			if(!entmemberId){
				alert("人员编号不能为空。");
				return ;
			}
			var isSupedValue = "";
			if(positionType=='3'){
				isSupedValue  = $("input[name='supervisor']:checked").val()||"";
	    		if(isSupedValue==""){
	    			jazz.info("请选择是否设立监事会");
	    			return;
	    		}
			}
			util.openWindow('cover','主要人员信息',"member_edit.html?entmemberId="+entmemberId + '&mbrFlag='+positionType+"&gid="+gid+"&isSuped="+isSupedValue,950,538);
	    	/*win = jazz.widget({
	    		vtype: 'window',
	    		name: 'cover',
	        	title: '主要人员信息',
	        	titlealign: 'left',
	        	titledisplay: true,
	            width: 950,
	            height: 518,
	            visible: true,
	            modal:true,
	            frameurl:"member_edit.html?entmemberId="+entmemberId + '&mbrFlag='+positionType+"&gid="+gid,
	            customtitlebutton:[{
	  		  	  id: "id_1",
	  		  	  align: "right",
	  		  	  icon: "../../../static/image/img/icon/test2.png",
	  		  	  click:function(e,ui){
	  		  		  mbr.refreshList();
	  		  		  win.window('close');
	  		  	  }
	            }]
	    	});*/
		},
		/**
		 * 新增主要人员
		 * @param mbrFlag
		 */
		_addMember:function(mbrFlag){
			var isBoardValue ="";
			if(mbrFlag=='1'){
				isBoardValue  = $("input[name='executive']:checked").val()||"";
	    		if(isBoardValue==""){
	    			jazz.info("请选择是否设立董事会");
	    			return;
	    		}
			}
			var isSupedValue = "";
			if(mbrFlag=='3'){
				isSupedValue  = $("input[name='supervisor']:checked").val()||"";
	    		if(isSupedValue==""){
	    			jazz.info("请选择是否设立监事会");
	    			return;
	    		}
			}
			var gid = jazz.util.getParameter('gid')||'';
			util.openWindow('addMbrWin','主要人员信息',"member_add.html?gid="+gid+"&mbrFlag="+mbrFlag+"&isBoard="+isBoardValue+"&isSuped="+isSupedValue,950,553);
			/*win = jazz.widget({
	    		vtype: 'window',
	    		name: 'addMbrWin',
	        	title: '主要人员信息维护',
	        	titlealign: 'left',
	        	titledisplay: true,
	            width: 950,
	            height: 518,
	            visible: true,
	            modal:true,
	            frameurl:"member_add.html?gid="+gid+"&mbrFlag="+mbrFlag+"&isBoard="+isBoardValue+"&isSuped="+isSupedValue,
	            customtitlebutton:[{
		  		  	  id: "id_1",
		  		  	  align: "right",
		  		  	  icon: "../../../static/image/img/icon/test2.png",
		  		  	  click:function(e,ui){
		  		  		  mbr.refreshList();
		  		  		  win.window('close');
		  		  	  }
		  		}]
		    });*/
		},
    	/**
    	 * 上一页跳转
    	 */
    	backStep:function(){
    		window.location.href="../../../page/apply/setup/inv.html?gid="+jazz.util.getParameter("gid");
    	},
    	
    	/**
    	 * 保存
    	 */
    	saveOrNextStepMember:function(e){
    		var gid = jazz.util.getParameter('gid')||'';
    		var isBoardValue  = $("input[name='executive']:checked").val()||"";
    		if(isBoardValue==""){
    			jazz.info("请选择是否设立董事会");
    			return;
    		}
    		var isSupedValue  = $("input[name='supervisor']:checked").val()||"";
    		if(isSupedValue==""){
    			jazz.info("请选择是否设立监事会");
    			return ;
    		}
    		var isNext='0';
    		var saveOrNext = $(e.target).attr("name");
			if(saveOrNext=='applySetupBasic_forward_button'){
				isNext='1';				
			}
			
			var entId = $("#fddbr").comboxfield("getValue");
		
			//var entMob = $("#mobile").textfield("getValue");
			if(entId == ""){
				jazz.error("未设定法定代表人!");
    			return ;
			}
			/*else if(entId != "" && entMob == ""){
				jazz.error("请为法定代表人填写移动号码!");
			}*/
			
    		var params = {
		        url : '../../../apply/setup/member/runRule.do?gid='+gid +'&entId='+entId,//+'&entMob='+entMob,
		        params:{
					isBoardValue:isBoardValue,
					isSupedValue:isSupedValue,
					//isNext:isNext
		        },
		        async:false,
		        callback : function(data, param, res) {
		        	/*var nextUrl = res.getAttr('nextUrl');
		        	
		        	if(nextUrl){
		        		nextUrl+=('?gid='+gid+'&isNext='+isNext);
		        		util.openWindow('tipPage', '提交业务确认', nextUrl , '710' , '380');
		        	}else{*/
		        		if(isNext=='1'){
		        			window.location.href="../../../page/apply/setup/contact.html?gid="+jazz.util.getParameter('gid');
		        		}else{
		        			jazz.info("保存成功",function(){
		        				mbr.getAllMemberList();
		        			});
		        		}
		        	//}
		        	
		        } 	
		    };  	
    		$.DataAdapter.submit(params);
    	},
    	/**
    	 * 改变法定代表人标识位置
    	 */
    	changeLeRepStyle:function(){
    		$(".main2-font4").remove();
    		var ids = $("#fddbr").comboxfield('getValue');
    		var entmemberId= ids.slice(0,ids.indexOf("_"));
    		var position = ids.slice(ids.indexOf("_")+1,ids.length);
    		var html = "<span class='main2-font4' title='法定代表人'>法定代表人</span>";
    		$("div[entmemberId='"+entmemberId+"'][position='"+position+"']").find(".name-info.omit").append(html);
    		
    		var entmemberName = $("#fddbr").comboxfield('getText');
    		//alert(entmemberName);
    		var start = entmemberName.indexOf("(");
    		if(start>=0){
    			var name =  entmemberName.slice(0,entmemberName.indexOf("("));
        		$("#fddbr").comboxfield('setText',name);
    		}
    		
    		var gid = jazz.util.getParameter('gid')||'';
    		var entId = $("#fddbr").comboxfield("getValue");
    		if(!entId){
    			jazz.info("请选择法定代表人。");
    			return;
    		}
			//var entMob = $("#mobile").textfield("getValue");
    		//每次改变法定代表人 存库
			var params = {
			        url : '../../../apply/setup/member/storeLegalRep.do?gid='+gid +'&entId='+entId,//+'&entMob='+entMob,
			        
			        async:false,
			        callback : function(data, param, res) {
			        } 	
			    };  	
	    		$.DataAdapter.submit(params);
    	},
    	
    	/**
    	 * 查询企业的基本信息
    	 */
    	applySetupBeWkEntInfo:function(){
    		var gid = jazz.util.getParameter('gid')||'';
    		$.ajax({
				url:'../../../apply/setup/member/setting.do?gid='+gid,
				type:"post",
				async:false,
				dataType:"json",
				success: function(data){
					var isBoard = "-1" , isSuped = "-1";
					if(!data){
						 isBoard = "-1" ;
						 isSuped = "-1" ;
					}else{
						isBoard = data.isBoard || "-1";
						isSuped = data.isSuped || "-1";
					}
					
					if(isBoard=='0'){
						$("#noSet").attr("checked",'0');
					}
					if(isBoard=='1'){
						$("#yesSet").attr("checked",'1');
					}
					if(isSuped=='0'){
						$("#supervisorNotSet").attr("checked",'0');
					}
					if(isSuped=='1'){
						$("#supervisorYesSet").attr("checked",'1');
					}
					
				},
			});
    	},
    	/**
    	 * 处理主要人员主体信息模板
    	 */
    	renderTemplate:function(){
    		var gid = jazz.util.getParameter("gid");
    		$(".main").renderTemplate(
    				{templateName:"content",insertType:"wrap",wrapSelector:".content",},
        			{gid:gid}
    		);
    	},
    	/**
    	 *  选中主要人员信息页签
    	 */
    	reLocate:function(){
    		var urlstr=location.href;
    		$(".banner a").each(function(){
    			if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
    				$(this).addClass("blueactive");
    	    		$(this).find("span:eq(0)").addClass("icon-mbr-blue");
    			}else{
    				$(this).removeClass("blueactive");
    				$(this).find("span:eq(0)").removeClass("icon-info-blue");
    				$(this).find("span:eq(0)").addClass("icon-info");
    			}
    		});
    	},
    	/**
    	 * 根据董事会设置更新页面展示。
    	 * 
    	 * 如果切换为设置董事会，显示“添加”卡片；
    	 * 如果切换为不设置董事会，现有董事超过1个，则提示“不设置董事会最多只能有一个执行董事，请先删除多出的董事。”
    	 *如果现有董事只有1个，需调用后台，将现有董事职务更新为“执行董事”，隐藏添加卡片。
    	 * 
    	 * @params isSet true - 设置董事会，false - 不设置董事会
    	 */
    	onDirectorSettingChange:function (){
    		var isBoardValue  = $("input[name='executive']:checked").val()||"";
    		var cnt = mbr.getDirectorCount();
    		if(isBoardValue=='1'){//设置董事会
    			if(cnt>13){
    				jazz.confirm("设立董事会，董事成员为3-13人，请先删除多出的董事，其中董事长1人",function(){
    					
    				},function(){
    					
    				});
    			}
    		}
    		if(isBoardValue=='0'){//不设董事会
    			if(cnt>1){
    				jazz.info("不设置董事会最多只能有一个执行董事，请先删除多出的董事。");
    			}
    		}
    	},
    	/**
    	 * 如果切换为设置监事会，监事人数至少为3人,其中设监事会主席1人，显示“添加”卡片；
    	 * 如果切换为不设置监事会，现有监事超过2个，则提示“不设置监事会，监事人数1~2人，请先删除多出的监事。”
    	 * 		
    	 * @params isSet true - 设置监事会，false - 不设置监事会
    	 */
    	onSupervisorSettingChange:function (isSet){
    		var isSupeValue  = $("input[name='supervisor']:checked").val()||"";
    		var cnt = mbr.getSupervisorCount();
    		if(isSupeValue=='1'){//设置监事会
    			if(cnt<3){
    				jazz.info("设置监事会，监事人数至少为3人,其中设监事会主席1人");
    			}
    		}
    		
    		if(isSupeValue=='0'){//不设监事会
    			if(cnt>2){
    				jazz.info("不设置监事会，监事人数1~2人，请先删除多出的监事。");
    			}
    		}
    	},
    	/**
    	 * 获得董事人数。
    	 * 不查后台。
    	 */
    	getDirectorCount:function (){
    		var directorCnt = $(".directors").size();
    		return directorCnt;
    	},
    	/**
    	 * 获得监事人数。
    	 * 
    	 * 不查后台。
    	 */
    	getSupervisorCount:function (){
    		var conductorCnt = $(".conductors").size();
    		return conductorCnt;
    	},
    	/**
    	 * 更新列表显示。
    	 * 
    	 */
    	refreshList:function (){
//    		mbr.removeAllMemberList();
//	  		mbr.getAllMemberList();
    		window.location.reload();
    	},
	    //没有数据的时候隐藏显示title和分页按钮
 	   TitlePage:function(){
 		   var num=$(".main").children("input").eq(0).val();
 		   num++;
 		   if(document.getElementsByClassName("card").length>0){
 			   if($(".main").children("input").eq(0).val()>7){
 				   $(".executive").css("display","block");
 			   }
 			   if($(".main").children("input").eq(1).val()>7){
 				   $(".pageCp").css("display","block");
 			   }
 		   } 
 	   },
 	   
 	   /**
 	    * 查询备选的法定代表人
 	    */
 	   getLegalDelegatedData : function(obj){
 		   if(obj && obj.length>0){
 			   $("#fddbr").comboxfield('setValue','');
 			   $("#fddbr").comboxfield('setText','');
 			  // $("#mobile").textfield('setText','');
 			   return ;
      	   }
 		   var selected =$("#fddbr").comboxfield('getValue');
 		   var gid = jazz.util.getParameter('gid');
 		   var isBoardValue  = $("input[name='executive']:checked").val();
 		   var nameDataUrl = '../../../dmj/queryLegalDelegated.do?gid='+gid+'&isBoardValue='+isBoardValue+'&'+Math.random();
 		   $("#fddbr").comboxfield('option', 'dataurl',nameDataUrl);
 		   $("#fddbr").comboxfield("reload",null,function(){
 			   //回显法定代表人
 			  /*if(selected){
 	 			  $("#fddbr").comboxfield("setValue",selected);
 	 			  var entmemberName = $("#fddbr").comboxfield('getText');
 	 			  var name =  entmemberName.slice(0,entmemberName.indexOf("("));
 	 			  $("#fddbr").comboxfield('setText',name);
 	 		   }else{*/
 	 			   mbr.reDeployLegalDelegate();
 	 		   //}
				
 		   });
 		   
 	   },
 	  
 	  //回显法定代表人
 	  reDeployLegalDelegate : function(){
 		 var gid = jazz.util.getParameter('gid');
 		var params = {
		        url : '../../../apply/setup/member/reDeployLegalDelegate.do?gid='+gid,
		        async:false,
		        callback : function(data, param, res) {
		        	var jsonData = res.getAttr('data')||"";	
		        	if(jsonData!=""){
		        		$("#fddbr").comboxfield('setValue',jsonData.entmemberId+'_'+jsonData.leRepJobCode);
		        		$("#fddbr").comboxfield('setText',jsonData.name);
		        		//$("#mobile").textfield('setText',jsonData.leRepMob);
		        		$(".main2-font4").remove();
		        		var html = "<span class='main2-font4' title='法定代表人'>法定代表人</span>";
		        		$("div[entmemberId='"+jsonData.entmemberId+"'][position='"+jsonData.leRepJobCode+"']").find(".name-info.omit").append(html);
		        		
		        	}else{
		        		//清空
		        		$("#fddbr").comboxfield('setValue',"");
		        		$("#fddbr").comboxfield('setText',"");
		        		//$("#mobile").textfield('setText',"");
		        		$(".main2-font4").remove();
		        	}
		        } 	
		    };  	
    	$.DataAdapter.submit(params);
 	  },
 	  
 	  //根据是否设立董事会决定法定代表人的提示信息
 	  changeFddbrNotice : function(){
 		 var isBoardValue  = $("input[name='executive']:checked").val();
 		 if(isBoardValue == "0"){
 			document.getElementById("fddbrNotice").innerText = "( 法定代表人只能由担任执行董事,经理的人员担任 )";
 		 }else{
 			document.getElementById("fddbrNotice").innerText = "( 法定代表人只能由担任董事长,经理的人员担任 )";
 		 }
 	  }
    };
    
    /* 执行初始化 */
    mbr._init();
    
    /* 返回模块 */
    return mbr;
});