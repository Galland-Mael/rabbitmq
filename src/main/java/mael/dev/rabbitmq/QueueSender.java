package mael.dev.rabbitmq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static mael.dev.rabbitmq.RabbitmqApplication.TOPIC_EXCHANGE_NAME;

@Service
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToGroup1(String message) {
        System.out.println("Sending to group1: " + message);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "group.1", message);
    }

    public void sendToGroup2(String message) {
        System.out.println("Sending to group2: " + message);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "group.2", message);
    }

    public void sendToAllGroups(String message) {
        System.out.println("Sending to all groups: " + message);
        rabbitTemplate.convertAndSend("mainTopicExchange", "group.broadcast", message);
    }
}

