package com.ukhanov.demo.kafka.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, Object> jsonKafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> stringKafkaTemplate, KafkaTemplate<String, Object> jsonKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    //отправка в случайную партицию без обработки результата (не понятно было ли доставлено успешно)
    public void sendMessageFireAndForget(String message){
        stringKafkaTemplate.send("string-topic", message);
    }

    // key - влияет на выбор партиции
    public void sendJsonMessageWithKeyAsync(Object payload) {
        ProducerRecord<String, Object> record = new ProducerRecord<>("json-topic", "partition-key-1", payload);

        // Асинхронная отправка с CompletableFuture
        CompletableFuture<SendResult<String, Object>> future = jsonKafkaTemplate.send(record);

        // обработка результата
        future.thenAccept(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            System.out.printf("JSON отправлен в топик %s partition %d offset %d%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
        }).exceptionally(ex -> {
            System.err.printf("Ошибка отправки json: %s%n", ex.getMessage());
            // Можно добавить retry или логирование
            return null;
        });
    }




}
