package com.server.service;

import com.server.entity.Comment;

import java.util.List;

public interface CommentService {
    // 获取评论列表
    List<Comment> getCommentList(Long productId);

    // 添加评论
    void addComment(Comment comment);

    // 回复评论
    void replyComment(Comment comment);

    // 删除评论
    void deleteComment(Long id);
}
