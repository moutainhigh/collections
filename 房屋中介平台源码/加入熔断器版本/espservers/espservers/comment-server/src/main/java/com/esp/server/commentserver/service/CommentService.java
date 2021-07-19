package com.esp.server.commentserver.service;

import com.alibaba.fastjson.JSON;
import com.esp.server.commentserver.constant.CommentType;
import com.esp.server.commentserver.domain.Comment;
import com.esp.server.commentserver.mapper.CommentMapper;
import com.esp.server.commentserver.model.ApiResponse;
import com.esp.server.commentserver.model.User;
import com.esp.server.commentserver.utils.BeanHelper;
import com.esp.server.commentserver.utils.GeneralConvert;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class CommentService {
  
  @Autowired(required = false)
  private CommentMapper commentMapper;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${qiniu.cdn.prefix:}")
  private String avatarPrefix;


  /**
   * 添加评论
   * @param houseId
   * @param content
   * @param userId
   */
  @Transactional(rollbackFor=Exception.class)
  public void addComment(Long houseId, Integer blogId, String content, Long userId, CommentType type) {
    String key = null;
    Comment comment = new Comment();
    if (type.value == 1) {
      comment.setHouseId(houseId);
      key = "house_comments_" + houseId;
    }else {
      comment.setBlogId(blogId);
      key = "blog_comments_" + blogId ;
    }
    comment.setContent(content);
    comment.setUserId(userId);
    comment.setType(type.value);
    BeanHelper.onInsert(comment);
    BeanHelper.setDefaultProp(comment, Comment.class);
    commentMapper.insert(comment);

    redisTemplate.delete(redisTemplate.keys(key + "*"));
  }

  /**
   * 获取某个博客的评论信息
   * @param blogId
   * @param size
   * @param user
   * @return
   */
  public List<Comment> getBlogComments(long blogId, int size, User user) {
    String key = "blog_comments_"+blogId + "_" + size;
    String json = redisTemplate.opsForValue().get(key)+"";
    List<Comment> comments = JSON.parseArray(json,Comment.class);
    if (Strings.isNullOrEmpty(json)) {
      comments = commentMapper.selectBlogComments(blogId,size);
      if (comments.size() > 0) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(comments));
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
      }
    }
    comments.forEach(comment -> {
       comment.setUserName(user.getName());
       comment.setAvatar(avatarPrefix+user.getAvatar());
    });
    return comments;
  }

  /**
   * 获取某套房产对应评论信息
   * @param houseId
   * @param size
   * @return
   */
  public List<Comment> getHouseComments(Long houseId, Integer size) {
    String key  ="house_comments" + "_" + houseId + "_" + size;
    Object json = redisTemplate.opsForValue().get(key);
    List<Comment> lists = null;
    if (Objects.isNull(json)) {
       lists = doGetHouseComments(houseId, size);
      if (lists.size() > 0) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(lists));
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
      }
    }else {
      lists =  JSON.parseArray(json+"", Comment.class);
    }
    return lists;
  }

  /**
   * 数据库获取评论列表
   * 调用用户服务获取头像
   * @param houseId
   * @param size
   * @return
   */
  public List<Comment> doGetHouseComments(Long houseId, Integer size) {
    List<Comment>  comments = commentMapper.selectComments(houseId, size);
    comments.forEach(comment -> {
        User user = getUser(comment.getUserId());
        comment.setAvatar(avatarPrefix+user.getAvatar());
        comment.setUserName(user.getName());
    });
    return comments;
  }

  public List<Comment> getBlogComments(Integer blogId, Integer size) {
    String key  ="blog_comments" + "_" + blogId + "_" + size;
    Object json = redisTemplate.opsForValue().get(key);
    List<Comment> lists = null;
    if (Objects.isNull(json)) {
      lists = doGetBlogComments(blogId, size);
      if (lists.size() > 0) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(lists));
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
      }
    }else {
      lists =  JSON.parseArray(json+"",Comment.class);
    }
    return lists;
  }

  private List<Comment> doGetBlogComments(Integer blogId, Integer size) {
    List<Comment>  comments = commentMapper.selectBlogComments(blogId, size);
    comments.forEach(comment -> {
       User user = getUser(comment.getUserId());
       comment.setAvatar(avatarPrefix+user.getAvatar());
       comment.setUserName(user.getName());
    });
    return comments;
  }

  /**
   * 根据用户编号查询用户信息
   * @param id
   * @return
   */
  private User getUser(Long id) {
    ApiResponse<User> apiResponse = restTemplate.getForObject("http://user-service/accounts/getUser?id=" + id, ApiResponse.class);
    return GeneralConvert.converter(apiResponse.getData(), User.class);
  }
}
