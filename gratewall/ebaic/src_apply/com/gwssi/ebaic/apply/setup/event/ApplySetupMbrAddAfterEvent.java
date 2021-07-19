package com.gwssi.ebaic.apply.setup.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.setup.service.SetupMemberService;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 新增主要人员 After Event。
 * 
 * @author chaiyoubing
 */
@Service("ApplySetupMbrAddAfter")
public class ApplySetupMbrAddAfterEvent implements OnEventListener {
	@Autowired 
	SetupMemberService setupMemberService;
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		// donothing
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String positionType = request.getParameter("mbrFlag");
		String gid = request.getParameter("gid");
		//若添加的经理信息与董事信息一致，删除添加的经理信息，经理栏只显示兼任经理的董事信息
		if("2".equals(positionType)){
			List<Map<String, Object>> list = setupMemberService.queryBoard(gid);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					if((list.get(i).get("cerType").equals(params.get("cerType")))&&(list.get(i).get("cerNo").equals(params.get("cerNo")))){
						//
						String sql = " select e.entmember_id from be_wk_entmember e left join be_wk_job j on e.entmember_id=j.entmember_id  " +
								"where (e.is_manager!='1' or e.is_manager is null) and j.position_type='2' and "+
								" e.cer_type=? and  e.cer_no = ? and  e.gid=?";
						List<Object> param = new ArrayList<Object>();
						param.add(params.get("cerType"));
						param.add(params.get("cerNo"));
						param.add(gid);
						String entmemberId = (String) DaoUtil.getInstance().queryForOne(sql, param);
						setupMemberService.deleteMbr(entmemberId,gid);
					}
				}
			}
		}
		
	}

}
