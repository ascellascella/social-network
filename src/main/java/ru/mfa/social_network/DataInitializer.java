package ru.mfa.social_network;

import ru.mfa.social_network.model.*;
import ru.mfa.social_network.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли уже данные
        if (userRepository.count() == 0) {
            System.out.println("📝 Загрузка тестовых данных...");

            // Создаём пользователей
            User alice = new User("alice", "alice@mail.com");
            User bob = new User("bob", "bob@mail.com");
            User charlie = new User("charlie", "charlie@mail.com");
            User diana = new User("diana", "diana@mail.com");

            userRepository.save(alice);
            userRepository.save(bob);
            userRepository.save(charlie);
            userRepository.save(diana);

            // Создаём посты
            Post post1 = new Post("Привет, мир! Это мой первый пост", alice);
            Post post2 = new Post("Java - лучший язык программирования!", bob);
            Post post3 = new Post("Сегодня отличная погода", charlie);
            Post post4 = new Post("Люблю Spring Boot!", alice);
            Post post5 = new Post("PostgreSQL рулит", diana);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
            postRepository.save(post4);
            postRepository.save(post5);

            // Создаём комментарии
            commentRepository.save(new Comment("Добро пожаловать!", bob, post1));
            commentRepository.save(new Comment("Согласен!", alice, post2));
            commentRepository.save(new Comment("У нас дождь :(", alice, post3));
            commentRepository.save(new Comment("Отличный пост!", charlie, post4));
            commentRepository.save(new Comment("Полезная информация", bob, post5));

            // Создаём лайки
            likeRepository.save(new Like(bob, post1));
            likeRepository.save(new Like(charlie, post1));
            likeRepository.save(new Like(alice, post2));
            likeRepository.save(new Like(charlie, post2));
            likeRepository.save(new Like(alice, post4));
            likeRepository.save(new Like(bob, post4));
            likeRepository.save(new Like(charlie, post4));
            likeRepository.save(new Like(alice, post5));
            likeRepository.save(new Like(bob, post5));

            // Создаём подписки
            followRepository.save(new Follow(alice, bob));     // alice подписана на bob
            followRepository.save(new Follow(alice, charlie)); // alice подписана на charlie
            followRepository.save(new Follow(bob, alice));     // bob подписан на alice
            followRepository.save(new Follow(charlie, alice)); // charlie подписан на alice
            followRepository.save(new Follow(diana, alice));   // diana подписана на alice

            System.out.println(" Загружено: ");
            System.out.println("   - Пользователей: 4");
            System.out.println("   - Постов: 5");
            System.out.println("   - Комментариев: 5");
            System.out.println("   - Лайков: 9");
            System.out.println("   - Подписок: 5");
        }
    }
}