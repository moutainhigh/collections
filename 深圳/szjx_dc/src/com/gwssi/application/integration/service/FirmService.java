package com.gwssi.application.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmFirmBO;
import com.gwssi.application.model.SmLikemanBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service
public class FirmService extends BaseService {
	
	/**
	 * 通过厂商名、简称查询厂商信息
	 * @param params
	 * @return 查询到的厂商信息集合
	 * @throws OptimusException 
	 */
	public List getFirm(Map params) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String firmName = StringUtil.getMapStr(params, "firmName").trim();
		String firmNameShort = StringUtil.getMapStr(params, "firmNameShort").trim();
		
		//编写sql
		StringBuffer sql=new StringBuffer();
		sql.append("select * from SM_FIRM where EFFECTIVE_MARKER=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//firmName有值时
		if(StringUtils.isNotEmpty(firmName)){
			sql.append(" and FIRM_NAME like ?");
			listParam.add("%"+firmName+"%");
					
		}
				
		//firmNameShort有值时
		if(StringUtils.isNotEmpty(firmNameShort)){
			sql.append(" and FIRM_NAME_SHORT like ?");
			listParam.add("%"+firmNameShort+"%");
		}
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);	
	}
	
	/**
	 * 通过厂商名查询厂商信息是否已存在
	 * @param smFirmBO
	 * @return 
	 * @throws OptimusException 
	 */
	public boolean getFirmByName(SmFirmBO smFirmBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取填写的厂商名称
		String firmName = smFirmBO.getFirmName();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_FIRM where FIRM_NAME=? and EFFECTIVE_MARKER=?");
		listParam.add(firmName);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//封装结果集
		List list=dao.queryForList(SmFirmBO.class,sql.toString(),listParam);
		
		//当list有值时，该记录已存在
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
		
		
		
		
		
	}
	
	/**
	 * 保存填写的内容
	 * @param smFirmBO
	 * @throws OptimusException 
	 */
	public void saveFirm(SmFirmBO smFirmBO) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();
		
		//给主键pkSmFirm赋值
		String pkSmFirm = UuidGenerator.getUUID();
		smFirmBO.setPkSmFirm(pkSmFirm);
		
		//将有效标记设为Y
		smFirmBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
				
		//根据smFirmBO,添加记录
		dao.insert(smFirmBO);
		
	}
	
	/**
	 * 通过主键，获取选中的厂商信息
	 * @param pkSmFirm
	 * @return 选中的厂商信息集合
	 * @throws OptimusException 
	 */
	public SmFirmBO getDisplayFirm(String pkSmFirm) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//通过主键查询
		return dao.queryByKey(SmFirmBO.class,pkSmFirm);
		
	}
	
	/**
	 * 通过填写的数据修改厂商信息
	 * @param smFirmBO
	 * @throws OptimusException 
	 */
	public void updateFirm(SmFirmBO smFirmBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取修改的内容
		String pkSmFirm = smFirmBO.getPkSmFirm();
		String firmName = smFirmBO.getFirmName();
		String firmNameShort = smFirmBO.getFirmNameShort();
		String address = smFirmBO.getAddress();
		String phone = smFirmBO.getPhone();
		String fax = smFirmBO.getFax();
		String zipCode = smFirmBO.getZipCode();
		String remarks = smFirmBO.getRemarks();
		String modifierId = smFirmBO.getModifierId();
		String modifierName = smFirmBO.getModifierName();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update SM_FIRM set FIRM_NAME=?,FIRM_NAME_SHORT=?,ADDRESS=?,PHONE=?,FAX=?,"
				+ "ZIP_CODE=?, REMARKS=?,MODIFIER_ID=?,MODIFIER_Name=?,MODIFIER_TIME=? where PK_SM_FIRM=?");
		listParam.add(firmName);
		listParam.add(firmNameShort);
		listParam.add(address);
		listParam.add(phone);
		listParam.add(fax);
		listParam.add(zipCode);
		listParam.add(remarks);
		listParam.add(modifierId);
		listParam.add(modifierName);
		listParam.add(smFirmBO.getModifierTime());
		listParam.add(pkSmFirm);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 通过主键删除厂商信息
	 * @param pkSmFirm
	 * @throws OptimusException 
	 */
	public void deleteFirm(String pkSmFirm) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql语言
		String sql= "update SM_FIRM set EFFECTIVE_MARKER=? where PK_SM_FIRM=?";
		listParam.add(AppConstants.EFFECTIVE_N);
		listParam.add(pkSmFirm);
		
		//执行sql
		dao.execute(sql.toString(), listParam);	
		
		
	}
	
	/**
	 * 通过联系人名、联系电话，获取联系人信息
	 * @param params pkSmFirm
	 * @return 查询到的联系人信息集合
	 * @throws OptimusException 
	 */
	public List getLinkman(Map params, String pkSmFirm) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String smLikeman = StringUtil.getMapStr(params, "smLikeman").trim();
		/*String phone = StringUtil.getMapStr(params, "phone").trim();*/
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_LIKEMAN where PK_SM_FIRM=? and EFFECTIVE_MARKER=?");
		listParam.add(pkSmFirm);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//联系人名不为空时
		if(StringUtils.isNotEmpty(smLikeman)){
			sql.append(" and SM_LIKEMAN like ?");
			listParam.add("%"+smLikeman+"%");
				
	    }
			
		//联系电话不为空时
		/*if(StringUtils.isNotEmpty(phone)){
			sql.append(" and PHONE like ?");
			listParam.add("%"+phone+"%");
		}*/
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);
	}
	
	public boolean getLinkmanByNameAndPhone(SmLikemanBO smLikemanBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取填写的厂商主键、联系人名
		String pkSmFirm = smLikemanBO.getPkSmFirm();
		String smLikeman = smLikemanBO.getSmLikeman();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_LIKEMAN where PK_SM_FIRM=? and SM_LIKEMAN=? and EFFECTIVE_MARKER=?");
		listParam.add(pkSmFirm);
		listParam.add(smLikeman);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//封装结果集
		List list=dao.queryForList(SmLikemanBO.class,sql.toString(),listParam);
				
		//当list有值时，该记录已存在
		if(list!=null&&!list.isEmpty()){
			return true;
		}
			return false;
		
	}
	
	/**
	 * 保存填写的联系人信息
	 * @param smLikemanBO
	 * @throws OptimusException 
	 */
	public void saveLinkman(SmLikemanBO smLikemanBO) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();
		
		//给主键pkSmLikeman赋值
		String pkSmLikeman = UuidGenerator.getUUID();
		smLikemanBO.setPkSmLikeman(pkSmLikeman);
		
		//将有效标记设为Y
		smLikemanBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
				
		//根据smLikemanBO,添加记录
		dao.insert(smLikemanBO);
		
	}
	
	/**
	 * 通过主键删除联系人信息
	 * @param pkSmLikeman
	 * @throws OptimusException 
	 */
	public void deleteLinkman(String pkSmLikeman) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql语言
		String sql= "update SM_LIKEMAN set EFFECTIVE_MARKER=? where PK_SM_LIKEMAN=?";
		listParam.add(AppConstants.EFFECTIVE_N);
		listParam.add(pkSmLikeman);
		
		//执行sql
		dao.execute(sql.toString(), listParam);	
		
	}
	
	/**
	 * 通过主键查询选中的联系人信息
	 * @param pkSmLikeman
	 * @return 查询到的联系人信息集合
	 * @throws OptimusException 
	 */
	public SmLikemanBO getDisplayLinkman(String pkSmLikeman) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//通过主键查询联系人信息
		return dao.queryByKey(SmLikemanBO.class,pkSmLikeman);
	}
	
	/**
	 * 通过填写数据修改联系人信息
	 * @param params pkSmFirm
	 * @throws OptimusException 
	 */
	public void updateLinkman(SmLikemanBO smLikemanBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取修改的内容
		String pkSmLikeman = smLikemanBO.getPkSmLikeman();
		String smLikeman = smLikemanBO.getSmLikeman();
		String phone = smLikemanBO.getPhone();
		String email = smLikemanBO.getEmail();
		String remarks = smLikemanBO.getRemarks();
		String modifierId = smLikemanBO.getModifierId();
		String modifierName = smLikemanBO.getModifierName();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update SM_LIKEMAN set SM_LIKEMAN=?,PHONE=?,EMAIL=?,REMARKS=?,MODIFIER_ID=?,"
				+ "MODIFIER_NAME=?,MODIFIER_TIME=? where PK_SM_LIKEMAN=?");
		listParam.add(smLikeman);
		listParam.add(phone);
		listParam.add(email);
		listParam.add(remarks);
		listParam.add(modifierId);
		listParam.add(modifierName);
		listParam.add(smLikemanBO.getModifierTime());
		listParam.add(pkSmLikeman);
		
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 查询所有厂商 
	 * @param 
	 * @return 返回所有厂商
	 * @throws OptimusException 
	 */
	public List getAllFirm() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		StringBuffer sql=new StringBuffer();
		sql.append("select distinct (FIRM_NAME_SHORT) as text , PK_SM_FIRM as value from SM_FIRM where EFFECTIVE_MARKER = ? ");
		listParam.add(AppConstants.EFFECTIVE_Y);
		return dao.pageQueryForList(sql.toString(), listParam);	
	}
	
	/**
	 * 根据厂商查询所有联系人 
	 * @param 厂商Id
	 * @return 返回所有联系人
	 * @throws OptimusException 
	 */
	public List getAllLinkman(String pkSmFirm) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		StringBuffer sql=new StringBuffer();
		sql.append("select SM_LIKEMAN as text , PK_SM_LIKEMAN as value from SM_LIKEMAN where EFFECTIVE_MARKER = ? ");
		listParam.add(AppConstants.EFFECTIVE_Y);
		if(StringUtils.isNotEmpty(pkSmFirm)){
			sql.append(" and PK_SM_FIRM = ? ");
			listParam.add(pkSmFirm);
		}
		return dao.pageQueryForList(sql.toString(), listParam);	
	}

	

	

}
