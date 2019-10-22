package com.kzkj.config;



import com.kzkj.mq.MqReceiver;
import com.kzkj.mq.MqSender;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig
{
    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public RabbitConfigInfo mqConfig() {
        return new RabbitConfigInfo();
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        RabbitConfigInfo config = mqConfig();
        connectionFactory.setAddresses(config.getAddress());
        connectionFactory.setUsername(config.getUsername());
        connectionFactory.setPassword(config.getPassword());
        connectionFactory.setVirtualHost(config.getVhost());
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
        RabbitConfigInfo config = mqConfig();
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setPrefetchCount(config.getPrefetchCount());
        simpleMessageListenerContainer.setMaxConcurrentConsumers(config.getMaxConcurrency());
        simpleMessageListenerContainer.setConcurrentConsumers(config.getConcurrentConsumers());
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO); //设置确认模式自动确认
        simpleMessageListenerContainer.setupMessageListener(mqReceiver());
        return simpleMessageListenerContainer;
    }

}
