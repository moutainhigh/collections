package com.esp.server.web.common;

import com.esp.server.web.vo.UserVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 会话保存类
 */
public class UserContext  {

  public static final String LONGED_USER = "loginUser";

  private static HttpSession getSession() {
      ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      return  attrs.getRequest().getSession();
  }

  
  public static void setUser(UserVO user){
    getSession().setAttribute(LONGED_USER, user);
  }
  
  public static void remove() {
    getSession().removeAttribute(LONGED_USER);
  }
  
  public static UserVO getUser() {
    return (UserVO) getSession().getAttribute(LONGED_USER);
  }

}
