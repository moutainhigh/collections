package cn.gwssi.datachange.datatheme.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import cn.gwssi.plugin.auth.OptimusAuthManager;
import cn.gwssi.plugin.auth.model.User;
import com.gwssi.optimus.util.TreeUtil;
import cn.gwssi.resource.DateUtil;
import cn.gwssi.resource.KafkaTopicUtil;

import cn.gwssi.datachange.datatheme.model.TPtThemexxBO;
import cn.gwssi.datachange.datatheme.service.DataThemeService;
import cn.gwssi.datachange.datatheme.service.ShareServiceService;

/**
 * 服务对象主题控制类
 * 
 */
@Controller
@RequestMapping("/datatheme")
public class DataThemeController {
	private static  Logger log=Logger.getLogger(DataThemeController.class);
	
	@Autowired
	private DataThemeService dataThemeService;
	
	@Autowired
	private ShareServiceService shareServiceService;
	/**
	 * 获取主题列表
	 * @param req
	 * @param res
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/themeList")
	public void themeList(OptimusRequest req, OptimusResponse res)throws OptimusException{
		//TPtThemexxBO bo=req.getForm("formpanel_edit",TPtThemexxBO.class);
		Map<String,String> params = req.getForm("formpanel");//获取参数
		List<Map> list=null;
		if(params!=null){
			list=dataThemeService.selectThemeList(params);
		}
		res.addGrid("zzjgGrid", list, null);
	}
	/**
	 * 获取服务详细信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/themeDetail")
	public void themeDetail(OptimusRequest req, OptimusResponse res)throws OptimusException{
			String themeid=req.getParameter("themeid");
			List list=dataThemeService.findTheme(themeid);
			if(list!=null && list.size()>0){
				res.addForm("formpanel",list.get(0), null);
			}
	}
	
	
	/**
	 * 增加主题信息
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveTheme")
	public void saveTheme(OptimusRequest req, OptimusResponse res)throws OptimusException{
		TPtThemexxBO bo=req.getForm("formpanel_edit",TPtThemexxBO.class);
		String update = req.getParameter("update");
		
		
		HttpSession session= req.getHttpRequest().getSession();
		User customer=(User)session.getAttribute(OptimusAuthManager.USER);
		
		Date date  = new Date();
		
    	String back = "fail";
		if(StringUtils.isNotEmpty(update)&&"true".equals(update)){//修改
			//bo.setCreateperson(customer.getLoginName());
			bo.setCreateperson("sys");
			bo.setCreatetime(DateUtil.DateToStr(date));
			dataThemeService.updateService(bo);
			
			List<String> name=new ArrayList<String>();
			name.add(bo.getThemename());
			KafkaTopicUtil.getKafkaConnect(KafkaTopicUtil.getKafkaConsumer(), name);
			back = "success";
		}else{//新增
			//bo.setCreateperson(customer.getLoginName());
			bo.setFwdxjbid("0");
			//bo.setThemeid(UUID.randomUUID().toString().replace("-", ""));
			bo.setCreateperson("sys");
			bo.setCreatetime(DateUtil.DateToStr(date));
			bo.setIsstart("0");
			dataThemeService.addTheme(bo);
			
			
			List<String> name=new ArrayList<String>();
			name.add(bo.getThemename());
			KafkaTopicUtil.getKafkaConnect(KafkaTopicUtil.getKafkaConsumer(), name);

			back = "success";
		}
		res.addAttr("back", back);
	}
	
	/**
	 * 改变主题状态
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/runTheme")
	public void runTheme(OptimusRequest req, OptimusResponse resp)throws OptimusException{
		String themeid=req.getParameter("themeid");//获取主体身份代码
		String state=req.getParameter("state");
		String str = "";
		if(StringUtils.isBlank(state)){
			str = "fail";
		}
		if("1".equals(state.trim())){
			//停用时需要检查是否被服务对象引用
			List list = dataThemeService.selectServiceObjectByServiceId(themeid);
			if(list!=null&list.size()>0){
				str = "exist";
			}else{
				dataThemeService.updateRunService(themeid,state);
				str = "success";
			}
		}else{
			dataThemeService.updateRunService(themeid,state);
			str = "success";
		}
		List<String> name=new ArrayList<String>();
		
		Map params=new HashMap();
		params.put("themeid", themeid);
		List<Map> themelist=dataThemeService.selectThemeDetail(params);
		for(Map themeMap : themelist){
			Iterator iter = themeMap.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			if("themename".equals(key)){
				 name.add((String)entry.getValue());
				 KafkaTopicUtil.getKafkaConnect(KafkaTopicUtil.getKafkaConsumer(),name);
				}
			}
		}
		//name.add(themelist.);
		
		resp.addAttr("back", str);
	}
	/**
	 * 获取对象树
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/themeContentTree")
    public void themeContentTree(OptimusRequest req, OptimusResponse res) throws OptimusException {
        String fwdxjbid = req.getParameter("fwdxjbid");
        String update = req.getParameter("update");
        List allFucnList = null;
        System.out.println(allFucnList);
        if(StringUtils.isNotEmpty(update)&&"true".equals(update)){
        	//修改
        	//List<Map> authFuncList = dataThemeService.selectServicemakeCheckedTree(themeid);
        	allFucnList = dataThemeService.selectThemeTreeUpdate(fwdxjbid);
        	List<Map> authFuncList = dataThemeService.selectServicemakeCheckedTree(fwdxjbid);
        	TreeUtil.makeCheckedTree(allFucnList, authFuncList, "ztid");
        }else{
        	allFucnList = dataThemeService.selectThemeTreeAdd();
        }
        res.addTree("funcTree", allFucnList);
    }
}
