package com.domeastudio.mappingo.servers.microservice.amqp.message;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.support.CorrelationData;

import java.io.Serializable;

public abstract class BaseMessage implements Serializable {
    private String fromMe;
    private String toOther;
    private Boolean isPublisher;
    private Queue queue;
    private Exchange exchange;
    private CorrelationData correlationData;

    public String getFromMe() {
        return fromMe;
    }

    public void setFromMe(String fromMe) {
        this.fromMe = fromMe;
    }

    public String getToOther() {
        return toOther;
    }

    public void setToOther(String toOther) {
        this.toOther = toOther;
    }

    public Boolean getPublisher() {
        return isPublisher;
    }

    public void setPublisher(Boolean publisher) {
        isPublisher = publisher;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public CorrelationData getCorrelationData() {
        return correlationData;
    }

    public void setCorrelationData(CorrelationData correlationData) {
        this.correlationData = correlationData;
    }
}
