package com.domeastudio.mappingo.servers.microservice.amqp.producer;

import com.domeastudio.mappingo.servers.microservice.amqp.message.TestMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

@Component
public class TestSender extends Sender<TestMessage> {
    @Override
    public void sendMsg(String queueName, String exchange, String content, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange,queueName,content,correlationData);
    }

    @Override
    public void sendMsg(String queueName,String exchange, String content) {
        rabbitTemplate.convertAndSend(exchange,queueName,content);
    }

    @Override
    public void sendMsg(String queueName, String exchange, TestMessage content, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange,queueName,content,correlationData);
    }

    @Override
    public void sendMsg(String exchange, String routingKey, TestMessage content) {
        rabbitTemplate.convertAndSend(exchange,routingKey,content);
    }

    @Override
    public void sendMsg(String queueName, TestMessage content) {
        rabbitTemplate.convertAndSend(queueName,content);
    }

    @Override
    public void sendMsg(String queueName, String content) {
        rabbitTemplate.convertAndSend(queueName,content);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(" 回调id:" + correlationData.getId());
        if (b) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {

    }
}
