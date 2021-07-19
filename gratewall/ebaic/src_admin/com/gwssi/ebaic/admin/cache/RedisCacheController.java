package com.gwssi.ebaic.admin.cache;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.admin.cache.RedisCacheService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/admin/redis")
public class RedisCacheController extends BaseController {
	
	@Resource
	private RedisCacheService redisCacheService;
	
	/**
	 * 查询缓存redis列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/listByPrefix")
	public void queryRedisCache(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
        String prefix = ParamUtil.get("prefix");
        List<Map<String, Object>> redisList = redisCacheService.getListByPrefix(prefix);
        resp.addGrid("redisGrid", redisList);
	}
	
	/**
	 * 查询redis信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/selectRedisInfo")
	public void selectRedisInfo(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
		String redisInfo = ParamUtil.get("redisInfo");
		List<Map<String, String> > redisInfoList  = redisCacheService.getRedisInfo(redisInfo);
		 resp.addGrid("redisInfoGrid", redisInfoList);
	}
	
	
	
	/**
	 * 根据key清理单个redis缓存
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	 @RequestMapping("/delRedis")
	    public void delRedis(OptimusRequest req, OptimusResponse res) 
	            throws OptimusException {
		 	String key = ParamUtil.get("key");
	        redisCacheService.deleterRedis(key);
	        res.addAttr("del", "success");
	    }
	
	/**
	 * 清理批次缓存
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/delBatchRedis")
	public void delBatchRedis(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
		List<Map<String, String>> list = req.getGrid("redisGrid");
    	for(Map<String, String> map : list){
			String key = (String) map.get("key");
			redisCacheService.deleteBatchResdis(key);
		}
		resp.addAttr("del", "success");
	}

}
