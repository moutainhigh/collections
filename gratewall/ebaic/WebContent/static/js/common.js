define(['jquery', 'renderTemplate', 'jazz'], function($, renderTemplate, jazz){
    var common = {
    	_init: function(){
    		var $_this = this;
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				  window.log = $_this.log;
				  if(top == self){
					  if($("header").length == 0){
						  $("body").renderTemplate({templateName:'footer',insertType:'append'});
			        	  $("body").renderTemplate({templateName:'header',insertType:'prepend'});			        	
			        	  var pageName = window.location.href;
			        	  var flag = true;
			        	  for(var i = 0, len = $_this.noLogin.length; i < len; i++){
			        		  if(pageName.indexOf($_this.noLogin[i]) > -1){
			        			  flag = false;
			        		  }
			        	  }
			        	  if(flag){
			        		  common.getCurrentUser();
			        	  }
			        	  
					  }
					 // $_this.isBrowser();
					  $_this.computeHeight();
					  $('#log-exit').on('click',$_this.logout);
				  }   		
				  
			  });
			});
    		
    	},
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
       	    if(document.getElementsByTagName("header")[0]==null){
       	    	/*没有header标签*/
       	    }else {
       	    	headerh=document.getElementsByTagName("header")[0].offsetHeight;
       	    }
       	    if(document.getElementsByTagName("footer")[0]==null){
       	    	/*没有footer标签*/
       	    }else{
       	   	    footerh=document.getElementsByTagName("footer")[0].offsetHeight;
       	    }       	    
       	    articleH=total-headerh-footerh;
       	    if(document.getElementsByTagName("article")[0]==null)
       	    {
       	    	//没有article标签 的情况  登录页没有article标签 
       	    }else if(document.getElementsByTagName("article")[0]!=null ){
       	    	article=document.getElementsByTagName("article")[0].offsetHeight;
       	    	if(article<articleH){	
       	    		document.getElementsByTagName("article")[0].style.height=articleH+"px";       	    		
       	    	}else{
       	    		document.getElementsByTagName("article")[0].style.minHeight=articleH+"px";
       	    		document.getElementsByTagName("article")[0].style.height="100%";
       	    	}
       	    }
       	},
       	
       	noLogin : ['reg.html','find_pwd.html'],
       
       	getCurrentUser : function(){
       		var _user = "" ;
    		$.ajax({
    			url : '../../../security/auth/user/loginStatus.do',
    			type : "post",
    			dataType : "json",
    			async:false,
    			success : function(data) {
    				_user=data;
    				userType=_user.userType;
    				if(userType=="person"){
    					/*如果登陆的是个人账号*/
    					if(data.result=="fail"){
        					jazz.info("登录超时，请重新登录。",function(){
        						window.location.href = "../../apply/index.html";
        					});
        				}
        				if(data.name){
        					$(".li-img-user").find("span").html(data.name);
        				}
    				}else{
    					/*如果登陆的是企业账号*/
    					if(data.result=="fail"){
        					jazz.info("登录超时，请重新登录。",function(){
        						window.location.href = "../../apply/index.html";
        					});
        				}
        				if(data.name){
        					$("body").renderTemplate({templateName:'ent_header',insertType:'prepend'},{type:'business_verify'});
        					$("#person").css("display","none");
        					$("#entName").text(data.name);
        				
        				}
    				}
    				
    			}
    		});
			return _user;
    	},
    	logout:function(){
    		$.ajax({
    			url:'../../../security/auth/user/logout.do',
    			type:'post',
    			success:function(data){
    				window.location.href = "../index.html";
    			},
    			error:function(data){
    			}
    		});
    	},
    	log: function(info){
    		if(window.console){
				console.log(info);
			}
    	}
    	
    };
    common._init();
    return common;
});