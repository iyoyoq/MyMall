package com.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.entity.Comment;
import com.server.mapper.CommentMapper;
import com.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentList(Long productId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getProductId, productId)
              .eq(Comment::getStatus, 2)  // 只查询状态为公示的评论
              .orderByDesc(Comment::getCreateTime);
        return commentMapper.selectList(wrapper);
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(System.currentTimeMillis());
        comment.setStatus(2);  // 设置为公示状态
        commentMapper.insert(comment);
    }

    @Override
    public void replyComment(Comment comment) {
        Comment parentComment = commentMapper.selectById(comment.getParentId());
        if (parentComment != null) {
            comment.setProductId(parentComment.getProductId());
            comment.setCreateTime(System.currentTimeMillis());
            comment.setStatus(2);
            commentMapper.insert(comment);
        }
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(0);  // 设置为删除状态
        commentMapper.updateById(comment);
    }
}
