package com.gwssi.application.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmNoticeBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service(value = "messageService")
public class MessageService extends BaseService{
	
	/**
	 * 通过标题、创建时间，获取通知公告信息
	 * @param params
	 * @param username 
	 * @return 通知公告信息集合
	 * @throws OptimusException 
	 */
	public List queryMessage(Map params, String userid) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取查询条件
		String title = StringUtil.getMapStr(params, "title").trim();
		String createrTime = StringUtil.getMapStr(params, "createrTime").trim();
		
		//编写sql
		String sql="select * from SM_NOTICE where CREATER_ID=? and EFFECTIVE_MARKER=?";
		listParam.add(userid);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//title不为空
		if(StringUtils.isNotEmpty(title)){
			sql+=" and TITLE like ?";
			listParam.add("%"+title+"%");
		}
		
		//创建时间不为空
		if(StringUtils.isNotEmpty(createrTime)){
			sql+=" and to_char(CREATER_TIME,'YYYY-MM-DD' )=?";
			listParam.add(createrTime);
		}
		
		//封装数据
		return dao.pageQueryForList(sql.toString(), listParam);
	}
	
	/**岗位资源树查询
	 * @param paramObject 系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPostTree() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		
		//编写sql
		sql.append(" select b.ORGAN_NAME as NAME,b.ORGAN_ID as ID ,b.PARENT_ID as P_ID ,b.ORGAN_TYPE as TYPE from ");
		sql.append(" ( select * from (select o.ORGAN_NAME,o.ORGAN_CODE, o.ORGAN_TYPE,s.PARENT_ID,s.STRU_PATH,s.STRU_ORDER,o.ORGAN_ID,s.STRU_ID from HR_ORGAN o, HR_STRU s where o.ORGAN_CODE = s.ORGAN_ID ) A start with PARENT_ID= '1' ");
		sql.append(" connect by prior ORGAN_CODE = PARENT_ID and ORGAN_TYPE <> '8' ) b union select POST_NAME as NAME , ID , ORGAN_ID as PID , '8' as TYPE from HR_POST ");
		
		//封装结果集
		List<Map> RoleList = dao.queryForList(sql.toString(), null);
		return RoleList;
	}
	
	/**
	 * 保存新增的消息
	 * @param smNoticeBO
	 * @throws OptimusException 
	 */
	public void saveMessage(SmNoticeBO smNoticeBO) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//给主键pkNotice赋值
		String pkNotice = UuidGenerator.getUUID();
		smNoticeBO.setPkNotice(pkNotice);
		smNoticeBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		
		//根据smNoticeBO,添加记录
		dao.insert(smNoticeBO);
		
	}
	
	/**
	 * 维护时数据的回显
	 * @param pkNotice
	 * @return SmNoticeBO 查询到的消息的集合
	 * @throws OptimusException 
	 */
	public SmNoticeBO getDisplayDef(String pkNotice) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//封装结果集，通过主键查询
		return dao.queryByKey(SmNoticeBO.class, pkNotice);
	}
	
	/**
	 * 维护消息
	 * @param smNoticeBO
	 * @throws OptimusException 
	 */
	public void updateMessage(SmNoticeBO smNoticeBO) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取修改的数据
		String pkNotice = smNoticeBO.getPkNotice();
		String title = smNoticeBO.getTitle();
		String content = smNoticeBO.getContent();
		String sendTo = smNoticeBO.getSendTo();
		String modifierId = smNoticeBO.getModifierId();
		String modifierName = smNoticeBO.getModifierName();
		
		//编写sql
		String sql = "update SM_NOTICE set TITLE=?,CONTENT=?,SEND_TO=?,MODIFIER_ID=?,modifier_NAME=?, "
				+ "MODIFIER_TIME=? where PK_NOTICE=?";
		listParam.add(title);
		listParam.add(content);
		listParam.add(sendTo);
		listParam.add(modifierId);
		listParam.add(modifierName);
		listParam.add(smNoticeBO.getModifierTime());
		listParam.add(pkNotice);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
	}

}
