package br.com.kafka.producer.resource.kafka;

import br.com.kafka.producer.exception.AppException;
import br.com.kafka.producer.model.User;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroFactory;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaResource {

    private static final String KAFKA_TOPIC = "kafka_topic_example";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @GetMapping("/publish")
    public String post() throws AppException {
        try {
            kafkaTemplate.send(KAFKA_TOPIC, User.builder().name("Teste").build()).get();
            return "Published successfully";
        } catch (Exception e) {
            throw new AppException("kafka", e.getMessage());
        } finally {
            kafkaTemplate.flush();
        }
    }

    @GetMapping("/avro/generate")
    public ResponseEntity generate() throws AppException {
        ObjectMapper objectMapper = new ObjectMapper(new AvroFactory());
        AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();
        try {
            objectMapper.acceptJsonFormatVisitor(User.class, avroSchemaGenerator);
            return new ResponseEntity<>(avroSchemaGenerator.getAvroSchema().toString(), HttpStatus.OK);
        } catch (JsonMappingException e) {
            throw new AppException("json", e.getMessage());
        }
    }
}
