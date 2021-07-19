package com.gwssi.ebaic.apply.setup.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.setup.service.SetupMemberService;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;
import com.gwssi.torch.web.TorchController;

/**
 * 编辑主要人员 Before Event。
 * 
 * @author chaiyoubing
 */
@Service("ApplySetupMbrEditBefor")
public class ApplySetupMbrEditBeforEvent implements OnEventListener {
	@Autowired 
	SetupMemberService setupMemberService;
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {

	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		// 查验身份证号合法性
		String cerType = params.get("cerType");
		String name = params.get("name");
		String cerNo = params.get("cerNo");
		if("1".equals(cerType)){// 如果是身份证
			
			boolean idCheckResult = IdentityCardUtil.check(name, cerNo);
			if(!idCheckResult){
				throw new EBaicException("姓名和身份证号码不匹配，请查验后重新输入。");
			}
		}
		
		//
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String positionType = request.getParameter("mbrFlag");
		String gid = request.getParameter("gid");
		
		String curmid = params.get("entmemberId");//要保存的entmemberId
		//获取原来的经理信息			
		Map<String,Object> mRow=getManagerId(gid);
		String oldManagerId = StringUtil.safe2String(mRow.get("entmemberId"));
		String oldManagerCerNo = StringUtil.safe2String(mRow.get("cerNo"));
		String oldManagerName = StringUtil.safe2String(mRow.get("name"));
		
		/**
		 * 编辑经理信息时
		 */		
		if("2".equals(positionType)){
			
			if(cerNo!="" && !(cerNo.equals(oldManagerCerNo)
					&& name.equals(oldManagerName))){
				//不是同一个人
				
				String isManager = params.get("isManager");
				String sql ="";
				if("1".equals(isManager)){
					//如果原来的经理是兼任，清掉兼任标记
					sql = "update be_wk_entmember e set e.is_manager ='0' where e.gid=?";
					DaoUtil.getInstance().execute(sql, gid);
				}
				
				
				//处理人员记录
				addManager(params,gid);
				
				//删除旧的经理职位信息
				setupMemberService.delMbr(oldManagerId, "2", gid);
			}else{
				//是同一个人
			}
			
		}
		
		if("1".equals(positionType)){//编辑董事信息时
			String isManager = params.get("isManager");
		
			//经理是其他人 选择了兼任
			if("1".equals(isManager) && cerNo!="" 
					&& !(cerNo.equals(oldManagerCerNo)
					&& name.equals(oldManagerName))){
				//去掉原经理职务记录		
				setupMemberService.delMbr(oldManagerId, "2", gid);
				
				//新增经理职务记录
				addMemberJob("436A",params,gid);
			}
			
			//经理是自己但是不同的人员记录
			if(cerNo!="" && (cerNo.equals(oldManagerCerNo))
					&& name.equals(oldManagerName) && !oldManagerId.equals(curmid)){
				//删除当前的记录 
				setupMemberService.deleteMbr(curmid,gid);
				//关联老记录
				params.put("isManager", "1");//设置为兼任
				params.put("entmemberId", oldManagerId);
				
				//查询老记录是否有董事职务记录
				long n = DaoUtil.getInstance().queryForOneLong("select count(*) from be_wk_job j where j.entmember_id =? and j.position_type='1' ", oldManagerId);
				if(n==0){//没有则新增
					//是否设置了董事会
					String isBoard = DaoUtil.getInstance().queryForOneString("select e.is_board from be_wk_ent e where gid=?", gid);
					String jobCode="432K";//执行董事432K
					if("1".equals(isBoard)){
						jobCode="432A";//董事432A
					}
					//新增董事职务
					addMemberJob(jobCode,params,gid);
				}
				
			}
			
			
			
			
		}
		
	}
	/**
	 * 获取有经理职务的主要人员
	 * @param gid
	 * @return
	 */
	private Map<String , Object> getManagerId(String gid){
		String mSql0 = "select e.entmember_id,e.cer_no,e.cer_type,e.name from be_wk_entmember e left join be_wk_job j on e.entmember_id=j.entmember_id "+
				"where j.position_type='2' and e.gid=?";
		Map<String , Object> mRow= DaoUtil.getInstance().queryForRow(mSql0, gid);//编辑前经理id,若有的话
		if(mRow==null)
			mRow = new HashMap<String , Object>();
		return mRow;
	}
	/**
	 * 添加经理和职务
	 * @param params
	 */
	private void addManager(Map<String, String> params,String gid){
		
		String cerType = params.get("cerType");
		String name = params.get("name");
		String cerNo = params.get("cerNo");
		
		//检查新的经理是否有人员记录了
		String sql="select e.entmember_id from be_wk_entmember e  where e.gid=? and e.cer_type=? and e.cer_no=? and e.name=?";
		String memberid = DaoUtil.getInstance().queryForOneString(sql, gid,cerType,cerNo,name);
		if(StringUtils.isNotBlank(memberid)){
			//有人员记录
			//删掉当前记录
			String oldmid = params.get("entmemberId");
			DaoUtil.getInstance().execute("delete from be_wk_entmember where entmember_id=?", oldmid);
			//关联原来的那个
			params.put("entmemberId", memberid);
		}else{
			//全新的
			//新增一条人员记录
			String addSql = "insert into be_wk_entmember(is_manager,entmember_id,gid,ent_id,cer_type,cer_no,name,name_eng,sex,mob_tel," +
					"country,house_add,house_add_prov,house_add_city,house_add_other,lite_deg,nation,pol_stand,nat_date,modify_sign,timestamp) " +
					"values('0',?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),'1',sysdate)";
			List<Object> addParam = new ArrayList<Object>();
			memberid = UUIDUtil.getUUID();
			addParam.add(memberid);
			addParam.add(gid);
			addParam.add(params.get("entId"));
			addParam.add(params.get("cerType"));
			addParam.add(params.get("cerNo"));
			addParam.add(params.get("name"));
			addParam.add(params.get("nameEng"));
			addParam.add(params.get("sex"));
			addParam.add(params.get("mobTel"));
			addParam.add(params.get("country"));
			addParam.add(params.get("houseAdd"));
			addParam.add(params.get("houseAddProv"));
			addParam.add(params.get("houseAddCity"));
			addParam.add(params.get("houseAddOther"));
			addParam.add(params.get("liteDeg"));
			addParam.add(params.get("nation"));
			addParam.add(params.get("polStand"));
			addParam.add(params.get("natDate"));
			DaoUtil.getInstance().execute(addSql, addParam);
		}
		
		
		//添加经理的职务
		List<Object> sqlParam3 = new ArrayList<Object>();
		String psnjobId = UUIDUtil.getUUID();
		sqlParam3.add(psnjobId);
		sqlParam3.add(memberid);
		sqlParam3.add(params.get("offYears"));
		sqlParam3.add(params.get("posBrForm"));
		sqlParam3.add(gid);
		Calendar now = DateUtil.getCurrentTime();
		sqlParam3.add(now);
		sqlParam3.add(params.get("supsType"));
		String sql3 = "insert into be_wk_job(psnjob_id,entmember_id,position_type,position,off_years," +
				"pos_br_form,gid,timestamp,sups_type) values(?,?,'2','436A',?,?,?,?,?)";
		DaoUtil.getInstance().execute(sql3, sqlParam3);
	}
	
	
	/**
	 * 为指定的人员增加指定的职务记录
	 * @param position
	 * @param params
	 * @param gid
	 */
	private void addMemberJob(String position,Map<String, String> params,String gid){
		String memberId = params.get("entmemberId");
		
		
		List<Object> sqlParam1 = new ArrayList<Object>();
		sqlParam1.add(UUIDUtil.getUUID());
		sqlParam1.add(memberId);
		sqlParam1.add(position);
		sqlParam1.add(params.get("offYears"));
		sqlParam1.add(params.get("posBrForm"));
		sqlParam1.add(gid);
		Calendar now = DateUtil.getCurrentTime();
		sqlParam1.add(now);
		sqlParam1.add(params.get("supsType"));
		String sql = "insert into be_wk_job(psnjob_id,entmember_id,position_type,position,off_years," +
				"pos_br_form,gid,timestamp,sups_type) values(?,?,'2',?,?,?,?,?,?)";
		DaoUtil.getInstance().execute(sql, sqlParam1);
	} 
	
}
