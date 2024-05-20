package com.fse.services;

import com.fse.modals.BlogsModal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @KafkaListener(topics = "blogs", groupId = "group_id")
    public void consume(BlogsModal blogs) {
        logger.info("consumed blogs ",blogs.toString());
    }
}
