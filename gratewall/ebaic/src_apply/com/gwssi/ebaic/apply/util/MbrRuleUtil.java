package com.gwssi.ebaic.apply.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gwssi.ebaic.domain.BeWkEntBO;
import com.gwssi.ebaic.domain.BeWkEntmemberBO;
import com.gwssi.ebaic.domain.BeWkJobBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;

public class MbrRuleUtil {
	protected final static Logger logger = Logger.getLogger(MbrRuleUtil.class);

	private static void preparePrams(String gid, MbrRuleContext context)
			throws OptimusException {
		Map<String, String> params = new HashMap<String, String>();
		String entSql = "select nvl(t.is_board,0) as is_board,0 as is_council,nvl(t.is_suped,0) as is_suped,r.cat_id,t.ent_id,t.gid from be_wk_ent t left join be_wk_requisition r on t.ent_id=r.ent_id where t.gid = ? ";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(entSql, gid);
		Map<String,String> strRow = new HashMap<String,String>();
		for(Map.Entry<String,Object> entry : row.entrySet()){
			strRow.put(entry.getKey(),StringUtil.safe2String(entry.getValue()));
		}
		params.putAll(strRow);
		context.setParams(params);
	}
	
	/**
	 * 非公司-设立主要人员校验
	 * 
	 * @param entId
	 * @return
	 * @throws OptimusException
	 */
	public static List<String> ruleCheckWhenSavingAll(String gid,String isBossChange)
			throws OptimusException {

		MbrRuleContext context = new MbrRuleContext(gid);
		preparePrams(gid, context);
		ensureMbrUnique(context);
		checkChief(context);
		
		List<String> _list, _list1;

		String isBoard = context.getParams().get("isBoard"); // 是否设立董事会
		// String isSuped = context.getParams().get("isSuped"); // 是否设立监事会
		String isCouncil = context.getParams().get("isCouncil"); // 是否设立理事会

		// 每个人最多两个职务 。
		checkPositionCnt(context);
		
				
		// 主要人员不能为空
		if (context.members == null || context.members.isEmpty()) {
			context.addErrorMsg("主要人员不能空。");
		}

		// 法定代表人检测
		bossCheck(context, isBossChange);
		
		// 经理
		positionNumHighLimit(context, "434Q", "总经理", 1);
		positionNumHighLimit(context, "436A", "经理", 1);
		_list = context.positionMap.get("434Q");
		_list1 = context.positionMap.get("436A");
		if ((_list != null && _list.size() > 0)
				&& (_list1 != null && _list1.size() > 0)) {
			context.addErrorMsg("不能同时设置“经理”、“总经理”。");
		}
		// 监事
		checkJsNumber(gid, context);

		// 董事和理事互斥检测。
		if (context.directorSet.size() > 0 && context.councilSet.size() > 0) {
			context.addErrorMsg("不能同时设置董事和理事。");

		}

		if ("1".equals(isBoard)) {
			if (context.councilSet.size() > 0) {
				context.addErrorMsg("设立董事会，则不能添加理事相关职务。");
				return context.errorMsgs;
			}
		}

		if ("1".equals(isCouncil)) {
			if (context.directorSet.size() > 0) {
				context.addErrorMsg("设立理事会，则不能添加董事相关职务。");
				return context.errorMsgs;
			}
		}
		
		// 按董经监校验
		if ("1".equals(isBoard)) {
			if (context.directorSet == null || context.directorSet.isEmpty()) {
				context.addErrorMsg("设立董事会，则至少添加一名董事人员。");
			}
		}
		if ("1".equals(isBoard) || context.directorSet.size() > 0) {
			context.errorMsgs = ruleCheckWhenSavingAll_withDirectors(gid, context);
		}
		// 按理经监校验
		if ("1".equals(isCouncil)) {
			if (context.councilSet == null || context.councilSet.isEmpty()) {
				context.addErrorMsg("设立理事会，则至少添加一名理事人员。");
			}
		}
		if ("1".equals(isCouncil) || context.councilSet.size() > 0) {// 理事
			ruleCheckWhenSavingAll_withCouncils(gid, context);
		}
		// 其他
		return context.errorMsgs;
	}
	
