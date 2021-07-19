package com.esp.server.commentserver.model;

import lombok.Data;

@Data
public class User {
  
  private Long id;
  private String  name;
  private String  phone;
  private String  email;
  private String  avatar;

}
