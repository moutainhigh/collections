package redis.tesst;

import org.springframework.beans.factory.annotation.Autowired;

import redis.RedisDao;

public class Te {
	@Autowired
	private RedisDao redisDao;

	private static int recordeTimeOut = 10;// 10秒
	
	public void sho(){
		
		String keys  ="";
		redisDao.add(keys, recordeTimeOut, 30);
	}
}
