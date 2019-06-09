package br.com.kafka.producer.resource;

import br.com.kafka.producer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class UserResource {

    private static final String KAFKA_TOPIC = "kafka_topic_example";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @GetMapping("/publish/{name}")
    public String post(@PathVariable("name") String name) {
        kafkaTemplate.send(KAFKA_TOPIC, new User(name, "Development", 5000L));
        return "Published successfully";
    }

}