	/**
	 * 7200首席代表有且只能有一个人 491A
	 * 代表可以有0-3个，不能超过3个
	 * 变更前可能存在有超过3个代表的企业，不用处理，不用拦截
	 * @param context
	 * @throws OptimusException 
	 */
	private static void checkChief(MbrRuleContext context) throws OptimusException {
		String catId = context.getParams().get("catId");
		if(!"7200".equals(catId)){
			return;
		}
		String gid = context.getParams().get("gid");
		String sql = "select count(1) num from be_wk_job t where t.position='491A' and t.gid=?";
		BigDecimal num = DaoUtil.getInstance().queryForOneBigDecimal(sql, gid);
		if(num== null || new BigDecimal(1).compareTo(num) != 0){
			throw new OptimusException("首席代表有且只能有一个人。");
		}
		
		 // 人与不能重复
        if(!isUnique(context.allSet)){
        	context.addErrorMsg("人员不能重复（根据证件号码判断）。");
        }
        
        String rsMbr = "select count(1) num from cp_rs_entmember rs,be_wk_entmember wk where rs.entmember_id=wk.rs_entmember_id and wk.gid=?";
        BigDecimal rsMbrCnt = DaoUtil.getInstance().queryForOneBigDecimal(rsMbr, gid);
        if(rsMbrCnt != null && rsMbrCnt.intValue() > 3){
        	return;
        }
    	if(context.allSet!=null && context.allSet.size()>3){
        	context.addErrorMsg("普通代表最多3人。");
        }
	}
	
