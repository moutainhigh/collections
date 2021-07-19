package com.esp.server.web.service;

import com.esp.server.web.common.ApiResponse;
import com.esp.server.web.model.Comment;
import com.esp.server.web.model.CommentReq;
import com.esp.server.web.util.ResponseDataUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/30 14:39
 * @Description:
 */
@FeignClient(value = "gateway", fallback = CommentService.CommentServiceFallback.class)
public interface CommentService {

    @PostMapping("/comment/comment/list")
    ApiResponse<List<Comment>> listComments(@RequestBody CommentReq commentReq);

    @PostMapping("/comment/comment/add")
    ApiResponse addComment(@RequestBody CommentReq commentReq);

    @Component
    class CommentServiceFallback implements CommentService {
        @Override
        public ApiResponse<List<Comment>> listComments(CommentReq commentReq) {
            return ResponseDataUtil.setResponseData("listComments");
        }

        @Override
        public ApiResponse addComment(CommentReq commentReq) {
            return ResponseDataUtil.setResponseData("addComment");
        }
    }
}
