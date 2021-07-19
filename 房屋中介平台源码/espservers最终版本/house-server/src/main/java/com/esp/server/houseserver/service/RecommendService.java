package com.esp.server.houseserver.service;


import com.esp.server.houseserver.domain.House;
import com.esp.server.houseserver.model.LimitOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 推荐业务实现
 */
@Service
public class RecommendService {
  
  private static final String HOT_HOUSE_KEY = "_hot_house";

  @Autowired
  private HouseService houseService;
  
  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 获取热度房产
   * @param size
   * @return
   */
  public List<House> getHotHouse(Integer size) {
    Set<String> idSet =  redisTemplate.opsForZSet().reverseRange(HOT_HOUSE_KEY, 0, -1);//根据热度从高到底排序
    List<Long> ids = idSet.stream().map(b -> Long.parseLong(b)).collect(Collectors.toList());
    House query = new House();
    query.setIds(ids);
    return houseService.queryAndSetImg(query, LimitOffset.build(size, 0));
  }

  /**
   * 增加热度
   * @param id
   */
  public void increaseHot(long id) {
    redisTemplate.opsForZSet().incrementScore(HOT_HOUSE_KEY, ""+id, 1.0D);
    redisTemplate.opsForZSet().removeRange(HOT_HOUSE_KEY, 0, -11);
  }

  /**
   * 获取最新的房源信息
   * @return
   */
  public List<House> getLastest() {
    House query = new House();
    query.setSort("create_time");
    return houseService.queryAndSetImg(query, LimitOffset.build(8, 0));
  }
}
