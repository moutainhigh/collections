package com.gwssi.optimus.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.demo.domain.BeWkEntDemoBO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;
import com.gwssi.rodimus.validate.api.Assert;

/**
 * 
 * 根据业务需要创建方法。
 * 
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service
public class EntDemoService {

	/**
	 * 新增企业。
	 * 
	 * @param bo
	 */
	public void add(BeWkEntDemoBO bo){
		//0. 参数判断
		Assert.notNull(bo, "企业信息");
		Assert.notBlank(bo.getEntName(), "企业名称");
		Assert.notBlank(bo.getEntType(), "企业类型");
		
		//1. 设置默认值
		String entId = UUIDUtil.getUUID();
		bo.setEntId(entId);
		Calendar now = DateUtil.getCurrentTime();
		String regOrg = determineRegOrg(bo);
		bo.setRegOrg(regOrg);
		bo.setAppDate(now);
		
		//3. 更新时间戳
		bo.setTimestamp(now);
		
		//4. 保存到数据
		DaoUtil.getInstance().insert(bo);
	}

	/**
	 * 判断登记审核机关。
	 * 
	 * @param bo
	 * @return
	 */
	private String determineRegOrg(BeWkEntDemoBO bo) {
		return "110108000";
	}
	/**
	 * 删除企业。
	 * 
	 * @param bo
	 */
	public void delete(String entId){
		//0. 判断参数
		Assert.notBlank(entId, "企业编号");

		// TODO 确认删除前是否要判断状态
		// TODO 确认是否要删除其他表的记录
		
		//1. 数据库操作
		DaoUtil.getInstance().delete(BeWkEntDemoBO.class, entId);
	}
	/**
	 * 更新企业信息。
	 * 
	 * @param bo
	 */
	public void update(BeWkEntDemoBO bo){
		//0. 参数判断
		Assert.notNull(bo, "企业信息");
		Assert.notBlank(bo.getEntId(), "企业编号");
		
		//1. 更新时间戳
		Calendar now = DateUtil.getCurrentTime();
		bo.setTimestamp(now);
		
		//2. 数据库操作
		DaoUtil.getInstance().update(bo);
	}
	
	/**
	 * 设置审核机关。
	 * 
	 * @param entId
	 * @param regOrg
	 */
	public void setRegOrg(String entId , String regOrg){
		Assert.notBlank(entId, "企业编号");
		Assert.notBlank(regOrg, "审核机关编号");
		
		String sql = "update be_wk_entdemo t set t.reg_org = ? , t.timestamp = ? where t.ent_id = ? ";
		Calendar now = DateUtil.getCurrentTime();
		DaoUtil.getInstance().execute(sql, regOrg, now, entId);
	}
	/**
	 * 查询单个企业信息。
	 * 
	 * @param id 企业编号
	 */
	public BeWkEntDemoBO get(String id){
		//0. 参数判断
		Assert.notBlank(id, "企业编号");
		//1. 查询数据库
		BeWkEntDemoBO ret = DaoUtil.getInstance().get(BeWkEntDemoBO.class, id);
		//2. 返回
		return ret;
	}
	
	/**
	 * 查询企业信息，分页查询。
	 * 
	 * @param condition
	 * @return
	 */
	public List<BeWkEntDemoBO> getList(BeWkEntDemoBO condition){
		List<BeWkEntDemoBO> ret = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from be_wk_entdemo ent where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(condition!=null){
			if(StringUtil.isNotBlank(condition.getEntName())){
				sql.append(" and ent.ent_name like ? ");
				params.add("%"+condition.getEntName()+"%");
			}
			
			if(StringUtil.isNotBlank(condition.getEntType())){
				sql.append(" and ent.ent_type = ? ");
				params.add(condition.getEntType());
			}
		}
		ret = DaoUtil.getInstance().pageQueryForListBo(sql.toString(), BeWkEntDemoBO.class, params);
		
		return ret;
	}
	
}
