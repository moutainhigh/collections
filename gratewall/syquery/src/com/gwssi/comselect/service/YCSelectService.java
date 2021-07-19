package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "ycSelectService")
public class YCSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 异常名录查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSXList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select case when s.unifsocicrediden is null then s.regno else s.unifsocicrediden end as regno,"
        +"s.entname,"
        +"s.ENTSTATUS as ENTSTATUS,"
        +"s.id as pripid,"
        +"s.enttype as type,"
        +"s.opetype,"
        +"s.pripid as entid,"
        +"b.id as id,"
        +"b.RESOLEUNIT,"
        +" case b.ABNORMALTYPE"
        +" when '1' then '通过登记的住所或经营场所无法联系'"
        +" when '2' then '未在责令的期限内公示即时信息'"
        +" when '3' then '公示企业信息隐瞒真实情况，弄虚作假'"
        +" when '4' then '个体工商户年度报告隐瞒真实情况，弄虚作假'"
        +" when '5' then '未按规定期限公示年度报告' end as ABNORMALTYPE,"
        +"b.CREATETIME,"
        +"b.REMOVETYPECN,"
        +"b.REMOVETIME,"
        +"b.REMARK,"
        +"case b.TYPE "
        +" when '1' then '拟载入'"
        +" when '2' then '载入'"
        +" when '3' then '移出' end as btype,"
        +"b.REMOVEDEPT,"
        +"b.PUBLICTIME"
        +" from dc_ra_mer_base s, dc_ms_abnormal_diretory b"
        +" where trim(s.pripid) = b.entid ");
		List list=new ArrayList();
		if (map!=null) {
			Object entname = map.get("entname");
			if (entname!=null) {
				if (entname.toString().length()>0) {
						sql.append(" and entname = ?");
						list.add(entname.toString().trim());
					}
				}
			}
			
			Object regno = map.get("regno");
			if (regno!=null) {
				if (regno.toString().length()>0) {
					sql.append(" and (regno = ? or unifsocicrediden = ?)");
					list.add(regno.toString().trim());
					list.add(regno.toString().trim());
				}
			
			
		}
		//String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		/*sql.append(" ORDER BY REG_DATE desc");*/
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("快速查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	/**
	 * 根据sExtSequence获取异常目录的查询信息详情
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getSXQueryById(String sExtSequence) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select case when s.unifsocicrediden is null then s.regno else s.unifsocicrediden end as regno,"
		        +"s.entname,"
		        +"b.id as id,"
		        +"b.RESOLEUNIT,"
		        +" case b.ABNORMALTYPE"
	            +" when '1' then '通过登记的住所或经营场所无法联系'"
	            +" when '2' then '未在责令的期限内公示即时信息'"
	            +" when '3' then '公示企业信息隐瞒真实情况，弄虚作假'"
	            +" when '4' then '个体工商户年度报告隐瞒真实情况，弄虚作假'"
	            +" when '5' then '未按规定期限公示年度报告' end as ABNORMALTYPE,"
		        +"b.CREATETIME,"
		        +"b.REMOVETYPECN,"
		        +"b.REMOVETIME,"
		        +"b.REMARK,"
		        +"case b.TYPE "
		        +" when '1' then '拟载入'"
	            +" when '2' then '载入'"
	            +" when '3' then '移出' end as btype,"
		        +"b.REMOVEDEPT,"
		        +"b.PUBLICTIME"
		        +" from dc_ra_mer_base s, dc_ms_abnormal_diretory b"
		        +" where trim(s.pripid) = trim(b.entid) and b.id = ?");
		List list=new ArrayList();
		list.add(sExtSequence);
		Map res = dao.pageQueryForList(sql.toString(), list).get(0);
		return res;
	}

}
