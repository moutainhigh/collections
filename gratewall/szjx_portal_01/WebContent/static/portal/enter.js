/**
 * Created by lily on 16-9-14.
 */
$(function(){
	$(".menu_icon1").next().next().show();
    $(".menu").mouseover(function(){
    	$(".menu_icon1").next().next().hide();
        var id=$(this).attr("id");
        if(id==2){
            $(".icon_supervise").css("background","url('../../static/portal/images/supervise1.png') no-repeat");
            $("#2 h3").addClass("menu_icon1 select")
            $("#2 .menu_icon").css({"background":"#0186bc","color":"#fff"});
            $("#2 .submenu").css("display","block");
            $(".show2").css("display","block");
        }else{
            $(".icon_supervise").css("background","url('../../static/portal/images/supervise.png') no-repeat");
            $("#2 h3").removeClass("menu_icon1 select")
            $("#2 .menu_icon").css({"background":"#f9f9f9","color":"#444"});
            $("#2 .submenu").css("display","none");
            $(".show2").css("display","none");
        }
        if(id==1){
            $(".icon_permission").css("background","url('../../static/portal/images/permission1.png') no-repeat");
            $("#1 h3").addClass("menu_icon1 select");
            $(".show1").show();
            $("#1 .menu_icon").addClass("menu_icon1")
            $(".show1").css("display","block");
            $("#1 .submenu").addClass("submenu1");
        }else{
            $(".icon_permission").css("background","url('../../static/portal/images/permission.png') no-repeat");
            $("#1 h3").removeClass("menu_icon1 select");
            $(".show1").hide();
            $("#1 .menu_icon").removeClass("menu_icon1")
            $("#1 .submenu").removeClass("submenu1");
            $(".show1").css("display","none");
        }
        if(id==3){
            $(".icon_service").css("background","url('../../static/portal/images/service1.png') no-repeat");
            $("#3 h3").addClass("menu_icon1 select")
            $("#3 .menu_icon").css("background","#0186bc");
            $(".show3").css("display","block");
            $("#3 .submenu").css("display","block");
        }else{
            $(".icon_service").css("background","url('../../static/portal/images/service.png') no-repeat");
            $("#3 h3").removeClass("menu_icon1 select")
            $("#3 .menu_icon").css("background","#f9f9f9");
            $("#3 .submenu").css("display","none");
            $(".show3").css("display","none");
        }
        if(id==4){
            $(".icon_manage").css("background","url('../../static/portal/images/manage1.png') no-repeat");
            $("#4 h3").addClass("menu_icon1 select")
            $("#4 .menu_icon").css("background","#0186bc");
            $(".show4").css("display","block");
            $("#4 .submenu").css("display","block");
        }else{
            $(".icon_manage").css("background","url('../../static/portal/images/manage.png') no-repeat");
            $("#4 h3").removeClass("menu_icon1 select")
            $("#4 .menu_icon").css("background","#f9f9f9");
            $("#4 .submenu").css("display","none");
            $(".show4").css("display","none");
        }
        if(id==5){
            $(".icon_enforcement").css("background","url('../../static/portal/images/enforcement1.png') no-repeat");
            $("#5 h3").addClass("menu_icon1 select")
            $("#5 .menu_icon").css("background","#0186bc");
            $(".show5").css("display","block");
            $("#5 .submenu").css("display","block");
        }else{
            $(".icon_enforcement").css("background","url('../../static/portal/images/enforcement.png') no-repeat");
            $("#5 h3").removeClass("menu_icon1 select")
            $("#5 .menu_icon").css("background","#f9f9f9");
            $("#5 .submenu").css("display","none");
            $(".show5").css("display","none");
        }
        if(id==6){
            $(".icon_decision").css("background","url('../../static/portal/images/decision1.png') no-repeat");
            $("#6 h3").addClass("menu_icon1 select")
            $("#6 .menu_icon").css("background","#0186bc");
            $(".show6").css("display","block");
            $("#6 .submenu").css("display","block");
        }else{
            $(".icon_decision").css("background","url('../../static/portal/images/decision.png') no-repeat");
            $("#6 h3").removeClass("menu_icon1 select")
            $("#6 .menu_icon").css("background","#f9f9f9");
            $("#6 .submenu").css("display","none");
            $(".show6").css("display","none");
        }
        if(id==7){
            $(".icon_system").css("background","url('../../static/portal/images/system1.png') no-repeat");
            $("#7 h3").addClass("menu_icon1 select")
             $("#71").removeClass("menu_icon20");
            $("#71").addClass("meu_icon21");
         /*   $("#71").css("background","#0186bc");*/
            $("#7 .submenu").css("display","block");
        }else{
            $(".icon_system").css("background","url('../../static/portal/images/system.png') no-repeat");
            $("#7 h3").removeClass("menu_icon1 select")
            
             $("#71").removeClass("meu_icon21");
            $("#71").addClass("menu_icon20");
            //$("#71").css("background","#f9f9f9");
            $("#7 .submenu").css("display","none");
        }

    })
})
