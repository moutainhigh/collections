package com.report.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.bean.FileDown;
import com.report.bean.ShangShiZhuTiFileDown;
import com.report.dao.BaseDao;
import com.report.util.PageInfo;
import com.report.util.StrUtil;

@Transactional
@Service
public class CongYeRenYuanService  extends BaseDao {

private static Logger logger = Logger.getLogger("cyry"); // 获取logger实例
	
	
	public List getList(String time) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.regorg, t.regorg_cn, t.adminbrancode, d.dep_name, count(1) ");
		sb.append(" from dc_ra_mer_base_query t, dc_jg_sys_right_department d ");
		sb.append("  where t.adminbrancode = d.sys_right_department_id(+) ");
		sb.append("  and t.regstate = '1' ");
		//sb.append("   and t.estdate < to_date('2018-12-25', 'yyyy-mm-dd') ");
		sb.append("   and t.estdate < to_date(?, 'yyyy-mm-dd') ");
		sb.append("   group by t.regorg, t.regorg_cn, t.adminbrancode, d.dep_name ");
		sb.append(" order by instr('福田局, 罗湖局, 盐田局,南山局,宝安局,龙岗局,龙华局,坪山局,光明局,大鹏局', t.regorg_cn) , t.adminbrancode ");

		
		logger.info("执行的SQL语句====> " + sb.toString());
		Query query = this.getSession().createSQLQuery(sb.toString()).setParameter(0, time);
		
		
		
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;
	}
	
	
	
	public void save(ShangShiZhuTiFileDown fiDown) {
		this.getSession().save(fiDown);
	}
	
	
	/*public void delete(List lists ) throws Exception {
		String hql = "delete  from  ShangShiZhuTiFileDown t where  t.id in (:lists)";
		Query query = this.getSession().createQuery(hql);
		query.setParameterList("lists",lists);//第二个参数idList为传入参数，idList中的值的类型与DB中ID类型一致
		query.executeUpdate();
	}*/
	
	
	
	
	
	public void delete(String  end) {
		String hql = "delete  from  ShangShiZhuTiFileDown t where 1=1 and t.createTime <= ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, end);
		query.executeUpdate();
	}
	
	public List getFileToDown(String time, PageInfo dgparam) {
		Query query = null;
		String hql = null;
		List list = null;
		try {
			hql = "from ShangShiZhuTiFileDown where 1 = 1";
			if (!StrUtil.isEmpty(time)) {
				hql += " and createTime = :dateQueryTime order by dateQueryTime desc";
				query = this.getSession().createQuery(hql);
				query.setParameter("dateQueryTime", time);
				int start = dgparam.getPage()*dgparam.getRows();
				query.setFirstResult(start);
				query.setMaxResults(dgparam.getRows());
			} else {
				hql+=" order by createTime desc ";
				query = this.getSession().createQuery(hql);
				int start = dgparam.getPage()*dgparam.getRows();
				query.setFirstResult(start);
				query.setMaxResults(dgparam.getRows());
			}
			list  = query.list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}


	public Integer getTotal(String time, PageInfo dgparam) {
		try {
			String hqlString = "select count(f.id) from ShangShiZhuTiFileDown f  where 1 = 1 ";
			Query query = null;
			if (!StrUtil.isEmpty(time)) {
				hqlString += " and createTime=:dateQueryTime ";
				query = this.getSession().createQuery(hqlString);
				query.setParameter("dateQueryTime", time);
			} else {
				query = this.getSession().createQuery(hqlString);
			}
			Integer count = ((Number) query.uniqueResult()).intValue();
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
}
