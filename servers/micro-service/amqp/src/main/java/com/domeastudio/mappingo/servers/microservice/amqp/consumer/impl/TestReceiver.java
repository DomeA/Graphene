package com.domeastudio.mappingo.servers.microservice.amqp.consumer.impl;

import com.domeastudio.mappingo.servers.microservice.amqp.consumer.Receiver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="userQueue")
public class TestReceiver implements Receiver {

    @Override
    public void process(String receive) {
        System.out.println("Receiver1  : " + receive);
    }
}
