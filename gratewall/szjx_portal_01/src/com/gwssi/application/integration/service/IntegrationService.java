/**
 * 
 */
package com.gwssi.application.integration.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.FileUtil;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.core.web.fileupload.OptimusFileItem;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.UuidGenerator;

/**
 * @author xiaohan
 *
 */
@Service
public class IntegrationService extends BaseService {
	
	@Autowired
	SmRoleService roleservice;
	/**
	 * 应用集成-系统新增
	 * 
	 * @param integrationbo 系统bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void saveIntegration(SmSysIntegrationBO integrationbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		if(StringUtils.equals(AppConstants.SYSTEM_TYPE_NOSYS,integrationbo.getSystemType())){
			//为业务时
			integrationbo.setParentCode(null);
			integrationbo.setPkSmFirm(null);
			integrationbo.setPkSmLikeman(null);
			integrationbo.setLevelCode(null);
			integrationbo.setIntegratedUrl(null);
			integrationbo.setSystemState(null);
		}
		dao.insert(integrationbo);
	}
	
	/**
	 * 应用集成-根据id系统查询
	 * 
	 * @param pkSysIntegration 系统id主键
	 * @return
	 * @throws OptimusException
	 */
	public SmSysIntegrationBO findIntegrationById(String pkSysIntegration) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		SmSysIntegrationBO integrationbo = dao.queryByKey(SmSysIntegrationBO.class, pkSysIntegration);
		return integrationbo;
	}
	
	/**
	 * 应用集成-系统更新
	 * 
	 * @param integrationbo 系统bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void updateIntegration(SmSysIntegrationBO integrationbo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		if(StringUtils.equals(AppConstants.SYSTEM_TYPE_NOSYS,integrationbo.getSystemType())){
			//为业务时
			integrationbo.setParentCode(null);
			integrationbo.setPkSmFirm(null);
			integrationbo.setPkSmLikeman(null);
			integrationbo.setLevelCode(null);
			integrationbo.setIntegratedUrl(null);
			integrationbo.setSystemState(null);
		}
		
		dao.update(integrationbo);
	}
	
	/**
	 * 应用集成-系统查询
	 * 
	 * @param paramObject 系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<SmSysIntegrationBO> findByIdIntegration(List paramObject) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<SmSysIntegrationBO> integrationList = dao.queryByKey(SmSysIntegrationBO.class, paramObject);
		return integrationList;
	}
	
	/**
	 * 应用集成-系统查询
	 * 
	 * @param paramObject 系统查询字段对象集合  flag标识位，当前用户是否为超级管理员
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findIntegration(Map<String,String> parmas ,String flag ,List listCode) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*, "+flag+" as ISSUPER ,(select FIRM_NAME_SHORT from SM_FIRM where PK_SM_FIRM = a.PK_SM_FIRM) as FIRMNAME , (select SM_LIKEMAN from SM_LIKEMAN where PK_SM_LIKEMAN = a.PK_SM_LIKEMAN) as LINKMAN from SM_SYS_INTEGRATION a where a.EFFECTIVE_MARKER = ? ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		if("0".equals(flag)){
			sql.append(" and a.PK_SYS_INTEGRATION in ( " );
			for(Object code:listCode){
				sql.append(" ? , ");
				listParam.add(code.toString());
			}
			sql.append(" ? ) " );
			//0占位符
			listParam.add("0");
		}
		
		if(parmas != null){
			String systemName = parmas.get("systemName").trim();
			if(!"".equals(systemName)){
				sql.append(" and  a.SYSTEM_NAME like ? ");
				listParam.add("%"+systemName+"%");
			}
			String systemCode = parmas.get("systemCode").trim();
			if(!"".equals(systemCode)){
				sql.append(" and ( lower(a.SYSTEM_CODE) like ? or upper(a.SYSTEM_CODE) like ?  ) ");
				listParam.add("%"+systemCode+"%");
				listParam.add("%"+systemCode+"%");
			}
		}
		sql.append(" order by a.CREATER_TIME DESC"); 
		List<Map> integrationList = dao.pageQueryForList(sql.toString(), listParam);
		return integrationList;
	}
	
	/**
	 * 应用集成-系统删除
	 * 
	 * @param pkSysIntegrations 系统Id编号集合
	 * @return
	 * @throws OptimusException
	 */
	public void deleteIntegration(String pkSysIntegrations) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.deleteByKey(SmSysIntegrationBO.class, pkSysIntegrations);
	}
	
	/**
	 * 应用集成-系统查询总个数
	 * 
	 * @return int
	 * @throws OptimusException
	 */
	public int getNumIntegration() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select count(1) from SM_SYS_INTEGRATION"; 
		return dao.queryForInt(sql, null);
	}
	

	/**
	 * 查询业务域
	 * @param 
	 * @return 返回业务域
	 * @throws OptimusException 
	 */
	public List getBusiDomain() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		StringBuffer sql=new StringBuffer();
		//sql.append("select distinct (BUSI_DOMAIN_NAME) as text , PK_BUSI_DOMAIN as value from SM_BUSI_DOMAIN where EFFECTIVE_MARKER = ? ");
		//查询域和第一级
		sql.append("select  distinct (t.system_name) as text , t.pk_sys_integration as value  from sm_sys_integration t where t.effective_marker= ? and (t.system_type = ? OR (t.level_code= ? and t.integrated_url is null ) )");
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(AppConstants.SYSTEM_TYPE_NOSYS);
		listParam.add(AppConstants.SYSTEM_lEVEL_CODE);
		return dao.queryForList(sql.toString(), listParam);	
	}
	/**
	 * 保证事物放入这里
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void dosaveIntegration(OptimusRequest req, OptimusResponse resp) throws Exception {
		
		SmSysIntegrationBO integration = (SmSysIntegrationBO) req.getForm(
				"formpanel", SmSysIntegrationBO.class); 
/*		String busiDomainName = req.getAttr("busiDomainName").toString();
		integration.setBusiDomainName(busiDomainName);*/
		Map imageInfo = new HashMap();
		List<OptimusFileItem> imageList = req.getUploadList("formpanel",
				"systemImgUrlCode");
		if (!imageList.isEmpty()) {
			String rootDir = ConfigManager.getProperty("rootDir");
			String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
			//String uploadPath = req.getHttpRequest().getRealPath("/") ;
			String uploadPath = rootDir + File.separator + uploadTempDir;
			String systemImgUrl = integration.getSystemImgUrl();
			if(!"".equals(systemImgUrl)){
				FileUtil.deleteFile(uploadPath + systemImgUrl);
				imageInfo = FileUtil.saveFile(imageList.get(0), uploadPath);
			}else{
				imageInfo = FileUtil.saveFile(imageList.get(0), uploadPath);
			}
			integration.setSystemImgUrl(imageInfo.get("path").toString().replace("\\", "/"));
		}
		
		String pkSysIntegration = integration.getPkSysIntegration();
		if("".equals(pkSysIntegration)){
			pkSysIntegration = UuidGenerator.getUUID();
			integration.setPkSysIntegration(pkSysIntegration);
			integration.setModifierId(integration.getCreaterId());
			integration.setModifierTime(integration.getCreaterTime());
			integration.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
			int countintegration = this.getNumIntegration() + 1;
			integration.setOrderNo(new BigDecimal(countintegration));
			this.saveIntegration(integration);
			
			if(!StringUtils.equals(AppConstants.SYSTEM_TYPE_NOSYS,integration.getSystemType())){
				//不是业务系统时创建管理员
				SmRoleBO rolebo1 = roleservice.findRoleById(integration.getSystemCode()+"_ADMIN");
				if(rolebo1==null){
					SmRoleBO rolebo = new SmRoleBO();
					rolebo.setPkSysIntegration(pkSysIntegration);
					rolebo.setRoleCode(integration.getSystemCode()+"_ADMIN");
					rolebo.setRoleName(integration.getSystemName()+"系统管理员");
					rolebo.setRoleType(AppConstants.ROLE_TYPE_SYS);
					rolebo.setRoleState(integration.getSystemState());
					rolebo.setCreaterId(integration.getCreaterId());
					rolebo.setCreaterTime(integration.getCreaterTime());
					rolebo.setModifierId(integration.getCreaterId());
					rolebo.setModifierTime(integration.getCreaterTime());
					rolebo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
					int countrole = roleservice.getNumIntegration() + 1;
					rolebo.setOrderNo(new BigDecimal(countrole));
					roleservice.saveRole(rolebo);		
				}else{
					if(StringUtils.equals(rolebo1.getEffectiveMarker(),AppConstants.EFFECTIVE_N)){
						rolebo1.setRoleName(integration.getSystemName()+"系统管理员");
						rolebo1.setRoleState(integration.getSystemState());
						rolebo1.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
						rolebo1.setPkSysIntegration(integration.getPkSysIntegration());
						roleservice.updateSmRole(rolebo1);
					}else{
						
						throw new Exception("存在一个相同的角色代码");
					}
					
				}
			
			}

		}else{
			Calendar calendar = Calendar.getInstance();
			HttpSession session = WebContext.getHttpSession();
			User user = (User) session.getAttribute(OptimusAuthManager.USER);
			integration.setModifierId(user.getUserId());
			integration.setModifierTime(calendar);
			this.updateIntegration(integration);
			
			SmRoleBO rolebo = roleservice.findRoleById(integration.getSystemCode()+"_ADMIN");
			if(StringUtils.equals(AppConstants.SYSTEM_TYPE_NOSYS,integration.getSystemType())){
				//为业务时
				if(rolebo != null){
					rolebo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
					roleservice.updateSmRole(rolebo);	
				}
			}else{
				if(rolebo != null){
					rolebo.setRoleName(integration.getSystemName()+"系统管理员");
					rolebo.setRoleState(integration.getSystemState());
					rolebo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
					rolebo.setPkSysIntegration(integration.getPkSysIntegration());
					roleservice.updateSmRole(rolebo);
				}else{
					SmRoleBO rolebo1 = new SmRoleBO();
					rolebo1.setPkSysIntegration(pkSysIntegration);
					rolebo1.setRoleCode(integration.getSystemCode()+"_ADMIN");
					rolebo1.setRoleName(integration.getSystemName()+"系统管理员");
					rolebo1.setRoleType(AppConstants.ROLE_TYPE_SYS);
					rolebo1.setRoleState(integration.getSystemState());
					rolebo1.setCreaterId(integration.getCreaterId());
					rolebo1.setCreaterTime(integration.getCreaterTime());
					rolebo1.setModifierId(integration.getCreaterId());
					rolebo1.setModifierTime(integration.getCreaterTime());
					rolebo1.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
					int countrole = roleservice.getNumIntegration() + 1;
					rolebo1.setOrderNo(new BigDecimal(countrole));
					roleservice.saveRole(rolebo1);
				}
			}
			

		}
		
	}
}
