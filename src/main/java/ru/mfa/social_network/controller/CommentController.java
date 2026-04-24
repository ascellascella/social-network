package ru.mfa.social_network.controller;

import ru.mfa.social_network.model.Comment;
import ru.mfa.social_network.model.User;
import ru.mfa.social_network.model.Post;
import ru.mfa.social_network.repository.CommentRepository;
import ru.mfa.social_network.repository.UserRepository;
import ru.mfa.social_network.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PostRepository postRepo;

    @PostMapping
    public Comment create(@RequestBody CommentRequest req) {
        User user = userRepo.findById(req.getUserId()).orElse(null);
        Post post = postRepo.findById(req.getPostId()).orElse(null);
        return commentRepo.save(new Comment(req.getText(), user, post));
    }

    @GetMapping
    public List<Comment> getAll() { return commentRepo.findAll(); }

    @GetMapping("/{id}")
    public Comment getOne(@PathVariable Long id) { return commentRepo.findById(id).orElse(null); }

    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id, @RequestBody String text) {
        Comment comment = commentRepo.findById(id).orElse(null);
        if (comment != null) comment.setText(text);
        return commentRepo.save(comment);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        commentRepo.deleteById(id);
        return "deleted";
    }
}

class CommentRequest {
    private Long userId;
    private Long postId;
    private String text;
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}