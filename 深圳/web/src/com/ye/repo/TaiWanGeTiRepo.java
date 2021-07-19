package com.ye.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ye.base.BaseDao;


@Repository
@Transactional
public class TaiWanGeTiRepo extends BaseDao {
	private static Logger logger = Logger.getLogger("taiwai"); // 获取logger实例

	
	/**
	 * (按季度来算)
	 * @param threeMonthsAgoTime
	 * @param endTime
	 * @return
	 */
	public List getList(String month) {
		String timeStr = month + "-25 23:59:59";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date  date = null;
		try {
			date = format.parse(month+"-26 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}  
	   
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, -3);  //设置为前3月
		
		Date dBefore = calendar.getTime();
		String defaultStartDate = format.format(dBefore);
		
		String thisYearBegTime = defaultStartDate;
		
		String thisYearEndTime = timeStr;
		
		
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
						"   and b.estdate >= to_date('"+ thisYearBegTime+"', 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"   and b.estdate <= to_date('"+thisYearEndTime+"', 'yyyy-mm-dd hh24:mi:ss')\n" + 
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
		TaiWanGeTiRepo ss = ac.getBean(TaiWanGeTiRepo.class);
		String timeStr = "2019-06";

		
		ss.getList(timeStr);
	}
	
}
