package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
@Service(value = "zzQueryService")
public class ZZQueryService extends BaseService{
	/**
	 *   数据源
	 */
	private static final String DATASOURS ="dc_dc";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> ZZQuery(Map<String, String> form,
			HttpServletRequest httpRequest) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list= new ArrayList();
		StringBuffer sb=new StringBuffer();
		if (form!=null) {
			if (form.get("certtype")!=null&&"1".equals(form.get("certtype"))) {
				sb.append("select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id from dc_dc.jsgcjgghyshgz104358 b where 1=1 \n");
				if (form.get("projectname")!=null&&form.get("projectname").length()!=0) {
					sb.append(" and b.xmmc= ? \n");
					list.add(form.get("projectname").trim());
				}
				if (form.get("certno")!=null&&form.get("certno").length()!=0) {
					sb.append(" and b.fzbh= ?  \n");
					list.add(form.get("certno").trim());
				}
				if (form.get("ficationdate")!=null&&form.get("ficationdate").length()!=0) {
					sb.append(" and to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd')= ?  \n");
					list.add(form.get("ficationdate").trim());
				}
			}else if(form.get("certtype")!=null&&"2".equals(form.get("certtype"))){
				sb.append("select b.PROJ_NAME as projectname,to_char(to_date(b.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,b.LICENSE_NO as certno ,'2' as certtype,'' as opt ,b.id as id  from dc_dc.jsgcghxkz121798 b where 1=1 \n");
				if (form.get("projectname")!=null&&form.get("projectname").length()!=0) {
					sb.append(" and b.PROJ_NAME= ? \n");
					list.add(form.get("projectname").trim());
				}
				if (form.get("certno")!=null&&form.get("certno").length()!=0) {
					sb.append(" and b.LICENSE_NO= ? \n");
					list.add(form.get("certno").trim());
				}
				if (form.get("ficationdate")!=null&&form.get("ficationdate").length()!=0) {
					sb.append(" and to_char(to_date(b.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') = ? \n");
					list.add(form.get("ficationdate").trim());
				}
			}else{
				if ((form.get("projectname")!=null&&form.get("projectname").length()!=0)
				    &&(form.get("certno")!=null&&form.get("certno").length()!=0)
				    &&(form.get("ficationdate")!=null&&form.get("ficationdate").length()!=0)) {
					sb.append(
								"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id from dc_dc.jsgcjgghyshgz104358 b\n" +
								"      where 1=1 and b.xmmc= ? and b.fzbh= ? and to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd')= ?\n" + 
								"      union all\n" + 
								"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype,'' as opt ,c.id as id  from dc_dc.jsgcghxkz121798 c\n" + 
								"       where 1=1 and c.PROJ_NAME= ? and c.LICENSE_NO= ? and to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') = ?");
					list.add(form.get("projectname").trim());
					list.add(form.get("certno").trim());
					list.add(form.get("ficationdate").trim());
					list.add(form.get("projectname").trim());
					list.add(form.get("certno").trim());
					list.add(form.get("ficationdate").trim());
				}else if((form.get("projectname")!=null&&form.get("projectname").length()!=0)
						&&(form.get("certno")!=null&&form.get("certno").length()!=0)
						&&(form.get("ficationdate")==null||form.get("ficationdate").length()==0)){
					sb.append(
							"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id from dc_dc.jsgcjgghyshgz104358 b\n" +
							"      where 1=1 and b.xmmc= ? and b.fzbh= ? \n" + 
							"      union all\n" + 
							"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype ,'' as opt ,c.id as id from dc_dc.jsgcghxkz121798 c\n" + 
							"       where 1=1 and c.PROJ_NAME= ? and c.LICENSE_NO= ?");
					list.add(form.get("projectname").trim());
					list.add(form.get("certno").trim());
					list.add(form.get("projectname").trim());
					list.add(form.get("certno").trim());
				}else if ((form.get("projectname")!=null&&form.get("projectname").length()!=0)
						&&(form.get("certno")==null||form.get("certno").length()==0)
						&&(form.get("ficationdate")!=null&&form.get("ficationdate").length()!=0)){
					sb.append(
							"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id  from dc_dc.jsgcjgghyshgz104358 b\n" +
							"      where 1=1 and b.xmmc= ?  and to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd')= ?\n" + 
							"      union all\n" + 
							"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype ,'' as opt ,c.id as id from dc_dc.jsgcghxkz121798 c\n" + 
							"       where 1=1 and c.PROJ_NAME= ?  and to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') = ?");
					list.add(form.get("projectname").trim());
					list.add(form.get("ficationdate").trim());
					list.add(form.get("projectname").trim());
					list.add(form.get("ficationdate").trim());
				}else if((form.get("projectname")!=null&&form.get("projectname").length()!=0)
						&&(form.get("certno")==null||form.get("certno").length()==0)
						&&(form.get("ficationdate")==null||form.get("ficationdate").length()==0)){
					sb.append(
							"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype,'' as opt ,b.id as id  from dc_dc.jsgcjgghyshgz104358 b\n" +
							"      where 1=1 and b.xmmc= ? \n" + 
							"      union all\n" + 
							"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype ,'' as opt ,c.id as id from dc_dc.jsgcghxkz121798 c\n" + 
							"       where 1=1 and c.PROJ_NAME= ? ");
					list.add(form.get("projectname").trim());
					list.add(form.get("projectname").trim());
				}else if((form.get("projectname")==null||form.get("projectname").length()==0)
						&&(form.get("certno")!=null&&form.get("certno").length()!=0)
						&&(form.get("ficationdate")!=null&&form.get("ficationdate").length()!=0)){
					sb.append(
							"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id from dc_dc.jsgcjgghyshgz104358 b\n" +
							"      where 1=1  and b.fzbh= ? and to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd')= ?\n" + 
							"      union all\n" + 
							"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype ,'' as opt ,c.id as id from dc_dc.jsgcghxkz121798 c\n" + 
							"       where 1=1  and c.LICENSE_NO= ? and to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') = ?");
					list.add(form.get("certno").trim());
					list.add(form.get("ficationdate").trim());
					list.add(form.get("certno").trim());
					list.add(form.get("ficationdate").trim());
				}else if((form.get("projectname")==null||form.get("projectname").length()==0)
						&&(form.get("certno")!=null&&form.get("certno").length()!=0)
						&&(form.get("ficationdate")==null||form.get("ficationdate").length()==0)){
					sb.append(
							"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id from dc_dc.jsgcjgghyshgz104358 b\n" +
							"      where 1=1 and b.fzbh= ? \n" + 
							"      union all\n" + 
							"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype ,'' as opt ,c.id as id from dc_dc.jsgcghxkz121798 c\n" + 
							"       where 1=1  and c.LICENSE_NO= ? ");
					list.add(form.get("certno").trim());
					list.add(form.get("certno").trim());
				}else if((form.get("projectname")==null||form.get("projectname").length()==0)
						&&(form.get("certno")==null||form.get("certno").length()==0)
						&&(form.get("ficationdate")!=null&&form.get("ficationdate").length()!=0)){
					sb.append(
							"select b.xmmc as projectname,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.fzbh as certno ,'1' as certtype ,'' as opt ,b.id as id from dc_dc.jsgcjgghyshgz104358 b\n" +
							"      where 1=1 and to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd')= ?\n" + 
							"      union all\n" + 
							"      select c.PROJ_NAME as projectname,to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as ficationdate ,c.LICENSE_NO as certno ,'2' as certtype,'' as opt ,c.id as id  from dc_dc.jsgcghxkz121798 c\n" + 
							"       where 1=1  and to_char(to_date(c.LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') = ?");
					list.add(form.get("ficationdate").trim());
					list.add(form.get("ficationdate").trim());
				}else {
					sb.append("select null from dual");
				}
			}
			return dao.pageQueryForList(sb.toString(), list);
		}else {
			return null;
		}
	}
	

	@SuppressWarnings({ "null", "rawtypes", "unchecked" })
	public Map zzJgcert(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		String sql="select b.xmmc as projectname,b.fzbh as certno,to_char(to_date(b.fzrq,'yyyy/mm/dd'),'yyyy-mm-dd') as ficationdate,b.jsdd as constructionsite ,b.jsdw as constructionunit from dc_dc.jsgcjgghyshgz104358 b where b.id= ? ";
		List list=new ArrayList();
		list.add(id);
		List<Map> list2 = dao.queryForList(sql, list);
		if (list2!=null||list2.size()!=0) {
			return list2.get(0);
		}else{
			return null;
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "null" })
	public Map zzYscert(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		String sql=
				"select b.valid_flag,\n" +
						"       b.id,\n" + 
						"       b.license_no,\n" + 
						"       to_char(to_date(LICENSE_data,'dd-Mon -yy'),'yyyy-mm-dd') as license_data,\n" + 
						"       b.unit_name,\n" + 
						"       b.proj_name,\n" + 
						"       b.location,\n" + 
						"       b.parcel_no,\n" + 
						"       b.bldg_item_name,\n" + 
						"       b.bldg_func,\n" + 
						"       b.bldg_num,\n" + 
						"       b.floor_num,\n" + 
						"       b.stru_type,\n" + 
						"       b.pr_area,\n" + 
						"       b.no_pr_area,\n" + 
						"       b.area_rule,\n" + 
						"       b.rule_func,\n" + 
						"       b.area_add,\n" + 
						"       b.add_func,\n" + 
						"       b.area_reduce,\n" + 
						"       b.reduce_func,\n" + 
						"       b.area_award,\n" + 
						"       b.award_func,\n" + 
						"       b.pub_park_seats,\n" + 
						"       b.priv_park_seats,\n" + 
						"       b.remark,\n" + 
						"       b.site_name\n" + 
						"  from dc_dc.jsgcghxkz121798 b\n" + 
						" where b.id = ?";

		List list=new ArrayList();
		list.add(id);
		List<Map> list2 = dao.queryForList(sql, list);
		if (list2!=null||list2.size()!=0) {
			return list2.get(0);
		}else{
			return null;
		}
	}
}
