package com.esp.server.commentserver.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
public class Blog {
  private Integer id;
  private String  tags;
  private String  content;
  private String  title;
  private Date    createTime;

  @Transient
  private String  digest;  // 博客摘要
  @Transient
  private List<String> tagList = Lists.newArrayList(); // 标签列表
}
