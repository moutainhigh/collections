package com.esp.server.commentserver.constant;

/**
 * 评论类型枚举类
 */
public enum CommentType {
  HOUSE(1),BLOG(2);
  
  public  final Integer value;
  
  CommentType(Integer value){
    this.value = value;
  }
  

}
