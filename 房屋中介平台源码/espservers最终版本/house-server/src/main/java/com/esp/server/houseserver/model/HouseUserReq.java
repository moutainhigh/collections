package com.esp.server.houseserver.model;

import lombok.Data;

@Data
public class HouseUserReq {
  private Long houseId;
  private Long userId;
  private Integer bindType;
  private boolean unBind;
}
