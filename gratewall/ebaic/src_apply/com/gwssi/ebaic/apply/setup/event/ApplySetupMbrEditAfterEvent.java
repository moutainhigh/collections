package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.setup.service.SetupMemberService;
import com.gwssi.ebaic.apply.util.ReqStateMappingUtil;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;
/**
 * 编辑主要人员 Before Event。
 * 
 * @author chaiyoubing
 */
@Service("ApplySetupMbrEditAfter")
public class ApplySetupMbrEditAfterEvent implements OnEventListener{
	@Autowired 
	SetupMemberService setupMemberService;
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		// 保存监事类型
		String supsType = params.get("supsType");
		String posBrForm = params.get("posBrForm");
		String offYears = params.get("offYears");
		String entmemberId = params.get("entmemberId");
		String psnjobId = params.get("psnjobId");
		String gid = null;
//		String isManager = params.get("isManager");
		if(!StringUtil.isBlank(psnjobId)){
			String sql = "update be_wk_job j set j.sups_type = ?,pos_br_form=?,off_years=? where j.psnjob_id = ?";
			DaoUtil.getInstance().execute(sql, supsType, posBrForm, offYears ,psnjobId);
		}
		
		String sql = "select nvl(m.le_rep_sign,0) as le_rep_sign,m.gid from be_wk_entmember m where m.entmember_id = ?";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, entmemberId);
		if(row!=null){
			String leRepSign = StringUtil.safe2String(row.get("leRepSign"));
			gid = StringUtil.safe2String(row.get("gid"));
			if("1".equals(leRepSign)){//如果是法定代表人
				// 更新 be_wk_le_rep表
				sql = "update be_wk_le_rep l set (l.le_rep_name,l.le_rep_cer_type,l.le_rep_cer_no,l.le_rep_mob,l.le_rep_email,l.timestamp )= "+
						"(select m.name,m.cer_type,m.cer_no,m.mob_tel,m.email,m.timestamp from be_wk_entmember m where m.entmember_id=? ) where l.gid = ?";
				DaoUtil.getInstance().execute(sql, entmemberId,gid );
			}
		}
		
		/**
		 * 将地址拼为一段式  chaiyoubing
		 */
		String addressStr = "";
		String prov = "";
		String city = "";
		sql = "update be_wk_entmember ent set ent.house_add=? where ent.entmember_id=? ";
		
		prov = ReqStateMappingUtil.changeTextByDm(params.get("houseAddProv"));
		city = ReqStateMappingUtil.changeTextByDm(params.get("houseAddCity"));
	
		String houseAddOther = params.get("houseAddOther");
		addressStr = prov+city+houseAddOther;
		String updateId= params.get("entmemberId");
		DaoUtil.getInstance().execute(sql, addressStr,updateId);
		
		SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
		
//		OptimusRequest request = RequestUtil.getOptimusRequest();
//		String positionType = request.getParameter("mbrFlag");
//		String gid = request.getParameter("gid");
//		Calendar now = DateUtil.getCurrentTime();
		/**
		 * 董事兼任经理时
		 */
		/*if("1".equals(positionType)){//编辑董事信息时
			
			//先删除原来的经理职务
			String sql0 = "delete from be_wk_job j where j.entmember_id = ? and j.position_type = '2' ";
			DaoUtil.getInstance().execute(sql0, entmemberId);
			
			if("1".equals(isManager)){//兼任经理
				String entmbrId = setupMemberService.queryManager(gid);
				if(entmbrId!=null&&(!entmbrId.equals(entmemberId))){//若原来有经理且不是董事兼任，则先删除原来的，再添加新的经理
					setupMemberService.deleteMbr(entmbrId);
				}
				//1.董事添加经理职务
				List<Object> sqlParam1 = new ArrayList<Object>();
				String psnjobId1 = UUIDUtil.getUUID();
				sqlParam1.add(psnjobId1);
				sqlParam1.add(entmemberId);
				sqlParam1.add(offYears);
				sqlParam1.add(posBrForm);
				sqlParam1.add(gid);
				sqlParam1.add(now);
				sqlParam1.add(supsType);
				String sql1 = "insert into be_wk_job(psnjob_id,entmember_id,position_type,position,off_years," +
						"pos_br_form,gid,timestamp,sups_type) values(?,?,'2','436A',?,?,?,?,?)";
				DaoUtil.getInstance().execute(sql1, sqlParam1);
				
				//2.其他的董事‘兼任经理’字段都赋值为‘0’
				List<Map<String, Object>> list = setupMemberService.queryBoard(gid);
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						if(!entmemberId.equals(list.get(i).get("entmemberId"))){
							String sql2 = "update be_wk_entmember e set e.is_manager ='0' where e.entmember_id=?";
							List<Object> sqlParam2 = new ArrayList<Object>();
							sqlParam2.add(list.get(i).get("entmemberId"));
							DaoUtil.getInstance().execute(sql2, sqlParam2);
							
						}
					}
				}
				
			}
		}*/
		
		
		
	}

}