	/**
	 * isBossChange设立传null，变更传1或0（没有变更项）
	 * @param context
	 * @param isBossChange
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void bossCheck(MbrRuleContext context, String isBossChange){
		if("0".equals(isBossChange)){
			return;
		}
		Iterator<Map> it = context.members.iterator();
		Map<String, String> row;
		String leRepSign = "0"; // 是否法人
		while (it.hasNext()) {
			row = it.next();
			leRepSign = row.get("mLeRepSign");
			if ("1".equals(leRepSign)) {
				break;
			}
		}
		if (!"1".equals(leRepSign)) {
			context.addErrorMsg("没有设定法定代表人(人员)，不能保存。");
		}

		it = context.members.iterator();
		leRepSign = "0";
		while (it.hasNext()) {
			row = it.next();
			leRepSign = row.get("jLeRepSign");
			if ("1".equals(leRepSign)) {
				break;
			}
		}
			
		if (!"1".equals(leRepSign)) {
			context.addErrorMsg("没有设定法定代表人（职务），不能保存。");
		}
	}
	/**
	 * 人员不能重复（根据证件号码判断）。
	 * @param context
	 * @throws OptimusException
	 */
	private static void ensureMbrUnique(MbrRuleContext context) throws OptimusException {
		String gid = context.getParams().get("gid");
		String sql = " select t.cer_no mbr from be_wk_entmember t where t.gid=? and (t.modify_sign not like '3%' or t.modify_sign is null) ";
		List<String> mbrList = new ArrayList<String>();
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, gid);
		if(list != null && list.size()>0){
			for (Map<String, Object> row : list) {
				String mbr = (String) row.get("mbr");
				mbrList.add(mbr);
			}
		}
		if (!isUnique(mbrList)) {
			context.addErrorMsg("人员不能重复（根据证件号码判断）。");
		}		
	}

	/**
	 * 理经监。
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public static List<String> ruleCheckWhenSavingAll_withCouncils(
			String gid, MbrRuleContext context) throws OptimusException {

		List<String> errors = ruleCheckWhenSavingAll_CouncilOnly(gid, context);
		
		// 校验理事和经理 不能兼任监事
		LsRejectJs(context);
		DsRejectJs(context);

		checkLsNumber(context);
		return errors;
	}

	/**
	 * 根据是否设置理事会，限制理事人数
	 * 
	 * @param context
	 */
	private static void checkLsNumber(MbrRuleContext context) {
		String isCouncil = context.getParams().get("isCouncil");
		List<String> _list = null;
		// 405B 副理事长
		// 406K 执行理事
		// 406A 理事
		// 405A 理事长
		if ("1".equals(isCouncil)) {
			_list = context.positionMap.get("406K");// 执行理事
			if (_list != null && _list.size() > 0) {
				context.addErrorMsg("设立理事会，不能设执行理事。");
			}
		} else { // 如果不设立理事会
//			_list = context.positionMap.get("405A");// 理事长
//			if (_list != null && _list.size() > 0) {
//				context.addErrorMsg("不设理事会，不能设立理事长。");
//			}
//			int directorSetSize = context.councilSet.size();
//			if (directorSetSize > 1) {
//				context.addErrorMsg("不设理事会，只能有1名理事。");
//			}
		}
	}

	public static List<String> ruleCheckWhenSavingAll_CouncilOnly(String entId,
			MbrRuleContext ruleContext) throws OptimusException {
		MbrRuleContext content = ruleContext;
		if (content == null) {
			content = new MbrRuleContext(entId, null);
		}

		// 人员不能重复任职（根据证件号码判断）
		if (!isUnique(content.councilSet)) {
			content.addErrorMsg("人员不能重复任职。");
		}

		if(content.councilSet.size()<3){
			content.addErrorMsg("需至少3名理事人员（含理事长、副理事长、理事）。");
		}
		List<String> _list = content.positionMap.get("405A");// 理事长
		if (_list == null || _list.size() < 1) {
			content.addErrorMsg("设立理事会，必须录入理事长信息。");
		}
		if (_list != null && _list.size() > 1) {
			content.addErrorMsg("理事长只能有1人。");
		}
		// 有且仅有一个理事长
		//positionNumNotEmpty(content, "405A", "理事长");
		//positionNumHighLimit(content, "405A", "理事长", 1);

		return content.errorMsgs;
	}

	/**
	 * 校验 只有理事长、副理事长、理事 的情况。
	 * 
	 * @param gid
	 * @param catId
	 * @param entId
	 * @return
	 * @throws OptimusException
	 */
	public static List<String> ruleCheckWhenSavingAll_CouncilOnly(String entId)
			throws OptimusException {
		return ruleCheckWhenSavingAll_CouncilOnly(entId, null);
	}
	/**
	 * 校验只有 首席代表、代表的情况。
	 * 
	 * @param entId
	 * @return
	 * @throws OptimusException 
	 */
	public static List<String> ruleCheckWhenSavingAll_RepOnly(String entId) throws OptimusException {
		MbrRuleContext content = new MbrRuleContext(entId, null);
		
		// 有且仅有一个首席代表
		positionNumNotEmpty(content, "491A", "首席代表");
		positionNumHighLimit(content, "491A", "首席代表", 1);
		
		// 人与不能重复
		if(!isUnique(content.allSet)){
			content.addErrorMsg("人员不能重复（根据证件号码判断）。");
		}
		
		if(content.allSet!=null && content.allSet.size()>3){
			content.addErrorMsg("普通代表最多3人。");
		}
		
		return content.errorMsgs;
	}


	public static List<String> ruleCheckWhenSavingAll_withDirectors(
			String gid, MbrRuleContext context, String isChangeBoss)
			throws OptimusException {
		String catId = context.getParams().get("catId");
		String isBoard = context.getParams().get("isBoard");

		MbrRuleContext content = new MbrRuleContext(gid, null);
		content.errorMsgs.addAll(context.errorMsgs);
		List<String> _list = null;
		boolean _isOk = true;

		

		// 董事会中有重复人员
		if (!isUnique(content.directorSet)) {
			content.addErrorMsg("董事会中存在重复人员。");
		}
		// 监事会中有重复人员
		if (!isUnique(content.supervisorSet)) {
			content.addErrorMsg("监事会中存在重复人员。");
		}
		// 经理中有重复人员
		if (!isUnique(content.managerSet)) {
			content.addErrorMsg("经理中存在重复人员。");
		}

		// 股份、国有独资，必须设立董事会，必须设立监事会

		// 董事不得兼任监事
		DsRejectJs(content);
		// 经理不得兼任监事
		_isOk = true;
		for (String suspCerNo : content.supervisorSet) {
			if (content.managerSet.contains(suspCerNo)) {
				_isOk = false;
				break;
			}
		}
		if (!_isOk) {
			content.addErrorMsg("经理不得兼任监事。");
		}

		boolean hasManager = false;
		// 股份或有限
		if ("1100".equals(catId) || "1200".equals(catId)) {
			// 经理
			_list = content.positionMap.get("436A");
			if (_list != null && _list.size() > 1) {
				content.addErrorMsg("经理只能有1人。");
			}
			if (_list == null || _list.isEmpty()) {
				content.addErrorMsg("必须设置经理。");
			} else {
				hasManager = true;
			}
		}

		boolean hasGeneralManager = false;
		// 外资股份或外资有限
		// if ("5100".equals(catId) || "5200".equals(catId)) {
//		if (NoncpEntTypeUtils.isForeignEnt(catId)) {
//			// 总经理
//			_list = content.positionMap.get("434Q");
//			if (_list != null && _list.size() > 1) {
//				content.addErrorMsg("总经理只能有1人。");
//			}
//			if (_list == null || _list.isEmpty()) {
//				content.addErrorMsg("必须设置总经理。");
//			} else {
//				hasGeneralManager = true;
//			}
//		}

		// 同时设置了经理和总经理
		if (hasManager && hasGeneralManager) {
			content.addErrorMsg("经理或总经理，只能有1个。");
		}

		// 如果设立董事会
		if ("1".equals(isBoard)) {
			_list = content.positionMap.get("431A");// 董事长
			if (_list == null || _list.size() < 1) {
				content.addErrorMsg("设立董事会，必须录入董事长信息。");
			}
			if (_list != null && _list.size() > 1) {
				content.addErrorMsg("董事长只能有1人。");
			}

			_list = content.positionMap.get("432K");// 执行董事
			if (_list != null && _list.size() > 0) {
				content.addErrorMsg("设立董事会，不能设执行董事。");
			}

			int directorSetSize = content.directorSet.size();
			if (directorSetSize < 3) {
				content.addErrorMsg("设立董事会，董事人数不得少于3人。");
			}

		} else { // 如果不设立董事会
			_list = content.positionMap.get("431A");// 董事长
			if (_list != null && _list.size() > 0) {
				content.addErrorMsg("不设董事会，不能设立董事长。");
			}

			int directorSetSize = content.directorSet.size();
			if (directorSetSize > 1) {
				content.addErrorMsg("不设董事会，只能有1名执行董事，不能设其他董事。");
			}

		}
//		checkJsNumber(entId, content);
		checkForignMbr(gid, content);
//		checkLsNumber(content);
		return content.errorMsgs;
	}

	/**
	 * 董事不得兼任监事
	 * 
	 * @param context
	 */
	private static void DsRejectJs(MbrRuleContext context) {
		for (String suspCerNo : context.supervisorSet) {
			if (context.directorSet.contains(suspCerNo)) {
				context.addErrorMsg("董事不得兼任监事。");
				break;
			}
		}
	}

	/**
	 * 理事不得兼任监事
	 * 
	 * @param context
	 */
	private static void LsRejectJs(MbrRuleContext context) {
		for (String suspCerNo : context.supervisorSet) {
			if (context.councilSet.contains(suspCerNo)) {
				context.addErrorMsg("理事不得兼任监事。");
				break;
			}
		}
	}

	public static List<String> ruleCheckWhenSavingAll_withDirectors(
			String gid, MbrRuleContext context) throws OptimusException {
		return ruleCheckWhenSavingAll_withDirectors(gid, context, null);
	}

	/**
	 * 校验监事人数。
	 * 
	 * @param catId
	 * @param context
	 */
	private static void checkJsNumber(String gid, MbrRuleContext context) {
		String isSuped = context.getParams().get("isSuped");
		//String catId = context.getParams().get("catId");

		List<String> _list;
		if ("1".equals(isSuped)) {
			_list = context.positionMap.get("408B");// 监事会主席
			if (_list == null || (_list != null && _list.size() != 1)) {
				context.addErrorMsg("设立监事会，必须设立1名监事会主席信息。");
			}
			// 有限责任 设监事会，其成员不得少于三人
			if (context.supervisorSet.size() < 3) {
				context.addErrorMsg("设立监事会，其成员不得少于3人。");
			}
//			if (NoncpEntTypeUtils.isGf(catId)) {
//				int workerSuspCnt = context.workerSuspList.size();
//				if (workerSuspCnt * 3 < context.supervisorSet.size()) {
//					context.addErrorMsg("设立监事会，“职工监事”的比例不得低于三分之一。");
//				}
//			}
		} else { // 如果不设监事会
			_list = context.positionMap.get("408B");// 监事会主席
			if (_list != null && _list.size() > 0) {
				context.addErrorMsg("不设立监事会，不能设立监事会主席。");
			}
		}
	}

	/**
	 * 
	 0、 所有企业类型 0.1、总经理/经理：变更前没有，则可以没有，否则，变更后必须有。
	 * 
	 * 1、中外合资、中外合作： 1.1、董事会：变更后必须有； 1.2、监事：原来没有，变更有也可以没有；
	 * 
	 * 2、外商独资 2.1、董事（包括：执行董事、普通董事）：原来没有，变更后也可以没有。 2.2、监事：原来没有，变更有也可以没有；
	 * 
	 * @param entId
	 * @param content
	 * @throws OptimusException
	 */
	public static void checkForignMbr(String gid, MbrRuleContext content)
			throws OptimusException {
		// 查询企业类型
		String sql = "select t.*,r.cat_id from be_wk_ent t left join be_wk_requisition r on t.ent_id=r.ent_id where t.gid = ?";
		BeWkEntBO entBO = DaoUtil.getInstance().queryForRowBo(sql, BeWkEntBO.class, gid);

		//String catId = entBO.getCatId();
		String rsEntId = entBO.getRsEntId();

		if (StringUtil.isBlank(rsEntId)) {// 不是变更业务
			return;
		}

		// 变更前有哪些职位？
		sql = "select j.position from cp_rs_job j left join cp_rs_entmember m on j.entmember_id=m.entmember_id where m.ent_id=? ";
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, rsEntId);
		List<String> rsPositions = new ArrayList<String>();
		for (Map<String, Object> rowMap : list) {
			rsPositions.add(StringUtil.safe2String(rowMap.get("position")));
		}

		// 外资在某种情况下可以不设
