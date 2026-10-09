package com.test.service;

import java.util.List;

import com.test.model.Category;
import com.test.model.Message;
import com.test.model.User;

public interface MessageService {

    List<Message> findAll();

    Message findById(Long id);

    void deleteById(Long id);

    
    List<Message> createAll(User user, Category category, List<Message> messages);
}