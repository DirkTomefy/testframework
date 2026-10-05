package com.test.service;

import com.test.model.Message;
import com.test.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}