package com.esp.server.userserver.model;

import lombok.Data;

@Data
public class LimitOffset {
  private Integer limit;
  private Integer offset;

  /**
   * 构建分页参数的对象
   * @param limit
   * @param offset
   * @return
   */
  public static LimitOffset build(Integer limit,Integer offset) {
    LimitOffset limitOffset = new LimitOffset();
    limitOffset.setLimit(limit);
    limitOffset.setOffset(offset);
    return limitOffset;
  }

}
