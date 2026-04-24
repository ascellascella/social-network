package ru.mfa.social_network.repository;

import ru.mfa.social_network.model.Follow;
import ru.mfa.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    boolean existsByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollowing(User user);  // подписчики пользователя
    List<Follow> findByFollower(User user);   // на кого подписан пользователь
}