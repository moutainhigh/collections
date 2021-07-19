package com.gwssi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwssi.dao.EntInfoDao;
import com.gwssi.dao.LogOffEntInfoDao;
import com.gwssi.dao.UserInfoDao;
import com.gwssi.pojo.EntInfo;
import com.gwssi.pojo.LogOff;
import com.gwssi.redis.RedisDao;
import com.gwssi.utils.SysResult;
import com.gwssi.utils.TmStringUtils;

@RestController
public class BankReqController {
	private static Logger logger = Logger.getLogger(BankReqController.class); // 获取logger实例

	private static int recordeTimeOut = 24 * 60 * 60;

	@Autowired
	private EntInfoDao entInfoDao;

	@Autowired
	private LogOffEntInfoDao logOffEntInfoDao;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private RedisDao redisDao;

	@SuppressWarnings("unchecked")
	@RequestMapping("getEntInfo")
	public Map getEntInfo(String key, String entset, String type, String stime, String etime, String page, String size)
			throws Exception {
		Map retMap = new HashMap();
		Map params = new HashMap();
		retMap.put("msg", "");
		retMap.put("totals", "");
		retMap.put("data", "");
		retMap.put("code", "0");
		List listEntInfo = null;
		int totals = 0;

		if (TmStringUtils.isEmpty(key)) {
			retMap.put("msg", "银行key不能为空");
		} else {
			params.put("key", key);
			Integer userIsExit = userInfoDao.queryUserInfo(params);
			if (userIsExit == null || userIsExit == 0) {
				retMap.put("msg", "您好，您使用的银行key不存在");
			} else {
				if (TmStringUtils.isEmpty(type)) {
					type = "1";
				}
				

				params.put("type", type);
				
				
				if(TmStringUtils.isNotEmpty(stime)&&TmStringUtils.isNotEmpty(etime)) {
					params.put("stime", stime);
					params.put("etime", etime);
					
					//start
					
					if (TmStringUtils.isEmpty(page)) { // 起始页为空
						// 起始值为空，则默认查询数据库中前100条数据
						params.put("pageBegin", "0");
						params.put("pageEnd", "100");
					} else {

						if (page.equals("0")) {
							retMap.put("code", "1");
							retMap.put("msg", "您好，第一页应该从1开始");
						}

						// 起始页不为空
						int parsePage = Integer.parseInt(page);

						if (parsePage != 0) {
							if (TmStringUtils.isEmpty(size)) {
								int begin = (parsePage - 1) * 100;
								int end = parsePage * 100;
								params.put("pageBegin", begin + "");
								params.put("pageEnd", end + "");
							} else { // page和size都不为空
								int parseSize = Integer.parseInt(size);
								int begin = (parsePage - 1) * parseSize;
								int end = parsePage * parseSize;
								params.put("pageBegin", begin + "");
								params.put("pageEnd", end + "");
							}
						} else { // page的值为0
							if (TmStringUtils.isEmpty(size)) { // page为0，但size为空，则默认返回100条查询数据
								params.put("pageBegin", "0");
								params.put("pageEnd", "100");
							} else { // page为0，size不为空
								params.put("pageBegin", parsePage + "");
								params.put("pageEnd", size);
							}
						}
					}

					String pageBeginRdis = (String) params.get("pageBegin");
					String pageEndRdis = (String) params.get("pageEnd");

					String stimeRdis = (String) params.get("stime");
					String etimeRdis = (String) params.get("etime");

					String redisKeys = "type_" + type + "_entInfo_" + entset + "_" + pageBeginRdis + "_" + pageEndRdis + "_"
							+ stimeRdis + "_" + etimeRdis;
					String redisKeysTotals = "totals_" + totals + "_type_" + type + "_entInfo_" + entset + "_" + pageBeginRdis
							+ "_" + pageEndRdis + "_" + stimeRdis + "_" + etimeRdis;

					logger.debug("存redis的key集合 " + redisKeys);
					logger.debug("存redis的key总数==》 " + redisKeysTotals);

					if (TmStringUtils.isNotEmpty(entset)) {
						params.put("entset", entset);
						listEntInfo = redisDao.getList(redisKeys, EntInfo.class);
						String totalsStr = redisDao.get(redisKeysTotals);

						if (totalsStr != null) {
							totals = Integer.valueOf(totalsStr);
						}

						if (listEntInfo == null) {
							ExecutorService pool = Executors.newFixedThreadPool(2);
							// 创建两个有返回值的任务
							Callable c1 = new EntTotal(entInfoDao, params);
							Callable c2 = new EntList(entInfoDao, params);
							// 执行任务并获取Future对象
							Future f1 = pool.submit(c1);
							Future f2 = pool.submit(c2);
							// 从Future对象上获取任务的返回值，并输出到控制台
							totals = (Integer) f1.get();
							listEntInfo = (List<EntInfo>) f2.get();
							// 关闭线程池
							pool.shutdown();
							// 关闭线程池
							pool.shutdown();

							// 存入redis
							redisDao.add(redisKeys, 5 * 60 * 60, listEntInfo);// 五个小时
							redisDao.add(redisKeysTotals, 5 * 60 * 60, totals);// 五个小时
						}
						if (listEntInfo != null && listEntInfo.size() > 0) {

							retMap.put("code", "1");
							retMap.put("msg", "请求成功");
							retMap.put("totals", totals);
							retMap.put("data", listEntInfo);

						}
					} else {
						listEntInfo = redisDao.getList(redisKeys, EntInfo.class);
						String totalsStr = redisDao.get(redisKeysTotals);

						if (totalsStr != null) {
							totals = Integer.valueOf(totalsStr);
						}

						if (listEntInfo == null) {
							ExecutorService pool = Executors.newFixedThreadPool(2);
							// 创建两个有返回值的任务
							Callable c1 = new EntTotal(entInfoDao, params);
							Callable c2 = new EntList(entInfoDao, params);
							// 执行任务并获取Future对象
							Future f1 = pool.submit(c1);
							Future f2 = pool.submit(c2);
							// 从Future对象上获取任务的返回值，并输出到控制台
							totals = (Integer) f1.get();
							listEntInfo = (List<EntInfo>) f2.get();
							// 关闭线程池
							pool.shutdown();
							// 关闭线程池
							pool.shutdown();

							// 存入redis
							redisDao.add(redisKeys, recordeTimeOut, listEntInfo);// 五个小时
							redisDao.add(redisKeysTotals, 5 * 60 * 60, totals);// 五个小时
						}
						if (listEntInfo != null && listEntInfo.size() > 0) {

							retMap.put("code", "1");
							retMap.put("msg", "请求成功");
							retMap.put("totals", totals);
							retMap.put("data", listEntInfo);
						}
					}
					
					
					//end
					
				}else {
					retMap.put("msg", "您好，需要传入的stime（开始时间）和etime（结束时间）不能为空");
				}

			}
		}

		

		return retMap;
	}

