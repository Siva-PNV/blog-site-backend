package com.fse.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.modals.BlogsModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private static final String TOPIC = "blogs";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, BlogsModal> kafkaTemplate;

    public void sendMessage(BlogsModal blogs) {
        this.kafkaTemplate.send(TOPIC, blogs);
    }
}
