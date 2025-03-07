package com.server.controller.user;

import com.server.entity.Comment;
import com.server.pojo.R;
import com.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public R<List<Comment>> getCommentList(@RequestParam Long productId) {
        return R.ok(commentService.getCommentList(productId));
    }

    @PostMapping("/add")
    public R addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return R.ok();
    }

    @PostMapping("/reply")
    public R replyComment(@RequestBody Comment comment) {
        commentService.replyComment(comment);
        return R.ok();
    }

    @PostMapping("/delete")
    public R deleteComment(@RequestParam Long id) {
        commentService.deleteComment(id);
        return R.ok();
    }
}
