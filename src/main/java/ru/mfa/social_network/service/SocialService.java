package ru.mfa.social_network.service;

import ru.mfa.social_network.model.*;
import ru.mfa.social_network.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FollowRepository followRepository;

    // 1. Подписаться на пользователя
    @Transactional
    public String followUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("Вы уже подписаны на этого пользователя");
        }

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
        return follower.getUsername() + " подписался на " + following.getUsername();
    }

    // 2. Отписаться от пользователя
    @Transactional
    public String unfollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Подписка не найдена"));

        followRepository.delete(follow);
        return follower.getUsername() + " отписался от " + following.getUsername();
    }

    // 3. Поставить лайк
    @Transactional
    public String likePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new RuntimeException("Вы уже поставили лайк этому посту");
        }

        Like like = new Like(user, post);
        likeRepository.save(like);
        return user.getUsername() + " лайкнул пост #" + postId;
    }

    // 4. Убрать лайк
    @Transactional
    public String unlikePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("Лайк не найден"));

        likeRepository.delete(like);
        return user.getUsername() + " убрал лайк с поста #" + postId;
    }

    // 5. Получить список подписчиков пользователя
    public List<User> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return followRepository.findByFollowing(user).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }

    // 6. Получить список подписок пользователя
    public List<User> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());
    }

    // 7. Получить ленту постов (посты пользователей, на которых подписан)
    public List<Post> getFeed(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        List<User> following = getFollowing(userId);
        return postRepository.findAll().stream()
                .filter(post -> following.contains(post.getUser()))
                .collect(Collectors.toList());
    }

    // 8. Получить количество лайков у поста
    public long getLikesCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));
        return likeRepository.countByPost(post);
    }

    // 9. Получить топ-5 популярных постов (по лайкам)
    public List<Post> getTopPosts() {
        return postRepository.findAll().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getLikesCount(), p1.getLikesCount()))
                .limit(5)
                .collect(Collectors.toList());
    }
}