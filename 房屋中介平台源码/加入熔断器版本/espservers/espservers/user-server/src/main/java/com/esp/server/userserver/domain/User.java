package com.esp.server.userserver.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;


@Data
public class User {
  @Id
  @KeySql(useGeneratedKeys = true)
  private Long id;
  private String  name;
  private String  phone;
  private String  email;
  private String  aboutme;
  private String  password;
  private String avatar;
  private Integer type;
  private Date    createTime;
  private Integer enable;
  private Long  agencyId;

  @Transient
  private String  confirmPassword;
  @Transient
  private String  key;
  @Transient // 激活链接
  private String enableUrl;
  @Transient
  private String token;
  @Transient
  private String agencyName;

}
