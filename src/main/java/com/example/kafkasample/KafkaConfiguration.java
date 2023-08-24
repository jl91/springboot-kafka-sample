package com.example.kafkasample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

@EnableKafka
@Configuration
public class KafkaConfiguration {


    @Autowired
    KafkaProperties properties;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                properties.buildConsumerProperties());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> listener =
                new ConcurrentKafkaListenerContainerFactory<>();

        listener.setConsumerFactory(consumerFactory());

        // Não falhar, caso ainda não existam os tópicos para consumo
        listener.getContainerProperties()
                .setMissingTopicsFatal(false);

        // ### AQUI
        // Commit manual do offset
        listener.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // ### AQUI
        // Commits síncronos
        listener.getContainerProperties().setSyncCommits(Boolean.TRUE);

        return listener;
    }
}
