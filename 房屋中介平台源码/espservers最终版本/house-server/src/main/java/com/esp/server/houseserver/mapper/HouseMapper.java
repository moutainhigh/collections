package com.esp.server.houseserver.mapper;

import com.esp.server.houseserver.common.mapper.HouseBaseMapper;
import com.esp.server.houseserver.domain.Community;
import com.esp.server.houseserver.domain.House;
import com.esp.server.houseserver.domain.HouseUser;
import com.esp.server.houseserver.domain.UserMsg;
import com.esp.server.houseserver.model.LimitOffset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper extends HouseBaseMapper<House> {
  /**
   * Map<String, Object>
   *     house  query
   *
   * @param query
   * @param limitOffset
   * @return
   */
  List<House> selectHouse(@Param("house") House query, @Param("pageParams") LimitOffset limitOffset);
  
  Long selectHouseCount(@Param("house") House query);
  
  List<Community> selectCommunity(Community community);
  
  int insertUserMsg(UserMsg userMsg);
  
  int updateHouse(House house);
  
  HouseUser selectHouseUser(@Param("userId") long userID, @Param("id") long id, @Param("type") int type);
  
 /* HouseUser selectHouseUserById(@Param("id") long id, @Param("type") int type);*/
  
  int insertHouseUser(HouseUser houseUser);
  
  int downHouse(Long id);
  
  int deleteHouseUser(@Param("id") Long id, @Param("userId") Long userId, @Param("type") Integer type);
  
  
}

