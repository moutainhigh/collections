package com.gwssi.datacenter.dataStandar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcBusiObjectBO;
import com.gwssi.datacenter.model.DcDmJcdmBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "systemCodeService")
public class SystemCodeService extends BaseService{
	
	/**
	 * 根据标识符、代码集名称查询标准代码集
	 * @return 标准代码集集合
	 * @throws OptimusException 
	 */
	public List dogetCodeSet(Map params) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取查询条件
		String dcDmDm = StringUtil.getMapStr(params, "dcDmDm").trim();
		String dcDmMc = StringUtil.getMapStr(params, "dcDmMc").trim();
		String pkDcBusiObject=StringUtil.getMapStr(params, "pkDcBusiObject").trim();
		//编写sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_DM_JCDM where EFFECTIVE_MARKER=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当identifier不为空时
		if(StringUtils.isNotEmpty(dcDmDm)){
			dcDmDm=dcDmDm.toLowerCase();
			sql.append(" and  lower(dc_dm_dm) like ?  ");
			listParam.add("%"+dcDmDm+"%");

		}
		
		//当codeindexName不为空时
		if(StringUtils.isNotEmpty(dcDmMc)){
			dcDmMc =dcDmMc.toLowerCase();
			sql.append(" and lower(dc_dm_mc) like ?");
			listParam.add("%"+dcDmMc+"%");
		}
		
		if(StringUtils.isNotEmpty(pkDcBusiObject)){
			dcDmMc =dcDmMc.toLowerCase();
			sql.append(" and lower(PK_DC_BUSI_OBJECT) like ?");
			listParam.add("%"+pkDcBusiObject+"%");
		}
		
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);	
	}
	
	
	/**
	 * 把字符串时间转换为Calendar类型
	 * 
	 * @param str
	 * @return
	 */
	public Calendar changeStringToCalendar(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}
	

	

	

	public DcDmJcdmBO doqueryCodeSetByCode(String dcDmDm) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		DcDmJcdmBO oldDc = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_DM_JCDM where dc_dm_dm=? and EFFECTIVE_MARKER=?");
		listParam.add(dcDmDm);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		List list = dao.queryForList(DcDmJcdmBO.class, sql.toString(), listParam);
		
		//当list不为空时
		if(list!=null&&!list.isEmpty()){
			oldDc = (DcDmJcdmBO) list.get(0);
		}
		
		return oldDc;
	}

	/**
	 * 保存bo
	 * 
	 * @param bo
	 * @return 0 为位置错误 1为成功
	 * @throws OptimusException
	 */
	public int dosave(DcDmJcdmBO bo) throws OptimusException {
	   // String dcDmDm = bo.getDcDmDm();
	  //  DcDmJcdmBO oldbo= this.doqueryCodeSetByCode(dcDmDm);
		DcBusiObjectBO  dcbusi=null;
	   if(StringUtils.isNotEmpty(bo.getPkDcBusiObject())){
			dcbusi= this.doquerySysNo(bo.getPkDcBusiObject());

	   }
	  
	   if(dcbusi==null){
		   return 0;
	   }else{
		   bo.setBusiObjectCode(dcbusi.getBusiObjectCode());
	   }
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
	    
	    bo.setRegname(user.getUserName());
	    bo.setRegtime(Calendar.getInstance());
	    bo.setRegid(user.getUserId());
	    bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
	    bo.setDataFrom(AppConstants.DATA_FROM_WRITE);//录入方式 手工录入
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(bo);
	    
		return 1;
	}

	//查询业务系统编号
	public DcBusiObjectBO doquerySysNo(String string) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//封装结果集，通过主键查询
		return dao.queryByKey(DcBusiObjectBO.class, string);
	}


	public DcDmJcdmBO dogetCodeSetById(String dcDmId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//封装结果集，通过主键查询
		return dao.queryByKey(DcDmJcdmBO.class, dcDmId);
	}


	public void dosaveCodeSetUpdate(DcDmJcdmBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
		
	}


	public void deleteCodeData(String dcdmid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		DcDmJcdmBO oldDc = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("delete from dc_dm_jcdm_fx t  where t.dc_dm_id=?");
		listParam.add(dcdmid);

		dao.execute(sql.toString(), listParam);	


	}


	public void dodeleteCodeAndValue(String dcDmId) throws OptimusException {
		this.deleteCodeData(dcDmId);
		DcDmJcdmBO bo = new DcDmJcdmBO();
		bo.setDcDmId(dcDmId);
		bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
		this.dosaveCodeSetUpdate(bo);
	}

}
