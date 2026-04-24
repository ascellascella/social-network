package ru.mfa.social_network.repository;

import ru.mfa.social_network.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}