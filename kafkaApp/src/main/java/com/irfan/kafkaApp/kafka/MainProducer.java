package com.irfan.kafkaApp.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MainProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public MainProducer(KafkaTemplate<String, String> kafkaTemplate,
                        @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendCsv(String csv) {
        kafkaTemplate.send(topic, csv);
    }
}
