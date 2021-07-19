package com.report.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.report.bean.FileDown;
import com.report.dao.RedisDao;
import com.report.dao.impl.FileDaoImpl;
import com.report.service.Data01Service;
import com.report.service.Data02Service;
import com.report.service.Data03Service;
import com.report.service.Data04Service;
import com.report.service.Data05Service;
import com.report.service.TaiWaiGeTiService;
import com.report.util.UUIDUtils;

@Component
public class ReportCacheBusTask {

	private static Logger logger = Logger.getLogger(ReportCacheBusTask.class); // 获取logger实例

	@Autowired
	private Data01Service data01Service;
	@Autowired
	private Data02Service data02Service;
	@Autowired
	private Data03Service data03Service;
	@Autowired
	private Data04Service data04Service;
	@Autowired
	private Data05Service data05Service;

	@Autowired
	private TaiWaiGeTiService taiWaiGeTi;
	
	
	@Autowired
	private RedisDao redisDao;

	@Autowired
	private FileDaoImpl fileDaoImpl;

	private int mytimeout = 3 * 60 * 60 * 24; // 3天过期

	// http://www.cnblogs.com/kxxiang/p/4297535.html
	// @Scheduled(cron="0/5 * * * * ? ") //每5秒执行一次
	@SuppressWarnings("rawtypes")
	// @Scheduled(cron="0 0 3 * * ?") //每天3点执行一次
	// @Scheduled(cron="0/45 * * * * ? ") //每5秒执行一次
	@Scheduled(cron = "0 25 17 * * ?") // 每天18点05执行一次
	public void doBusForRedis() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fileTableUUID = UUIDUtils.getUUID32();

        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String startTime = sdf.format(date);
		
		FileDown file = new FileDown();
		file.setCreateTime(format0.format(date));
		file.setDateQueryTime(sdf.format(date));
		file.setTypes(0);
		file.setDataStatus(0);
		file.setFileId(fileTableUUID);
		fileDaoImpl.save(file);

		logger.debug("定时采集数据开始========> ");
		//long start = System.currentTimeMillis(); // 获取开始时间

