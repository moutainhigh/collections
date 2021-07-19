package com.esp.server.houseserver.model;

import com.esp.server.houseserver.domain.House;
import lombok.Data;

/**
 * 房产查询的分页条件对象
 */
@Data
public class HouseQueryReq {
  
  private House query;
  
  private Integer limit;
  
  private Integer offset; // （当前页 - 1）* 每页显示数

}
