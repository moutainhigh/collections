package com.gwssi.ebaic.apply.setup.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.setup.service.SetupBusiScopeService;
import com.gwssi.ebaic.apply.util.SplitWordUtil;
import com.gwssi.ebaic.common.util.DictUtil;
import com.gwssi.ebaic.torch.event.BaseEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.torch.util.StringUtil;

@Service("applySetupBasicAfterEvent")
public class ApplySetupBasicAfterEvent extends BaseEventListener {
	
	@Autowired 
	SetupBusiScopeService setupBusiScopeService;

	final String SHIJU_CODE = "110000000";// 市工商局 SYSMGR_ORG
	
	public void exec(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			throw new EBaicException("获取参数异常！");
		}
		
		String gid = params.get("gid");
		if (StringUtils.isBlank(gid)) {
			throw new EBaicException("获取gid异常！");
		}
		// 保存经营范围
		String scopeJson = ParamUtil.get("scopeJson",false);
		if (StringUtils.isBlank(scopeJson)) {
			throw new EBaicException("经营范围数据不能为空。");
		}
		String businessScope = ParamUtil.get("businessScope",false);
		if (StringUtils.isBlank(scopeJson)) {
			throw new EBaicException("经营范围不能为空。");
		}
		setupBusiScopeService.save(gid,scopeJson,businessScope);
		
		// 确定登记管辖机关
		String regOrg = getRegOrg(gid,params);
		String reqSql = " update be_wk_requisition r set r.reg_org = ?,r.timestamp = sysdate where r.gid = ? ";
		DaoUtil.getInstance().execute(reqSql, regOrg,gid);
		
		// 保存住所、登记管辖
		saveOpLoc(params,gid,regOrg);
		
	}
	
	/**
	 * 准备相关的参数
	 * @param gid
	 * @return
	 */
	public Map<String,Object> prepareParams(String gid) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " select tr.transact_auth,r.cat_id, t.ent_type,t.op_loc,t.dom_district,t.dom_detail "+
				     " from nm_rs_name n "+
				     " left join be_wk_ent t on n.not_no = t.name_app_id and n.ent_name = t.ent_name "+
				     " left join nm_rs_transact tr on tr.name_id = n.name_id "+
				     " left join be_wk_requisition r on r.gid = t.gid "+
				     " where t.gid = ? ";
		List<String> param = new ArrayList<String>();
		param.add(gid);
		map = DaoUtil.getInstance().queryForRow(sql, param);
		return map;
	}
	
	
	//确定管辖机关
	public String getRegOrg(String gid,Map<String, String> rawParams){
		Map<String,Object> map = prepareParams(gid);
		String domDistrict = StringUtil.safe2String(map.get("domDistrict"));
		String domDetail  = StringUtil.safe2String(map.get("domDetail"));
		String regOrg = getRegOrgByDistrict(domDistrict,domDetail);
		
		//String nameRegOrg = (String) map.get("transactAuth");// 名称审核机关
		
		return regOrg;
	}
	
	/**
	 * 按住所所在区域决定办理机关。
	 */
	private String getRegOrgByDistrict(String domDistrict,String opLoc) {
		//经济开发区
		if ("110130".equals(domDistrict)) {
			return "110302000";
		}
		if(StringUtils.isEmpty(opLoc)){
			throw new EBaicException("住所不能为空");
		}
		
		// 房山区，住所中包含“燕山”字样，分派到燕山分局
		if("110111".equals(domDistrict) && opLoc.indexOf("燕山")!=-1){
			return "110304000";
		}
		return domDistrict + "000"; 
	}
	
	
	
	/**
	 * 
	 * @param addressMap
	 * @param gid
	 * @return
	 */
	public void saveOpLoc(Map<String, String> params,String gid,String regOrg) {
		
		String domDetail = params.get("domDetail");
		String domDistrict = params.get("domDistrict");
		String proLocCity = params.get("proLocCity");
		String proLocOther = params.get("proLocOther");
		
		if (StringUtils.isBlank(domDetail)) {
			throw new EBaicException("获取domDetail异常！");
		}
		if (StringUtils.isBlank(domDistrict)) {
			throw new EBaicException("获取domDistrict异常！");
		}
		if(StringUtils.isBlank(proLocCity)){
			throw new EBaicException("获取proLocCity异常！");
		}
		if(StringUtils.isBlank(proLocOther)){
			throw new EBaicException("获取proLocOther异常！");
		}
		
		
		proLocCity = DictUtil.getText("CA01", proLocCity);
		domDistrict =DictUtil.getText("CA01", domDistrict); 
		
		
		String proLoc = "北京市" + proLocCity + proLocOther;
		String proLocprov = "110000";//省只有北京市  TODO
		String opLoc = "北京市" + domDistrict + domDetail;
		// 将住所分隔到多个字段并插入到表中
		Map<String, String> addressMap = SplitWordUtil.splitAddress(domDetail);

		if (addressMap == null || addressMap.isEmpty()) {
			throw new EBaicException("获取地址信息异常");
		}
		String domStreet = StringUtil.safe2String(addressMap.get("domStreet"));
		String domVillage = StringUtil.safe2String(addressMap.get("domVillage"));
		String domNo = StringUtil.safe2String(addressMap.get("domNo"));
		String domBuliding = StringUtil.safe2String(addressMap.get("domBuliding"));
		String domOther = StringUtil.safe2String(addressMap.get("domOther"));
		
		List<Object> listParam = new ArrayList<Object>();
		listParam.add(proLoc);
		listParam.add(proLocprov);
		listParam.add(opLoc);
		listParam.add(domDetail);
		listParam.add(domStreet);
		listParam.add(domVillage);
		listParam.add(domNo);
		listParam.add(domBuliding);
		listParam.add(domOther);
		listParam.add(regOrg);
		
		listParam.add(gid);//主键
		Object[] paramsObject = listParam.toArray();
		String sql = " update be_wk_ent t set t.pro_loc=?,t.pro_loc_prov=?,t.op_loc=?,t.dom_detail=?,t.dom_street=?,t.dom_village=?,t.dom_no=?,t.dom_building=?,t.dom_other=?,t.reg_org=? where t.gid = ? ";
		DaoUtil.getInstance().execute(sql, paramsObject);

	}
}
