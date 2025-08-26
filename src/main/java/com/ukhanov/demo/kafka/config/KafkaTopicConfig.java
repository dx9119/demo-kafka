package com.ukhanov.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic exampleTopic() {
        return TopicBuilder.name("string-topic")
                .partitions(3)
                .replicas(1) //без резервных копий
                .build();
    }

    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name("json-topic")
                .partitions(3)
                .replicas(1) //без резервных копий
                .build();
    }

}
