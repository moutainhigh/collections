package com.gwssi.taiwai.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/*select b.traname "个体工商户名称",b.oploc "经营场所",b.compform_cn "组成形式",b.fundam "注册资本",b.opscope "经营范围及方式",b.regno "工商注册号",b.uniscid "统一社会信用代码",b.estdate "工商注册日期",b.regstate_cn "存续状态",b.empnum "从业人数",b.name "法定代表人(经营者)姓名",p.dom "法定(经营者)户籍住址(台湾)",p.tel "电话号码",p.cerno "备注(证件号码)" 
from tm_updata.e_pb_baseinfo b 
     left join tm_updata.e_pb_operator p on b.pripid=p.pripid
where b.enttype in ('9550')
and b.estdate>=to_date('2019-06-01','yyyy-mm-dd') and b.estdate<=to_date('2019-7-31 23:59:59','yyyy-mm-dd hh24:mi:ss')
and b.regstate='1'*/
@Service
public class TaiWaiGtService extends BaseService{

	public List<Map> getList(String begTime,String endTime) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select b.traname, b.oploc,b.compform_cn ,b.fundam ,b.opscope ,b.regno ,b.uniscid ,b.estdate ,b.regstate_cn,b.empnum ,b.name ,p.dom ,p.tel ,p.cerno   from tm_updata.e_pb_baseinfo b ");
		sql.append("  left join tm_updata.e_pb_operator p on b.pripid=p.pripid ");
		sql.append(" where b.enttype in ('9550') ");
		sql.append(" and to_char( b.estdate,'yyyy-mm-dd') >= '"+begTime+"' and to_char( b.estdate,'yyyy-mm-dd')<= '"+endTime+"'");
		sql.append(" and b.regstate='1' ");
		sql.append(" order by  b.estdate asc ");
		List list = dao.queryForList(sql.toString(),null);
		    
		return list;
	}

	public String getListCount(String begTime,String endTime) throws OptimusException {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) as count   from tm_updata.e_pb_baseinfo b ");
		sql.append("  left join tm_updata.e_pb_operator p on b.pripid=p.pripid ");
		sql.append(" where b.enttype in ('9550') ");
		sql.append(" and to_char( b.estdate,'yyyy-mm-dd') >= '"+begTime+"' and to_char( b.estdate,'yyyy-mm-dd')<= '"+endTime+"'");
		sql.append(" and b.regstate='1' ");
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		List list=new ArrayList();
		
		return dao.queryForList(sql.toString(), list).get(0).get("count").toString();
	}

}
