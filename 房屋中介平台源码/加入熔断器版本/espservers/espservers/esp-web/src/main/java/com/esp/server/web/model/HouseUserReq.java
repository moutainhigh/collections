package com.esp.server.web.model;

import lombok.Data;

/**
 * 用户和房产关系的类
 */
@Data
public class HouseUserReq {

  private Long houseId;
  
  private Long userId;
  
  private Integer bindType;
  
  private boolean unBind;

  private Integer mark;

}
