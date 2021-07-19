package com.esp.server.commentserver.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Data
public class Comment {

  @Id
  private Long id;
  private String content;
  private Long   houseId;
  private Date   createTime;
  private Integer blogId;
  private Integer type;
  private Long    userId;

  @Transient
  private String  userName;
  @Transient
  private String  avatar; // 用户头像
  
}
