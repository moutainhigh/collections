package com.gwssi.safe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gwssi.entSelect.service.SafeVisitService;
import com.gwssi.entSelect.util.IPUtil;

public class SafeHaddleInterceptor implements HandlerInterceptor {
	
	private int count = 0;
	@Autowired
	private SafeVisitService safeVisitService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	//http://blog.csdn.net/tjcyjd/article/details/7498236
	//http://www.cnblogs.com/xiaoyangjia/p/3762150.html
	///*String ip = IPUtil.getIpPropAddr(req);
	//System.out.println(ip);*/
	//
	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// yyyy/MM/dd
	// HH:mm:ss
	//String date = sdf.format(new Date());
	//System.out.println(date);
	//最先执行
	
	/*if(ipSession!=null){
		return true;
	}else{
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print("error");
		return false;
	}*/
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		//String ip = IPUtil.getIpPropAddr(req);
		//int count  = safeVisitService.count(ip);
		//String reqURI = req.getRequestURI();
		//System.out.println("记录的URL==" + reqURI);
		//System.out.println("当前IP:" + ip +" 当前访问次数为： " + count);
		/*if(count>20){
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().print("您查询次数过多，请稍后再查！");
			req.getSession().setAttribute("isNotAllow", true);
			return false;
		}else{
			//safeVisitService.saveIPLog(ip);
			return true;
		}*/
		return true;
	}
}
