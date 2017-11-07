package com.domeastudio.mappingo.servers.microservice.amqp.message;


public class TestMessage extends BaseMessage{
    private MessageBody message;

    public MessageBody getMessage() {
        return message;
    }

    public void setMessage(MessageBody message) {
        this.message = message;
    }
}
