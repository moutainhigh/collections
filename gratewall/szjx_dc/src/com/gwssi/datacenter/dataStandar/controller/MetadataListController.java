package com.gwssi.datacenter.dataStandar.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmCodeTableManagerBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.datacenter.dataStandar.service.MetadataService;
import com.gwssi.datacenter.model.DcStandardDataelementBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/metadataList")
public class MetadataListController extends BaseController {
	
	@Autowired
    private MetadataService metadataService;
	
	/**进入标准元数据管理页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("metadataPage")
	public void getMetadataPage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//进入标准元数据管理页面
		resp.addPage("/page/dataStandard/metadata_list.jsp");
	}
	
	/**进入标准元数据新增页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("addMetadata")
	public void addMetadata(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		//获取参数
		String type = req.getParameter("type");
		String pkDcStandardDataelement = req.getParameter("pkDcStandardDataelement");
		
		//封装数据
		resp.addAttr("type", type);
		resp.addAttr("pkDcStandardDataelement",pkDcStandardDataelement);
		
		//进入标准元数据新增页面
		resp.addPage("/page/dataStandard/metadata_edit.jsp");
	}
	
	/**根据标识符、字段名称查询标准元数据
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void getMetadata(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		Map params = req.getForm("formpanel");
		
		//根据标识符、字段名称查询标准元数据
		List list = metadataService.getMetadata(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);	
	}
	
	/**保存新增的标准元数据
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveAdd")
	public void saveMetadataAdd(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String back ="fail";
		
		//获取formpanel中填写的数据
		Map<String,String> map = req.getForm("formpanel_edit");
		DcStandardDataelementBO dcStandardDataelementBO = req.getForm("formpanel_edit", DcStandardDataelementBO.class);
				
		//通过标识符查询
		DcStandardDataelementBO oldDc = metadataService.queryMetadataByIdentifier(dcStandardDataelementBO.getIdentifier());
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//判断添加的记录是否已存在
		if(oldDc == null){
			dcStandardDataelementBO.setCreaterId(user.getUserId());
			dcStandardDataelementBO.setCreaterTime(Calendar.getInstance());
			dcStandardDataelementBO.setModifierId(user.getUserId());
		    dcStandardDataelementBO.setModifierTime(Calendar.getInstance());
		    dcStandardDataelementBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		    
		    //保存新增的信息
		    metadataService.saveMetadataAdd(dcStandardDataelementBO);
		    back="success";		
					
		}
		//封装数据并返回前台
		resp.addAttr("back", back);
				
	}
	
	
	/**设置创建、最后修改的id、name、time
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("setMessage")
	public void setMessage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String pkDcStandardDataelement = req.getParameter("pkDcStandardDataelement");
		String type = req.getParameter("type");
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//创建对象
		DcStandardDataelementBO dcStandardDataelementBO = new DcStandardDataelementBO();
		
		//新增时设置创建、最后修改信息
		if(type.equals("add") ){
			dcStandardDataelementBO.setCreaterId(user.getUserId());
			dcStandardDataelementBO.setCreaterName(user.getUserName());
			dcStandardDataelementBO.setCreaterTime(Calendar.getInstance());
			dcStandardDataelementBO.setModifierId(user.getUserId());
			dcStandardDataelementBO.setModifierName(user.getUserName());
			dcStandardDataelementBO.setModifierTime(Calendar.getInstance());
			
			//封装数据并返回前台
			resp.addForm("formpanel",dcStandardDataelementBO);
		}
		
	} 
	
	/**删除标准元数据(将有效标记设为N)
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("delete")
	public void deleteMetadata(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String pkDcStandardDataelement = req.getAttr("pkId").toString();
		
		//通过主键删除标准元数据
		metadataService.deleteMetadata(pkDcStandardDataelement);
		
		
		//封装数据并返回前台
		resp.addAttr("back", "success");
	}
	
	/**编辑、查看标准元数据，回显选中记录
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getMetadataById")
	public void getMetadataById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String pkDcStandardDataelement = req.getParameter("pkDcStandardDataelement");
		
		//通过主键查询标准元数据
		DcStandardDataelementBO dcStandardDataelementBO = metadataService.getMetadataById(pkDcStandardDataelement);
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit", dcStandardDataelementBO);
	}
	
	/**保存编辑的标准元数据
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveUpdate")
	public void saveUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		String back ="fail";
	
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//获取formpanel中填写的数据
		Map<String,String> map = req.getForm("formpanel_edit");
		DcStandardDataelementBO dcStandardDataelementBO = req.getForm("formpanel_edit", DcStandardDataelementBO.class);
		dcStandardDataelementBO.setModifierTime(Calendar.getInstance());
		dcStandardDataelementBO.setModifierId(user.getUserId());
		dcStandardDataelementBO.setModifierName(user.getUserName());
		
		//String 转换为Calendar
		String str=map.get("createrTime");
		if(StringUtils.isNotEmpty(str)){
			dcStandardDataelementBO.setCreaterTime(metadataService.changeStringToCalendar(str));	
		}
		
		//修改标准元数据
		try{
			metadataService.saveUpdate(dcStandardDataelementBO);
			back = "success";
		}catch(Exception e){
			back="fail";
		}
		resp.addAttr("back", back);
		
	}
	
	/**
	 * 查询标准规范
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryStandardSpec")
	public void queryStandardSpec(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//查询标准规范
		List list = metadataService.queryStandardSpec();
		
		//封装数据并返回
		resp.addTree("pkDcStandardSpec", list);
	}
	
}
