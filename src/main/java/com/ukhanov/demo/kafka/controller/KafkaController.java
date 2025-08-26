package com.ukhanov.demo.kafka.controller;

import com.ukhanov.demo.kafka.model.User;
import com.ukhanov.demo.kafka.service.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send (@RequestParam String message) {
        producer.sendMessageFireAndForget(message);

        User user = new User("User01");
        producer.sendJsonMessageWithKeyAsync(user);

        return ResponseEntity.ok("Отправлено:"+message);
    }
}
