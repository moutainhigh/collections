package com.esp.server.web.model;

import lombok.Data;

@Data
public class HouseQueryReq {
  
  private House query;
  
  private Integer limit;
  
  private Integer offset;
}
