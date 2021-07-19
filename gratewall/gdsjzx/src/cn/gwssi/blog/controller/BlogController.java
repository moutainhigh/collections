package cn.gwssi.blog.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.blog.service.BlogService;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/blog")
public class BlogController {

	public static Logger log=Logger.getLogger(BlogController.class);

	@Autowired
	private BlogService blogService;
	
    @RequestMapping("/saveUser")
    public void saveUser(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String,String> params = req.getForm("formpanel");//获取参数
		TPtSysLogBO logBO = new TPtSysLogBO();
		blogService.addUser(logBO);
		String back="";
		resp.addAttr("back", back);
	}
    /**
     * 系統操作日誌查詢
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @RequestMapping("/querySysLogList")
    public void querySysLogList(OptimusRequest req, OptimusResponse resp) throws Exception {
    	Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> list=blogService.findSysLogList(params);
		resp.addGrid("zzjgGrid", list, null);
	}
    
    /**
     * 採集日誌查詢
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @RequestMapping("/queryCollectLogList")
    public void queryCollectLogList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> list=blogService.findCollectLogList(params);
		resp.addGrid("zzjgGrid", list, null);
	}
    
    /**
     * 共享日誌查詢
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @RequestMapping("/findShareLogList")
    public void findShareLogList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
    	Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> list=blogService.findShareLogList(params);
		resp.addGrid("zzjgGrid", list, null);
	}
    
    /**
     * 共享日誌详细查詢
     * @param req
     * @param resp
     * @throws OptimusException
     */
    @RequestMapping("/findShareLogDetailList")
    public void findShareLogDetailList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String fwrzjbid = req.getParameter("fwrzjbid");
		if(StringUtils.isNotBlank(fwrzjbid)){
			List<Map> list=blogService.findShareLogDetailList(fwrzjbid);
			resp.addGrid("zzjgGrid", list, null);
		}else{
			resp.addGrid("zzjgGrid", null, null);
		}
	}

	@RequestMapping("/logDetail")
	protected void logDetail(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String logid = req.getParameter("logid");
		Map<String,String> map = blogService.findLog(logid);
		res.addForm("formpanel", map);
		//List<Map> list = queryService.queryRegJbxx(params);
		//res.addForm("formpanel",list.get(0), null);
	}
}
