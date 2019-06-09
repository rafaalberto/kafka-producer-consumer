package br.com.kafka.consumer.listener;

import br.com.kafka.consumer.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final String KAFKA_TOPIC = "kafka_topic_example";
    private static final String KAFKA_TOPIC_JSON = "kafka_topic_example_json";

    @KafkaListener(topics = KAFKA_TOPIC, groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consume: " + message);
    }

    @KafkaListener(topics = KAFKA_TOPIC_JSON, groupId = "group_json", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(User user) {
        System.out.println("Consumed JSON Message: " + user);
    }

}
