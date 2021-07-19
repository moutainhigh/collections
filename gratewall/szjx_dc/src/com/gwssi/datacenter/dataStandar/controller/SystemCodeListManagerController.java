package com.gwssi.datacenter.dataStandar.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmCodeTableManagerBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.datacenter.dataStandar.service.MetadataService;
import com.gwssi.datacenter.dataStandar.service.SystemCodeService;
import com.gwssi.datacenter.model.DcDmJcdmBO;
import com.gwssi.datacenter.model.DcStandardCodeindexBO;
import com.gwssi.datacenter.model.DcStandardDataelementBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

/**
 * 系统代码集
 * @author chaihw
 */
@Controller
@RequestMapping("/syscodelistManager")
public class SystemCodeListManagerController extends BaseController {
	
	@Autowired
    private SystemCodeService systemCodeService;
	
	/**系统代码集管理
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("syscodelistManagerPage")
	public void getMetadataPage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
	
		//进入标准元数据管理页面
		resp.addPage("/page/dataStandard/sysCodeSet_list.jsp");
	}
	
	/**
	 *  系统代码集查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void getCodeSet(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		Map params = req.getForm("formpanel");
				
		//根据标识符、字段名称查询标准元数据
		List list = systemCodeService.dogetCodeSet(params);
				
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);	
	}
	/**
	 * 进入系统代码集页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("toSysCodeSet")
	public void addCodeSet(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String type = req.getParameter("type");
		String dcDmId = req.getParameter("dcDmId");
		
		//封装数据
		resp.addAttr("type", type);
		resp.addAttr("dcDmId", dcDmId);
		
		//进入标准代码集新增页面
		resp.addPage("/page/dataStandard/sysCodeSet_edit.jsp");	
	}
	
	/**
	 * 新增系统代码集
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveCodeSetAdd")
	public void saveCodeSetAdd(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String back ="";
		
		//获取formpanel中填写的数据
		Map<String,String> map = req.getForm("formpanel_edit");
		DcDmJcdmBO bo = req.getForm("formpanel_edit", DcDmJcdmBO.class);
			
		int isSuccess = systemCodeService.dosave(bo);

		if(isSuccess==1){
			//封装数据并返回前台
				back="success";	
				resp.addAttr("back", back);
		}else{
			back ="fail";
			resp.addAttr("back", back);
		}			
	}
	/**
	 * 查看代码集成
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getCodeSetById")
	public void getCodeSetById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String dcDmId = req.getParameter("dcDmId");
		
		//通过主键查询标准代码集
		DcDmJcdmBO dcStandardCodeindexBO = systemCodeService.dogetCodeSetById(dcDmId);
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit", dcStandardCodeindexBO);
	}
	
	@RequestMapping("saveCodeSetUpdate")
	public void saveCodeSetUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		String back ="fail";
	
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		

		DcDmJcdmBO bo = req.getForm("formpanel_edit", DcDmJcdmBO.class);
		bo.setModid(user.getUserId());
		bo.setModname(user.getUserName());
		bo.setModtime(Calendar.getInstance());
		
		bo.setRegid(null);
		bo.setRegname(null);
		bo.setRegtime(null);
		Set  se=	bo.getNullProps();
		se.remove("regid");
		se.remove("regname");
		se.remove("regtime");
		//修改标准代码集
		try{
			systemCodeService.dosaveCodeSetUpdate(bo);
			back = "success";
		}catch(Exception e){
			back="fail";
		}
		resp.addAttr("back", back);
		
	}
	
	/**删除标准代码集(将有效标记设为N)
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("delete")
	public void deleteCodeSet(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String dcDmId = req.getAttr("dcDmId").toString();

		systemCodeService.dodeleteCodeAndValue(dcDmId);
		
		//封装数据并返回前台
		resp.addAttr("back", "success");
	}
}
