package com.report.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.report.comon.TransferTime;
import com.report.dao.BaseDao;

@Component
@Transactional
public class TaiWaiGeTiService extends BaseDao {
	private static Logger logger = Logger.getLogger("taiwai"); // 获取logger实例

	
	/**
	 * (按季度来算)
	 * @param threeMonthsAgoTime
	 * @param endTime
	 * @return
	 */
	public List getList(String threeMonthsAgoTime, String endTime) {
		String sql =
				"select b.traname     \"个体工商户名称\",\n" +
						"       b.oploc       \"经营场所\",\n" + 
						"       b.compform_cn \"组成形式\",\n" + 
						"       b.fundam      \"注册资本\",\n" + 
						"       b.opscope     \"经营范围及方式\",\n" + 
						"       b.regno       \"工商注册号\",\n" + 
						"       b.uniscid     \"统一社会信用代码\",\n" + 
						"       b.estdate     \"工商注册日期\",\n" + 
						"       b.regstate_cn \"存续状态\",\n" + 
						"       b.empnum      \"从业人数\",\n" + 
						"       b.name        \"法定代表人(经营者)姓名\",\n" + 
						"       p.dom         \"法定(经营者)户籍住址(台湾)\",\n" + 
						"       p.tel         \"电话号码\",\n" + 
						"       p.cerno       \"备注(证件号码)\"\n" + 
						"  from tm_updata.e_pb_baseinfo b\n" + 
						"  left join tm_updata.e_pb_operator p\n" + 
						"    on b.pripid = p.pripid\n" + 
						" where b.enttype in ('9550')\n" + 
						"   and b.estdate >= to_date("+threeMonthsAgoTime+", 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"   and b.estdate <= to_date("+endTime+", 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"   and b.regstate = '1'";

		
		Query query = this.getSession().createSQLQuery(sql);

		logger.debug(sql);

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		return list;

	}
	
	
	public static void main(String[] args) throws ParseException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		TaiWaiGeTiService ss = ac.getBean(TaiWaiGeTiService.class);
		String timeStr = "2019-9-25";

		Map<String,String> map = TransferTime.getStartTimeAndEndTimeByStr(timeStr);
		
		System.out.println(map);
		String quNianTongQi = map.get("选择查询截止时间的去年同期时间");
		
		String begTime = map.get("查询截止时间三个月前的开始时间");
		String endTime = map.get("选择查询截止时间");
		
		
		
		ss.getList(begTime, endTime);
	}
	
}
