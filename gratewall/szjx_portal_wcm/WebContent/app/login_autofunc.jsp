<%@ page import="java.io.IOException" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="com.trs.infra.util.BASE64EncoderStream" %>
<%@ page import="com.trs.infra.util.BASE64DecoderStream" %>
<%@ page import="com.trs.infra.util.PinyinHelper" %>

<%!
	//定义cookie常量
	private final String TRSUSERNAME = "trswcmusername";//用户名称
	private final String TRSPASSWORD = "trswcmtoken";//用户密码
	private final String AUTOLOGIN = "trswcmautologin";//用户是否自动登录
	private final String ENCODE = "UTF-8";
	private final int SECONDS_OF_YEAR = 60*60*24*365;
	

	/**
	 * 用jsp取cookie的方法，实现自动登录,基本代码来自EKP
	 **/

	/**
	 * 从request对象中获取指定的cookie字段值
	 * @param request HttpServletRequest request对象
	 * @param cookieName 要获得的cookie字段
	 */
	private String getCookieByName(HttpServletRequest request,String cookieName){
		Cookie[] cookies = request.getCookies();		
		if(cookies != null){
			for(int i=0 ,len=cookies.length; i < len; i++){
				Cookie cookie = cookies[i];
				if(cookieName.equals(cookie.getName())){
					return base64Decrypt(cookie.getValue());
				}
			}
		}
		return "";
	}
	/**
	 * 删除指定的cookie字段
	 * @param request HttpServletRequest request对象
	 * @param request HttpServletResponse response对象
	 * @param cookieName 要删除的cookie字段
	 */
	private void clearCookieByName(HttpServletRequest request,HttpServletResponse response,String cookieName){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(int i = 0, len = cookies.length; i < len; i++){
				Cookie cookie = cookies[i];
				if(cookieName.equals(cookie.getName())){
					cookie.setValue(null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}
	/**
	 * 取消自动登录
	 * @param request HttpServletRequest request对象
	 * @param request HttpServletResponse response对象
	 */
	private void clearCookieAll(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(int i = 0, len = cookies.length; i < len; i++) {
				Cookie cookie = cookies[i];
				if(TRSPASSWORD.equals(cookie.getName())
					 || AUTOLOGIN.equals(cookie.getName())){
					cookie.setValue(null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	//base64加密
	public String base64Encryption(String sValue){
		try{
			return new String(BASE64EncoderStream.encode(sValue.getBytes("UTF-8")));
		}catch(UnsupportedEncodingException ex){
		}
		return "";
	}
	//base64解密
	public String base64Decrypt(String sValue){
		try{
			return new String(BASE64DecoderStream.decode(sValue),ENCODE);
		}catch(IOException ex){
		}
		
		return "";
	}
		
	//添加Cookie
	private void setCookie(HttpServletResponse response, String sCookieName, String sCookieValue){
		Cookie cookie = new Cookie(sCookieName,base64Encryption(sCookieValue));
		cookie.setMaxAge(SECONDS_OF_YEAR);
		response.addCookie(cookie);
	}

	private String makeTokenCookieName(String userName){
		String result = PinyinHelper.convert(userName) + '_' + TRSPASSWORD;
		return result;
	}
%>