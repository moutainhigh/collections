package com.gwssi.tasker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gwssi.dao.DCUploadRecordDao;
import com.gwssi.dao.EBaseinfoDao;
import com.gwssi.dao.EPb_baseinfoDao;
import com.gwssi.pojo.DC_upload_record;
import com.ye.common.util.UUIDUtils;

@Component
public class JobTasker {
	private static Logger logger = Logger.getLogger(JobTasker.class);

	@Autowired
	private DCUploadRecordDao recorddao;

	@Autowired
	private EBaseinfoDao ebaseDao;

	@Autowired
	private EPb_baseinfoDao epDao;

	//@Scheduled(cron = "0 36 14 20 * ?")
	@Scheduled(cron = "0 15 10 25 * ?")
	public void save01() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println("当前时间是：" + dateFormat.format(date));
		String endtime = dateFormat.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(2, calendar.get(2) - 1);
		date = calendar.getTime();

		System.out.println("上一个月的时间： " + dateFormat.format(date));

		String begtime = dateFormat.format(date);

		System.out.println(begtime);
		System.out.println(endtime);
		
		
		final Map paramMap = new HashMap();
		paramMap.put("begtime", begtime);
		paramMap.put("endtime", endtime + " 23:59:59");

		System.out.println("=============定时任务=====");
		// DC_upload_record record = null;
		// Integer i = null;

