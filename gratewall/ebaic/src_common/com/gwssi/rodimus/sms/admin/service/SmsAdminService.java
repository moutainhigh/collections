package com.gwssi.rodimus.sms.admin.service;

import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.sms.domain.SmsWkTemplateBO;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.UUIDUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.service.BaseService;


/**
 * 短信模板后台管理。
 * 
 * @author liuhailong
 */
@Service(value = "smsAdminService")
public class SmsAdminService extends BaseService {

	/**
	 * @return 返回模板列表
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getList() throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.* from sms_wk_template t where t.BUSI_TYPE='A' order by t.SUB_BUSI_TYPE_CODE asc";
        List<String> params = null;
		List<Map> list = dao.pageQueryForList(sql, params);
		if(list==null || list.isEmpty()){
			return new ArrayList<Map>();
		}else{
			return list;
		}
	}

	/**
	 * 根据id加载。
	 * 
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	public SmsWkTemplateBO load(String id)throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		SmsWkTemplateBO bo = dao.queryByKey(SmsWkTemplateBO.class, id);
		return bo;
	}
	
	public void saveTemplate(SmsWkTemplateBO bo){
		
	}
	
	/**
	 * 增加新模板。
	 * 
	 * @param bo
	 * @return
	 * @throws OptimusException
	 */
	public SmsWkTemplateBO add(SmsWkTemplateBO bo) throws OptimusException{
		
//		Assert.assertNotNull(bo.getSubBusiType(), "业务类型");
//		Assert.assertNotNull(bo.getConetnt(), "模板内容");
//		Assert.assertNotNull(bo.getPriority(), "优先级");
//		
		bo.setId(UUIDUtil.getUUID());
		bo.setBusiType("A");
		bo.setFlag("1");
		bo.setTimestamp(DateUtil.getCurrentTime());
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(bo);
		
		return bo;
	}

	/**
	 * 保存模板信息。
	 * 
	 * @param bo
	 * @throws OptimusException
	 */
	public void save(SmsWkTemplateBO bo) throws OptimusException{
		
//		Assert.assertNotNull(bo.getSubBusiType(), "业务类型");
//		Assert.assertNotNull(bo.getConetnt(), "模板内容");
//		Assert.assertNotNull(bo.getPriority(), "优先级");
//		Assert.assertNotNull(bo.getId(), "模板编号");
		
		bo.setTimestamp(DateUtil.getCurrentTime());
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
	}
	
	/**
	 * 停用。
	 * 
	 * @param id
	 * @throws OptimusException
	 */
	public void disable(String id) throws OptimusException {
		SmsWkTemplateBO bo = this.load(id);
		
		bo.setFlag("0");
		bo.setTimestamp(DateUtil.getCurrentTime());
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
	}

	/**
	 * 启用。
	 * 
	 * @param id
	 * @throws OptimusException
	 */
	public void enable(String id) throws OptimusException {
		SmsWkTemplateBO bo = this.load(id);
		
		bo.setFlag("1");
		bo.setTimestamp(DateUtil.getCurrentTime());
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
	}
}
