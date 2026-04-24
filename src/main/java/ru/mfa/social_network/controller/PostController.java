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
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public Post create(@RequestBody PostRequest req) {
        User user = userRepo.findById(req.getUserId()).orElse(null);
        return postRepo.save(new Post(req.getTitle(), user));
    }

    @GetMapping
    public List<Post> getAll() { return postRepo.findAll(); }

    @GetMapping("/{id}")
    public Post getOne(@PathVariable Long id) { return postRepo.findById(id).orElse(null); }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody String title) {
        Post post = postRepo.findById(id).orElse(null);
        if (post != null) post.setTitle(title);
        return postRepo.save(post);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        postRepo.deleteById(id);
        return "deleted";
    }
}

class PostRequest {
    private Long userId;
    private String title;
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}