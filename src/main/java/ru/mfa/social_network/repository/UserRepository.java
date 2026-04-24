package ru.mfa.social_network.repository;

import ru.mfa.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}