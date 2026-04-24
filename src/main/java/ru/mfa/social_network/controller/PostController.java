package ru.mfa.social_network.controller;

import ru.mfa.social_network.model.Post;
import ru.mfa.social_network.model.User;
import ru.mfa.social_network.repository.PostRepository;
import ru.mfa.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE - создать пост
    @PostMapping
    public Post create(@RequestBody PostRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        Post post = new Post(request.getContent(), user);
        return postRepository.save(post);
    }

    // READ - получить все посты
    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    // READ - получить пост по ID
    @GetMapping("/{id}")
    public Post getOne(@PathVariable Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // UPDATE - обновить пост
    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody String content) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setContent(content);
        }
        return postRepository.save(post);
    }

    // DELETE - удалить пост
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "Post deleted";
    }
}

// Вспомогательный класс для создания поста
class PostRequest {
    private Long userId;
    private String content;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}