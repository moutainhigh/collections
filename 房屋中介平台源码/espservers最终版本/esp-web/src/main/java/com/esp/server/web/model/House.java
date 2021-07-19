package com.esp.server.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

// 文件MultipartFile在jackson没有对应的转换器，必须排除，否则报序列化错误
@JsonIgnoreProperties({"houseFiles","floorPlanFiles"})
@Data
public class House {

  private Long id;
  private Integer type;
  private Integer price;
  private String  name;
  private String images;
  private Integer area;
  private Integer beds;
  private Integer baths;
  private Double  rating;
  private Integer roundRating = 0;
  private String  remarks;
  private String  properties;
  private String  floorPlan;
  private String  tags;
  private Integer cityId;
  private Integer    communityId;
  private String     address;
  private Date createTime;
  
  private String     firstImg;
  
  private List<String> imageList = Lists.newArrayList();
  
  
  private List<String> floorPlanList = Lists.newArrayList();
  private List<String> featureList   = Lists.newArrayList();
  
  private List<MultipartFile> houseFiles;
  
  private List<MultipartFile> floorPlanFiles;

  private String priceStr;
  private String typeStr;
  private Long userId;

  // 收藏状态
  private boolean bookmarked;
  
  private Integer state;
  
  private List<Long> ids;
  
  private String  sort = "time_desc";//price_desc,price_asc,time_desc
  public void setType(Integer type) {
    this.type = type;
    if (Objects.equal(type, 1)) {
      this.typeStr = "售卖";
    }else {
      this.typeStr = "出租";
    }
  }
  public void setPrice(Integer price) {
    this.price = price;
    this.priceStr = this.price + "万";
  }

  public void setProperties(String properties) {
    this.properties = properties;
    this.featureList = Splitter.on(",").splitToList(properties);
  }

  public void setFloorPlan(String floorPlan) {
    this.floorPlan = floorPlan;
    if (StringUtils.isNotBlank(floorPlan)) {
      this.floorPlanList = Splitter.on(",").splitToList(floorPlan);
    }
   
  }

  
  public void setImages(String images) {
    this.images = images;
    if (StringUtils.isNotBlank(images) && firstImg == null) {
       List<String> list =  Splitter.on(",").splitToList(images);
       if (list.size() > 0) {
          this.firstImg = list.get(0);
          this.imageList = list;
       }
    }
  }

  public void setFeatureList(List<String> featureList) {
    this.featureList = featureList;
    this.properties = Joiner.on(",").join(featureList);
  }

  public void setRating(Double rating) {
    this.rating = rating;
    this.roundRating = (int) Math.round(rating);
  }

}
