package com.kzkj.mq;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

@Component
public class MqSender implements RabbitTemplate.ConfirmCallback
{
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String queue,Object xml,String messageType)
    {
        CorrelationData correlationId = new CorrelationData((String) messageType);
        rabbitTemplate.convertAndSend(queue, xml, correlationId);
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b,
                        String s)
    {

    }
}
