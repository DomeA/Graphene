package com.domeastudio.mappingo.servers.microservice.amqp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

/**
 * 消费者
 */
public interface Receiver {
    @RabbitHandler
    void process(String receive);
}