//		if (NoncpEntTypeUtils.isForeignEnt(catId)) {
//			// 0、 所有企业类型
//			// 0.1、总经理/经理：变更前没有，则可以没有，否则，变更后必须有。
//			if (!rsPositions.contains("436A") && !rsPositions.contains("434Q")) {// 变更前没有经理、总经理
//				content.removeErrorMsg("必须设置经理。");
//				content.removeErrorMsg("必须设置总经理。");
//			}
//		}

		// 1、中外合资、中外合作：
//		if (NoncpEntTypeUtils.isInternationalJointCapital(entType)
//				|| NoncpEntTypeUtils.isInternationalCollaborate(entType)) {
//			// 1.1、董事会：变更后必须有；界面限制
//			// 1.2、监事：原来没有，变更有也可以没有；
//			if (!rsPositions.contains("408A") && !rsPositions.contains("408B")) {// 變更前沒有監事
//				content.removeErrorMsg("股份企业或国有独资企业，必须设立监事会。");
//				content.removeErrorMsg("不设立监事会，监事应为1~2人。");
//
//			}
//		}

		// 2、外商独资
//		if (NoncpEntTypeUtils.isForeignSole(entType)) {
//			// 2.1、董事（包括：执行董事、普通董事）：原来没有，变更后也可以没有。
//			if (!rsPositions.contains("431A") && !rsPositions.contains("431B")
//					&& !rsPositions.contains("432A")
//					&& !rsPositions.contains("432K")
//					&& !rsPositions.contains("432L")) {
//				content.removeErrorMsg("不设董事会，必须设立1名执行董事。");
//			}
//			// 2.2、监事：原来没有，变更后也可以没有；
//			if (!rsPositions.contains("408A") && !rsPositions.contains("408B")) {// 變更前沒有監事
//				content.removeErrorMsg("股份企业或国有独资企业，必须设立监事会。");
//				content.removeErrorMsg("不设立监事会，监事应为1~2人。");
//			}
//		}
	}

	/**
	 * 保存主要人员及其职务前的校验，返回错误信息集合。
	 * 
	 * 如果mbrBo.getEntmemberId()为空，执行新增校验，否则执行更新校验。
	 * 
	 * @param catId
	 *            企业类型，7大类
	 * @param entId
	 *            企业编号
	 * @param mbrBo
	 *            主要人员信息
	 * @param job0
	 *            第一个职位。
	 * @param job1
	 *            第二个职位。
	 * @return 错误信息
	 * @throws OptimusException
	 */
	public static List<String> ruleCheckWhenSavingSingle(String catId,
			String gid, String isBoard, String isSuped,
			BeWkEntmemberBO mbrBo, BeWkJobBO job0, BeWkJobBO job1)
			throws OptimusException {
		// check job0
		if ((job0 == null || StringUtil.isBlank(job0.getPosition()))
				&& (job1 == null || StringUtil.isBlank(job1.getPosition()))) {
			throw new OptimusException("校验错误：未传入职位信息。");
		}

		MbrRuleContext content = new MbrRuleContext(gid, mbrBo);
		
		// check new cerNo
		if(StringUtil.isBlank(content.mbrBo.getCerNo())){
			throw new OptimusException("证件号码不能为空。");
		}

		// 如果是新添加的主要人员，判断该主要人员是否已经存在
		if (mbrBo != null && StringUtil.isBlank(mbrBo.getEntmemberId())) {
			List<String> params = new ArrayList<String>();
			params.add(gid);
			params.add(mbrBo.getCerType());
			params.add(mbrBo.getCerNo());
			String sql = "select count(*) from be_wk_entmember c where c.gid=? and c.cer_type=? and c.cer_no=?";
			long num = DaoUtil.getInstance().queryForOneLong(sql, params);
			if (num > 0) {
				throw new OptimusException("该主要人员已存在，不可重复添加。");
			}
		}

		// check new job 0
		if (job0 != null && !StringUtil.isBlank(job0.getPosition())) {
			_ruleCheckWhenInsertingJob(catId, gid, isBoard, isSuped,
					content.mbrBo, job0, content);
		}

		if (job1 != null && !StringUtil.isBlank(job1.getPosition())) {
			if (job0 != null && !StringUtil.isBlank(job0.getPosition())) {
				// add job 0
				content.addNewMbr(content.mbrBo.getCerNo(), job0.getPosition());
			}
			// check new job 1
			_ruleCheckWhenInsertingJob(catId, gid, isBoard, isSuped,
					content.mbrBo, job1, content);

		}

		return content.errorMsgs;
	}

	/**
	 * 
	 <pre>
	 * A、通用：
	 * 1、董事、经理不得兼任监事（保存单个时校验）
	 * 2、董事都可以兼任经理（不校验）
	 * 3、法定代表人：1（不做法人相关校验）
	 * 4、股份、国有独资，必须设立董事会，必须设立监事会。（保存页面时校验）
	 * 
	 * B、和是否设董事会、监事会相关：
	 * 1、如果设立董事会，董事长：1（页面保存时校验）
	 * 2、如果设立监事会，监事会主席：1（页面保存时校验）
	 * 
	 * 3、如果设立监事会，监事人数：3~无限（保存页面时校验，不能少于3人）
	 * 4、如果设立监事会，职工监事比例不低于1/3（保存页面时校验）
	 * 
	 * 5、如果不设董事会，执行董事人数：1（保存职务时校验不能超过1人，页面保存时校验至少有1人）
	 * 6、如果不设监事会，监事人数：1~2（保存职务时校验不能超过2人，页面保存时校验至少有1人）
	 * 
	 * C、和企业类型相关：
	 * 1、有限，如果设立董事会，董事人数：3-13（保存职务时校验不能超过13人，页面保存时校验至少有3人）
	 * 2、股份，必须设立董事会，董事人数：5-19（保存职务时校验不能超过19人，页面保存时校验至少有5人）
	 * 
	 * D、董事中，人员不能重复；监事中，人员不能重复；经理中，人员不能重复
	 * 有限、国有独资：董事会中至少有1名职工代表
	 * </pre>
	 * 
	 * @param catId
	 * @param entId
	 * @param mbrBo
	 * @param job
	 * @param content
	 * @throws OptimusException
	 */
	protected static void _ruleCheckWhenInsertingJob(String catId,
			String gid, String isBoard, String isSuped,
			BeWkEntmemberBO mbrBo, BeWkJobBO job, MbrRuleContext content)
			throws OptimusException {
		if (mbrBo == null || job == null) {
			return;
		}
		if(StringUtil.isBlank(mbrBo.getCerNo())){
			throw new OptimusException("证件号码不能为空。");
		}
		if(StringUtil.isBlank(job.getPosition())){
			throw new OptimusException("职务不能为空。");
		}
		String newCerNo = mbrBo.getCerNo();
		String newPosition = job.getPosition();

		List<String> _list = null;

		// 同一个人担任同一职务
		if (content.personMap != null
				&& content.personMap.get(newCerNo) != null
				&& newPosition.equals(content.personMap.get(newCerNo).get(0))) {
			content.addErrorMsg("当前主要人员兼任职务重复，请重新选择。");
		}
		// 董事中，人员不能重复；监事中，人员不能重复；经理中，人员不能重复。
		if (content.directorSet.contains(newCerNo) && isDirector(newPosition)) {
			content.addErrorMsg("董事中人员不能重复任职，请核对姓名与证件号码是否相符。");
		}
		if (content.managerSet.contains(newCerNo) && isManager(newPosition)) {
			content.addErrorMsg("经理中人员不能重复任职，请核对姓名与证件号码是否相符。");
		}
		if (content.supervisorSet.contains(newCerNo)
				&& ("408B".equals(newPosition) || "408A".equals(newPosition))) {
			content.addErrorMsg("监事中人员不能重复任职，请核对姓名与证件号码是否相符。");
		}

		// 董事和经理都不能兼任监事。
		if (isSupervisor(newPosition)) {// 新添加一个监事
			if (content.directorSet.contains(newCerNo)) {
				content.addErrorMsg("董事不能兼任监事。");
			}
			if (content.managerSet.contains(newCerNo)) {
				content.addErrorMsg("经理不能兼任监事。");
			}
		}

		if ("431A".equals(newPosition) || "431B".equals(newPosition)
				|| "432A".equals(newPosition) || "432K".equals(newPosition)
				|| "432L".equals(newPosition)) {// 新添加一个董事
			if (content.supervisorSet.contains(newCerNo)) {
				content.addErrorMsg("该人员已经是监事，不能再担任董事。");
			}
		}
		if ("434Q".equals(newPosition) || "434R".equals(newPosition)
				|| "436A".equals(newPosition)) {// 新添加一个经理
			if (content.supervisorSet.contains(newCerNo)) { // 这个人已经是监事
				content.addErrorMsg("该人员已经是监事，不能再担任经理。");
			}
		}

		// 董事长只能有1人，执行董事只能有1人。监事会主席只能有1人。
		// if ("431A".equals(newPosition)) {// 董事长
		// _list = content.positionMap.get("431A");
		// if (_list != null && _list.size() > 0) {
		// content.addErrorMsg("董事长只能有1人，请先取消其他人的董事长职位。");
		// }
		// }
		// if ("432K".equals(newPosition)) {// 执行董事
		// _list = content.positionMap.get("432K");
		// if (_list != null && _list.size() > 0) {
		// content.addErrorMsg("执行董事只能有1人，请先取消其他人的执行董事职位。");
		// }
		// }
		// if ("408B".equals(newPosition)) {// 监事会主席
		// _list = content.positionMap.get("408B");
		// if (_list != null && _list.size() > 0) {
		// content.addErrorMsg("监事会主席只能有1人，请先取消其他人的监事会主席职位。");
		// }
		// }
		// // 经理只能有1人。外资总经理只能有1人。
		// if ("434Q".equals(newPosition)) {// 总经理
		// _list = content.positionMap.get("434Q");
		// if (_list != null && _list.size() > 0) {
		// content.addErrorMsg("总经理只能有1人，请先取消其他人的总经理职位。");
		// }
		// }
		// if ("436A".equals(newPosition)) {// 经理
		// _list = content.positionMap.get("436A");
		// if (_list != null && _list.size() > 0) {
		// content.addErrorMsg("经理只能有1人，请先取消其他人的经理职位。");
		// }
		// }

		// 如果设立董事会
		if ("1".equals(isBoard)) {

			if ("431A".equals(newPosition)) {
				_list = content.positionMap.get("431A");// 董事长
				if (_list != null && _list.size() == 1) {
					content.addErrorMsg("设立董事会，董事长只能1人。");
				}
			}

			if (isDirector(newPosition)) {
				int directorSetSize = content.directorSet.size();
				// 有限责任董事
				if (("1100".equals(catId) || "5100".equals(catId))
						&& (directorSetSize == 13)) {
					content.addErrorMsg("设立董事会，董事人数最多13人。");
				}

				// 股份 董事5-19个
				if (("1200".equals(catId) || "5200".equals(catId))
						&& (directorSetSize == 19)) {
					content.addErrorMsg("股份企业，董事人数最多19人。");
				}
			}

			if ("432K".equals(newPosition)) {
				content.addErrorMsg("设立董事会，不能设立执行董事。");
			}
		} else { // 如果不设立董事会

			if ("431A".equals(newPosition)) {
				content.addErrorMsg("不设董事会，不能设立董事长。");
			}
		}
		// 如果设立监事会
		if ("1".equals(isSuped)) {
			if ("408B".equals(newPosition)) {
				_list = content.positionMap.get("408B");// 监事会主席
				if (_list != null && _list.size() == 1) {
					content.addErrorMsg("设立监事会，监事会主席只能1人。");
				}
			}
		} else { // 如果不设监事会
			if (isSupervisor(newPosition)) {
				if (content.supervisorSet.size() == 2) {
					content.addErrorMsg("不设立监事会，监事最多为2人。");
				}
			}
			if ("408B".equals(newPosition)) {
				content.addErrorMsg("不设监事会，不能设立监事会主席。");
			}
		}
	}

	/**
	 * List中没有重复元素。
	 * 
	 * @param list
	 * @return 如果没有重复元素，返回true，否则，返回false。
	 */
	public static boolean isUnique(List<String> list) {
		if (list == null) {
			return true;
		}
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		boolean ret = list.size() == set.size();
		return ret;
	}

	/**
	 * 功能：是否是经理
	 * 
	 * @param position
	 * @return boolean
	 */
	public static boolean isManager(String newPosition) {
		return "434Q".equals(newPosition) || "434R".equals(newPosition)
				|| "436A".equals(newPosition);
	}

	/**
	 * 功能：是否是董事
	 * 
	 * @param position
	 * @return boolean
	 */
	public static boolean isDirector(String newPosition) {
		return "431A".equals(newPosition) || "431B".equals(newPosition)
				|| "432A".equals(newPosition) || "432L".equals(newPosition)
				|| "432K".equals(newPosition);
	}

	/**
	 * 功能：是否是监事
	 * 
	 * @param position
	 * @return boolean
	 */
	public static boolean isSupervisor(String position) {
		return "408A".equals(position) || "408B".equals(position);
	}



	private static void positionNumHighLimit(MbrRuleContext context,
			String position, String text, int num) {
		List<String> list = context.positionMap.get(position);
		if (list != null && list.size() > num) {
			context.addErrorMsg(text + "只能有" + num + "人。");
		}
	}

	private static void positionNumNotEmpty(MbrRuleContext context,
			String position, String text) {
		List<String> list = context.positionMap.get(position);
		if (list == null || list.isEmpty()) {
			context.addErrorMsg("必须设置" + text + "。");
		}
	}

	/**
	 * 每个人最多两个职务 。
	 * 
	 * @param context
	 * @throws OptimusException
	 */
	private static void checkPositionCnt(MbrRuleContext context)
			throws OptimusException {
		for (Map.Entry<String, List<String>> map : context.personMap.entrySet()) {
			List<String> positionList = map.getValue();
			if (positionList.size() > 2) {
				context.addErrorMsg("每个人最多两个职务。");
				return;
			}
		}
	}

	
}
