/**
 * 
 */
package com.tz.util.email;

import com.tz.util.date.TmDateUtil;


/**
 * @author Administrator
 *
 */
public class TmEmailTemplate {
    
	
	public static void main(String[] args) {
		sendEmailForEmailCode("761008250@qq.com","123456");
	}
	
  /**
     * 邮箱验证码
     * @param email
     * @param random
     * @return
     */
    public static boolean sendEmailForEmailCode(String email,String random){
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setHost("smtp.163.com");
        emailInfo.setPort("25");
        emailInfo.setValidate(true);
        emailInfo.setUserName("xuchengfeifei");
        emailInfo.setPassword("xiaoer");
        emailInfo.setSendAddress("xuchengfeifei@163.com");
        emailInfo.setReceivAddress(email);
        emailInfo.setSubject("账户邮箱验证码 - 潭州学院KeKe博客");
        //邮箱提醒验证码
        emailInfo.setContent("<div style='background: #FFFFFF;border:1px solid #4E5E7E;border-radius:10px;'>"+
        "       <div style='color:#fff;line-height: 30px;font-size: 24px;font-weight: 900;text-align: center;border-bottom: 2px solid #ddd;padding: 10px;margin-bottom: 10px;background: #4E5E7E;border-radius: 10px 10px 0 0;box-shadow: inset 0 1px 3px #FFF;'>新用户注册邮箱验证码 - 潭州学院KeKe博客</div>"+
        "       <div style='padding: 15px;word-break: break-all;font-size: 14px;color: #666;line-height: 26px;'>"+
        "           <p>亲爱的用户您好，<a href='http://keke.tanzhouedu.com' target='_blank'><b style='margin: 0 2px;'>http://keke.tanzhouedu.com</b></a>上注册您的账户，为了更好的用户体验，我们将对您的注册信息采取邮箱验证的方式来保证您的账号不被他用。</p>"+
        "           <div style='border-right: 1px solid #d4d4d4; border-left: 1px solid #d4d4d4; border-bottom: 1px solid #AAAAAA; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; -moz-box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4; -webkit-box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4; box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4;padding:20px;'>"+
        "               以下您本次注册的邮箱验证码，复制该验证码在刚才的页面填写即可。每个邮箱验证码的有效期约为20分钟，请尽快注册使用。"+
        "               <div style='margin-top:20px;'>"+
        "                   邮箱验证码为：<b style='color: #FFF;font-size: 20px;background: #464646;padding: 2px 6px;margin: 0 5px;border: 1px solid #303030;border-radius: 3px;'>"+random+"</b>。"+
        "               </div>"+
        "           </div>"+
        "       </div> "+
        "       <div style='padding: 15px 10px;text-align: right;color: #888;font-size: 12px;'>"+
        "       <a href='http://keke.tanzhouedu.com' target='_blank'><b>潭州学院KeKe博客</b></a>"+ TmDateUtil.getCurrentTime("yyyy-年-MM-月-dd日 HH:时mm分ss秒")+
        "       </div>"+
        "   </div>");
        return TmSendEmailUtil.sendEmail(emailInfo);
    }
    