		new Thread() {

			public void run() {

				try {

					// data01

					DC_upload_record record = null;
					Integer i = null;
					System.out.println("=============【data01 start】===========\t");
					logger.debug("=============【data01 start】===========\t");
					i = ebaseDao.data01(null);
					record = new DC_upload_record();
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("1");
					recorddao.save(record);
					System.out.println("=============【data01 end】===========\t");
					logger.debug("=============【data01 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		new Thread() {
			public void run() {
				try {
					// 个体管辖区域为空数量 2
					System.out.println("=============【data02 start】===========\t");
					logger.debug("=============【data02 start】===========\t");
					DC_upload_record record = null;
					Integer i = null;
					record = new DC_upload_record();
					i = epDao.data02(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("2");
					recorddao.save(record);
					System.out.println("=============【data02 end】===========\t");
					logger.debug("=============【data02 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		// 不存在的企业类型数据3 ---列表 不记

		new Thread() {
			public void run() {
				try {
					// 04 企业行业类型为空数量
					System.out.println("=============【data04 start】===========\t");
					logger.debug("=============【data04 start】===========\t");
					DC_upload_record record = null;
					Integer i = null;
					record = new DC_upload_record();
					i = ebaseDao.data04(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("4");
					recorddao.save(record);
					System.out.println("=============【data04 end】===========\t");
					logger.debug("=============【data04 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		new Thread() {
			public void run() {
				try {
					// 05个体行业类型为空数量
					System.out.println("=============【data05 start】===========\t");
					logger.debug("=============【data05 start】===========\t");
					DC_upload_record record = null;
					Integer i = null;
					record = new DC_upload_record();
					i = epDao.data05(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("5");
					recorddao.save(record);
					System.out.println("=============【data05 end】===========\t");
					logger.debug("=============【data05 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		// 06企业注册资本排序 ---列表 不记

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 07企业中状态为吊销的户数
					System.out.println("=============【data07 start】===========\t");
					logger.debug("=============【data07 start】===========\t");

					record = new DC_upload_record();
					i = ebaseDao.data07(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("7");
					recorddao.save(record);
					System.out.println("=============【data07 end】===========\t");
					logger.debug("=============【data07 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					//// 08 企业中状态为吊销并且吊销日期不为空的户数
					System.out.println("=============【data08 start】===========\t");
					logger.debug("=============【data08 start】===========\t");

					record = new DC_upload_record();
					i = ebaseDao.data08(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("8");
					recorddao.save(record);
					System.out.println("=============【data08 end】===========\t");
					logger.debug("=============【data08 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
	

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 09 个体中状态为吊销的户数
					System.out.println("=============【data09 start】===========\t");
					logger.debug("=============【data09 start】===========\t");

					record = new DC_upload_record();
					i = epDao.data09(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("9");
					recorddao.save(record);
					System.out.println("=============【data09 end】===========\t");
					logger.debug("=============【data09 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 10个体中状态为吊销并且吊销日期不为空的户数
					System.out.println("=============【data10 start】===========\t");
					logger.debug("=============【data10 start】===========\t");

					record = new DC_upload_record();
					i = epDao.data10(null);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("10");
					recorddao.save(record);
					System.out.println("=============【data10 end】===========\t");
					logger.debug("=============【data10 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 11 企业期末实有户数
					System.out.println("=============【data11 start】===========\t");
					logger.debug("=============【data11 start】===========\t");

					record = new DC_upload_record();
					paramMap.put("date1", "date1");
					i = ebaseDao.data11(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("11");
					recorddao.save(record);
					System.out.println("=============【data11 end】===========\t");
					logger.debug("=============【data11 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 12企业本期登记户数
					System.out.println("=============【data12 start】===========\t");
					logger.debug("=============【data12 start】===========\t");

					record = new DC_upload_record();
					paramMap.put("date1", "");
					paramMap.put("date2", "date2");
					paramMap.put("date3", "date3");
					i = ebaseDao.data11(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("12");
					recorddao.save(record);
					System.out.println("=============【data12 end】===========\t");
					logger.debug("=============【data12 end】===========\t");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

		
		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 13企业本期吊销户数
					System.out.println("=============【data13 start】===========\t");
					logger.debug("=============【data13 start】===========\t");
					record = new DC_upload_record();
					i = ebaseDao.data13(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("13");
					recorddao.save(record);
					System.out.println("=============【data13 end】===========\t");
					logger.debug("=============【data13 end】===========\t");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		
		
		
		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 14企业本期注销户数
					System.out.println("=============【data14 start】===========\t");
					logger.debug("=============【data14 start】===========\t");

					record = new DC_upload_record();
					i = ebaseDao.data14(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("14");
					recorddao.save(record);
					System.out.println("=============【data14 end】===========\t");
					logger.debug("=============【data14 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 15 个体期末实有户数
					System.out.println("=============【data15 start】===========\t");
					logger.debug("=============【data15 start】===========\t");

					record = new DC_upload_record();
					i = epDao.data15(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("15");
					recorddao.save(record);
					System.out.println("=============【data15 end】===========\t");
					logger.debug("=============【data15 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 16 个体本期登记户数
					System.out.println("=============【data16 start】===========\t");
					logger.debug("=============【data16 start】===========\t");

					record = new DC_upload_record();
					i = epDao.data16(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("16");
					recorddao.save(record);
					System.out.println("=============【data16 end】===========\t");
					logger.debug("=============【data16 end】===========\t");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		
		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 17 个体本期吊销户数
					System.out.println("=============【data17 start】===========\t");
					logger.debug("=============【data17 start】===========\t");

					record = new DC_upload_record();
					i = epDao.data17(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("17");
					recorddao.save(record);
					System.out.println("=============【data17 end】===========\t");
					logger.debug("=============【data17 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		

		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 18 个体本期注销户数
					System.out.println("=============【data18 start】===========\t");
					logger.debug("=============【data18 start】===========\t");

					record = new DC_upload_record();
					i = epDao.data18(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("18");
					recorddao.save(record);
					System.out.println("=============【data18 end】===========\t");
					logger.debug("=============【data18 end】===========\t");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		
		new Thread() {
			public void run() {
				try {
					DC_upload_record record = null;
					Integer i = null;
					// 19本期登记纯内资企业户数
					System.out.println("=============【data19 start】===========\t");
					logger.debug("=============【data19 start】===========\t");
					record = new DC_upload_record();
					i = ebaseDao.data19(paramMap);
					record.setCounts(i.toString());
					record.setId(UUIDUtils.getUUID32());
					record.setRemark("19");
					recorddao.save(record);
					System.out.println("=============【data19 end】===========\t");
					logger.debug("=============【data19 end】===========\t");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();

	}

	@Scheduled(cron = "0 10 14 ? 3 WED")
	public void deleRecordByTwoYear() {

	}

	// https://blog.csdn.net/wolfies/article/details/79441564
	// @Scheduled(cron = "0/10 * * * * ? ")
	public void test01() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		System.out.println("当前时间是：" + dateFormat.format(date));
		String endtime = dateFormat.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(2, calendar.get(2) - 1);
		date = calendar.getTime();
		System.out.println("上一个月的时间： " + dateFormat.format(date));

		String begtime = dateFormat.format(date);
		System.out.println("处理开始时间===》" + begtime);
		System.out.println("处理结束时间===》" + endtime);
	}
}