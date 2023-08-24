package com.example.kafkasample;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerController {


    @KafkaListener(
            topics = "test",
            groupId = "group_id"
    )
    public void listen(
            @Headers String headers,
            @Payload String message,
            Acknowledgment ack
    ) {
        ack.acknowledge();

        System.out.println(
                String.format(
                        "Received Messasge in group - headers: %s, message: %s ",
                        headers,
                        message
                )
        );

    }
}
