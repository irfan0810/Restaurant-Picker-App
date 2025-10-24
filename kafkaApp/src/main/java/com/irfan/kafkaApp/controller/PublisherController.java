package com.irfan.kafkaApp.controller;

import com.irfan.kafkaApp.kafka.MainProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publish")
public class PublisherController {

    private final MainProducer producer;

    public PublisherController(MainProducer producer) {
        this.producer = producer;
    }

    // testing
    @PostMapping(value="/csv", consumes="text/plain")
    public ResponseEntity<Void> publishCsv(@RequestBody String csv) {
        producer.sendCsv(csv);
        return ResponseEntity.accepted().build();
    }
}
