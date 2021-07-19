package com.gwssi.ebaic.apply.setup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.SetMemberPositionUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;

/**
 * 主要人员保存和下一步。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("setupMemberService")
public class SetupMemberService {
	
	/**
	 * 主要人员页签  保存和下一步方法
	 * @param request
	 * @param response
	 * @throws OptimusException 
	 */
	public void runRule(String gid,String isBoard,String isSuped,String entId,String entMob) throws OptimusException  {
		
		
		
//		IPersistenceDAO dao = new DmjService().getPersistenceDAO();
//		//更新主要人员表中的法人代表标记
//		String sql = "update be_wk_entmember t set t.le_rep_sign = 1 where entmember_id = ?";
//		ArrayList<Object> params = new ArrayList<>();
//		params.add(entId);
//		dao.execute(sql, params);
		
		
		//去掉没有job表关联的人员表记录
		//本应该对人员新增环节处理，改动较大，这里是取巧的方式
		String sql="delete from be_wk_entmember m where m.gid = ? and m.entmember_id not in (select distinct entmember_id from be_wk_job where gid = ?)";
		DaoUtil.getInstance().execute(sql, gid,gid);
		
		//插入选中的法定代表人带法定代表人表
		//从主要人员表获取相关信息 a
		SetMemberPositionUtil.setLegal(entId, gid, entMob);
		
		ValidateMsg msg = RuleUtil.getInstance().runRule("ebaic_setup_member",gid);
		List<ValidateMsgEntity> errors =  msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			throw new EBaicException(msg.getAllMsgString());
		}
		
		
	}
	
	/**
	 * 设置主要人员  法定代表人、董事长、监事会主席
	 * @param request
	 * @param response
	 */
	public void setupPosition(String entmemberId,String setType,String gid) {
//		if("L".equals(setType)){//设置法定代表人
//			SetMemberPositionUtil.setLegal(entmemberId,gid);
//		}
		if("P".equals(setType)){//设置董事长
			SetMemberPositionUtil.setPresident(entmemberId,gid);
		}
		if("C".equals(setType)){//设置监事会主席
			SetMemberPositionUtil.setChairman(entmemberId);
		}
	}

	/**
	 * 查询是否设立董事会，是否设立监事会。
	 * 
	 * @param gid
	 * @return
	 */
	public Map<String, Object> querySetting(String gid) {
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		String sql = "select nvl(t.is_board,-1) is_board,nvl(t.is_suped,-1) is_suped from be_wk_ent t where t.gid = ?";
		Map<String,Object> ret = DaoUtil.getInstance().queryForRow(sql, gid);
		if(ret==null){
			throw new EBaicException("根据传入的gid找不到业务数据。");
		}
		return ret;
	}
	
	public void setSettingBoard(String gid, String isBoard){
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		if(!"1".equals(isBoard)){// 不设董事会
			long direcotrCnt = getDirectorCnt(gid);
			if(direcotrCnt>1){//当前董事人数大于1人
				throw new EBaicException("不设董事会，只能有一名执行董事，请先删除其他董事人选。");
			}else{//当前董事人数0~1人
				//更新 董事 为 执行董事
				String sql = "update be_wk_job j set j.position = '432K' where j.position  in ('431A', '431B', '432A', '432L') and j.gid = ?";
				DaoUtil.getInstance().execute(sql, gid);
			}
		}else{ // 设董事会
			//更新执行董事为董事
			String sql = "update be_wk_job j set j.position = '432A' where j.position = '432K' and j.gid = ?";
			DaoUtil.getInstance().execute(sql, gid);
		}
		String sql = "update be_wk_ent t set t.is_board = ? where t.gid = ?";
		DaoUtil.getInstance().execute(sql, isBoard, gid);
	}
	/**
	 * 设置是否设立董事会，是否设立监事会。
	 * 
	 * @param gid
	 * @param isBoard
	 * @param isSusped
	 * @return
	 */
	public void setSettingSusped(String gid, String isSusped) {
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		String sql = "";
		if(!"1".equals(isSusped)){// 不设董事会
			long suspCnt = getSuspCnt(gid);
			if(suspCnt>2){
				throw new EBaicException("不设监事会，最多2名监事，请先删除其他监事人选。");
			}else{
				sql = "update be_wk_job j set j.position = '408A' where j.position  in ('408B', '408K') and j.gid = ?";
				DaoUtil.getInstance().execute(sql, gid);
			}
		}
		sql = "update be_wk_ent t set t.is_suped = ? where t.gid = ?";
		DaoUtil.getInstance().execute(sql, isSusped, gid);
	}
	
	/**
	 * 查询董事人数。
	 * 
	 * @param gid
	 * @return
	 */
	public long getDirectorCnt(String gid){
		String sql = "select count(distinct m.entmember_id) from be_wk_entmember m left join be_wk_job j on m.entmember_id=j.entmember_id where j.position in ('431A', '431B', '432A', '432L', '432K') and m.modify_sign < 3 and m.gid = ?";
		long ret = DaoUtil.getInstance().queryForOneLong(sql, gid);
		return ret;
	}
	
	/**
	 * 查询监事人数。
	 * 
	 * @param gid
	 * @return
	 */
	public long getSuspCnt(String gid){
		String sql = "select count(distinct m.entmember_id) from be_wk_entmember m left join be_wk_job j on m.entmember_id=j.entmember_id where j.position in ('408A', '408B', '408K') and m.modify_sign < 3 and m.gid = ?";
		long ret = DaoUtil.getInstance().queryForOneLong(sql, gid);
		return ret;
	}
	/**
	 * 删除主要人员。
	 * 
	 * 如果没有兼职，直接删除人员。
	 * 如果有，只删除职务。
	 * @param entmemberId
	 * @param gid
	 */
	public void delMbr(String entmemberId, String positionType , String gid) {
		String sql = "select j.position,j.position_type from be_wk_job j where j.entmember_id = ?";
		List<Map<String,Object>> jobs= DaoUtil.getInstance().queryForList(sql, entmemberId);
		sql = "select le_rep_sign from be_wk_entmember where entmember_id=?";
		String leRepSign = DaoUtil.getInstance().queryForOneString(sql, entmemberId);
		sql = "select le_rep_job_code from  be_wk_le_rep where gid=?";
		String jobcode = DaoUtil.getInstance().queryForOneString(sql, gid);
		
		if(jobs!=null && "1".equals(leRepSign)){
			int len = jobs.size();
			for (int i = 0; i < len; i++) {
				String position = StringUtil.safe2String(jobs.get(i).get("position"));
				String pType = StringUtil.safe2String(jobs.get(i).get("positionType"));
				
				if(position==null || pType==null){
					continue;
				}
				if(position.equals(jobcode) && pType.equals(positionType)){
					
					//删除法人记录
					sql="delete from be_wk_le_rep where gid=?";
					DaoUtil.getInstance().execute(sql, gid);
					//去掉法人标记
					sql="update be_wk_entmember set le_rep_sign='' where gid=?";
					DaoUtil.getInstance().execute(sql, gid);
					break;
				}
			}
		}
		
		if(jobs!=null &&jobs.size()>1){
			// 只删除职务
			sql = "delete from be_wk_job j where j.entmember_id = ? and j.position_type = ? ";
			DaoUtil.getInstance().execute(sql, entmemberId, positionType);
			//清除兼职标记
			sql = "update be_wk_entmember set is_manager=0 where entmember_id=?";
			DaoUtil.getInstance().execute(sql, entmemberId);
		}else{
			// 删除职务
			sql = "delete from be_wk_job j where j.entmember_id = ? ";
			DaoUtil.getInstance().execute(sql, entmemberId);
			// 删除人
			sql = "delete from be_wk_entmember m where m.entmember_id = ?";
			DaoUtil.getInstance().execute(sql, entmemberId);
		}
	}
	/**
	 *查询单个主要人员或者单个股东的信息
	 * @param entmemberId
	 * @param gid
	 */
	public Map<String, Object> queryEntMemberOrInvester(String id) {
		//1、准备变量
		List<String> params = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> row = null;
		
		//2、查询股东表
		sql.append(" select inv.inv as name,inv.cer_no,inv.cer_type,inv.sex,inv.contry as country,inv.prov,inv.city,inv.dom_other as other,inv.folk from be_wk_investor inv where inv.investor_id = ?");
		params.add(id);
		row = DaoUtil.getInstance().queryForRow(sql.toString(), params);
		
		//3、再次查主要人员表
		if(row==null){
			sql.delete(0, sql.length());
			sql.append(" select ent.is_manager,ent.name,ent.cer_no,ent.cer_type,ent.sex,ent.country,ent.house_add_prov as prov,ent.house_add_city as city,ent.house_add_other as other,ent.nation as folk from be_wk_entmember ent where ent.entmember_id = ? ");
			row = DaoUtil.getInstance().queryForRow(sql.toString(), params);
		}
		return row;
	}
	/**
	 * 
	 * @param entmemberId
	 * @return
	 */
	public Map<String, Object> job(String entmemberId,String positionType) {
		String sql = "select * from be_wk_job j where j.position_type=? and j.entmember_id = ?";
		Map<String, Object> ret = DaoUtil.getInstance().queryForRow(sql, positionType, entmemberId);
		return ret;
	}

	
	/**
	 * 法定代表人数据回显
	 * yzh
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object>  reDeployLegalDelegate(String gid) throws OptimusException {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid为空");
		}
		Map<String, Object> row = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select m.name, m.entmember_id, l.le_rep_mob, l.le_rep_job_code")
		.append(" from be_wk_entmember m, be_wk_le_rep l")
		.append(" where l.le_rep_cer_type = m.cer_type and l.flag = '1'") 
		.append(" and l.le_rep_cer_no = m.cer_no and l.gid = ?") 
		.append(" and le_rep_sign = '1' and m.gid =?");   
		
		row = DaoUtil.getInstance().queryForRow(sql.toString(), gid,gid);
		
		return row;
	}
	/**
	 * 根据gid查询兼任经理的董事
	 * @param gid
	 */
	public Map<String, Object> queryManagered(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid为空");
		}
		String sql = "select * from be_wk_entmember e left join be_wk_job j on e.entmember_id=j.entmember_id "+
					"where e.is_manager='1' and j.position_type='1' and e.gid=?";
		Map<String, Object> row = DaoUtil.getInstance().queryForRow(sql.toString(), gid);
		return row;
	}
	/**
	 * 根据gid查询董事
	 * @param gid
	 */
	public List<Map<String, Object>> queryBoard(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid为空");
		}
		String sql = "select * from be_wk_entmember e left join be_wk_job j on e.entmember_id=j.entmember_id "+
					"where j.position_type='1' and e.gid=?";
		List<Object> param = new ArrayList<Object>();
		param.add(gid);
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, param);
		return list;
	}
	/**
	 * 修改所有董事‘是否兼任经理’字段为‘0’不兼任。
	 * @param gid
	 */
	public void updateIsManager(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid为空");
		}
		String sql = "update be_wk_entmember e set e.is_manager ='0' where e.gid= ?";
		List<Object> param = new ArrayList<Object>();
		param.add(gid);
		DaoUtil.getInstance().execute(sql, param);
	}
	/**
	 * 查询主要人员中的经理，并返回id
	 * @param gid
	 * @return
	 */
	public String queryManager(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("gid为空");
		}
		String sql = "select e.entmember_id from be_wk_entmember e left join be_wk_job j on e.entmember_id=j.entmember_id "+
				"where (e.is_manager!='1' or e.is_manager is null) and j.position_type='2' and e.gid=?";
		List<Object> param = new ArrayList<Object>();
		param.add(gid);
		return (String) DaoUtil.getInstance().queryForOne(sql, param);
	}
	/**
	 * 根据主要人员Id删除主要人员以及它的职务
	 * @param entmemberId
	 */
	public void deleteMbr(String entmemberId,String gid){
		//要删除的人是法人，清空法人信息
		String sql ="select le_rep_sign from be_wk_entmember m where m.entmember_id = ?";
		String leRepSign = DaoUtil.getInstance().queryForOneString(sql, entmemberId);
		if("1".equals(leRepSign)){
			sql="delete from be_wk_le_rep l where gid=?";
			DaoUtil.getInstance().execute(sql, gid);
		}
		// 删除职务
		sql = "delete from be_wk_job j where j.entmember_id = ? ";
		DaoUtil.getInstance().execute(sql, entmemberId);
		// 删除人
		sql= "delete from be_wk_entmember m where m.entmember_id = ?";
		DaoUtil.getInstance().execute(sql, entmemberId);
	}

	public void deleteDirector(String entMemberId) {
		String sql = "delete be_wk_job where entmember_id = ? and position_type = '2'";
		DaoUtil.getInstance().execute(sql, entMemberId);
	}

	public int queryDirector(String gid) {
		String sql = "select count(1) from be_wk_entmember e left join be_wk_job j on e.entmember_id = j.entmember_id "
				+ " where e.gid = ? and j.position_type = '2'";
		return DaoUtil.getInstance().queryForOneBigDecimal(sql, gid).intValue();
	}
}
