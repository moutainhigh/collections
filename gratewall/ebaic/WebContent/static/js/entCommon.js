define(['jquery', 'renderTemplate', 'jazz'], function($, renderTemplate, jazz){
    var common = {
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				  if(top == self){
					  if($("header").length == 0){
						  if(window.location.href.indexOf('business_verify') != -1){
							  $("body").renderTemplate({templateName:'ent_header',insertType:'prepend'},{type:'business_verify'});
							  $('#log-exit').on('click',$_this.logout);
						  }else{
							  $("body").renderTemplate({templateName:'ent_header',insertType:'prepend'});
							  $('#log-exit').on('click',$_this.entLogout);
						  }
						  $("body").renderTemplate({templateName:'footer',insertType:'append'});
			        	  
			        	  var pageName = window.location.href;
			        	  var flag = true;
			        	  for(var i = 0, len = $_this.noLogin.length; i < len; i++){
			        		  if(pageName.indexOf($_this.noLogin[i]) > -1){
			        			  flag = false;
			        		  }
			        	  }
			        	  if(flag){
			        		  if(window.location.href.indexOf('business_verify') != -1){
			        			  common.getVerifyUser();
							  }else{
								  common.getCurrentUser();
							  }
			        		  
			        	  }
					  }
					 // $_this.isBrowser();
					  $_this.computeHeight();	
		    		}   		
				  
			  });
			});
    		
    	},
    	
    	noLogin : ['reg.html','find_pwd.html'],
    	
    	getSmsVercodeValidateTimeInSeconds : function(){
       		return 30;
       	},
       	
    	//浏览器判断
    	isBrowser:function (){
    		var IeMsg="请使用360浏览器或ie9浏览器";
    		var flag = true;
    		if(navigator.userAgent.indexOf("MSIE")>0)
    		{
    		    if(navigator.userAgent.indexOf("MSIE 6.0")>0)
    		{
    		    flag = false;
    		}
    		if(navigator.userAgent.indexOf("MSIE 7.0")>0)
    		{
    		    flag = false;
    		}
    		if(navigator.userAgent.indexOf("MSIE 8.0")>0)
    		    {
    		        flag=false;
    		    }
    		}else if(navigator.userAgent.indexOf("Chrome")>0){
    		/*alert("这个浏览器支持css3");*/
    		}
    		else
    		{
    		   flag = false;
    		}
    		if(!flag){
    		    alert(IeMsg);
    		}
    	},
    	//动态计算article中内容高度
    	   computeHeight :function (){
       	    total=document.documentElement.clientHeight;
       	    if(document.getElementsByTagName("header")[0].offsetHeight!=null){
       	    	headerh=document.getElementsByTagName("header")[0].offsetHeight;
       	    }
       	    if(document.getElementsByTagName("footer")[0].offsetHeight!=null){
       	   	    footerh=document.getElementsByTagName("footer")[0].offsetHeight;
       	    }       	    
       	    articleH=total-headerh-footerh;
       	    if(document.getElementsByTagName("article")[0]==null)
       	    {
       	    	//没有article标签 的情况  登录页没有article标签 
       	    }else if(document.getElementsByTagName("article")[0]!=null){
       	    	article=document.getElementsByTagName("article")[0].offsetHeight;
       	    	if(article<articleH){
       	    		document.getElementsByTagName("article")[0].style.height=articleH+"px";       	    		
       	    	}else{
       	    		document.getElementsByTagName("article")[0].style.minHeight=articleH+"px";
       	    		document.getElementsByTagName("article")[0].style.height="100%";
       	    	}
       	    }
       	},
       	
       	getVerifyUser : function(){
       		var _user = "" ;
    		$.ajax({
    			url : '../../../security/auth/user/loginStatus.do',
    			type : "post",
    			dataType : "json",
    			async:false,
    			success : function(data) {
    				_user=data;
    				if(data.result=="fail"){
    					jazz.info("登录超时，请重新登录。",function(){
    						window.location.href = "../../apply/index.html";
    					});
    				}
    				if(data.name){
    					$("#entName").text(data.name);
    				}
    			}
    		});
			return _user;
    	},
       
       	getCurrentUser : function(){
       		var _user ;
    		$.ajax({
    			url : '../../../../torch/service.do?fid=entAccountIndex&wid=myOperation',
    			type : "post",
    			dataType : "json",
    			async:false,
    			success : function(data) {
    				_user = data;
    				if(!_user){
    					alert('登录超时，请重新登录。');
    					window.location.href = "/";
    				}
    				var tmp  = $('#notice-todo').html();
    				if(tmp){//避免重复调用
    					return;
    				}
    				var notice="";
    				var sn ="";
    				var i=1;
    				if(_user.Iresult=='identityFail'){
		    			notice = (notice==""?('<p class="noticeInfo-indent" >'+i+'、完成个人身份信息的提交</p>'):(notice+=('<p class="noticeInfo-indent">'+i+'、完成个人身份信息的提交</p>')));
		    			i++;
		    		}
    				if(_user.Mresult=='mobFail'){
		    			notice = (notice==""?('<p class="noticeInfo-indent" >'+i+'、完成个人移动电话的认证及绑定</p>'):(notice+=('<p class="noticeInfo-indent">'+i+'、完成个人移动电话的认证及绑定</p>')));
		    			i++;
		    		}
    				
    				if(_user.Iresult=='identityFail' || _user.Mresult=='mobFail'){
    					//暂时没有具体的验证页面，统一跳转到安全中心，后台有可用的内部跳转的方法
    					$('#notice-todo').append(notice);
    					$("#hint-box").show();
    					$("#bg").show();
    				}
    				$(".entName").find("span").html(data.data[0].data.rows[0].entName);
    				$("#entName").attr("title",data.data[0].data.rows[0].entName);
    				$(".bgcolor").css("width","60px");
    				$(".li-img-exit").css("margin-left","5px");
    			}
    		});
			return _user;
    	},
    	
    	logout:function(){
    		$.ajax({
    			url:'../../../security/auth/user/logout.do',
    			type:'post',
    			success:function(data){
    				/*window.location.href='../../../index.html';*/
    				window.location.href = "../index.html";
    			},
    			error:function(data){
    				
    			}
    		});
    	},
    	entLogout:function(){
    		$.ajax({
    			url:'../../../apply/entAccount/entLogout.do',
    			type:'post',
    			success:function(data){
    				/*window.location.href='../../../index.html';*/
    				window.location.href = "../../apply/ent_account/entindex.html";
    			},
    			error:function(data){
    				
    			}
    		});
    	}
    	
    };
    common._init();
    return common;
});