package com.esp.server.web.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户与经纪人消息类
 */
@Data
public class UserMsg {
  private Long id;
  private String msg;
  private Long   userId;
  private Date   createTime;
  private Long   agentId;
  private Long   houseId;
  private String email;
  private String userName;
}
