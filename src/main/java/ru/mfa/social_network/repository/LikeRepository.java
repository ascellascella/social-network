package ru.mfa.social_network.repository;

import ru.mfa.social_network.model.Like;
import ru.mfa.social_network.model.User;
import ru.mfa.social_network.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    boolean existsByUserAndPost(User user, Post post);
    long countByPost(Post post);
}