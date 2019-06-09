const kafka = require('no-kafka')
const consumer = new kafka.SimpleConsumer({
    "connectionString": "127.0.0.1:9092"
})
const data = (messageSet) => {
    messageSet.forEach(element => {
        const value = element.message.value.toString('utf8')
        console.log(value)
    });
}
return consumer.init().then(() => consumer.subscribe('kafka_topic_example', data))