		new Thread() {

			public void run() {
				
				try {
					logger.debug("01.深圳各区优势产业分布");
					List list01 = data01Service.getList(startTime);
					List list01_02 = data01Service.getList2(startTime);
					
					
					redisDao.add("list01" + startTime, mytimeout, list01);
					redisDao.add("list01_02" + startTime, mytimeout, list01_02);
					logger.debug("01.深圳各区优势产业分布========> 采集数据完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		}.start();

	

		
		new Thread() {
			public void run() {
				try {
					logger.debug("02.深圳本期新登记企业前十名监管所");
					List list02SumJidu = data02Service.getSumJiDu(startTime);
					List list02SumAll = data02Service.getSumAll(startTime);

					List list02NeiZiJiDu = data02Service.getNeiZiJiDu(startTime);
					List list02NeiZiAll = data02Service.getNeiZiAll(startTime);

					List list02SiYinJiDu = data02Service.getSiYinJiDu(startTime);
					List list02SiYinAll = data02Service.getSiYinAll(startTime);

					List list02WaiZiJiDu = data02Service.getWaiZiJiDu(startTime);
					List list02WaiZiAll = data02Service.getWaiZiAll(startTime);
					

					redisDao.add("list02SumJidu" + startTime, mytimeout, list02SumJidu);
					redisDao.add("list02SumAll" + startTime, mytimeout, list02SumAll);

					redisDao.add("list02NeiZiJiDu" + startTime, mytimeout, list02NeiZiJiDu);
					redisDao.add("list02NeiZiAll" + startTime, mytimeout, list02NeiZiAll);

					redisDao.add("list02SiYinJiDu" + startTime, mytimeout, list02SiYinJiDu);
					redisDao.add("list02SiYinAll" + startTime, mytimeout, list02SiYinAll);

					redisDao.add("list02WaiZiJiDu" + startTime, mytimeout, list02WaiZiJiDu);
					redisDao.add("list02WaiZiAll" + startTime, mytimeout, list02WaiZiAll);
					file.setDataStatus(1);
					
					fileDaoImpl.updateSatus(file);
					
					logger.debug("02.深圳本期新登记企业前十名监管所========>采集数据完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		}.start();
		
		
		
		new Thread() {
			public void run() {
				try {
					logger.debug("03.各辖区局当期新设立个体前三名的监管所");
					List list03BenQi = data03Service.list2_BenQi(startTime);
					List list03QuNianBenQi = data03Service.list2_QuNianBenQi(startTime);
					List list03JianNianLeiJi = data03Service.list1_JianNianLeiJi(startTime);
					List list03QuNianLeiJi = data03Service.list1_QuNianLeiJi(startTime);
					redisDao.add("list03BenQi" + startTime, mytimeout, list03BenQi);
					redisDao.add("list03QuNianBenQi" + startTime, mytimeout, list03QuNianBenQi);
					redisDao.add("list03JianNianLeiJi" + startTime, mytimeout, list03JianNianLeiJi);
					redisDao.add("list03QuNianLeiJi" + startTime, mytimeout, list03QuNianLeiJi);
					
					logger.debug("03.各辖区局当期新设立个体前三名的监管所========>采集数据完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
		
	
		
		
		
		new Thread() {

			public void run() {
				try {
					logger.debug("04.各辖区局当期新设立企业前三名的监管所");
					List list04BenQi = data04Service.list2BenQi(startTime);
					List list04QuNianBenQi = data04Service.list2_QuNianBenQi(startTime);
					List list04JinNianLeiJi = data04Service.list1_JinNianLeiJi(startTime);
					List list04QuNianLeiJi = data04Service.list1_QuNianLeiJi(startTime);
					redisDao.add("list04BenQi" + startTime, mytimeout, list04BenQi);
					redisDao.add("list04QuNianBenQi" + startTime, mytimeout, list04QuNianBenQi);
					redisDao.add("list04JinNianLeiJi" + startTime, mytimeout, list04JinNianLeiJi);
					redisDao.add("list04QuNianLeiJi" + startTime, mytimeout, list04QuNianLeiJi);
					
					logger.debug("04.各辖区局当期新设立企业前三名的监管所========> 采集数据完成");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		
		
		
		
		
		new Thread() {

			public void run() {
				try {
					logger.debug("05.深圳本期新登记个体前十名监管所");
					List list05 = data05Service.getList(startTime);
					List list05LeiJi = data05Service.getList2(startTime);
					redisDao.add("list05" + startTime, mytimeout, list05);
					redisDao.add("list05LeiJi" + startTime, mytimeout, list05LeiJi);
					
					logger.debug("05.深圳本期新登记个体前十名监管所========>采集完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();

		
		new Thread() {

			public void run(){
				try {
					logger.debug("台弯个体户");
					List listTaiWai = taiWaiGeTi.getList(startTime);
					redisDao.add("listTaiWai"+startTime, mytimeout, listTaiWai);
					
					logger.debug("台弯个体户========>采集完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
	

	

	

	

	
		

	

		//long end = System.currentTimeMillis(); // 获取结束时间
		//System.out.println("程序运行时间： " + (end - start) + "ms , 共 【  " + (end - start) / 1000 / 60 + " 】分钟");
		//logger.debug("定时采集数据结束========> ");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//手动的
	public void doBusForRedisByHand(String handTime) throws ParseException {
	    SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		String fileTableUUID = UUIDUtils.getUUID32();
		
		String startTime = handTime;
		
		FileDown file = new FileDown();
		file.setCreateTime(format0.format(date));
		
		file.setDateQueryTime(handTime);
		file.setTypes(1);
		file.setFileId(fileTableUUID);
		file.setDataStatus(0);
		
		fileDaoImpl.save(file);

		logger.debug("定时采集数据开始========> ");
		//long start = System.currentTimeMillis(); // 获取开始时间

		new Thread() {

			public void run() {
				
				try {
					logger.debug("01.深圳各区优势产业分布");
					List list01 = data01Service.getList(startTime);
					List list01_02 = data01Service.getList2(startTime);
					
					
					redisDao.add("list01" + startTime, mytimeout, list01);
					redisDao.add("list01_02" + startTime, mytimeout, list01_02);
					logger.debug("01.深圳各区优势产业分布========> 采集数据完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		}.start();

	

		
		
		
		
		
		new Thread() {

			public void run() {
				try {
					logger.debug("02.深圳本期新登记企业前十名监管所");
					List list02SumJidu = data02Service.getSumJiDu(startTime);
					List list02SumAll = data02Service.getSumAll(startTime);

					List list02NeiZiJiDu = data02Service.getNeiZiJiDu(startTime);
					List list02NeiZiAll = data02Service.getNeiZiAll(startTime);

					List list02SiYinJiDu = data02Service.getSiYinJiDu(startTime);
					List list02SiYinAll = data02Service.getSiYinAll(startTime);

					List list02WaiZiJiDu = data02Service.getWaiZiJiDu(startTime);
					List list02WaiZiAll = data02Service.getWaiZiAll(startTime);
					
					
					
					

					redisDao.add("list02SumJidu" + startTime, mytimeout, list02SumJidu);
					redisDao.add("list02SumAll" + startTime, mytimeout, list02SumAll);

					redisDao.add("list02NeiZiJiDu" + startTime, mytimeout, list02NeiZiJiDu);
					redisDao.add("list02NeiZiAll" + startTime, mytimeout, list02NeiZiAll);

					redisDao.add("list02SiYinJiDu" + startTime, mytimeout, list02SiYinJiDu);
					redisDao.add("list02SiYinAll" + startTime, mytimeout, list02SiYinAll);

					redisDao.add("list02WaiZiJiDu" + startTime, mytimeout, list02WaiZiJiDu);
					redisDao.add("list02WaiZiAll" + startTime, mytimeout, list02WaiZiAll);
					file.setDataStatus(1);
					
					fileDaoImpl.updateSatus(file);
					logger.debug("02.深圳本期新登记企业前十名监管所========>采集数据完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

		}.start();
		
		
		
		
		
		
		
		
		
		new Thread() {

			public void run() {
				try {
					logger.debug("03.各辖区局当期新设立个体前三名的监管所");
					List list03BenQi = data03Service.list2_BenQi(startTime);
					List list03QuNianBenQi = data03Service.list2_QuNianBenQi(startTime);
					List list03JianNianLeiJi = data03Service.list1_JianNianLeiJi(startTime);
					List list03QuNianLeiJi = data03Service.list1_QuNianLeiJi(startTime);
					redisDao.add("list03BenQi" + startTime, mytimeout, list03BenQi);
					redisDao.add("list03QuNianBenQi" + startTime, mytimeout, list03QuNianBenQi);
					redisDao.add("list03JianNianLeiJi" + startTime, mytimeout, list03JianNianLeiJi);
					redisDao.add("list03QuNianLeiJi" + startTime, mytimeout, list03QuNianLeiJi);
					
					logger.debug("03.各辖区局当期新设立个体前三名的监管所========>采集数据完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
		
	
		
		
		
		new Thread() {

			public void run() {
				try {
					logger.debug("04.各辖区局当期新设立企业前三名的监管所");
					List list04BenQi = data04Service.list2BenQi(startTime);
					List list04QuNianBenQi = data04Service.list2_QuNianBenQi(startTime);
					List list04JinNianLeiJi = data04Service.list1_JinNianLeiJi(startTime);
					List list04QuNianLeiJi = data04Service.list1_QuNianLeiJi(startTime);
					redisDao.add("list04BenQi" + startTime, mytimeout, list04BenQi);
					redisDao.add("list04QuNianBenQi" + startTime, mytimeout, list04QuNianBenQi);
					redisDao.add("list04JinNianLeiJi" + startTime, mytimeout, list04JinNianLeiJi);
					redisDao.add("list04QuNianLeiJi" + startTime, mytimeout, list04QuNianLeiJi);
					
					logger.debug("04.各辖区局当期新设立企业前三名的监管所========> 采集数据完成");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}.start();
		
		
		
		
		
		new Thread() {

			public void run() {
				try {
					logger.debug("05.深圳本期新登记个体前十名监管所");
					List list05 = data05Service.getList(startTime);
					List list05LeiJi = data05Service.getList2(startTime);
					redisDao.add("list05" + startTime, mytimeout, list05);
					redisDao.add("list05LeiJi" + startTime, mytimeout, list05LeiJi);
					
					logger.debug("05.深圳本期新登记个体前十名监管所========>采集完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();

		
		new Thread() {

			public void run(){
				try {
					logger.debug("台弯个体户");
					List listTaiWai = taiWaiGeTi.getList(startTime);
					redisDao.add("listTaiWai"+startTime, mytimeout, listTaiWai);
					
					logger.debug("台弯个体户========>采集完成 ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
	

	
	}	
	
	
	
	
	
	
	
	
	
	
	@Scheduled(cron = "0 0 1 * * ?") // 每天1点执行一次
	public void updateFail() {

		List<FileDown> list = fileDaoImpl.getFailList();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String startTime = list.get(i).getDateQueryTime();
				FileDown file = list.get(i);

				logger.debug("定时采集失败的数据开始========> ");
				// long start = System.currentTimeMillis(); // 获取开始时间

				new Thread() {

					public void run() {

						try {
							logger.debug("01.深圳各区优势产业分布");
							List list01 = data01Service.getList(startTime);
							List list01_02 = data01Service.getList2(startTime);

							redisDao.add("list01" + startTime, mytimeout, list01);
							redisDao.add("list01_02" + startTime, mytimeout, list01_02);
							logger.debug("01.深圳各区优势产业分布========> 采集数据完成");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}.start();

				new Thread() {

					public void run() {
						try {
							logger.debug("02.深圳本期新登记企业前十名监管所");
							List list02SumJidu = data02Service.getSumJiDu(startTime);
							List list02SumAll = data02Service.getSumAll(startTime);

							List list02NeiZiJiDu = data02Service.getNeiZiJiDu(startTime);
							List list02NeiZiAll = data02Service.getNeiZiAll(startTime);

							List list02SiYinJiDu = data02Service.getSiYinJiDu(startTime);
							List list02SiYinAll = data02Service.getSiYinAll(startTime);

							List list02WaiZiJiDu = data02Service.getWaiZiJiDu(startTime);
							List list02WaiZiAll = data02Service.getWaiZiAll(startTime);

							redisDao.add("list02SumJidu" + startTime, mytimeout, list02SumJidu);
							redisDao.add("list02SumAll" + startTime, mytimeout, list02SumAll);

							redisDao.add("list02NeiZiJiDu" + startTime, mytimeout, list02NeiZiJiDu);
							redisDao.add("list02NeiZiAll" + startTime, mytimeout, list02NeiZiAll);

							redisDao.add("list02SiYinJiDu" + startTime, mytimeout, list02SiYinJiDu);
							redisDao.add("list02SiYinAll" + startTime, mytimeout, list02SiYinAll);

							redisDao.add("list02WaiZiJiDu" + startTime, mytimeout, list02WaiZiJiDu);
							redisDao.add("list02WaiZiAll" + startTime, mytimeout, list02WaiZiAll);
							file.setDataStatus(1);

							fileDaoImpl.updateSatus(file);
							logger.debug("02.深圳本期新登记企业前十名监管所========>采集数据完成 ");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}.start();

				new Thread() {

					public void run() {
						try {
							logger.debug("03.各辖区局当期新设立个体前三名的监管所");
							List list03BenQi = data03Service.list2_BenQi(startTime);
							List list03QuNianBenQi = data03Service.list2_QuNianBenQi(startTime);
							List list03JianNianLeiJi = data03Service.list1_JianNianLeiJi(startTime);
							List list03QuNianLeiJi = data03Service.list1_QuNianLeiJi(startTime);
							redisDao.add("list03BenQi" + startTime, mytimeout, list03BenQi);
							redisDao.add("list03QuNianBenQi" + startTime, mytimeout, list03QuNianBenQi);
							redisDao.add("list03JianNianLeiJi" + startTime, mytimeout, list03JianNianLeiJi);
							redisDao.add("list03QuNianLeiJi" + startTime, mytimeout, list03QuNianLeiJi);

							logger.debug("03.各辖区局当期新设立个体前三名的监管所========>采集数据完成 ");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}.start();

				new Thread() {

					public void run() {
						try {
							logger.debug("04.各辖区局当期新设立企业前三名的监管所");
							List list04BenQi = data04Service.list2BenQi(startTime);
							List list04QuNianBenQi = data04Service.list2_QuNianBenQi(startTime);
							List list04JinNianLeiJi = data04Service.list1_JinNianLeiJi(startTime);
							List list04QuNianLeiJi = data04Service.list1_QuNianLeiJi(startTime);
							redisDao.add("list04BenQi" + startTime, mytimeout, list04BenQi);
							redisDao.add("list04QuNianBenQi" + startTime, mytimeout, list04QuNianBenQi);
							redisDao.add("list04JinNianLeiJi" + startTime, mytimeout, list04JinNianLeiJi);
							redisDao.add("list04QuNianLeiJi" + startTime, mytimeout, list04QuNianLeiJi);

							logger.debug("04.各辖区局当期新设立企业前三名的监管所========> 采集数据完成");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}.start();

				new Thread() {

					public void run() {
						try {
							logger.debug("05.深圳本期新登记个体前十名监管所");
							List list05 = data05Service.getList(startTime);
							List list05LeiJi = data05Service.getList2(startTime);
							redisDao.add("list05" + startTime, mytimeout, list05);
							redisDao.add("list05LeiJi" + startTime, mytimeout, list05LeiJi);

							logger.debug("05.深圳本期新登记个体前十名监管所========>采集完成 ");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}.start();

				new Thread() {

					public void run() {
						try {
							logger.debug("台弯个体户");
							List listTaiWai = taiWaiGeTi.getList(startTime);
							redisDao.add("listTaiWai" + startTime, mytimeout, listTaiWai);

							logger.debug("台弯个体户========>采集完成 ");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}.start();

			}

		}
	}
	

}
