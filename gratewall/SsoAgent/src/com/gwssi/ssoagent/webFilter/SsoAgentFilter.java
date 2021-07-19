package com.gwssi.ssoagent.webFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.ssoagent.dualUser.HttpUserActor;
import com.gwssi.ssoagent.httpClient.HttpClientTest;
import com.gwssi.ssoagent.model.SSOUser;
import com.gwssi.ssoagent.util.PropertiesUtil;

/**
 * �����¼agent
 * 
 * @author chaihw
 */
public class SsoAgentFilter implements Filter {
	private static PropertiesUtil pro = new PropertiesUtil("/SSoAgent.properties");
	private static Logger logger = Logger.getLogger(SsoAgentFilter.class);

	public SsoAgentFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String urlr1 = "" + httpRequest.getRequestURL();
		/*
		 * if(null!=httpRequest.getQueryString()&&httpRequest.getQueryString().
		 * length()>0){ urlright2="?"+httpRequest.getQueryString(); }
		 */
		
		// ��ȡ�����ļ������¼�Ƿ��
		String ssoOn = pro.getProperty("sso.on");

		// δ�� ֱ��ͨ��������
		if (!(ssoOn != null && "true".equalsIgnoreCase(ssoOn))) {
			// System.out.println("SSoAgent.properties
			// �ļ���sso.on��Ϊtrue�����¼����δ����������ʹ�õ����¼������������ļ�sso.on��Ϊtrue");
			chain.doFilter(request, response);

		} else { // ��������

			// ��ȡtokenֵ
			String token = request.getParameter("token");
			HttpUserActor actor;
			try {

				String classname = pro.getProperty("ssoclass");
				actor = (HttpUserActor) Class.forName(classname).newInstance();

				// �ж��Ƿ��¼
				boolean islogin = actor.checkLocalLogin(httpRequest.getSession());
				if (islogin == true) {// ��¼ֱ��ͨ��

					chain.doFilter(request, response);
				} else {// δ��¼ token��֤
					if (token == null || "".equals(token)) {
						tokenIsNUll(httpRequest, res);
						return;
					} else {

						if (tokenNOTNull(token, actor, httpRequest, res)) {
							chain.doFilter(request, response);
						} else {
							tokenIsNUll(httpRequest, res);
							return;
						}

					}

				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}
	// chain.doFilter(request, response);

	/**
	 * tokenΪ�ջ��߹���ʱ�����
	 * 
	 * @param httpRequest
	 * @param res
	 * @throws IOException
	 */
	private static void tokenIsNUll(HttpServletRequest httpRequest, HttpServletResponse res) throws IOException {
		// System.out.println("token Ϊ��,��ת ��ȡtoken��");

		String urlleft = pro.getProperty("ssoToken");

		String urlr1 = "" + httpRequest.getRequestURL();
		/*
		 * if(null!=httpRequest.getQueryString()&&httpRequest.getQueryString().
		 * length()>0){ urlright2="?"+httpRequest.getQueryString(); }
		 */
		logger.info("��ǰ���ʵĵ�ַ��>>>>>>>>>>>>>>>>>>>>>>>>>>>:" + urlr1);
		String ssogetUrl = pro.getProperty("sso.getUrl");

		if (ssogetUrl != null) {
			String[] changurls=  pro.getProperty("sso.changUrl").split(",");
			if (ssogetUrl.split(",").length > 1) {
				String[] ssourl= ssogetUrl.split(",");
				for(int i=0;i<ssourl.length;i++){
					if (urlr1.startsWith(ssourl[i])) {
						String changurl=null;
						if(changurls.length==ssourl.length){
							changurl=changurls[i];
						}else{
							changurl=changurls[0];
						}
						urlr1 = urlr1.replaceFirst(ssourl[i], changurl);
						logger.info("�滻���ַ>>>>>>>>>>>>>\n" + urlr1);
						break;
					}
				}
 
			} else {
				if (urlr1.startsWith(ssogetUrl)) {
					urlr1 = urlr1.replaceFirst(ssogetUrl, pro.getProperty("sso.changUrl"));
					logger.info("�滻���ַ>>>>>>>>>>>>>\n" + urlr1);
				}
			}

		}
		String urlright = "?" + urlr1;
		String urlright2 = dualtoken(httpRequest, res);

		String url = urlleft + "" + urlright + urlright2;
		res.sendRedirect(url);
	}

	private static boolean tokenNOTNull(String token, HttpUserActor actor, HttpServletRequest httpRequest,
			HttpServletResponse res) throws Exception {
		String url = pro.getProperty("UserMessageurl") + "/" + pro.getProperty("urlMd5Key") + "/" + token;
		String jsonback = HttpClientTest.getClient(url);
		JSONObject json = JSON.parseObject(jsonback);
		boolean returnbo = false;
		String code = json.get("code").toString();
		String successcode = pro.getProperty("sso.return.success");
		if (successcode.equals(code)) {// �ɹ�
			JSONObject datajson = JSON.parseObject(json.get("data").toString());
			SSOUser ssouser = new SSOUser();
			ssouser.setAdDomain(datajson.getString("domain"));
			ssouser.setIpAddr(datajson.getString("ipaddr"));
			ssouser.setUsername(datajson.getString("userid"));
			actor.loadLoginUser(httpRequest, ssouser);
			returnbo = true;
			// chain.doFilter(httpRequest, res);
		}

		String timeOutcode = pro.getProperty("sso.return.timeout");
		if (timeOutcode.equals(code)) {// ����
			// tokenIsNUll(httpRequest,res);
			returnbo = false;
		}

		boolean issuccess = successcode.equals(code);
		boolean istimeout = timeOutcode.equals(code);

		if ((!issuccess) && (!istimeout)) {
			throw new Exception(json.toJSONString());
		}
		return returnbo;

		// res.sendRedirect(url2);
		// httpRequest.getRequestDispatcher(url).forward(httpRequest, response);
		// return;

	}

	/**
	 * ȥ��tokenֵ
	 * 
	 * @param httpRequest
	 * @param res
	 * @return
	 */
	private static String dualtoken(HttpServletRequest httpRequest, HttpServletResponse res) {
		// �����߼�Ϊ����token
		String urlright = httpRequest.getRequestURL().toString();
		String urlright2 = "";
		if (null != httpRequest.getQueryString() && httpRequest.getQueryString().length() > 0) {
			urlright2 = "?" + httpRequest.getQueryString();
			int begintoken = urlright2.indexOf("&token");
			if (begintoken >= 0) {
				urlright2 = urlright2.substring(0, begintoken);
			}
			begintoken = urlright2.indexOf("token");
			if (begintoken >= 0) {
				urlright2 = urlright2.substring(0, begintoken);
			}
			if (urlright2.length() <= 1) {
				urlright2 = "";
			}

		}
		return urlright2;
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
