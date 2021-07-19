package com.esp.server.web.common;

public class PageParams {
  
  private static final Integer PAGE_SIZE = 5;

  // 每页显示数
  private Integer pageSize;

  // 当前页
  private Integer pageNum ;

  // 当前页的偏移量
  private Integer offset;

  // 查询限制显示的条数
  private Integer limit;
  
  public static PageParams build(){
    return build(PAGE_SIZE, 1);
  }
  
  public static PageParams build(Integer pageSize,Integer pageNum){
    if (pageSize == null) {
      pageSize = PAGE_SIZE;
    }
    if (pageNum == null) {
      pageNum = 1;
    }
    return new PageParams(pageSize,pageNum);
  }
  
  
  public  PageParams(){
    this(PAGE_SIZE, 1);
  }
  
  public  PageParams(Integer pageSize,Integer pageNum) {
    assert pageSize != null;
    assert pageNum  != null;
    this.pageSize = pageSize;
    this.pageNum  = pageNum;
    
    this.offset = pageSize * (pageNum - 1);
    this.limit  = pageSize;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }
  

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public String toString() {
    return "PageParams [pageSize=" + pageSize + ", pageNum=" + pageNum + "]";
  }

}
