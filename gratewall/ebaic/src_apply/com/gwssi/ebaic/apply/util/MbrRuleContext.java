package com.gwssi.ebaic.apply.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.domain.BeWkEntmemberBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;

public class MbrRuleContext{
	
	protected final static Logger logger = Logger.getLogger(MbrRuleContext.class);
	
	public BeWkEntmemberBO mbrBo = null;
	
	@SuppressWarnings("rawtypes")
	public List<Map> members = null;
	
	public Map<String, List<String>> personMap = new HashMap<String, List<String>>();
	public Map<String, List<String>> positionMap = new HashMap<String, List<String>>();
	
	public List<String> allSet = new ArrayList<String>();
	public List<String> directorSet = new ArrayList<String>();// 董事
	public List<String> managerSet = new ArrayList<String>();// 经理
	public List<String> supervisorSet = new ArrayList<String>();// 监事
	public List<String> councilSet = new ArrayList<String>();// 理事
	public List<String> workerSuspList = new ArrayList<String>();
	
	public List<String> errorMsgs = new ArrayList<String>();
	
	private Map<String, String> params = new HashMap<String, String>();
	
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public MbrRuleContext(String gid) throws OptimusException {
		this(gid, null);
	}

	/**
	 * 构造函数。
	 * @param entMemberBO
	 * @throws OptimusException
	 */
	public MbrRuleContext(String gid, BeWkEntmemberBO mbrBo) throws OptimusException{
		
		if( mbrBo!=null && StringUtil.isBlank(mbrBo.getCerNo()) && StringUtil.isBlank(mbrBo.getEntmemberId())){
			this.mbrBo = DaoUtil.getInstance().get(BeWkEntmemberBO.class, mbrBo.getEntmemberId());
		}else{
			this.mbrBo = mbrBo ;
		}
		String mbrId = (this.mbrBo==null)? null : this.mbrBo.getEntmemberId();
		// 1、构造主要人员和职务Map		
		this.members = buildCheckMapsWithEntId(gid,mbrId, this.personMap, this.positionMap, this.workerSuspList);
		// 2、构造董经监集合
		_buildPositionsList(this.positionMap, this.directorSet, this.managerSet, this.supervisorSet,this.councilSet);
		if(logger.isDebugEnabled()){
			logger.debug("gid :" + gid 
					+ ", entmemberId : "+ mbrId 
					+ ", personMap : " + JSON.toJSONString(this.personMap) 
					+ ", positionMap : " + JSON.toJSONString(this.positionMap));
			
			logger.debug("directorSet : " + JSON.toJSONString(this.directorSet)
					+ ", managerSet ： " + JSON.toJSONString(this.directorSet)
					+ ", supervisorSet ： " + JSON.toJSONString(this.supervisorSet)
					+ ", councilSet ： " + JSON.toJSONString(this.councilSet));			
		}
	}
	
	public void addNewMbr(String newCerNo,String newPosition) {

		List<String> _list;
		
		// put into personMap
		_list = this.personMap.get(newCerNo);
		_list = (_list == null) ? new ArrayList<String>() : _list;
		_list.add(newPosition);
		
		this.personMap.put(newCerNo, _list);
		// put into positionMap
		_list = this.positionMap.get(newPosition);
		_list = (_list == null) ? new ArrayList<String>() : _list;
		_list.add(newCerNo);
		this.positionMap.put(newPosition, _list);

	}

	/**
	 * 增加错误信息。
	 * @param errorMsg
	 */
	public void addErrorMsg(String errorMsg){
		this.errorMsgs.add(errorMsg);
	}
	
