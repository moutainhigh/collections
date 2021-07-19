package com.esp.server.commentserver.controller;

import com.esp.server.commentserver.constant.CommentType;
import com.esp.server.commentserver.domain.Comment;
import com.esp.server.commentserver.model.ApiResponse;
import com.esp.server.commentserver.model.CommentReq;
import com.esp.server.commentserver.service.CommentService;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
  
  @Autowired(required = false)
  private CommentService commentService;

  // 添加评论
  @PostMapping("add")
  public ApiResponse leaveComment(@RequestBody CommentReq commentReq){
    Integer type = commentReq.getType();
    CommentType commentType = CommentType.BLOG;
    if (Objects.equal(1,type)) {
      commentType = CommentType.HOUSE;
    }
    commentService.addComment(commentReq.getHouseId(),commentReq.getBlogId(),commentReq.getContent(),commentReq.getUserId(), commentType);
    return ApiResponse.ofSuccess();
  }
  
  @PostMapping("list")
  public ApiResponse<List<Comment>> list(@RequestBody CommentReq commentReq){
    Integer type = commentReq.getType();
    List<Comment> list = null;
    if (Objects.equal(1, type)) {
      list = commentService.getHouseComments(commentReq.getHouseId(),commentReq.getSize());
    }else {
      list = commentService.getBlogComments(commentReq.getBlogId(),commentReq.getSize());
    }
    return ApiResponse.ofSuccess(list);
  }
}