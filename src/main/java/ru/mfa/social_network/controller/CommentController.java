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
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // CREATE - создать комментарий
    @PostMapping
    public Comment create(@RequestBody CommentRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        Post post = postRepository.findById(request.getPostId()).orElse(null);
        Comment comment = new Comment(request.getText(), user, post);
        return commentRepository.save(comment);
    }

    // READ - получить все комментарии
    @GetMapping
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    // READ - получить комментарий по ID
    @GetMapping("/{id}")
    public Comment getOne(@PathVariable Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    // UPDATE - обновить комментарий
    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id, @RequestBody String text) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setText(text);
        }
        return commentRepository.save(comment);
    }

    // DELETE - удалить комментарий
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return "Comment deleted";
    }
}

// Вспомогательный класс для создания комментария
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