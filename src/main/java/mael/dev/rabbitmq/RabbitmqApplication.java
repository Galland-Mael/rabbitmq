package mael.dev.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqApplication {

    static final String TOPIC_EXCHANGE_NAME = "mainTopicExchange";

    static final String GROUP_1 = "group1";
    static final String GROUP_2 = "group2";

    @Bean
    Queue queueGroup1() {
        return new Queue(GROUP_1, false);
    }

    @Bean
    Queue queueGroup2() {
        return new Queue(GROUP_2, false);
    }
    
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding bindingGroup1(Queue queueGroup1, TopicExchange exchange) {
        return BindingBuilder.bind(queueGroup1).to(exchange).with("group.1");
    }

    @Bean
    Binding bindingGroup2(Queue queueGroup2, TopicExchange exchange) {
        return BindingBuilder.bind(queueGroup2).to(exchange).with("group.2");
    }

    @Bean
    SimpleMessageListenerContainer containerGroup(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapterGroup1) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(GROUP_1);
        container.setMessageListener(listenerAdapterGroup1);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerGroup2(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapterGroup2) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(GROUP_2);
        container.setMessageListener(listenerAdapterGroup2);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterGroup1(QueueReceiverGroup1 receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter listenerAdapterGroup2(QueueReceiverGroup2 receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
