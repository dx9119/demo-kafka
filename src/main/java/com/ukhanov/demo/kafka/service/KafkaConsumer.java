package com.ukhanov.demo.kafka.service;

import com.ukhanov.demo.kafka.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "string-topic", groupId = "demo-group")
    public void listenStr(String message) {
        System.out.println("Принято: "+message);
    }

    @KafkaListener(topics = "json-topic", groupId = "demo-group")
    public void listen(User user) {
        System.out.println("Получен пользователь: " + user.getName());
    }

}
