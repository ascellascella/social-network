package ru.mfa.social_network.controller;

import ru.mfa.social_network.model.User;
import ru.mfa.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    // CREATE - создать пользователя
    @PostMapping
    public User create(@RequestBody User user) {
        return repository.save(user);
    }

    // READ - получить всех пользователей
    @GetMapping
    public List<User> getAll() {
        return repository.findAll();
    }

    // READ - получить пользователя по ID
    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE - обновить пользователя
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return repository.save(user);
    }

    // DELETE - удалить пользователя
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "User deleted";
    }
}