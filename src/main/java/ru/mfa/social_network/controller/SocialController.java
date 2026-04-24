package ru.mfa.social_network.controller;

import ru.mfa.social_network.model.Post;
import ru.mfa.social_network.model.User;
import ru.mfa.social_network.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    // 1. Подписаться
    @PostMapping("/follow")
    public String follow(@RequestParam Long followerId, @RequestParam Long followingId) {
        return socialService.followUser(followerId, followingId);
    }

    // 2. Отписаться
    @DeleteMapping("/unfollow")
    public String unfollow(@RequestParam Long followerId, @RequestParam Long followingId) {
        return socialService.unfollowUser(followerId, followingId);
    }

    // 3. Поставить лайк
    @PostMapping("/like")
    public String like(@RequestParam Long userId, @RequestParam Long postId) {
        return socialService.likePost(userId, postId);
    }

    // 4. Убрать лайк
    @DeleteMapping("/unlike")
    public String unlike(@RequestParam Long userId, @RequestParam Long postId) {
        return socialService.unlikePost(userId, postId);
    }

    // 5. Получить подписчиков
    @GetMapping("/followers/{userId}")
    public List<User> getFollowers(@PathVariable Long userId) {
        return socialService.getFollowers(userId);
    }

    // 6. Получить подписки
    @GetMapping("/following/{userId}")
    public List<User> getFollowing(@PathVariable Long userId) {
        return socialService.getFollowing(userId);
    }

    // 7. Лента постов
    @GetMapping("/feed/{userId}")
    public List<Post> getFeed(@PathVariable Long userId) {
        return socialService.getFeed(userId);
    }

    // 8. Количество лайков у поста
    @GetMapping("/likes-count/{postId}")
    public long getLikesCount(@PathVariable Long postId) {
        return socialService.getLikesCount(postId);
    }

    // 9. Топ-5 постов
    @GetMapping("/top-posts")
    public List<Post> getTopPosts() {
        return socialService.getTopPosts();
    }
}