    /**
     * 注册成功发送验证码
     * @param user
     * @param password 没有加密的密码
     * @return
     */
    public static boolean sendEmailForRegister(Long userId,String account,String username,String password,String email){
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setHost("smtp.163.com");
        emailInfo.setPort("25");
        emailInfo.setValidate(true);
        emailInfo.setUserName("xuchengfeifei");
        emailInfo.setPassword("xiaoer");
        emailInfo.setSendAddress("xuchengfeifei@163.com");
        emailInfo.setReceivAddress(email);
        emailInfo.setSubject("账户注册邮箱验证码 --潭州学院KeKe博客");
        //注册成功
        emailInfo.setContent("<div style='background: #FFFFFF;border:1px solid #ddd;border-radius:10px;'>"+
          "       <div style='line-height: 30px;font-size: 24px;font-weight: 900;text-align: center;border-bottom: 2px solid #ddd;padding: 10px;margin-bottom: 10px;background: #F1F1F1;border-radius: 10px 10px 0 0;box-shadow: inset 0 1px 3px #FFF;'>感谢注册 - 潭州学院KeKe博客</div>"+
          "       <div style='padding: 15px;word-break: break-all;font-size: 14px;color: #666;line-height: 26px;'>"+
          "           <p>尊敬的<a href='http://keke.tanzhouedu.com/user' target='_blank'><b style='margin: 0 2px;'>"+account+"</b></a>您好，感谢您在<a href='http://keke.tanzhouedu.com' target='_blank'><b style='margin: 0 2px;'>潭州学院KeKe博客</b></a>上注册您的账户。以下是您的注册信息，请您妥善保管。</p>"+
          "           <div style='border-right: 1px solid #d4d4d4; border-left: 1px solid #d4d4d4; border-bottom: 1px solid #AAAAAA; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; -moz-box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4; -webkit-box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4; box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4;padding:20px;'>"+
          "               <ul>"+
          "                   <li style='font-weight:900;'>"+
          "                       <b style='font-weight:normal;color: #888;'>姓名：</b>"+
          "                       "+username+
          "                   </li>"+
          "                   <li style='font-weight:900;'>"+
          "                       <b style='font-weight:normal;color: #888;'>账号：</b>"+
          "                       "+account+
          "                   </li>"+
          "                   <li style='font-weight:900;'>"+
          "                       <b style='font-weight:normal;color: #888;'>密码：</b>"+
          "                       "+password+
          "                   </li>"+
          "                   <li style='font-weight:900;'>"+
          "                       <b style='font-weight:normal;color: #888;'>邮箱：</b>"+
          "                       <a href='mailto:xuchengfeifei@163.com' target='_blank'>"+email+"</a>"+
          "                   </li>"+
          "               </ul>"+
          "           </div>"+
          "       </div> "+
          "       <div style='padding: 15px 10px;text-align: right;color: #888;font-size: 12px;'>"+
          "           <a href='http://keke.tanzhouedu.com' target='_blank'><b>潭州学院KeKe博客</b></a>"+ TmDateUtil.getCurrentTime("yyyy-年-MM-月-dd日 HH:时mm分ss秒")+
          "       </div>"+
          "   </div>");
        return TmSendEmailUtil.sendEmail(emailInfo);
    }
    
    
    /**
     * 邮箱验证码
     * @param email
     * @param random
     * @return
     */
    public static boolean sendEmailForBackPassword(String account,String email,String random){
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setHost("smtp.163.com");
        emailInfo.setPort("25");
        emailInfo.setValidate(true);
        emailInfo.setUserName("xuchengfeifei");
        emailInfo.setPassword("xiaoer");
        emailInfo.setSendAddress("xuchengfeifei@163.com");
        emailInfo.setReceivAddress(email);
        emailInfo.setSubject("找回密码邮箱验证码 - 潭州学院KeKe博客");
        //找回密码
        emailInfo.setContent("<div style='background: #FFFFFF;border:1px solid #ddd;border-radius:10px;'>"
          + "       <div style='line-height: 30px;font-size: 24px;font-weight: 900;text-align: center;border-bottom: 2px solid #ddd;padding: 10px;margin-bottom: 10px;background: #F1F1F1;border-radius: 10px 10px 0 0;box-shadow: inset 0 1px 3px #FFF;'>找回账户邮箱验证码 - 潭州学院KeKe博客</div>"
          + "       <div style='padding: 15px;word-break: break-all;font-size: 14px;color: #666;line-height: 26px;'>"
          + "           <p>尊敬的<b style='margin: 0 2px;'>"+account+"</b></a>您好，首先感谢您在<a href='http://keke.tanzhouedu.com' target='_blank'><b style='margin: 0 2px;'>潭州学院KeKe博客</b>上注册过您的账户。即使您已经忘记了您的用户名和密码，也可以找回您曾经注册的账户，请您按照找回账户步骤耐心操作。为了验证账户的所有者，我们将对您的找回账户操作采取邮箱验证的方式来保证您的账号不被他用。</p>"
          + "           <div style='border-right: 1px solid #d4d4d4; border-left: 1px solid #d4d4d4; border-bottom: 1px solid #AAAAAA; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; -moz-box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4; -webkit-box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4; box-shadow: 0px 1px 1px #d4d4d4, inset 0px 0px 6px #d4d4d4;padding:20px;'>"
          + "               以下您本次找回账户的邮箱验证码，复制该验证码在刚才的页面填写即可。每个邮箱验证码的有效期约为20分钟，请尽快使用。"
          + "               <ul>"
          + "                   <li style='font-weight:900;'>"
          + "                       <b style='font-weight:normal;color: #888;'>用户名：</b>"
          + "                       <b style='color: #FFF;font-size: 20px;background: #464646;padding: 2px 6px;border: 1px solid #303030;border-radius: 3px;'>"+account+"</b>"
          + "                   </li>"
          + "                   <li style='font-weight:900;'>"
          + "                       <b style='font-weight:normal;color: #888;'>验证码：</b>"
          + "                       <b style='color: #FFF;font-size: 20px;background: #464646;padding: 2px 6px;border: 1px solid #303030;border-radius: 3px;'>"
          + random
          + "</b>"
          + "                   </li>"
          + "               </ul>"
          + "           </div>"
          + "       </div>"
          + "       <div style='padding: 15px 10px;text-align: right;color: #888;font-size: 12px;'>"
          + "           <a href='http://keke.tanzhouedu.com' target='_blank'><b>潭州学院KeKe博客</b></a>"
          + "           于"
          + TmDateUtil.getCurrentTime("yyyy-年-MM-月-dd日 HH:时mm分ss秒")
          + " </div>" + "   </div>");
        
        return TmSendEmailUtil.sendEmail(emailInfo);
    }
    
    
}
