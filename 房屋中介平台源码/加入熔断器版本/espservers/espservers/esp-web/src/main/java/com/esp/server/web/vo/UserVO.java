package com.esp.server.web.vo;

import lombok.Data;


@Data
public class UserVO {
  private Long id;
  private String  name;
  private String  phone;
  private String  email;
  private String  aboutme;
  private String  password;
  private String avatar;
  private Integer type;
  private Integer enable;
  private Long  agencyId;
  private String  confirmPassword;
  private String  key;
  private String enableUrl;
  private String token;
  private String agencyName;
}
