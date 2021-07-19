package com.gwssi.ebaic.apply.util;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.ebaic.domain.BeWkEntmemberBO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.UUIDUtil;
import com.gwssi.torch.util.StringUtil;


public class SetMemberPositionUtil {
	
	/**
	 * 设置法定代表人
	 * @param entmemberIdandPosition   entmemberid_position
	 * @param gid
	 */
	public static void setLegal(String entmemberIdandPosition,String gid,String mobile){
		// 0. 参数检查
		if(StringUtils.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		if(StringUtils.isBlank(entmemberIdandPosition)){
			throw new EBaicException("entmemberId不能为空。");
		}
		//主要人员表主键
		String entmemberId = entmemberIdandPosition.substring(0, entmemberIdandPosition.indexOf("_"));
		//选为法人的职务
		String position = entmemberIdandPosition.substring(entmemberIdandPosition.indexOf("_")+1,entmemberIdandPosition.length());
		BeWkEntmemberBO mbrBo = DaoUtil.getInstance().get(BeWkEntmemberBO.class, entmemberId);
		
		if(mbrBo==null){
			throw new EBaicException("entmemberId参数不正确。");
		}
		
		//1.将所有主要成员法定代表人字段  更新  为空
		String sql = "update be_wk_entmember ent set ent.le_rep_sign='' where ent.gid = ?";
		DaoUtil.getInstance().execute(sql, gid);
		
		//2.将entmemberId的主要成员设置为法定代表人
		sql = " update be_wk_entmember ent set ent.le_rep_sign='1' where ent.entmember_id = ? ";
		DaoUtil.getInstance().execute(sql, entmemberId);
		
		//3.保存法定代表人信息到be_wk_le_rep表
		sql = "select count(1) as cnt from be_wk_le_rep l where l.gid = ?";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		
		Calendar now = DateUtil.getCurrentTime();
		sql=" select job.position from  be_wk_job  job where job.entmember_id = ? and job.position=?";
		Map<String, Object> row = DaoUtil.getInstance().queryForRow(sql, entmemberId,position);
		if(row==null){
			throw new EBaicException("未查到该主要人员的职务");
		}
		
		
		if(cnt<1){
			sql = "insert into be_wk_le_rep(le_rep_id,ent_id,le_rep_name,le_rep_cer_type,le_rep_cer_no,"
					+ "le_rep_job_code,le_rep_mob,le_rep_email,flag,gid,"
					+ "timestamp) values (?,?,?,?,?, ?,?,?,?,?, ?)";
			String leRepId = UUIDUtil.getUUID();
			String flag = "1";
			DaoUtil.getInstance().execute(sql, leRepId,mbrBo.getEntId(),mbrBo.getName(),mbrBo.getCerType(),mbrBo.getCerNo(), position,mobile,mbrBo.getEmail(),flag,gid,now);
		}else{
			sql = "update be_wk_le_rep set le_rep_name=?, le_rep_cer_type=?, le_rep_cer_no=?, le_rep_job_code=?, le_rep_mob=?, le_rep_email=?, timestamp=? where gid=?";
			DaoUtil.getInstance().execute(sql, mbrBo.getName(),mbrBo.getCerType(),mbrBo.getCerNo(),position,mobile,mbrBo.getEmail(),now,gid);
		}
	}
	/**
	 * 设置董事长
	 * @param entmemberId
	 * @param gid
	 */
	public static void setPresident(String entmemberId,String gid) {
		//1.将所有职位为董事长的修改为董事
		String sql = " update be_wk_job job set job.position='432A' where job.position='431A' and job.gid = ? ";
		DaoUtil.getInstance().execute(sql,gid);
		
		//2.将所有entmemberId职位更新为董事长
		sql = " update be_wk_job job set job.position='431A' where job.entmember_id = ? and job.position_type='1' ";
		DaoUtil.getInstance().execute(sql, entmemberId);
	}
	/**
	 * 设置    监事会主席
	 * @param entmemberId
	 * @param gid
	 */
	public static void setChairman(String entmemberId){
		String sql = "select m.gid from be_wk_entmember m where m.entmember_id = ?";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, entmemberId);
		String gid = StringUtil.safe2String(row.get("gid"));
		
		//1、将所有职位为监事会主席的修改为监事
		sql = " update be_wk_job job set job.position='408A' where job.position='408B' and job.gid=? and job.position_type='3' ";
		DaoUtil.getInstance().execute(sql, gid);
		//2.将所有entmemberId职位更新为监事会主席
		sql = " update be_wk_job job set job.position='408B' where job.entmember_id = ? and job.position_type='3' ";
		DaoUtil.getInstance().execute(sql, entmemberId);
	}
	
}
