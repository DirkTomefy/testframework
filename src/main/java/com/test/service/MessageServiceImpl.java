package com.test.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.model.Category;
import com.test.model.Message;
import com.test.model.User;
import com.test.repository.CategoryRepository;
import com.test.repository.MessageRepository;
import com.test.repository.UserRepository;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        return messageRepository.findAllWithRelations();
    }

    @Override
    @Transactional(readOnly = true)
    public Message findById(Long id) {
        return messageRepository.findByIdWithRelations(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> createAll(User user, Category category, List<Message> messages) {

        // 1) Un seul user
        User savedUser = userRepository.save(user);

        // 2) Une seule catégorie
        Category savedCategory = categoryRepository.save(category);

        // 3) Tous les messages liés au même user + même catégorie
        LocalDateTime now = LocalDateTime.now();
        for (Message m : messages) {
            m.setUser(savedUser);
            m.setCategory(savedCategory);
            m.setCreatedAt(now);
        }

        return messageRepository.saveAll(messages);
    }
}