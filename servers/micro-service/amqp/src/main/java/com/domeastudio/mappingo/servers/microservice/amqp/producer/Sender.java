package com.domeastudio.mappingo.servers.microservice.amqp.producer;

import com.domeastudio.mappingo.servers.microservice.amqp.message.BaseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component
public abstract class Sender<T extends BaseMessage> implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    public abstract void sendMsg(String queueName, String exchange, String content, CorrelationData correlationData);
    public abstract void sendMsg(String exchange, String routingKey, String content);
    public abstract void sendMsg(String queueName,String exchange,T content,CorrelationData correlationData);
    public abstract void sendMsg(String queueName,String exchange,T content);
    public abstract void sendMsg(String queueName,T content);
    public abstract void sendMsg(String queueName,String content);
}
