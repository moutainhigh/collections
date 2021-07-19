package com.ly.batch.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//https://www.cnblogs.com/khldragon/p/4096748.html

@Component
public class BatchUtil {
	private static Logger logger = Logger.getLogger(BatchUtil.class);
	@Autowired
	private SqlSessionFactory  sqlSessionFactory;
	
	public <T> void batchCommit(String mybatisSQLId, int commitCountEveryTime, List<T> list) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
			int commitCount = (int) Math.ceil(list.size() / (double) commitCountEveryTime);
			List<T> tempList = new ArrayList<T>(commitCountEveryTime);
			int start, stop;
			Long startTime = System.currentTimeMillis();
			for (int i = 0; i < commitCount; i++) {
				tempList.clear();
				start = i * commitCountEveryTime;
				stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, list.size() - 1);
				for (int j = start; j <= stop; j++) {
					tempList.add(list.get(j));
				}
				session.insert(mybatisSQLId, tempList);
				session.commit();
				session.clearCache();
			}
			Long endTime = System.currentTimeMillis();
			logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
		} catch (Exception e) {
			logger.error("batchCommit error!", e);
			e.printStackTrace();
			session.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