	@RequestMapping("getLogOffEntInfo")
	public Map getLogOffEntInfo(String key, String entset, String type, String stime, String etime, String page,
			String size) throws InterruptedException, ExecutionException {

		Map retMap = new HashMap();
		Map params = new HashMap();
		retMap.put("msg", "");
		retMap.put("code", "0");
		retMap.put("totals", "");
		retMap.put("data", "");
		List listEntOffInfo = null;
		int totals = 0;

		if (TmStringUtils.isEmpty(key)) {
			retMap.put("msg", "银行key不能为空");
		} else {
			params.put("key", key);
			Integer userIsExit = userInfoDao.queryUserInfo(params);
			if (userIsExit == null || userIsExit == 0) {
				retMap.put("msg", "您好，您使用的银行key不存在");
			} else {
				if (TmStringUtils.isEmpty(type)) {
					type = "1";
				}

				params.put("type", type);
				
				if(TmStringUtils.isNotEmpty(stime)&&TmStringUtils.isNotEmpty(etime)) {
					params.put("stime", stime);
					params.put("etime", etime);
					
					//start
					

					/*
					 * 当用户输入的PAGE为空时， SIZE的数据自动忽略，系统默认查询前100条数据； 当用户输入的PAGE不为空时，
					 * 若SIZE为空，则系统根据(PAGE-1)*100为起始，查询数据中的100条数据
					 * 若SIZE不为空，则系统根据(PAGE-1)*SIZE为起始，PAGE*SIZE为结束，查询数据。
					 */
					if (TmStringUtils.isEmpty(page)) { // 起始页为空
						// 起始值为空，则默认查询数据库中前100条数据
						params.put("pageBegin", "0");
						params.put("pageEnd", "100");
					} else {

						if (page.equals("0")) {
							retMap.put("msg", "您好，第一页应该从1开始");
						}

						// 起始页不为空
						int parsePage = Integer.parseInt(page);

						if (parsePage != 0) {
							if (TmStringUtils.isEmpty(size)) {
								int begin = (parsePage - 1) * 100;
								int end = parsePage * 100;
								params.put("pageBegin", begin + "");
								params.put("pageEnd", end + "");
							} else { // page和size都不为空
								int parseSize = Integer.parseInt(size);
								int begin = (parsePage - 1) * parseSize;
								int end = parsePage * parseSize;
								params.put("pageBegin", begin + "");
								params.put("pageEnd", end + "");
							}
						} else { // page的值为0
							if (TmStringUtils.isEmpty(size)) { // page为0，但size为空，则默认返回100条查询数据
								params.put("pageBegin", "0");
								params.put("pageEnd", "100");
							} else { // page为0，size不为空
								params.put("pageBegin", parsePage + "");
								params.put("pageEnd", size);
							}
						}
					}

					if (TmStringUtils.isNotEmpty(entset)) {
						params.put("entset", entset);
					}

					String pageBeginRdis = (String) params.get("pageBegin");
					String pageEndRdis = (String) params.get("pageEnd");

					String stimeRdis = (String) params.get("stime");
					String etimeRdis = (String) params.get("etime");
					String redisKeys = "type_" + type + "_entOffInfo_" + entset + "_" + pageBeginRdis + "_" + pageEndRdis + "_"
							+ stimeRdis + "_" + etimeRdis;
					String redisKeysTotals = "totals_" + totals + "_type_" + type + "_entOffInfo_" + entset + "_"
							+ pageBeginRdis + "_" + pageEndRdis + "_" + stimeRdis + "_" + etimeRdis;

					logger.debug("存redis的key集合 " + redisKeys);
					logger.debug("存redis的key总数==》 " + redisKeysTotals);

					if (TmStringUtils.isNotEmpty(entset)) {
						params.put("entset", entset);
						listEntOffInfo = redisDao.getList(redisKeys, LogOff.class);
						String totalsStr = redisDao.get(redisKeysTotals);

						if (totalsStr != null) {
							totals = Integer.valueOf(totalsStr);
						}

						if (listEntOffInfo == null) {

							ExecutorService pool = Executors.newFixedThreadPool(2);
							// 创建两个有返回值的任务
							Callable c1 = new EntLogOffTotal(logOffEntInfoDao, params);
							Callable c2 = new EntLogOffList(logOffEntInfoDao, params);
							// 执行任务并获取Future对象
							Future f1 = pool.submit(c1);
							Future f2 = pool.submit(c2);
							// 从Future对象上获取任务的返回值，并输出到控制台
							totals = (Integer) f1.get();
							listEntOffInfo = (List<LogOff>) f2.get();
							// 关闭线程池
							pool.shutdown();

							// 存入redis
							redisDao.add(redisKeys, recordeTimeOut, listEntOffInfo);// 五个小时
							redisDao.add(redisKeysTotals, recordeTimeOut, totals);// 五个小时
						}
						if (listEntOffInfo != null && listEntOffInfo.size() > 0) {
							retMap.put("code", "1");
							retMap.put("msg", "请求成功");
							retMap.put("totals", totals);
							retMap.put("data", listEntOffInfo);
						}
					} else {
						listEntOffInfo = redisDao.getList(redisKeys, LogOff.class);
						String totalsStr = redisDao.get(redisKeysTotals);

						if (totalsStr != null) {
							totals = Integer.valueOf(totalsStr);
						}
						if (listEntOffInfo == null) {
							ExecutorService pool = Executors.newFixedThreadPool(2);
							// 创建两个有返回值的任务
							Callable c1 = new EntLogOffTotal(logOffEntInfoDao, params);
							Callable c2 = new EntLogOffList(logOffEntInfoDao, params);
							// 执行任务并获取Future对象
							Future f1 = pool.submit(c1);
							Future f2 = pool.submit(c2);
							// 从Future对象上获取任务的返回值，并输出到控制台
							totals = (Integer) f1.get();
							listEntOffInfo = (List<LogOff>) f2.get();
							// 关闭线程池
							pool.shutdown();

							// 存入redis
							redisDao.add(redisKeys, recordeTimeOut, listEntOffInfo);// 五个小时
							redisDao.add(redisKeysTotals, recordeTimeOut, totals);// 五个小时
						}
						if (listEntOffInfo != null && listEntOffInfo.size() > 0) {
							retMap.put("code", "1");
							retMap.put("msg", "请求成功");
							retMap.put("totals", totals);
							retMap.put("data", listEntOffInfo);
						}

					}
					
					
					
					//end
					
					
				}else {
					retMap.put("msg", "您好，需要传入的stime（开始时间）和etime（结束时间）不能为空");
				}


			}
		}

		

		return retMap;
	}

}

