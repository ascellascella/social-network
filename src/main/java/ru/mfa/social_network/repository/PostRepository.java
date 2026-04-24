package ru.mfa.social_network.repository;

import ru.mfa.social_network.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}