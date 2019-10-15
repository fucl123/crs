package com.kzkj.config;



import com.kzkj.mq.MqReceiver;
import com.kzkj.mq.MqSender;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig
{
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    @Value("${spring.rabbitmq.prefetchCount}")
    private int prefetchCount;

    @Value("${spring.rabbitmq.concurrentConsumers}")
    private int concurrentConsumers;

    @Value("${spring.rabbitmq.maxConcurrency}")
    private int maxConcurrency;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host + ":" + port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }
    @Bean
    public MqSender mqSender() {
        return new MqSender();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        MqSender mqSender = mqSender();
        template.setConfirmCallback(mqSender);
        mqSender.setRabbitTemplate(template);
        return template;
    }


    @Bean
    public MqReceiver mqReceiver() {
        MqReceiver mqReceiver = new MqReceiver();
        return mqReceiver;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setPrefetchCount(prefetchCount);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(maxConcurrency);
        simpleMessageListenerContainer.setConcurrentConsumers(concurrentConsumers);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO); //设置确认模式自动确认
        simpleMessageListenerContainer.setupMessageListener(mqReceiver());
        return simpleMessageListenerContainer;
    }

}
