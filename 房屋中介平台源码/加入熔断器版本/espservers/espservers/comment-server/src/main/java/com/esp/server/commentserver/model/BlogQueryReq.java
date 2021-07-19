package com.esp.server.commentserver.model;

import com.esp.server.commentserver.domain.Blog;
import lombok.Data;

@Data
public class BlogQueryReq {
  
  private Blog blog;
  
  private Integer limit;
  
  private Integer offset;
}
