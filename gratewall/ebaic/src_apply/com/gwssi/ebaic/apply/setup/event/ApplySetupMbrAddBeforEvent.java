package com.gwssi.ebaic.apply.setup.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.setup.service.SetupMemberService;
import com.gwssi.ebaic.apply.util.ReqStateMappingUtil;
import com.gwssi.ebaic.apply.util.SetupMemberRuleUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.UUIDUtil;
import com.gwssi.torch.web.TorchController;

/**
 * 新增主要人员 Before Event。
 * 
 * @author chaiyoubing
 */
@Service("ApplySetupMbrAddBefor")
public class ApplySetupMbrAddBeforEvent implements OnEventListener {
	@Autowired 
	SetupMemberService setupMemberService;

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取页面用户信息失败!");
		}
		// 查验身份证号合法性
		String cerType = params.get("cerType");
		if("1".equals(cerType)){// 如果是身份证
			String name = params.get("name");
			String cerNo = params.get("cerNo");
			boolean idCheckResult = IdentityCardUtil.check(name, cerNo);
			if(!idCheckResult){
				throw new EBaicException("姓名和身份证号码不匹配，请查验后重新输入。");
			}
		}
		Calendar now = DateUtil.getCurrentTime();
		/**
		 * 1、得到前台传过来的业务gid
		 */
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		params.put("gid", gid);
		
		/**
		 * 2、根据gid查询其他表需要的主键
		 */
		Map<String, Object> paramsMap = ParamUtil.prepareParams(gid);
		String entId = (String)paramsMap.get("entId");
		params.put("entId", entId);
		
		String isBoard = StringUtil.safe2String(paramsMap.get("isBoard"));
		
		/**
		 * 3.校验董事、经理、监事之间是否兼任，是否重复任职
		 */
		String positionType = request.getParameter("mbrFlag");
		List<String> errorMsg = null;
		try {
			errorMsg = SetupMemberRuleUtil.ruleCheckWhenSavingSingle(params,positionType,entId);
		} catch (OptimusException e) {
			throw new EBaicException(e.getMessage(),e);
		}
		if(errorMsg!=null&&errorMsg.size()>0){
			String msg = errorMsg.toString();
			 throw new EBaicException(msg);
		}
		
		/**
		 * 4、生成be_wk_entmember表主键，job表必须相同
		 */
		String entmemberId = UUIDUtil.getUUID();
		params.put("entmemberId", entmemberId);
		
		/**
		 * 将地址拼为一段式  chaiyoubing
		 */
		String addressStr = "";
		String prov = "";
		String city = "";
	
		prov = ReqStateMappingUtil.changeTextByDm(params.get("houseAddProv"));
		city = ReqStateMappingUtil.changeTextByDm(params.get("houseAddCity"));
	
		String houseAddOther = params.get("houseAddOther");
		addressStr = prov+city+houseAddOther;
		params.put("houseAdd", addressStr);
		

		/**
		 * 	5.处理job
		 * 	position_type:职务类型：1-董事，2-经理，3-监事，4-理事，5-代表
		 * 	poff_years :3年任职年限
		 * 	pos_br_form:02 委派职务产生方式(CB19)
		 */
		
		if(StringUtil.isBlank(positionType) || "undefined".equals(positionType)) {
			 throw new EBaicException("传入的主要人员类型不能为空。");
		}
		
		String posBrForm = params.get("posBrForm");
		String offYears = params.get("offYears");
		String supsType = params.get("supsType");
		
		StringBuffer sql = new StringBuffer();
		List<Object> sqlParam = new ArrayList<Object>();
		sql.append("insert into be_wk_job(psnjob_id,entmember_id," +
				"position_type,position,off_years,pos_br_form,gid,timestamp,sups_type) values(?,?,?,?,?,?,?,?,?)");
		String psnjobId = UUIDUtil.getUUID();
		sqlParam.add(psnjobId);
		sqlParam.add(entmemberId);
		sqlParam.add(positionType);
		if("1".equals(positionType)){
			if("1".equals(isBoard)){
				sqlParam.add("432A");//默认董事432A
			}else{
				sqlParam.add("432K");//默认执行董事432K
			}
		}else if("2".equals(positionType)){//默认经理436A
			sqlParam.add("436A");
		}else if("3".equals(positionType)){//默认监事408A
			sqlParam.add("408A");
		}else {
			sqlParam.add("");
		}
		sqlParam.add(offYears);
		sqlParam.add(posBrForm);
		sqlParam.add(gid);
		sqlParam.add(now);
		sqlParam.add(supsType);
		DaoUtil.getInstance().execute(sql.toString(), sqlParam);
		/**
		 * 6.董事兼任经理时
		 */
		if("1".equals(positionType)){//添加董事信息时
			String isManager = params.get("isManager");
			String oldEntmbrId = getManagerId(gid);//编辑前经理id
			
			//若添加时，兼任经理
			if("1".equals(isManager)){
				//先删除原来的经理职务
				String sql0 = "delete from be_wk_job j where j.entmember_id = ? and j.position_type = '2' ";
				DaoUtil.getInstance().execute(sql0, oldEntmbrId);
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
		}
		/**
		 * 7.添加经理信息时,校验是否有与董事信息一致的，若有修改董事“是否兼任经理”为‘1’，相反为‘0’
		 */
		if("2".equals(positionType)){
			Map<String, Object> res = setupMemberService.queryManagered(gid);
			//若有兼任经理的董事，且保存的经理信息与兼任经理的董事证件类型和证件号不一致修改是否兼任经理标志为‘0’
			if(res!=null){
				if((!res.get("cerType").equals(params.get("cerType")))||(!res.get("cerNo").equals(params.get("cerNo")))){
					String sql2 = "update be_wk_entmember e set e.is_manager ='0' where e.entmember_id=?";
					List<Object> sqlParam2 = new ArrayList<Object>();
					sqlParam2.add(res.get("entmemberId"));
					DaoUtil.getInstance().execute(sql2, sqlParam2);
				}
			}
			//若董事里有与经理的证件类型和证件号一致的，修改是否兼任经理标志为‘1’,并更新改董事信息
			List<Map<String, Object>> list = setupMemberService.queryBoard(gid);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					if((list.get(i).get("cerType").equals(params.get("cerType")))&&(list.get(i).get("cerNo").equals(params.get("cerNo")))){
						//
						String sql3 = "update be_wk_entmember e set e.is_manager ='1',CER_TYPE=?,CER_NO=?,NAME=?,NAME_ENG=?,SEX=?,MOB_TEL=?,COUNTRY=?," +
								"HOUSE_ADD=?,HOUSE_ADD_PROV=?,HOUSE_ADD_CITY=?,HOUSE_ADD_OTHER=?,LITE_DEG=?,NATION=?,POL_STAND=?," +
								"NAT_DATE=to_date(?,'yyyy-mm-dd'),TIMESTAMP=sysdate where e.entmember_id=?";
						List<Object> sqlParam3 = new ArrayList<Object>();
						sqlParam3.add(params.get("cerType"));
						sqlParam3.add(params.get("cerNo"));
						sqlParam3.add(params.get("name"));
						sqlParam3.add(params.get("nameEng"));
						sqlParam3.add(params.get("sex"));
						sqlParam3.add(params.get("mobTel"));
						sqlParam3.add(params.get("country"));
						sqlParam3.add(params.get("houseAdd"));
						sqlParam3.add(params.get("houseAddProv"));
						sqlParam3.add(params.get("houseAddCity"));
						sqlParam3.add(params.get("houseAddOther"));
						sqlParam3.add(params.get("liteDeg"));
						sqlParam3.add(params.get("nation"));
						sqlParam3.add(params.get("polStand"));
						sqlParam3.add(params.get("natDate"));
						sqlParam3.add(list.get(i).get("entmemberId"));
						DaoUtil.getInstance().execute(sql3, sqlParam3);
						//为该董事添加经理职务
						List<Object> sqlParam1 = new ArrayList<Object>();
						String psnjobId1 = UUIDUtil.getUUID();
						sqlParam1.add(psnjobId1);
						sqlParam1.add(list.get(i).get("entmemberId"));
						sqlParam1.add(offYears);
						sqlParam1.add(posBrForm);
						sqlParam1.add(gid);
						sqlParam1.add(now);
						sqlParam1.add(supsType);
						String sql1 = "insert into be_wk_job(psnjob_id,entmember_id,position_type,position,off_years," +
								"pos_br_form,gid,timestamp,sups_type) values(?,?,'2','436A',?,?,?,?,?)";
						DaoUtil.getInstance().execute(sql1, sqlParam1);
						
					}
				}
			}
		}
	}
	/**
	 * 获取有经理职务的主要人员
	 * @param gid
	 * @return
	 */
	private String getManagerId(String gid){
		String mSql0 = "select e.entmember_id from be_wk_entmember e left join be_wk_job j on e.entmember_id=j.entmember_id "+
				"where j.position_type='2' and e.gid=?";
		List<Object> mParam0 = new ArrayList<Object>();
		mParam0.add(gid);
		String mEntmemberId = (String) DaoUtil.getInstance().queryForOne(mSql0, mParam0);//编辑前经理id,若有的话
		return mEntmemberId;
	}
}