	public void addErrorListMsg(List<String> errorMsg){
		this.errorMsgs.addAll(errorMsg);
	}
	public void removeErrorMsg(String errorMsg){
		this.errorMsgs.remove(errorMsg);
	}
	/**
	 * [1]
	 * 根据entId返回主要人员及其职务。
	 * 
	 * 标记为“被删除”的主要人员不返回。
	 * 
	 * @param entId
	 * @param entmemberId 如果传入entmemberId为空，则返回当前企业编号所属的所有主要人员及其职务。
	 * 					       如果entmemberId不为空，则不返回entmemberId指定的主要人员及其职务。
	 * @return List<Map>
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	protected List<Map> _queryEntMemberAndJobList(String gid, String entmemberId)
			throws OptimusException {
		String sql = "SELECT m.cer_type,m.cer_no,m.le_rep_sign as m_le_rep_sign,j.le_rep_sign as j_le_rep_sign,j.position,j.sups_type FROM cp_wk_entmember m LEFT JOIN cp_wk_job j ON m.entmember_id=j.entmember_id WHERE m.gid = ?  AND (m.modify_sign is null or m.modify_sign Not In ('3','30','31','32','33')) ";
		List<String> param = new ArrayList<String>();
		param.add(gid);
		if (StringUtil.isNotBlank(entmemberId)) {
			sql += " and m.entmember_id <> ? ";
			param.add(entmemberId);
		}
		List<Map> ret = DAOManager.getPersistenceDAO().queryForList(sql, param);
		return ret;
	}
	
	/**
	 * [2]
	 * 构造主要人员校验的数据。
	 * 
	 * @param entId 			企业编号
	 * @param entmemberId 		主要人员编号
	 * @param personMap 		人员集合，传入空Map，作为结果返回，结构：Map<String,List<String>>，Key为CerNo，值为职位编号List。
	 * @param positionMap 		职位集合，传入空Map，作为结果返回，结构：Map<String,List<String>>，Key为职位编号，值为CerNo List。
	 * @param workerSuspList 	职工监事数量，传入空List，作为结果返回，结构：List<String>，为所有职工监事身份证的列表。
	 * @return List<Map>
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "rawtypes" })
	protected List<Map> buildCheckMapsWithEntId(String gid, String entmemberId,
			Map<String, List<String>> personMap,
			Map<String, List<String>> positionMap,
			List<String> workerSuspList) throws OptimusException {
		List<Map> members = _queryEntMemberAndJobList(gid, entmemberId);
		if(members==null || members.isEmpty()){
			// this.addErrorMsg("当前企业没有主要人员。");
		}else{
			for (Map row : members) {
				String cerNo = (String) row.get("cerNo");
				String position = (String) row.get("position");
				String supsType = (String) row.get("supsType");
				// 1、主要人员集合
				__buildDataMap(personMap, cerNo, position);
				// 2、职位集合
				__buildDataMap(positionMap, position, cerNo);
				// 3、职工监事数量
				if (workerSuspList != null && "2".equals(supsType)) {
					workerSuspList.add(cerNo);
				}
			}
		}
		return members;
	}	
	/**
	 * [3]
	 * 向Map中添加新数据。
	 * 
	 * @param resultMap 作为结果返回的数据，结构为Map<String,List<String>>，key为身份证件号码或职位编码，value为对应的职位列表或身份证件号码列表。
	 * @param newKey	 将要新加入的key
	 * @param newValue	
	 */
	private void  __buildDataMap(Map<String, List<String>> resultMap, String newKey,String newValue) {
		
		// 找到原来的Value List
		List<String> oriValueList = resultMap.get(newKey);
		// 如果原来的Value List为空，建一个新的
		if (oriValueList == null){
			oriValueList = new ArrayList<String>();
		}
		// 放入新值
		oriValueList.add(newValue);
		resultMap.put(newKey, oriValueList);
	}
	/**
	 * [4]
	 * 功能：构造董经监集合。
	 * 
	 * @param positionMap  	职位集合，将被分别存储到董经监集合中。
	 * @param directorSet 	董事集合。
	 * @param managerSet 	经理集合。
	 * @param supervisorSet 监事集合。
	 * @throws OptimusException 
	 */
	private void _buildPositionsList(Map<String, List<String>> positionMap,
			List<String> directorSet, List<String> managerSet,
			List<String> supervisorSet, List<String> councilSet) throws OptimusException {

		String[] directorArr = { "431A", "431B", "432A", "432L", "432K" };
		__buildPositionList(positionMap, directorArr, directorSet);
		
		String[] managerArr = { "434Q", "434R", "436A" };
		 __buildPositionList(positionMap, managerArr, managerSet);
		
		String[] supervisorArr = { "408A", "408B" };
		__buildPositionList(positionMap, supervisorArr, supervisorSet);
		
		String[] councilArr = { "405A", "405B" , "406A" };
		__buildPositionList(positionMap, councilArr, councilSet);
		
		__buildAllSet(allSet);
		
	}

	@SuppressWarnings("unchecked")
	private void __buildAllSet(List<String> allSet) {
		if(allSet==null){
			allSet = new ArrayList<String>();
		}else{
			allSet.clear();
		}
		
		if(this.members==null || this.members.size()==0){
			return ;
		}
		
		for(Map<String, Object> row : this.members){
			String cerNo = StringUtil.safe2String(row.get("cerNo"));
			allSet.add(cerNo);
		}
	}

	/**
	 * [5]
	 * 构造董经监集合。
	 * 
	 * @param positionMap 
	 * @param cerNoList
	 * @param positionArr
	 * @throws OptimusException 
	 */
	protected void __buildPositionList(final Map<String, List<String>> positionMap,
			String[] positionArr,List<String> positionTypeList) throws OptimusException {
		if(positionMap ==null){ 
			return; 
		}
		
		if(positionTypeList==null){
			throw new OptimusException("传入的集合不能为空。");
		}
		
		positionTypeList.clear();
		if(positionArr!=null){
			for (String position : positionArr) {
				List<String> list = positionMap.get(position);
				if (list != null) {
					positionTypeList.addAll(list);
				}
			}
		}else{
			for(Entry<String, List<String>> entry : positionMap.entrySet()){
				List<String> list = entry.getValue();
				if (list != null) {
					positionTypeList.addAll(list);
				}
			}
		}
	}
}