class EntTotal implements Callable {

	private EntInfoDao ser;
	private Map params;

	public EntTotal(EntInfoDao ser, Map params) {
		super();
		this.ser = ser;
		this.params = params;
	}

	public int getTotal() {
		Integer total = ser.queryEntInfoTotals(params);
		if (total != null) {
			return total;
		} else {
			return 0;
		}
	}

	@Override
	public Object call() throws Exception {
		return getTotal();
	}
}

class EntList implements Callable {
	private EntInfoDao ser;
	private Map params;

	public EntList(EntInfoDao ser, Map params) {
		super();
		this.ser = ser;
		this.params = params;
	}

	public List getList() {
		List lists = ser.queryEntInfo(params);
		if (lists != null && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@Override
	public Object call() throws Exception {
		return getList();
	}
}

class EntLogOffTotal implements Callable {
	private LogOffEntInfoDao ser;
	private Map params;

	public EntLogOffTotal(LogOffEntInfoDao ser, Map params) {
		super();
		this.ser = ser;
		this.params = params;
	}

	public int getTotal() {
		Integer total = ser.queryLogOffEntInfoTotals(params);
		if (total != null) {
			return total;
		} else {
			return 0;
		}
	}

	@Override
	public Object call() throws Exception {
		return getTotal();
	}
}

class EntLogOffList implements Callable {
	private LogOffEntInfoDao ser;
	private Map params;

	public EntLogOffList(LogOffEntInfoDao ser, Map params) {
		super();
		this.ser = ser;
		this.params = params;
	}

	public List getList() {
		List lists = ser.queryLogOffEntInfo(params);
		if (lists != null && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	@Override
	public Object call() throws Exception {
		return this.getList();
	}

}
