package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.comselect.utils.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value="yzwfsxSelectService")
public class YZWFSXSelectService extends BaseService {
	
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";

	/**
	 * 查询严重违法失信企业列表
	 * @param map
	 * @param httpRequest
	 * @return
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getEntList(Map map, HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql = new StringBuffer();
		sql.append("select t.regno,t.pripid as id, t.entid, t.entname, t.enttype_cn as enttype, "
				+ " t.estdate, t.regstate_cn as regstate, t.regstate as entstatus, t.opetype, "
				+ " t.enttype as type from dc_ra_mer_base_query t "
				+ " where entid in (select entid from dc_ms_dishonesty_directory a where a.type = '2')");
		
		List<String> params = new ArrayList<String>();
		
		if (map != null) {
			Object entname = map.get("entname");
			if (entname!=null) {
				if (entname.toString().length()>0) {
						sql.append(" and entname = ?");
						params.add(entname.toString().trim());
					}
				}
			
			Object regno = map.get("regno");
			if (regno!=null) {
				if (regno.toString().length()>0) {
					sql.append(" and (regno = ? or regno_old = ?)");
					params.add(regno.toString().trim());
					params.add(regno.toString().trim());
				}	
			}
			
			Object regorg = map.get("regorg");
			if (regorg!=null) {
				if (regorg.toString().length()>0) {
					sql.append(" and regorg = ?");
					params.add(regorg.toString().trim());
				}	
			}
			
			Object adminbrancode = map.get("adminbrancode");
			if (adminbrancode!=null) {
				if (adminbrancode.toString().length()>0) {
					sql.append(" and adminbrancode = ?");
					params.add(adminbrancode.toString().trim());
				}	
			}
			
			Object entstatus = map.get("entstatus");
			if (entstatus!=null) {
				if (entstatus.toString().length()>0) {
					sql.append(" and regstate = ?");
					params.add(entstatus.toString().trim());
				}	
			}
			
			String area = (String)map.get("area");
			if (area!=null) {
				if ("-1".equals(area)) { //全部
					sql.append(" and t.pripid in (select b.id from dc_ns_enterprise_list b)");
				}else if ("1".equals(area)) { //前海
					sql.append(" and t.pripid in (select b.id from dc_ns_enterprise_list b where b.addr_flag = ?)");
					params.add(area);
				}else if ("0".equals(area)) { //蛇口
					sql.append(" and  t.pripid in (select b.id from dc_ns_enterprise_list b where b.addr_flag = ?)");
					params.add(area);
				}	
			}
		}
		 List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(), params);
		 LogUtil.insertLog("严重违法失信", sql.toString(), params.toString(), httpRequest, dao);
		 return pageQueryForList;
	}

	/**
	 * 严重违法失信企业
	 * @param map
	 * @param httpRequest
	 * @return
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSXList(String entid, HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		if (StringUtil.isNotEmpty(entid)) {
			sql.append("select t.id,"
				      +" t.entid,"
				      +" t.createtime,"
				      +" case t.type"
				      +" when '1' then '拟列入'"
				      +" when '2' then '已列入'"
				      +" when '3' then '移除' end as ttype,"
				      +" t.resoleunit,"
				      +" t.removereason,"
				      +" case t.illrea"
				      +" when '1' then '被列入经营异常名录届满3年仍未履行相关义务的'"
				      +" when '2' then '提交虚假材料或者采取其他欺诈手段隐瞒重要事实，取得公司变更或者注销登记，被撤销登记的'"
				      +" when '3' then '组织策划传销的，或者因为传销行为提供便利条件两年内收到三次以上行政处罚的'"
				      +" when '4' then '因直销违法行为两年内收到三次以上行政处罚的'"
				      +" when '5' then '因不正当竞争行为两年内收到三次以上行政处罚的'"
				      +" when '6' then '因提供的商品或者服务不符合保障人身、财产安全要求，造成人身伤害等严重侵害消费者权益的违法行为，两年内收到三次以上行政处罚的'"
				      +" when '7' then '因发布虚假广告两年内受到三次以上行政处罚的，或者发布关系消费者生命健康的商品或者服务的虚假广告，造成人身伤害的或者其他严重社会不良影响的'"
				      +" when '8' then '因商品侵权行为五年内收到两次以上行政处罚的'"
				      +" when '9' then '被决定停止受理商标代理业务的'"
				      +" when '10' then '国家工商行政管理总局规定的其他违法工商行政管理法律、行政法规且情节严重的'"
				      +" when '11' then '企业有《严重违法失信企业名单管理暂行办法》第五条第一款第（三）项至第（八）项规定行为之一，两年内累计受到三次以上行政处罚的' end as illrea"
				  +" from dc_ms_dishonesty_directory t"
				  +" where t.entid = ?");
			params.add(entid);
			return dao.queryForList(sql.toString(), params);
		} else {
			return null;
		}
	}

	/**
	 * 根据sExtSequence查询严重违法失信详细信息
	 * @param id
	 * @param httpRequest
	 * @return
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	public Map getSXInfoById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		sql.append("select"
			      +" t.createtime,"
			      +" case t.type"
			      +" when '1' then '拟列入'"
			      +" when '2' then '已列入'"
			      +" when '3' then '移除' end as ttype,"
			      +" t.resoleunit,"
			      +" t.removereason,"
			      +" t.removetime,"
			      +" t.removeoperatordept,"
			      +" case t.illrea"
			      +" when '1' then '被列入经营异常名录届满3年仍未履行相关义务的'"
			      +" when '2' then '提交虚假材料或者采取其他欺诈手段隐瞒重要事实，取得公司变更或者注销登记，被撤销登记的'"
			      +" when '3' then '组织策划传销的，或者因为传销行为提供便利条件两年内收到三次以上行政处罚的'"
			      +" when '4' then '因直销违法行为两年内收到三次以上行政处罚的'"
			      +" when '5' then '因不正当竞争行为两年内收到三次以上行政处罚的'"
			      +" when '6' then '因提供的商品或者服务不符合保障人身、财产安全要求，造成人身伤害等严重侵害消费者权益的违法行为，两年内收到三次以上行政处罚的'"
			      +" when '7' then '因发布虚假广告两年内受到三次以上行政处罚的，或者发布关系消费者生命健康的商品或者服务的虚假广告，造成人身伤害的或者其他严重社会不良影响的'"
			      +" when '8' then '因商品侵权行为五年内收到两次以上行政处罚的'"
			      +" when '9' then '被决定停止受理商标代理业务的'"
			      +" when '10' then '国家工商行政管理总局规定的其他违法工商行政管理法律、行政法规且情节严重的'"
			      +" when '11' then '企业有《严重违法失信企业名单管理暂行办法》第五条第一款第（三）项至第（八）项规定行为之一，两年内累计受到三次以上行政处罚的' end as illrea"
			  +" from dc_ms_dishonesty_directory t"
			  +" where t.id = ?");
		params.add(id);
		Map map = dao.queryForList(sql.toString(), params).get(0);
		return map;
	}
	

}
